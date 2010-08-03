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
package com.sapienter.jbilling.server.user.permisson.db;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name="permission")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PermissionDTO  implements java.io.Serializable {


     private int id;
     private PermissionTypeDTO permissionType;
     private Integer foreignId;
     private Set<PermissionUserDTO> permissionUsers = new HashSet<PermissionUserDTO>(0);
     private Set<RoleDTO> roles = new HashSet<RoleDTO>(0);

    public PermissionDTO() {
    }

    public PermissionDTO(int id) {
        this.id = id;
    }
	
    public PermissionDTO(int id, PermissionTypeDTO permissionType) {
        this.id = id;
        this.permissionType = permissionType;
    }
    public PermissionDTO(int id, PermissionTypeDTO permissionType, Integer foreignId, Set<PermissionUserDTO> permissionUsers, Set<RoleDTO> roles) {
       this.id = id;
       this.permissionType = permissionType;
       this.foreignId = foreignId;
       this.permissionUsers = permissionUsers;
       this.roles = roles;
    }
   
    @Id 
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="type_id", nullable=false)
    public PermissionTypeDTO getPermissionType() {
        return this.permissionType;
    }
    
    public void setPermissionType(PermissionTypeDTO permissionType) {
        this.permissionType = permissionType;
    }
    
    @Column(name="foreign_id")
    public Integer getForeignId() {
        return this.foreignId;
    }
    
    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="permission")
    public Set<PermissionUserDTO> getPermissionUsers() {
        return this.permissionUsers;
    }
    
    public void setPermissionUsers(Set<PermissionUserDTO> permissionUsers) {
        this.permissionUsers = permissionUsers;
    }
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="permission_role_map", joinColumns = { 
        @JoinColumn(name="permission_id", updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="role_id", updatable=false) })
    public Set<RoleDTO> getRoles() {
        return this.roles;
    }
    
    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public boolean equals(Object p) {
        if (p != null && ((PermissionDTO)p).getId() == this.id) return true;
        return false;
    }


}


