package com.sapienter.jbilling.server.payment.tasks;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.payment.PaymentAuthorizationBL;
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.db.PaymentAuthorizationDTO;
import com.sapienter.jbilling.server.payment.db.PaymentResultDAS;
import com.sapienter.jbilling.server.user.ContactWS;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.pluggableTask.PaymentTask;
import com.sapienter.jbilling.server.pluggableTask.PaymentTaskWithTimeout;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;

import sun.misc.BASE64Encoder;


public class PaymentIDirectDebitTask extends PaymentTaskWithTimeout implements PaymentTask {
	
    private static final Logger LOG = Logger.getLogger(PaymentIDirectDebitTask.class);
       
	private static final String REQUEST_HOST = "https://secure.ddprocessing.co.uk";
	private static final String ADHOC_VALIDATE_PATH = "/api/ddi/adhoc/validate";
	private static final String ADHOC_CREATE_PATH = "/api/ddi/adhoc/create"; 
	
	//These need to set on create
	//private String testReferenceNumber = "226730";
	private static final int PAYMENT_METHOD_ACH = 2;
	private static final String CCF_REFERENCE_NO = "1"; //The CCF id (customer_field_type) is set to 1,	
	private static final String PROCESSOR = "PaymentIDirectDebitTask";
	public static int CONNECTION_TIMEOUT = 60000;
	
    //These should be set as PaymentTaskParams
	public static String PARAM_IDD_USERNAME = "idd_username";
	public static String PARAM_IDD_PASSWORD = "idd_password";	
 // Needs to be a String as GUI can only pass Strings! 	
	
