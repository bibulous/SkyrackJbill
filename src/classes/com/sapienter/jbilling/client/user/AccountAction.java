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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sapienter.jbilling.client.util.Constants;

/*
 * This is to avoid a landing page for the account.
 * It only puts in the session some values so the user edit and contact
 * edit pick up the current user. 
 */
public class AccountAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        // set up the variable to allow for user edit (change password)
        session.setAttribute(Constants.SESSION_USER_ID, session.getAttribute(
                Constants.SESSION_LOGGED_USER_ID));

        // do the setup for the contact edit
        session.setAttribute(Constants.SESSION_CONTACT_USER_ID, session.
                getAttribute(Constants.SESSION_LOGGED_USER_ID));
        
        // it will go to the default 'account' page
        // this could be editContact or passwordChange
        // this is chaining actions, from this one it goes to the maintain action
        return mapping.findForward("passwordChange");        

    }
}
