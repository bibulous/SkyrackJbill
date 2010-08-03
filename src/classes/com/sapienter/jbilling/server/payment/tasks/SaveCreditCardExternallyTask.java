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

package com.sapienter.jbilling.server.payment.tasks;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskBL;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.payment.IExternalCreditCardStorage;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.db.CreditCardDTO;
import com.sapienter.jbilling.server.user.event.NewContactEvent;
import com.sapienter.jbilling.server.user.event.NewCreditCardEvent;

/**
 * Internal Event Plugin designed to save a unique "gateway key" returned by the configured IExternalCreditCardStorage
 * task instead of the complete credit card number.
 *
 * This plugin subscribes to both NewCreditCardEvent and NewContactEvent. For NewContactEvent, this plugin
 * will only invoke the external save logic for new contacts matching the configured "contactType" id. 
 */
public class SaveCreditCardExternallyTask extends PluggableTask implements IInternalEventsTask {
    private static final Logger LOG = Logger.getLogger(SaveCreditCardExternallyTask.class);

    private static final String PARAM_CONTACT_TYPE = "contactType";
    private static final String PARAM_EXTERNAL_SAVING_PLUGIN_ID = "externalSavingPluginId";

    private Integer contactType;
    private Integer externalSavingPluginId;

    @SuppressWarnings("unchecked")
    private static final Class<Event> events[] = new Class[] {
            NewCreditCardEvent.class,
            NewContactEvent.class
    };

    public Class<Event>[] getSubscribedEvents() { return events; }

    // fixme: user parameters always come out as strings, int/float only available through db configured plugins

    /**
     * Returns the configured contact type as an integer.
     *
     * @return contact type
     * @throws PluggableTaskException if type cannot be converted to an integer
     */
    public Integer getContactType() throws PluggableTaskException {
        if (contactType == null) {
            try {
                if (parameters.get(PARAM_CONTACT_TYPE) == null) {
                    contactType = 1; // default if not configured
                } else {
                    contactType = Integer.parseInt(parameters.get(PARAM_CONTACT_TYPE).toString());
                }
            } catch (NumberFormatException e) {
                throw new PluggableTaskException("Configured contactType must be an integer!", e);
            }
        }
        return contactType;
    }

    /**
     * Returns the configured external saving event plugin id ({@link IExternalCreditCardStorage})
     * as an integer.
     *
     * @return plugin id of the configured external saving event plugin
     * @throws PluggableTaskException if id cannot be converted to an integer
     */
    public Integer getExternalSavingPluginId() throws PluggableTaskException {
        if (externalSavingPluginId == null) {
            try {
                externalSavingPluginId = Integer.parseInt(parameters.get(PARAM_EXTERNAL_SAVING_PLUGIN_ID).toString());
            } catch (NumberFormatException e) {
                throw new PluggableTaskException("Configured externalSavingPluginId must be an integer!", e);
            }
        }
        return externalSavingPluginId;
    }

    /**
     * @see IInternalEventsTask#process(com.sapienter.jbilling.server.system.event.Event)
     *
     * @param event event to process
     * @throws PluggableTaskException
     */
    public void process(Event event) throws PluggableTaskException {
        PluggableTaskBL<IExternalCreditCardStorage> ptbl = new PluggableTaskBL<IExternalCreditCardStorage>(getExternalSavingPluginId());
        IExternalCreditCardStorage externalCCStorage = ptbl.instantiateTask();

        if (event instanceof NewCreditCardEvent) {
            LOG.debug("Processing NewCreditCardEvent ...");
            NewCreditCardEvent ev = (NewCreditCardEvent) event;

            // only save credit cards associated with users
            if (!ev.getCreditCard().getBaseUsers().isEmpty()) {
                String gateWayKey = externalCCStorage.storeCreditCard(null, ev.getCreditCard());
                updateCreditCard(ev.getCreditCard(), gateWayKey);
            } else {
                LOG.debug("Credit card is not associated with a user (card for payment) - can't save through gateway.");
            }

        } else if (event instanceof NewContactEvent) {
            LOG.debug("Processing NewContactEvent ...");
            NewContactEvent ev = (NewContactEvent) event;

            ContactDTO contact = ev.getContactDto();
            int thisRecordContactType = contact.getContactMap().getContactType().getId();

            if (getContactType() == thisRecordContactType) {
                UserBL userBl = new UserBL(contact.getUserId());
                CreditCardDTO creditCard = userBl.getCreditCard();

                if (creditCard != null &&  !creditCard.getNumber().startsWith("*")) {
                    // credit card has changed or was not previously obscured
                    String gateWayKey = externalCCStorage.storeCreditCard(contact, creditCard);
                    creditCard.setGatewayKey(gateWayKey);
                    creditCard.obscureNumber();
                } else {
                    /*  call the external store without a credit card. It's possible the payment gateway
                        may have some vendor specific recovery facilities, or perhaps they operate on different
                        data? We'll leave it open ended so we don't restrict possible client implementations.
                     */
                    LOG.warn("Cannot determine credit card for storage, invoking external store with contact only");
                    String gateWayKey = externalCCStorage.storeCreditCard(contact, null);
                    updateCreditCard(creditCard, gateWayKey);
                }
            }
        } else {
            throw new PluggableTaskException("Cant not process event " + event);
        }
    }

    private void updateCreditCard(CreditCardDTO creditCard, String gatewayKey) {
        if (gatewayKey != null) {
            LOG.debug("Storing gateway key: " + gatewayKey);
            creditCard.setGatewayKey(gatewayKey);
            creditCard.obscureNumber();
        } else {
            LOG.warn("gateway key returned from external store is null, credit card will not be obscured!");
        }
    }
}