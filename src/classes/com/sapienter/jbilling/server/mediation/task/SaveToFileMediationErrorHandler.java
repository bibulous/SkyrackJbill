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

package com.sapienter.jbilling.server.mediation.task;

import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveToFileMediationErrorHandler extends PluggableTask
        implements IMediationErrorHandler {

    private static final Logger LOG = Logger.getLogger(SaveToFileMediationErrorHandler.class);

    // plug-in parameters
    protected final static String PARAM_DIRECTORY_NAME = "directory";
    protected final static String PARAM_FILE_NAME = "file_name";
    protected final static String PARAM_ROTATE_FILE_DAILY = "rotate_file_daily";

    // default values
    protected final static String DEFAULT_DIRECTORY_NAME = "mediation" + File.separator + "errors";
    protected final static String DEFAULT_FILE_NAME = "mediation-errors";
    protected final static String DEFAULT_FILE_EXTENSION = ".csv";
    protected final static String DEFAULT_CSV_FILE_SEPARATOR = ",";


    public void process(Record record, List<String> errors, Date processingTime) throws TaskException {
        File file = getFileForDate(processingTime);
        LOG.debug("Perform saving errors to file " + file.getAbsolutePath());
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);

            List<String> columns = new ArrayList<String>();
            for (PricingField field : record.getFields()) {
                columns.add(PricingField.encode(field));
            }
            columns.add(com.sapienter.jbilling.server.util.Util.join(errors, " "));
            columns.add(new SimpleDateFormat("yyyyMMdd-HHmmss").format(processingTime));

            String line = com.sapienter.jbilling.server.util.Util.concatCsvLine(columns, DEFAULT_CSV_FILE_SEPARATOR);
            if (line != null) {
                writer.write(line + "\r\n");
            }
        } catch (IOException e) {
            LOG.error(e);
            throw new TaskException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }
    }

    protected String getDirectory() {
        return parameters.get(PARAM_DIRECTORY_NAME) == null
                ? Util.getSysProp("base_dir") + DEFAULT_DIRECTORY_NAME
                : (String) parameters.get(PARAM_DIRECTORY_NAME);
    }

    protected String getFileName(Date date) {
        String fileName = parameters.get(PARAM_FILE_NAME) == null
                ? DEFAULT_FILE_NAME
                : (String) parameters.get(PARAM_FILE_NAME);
        String suffix = parameters.get(PARAM_ROTATE_FILE_DAILY) == null
                || Boolean.valueOf((String) parameters.get(PARAM_ROTATE_FILE_DAILY)).equals(Boolean.FALSE)
                ? "" : "_" + new SimpleDateFormat("yyyyMMdd").format(date);
        return fileName + suffix + DEFAULT_FILE_EXTENSION;
    }

    protected File getFileForDate(Date date) {
        return new File(getDirectory() + File.separator + getFileName(date));
    }


}