	/**
	* Validates a set of inputs. Should be used before creating a customer direct debit instruction
	* @param firstname
	* @param lastname
	* @param address1
	* @param town
	* @param postcode
	* @param country
	* @param accountName
	* @param sortcode
	* @param int accoutNumber
	* @param String emailAddress
	* @return boolean isValidated.
	* ResponseBody example:
		Response(sucess) from iDirectDebit:
<?xml version="1.0" encoding="UTF-8"?>
<successful>
  <success>DDI details valid</success>
  <success>Bank Account supports Direct Debit Instructions</success>
</successful>
	*/
	public boolean validate(String firstname, String lastname, String address1, String town, String postcode,
						 	String country, String accountName, String sortCode, int accountNumber, String emailAddress){
		
        LOG.info("iDirectDebit validate called firstname:"+firstname+" lastname:"+lastname+" address1:"+address1+
        		" town:"+town+" postcode:"+postcode+" country:"+country+" accountName:"+accountName+" sortCode:"+sortCode+
        		" accountNumber:"+accountNumber+" emailAddress:"+emailAddress);
		
		//Set the return
		boolean isValidated = false;		
		
		try{
		
			//Set the params
			StringBuffer sbParamsAdhocValidate = new StringBuffer(120);
			sbParamsAdhocValidate.append("adhoc_ddi[first_name]="+URLEncoder.encode(firstname,"UTF-8"));
			sbParamsAdhocValidate.append("&adhoc_ddi[last_name]="+URLEncoder.encode(lastname,"UTF-8"));
			sbParamsAdhocValidate.append("&adhoc_ddi[address_1]="+URLEncoder.encode(address1,"UTF-8"));
			sbParamsAdhocValidate.append("&adhoc_ddi[town]="+URLEncoder.encode(town,"UTF-8"));
			sbParamsAdhocValidate.append("&adhoc_ddi[postcode]="+URLEncoder.encode(postcode,"UTF-8"));
			sbParamsAdhocValidate.append("&adhoc_ddi[country]="+URLEncoder.encode(country,"UTF-8"));
			sbParamsAdhocValidate.append("&adhoc_ddi[account_name]="+URLEncoder.encode(accountName,"UTF-8"));
			sbParamsAdhocValidate.append("&adhoc_ddi[sort_code]="+URLEncoder.encode(sortCode,"UTF-8"));
			sbParamsAdhocValidate.append("&adhoc_ddi[account_number]="+accountNumber);
			sbParamsAdhocValidate.append("&adhoc_ddi[service_user][pslid]="+URLEncoder.encode(PARAM_IDD_USERNAME,"UTF-8"));			
			//Optional param
			sbParamsAdhocValidate.append("&adhoc_ddi[email_address]="+URLEncoder.encode(emailAddress,"UTF-8"));
			/*OPTIONAL Params: adhoc_ddi[payer_reference], adhoc_ddi[start_date]Format: YYYY-MM-DD adhoc_ddi[end_date],
			adhoc_ddi[title],adhoc_ddi[address_2],adhoc_ddi[address_3],adhoc_ddi[county]
			adhoc_ddi[email_address],adhoc_ddi[promotion],adhoc_ddi[reference_number]*/
			
			String adhocValidateParams = sbParamsAdhocValidate.toString();
				
			//Make call
			HashMap<String,String> responseMap = callIDirectDebit(ADHOC_VALIDATE_PATH, adhocValidateParams);
			
			//Extract referenceNumber from body
			String responseBody = responseMap.get("responseBody");
			String searchString = "<successful>";	
			if (responseBody!=null && responseBody.contains(searchString) ){
				isValidated=true;				
			}					
		}
		catch(Exception e){
			LOG.error("iDirectDebit validate Exception thrown:"+e.getMessage());
		}

		return isValidated;				
	}
	
	
	/**
	* Creating a customer direct debit instruction returning a unique identifier, where you can amend.
	* @param firstname
	* @param lastname
	* @param address1
	* @param town
	* @param postcode
	* @param country
	* @param accountName
	* @param sortcode
	* @param int accoutNumber
	* @param String emailAddress
	* @return String referenceNumber on success. Null if not.
	* ResponseBody example:
		Response(sucess) from iDirectDebit:
		<?xml version="1.0" encoding="UTF-8"?>
		<adhoc_ddi>
  			<address_1>123 Fake St</address_1>
  			<country>United Kingdom</country>
  			<email_address>Tom.Veitch@SkyrackTechnology.com</email_address>
  			<first_name>John</first_name>
  			<last_name>Smith</last_name>
  			<postcode>se3 3ed</postcode>
  			<reference_number>226730</reference_number>
  			<start_date type="datetime">2010-08-11T00:00:00Z</start_date>
  			<town>London</town>
  			<debits type="array">
    			<debit type="DdaDebit">
      				<amount type="integer">100</amount>
      				<debit_date type="datetime">2010-08-11T00:00:00Z</debit_date>
    			</debit>
  			</debits>
  			<service_user>
    			<pslid>APITESTNO31</pslid>
  			</service_user>
		</adhoc_ddi>
	*/
	public String create(String firstname, String lastname, String address1, String town, String postcode,
						 String country, String accountName, String sortCode, int accountNumber, String emailAddress){
		
        LOG.info("iDirectDebit create called firstname:"+firstname+" lastname:"+lastname+" address1:"+address1+
        		" town:"+town+" postcode:"+postcode+" country:"+country+" accountName:"+accountName+" sortCode:"+sortCode+
        		" accountNumber:"+accountNumber+" emailAddress:"+emailAddress);
		
		//Set the return
		String referenceNumber = null;		
		
		try{
		
			//Set the params
			StringBuffer sbParamsAdhocCreate = new StringBuffer(120);
			sbParamsAdhocCreate.append("adhoc_ddi[first_name]="+URLEncoder.encode(firstname,"UTF-8"));
			sbParamsAdhocCreate.append("&adhoc_ddi[last_name]="+URLEncoder.encode(lastname,"UTF-8"));
			sbParamsAdhocCreate.append("&adhoc_ddi[address_1]="+URLEncoder.encode(address1,"UTF-8"));
			sbParamsAdhocCreate.append("&adhoc_ddi[town]="+URLEncoder.encode(town,"UTF-8"));
			sbParamsAdhocCreate.append("&adhoc_ddi[postcode]="+URLEncoder.encode(postcode,"UTF-8"));
			sbParamsAdhocCreate.append("&adhoc_ddi[country]="+URLEncoder.encode(country,"UTF-8"));
			sbParamsAdhocCreate.append("&adhoc_ddi[account_name]="+URLEncoder.encode(accountName,"UTF-8"));
			sbParamsAdhocCreate.append("&adhoc_ddi[sort_code]="+URLEncoder.encode(sortCode,"UTF-8"));
			sbParamsAdhocCreate.append("&adhoc_ddi[account_number]="+accountNumber);
			sbParamsAdhocCreate.append("&adhoc_ddi[service_user][pslid]="+URLEncoder.encode(PARAM_IDD_USERNAME,"UTF-8"));			
			//Optional param
			sbParamsAdhocCreate.append("&adhoc_ddi[email_address]="+URLEncoder.encode(emailAddress,"UTF-8"));
			/*OPTIONAL Params: adhoc_ddi[payer_reference], adhoc_ddi[start_date]Format: YYYY-MM-DD adhoc_ddi[end_date],
			adhoc_ddi[title],adhoc_ddi[address_2],adhoc_ddi[address_3],adhoc_ddi[county]
			adhoc_ddi[email_address],adhoc_ddi[promotion],adhoc_ddi[reference_number]*/
			//Initial Set of Debits Dont need
			//sbParamsAdhocValidateCreate.append("&adhoc_ddi[debits][debit][][amount]="+debitAmountInPence);
			//sbParamsAdhocValidateCreate.append("&adhoc_ddi[debits][debit][][date]="+URLEncoder.encode(debitDate,"UTF-8"));			
			String adhocCreateParams = sbParamsAdhocCreate.toString();
				
			//Make call
			HashMap<String,String> hashMap = callIDirectDebit(ADHOC_CREATE_PATH, adhocCreateParams);
			
			//Extract referenceNumber from body
			String responseBody = hashMap.get("responseBody");
			String searchString = "<reference_number>";
			String searchStringEnd = "</reference_number>";			
			if (responseBody!=null && responseBody.contains(searchString) ){
				
				int beginIndex = responseBody.indexOf(searchString);
				int endIndex = responseBody.indexOf(searchStringEnd);
				referenceNumber = responseBody.substring(beginIndex+searchString.length(), endIndex);
			}					
		}
		catch(Exception e){
			LOG.info("iDirectDebit create Exception thrown:"+e.getMessage());
		}

		return referenceNumber;				
	}

	
	/**
	* This makes a direct debit instructions against an already created customer via iDirectDebit gateway
	* @param String refereceNumber - Uniquely identifies the customer and account. 
	* @param String amountInPence - Amount to be billed
	* @param String debitDate - Must be at least 5 days in the future. Format YYYY-MM-DD e.g.2010-08-12
	* @return String dataReturned
	* 
	* ResponseUpdate will be like
		Response(sucess) from iDirectDebit:<?xml version="1.0" encoding="UTF-8"?>
		<adhoc_ddi>
  			<address_1>123 Fake St</address_1>
  			<country>United Kingdom</country>
  			<email_address>Tom.Veitch@SkyrackTechnology.com</email_address>
  			<first_name>John</first_name>
  			<last_name>Smith</last_name>
  			<postcode>se3 3ed</postcode>
  			<reference_number>226730</reference_number>
  			<start_date type="datetime">2010-08-11T00:00:00Z</start_date>
  			<town>London</town>
  			<debits type="array">
    			<debit type="DdaDebit">
      				<amount type="integer">100</amount>
      				<debit_date type="datetime">2010-08-11T00:00:00Z</debit_date>
    			</debit>
    			<debit type="DdaDebit">
      				<amount type="integer">500</amount>
      				<debit_date type="datetime">2010-08-12T00:00:00Z</debit_date>
    			</debit>
  			</debits>
  			<service_user>
    			<pslid>APITESTNO31</pslid>
  			</service_user>
		</adhoc_ddi>
	*/
	public PaymentAuthorizationDTO update(String referenceNumber, int amountInPence, String debitDate) throws Exception {
		
        LOG.info("iDirectDebit update called referenceNumber:"+referenceNumber+" amountInPence:"+amountInPence+" debitDate:"+debitDate);
				
		//Set URL path
		String adhocUpdatePath = "/api/ddi/adhoc/"+referenceNumber+"/update";
				
		//Set the params
		StringBuffer sbParamsAdhocUpdate = new StringBuffer(120);
		sbParamsAdhocUpdate.append("&adhoc_ddi[debits][debit][][amount]="+amountInPence);
		sbParamsAdhocUpdate.append("&adhoc_ddi[debits][debit][][date]="+URLEncoder.encode(debitDate,"UTF-8"));			
		String adhocUpdateParams = sbParamsAdhocUpdate.toString();		
		
		//Make call
		HashMap<String,String> responseMap = callIDirectDebit(adhocUpdatePath, adhocUpdateParams);
			
		//Initialise response for JBilling
		PaymentAuthorizationDTO paymentAuthDTO = new PaymentAuthorizationDTO();			
			
		//Extract values and insert into return
		String responseCode = responseMap.get("responseCode");
		String responseMessage = responseMap.get("responseMessage");
		String responseBody = responseMap.get("responseBody");			
        LOG.debug("iDirectDebit update returned responseCode:"+responseCode+" responseMessage:"+responseMessage+" responseBody:"+responseBody);
		
		//Set paymentAuthDTO
		paymentAuthDTO.setCode1(responseCode);		
		if (responseMessage!=null){
			paymentAuthDTO.setCode2(responseMessage);
		}		
		paymentAuthDTO.setProcessor(PROCESSOR);
		Date now = new Date();
		paymentAuthDTO.setCreateDate(now);
		
		//Error code
		String error = "";
		if (!("200".equals(responseCode))){
			//Extract error from body
			String searchString = "<error>";
			String searchStringEnd = "</error>";			
			if (responseBody!=null && responseBody.contains(searchString) ){
				
				int beginIndex = responseBody.indexOf(searchString);
				int endIndex = responseBody.indexOf(searchStringEnd);
				error = responseBody.substring(beginIndex+searchString.length(), endIndex);
				
				paymentAuthDTO.setResponseMessage(error);
			}	
		}

        LOG.debug("iDirectDebit update paymentAuthDTO set with code1:"+responseCode+" code2:"+responseMessage+" processor:"+PROCESSOR+" createdDate:"+now.toString()+" responseMessage:"+error);		
		
		return paymentAuthDTO;
				
	}

