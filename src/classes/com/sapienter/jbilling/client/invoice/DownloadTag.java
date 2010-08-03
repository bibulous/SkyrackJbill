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
 * Created on 4-Jun-2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.invoice;

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
import com.sapienter.jbilling.server.invoice.IInvoiceSessionBean;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.util.Context;

/**
 * Calls the report session bean to get the specified report DTO
 * 
 * @author emilc
 *
 * @jsp:tag name="invoiceDownload"
 *          body-content="empty"
 */
public class DownloadTag extends TagSupport {
    
    private String name = null;

    public int doStartTag() throws JspException {
        int retValue = SKIP_BODY;
        
        Logger log = Logger.getLogger(DownloadTag.class);
        ActionErrors errors = new ActionErrors();
        
        log.debug("Running download:");
        
        HttpSession session = pageContext.getSession();
        InvoiceDTO invoice = (InvoiceDTO) session.getAttribute(
                Constants.SESSION_INVOICE_DTO);
        try {
            IInvoiceSessionBean invoiceSession = (IInvoiceSessionBean) 
                    Context.getBean(Context.Name.INVOICE_SESSION);

            HttpServletResponse response = (HttpServletResponse) 
                    pageContext.getResponse();
            
            response.setContentType("application/download");
            response.setHeader("Content-Disposition",  
                    "attachment;filename=" + invoiceSession.getFileName(
                            invoice.getId()) + ".pdf");
            // a pdf is a binary file, therefore we use an output stream
            // that won't be encoded
            ServletOutputStream out = response.getOutputStream();

            byte[] document = invoiceSession.getPDFInvoice(invoice.getId());
            for(int f = 0; f < document.length; f++) {
            	out.write(document[f]);
            }
            
            out.close();
        } catch (Exception e) {
            log.error("Exception", e);
            errors.add(ActionErrors.GLOBAL_ERROR, 
                    new ActionError("all.internal"));
            pageContext.getRequest().setAttribute(Globals.ERROR_KEY, 
                    errors);
        }
        
        return retValue;
    }    
    
}
