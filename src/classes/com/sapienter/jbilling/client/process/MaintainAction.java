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

package com.sapienter.jbilling.client.process;

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

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.process.BillingProcessDTOEx;
import com.sapienter.jbilling.server.process.IBillingProcessSessionBean;
import com.sapienter.jbilling.server.process.db.BillingProcessConfigurationDTO;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class MaintainAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        Logger log = Logger.getLogger(MaintainAction.class);
        HttpSession session = request.getSession(false);
        String forward = "error";
        ActionMessages messages = new ActionMessages();
        try {
            IBillingProcessSessionBean processSession = 
                    (IBillingProcessSessionBean) Context.getBean(
                    Context.Name.BILLING_PROCESS_SESSION);
            
            String action = request.getParameter("action");
            Integer entityId = (Integer) session.
                    getAttribute(Constants.SESSION_ENTITY_ID_KEY);
            
            if (action.equals("newInvoice") || 
                    action.equals("applyToInvoice")) {
                OrderDTO order = (OrderDTO) session.getAttribute(
                        Constants.SESSION_ORDER_DTO);
                if (!order.getStatusId().equals(
                        Constants.ORDER_STATUS_ACTIVE)) {
                    ActionErrors errors = new ActionErrors();
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                            "order.error.status"));
                    saveErrors(request, errors);
                    return mapping.findForward("order_view");       
                }
            }

            if (action.equals("view")) {
                Integer processId = (Integer) session.getAttribute(
                        Constants.SESSION_LIST_ID_SELECTED);

                // by default, we show the last for this entity
                if (processId == null || request.getParameter("latest") != null) {
                    processId = processSession.getLast(entityId);
                }
                // it could be that an entity just signed up and has no processes
                if (processId != null) {
                    BillingProcessDTOEx dto = processSession.getDto(processId,
                            (Integer) session.getAttribute(
                                Constants.SESSION_LANGUAGE));
                    session.setAttribute(Constants.SESSION_PROCESS_DTO, dto);
                    forward = "process_view";
                } else {
                    forward = "process_list";
                }
            } else if (action.equals("review")) {
                BillingProcessDTOEx dto = processSession.getReviewDto(entityId,
                        (Integer) session.getAttribute(
                            Constants.SESSION_LANGUAGE));
                BillingProcessConfigurationDTO configDto = processSession.
                        getConfigurationDto(entityId);
                if (dto != null) {
                    session.setAttribute(Constants.SESSION_PROCESS_DTO, dto);
                } else {
                    
                    session.removeAttribute(Constants.SESSION_PROCESS_DTO);
                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("process.noReview"));
                }
                session.setAttribute(
                        Constants.SESSION_PROCESS_CONFIGURATION_DTO, 
                        configDto);
                forward = "process_review";
            } else if (action.equals("approval")) {
                boolean flag;
                if (request.getParameter("yes") != null) {
                    flag = true;
                } else {
                    flag = false;
                }
                BillingProcessConfigurationDTO configDto = 
                        processSession.setReviewApproval((Integer) 
                        session.getAttribute(Constants.SESSION_LOGGED_USER_ID), 
                            entityId, new Boolean(flag));
                session.setAttribute(
                        Constants.SESSION_PROCESS_CONFIGURATION_DTO, 
                        configDto);
                forward = "process_review";
            } else if (action.equals("newInvoice")) {
                // an invoice is being generated from the gui, directly from
                // an order.
                // I need an order first
                OrderDTO order = (OrderDTO) session.getAttribute(
                        Constants.SESSION_ORDER_DTO);
                if (order == null) {
                    throw new SessionInternalError("an order dto has to be " +
                            "present to generate an invoice");
                }
                Integer languageId = (Integer) session.getAttribute(
                        Constants.SESSION_LANGUAGE);
                InvoiceDTO invoice = processSession.generateInvoice(
                        order.getId(), null, languageId);
                
                if (invoice != null) {
                    // get the user in the session
                    IUserSessionBean myRemoteSession = (IUserSessionBean) 
                            Context.getBean(Context.Name.USER_SESSION);
                    session.setAttribute(Constants.SESSION_CUSTOMER_CONTACT_DTO,
                            myRemoteSession.getPrimaryContactDTO(
                                invoice.getUserId()));
                    // the user id too
                    session.setAttribute(Constants.SESSION_USER_ID,
                            invoice.getUserId());
                    // and this invoice
                    session.setAttribute(Constants.SESSION_INVOICE_DTO,
                            invoice);
                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("process.invoiceGenerated", 
                                    invoice.getId()));
                    forward = "payment_create";
                } else {
                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("process.invoiceNotGenerated"));
                    forward = "order_view";
                }
            } else if (action.equals("applyToInvoice")) {
                OrderDTO order = (OrderDTO) session.getAttribute(
                        Constants.SESSION_ORDER_DTO);
                InvoiceDTO invoice = (InvoiceDTO) session.getAttribute(
                        Constants.SESSION_INVOICE_DTO);
                
                if (order == null || invoice == null) {
                    throw new SessionInternalError("an order dto and " +
                            " invoice dto have to be " +
                            "present to apply to an invoice");
                }
                
                Integer languageId = (Integer) session.getAttribute(
                        Constants.SESSION_LANGUAGE);
                InvoiceDTO invoiceEx  = processSession.generateInvoice(
                        order.getId(), invoice.getId(), languageId);
                
                if (invoiceEx == null) {
                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("process.orderNotApplied",
                                    order.getId()));
                    
                    forward = "order_view";
                } else {
                    // the new invoice has to be in the session for display
                    session.setAttribute(Constants.SESSION_INVOICE_DTO, 
                            invoiceEx);
                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("process.orderApplied", 
                                    order.getId(), invoiceEx.getId()));
                    forward = "invoice_view";
                }
            }
            
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        
        saveMessages(request, messages);
        
        return mapping.findForward(forward);            
    }
  
}
