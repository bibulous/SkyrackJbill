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
import java.util.HashMap;

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
import com.sapienter.jbilling.server.process.AgeingDTOEx;
import com.sapienter.jbilling.server.process.IBillingProcessSessionBean;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class AgeingMaintainAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        Logger log = Logger.getLogger(AgeingMaintainAction.class);
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        
        try {
            IBillingProcessSessionBean processSession = 
                    (IBillingProcessSessionBean) Context.getBean(
                    Context.Name.BILLING_PROCESS_SESSION);
            IUserSessionBean userSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
            
            String action = request.getParameter("action");
            HttpSession session = request.getSession(false);
            Integer entityId = (Integer) session.getAttribute(
                    Constants.SESSION_ENTITY_ID_KEY);
            Integer languageId = (Integer) session.getAttribute(
                    Constants.SESSION_LANGUAGE);
            Integer executorLanguageId = languageId;
            AgeingArrayForm myForm = (AgeingArrayForm) form;
            if (action.equals("setup")) {
                myForm.setLines(processSession.getAgeingSteps(
                        entityId, executorLanguageId, languageId));
                // the grace period & url are preferences
                Integer[] preferenceIds = new Integer[2];
                preferenceIds[0] = Constants.PREFERENCE_GRACE_PERIOD;
                preferenceIds[1] = Constants.PREFERENCE_URL_CALLBACK;
                HashMap result = ((IUserSessionBean) userSession).
                        getEntityParameters(entityId, preferenceIds);
            
                String gracePeriod = (String) result.get(
                       Constants.PREFERENCE_GRACE_PERIOD); 
                myForm.setGracePeriod(gracePeriod);
                String url = (String) result.get(
                        Constants.PREFERENCE_URL_CALLBACK);
                myForm.setUrlCallback(url);
                // default the language to the user's language
                myForm.setLanguageId(languageId);
            } else if (action.equals("edit")) {
                languageId = myForm.getLanguageId();
                if (request.getParameter("reload") != null) {
                    // it is just a change of language
                    myForm.setLines(processSession.getAgeingSteps(
                            entityId, executorLanguageId, languageId));
                } else {
                    
                    if (myForm.getGracePeriod() == null || 
                            myForm.getGracePeriod().length() == 0) {
                        String field = Resources.getMessage(request, 
                                "system.ageing.gracePeriod"); 
                        errors.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("errors.required", field));
                    } else {
                        try {
                            Integer.valueOf(myForm.getGracePeriod().trim());
                        } catch(NumberFormatException e) {
                            String field = Resources.getMessage(request, 
                                    "system.ageing.gracePeriod"); 
                            errors.add(ActionErrors.GLOBAL_ERROR,
                                    new ActionError("errors.integer", field));
                            
                        }
                    }
                    
                    // I need to know which one is the last selected step first
                    int lastSelected = 0;
                    for (int f = 0; f < myForm.getLines().length; f++) {
                        AgeingDTOEx line = myForm.getLines()[f];
                        if (line.getInUse().booleanValue()) {
                            lastSelected = f;
                        }
                    }
                                
                    for (int f = 0; f < myForm.getLines().length; f++) {
                        AgeingDTOEx line = myForm.getLines()[f];
                        if (line.getStatusId().equals(UserDTOEx.STATUS_ACTIVE)) {
                            line.setInUse(new Boolean(true));
                        }
                        if (line.getInUse().booleanValue()) {
                            if (!line.getStatusId().equals(UserDTOEx.STATUS_DELETED) && 
                                    (line.getWelcomeMessage() == null || 
                                    line.getWelcomeMessage().length() == 0)) {
                                String field = Resources.getMessage(request, 
                                        "system.ageing.welcomeMessage"); 
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError("errors.required.line", field,
                                                line.getStatusId()));
                            }
                            // active and deleted don't check days
                            if (line.getStatusId().equals(UserDTOEx.STATUS_ACTIVE) ||
                                    line.getStatusId().equals(UserDTOEx.STATUS_DELETED)) {
                                // nothing ... :)
                            } else if ((line.getDays() <= 0) && f != lastSelected) {
                                String field = Resources.getMessage(request, 
                                        "system.ageing.days"); 
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError("errors.required.line", field,
                                            line.getStatusId()));
                            } else if (f == lastSelected && 
                                    (line.getDays() != 0)) {
                                errors.add(ActionErrors.GLOBAL_ERROR,
                                        new ActionError("system.ageing.error.lastDay"));              
                            }
                        }                                
                    }
                    
                    if (errors.isEmpty()) {
                    	log.debug("Sending update of ageing for enitity " + 
                    			entityId);
                        processSession.setAgeingSteps(entityId, languageId, 
                                myForm.getLines());
                        // update the grace period in another call
                        userSession.setEntityParameter(entityId, 
                                Constants.PREFERENCE_GRACE_PERIOD, null,
                                Integer.valueOf(myForm.getGracePeriod()),
                                null);

                        if (myForm.getUrlCallback() == null || 
                                myForm.getUrlCallback().trim().length() == 0) {
                            myForm.setUrlCallback(null);
                        }
                        userSession.setEntityParameter(entityId, 
                                Constants.PREFERENCE_URL_CALLBACK, 
                                myForm.getUrlCallback(), null, null);

                        messages.add(ActionMessages.GLOBAL_MESSAGE, 
                                new ActionMessage("system.ageing.updated"));
                    } else {
                        // Save the error messages we need
                        request.setAttribute(Globals.ERROR_KEY, errors);
                    }
                }
            }
            
            saveMessages(request, messages);
            return mapping.findForward("edit");
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        
        return mapping.findForward("error");
    }

}
