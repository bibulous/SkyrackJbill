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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.OrderBy;

import com.sapienter.jbilling.server.user.db.CompanyDTO;

@Entity
@TableGenerator(
        name = "report_GEN", 
        table = "jbilling_seqs", 
        pkColumnName = "name", 
        valueColumnName = "next_id", 
        pkColumnValue = "report", 
        allocationSize = 100)
@Table(name = "report")
public class ReportDTO implements Serializable {

    private int id;
    private String titleKey;
    private String instructionskey;
    private String tablesList;
    private String whereStr;
    private int idColumn;
    private String link;
    private Set<CompanyDTO> entities = new HashSet<CompanyDTO>(0);
    private Set<ReportFieldDTO> reportFields = new HashSet<ReportFieldDTO>(0);
    private Set<ReportTypeDTO> reportTypes = new HashSet<ReportTypeDTO>(0);
    private Set<ReportUserDTO> reportUsers = new HashSet<ReportUserDTO>(0);
    private int versionNum;

    public ReportDTO() {
    }

    public ReportDTO(int id, String tablesList, String whereStr, int idColumn) {
        this.id = id;
        this.tablesList = tablesList;
        this.whereStr = whereStr;
        this.idColumn = idColumn;
    }

    public ReportDTO(int id, String titlekey, String instructionskey,
            String tablesList, String whereStr, int idColumn, String link,
            Set<CompanyDTO> entities, Set<ReportFieldDTO> reportFields,
            Set<ReportTypeDTO> reportTypes, Set<ReportUserDTO> reportUsers) {
        this.id = id;
        this.titleKey = titlekey;
        this.instructionskey = instructionskey;
        this.tablesList = tablesList;
        this.whereStr = whereStr;
        this.idColumn = idColumn;
        this.link = link;
        this.entities = entities;
        this.reportFields = reportFields;
        this.reportTypes = reportTypes;
        this.reportUsers = reportUsers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "report_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "titlekey", length = 50)
    public String getTitleKey() {
        return this.titleKey;
    }

    public void setTitleKey(String titlekey) {
        this.titleKey = titlekey;
    }

    @Column(name = "instructionskey", length = 50)
    public String getInstructionsKey() {
        return this.instructionskey;
    }

    public void setInstructionsKey(String instructionskey) {
        this.instructionskey = instructionskey;
    }

    @Column(name = "tables_list", nullable = false, length = 1000)
    public String getTablesList() {
        return this.tablesList;
    }

    public void setTablesList(String tablesList) {
        this.tablesList = tablesList;
    }

    @Column(name = "where_str", nullable = false, length = 1000)
    public String getWhereStr() {
        return this.whereStr;
    }

    public void setWhereStr(String whereStr) {
        this.whereStr = whereStr;
    }

    @Column(name = "id_column", nullable = false)
    public int getIdColumn() {
        return this.idColumn;
    }

    public void setIdColumn(int idColumn) {
        this.idColumn = idColumn;
    }

    @Column(name = "link", length = 200)
    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "report_entity_map", joinColumns = { @JoinColumn(name = "report_id", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "entity_id", updatable = false) })
    public Set<CompanyDTO> getEntities() {
        return this.entities;
    }

    public void setEntities(Set<CompanyDTO> entities) {
        this.entities = entities;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "report")
    @OrderBy( clause="position_number, id" )
    public Set<ReportFieldDTO> getReportFields() {
        return this.reportFields;
    }

    public void setReportFields(Set<ReportFieldDTO> reportFields) {
        this.reportFields = reportFields;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "report_type_map", joinColumns = { @JoinColumn(name = "report_id", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "type_id", updatable = false) })
    public Set<ReportTypeDTO> getReportTypes() {
        return this.reportTypes;
    }

    public void setReportTypes(Set<ReportTypeDTO> reportTypes) {
        this.reportTypes = reportTypes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "report")
    @OrderBy( clause="id")
    public Set<ReportUserDTO> getReportUsers() {
        return this.reportUsers;
    }

    public void setReportUsers(Set<ReportUserDTO> reportUsers) {
        this.reportUsers = reportUsers;
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
