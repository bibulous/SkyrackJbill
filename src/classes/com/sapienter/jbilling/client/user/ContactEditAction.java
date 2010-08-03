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
import java.util.Hashtable;
import java.util.Iterator;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.DynaValidatorForm;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class ContactEditAction extends Action {

    private DynaValidatorForm contactForm = null;
    private static final Logger LOG = Logger.getLogger(ContactEditAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        String forward = "edit";
        // Look up the module configuration information. Later will need it
        // to create the dynamic bean
        ModuleConfig moduleConfig = RequestUtils.getModuleConfig(request,
                servlet.getServletContext());
        
        Integer userId = (request.getParameter("userId") == null) 
                ? (Integer) session.getAttribute(
                        Constants.SESSION_CONTACT_USER_ID)
                : Integer.valueOf(request.getParameter("userId"));
                        
                        
        String action = request.getParameter("action");

        LOG.debug("In contact edit action = " + action);

        try {
            IUserSessionBean myRemoteSession = (IUserSessionBean) 
                    Context.getBean(Context.Name.USER_SESSION);
            
            contactForm = (DynaValidatorForm) form;
        
            if (action != null && action.equals("setup")) {
                // let's create the form bean and initialized it with the
                // data from the database
                
                fillForm(myRemoteSession.getPrimaryContactDTO(userId), request,
                        mapping, moduleConfig);
                contactForm.set("type", null);
                session.setAttribute("contact", contactForm);
                
                // make sure the user id is now in the session
                // (if called from a request, it'll be lost when submiting)
                session.setAttribute(Constants.SESSION_CONTACT_USER_ID,
                        userId);
            } else if (request.getParameter("reload") != null) {
                Integer type = (Integer) contactForm.get("type");
                ContactDTOEx dbContact;
                dbContact = myRemoteSession.getContactDTO(
                        userId, type);
                fillForm(dbContact, request, mapping, moduleConfig);
                session.setAttribute("contact", contactForm);
            } else { // send the information to the server for update
                
                ContactDTOEx contact = new ContactDTOEx();
                
                Integer type = (Integer) contactForm.get("type");
                contact.setOrganizationName(cleanField("organizationName"));
                contact.setAddress1(cleanField("address1"));
                contact.setAddress2(cleanField("address2"));
                contact.setCity(cleanField("city"));
                contact.setStateProvince(cleanField("stateProvince"));
                contact.setPostalCode(cleanField("postalCode"));
                contact.setCountryCode(cleanField("countryCode"));
                contact.setLastName(cleanField("lastName"));
                contact.setFirstName(cleanField("firstName"));
                contact.setInitial(cleanField("initial"));
                contact.setTitle(cleanField("title"));
                contact.setPhoneCountryCode((Integer) contactForm.get("phoneCountryCode"));
                String area = (String) contactForm.get("phoneAreaCode");
                area = area.trim();
                if (area.length() > 0) {
                    contact.setPhoneAreaCode(Integer.valueOf(area));
                }
                contact.setPhoneNumber(cleanField("phoneNumber"));
                contact.setFaxCountryCode((Integer) contactForm.get("faxCountryCode"));
                contact.setFaxAreaCode((Integer) contactForm.get("faxAreaCode"));
                contact.setFaxNumber(cleanField("faxNumber"));
                contact.setEmail(cleanField("email"));
                contact.setFieldsTable((Hashtable) contactForm.get("fieldsTable"));
                contact.setInclude(new Integer(((Boolean) contactForm.get("chbx_include")).
                        booleanValue() ? 1 : 0));
                
                myRemoteSession.setContact(contact, userId, type);  
                
                messages.add(ActionMessages.GLOBAL_MESSAGE, 
                        new ActionMessage("user.contact.edit.done"));
                saveMessages(request, messages);
                
                // remove the user's list so the contact info will be refreshed
                session.removeAttribute(Constants.SESSION_LIST_KEY + 
                        Constants.LIST_TYPE_CUSTOMER);
                forward = "done";
            }
                
        } catch (Exception e) {
            LOG.error("Exception!", e);
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("all.internal"));
            saveErrors(request, errors);
        }
        
        LOG.debug("After contact: action = " + action);
        LOG.debug("Fields number:" + ((Hashtable) contactForm.get(
                "fieldsTable")).size());
        for (Iterator it = ((Hashtable) contactForm.get(
                "fieldsTable")).keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            LOG.debug("field " + key + " is " + ((Hashtable) contactForm.get(
                "fieldsTable")).get(key));
        }
        return mapping.findForward(forward);
    }
    
    // we don't want to insert blanks or empty strings in the DB
    private String cleanField(String field) {
        String retValue = (String) contactForm.get(field);
        if (retValue == null) return null;
        retValue = retValue.trim();
        
        return (retValue.length() > 0 ? retValue : null);
    }
    
    private void fillForm(ContactDTOEx dbContact,
            HttpServletRequest request, ActionMapping mapping,
            ModuleConfig moduleConfig) {
        contactForm = (DynaValidatorForm) RequestUtils
                .createActionForm(request, mapping, moduleConfig, servlet);
        
        contactForm.set("organizationName",
                dbContact.getOrganizationName());
        contactForm.set("address1", dbContact.getAddress1());
        contactForm.set("address2", dbContact.getAddress2());
        contactForm.set("city", dbContact.getCity());
        contactForm.set("stateProvince", dbContact.getStateProvince());
        contactForm.set("postalCode", dbContact.getPostalCode());
        contactForm.set("countryCode", dbContact.getCountryCode());
        contactForm.set("lastName", dbContact.getLastName());
        contactForm.set("firstName", dbContact.getFirstName());
        contactForm.set("initial", dbContact.getInitial());
        contactForm.set("title", dbContact.getTitle());
        contactForm.set("phoneCountryCode",
                dbContact.getPhoneCountryCode());
        contactForm.set("phoneAreaCode", 
                dbContact.getPhoneAreaCode() != null ?
                dbContact.getPhoneAreaCode().toString() : null);
        contactForm.set("phoneNumber", dbContact.getPhoneNumber());
        contactForm.set("faxCountryCode",
                dbContact.getFaxCountryCode());
        contactForm.set("faxAreaCode",
                dbContact.getFaxAreaCode());
        contactForm.set("faxNumber", dbContact.getFaxNumber());
        contactForm.set("email", dbContact.getEmail());
        contactForm.set("fieldsTable", dbContact.getFieldsTable());
        Boolean include = new Boolean(false);
        if (dbContact.getInclude() != null) {
            include = new Boolean(dbContact.getInclude().intValue() == 1);
        }
        contactForm.set("chbx_include", include);
    }
}
