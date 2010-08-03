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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity
@TableGenerator(
        name = "list_field_entity_GEN", 
        table = "jbilling_seqs", 
        pkColumnName = "name", 
        valueColumnName = "next_id", 
        pkColumnValue = "list_field_entity", 
        allocationSize = 100)
@Table(name = "list_field_entity")
public class ListFieldEntityDTO implements Serializable {

    private int id;
    private ListEntityDTO listEntity;
    private ListFieldDTO listField;
    private Integer minValue;
    private Integer maxValue;
    private String minStrValue;
    private String maxStrValue;
    private Date minDateValue;
    private Date maxDateValue;
    private int versionNum;

    public ListFieldEntityDTO() {
    }

    public ListFieldEntityDTO(int id) {
        this.id = id;
    }

    public ListFieldEntityDTO(int id, ListEntityDTO listEntity,
            ListFieldDTO listField, Integer minValue, Integer maxValue,
            String minStrValue, String maxStrValue, Date minDateValue,
            Date maxDateValue) {
        this.id = id;
        this.listEntity = listEntity;
        this.listField = listField;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minStrValue = minStrValue;
        this.maxStrValue = maxStrValue;
        this.minDateValue = minDateValue;
        this.maxDateValue = maxDateValue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "list_field_entity_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_entity_id")
    public ListEntityDTO getListEntity() {
        return this.listEntity;
    }

    public void setListEntity(ListEntityDTO listEntity) {
        this.listEntity = listEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_field_id")
    public ListFieldDTO getListField() {
        return this.listField;
    }

    public void setListField(ListFieldDTO listField) {
        this.listField = listField;
    }

    @Column(name = "min_value")
    public Integer getMinValue() {
        return this.minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    @Column(name = "max_value")
    public Integer getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    @Column(name = "min_str_value", length = 100)
    public String getMinStrValue() {
        return this.minStrValue;
    }

    public void setMinStrValue(String minStrValue) {
        this.minStrValue = minStrValue;
    }

    @Column(name = "max_str_value", length = 100)
    public String getMaxStrValue() {
        return this.maxStrValue;
    }

    public void setMaxStrValue(String maxStrValue) {
        this.maxStrValue = maxStrValue;
    }

    @Column(name = "min_date_value", length = 29)
    public Date getMinDateValue() {
        return this.minDateValue;
    }

    public void setMinDateValue(Date minDateValue) {
        this.minDateValue = minDateValue;
    }

    @Column(name = "max_date_value", length = 29)
    public Date getMaxDateValue() {
        return this.maxDateValue;
    }

    public void setMaxDateValue(Date maxDateValue) {
        this.maxDateValue = maxDateValue;
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
