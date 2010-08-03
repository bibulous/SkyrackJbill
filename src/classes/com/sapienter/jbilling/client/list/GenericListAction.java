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

package com.sapienter.jbilling.client.list;

import java.io.IOException;
import java.util.List;

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
import org.apache.struts.validator.Resources;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.customer.ICustomerSessionBean;
import com.sapienter.jbilling.server.invoice.IInvoiceSessionBean;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.payment.IPaymentSessionBean;
import com.sapienter.jbilling.server.payment.db.PaymentDTO;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.util.Context;
import java.util.ArrayList;

/**
 * All the parameters of the list have to come from the request (hidden fields
 * of the form). Otherwise, if they come from the session, hitting the 'back'
 * button from the browser can mix up parameters between to generic lists.
 * @author Emil
 */
public class GenericListAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        ActionErrors errors = new ActionErrors();
        ActionForward retValue = null;
        HttpSession session = request.getSession(false);
        Logger log = Logger.getLogger(GenericListAction.class);
        
        String action = request.getParameter("action");
        
        if (action != null) {
            // this is a paging action, not a selection
            PagedList list = (PagedList) session.getAttribute(
                    Constants.SESSION_PAGED_LIST);
            // this helps for 'Back' on the browser
            list.setDoSearch(null);
            // verify if there has been some 'Back' pressed
            if (action.equals("next") || action.equals("prev") || 
                    action.equals("inverse")) {
                String listId = request.getParameter("listId");
                if (!list.getListId().toString().equals(listId)) {
                    errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("list.error.navBack"));
                    saveErrors(request, errors);
                    action = ""; // to avoid any other if
                }
            }
            if (action.equals("next")) {
                if (session.getAttribute(Constants.SESSION_PAGED_IS_NEXT) 
                        != null){
                    // add one page
                    int newPage = list.getCurrentPage().intValue() + 1;
                    list.setCurrentPage(new Integer(newPage));
                    // make sure the start of this new page is noted
                    if (list.getPageFrom().size() <= newPage) {
                        Integer lastId = (Integer) session.getAttribute(
                                Constants.SESSION_LIST_LAST_ID);
                        list.getPageFrom().add(lastId);
                    }
                }
            } else if (action.equals("prev")) {
                if (list.getPageFrom().get(list.getCurrentPage().intValue()) 
                        != null) {
                    // one page back 
                    int newPage = list.getCurrentPage().intValue() - 1;
                    list.setCurrentPage(new Integer(newPage));
                }
            } else if (action.equals("inverse")) {
                list.setDirection(new Boolean(!list.getDirection().
                        booleanValue()));
                // reset
                reset(list);
            } else  if (action.equals("size")) {
                try {
                    DynaValidatorForm myForm = (DynaValidatorForm) form;
                    String sizeStr = (String) myForm.get("pageSize");
                    String field = Resources.getMessage(request, 
                            "list.prompt.pageSize"); 
                    Integer size = null;
                    if (sizeStr == null) {
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("errors.required", field));
                    } else {
                        try {
                            size = Integer.valueOf(sizeStr);
                            if (size.intValue() < 20 || size.intValue() > 500) {
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError("errors.range", field, "20", "500"));
                            }
                        } catch (NumberFormatException e) {
                            errors.add(ActionErrors.GLOBAL_ERROR,
                                    new ActionError("errors.integer", field));
                        }
                    }
                    
                    if (errors.isEmpty()) {
                        IUserSessionBean remoteUser = (IUserSessionBean) 
                                Context.getBean(Context.Name.USER_SESSION);
                        
                        remoteUser.updatePreference((Integer)session.getAttribute(
                                Constants.SESSION_LOGGED_USER_ID), 
                                Constants.PREFERENCE_PAGE_SIZE,
                                size, null, null);
                        list.setPageSize(size);
                        reset(list);
                    } else {
                        saveErrors(request, errors);
                    }

                } catch (Exception e) {
                    throw new ServletException(e);
                }
            } else if (action.equals("search")) {
                try { 
                    DynaValidatorForm myForm = (DynaValidatorForm) form;
                    // get the data type to see what to validate
                    String dataType = (String) myForm.get("dataType");
                    String key = (String) myForm.get("key");
                    String search1 = (String) myForm.get("search1");
                    String listId = (String) myForm.get("listType");
                    String prompt = Resources.getMessage(request, key); 
                    Integer intParam = null;
                    String start = null;
                    String end = null;
                    
                    // verify that the page is consitent, and no 'back' button
                    // was involved
                    if (!list.getListId().toString().equals(listId)) {
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("list.error.searchBack"));
                    } else {
                        if (dataType.equals("integer")) {
                            if (search1 == null || search1.length() == 0) {
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError("errors.required", prompt));
                            } else {
                                try {
                                    intParam = Integer.valueOf(search1.trim());
                                    start = intParam.toString();
                                } catch (NumberFormatException e) {
                                    errors.add(ActionErrors.GLOBAL_ERROR,
                                            new ActionError("errors.integer", prompt));
                                }
                            }
                        }
                        if (dataType.equals("string")) {
                            if (search1 == null || search1.length() == 0) {
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError("errors.required", prompt));
                            } else {
                                search1 = search1.trim();
                                if (search1.length() < 3) {
                                    errors.add(ActionErrors.GLOBAL_ERROR,
                                            new ActionError("errors.minlength", prompt, "3"));
                                } else {
                                    start = search1;
                                }
                            }
                        }
                        if (dataType.equals("date")) {
                            String search2 = ((String) myForm.get("search2")).trim();
                            String search3 = ((String) myForm.get("search3")).trim();
                            String search4 = ((String) myForm.get("search4")).trim();
                            String search5 = ((String) myForm.get("search5")).trim();
                            String search6 = ((String) myForm.get("search6")).trim();
                            try {
                                validateDate(search1);
                                validateDate(search2);
                                validateDate(search3);
                                validateDate(search4);
                                validateDate(search5);
                                validateDate(search6);
                            } catch (NumberFormatException e) {
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError("list.error.invalidDate", prompt));
                            }
                            start = search3 + '-' + search1 + '-' + search2;
                            end = search6 + '-' + search4 + '-' + search5;
                            if (Util.parseDate(start) == null) {
                                if (start.length() > 2) {
                                    errors.add(ActionErrors.GLOBAL_ERROR,
                                            new ActionError("list.error.invalidFrom"));
                                }
                                start = null;
                            }
                            if (Util.parseDate(end) == null) {
                                if (end.length() > 2) {
                                    errors.add(ActionErrors.GLOBAL_ERROR,
                                            new ActionError("list.error.invalidTo"));
                                }
                                end = null;
                            }
                            if (start == null && end == null) {
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError("errors.required", prompt));
                            } 
                        }
                    }
                    
                    if (errors.isEmpty()) {
                        list.setSearchStart(start);
                        list.setSearchEnd(end);
                        list.setDoSearch(new Boolean(true));
                        list.setSearchFieldId((Integer) myForm.get("fieldId"));
                    } else {
                        list.setDoSearch(null);
                        saveErrors(request, errors);
                    }
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            } 
            
            request.setAttribute(Constants.REQUEST_LIST_TYPE, 
                    list.getLegacyName());
            request.setAttribute(Constants.REQUEST_LIST_METHOD, 
                    "jdbc");
            request.setAttribute(Constants.REQUEST_LIST_IS_PAGED, 
                    new Boolean(true));

            return mapping.findForward("list");
        }

        // This action requires to know to where to send the request
        String forwardFrom = request.getParameter(
                Constants.REQUEST_FORWARD_FROM);
        String forwardTo = request.getParameter(
                Constants.REQUEST_FORWARD_TO);

        if (forwardFrom == null || forwardTo == null) {
            throw new ServletException("Forward parameters are required." +
                    "[" + forwardFrom + "]" + " [" + forwardTo + "]");
        }
        
        // get the type from a hidden field, this will avoid problems with 
        // the 'back' browser button
        String type = request.getParameter(Constants.REQUEST_LIST_TYPE);
        if (type == null || type.length() == 0) {
            throw new ServletException("The list type is required in the" +
                    " request" + type);
        }
        
        // verifies that the customer has been selected, and 
        // if so, it puts it in the session
        String selectionStr = request.getParameter(
                Constants.REQUEST_SELECTION_ID);
        log.debug("Selection:" + selectionStr);
        try {
            if (selectionStr == null) {
                // it didn't make a pick ...not good ...
                errors.add( ActionErrors.GLOBAL_ERROR,
                    new ActionError("all.error.listSelection"));
                saveErrors(request, errors);
                retValue = (mapping.findForward(forwardFrom));
            
            } else {
                Integer selectionId = Integer.valueOf(selectionStr);
               
                if (type.equals(Constants.LIST_TYPE_CUSTOMER) ||
                        type.equals(Constants.LIST_TYPE_CUSTOMER_SIMPLE) ||
                        type.equals(Constants.LIST_TYPE_SUB_ACCOUNTS)) {
                    // the user list adds two objects to the session, the
                    // contact dto and the user id selected, that's why it
                    // has specific code here
                    ICustomerSessionBean remoteCustomer = (ICustomerSessionBean)
                            Context.getBean(Context.Name.CUSTOMER_SESSION);
                    ContactDTO info = remoteCustomer.getPrimaryContactDTO(selectionId);
                    session.setAttribute(Constants.SESSION_CUSTOMER_CONTACT_DTO,
                    		info);
                    
                    session.setAttribute(Constants.SESSION_USER_ID,
                            selectionId);
                    
                    // this is for compatibility issues
                    session.setAttribute(Constants.SESSION_LIST_ID_SELECTED,
                            selectionId); 
                    // Is your list or form sensitive to a previous selection of
                    // a customer? If so, the cache mechanism will make the list
                    // to stay the same even though the user as changed.
                    // If that is the case, you have to add you list in this
                    // section
                    /* When entering a payment, selecting a customer means that
                     * the invoice list has to be rerun.
                     * All this is to solve the browser 'back' button */
                    session.removeAttribute(Constants.SESSION_INVOICE_DTO);
                    session.removeAttribute(Constants.SESSION_LIST_KEY + 
                            Constants.LIST_TYPE_INVOICE);
                    session.removeAttribute("payment");
                    // the list of items when creating an order ...
                    session.removeAttribute(Constants.SESSION_LIST_KEY + 
                            Constants.LIST_TYPE_ITEM_ORDER);
                } else if (type.equals(Constants.LIST_TYPE_PARTNER)) {
                    // the list of payouts changes when a new partner is selected
                    session.removeAttribute(Constants.SESSION_LIST_KEY + 
                            Constants.LIST_TYPE_PAYOUT);
                    session.setAttribute(Constants.SESSION_LIST_ID_SELECTED,
                            selectionId);   
                } else if (type.equals(Constants.LIST_TYPE_INVOICE) ||
                        type.equals(Constants.LIST_TYPE_INVOICE_ORDER)) {
                    IInvoiceSessionBean remoteInvoice = (IInvoiceSessionBean) 
                            Context.getBean(Context.Name.INVOICE_SESSION);
                    InvoiceDTO info = remoteInvoice.getInvoice(selectionId);
                    session.setAttribute(Constants.SESSION_INVOICE_DTO, info);
                } else if (type.equals(Constants.LIST_TYPE_PAYMENT_USER)) {                    
                    IPaymentSessionBean remotePayment = (IPaymentSessionBean) 
                            Context.getBean(Context.Name.PAYMENT_SESSION);
                    PaymentDTO info = remotePayment.getPayment(selectionId, 
                            (Integer) session.getAttribute(
                                Constants.SESSION_LANGUAGE));
                    session.setAttribute(Constants.SESSION_PAYMENT_DTO, info);
                } else if (type.equals(Constants.LIST_TYPE_ITEM_USER_PRICE)) {
                    // user price can't rely on the generic 'ID_SELECTED', because
                    // the setup is called in the generic action for both the
                    // creation (after selection an item), and edition (after
                    // selecting a user price from the list).
                    session.setAttribute(Constants.SESSION_ITEM_PRICE_ID,
                            selectionId);
                } else { // use a generic key
                    session.setAttribute(Constants.SESSION_LIST_ID_SELECTED,
                            selectionId);                    
                }
                
                retValue = (mapping.findForward(forwardTo)); 
            }
        } catch (Exception e) {
            errors.add(
                ActionErrors.GLOBAL_ERROR,
                new ActionError("all.internal"));
            saveErrors(request, errors);
            retValue = (mapping.findForward(forwardFrom));
            log.error("Exception", e);
        }
        
        return retValue;
    }
    
    private void validateDate(String arg) 
            throws NumberFormatException {
        if (arg != null && arg.length() > 0) {
            Integer.valueOf(arg);
        }
    }
    
    private void reset(PagedList dto) {
        dto.setCurrentPage(new Integer(0));
        List starts = new ArrayList();
        starts.add(null);
        dto.setPageFrom(starts);
        dto.setDoSearch(null);
    }
}
