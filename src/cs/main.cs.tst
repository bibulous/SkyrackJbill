using System;
using System.Net;

/*
  Note that although the proxy class WebServiceSessionLocalService was generated
  with a tool, it had to be edited to work with HTTP version 1.0 instead of 1.1:
  
  	protected override System.Net.WebRequest GetWebRequest(Uri uri) 
	{ 
		System.Net.HttpWebRequest req; 
		req = (System.Net.HttpWebRequest)base.GetWebRequest(uri); 
	
		req.ProtocolVersion = System.Net.HttpVersion.Version10; 
		return req; 
	} 
  
*/

public class DotNetWSTest {
    public static void Main(String[] args) {
        WebServicesSessionLocalService service = new WebServicesSessionLocalService();
        NetworkCredential cre = new NetworkCredential();
        cre.UserName = "wsapi-314";
        cre.Password = "9vmn36dl";
        service.PreAuthenticate = true;
        service.Credentials = cre;

		// create a new user type customer
        UserWS newUser = new UserWS();
        newUser.userName = "myTestCustomer";
        newUser.password = "vanilla";
        newUser.currencyId = 1;  // US Dollars
        newUser.languageId = 1;  // English
        newUser.mainRoleId = 5;  // Type Customer
        newUser.statusId = 1;    // Status Active
        
        // add a contact (all fields optional)
        ContactWS contact = new ContactWS();
        contact.email = "jsmith@trend.com";
        contact.firstName = "John";
        contact.lastName = "Smith";
        contact.initial = "J.";
        contact.title = "Owner";
        contact.countryCode = "US"; // two digits code (ISO-3166)
        contact.organizationName = "Trend Inc.";
        contact.address1 = "1234 Main St";
        contact.address2 = "Suite 100";
        contact.city = "Miami";
        contact.stateProvince = "FL";
        contact.postalCode = "12345";
        contact.phoneCountryCode = 1;
        contact.phoneAreaCode = 800;
        contact.phoneNumber = "303-3030";
        newUser.contact = contact;
        
        // add a credit card
        CreditCardDTO cc = new CreditCardDTO();
        cc.name = "John J. Smith";
        cc.number = "4111-1111 1111-1111  ";
        cc.expiry = DateTime.Now;
        newUser.creditCard = cc;
            
        Console.WriteLine("Creating new customer ... ");
        int newUserId = service.createUser(newUser);
        if (newUserId == 0) {
        	Console.WriteLine("The user " + newUser.userName + 
        			" already exists in the system. Please select a new one");
        	return;
        }
        Console.WriteLine("New user id is " + newUserId);
 
        Console.WriteLine("Done!");
    }
}
