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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.payment.IPaymentSessionBean;
import com.sapienter.jbilling.server.payment.blacklist.CsvProcessor;
import com.sapienter.jbilling.server.util.Context;

/**
 * Handles uploaded blacklist csv files to either add to or replace
 * existing blacklist entries.
 */
public class BlacklistUploadAction extends Action {
    /* CSV file columns - see CsvProcessor
     * type, first_name, last_name, address_1, address_2, city, state_province, 
     * postal_code, country_code, phone_country_code, phone_area_code, 
     * phone_number, ip_address, cc_number, user_id
     */

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Logger LOG = Logger.getLogger(BlacklistUploadAction.class);
        HttpSession session = request.getSession(false);
        DynaActionForm blacklistUploadForm = (DynaActionForm) form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        Integer entityId = (Integer) session.getAttribute(
                Constants.SESSION_ENTITY_ID_KEY);
        String filePath = null; // csv temp file path

        try {
            FormFile csvFile = (FormFile) blacklistUploadForm.get("csvFile");
            String addOrReplace = (String) blacklistUploadForm.get("addOrReplace");
            boolean replace = false;

            // validate addOrReplace parameter
            if (addOrReplace != null) {
                if (addOrReplace.equals("replace")) {
                    replace = true;
                } else if(!addOrReplace.equals("add")) {
                    throw new ServletException("Wrong addOrReplace paramater value");
                }
            } else {
                throw new ServletException("Missing addOrReplace paramater");
            }

            if (csvFile != null) {
                // create a temp file
                filePath = processFile(csvFile, errors);

                // if there were no errors
                if (errors.isEmpty()) {
                    IPaymentSessionBean paymentSession = (IPaymentSessionBean)
                            Context.getBean(Context.Name.PAYMENT_SESSION);

                    int number = paymentSession.processCsvBlacklist(
                            filePath, replace, entityId);

                    if (replace) {
                        messages.add(ActionMessages.GLOBAL_MESSAGE, 
                                new ActionMessage("system.blacklist.done.replace",
                                number));
                    } else {
                        messages.add(ActionMessages.GLOBAL_MESSAGE, 
                                new ActionMessage("system.blacklist.done.add",
                                number));
                    }
                }
            } else {
                // most likely file is too big 
                // see '<controller maxFileSize="..." />' in struts-config.xml
                errors.add("csvFile", 
                        new ActionError("system.blacklist.error.file_size"));
            }
        } catch (CsvProcessor.ParseException pe) {
            LOG.error("CSV Parse Exception", pe);
            errors.add(ActionMessages.GLOBAL_MESSAGE, 
                    new ActionError("system.blacklist.error.parse_exception",
                    pe.getMessage()));
        } catch (Exception e) {
            LOG.error("Exception ", e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, 
                    new ActionError("all.internal"));
        } finally {
            // delete the csv temp file, if it was created
            if (filePath != null) {
                File tempFile = new File(filePath);
                tempFile.delete();
            }
        }

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }
        if (!messages.isEmpty()) {
            saveMessages(request, messages);
        }
        return mapping.findForward("done");
    }

    /**
     * Creates a temp file for the server to process. 
     * Returns the absolute path to the file.
     */
    private String processFile(FormFile csvFile, ActionErrors errors) 
            throws FileNotFoundException, IOException {
        // check a file was actually uploaded
        if (csvFile.getFileSize() == 0) {
            errors.add(ActionMessages.GLOBAL_MESSAGE, 
                    new ActionError("system.blacklist.error.empty_file"));
            return null;
        }

        File tempFile = null;
        BufferedInputStream inFile = null;
        BufferedOutputStream outFile = null;
        String line = null;

        try {
            tempFile = File.createTempFile("blacklist", ".csv");
            inFile = new BufferedInputStream(csvFile.getInputStream());
            outFile = new BufferedOutputStream(new FileOutputStream(tempFile));
            while (inFile.available() != 0) {
                outFile.write(inFile.read());
            }
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
                if (outFile != null) {
                    outFile.close();
                }
            } catch (IOException ioe) { }
        }

        return tempFile.getAbsolutePath();
    }
}
