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
import javax.persistence.Version;

import org.hibernate.annotations.OrderBy;

import com.sapienter.jbilling.server.user.db.UserDTO;

@Entity
@TableGenerator(
        name = "report_user_GEN", 
        table = "jbilling_seqs", 
        pkColumnName = "name", 
        valueColumnName = "next_id", 
        pkColumnValue = "report_user", 
        allocationSize = 100)
@Table(name = "report_user")
public class ReportUserDTO implements Serializable {

    private int id;
    private UserDTO baseUser;
    private ReportDTO report;
    private Date createDatetime;
    private String title;
    private Set<ReportFieldDTO> fields = new HashSet<ReportFieldDTO>(0);
    private int versionNum;

    public ReportUserDTO() {
    }

    public ReportUserDTO(int id, Date createDatetime) {
        this.id = id;
        this.createDatetime = createDatetime;
    }

    public ReportUserDTO(int id, UserDTO baseUser, ReportDTO report,
            Date createDatetime, String title) {
        this.id = id;
        this.baseUser = baseUser;
        this.report = report;
        this.createDatetime = createDatetime;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "report_user_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserDTO getBaseUser() {
        return this.baseUser;
    }

    public void setBaseUser(UserDTO baseUser) {
        this.baseUser = baseUser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    public ReportDTO getReport() {
        return this.report;
    }

    public void setReport(ReportDTO report) {
        this.report = report;
    }

    @Column(name = "create_datetime", nullable = false, length = 29)
    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Column(name = "title", length = 50)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reportUser")
    @OrderBy( clause="id")
    public Set<ReportFieldDTO> getFields() {
        return fields;
    }

    public void setFields(Set<ReportFieldDTO> fields) {
        this.fields = fields;
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
