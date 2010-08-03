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

package com.sapienter.jbilling.server.order;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.sapienter.jbilling.common.JNDILookup;
import com.sapienter.jbilling.interfaces.OrderSession;
import com.sapienter.jbilling.interfaces.OrderSessionHome;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.process.BillingProcessTest;

public class OrderTest extends TestCase {

    public OrderTest(String arg0) {
        super(arg0);
    }
    
    public void testGetOrder() {
        try {
            OrderSessionHome customerHome =
                    (OrderSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    OrderSessionHome.class,
                    OrderSessionHome.JNDI_NAME);
            OrderSession remoteSession = customerHome.create();
             
            OrderDTO order = remoteSession.getOrder(new Integer(10));
            assertNotNull(order);
             
            // check a few fields here and there
            assertEquals("create date", BillingProcessTest.parseDate(
                   "2002-12-01"), order.getCreateDate());
            assertEquals("created by", new Integer(1), order.getCreatedBy());
            assertEquals("billing type", new Integer(2), order.getBillingTypeId());            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
        
    }

    public void testGetOrderEx() {
        try {
            OrderSessionHome customerHome =
                    (OrderSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    OrderSessionHome.class,
                    OrderSessionHome.JNDI_NAME);
            OrderSession remoteSession = customerHome.create();
            OrderDTO order = remoteSession.getOrderEx(new Integer(11),
                    new Integer(1));
            
            assertNotNull(order);
            assertNotNull(order.getInvoices());
            assertNotNull(order.getOrderLines());
            assertTrue(order.getOrderLines().size() > 0);
            
            OrderLineDTO line = (OrderLineDTO) order.getOrderLines().get(1);
            assertNotNull(line);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
    }

    public void testNotification() {
        try {
            OrderSessionHome customerHome =
                    (OrderSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    OrderSessionHome.class,
                    OrderSessionHome.JNDI_NAME);
            OrderSession remoteSession = customerHome.create();
            
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(2003, Calendar.MARCH, 28);
            remoteSession.reviewNotifications(cal.getTime());
            
            // get the order that should have generated  the notif
            OrderDTO order = remoteSession.getOrder(new Integer(25));
            assertEquals("Notif flag", order.getNotify(), new Integer(1));
            assertEquals("Step", order.getNotificationStep(), new Integer(1));
            assertNotNull("Notif date", order.getLastNotified());
            Date last = order.getLastNotified();

            //if I call it again a day later, it shouldn't notify
            cal.set(2003, Calendar.MARCH, 29);
            remoteSession.reviewNotifications(cal.getTime());
            order = remoteSession.getOrder(new Integer(25));
            assertEquals("Last notif 1", last, order.getLastNotified());
            
            // make another call the same day that expires
            cal.set(2003, Calendar.APRIL, 1);
            remoteSession.reviewNotifications(cal.getTime());
            order = remoteSession.getOrder(new Integer(25));
            assertEquals("Notif flag 2", order.getNotify(), new Integer(0));
            assertEquals("Step 2", order.getNotificationStep(), new Integer(3));
            assertNotSame("Last notif 2", last, order.getLastNotified());
            
            // if I call it again, it shouldn't notify
            last = order.getLastNotified();
            remoteSession.reviewNotifications(cal.getTime());
            order = remoteSession.getOrder(new Integer(25));
            assertEquals("Last notif 3", last, order.getLastNotified());
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
    }

}
