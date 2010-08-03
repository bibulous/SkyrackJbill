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

import com.sapienter.jbilling.server.payment.IExternalCreditCardStorage;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.db.CreditCardDTO;

/**
 * A fake IExternalCreditCardStorage task for use with the SaveCreditCardExternallyTask. This plugin
 * will return a crafted "gateway key" for storage that will be used to replace the users existing
 * credit card details.
 *
 * The gateway key represents a vendor specific unique identifier for use when processing subsequent
 * payments for the same user - elmiminating the need to send the credit card in its entirety.
 */
public class PaymentFakeExternalStorage extends PaymentFakeTask implements IExternalCreditCardStorage {

    /**
     * for testing purposes, always returns "stored externaly" as a gateway key.
     *
     * @param contact contact to process
     * @param creditCard credit card to process
     * @return resulting unique gateway key for the credit card/contact
     */
	public String storeCreditCard(ContactDTO contact, CreditCardDTO creditCard) {
		return "stored externaly";
	}
}
