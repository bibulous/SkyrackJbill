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
 * Created on Nov 26, 2004
 *
 */
package com.sapienter.jbilling.server.list;

import java.util.Calendar;

import com.sapienter.jbilling.server.list.db.ListDTO;
import com.sapienter.jbilling.server.list.db.ListEntityDAS;
import com.sapienter.jbilling.server.list.db.ListEntityDTO;


/**
 * @author Emil
 *
 */
public class ListEntityBL {
    private ListEntityDAS listEntityDAS = null;
    private ListEntityDTO listEntity = null;

    public ListEntityBL(Integer listEntityId) {
        init();
        set(listEntityId);
    }

    public ListEntityBL() {
        init();
    }

    public ListEntityBL(ListEntityDTO listEntity) {
        init();
        set(listEntity);
    }

    private void init() {
        listEntityDAS = new ListEntityDAS();
    }

    public ListEntityDTO getEntity() {
        return listEntity;
    }
    
    public ListEntityDAS getHome() {
        return listEntityDAS;
    }

    public void set(Integer id) {
        listEntity = listEntityDAS.find(id);
    }
    
    public void set(Integer listId, Integer entityId) {
        listEntity = listEntityDAS.findByEntity(listId, entityId);
    }
    
    public void set(ListEntityDTO listEntity) {
        this.listEntity = listEntity;
    }
    
    public void create(ListDTO list, Integer entityId, 
            Integer count) {
        listEntity = listEntityDAS.create(list, entityId, count);
    }
    
    public void update(Integer count) {
        listEntity.setTotalRecords(count);
        listEntity.setLastUpdate(Calendar.getInstance().getTime());
    }

}