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
 * Created on Dec 18, 2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.server.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;

import junit.framework.TestCase;

import com.sapienter.jbilling.server.entity.CreditCardDTO;
import com.sapienter.jbilling.server.entity.PaymentInfoChequeDTO;
import com.sapienter.jbilling.server.invoice.InvoiceWS;
import com.sapienter.jbilling.server.order.OrderLineWS;
import com.sapienter.jbilling.server.order.OrderWS;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.api.JbillingAPI;
import com.sapienter.jbilling.server.util.api.JbillingAPIFactory;

/**
 * @author Emil
 */
public class WSTest extends TestCase {
	
      
    public void testApplyGet() {
        try {
        	
            JbillingAPI api = JbillingAPIFactory.getAPI();
           
            /*
             * apply payment
             */
            PaymentWS payment = new PaymentWS();
            payment.setAmount(new BigDecimal("15.00"));
            payment.setIsRefund(new Integer(0));
            payment.setMethodId(Constants.PAYMENT_METHOD_CHEQUE);
            payment.setPaymentDate(Calendar.getInstance().getTime());
            payment.setResultId(Constants.RESULT_ENTERED);
            payment.setCurrencyId(new Integer(1));
            payment.setUserId(new Integer(2));
						payment.setPaymentNotes("Notes");
						payment.setPaymentPeriod(new Integer(1));
            
            
            PaymentInfoChequeDTO cheque = new PaymentInfoChequeDTO();
            cheque.setBank("ws bank");
            cheque.setDate(Calendar.getInstance().getTime());
            cheque.setNumber("2232-2323-2323");
            payment.setCheque(cheque);
           
            System.out.println("Applying payment");
            Integer ret = api.applyPayment(payment, new Integer(35));
            System.out.println("Created payemnt " + ret);
            assertNotNull("Didn't get the payment id", ret);
            
            /*
             * get
             */
            //verify the created payment       
            System.out.println("Getting created payment");
            PaymentWS retPayment = api.getPayment(ret);
            assertNotNull("didn't get payment ", retPayment);
            assertEquals("created payment result", retPayment.getResultId(), payment.getResultId());
            assertEquals("created payment cheque ", retPayment.getCheque().getNumber(), payment.getCheque().getNumber());
            assertEquals("created payment user ", retPayment.getUserId(),  payment.getUserId());
            assertEquals("notes", retPayment.getPaymentNotes(), payment.getPaymentNotes());
            assertEquals("period", retPayment.getPaymentPeriod(), payment.getPaymentPeriod());


            System.out.println("Validated created payment and paid invoice");
            assertNotNull("payment not related to invoice", retPayment.getInvoiceIds());
            assertTrue("payment not related to invoice", retPayment.getInvoiceIds().length == 1);
            assertEquals("payment not related to invoice", retPayment.getInvoiceIds()[0], new Integer(35));
            
            InvoiceWS retInvoice = api.getInvoiceWS(retPayment.getInvoiceIds()[0]);
            assertNotNull("New invoice not present", retInvoice);
            assertEquals("Balance of invoice should be total of order", BigDecimal.ZERO, retInvoice.getBalanceAsDecimal());
            assertEquals("Total of invoice should be total of order", new BigDecimal("15"), retInvoice.getTotalAsDecimal());
            assertEquals("New invoice not paid", retInvoice.getToProcess(), new Integer(0));
            assertNotNull("invoice not related to payment", retInvoice.getPayments());
            assertTrue("invoice not related to payment", retInvoice.getPayments().length == 1);
            assertEquals("invoice not related to payment", retInvoice.getPayments()[0].intValue(), retPayment.getId());
            
            /*
             * get latest
             */
            //verify the created payment       
            System.out.println("Getting latest");
            retPayment = api.getLatestPayment(new Integer(2));
            assertNotNull("didn't get payment ", retPayment);
            assertEquals("latest id", ret.intValue(), retPayment.getId());
            assertEquals("created payment result", retPayment.getResultId(), payment.getResultId());
            assertEquals("created payment cheque ", retPayment.getCheque().getNumber(), payment.getCheque().getNumber());
            assertEquals("created payment user ", retPayment.getUserId(), payment.getUserId());

            try {
                System.out.println("Getting latest - invalid");
                retPayment = api.getLatestPayment(new Integer(13));
                fail("User 13 belongs to entity 301");
            } catch (Exception e) {
            }
            
            /*
             * get last
             */
            System.out.println("Getting last");
            Integer retPayments[] = api.getLastPayments(new Integer(2), new Integer(2));
            assertNotNull("didn't get payment ", retPayments);
            // fetch the payment
            
            
            retPayment = api.getPayment(retPayments[0]);
            
            assertEquals("created payment result", retPayment.getResultId(), payment.getResultId());
            assertEquals("created payment cheque ", retPayment.getCheque().getNumber(), payment.getCheque().getNumber());
            assertEquals("created payment user ", retPayment.getUserId(), payment.getUserId());
            assertTrue("No more than two records", retPayments.length <= 2);

            try {
                System.out.println("Getting last - invalid");
                retPayments = api.getLastPayments(new Integer(13), 
                	new Integer(2));
                fail("User 13 belongs to entity 301");
            } catch (Exception e) {
            }
            
            
            /*
             * TODO test refunds. There are no refund WS methods.
             * Using applyPayment with is_refund = 1 DOES NOT work
             */

 
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception caught:" + e);
        }
    }

