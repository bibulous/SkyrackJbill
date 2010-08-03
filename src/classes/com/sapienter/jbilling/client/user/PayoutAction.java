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
 * Created on Apr 13, 2004
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.user;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

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
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.user.partner.db.PartnerPayout;
import com.sapienter.jbilling.server.util.Context;

/**
 * @author Emil
 */
public class PayoutAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        Logger log = Logger.getLogger(PayoutAction.class);
        String forward = "error";
        ActionErrors errors = new ActionErrors();
        try {
            IUserSessionBean userSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
            String action = request.getParameter("action");
            HttpSession session = request.getSession(false);
            Partner partner = (Partner) session.getAttribute(
                    Constants.SESSION_PARTNER_DTO);
            
            DynaValidatorForm myForm = (DynaValidatorForm) form;
            if (action.equals("setup")) {
                // get the dates for a new payout
                Date dates[] = userSession.getPartnerPayoutDates(
                        partner.getId());
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dates[0]);
                myForm.set("start_month", String.valueOf(cal.get(
                        GregorianCalendar.MONTH) + 1));
                myForm.set("start_day", String.valueOf(cal.get(
                        GregorianCalendar.DAY_OF_MONTH)));
                myForm.set("start_year", String.valueOf(cal.get(
                        GregorianCalendar.YEAR)));            
                cal.setTime(dates[1]);
                myForm.set("end_month", String.valueOf(cal.get(
                        GregorianCalendar.MONTH) + 1));
                myForm.set("end_day", String.valueOf(cal.get(
                        GregorianCalendar.DAY_OF_MONTH)));
                myForm.set("end_year", String.valueOf(cal.get(
                        GregorianCalendar.YEAR)));            
                forward = "edit";
            } else if (action.equals("edit")) {
                // validates
                errors = myForm.validate(mapping, request);
                Date startDate = null, endDate = null;
                if (errors.isEmpty()) {
                    startDate = Util.getDate(Integer.valueOf(
                            (String) myForm.get("start_year")), 
                            Integer.valueOf((String) myForm.get("start_month")),
                            Integer.valueOf((String) myForm.get("start_day")));
                    if (startDate == null) {
                        String field = Resources.getMessage(request, 
                                "payout.prompt.startDate"); 
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("errors.date", field));
                    } 
                    endDate = Util.getDate(Integer.valueOf(
                            (String) myForm.get("end_year")), 
                            Integer.valueOf((String) myForm.get("end_month")),
                            Integer.valueOf((String) myForm.get("end_day")));
                    if (endDate == null) {
                        String field = Resources.getMessage(request, 
                                "payout.prompt.endDate"); 
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("errors.date", field));
                    } 
                }
                
                if (errors.isEmpty()) {
                    PartnerPayout payout = userSession.calculatePayout(
                            partner.getId(), startDate, endDate, null);
                    session.setAttribute(Constants.SESSION_PAYMENT_DTO, 
                            new PaymentDTOEx(payout.getPayment()));
                    session.setAttribute(Constants.SESSION_PAYOUT_DTO, payout);
                    session.setAttribute("jsp_payment_method", 
                            (String) myForm.get("method"));
                    forward = "payment";
                } else {
                    forward = "edit";
                }
            } else if (action.equals("view")) {
                Integer payoutId = Integer.valueOf(request.getParameter("id"));
                session.setAttribute(Constants.SESSION_PAYOUT_DTO,
                        userSession.getPartnerPayoutDTO(payoutId));
                forward = "view";
            }
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        
        saveErrors(request, errors);
        
        return mapping.findForward(forward);
    }
 
}
