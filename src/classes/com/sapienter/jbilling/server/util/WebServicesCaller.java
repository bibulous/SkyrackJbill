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

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.db.UserDTO;

/**
 * Provides thread local storage of web services caller info. The 
 * authentication mechanism must make available the caller info here,
 * which is then used by the WebServicesSessionBean and the security 
 * proxy. 
 */
public class WebServicesCaller {

    /**
     * Class used to hold default username/password for 
     * unauthenticated calls. 
     */
    public static class Defaults {
        String username = null;
        String password = null;

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }
    }

    private static final Logger LOG = Logger.getLogger(WebServicesCaller.class);

    private static final ThreadLocal<UserDTO> caller = 
            new ThreadLocal<UserDTO>();

    // non-instantiable 
    private WebServicesCaller() {
    }

    /**
     * Returns the caller UserDTO. If the caller is not set, the 
     * default for unauthenticated calls is returned instead. 
     */
    public static UserDTO get() {
        if (caller.get() == null) {
            // no caller set, try to get default username and password
            Defaults defaults = (Defaults) Context.getBean(
                    Context.Name.WEB_SERVICES_CALLER_DEFAULTS);

            if (defaults.getUsername() != null && 
                    defaults.getPassword() != null) {
                IUserSessionBean userSession = (IUserSessionBean) 
                        Context.getBean(Context.Name.USER_SESSION);
                UserDTO user = userSession.webServicesAuthenticate(
                        defaults.getUsername(), defaults.getPassword());

                if (user != null) {
                    LOG.info("Using default username and password for " +
                            "unauthenticated API call/s.");
                    set(user);
                } else {
                    throw new SecurityException("Invalid default " +
                            "username/password");
                }
            } else {
                throw new SecurityException("No caller set.");
            }
        }
        return caller.get();
    }

    public static void set(UserDTO user) {
        caller.set(user);
    }

    public static Integer getUserId() {
        return get().getId();
    }

    public static String getUserName() {
        return get().getUserName();
    }

    public static Integer getCompanyId() {
        return get().getCompany().getId();
    }
}