    /**
     * Test for UserIdFilter, NameFilter, AddressFilter, PhoneFilter, 
     * CreditCardFilter and IpAddressFilter
     */
    public void testBlacklistFilters() {
        try {
            Integer userId = 1000; // starting user id

            // expected filter response messages
            String[] message = {
                    "User id is blacklisted.",
                    "Name is blacklisted.",
                    "Address is blacklisted.",
                    "Phone number is blacklisted.", 
                    "Credit card number is blacklisted.", 
                    "IP address is blacklisted." };

            JbillingAPI api = JbillingAPIFactory.getAPI();

            /*
             * Loop through users 1000-1005, which should fail on a respective 
             * filter: UserIdFilter, NameFilter, AddressFilter, PhoneFilter, 
             * CreditCardFilter or IpAddressFilter
             */
            for(int i = 0; i < 6; i++, userId++) {
                // create a new order and invoice it
                OrderWS order = new OrderWS();            
                order.setUserId(userId); 
                order.setBillingTypeId(Constants.ORDER_BILLING_PRE_PAID);
                order.setPeriod(2);
                order.setCurrencyId(new Integer(1));

                // add a line
                OrderLineWS lines[] = new OrderLineWS[1];
                OrderLineWS line;
                line = new OrderLineWS();
                line.setTypeId(Constants.ORDER_LINE_TYPE_ITEM);
                line.setQuantity(new Integer(1));
                line.setItemId(new Integer(1));
                line.setUseItem(new Boolean(true));
                lines[0] = line;

                order.setOrderLines(lines);

                // create the order and invoice it
                System.out.println("Creating and invoicing order ...");
                Integer thisInvoiceId = api.createOrderAndInvoice(order);
                InvoiceWS newInvoice = api.getInvoiceWS(thisInvoiceId);
                Integer orderId = newInvoice.getOrders()[0]; // this is the order that was also created
                assertNotNull("The order was not created", orderId);

                // get invoice id
            	InvoiceWS invoice = api.getLatestInvoice(userId);
            	assertNotNull("Couldn't get last invoice", invoice);
            	Integer invoiceId = invoice.getId();
        	    assertNotNull("Invoice id was null", invoiceId);

                // try paying the invoice
                System.out.println("Trying to pay invoice for blacklisted user ...");
                PaymentAuthorizationDTOEx authInfo = api.payInvoice(invoiceId);
            	assertNotNull("Payment result empty", authInfo);

                // check that it was failed by the test blacklist filter
                assertFalse("Payment wasn't failed for user: " + userId, authInfo.getResult().booleanValue());
                assertEquals("Processor response", message[i], authInfo.getResponseMessage());

                // remove invoice and order
                api.deleteInvoice(invoiceId);
                api.deleteOrder(orderId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception caught:" + e);
        }
    }
    
    public void testRemoveOnCCChange() {
        try {
            JbillingAPI api = JbillingAPIFactory.getAPI();
            final Integer userId = 868; // this is a user with a good CC
            
            // put a pre-auth record on this user
            api.createOrderPreAuthorize(com.sapienter.jbilling.server.order.WSTest.createMockOrder(userId, 3, new BigDecimal("3.45")));
            Integer orderId = api.getLatestOrder(userId).getId();
            
            // user should a a pre-auth
            PaymentWS payment = null;
            for (int paymentId:api.getLastPayments(userId, 10)) {
                payment = api.getPayment(paymentId);
                if (payment.getIsPreauth() == 1) break;
            }
            
            if (payment == null || payment.getIsPreauth() == 0) {
                fail("Could not find pre-auth payment for user " + userId);
            }
            
            // change the credit card
            UserWS user = api.getUserWS(userId);
            user.getCreditCard().setName("Meriadoc Pipin");
            api.updateCreditCard(userId, user.getCreditCard());
            
            // the payment should not be there any more
            payment = api.getPayment(payment.getId());
            if (payment != null && payment.getDeleted() == 0) {
                fail("Pre-auth should've been deleted");
            }
            
            // clean-up 
            api.deleteOrder(orderId);
            

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception caught:" + e);
        }
    }

    /**
     * Test for BlacklistUserStatusTask. When a user's status moves to
     * suspended or higher, the user and all their information is 
     * added to the blacklist.
     */
    public void testBlacklistUserStatus() {
        try {
            final Integer USER_ID = 1006; // user id for testing

            // expected filter response messages
            String[] messages = new String[6];
            messages[0] = "User id is blacklisted.";
            messages[1] = "Name is blacklisted.";
            messages[2] = "Credit card number is blacklisted.";
            messages[3] = "Address is blacklisted.";
            messages[4] = "IP address is blacklisted.";
            messages[5] = "Phone number is blacklisted.";

            JbillingAPI api = JbillingAPIFactory.getAPI();

            // check that a user isn't blacklisted
            UserWS user = api.getUserWS(USER_ID);
            // CXF returns null
            if (user.getBlacklistMatches() != null) {
            assertTrue("User shouldn't be blacklisted yet", 
                    user.getBlacklistMatches().length == 0);
            }

            // change their status to suspended
            user.setStatusId(UserDTOEx.STATUS_SUSPENDED);
            user.setPassword(null);
            api.updateUser(user);

            // check all their records are now blacklisted
            user = api.getUserWS(USER_ID);
            assertEquals("User records should be blacklisted.",
                    Arrays.toString(messages),
                    Arrays.toString(user.getBlacklistMatches()));

            // clean-up
            user.setStatusId(UserDTOEx.STATUS_ACTIVE);
            user.setPassword(null);
            api.updateUser(user);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception caught:" + e);
        }
    }

    /**
     * Tests the PaymentRouterCurrencyTask. 
     */
    public void testPaymentRouterCurrencyTask() {
        try {
            final Integer USER_USD = 10730;
            final Integer USER_AUD = 10731;

            JbillingAPI api = JbillingAPIFactory.getAPI();

            // create a new order
            OrderWS order = new OrderWS();            
            order.setUserId(USER_USD);
            order.setBillingTypeId(Constants.ORDER_BILLING_PRE_PAID);
            order.setPeriod(2);
            order.setCurrencyId(new Integer(1));

            // add a line
            OrderLineWS lines[] = new OrderLineWS[1];
            OrderLineWS line;
            line = new OrderLineWS();
            line.setTypeId(Constants.ORDER_LINE_TYPE_ITEM);
            line.setQuantity(new Integer(1));
            line.setItemId(new Integer(1));
            line.setUseItem(new Boolean(true));
            lines[0] = line;

            order.setOrderLines(lines);

            // create the order and invoice it
            System.out.println("Creating and invoicing order ...");
            Integer invoiceIdUSD = api.createOrderAndInvoice(order);
            Integer orderIdUSD = api.getLastOrders(USER_USD, 1)[0];

            // try paying the invoice in USD
            System.out.println("Making payment in USD...");
            PaymentAuthorizationDTOEx authInfo = api.payInvoice(invoiceIdUSD);

            assertTrue("USD Payment should be successful", authInfo.getResult().booleanValue());
            assertEquals("Should be processed by 'first_fake_processor'", authInfo.getProcessor(), "first_fake_processor");

            // create a new order in AUD and invoice it
            order.setUserId(USER_AUD);
            order.setCurrencyId(11);

            System.out.println("Creating and invoicing order ...");
            Integer invoiceIdAUD = api.createOrderAndInvoice(order);
            Integer orderIdAUD = api.getLastOrders(USER_AUD, 1)[0];

            // try paying the invoice in AUD
            System.out.println("Making payment in AUD...");
            authInfo = api.payInvoice(invoiceIdAUD);

            assertTrue("AUD Payment should be successful", authInfo.getResult().booleanValue());
            assertEquals("Should be processed by 'second_fake_processor'", authInfo.getProcessor(), "second_fake_processor");

            // remove invoices and orders
            System.out.println("Deleting invoices and orders.");
            api.deleteInvoice(invoiceIdUSD);
            api.deleteInvoice(invoiceIdAUD);
            api.deleteOrder(orderIdUSD);
            api.deleteOrder(orderIdAUD);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception caught:" + e);
        }
    }

    public void testPayInvoice() {
        try {
            final Integer USER = 1072;

            JbillingAPI api = JbillingAPIFactory.getAPI();

            System.out.println("Getting an invoice paid, and validating the payment.");
            OrderWS order = com.sapienter.jbilling.server.order.WSTest.createMockOrder(USER, 3, new BigDecimal("3.45"));
            Integer invoiceId = api.createOrderAndInvoice(order);
            PaymentAuthorizationDTOEx auth = api.payInvoice(invoiceId);
            assertNotNull("auth can not be null", auth);
            PaymentWS payment  = api.getLatestPayment(USER);
            assertNotNull("payment can not be null", payment);
            assertNotNull("auth in payment can not be null", payment.getAuthorizationId());

            api.deleteInvoice(invoiceId);
            api.deleteOrder(api.getLatestOrder(USER).getId());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception caught:" + e);
        }
    }

    public void testProcessPayment(){
        try {
            JbillingAPI api = JbillingAPIFactory.getAPI();		
            final Integer USER_ID = new Integer(1071);

            // first, create two unpaid invoices
            OrderWS order = com.sapienter.jbilling.server.order.WSTest.createMockOrder(USER_ID, 1, new BigDecimal("10.00"));
            Integer invoiceId1 = api.createOrderAndInvoice(order);
            Integer invoiceId2 = api.createOrderAndInvoice(order);

            // create the payment
            PaymentWS payment = new PaymentWS();
            payment.setAmount(new BigDecimal("5.00"));
            payment.setIsRefund(new Integer(0));
            payment.setMethodId(Constants.PAYMENT_METHOD_VISA);
            payment.setPaymentDate(Calendar.getInstance().getTime());
            payment.setCurrencyId(new Integer(1));
            payment.setUserId(USER_ID);            

            UserWS user = api.getUserWS(USER_ID);         
            CreditCardDTO originalCC= user.getCreditCard();


            /*
             * try a credit card number that fails
             */
            CreditCardDTO cc = new CreditCardDTO();
            cc.setName("Frodo Baggins");
            cc.setNumber("4111111111111111");
            cc.setType(Constants.PAYMENT_METHOD_VISA);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 5);
            cc.setExpiry(cal.getTime());
            payment.setCreditCard(cc);                       

            System.out.println("processing payment.");
            PaymentAuthorizationDTOEx authInfo = api.processPayment(payment);

            // check payment failed
            assertNotNull("Payment result not null", authInfo);
            assertFalse("Payment Authorization result should be FAILED", authInfo.getResult().booleanValue());

            // check payment has zero balance
            PaymentWS lastPayment = api.getLatestPayment(USER_ID);
            assertNotNull("payment can not be null", lastPayment);
            assertNotNull("auth in payment can not be null", lastPayment.getAuthorizationId());
            assertEquals("correct payment amount", new BigDecimal("5"), lastPayment.getAmountAsDecimal());
            assertEquals("correct payment balance", BigDecimal.ZERO, lastPayment.getBalanceAsDecimal());

            // check invoices still have balance
            InvoiceWS invoice1 = api.getInvoiceWS(invoiceId1);
            assertEquals("correct invoice balance", new BigDecimal("10.0"), invoice1.getBalanceAsDecimal());
            InvoiceWS invoice2 = api.getInvoiceWS(invoiceId1);
            assertEquals("correct invoice balance", new BigDecimal("10.0"), invoice2.getBalanceAsDecimal());

            // do it again, but using the credit card on file
            // which is also 41111111111111
            payment.setCreditCard(null);
            System.out.println("processing payment.");
            authInfo = api.processPayment(payment);
            // check payment has zero balance
            PaymentWS lastPayment2 = api.getLatestPayment(USER_ID);
            assertNotNull("payment can not be null", lastPayment2);
            assertNotNull("auth in payment can not be null", lastPayment2.getAuthorizationId());
            assertEquals("correct payment amount", new BigDecimal("5"), lastPayment2.getAmountAsDecimal());
            assertEquals("correct payment balance", BigDecimal.ZERO, lastPayment2.getBalanceAsDecimal());
            assertFalse("Payment is not the same as preiouvs", lastPayment2.getId() == lastPayment.getId());

            // check invoices still have balance
            invoice1 = api.getInvoiceWS(invoiceId1);
            assertEquals("correct invoice balance", new BigDecimal("10"), invoice1.getBalanceAsDecimal());
            invoice2 = api.getInvoiceWS(invoiceId1);
            assertEquals("correct invoice balance", new BigDecimal("10"), invoice2.getBalanceAsDecimal());


            /*
             * do a successful payment of $5
             */
            cc.setNumber("4111111111111152");
            payment.setCreditCard(cc);
            System.out.println("processing payment.");
            authInfo = api.processPayment(payment);

            // check payment successful
            assertNotNull("Payment result not null", authInfo);
            assertEquals("Auth id", 116, authInfo.getId().intValue());
            assertTrue("Payment Authorization result should be OK", authInfo.getResult().booleanValue());

            // check payment was made
            lastPayment = api.getLatestPayment(USER_ID);
            assertNotNull("payment can not be null", lastPayment);
            assertNotNull("auth in payment can not be null", lastPayment.getAuthorizationId());
            assertEquals("payment ids match", lastPayment.getId(), authInfo.getPaymentId().intValue());
            assertEquals("correct payment amount", new BigDecimal("5"), lastPayment.getAmountAsDecimal());
            assertEquals("correct payment balance", BigDecimal.ZERO, lastPayment.getBalanceAsDecimal());

            // check invoice 1 was partially paid (balance 5)
            invoice1 = api.getInvoiceWS(invoiceId1);
            assertEquals("correct invoice balance", new BigDecimal("5.0"), invoice1.getBalanceAsDecimal());

            // check invoice 2 wan't paid at all
            invoice2 = api.getInvoiceWS(invoiceId2);
            assertEquals("correct invoice balance", new BigDecimal("10.0"), invoice2.getBalanceAsDecimal());


            /*
             * another payment for $10, this time with the user's credit card
             */
            // update the credit card to the one that is good
            api.updateCreditCard(USER_ID, cc);
            // now the payment does not have a cc
            payment.setCreditCard(null);

            payment.setAmount(new BigDecimal("10.00"));
            System.out.println("processing payment.");
            authInfo = api.processPayment(payment);

            // check payment successful
            assertNotNull("Payment result not null", authInfo);
            assertTrue("Payment Authorization result should be OK", authInfo.getResult().booleanValue());

            // check payment was made
            lastPayment = api.getLatestPayment(USER_ID);
            assertNotNull("payment can not be null", lastPayment);
            assertNotNull("auth in payment can not be null", lastPayment.getAuthorizationId());
            assertEquals("correct payment amount", new BigDecimal("10"), lastPayment.getAmountAsDecimal());
            assertEquals("correct payment balance", BigDecimal.ZERO, lastPayment.getBalanceAsDecimal());

            // check invoice 1 is fully paid (balance 0)
            invoice1 = api.getInvoiceWS(invoiceId1);
            assertEquals("correct invoice balance", BigDecimal.ZERO, invoice1.getBalanceAsDecimal());

            // check invoice 2 was partially paid (balance 5)
            invoice2 = api.getInvoiceWS(invoiceId2);
            assertEquals("correct invoice balance", new BigDecimal("5"), invoice2.getBalanceAsDecimal());


            /* 
             *another payment for $10
             */
            payment.setCreditCard(cc);
            payment.setAmount(new BigDecimal("10.00"));
            System.out.println("processing payment.");
            authInfo = api.processPayment(payment);

            // check payment successful
            assertNotNull("Payment result not null", authInfo);
            assertTrue("Payment Authorization result should be OK", authInfo.getResult().booleanValue());

            // check payment was made
            lastPayment = api.getLatestPayment(USER_ID);
            assertNotNull("payment can not be null", lastPayment);
            assertNotNull("auth in payment can not be null", lastPayment.getAuthorizationId());
            assertEquals("correct  payment amount", new BigDecimal("10"), lastPayment.getAmountAsDecimal());
            assertEquals("correct  payment balance", new BigDecimal("5"), lastPayment.getBalanceAsDecimal());

            // check invoice 1 balance is unchanged
            invoice1 = api.getInvoiceWS(invoiceId1);
            assertEquals("correct invoice balance", BigDecimal.ZERO, invoice1.getBalanceAsDecimal());

            // check invoice 2 is fully paid (balance 0)
            invoice2 = api.getInvoiceWS(invoiceId2);
            assertEquals("correct invoice balance", BigDecimal.ZERO, invoice2.getBalanceAsDecimal());


            // clean up
            api.updateCreditCard(USER_ID, originalCC);
            System.out.println("Deleting invoices and orders.");
            api.deleteInvoice(invoice1.getId());
            api.deleteInvoice(invoice2.getId());
            api.deleteOrder(invoice1.getOrders()[0]);
            api.deleteOrder(invoice2.getOrders()[0]);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception caught:" + e);
        }
    }
    
    public static void assertEquals(BigDecimal expected, BigDecimal actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, BigDecimal expected, BigDecimal actual) {
        assertEquals(message,
                     (Object) (expected == null ? null : expected.setScale(2, RoundingMode.HALF_UP)),
                     (Object) (actual == null ? null : actual.setScale(2, RoundingMode.HALF_UP)));
    }
}
