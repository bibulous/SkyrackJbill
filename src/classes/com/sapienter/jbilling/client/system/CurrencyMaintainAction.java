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

package com.sapienter.jbilling.client.system;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
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
import com.sapienter.jbilling.server.item.IItemSessionBean;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;

public class CurrencyMaintainAction extends Action {
    
    private static final Logger LOG = Logger.getLogger(CurrencyMaintainAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        
        try {
            IItemSessionBean itemSession = (IItemSessionBean) Context.getBean(Context.Name.ITEM_SESSION);
            
            String action = request.getParameter("action");
            HttpSession session = request.getSession(false);
            Integer entityId = (Integer) session.getAttribute(
                    Constants.SESSION_ENTITY_ID_KEY);
            Integer languageId = (Integer) session.getAttribute(
                    Constants.SESSION_LANGUAGE);
            CurrencyArrayForm myForm = (CurrencyArrayForm) form;
            if (action.equals("setup")) {
                myForm.setLines(itemSession.getCurrencies(languageId, 
                        entityId));
                myForm.setDefaultCurrencyId(itemSession.getEntityCurrency(
                        entityId)); 
            } else if (action.equals("edit")) {
                for (int f = 0; f < myForm.getLines().length; f++) {
                    CurrencyDTO line = myForm.getLines()[f];
                    if (line.getRate() != null && line.getRate().trim().
                            length() > 0) {
                        try {
                            UserDTOEx user = (UserDTOEx) session.getAttribute(
                                    Constants.SESSION_USER_DTO);
                            NumberFormat nf = NumberFormat.getInstance(
                                    user.getLocale());

                            nf.parse(line.getRate().trim());
                        } catch(ParseException e) {
                            String field = Resources.getMessage(request, 
                                    "system.currency.prompt.rate"); 
                            errors.add(ActionErrors.GLOBAL_ERROR,
                                    new ActionError("errors.float", field));
                            
                        }
                    } else {
                        myForm.getLines()[f].setRate(null);
                    }
                }
                
                if (errors.isEmpty()) {
                    itemSession.setCurrencies(entityId, myForm.getLines(),
                            myForm.getDefaultCurrencyId());
                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("system.currency.updated"));
                } else {
                    // Save the error messages we need
                    request.setAttribute(Globals.ERROR_KEY, errors);
                }
            }
            
            saveMessages(request, messages);
            return mapping.findForward("edit");
        } catch (Exception e) {
            LOG.error("Exception ", e);
        }
        
        return mapping.findForward("error");
    }

}
