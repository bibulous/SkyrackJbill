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
import java.util.Date;
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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.sapienter.jbilling.server.user.db.CompanyDTO;

@Entity
@TableGenerator(
        name = "list_entity_GEN", 
        table = "jbilling_seqs", 
        pkColumnName = "name", 
        valueColumnName = "next_id", 
        pkColumnValue = "list_entity", 
        allocationSize = 100)
@Table(name = "list_entity", uniqueConstraints = @UniqueConstraint(columnNames = {
        "list_id", "entity_id" }))
public class ListEntityDTO implements Serializable {

    private int id;
    private ListDTO list;
    private CompanyDTO entity;
    private int totalRecords;
    private Date lastUpdate;
    private Set<ListFieldEntityDTO> listFieldEntities = new HashSet<ListFieldEntityDTO>(
            0);
    private int versionNum;

    public ListEntityDTO() {
    }

    public ListEntityDTO(int id, CompanyDTO entity, int totalRecords,
            Date lastUpdate) {
        this.id = id;
        this.entity = entity;
        this.totalRecords = totalRecords;
        this.lastUpdate = lastUpdate;
    }

    public ListEntityDTO(int id, ListDTO list, CompanyDTO entity,
            int totalRecords, Date lastUpdate,
            Set<ListFieldEntityDTO> listFieldEntities) {
        this.id = id;
        this.list = list;
        this.entity = entity;
        this.totalRecords = totalRecords;
        this.lastUpdate = lastUpdate;
        this.listFieldEntities = listFieldEntities;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "list_entity_GEN")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    public CompanyDTO getEntity() {
        return this.entity;
    }

    public void setEntity(CompanyDTO entity) {
        this.entity = entity;
    }

    @Column(name = "total_records", nullable = false)
    public int getTotalRecords() {
        return this.totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    @Column(name = "last_update", nullable = false, length = 13)
    public Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "listEntity")
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
}
