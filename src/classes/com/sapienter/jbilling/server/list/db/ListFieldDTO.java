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
package com.sapienter.jbilling.server.list.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity
@TableGenerator(
        name = "list_field_GEN", 
        table = "jbilling_seqs", 
        pkColumnName = "name", 
        valueColumnName = "next_id", 
        pkColumnValue = "list_field", 
        allocationSize = 100)
@Table(name = "list_field")
public class ListFieldDTO implements Serializable, Comparable<ListFieldDTO> {

    private int id;
    private ListDTO list;
    private String titleKey;
    private String columnName;
    private Integer ordenable;
    private short searchable;
    private String dataType;
    private Set<ListFieldEntityDTO> listFieldEntities = new HashSet<ListFieldEntityDTO>(
            0);
    private int versionNum;

    public ListFieldDTO() {
    }

    public ListFieldDTO(int id, String titleKey, String columnName,
            Integer ordenable, short searchable, String dataType) {
        this.id = id;
        this.titleKey = titleKey;
        this.columnName = columnName;
        this.ordenable = ordenable;
        this.searchable = searchable;
        this.dataType = dataType;
    }

    public ListFieldDTO(int id, ListDTO list, String titleKey,
            String columnName, Integer ordenable, short searchable,
            String dataType, Set<ListFieldEntityDTO> listFieldEntities) {
        this.id = id;
        this.list = list;
        this.titleKey = titleKey;
        this.columnName = columnName;
        this.ordenable = ordenable;
        this.searchable = searchable;
        this.dataType = dataType;
        this.listFieldEntities = listFieldEntities;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "list_field_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    public ListDTO getList() {
        return this.list;
    }

    public void setList(ListDTO list) {
        this.list = list;
    }

    @Column(name = "title_key", nullable = false, length = 100)
    public String getTitleKey() {
        return this.titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    @Column(name = "column_name", nullable = false, length = 50)
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Column(name = "ordenable", nullable = false)
    public Integer getOrdenable() {
        return this.ordenable;
    }

    public void setOrdenable(Integer ordenable) {
        this.ordenable = ordenable;
    }

    @Column(name = "searchable", nullable = false)
    public short getSearchable() {
        return this.searchable;
    }

    public void setSearchable(short searchable) {
        this.searchable = searchable;
    }

    @Column(name = "data_type", nullable = false, length = 10)
    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "listField")
    public Set<ListFieldEntityDTO> getListFieldEntities() {
        return this.listFieldEntities;
    }

    public void setListFieldEntities(Set<ListFieldEntityDTO> listFieldEntities) {
        this.listFieldEntities = listFieldEntities;
    }

    @Version
    @Column(name="OPTLOCK")
    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

	@Override
	public int compareTo(ListFieldDTO field) {
		return new Integer(this.id).compareTo(new Integer(field.getId()));
	}
}
