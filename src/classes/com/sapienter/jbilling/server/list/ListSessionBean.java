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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.customer.CustomerBL;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.item.CurrencyBL;
import com.sapienter.jbilling.server.item.ItemListBL;
import com.sapienter.jbilling.server.list.db.ListFieldDAS;
import com.sapienter.jbilling.server.list.db.ListFieldDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.payment.PaymentBL;
import com.sapienter.jbilling.server.process.BillingProcessBL;
import com.sapienter.jbilling.server.user.partner.PartnerBL;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.GetSelectableOptions;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import java.util.ArrayList;

/**
 * 
 * Generic list provider.
 * 
 * @author emilc
 **/
@Transactional( propagation = Propagation.REQUIRED )
public class ListSessionBean implements IListSessionBean {

    private static final Logger LOG = Logger.getLogger(ListSessionBean.class);

    public CachedRowSet getList(String type, Hashtable parameters)
            throws SessionInternalError {

        CachedRowSet retValue = null;
        try {
            LOG.debug("List requested for type" + type + " param = "
                    + parameters);

            if (type.equals(Constants.LIST_TYPE_CUSTOMER)) {
                CustomerBL list = new CustomerBL();
                int entityId = ((Integer) parameters.get("entityId"))
                        .intValue();
                Integer userType = (Integer) parameters.get("userType");
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getList(entityId, userType, userId);
            } else if (type.equals(Constants.LIST_TYPE_CUSTOMER_SIMPLE)) {
                CustomerBL list = new CustomerBL();
                int entityId = ((Integer) parameters.get("entityId"))
                        .intValue();
                Integer userType = (Integer) parameters.get("userType");
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getCustomerList(entityId, userType, userId);
            } else if (type.equals(Constants.LIST_TYPE_SUB_ACCOUNTS)) {
                CustomerBL list = new CustomerBL();
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getSubAccountsList(userId);
            } else if (type.equals(Constants.LIST_TYPE_PARTNERS_CUSTOMER)) {
                CustomerBL list = new CustomerBL();
                Integer partnerId = (Integer) parameters.get("partnerId");
                PartnerBL partner = new PartnerBL(partnerId);
                int entityId = partner.getEntity().getUser().getEntity()
                        .getId();
                Integer userType = Constants.TYPE_PARTNER;
                Integer userId = partner.getEntity().getUser().getUserId();
                retValue = list.getCustomerList(entityId, userType, userId);
            } else if (type.equals(Constants.LIST_TYPE_ITEM_TYPE)) {
                ItemListBL list = new ItemListBL();
                Integer entityId = (Integer) parameters.get("entityId");
                // Integer languageId = (Integer) parameters.get("languageId");
                retValue = list.getTypeList(entityId);
            } else if (type.equals(Constants.LIST_TYPE_ITEM)) {
                ItemListBL list = new ItemListBL();
                Integer entityId = (Integer) parameters.get("entityId");
                // Integer languageId = (Integer) parameters.get("languageId");
                retValue = list.getList(entityId);
            } else if (type.equals(Constants.LIST_TYPE_ITEM_USER_PRICE)) {
                ItemListBL list = new ItemListBL();
                Integer entityId = (Integer) parameters.get("entityId");
                Integer userId = (Integer) parameters.get("userId");
                Integer languageId = (Integer) parameters.get("languageId");
                retValue = list.getUserPriceList(entityId, userId, languageId);
            } else if (type.equals(Constants.LIST_TYPE_PROMOTION)) {
                ItemListBL list = new ItemListBL();
                Integer entityId = (Integer) parameters.get("entityId");
                Integer languageId = (Integer) parameters.get("languageId");
                retValue = list.getPromotionList(entityId, languageId);
            } else if (type.equals(Constants.LIST_TYPE_PAYMENT)
                    || type.equals(Constants.LIST_TYPE_REFUND)) {
                PaymentBL list = new PaymentBL();
                Integer entityId = (Integer) parameters.get("entityId");
                Integer languageId = (Integer) parameters.get("languageId");
                Integer userType = (Integer) parameters.get("userType");
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getList(entityId, languageId, userType, userId,
                        type.equals(Constants.LIST_TYPE_REFUND));
            } else if (type.equals(Constants.LIST_TYPE_ORDER)) {
                OrderBL list = new OrderBL();
                Integer entityId = (Integer) parameters.get("entityId");
                Integer userType = (Integer) parameters.get("userType");
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getList(entityId, userType, userId);
            } else if (type.equals(Constants.LIST_TYPE_INVOICE)) {
                InvoiceBL list = new InvoiceBL();
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getPayableInvoicesByUser(userId);
            } else if (type.equals(Constants.LIST_TYPE_INVOICE_ORDER)) {
                InvoiceBL list = new InvoiceBL();
                Integer orderId = (Integer) parameters.get("orderId");
                retValue = list.getList(orderId);
            } else if (type.equals(Constants.LIST_TYPE_PAYMENT_USER)) {
                PaymentBL list = new PaymentBL();
                // this one gets the payments of the selected user
                Integer languageId = (Integer) parameters.get("languageId");
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getRefundableList(languageId, userId);
            } else if (type.equals(Constants.LIST_TYPE_INVOICE_GRAL)) {
                InvoiceBL list = new InvoiceBL();
                Integer entityId = (Integer) parameters.get("entityId");
                Integer userType = (Integer) parameters.get("userType");
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getList(entityId, userType, userId);
            } else if (type.equals(Constants.LIST_TYPE_PROCESS)) {
                BillingProcessBL list = new BillingProcessBL();
                Integer entityId = (Integer) parameters.get("entityId");
                retValue = list.getList(entityId);
            } else if (type.equals(Constants.LIST_TYPE_PROCESS_INVOICES)) {
                InvoiceBL list = new InvoiceBL();
                Integer processId = (Integer) parameters.get("processId");
                retValue = list.getInvoicesByProcessId(processId);
            } else if (type.equals(Constants.LIST_TYPE_PROCESS_ORDERS)) {
                OrderBL list = new OrderBL();
                Integer processId = (Integer) parameters.get("processId");
                retValue = list.getOrdersByProcessId(processId);
            } else if (type.equals(Constants.LIST_TYPE_NOTIFICATION_TYPE)) {
                NotificationBL list = new NotificationBL();
                Integer languageId = (Integer) parameters.get("languageId");
                retValue = list.getTypeList(languageId);
            } else if (type.equals(Constants.LIST_TYPE_PARTNER)) {
                PartnerBL list = new PartnerBL();
                Integer entityId = (Integer) parameters.get("entityId");
                retValue = list.getList(entityId);
            } else if (type.equals(Constants.LIST_TYPE_PAYOUT)) {
                PartnerBL list = new PartnerBL();
                Integer partnerId = (Integer) parameters.get("partnerId");
                retValue = list.getPayoutList(partnerId);
            } else {
                LOG.error("list type " + type + " is not supported");
                throw new Exception("list type " + type + " is not supported");
            }

        } catch (Exception e) {
            LOG.error("Exception retreiving list " + type, e);
            throw new SessionInternalError("Generic list");
        }

        return retValue;
    }

