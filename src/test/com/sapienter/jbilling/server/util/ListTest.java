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

package com.sapienter.jbilling.server.util;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import junit.framework.TestCase;
import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.common.JNDILookup;
import com.sapienter.jbilling.server.list.ListDTO;
import com.sapienter.jbilling.server.list.ListSession;
import com.sapienter.jbilling.server.list.ListSessionHome;

public class ListTest extends TestCase {

    /**
     * Constructor for ListTest.
     * @param arg0
     */
    public ListTest(String arg0) {
        super(arg0);
    }

    public void testList() {
        try {
            ListSessionHome listHome =
                    (ListSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    ListSessionHome.class,
                    ListSessionHome.JNDI_NAME);
            ListSession remoteSession = listHome.create();
            Hashtable parameters = new Hashtable();
            CachedRowSet list;
            ListDTO listDto;
            
            // test just a bunch of the list
            // CUSTOMERS
            parameters.put("userType", Constants.TYPE_INTERNAL);
            parameters.put("entityId", new Integer(1));
            parameters.put("userId", new Integer(1));
            list = remoteSession.getList(
                    Constants.LIST_TYPE_CUSTOMER, parameters);
            doChecks(list, 4, true);
            
            // ITEMS
            parameters.clear();
            parameters.put("entityId", new Integer(1));
            parameters.put("languageId", new Integer(1));
            list = remoteSession.getList(
                    Constants.LIST_TYPE_ITEM, parameters);
            doChecks(list, 3, true);

            // PROMOTIONS
            parameters.clear();
            parameters.put("entityId", new Integer(1));
            parameters.put("languageId", new Integer(1));
            list = remoteSession.getList(
                    Constants.LIST_TYPE_PROMOTION, parameters);
            doChecks(list, 5, false);

            // PAYMENT
            parameters.clear();
            parameters.put("entityId", new Integer(1));
            parameters.put("userType", Constants.TYPE_INTERNAL);
            parameters.put("languageId", new Integer(1));
            parameters.put("userId", new Integer(1)); // just in case it's a partner
            list = remoteSession.getList(
                    Constants.LIST_TYPE_PAYMENT, parameters);
            doChecks(list, 6, false);

            // ITEMS for and Order
            parameters.clear();
            parameters.put("entityId", new Integer(1));
            parameters.put("languageId", new Integer(1));
            parameters.put("userId", new Integer(1));
            listDto = remoteSession.getDtoList(
                    Constants.LIST_TYPE_ITEM_ORDER, parameters);
            doDtoChecks(listDto, 3, true);
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
    }
    
    public void testStatistics() {
        try {
            ListSessionHome listHome =
                    (ListSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    ListSessionHome.class,
                    ListSessionHome.JNDI_NAME);
            ListSession remoteSession = listHome.create();
            
            remoteSession.updateStatistics();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
    }
    
    public void testPageing() {
        try {
            ListSessionHome listHome =
                    (ListSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    ListSessionHome.class,
                    ListSessionHome.JNDI_NAME);
            ListSession remoteSession = listHome.create();
            
            int rows;
            Hashtable parameters = new Hashtable();
            parameters.put("userType", new Integer(2));
            parameters.put("entityId", new Integer(301));
            Integer start = null, end = null;
            List pagesFrom = new ArrayList();
            do {
                CachedRowSet result = remoteSession.getPage(start, null, 10, 
                        new Integer(1), new Integer(301),
                        true, new Integer(1), parameters);
                System.out.println("Page starting at " + start);
                pagesFrom.add(start);
                rows = 0;
                while (result.next()) {
                    for (int f = 1; f <= result.getMetaData().getColumnCount(); f++) {
                        if (f == 1) {
                            start = new Integer(result.getInt(f));
                        }
                        System.out.print(result.getMetaData().getColumnName(f));
                        System.out.print(" ");
                        System.out.print(result.getString(f));
                        System.out.print(" ");
                    }
                    System.out.println("");
                    rows++;
                }
            } while (rows == 10);
            
            int currentPage = pagesFrom.size() - 1;
            do {
                if (currentPage + 1 < pagesFrom.size()) {
                    end = (Integer) pagesFrom.get(currentPage + 1);
                } else {
                    end = null;
                }
                start = (Integer) pagesFrom.get(currentPage);
                System.out.println("Going backwards. Page " + currentPage + 
                        " start " + start + " end " + end);
                CachedRowSet result = remoteSession.getPage(start, end, 10, 
                        new Integer(1), new Integer(301),
                        true, new Integer(1), parameters);
                rows = 0;
                while (result.next()) {
                    for (int f = 1; f <= result.getMetaData().getColumnCount(); f++) {
                        System.out.print(result.getMetaData().getColumnName(f));
                        System.out.print(" ");
                        System.out.print(result.getString(f));
                        System.out.print(" ");
                    }
                    System.out.println("");
                    rows++;
                }
                currentPage--;
            } while (start != null);

            
            System.out.println("Total = " + rows);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
    }
    
    public void testSearch () {
        try {
            Hashtable parameters = new Hashtable();
            parameters.put("userType", new Integer(2));
            parameters.put("entityId", new Integer(301));

            ListSessionHome listHome =
                (ListSessionHome) JNDILookup.getFactory(true).lookUpHome(
                ListSessionHome.class,
                ListSessionHome.JNDI_NAME);
            ListSession remoteSession = listHome.create();
            int rows;
            
            // one id in particular
            CachedRowSet result = remoteSession.search("1750", null, new Integer(1), 
                    new Integer(1), new Integer(301), parameters);
                    
            rows = 0;
            while (result.next()) {
                for (int f = 1; f <= result.getMetaData().getColumnCount(); f++) {
                    System.out.print(result.getMetaData().getColumnName(f));
                    System.out.print(" ");
                    System.out.print(result.getString(f));
                    System.out.print(" ");
                }
                System.out.println("");
                rows++;
            }
            System.out.println("Total = " + rows);
            
            // a bunch by a string field
            result = remoteSession.search("ll", null, new Integer(2), 
                    new Integer(1), new Integer(301), parameters);
            rows = 0;
            while (result.next()) {
                for (int f = 1; f <= result.getMetaData().getColumnCount(); f++) {
                    System.out.print(result.getMetaData().getColumnName(f));
                    System.out.print(" ");
                    System.out.print(result.getString(f));
                    System.out.print(" ");
                }
                System.out.println("");
                rows++;
            }
            System.out.println("Total = " + rows);

            // a bunch by a string field
            result = remoteSession.search("2004-10-01", "2004-10-15", new Integer(10), 
                    new Integer(2), new Integer(301), parameters);
            rows = 0;
            while (result.next()) {
                for (int f = 1; f <= result.getMetaData().getColumnCount(); f++) {
                    System.out.print(result.getMetaData().getColumnName(f));
                    System.out.print("=");
                    System.out.print(result.getString(f));
                    System.out.print(" ");
                }
                System.out.println("");
                rows++;
            }
            System.out.println("Total = " + rows);
        
        
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
        
    }
    
    public void testSelectableOptions() {
        try {
            Integer entityId = new Integer(1);
            Integer languageId = new Integer(1);
            Collection options;
            ListSessionHome listHome =
                    (ListSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    ListSessionHome.class,
                    ListSessionHome.JNDI_NAME);
            ListSession listSession = listHome.create();
           
           
            options = listSession.getOptions("countries", languageId, entityId,
                    null);
            checkOptions(options, "countries");

            options = listSession.getOptions("orderPeriod", languageId, entityId,
                    null);
            checkOptions(options, "orderPeriod");

            options = listSession.getOptions("language", languageId, entityId,
                    null);
            checkOptions(options, "language");
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
    }
    
    private void checkOptions(Collection options, String type) {
        assertNotNull(type, options);
        assertTrue(type + "- size", options.size() > 0);
        assertNotNull(type + "- content", ((OptionDTO) options.toArray()[0]).getCode());
    }
    
    private void doChecks(CachedRowSet list, int columns, boolean data) 
            throws SQLException {
        // can't be null
        assertNotNull(list);
            
        // verify that the number of columns is there
        ResultSetMetaData metaData = list.getMetaData();
        assertEquals("Number of columns", (columns + 1), 
                metaData.getColumnCount());
            
        // and that there are some rows
        if (data) {
            assertTrue("Rows present (1)", list.last());
            assertTrue("Rows present (2)", list.getRow() > 0);
        }
    }        

    private void doDtoChecks(ListDTO list, int columns, boolean data) {
        assertNotNull(list);
        
        assertEquals("Number of columns", list.getTypes().length, 
                (columns + 1));
        if (data) {
            assertTrue("Rows present", list.getLines().size() > 0);
        }
        
    }
    
    public void testChars() {
        try {
            
            String hex = Integer.toHexString(8364);
            char c = 8364;
            System.out.print(c);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        
        }
    }
}