	/**
	* This makes a call to iDirectDebit
	* @param String urlSuffix - The url which determines whether its a validate, create or update 
	* @param String params - The parameters to be passed in
	* @return HashMap responseCode (e.g. "200","404"), responseMessage("OK") and responseBody
	*/	
	private HashMap<String,String> callIDirectDebit(String urlSuffix, String params){
		
		HttpURLConnection connection = null;	
		String responseBody = null;
		int responseCode = 0;
		HashMap<String,String> responseMap  = new HashMap<String,String>();
				
		try{
		
			URL url = new URL(REQUEST_HOST+urlSuffix);
			connection = (HttpURLConnection)url.openConnection();			
			    
			// write auth header for username and password
			BASE64Encoder encoder = new BASE64Encoder();
			String encodedCredential = encoder.encode( (PARAM_IDD_USERNAME + ":" + PARAM_IDD_PASSWORD).getBytes() );
			connection.setRequestProperty("Authorization", "Basic " + encodedCredential);			
		
			// Send data - write inputStream
			connection.setDoOutput(true);				
			connection.setRequestMethod("POST");
			connection.setReadTimeout(CONNECTION_TIMEOUT); //Give a minute
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			LOG.info("RequestMethod:POST to iDirectDebit URL:"+url.toString()+" Outputstream params:"+params);
			wr.write(params);
			wr.flush();
				
			//Make call
			long time = System.currentTimeMillis();			
			connection.connect();		
			responseCode = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage(); 
			time = System.currentTimeMillis() - time;		
			LOG.info("Response from iDirectDebit responseCode:"+responseCode+" responseMessage:"+responseMessage+" time:"+time+"ms");//That'll be POST!
		
			//Extract response
			byte buffer[] = new byte[8192];
			int read = 0;	
		       			
			//Success - InputStream
			if (responseCode==200){
				InputStream responseBodyStream = connection.getInputStream();
				StringBuffer sbResponseBody = new StringBuffer();
				while ((read = responseBodyStream.read(buffer)) != -1){
					sbResponseBody.append(new String(buffer, 0, read));
				}
				LOG.debug("Response(sucess) from iDirectDebit:"+sbResponseBody.toString());
				responseBody = sbResponseBody.toString();
			}
			else{
				InputStream errorResponseBodyStream = connection.getErrorStream();
				StringBuffer sbResponseBody = new StringBuffer();
				while ((read = errorResponseBodyStream.read(buffer)) != -1){
					sbResponseBody.append(new String(buffer, 0, read));
				}
				LOG.error("Response(error) from iDirectDebit:"+ sbResponseBody);
				responseBody = sbResponseBody.toString();				
			}
		
			// Print Headers
			// the 0th header has a null key, and the value is the response line ("HTTP/1.1 200 OK" or whatever)
			String header = null;
			String headerValue = null;
			StringBuffer sbHeaderValues = new StringBuffer(120);
			int index = 0;
			while ((headerValue = connection.getHeaderField(index)) != null){
				header = connection.getHeaderFieldKey(index);
		                
				if (header == null){
					sbHeaderValues.append("["+headerValue+"] ");
				}
				else{
					sbHeaderValues.append("["+header+": "+headerValue+"] ");
				}
				index++;
			}
			LOG.debug("Reponse Headers from iDirectDebit:"+sbHeaderValues.toString());
			
			//Set return
			responseMap.put("responseCode", ""+responseCode);
			responseMap.put("responseMessage", responseMessage);
			responseMap.put("responseBody", responseBody);			
			
			return responseMap;
		}
		catch(Exception e){
			LOG.error("callIDirectDebit Exception e:"+e.getMessage());
			return null;
		}
		finally{
			if (connection!=null){
				connection.disconnect();// Indicates that other requests to the server are unlikely in the near future.
			}
		}		
	}

