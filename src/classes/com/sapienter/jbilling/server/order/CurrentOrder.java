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

package com.sapienter.jbilling.server.order;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.user.EntityBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.MapPeriodToCalendar;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import java.util.List;
import org.springmodules.cache.CachingModel;
import org.springmodules.cache.FlushingModel;
import org.springmodules.cache.provider.CacheProviderFacade;

public class CurrentOrder {
    private final Date eventDate;
    private final Integer userId;
    private final UserBL user;
    private static final Logger LOG = Logger.getLogger(CurrentOrder.class);
    private OrderBL order = null;
    private final EventLogger eLogger = EventLogger.getInstance();

    // cache management
    private CacheProviderFacade cache;
    private CachingModel cacheModel;
    private FlushingModel flushModel;

    protected CurrentOrder(Integer userId, Date eventDate) {
        LOG.debug("Current order constructed with user " + userId + " event date " +
                eventDate);
        if (userId == null || eventDate == null) {
            throw new IllegalArgumentException("user and date are mandatory for current " +
                    "orders [" + userId + '-' + eventDate + ']');
        }
        this.userId = userId;
        this.eventDate = eventDate;
        user = new UserBL(userId);

        cache = (CacheProviderFacade) Context.getBean(Context.Name.CACHE);
        cacheModel = (CachingModel) Context.getBean(
                Context.Name.CACHE_MODEL_RW);
        flushModel = (FlushingModel) Context.getBean(
                Context.Name.CACHE_FLUSH_MODEL_RW);
    }
    
    /**
     * Returns the ID of a one-time order, where to add an event.
     * Returns null if no applicable order
     * @return
     */
    public Integer getCurrent() {

        // find in the cache
        Integer retValue = (Integer) cache.getFromCache(userId.toString() + Util.truncateDate(eventDate) ,
                cacheModel);

        if (retValue != null) {
            LOG.debug("cache hit for " + retValue);
            return retValue;
        }

        Integer subscriptionId = user.getEntity().getCustomer().getCurrentOrderId();
        Integer entityId = null;
        Integer currencyId = null;
        if (subscriptionId == null) {
            return null;
        }
        try {
            order = new OrderBL(subscriptionId);
            entityId = order.getEntity().getBaseUserByUserId().getCompany().getId();
            currencyId = order.getEntity().getCurrencyId();
        } catch (Exception e) {
            throw new SessionInternalError("Error looking for main subscription order", 
                    CurrentOrder.class, e);
        }
        
        int futurePeriods = 0;
        boolean orderFound = false;
        do {
            LOG.debug("Calculating one timer date. Future periods " + futurePeriods);
            final Date newOrderDate = calculateDate(futurePeriods);
            if (newOrderDate == null) {
                // this is an error, there isn't a good date give the event date and
                // the main subscription order
                return null;
            }
            // now that the date is set, let's see if there is a one-time order for that date
            boolean somePresent = false;
            try {
                List<OrderDTO> rows = new OrderDAS().findOneTimersByDate(userId,
                        newOrderDate);
                for (OrderDTO oneTime : rows) {
                    somePresent = true;
                    order.set(oneTime.getId());
                    if (order.getEntity().getStatusId().equals(
                            Constants.ORDER_STATUS_FINISHED)) {
                        LOG.debug("Found one timer " + oneTime.getId()
                                + " but status is finished");
                    } else {
                        orderFound = true;
                        LOG.debug("Found existing one-time order");
                        break;
                    }
                }
            } catch (Exception e) {
                throw new SessionInternalError(
                        "Error looking for one time orders",
                        CurrentOrder.class, e);
            }
            if (somePresent && !orderFound) {
                eLogger.auditBySystem(entityId, userId, Constants.TABLE_PUCHASE_ORDER, order.getEntity().getId(), 
                        EventLogger.MODULE_MEDIATION, EventLogger.CURRENT_ORDER_FINISHED, 
                        subscriptionId, null, null);
            } else if (!somePresent) {
                // there aren't any one-time orders for this date at all, create one
                create(newOrderDate, currencyId, entityId);
                
                orderFound = true;
                LOG.debug("Created new one-time order");
            }
            // non present -> create new one with correct date
            // some present & none found -> try next date
            // some present & found -> use the found one
            futurePeriods++;
        } while (!orderFound);  
        
        // the result is in 'order'
        retValue = order.getEntity().getId();

        cache.putInCache(userId.toString() + Util.truncateDate(eventDate), cacheModel,
                retValue);
        LOG.debug("Returning " + retValue);
        return retValue;
    }
    
    /**
     * Assumes that the order has been set with the main subscription order
     * @return
     */
    private Date calculateDate(int futurePeriods) {
        GregorianCalendar cal = new GregorianCalendar();
        // start from the active since if it is there, otherwise the create time
        final Date startingTime = order.getEntity().getActiveSince() == null ? order
                .getEntity().getCreateDate() : order.getEntity()
                .getActiveSince();
                
        // calculate the event date with the added future periods
        Date actualEventDate = eventDate;
        cal.setTime(actualEventDate);
        for(int f = 0; f < futurePeriods; f++) {
            cal.add(MapPeriodToCalendar.map(order.getEntity().getOrderPeriod().getPeriodUnit().getId()), 
                    order.getEntity().getOrderPeriod().getValue());
        }
        actualEventDate = cal.getTime();
                
        // is the starting date beyond the time frame of the main order?
        if (order.getEntity().getActiveSince() != null && 
                actualEventDate.before(order.getEntity().getActiveSince())) {
            LOG.error("The event for date " + actualEventDate + " can not be assigned for " +
                    "order " + order.getEntity().getId() + 
                    " active since " + order.getEntity().getActiveSince());
            return null;
        }
        
        Date newOrderDate = startingTime;
        cal.setTime(startingTime);
        while (cal.getTime().before(actualEventDate)) {
            newOrderDate = cal.getTime();
            cal.add(MapPeriodToCalendar.map(order.getEntity().getOrderPeriod().getPeriodUnit().getId()), 
                    order.getEntity().getOrderPeriod().getValue());
        }
        
        // is the found date beyond the time frame of the main order?
        if (order.getEntity().getActiveUntil() != null && 
                newOrderDate.after(order.getEntity().getActiveUntil())) {
            LOG.error("The event for date " + actualEventDate + " can not be assigned for " +
                    "order " + order.getEntity().getId() + 
                    " active until " + order.getEntity().getActiveUntil());
            return null;
        }

        return newOrderDate;
    }
    
    public Integer create(Date activeSince, Integer currencyId, Integer entityId) {
        OrderDTO currentOrder = new OrderDTO();
        currentOrder.setCurrency(new CurrencyDTO(currencyId));
        // notes
        try {
            EntityBL entity = new EntityBL(entityId);
            ResourceBundle bundle = ResourceBundle.getBundle("entityNotifications", 
                    entity.getLocale());
            currentOrder.setNotes(bundle.getString("order.current.notes"));
        } catch (Exception e) {
            throw new SessionInternalError("Error setting the new order notes", 
                    CurrentOrder.class, e);
        } 

        currentOrder.setActiveSince(activeSince);
        
        // create the order
        if (order == null) {
            order = new OrderBL();
        }
        order.set(currentOrder);
        order.addRelationships(userId, Constants.ORDER_PERIOD_ONCE, currencyId);
        return order.create(entityId, null, currentOrder);
    }
}
