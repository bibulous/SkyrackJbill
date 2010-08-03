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
 * Created on Nov 18, 2004
 *
 */
package com.sapienter.jbilling.client.util;

import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sapienter.jbilling.server.user.UserDTOEx;

/**
 * @author Emil
 * 
 * @jsp:tag name="help"
 *      body-content="JSP"
 */
public class HelpTag extends TagSupport {

    private String page = null;
    private String anchor = null;
    private Boolean isMenu = null;
    
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        UserDTOEx dto = (UserDTOEx) session.getAttribute(
                Constants.SESSION_USER_DTO); 
        ResourceBundle bundle = ResourceBundle.getBundle("help", 
                dto.getLocale());
        
        StringBuffer link = new StringBuffer();
        link.append(bundle.getString("url"));
        
        // menus need special parsing
        if (isMenu != null && isMenu.booleanValue()) {
            String fields[] = page.split("\\|");
            page = fields[1].substring(fields[1].indexOf('=') + 1);
            anchor = fields[2].substring(fields[2].indexOf('=') + 1);
        }
        
        link.append(bundle.getString(page));
        
        if (anchor != null && anchor.length() > 0) {
            link.append('#');
            link.append(bundle.getString(page + '.' + anchor));
        }
        
        // render the link 
        JspWriter out = pageContext.getOut();
        try {
            out.print("<a href=\"" + link.toString() + "\" target=\"" +
                    bundle.getString("target") + "\">");
        } catch (Exception e) {
            throw new JspException(e);
        }
        
        return EVAL_BODY_INCLUDE;
    }
    
    public int doAfterBody() throws JspException {
        // close the link 
        JspWriter out = pageContext.getOut();
        try {
            out.print("</a>");
        } catch (Exception e) {
            throw new JspException(e);
        }
        
        return SKIP_BODY;
    }
    
    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.String"
     */
    public String getAnchor() {
        return anchor;
    }
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="false"
     *                type="java.lang.Boolean"
     */
    public Boolean getIsMenu() {
        return isMenu;
    }
    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }

    /**
     * @jsp:attribute required="true"
     *                rtexprvalue="true"
     *                type="java.lang.String"
     */
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
}
