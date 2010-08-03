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
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.user.PaymentMethodCrudContext.CCContext;
import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.db.CreditCardDTO;

public class CreditCardMaintainAction extends
		AbstractPaymentMethodMaintainAction<CCContext> {
	private static final String FORM_CREDIT_CARD = "creditCard";

	private static final String FIELD_NUMBER = "number";
	private static final String FIELD_GROUP_EXPIRY = "expiry";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_USE_CARD = "chbx_use_this";

	private static final String MESSAGE_UPDATE_SUCCESS = "creditcard.update.done";
	private static final String FORWARD_EDIT = "creditCard_edit";
	private static final String FORWARD_DONE = "creditCard_done";
	private static final String FORWARD_DELETED = "creditCard_deleted";

	public CreditCardMaintainAction() {
		super(FORM_CREDIT_CARD, "credit card", FIELD_NUMBER, FORWARD_EDIT);
	}

	@Override
	protected ForwardAndMessage doSetup() throws RemoteException {
		Integer userId = customGetUserId();
		// now only one credit card is supported per user
		CreditCardDTO dto = getUserSession().getCreditCard(userId);
		Integer type = getUserSession().getAuthPaymentType(userId);
		boolean use = Constants.AUTO_PAYMENT_TYPE_CC.equals(type);
		if (dto != null) { // it could be that the user has no cc yet
			String ccNumber = maskCreditCardNumberIfNeeded(dto);
			myForm.set(FIELD_NUMBER, ccNumber);
			setFormDate(FIELD_GROUP_EXPIRY, dto.getCcExpiry());
			myForm.set(FIELD_NAME, dto.getName());
			myForm.set(FIELD_USE_CARD, use);
		} else {
			setupNotFound();
		}
		return new ForwardAndMessage(FORWARD_EDIT);
	}

	@Override
	protected CCContext doEditFormToDTO() throws RemoteException {
		CreditCardDTO dto = new CreditCardDTO();
		dto.setName((String) myForm.get(FIELD_NAME));
		dto.setNumber((String) myForm.get(FIELD_NUMBER));
		myForm.set(FIELD_GROUP_EXPIRY + "_day", "01"); // to complete the date
		dto.setCcExpiry(parseDate(FIELD_GROUP_EXPIRY, "payment.cc.date"));

		// validate the expiry date
		if (dto.getCcExpiry() != null) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dto.getCcExpiry());
			cal.add(GregorianCalendar.MONTH, 1); // add 1 month
			if (Calendar.getInstance().getTime().after(cal.getTime())) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"creditcard.error.expired", "payment.cc.date"));
			}
		}

		// the credit card number is required
		required(dto.getNumber(), "payment.cc.number");
		if (errors.isEmpty()) {
			// verify that this entity actually accepts this kind of
			// payment method
			Integer paymentMethod = Util.getPaymentMethod(dto.getNumber());
			if (!getPaymentSession().isMethodAccepted(entityId, paymentMethod)) {
				errors.add( //
						ActionErrors.GLOBAL_ERROR, //
						new ActionError("payment.error.notAccepted",
								"payment.method"));
			}
		}

		CCContext result = new CCContext(dto);
		// update the autimatic payment type for this customer
		Boolean shouldUse = (Boolean) myForm.get(FIELD_USE_CARD);
		if (shouldUse != null) {
			result.setIsAutomaticPayment(shouldUse);
		}

		return result;
	}

	@Override
	protected ForwardAndMessage doUpdate(CCContext dto)
			throws RemoteException {
		Integer userId = commonGetUserId();
		getUserSession().updateCreditCard(executorId, userId, dto.getDto());
		getUserSession().setAuthPaymentType(userId,
				Constants.AUTO_PAYMENT_TYPE_CC, dto.isAutomaticPayment());
		return new ForwardAndMessage(FORWARD_DONE, MESSAGE_UPDATE_SUCCESS);
	}

	@Override
	protected ForwardAndMessage doDelete() throws RemoteException {
		getUserSession().deleteCreditCard(executorId, commonGetUserId());
		// no need to modify the auto payment type. If it is cc and
		// there's no cc the payment will be bypassed
		return new ForwardAndMessage(FORWARD_DELETED);
	}

	private Integer customGetUserId() {
		String fromRequest = request.getParameter("userId");
		if (fromRequest != null) {
			return Integer.valueOf(fromRequest);
		}
		return commonGetUserId();
	}

	private Integer commonGetUserId() {
		return (Integer) session.getAttribute(Constants.SESSION_USER_ID);
	}

	private UserDTOEx getUserDto() {
		return (UserDTOEx) session.getAttribute(Constants.SESSION_USER_DTO);
	}

	private String maskCreditCardNumberIfNeeded(CreditCardDTO dto)
			throws RemoteException {
		// if the user is not allowed to see cc info
		// or the entity does not want anybody to see cc numbers

		boolean maskNeeded = getUserDto().isGranted(
				Constants.P_USER_EDIT_VIEW_CC);
		if (!maskNeeded) {
			final Integer HIDE_CC_NUMBERS = com.sapienter.jbilling.server.util.Constants.PREFERENCE_HIDE_CC_NUMBERS;
			String maskAll = getUserSession().getEntityPreference(//
					entityId, HIDE_CC_NUMBERS);
			maskNeeded = "1".equals(maskAll);
		}

		String result = dto.getNumber();
		if (maskNeeded) {
			result = "************" + result.substring(result.length() - 4);
		}
		return result;
	}
}
