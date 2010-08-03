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

import com.sapienter.jbilling.server.util.Constants;
import org.apache.log4j.Logger;
import org.hibernate.Query;

import com.sapienter.jbilling.server.util.db.AbstractDAS;
import java.util.List;

public class OrderLineDAS extends AbstractDAS<OrderLineDTO> {
    private static final Logger LOG = Logger.getLogger(OrderLineDAS.class);

    public Long findLinesWithDecimals(Integer itemId) {

        final String hql =
            "select count(*)" + 
            "  from OrderLineDTO ol " +
            " where ol.deleted = 0 " + 
            "   and ol.item.id= :item and (ol.quantity - cast(ol.quantity as integer)) <> 0";

        Query query = getSession().createQuery(hql);
        query.setParameter("item", itemId);

        return (Long) query.uniqueResult();
    }

    public List<OrderLineDTO> findByUserItem(Integer userId, Integer itemId) {
        final String hql =
            "select ol" +
            "  from OrderLineDTO ol " +
            " where ol.deleted = 0 " +
            "   and ol.item.id = :item " +
            "   and ol.purchaseOrder.baseUserByUserId.id = :user";

        Query query = getSession().createQuery(hql);
        query.setParameter("item", itemId);
        query.setParameter("user", userId);

        return query.list();
    }

    public OrderLineDTO findRecurringByUserItem(Integer userId, Integer itemId) {
        final String hql =
                "select line "
                + "  from OrderLineDTO line "
                + "where line.deleted = 0 "
                + "  and line.item.id = :itemId "
                + "  and line.purchaseOrder.baseUserByUserId.id = :userId "
                + "  and line.purchaseOrder.orderPeriod.id != :period "
                + "  and line.purchaseOrder.orderStatus.id = :status "
                + "  and line.purchaseOrder.deleted = 0 ";

        Query query = getSession().createQuery(hql);
        query.setParameter("itemId", itemId);
        query.setParameter("userId", userId);
        query.setParameter("period", Constants.ORDER_PERIOD_ONCE);
        query.setParameter("status", Constants.ORDER_STATUS_ACTIVE);

        return (OrderLineDTO) query.uniqueResult();
    }
}
