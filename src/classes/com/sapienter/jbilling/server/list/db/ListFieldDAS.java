/*
    jBilling - The Enterprise Open Source Billing System
    Copyright (C) 2003-2009 Enterprise jBilling Software Ltd. and Emiliano Conde

    This file is part of jBilling.

    jBilling is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jBilling is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with jBilling.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.sapienter.jbilling.server.list.db;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class ListFieldDAS extends AbstractDAS<ListFieldDTO> {
	
	public static Collection<ListFieldDTO> orderFields(Collection<ListFieldDTO> fields) {
		LinkedList<ListFieldDTO> orderFields = new LinkedList<ListFieldDTO>(fields);
		Collections.sort(orderFields);
		return orderFields;
	}

}
