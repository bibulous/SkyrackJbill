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

package com.sapienter.jbilling.server.item;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Comparator;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.list.ListDTO;
import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

/**
 * @author Emil
 */
public final class ItemListBL extends ResultList 
        implements ItemSQL, Serializable { 
	
    private static final Logger LOG = Logger.getLogger(ItemListBL.class);


	public CachedRowSet getList(Integer entityID) 
            throws SQLException, Exception{
	    prepareStatement(ItemSQL.list);
	    cachedResults.setInt(1,entityID.intValue());
        // the language is now always the entity's
        // cachedResults.setInt(2,languageId.intValue());
	    
	    execute();
	    conn.close();
	    return cachedResults;
	}

    public CachedRowSet getTypeList(Integer entityID) 
            throws SQLException, Exception{
        prepareStatement(ItemSQL.listType);
        cachedResults.setInt(1, entityID.intValue());
        // cachedResults.setInt(2,languageId.intValue());
        execute();
        conn.close();
        return cachedResults;
    }

    public CachedRowSet getUserPriceList(Integer entityID, Integer userId,
            Integer languageId) throws SQLException, Exception{
        prepareStatement(ItemSQL.listUserPrice);
        cachedResults.setInt(1, entityID.intValue());
        cachedResults.setInt(2, userId.intValue());
        cachedResults.setInt(3, languageId.intValue());
        execute();
        conn.close();
        return cachedResults;
    }
    
    public CachedRowSet getPromotionList(Integer entityID, Integer languageId) 
            throws SQLException, Exception{
        prepareStatement(ItemSQL.listPromotion);
        cachedResults.setInt(1,entityID.intValue());
        cachedResults.setInt(2,languageId.intValue());
        
        execute();
        conn.close();
        return cachedResults;
    }

    /*
     * This is the list of items shown when an order is being created
     */
    public ListDTO getOrderList(Integer entityID, Integer languageId, 
            Integer userId) 
            throws NamingException, SessionInternalError {
        ListDTO result = new ListDTO();

        LOG.debug("Runing item list for a new order");
        CompanyDTO entity = new CompanyDAS().find(entityID);
        // I wish I could exclude the deleted right here
        int fields = 4;
        
        result.setTypes(new Integer[fields]);
        result.getTypes()[0] = new Integer(Types.VARCHAR);
        result.getTypes()[1] = new Integer(Types.VARCHAR);
        result.getTypes()[2] = new Integer(Types.VARCHAR);
        result.getTypes()[3] = new Integer(Types.FLOAT);
        
        for (ItemDTO item: entity.getItems()) {
            if (item.getDeleted()  == 1) {
                continue;
            }
            ItemBL itemBL = new ItemBL(item.getId());
            Object columns[] = new Object[fields];
            columns[0] = String.valueOf(item.getId());
            columns[1] = (item.getDescription(languageId) == null) ? "" : item.getDescription(languageId);
            if (item.getPercentage() != null) {
                columns[2] = "%";
                columns[3] = item.getPercentage().floatValue();
            } else {
                columns[3] = itemBL.getPrice(userId, entityID);
                columns[2] = itemBL.getPriceCurrencySymbol(); 
            }
            result.getLines().add(columns);
        }
        
        // sort the result by the second column - item description
        // These lines look kinda tricky. This is an 'anonymous class'
        // I prefer this than creating a new comparator class that will
        // be used only once
       Collections.sort(result.getLines(), new Comparator() {
           public int compare(Object o1, Object o2) {
               return ((String)(((Object []) o1)[1])).compareTo(
                       (String)(((Object []) o2)[1]));
           }
       });
        
        return result;
    }
    
}

