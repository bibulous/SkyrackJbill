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

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class ChangePasswordAction extends Action {
    private static Logger LOG = Logger.getLogger(ChangePasswordAction.class);
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        ActionErrors errors = new ActionErrors();
        DynaValidatorForm info = (DynaValidatorForm) form;
        String password = (String) info.get("password");
        String verifyPassword = (String) info.get("verifyPassword");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(Constants.SESSION_USER_ID);
        
        if (!password.equals(verifyPassword)) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("user.create.error.password_match"));
        }

        String oldPassword = (String) session.getAttribute("jsp_initial_password");
        if (oldPassword.equalsIgnoreCase(password)) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.repeated", "New password"));
        }


        IUserSessionBean myRemoteSession = null;
        UserDTOEx user = null;

        try {
            myRemoteSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
        } catch (Exception e) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("all.internal"));
        }
        
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }

        try {
            // do the actual password change
            user = myRemoteSession.getUserDTOEx(userId);
            user.setPassword(password);
            myRemoteSession.update(userId, user);

            // I still need to call this method, because it populates the dto
            // with the menu and other fields needed for the login
            user = myRemoteSession.getGUIDTO(user.getUserName(), user.getEntityId());
        } catch (Exception e) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("all.internal"));
        }

        LOG.debug("Password changed for user " + userId);
        // now get the session completed. The user is actually logged only now.
        UserLoginAction.logUser(session, user);
        return (mapping.findForward("success"));

    }
}
