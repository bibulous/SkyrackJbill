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
 * Created on Dec 15, 2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.report;

import java.io.IOException;
import java.util.Calendar;

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
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.report.Field;
import com.sapienter.jbilling.server.report.IReportSessionBean;
import com.sapienter.jbilling.server.report.ReportDTOEx;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.util.Context;

/**
 * This action is used to run a report with a series of parameters directly from
 * a link. This way, there's no form shown to the user, or any user interaction
 * at all. After clicking on the link, the user is shown the results.
 * @author Emil
 */
public class TriggerAction extends Action {

    Logger log = null;
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        log = Logger.getLogger(TriggerAction.class);
        HttpSession session = request.getSession();
        Integer entityId = (Integer) session.getAttribute(
                Constants.SESSION_ENTITY_ID_KEY);

        try {
            // Now I'll call the session bean to get the CachedRowSet with
            // the results of the query
            IReportSessionBean remoteSession = (IReportSessionBean) 
                    Context.getBean(Context.Name.REPORT_SESSION);

            String id = request.getParameter("id");
            String mode = request.getParameter("mode");
            if (id == null || id.length() == 0 || mode == null || 
                    mode.length() == 0) {
                throw new ServletException("id and mode are " +
                        "required");
            }
            
            Integer reportId = Integer.valueOf(id);
            ReportDTOEx report = remoteSession.getReportDTO(reportId, 
                    entityId);

            /*
             * add the dynamic parameters
             */
            addDynamicVariables(report, session);
            
            // now define this reports variables, since the user is not involved
            setFieldValues(report, mode, session);
            
            // leave the report object ready to be picked up by the execute tag
            session.setAttribute(Constants.SESSION_REPORT_DTO, report);
            // avoid caching
            session.removeAttribute(Constants.SESSION_REPORT_RESULT);
        } catch (Exception e) {
            log.debug("Exception:", e);
            return mapping.findForward("error");
        }

        return mapping.findForward("show_result");
    }
    
    /**
     * This will allow for the substitution of fields with the where = "?" 
     * @param report
     * @param session
     */
    static void addDynamicVariables(ReportDTOEx report, HttpSession session) {
        String entityId = String.valueOf((Integer) 
                session.getAttribute(Constants.SESSION_ENTITY_ID_KEY));
        String languageId = String.valueOf((Integer) 
                session.getAttribute(Constants.SESSION_LANGUAGE));
        
        Integer compare = new Integer(report.getId());
        if (compare.equals(ReportDTOEx.REPORT_PAYMENT) ||
        		compare.equals(ReportDTOEx.REPORT_ORDER) || 
        		compare.equals(ReportDTOEx.REPORT_ORDER_LINE) ||
        		compare.equals(ReportDTOEx.REPORT_REFUND) ||
        		compare.equals(ReportDTOEx.REPORT_PARTNER) ||
        		compare.equals(ReportDTOEx.REPORT_PAYOUT) ||
        		compare.equals(ReportDTOEx.REPORT_USERS) ||
        		compare.equals(ReportDTOEx.REPORT_TRANSACTIONS) ||
        		compare.equals(ReportDTOEx.REPORT_SUBSCRIPTIONS) ||
        		compare.equals(ReportDTOEx.REPORT_STATUS_TRANSITIONS) ||
        		compare.equals(ReportDTOEx.REPORT_SUBSC_TRANSITIONS)) {
        	//reports with both entity and language
            report.addDynamicParameter(entityId);
            report.addDynamicParameter(languageId);
            // the users need an additional parameter: the id for the primary
            // contact type
            if(compare.equals(ReportDTOEx.REPORT_USERS)) {
                try {
                    IUserSessionBean remoteSession = (IUserSessionBean) 
                            Context.getBean(Context.Name.USER_SESSION);
                    report.addDynamicParameter(remoteSession.
                            getEntityPrimaryContactType(Integer.valueOf(entityId)).
                                toString());
                } catch (Exception e) {
                    Logger.getLogger(TriggerAction.class).error("Exception " +
                            "finding the primary type for entity " + entityId,
                            e);
                } 
            }
        } else if(compare.equals(ReportDTOEx.REPORT_OVERDUE)) {
            report.addDynamicParameter(entityId);
            // compares the due_date with todays date
            report.addDynamicParameter(Util.parseDate(Calendar.getInstance().
                    getTime()));
        } else if(compare.equals(ReportDTOEx.REPORT_PARTNER_ORDERS) ||
        		compare.equals(ReportDTOEx.REPORT_PARTNER_PAYMENTS) ||
        		compare.equals(ReportDTOEx.REPORT_PARTNER_REFUNDS)) {
            UserDTOEx loggedUser = (UserDTOEx) session.getAttribute(
                    Constants.SESSION_USER_DTO);
            if (loggedUser.getMainRoleId().equals(Constants.TYPE_PARTNER)) {
                report.addDynamicParameter(String.valueOf(loggedUser.getPartner().getId()));
            } else {
                Partner partner = (Partner) session.getAttribute(
                        Constants.SESSION_PARTNER_DTO);
                report.addDynamicParameter(String.valueOf(partner.getId()));
            }
            report.addDynamicParameter(languageId);
        } else {
            // reports with only the entity
            report.addDynamicParameter(entityId);
        }
    }

    /**
     * This method will set the value of a field, so there's no interaction with
     * the user. If the report is run normally, this field will be shown.
     * @param report
     * @param mode
     * @param session
     */
    void setFieldValues(ReportDTOEx report, String mode, HttpSession session) {
    	
    	Integer compare = new Integer(report.getId());
    	
        if (mode.equals("customer")) {
        	// add some additional where conditions
            if (compare.equals(ReportDTOEx.REPORT_ORDER) || 
            		compare.equals(ReportDTOEx.REPORT_INVOICE) ||
            		compare.equals(ReportDTOEx.REPORT_PAYMENT) ||
            		compare.equals(ReportDTOEx.REPORT_REFUND)) {
                UserDTOEx user = (UserDTOEx) session.getAttribute(
                        Constants.SESSION_CUSTOMER_DTO);
                Field username = report.getField("base_user", "id");
                username.setOperatorValue(Field.OPERATOR_EQUAL);
                username.setWhereValue(user.getUserId().toString());
            }
            // add some basic ordering
            if (compare.equals(ReportDTOEx.REPORT_ORDER)) {
                Field id = report.getField("purchase_order", "id");
                id.setOrderPosition(new Integer(1));
            } else if (compare.equals(ReportDTOEx.REPORT_INVOICE)) {
                Field id = report.getField("invoice", "id");
                id.setOrderPosition(new Integer(1));
            } else if (compare.equals(ReportDTOEx.REPORT_PAYMENT) ||
            		compare.equals(ReportDTOEx.REPORT_REFUND)) {
                Field id = report.getField("payment", "id");
                id.setOrderPosition(new Integer(1));
            } 
            // remove columns not needed
            if (compare.equals(ReportDTOEx.REPORT_PAYMENT)) {
            	Field field = report.getField("base_user", "user_name");
            	field.setIsShown(0);
            	field = report.getField("payment", "result_id");
            	field.setIsShown(0);
            	field = report.getField("payment", "method_id");
            	field.setIsShown(0);
            }
                
        } else if (mode.equals("partner")) {
            if (compare.equals(ReportDTOEx.REPORT_PARTNER)) {
                Field date = report.getField("partner", "next_payout_date");
                date.setOperatorValue(Field.OPERATOR_SM_EQ);
                date.setWhereValue(Util.parseDate(Calendar.getInstance().
                        getTime()));
            }
        }
    }
}
