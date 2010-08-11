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

/*
 * PaymentTask for micropayments API.
 * www.micropayments.de
 * 
 * @author Si Bury
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Calendar;

import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.payment.PaymentAuthorizationBL;
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.db.PaymentAuthorizationDTO;
import com.sapienter.jbilling.server.payment.db.PaymentDTO;
import com.sapienter.jbilling.server.payment.db.PaymentResultDAS;
import com.sapienter.jbilling.server.pluggableTask.PaymentTask;
import com.sapienter.jbilling.server.pluggableTask.PaymentTaskWithTimeout;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.util.Constants;


public class PaymentMicropaymentTask extends PaymentTaskWithTimeout implements
		PaymentTask {

    // mandatory parameters
	public static final String PARAMETER_ACCESSKEY = "access_key";
	public static final String PARAMETER_TESTMODE = "test_mode";
	private int testMode = 1;
	private static final int CONNECTION_TIME_OUT = 10000; // in millisec
	//private static final String PARAMETER_WEB_URL = "https://webservices.micropayment.de/public/debit/v1.4/nvp/";
	private static final String PARAMETER_WEB_URL = "web_url";
	public static final String PARAMETER_PROJECT_CODE = "project_code";
	
    // optional parameters
	private String customerId = "JB_CUSTOMER_ID1";
	public static final String PARAMETER_FREE_PARAMS = "jb";
	private static final int REPLY_TIME_OUT = 30000; // in millisec
	private String sessionId = "JB_CUSTOMER_ID1_TEST";
	public static final String PARAMETER_PROCESSOR_NAME = "processor_name";
	private String processorName = "PaymentMicropaymentTask";
	
	//Credit Card Types
	private static final int CC_TYPE_VISA = 2;
	private static final int CC_TYPE_MASTER = 3;
	private static final int CC_TYPE_AMEX = 4; 
	private static final int CC_TYPE_DISC = 6;
	private static final int CC_TYPE_DINE = 7;
	//unsupported though
	private static final int CC_TYPE_JCB = 8;
	 

	/*
	jBilling defs.
	public static final Integer PAYMENT_METHOD_CHEQUE = new Integer(1);
    public static final Integer PAYMENT_METHOD_VISA = new Integer(2);
    public static final Integer PAYMENT_METHOD_MASTERCARD = new Integer(3);
    public static final Integer PAYMENT_METHOD_AMEX = new Integer(4);
    public static final Integer PAYMENT_METHOD_ACH = new Integer(5);
    public static final Integer PAYMENT_METHOD_DISCOVERY = new Integer(6);
    public static final Integer PAYMENT_METHOD_DINERS = new Integer(7);
    public static final Integer PAYMENT_METHOD_PAYPAL = new Integer(8);
	*/

	//Payment Method 
	//May need to swap these if this is order but if no CC details,
	//should do ACH anyway.
	private static final int PAYMENT_METHOD_CC = 1;
	private static final int PAYMENT_METHOD_ACH = 2;

	//To get/set bank account For creating bank account need Strings
	private String country = "DE";
	//ach.aba_routing
	private String bankCode = "12345678";  //sort code, use ABAN field. ABA routing number
	//ach.bank_account
	private String accountNumber = "123456789"; //Bank account number
	//ach.account_name
	private String accountHolder = "SiBury"; //Name on the customer's bank account

	//Micropayment response params.  ach.bank_name
	private String bankName = "SiBury Bank";
	//Micropayment response 
	//Session has been initialized and is waiting for confirmation
	private static final String RESPONSE_CODE_INIT = "INIT";
	//Existing session has been newly created and is waiting for confirmation
	private static final String RESPONSE_CODE_REINIT = "REINIT";
	//Payment has been confirmed; the payment amount will be debited in the next few days.
	private static final String RESPONSE_CODE_APPROVED = "APPROVED";
	//The amount has been successfully debited. 
	private static final String RESPONSE_CODE_CHARGED = "CHARGED";
	//The debit order has failed.  No costs have resulted. 
	private static final String RESPONSE_CODE_FAILED = "FAILED";
	//The debit charge has been reversed or cancelled by the customer.
	//This has caused return debit fees. 
	private static final String RESPONSE_CODE_REVERSED = "REVERSED";
	//In case of failed or reversed status, a detailed explanation
	//of the cause is provided in plain text under "statusDetail". 
	private String RESPONSE_STATUS_DETAIL = "STATUS_DETAIL";
	
	private static final String RESPONSE_CODE_ERROR = "E"; //Exception	
	
	//CC Transaction Types 
	private static final String CC_SALE = "10";
	private static final String CC_AUTH = "11";
	private static final String CC_CAPT = "12";
	private static final String CC_CRED = "13";  //CC Refunds

	//CC Transaction Types 
	private static final String EFT_SALE = "20";
	private static final String EFT_AUTH = "21";
	private static final String EFT_CAPT = "22";
	private static final String EFT_CRED = "23"; //ACH Refund
	
	
	//Response Type
	/*
	private static final String RESPONSE_CODE_APPROVED = "A"; //Approved
	private static final String RESPONSE_CODE_DECLINED = "D"; //Declined
	private static final String RESPONSE_CODE_ERROR = "E"; //Exception
*/
	
	//default fields for testing.
	String payloadData = "";
	String rootUrl = PARAMETER_WEB_URL+"?";
	private String project = "projectCode";
	private String currency = "EUR";
	private String title = "Fax_usage";
	private String payText = "Voxtelo_Jbilling_Test_Buchung";
	private String ip = "tenios.de";
	private Logger log;
	private int amount = 0;

	public PaymentMicropaymentTask() {
        this.log = Logger.getLogger(PaymentMicropaymentTask.class);
    }

	public boolean process(PaymentDTOEx paymentInfo)
			throws PluggableTaskException {
		/*This is main method to process the message.
		 * retValue should be set to true if process succeeds.
		 */
		boolean retValue = false;
			
		try {
			log.debug("PaymentMicropaymentTask.process method being called");
			int method = -1; 
			boolean preAuth = false;
			if (paymentInfo.getIsPreauth() != null && paymentInfo.getIsPreauth().intValue() == 1) {
				preAuth = true;
			}
            log.debug("Payment request Received ; Method : " + paymentInfo.getMethod());
                        
            if (paymentInfo.getCreditCard() != null) {
            	//This should be called for integration with CC gateway in phase2.
            	method = 1;
            	
            } else if (paymentInfo.getAch() != null) {
            	//This should be called for DD integration in phase1.
            	method = 2;
            	log.debug("PaymentMicropaymentTask.process method = " + method);
            } else {
				log.error("PaymentMicropaymentTask.process can't process without a credit card or ach");
                throw new PluggableTaskException("Credit card/ACH not present in payment");
			}
                 
            if (paymentInfo.getIsRefund() == 1 &&
                    (paymentInfo.getPayment() == null ||
                        paymentInfo.getPayment().getAuthorization() ==null)) {
                log.error("Can't process refund without a payment with an" +
                        " authorization record");
                throw new PluggableTaskException("Refund without previous " +
                        "authorization");
            } 
            
			
			//prepare common data for sending to Gateway
			validateParameters();
			//Set the payload data.  Could be a name:value url string for calling
			//the api, or just set params correctly to call web service.
			/*
			 * TODO  here we need to go through the api cycle.
			 * sessionCreate
			 * sessionApprove
			 * sessionGet
			 * 
			 * Then set responses below for further processing.
			 */
			
			//Initialize PaymentAuthorizationDTO.
			PaymentAuthorizationDTO paDto = createPaymentAuthorizationDto();
			
			int sessionCreateResponse = sessionCreate(paymentInfo, method, preAuth, paDto);
			
			int sessionApproveResponse = 0;
			if (sessionCreateResponse == 0 || sessionCreateResponse == 3006) {
				sessionApproveResponse = sessionApprove(paymentInfo, method, preAuth, paDto);
			}
			if (sessionApproveResponse == 0) {
				log.debug("PMT.process session approved");
			}
				
			//This calls gateway and sets response parameters
			PaymentAuthorizationDTO response = sessionGet(paymentInfo, method, preAuth, paDto);
			paymentInfo.setAuthorization(response);

			//Need to add extra statuses here.
			//TODO need more statuses and logic here for micropayments.
			//Check PaymentAuthorizationDTO can hold all data required.
			//PaymentResult common constants may need adding to.  Would rather not!
			log.debug("PMT.process Response code1 = " + response.getCode1());
			if (RESPONSE_CODE_CHARGED.equals(response.getCode2())) {
				paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_OK));
				log.debug("PMT.process result is " + RESPONSE_CODE_CHARGED);
			}
			if (RESPONSE_CODE_APPROVED.equals(response.getCode2())) {
				paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_OK));
				log.debug("PMT.process result is " + RESPONSE_CODE_APPROVED);
				//Is this OK.  Result OK but more processing needs to be done via mesg queue?
			}			
			//RESPONSE_CODE_FAILED
			//RESPONSE_CODE_REVERSED
			//  For now these class as failed.
			else {
				paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_FAIL));
				log.debug("PMT.process result is " + Constants.RESULT_FAIL);
			}

			//
			PaymentAuthorizationBL bl = new PaymentAuthorizationBL();
			bl.create(response, paymentInfo.getId());
			
			} catch (PluggableTaskException e) {
				log.error("PluggableTaskException", e);
				throw e;
			} catch (Exception e) {
				log.error("Exception", e);
				throw new PluggableTaskException(e);
			}
			
		log.debug("PMT.process returning "+retValue);
		
	return retValue;

}

	private PaymentAuthorizationDTO createPaymentAuthorizationDto() {
		PaymentAuthorizationDTO paDto = new PaymentAuthorizationDTO();
		
		String result = (String) parameters.get(PARAMETER_PROCESSOR_NAME);
        if (result == null) {
            result = this.processorName;
        }
		paDto.setProcessor(result);
		return paDto;
	}

	/*ecom_payment_card_type-credit card issuer from Table 5-Credit Card Issuer Types below
		ecom_payment_card_name-cardholder name as it appears on the card
		ecom_payment_card_number-card account number
		ecom_payment_card_expdate_month-numeric month of expiration (Jan = 1)
		ecom_payment_card_expdate_year-four-digit year of expiration
		ecom_payment_card_verification-CVV2/verification number
		pg_procurement_card-indicates procurement card transaction, requires pg_sales_tax and pg_customer_acct_code fields
		pg_customer_acct_code-accounting information for procurement card transactions
		pg_cc_swipe_data-magstripe data from track one or two
		pg_mail_or_phone_order-indicates mail order or phone order transaction (as opposed to an Internet-based transaction) */
	
	private void validateParameters() throws PluggableTaskException {
		//Get mandatory parameters for plugin 3 here.
		ensureGetParameter(PARAMETER_ACCESSKEY);
		ensureGetParameter(PARAMETER_TESTMODE);
		ensureGetParameter(PARAMETER_WEB_URL);
		ensureGetParameter(PARAMETER_PROJECT_CODE);
	}

	public void failure(Integer userId, Integer retry) {
	}


	
