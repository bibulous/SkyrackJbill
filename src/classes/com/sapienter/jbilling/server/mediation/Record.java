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
package com.sapienter.jbilling.server.mediation;

import java.util.List;

import com.sapienter.jbilling.server.item.PricingField;
import java.util.ArrayList;

public class Record {
    private List<PricingField> fields = null;
    private int position;
    private StringBuffer key = null;
    
    public Record() {
        fields = new ArrayList<PricingField>();
        key = new StringBuffer(100);
    }
    
    public int getPosition() {
        return position;
    }
    
    public void addField(PricingField field, boolean isKey) {
        fields.add(field);
        if (isKey) {
        	key.append(field.getValue().toString());
        }
    }

    public void setPosition(int position) {
        this.position = position;
        for (PricingField field: fields) {
            field.setPosition(position);
        }
    }

    public List<PricingField> getFields() {
        return fields;
    }
    
    public String getKey() {
    	return key.toString();
    }
 
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        sb.append("record=position: " + position + "\n");
        sb.append("key=" + getKey() + "\n");
        for (PricingField field: fields) {
            sb.append("field: " + field + "\n");
        }
        return sb.toString();
    }
}
