/*
    jBilling - The Enterprise Open Source Billing System
    Copyright (C) 2003-2009 Enterprise jBilling Software Ltd. and Emiliano Conde

    This file is part of jBilling.

    jBilling is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jBilling is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with jBilling.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.sapienter.jbilling.server.list.db;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class ListFieldEntityDAS extends AbstractDAS<ListFieldEntityDTO> {

    public ListFieldEntityDTO findByFieldEntity(Integer fieldId,
            Integer listEntityId) {

        Criteria criteria = getSession().createCriteria(
                ListFieldEntityDTO.class);
        criteria.createAlias("listField", "list").add(
                Restrictions.eq("list.id", fieldId.intValue()));
        criteria.createAlias("listEntity", "entity").add(
                Restrictions.eq("entity.id", listEntityId.intValue()));

        return (ListFieldEntityDTO) criteria.uniqueResult();
    }

    public ListFieldEntityDTO create(Integer entity, Integer field) {
        ListEntityDTO listEntity = new ListEntityDAS().find(entity);
        ListFieldDTO listField = new ListFieldDAS().find(field);

        ListFieldEntityDTO newListField = new ListFieldEntityDTO();
        newListField.setListEntity(listEntity);
        newListField.setListField(listField);

        return save(newListField);

    }

    public ListFieldEntityDTO create(ListEntityDTO entity, ListFieldDTO field) {

        ListFieldEntityDTO newListField = new ListFieldEntityDTO();
        newListField.setListEntity(entity);
        newListField.setListField(field);

        return save(newListField);
    }
}