/*Credit Card 
			10 SALE Customer is charged
			11 AUTH ONLY Authorization only, CAPTURE transaction required
			12 CAPTURE Completes AUTH ONLY transaction
			13 CREDIT Customer is credited
			14 VOID Cancels non-settled transactions
			15 PRE-AUTH Customer charge approved from other source 
EFT			
			20 SALE Customer is charged 
			21 AUTH ONLY Authorization only, CAPTURE transaction required 
			22 CAPTURE Completes AUTH ONLY transaction 
			23 CREDIT Customer is credited 
			24 VOID Cancels non-settled transactions 
			25 FORCE Customer charged (no validation checks) 
			26 VERIFY ONLY Verification only, no customer charge */


	public String getTransType(PaymentDTOEx paymentInfo, int method, boolean preAuth) 
		throws PluggableTaskException{

		String transType = new String();
				
		if (paymentInfo.getIsRefund() == 1) {
			if(method == PAYMENT_METHOD_CC) {
				transType+=CC_CRED;
			} else if(method == PAYMENT_METHOD_ACH) {
				transType+=EFT_CRED;
			} else {
				log.error("Can't process refund for this method: "+method);
				throw new PluggableTaskException("Can't process refund for this method");
			}
		} else if (paymentInfo.getIsRefund() == 0) {
			

			switch(method) {
				case PAYMENT_METHOD_CC:
					if(preAuth) {
						transType+=CC_AUTH;
					} else {
						transType+=CC_SALE;
					}
					break;
				case PAYMENT_METHOD_ACH:
					if(preAuth) {
						transType+=EFT_AUTH;
					} else {
						transType+=EFT_SALE;
					}
					break;
				default:
					log.error("Unknown payment method : "+method);
					throw new PluggableTaskException("Unknown payment method : Neither Credit nor ACH ");
			}
				
		} else {
				log.error("Unknown transaction type : "+paymentInfo.getIsRefund());
				throw new PluggableTaskException("Unknown transaction type : Neither Credit nor Refund");
		}
		
		return transType;

	}

	public String getCCType(Integer type) {

		log.debug("credit card type: "+type);
		String ccType = "";
		switch(type) {

			case CC_TYPE_VISA:
				ccType+="VISA";
				break;

			case CC_TYPE_MASTER:
				ccType+="MAST";
				break;
			case CC_TYPE_AMEX:
				ccType+="AMER";
				break;

			case CC_TYPE_DISC:
				ccType+="DISC";
				break;

			case CC_TYPE_DINE:
				ccType+="DINE";
				break;

			case CC_TYPE_JCB:
				ccType+="JCB";
				break;

			default:
				log.error("Unknown credit card type: "+type);
				break;
				//throw new PluggableTaskException("Cannot find credit type");
				
		}

		return ccType;
	}

