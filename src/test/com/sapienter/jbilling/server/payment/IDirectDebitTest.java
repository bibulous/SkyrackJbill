package com.sapienter.jbilling.server.payment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.sapienter.jbilling.server.payment.tasks.PaymentIDirectDebitTask;
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
		
		IDirectDebit iDirectDebit = new IDirectDebit();		
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
	public void testUpdate(){
		
		//Prepare params
		//String username = "apitestno31";
		//String password = "APINo3196";
		
		//String referenceNumber = "226943";//Created on 2010-07-30 set for 2010-08-11
		String referenceNumber = "226730"; //Created
		int amountInPence = 600;
		
		//Debit date must not be before the start of the DDI (which is usually 13 days from request). 
		//It'll then take 5 days for the customer to be actually billed
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		       
		String debitDate = df.format(System.currentTimeMillis()+(1000l*60*60*24*13)); //13 days after request				
				
		PaymentIDirectDebitTask iDirectDebit = new PaymentIDirectDebitTask();		
		boolean willBeBilled = iDirectDebit.update(referenceNumber, amountInPence, debitDate);
		
		assertEquals(true,willBeBilled);
	}	
	
}
