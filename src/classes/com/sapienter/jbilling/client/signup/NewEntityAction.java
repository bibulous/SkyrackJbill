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
 * Created on Feb 8, 2005
 *
 */
package com.sapienter.jbilling.client.signup;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

import com.sapienter.jbilling.common.Constants;
import com.sapienter.jbilling.server.invoice.db.InvoiceDeliveryMethodDTO;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.db.CustomerDTO;
import com.sapienter.jbilling.server.util.Context;

/**
 * @author Emil
 */
public class NewEntityAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        Logger log = Logger.getLogger(NewEntityAction.class);
        String action = request.getParameter("action");
        if (action == null || action.length() == 0) {
            return null;
        }
        
        String retValue = null;
        HttpSession session;
        
        if (action.equals("setup")) {
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = request.getSession();
            // locale
            String language = request.getParameter("language");
            String country = request.getParameter("country");
            Locale locale = null;
            if (language == null || language.length() == 0) {
                language = "en";
            }
            if (country == null || country.length() == 0) {
                locale = new Locale(language);
                country = "US";
            } else {
                locale = new Locale(language, country);
            }
            
            
            session.setAttribute(Globals.LOCALE_KEY, locale);
            session.setAttribute("signup_language", language);
            log.debug("Setup language to " + 
                    session.getAttribute("signup_language"));
            session.setAttribute("signup_country", country);

            retValue = "edit";
        } else if (action.equals("edit")) {
            session = request.getSession(false);
            DynaValidatorForm myForm = (DynaValidatorForm) form;
            ActionErrors errors = new ActionErrors(myForm.validate(
                    mapping, request));
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
                return mapping.findForward("edit");
            }
            log.debug("now doing the entity creation");
            // check this constructor out ... :p
            ContactDTO contact = new ContactDTO();
            contact.setOrganizationName((String) myForm.get("company_name")); 
            contact.setAddress1((String) myForm.get("address1"));
            contact.setAddress2((String) myForm.get("address2"));
            contact.setCity((String) myForm.get("city"));
            contact.setStateProvince((String) myForm.get("state"));
            contact.setPostalCode((String) myForm.get("postal_code"));
            contact.setCountryCode((String) myForm.get("country"));
            contact.setLastName((String) myForm.get("last_name"));
            contact.setFirstName((String) myForm.get("first_name"));
            contact.setPhoneAreaCode(((String)myForm.get("phone_area")).length() == 0 ? 
                        null : Integer.valueOf((String) myForm.get("phone_area")));
            contact.setPhoneNumber((String) myForm.get("phone_number")); 
            contact.setEmail((String) myForm.get("email"));
            contact.setDeleted(0);
            contact.setInclude(1);
            
            UserDTOEx user = new UserDTOEx();
            user.setUserName((String) myForm.get("user_name"));
            user.setPassword((String) myForm.get("password"));
            user.setMainRoleId(Constants.TYPE_ROOT);
            CustomerDTO cust = new CustomerDTO();
            cust.setInvoiceDeliveryMethod(new InvoiceDeliveryMethodDTO(Constants.D_METHOD_EMAIL));
            user.setCustomer(cust);
            
            try {
                IUserSessionBean userSession = (IUserSessionBean) 
                        Context.getBean(Context.Name.USER_SESSION);
                log.debug("Using language " + 
                        session.getAttribute("signup_language"));
                
                Integer entityId = userSession.createEntity(contact, user, 
                        null, null, 
                        (String) session.getAttribute("signup_language"), null);

                // refresh the jbilling table 
                RefreshBettyTable toCall = new RefreshBettyTable(null);
                toCall.refresh();
                
                // store message
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, 
                        new ActionMessage("newEntity.welcome", entityId.toString(),
                                user.getUserName(), user.getPassword()));
                saveMessages(request, messages);

                retValue = "done";
            } catch (Exception e) {
                log.debug("Exception ", e);
            }
        }

        return mapping.findForward(retValue);
    }
}
