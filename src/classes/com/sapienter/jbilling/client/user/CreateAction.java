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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.CustomerDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.user.permisson.db.RoleDTO;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import com.sapienter.jbilling.server.util.db.LanguageDTO;

public class CreateAction extends Action {
    
    private static final Logger LOG = Logger.getLogger(CreateAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        String retValue = "done";
        Integer newUserId = null;
        // some attributes of the new user will be inherited from the root user
        // that is creating this account
        HttpSession session = request.getSession();
        Integer entityId = (Integer) session.getAttribute(
                Constants.SESSION_ENTITY_ID_KEY);
        
        Integer languageId = (Integer) session.getAttribute(
                Constants.SESSION_LANGUAGE);
        // get the dynamic form
        DynaActionForm userForm = (DynaActionForm) form;
        
        LOG.debug("Create user action");
        
        // verify that the password and the verification password are the same
        if (!((String) userForm.get("password")).equals((String) 
                userForm.get("verifyPassword"))) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("user.create.error.password_match"));
        } else {
         
            try {
                IUserSessionBean myRemoteSession = (IUserSessionBean) 
                        Context.getBean(Context.Name.USER_SESSION);
                
                // create the dto from the dynamic form
                Integer typeId = (Integer) userForm.get("type");
                if (typeId == null) {
                    // some users won't have the permission to pick the
                    // type, so it is assumend they are creating customers
                    typeId = Constants.TYPE_CUSTOMER;
                }

                UserDTOEx dto = new UserDTOEx();
                dto.setCompany(new CompanyDTO(entityId));
                dto.setUserName((String)userForm.get("username"));
                dto.setPassword((String)userForm.get("password"));
                dto.setLanguage(new LanguageDTO(languageId));
                dto.setMainRoleId(typeId);
                dto.setCurrency(new CurrencyDTO((Integer) userForm.get("currencyId")));
                
                // add the roles 
                // now, it will be just one and directly mapped to the user type
                // this doesn't have to be like this, it is now because it is 
                // enough for the BAT requirements
                dto.getRoles().add(new RoleDTO(typeId));
                
                // finally, create the contact dto to send the email
                ContactDTOEx contact = new ContactDTOEx();
                contact.setEmail((String) userForm.get("email"));
                // and default the country to the entity's
                contact.setCountryCode(myRemoteSession.getEntityContact(
                        entityId).getCountryCode());
                // by default the contact is included 
                contact.setInclude(new Integer(1));
                
                
                // if it is a customer, it'll need the customer dto
                if (typeId.equals(Constants.TYPE_CUSTOMER)) {
                    CustomerDTO customerDto = new CustomerDTO();
                    
                    // set the partner 
                    Integer partnerId = null;
                    try {
                        partnerId = Integer.valueOf((String) userForm.get(
                                "partnerId"));
                    } catch (Exception e) {
                        try {
                            partnerId = Integer.valueOf((String)
                                    session.getAttribute("jsp_partnerId"));
                        } catch (Exception e1) {
                        }
                    }
                    if (partnerId != null) {
                        customerDto.setPartner(new Partner(partnerId));
                    }
                    customerDto.setReferralFeePaid(new Integer(0));
                    dto.setCustomer(customerDto);

                    // set the sub-account fields
                    String parentStr = (String) userForm.get("parentId");
                    Integer parentId = null;
                    if (parentStr != null && parentStr.length() > 0) {
                        parentId = Integer.valueOf(parentStr);
                    }
                    if (parentId != null) {
                        customerDto.setParent(new CustomerDTO(parentId));
                    }
                    
                    customerDto.setIsParent(((Boolean) userForm.get(
                            "chbx_is_parent")).booleanValue() 
                            ? new Integer(1) : new Integer(0));
                    
                    // verify that the parent is valid
                    if (customerDto.getParent() != null) {
                        UserDTOEx parent = myRemoteSession.getUserDTOEx(
                                customerDto.getParent().getId());
                        if (parent == null || !parent.getEntityId().equals(entityId) ||
                                parent.getDeleted() == 1) {
                            errors.add(ActionErrors.GLOBAL_ERROR,
                                    new ActionError(
                                    "user.create.error.noParent"));
                        } else {
                            if (parent.getCustomer() == null ||
                                    parent.getCustomer().getIsParent() == null ||
                                    parent.getCustomer().getIsParent().intValue() == 0) {
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError(
                                        "user.create.error.badParent"));
                            }
                        }
                    }
                    
                    customerDto.setInvoiceChild(((Boolean) userForm.get(
                            "chbx_invoiceChild")).booleanValue() 
                            ? new Integer(1) : new Integer(0));
                    
                    if (customerDto.getInvoiceChild().intValue() == 1 &&
                            customerDto.getParent() == null) {
                        // it has to be a child with a parent for this flag to 
                        // make any sense
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError(
                                    "user.create.error.invoiceChild"));
                    }
                                    

                } 
                
                if (typeId.equals(Constants.TYPE_PARTNER) && errors.isEmpty()) {
                    // partners require more data
                    
                    // verify that the user is available
                    UserDTO user = myRemoteSession.getUserDTO(
                            dto.getUserName(), entityId);
                    if (user != null) {
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("user.create.error.taken", 
                                (String) userForm.get("username")));
                    } else {
                        // leave the data available to the next step
                        session.setAttribute(Constants.SESSION_CUSTOMER_DTO, dto);
                        session.setAttribute(
                                Constants.SESSION_CUSTOMER_CONTACT_DTO, contact);
                        // but to allow the defaults to be loaded, make sure no
                        // id is in the session
                        session.removeAttribute(Constants.SESSION_PARTNER_ID);
                        retValue = "partner";
                    }
                } else if (errors.isEmpty()) {
                    newUserId = myRemoteSession.create(dto, contact);
                    if (newUserId == null) {
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("user.create.error.taken", 
                                (String) userForm.get("username")));
                    } else if (newUserId.intValue() == -1) {
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("user.create.error.badPartner"));
                    }
                }
            } catch (Exception e) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("all.internal"));
                LOG.error("Exception", e);
            }
        }
        
        if (errors.isEmpty()) {
            // the creation was successfull, let's now see if that's all or
            // the contact information will be entered
            messages.add(ActionMessages.GLOBAL_MESSAGE, 
                    new ActionMessage("user.create.done"));
            // later, when a customer is self creating, the forward might
            // have to be to the contact form.
            // leave the user id in the session so the page knows that
            // is time to show up the contact/cc options
            session.setAttribute(Constants.SESSION_USER_ID, 
                    newUserId);
            LOG.debug("new user id = " + 
                    newUserId);
            session.setAttribute(Constants.SESSION_CONTACT_USER_ID, 
                    newUserId);
            saveMessages(request, messages);
            // remove any list with users
            session.removeAttribute(Constants.SESSION_LIST_KEY + 
                    Constants.LIST_TYPE_CUSTOMER);
            session.removeAttribute(Constants.SESSION_LIST_KEY + 
                    Constants.LIST_TYPE_CUSTOMER_SIMPLE);
            session.removeAttribute(Constants.SESSION_LIST_KEY + 
                    Constants.LIST_TYPE_SUB_ACCOUNTS);
        } else {
            // something failed
            saveErrors(request, errors);
            LOG.debug("errors: " + errors.size());
            retValue = "error";
        }
        
        return mapping.findForward(retValue);
        
    }
}
