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

/*
 * Created on May 5, 2004
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.common;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.apache.commons.validator.Var;
import org.apache.log4j.Logger;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.order.IOrderSessionBean;
import com.sapienter.jbilling.server.order.OrderLineWS;
import com.sapienter.jbilling.server.order.OrderWS;
import com.sapienter.jbilling.server.payment.IPaymentSessionBean;
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.db.PaymentMethodDAS;
import com.sapienter.jbilling.server.process.IBillingProcessSessionBean;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.db.CreditCardDTO;
import com.sapienter.jbilling.server.user.db.CustomerDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.user.permisson.db.RoleDTO;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import com.sapienter.jbilling.server.util.db.LanguageDTO;
import com.sapienter.jbilling.server.util.Context;
import java.util.ArrayList;

/**
 * @author Emil Moved to the common package bacause the web services can use the
 *         validation code
 */
public class GatewayBL {

    class RetValue {
        private String name = null;

        private String value = null;

        public RetValue(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    // result codes
    public static final int RES_CODE_OK = 1;

    public static final int RES_SUB_CODE_OK = 1;

    public static final int RES_CODE_ERROR = 2;

    public static final int RES_SUB_CODE_ERR_CORE = 1; // core parameters
                                                        // missing

    public static final int RES_SUB_CODE_ERR_INTERNAL = 2;

    public static final int RES_SUB_CODE_ERR_AREA = 3;

    public static final int RES_SUB_CODE_ERR_ACTION = 4;

    public static final int RES_SUB_CODE_ERR_NOACCESS = 5;

    public static final int RES_SUB_CODE_ERR_NOAUTH = 6;

    public static final int RES_SUB_CODE_ERR_REQ_UNPASS = 7; // username or
                                                                // password
                                                                // missing

    public static final int RES_SUB_CODE_ERR_REQ = 8; // extra parameters
                                                        // missing

    public static final int RES_SUB_CODE_ERR_TYPE = 9; // wrong data type

    public static final int RES_SUB_CODE_ERR_USER_TYPE = 10; // wrong user
                                                                // type

    public static final int RES_SUB_CODE_ERR_TAKEN = 11; // username is taken
                                                            // for new user

    public static final int RES_SUB_CODE_ERR_CLERK = 12; // wrong user for
                                                            // clerk for partner

    public static final int RES_SUB_CODE_ERR_WRONG = 13; // server error,
                                                            // probably bad
                                                            // param

    public static final int RES_SUB_CODE_ERR_LENGTH = 14; // invlid length of
                                                            // parameter

    public static final int RES_SUB_CODE_ERR_RANGE = 15;

    public static final int RES_SUB_CODE_ERR_VALIDATION = 16; // validatior
                                                                // error

    public static final int RES_SUB_CODE_ERR_USER = 17; // specified user is not
                                                        // valid

    public static final int RES_SUB_CODE_ERR_NOLINES = 18; // no lines for
                                                            // order

    public static final int RES_SUB_CODE_ERR_INVOICE = 19; // errors generating
                                                            // invoice

    // message texts
    private static final String MSG_INTERNAL = "Internal server error";

    private static final String MSG_REQUIRED = "Missing required field";

    private static final String MSG_NOACCESS = "Unauthorized access";

    private static final String MSG_BAD_AREA = "Invalid area";

    private static final String MSG_BAD_ACTION = "Invalid action";

    private static final String MSG_NOAUTH = "Invalid user name or password";

    private static final String MSG_INT = " has to be an integer";

    private static final String MSG_FLOAT = " has to be a float";

    private static final String MSG_DATE = " has to be a date with format yyyy-mm-dd";

    private static final String MSG_USER_TYPE = "Invalid user type";

    private static final String MSG_NAME_TAKEN = "User name for new user already in use";

    private static final String MSG_BAD_CLERK = "Invalid user id as clerk";

    private static final String MSG_BAD_DATA = "Invalid data";

    private static final String MSG_BAD_LENGTH = " length has to be between ";

    private static final String MSG_RANGE = " has to be in range ";

    private static final String MSG_USER = "User not valid";

    private static final String MSG_NOLINES = "No lines found for order";

    private static final String MSG_INVOICE = "Error generating an invoice for the given order id";

    // private fields
    private HttpServletRequest request = null;

    private Integer entityId = null;

    private UserDTO userDto = null;

    private String username = null;

    private String rootUserName; // the login id of the root user

    private static ResourceBundle apps = ResourceBundle.getBundle("ApplicationResources");

    // result fields
    private String separator = "|"; // this is the default

    private int code; // 1

    private int subCode; // 2

    private String text; // 3

    private List resultFields = null;

    private JNDILookup EJBFactory = null;

    private IUserSessionBean userSession = null;

    private Logger log = null;

    private Validator validator = null;

    private ValidatorResources resources = null;

    private void init(String dir, String file) {
        // initialize result fields
        code = RES_CODE_OK;
        subCode = RES_SUB_CODE_OK;
        text = "";

        log = Logger.getLogger(GatewayBL.class);
        InputStream[] ins = new InputStream[2];
        resultFields = new ArrayList();

        try {
            // first add the description of all the validators, in common with
            // the
            // struts web application
            String fileName = dir + "/validator-rules.xml";
            ins[0] = GatewayBL.class.getResourceAsStream(fileName);
            if (ins[0] == null) {
                log.error("failed to open " + fileName);
                throw new Exception("opening " + fileName);
            }
            // now the instructions of which fields to test
            fileName = dir + '/' + file;
            ins[1] = GatewayBL.class.getResourceAsStream(fileName);
            if (ins[1] == null) {
                log.error("failed to open " + fileName);
                throw new Exception("opening " + fileName);
            }
            // this requires validator 1.1. At this time, I had to look for it
            // in
            // google and found it in an obscure maven directory
            // log.debug(ins[1].)

            resources = new ValidatorResources(ins);
            validator = new Validator(resources);
        } catch (Exception e) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_INTERNAL;
            text = MSG_INTERNAL;
            log.error("Exception:" + e.getMessage(), e);

        } finally {
            try {
                // Make sure we close the input streams.
                if (ins[0] != null) {
                    ins[0].close();
                }
                if (ins[1] != null) {
                    ins[1].close();
                }
            } catch (IOException e1) {
                code = RES_CODE_ERROR;
                subCode = RES_SUB_CODE_ERR_INTERNAL;
                text = MSG_INTERNAL;
                log.error("Exception", e1);
            }
        }
    }