	@Override
	public boolean confirmPreAuth(PaymentAuthorizationDTO arg0,
			PaymentDTOEx arg1) throws PluggableTaskException {
		// Not supported
		return false;
	}

	@Override
	public void failure(Integer arg0, Integer arg1) {
		// Not supported
		
	}

	@Override
	public boolean preAuth(PaymentDTOEx arg0) throws PluggableTaskException {
		// Not supported
		return false;
	}

	@Override
	/*
	 * Method gets params and calls IDirectDebit update.
	 * The customer ach must have been set up with a stored reference number	 * 
	 */
	public boolean process(PaymentDTOEx paymentDTOEx) throws PluggableTaskException {
	
		try{
			// VALIDATION		
			//Make sure we've got payment details
			if (paymentDTOEx.getAch()==null){
				LOG.error("process - paymentDTOEx.getAch is null");
				throw new PluggableTaskException();
			}
			
			//Make sure all the required params have been passed in
			validateParameters();
			
			// GET DETAILS referenceNumber, amountInPence and debitDate
			// DebitDate - We assume here that the account has been created at least 12 days ago
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		       
			String debitDate = df.format(System.currentTimeMillis()); //13 days after request		
			
			// amountInPence - in DB its held without any decimal places
			BigDecimal amount = paymentDTOEx.getAmount();
			BigDecimal amountPence = amount.movePointRight(amount.scale());
			int amountInPence = amountPence.intValue();
			
			// referenceNumber - this is stored in customer contact field
			UserDTOEx userDTOEx = (UserDTOEx) paymentDTOEx.getBaseUser();		
			UserWS userWS = new UserWS(userDTOEx);
			ContactWS contactWS = userWS.getContact();
			String fieldNames[] = contactWS.getFieldNames();
			String fieldValues[] = contactWS.getFieldValues();
			String referenceNumber = null;
			for (int i=0; i<fieldNames.length; i++){
				LOG.debug("fieldNames["+i+"]:"+fieldNames[i]);
				LOG.debug("fieldValues["+i+"]:"+fieldValues[i]);	
				if ("ccf.idd_reference_no".equals(fieldNames[i])){
					referenceNumber = fieldValues[i];
					break;
				}
			}		
			
			//make iDirectDebit call
			LOG.info("process - calling update method with referenceNo:"+referenceNumber+" amountInPence:"+amountInPence+" debitDate:"+debitDate);
			//Handle response and update db
	        PaymentAuthorizationDTO paymentAuthDTOResponse = update(referenceNumber, amountInPence, debitDate);
			
	        //Set the PaymentAuthorizationDTO
			paymentDTOEx.setAuthorization(paymentAuthDTOResponse);
	        
			//Set the PaymentAuthorizationDTO payment result
			if (!("200".equals(paymentAuthDTOResponse.getCode1()))){						
				paymentDTOEx.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_OK));
				LOG.info("process setting success payment result:"+Constants.RESULT_OK);			
			}
			else{ //fail
				paymentDTOEx.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_FAIL));
				LOG.info("process setting success payment result:"+Constants.RESULT_FAIL);			
			}	
			
			//Create the paymentAuthorization record
			PaymentAuthorizationBL bl = new PaymentAuthorizationBL();
			bl.create(paymentAuthDTOResponse, paymentDTOEx.getId());
			
		}
		catch(Exception e){
			LOG.error("process Exception thrown:"+e.getMessage()+" throwing PluggableTaskException");
			throw new PluggableTaskException(e);
		}
		
		//Return false as we dont wish to failover to another payment gateway
		return false;
	}
	
	/*
	 * Ensure that these parameters are retrieved 
	 */	
	private void validateParameters() throws PluggableTaskException {
		ensureGetParameter(PARAM_IDD_USERNAME);
		ensureGetParameter(PARAM_IDD_PASSWORD);
	}	
}
