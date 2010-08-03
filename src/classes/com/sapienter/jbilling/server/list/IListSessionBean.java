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

package com.sapienter.jbilling.server.list;

import java.util.Collection;
import java.util.Hashtable;

import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;

/**
 * 
 * Generic list provider.
 * 
 * @author emilc
 **/
public interface IListSessionBean {

    public CachedRowSet getList(String type, Hashtable parameters)
            throws SessionInternalError;

    public ListDTO getDtoList(String type, Hashtable parameters)
            throws SessionInternalError;

    /**
     * This really doesn't belong to the list session bean, but it needs some
     * remote interface, so might as well put it here. All the real code it in
     * GetSelectableOptions.java
     */
    public Collection getOptions(String type, Integer languageId,
            Integer entityId, Integer executorType) throws SessionInternalError;

    /**
     * Returns a map to all the currencies with 'id' - 'symbol'. This is useful
     * as an application wide object for reference
     * 
     * @return
     */
    public CurrencyDTO[] getCurrencySymbolsMap() throws SessionInternalError;

    /*
     * The above are legacy methods. The follwing are the new list methods
     * 
     * @author Emil
     */

    public void updateStatistics() throws SessionInternalError;

    public CachedRowSet getPage(Integer start, Integer end, Integer size,
            Integer listId, Integer entityId, Boolean direction,
            Integer fieldId, Hashtable parameters) throws SessionInternalError;

    public CachedRowSet search(String start, String end, Integer fieldId,
            Integer listId, Integer entityId, Hashtable parameters)
            throws SessionInternalError;

    public PagedListDTO getPagedListDTO(Integer listId, String legacyName,
            Integer entityId, Integer userId) throws SessionInternalError;
}
