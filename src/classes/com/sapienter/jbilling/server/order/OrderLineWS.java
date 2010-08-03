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
 * Created on Dec 30, 2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.server.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sapienter.jbilling.server.item.ItemDTOEx;

/**
 * @author Emil
 */
public class OrderLineWS implements Serializable {

    private int id;
    private Integer orderId;
    private String amount;
    private String quantity;
    private String price;
    private Date createDatetime;
    private int deleted;
    private String description;
    private Integer versionNum;
    private Boolean editable = null;

    //provisioning fields
    private Integer provisioningStatusId;
    private String provisioningRequestId;

    // other fields, non-persistent
    private String priceStr = null;
    private ItemDTOEx itemDto = null;
    private Integer typeId = null;
    private Boolean useItem = null;
    private Integer itemId = null;

    public OrderLineWS() {
    }

    public OrderLineWS(Integer id, Integer itemId, String description, BigDecimal amount, BigDecimal quantity,
                       BigDecimal price,
                       Date create, Integer deleted, Integer newTypeId, Boolean editable, Integer orderId,
                       Boolean useItem, Integer version, Integer provisioningStatusId, String provisioningRequestId) {
        setId(id);
        setItemId(itemId);
        setDescription(description);
        setAmount(amount);
        setQuantity(quantity);
        setPrice(price);
        setCreateDatetime(create);
        setDeleted(deleted);
        setTypeId(newTypeId);
        setEditable(editable);
        setOrderId(orderId);
        setUseItem(useItem);
        setVersionNum(version);
        setProvisioningStatusId(provisioningStatusId);
        setProvisioningRequestId(provisioningRequestId);

    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Boolean getUseItem() {
        return useItem == null ? new Boolean(false) : useItem;
    }

    public void setUseItem(Boolean useItem) {
        this.useItem = useItem;
    }

    public String toString() {
        return " typeId = " + typeId + " useItem = "
                + useItem + "itemId = " + itemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getAmount() {
        return amount;
    }

    public BigDecimal getAmountAsDecimal() {
        return (amount == null ? null : new BigDecimal(amount));
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            this.amount = amount.toString();
        }
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemDTOEx getItemDto() {
        return itemDto;
    }

    public void setItemDto(ItemDTOEx itemDto) {
        this.itemDto = itemDto;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public BigDecimal getPriceAsDecimal() {
        return (price == null ? null : new BigDecimal(price));
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPrice(BigDecimal price) {
        if (price != null) {
            this.price = price.toString();
        }
    }

    public String getPriceStr() {
        return priceStr;
    }

    public String getQuantity() {
        return quantity;
    }

    public BigDecimal getQuantityAsDecimal() {
        return (quantity == null ? null : new BigDecimal(quantity));
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity != null) {
            this.quantity = quantity.toString();
        }
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    /**
     * @return the provisioningStatusId
     */
    public Integer getProvisioningStatusId() {
        return provisioningStatusId;
    }

    /**
     * @param provisioningStatusId the provisioningStatusId to set
     */
    public void setProvisioningStatusId(Integer provisioningStatusId) {
        this.provisioningStatusId = provisioningStatusId;
    }

    /**
     * @return the provisioningRequestId
     */
    public String getProvisioningRequestId() {
        return provisioningRequestId;
    }

    /**
     * @param provisioningRequestId the provisioningRequestId to set
     */
    public void setProvisioningRequestId(String provisioningRequestId) {
        this.provisioningRequestId = provisioningRequestId;
    }
}
