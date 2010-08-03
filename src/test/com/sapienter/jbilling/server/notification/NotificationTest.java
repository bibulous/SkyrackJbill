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

package com.sapienter.jbilling.server.notification;

import junit.framework.TestCase;

import com.sapienter.jbilling.common.JNDILookup;

public class NotificationTest extends TestCase {

    /**
     * Constructor for NotificationTest.
     * @param arg0
     */
    public NotificationTest(String arg0) {
        super(arg0);
    }

    public void testNotify() {

        try {
            NotificationSessionHome customerHome =
                    (NotificationSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    NotificationSessionHome.class,
                    NotificationSessionHome.JNDI_NAME);
            NotificationSession remoteSession = customerHome.create();
            
            MessageDTO message = new MessageDTO();
            message.setTypeId(MessageDTO.TYPE_INVOICE_EMAIL);
            message.addParameter("total", "11.5");
            message.addParameter("due date","10/20/2003");
            
            MessageSection section = new MessageSection(new Integer(1),
                    "Hello World");
                    
            message.addSection(section);
            //seciton = new MessageSection(new Integer
            remoteSession.notify(new Integer(5), message);
            // yikes, can't really assert anything ... :(
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
    }

    public void testSapienterEmail() {
    	Integer entityId = new Integer(1);
    	try {
    		System.out.println("Attempting sending . . . ");
	    	NotificationBL.sendSapienterEmail(entityId, "invoice_batch",
	    			Util.getSystemProperty("base_dir") + "invoices/" + entityId + 
					"-" + new Integer(1) + "-batch.pdf");
	    	System.out.println("DONE !");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
   			
    }
}
