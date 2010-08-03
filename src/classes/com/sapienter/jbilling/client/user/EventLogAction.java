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

package com.sapienter.jbilling.client.user;

import java.io.IOException;
import java.util.List;

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

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.audit.db.EventLogDTO;

public class EventLogAction extends Action {

    private static Logger LOG = Logger.getLogger(EventLogAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        HttpSession session = request.getSession(false);

        String action = request.getParameter("action");
        if (action == null) {
            LOG.error("action is required in EventLogAction");
            throw new ServletException("action is required");
        }
        
        Integer userId = (Integer) session.getAttribute(
                Constants.SESSION_USER_ID);

        String forward = "displayLog";

        try {
            IUserSessionBean userSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);

            if (action.equals("displayLog")) {
                List<EventLogDTO> events = userSession.getEventLog(userId);
                request.setAttribute(Constants.REQUEST_EVENT_LOG, events);
            } else {
                LOG.error("action not supported" + action);
                throw new ServletException("action not supported: " + action);
            }
        } catch (Exception e) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("all.internal"));
            LOG.debug("Exception:", e);
        }

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }

        return mapping.findForward(forward);
    }
}
