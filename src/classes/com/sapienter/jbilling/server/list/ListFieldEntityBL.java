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
 * Created on Nov 26, 2004
 *
 */
package com.sapienter.jbilling.server.list;

import java.util.Date;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.db.ListEntityDTO;
import com.sapienter.jbilling.server.list.db.ListFieldDTO;
import com.sapienter.jbilling.server.list.db.ListFieldEntityDAS;
import com.sapienter.jbilling.server.list.db.ListFieldEntityDTO;

/**
 * @author Emil
 * 
 */
public class ListFieldEntityBL {
	private ListFieldEntityDAS fieldEntityDas = null;
	private ListFieldEntityDTO fieldEntity = null;
	private Logger log = null;

	public ListFieldEntityBL(Integer fieldEntityId) throws NamingException {
		init();
		set(fieldEntityId);
	}

	public ListFieldEntityBL() throws NamingException {
		init();
	}

	public ListFieldEntityBL(ListFieldEntityDTO fieldEntity) {
		init();
		set(fieldEntity);
	}

	private void init() {
		log = Logger.getLogger(ListFieldEntityBL.class);
		fieldEntityDas = new ListFieldEntityDAS();
	}

	public ListFieldEntityDTO getEntity() {
		return fieldEntity;
	}

	public ListFieldEntityDAS getHome() {
		return fieldEntityDas;
	}

	public void set(Integer id) {
		fieldEntity = fieldEntityDas.find(id);
	}

	public void set(ListFieldEntityDTO fieldEntity) {
		this.fieldEntity = fieldEntity;
	}

	public void set(Integer fieldId, Integer listEntityId) {
		fieldEntity = fieldEntityDas.findByFieldEntity(fieldId, listEntityId);
	}

	public void createUpdate(ListFieldDTO field, ListEntityDTO entity,
			Integer min, Integer max, String minStr, String maxStr,
			Date minDate, Date maxDate) {
		fieldEntity = fieldEntityDas.findByFieldEntity(field.getId(), entity
				.getId());
		if (fieldEntity == null) {
			fieldEntity = fieldEntityDas.create(entity, field);
		}

		fieldEntity.setMaxValue(max);
		fieldEntity.setMinValue(min);
		fieldEntity.setMaxStrValue(maxStr);
		fieldEntity.setMinStrValue(minStr);
		fieldEntity.setMinDateValue(minDate);
		fieldEntity.setMaxDateValue(maxDate);
	}
}
