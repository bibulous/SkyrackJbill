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
package com.sapienter.jbilling.client.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

/**
 * Simple server meant to be called by a monitoring service.
 * It will just find entity 1 and return PASSED, or something else if there
 * is a problem.
 * A PASSED return means that the basic health of jbilling is good. The classes
 * are loaded, the database is running and the app server JNDI service is working.
 * @author Emil
 *
 */
public class ServerUpCheckServlet extends HttpServlet {
    
    private static final Logger LOG = Logger.getLogger(ServerUpCheckServlet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doCheck(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doCheck(request,response);
    }
    
    private void doCheck(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            ServletOutputStream output = response.getOutputStream();

            IUserSessionBean myRemoteSession = null;
            try {
                myRemoteSession = (IUserSessionBean) Context.getBean(
                        Context.Name.USER_SESSION);
                myRemoteSession.getEntityPrimaryContactType(1);
                output.print("PASSED");
            } catch (Throwable e) {
                LOG.error("Error in server up check: " + e);
                output.print("ERROR: Exception." + e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
