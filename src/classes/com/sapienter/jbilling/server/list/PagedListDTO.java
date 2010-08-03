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
 * Created on Dec 2, 2004
 *
 */
package com.sapienter.jbilling.server.list;

import java.io.Serializable;

import com.sapienter.jbilling.server.list.db.ListFieldDTO;

/**
 * @author Emil
 * 
 */
public class PagedListDTO implements Serializable {
	private Integer count = null;
	private Integer keyFieldId = null;
	private Integer pageSize = null;
	private Integer listId = null;
	private String titleKey = null;
	// now only those searchable go here
	private ListFieldDTO fields[] = null;

	public PagedListDTO() {
	}

	public PagedListDTO(PagedListDTO another) {
		count = another.getCount();
		keyFieldId = another.getKeyFieldId();
		pageSize = another.getPageSize();
		listId = another.getListId();
		fields = another.fields;
		titleKey = another.titleKey;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getKeyFieldId() {
		return keyFieldId;
	}

	public void setKeyFieldId(Integer keyFieldId) {
		this.keyFieldId = keyFieldId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public ListFieldDTO[] getFields() {
		return fields;
	}

	public void setFields(ListFieldDTO[] fields) {
		this.fields = fields;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}
}