    public GatewayBL() {
        init("/META-INF", "ws-validation.xml");
    }

    public GatewayBL(HttpServletRequest request) {
        this.request = request;

        init("/WEB-INF", "validator-beans.xml");
        try {
            userSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
        } catch (Exception e) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_INTERNAL;
            text = MSG_INTERNAL;
            log.error("Exception", e);
        }
    }

    public void process() {

        String companyId; // the unique hash id provided by sapienter
        String area;
        String action;

        if (code != RES_CODE_OK) {
            // something went wrong earlier
            return;
        }

        // start getting the required fields
        rootUserName = getStringPar("s_login", true);
        companyId = getStringPar("s_company_id", true);
        area = getStringPar("s_area", true);
        action = getStringPar("s_action", true);
        username = getStringPar("s_username", true);
        vSize("s_username", 4, 20);

        if (code != RES_CODE_OK) {
            return;
        }

        try {
            // validate the user
            if (!validate(rootUserName, companyId)) {
                return;
            }

            // make sure the involved user is good
            if (!getUser(username, entityId, (area.equals("user") && action.equals("create")))) {
                return;
            }

            // call depending on the area
            if (area.equals("user")) {
                userRequest(action);
            } else if (area.equals("payment")) {
                paymentRequest(action);
            } else if (area.equals("order")) {
                orderRequest(action);
            } else {
                code = RES_CODE_ERROR;
                subCode = RES_SUB_CODE_ERR_AREA;
                text = MSG_BAD_AREA;
                return;
            }
        } catch (SessionInternalError e) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_WRONG;
            text = MSG_BAD_DATA;
        } catch (Exception e) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_INTERNAL;
            text = MSG_INTERNAL;

