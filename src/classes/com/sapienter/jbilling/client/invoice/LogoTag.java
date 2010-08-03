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
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
 * Retrieves the invoice logo for the user's entity (company).
 * 
 * @author Lucas Pickstone
 *
 * @jsp:tag name="invoiceLogo"
 *          body-content="empty"
 */
public class LogoTag extends TagSupport {
     public int doStartTag() throws JspException {
        Logger log = Logger.getLogger(LogoTag.class);
        ActionErrors errors = new ActionErrors();
        
        log.debug("Running invoice logo file download:");
        
        HttpSession session = pageContext.getSession();

        try {

            HttpServletResponse response = (HttpServletResponse) 
                    pageContext.getResponse();
            
            // image is a binary file, therefore we use an output stream
            // that won't be encoded
            ServletOutputStream out = response.getOutputStream();

            response.setContentType("image/jpeg");

            Integer entityId = (Integer) session.getAttribute(
                    Constants.SESSION_ENTITY_ID_KEY);

            File imageFile = new File(Util.getSysProp("base_dir") + "logos"
                    + File.separator + "entity-" + entityId + ".jpg");
            FileInputStream imageInput = new FileInputStream(imageFile);

            while (imageInput.available() != 0) {
                out.write(imageInput.read());
            }

            imageInput.close();
            out.close();
        } catch (Exception e) {
            log.error("Exception", e);
            errors.add(ActionErrors.GLOBAL_ERROR, 
                    new ActionError("all.internal"));
            pageContext.getRequest().setAttribute(Globals.ERROR_KEY, 
                    errors);
        }

        return SKIP_BODY;
    }
}
