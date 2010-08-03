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
package com.sapienter.jbilling.server.order.db;


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
@Table(name="order_line_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class OrderLineTypeDTO  implements java.io.Serializable {


     private int id;
     private Integer editable;
     private Set<OrderLineDTO> orderLineDTOs = new HashSet<OrderLineDTO>(0);

    public OrderLineTypeDTO() {
    }

	
    public OrderLineTypeDTO(int id, Integer editable) {
        this.id = id;
        this.editable = editable;
    }
    public OrderLineTypeDTO(int id, Integer editable, Set<OrderLineDTO> orderLineDTOs) {
       this.id = id;
       this.editable = editable;
       this.orderLineDTOs = orderLineDTOs;
    }
   
    @Id 
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="editable", nullable=false)
    public Integer getEditable() {
        return this.editable;
    }
    
    public void setEditable(Integer editable) {
        this.editable = editable;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="orderLineType")
    public Set<OrderLineDTO> getOrderLines() {
        return this.orderLineDTOs;
    }
    
    public void setOrderLines(Set<OrderLineDTO> orderLineDTOs) {
        this.orderLineDTOs = orderLineDTOs;
    }




}


