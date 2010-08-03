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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemDTOEx implements Serializable {

    // ItemDTO
    private Integer id;
    private String number;
    private BigDecimal percentage;
    private Integer priceManual;
    private Integer hasDecimals;
    private Integer deleted;
    private Integer entityId;

    // *** ItemDTOEx ***
    private String description = null;
    private Integer[] types = null;
    private String promoCode = null;
    private Integer currencyId = null;
    private BigDecimal price = null;
    private Integer orderLineTypeId = null;

    private List<ItemPriceDTOEx> prices = null;

    public ItemDTOEx(Integer id,String number, Integer entity, String description, Integer manualPrice,
                     Integer deleted, Integer currencyId, BigDecimal price, BigDecimal percentage,
                     Integer orderLineTypeId, Integer hasDecimals) {

        this(id, number, percentage, manualPrice, hasDecimals, deleted, entity);
        setDescription(description);
        setCurrencyId(currencyId);
        setPrice(price);
        setOrderLineTypeId(orderLineTypeId);
    }

    public ItemDTOEx() {
    }

    public ItemDTOEx(Integer id, String number, BigDecimal percentage, Integer priceManual, Integer hasDecimals,
                     Integer deleted, Integer entityId) {
        this.id = id;
        this.number = number;
        this.percentage = percentage;
        this.priceManual = priceManual;
        this.hasDecimals = hasDecimals;
        this.deleted = deleted;
        this.entityId = entityId;
    }

    public ItemDTOEx(ItemDTOEx otherValue) {
        this.id = otherValue.id;
        this.number = otherValue.number;
        this.percentage = otherValue.percentage;
        this.priceManual = otherValue.priceManual;
        this.hasDecimals = otherValue.hasDecimals;
        this.deleted = otherValue.deleted;
        this.entityId = otherValue.entityId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getPercentage() {
        return this.percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getPriceManual() {
        return this.priceManual;
    }

    public void setPriceManual(Integer priceManual) {
        this.priceManual = priceManual;
    }

    public Integer getHasDecimals() {
        return this.hasDecimals;
    }

    public void setHasDecimals(Integer hasDecimals) {
        this.hasDecimals = hasDecimals;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getEntityId() {
        return this.entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * Returns the description.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer[] getTypes() {
        return types;
    }

    /*
     * Rules only work on collections of strings (operator contains)
     */
    public Collection<String> getStrTypes() {
        List<String> retValue = new ArrayList<String>();
        for (Integer i: types) {
            retValue.add(i.toString());
        }
        return retValue;
    }

    public void setTypes(Integer[] vector) {
        types = vector;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String string) {
        promoCode = string;
    }

    public Integer getOrderLineTypeId() {
        return orderLineTypeId;
    }

    public void setOrderLineTypeId(Integer typeId) {
        orderLineTypeId = typeId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ItemPriceDTOEx> getPrices() {
        return prices;
    }

    public void setPrices(List<ItemPriceDTOEx> prices) {
        this.prices = prices;
    }

    public boolean isIdentical(Object other) {
        if (other instanceof ItemDTOEx) {
            ItemDTOEx that = (ItemDTOEx) other;
            boolean lEquals = true;
            if (this.number == null) {
                lEquals = lEquals && (that.number == null);
            } else {
                lEquals = lEquals && this.number.equals(that.number);
            }
            if (this.percentage == null) {
                lEquals = lEquals && (that.percentage == null);
            } else {
                lEquals = lEquals && this.percentage.equals(that.percentage);
            }
            if (this.priceManual == null) {
                lEquals = lEquals && (that.priceManual == null);
            } else {
                lEquals = lEquals && this.priceManual.equals(that.priceManual);
            }
            if (this.hasDecimals == null) {
                lEquals = lEquals && (that.hasDecimals == null);
            } else {
                lEquals = lEquals && this.hasDecimals.equals(that.hasDecimals);
            }
            if (this.deleted == null) {
                lEquals = lEquals && (that.deleted == null);
            } else {
                lEquals = lEquals && this.deleted.equals(that.deleted);
            }
            if (this.entityId == null) {
                lEquals = lEquals && (that.entityId == null);
            } else {
                lEquals = lEquals && this.entityId.equals(that.entityId);
            }

            return lEquals;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof ItemDTOEx))
            return false;

        ItemDTOEx that = (ItemDTOEx) other;
        boolean lEquals = true;
        if( this.id == null ) {
            lEquals = lEquals && ( that.id == null );
        } else {
            lEquals = lEquals && this.id.equals( that.id );
        }

        lEquals = lEquals && isIdentical(that);
        return lEquals;
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 37*result + ((this.id != null) ? this.id.hashCode() : 0);
        result = 37*result + ((this.number != null) ? this.number.hashCode() : 0);
        result = 37*result + ((this.percentage != null) ? this.percentage.hashCode() : 0);
        result = 37*result + ((this.priceManual != null) ? this.priceManual.hashCode() : 0);
        result = 37*result + ((this.hasDecimals != null) ? this.hasDecimals.hashCode() : 0);
        result = 37*result + ((this.deleted != null) ? this.deleted.hashCode() : 0);
        result = 37*result + ((this.entityId != null) ? this.entityId.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("{");

        str.append("id=" + getId() + " " + "number=" + getNumber() + " " + "percentage=" + getPercentage() + " " + "priceManual=" + getPriceManual() + " " + "hasDecimals=" + getHasDecimals() + " " + "deleted=" + getDeleted() + " " + "entityId=" + getEntityId());
        str.append('}');

        return(str.toString());
    }
}
