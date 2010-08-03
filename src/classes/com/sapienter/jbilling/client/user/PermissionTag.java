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

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.UserDTOEx;

/**
 * This is the base for custom tag that will be used to list data.
 * The class that extends this one has to implement the doStartTag and
 * get the CachedRowSet from the session bean.
 * @author emilc
 *
 * @jsp:tag name="permission"
 *          body-content="JSP"
 */

public class PermissionTag extends TagSupport {

    private Integer permission = null;
    private Boolean negative = new Boolean(false);
    private Integer typeId = null;
    private Integer foreignId = null;

    public int doStartTag() throws JspException {
        int retValue;
        
        if (negative.booleanValue()) {
            retValue = EVAL_BODY_INCLUDE;
        } else {
            retValue = SKIP_BODY;
        }
        
        try {
            // now verify that this permission is granted to the logged user
            HttpSession session = pageContext.getSession();
            if (permission != null) {
                if (((UserDTOEx) session.getAttribute(Constants.SESSION_USER_DTO)).
                        isGranted(permission)) {
                    
                    if (negative.booleanValue()) {
                        retValue = SKIP_BODY;
                    } else {
                        retValue = EVAL_BODY_INCLUDE;
                    }
                }
            } else {
                if (typeId == null || foreignId == null) {
                    throw new JspException("Either permission or typeId and " +
                            "foreignId have to be provided in the parameters");
                }

                if (((UserDTOEx) session.getAttribute(Constants.SESSION_USER_DTO)).
                        isGranted(typeId, foreignId)) {
                    
                    if (negative.booleanValue()) {
                        retValue = SKIP_BODY;
                    } else {
                        retValue = EVAL_BODY_INCLUDE;
                    }
                }
                
            }

        } catch (Exception e) {
            throw new JspException(e);
        }

        return retValue;
    }

  
    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Integer"
     * @return
     */
    public Integer getPermission() {
        return permission;
    }

    /**
     * @param integer
     */
    public void setPermission(Integer integer) {
        permission = integer;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     * @return
     */
    public Boolean getNegative() {
        return negative;
    }

    /**
     * @param boolean1
     */
    public void setNegative(Boolean boolean1) {
        negative = boolean1;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Integer"
     * @return
     */
    public Integer getForeignId() {
        return foreignId;
    }

    /**
     * @param foreignId
     */
    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Integer"
     * @return
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * @param typeId
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

}
