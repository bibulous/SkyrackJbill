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
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.util.Context;

public class PartnerMaintainAction extends Action {

    private final static Logger LOG = Logger.getLogger(PartnerMaintainAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        
        try {
            IUserSessionBean userSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
            HttpSession session = request.getSession(false);
            if (request.getParameter("action").equals("view")) {
                
                if (request.getParameter("payout") != null) {
                    
                    Integer selectedId = (Integer) session.getAttribute(
                            Constants.SESSION_LIST_ID_SELECTED);
                    session.setAttribute(Constants.SESSION_PAYOUT_DTO,
                            userSession.getPartnerPayoutDTO(selectedId));
                    return mapping.findForward("payout_view");       
                }
                String forward;
                //see if this is a partner logged and looking at her own 
                //statement
                // first, get the user
                UserDTOEx dto;
                if (request.getParameter("self") != null) {
                    dto = (UserDTOEx) session.getAttribute(
                            Constants.SESSION_USER_DTO);
                    forward = "partner_statement";
                } else { // its a root looking a this partner
                    String userIdstr = request.getParameter("id");
                    Integer selectedId;
                    if (userIdstr == null) {
                        selectedId = (Integer) session.getAttribute(
                                Constants.SESSION_LIST_ID_SELECTED);
                    } else {
                        // this probably comes from a report
                        selectedId = Integer.valueOf(userIdstr);
                    }
                    dto = userSession.getUserDTOEx(selectedId);
                    forward = "partner_view";
                }
                session.setAttribute(Constants.SESSION_CUSTOMER_DTO, dto);
                
                // now the partner
                session.setAttribute(Constants.SESSION_PARTNER_DTO,
                        dto.getPartner());
                
                // the contact
                session.setAttribute(Constants.SESSION_CUSTOMER_CONTACT_DTO,
                        userSession.getPrimaryContactDTO(dto.getUserId()));
                        
                // the latest payout, if any available
                LOG.debug("Partner has " + dto.getPartner().getPartnerPayouts().size()
                        + " payouts");
                if (dto.getPartner().getPartnerPayouts().size() > 0) {
                    session.setAttribute(Constants.SESSION_PAYOUT_DTO,
                            userSession.getPartnerLastPayoutDTO(
                                dto.getPartner().getId()));
                } else {
                    session.removeAttribute(Constants.SESSION_PAYOUT_DTO);
                }
                
                // for the link to edit the user to work
                session.setAttribute(Constants.SESSION_USER_ID, 
                        dto.getUserId());
                
                // the list of customer has to be removed, or it will take the
                // one from the latest partner selected
                session.removeAttribute(Constants.SESSION_LIST_KEY + Constants.LIST_TYPE_PARTNERS_CUSTOMER);

                        
                return mapping.findForward(forward);
            } else {
                if (request.getParameter("action").equals("setup") && 
                        session.getAttribute(Constants.SESSION_PARTNER_DTO) 
                            != null) {
                    
                    session.setAttribute(Constants.SESSION_PARTNER_ID,
                            ((Partner) session.getAttribute(
                                    Constants.SESSION_PARTNER_DTO)).getId());
                }
                PartnerCrudAction delegate = new PartnerCrudAction(userSession);
                delegate.setServlet(this.getServlet());
                return delegate.execute(mapping, form, request, response);
            }
        } catch (Exception e) {
            LOG.error("Exception ", e);
        }
        
        return mapping.findForward("error");
    }
  
}
