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

/*
 * Created on May 5, 2004
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.server.user;

import java.sql.SQLException;
import java.util.Locale;
import java.util.List;

import javax.naming.NamingException;

import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.util.db.LanguageDAS;
import com.sapienter.jbilling.server.util.db.LanguageDTO;
import java.util.ArrayList;

/**
 * @author Emil
 */
public class EntityBL extends ResultList 
        implements EntitySQL {
    private CompanyDAS das = null;
    private CompanyDTO entity = null;
    
    public EntityBL()  {
        init();
    }
    
    public EntityBL(Integer id)  {
    	init();
    	entity = das.find(id);
    }

    /*
    public EntityBL(String externalId) 
            throws FinderException, NamingException {
        init();
        entity = entityHome.findByExternalId(externalId);
    }
    */
    
    private void init() {
        das = new CompanyDAS();
    }
    
    public CompanyDTO getEntity() {
        return entity;
    }
    
    public Locale getLocale()  {
    	Locale retValue = null;
    	// get the language first
    	Integer languageId = entity.getLanguageId();
    	LanguageDTO language = new LanguageDAS().find(languageId);
    	String languageCode = language.getCode();
    	
    	// now the country
    	ContactBL contact = new ContactBL();
    	contact.setEntity(entity.getId());
    	String countryCode = contact.getEntity().getCountryCode();
    	
    	if (countryCode != null) {
    		retValue = new Locale(languageCode, countryCode);
    	} else {
    		retValue = new Locale(languageCode);
    	}

    	return retValue;
    }

    /**
     * Creates a new entity with its root user. Uses all defaults.
     * @param user
     * @param contact
     * @param languageId
     * @return
     * @throws Exception
     */
    public Integer create(UserDTOEx user, ContactDTO contact, 
            Integer languageId) 
            throws Exception {
        EntitySignup sign = new EntitySignup(user, contact, languageId);
        return new Integer(sign.process());
    }
    
    public Integer[] getAllIDs() 
            throws SQLException, NamingException {
        List list = new ArrayList();
        
        prepareStatement(EntitySQL.listAll);
        execute();
        conn.close();
        
        while (cachedResults.next()) {
            list.add(new Integer(cachedResults.getInt(1)));
        } 
        
        Integer[] retValue = new Integer[list.size()];
        list.toArray(retValue);
        return retValue;
    }
    
    public CachedRowSet getTables() 
            throws SQLException, NamingException {
        prepareStatement(EntitySQL.getTables);
        execute();
        conn.close();
        
        return cachedResults;
    }
    
    public Integer getRootUser(Integer entityId) {
        try {
            prepareStatement(EntitySQL.findRoot);
            cachedResults.setInt(1, entityId);

            execute();
            conn.close();
            
            cachedResults.next();
            return cachedResults.getInt(1);
        } catch (Exception e) {
            throw new SessionInternalError("Finding root user for entity " + 
                    entity.getId(), EntityBL.class, e);
        } 
    }
}
