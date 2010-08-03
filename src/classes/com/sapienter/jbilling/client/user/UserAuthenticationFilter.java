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

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.Util;

/**
 * @author emilc
 *
 * This filter applies to all JSPs files. It verifies that a session with 
 * a username exists, which means that the user has logged in.
 * It takes as a parameter from the descriptor the name of the Login servlet,
 * because it's the only one that shouldn't be filtered and is the one 
 * where those not authenticated will be redirected.
 * mapping:
 * A string beginning with a ?/? character and ending with a ?/*? postfix is used
 *  for path mapping.
 * A string beginning with a ?*.? prefix is used as an extension mapping.
 * A string containing only the ?/? character indicates the "default" servlet of the
 *   application. In this case the servlet path is the request URI minus the context
 *   path and the path info is null.
 * All other strings are used for exact matches only.
 */
public final class UserAuthenticationFilter implements Filter {
    
    private String[] exceptionPages = null;
    private String signupPrefix = null;
    private static final Logger LOG = Logger.getLogger(UserAuthenticationFilter.class);

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.signupPrefix = filterConfig.getInitParameter("signup_prefix");
        this.exceptionPages = filterConfig.getInitParameter("exception_pages").split(",");
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(
        ServletRequest req,
        ServletResponse res,
        FilterChain fChain)
        throws IOException, ServletException {
            
        if(exceptionPages.length <= 1) {
            LOG.fatal("Exceptions to filter, such as the login page, are not configured. Add this parameter to the " +
                    "web.xml file.");
            return;
        }
        
        if (!(req instanceof HttpServletRequest)) {
            LOG.warn("Request not of a servlet.");
            return;
        }
        
        // check if this is the Login page
        HttpServletRequest httpReq = (HttpServletRequest)req;
        String thisPage = httpReq.getServletPath();
        
        /*
        LOG.debug("Filtering page " + thisPage + " Context:" + httpReq.getContextPath() +
                " path info:" + httpReq.getPathInfo() + " uri:" + httpReq.getRequestURI() +
                " protocol:" + httpReq.getProtocol() + " servlet path:" + httpReq.getServletPath()+
                " url:" + httpReq.getRequestURL());
        */

        // first verify that this is being done in a secure channel
        if (Boolean.valueOf(Util.getSysProp("force_https")).
                booleanValue()) {
            String url = httpReq.getRequestURL().toString();
            if (url.substring(0, 5).equals("http:")) {
                String newURL = url.replaceFirst("http", "https");
                LOG.debug("Redirecting from " + httpReq.getRequestURL() + 
                        " to " + newURL + "[" + Boolean.valueOf(Util.getSysProp("force_https")).
                        booleanValue() +"]");
                ((HttpServletResponse)res).sendRedirect(newURL);
                return;
            }
        }
        
        // check that the process is not running, if so, kill the session
        File lock = new File(Util.getSysProp("login_lock"));
        if (lock.exists()) {
            HttpSession session = httpReq.getSession(false);
            if (session != null) {
                LOG.debug("Kicking user out due to process lock");
                try {
                    session.invalidate();
                } catch (IllegalStateException e) {
                    // the session was already invalid ..  sure, no problem
                }
            }
        }

        
        //LOG.debug("Login = " + loginPage);
        boolean isException = false;
        for (String page: exceptionPages) {
            if (page.equals(thisPage)) {
                isException = true;
                break;
            }
        }
        if (!isException &&
                !thisPage.startsWith(signupPrefix)) {
            // then you need a session
            HttpSession session = httpReq.getSession(false);
            if (session == null) {
                LOG.info("Session not present accessing " + thisPage);
                // TODO add a 'you session might have timed out' message
                ((HttpServletResponse)res).sendRedirect(httpReq.getContextPath() + 
                        exceptionPages[0]);
            } else {
                if (session.getAttribute(Constants.SESSION_LOGGED_USER_ID) == null) {
                    LOG.warn("Session exists but without user.");
                    ((HttpServletResponse)res).sendRedirect(
                            httpReq.getContextPath() + exceptionPages[0]);
                } else {
                    //LOG.debug("Session is good:" + session.getAttribute(Constants.SESSION_USER_ID_KEY));
                    fChain.doFilter(req, res);
                }
            }
        } else {
            // it's the login page
            //LOG.debug("This is the login page/action.");
            fChain.doFilter(req, res);
        }

    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        exceptionPages = null;
    }

}
