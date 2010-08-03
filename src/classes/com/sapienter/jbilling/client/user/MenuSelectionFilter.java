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

package com.sapienter.jbilling.client.user;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.FormHelper;
import com.sapienter.jbilling.server.user.Menu;
import com.sapienter.jbilling.server.user.UserDTOEx;

/**
 * @author emilc
 *
 * This filter applies to all JSPs files. It verifies that a session with 
 * a username exists, which means that the user has logged in.
 * It takes as a parameter from the descriptor the name of the Login servlet,
 * because it's the only one that shouldn't be filtered and is the one 
 * where those not authenticated will be redirected.
 */
public final class MenuSelectionFilter implements Filter {
    
    private Logger log = null;

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        log = Logger.getLogger(MenuSelectionFilter.class);
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain fChain) throws IOException, ServletException {
        
        if (!(req instanceof HttpServletRequest)) {
            log.warn("Request not of a servlet.");
            return;
        }
        
        HttpServletRequest httpReq = (HttpServletRequest)req;
        String optionStr = req.getParameter("menu_option");
        if (optionStr != null) {
            //log.debug("option selected " + optionStr);
            try {
                Integer option = Integer.valueOf(optionStr);
                HttpSession session = httpReq.getSession(false); // this is the right way to do it
                Menu menu = ((UserDTOEx) session.getAttribute(
                        Constants.SESSION_USER_DTO)).getMenu();
                
                if (menu.selectOption(option)) {
                    log.debug("Cleaning up the session");
                    FormHelper.cleanUpSession(session);
                    /*
                    java.util.Enumeration entries = session.getAttributeNames();
                    for (String entry = (String)entries.nextElement(); entries.hasMoreElements();
                        entry = (String)entries.nextElement()) {
                        log.debug("Session entry:[" + entry + "]");        
                    }
                    */
                }
            } catch (Exception e) {
                log.error("exception selection an option:" + optionStr, e);
                throw new ServletException(e);
            }
        }
        
        fChain.doFilter(req, res);

    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        log = null;
    }

}
