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
 * Created on Mar 13, 2004
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.item;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.validator.DynaValidatorForm;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.item.db.ItemPriceDTO;
import com.sapienter.jbilling.server.util.OptionDTO;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;

/**
 * Takes the object form and adds prices from the options page object
 * 
 * @author emilc
 *
 * @jsp:tag name="populateCurrencies"
 *          body-content="empty"
 */
public class PopulateCurrenciesTag extends TagSupport {

    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        // put the price in the form
        DynaValidatorForm form = (DynaValidatorForm) session.getAttribute(
                "item");

        List prices = (List) form.get("prices");
        if (prices.size() > 0) {
            // it's been done already
            return SKIP_BODY;
        } 
        
        for (Iterator it = ((Collection) pageContext.getAttribute(
                Constants.PAGE_CURRENCIES)).iterator(); it.hasNext(); ) {
            OptionDTO option = (OptionDTO) it.next();
            ItemPriceDTO price = new ItemPriceDTO(null, null, null,
                    new CurrencyDTO(Integer.valueOf(option.getCode())));
            price.setName(option.getDescription());
            
            prices.add(price);
        }
        
        return SKIP_BODY;
    }
}