/*	private PaymentAuthorizationDTO processPGRequest(String data) 
		throws PluggableTaskException {


		PaymentAuthorizationDTO dbRow = new PaymentAuthorizationDTO();
		
			
		try {

			//This submits the request to the gateway and processes the response
			//updating the dB.
			//TODO  call spi here and use dbRow to store sessionId in transactionId.
			
			
			BufferedReader br = callPG(data);
			String line = br.readLine();
			log.debug("PMT.processPGRequest Response line: "+br);
			while(line!=null) {
				// check for end of message
				if(line.equals("endofdata")) {
					log.debug("ENDOFDATA");
					break;
				}

				log.debug("Response line: "+line);
				// parse and display name/value pair
				int equalPos=line.indexOf('=');
				String name=line.substring(0,equalPos);
				String value=line.substring(equalPos+1);
				log.debug(name+"="+value);
				if (name.equals("pg_response_type")) {
					dbRow.setCode1(value); // code if 1 it is ok
				}
				if(name.equals("pg_response_code")) {
					dbRow.setCode2(value);
				}
				
				if(name.equals("pg_authorization_code")) {
					dbRow.setApprovalCode(value);
				}
				if(name.equals("pg_response_description")) {
					dbRow.setResponseMessage(value);
				}
				if(name.equals("pg_trace_number")) {
					dbRow.setTransactionId(value);
				}
				//preAuth
				if(name.equals("pg_preauth_result")) {
					dbRow.setCode3(value);
				}
				// get next line
				line = br.readLine();
			}
			br.close();
		}catch(Exception e) {
			log.error("PMT.callPG Error processing payment", e);
		}
		//Add processor to dB row.
		dbRow.setProcessor("MicropaymentGateway");
		
		
		return dbRow;
	}
*/
	
	public boolean preAuth(PaymentDTOEx payment) throws PluggableTaskException {
		log = Logger.getLogger(PaymentMicropaymentTask.class);

		log.error("Processing preAuth Request");
		int method = 1; //CC 
		boolean preAuth = true;
		if (payment.getCreditCard() != null) {
			method = 1;
        } else if (payment.getAch() != null) {
            method = 2;
        } else {
			//hmmm problem
			log.error("Can't process without a credit card or ach");
            throw new PluggableTaskException("Credit card/ACH not present in payment");
		}

		/*
		try {
			validateParameters();
			String data = getChargeData(payment, method, preAuth);
			PaymentAuthorizationDTO response = processPGRequest(data);

			PaymentAuthorizationDTO authDtoEx = new PaymentAuthorizationDTO(
					response);
			PaymentAuthorizationBL bl = new PaymentAuthorizationBL();
			bl.create(authDtoEx, payment.getId());

			payment.setAuthorization(authDtoEx);
			return false;
		} catch (Exception e) {
			log.error("error trying to pre-authorize", e);
			return true;
		}
		*/
		preAuth = false;
		return preAuth;
	}


	/* 
	pay load for confirmPreAuth
	
		Pg_merchant_id N8 M
		pg_password A20 M 
		pg_transaction_type L M 
		pg_merchant_data_[1-9] A40 O
		pg_original_trace_number A36 M
		pg_original_authorization_code 	A80	C (AC) */
		

	public boolean confirmPreAuth(PaymentAuthorizationDTO auth,
			PaymentDTOEx paymentInfo) throws PluggableTaskException {


		log.error("Processing confitmPreAuth Request");
		boolean retValue = false;
/*
		try {

			if (!RESPONSE_CODE_APPROVED.equals(auth.getCode1())) {
				log.error("Cannot process failed preAuth");
				return retValue;
			}
			payloadData+="pg_merchant_id="+ensureGetParameter(PARAMETER_MERCHANT_ID)+"\n";
			payloadData+="pg_password="+ensureGetParameter(PARAMETER_PASSWORD)+"\n";
			String transType = "";
			
			if (paymentInfo.getCreditCard() != null) {
				transType+=CC_CAPT;
            	
            } else if (paymentInfo.getAch() != null) {
            	transType+=EFT_CAPT;
           
            } else {
				//hmmm problem!!! this should not happen
				log.error("Can't process without a credit card or ach");
                throw new PluggableTaskException("Credit card/ACH not present in payment");
				//return false;
			}
                 
			payloadData+="pg_transaction_type="+transType+"\n";
			payloadData+="pg_original_trace_number="+auth.getTransactionId()+"\n";
			payloadData+="pg_original_authorization_code="+auth.getApprovalCode()+"\n";
			payloadData+="endofdata\n";

			BufferedReader br = callPG(payloadData);
			String line = br.readLine();
			
			while(line!=null) {
				// check for end of message
				if(line.equals("endofdata")) {
					log.debug("ENDOFDATA");
					break;
				}

				log.debug("Response line: "+line);
				// parse and display name/value pair
				int equalPos=line.indexOf('=');
				String name=line.substring(0,equalPos);
				String value=line.substring(equalPos+1);
				
				if (name.equals("pg_response_type")) {
					if (RESPONSE_CODE_APPROVED.equals(value)) {
						paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_OK));
						log.debug("preAuth result is ok");
						retValue = false;
					} else {

						paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_FAIL));
						log.debug("preAuth result is failed");
						retValue = true;
					}

					auth.setCode1(value);

				}
				if(name.equals("pg_response_code")) {
					auth.setCode2(value);
				}
				
				if(name.equals("pg_authorization_code")) {
					auth.setApprovalCode(value);
				}
				if(name.equals("pg_response_description")) {
					auth.setResponseMessage(value);
				}
				if(name.equals("pg_trace_number")) {
					auth.setTransactionId(value);
				}
								
				// get next line
				line = br.readLine();
			}
			br.close();

		}catch(Exception e) {
			log.error("error trying to confirm pre-authorize", e);
			throw new PluggableTaskException(e);
		}
*/
	return retValue;
	
}


