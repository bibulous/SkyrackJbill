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
package com.sapienter.jbilling.server.user.contact.db;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sapienter.jbilling.server.user.db.CompanyDTO;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name="contact_type")
public class ContactTypeDTO  implements java.io.Serializable {


     private int id;
     private CompanyDTO entity;
     private Integer isPrimary;
     private Set<ContactMapDTO> contactMaps = new HashSet<ContactMapDTO>(0);

    public ContactTypeDTO() {
    }

	
    public ContactTypeDTO(int id) {
        this.id = id;
    }
    public ContactTypeDTO(int id, CompanyDTO entity, Integer isPrimary, Set<ContactMapDTO> contactMaps) {
       this.id = id;
       this.entity = entity;
       this.isPrimary = isPrimary;
       this.contactMaps = contactMaps;
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
    @JoinColumn(name="entity_id")
    public CompanyDTO getEntity() {
        return this.entity;
    }
    
    public void setEntity(CompanyDTO entity) {
        this.entity = entity;
    }
    
    @Column(name="is_primary")
    public Integer getIsPrimary() {
        return this.isPrimary;
    }
    
    public void setIsPrimary(Integer isPrimary) {
        this.isPrimary = isPrimary;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="contactType")
    public Set<ContactMapDTO> getContactMaps() {
        return this.contactMaps;
    }
    
    public void setContactMaps(Set<ContactMapDTO> contactMaps) {
        this.contactMaps = contactMaps;
    }




}


