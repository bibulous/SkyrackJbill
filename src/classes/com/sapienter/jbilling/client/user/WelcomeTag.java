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
 * Created on Mar 29, 2004
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.user;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

/**
 * This tag simply goes to the server to look for the message that
 * goes with this user's status.
 * @author emilc
 *
 * @jsp:tag name="welcome"
 *          body-content="JSP"
 */
public class WelcomeTag extends TagSupport {

    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        // gather some information about the logged user
        Integer entityId = (Integer) session.getAttribute(
                Constants.SESSION_ENTITY_ID_KEY);
        Integer languageId = (Integer) session.getAttribute(
                Constants.SESSION_LANGUAGE);
        Integer statusId = ((UserDTOEx) session.getAttribute(
                Constants.SESSION_USER_DTO)).getStatusId();
        JspWriter out = pageContext.getOut();
        try {
            // get the order session bean
            IUserSessionBean remoteUser = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
            out.print(remoteUser.getWelcomeMessage(
                    entityId, languageId, statusId));
        } catch (Exception e) {
            throw new JspException(e);
        }        
        return SKIP_BODY;
    }
}
