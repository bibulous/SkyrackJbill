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
 * Created on 4-Jun-2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.report.Field;
import com.sapienter.jbilling.server.report.IReportSessionBean;
import com.sapienter.jbilling.server.report.ReportDTOEx;
import com.sapienter.jbilling.server.util.Context;

/**
 * Calls the report session bean to get the specified report DTO
 * 
 * @author emilc
 *
 * @jsp:tag name="reportSetUp"
 *          body-content="empty"
 */
public class ReportSetUpTag extends TagSupport {

    private static final Logger LOG = Logger.getLogger(ReportSetUpTag.class);
    
    public int doStartTag() throws JspException {
        int retValue = SKIP_BODY;
        
        
        ActionErrors errors = new ActionErrors();
        
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) 
                pageContext.getRequest();
        ReportDTOEx report = (ReportDTOEx) session.getAttribute(
                Constants.SESSION_REPORT_DTO);
        // get the either the report id or the user report id
        String param = request.getParameter(Constants.REQUEST_REPORT_ID);
        Integer reportId = null;
        Integer userReportId = null;
        
        if (param != null ) {
            reportId = Integer.valueOf(param);
            session.setAttribute(Constants.SESSION_REPORT_LINK, 
                Constants.REQUEST_REPORT_ID + "=" + reportId);
        }
        
        param = request.getParameter(
                Constants.REQUEST_USER_REPORT_ID);
        
        if (param != null) {
            userReportId = Integer.valueOf(param);
            session.setAttribute(Constants.SESSION_REPORT_LINK, 
                Constants.REQUEST_USER_REPORT_ID + "=" + userReportId);
        }

        LOG.debug("Running report setup rid:" + reportId + ":" + 
                (report != null ? report.getId() : null));

        String back = request.getParameter("back");
        
        // verify that this is not just a recall
        if ((reportId != null && report != null && 
                new Integer(report.getId()).equals(reportId) && 
                report.getUserReportId() == null) ||
                back != null) {
            // this is doing some caching
            LOG.debug("Using a cached report");
            return retValue;
        }
        
        try {
            // get a report remote session
            IReportSessionBean myRemoteSession = (IReportSessionBean) 
                    Context.getBean(Context.Name.REPORT_SESSION);
            // get the object
            if (reportId != null) {
                Integer entityId = (Integer) session.getAttribute(
                        Constants.SESSION_ENTITY_ID_KEY);
                LOG.debug("Fetching report " + reportId);
                report = myRemoteSession.getReportDTO(
                        reportId, entityId);
            } else if (userReportId != null) {
                LOG.debug("Fetching user report " + userReportId);
                report = myRemoteSession.getReportDTO(
                        userReportId);
            } 
            
            if (report == null) {
                throw new SessionInternalError("Report is not present. " +
                        "Both report id and user report id are missing");
            }

            // make it available in the session
            session.setAttribute(Constants.SESSION_REPORT_DTO, report);
            LOG.debug("reportDto = " + report);
            
            // create the dynamic form with the necessary fields
            Form form = new Form(report.getFields().size());
            for (int f = 0; f < report.getFields().size(); f++) {
                Field field = (Field) report.getFields().get(f);
                // set the flag of selected or not
                form.setSelect(f, field.getIsShown().intValue() == 1);
                // set the where fields if this field is wherable
                if (field.getWhereable().intValue() == 1) {
                    if (field.getWhereValue() != null) {
                        if (field.getDataType().equals(Field.TYPE_DATE)) {
                            // TODO: this will fail to put back an invalid
                            // value. Example: an empty month will throw
                            // so the year will be skipped.
                            try {
                                form.setDay(f, String.valueOf(Util.getDay(
                                        field.getWhereValue())));
                                form.setMonth(f, String.valueOf(Util.getMonth(
                                        field.getWhereValue())));
                                form.setYear(f, String.valueOf(Util.getYear(
                                        field.getWhereValue())));
                            } catch (SessionInternalError e) {
                            }
                        } else {
                            form.setWhere(f, field.getWhereValue());
                        }
                    } else {
                        // let's see what happens when a null value
                        // is used to populate a html field.
                        // Hopefully is going to show up empty
                    }
                    
                    // since it's wherable, the operator will be required
                    form.setOperator(f, field.getOperatorValue());
                }
                
                // set the functionable values
                if (field.getIsShown().intValue() == 1 && 
                        field.getFunctionable().intValue() == 1) {
                    if (field.getFunctionName() != null) {
                        form.setFunction(f, field.getFunctionName());            
                    } else if (field.getIsGrouped().intValue() == 1) {
                        form.setFunction(f, "grouped"); 
                    }
                    else {
                        form.setFunction(f, "none");
                    }
                }
                
                // set the order by values
                if (field.getOrdenable().intValue() == 1) {
                    form.setOrderBy(f, String.valueOf(
                            field.getOrderPosition()));
                }
            }

            LOG.debug("Now form is " + form);
            session.setAttribute(Constants.SESSION_REPORT_FORM, form);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
            errors.add(ActionErrors.GLOBAL_ERROR, 
                    new ActionError("all.internal"));
        }        

        if (!errors.isEmpty()) {
            pageContext.getRequest().setAttribute(Globals.ERROR_KEY, 
                    errors);
        }
        
        return retValue;
    }    
}

