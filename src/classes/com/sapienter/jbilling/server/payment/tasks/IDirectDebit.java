package com.sapienter.jbilling.server.payment.tasks;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Encoder;

public class IDirectDebit {
	
    private static final String requestMethod = "POST";
	private static final String requestHost = "https://secure.ddprocessing.co.uk";
	private static final String adhocValidatePath = "/api/ddi/adhoc/validate";
	private static final String adhocCreatePath = "/api/ddi/adhoc/create"; 
	
	//These need to set on create
	//private String testReferenceNumber = "226730";

	
    //These should be set as PaymentTaskParams
	private static final String username = "apitestno31";
	private static final String password = "APINo3196";	    
	
	//Test
	public static void main (String[]args){
				
		//Create the direct debit (date needs to be set 13 days in advance)
		//validate();
		//String referenceNo = create();
		
		//Charge (date needs to be set 5 days in advance
		//update(String referenceNo, int amountInPence, String debitDate);

		
		// The mandatory request parameters

		//$payer_ref = 'PHP-12345';
		/*
		HttpURLConnection connection = null;		
		
		try{
			//Variable Validate params
			StringBuffer sbParamsVariableValidate = new StringBuffer(120);
			sbParamsVariableValidate.append("variable_ddi[first_name]="+URLEncoder.encode(firstname,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[last_name]="+URLEncoder.encode(lastname,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[address_1]="+URLEncoder.encode(address1,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[town]="+URLEncoder.encode(town,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[postcode]="+URLEncoder.encode(postcode,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[country]="+URLEncoder.encode(country,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[account_name]="+URLEncoder.encode(accountName,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[sort_code]="+URLEncoder.encode(sortCode,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[account_number]="+accountNumber);
			sbParamsVariableValidate.append("&variable_ddi[frequency_type]="+URLEncoder.encode(frequencyType,"UTF-8"));
			sbParamsVariableValidate.append("&variable_ddi[service_user][pslid]="+URLEncoder.encode(pslId,"UTF-8"));
			String variableValidateParams = sbParamsVariableValidate.toString();
			
			//Adhoc Validate params
			StringBuffer sbParamsAdhocValidateCreate = new StringBuffer(120);
			sbParamsAdhocValidateCreate.append("adhoc_ddi[first_name]="+URLEncoder.encode(firstname,"UTF-8"));
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[last_name]="+URLEncoder.encode(lastname,"UTF-8"));
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[address_1]="+URLEncoder.encode(address1,"UTF-8"));
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[town]="+URLEncoder.encode(town,"UTF-8"));
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[postcode]="+URLEncoder.encode(postcode,"UTF-8"));
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[country]="+URLEncoder.encode(country,"UTF-8"));
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[account_name]="+URLEncoder.encode(accountName,"UTF-8"));
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[sort_code]="+URLEncoder.encode(sortCode,"UTF-8"));
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[account_number]="+accountNumber);
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[service_user][pslid]="+URLEncoder.encode(pslId,"UTF-8"));			
			//Optional param
			sbParamsAdhocValidateCreate.append("&adhoc_ddi[email_address]="+URLEncoder.encode(emailAddress,"UTF-8"));
			/*OPTIONAL Params: adhoc_ddi[payer_reference], adhoc_ddi[start_date]Format: YYYY-MM-DD adhoc_ddi[end_date],
			adhoc_ddi[title],adhoc_ddi[address_2],adhoc_ddi[address_3],adhoc_ddi[county]
			adhoc_ddi[email_address],adhoc_ddi[promotion],adhoc_ddi[reference_number]*/
			//Initial Set of Debits Dont need
			//sbParamsAdhocValidateCreate.append("&adhoc_ddi[debits][debit][][amount]="+debitAmountInPence);
			//sbParamsAdhocValidateCreate.append("&adhoc_ddi[debits][debit][][date]="+URLEncoder.encode(debitDate,"UTF-8"));
		/*
			String adhocValidateCreateParams = sbParamsAdhocValidateCreate.toString();
			
			//Update
			StringBuffer sbParamsAdhocUpdate = new StringBuffer(120);
			sbParamsAdhocUpdate.append("&adhoc_ddi[debits][debit][][amount]="+debitAmountInPence);
			sbParamsAdhocUpdate.append("&adhoc_ddi[debits][debit][][date]="+URLEncoder.encode(debitDate,"UTF-8"));			
			String adhocUpdateParams = sbParamsAdhocUpdate.toString();			
			
						
			
		    URL url = new URL(requestHost+adhocValidatePath);
		    connection = (HttpURLConnection)url.openConnection();			
			
		    
			// write auth header for username and password
			BASE64Encoder encoder = new BASE64Encoder();
			String encodedCredential = encoder.encode( (username + ":" + password).getBytes() );
			connection.setRequestProperty("Authorization", "Basic " + encodedCredential);			
			//connection.se

			
		    // Send data - write inputStream if we're doing POST or PUT ???
			connection.setDoOutput(true);				
			connection.setRequestMethod("POST");				
		    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		    System.out.println("RequestMethod:"+requestMethod+" to iDirectDebit URL:"+url.toString()+" output stream params:"+adhocUpdateParams);
		    wr.write(adhocValidateCreateParams);
		    wr.flush();
			
			
			//Make call
			long time = System.currentTimeMillis();			
			connection.connect();		
			int responseCode = connection.getResponseCode();		
			String responseMessage = connection.getResponseMessage(); 
			time = System.currentTimeMillis() - time;		
			System.out.println("Response from iDirectDebit responseCode:"+responseCode+" responseMessage:"+responseMessage+" time:"+time+"ms");//That'll be POST!
			
			//Extract response
			byte buffer[] = new byte[8192];
			int read = 0;	
			       			
			//Success - InputStream
			if (responseCode==200){
				InputStream responseBodyStream = connection.getInputStream();
				StringBuffer responseBody = new StringBuffer();
				while ((read = responseBodyStream.read(buffer)) != -1)
				{
					responseBody.append(new String(buffer, 0, read));
				}
		        System.out.print("Response(sucess) from iDirectDebit:"+responseBody);
		        System.out.flush();            
			}
			else{
				InputStream errorResponseBodyStream = connection.getErrorStream();
				StringBuffer errorResponseBody = new StringBuffer();
				while ((read = errorResponseBodyStream.read(buffer)) != -1)
				{
					errorResponseBody.append(new String(buffer, 0, read));
				}
		        System.out.print("Response(error) from iDirectDebit:"+ errorResponseBody); 			
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
	        System.out.println("Reponse Headers from iDirectDebit:"+sbHeaderValues.toString());	        
		}
		catch(Exception e){
			System.out.println("Exception e:"+e.getMessage());
		}
		finally{
			if (connection!=null){
				connection.disconnect();// Indicates that other requests to the server are unlikely in the near future.
			}
		}
		*/
	}

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
		
