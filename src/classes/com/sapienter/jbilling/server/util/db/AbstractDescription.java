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

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.util.Context;

public abstract class AbstractDescription implements Serializable {
	
    private static final Logger LOG = Logger.getLogger(AbstractDescription.class);
	private String description = null;
    // do not add not Serializable fields here... or feel the pain.
	abstract public int getId();
	abstract protected String getTable();

    /**
     * Returns the description.
     * @return String
     */
    public String getDescription(Integer languageId) {
        return getDescription(languageId, "description");
    }
    
    public String getDescription(Integer languageId, String label) {
        if (label == null || languageId == null) {
            throw new SessionInternalError("Null parameters " + label + " " + languageId);
        }
        DescriptionDAS de = new DescriptionDAS();
        JbillingTableDAS jbDAS = (JbillingTableDAS) Context.getBean(Context.Name.JBILLING_TABLE_DAS);
        InternationalDescriptionId iid = new InternationalDescriptionId(jbDAS.findByName(
                getTable()).getId(), getId(), label, languageId);
        InternationalDescriptionDTO desc = de.findNow(iid);
        
        if (desc != null) {
            return desc.getContent();
        } else {
            LOG.debug("Description not set for " + iid);
            return null;
        }
        
    }
    /**
     * Sets the description.
     * @param description The description to set
     */
    public void setDescription(String labelProperty, Integer languageId, String content) {
        JbillingTableDAS jbDAS = (JbillingTableDAS) Context.getBean(Context.Name.JBILLING_TABLE_DAS);
        InternationalDescriptionId iid = new InternationalDescriptionId(jbDAS.findByName(
        		getTable()).getId(), getId(), labelProperty, languageId);
        InternationalDescriptionDTO desc = new InternationalDescriptionDTO(iid, content);
        
        DescriptionDAS de = new DescriptionDAS();
        de.save(desc);
    }

    public void setDescription(String content, Integer languageId) {
        setDescription("description", languageId, content);
    }
    
    public String getDescription() {
    	if (description == null) {
    		return getDescription(1);
    	} else {
    		return description;
    	}
    }
    public void setDescription(String text) {
    	description = text;
    }
}
