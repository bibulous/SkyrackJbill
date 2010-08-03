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
 * Created on 27-Feb-2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.server.report;

/**
 * @author Emil
 */
public interface ReportSQL {
	static final String list = 
		"select a.id, a.titleKey " +
		"  from report a, report_entity_map b, report_type rt, report_type_map rtm " +
		" where b.entity_id = ? " +
        "   and a.id = rtm.report_id " +
        "   and rt.id = rtm.type_id " +
        "   and rt.showable = 1 " +
        "   and a.id = b.report_id";
}
