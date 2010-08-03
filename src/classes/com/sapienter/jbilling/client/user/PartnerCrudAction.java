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

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.CrudActionBase;
import com.sapienter.jbilling.client.util.PreferencesMap;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;

public class PartnerCrudAction extends CrudActionBase<Partner> {
	private static final String FORM = "partner";

	private static final String FIELD_BALANCE = "balance";
	private static final String FIELD_RATE = "rate";
	private static final String FIELD_FEE = "fee";
	private static final String FIELD_FEE_CURRENCY = "fee_currency";
	private static final String FIELD_ONE_TIME = "chbx_one_time";
	private static final String FIELD_PERIOD_UNIT_ID = "period_unit_id";
	private static final String FIELD_PERIOD_VALUE = "period_value";
	private static final String FIELD_GROUP_PAYOUT = "payout";
	private static final String FIELD_PROCESS = "chbx_process";
	private static final String FIELD_CLERK = "clerk";
	
	private static final String FORWARD_EDIT = "partner_edit";
	private static final String FORWARD_LIST = "partner_list";
	private static final String FORWARD_RANGES = "partner_ranges";
	private static final String FORWARD_CREATE = "partner_create";

	private static final String MESSAGE_CREATED = "partner.created";
	private static final String MESSAGE_UPDATED = "partner.updated";
	
	private final IUserSessionBean myUserSession;
	
	public PartnerCrudAction(IUserSessionBean userSession) {
		super(FORM, "partner");
		myUserSession = userSession;
	}
	
	
	@Override
	protected ForwardAndMessage doCreate(Partner dto) throws RemoteException {
        // get the user dto from the session. This is the dto with the
        // info of the user to create
        UserDTOEx user = (UserDTOEx) session.getAttribute(Constants.SESSION_CUSTOMER_DTO);
        ContactDTOEx contact = (ContactDTOEx) session.getAttribute(Constants.SESSION_CUSTOMER_CONTACT_DTO);
        // add the partner information just submited to the user to be
        // created
        user.setPartner(dto);
        // make the call
        Integer newUserID = myUserSession.create(user, contact);
        LOG.debug("Partner created = " + newUserID);
        session.setAttribute(Constants.SESSION_USER_ID, newUserID);
        
        session.setAttribute(Constants.SESSION_PARTNER_DTO, 
                myUserSession.getUserDTOEx(newUserID).getPartner());
        return (request.getParameter("ranges") == null) ?
        		new ForwardAndMessage(FORWARD_LIST, MESSAGE_CREATED) :
        		new ForwardAndMessage(FORWARD_RANGES, MESSAGE_CREATED);
	}
	
	@Override
	protected ForwardAndMessage doDelete() throws RemoteException {
		throw new UnsupportedOperationException("Delete mode is not directly supported for partners");
	}

	@Override
	protected void preEdit() {
		super.preEdit();
		setForward(FORWARD_EDIT);
	}
	