        System.out.println("iDirectDebit validate called firstname:"+firstname+" lastname:"+lastname+" address1:"+address1+
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
			sbParamsAdhocValidate.append("&adhoc_ddi[service_user][pslid]="+URLEncoder.encode(username,"UTF-8"));			
			//Optional param
			sbParamsAdhocValidate.append("&adhoc_ddi[email_address]="+URLEncoder.encode(emailAddress,"UTF-8"));
			/*OPTIONAL Params: adhoc_ddi[payer_reference], adhoc_ddi[start_date]Format: YYYY-MM-DD adhoc_ddi[end_date],
			adhoc_ddi[title],adhoc_ddi[address_2],adhoc_ddi[address_3],adhoc_ddi[county]
			adhoc_ddi[email_address],adhoc_ddi[promotion],adhoc_ddi[reference_number]*/
			
			String adhocValidateParams = sbParamsAdhocValidate.toString();
				
			//Make call
			String responseBody = callIDirectDebit(adhocValidatePath, adhocValidateParams);
			
			//Extract referenceNumber from body
			String searchString = "<successful>";	
			if (responseBody!=null && responseBody.contains(searchString) ){
				isValidated=true;				
			}					
		}
		catch(Exception e){
			System.out.println("Exception thrown:"+e.getMessage());
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
		
        System.out.println("iDirectDebit create called firstname:"+firstname+" lastname:"+lastname+" address1:"+address1+
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
			sbParamsAdhocCreate.append("&adhoc_ddi[service_user][pslid]="+URLEncoder.encode(username,"UTF-8"));			
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
			String responseBody = callIDirectDebit(adhocCreatePath, adhocCreateParams);
			
			//Extract referenceNumber from body
			String searchString = "<reference_number>";
			String searchStringEnd = "</reference_number>";			
			if (responseBody!=null && responseBody.contains(searchString) ){
				
				int beginIndex = responseBody.indexOf(searchString);
				int endIndex = responseBody.indexOf(searchStringEnd);
				referenceNumber = responseBody.substring(beginIndex+searchString.length(), endIndex);
			}					
		}
		catch(Exception e){
			System.out.println("Exception thrown:"+e.getMessage());
		}

		return referenceNumber;				
	}

	
	/**
	* This makes a direct debit instructions against an already created customer via iDirectDebit gateway
	* @param String refereceNumber - Uniquely identifies the customer and account. 
	* @param String amountInPence - Amount to be billed
	* @param String debitDate - Must be at least 5 days in the future. Format YYYY-MM-DD e.g.2010-08-12
	* @return boolean willBeBilled
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
	public boolean update(String referenceNumber, int amountInPence, String debitDate){
		
        System.out.println("iDirectDebit update called referenceNumber:"+referenceNumber+" amountInPence:"+amountInPence+" debitDate:"+debitDate);
		
		//Set the return
		boolean willBeBilled = false;		
		
		//Set URL path
		String adhocUpdatePath = "/api/ddi/adhoc/"+referenceNumber+"/update";
		
		try{
		
			//Set the params
			StringBuffer sbParamsAdhocUpdate = new StringBuffer(120);
			sbParamsAdhocUpdate.append("&adhoc_ddi[debits][debit][][amount]="+amountInPence);
			sbParamsAdhocUpdate.append("&adhoc_ddi[debits][debit][][date]="+URLEncoder.encode(debitDate,"UTF-8"));			
			String adhocUpdateParams = sbParamsAdhocUpdate.toString();		
		
			//Make call
			String responseBody = callIDirectDebit(adhocUpdatePath, adhocUpdateParams);
			if (responseBody!=null){
				willBeBilled = true;
			}
			else{
				willBeBilled = false;
			}		
		}
		catch(Exception e){
			System.out.println("Exception thrown:"+e.getMessage());
		}

		return willBeBilled;
				
	}

	/**
	* This makes a call to iDirectDebit
	* @param String urlSuffix - The url which determines whether its a validate, create or update 
	* @param String params - The parameters to be passed in
	* @return String responseBody (on success, otherwise null)
	*/	
	private String callIDirectDebit(String urlSuffix, String params){
		
		HttpURLConnection connection = null;	
		String responseBody = null;
		
		try{
		
			URL url = new URL(requestHost+urlSuffix);
			connection = (HttpURLConnection)url.openConnection();			
			    
			// write auth header for username and password
			BASE64Encoder encoder = new BASE64Encoder();
			String encodedCredential = encoder.encode( (username + ":" + password).getBytes() );
			connection.setRequestProperty("Authorization", "Basic " + encodedCredential);			
		
			// Send data - write inputStream
			connection.setDoOutput(true);				
			connection.setRequestMethod("POST");
			connection.setReadTimeout(1000*60*60); //Give a minute
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			System.out.println("RequestMethod:"+requestMethod+" to iDirectDebit URL:"+url.toString()+" Outputstream params:"+params);
			wr.write(params);
			wr.flush();
				
			//Make call
			long time = System.currentTimeMillis();			
			connection.connect();		
			int responseCode = connection.getResponseCode();		
			String responseMessage = connection.getResponseMessage(); 
			time = System.currentTimeMillis() - time;		
			System.out.println("Response from iDirectDebit responseCode:"+responseCode+" responseMessage:"+responseMessage+" time:"+time+"ms");//That'll be POST!
		
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
				System.out.print("Response(sucess) from iDirectDebit:"+sbResponseBody.toString());
				responseBody = sbResponseBody.toString();
			}
			else{
				InputStream errorResponseBodyStream = connection.getErrorStream();
				StringBuffer errorResponseBody = new StringBuffer();
				while ((read = errorResponseBodyStream.read(buffer)) != -1){
					errorResponseBody.append(new String(buffer, 0, read));
				}
				System.out.print("Response(error) from iDirectDebit:"+ errorResponseBody); 			
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
			System.out.println("Reponse Headers from iDirectDebit:"+sbHeaderValues.toString());
			
			return responseBody;
		}
		catch(Exception e){
			System.out.println("Exception e:"+e.getMessage());
			return responseBody;
		}
		finally{
			if (connection!=null){
				connection.disconnect();// Indicates that other requests to the server are unlikely in the near future.
			}
		}		
	}
}
