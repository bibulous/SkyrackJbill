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
 * Created on 19-Feb-2003
 *
 */
package com.sapienter.jbilling.client.user;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author Emil
 */
public final class UserLoginForm
    extends ValidatorForm
    implements Serializable {

    /**
     * The username.
     */
    private String sUserName = null;
    
    private String password = null;
    
    private String entityId = null;

    /**
     * Return the username.
     */
    public String getUserName() {
        return (this.sUserName);
    }

    /**
     * Set the username.
     *
     * @param username The new username
     */
    public void setUserName(String username) {
        this.sUserName = username;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.sUserName = null;
        this.password = null;
    }

    /**
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * @param entityId
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

}
