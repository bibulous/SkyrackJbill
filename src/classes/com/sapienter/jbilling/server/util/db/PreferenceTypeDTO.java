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
package com.sapienter.jbilling.server.util.db;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="preference_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PreferenceTypeDTO  implements java.io.Serializable {

    private int id;
    private Integer intDefValue;
    private String strDefValue;
    private BigDecimal floatDefValue;
    private Set<PreferenceDTO> preferences = new HashSet<PreferenceDTO>(0);

    public PreferenceTypeDTO() {
    }

    public PreferenceTypeDTO(int id) {
        this.id = id;
    }

    public PreferenceTypeDTO(int id, Integer intDefValue, String strDefValue, BigDecimal floatDefValue,
                             Set<PreferenceDTO> preferences) {
        this.id = id;
        this.intDefValue = intDefValue;
        this.strDefValue = strDefValue;
        this.floatDefValue = floatDefValue;
        this.preferences = preferences;
    }

    @Id
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="int_def_value")
    public Integer getIntDefValue() {
        return this.intDefValue;
    }

    public void setIntDefValue(Integer intDefValue) {
        this.intDefValue = intDefValue;
    }

    @Column(name="str_def_value", length=200)
    public String getStrDefValue() {
        return this.strDefValue;
    }

    public void setStrDefValue(String strDefValue) {
        this.strDefValue = strDefValue;
    }

    @Column(name="float_def_value", precision=17, scale=17)
    public BigDecimal getFloatDefValue() {
        return this.floatDefValue;
    }

    public void setFloatDefValue(BigDecimal floatDefValue) {
        this.floatDefValue = floatDefValue;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="preferenceType")
    public Set<PreferenceDTO> getPreferences() {
        return this.preferences;
    }

    public void setPreferences(Set<PreferenceDTO> preferences) {
        this.preferences = preferences;
    }
}