    public ListDTO getDtoList(String type, Hashtable parameters)
            throws SessionInternalError {
        ListDTO retValue;

        Integer entityId = (Integer) parameters.get("entityId");
        try {
            if (type.equals(Constants.LIST_TYPE_ITEM_ORDER)) {
                ItemListBL list = new ItemListBL();
                Integer languageId = (Integer) parameters.get("languageId");
                Integer userId = (Integer) parameters.get("userId");
                retValue = list.getOrderList(entityId, languageId, userId);
            } else {
                throw new SessionInternalError("dto list type " + type
                        + " is not supported");
            }
        } catch (Exception e) {
            LOG.error("Exception retreiving list " + type, e);
            throw new SessionInternalError("Generic list");
        }

        return retValue;
    }

    /**
     * This really doesn't belong to the list session bean, but it needs some
     * remote interface, so might as well put it here. All the real code it in
     * GetSelectableOptions.java
     */
    public Collection getOptions(String type, Integer languageId,
            Integer entityId, Integer executorType) throws SessionInternalError {
        LOG.debug("getting option " + type);
        return GetSelectableOptions.getOptions(type, languageId, entityId,
                executorType);
    }

    /**
     * Returns a map to all the currencies with 'id' - 'symbol'. This is useful
     * as an application wide object for reference
     * 
     * @return
     */
    public CurrencyDTO[] getCurrencySymbolsMap() throws SessionInternalError {
        try {
            CurrencyBL currency = new CurrencyBL();
            CurrencyDTO[] retValue = currency.getSymbols();
            LOG.debug("symbols total = " + retValue.length + " content="
                    + retValue);
            return retValue;
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    /*
     * The above are legacy methods. The follwing are the new list methods
     * 
     * @author Emil
     */

    public void updateStatistics() throws SessionInternalError {
        try {
            ListBL list = new ListBL();
            list.updateStatistics();
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    public CachedRowSet getPage(Integer start, Integer end, Integer size,
            Integer listId, Integer entityId, Boolean direction,
            Integer fieldId, Hashtable parameters) throws SessionInternalError {
        try {
            ListBL list = new ListBL();
            return list.getPage(start, end, size.intValue(), listId, entityId,
                    direction.booleanValue(), fieldId, parameters);
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    public CachedRowSet search(String start, String end, Integer fieldId,
            Integer listId, Integer entityId, Hashtable parameters)
            throws SessionInternalError {
        try {
            ListBL list = new ListBL();
            return list.search(start, end, fieldId, listId, entityId,
                    parameters);
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    public PagedListDTO getPagedListDTO(Integer listId, String legacyName,
            Integer entityId, Integer userId) throws SessionInternalError {
        if (listId == null && legacyName == null) {
            throw new SessionInternalError("Can not identify the list");
        }

        PagedListDTO retValue = new PagedListDTO();
        try {
            ListBL bl = new ListBL();
            if (listId == null) {
                bl.set(legacyName);
                retValue.setListId(bl.getEntity().getId());
            } else {
                bl.set(listId);
                retValue.setListId(listId);
            }
            // find the searcheable fields
            List fields = new ArrayList();
            // find the key id, it is now the only one ordenable
            for (Iterator it = bl.getEntity().getListFields().iterator(); it
                    .hasNext();) {
                ListFieldDTO field = (ListFieldDTO) it.next();
                if (field.getOrdenable().intValue() == 1) {
                    retValue.setKeyFieldId(field.getId());
                }
                if (field.getSearchable() == 1) {
                    fields.add(new ListFieldDTO(field.getId(), field
                            .getTitleKey(), field.getColumnName(), field
                            .getOrdenable(), field.getSearchable(), field
                            .getDataType()));
                }
            }
            LinkedList<ListFieldDTO> orderedFields = (LinkedList<ListFieldDTO>) ListFieldDAS.orderFields(fields);
            ListFieldDTO[] arr = new ListFieldDTO[fields.size()];
            retValue.setFields((ListFieldDTO[]) orderedFields.toArray(arr));
            
            LOG.debug("There are " + retValue.getFields().length
                    + " searchable fields");
            // see if there's a count
            ListEntityBL eBl = new ListEntityBL();
            eBl.set(retValue.getListId(), entityId);
            if (eBl.getEntity() != null) {
                retValue.setCount(eBl.getEntity().getTotalRecords());
            } else {
                // no statistics for this entity
                retValue.setCount(null);
            }

            // get the page size from the user's preferences
            PreferenceBL pref = new PreferenceBL();
            try {
                pref.setForUser(userId, Constants.PREFERENCE_PAGE_SIZE);
            } catch (EmptyResultDataAccessException e) {
                // use the defaults
            }
            retValue.setPageSize(new Integer(pref.getInt()));
            // the title key
            retValue.setTitleKey(bl.getEntity().getTitleKey());
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }

        return retValue;
    }
}