/*private BufferedReader callPG(String data) throws PluggableTaskException {

	//Set url
	String urlString = "";
	urlString = this.rootUrl+data;
	log.debug("urlString = " + urlString);
	BufferedReader in = null;

	try {
		URL url = new URL(urlString);		
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		
		
		// read the response
			in = new BufferedReader(
					new InputStreamReader(
					connection.getInputStream()));
					
			String decodedString;
		
			while ((decodedString = in.readLine()) != null) {
			    //System.out.println("decodedString = " + decodedString);
				decodedString = URLDecoder.decode(decodedString, "ISO-8859-1");
				log.debug("urlDecodedString = " + decodedString);
			}
			in.close();

		}catch(Exception e) {
			log.error("PMT.callPG Error processing payment = " + e.getMessage());
			in = null;
		}
		return in;	
	}
*/
	
	private int sessionCreate(PaymentDTOEx paymentInfo, int method, boolean preAuth,
			PaymentAuthorizationDTO paDto) {
		int responseCode = 0;
		// TODO update states
		// construct payloadData.
		this.customerId = paymentInfo.getUserId().toString();
		//TODO if this is blank then use return sessionId as  transactionId
		this.sessionId = paDto.getTransactionId();
		if (sessionId == null) {
			this.sessionId = new String(new Integer(paymentInfo.getId()).toString());
			paDto.setTransactionId(sessionId);
		}
		log.debug("PMT.sessionCreate sessionId = " + sessionId);
		BigDecimal decimalAmount = paymentInfo.getAmount();
		decimalAmount = decimalAmount.multiply(new BigDecimal(100));
		//This ignores trailing amounts to convert BigDecimal amount to api int amount.
		this.amount = decimalAmount.toBigInteger().intValue();
		
		payloadData = "";
		payloadData+="action=sessionCreate";

		payloadData+="&accessKey="+parameters.get(PARAMETER_ACCESSKEY);
		payloadData+="&testMode="+parameters.get(PARAMETER_TESTMODE);
		payloadData+="&customerId="+customerId;
		payloadData+="&sessionId="+sessionId;
		payloadData+="&project="+parameters.get(PARAMETER_PROJECT_CODE);
		payloadData+="&amount="+amount;
		payloadData+="&currency="+currency;
		payloadData+="&title="+title;
		payloadData+="&payText="+payText;
		payloadData+="&ip="+ip;
		log.debug("sessionCreate payloadData = "+ payloadData);
		
		//TODO need to call PG and set resultValues based on response.
		//Also need to handle errors.
		try {
			//Set url
			String urlString = "";
			this.rootUrl = parameters.get(PARAMETER_WEB_URL)+"?";
			urlString = rootUrl+payloadData;
			log.debug("urlString = " + urlString);
		
			URL url = new URL(urlString);		
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	
			// read the response
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
						connection.getInputStream()));
						
				String decodedString;
			
				while ((decodedString = in.readLine()) != null) {
					decodedString = URLDecoder.decode(decodedString, "ISO-8859-1");
					log.debug("urlDecodedString = " + decodedString);						
				    //TODO iterate around response params and update classes accordingly.
					//Do primary path first error=0.
					/*
					 * v1.4 response params are
					 * error, status, expire, sessionId, amount, currency, title,payText.
					 */
					log.debug("Response decodedString: "+decodedString);
					// parse and display name/value pair
					int equalPos=decodedString.indexOf('=');
					String name=decodedString.substring(0,equalPos);
					String value=decodedString.substring(equalPos+1);
					log.debug(name+"="+value);
					if (name.equals("error")) {
						paDto.setCode1(value); // code if 0 it is ok
						responseCode = new Integer(value).intValue();
					}
					if(name.equals("status")) {
						paDto.setApprovalCode(value);
						paDto.setCode2(value);
					}
					if(name.equals("sessionId")) {
						paDto.setTransactionId(value);
					}
					if(name.equals("errorMessage")) {
						paDto.setResponseMessage(value);
					}
				}
				in.close();
			} catch(Exception e) {
				log.error("PMT.sessionCreate Error processing payment = " + e.getMessage());
			}
		return responseCode;
	}

	private int sessionApprove(PaymentDTOEx paymentInfo, int method, boolean preAuth,
			PaymentAuthorizationDTO paDto) {
		int responseCode = 0;
		// construct payloadData.
		payloadData = "";
		payloadData+="action=sessionApprove";

		payloadData+="&accessKey="+parameters.get(PARAMETER_ACCESSKEY);
		payloadData+="&testMode="+parameters.get(PARAMETER_TESTMODE);
		payloadData+="&sessionId="+this.sessionId;
		log.debug("PMT.sessionApprove payloadData = "+ payloadData);
		
		//TODO need to call PG and set resultValues based on response.
		//Also need to handle errors.
		try {
			/*Set url
			 * TODO remove connection creation to separate method and configure timeout.
			 */
			String urlString = "";
			this.rootUrl = parameters.get(PARAMETER_WEB_URL)+"?";
			urlString = rootUrl+payloadData;
			log.debug("urlString = " + urlString);
		
			URL url = new URL(urlString);		
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	
			// read the response
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
						connection.getInputStream()));
						
				String decodedString;
			
				while ((decodedString = in.readLine()) != null) {
					decodedString = URLDecoder.decode(decodedString, "ISO-8859-1");
					log.debug("urlDecodedString = " + decodedString);						
				    //TODO iterate around response params and update classes accordingly.
					//Do primary path first error=0.
					//TODO add timeout reading response.
					/*
					 * v1.4 response params are
					 * error, status, expire, errorMessage.
					 */
					log.debug("Response decodedString: "+decodedString);
					// parse and display name/value pair
					int equalPos=decodedString.indexOf('=');
					String name=decodedString.substring(0,equalPos);
					String value=decodedString.substring(equalPos+1);
					log.debug(name+"="+value);
					if (name.equals("error")) {
						paDto.setCode1(value); // code if 0 it is ok
						responseCode = new Integer(value).intValue();
					}
					if(name.equals("status")) {
						paDto.setApprovalCode(value);
						paDto.setCode2(value);
					}
					/*if(name.equals("sessionId")) {
						paDto.setTransactionId(value);
					}*/
					if(name.equals("errorMessage")) {
						paDto.setResponseMessage(value);
					}
				}
				in.close();
			} catch(Exception e) {
				log.error("PMT.sessionApprove Error processing payment = " + e.getMessage());
			}
			return responseCode;
	}

	private PaymentAuthorizationDTO sessionGet(PaymentDTOEx paymentInfo, int method, boolean preAuth,
			PaymentAuthorizationDTO paDto) {
		// Not needed PaymentAuthorizationDTO responseDto = paDto;
		// TODO Auto-generated method stub
		// construct payloadData.
		payloadData = "";
		payloadData+="action=sessionGet";
		//  TODO may parameterise methods? +ensureGetParameter(PARAMETER_CUSTOMER_CREATE)+"\n";
		payloadData+="&accessKey="+parameters.get(PARAMETER_ACCESSKEY);
		payloadData+="&testMode="+parameters.get(PARAMETER_TESTMODE);
		payloadData+="&sessionId="+sessionId;
		log.debug("sessionGet payloadData = "+ payloadData);
		
		//TODO need to call PG and set resultValues based on response.
		//Also need to handle errors.
		try {
			//Set url
			String urlString = "";
			this.rootUrl = parameters.get(PARAMETER_WEB_URL)+"?";
			urlString = rootUrl+payloadData;
			log.debug("urlString = " + urlString);
		
			URL url = new URL(urlString);		
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	
			// read the response
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
						connection.getInputStream()));
						
				String decodedString;
			
				while ((decodedString = in.readLine()) != null) {
					decodedString = URLDecoder.decode(decodedString, "ISO-8859-1");
					log.debug("urlDecodedString = " + decodedString);						
				    //TODO iterate around response params and update classes accordingly.
					//Do primary path first error=0.
					/*
					 * v1.4 response params are
					 * error, status, expire, errorMessage.
					 */
					log.debug("Response decodedString: "+decodedString);
					// parse and display name/value pair
					int equalPos=decodedString.indexOf('=');
					String name=decodedString.substring(0,equalPos);
					String value=decodedString.substring(equalPos+1);
					log.debug(name+"="+value);
					if (name.equals("error")) {
						paDto.setCode1(value); // code if 0 it is ok
						//responseCode = new Integer(value).intValue();
					}
					if(name.equals("status")) {
						paDto.setApprovalCode(value);
						paDto.setCode2(value);
					}
					if(name.equals("sessionId")) {
						paDto.setTransactionId(value);
					}
					if(name.equals("errorMessage")) {
						paDto.setResponseMessage(value);
					}
				}
				in.close();
			} catch(Exception e) {
				log.error("PMT.sessionApprove Error processing payment = " + e.getMessage());
			}
			return paDto;
	}

}
