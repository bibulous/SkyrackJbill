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

import java.util.Collection;

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
import com.sapienter.jbilling.server.report.IReportSessionBean;
import com.sapienter.jbilling.server.report.ReportDTOEx;
import com.sapienter.jbilling.server.util.Context;

/**
 * Prepares the result set so the InsertDataRowTag can then render
 * the data in a table
 * 
 * @author emilc
 *
 * @jsp:tag name="reportList"
 * 			body-content="JSP"
 */

public class ListTag extends TagSupport {
    
    // this can be:
    //     entity = all the reports for the entity (in session)
    //     user   = all the saved reports for the give user/report type
    String mode = null;

    public int doStartTag() throws JspException {
        int retValue = SKIP_BODY;

        Logger log = Logger.getLogger(ListTag.class);
        ActionErrors errors = new ActionErrors();
        
        try {
            // I'll get the entity id first from the session
            HttpSession session = pageContext.getSession();

            // this might be a recall, so lets see if the list is already cached
            // recalls have to be addressed with this cache, because for each click
            // to add an item there's a recall ...
            Collection queryResults = null;
            
            if (mode.equals("entity")) {
                queryResults = (Collection) session.getAttribute(
                    Constants.SESSION_REPORT_LIST);
            } else if (mode.equals("user")) {
                // do not cache the list of user's reports
                queryResults = null;
            }
            
            if (queryResults == null) {

                // Now I'll call the session bean to get the CachedRowSet with
                // the results of the query
                IReportSessionBean myRemoteSession = (IReportSessionBean) 
                        Context.getBean(Context.Name.REPORT_SESSION);

                if (mode.equals("entity")) {
                    Integer entityID = (Integer) session.getAttribute(
                            Constants.SESSION_ENTITY_ID_KEY);
                    
                    queryResults = myRemoteSession.getList(entityID);
                    session.setAttribute(Constants.SESSION_REPORT_LIST, 
                            queryResults);
                } else if (mode.equals("user")) {
                    Integer userId =  (Integer) session.getAttribute(
                            Constants.SESSION_LOGGED_USER_ID);
                    Object ob = session.getAttribute(
                            Constants.SESSION_REPORT_DTO);
                    HttpServletRequest request = (HttpServletRequest) 
                            pageContext.getRequest();
                    Integer reportId = null;
                    
                    String reqReportId = request.getParameter(
                            Constants.REQUEST_REPORT_ID);
                    if (reqReportId != null && reqReportId.length() > 0) {
                        reportId = Integer.valueOf(reqReportId);
                    }
                    if (reportId == null && ob != null ) { // take it from the session
                        reportId = ((ReportDTOEx) ob).getId();
                    } 
                    if (reportId == null) {
                        throw new SessionInternalError("can't get the report id");
                    }
                    queryResults = myRemoteSession.getUserList(reportId,
                            userId);
                    session.setAttribute(Constants.SESSION_REPORT_LIST_USER, 
                            queryResults);
                }
                log.debug("Added the reports list to the session");
            } 
        } catch (Exception e) {
            errors.add(ActionErrors.GLOBAL_ERROR, 
                    new ActionError("all.internal"));
            pageContext.getRequest().setAttribute(Globals.ERROR_KEY, 
                    errors);

            log.error("Exception at list report tag", e);
        }

        return retValue;

    }

    /**
     * @jsp:attribute required="true"
     *                rtexprvalue="true"
     *                type="java.lang.String"
     * @return
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param string
     */
    public void setMode(String string) {
        mode = string;
    }

}