            log.error("Exception:", e);
        }

    }

    private void orderRequest(String action) throws SessionInternalError, 
            ValidatorException {
        IOrderSessionBean orderSession = (IOrderSessionBean) Context.getBean(
                Context.Name.ORDER_SESSION);

        if (action.equals("create")) {
            OrderWS order = new OrderWS();
            order.setActiveSince(getDatePar("s_active_since"));
            order.setActiveUntil(getDatePar("s_active_until"));
            order.setCurrencyId(getIntPar("s_currency_id"));
            order.setUserId(userDto.getUserId());
            order.setBillingTypeId(getIntPar("s_order_type_id"));
            order.setPeriod(getIntPar("s_period_id"));
            Integer process = getBoolPar("s_process", true);
            Integer generateInvoice = getBoolPar("s_generate_invoice", false);

            if (!validate("Order", order)) {
                return;
            }

            // now add all the orders
            List orderLines = new ArrayList();
            int lineNumber = 1;
            while (true) {
                String prefix = "s_line_" + lineNumber + "_";
                if (request.getParameter(prefix + "type_id") == null) {
                    break;
                }

                OrderLineWS line = new OrderLineWS();

                line.setAmount(getDecimalPar(prefix + "amount"));
                line.setDescription(getStringPar(prefix + "description"));
                line.setItemId(getIntPar(prefix + "item_id"));
                line.setPrice(getDecimalPar(prefix + "price"));
                line.setQuantity(getDecimalPar(prefix + "quantity"));
                line.setTypeId(getIntPar(prefix + "type_id"));
                if (!validate("OrderLine", line)) {
                    return;
                }
                // if no process will take place, all the info has to be there
                if (process.intValue() == 0
                        && (line.getAmount() == null || line.getPrice() == null
                                || line.getQuantity() == null || line.getDescription() == null)) {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_REQ;
                    text = MSG_REQUIRED;
                    return;
                }

                orderLines.add(line);
                lineNumber++;
            }
            ;
            if (lineNumber == 1) {
                // I need at least one line
                code = RES_CODE_ERROR;
                subCode = RES_SUB_CODE_ERR_NOLINES;
                text = MSG_NOLINES;
                return;
            }
            OrderLineWS lines[] = new OrderLineWS[lineNumber - 1];
            log.debug("lineNumber=" + lineNumber + " array=" + lines.length);
            orderLines.toArray(lines);
            log.debug("line2=" + lines.length);
            order.setOrderLines(lines);

            /*
             * Integer newOrderId = orderSession.create(order, entityId,
             * rootUserName, process.intValue() == 1);
             */
            Integer newOrderId = null; // TODO. refactor previous line
            RetValue ret = new RetValue("r_order_id", newOrderId.toString());
            resultFields.add(ret);

            // now see if an invoice has to be generated for the new order
            if (generateInvoice != null && generateInvoice.intValue() == 1) {
                IBillingProcessSessionBean processSession = 
                        (IBillingProcessSessionBean) Context.getBean(
                        Context.Name.BILLING_PROCESS_SESSION);
                // default the language to english
                InvoiceDTO invoice = processSession.generateInvoice(newOrderId, null,
                        new Integer(1));
                if (invoice != null) {
                    ret = new RetValue("r_invoice_id", invoice.getId() + "");
                    resultFields.add(ret);
                } else {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_INVOICE;
                    text = MSG_INVOICE;
                    return;
                }
            }
        } else { // wrong action
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_ACTION;
            text = MSG_BAD_ACTION;
        }
    }

    private void paymentRequest(String action) {
        try {
            IPaymentSessionBean paymentSession = (IPaymentSessionBean) 
                    Context.getBean(Context.Name.PAYMENT_SESSION);

            /*
             * This is meant for online payments, at the time of signup of a
             * customer. That is way is only real-time and the payment date is
             * 'now'. A more complete api would allow for all the functionality
             * that the server offerss.
             */
            if (action.equals("process")) {
                PaymentDTOEx payment = new PaymentDTOEx();
                Integer invoiceId = null;
                CreditCardDTO cc = parseCreditCard();

                if (cc == null) {
                    return;
                }
                payment.setCreditCard(cc);
                payment.setAmount(getDecimalPar("s_amount"));
                payment.setCurrency(new CurrencyDAS().find(getIntPar("s_currency_id")));
                payment.setIsRefund(new Integer(0));
                payment.setPaymentMethod(new PaymentMethodDAS().find(Util.getPaymentMethod(cc.getNumber())));
                payment.setAttempt(new Integer(1));
                payment.setPaymentDate(Calendar.getInstance().getTime());
                invoiceId = getIntPar("s_invoice_id");
                payment.setUserId(userDto.getUserId());

                if (validate("Payment", payment)) {
                    Integer paymentId = paymentSession.processAndUpdateInvoice(payment, invoiceId,
                            entityId);
                    RetValue ret = new RetValue("r_payment_result_id", paymentId.toString());
                    resultFields.add(ret);
                }

            } else { // wrong action
                code = RES_CODE_ERROR;
                subCode = RES_SUB_CODE_ERR_ACTION;
                text = MSG_BAD_ACTION;
            }
        } catch (SessionInternalError e) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_WRONG;
            text = MSG_BAD_DATA;
        } catch (Exception e) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_INTERNAL;
            text = MSG_INTERNAL;

            log.error("Exception in payment", e);
        }

    }

    private CreditCardDTO parseCreditCard() throws ValidatorException {
        CreditCardDTO creditCard = new CreditCardDTO();
        creditCard.setNumber(getStringPar("s_number"));
        creditCard.setName(getStringPar("s_name"));
        validate("CreditCard", creditCard);

        // get the expiry date
        Integer month = getIntPar("s_expiry_month", true);
        Integer year = getIntPar("s_expiry_year", true);
        vRange("s_expiry_month", 1, 12);
        vRange("s_expiry_year", 4, 99);
        if (code != RES_CODE_OK) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.clear();
        cal.set(year.intValue() + 2000, month.intValue() - 1, 1);
        creditCard.setCcExpiry(cal.getTime());

        return creditCard;
    }

    private void userRequest(String action) {

        try {

            if (action.equals("authenticate")) {
                String password = getStringPar("s_password");
                UserDTOEx user = new UserDTOEx();
                user.setUserName(userDto.getUserName());
                user.setPassword(password);
                user.setEntityId(entityId);
                if (!validate("User", user)) {
                    return;
                }

                if (userSession.authenticate(user) == null) {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_NOAUTH;
                    text = MSG_NOAUTH;
                }
            } else if (action.equals("create")) {
                String password = getStringPar("s_password", true);
                if (password == null || !vSize("s_password", 6, 20)) {
                    return;
                }
                UserDTOEx user = new UserDTOEx();
                user.setUserName(username);
                user.setPassword(password);
                user.setEntityId(entityId);
                user.setCurrency(new CurrencyDTO(getIntPar("s_currency_id")));
                user.setLanguage(new LanguageDTO(getIntPar("s_language_id")));
                // only partners and customers are allowed
                Integer type = getIntPar("s_type_id", true);
                if (type == null)
                    return;
                if (!type.equals(Constants.TYPE_PARTNER) && !type.equals(Constants.TYPE_CUSTOMER)) {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_USER_TYPE;
                    text = MSG_USER_TYPE;
                    return;
                }
                user.setMainRoleId(type);
                user.getRoles().add(new RoleDTO(type));

                // see if this is a partner
                if (type.equals(Constants.TYPE_PARTNER)) {
                    Partner partner = new Partner();
                    partner.setAutomaticProcess(getIntPar("s_batch"));
                    partner.setOneTime(getIntPar("s_one_time"));
                    partner.setPeriodUnit(new PeriodUnitDTO(getIntPar("s_period_unit")));
                    partner.setPeriodValue(getIntPar("s_period_value"));
                    partner.setNextPayoutDate(getDatePar("s_next_payout"));
                    partner.setBalance(BigDecimal.ZERO);
                    partner.setPercentageRate(getDecimalPar("s_rate"));
                    partner.setReferralFee(getDecimalPar("s_fee"));
                    partner.setFeeCurrency(new CurrencyDTO(getIntPar("s_fee_currency_id")));
                    partner.setRelatedClerkUserId(getIntPar("s_clerk"));
                    if (!validate("Partner", partner)) {
                        return;
                    }
                    user.setPartner(partner);
                } else {
                    Integer partnerId = getIntPar("s_partner_id");
                    CustomerDTO customer = new CustomerDTO();
                    // the customer might belong to a partner
                    customer.setPartner(new Partner(partnerId));
                    customer.setReferralFeePaid(new Integer(0));
                    user.setCustomer(customer);
                }

                // there might be a whole bunch of error at this point
                if (code != RES_CODE_OK) {
                    return;
                }

                // proceed now with the contact
                ContactDTOEx contact = new ContactDTOEx();
                contact.setEmail(getStringPar("s_email"));
                contact.setAddress1(getStringPar("s_address1"));
                contact.setAddress2(getStringPar("s_address2"));
                contact.setCity(getStringPar("s_city"));
                contact.setCountryCode(getStringPar("s_country_code"));
                contact.setFaxAreaCode(getIntPar("s_fax_area_code"));
                contact.setFaxCountryCode(getIntPar("s_fax_country_code"));
                contact.setFaxNumber(getStringPar("s_fax_number"));
                contact.setFirstName(getStringPar("s_first_name"));
                contact.setInitial(getStringPar("s_initial"));
                contact.setLastName(getStringPar("s_last_name"));
                contact.setOrganizationName(getStringPar("s_organization"));
                contact.setPhoneAreaCode(getIntPar("s_phone_area_code"));
                contact.setPhoneCountryCode(getIntPar("s_phone_country_code"));
                contact.setPhoneNumber(getStringPar("s_phone_number"));
                contact.setPostalCode(getStringPar("s_postal_code"));
                contact.setStateProvince(getStringPar("s_state_province"));
                contact.setTitle(getStringPar("s_title"));

                if (!validate("Contact", contact)) {
                    return;
                }
                Integer result = userSession.create(user, contact);
                if (result == null) {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_TAKEN;
                    text = MSG_NAME_TAKEN;
                } else if (result.intValue() == -1) {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_CLERK;
                    text = MSG_BAD_CLERK;
                } else {
                    // it's good, return the new user id
                    RetValue ret = new RetValue("r_user_id", result.toString());
                    resultFields.add(ret);
                }
            } else if (action.equals("delete")) {
                userSession.delete(userDto.getUserName(), entityId);
            } else if (action.equals("creditcard")) { // create or update the
                                                        // cc
                // the db support many cc per user, but the server doesn't right
                // now

                CreditCardDTO creditCard = parseCreditCard();
                if (creditCard != null) {
                    userSession.updateCreditCard(userDto.getUserName(), entityId, creditCard);
                }
            } else { // wrong action
                code = RES_CODE_ERROR;
                subCode = RES_SUB_CODE_ERR_ACTION;
                text = MSG_BAD_ACTION;
            }
        } catch (SessionInternalError e) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_WRONG;
            text = MSG_BAD_DATA;
        } catch (Exception e) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_INTERNAL;
            text = MSG_INTERNAL;

            log.error("Exception in user", e);
        }

    }

    private BigDecimal getDecimalPar(String name) {
        return getDecimalPar(name, false);
    }

    private BigDecimal getDecimalPar(String name, boolean required) {
        String value = getStringPar(name, required);
        return (value == null ? null : new BigDecimal(value));
    }

    private String getStringPar(String name) {
        return getStringPar(name, false);
    }

    private String getStringPar(String name, boolean required) {
        String retValue = null;
        String field = request.getParameter(name);
        if (required && GenericValidator.isBlankOrNull(field)) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_REQ;
            text = MSG_REQUIRED + " [" + name + "]";
        } else {
            retValue = field;
        }

        return retValue;
    }

    private Integer getIntPar(String name) {
        return getIntPar(name, false);
    }

    private Date getDatePar(String name) {
        return getDatePar(name, false);
    }

    private Date getDatePar(String name, boolean required) {
        Date retValue = null;
        String field = request.getParameter(name);
        if (required && GenericValidator.isBlankOrNull(field)) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_REQ;
            text = MSG_REQUIRED + " [" + name + "]";
        } else {
            if (field != null) {
                retValue = Util.parseDate(field);
                if (retValue == null) {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_TYPE;
                    text = name + MSG_DATE;
                }
            }
        }

        return retValue;
    }

    private Integer getBoolPar(String name, boolean required) {
        Integer retValue = getIntPar(name, required);
        if (retValue != null && !vRange(name, 0, 1)) {
            retValue = null;
        }

        return retValue;
    }

    private boolean vRange(String name, int min, int max) {
        boolean retValue;
        Integer value = getIntPar(name, false);
        retValue = code == RES_CODE_OK;
        if (value != null) {
            retValue = GenericValidator.isInRange(value.intValue(), min, max);

            if (!retValue) {
                code = RES_CODE_ERROR;
                subCode = RES_SUB_CODE_ERR_RANGE;
                text = name + MSG_RANGE + min + " and " + max;
            }
        }

        return retValue;
    }

    private Integer getIntPar(String name, boolean required) {
        Integer retValue = null;
        String field = request.getParameter(name);
        if (required && GenericValidator.isBlankOrNull(field)) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_REQ;
            text = MSG_REQUIRED + " [" + name + "]";
        } else {
            if (field != null) {
                try {
                    retValue = Integer.valueOf(field);
                } catch (NumberFormatException e) {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_TYPE;
                    text = name + MSG_INT;
                }
            }
        }

        return retValue;
    }

    private Float getFloatPar(String name) {
        return getFloatPar(name, false);
    }

    private Float getFloatPar(String name, boolean required) {
        Float retValue = null;
        String field = request.getParameter(name);
        if (required && GenericValidator.isBlankOrNull(field)) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_REQ;
            text = MSG_REQUIRED + " [" + name + "]";
        } else {
            if (field != null) {
                try {
                    retValue = Float.valueOf(field);
                } catch (NumberFormatException e) {
                    code = RES_CODE_ERROR;
                    subCode = RES_SUB_CODE_ERR_TYPE;
                    text = name + MSG_FLOAT;
                }
            }
        }

        return retValue;
    }

    private Double getDoublePar(String name) {
        Double retValue = null;
        String field = request.getParameter(name);
        if (field != null) {
            try {
                retValue = Double.valueOf(field);
            } catch (NumberFormatException e) {
                code = RES_CODE_ERROR;
                subCode = RES_SUB_CODE_ERR_TYPE;
                text = name + MSG_FLOAT;
            }
        }

        return retValue;
    }

    public String getResult() {
        String result;
        String userSeparator = request.getParameter("s_separator");
        if (userSeparator != null) {
            separator = userSeparator;
        }

        // the code and subcode are alwayse there
        result = code + separator + subCode;

        // and the text if there
        if (text != null && text.length() > 0) {
            result += separator + "r_text=" + text;
        }

        // add all the result fields
        for (int f = 0; f < resultFields.size(); f++) {
            RetValue pair = (RetValue) resultFields.get(f);
            result += separator + pair.name + "=" + pair.value;
        }

        return result;
    }

    private boolean getUser(String username, Integer entityId, boolean isCreate)
            throws SessionInternalError {
        boolean retValue = true;
        // get the user id of this user
        userDto = userSession.getUserDTO(username, entityId);
        log.debug("user= " + userDto + " create=" + isCreate);
        if ((userDto == null && !isCreate) || (userDto != null && isCreate)) {
            code = RES_CODE_ERROR;
            subCode = RES_SUB_CODE_ERR_USER;
            text = MSG_USER;
            retValue = false;
        }

        return retValue;

    }

    private boolean vSize(String parameter, int min, int max) {
        boolean retValue;
        String field = getStringPar(parameter, true);
        if (field == null) {
            retValue = false;
        } else {
            retValue = GenericValidator.minLength(field, min)
                    && GenericValidator.maxLength(field, max);
            if (!retValue) {
                code = RES_CODE_ERROR;
                subCode = RES_SUB_CODE_ERR_LENGTH;
                text = " [" + parameter + "]" + MSG_BAD_LENGTH + min + " and " + max;
            }
        }

        return retValue;
    }

    public boolean validate(String name, Object bean) throws ValidatorException {
        // only validate there's no errors already detected
        if (code != RES_CODE_OK) {
            return false;
        }
        boolean success = true;
        ;
        // Tell the validator which bean to validate against.
        validator.setFormName(name);
        validator.setParameter(Validator.BEAN_PARAM, bean);

        ValidatorResults results = null;

        // Run the validation actions against the bean.
        log.info("Validating " + name); // can't print the bean... it put plain credit card numbers in the logs
        results = validator.validate();
        Form form = resources.getForm(Locale.getDefault(), name);
        // Iterate over each of the properties of the Bean which had messages.
        Iterator propertyNames = results.getPropertyNames().iterator();
        while (propertyNames.hasNext()) {
            String propertyName = (String) propertyNames.next();
            // log.debug("name " + propertyName);

            // Get the Field associated with that property in the Form
            Field field = form.getField(propertyName);

            // Look up the formatted name of the field from the Field arg0
            // String prettyFieldName =
            // apps.getString(field.getArg(0).getKey());

            // Get the result of validating the property.
            ValidatorResult result = results.getValidatorResult(propertyName);

            // Get all the actions run against the property, and iterate over
            // their names.
            Map actionMap = result.getActionMap();
            Iterator keys = actionMap.keySet().iterator();
            while (keys.hasNext()) {
                String actName = (String) keys.next();

                // Get the Action for that name.
                ValidatorAction action = resources.getValidatorAction(actName);

                /*
                 * If the result is valid, print PASSED, otherwise print FAILED
                 * log.debug(propertyName + "[" + actName + "] (" +
                 * (result.isValid(actName) ? "PASSED" : "FAILED") + ")");
                 */

                // If the result failed, format the Action's message against the
                // formatted field name
                if (!result.isValid(actName)) {
                    if (!success) {
                        text += "-";
                    } else {
                        success = false;
                        code = RES_CODE_ERROR;
                        subCode = RES_SUB_CODE_ERR_VALIDATION;
                    }
                    String message = apps.getString(action.getMsg());
                    // read the variables
                    Map vars = field.getVars();
                    // will need some changes to accomodate 'range'
                    Object[] args = new Object[2];
                    args[0] = propertyName;
                    Var var = field.getVar(actName);
                    if (var != null) {
                        args[1] = var.getValue();
                    }
                    text += "Object type " + name + ":" + MessageFormat.format(message, args);
                }
            }
        }
        log.debug("Done. result " + success + " message:" + text);
        return success;
    }

    public String getText() {
        return text;
    }

}
