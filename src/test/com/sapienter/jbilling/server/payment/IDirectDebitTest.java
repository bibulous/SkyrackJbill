package com.sapienter.jbilling.server.payment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.sapienter.jbilling.server.payment.tasks.PaymentIDirectDebitTask;
import com.sapienter.jbilling.server.user.ContactWS;
import com.sapienter.jbilling.server.user.UserDTOEx;

import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.db.AchDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.api.JbillingAPI;
import com.sapienter.jbilling.server.util.api.JbillingAPIException;
import com.sapienter.jbilling.server.util.api.JbillingAPIFactory;

import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.db.PaymentAuthorizationDTO;
import com.sapienter.jbilling.server.payment.db.PaymentResultDAS;

import junit.framework.TestCase;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Date;

public class IDirectDebitTest extends TestCase{
	
	protected void setUp() {
	   	PaymentIDirectDebitTask.PARAM_IDD_USERNAME = "apitestno31";
	   	PaymentIDirectDebitTask.PARAM_IDD_PASSWORD = "APINo3196";	
	}	

	
	//Tests whether you can extract from a value fro a response body.
	//Quicker than XPath API
	public void testReferenceNumberExtraction(){
		String createResponseBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+
									"<adhoc_ddi>"+"\n"+
										"<address_1>123 Fake St</address_1>"+"\n"+
										"<country>United Kingdom</country>"+"\n"+
										"<email_address>Tom.Veitch@SkyrackTechnology.com</email_address>"+"\n"+
										"<first_name>John</first_name>"+"\n"+
										"<last_name>Smith</last_name>"+"\n"+
										"<postcode>se3 3ed</postcode>"+"\n"+
										"<reference_number>226730</reference_number>"+"\n"+
										"<start_date type=\"datetime\">2010-08-11T00:00:00Z</start_date>"+"\n"+
										"<town>London</town>"+"\n"+
										"<debits type=\"array\">"+"\n"+
											"<debit type=\"DdaDebit\">"+"\n"+
												"<amount type=\"integer\">100</amount>"+"\n"+
												"<debit_date type=\"datetime\">2010-08-11T00:00:00Z</debit_date>"+"\n"+
												"</debit>"+"\n"+
											"<debit type=\"DdaDebit\">"+"\n"+
												"<amount type=\"integer\">500</amount>"+"\n"+
												"<debit_date type=\"datetime\">2010-08-12T00:00:00Z</debit_date>"+"\n"+
											"</debit>"+"\n"+
										"</debits>"+"\n"+
										"<service_user>"+"\n"+
											"<pslid>APITESTNO31</pslid>"+"\n"+
										"</service_user>"+"\n"+
									"</adhoc_ddi>";	
		
		String searchString = "<reference_number>";
		String searchStringEnd = "</reference_number>";
		if (createResponseBody.contains(searchString)){
			int beginIndex = createResponseBody.indexOf(searchString);
			int endIndex = createResponseBody.indexOf(searchStringEnd);
			String value = createResponseBody.substring(beginIndex+searchString.length(), endIndex);
			assertEquals("226730",value);
		}	
	}
	/*
	public void testValidate(){
				
		String firstname = "John";
		String lastname = "Smith";
		String address1 = "123 Fake St";
		String town = "London";
		String postcode = "se3 3ed";
		String country = "United Kingdom";
		String accountName = "John Smith";
		String sortCode = "000000";
		int accountNumber = 12345678;
		//String pslId = username;
		String emailAddress = "Tom.Veitch@SkyrackTechnology.com";
		
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		       
		//String debitDate = df.format(new Date()); //Needs to be 13 days ahead
		//String debitDate = "2010-08-12";		
		
		PaymentIDirectDebitTask iDirectDebit = new PaymentIDirectDebitTask();		
		boolean isValidated = iDirectDebit.validate(firstname, lastname, address1, town, postcode, country, accountName, sortCode, accountNumber, emailAddress);
		assertEquals(true,isValidated);
		
		//Test invalid
		sortCode = "123456";
		isValidated = iDirectDebit.validate(firstname, lastname, address1, town, postcode, country, accountName, sortCode, accountNumber, emailAddress);
		assertEquals(false,isValidated);		
		
	}
	
	public void testCreate(){
		
		//Prepare params
		//String username = "apitestno31";
		//String password = "APINo3196";
		
		String firstname = "John";
		String lastname = "Smith";
		String address1 = "123 Fake St";
		String town = "London";
		String postcode = "se3 3ed";
		String country = "United Kingdom";
		String accountName = "John Smith";
		String sortCode = "000000";
		int accountNumber = 12345678;
		//String pslId = username;
		String emailAddress = "Tom.Veitch@SkyrackTechnology.com";
		
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		       
		//String debitDate = df.format(new Date()); //Needs to be 13 days ahead
		//String debitDate = "2010-08-12";		
		
		PaymentIDirectDebitTask iDirectDebit = new PaymentIDirectDebitTask();		
		String referenceNumber = iDirectDebit.create(firstname, lastname, address1, town, postcode, country, accountName, sortCode, accountNumber, emailAddress);
		System.out.println("referenceNumber:"+referenceNumber);
		assertNotNull(referenceNumber);
		
		//Test error
		sortCode = "123456";
		referenceNumber = iDirectDebit.create(firstname, lastname, address1, town, postcode, country, accountName, sortCode, accountNumber, emailAddress);
		assertNull(referenceNumber);		
		
	}	
	
	/*
	public void testUpdate(){
					
		try{
			
			//String referenceNumber = "226943";//Created on 2010-07-30 set for 2010-08-11
			String referenceNumber = "226730"; //Created
			int amountInPence = 600;
			
			//Debit date must not be before the start of the DDI (which is usually 13 days from request). 
			//It'll then take 5 days for the customer to be actually billed
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		       
			String debitDate = df.format(System.currentTimeMillis()+(1000l*60*60*24*10)); //13 days after request				
					
			PaymentIDirectDebitTask iDirectDebit = new PaymentIDirectDebitTask();		
			PaymentAuthorizationDTO paymentAuthDTOResponse = iDirectDebit.update(referenceNumber, amountInPence, debitDate);
			String code1 = paymentAuthDTOResponse.getCode1();
			String code2 = paymentAuthDTOResponse.getCode2();
			String processor = paymentAuthDTOResponse.getProcessor();
			Date createDate = paymentAuthDTOResponse.getCreateDate();
			String responseMessage = paymentAuthDTOResponse.getResponseMessage();
			
			System.out.println("code1:"+code1+" code2:"+code2+" processor:"+processor+" createDate:"+createDate+" responseMessage:"+responseMessage);
			
		}
		catch (Exception e){
			System.out.println("Exception e:"+e.getMessage());
			assertTrue(false);
		}
	}	
	*/
	