	@Override
	protected Partner doEditFormToDTO() throws RemoteException {
		Partner dto = (Partner)session.getAttribute(Constants.SESSION_PARTNER_DTO);
        if (dto == null) {
            dto = new Partner();
        }
        dto.setBalance(string2decimal((String) myForm.get(FIELD_BALANCE)));
        dto.setPercentageRate(string2decimal((String) myForm.get(FIELD_RATE)));
        dto.setReferralFee(string2decimal((String) myForm.get(FIELD_FEE)));
        if (dto.getReferralFee() != null) {
            dto.setFeeCurrency(new CurrencyDTO((Integer) myForm.get(FIELD_FEE_CURRENCY)));
        }
        dto.setPeriodUnit(new PeriodUnitDTO((Integer) myForm.get(FIELD_PERIOD_UNIT_ID)));
        dto.setPeriodValue(Integer.valueOf((String) myForm.get(FIELD_PERIOD_VALUE)));
        dto.setNextPayoutDate(parseDate(FIELD_GROUP_PAYOUT, "partner.prompt.nextPayout"));
        
        dto.setAutomaticProcess(valueOfCheckBox(FIELD_PROCESS));
        dto.setOneTime(valueOfCheckBox(FIELD_ONE_TIME));

        Integer clerkId = Integer.valueOf((String) myForm.get(FIELD_CLERK));
        UserDTOEx clerk = getUser(clerkId);
        if (clerk == null) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("partner.error.clerknotfound"));
        }
        if (!entityId.equals(clerk.getEntityId()) || 
                clerk.getDeleted() == 1 ||
                clerk.getMainRoleId().intValue() > 
                Constants.TYPE_CLERK.intValue()) {
            	
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("partner.error.clerkinvalid"));
        } else {
            dto.setRelatedClerkUserId(clerkId);
        }
        return dto;
	}
	
	@Override
	protected ForwardAndMessage doUpdate(Partner dto) throws RemoteException {
        dto.setId((Integer) session.getAttribute(Constants.SESSION_PARTNER_ID));
        myUserSession.updatePartner(executorId, dto);
        return (request.getParameter("ranges") == null) ?
        		new ForwardAndMessage(FORWARD_LIST, MESSAGE_UPDATED) :
        		new ForwardAndMessage(FORWARD_RANGES, MESSAGE_CREATED);
	}
	
	@Override
	protected void resetCachedList() {
        session.removeAttribute(Constants.SESSION_LIST_KEY + "partner");
	}
	
	@Override
	protected ForwardAndMessage doSetup() throws RemoteException {
		final ForwardAndMessage result;
        
		Integer partnerId = (Integer) session.getAttribute(
                Constants.SESSION_PARTNER_ID);
        
        Partner partner;
        if (partnerId != null) {
            partner = myUserSession.getPartnerDTO(partnerId);
            result = new ForwardAndMessage(FORWARD_EDIT);
        } else {
            partner = createPartnerFromDefaults();
            result = new ForwardAndMessage(FORWARD_CREATE);
        }
        
        myForm.set(FIELD_BALANCE, decimal2string(partner.getBalance()));
        if (partner.getPercentageRate() != null) {
            myForm.set(FIELD_RATE, decimal2string(partner.getPercentageRate()));
        }
        if (partner.getReferralFee() != null) {
            myForm.set(FIELD_FEE, decimal2string(partner.getReferralFee()));
        }
        myForm.set(FIELD_FEE_CURRENCY, partner.getFeeCurrency().getId());
        myForm.set(FIELD_ONE_TIME, Integer.valueOf(1).equals(partner.getOneTime()));
        myForm.set(FIELD_PERIOD_UNIT_ID, partner.getPeriodUnit().getId());
        myForm.set(FIELD_PERIOD_VALUE, String.valueOf(partner.getPeriodValue()));
        myForm.set(FIELD_PROCESS, Integer.valueOf(1).equals(partner.getAutomaticProcess()));
        myForm.set(FIELD_CLERK, partner.getRelatedClerkUserId().toString());

        setFormDate(FIELD_GROUP_PAYOUT, partner.getNextPayoutDate());
        
        return result;
	}
	
	private Partner createPartnerFromDefaults() throws RemoteException {
		Partner partner;
		partner = new Partner();
		// set the values from the preferences (defaults)
		Integer[] preferenceIds = new Integer[] {
				Constants.PREFERENCE_PART_DEF_RATE, 
				Constants.PREFERENCE_PART_DEF_FEE, 
				Constants.PREFERENCE_PART_DEF_FEE_CURR, 
				Constants.PREFERENCE_PART_DEF_ONE_TIME, 
				Constants.PREFERENCE_PART_DEF_PER_UNIT, 
				Constants.PREFERENCE_PART_DEF_PER_VALUE, 
				Constants.PREFERENCE_PART_DEF_AUTOMATIC, 
				Constants.PREFERENCE_PART_DEF_CLERK, 
		};
      
		PreferencesMap prefs = mapEntityParameters(preferenceIds);
		partner.setPercentageRate(string2decimal(prefs.getString(Constants.PREFERENCE_PART_DEF_RATE)));
		partner.setReferralFee(string2decimal(prefs.getString(Constants.PREFERENCE_PART_DEF_FEE)));
		if (partner.getReferralFee() != null){
			partner.setFeeCurrency(new CurrencyDTO(prefs.getInteger(Constants.PREFERENCE_PART_DEF_FEE_CURR)));
		}
		partner.setOneTime(prefs.getInteger(Constants.PREFERENCE_PART_DEF_ONE_TIME));
		partner.setPeriodUnit(new PeriodUnitDTO(prefs.getInteger(Constants.PREFERENCE_PART_DEF_PER_UNIT)));
		partner.setPeriodValue(prefs.getInteger(Constants.PREFERENCE_PART_DEF_PER_VALUE));
		partner.setAutomaticProcess(prefs.getInteger(Constants.PREFERENCE_PART_DEF_AUTOMATIC));
		partner.setRelatedClerkUserId(prefs.getInteger(Constants.PREFERENCE_PART_DEF_CLERK));
		// some that are not preferences
		partner.setBalance(BigDecimal.ZERO);
		return partner;
	}

	@SuppressWarnings("unchecked")
	private PreferencesMap mapEntityParameters(Integer[] ids) throws RemoteException {
		HashMap<Integer, String> result = myUserSession.getEntityParameters(entityId, ids);
		return new PreferencesMap(result);
	}

	private Integer valueOfCheckBox(String fieldName){
		Boolean value = (Boolean) myForm.get(fieldName);
		return value ? 1 : 0;
	}
	
	
}
