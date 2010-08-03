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
 * Created on Aug 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sapienter.jbilling.client.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.validator.Resources;

/**
 *
 * @jsp:tag name="dateFormat"
 *          body-content="JSP"
 */
public class DateFormatTag extends TagSupport {
    public String format;
    
    public int doStartTag() throws JspException {
        int retValue = EVAL_BODY_INCLUDE;
        Logger log = Logger.getLogger(DateFormatTag.class);
        
        HttpServletRequest request = (HttpServletRequest)
                pageContext.getRequest();
        // get the message key with the format
        String field = Resources.getMessage(request, "format.form.date");
        if (field == null) {
            log.error("Missing key in ApplicationResources. format.form.date " +
                    "is needed to display date fields in the right order");
            retValue = SKIP_BODY;
        } else {
            if (!field.equalsIgnoreCase(getFormat())) {
                retValue = SKIP_BODY;
            }
        }
        return retValue;
    }
    
    /**
     * @jsp:attribute required="true"
     *                rtexprvalue="true"
     *                type="java.lang.String"
     * @return
     */
    public String getFormat() {
        return format;
    }
    /**
     * @param format The format to set.
     */
    public void setFormat(String format) {
        this.format = format;
    }
}
