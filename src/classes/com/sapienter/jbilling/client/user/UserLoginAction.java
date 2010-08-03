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
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.user.db.CompanyDTO;


public final class UserLoginAction extends Action {

    private static final Logger LOG = Logger.getLogger(UserLoginAction.class);

    // --------------------------------------------------------- Public Methods

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param actionForm The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException, ServletException {
            

        // Get the values from the form and do further validation
        // or parsing
        ActionErrors errors = new ActionErrors();
        Locale locale = null;
        boolean internalLogin = false;
        UserLoginForm info = (UserLoginForm) form;
        String username = info.getUserName().trim();
        String password = info.getPassword().trim();
        String entityId = info.getEntityId().trim();

        // create the bean that is going to be passed to the session
        // bean for authentication
        UserDTOEx user = new UserDTOEx();
        user.setUserName(username);
        user.setPassword(password);
        if (username.equals(Util.getSysProp("internal_username"))) {
            // then you have to show me the extra key
            String key = request.getParameter("internalKey");
            if (key != null && key.equals(
                    Util.getSysProp("internal_key"))) {
                user.setCompany(new CompanyDTO(1));
                internalLogin = true;
                LOG.debug("identified interal login");
            } else {
                LOG.debug("internal failed. Key is not good " + key);
            }
        }
        if (!internalLogin) {
            user.setCompany(new CompanyDTO(Integer.valueOf(entityId)));
        } else {
            user.setCompany(new CompanyDTO(1));
        }
        
        // verify that the billing process is not running
        File lock = new File(Util.getSysProp("login_lock"));
        if (lock.exists()) {
            LOG.debug("Denying login. Lock present.");
            errors.add(
                    ActionErrors.GLOBAL_ERROR,
                    new ActionError("user.login.lock"));
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }

        // now do the call to the business object
        // get the value from a Session EJB 
        IUserSessionBean myRemoteSession = null;
        try {
            myRemoteSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
            Integer result = myRemoteSession.authenticate(user);
            if (result.equals(Constants.AUTH_WRONG_CREDENTIALS)) {
                errors.add(
                    ActionErrors.GLOBAL_ERROR,
                    new ActionError("user.login.badpassword"));
            } else if (result.equals(Constants.AUTH_LOCKED)) {
                errors.add(
                        ActionErrors.GLOBAL_ERROR,
                        new ActionError("user.login.passwordLocked"));
            } else {
                user = myRemoteSession.getGUIDTO(user.getUserName(), user.getEntityId());
                // children accounts can not login. They have no invoices and
                // can't make any payments
                if (user.getCustomer() != null && 
                        user.getCustomer().getParent() != null) {
                    errors.add(
                            ActionErrors.GLOBAL_ERROR,
                            new ActionError("user.login.badpassword"));
                }
                locale = myRemoteSession.getLocale(user.getUserId());
                // it is authenticated, let's create the session
            }

        } catch (Exception e) {
            errors.add(
                ActionErrors.GLOBAL_ERROR,
                new ActionError("all.internal"));
        }

        // Report any errors we have discovered back to the original form
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }
        
        // Save our logged-in user in the session
        HttpSession session = request.getSession();
        if (!session.isNew()) {
        	session.invalidate();
        	session = request.getSession();
        }
        
        // this is for struts to pick up the right messges file
        session.setAttribute(Globals.LOCALE_KEY, locale);
        
        // verify that the password has not expired
        boolean  expired = myRemoteSession.isPasswordExpired(user.getUserId());
        if (expired) {
            // can't login, go to the change password page
            // the user id is needed to validate the new password (validators)
            // and for the next action to know who's the user
            session.setAttribute(Constants.SESSION_USER_ID, user.getUserId());
            // the password will come handy to compare to the new one
            session.setAttribute("jsp_initial_password", password);
            // Leave the session empty, otherwise it'd be possible to go directly 
            // to a page
            return (mapping.findForward("changePassword"));
        }

        if (internalLogin) {
            user.setCompany(new CompanyDTO(Integer.valueOf(entityId)));
        }
        
        logUser(session, user);
        
        LOG.debug("user " + user.getUserName() + " logged. entity = " + user.getEntityId());
        // Forward control to the specified success URI
        return (mapping.findForward("success"));

    }
    
    static void logUser(HttpSession session, UserDTOEx user) {

        session.setAttribute(Constants.SESSION_LOGGED_USER_ID, 
                user.getUserId());
        session.setAttribute(Constants.SESSION_ENTITY_ID_KEY, 
                user.getEntityId());
        session.setAttribute(Constants.SESSION_LANGUAGE, user.getLanguageId());
        session.setAttribute(Constants.SESSION_CURRENCY, user.getCurrencyId());
        // need the whole thing :( for the permissions
        session.setAttribute(Constants.SESSION_USER_DTO, user); 
    }
}
