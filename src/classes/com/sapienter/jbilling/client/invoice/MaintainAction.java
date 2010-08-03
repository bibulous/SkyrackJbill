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

package com.sapienter.jbilling.client.invoice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.invoice.IInvoiceSessionBean;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.util.Context;

public class MaintainAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        Logger log = Logger.getLogger(MaintainAction.class);
        HttpSession session = request.getSession(false);
        ActionMessages messages = new ActionMessages();
        String forward = "invoice_view";

        try {
            IInvoiceSessionBean invoiceSession = (IInvoiceSessionBean) 
                    Context.getBean(Context.Name.INVOICE_SESSION);

            // I could call the GenericMaintainAction here, but since
            // there's no create/edit/update with invoices, it'd make 
            // things more complicated
            String action = request.getParameter("action");
            
            Integer invoiceId;
            if (request.getParameter("id") != null) {
                // this is being called from anywhere, to check out a invoice
                invoiceId = Integer.valueOf(request.getParameter("id"));
                session.setAttribute(Constants.SESSION_LIST_ID_SELECTED,
                        invoiceId);
            } else if (request.getParameter("latest") != null) {
                UserDTOEx user = (UserDTOEx) session.getAttribute(
                        Constants.SESSION_USER_DTO);
                invoiceId = user.getLastInvoiceId();
                if (invoiceId == null) {
                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("invoice.error.noInvoice"));
                    forward = "no_invoice";
                }
            } else {
                // this is called from the list of invoices
                invoiceId = (Integer) session.getAttribute(
                        Constants.SESSION_LIST_ID_SELECTED);
            }
            
            if (action != null && action.equals("notify")) {
        
                INotificationSessionBean notificationSession = 
                        (INotificationSessionBean) Context.getBean(
                        Context.Name.NOTIFICATION_SESSION);
                Boolean result = notificationSession.emailInvoice(invoiceId);
                String field;
                if (result.booleanValue()) {
                    field = "email.notify.ok";
                } else {
                    field = "email.notify.error"; 
                }
                messages.add(ActionMessages.GLOBAL_MESSAGE, 
                        new ActionMessage(field));
            } if (action != null && action.equals("delete")) {
                Integer executorId = (Integer) session.getAttribute(
                        Constants.SESSION_LOGGED_USER_ID);
                invoiceSession.delete(invoiceId, executorId);
                messages.add(ActionMessages.GLOBAL_MESSAGE, 
                        new ActionMessage("invoice.delete.done"));
                // remove the cached list so the deleted invoice doesn't 
                // show up
                session.removeAttribute(Constants.SESSION_LIST_KEY +
                        Constants.LIST_TYPE_INVOICE_GRAL);
                forward = "invoice_list";
            } else if (invoiceId != null ) {
            
                InvoiceDTO dto = invoiceSession.getInvoiceEx(invoiceId,
                        (Integer) session.getAttribute(
                            Constants.SESSION_LANGUAGE));
                // just put the dto in the session for viewing
                session.setAttribute(Constants.SESSION_INVOICE_DTO, dto);
            }
            saveMessages(request, messages);
            log.debug("Forward to " + forward);
            return mapping.findForward(forward);            
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        
        return mapping.findForward("error");
    }
  
}
