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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.Util;

/**
 * Saves uploaded logo image file for the user's entity (company).
 * 
 * @author Lucas Pickstone
 */
public class LogoUploadAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Logger log = Logger.getLogger(LogoUploadAction.class);
        HttpSession session = request.getSession(false);
        DynaActionForm logoUploadForm = (DynaActionForm) form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        Integer entityId = (Integer) session.getAttribute(
                Constants.SESSION_ENTITY_ID_KEY);

        try {
            FormFile logoFile = (FormFile) logoUploadForm.get("logoFile");

            // if image file was received and less than 250KB
            if (logoFile != null && logoFile.getFileSize() < 256000) {
                // and is a JPEG image file
                // (mime types: firefox -> image/jpeg, IE -> image/pjpeg)
                if (logoFile.getContentType().equals("image/jpeg")
                        || logoFile.getContentType().equals("image/pjpeg")) {
                    // get file as a stream and write it into a file
                    InputStream inStream = logoFile.getInputStream();
                    File imageFile = new File(Util.getSysProp("base_dir")
                            + "logos" + File.separator + "entity-" + entityId
                            + ".jpg");
                    FileOutputStream outFile = new FileOutputStream(imageFile);

                    while (inStream.available() != 0) {
                        outFile.write(inStream.read());
                    }

                    outFile.close();

                    messages.add(ActionMessages.GLOBAL_MESSAGE, 
                            new ActionMessage("invoice.logo.done"));
                    saveMessages(request, messages);

                    return mapping.findForward("done");
                } else {
                    errors.add("logoFile", 
                            new ActionError("invoice.logo.fileType"));
                }
            } else {
                errors.add("logoFile", 
                        new ActionError("invoice.logo.fileSize"));
            }
        } catch (Exception e) {
            log.error("Exception ", e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, 
                    new ActionError("invoice.logo.error"));
        }

        saveErrors(request, errors);
        return mapping.findForward("done");
    }
}
