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
 * Created on 20-Mar-2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.order;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.Resources;

import com.sapienter.jbilling.server.order.db.OrderLineDTO;

/**
 * A great pain to make this work. Finally, the solution was the scope
 * of this bean. Since it goes back a forth to the server a few times,
 * it has to be at the session level. A request level is all trouble.
 * @author Emil
 */
public class NewOrderDTOForm extends ActionForm {
    private Hashtable orderLines = null;
    
    public NewOrderDTOForm() {
        orderLines = new Hashtable();
    }

    // called by the logic:iterate tag, to get the keys of
    // each line
    public Hashtable getOrderLines() {
        return orderLines;
    }

    // called by the action form, to set the lines from the
    // original DTO
    public void setOrderLines(Hashtable newOL) {
        orderLines = newOL;
    }

    // called by the html:text tag, to show (an apparently also set ??)
    // the values of each line
    public Object getOrderLine(String itemId) {
        return (OrderLineDTO) orderLines.get(Integer.valueOf(itemId));
    }

    // apparently never called
    public void setOrderLine(String itemId, Object line) {
        orderLines.put(Integer.valueOf(itemId), line);
    }

    // just for debugging
    public String toString() {
        return (orderLines == null ? "Empty" : orderLines.toString());
    }

    /**
     * Manual validation for the order review page.
     * This is necessary because is a multi-lined form made of X
     * number of beans in a Hashmap. (don't know how to acomplish this
     * by the normal xml declarations).
     * @see org.apache.struts.action.ActionForm#validate(ActionMapping, HttpServletRequest)
     */
    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {

        ServletContext application = getServlet().getServletContext();
        ActionErrors errors = new ActionErrors();
        Validator validator = null;
        Logger log = Logger.getLogger(NewOrderDTOForm.class);

        // validate is called even before the page is shown ..
        if (orderLines == null) {
            return null;
        }

        Collection lines = orderLines.values();
        try {
            int lineNumber = 1;
            for (Iterator i = lines.iterator(); i.hasNext(); lineNumber++) {
                OrderLineDTO line = (OrderLineDTO) i.next();
                // only editable lines get validated
                if (line.getEditable().booleanValue()) { 
                    validator = Resources.initValidator("orderReviewForm",
                            line, application, request, errors, 0);

                    validator.validate();
                    if (!errors.isEmpty()) {
                        log.debug("line = " + line);
                        errors.add(
                            ActionErrors.GLOBAL_ERROR,
                            new ActionError(
                                "order.review.bad_order_line",
                                new Integer(lineNumber)));
                        break;
                    }
                }

            }

        } catch (ValidatorException e) {
            log.error("Error calling the validator", e);
        }

        return errors;
    }

}