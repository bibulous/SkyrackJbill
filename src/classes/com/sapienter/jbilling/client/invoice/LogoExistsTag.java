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

package com.sapienter.jbilling.client.invoice;

import java.io.File;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.Util;

/**
 * Sets variable named by tag attribute 'id' to true if an invoice 
 * logo for the user's entity (company) has been uploaded.
 * 
 * @author Lucas Pickstone
 *
 * @jsp:tag name="invoiceLogoExists"
 *          body-content="empty"
 *          tei-class="com.sapienter.jbilling.client.invoice.LogoExistsTagTEI"
 */
public class LogoExistsTag extends TagSupport {
     public int doStartTag() {
         return SKIP_BODY;
     }

     public int doEndTag() throws JspException {
        Logger log = Logger.getLogger(LogoExistsTag.class);
        ActionErrors errors = new ActionErrors();
        
        log.debug("Checking whether invoice logo file exists:");
        
        HttpSession session = pageContext.getSession();

        try {

            Integer entityId = (Integer) session.getAttribute(
                    Constants.SESSION_ENTITY_ID_KEY);

            File imageFile = new File(Util.getSysProp("base_dir") + "logos"
                    + File.separator + "entity-" + entityId + ".jpg");

            pageContext.setAttribute(getId(), imageFile.exists());

        } catch (Exception e) {
            log.error("Exception", e);
            errors.add(ActionErrors.GLOBAL_ERROR, 
                    new ActionError("all.internal"));
            pageContext.getRequest().setAttribute(Globals.ERROR_KEY, 
                    errors);
        }

        return EVAL_PAGE;
    }

    /**
     * @jsp:attribute required="true"
     *                rtexprvalue="true"
     *                type="java.lang.String"
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param String
     */
    public void setId(String string) {
        id = string;
    }
}
