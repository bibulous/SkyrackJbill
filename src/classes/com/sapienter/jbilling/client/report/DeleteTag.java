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

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.report.IReportSessionBean;
import com.sapienter.jbilling.server.util.Context;

/**
 * Calls the report session bean to get the specified report DTO
 * 
 * @author emilc
 *
 * @jsp:tag name="reportDelete"
 *          body-content="empty"
 */
public class DeleteTag extends TagSupport {

    Integer reportId = null;

    public int doStartTag() throws JspException {
        int retValue = SKIP_BODY;
        
        Logger log = Logger.getLogger(DeleteTag.class);
        ActionErrors errors = new ActionErrors();
        HttpSession session = pageContext.getSession();
        
        log.debug("Deleting ..");
        
        try {
            IReportSessionBean myRemoteSession = (IReportSessionBean) 
                    Context.getBean(Context.Name.REPORT_SESSION);
            myRemoteSession.delete(reportId);

            // this will force a reload of the list, otherwise the new 
            // entiry won't show up
            session.removeAttribute(Constants.SESSION_REPORT_LIST_USER);

        } catch (Exception e) {
            log.error(e);
            errors.add(ActionErrors.GLOBAL_ERROR, 
                    new ActionError("all.internal"));
            pageContext.getRequest().setAttribute(Globals.ERROR_KEY, 
                    errors);
        }        
        
        return retValue;
    }    
    
    /**
     * @jsp:attribute required="true"
     *                rtexprvalue="true"
     *                type="java.lang.Integer"
     * @return
     */
    public Integer getReportId() {
        return reportId;
    }

    /**
     * @param integer
     */
    public void setReportId(Integer integer) {
        reportId = integer;
    }

}
