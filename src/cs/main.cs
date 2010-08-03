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
        cre.UserName = "testapi";
        cre.Password = "blue-3324";
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
        
        // fetch the user we have just created
        UserWS myUser = service.getUserWS(newUserId);
        // show some fields
        Console.WriteLine("my user name = " + myUser.contact.firstName +
        		" " + myUser.contact.lastName);
        Console.WriteLine("my user credit card = " + myUser.creditCard.number);
        
        // Add a purchase order for my new user
        OrderWS newOrder = new OrderWS();
        newOrder.userId = newUserId;
        newOrder.billingTypeId = 1; // pre-paid
        newOrder.period = 1; 		// one time
        newOrder.currencyId = 1;    // US Dollars
        
        // now add some lines
        OrderLineWS[] lines = new OrderLineWS[2];

		// This line will be using the information from the item
        lines[1] = new OrderLineWS();
        lines[1].typeId = 1;  // line with items
        lines[1].quantity = 1;
        lines[1].itemId = 307;
        lines[1].useItem = true; // this indicates that price, and description are those from the item

		// I put all the information manually by not setting the 'useItem' flag      
        lines[0] = new OrderLineWS();
        lines[0].price = 10;
        lines[0].typeId = 1;  // line with items
        lines[0].quantity = 1;
        lines[0].amount = 10; // usually is price * quantity
        lines[0].description = "Monthly top banner subscription";
        lines[0].itemId = 308;
        
        newOrder.orderLines = lines;
        Console.WriteLine("Creating new purchase order ... ");
        int newOrderId = service.createOrder(newOrder);
        Console.WriteLine("New order id is " + newOrderId);
        
        // get the latest purchase order of my user
        OrderWS latestOrder = service.getLatestOrder(newUserId);
        Console.WriteLine(latestOrder.id + " should be equal to " + newOrderId);

		/*
		 * Mega call: Creates a user with a purchase order in one call.
		 * Creates the user, the purchase order, generates an invoice and
		 * process a payment in real-time. 
		 * Use this for the initial signup of a customer.
		 * Future invoices/payments will be done automatically by the billing 
		 * process.
         */
		
        // Delete the user so it can be recreated with a mega-call
        service.deleteUser(newUserId);
        // make the call
        Console.WriteLine("Now making a mega call ... ");
        CreateResponseWS result = service.create(myUser, newOrder);
        // show the results
        Console.WriteLine("new user= " + result.userId + " new order= " +
        		result.orderId + " new invoice= " + result.invoiceId +
        		" new payment= " + result.paymentId + " payment result = " +
        		result.paymentResult);
        
        // Clean up so this test can run again
        service.deleteUser(result.userId);
        Console.WriteLine("Done!");
    }
}
