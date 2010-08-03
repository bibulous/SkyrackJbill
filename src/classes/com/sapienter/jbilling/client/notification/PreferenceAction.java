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
 * Created on Jul 16, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sapienter.jbilling.client.notification;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.PreferencesCrudActionBase;
import com.sapienter.jbilling.client.util.PreferencesMap;

/**
 * @author Emil
 * 
 */
public class PreferenceAction extends PreferencesCrudActionBase<PreferenceActionContext> {
	private static final String MESSAGE_UPDATED_OK = "notification.preference.update";
	private static final String FORM = "notificationPreference";
	private static final String FORWARD_EDIT = "notificationPreference_edit";

	private static final String FIELD_SELF_DELIVERY = "chbx_self_delivery";
	private static final String FIELD_SHOW_NOTES = "chbx_show_notes";
	private static final String FIELD_ORDER_DAYS_1 = "order_days1";
	private static final String FIELD_ORDER_DAYS_2 = "order_days2";
	private static final String FIELD_ORDER_DAYS_3 = "order_days3";
	private static final String FIELD_INVOICE_REMINDER = "chbx_invoice_reminders";
	private static final String FIELD_FIRST_REMINDER = "first_reminder";
	private static final String FIELD_NEXT_REMINDER = "next_reminder";

	public PreferenceAction() {
		super(FORM, "notification preferences", FORWARD_EDIT);
	}
	
	@Override
	protected PreferenceActionContext doEditFormToDTO() throws RemoteException {
		PreferenceActionContext result = new PreferenceActionContext(); 
		result.setSelfDelivery(getCheckBoxBooleanValue(FIELD_SELF_DELIVERY));
		result.setShowNotes(getCheckBoxBooleanValue(FIELD_SHOW_NOTES));
		result.setInvoiceReminders(getCheckBoxBooleanValue(FIELD_INVOICE_REMINDER));
		result.setOrderDays1(getIntegerFieldValue(FIELD_ORDER_DAYS_1));
		result.setOrderDays2(getIntegerFieldValue(FIELD_ORDER_DAYS_2));
		result.setOrderDays3(getIntegerFieldValue(FIELD_ORDER_DAYS_3));
		
		result.setFirstReminder(getIntegerFieldValue(FIELD_FIRST_REMINDER));
		result.setNextReminder(getIntegerFieldValue(FIELD_NEXT_REMINDER));
		
		if (!result.validateDayValuesIncremental()){
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("notification.orderDays.error"));
		}
		
		if (!result.validateReminders()){
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("notification.reminders.error"));
		}
		
		return result;
	}
	
	@Override
	protected ForwardAndMessage doSetup() throws RemoteException {
        Integer[] ids = new Integer[] { //
        		Constants.PREFERENCE_PAPER_SELF_DELIVERY, 
        		Constants.PREFERENCE_SHOW_NOTE_IN_INVOICE, 
        		Constants.PREFERENCE_DAYS_ORDER_NOTIFICATION_S1, 
        		Constants.PREFERENCE_DAYS_ORDER_NOTIFICATION_S2, 
        		Constants.PREFERENCE_DAYS_ORDER_NOTIFICATION_S3, 
        		Constants.PREFERENCE_FIRST_REMINDER, 
        		Constants.PREFERENCE_NEXT_REMINDER, 
        		Constants.PREFERENCE_USE_INVOICE_REMINDERS,
        };
        PreferencesMap prefs = getEntityPreferences(ids);
        
        myForm.set(FIELD_SELF_DELIVERY, prefs.getBoolean(Constants.PREFERENCE_PAPER_SELF_DELIVERY));
        myForm.set(FIELD_SHOW_NOTES, prefs.getBoolean(Constants.PREFERENCE_SHOW_NOTE_IN_INVOICE));
        myForm.set(FIELD_INVOICE_REMINDER, prefs.getBoolean(Constants.PREFERENCE_USE_INVOICE_REMINDERS));
        
        myForm.set(FIELD_ORDER_DAYS_1, prefs.getString(Constants.PREFERENCE_DAYS_ORDER_NOTIFICATION_S1));
        myForm.set(FIELD_ORDER_DAYS_2, prefs.getString(Constants.PREFERENCE_DAYS_ORDER_NOTIFICATION_S2));
        myForm.set(FIELD_ORDER_DAYS_3, prefs.getString(Constants.PREFERENCE_DAYS_ORDER_NOTIFICATION_S3));
        
        myForm.set(FIELD_FIRST_REMINDER, prefs.getString(Constants.PREFERENCE_FIRST_REMINDER));
        myForm.set(FIELD_NEXT_REMINDER, prefs.getString(Constants.PREFERENCE_NEXT_REMINDER));
        
        return getForwardEdit();
	}

	@Override
	protected ForwardAndMessage doUpdate(PreferenceActionContext dto) throws RemoteException {
		HashMap<Integer, Integer> preferencesMap = dto.asPreferencesMap();
        getUserSession().setEntityParameters(entityId, preferencesMap);
        return getForwardEdit(MESSAGE_UPDATED_OK);
	}

}