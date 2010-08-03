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

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;

/**
 * All readers should extend this class. 
 * All readers need to read a batch of records at a time
 * @author emilc
 */
public abstract class AbstractReader extends PluggableTask implements
        IMediationReader {

    private static final Logger LOG = Logger.getLogger(AbstractReader.class);
    private int batchSize;
    
    public boolean validate(List<String> messages) {
        boolean retValue = true;
        try {
            // the parameter is optional and defaults to 1000 records
            batchSize = Integer.parseInt(((String) parameters.get("batch_size") == null)
                ? "100" : (String) parameters.get("batch_size"));
            LOG.debug("Batch size for this reader is " + getBatchSize());
        } catch (NumberFormatException e) {
            retValue = false;
            messages.add(e.getMessage());
        }
        return retValue;
    }

    public abstract Iterator<List<Record>> iterator();

    public int getBatchSize() {
        return batchSize;
    }
}
