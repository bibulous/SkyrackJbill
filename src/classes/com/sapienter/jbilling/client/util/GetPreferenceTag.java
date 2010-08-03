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
 * Created on Jan 18, 2004
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.util;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

/**
 * Finds a preference. If no beanName is specified, the value of the
 * preference is redered to the output. Otherwise, a new bean 
 * in the page context is created.
 * 
 * @author emilc
 *
 * @jsp:tag name="getPreference"
 *          body-content="empty"
 */
public class GetPreferenceTag extends TagSupport {
    
    private Integer preferenceId = null;
    private String beanName = null;
    
    public int doStartTag() throws JspException {
        Logger log = Logger.getLogger(GetPreferenceTag.class);

        // pull some data from the session before making the call
        HttpSession session = pageContext.getSession();
        Integer entityId = (Integer) session.getAttribute(
                Constants.SESSION_ENTITY_ID_KEY);
        JspWriter out = pageContext.getOut();

        // these preferences can be called for every page, so it is
        // importante to cache
        String result = null;
        String sessionKey = null;
        if (preferenceId.equals(Constants.PREFERENCE_CSS_LOCATION)) {
            sessionKey = Constants.SESSION_CSS_LOCATION;
        } else if (preferenceId.equals(Constants.PREFERENCE_LOGO_LOCATION)) {
            sessionKey = Constants.SESSION_LOGO_LOCATION;
        }
        
        // some might not need any caching
        if (sessionKey != null) {
            result = (String) session.getAttribute(sessionKey);
        }
        
        if (result == null) {
            
            try {
                IUserSessionBean remoteUser = (IUserSessionBean) 
                        Context.getBean(Context.Name.USER_SESSION);
                result = remoteUser.getEntityPreference(entityId, preferenceId);
                
                // update the cache is applicable
                if (sessionKey != null) {
                    session.setAttribute(sessionKey, result);
                }
            } catch (Exception e) {
                 log.error("Exception on getting the css url", e);
                 throw new JspException(e);
            }
        }
        
        try {
            if (beanName != null && beanName.length() > 0) {
                pageContext.setAttribute(beanName, result);
            } else {
                out.print(result);
            }
        } catch (IOException e) {
            log.error("Can't write!", e);
            throw new JspException(e);
        }
        
        return SKIP_BODY;
    }
    
    /**
     * @jsp:attribute required="true"
     *                rtexprvalue="true"
     *                type="java.lang.Integer"
     */
    public Integer getPreferenceId() {
        return preferenceId;
    }

    /**
     * @param preferenceId
     */
    public void setPreferenceId(Integer parameterId) {
        this.preferenceId = parameterId;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.String"
     */
    public String getBeanName() {
        return beanName;
    }
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
