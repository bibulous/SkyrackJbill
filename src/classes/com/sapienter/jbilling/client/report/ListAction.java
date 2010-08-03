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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.report.IReportSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class ListAction extends Action {

    Logger log = null;
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        log = Logger.getLogger(ListAction.class);
        ActionErrors errors = new ActionErrors();        
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
        } else {
            throw new ServletException("typeId missing for list of reports");
        }
        
    }
    
    private Collection getListByType(Integer type) 
            throws SessionInternalError {
        IReportSessionBean myRemoteSession = (IReportSessionBean) 
                Context.getBean(Context.Name.REPORT_SESSION);
        return myRemoteSession.getListByType(type);

    }
}
