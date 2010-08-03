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

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.WebServicesCaller;

/**
 * Servlet filter for HTTP basic authentication of web services. 
 */
public final class WebServicesAuthenticationFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(
            WebServicesAuthenticationFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        UserDTO caller = (UserDTO) session.getAttribute(
            Constants.SESSION_USER_DTO);
        boolean authenticated = false;

        if (caller != null) {
            authenticated = true;
        } else {
            String username = null;
            String password = null;

            // No session, check for authorisation header.
            // Basic authentication header in the form: 
            // Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
            String authHeader = httpRequest.getHeader("Authorization");

            if (authHeader != null) {
                StringTokenizer st = new StringTokenizer(authHeader);
                if (st.hasMoreTokens()) {
                    String basic = st.nextToken();
                    // Only dealing with HTTP Basic authentication
                    if (basic.equalsIgnoreCase("Basic")) {
                        String credentials = st.nextToken();
                        // Decode username and password. 
                        // The decoded string is in the form:
                        // "userID:password".
                        String userPass = new String(Base64.decodeBase64(
                                credentials.getBytes()));

                        int separator = userPass.indexOf(":");
                        if (separator != -1) {
                            username = userPass.substring(0, separator);
                            password = userPass.substring(separator + 1);
                        }
                    }
                }
            }

            if (username != null && password != null) {
                // try to authenticate user
                IUserSessionBean userSession = (IUserSessionBean) 
                        Context.getBean(Context.Name.USER_SESSION);
                caller = userSession.webServicesAuthenticate(username,
                        password);
                if (caller != null) {
                    // save user in session
                    session.setAttribute(Constants.SESSION_LOGGED_USER_ID,
                            caller);
                    authenticated = true;
                }
            }
        }

        // If the user was not authenticated, fail with a 401
        // status code (UNAUTHORIZED) and pass back a 
        // WWW-Authenticate header.
        if (!authenticated) {
            httpResponse.setHeader("WWW-Authenticate", 
                    "Basic realm=\"jBilling Web Services\"");
            httpResponse.setStatus(401);
            LOG.debug("Sending 401 Reponse");
        } else {
            // save caller in thread-local variable to make it 
            // available for web services code
            WebServicesCaller.set(caller);
 
            chain.doFilter(request, response);

            // a precaution
            WebServicesCaller.set(null);
        }
    }

    public void destroy() {
    }
}
