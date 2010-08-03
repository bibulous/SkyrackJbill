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

package com.sapienter.jbilling.server.pluggableTask;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.util.Constants;

/**
 * Basic tasks that takes the quantity and multiplies it by the price to 
 * get the lines total. It also updates the order total with the addition
 * of all line totals
 * 
 */
public class BasicLineTotalTask extends PluggableTask implements OrderProcessingTask {

    private static final Logger LOG = Logger.getLogger(BasicLineTotalTask.class);

    public void doProcessing(OrderDTO order) throws TaskException {
        // calculations are done with 10 decimals. 
        // The final total is the rounded to 2 decimals.
        BigDecimal orderTotal = new BigDecimal("0.0000000000");
        BigDecimal taxPerTotal = new BigDecimal("0.0000000000");
        BigDecimal taxNonPerTotal = new BigDecimal("0.0000000000");
        BigDecimal nonTaxPerTotal = new BigDecimal("0.0000000000");
        BigDecimal nonTaxNonPerTotal = new BigDecimal("0.0000000000");
        
        validateLinesQuantity( order );
        
        // step one, go over the non-percentage items,
        // collecting both tax and non-tax values
        for (OrderLineDTO line : order.getLines()) {
        	if (line.getDeleted() == 1)
                continue;

        	ItemDTO item = new ItemDAS().find(line.getItemId()); // the line might be dettached
            if (item != null && item.getPercentage() == null) { 

                BigDecimal amount;
                if (!line.getTotalReadOnly()) {
                    amount = line.getQuantity().multiply(line.getPrice());
                    line.setAmount(line.getQuantity().multiply(line.getPrice()));
                } else {
                    amount = line.getAmount();
                }

                if (line.getTypeId().equals(Constants.ORDER_LINE_TYPE_TAX)) {
                    taxNonPerTotal = taxNonPerTotal.add(amount);
                } else {
                    nonTaxNonPerTotal = nonTaxNonPerTotal.add(amount);
                }
                LOG.debug("adding normal line. Totals =" + taxNonPerTotal + " - " + nonTaxNonPerTotal);
            }
        }
        
        // step two non tax percetage items
        for (OrderLineDTO line : order.getLines()) {
        	if (line.getDeleted() == 1)
                continue;

            ItemDTO item = new ItemDAS().find(line.getItemId());
            if (item != null
                    && item.getPercentage() != null
                    && !line.getTypeId().equals(Constants.ORDER_LINE_TYPE_TAX)) {

                BigDecimal amount;
                if (!line.getTotalReadOnly()) {
                    amount = nonTaxNonPerTotal.divide(new BigDecimal("100"), Constants.BIGDECIMAL_ROUND);
                    amount = amount.multiply(line.getPrice());
                    amount = amount.setScale(2, Constants.BIGDECIMAL_ROUND); // round final result down to 2 decimals
                    line.setAmount(amount);
                } else {
                    amount = line.getAmount();
                }
                nonTaxPerTotal = nonTaxPerTotal.add(amount);
                LOG.debug("adding no tax percentage line. Total =" + nonTaxPerTotal);
            }
        }
        
        // step three: tax percetage items
        BigDecimal allNonTaxes = nonTaxNonPerTotal.add(nonTaxPerTotal);
        for (OrderLineDTO line : order.getLines()) {
        	if (line.getDeleted() == 1)
                continue;

            ItemDTO item = new ItemDAS().find(line.getItemId());
            if (item != null
                    && item.getPercentage() != null
                    && line.getTypeId().equals(Constants.ORDER_LINE_TYPE_TAX)) {

                BigDecimal amount;
                if (!line.getTotalReadOnly()) {
                    amount = allNonTaxes.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_EVEN);
                    amount = amount.multiply(line.getPrice());
                    amount = amount.setScale(2, Constants.BIGDECIMAL_ROUND); // round final result down to 2 decimals
                    line.setAmount(amount);
                } else {
                    amount = line.getAmount();
                }
                
                taxPerTotal = taxPerTotal.add(amount);
                LOG.debug("adding tax percentage line. Total =" + taxPerTotal);
            }
        }

        orderTotal = taxNonPerTotal.add(taxPerTotal).add(nonTaxPerTotal).add(nonTaxNonPerTotal);
        orderTotal = orderTotal.setScale(2, Constants.BIGDECIMAL_ROUND); // round final result down to 2 decimals
        order.setTotal(orderTotal);
    }
    
    public void validateLinesQuantity( OrderDTO order ) throws TaskException {
    	for (OrderLineDTO line: order.getLines()) {
            if (line.getItem() != null
                    && line.getQuantity().remainder(Constants.BIGDECIMAL_ONE).compareTo(BigDecimal.ZERO) != 0.0
                    && line.getItem().getHasDecimals() == 0 ) {
            	throw new TaskException(new ItemDecimalsException( "Item does not allow Decimals" ));
            }
        }
    }
}
