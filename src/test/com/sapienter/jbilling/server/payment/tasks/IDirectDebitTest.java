package com.sapienter.jbilling.server.payment.tasks;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.sapienter.jbilling.server.payment.db.PaymentAuthorizationDTO;
import com.sapienter.jbilling.server.util.api.JbillingAPI;
import com.sapienter.jbilling.server.util.api.JbillingAPIException;
import com.sapienter.jbilling.server.util.api.JbillingAPIFactory;

import junit.framework.TestCase;

public class IDirectDebitTest extends TestCase{
	
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
		
		//Prepare params
		//Username and password not stored.
		
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
	/*
	public void testCreate(){
		
		//Prepare params
		//String username = "";
		//String password = "";
		
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
		
		IDirectDebit iDirectDebit = new IDirectDebit();		
		String referenceNumber = iDirectDebit.create(firstname, lastname, address1, town, postcode, country, accountName, sortCode, accountNumber, emailAddress);
		System.out.println("referenceNumber:"+referenceNumber);
		assertNotNull(referenceNumber);
		
		//Test error
		sortCode = "123456";
		referenceNumber = iDirectDebit.create(firstname, lastname, address1, town, postcode, country, accountName, sortCode, accountNumber, emailAddress);
		assertNull(referenceNumber);		
		
	}	
	*/
	/*
	public void testUpdate(){
		
		//Prepare params
		//String username = "";
		//String password = "";
		
		//String referenceNumber = "226943";//Created on 2010-07-30 set for 2010-08-11
		String referenceNumber = "226730"; //Created
		int amountInPence = 600;
		
		//Debit date must not be before the start of the DDI (which is usually 13 days from request). 
		//It'll then take 5 days for the customer to be actually billed
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		       
		String debitDate = df.format(System.currentTimeMillis()+(1000l*60*60*24*13)); //13 days after request				
				
		PaymentIDirectDebitTask paymentIDirectDebit = new PaymentIDirectDebitTask();		
		PaymentAuthorizationDTO paymentAuthorizationDTO = null;
		try {
			paymentAuthorizationDTO = paymentIDirectDebit.update(referenceNumber, amountInPence, debitDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception e:"+e.getMessage());
		}
		
		assertEquals("200",paymentAuthorizationDTO.getCode1());
	}	
	*/
	
	public void testBigAmount(){

		BigDecimal amountInPounds= new BigDecimal(10.00);
		BigDecimal bDAmountInPence = amountInPounds.multiply(new BigDecimal(100));
		int amountInPence = bDAmountInPence.intValue();
		System.out.println("process amountInPence:"+amountInPence);
		assertEquals(1000,amountInPence);
		
		amountInPounds= new BigDecimal(10);	
		bDAmountInPence = amountInPounds.multiply(new BigDecimal(100));
		amountInPence = bDAmountInPence.intValue();		
		System.out.println("process amountInPence:"+amountInPence);
		assertEquals(1000,amountInPence);	
		
		amountInPounds= new BigDecimal(10.50);
		bDAmountInPence = amountInPounds.multiply(new BigDecimal(100));
		amountInPence = bDAmountInPence.intValue();		
		System.out.println("process amountInPence:"+amountInPence);
		assertEquals(1050,amountInPence);		
				
	}
	
	public void testGetDirectDebitDate(){
		PaymentIDirectDebitTask task = new PaymentIDirectDebitTask();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");			
		try{
			String createdDate = "2010-08-20";
			String directDebitDate = task.getDirectDebitDate(createdDate);
			System.out.println("createdDate:"+createdDate+" directDebitDate:"+directDebitDate);
			
			//Ensure its at least 18 days difference
			Date dCreatedDate = df.parse(createdDate);
			long lCreatedDate = dCreatedDate.getTime();
			
			Date dDirectDebitDate = df.parse(directDebitDate);
			long lDirectDebitDate = dDirectDebitDate.getTime();
			
			assertTrue((lDirectDebitDate-lCreatedDate)>=(18l*24*60*60*1000));
			

			createdDate = "2010-08-01";
			directDebitDate = task.getDirectDebitDate(createdDate);
			System.out.println("createdDate:"+createdDate+" directDebitDate:"+directDebitDate);
			
			//Ensure its at least 18 days difference
			dCreatedDate = df.parse(createdDate);
			lCreatedDate = dCreatedDate.getTime();
			
			dDirectDebitDate = df.parse(directDebitDate);
			lDirectDebitDate = dDirectDebitDate.getTime();
			
			assertTrue((lDirectDebitDate-lCreatedDate)>=(18l*24*60*60*1000));
			
			
			createdDate = "2010-08-30";
			directDebitDate = task.getDirectDebitDate(createdDate);
			System.out.println("createdDate:"+createdDate+" directDebitDate:"+directDebitDate);
			
			//Ensure its at least 18 days difference
			dCreatedDate = df.parse(createdDate);
			lCreatedDate = dCreatedDate.getTime();
			
			dDirectDebitDate = df.parse(directDebitDate);
			lDirectDebitDate = dDirectDebitDate.getTime();
			
			assertTrue((lDirectDebitDate-lCreatedDate)>=(18l*24*60*60*1000));			
			
		}
		catch(Exception e){
			
		}
	}
	
	/*
	public void testInsertIDDCustomerContactFields(){
		
		int userId = 50;
		String referenceNo = "111111";
		String createdDate = "2010-08-11";
		
		PaymentIDirectDebitTask paymentIDirectDebitTask = new PaymentIDirectDebitTask();
		boolean wasInserted = paymentIDirectDebitTask.insertIDDCustomerContactFields(userId, referenceNo, createdDate);
		assertTrue(wasInserted);
		
	}
	*/
	
	/*
	 * When creating the ACH account, this needs to be hooked into the API.
	 */
	public void testCreateIDDAccount(){
		
		JbillingAPI api = null;
		int userId = 50;		
		String firstname = "Johnny";
		String lastname = "Smith";
		String address1 = "2 Hessle Mount";
		String town = "Leeds";
		String postcode = "LS6 1AA";
		String country = "UK";
		String accountName ="TestBankName";
		String sortCode ="000000"; //aba_routing
		int accountNumber =12345678;
		String emailAddress = "ThomasVeitch@hotmail.com";		

		try {		
		
			PaymentIDirectDebitTask paymentIDirectDebitTask = new PaymentIDirectDebitTask();
			//TODO: Need to set username and password
			paymentIDirectDebitTask.iddUsername = "";
			paymentIDirectDebitTask.iddPassword = "";
				
			//Get api object
			api = JbillingAPIFactory.getAPI();
		
		
			boolean wasSetUp = paymentIDirectDebitTask.setupIDDAccount(api,
					   					   							userId,
					   					   							firstname, 
					   					   							lastname, 
					   					   							address1, 
					   					   							town, 
					   					   							postcode,
					   					   							country,
					   					   							accountName,
					   					   							sortCode,
					   					   							accountNumber,
					   					   							emailAddress);		
					
			assertEquals(true, wasSetUp);
		
		} catch (Exception e) {
			assertTrue(false);
			System.out.println("iDirectDebit Exception:"+e.getMessage());	
		} 
	}	
}
