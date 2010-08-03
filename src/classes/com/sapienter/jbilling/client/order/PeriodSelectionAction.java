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

package com.sapienter.jbilling.client.order;

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

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;

/**
 * Now this just takes the period from the request and puts it
 * in the OrderDTO that is already in the session
 * @author emilc
 *
 */
public class PeriodSelectionAction extends Action {
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException, ServletException {

        Logger log = Logger.getLogger(ReviewOrderAction.class);

        HttpSession session = request.getSession(false);
        OrderDTO summary =
            (OrderDTO) session.getAttribute(Constants.SESSION_ORDER_SUMMARY);

		
        Integer period =
            Integer.valueOf(
                (String)request.getParameter(Constants.REQUEST_ORDER_PERIOD));
        log.debug("got period =" + period);        
        
        OrderPeriodDTO dto = new OrderPeriodDTO();
        dto.setId(period);
        summary.setOrderPeriod(dto);
        

        return mapping.findForward("showOrderItems");
    }

}