	public void testGetAmountFromBigDecimal(){
		
		//Jbilling holds amounts without decimal places
		BigDecimal oneHundredPence = new BigDecimal("100");
		int amountPence = oneHundredPence.intValue();
		assertEquals(100,amountPence);
		
		
		BigDecimal oneHundredPoundsAndFiftySixPence = new BigDecimal("100.56");
		oneHundredPoundsAndFiftySixPence = oneHundredPoundsAndFiftySixPence.movePointRight(oneHundredPoundsAndFiftySixPence.scale());
		amountPence = oneHundredPoundsAndFiftySixPence.intValue();
		assertEquals(10056,amountPence);
		
		BigDecimal oneHundredAndFiftyPence = new BigDecimal("150");
		oneHundredAndFiftyPence = oneHundredAndFiftyPence.movePointRight(oneHundredAndFiftyPence.scale());
		amountPence = oneHundredAndFiftyPence.intValue();
		assertEquals(150,amountPence);		
	}
	
	
	public void testGetReferenceNo(){
		
		try{
		
		/*Set up paymentDTOEx		
		PaymentDTOEx paymentDTOEx = new PaymentDTOEx();
		
		AchDTO achDTO = new AchDTO();
		achDTO.setId(100);
		achDTO.setAbaRouting("000000");
		achDTO.setBankAccount("12345678");
		achDTO.setAccountType(1);
		achDTO.setBankName("TestBank");
		achDTO.setAccountName("TestBankName");
		
		UserDTOEx userDTOExInput = new UserDTOEx();
		//userDTOExInput.setEntityId(3);
		userDTOExInput.setAch(achDTO);
		
		
		paymentDTOEx.setAch(achDTO);
		paymentDTOEx.setAmount(new BigDecimal("150"));
		paymentDTOEx.setAttempt(1);
		paymentDTOEx.setBaseUser(userDTOExInput);
		
		UserDTOEx userDTOEx = (UserDTOEx) paymentDTOEx.getBaseUser();		
		UserWS userWS = new UserWS(userDTOEx);
		ContactWS contactWS = userWS.getContact();
		if (contactWS==null){
			System.out.println("ContactWS is null");
		}
		String fieldNames[] = contactWS.getFieldNames();
		String fieldValues[] = contactWS.getFieldValues();
			
		for (int i=0; i<fieldNames.length; i++){
			System.out.println("fieldNames["+i+"]:"+fieldNames[i]);
			System.out.println("fieldValues["+i+"]:"+fieldValues[i]);				
		}
		*/
			
			JbillingAPI api = null;
			try {
				api = JbillingAPIFactory.getAPI();
			} catch (JbillingAPIException e1) {
				System.out.println("JbillingAPIException - Getting API. Exception:"+e1.getMessage());
			} catch (IOException e1) {
				System.out.println("IOException - Getting API. Exception:"+e1.getMessage());
				e1.printStackTrace();
			}
			boolean canLogin = false;
			
			int userId = 60; //is iddTest
			ContactWS[] contacts = api.getUserContactsWS(userId);
			for (int i=0; i<contacts.length; i++){
				ContactWS contactWS = (ContactWS)contacts[i];
				
				String firstName = contactWS.getFirstName();
				String organizationName = contactWS.getOrganizationName();
				
				System.out.println("firstName["+i+"]:"+firstName);
				System.out.println("organizationName["+i+"]:"+organizationName);				
				
				String fieldNames[] = contactWS.getFieldNames();
				String fieldValues[] = contactWS.getFieldValues();
				
				
				
				for (int j=0; j<fieldNames.length; j++){
					System.out.println("fieldNames["+j+"]:"+fieldNames[j]);
					System.out.println("fieldValues["+j+"]:"+fieldValues[j]);				
				}				
				
			}
			
		}catch(Exception e){
			System.out.println("Exception e:"+e.getMessage());
		}
		
			
	}
	
}
