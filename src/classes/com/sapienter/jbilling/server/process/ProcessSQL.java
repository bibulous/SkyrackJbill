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

package com.sapienter.jbilling.server.process;

public interface ProcessSQL {
    // Internal gets all the invoices ever
    static final String generalList = 
        "select id, id, billing_date " +
        "  from billing_process " +
        " where entity_id = ? " +
        "   and is_review = 0 " +
        " order by 1";

    static final String lastId =
        "select max(id) " +
        "  from billing_process" +
        " where entity_id = ?" +
        "   and is_review = 0 ";
    
    // needed to avoid getting into a trasaction in the billingProcess.trigger
    // since Collections have to be in transactions
    static String findToRetry =
        "select id " +
        " from billing_process " + 
        "where entity_id = ? " +
        "  and is_review = 0 " +
        "  and retries_to_do > 0";
    
}


