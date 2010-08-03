/*
 jBilling - The Enterprise Open Source Billing System
 Copyright (C) 2003-2009 Enterprise jBilling Software Ltd. and Emiliano Conde

 This file is part of jbilling.

 jbilling is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 jbilling is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sapienter.jbilling.server.payment.tasks;

import com.sapienter.jbilling.server.payment.IExternalCreditCardStorage;
import com.sapienter.jbilling.server.payment.PaymentAuthorizationBL;
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.db.PaymentAuthorizationDTO;
import com.sapienter.jbilling.server.payment.db.PaymentResultDAS;
import com.sapienter.jbilling.server.pluggableTask.PaymentTask;
import com.sapienter.jbilling.server.pluggableTask.PaymentTaskWithTimeout;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.db.CreditCardDAS;
import com.sapienter.jbilling.server.user.db.CreditCardDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PaymentAuthorizeNetCIMTask extends PaymentTaskWithTimeout
        implements PaymentTask, IExternalCreditCardStorage {

    private static class CustomerProfileData {

        private static final String GATEWAY_KEY_DELIMITER = "/";

        private String customerProfileId;
        private String customerPaymentProfileId;

        private CustomerProfileData(String customerProfileId, String customerPaymentProfileId) {
            this.customerProfileId = customerProfileId;
            this.customerPaymentProfileId = customerPaymentProfileId;
        }

        public String getCustomerProfileId() {
            return customerProfileId;
        }

        public void setCustomerProfileId(String customerProfileId) {
            this.customerProfileId = customerProfileId;
        }

        public String getCustomerPaymentProfileId() {
            return customerPaymentProfileId;
        }

        public void setCustomerPaymentProfileId(String customerPaymentProfileId) {
            this.customerPaymentProfileId = customerPaymentProfileId;
        }

        public String toGatewayKey() {
            return customerProfileId + GATEWAY_KEY_DELIMITER + customerPaymentProfileId;
        }

        public static CustomerProfileData buildFromGatewayKey(String gatewayKey) {

            int delimiterPosition = gatewayKey.indexOf(GATEWAY_KEY_DELIMITER);

            String customerProfileId = gatewayKey.substring(0, delimiterPosition);
            String paymentProfileId = gatewayKey.substring(
                    delimiterPosition + GATEWAY_KEY_DELIMITER.length(),
                    gatewayKey.length());

            return new CustomerProfileData(customerProfileId, paymentProfileId);
        }
    }

    // Required parameters
    private static final String PARAMETER_NAME = "login";

    private static final String PARAMETER_KEY = "transaction_key";

    // Optional parameters
    private static final String PARAMETER_TEST_MODE = "test"; // true or false

    /**
     * Validation mode allows you to generate a test transaction at the time you create a customer
     * profile. In Test Mode, only field validation is performed. In Live Mode, a transaction is
     * generated and submitted to the processor with the amount of $0.00 or $0.01. If successful,
     * the transaction is immediately voided. Visa transactions are being switched from $0.01 to
     * $0.00 for all processors. All other credit card types use $0.01. We recommend you consult
     * your Merchant Account Provider before switching to Zero Dollar Authorizations for Visa,
     * because you may be subject to fees For Visa transactions using $0.00, the billTo address and
     * billTo zip fields are required.
     */
    private static final String PARAMETER_VALIDATION_MODE = "validation_mode"; // none/testMode/liveMode

    // Authorize.Net Web Service Resources
    private static final String AUTHNET_XML_TEST_URL = "https://apitest.authorize.net/xml/v1/request.api";

    private static final String AUTHNET_XML_PROD_URL = "https://api.authorize.net/xml/v1/request.api";

    private static final String AUTHNET_XML_NAMESPACE = "AnetApi/xml/v1/schema/AnetApiSchema.xsd";

    private static final Logger LOG = Logger.getLogger(PaymentAuthorizeNetCIMTask.class);

    private String getProcessorName() {
        return "Authorize.Net CIM";
    }

    /**
     * Process jbilling payment
     */
    public boolean process(PaymentDTOEx paymentInfo) throws PluggableTaskException {

        return doProcess(paymentInfo, "profileTransAuthCapture", null);
    }

    public boolean preAuth(PaymentDTOEx paymentInfo) throws PluggableTaskException {

        return doProcess(paymentInfo, "profileTransAuthOnly", null);
    }

    public boolean confirmPreAuth(PaymentAuthorizationDTO paymentAuthDTO, PaymentDTOEx paymentInfo)
            throws PluggableTaskException {

        return doProcess(paymentInfo, "profileTransCaptureOnly", paymentAuthDTO.getApprovalCode());
    }

    public void failure(Integer userId, Integer retry) {
    }

    private boolean doProcess(PaymentDTOEx paymentInfo, String txType, String approvalCode)
            throws PluggableTaskException {
        try {

            ContactBL contact = new ContactBL();
            contact.set(paymentInfo.getUserId());

            if (isCreditCardStored(paymentInfo)) {
                LOG.debug("credit card is obscured, retrieving from database to use external store.");
                paymentInfo.setCreditCard(new UserBL(paymentInfo.getUserId()).getCreditCard());
            } else {
                /*  Credit cards being used for one time payments do not need to be saved in the CIM
                    as they do not represent the customers primary card.

                    Process using the next payment processor in the chain. This should be configured
                    as the PaymentAuthorizeNetTask to process normal credit cards through Authorize.net                 
                 */
                LOG.debug("One time payment credit card (not obscured!), process using the next PaymentTask.");
                paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_UNAVAILABLE));
                return true;
            }

            String XML = getCustomerProfileTransactionRequest(paymentInfo, txType, approvalCode);
            String HTTPResponse = sendViaXML(XML);
            PaymentAuthorizationDTO authorizationDTO = parsePaymentResponse(HTTPResponse);

            if (authorizationDTO.getCode1().equals("1")) {

                paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_OK));
                paymentInfo.setAuthorization(authorizationDTO);
                PaymentAuthorizationBL bl = new PaymentAuthorizationBL();
                bl.create(authorizationDTO, paymentInfo.getId());
                return false;
            } else {

                paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_FAIL));
                paymentInfo.setAuthorization(authorizationDTO);
                PaymentAuthorizationBL bl = new PaymentAuthorizationBL();
                bl.create(authorizationDTO, paymentInfo.getId());
                return false;
            }
        } catch (Exception e) {

            LOG.error(e);
            paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_UNAVAILABLE));
            return true;
        }
    }

    public String storeCreditCard(ContactDTO contact, CreditCardDTO creditCard) {
        LOG.debug("Storing credit card info within " + getProcessorName() + " gateway");

        // fetch contact info if missing
        if (contact == null && creditCard != null && !creditCard.getBaseUsers().isEmpty()) {
            UserDTO user = creditCard.getBaseUsers().iterator().next();
            if (user != null) {
                ContactBL bl = new ContactBL();
                bl.set(user.getId());
                contact = bl.getEntity();
            }
        }       

        // user does not have contact info
        if (contact == null) {
            LOG.error("Could not determine contact info for external credit card storage");
            return null;
        }

        // new contact that has not had a credit card created yet
        if (creditCard == null) {
            LOG.warn("No credit card to store externally.");
            return null;
        }

        // if the credit card has already been obscured, leave it as is.
        if (!creditCard.useGatewayKey() || !creditCard.isNumberObsucred()) {
            String cardRefNumber = storeCreditCardImpl(contact, creditCard).getGatewayKey();
            LOG.debug("Obtained card reference number during external storing: " + cardRefNumber);
            return cardRefNumber;
        } else {
            LOG.debug("Credit card is already externally stored or obscured, skipping external storage.");
        }

        return null;                
    }

    private CreditCardDTO storeCreditCardImpl(ContactDTO contact, CreditCardDTO creditCard) {
        try {
            String XML = getCreateCustomerProfileRequest(contact, creditCard);
            String HTTPResponse = sendViaXML(XML);
            CustomerProfileData profileData = parseProfileResponse(HTTPResponse);
            return updateGatewayKey(creditCard, profileData);

        } catch (PluggableTaskException ex) {
            LOG.error("Could not process external storage payment", ex);
            return null;
        }

    }

    private static boolean isCreditCardStored(PaymentDTOEx payment) {
        return payment.getCreditCard().useGatewayKey();
    }

    private CreditCardDTO updateGatewayKey(CreditCardDTO creditCard, CustomerProfileData customerProfile) {
        // update the gateway key with the returned Authorize.Net customer profile ID and
        // customer payment profile ID
        creditCard.setGatewayKey(customerProfile.toGatewayKey());

        // obscure new credit card numbers
        if (!com.sapienter.jbilling.common.Constants.PAYMENT_METHOD_GATEWAY_KEY.equals(creditCard.getCcType())) {
            creditCard.obscureNumber();
        }
        CreditCardDAS creditCardDAS = new CreditCardDAS();
        return creditCardDAS.save(creditCard);
    }

    /**
     * Builds the XML 'CustomerProfileTransactionRequest' to send to
     * Authorize.Net
     *
     * @param paymentInfo     paymentInfo The PaymentDTOEx object as passed to the
     *                        PaymentTask interface method
     * @param transactionType TransactionType The type of transaction to be processed.
     * @param approvalCode    approvalCode The authorizationCode as returned from
     *                        Authorize.Net during a 'preAuth'
     * @return String The 'CustomerProfileTransactionRequest' XML data
     * @throws PluggableTaskException
     */
    private String getCustomerProfileTransactionRequest(PaymentDTOEx paymentInfo,
                                                        String transactionType, String approvalCode)
            throws PluggableTaskException {

        StringBuffer XML = new StringBuffer();
        ContactBL contactLoader;
        contactLoader = new ContactBL();
        contactLoader.set(paymentInfo.getUserId());

        CustomerProfileData customerProfile = CustomerProfileData.buildFromGatewayKey(
                paymentInfo.getCreditCard().getGatewayKey());

        XML.append("<createCustomerProfileTransactionRequest xmlns=\"" + AUTHNET_XML_NAMESPACE + "\">");
        XML.append(getMerchantAuthenticationXML());
        XML.append("<transaction>");
        XML.append("<" + transactionType + ">");
        XML.append("<amount>" + paymentInfo.getAmount() + "</amount>");

        XML.append("<customerProfileId>"
                + customerProfile.getCustomerProfileId()
                + "</customerProfileId>");

        XML.append("<customerPaymentProfileId>"
                + customerProfile.getCustomerPaymentProfileId()
                + "</customerPaymentProfileId>");

        if (transactionType == "profileTransCaptureOnly") {
            XML.append("<approvalCode>" + approvalCode + "</approvalCode>");
        }

        XML.append("</" + transactionType + ">");
        XML.append("</transaction>");
        XML.append("<extraOptions><![CDATA[x_delim_char=|&x_encap_char=]]></extraOptions>");
        XML.append("</createCustomerProfileTransactionRequest>");

        return XML.toString();
    }

    /**
     * Returns a 'MerchantAuthentication' XML hierarchy
     *
     * @return The formatted 'MerchantAuthentication' XML data
     * @throws PluggableTaskException
     */
    private String getMerchantAuthenticationXML() throws PluggableTaskException {

        String name = ensureGetParameter(PARAMETER_NAME);
        String key = ensureGetParameter(PARAMETER_KEY);

        StringBuffer xml = new StringBuffer();

        xml.append("<merchantAuthentication>");
        xml.append("<name>" + name + "</name>");
        xml.append("<transactionKey>" + key + "</transactionKey>");
        xml.append("</merchantAuthentication>");

        return xml.toString();
    }

    /**
     * Builds the XML 'createCustomerProfileRequest' to send to
     * Authorize.Net
     *
     * @param contact    The ContactDTO object containing customer information.
     * @param creditCard The CreditCardDTO object containing credit card information.
     * @return String The 'createCustomerProfileRequest' XML data
     * @throws PluggableTaskException
     */
    private String getCreateCustomerProfileRequest(ContactDTO contact, CreditCardDTO creditCard)
            throws PluggableTaskException {

        LOG.debug("Contact: " + contact);
        LOG.debug("Credit card: + " + creditCard);

        StringBuffer XML = new StringBuffer();
        XML.append("<createCustomerProfileRequest xmlns=\"" + AUTHNET_XML_NAMESPACE + "\">");
        XML.append(getMerchantAuthenticationXML());
        XML.append("<profile>");
        XML.append("<email>" + contact.getEmail() + "</email>");
        XML.append("<paymentProfiles>");
        if (contact != null) {
            XML.append("<billTo>");
            XML.append("<firstName>" + contact.getFirstName() + "</firstName>");
            XML.append("<lastName>" + contact.getLastName() + "</lastName>");
            XML.append("<company>" + contact.getOrganizationName() + "</company>");
            XML.append("<address>" + contact.getAddress1() + "</address>");
            XML.append("<city>" + contact.getCity() + "</city>");
            XML.append("<state>" + contact.getStateProvince() + "</state>");
            XML.append("<country>" + contact.getCountryCode() + "</country>");
            XML.append("<phoneNumber>" + contact.getPhoneNumber() + "</phoneNumber>");
            XML.append("<faxNumber>" + contact.getFaxNumber() + "</faxNumber>");
            XML.append("</billTo>");
        }
        XML.append("<payment>");
        XML.append("<creditCard>");
        XML.append("<cardNumber>" + creditCard.getNumber() + "</cardNumber>");
        XML.append("<expirationDate>"
                + new SimpleDateFormat("yyyy-MM").format(creditCard.getCcExpiry())
                + "</expirationDate>");
        if (creditCard.getSecurityCode() != null) {
            XML.append("<cardCode>" + creditCard.getSecurityCode() + "</cardCode>");
        }
        XML.append("</creditCard>");
        XML.append("</payment>");
        XML.append("</paymentProfiles>");
        XML.append("</profile>");
        XML.append("<validationMode>" + getOptionalParameter(PARAMETER_VALIDATION_MODE, "none") + "</validationMode>");
        XML.append("</createCustomerProfileRequest>");

        return XML.toString();
    }


    /**
     * Sends the request to the Authorize.Net payment processor
     *
     * @param data String The HTTP POST formatted as a GET string
     * @return String
     * @throws PluggableTaskException
     */
    private String sendViaXML(String data) throws PluggableTaskException {

        int ch;
        StringBuffer responseText = new StringBuffer();
        String XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + data;

        try {

            // Set up the connection
            String mode = getOptionalParameter(PARAMETER_TEST_MODE, "false");
            URL url = (Boolean.valueOf(mode)) ? new URL(AUTHNET_XML_TEST_URL) : new URL(AUTHNET_XML_PROD_URL);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("CONTENT-TYPE", "application/xml");
            conn.setConnectTimeout(getTimeoutSeconds() * 1000);
            conn.setDoOutput(true);

            LOG.debug("Sending request: " + XML);

            // Send the request
            OutputStream ostream = conn.getOutputStream();
            ostream.write(XML.getBytes());
            ostream.close();

            // Get the response
            InputStream istream = conn.getInputStream();
            while ((ch = istream.read()) != -1) {
                responseText.append((char) ch);
            }
            istream.close();
            responseText.replace(0, 3, ""); // KLUDGE: Strips erroneous chars
            // from response stream.

            LOG.debug("Authorize.Net response: " + responseText);

            return responseText.toString();

        } catch (Exception e) {

            LOG.error(e);
            throw new PluggableTaskException(e);
        }
    }

    /**
     * Parses the XML response and stores the values in the
     * PaymentAuthorizationDTO
     *
     * @param HTTPResponse The HTTP response XML string
     * @return PaymentDTO
     * @throws PluggableTaskException
     */
    private PaymentAuthorizationDTO parsePaymentResponse(String HTTPResponse)
            throws PluggableTaskException {

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(HTTPResponse));
            Document doc = builder.parse(inStream);
            doc.getDocumentElement().normalize();
            Element rootElement = doc.getDocumentElement();
            NodeList nodeLst = rootElement.getChildNodes();
            NodeList messagesNodeLst = nodeLst.item(0).getChildNodes();
            NodeList resultCodeNodeLst = messagesNodeLst.item(0).getChildNodes();
            String resultCode = resultCodeNodeLst.item(0).getNodeValue();
            NodeList messageNodeLst = messagesNodeLst.item(1).getChildNodes();
            NodeList codeNodeLst = messageNodeLst.item(0).getChildNodes();
            String code = codeNodeLst.item(0).getNodeValue();
            NodeList textNodeLst = messageNodeLst.item(1).getChildNodes();
            String text = textNodeLst.item(0).getNodeValue();

            PaymentAuthorizationDTO paymentDTO = new PaymentAuthorizationDTO();

            // check for errors
            if (!resultCode.equals("Ok")) {

                paymentDTO.setCode1(resultCode);
                paymentDTO.setCode2(code);
                paymentDTO.setResponseMessage(text);
                paymentDTO.setProcessor("PaymentAuthorizeNetCIMTask");

                return paymentDTO;
            }
            /**
             * If the response was ok the direct response node gets parsed and
             * PaymentAuthorizationDTO gets updated with the parsed values
             */
            NodeList directResponseNodeLst = nodeLst.item(1).getChildNodes();
            String response = directResponseNodeLst.item(0).getNodeValue();
            String[] responseList = response.split("\\|", -2);
            paymentDTO.setApprovalCode(responseList[4]);
            paymentDTO.setAvs(responseList[5]);
            paymentDTO.setProcessor("PaymentAuthorizeNetCIMTask");
            paymentDTO.setCode1(responseList[0]);
            paymentDTO.setCode2(responseList[1]);
            paymentDTO.setCode3(responseList[2]);
            paymentDTO.setResponseMessage(responseList[3]);
            paymentDTO.setTransactionId(responseList[6]);
            paymentDTO.setMD5(responseList[37]);
            paymentDTO.setCreateDate(Calendar.getInstance().getTime());

            return paymentDTO;
        } catch (Exception e) {

            LOG.error(e);
            throw new PluggableTaskException(e);
        }
    }

    private CustomerProfileData parseProfileResponse(String HTTPResponse)
            throws PluggableTaskException {

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(HTTPResponse));
            Document doc = builder.parse(inStream);
            doc.getDocumentElement().normalize();
            Element rootElement = doc.getDocumentElement();
            NodeList nodeLst = rootElement.getChildNodes();
            NodeList messagesNodeLst = nodeLst.item(0).getChildNodes();
            NodeList resultCodeNodeLst = messagesNodeLst.item(0).getChildNodes();
            String resultCode = resultCodeNodeLst.item(0).getNodeValue();
            NodeList messageNodeLst = messagesNodeLst.item(1).getChildNodes();
            NodeList codeNodeLst = messageNodeLst.item(0).getChildNodes();
            String code = codeNodeLst.item(0).getNodeValue();
            NodeList textNodeLst = messageNodeLst.item(1).getChildNodes();
            String text = textNodeLst.item(0).getNodeValue();

            // check for errors
            if (!resultCode.equals("Ok")) {
                throw new PluggableTaskException(
                        String.format("Authorize.Net createCustomerProfile error: %s (code1: %s, code2: %s)",
                                      text, resultCode, code));
            }
            /**
             * If the response was ok the direct response node gets parsed and
             * PaymentAuthorizationDTO gets updated with the parsed values
             */
            NodeList customerProfileIdNodeLst = nodeLst.item(1).getChildNodes();
            String customerProfileId = customerProfileIdNodeLst.item(0).getNodeValue();
            NodeList customerPaymentProfileIdListNodeLst = nodeLst.item(2).getChildNodes();
            NodeList numericStringNodeLst = customerPaymentProfileIdListNodeLst.item(0).getChildNodes();
            String customerPaymentProfileId = numericStringNodeLst.item(0).getNodeValue();

            return new CustomerProfileData(customerProfileId, customerPaymentProfileId);
        } catch (Exception e) {

            LOG.error(e);
            throw new PluggableTaskException(e);
        }
    }
}
