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
package com.sapienter.jbilling.server.process.db;

import java.util.Calendar;
import java.util.Date;

import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class ProcessRunDAS extends AbstractDAS<ProcessRunDTO> {

    //private static final Logger LOG = Logger.getLogger(ProcessRunDAS.class);

    public ProcessRunDTO create(BillingProcessDTO process, Date runDate, Integer invoicesGenerated) {
        ProcessRunDTO dto = new ProcessRunDTO(0, runDate, Calendar.getInstance().getTime());
        dto.setBillingProcess(process);
        dto.setInvoicesGenerated(invoicesGenerated);

        dto = save(dto);
        process.getProcessRuns().add(dto);
        return dto;
    }

}
