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

package com.sapienter.jbilling.server.report;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;
import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.common.JNDILookup;
import com.sapienter.jbilling.interfaces.ReportSession;
import com.sapienter.jbilling.interfaces.ReportSessionHome;
import com.sapienter.jbilling.server.entity.ReportUserDTO;

public class ReportTest extends TestCase {

    /**
     * Constructor for ReportTest.
     * @param arg0
     */
    public ReportTest(String arg0) {
        super(arg0);
    }

    public void testGeneral() {
        try {
            ReportSessionHome reportHome =
                    (ReportSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    ReportSessionHome.class,
                    ReportSessionHome.JNDI_NAME);
            ReportSession remoteSession = reportHome.create();
            
            // I'll use the same entity id all the time
            Integer entityId = new Integer(1);
            // get a list of all the reports for this entity
            Collection allReports = remoteSession.getList(entityId);
            assertNotNull("List of entity's reports",allReports);
            assertFalse("list of reports is not empty", allReports.isEmpty());
            
            // I'll get the first report
            Iterator it = allReports.iterator();
            Integer reportId = ((ReportDTOEx) it.next()).getId();
            ReportDTOEx report = remoteSession.getReportDTO(reportId, 
                    entityId);
            assertNotNull("Get first report", report);
            
            // This assumes that the first report (that now is the general orders)
            // only takes this dyamic parameter. If the first report changes to
            // something that takes more parameters, they have to be added here
            // add the entity ID & language as a dynamic parameter
            report.addDynamicParameter(String.valueOf(entityId));
            report.addDynamicParameter("1"); // language
            // execute the report
            CachedRowSet result = remoteSession.execute(report);
            // verify there's some results
            assertNotNull("Get result of report", result);
            assertTrue("result has something", result.last());
            
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
    }
    
    public void testUserSaved() {
        try {
            ReportSessionHome reportHome =
                    (ReportSessionHome) JNDILookup.getFactory(true).lookUpHome(
                    ReportSessionHome.class,
                    ReportSessionHome.JNDI_NAME);
            ReportSession remoteSession = reportHome.create();
            
            Collection list = remoteSession.getUserList(new Integer(1),
                    new Integer(1));
            assertNotNull("Get list of user's report", list);
            assertFalse(list.isEmpty());
            
            // get the report
            Iterator it = list.iterator();
            Integer reportId = ((ReportUserDTO) it.next()).getId();
            ReportDTOEx report = remoteSession.getReportDTO(reportId);
            assertNotNull("Get user report", report);
            
            // execute it
            // execute the report
            CachedRowSet result = remoteSession.execute(report);
            // verify there's some results
            assertNotNull("Report result", result);
            assertTrue("result has something", result.last());

            // create another one
           remoteSession.save(report, new Integer(1), "!@#test case saved()()");    
           // now delete it
           list = remoteSession.getUserList(new Integer(1),
                new Integer(1));
           for (it = list.iterator(); it.hasNext();) { // got to find it first
               ReportUserDTO userRep = (ReportUserDTO) it.next();
               if (userRep.getTitle().equals("!@#test case saved()()")) {
                   remoteSession.delete(userRep.getId());
                   break;
               }
           }
           if (it.hasNext()) {
               fail("The saved report wasn't found.");
           }
                   
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail("Exception : " + e.getMessage());
        }
       
    }

}
