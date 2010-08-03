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

package com.sapienter.jbilling.client.user;

import java.rmi.RemoteException;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.PreferencesCrudActionBase;
import com.sapienter.jbilling.client.util.PreferencesMap;

public class PartnerDefaultsAction extends PreferencesCrudActionBase<PartnerDefaultsActionContext> {
	private static final String FORM = "partnerDefault";
	
	private static final String FIELD_RATE ="rate";
	private static final String FIELD_FEE = "fee";
	private static final String FIELD_FEE_CURRENCY="fee_currency";
	private static final String FIELD_ONE_TIME ="chbx_one_time";
	private static final String FIELD_PERIOD_UNITS ="period_unit_id";
	private static final String FIELD_PERIOD_VALUE ="period_value";
	private static final String FIELD_PROCESS ="chbx_process";
	private static final String FIELD_CLERK ="clerk";
	
	private static final String FORWARD_EDIT = "partnerDefault_edit";
	private static final String MESSAGE_UPDATE_SUCCESS = "partner.default.updated";
	
	public PartnerDefaultsAction(){
		super(FORM, "partner defaults", FORWARD_EDIT);
	}
	
	@Override
	protected PartnerDefaultsActionContext doEditFormToDTO() {
		PartnerDefaultsActionContext dto = new PartnerDefaultsActionContext();
		dto.setRate(string2float((String)myForm.get(FIELD_RATE)));
		dto.setFee(string2float((String)myForm.get(FIELD_FEE)));
		dto.setFeeCurrency((Integer) myForm.get(FIELD_FEE_CURRENCY));
		dto.setOneTime(getCheckBoxBooleanValue(FIELD_ONE_TIME));
        dto.setPeriodUnitId((Integer) myForm.get(FIELD_PERIOD_UNITS));
        dto.setPeriodValue(getIntegerFieldValue(FIELD_PERIOD_VALUE));
        dto.setProcess(getCheckBoxBooleanValue(FIELD_PROCESS));
        dto.setClerk(getIntegerFieldValue(FIELD_CLERK));
        return dto;
	}
	
	@Override
	protected ForwardAndMessage doUpdate(PartnerDefaultsActionContext dto) throws RemoteException {
		getUserSession().setEntityParameters(entityId, dto.asPreferencesMap());
		return getForwardEdit(MESSAGE_UPDATE_SUCCESS);
	}
	
	@Override
	protected ForwardAndMessage doSetup() throws RemoteException {
        // set up[ which preferences do we need
        Integer[] ids = new Integer[] {
        		Constants.PREFERENCE_PART_DEF_RATE, 
        		Constants.PREFERENCE_PART_DEF_FEE, 
        		Constants.PREFERENCE_PART_DEF_FEE_CURR,
        		Constants.PREFERENCE_PART_DEF_ONE_TIME,
        		Constants.PREFERENCE_PART_DEF_PER_UNIT,
        		Constants.PREFERENCE_PART_DEF_PER_VALUE,
        		Constants.PREFERENCE_PART_DEF_AUTOMATIC,
        		Constants.PREFERENCE_PART_DEF_CLERK,
        };
        
        PreferencesMap prefs = getEntityPreferences(ids);
        myForm.set(FIELD_RATE, prefs.getString(Constants.PREFERENCE_PART_DEF_RATE));
        myForm.set(FIELD_FEE, prefs.getString(Constants.PREFERENCE_PART_DEF_FEE));
        myForm.set(FIELD_FEE_CURRENCY, prefs.getInteger(Constants.PREFERENCE_PART_DEF_FEE_CURR));
        myForm.set(FIELD_ONE_TIME, prefs.getBoolean(Constants.PREFERENCE_PART_DEF_ONE_TIME));
        myForm.set(FIELD_PERIOD_UNITS, prefs.getInteger(Constants.PREFERENCE_PART_DEF_PER_UNIT));
        myForm.set(FIELD_PERIOD_VALUE, prefs.getString(Constants.PREFERENCE_PART_DEF_PER_VALUE));
        myForm.set(FIELD_PROCESS, prefs.getBoolean(Constants.PREFERENCE_PART_DEF_AUTOMATIC));
        myForm.set(FIELD_CLERK, prefs.getString(Constants.PREFERENCE_PART_DEF_CLERK));
        
        return getForwardEdit();
	}
	
}
