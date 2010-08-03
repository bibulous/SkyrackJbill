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
 * Created on 10-Mar-2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.order;

import java.io.IOException;
import java.math.BigDecimal;

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

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.order.IOrderSessionBean;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Context;

/**
 * @author Emil
 */
public class NewOrderItemAction extends Action {

    private static final Logger LOG = Logger.getLogger(NewOrderItemAction.class);

    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException, ServletException {

        ActionErrors errors = new ActionErrors();
        OrderDTO summary = null;

        // get the item id and quantity from the form
        Integer itemID;
        BigDecimal quantity;

        String action = request.getParameter("action");

        // check if the ejb session is already there
        HttpSession session = request.getSession(false);
        
        itemID = ((OrderAddItemForm) form).getItemID();
        quantity = ((OrderAddItemForm) form).getQuantity();

        if( quantity == null ){ 
            // No update on the summary, log errors
        	errors.add( ActionErrors.GLOBAL_ERROR, new ActionError("errors.invalid", "Quantity"));
            saveErrors(request, errors);
            
            // go back to the new order page, so the user can keep adding items 
            return (mapping.findForward("showOrderLIst"));
        }

        LOG.debug("Adding item " + itemID + " quant " + quantity);
        
        // if not create new one
        try {
            IOrderSessionBean remoteOrder = (IOrderSessionBean) Context.getBean(
                    Context.Name.ORDER_SESSION);

            summary = (OrderDTO) session.getAttribute(
                    Constants.SESSION_ORDER_SUMMARY);   

            // setup order if necessary
            if (summary.getBaseUserByCreatedBy() == null) {
                UserDTO user = new UserDTO();
                user.setId((Integer) session.getAttribute(
                        Constants.SESSION_USER_ID));
                summary.setBaseUserByCreatedBy(user);
            }

            // call the ejb session with the item to add
            // and get the order DTO for the summary
            if (action.compareTo("delete") == 0) {
                summary.removeLine(itemID);
            } else {
                summary = remoteOrder.addItem(itemID, quantity, summary,
                        (Integer) session.getAttribute(
                            Constants.SESSION_LANGUAGE),
                        (Integer) session.getAttribute(
                            Constants.SESSION_USER_ID),
                        (Integer) session.getAttribute(
                            Constants.SESSION_ENTITY_ID_KEY));
            }        	
        } catch (SessionInternalError e) {
            // this has been already logged ...
            LOG.error("Exception: ", e);
            errors.add(
                ActionErrors.GLOBAL_ERROR,
                new ActionError("all.internal"));
        } catch (Exception e) {
            if (e instanceof ItemDecimalsException ){
            	errors.add(
            			ActionErrors.GLOBAL_ERROR,
            			new ActionError("order.error.item.decimals"));
            } else if (e.getCause().getMessage().equals("Item Manager task error")) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("order.error.task"));
                LOG.error("Error at the AddItemAction", e);
            } else {
                errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("all.internal"));
                LOG.error("Error at the AddItemAction", e);
            }
        }

        if (!errors.isEmpty()) {
        	// No update on the summary, log errors
            saveErrors(request, errors);
        } else {
        	// add the order DTO to the http session for the summary
        	session.setAttribute(Constants.SESSION_ORDER_SUMMARY, summary);
        }
        /*
        log.debug("The bean " + Constants.SESSION_ORDER_SUMMARY
                + " is now in the session [" + 
                session.getAttribute(Constants.SESSION_ORDER_SUMMARY) + "]");
                */
        // go back to the new order page, so the user can keep adding items 
        return (mapping.findForward("showOrderLIst"));
    }
}
