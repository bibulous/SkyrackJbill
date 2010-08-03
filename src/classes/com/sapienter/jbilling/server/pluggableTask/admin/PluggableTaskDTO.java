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

package com.sapienter.jbilling.server.pluggableTask.admin;

import java.util.Collection;

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

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@TableGenerator(
        name="pluggable_task_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="pluggable_task",
        allocationSize = 10
        )
@Table(name = "pluggable_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PluggableTaskDTO implements java.io.Serializable {
    private static final Logger LOG = Logger.getLogger(PluggableTaskDTO.class);
    //  this is in synch with the DB (pluggable task type)
    public static final Integer TYPE_EMAIL = new Integer(9);
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="pluggable_task_GEN")
    private Integer id;
   
    @Column(name = "entity_id")
    private Integer entityId;
    
    @Column(name = "processing_order")
    private Integer processingOrder;
    
    @ManyToOne
    @JoinColumn(name="type_id")
    private PluggableTaskTypeDTO type;
    
    @OneToMany(mappedBy="task", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @Fetch( FetchMode.JOIN)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<PluggableTaskParameterDTO> parameters;
    
    @Version
    @Column(name="OPTLOCK")
    private Integer versionNum;
    
    public PluggableTaskDTO() {
        type = new PluggableTaskTypeDTO();
    }

 
   public String toString() {
	  StringBuffer str = new StringBuffer("{");
      str.append("-" + this.getClass().getName() + "-");

	  str.append("id=" + getId() + " " + "entityId=" + getEntityId() + " " + "processingOrder=" + 
              getProcessingOrder() + " " + "type=" + getType());
	  str.append('}');

	  return(str.toString());
   }


    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProcessingOrder() {
        return processingOrder;
    }

    public void setProcessingOrder(Integer processingOrder) {
        this.processingOrder = processingOrder;
    }

   public int hashCode(){
	  int result = 17;
      result = 37*result + ((this.id != null) ? this.id.hashCode() : 0);

      result = 37*result + ((this.entityId != null) ? this.entityId.hashCode() : 0);

      result = 37*result + ((this.processingOrder != null) ? this.processingOrder.hashCode() : 0);

	  return result;
   }


   public Collection<PluggableTaskParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(Collection<PluggableTaskParameterDTO> parameters) {
        this.parameters = parameters;
    }

    public PluggableTaskTypeDTO getType() {
        return type;
    }

    public void setType(PluggableTaskTypeDTO type) {
        this.type = type;
    }

    protected int getVersionNum() { return versionNum; }

    public void populateParamValues() {
        if (parameters != null) {
            for (PluggableTaskParameterDTO parameter : parameters) {
                parameter.populateValue();
            }
        }
    }
}
