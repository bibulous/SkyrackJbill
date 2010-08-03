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

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.JNDILookup;
import com.sapienter.jbilling.server.list.db.ListFieldDAS;
import com.sapienter.jbilling.server.list.db.ListFieldDTO;

/**
 * @author Emil
 *
 */
public class ListFieldBL {
    private JNDILookup EJBFactory = null;
    private ListFieldDAS listFieldDas = null;
    private ListFieldDTO listField = null;
    private Logger log = null;

    public ListFieldBL(Integer listFieldId) 
            throws NamingException {
        init();
        set(listFieldId);
    }

    public ListFieldBL() throws NamingException {
        init();
    }

    public ListFieldBL(ListFieldDTO listField) throws NamingException {
        init();
        set(listField);
    }

    private void init() throws NamingException {
        log = Logger.getLogger(ListFieldBL.class);     
        EJBFactory = JNDILookup.getFactory(false);
        listFieldDas = new ListFieldDAS();
    }

    public ListFieldDTO getEntity() {
        return listField;
    }
    
    public ListFieldDAS getHome() {
        return listFieldDas;
    }

    public void set(Integer id) {
        listField = listFieldDas.find(id);
    }
    
    public void set(ListFieldDTO listField) {
        this.listField = listField;
    }
}