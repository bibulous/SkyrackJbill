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
 * Created on Jul 16, 2004
 *
 */
package com.sapienter.jbilling.client.user;

import java.io.IOException;

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
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

/**
 * @author Emil
 * This will simply update the notes filed for the customer record.
 * If the trimmed field is empty, it will send a null,
 */
public class NotesEditAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Logger log = Logger.getLogger(NotesEditAction.class);
        HttpSession session = request.getSession(false);
        String forward = "error";
        ActionErrors errors = new ActionErrors();
        String action = request.getParameter("action");
    	try {
    		UserDTOEx user = (UserDTOEx) session.getAttribute(
	        		Constants.SESSION_CUSTOMER_DTO);
    		
    		if (action.equals("edit")) {
	            IUserSessionBean userSession = (IUserSessionBean) 
                        Context.getBean(Context.Name.USER_SESSION);
	            DynaActionForm myForm = (DynaActionForm) form;
	            
	            String notes = (String) myForm.get("notes");
	            notes = notes.trim();
	            if (notes.length() > 1000) {
	            	notes.substring(0,1000);
	            } else if (notes.length() == 0) {
	            	notes = null;
	            }

	            // parse new lines
	            if (notes != null) {
	            	notes = notes.replaceAll("\r\n", "<br/>");
	            }
	            Integer userId = (Integer) session.getAttribute(
	            		Constants.SESSION_USER_ID);
	            userSession.setCustomerNotes(userId, notes);

	            // refresh the object in the session
	            user.getCustomer().setNotes(notes);

	            forward = "customer_view";
    		} else if (action.equals("setup")) {
    	        ModuleConfig moduleConfig = RequestUtils.getModuleConfig(request,
    	                servlet.getServletContext());
    	        DynaActionForm myForm = (DynaActionForm) RequestUtils.createActionForm(
    	                request, mapping, moduleConfig, servlet);
    	        
    	        String notes = user.getCustomer().getNotes();
    	        if (notes != null) {
    	        	notes = notes.replaceAll("<br/>", "\r\n");
    	        }
        		myForm.set("notes", notes);
        		session.setAttribute("notes", myForm);
        		forward = "notes_edit";
    		}
        } catch (Exception e) {
        	log.error("Exception in action", e);
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("all.internal"));
        }
        
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }
        
        return mapping.findForward(forward);
    }
}
