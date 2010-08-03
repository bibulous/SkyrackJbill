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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import org.springmodules.cache.provider.CacheProviderFacade;
import org.springmodules.cache.CachingModel;
import org.springmodules.cache.FlushingModel;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemPriceDAS;
import com.sapienter.jbilling.server.item.db.ItemPriceDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;
import com.sapienter.jbilling.server.item.tasks.IPricing;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.user.EntityBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ItemBL {
    private ItemDAS itemDas = null;
    private ItemDTO item = null;
    private static final Logger LOG = Logger.getLogger(ItemBL.class);
    private EventLogger eLogger = null;
    private String priceCurrencySymbol = null;
    private List<PricingField> pricingFields = null;

    // item price cache for getPrice()
    private CacheProviderFacade cache;
    private CachingModel cacheModel;
    private FlushingModel flushModel;

    public ItemBL(Integer itemId) 
            throws SessionInternalError {
        try {
            init();
            set(itemId);
        } catch (Exception e) {
            throw new SessionInternalError("Setting item", ItemBL.class, e);
        } 
    }
    
    public ItemBL() {
        init();
    }
    
    public ItemBL(ItemDTO item) {
        this.item = item;
        init();
    }
    
    public void set(Integer itemId) {
        item = itemDas.find(itemId);
    }
    
    private void init() {
        eLogger = EventLogger.getInstance();        
        itemDas = new ItemDAS();
        cache = (CacheProviderFacade) Context.getBean(Context.Name.CACHE);
        cacheModel = (CachingModel) Context.getBean(
                Context.Name.CACHE_MODEL_ITEM_PRICE);
        flushModel = (FlushingModel) Context.getBean(
                Context.Name.CACHE_FLUSH_MODEL_ITEM_PRICE);
    }
    
    public ItemDTO getEntity() {
        return item;
    }

    public Integer create(ItemDTO dto, Integer languageId) 
             {
        EntityBL entity = new EntityBL(dto.getEntityId());
        if (languageId == null) {
            languageId = entity.getEntity().getLanguageId();
        }
        if (dto.getHasDecimals() != null) {
            dto.setHasDecimals(dto.getHasDecimals());
        } else {
            dto.setHasDecimals(0);
        }
        dto.setDeleted(0);

        item = itemDas.save(dto);

        item.setDescription(dto.getDescription(), languageId);
        updateTypes(dto);
        updateCurrencies(dto);
        
        return item.getId();
    }
    
    public void update(Integer executorId, ItemDTO dto, 
            Integer languageId)  {

        eLogger.audit(executorId, null, Constants.TABLE_ITEM, item.getId(),
                EventLogger.MODULE_ITEM_MAINTENANCE, 
                EventLogger.ROW_UPDATED, null, null, null);
        
        item.setNumber(dto.getNumber());
        item.setPriceManual(dto.getPriceManual());
        item.setDescription(dto.getDescription(), languageId);
        item.setPercentage(dto.getPercentage());
        item.setHasDecimals(dto.getHasDecimals());
        
        updateTypes(dto);
        updateCurrencies(dto);

        // invalidate item/price cache
        invalidateCache();
    }
    
    private void updateTypes(ItemDTO dto) 
            {
        // update the types relationship        
        Collection types = item.getItemTypes();
        types.clear();
        ItemTypeBL typeBl = new ItemTypeBL();
        // TODO verify that all the categories belong to the same
        // order_line_type_id
        for (int f=0; f < dto.getTypes().length; f++) {
            typeBl.set(dto.getTypes()[f]);
            types.add(typeBl.getEntity());
        }
    }
    
    private void updateCurrencies(ItemDTO dto) {
    	LOG.debug("updating prices. prices " + (dto.getPrices() != null) + 
    			" price = " + dto.getPrice());
        ItemPriceDAS itemPriceDas = new ItemPriceDAS();
    	// may be there's just one simple price
    	if (dto.getPrices() == null) {
    		if (dto.getPrice() != null) {
    			List prices = new ArrayList();
    			// get the defualt currency of the entity
                CurrencyDTO currency = new CurrencyDAS().findNow(
                        dto.getCurrencyId());
    			if (currency == null) {
        			EntityBL entity = new EntityBL(dto.getEntityId());
    				currency = entity.getEntity().getCurrency();
    			}
    			ItemPriceDTO price = new ItemPriceDTO(null, dto, dto.getPrice(),
    					currency);
    			prices.add(price);
    			dto.setPrices(prices);
    		} else {
    			LOG.warn("updatedCurrencies was called, but this " +
    					"item has no price");
        		return;
    		}
    	}
    	
    	// a call to clear() would simply set item_price.entity_id = null
        // instead of removing the row
        for (int f = 0; f < dto.getPrices().size(); f++) {
            ItemPriceDTO price = (ItemPriceDTO) dto.getPrices().get(f);
            ItemPriceDTO priceRow = null;

            priceRow = itemPriceDas.find(dto.getId(), 
                        price.getCurrencyId());
                    
            if (price.getPrice() != null) {
                if (priceRow != null) {
                    // if there one there already, update it
                    priceRow.setPrice(price.getPrice());
                    
                } else {
                    // nothing there, create one
                    ItemPriceDTO itemPrice= new ItemPriceDTO(null, item, 
                            price.getPrice(), price.getCurrency());
                    item.getItemPrices().add(itemPrice);
                }
            } else {
                // this price should be removed if it is there
                if (priceRow != null) {
                    itemPriceDas.delete(priceRow);
                }
            }
        }    
        // invalidate item/price cache
        invalidateCache();
    }
    
    public void delete(Integer executorId) {
        item.setDeleted(new Integer(1));
        eLogger.audit(executorId, null, Constants.TABLE_ITEM, item.getId(),
                EventLogger.MODULE_ITEM_MAINTENANCE, 
                EventLogger.ROW_DELETED, null, null, null);
        
    }

    public static boolean validate(ItemDTO dto) {
        boolean retValue = true;
        
        if (dto.getDescription() == null || dto.getPrice() == null ||
                dto.getPriceManual() == null || 
                dto.getTypes() == null) {
            retValue = false;
        }
        
        return retValue;
    }
    
    public boolean validateDecimals( Integer hasDecimals ){
	    if( hasDecimals == 0 ){
	        if(new OrderLineDAS().findLinesWithDecimals(item.getId()) > 0) {
	        	return false;
	        }
	    }
	    return true;
    }
    
    /**
     * This is the basic price, without any plug-ins applied.
     * It only takes into account the currency and makes the necessary
     * conversions.
     * It uses a cache to avoid repeating this look-up too often
     * @return The price in the requested currency
     */
    public BigDecimal getPriceByCurrency(Integer currencyId, Integer entityId)
            throws SessionInternalError {
        BigDecimal retValue = null;

        // try to get cached item price for this currency
        retValue = (BigDecimal) cache.getFromCache(item.getId() +
                currencyId.toString(), cacheModel);

        if (retValue != null) {
            return retValue;
        }

        // get the item's defualt price
        int prices = 0;
        BigDecimal aPrice = null;
        Integer aCurrency = null;
        // may be the item has a price in this currency
        for (Iterator it = item.getItemPrices().iterator(); it.hasNext(); ) {
            prices++;
            ItemPriceDTO price = (ItemPriceDTO) it.next();
            if (price.getCurrencyId().equals(currencyId)) {
                // it is there!
                retValue = price.getPrice();
                break;
            } else {
                // the pivot has priority, for a more accurate conversion
                if (aCurrency == null || aCurrency.intValue() != 1) { 
                    aPrice = price.getPrice();
                    aCurrency = price.getCurrencyId();
                }
            }
        }
        
        if (prices > 0 && retValue == null) {
            // there are prices defined, but not for the currency required
            try {
                CurrencyBL currencyBL = new CurrencyBL();
                retValue = currencyBL.convert(aCurrency, currencyId, aPrice, 
                        entityId);
            } catch (Exception e) {
                throw new SessionInternalError(e);
            }
        } else {
            if (retValue == null) {
                throw new SessionInternalError("No price defined for item " + 
                        item.getId());
            }
        }

        cache.putInCache(item.getId() + currencyId.toString(), cacheModel,
                retValue);

        return retValue;
    }
    
    /**
     * It will call the main getPrice, with the currency of the userId passed
     * @param userId
     * @param entityId
     * @return
     * @throws SessionInternalError
     */
    public BigDecimal getPrice(Integer userId, Integer entityId)
            throws SessionInternalError {
        UserBL user = new UserBL(userId);
        return getPrice(userId, user.getCurrencyId(), entityId);
    }    
    /**
     * Will find the right price considering the user's special prices and which
     * currencies had been entered in the prices table.
     * @param userId
     * @param currencyId
     * @param entityId
     * @return The price in the requested currency. It always returns a price,
     * otherwise an exception for lack of pricing for an item
     */
    public BigDecimal getPrice(Integer userId, Integer currencyId, Integer entityId)
            throws SessionInternalError {
        BigDecimal retValue = null;
        CurrencyBL currencyBL;
        
        if (currencyId == null || entityId == null) {
            throw new SessionInternalError("Can't get a price with null " +
                    "paramteres. currencyId = " + currencyId + " entityId = " +
                    entityId);
        }
        
        try {
            currencyBL = new CurrencyBL(currencyId);
            priceCurrencySymbol = currencyBL.getEntity().getSymbol();
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }

        retValue = getPriceByCurrency(currencyId, entityId);
        
        // run a plug-in with external logic (rules), if available
        try {
            PluggableTaskManager<IPricing> taskManager =
                new PluggableTaskManager<IPricing>(entityId,
                Constants.PLUGGABLE_TASK_ITEM_PRICING);
            IPricing myTask = taskManager.getNextClass();
            
            while(myTask != null) {
                retValue = myTask.getPrice(item.getId(), userId, currencyId, pricingFields, retValue);
                myTask = taskManager.getNextClass();
            }
        } catch (Exception e) {
            throw new SessionInternalError("Item pricing task error", ItemBL.class, e);
        }
        
        return retValue;
    }
    
    public ItemDTO getDTO(Integer languageId, Integer userId, 
            Integer entityId, Integer currencyId) 
            throws SessionInternalError {
        
        ItemDTO dto = new ItemDTO(
            item.getId(),
            item.getInternalNumber(),
            item.getEntity(),
            item.getDescription(languageId),
            item.getPriceManual(),
            item.getDeleted(),
            currencyId,
            null,
            item.getPercentage(),
            null, // to be set right after
            item.getHasDecimals() );  

        // add all the prices for each currency
        // if this is a percenteage, we still need an array with empty prices
        dto.setPrices(findPrices(entityId, languageId));
        
        if (currencyId != null && dto.getPercentage() == null) {
            // it wants one price in particular
            dto.setPrice(getPrice(userId, currencyId, entityId));
        }
        
        // set the types
        Integer types[] = new Integer[item.getItemTypes().size()];
        int index = 0;
        for (Iterator it = item.getItemTypes().iterator(); it.hasNext(); 
                index++) {
            ItemTypeDTO type = (ItemTypeDTO) it.next();
        
            types[index] = type.getId();
            
            // it is assumed that an item belongs to categories that have
            // all the same order_line_type_id
            dto.setOrderLineTypeId(type.getOrderLineTypeId());
        }
        dto.setTypes(types);
    
        return dto;
    }

    public ItemDTO getDTO(ItemDTOEx other) {
        ItemDTO retValue = new ItemDTO();
        if (other.getId() != null) {
            retValue.setId(other.getId());
        }

        retValue.setEntity(new CompanyDAS().find(other.getEntityId()));
        retValue.setNumber(other.getNumber());
        retValue.setPercentage(other.getPercentage());
        retValue.setPriceManual(other.getPriceManual());
        retValue.setDeleted(other.getDeleted());
        retValue.setHasDecimals(other.getHasDecimals());
        retValue.setDescription(other.getDescription());
        retValue.setTypes(other.getTypes());
        retValue.setPromoCode(other.getPromoCode());
        retValue.setCurrencyId(other.getCurrencyId());
        retValue.setPrice(other.getPrice());
        retValue.setOrderLineTypeId(other.getOrderLineTypeId());

        // convert prices between DTO and DTOEx (WS)
        List otherPrices = other.getPrices();
        if (otherPrices != null) {
            List prices = new ArrayList(otherPrices.size());
            for (int i = 0; i < otherPrices.size(); i++) {
                ItemPriceDTO itemPrice = new ItemPriceDTO();
                ItemPriceDTOEx otherPrice = (ItemPriceDTOEx) otherPrices.get(i);
                itemPrice.setId(otherPrice.getId());
                itemPrice.setCurrency(new CurrencyDAS().find(
                        otherPrice.getCurrencyId()));
                itemPrice.setPrice(otherPrice.getPrice());
                itemPrice.setName(otherPrice.getName());
                itemPrice.setPriceForm(otherPrice.getPriceForm());
                prices.add(itemPrice);
            }
            retValue.setPrices(prices);
        }

        return retValue;
    }

    public ItemDTOEx getWS(ItemDTO other) {
        if (other == null) {
            other = item;
        }

        ItemDTOEx retValue = new ItemDTOEx();
        retValue.setId(other.getId());

        retValue.setEntityId(other.getEntity().getId());
        retValue.setNumber(other.getInternalNumber());
        retValue.setPercentage(other.getPercentage());
        retValue.setPriceManual(other.getPriceManual());
        retValue.setDeleted(other.getDeleted());
        retValue.setHasDecimals(other.getHasDecimals());
        retValue.setDescription(other.getDescription());
        retValue.setTypes(other.getTypes());
        retValue.setPromoCode(other.getPromoCode());
        retValue.setCurrencyId(other.getCurrencyId());
        retValue.setPrice(other.getPrice());
        retValue.setOrderLineTypeId(other.getOrderLineTypeId());
        retValue.setPrices(other.getPrices());

        // convert prices between DTOEx (WS) and DTO
        List otherPrices = other.getPrices();
        if (otherPrices != null) {
            List prices = new ArrayList(otherPrices.size());
            for (int i = 0; i < otherPrices.size(); i++) {
                ItemPriceDTOEx itemPrice = new ItemPriceDTOEx();
                ItemPriceDTO otherPrice = (ItemPriceDTO) otherPrices.get(i);
                itemPrice.setId(otherPrice.getId());
                itemPrice.setCurrencyId(otherPrice.getCurrency().getId());
                itemPrice.setPrice(otherPrice.getPrice());
                itemPrice.setName(otherPrice.getName());
                itemPrice.setPriceForm(otherPrice.getPriceForm());
                prices.add(itemPrice);
            }
            retValue.setPrices(prices);
        }

        return retValue;
    }
    
    /**
     * This method will try to find a currency id for this item. It will
     * give priority to the entity's default currency, otherwise anyone
     * will do.
     * @return
     */
    private List findPrices(Integer entityId, Integer languageId) {
        List retValue = new ArrayList();

        // go over all the curencies of this entity
        for (CurrencyDTO currency: item.getEntity().getCurrencies()) {
            ItemPriceDTO price = new ItemPriceDTO();
            price.setCurrency(currency);
            price.setName(currency.getDescription(languageId));
            // se if there's a price in this currency

            ItemPriceDTO priceRow = new ItemPriceDAS().find(
                item.getId(),currency.getId());
            if (priceRow != null) {
                price.setPrice(priceRow.getPrice());
                price.setPriceForm(price.getPrice().toString());
            }
            retValue.add(price);
        }
        
        return retValue;
    }
    /**
     * @return
     */
    public String getPriceCurrencySymbol() {
        return priceCurrencySymbol;
    }

    /**
     * Returns all items for the given entity.
     * @param entityId
     * The id of the entity.
     * @return an array of all items
     */
    public ItemDTOEx[] getAllItems(Integer entityId) {
        EntityBL entityBL = new EntityBL(entityId);
        CompanyDTO entity = entityBL.getEntity();
        Collection itemEntities = entity.getItems();
        ItemDTOEx[] items = new ItemDTOEx[itemEntities.size()];

        // iterate through returned item entities, converting them into a DTO
        int index = 0;
        for (ItemDTO item: entity.getItems()) {
            set(item.getId());
            items[index++] = getWS(getDTO(entity.getLanguageId(),
                    null, entityId, entity.getCurrencyId()));
        }

        return items;
    }

    /**
     * Returns all items for the given item type (category) id. If no results
     * are found an empty array is returned.
     *
     * @see ItemDAS#findAllByItemType(Integer)
     *
     * @param itemTypeId item type (category) id
     * @return array of found items, empty if none found
     */
    public ItemDTOEx[] getAllItemsByType(Integer itemTypeId) {
        List<ItemDTO> results = new ItemDAS().findAllByItemType(itemTypeId);
        ItemDTOEx[] items = new ItemDTOEx[results.size()];

        int index = 0;
        for (ItemDTO item : results)
            items[index++] = getWS(item);

        return items;
    }

    public void setPricingFields(List<PricingField> fields) {
        pricingFields = fields;
    }

    public void invalidateCache() {
        cache.flushCache(flushModel);
    }
}
