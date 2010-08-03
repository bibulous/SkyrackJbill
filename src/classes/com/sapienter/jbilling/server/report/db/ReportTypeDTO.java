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
package com.sapienter.jbilling.server.report.db;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity
@TableGenerator(name = "report_type_GEN", 
        table = "jbilling_seqs", 
        pkColumnName = "name", 
        valueColumnName = "next_id", 
        pkColumnValue = "report_type", 
        allocationSize = 100)
@Table(name = "report_type")
public class ReportTypeDTO implements Serializable {

    private int id;
    private short showable;
    private Set<ReportDTO> reports = new HashSet<ReportDTO>(0);
    private int versionNum;

    public ReportTypeDTO() {
    }

    public ReportTypeDTO(int id, short showable) {
        this.id = id;
        this.showable = showable;
    }

    public ReportTypeDTO(int id, short showable, Set<ReportDTO> reports) {
        this.id = id;
        this.showable = showable;
        this.reports = reports;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "report_type_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "showable", nullable = false)
    public short getShowable() {
        return this.showable;
    }

    public void setShowable(short showable) {
        this.showable = showable;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "report_type_map", joinColumns = { @JoinColumn(name = "type_id", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "report_id", updatable = false) })
    public Set<ReportDTO> getReports() {
        return this.reports;
    }

    public void setReports(Set<ReportDTO> reports) {
        this.reports = reports;
    }

    @Version
    @Column(name = "OPTLOCK")
    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

}
