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

package com.sapienter.jbilling.client.report;

import java.io.IOException;
import java.util.Collection;

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
import org.apache.struts.validator.Resources;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.report.Field;
import com.sapienter.jbilling.server.report.IReportSessionBean;
import com.sapienter.jbilling.server.report.ReportDTOEx;
import com.sapienter.jbilling.server.util.Context;

public class SubmitAction extends Action {

    Logger log = null;
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        log = Logger.getLogger(SubmitAction.class);
        ActionErrors errors = new ActionErrors();        
        log.debug("In submit action");
        HttpSession session = request.getSession();
        
        /*
         * Use this same action to get the list of report for a type
         */
        
        String typeId = request.getParameter("type");
        if (typeId != null) {
            try {
                session.setAttribute(Constants.SESSION_REPORT_LIST, 
                        getListByType(Integer.valueOf(typeId)));
                return mapping.findForward("listType");
            } catch (Exception e) {
                log.error("Exception:", e);
                return mapping.findForward("error");
            }
        }
        
        
        /*
         * Get the form from and populate the values back to the dto 
         */
        Form reportForm = (Form) form;
        ReportDTOEx report = (ReportDTOEx) session.getAttribute(
                Constants.SESSION_REPORT_DTO);
        for (int f=0; f < reportForm.getSize(); f++) {
            Field field = (Field) report.getFields().get(f);
            if (field.getSelectable().intValue() == 1) {
                field.setIsShown(new Integer(
                        reportForm.getSelect(f) ? 1 : 0));
            }
            if (field.getWhereable().intValue() == 1) {
                if (field.getDataType().equals(Field.TYPE_DATE)) {
                    if (reportForm.getYear(f).length() > 0 ||
                            reportForm.getMonth(f).length() > 0 ||
                            reportForm.getDay(f).length() > 0) {
                        field.setWhereValue(reportForm.getYear(f) + "-" +
                                reportForm.getMonth(f) + "-" +
                                reportForm.getDay(f));
                    } else {
                        field.setWhereValue(null);
                    }
                } else {
                    field.setWhereValue(reportForm.getWhere(f));
                }
            }
            // the operator
            if (field.getOperatorable().intValue() == 1) {
                field.setOperatorValue(reportForm.getOperator(f));
            }
            
            // the function/groupby
            if (field.getFunctionable().intValue() == 1) {
                if (reportForm.getFunction(f).equals("none")) {
                    field.setFunctionName(null);
                    field.setIsGrouped(new Integer(0));
                } else if (reportForm.getFunction(f).equals("grouped")) {
                    field.setFunctionName(null);
                    field.setIsGrouped(new Integer(1));
                } else {
                    field.setFunctionName(reportForm.getFunction(f));
                    field.setIsGrouped(new Integer(0));
                }
                // since the report might have changed it's agregradtes status
                // the flag has to be updated
                report.updateAggregatedFlag(); 
            }
        
            // the order by
            if (field.getOrdenable().intValue() == 1) {
                int val = Integer.valueOf(reportForm.getOrderBy(f)).intValue();
                if (val == 0) {
                    field.setOrderPosition(null);
                } else {
                    field.setOrderPosition(new Integer(val));
                }
            }
        }
        
        /*
         * add the dynamic parameters
         */
        TriggerAction.addDynamicVariables(report, session);
        
        // now validate
        if (report.validate()) {  
            if (reportForm.getSaveFlag() != null &&
                    reportForm.getSaveFlag().length() > 0) {
                log.debug("It is a save");
                if (reportForm.getSaveName() == null  ||
                        reportForm.getSaveName().length() == 0) {
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.dto.save"));
                    saveErrors(request, errors);        
                    return mapping.findForward("error");
                } 
                session.setAttribute(Constants.SESSION_REPORT_TITLE, 
                        reportForm.getSaveName());
                try {
                    IReportSessionBean myRemoteSession = (IReportSessionBean) 
                            Context.getBean(Context.Name.REPORT_SESSION);
                    myRemoteSession.save(report, (Integer) session.getAttribute(
                            Constants.SESSION_LOGGED_USER_ID), (String)
                            session.getAttribute(Constants.SESSION_REPORT_TITLE));

                    // this will force a reload of the list, otherwise the new 
                    // entry won't show up
                    session.removeAttribute(Constants.SESSION_REPORT_LIST_USER);
                    
                    // put a message to let know the user that it has been saved
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("report.save.done"));
                    saveMessages(request, messages);
                } catch (Exception e) {
                    log.error(e);
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("all.internal"));
                    saveErrors(request, errors);
                }        
                
                return mapping.findForward("save");
                          
            } 
            // clean up the session results
            session.removeAttribute(Constants.SESSION_REPORT_RESULT);
            log.debug("Executing");
            // the execution will happen in a custom tag        
            return mapping.findForward("execute");
            
        } 
        translateErrors(report, errors, request);
        log.debug("Validation error");
        saveErrors(request, errors);
        return mapping.findForward("error");
    }
    
    private Collection getListByType(Integer type) 
            throws SessionInternalError {
        IReportSessionBean myRemoteSession = (IReportSessionBean) 
                Context.getBean(Context.Name.REPORT_SESSION);
        return myRemoteSession.getListByType(type);

    }
    
    private void translateErrors(ReportDTOEx report, ActionErrors errors, 
            HttpServletRequest request) {
        for (int f=0; f < report.getErrorCodes().size(); f++) {
            int code = ((Integer) report.getErrorCodes().get(f)).intValue();
            String fieldName = null;
            if (((Integer)report.getErrorFields().
                    get(f)).intValue() != -1) {
                fieldName = Resources.getMessage(request, 
                        ((Field)report.getFields().get(((Integer)report.getErrorFields().
                                get(f)).intValue())).getTitleKey());        
            }
            switch (code) {
                case ReportDTOEx.ERROR_ADD_AGREGATE:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.dto.add.agregate", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_AGREGATE:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.dto.agregate", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_DATATYPE:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.dataType", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_FUNCTION:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.function", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_ISNULL:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.isNull", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_MISSING:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.dto.missing")); 
                break;
                case ReportDTOEx.ERROR_NO_OPERATOR:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.noOperator", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_OPERATOR:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.operator", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_TITLE:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.title", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_WHERE:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.where", 
                            fieldName));
                break;
                case ReportDTOEx.ERROR_WHERE_NOINTEGER:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.where.noInteger",
                            fieldName));
                break;
                case ReportDTOEx.ERROR_WHERE_NODATE:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.where.noDate",
                            fieldName));
                break;
                case ReportDTOEx.ERROR_WHERE_NOFLOAT:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.where.noFloat",
                            fieldName));
                break;
                case ReportDTOEx.ERROR_ORDER:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.dto.order",
                            fieldName));
                break;
                case ReportDTOEx.ERROR_ORDER_RANGE:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.order",
                            fieldName));
                break;
                case ReportDTOEx.ERROR_ORDER_AGGREGATE:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.dto.order.aggregate",
                            fieldName));
                break;
                case ReportDTOEx.ERROR_IN_OP_EQUAL:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.inOperator",
                            fieldName));
                break;
                case ReportDTOEx.ERROR_NULL_OPERATOR:
                    errors.add(ActionErrors.GLOBAL_ERROR, 
                            new ActionError("report.error.field.operatorNull",
                            fieldName));
                break;

                default:
                    log.error("Unsupported error:" + code);
                    break;
            }
        }
    }
}
