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

package com.sapienter.jbilling.client.mediation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sapienter.jbilling.server.mediation.db.MediationRecordStatusDTO;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.mediation.IMediationSessionBean;
import com.sapienter.jbilling.server.mediation.db.MediationProcess;
import com.sapienter.jbilling.server.util.Context;

public class ProcessListAction extends Action {
    private static final Logger LOG = Logger.getLogger(ProcessListAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        try {
            IMediationSessionBean mediationSession = (IMediationSessionBean)
                    Context.getBean(Context.Name.MEDIATION_SESSION);
        
            HttpSession session = request.getSession(false);
            List <MediationProcess> list = mediationSession.getAll( (Integer)
                    session.getAttribute(Constants.SESSION_ENTITY_ID_KEY));
            Map<MediationRecordStatusDTO, Long> statusesMap = mediationSession.getNumberOfRecordsByStatuses(
                    (Integer) session.getAttribute(Constants.SESSION_ENTITY_ID_KEY));

            session.setAttribute("mediation_process_list", list);
            session.setAttribute("records_by_statuses_map", statusesMap);
            
            return mapping.findForward("view");            
        } catch (Exception e) {
            LOG.error("Exception ", e);
        }
        
        return mapping.findForward("error");
    }

}
