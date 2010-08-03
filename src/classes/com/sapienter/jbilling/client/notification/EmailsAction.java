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

package com.sapienter.jbilling.client.notification;

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
import org.apache.struts.action.DynaActionForm;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class EmailsAction extends Action {
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        Logger log = Logger.getLogger(MaintainAction.class);
        try {
            INotificationSessionBean notificationSession = 
                    (INotificationSessionBean) Context.getBean(
                    Context.Name.NOTIFICATION_SESSION);
        
            DynaActionForm myForm = (DynaActionForm) form;
            String separator = (String) myForm.get("separator");
            HttpSession session = request.getSession(false);
            String content = notificationSession.getEmails((Integer) 
                    session.getAttribute(Constants.SESSION_ENTITY_ID_KEY), 
                        separator);
            
            myForm.set("content", content);
            session.setAttribute("notificationEmails", myForm);
                    
            return mapping.findForward("view");            
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        
        return mapping.findForward("error");
    }
    
    protected boolean isCancelled(HttpServletRequest request) {
        return !request.getParameter("mode").equals("setup");
    }     
}
