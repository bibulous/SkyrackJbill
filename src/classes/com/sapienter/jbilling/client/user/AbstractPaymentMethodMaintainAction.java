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

import com.sapienter.jbilling.client.util.CrudActionBase;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.payment.IPaymentSessionBean;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

/**
 * Common base class for CreditCard / ACH maintinence action processors. The
 * common thing about them is special handling of the case when doSetup() method
 * can not find the corresponding DTO in the DB.
 * 
 * In contrast to the list-oriented maintainers, where element to edit is
 * choosen from the list, these actions assume that there is only one payment
 * configuration for each method. Thus, in case if none found in the DB, the new
 * one has to be created.
 * 
 * @see setupNotFound()
 * @see postSetup()
 * @see preEdit()
 */
public abstract class AbstractPaymentMethodMaintainAction<DTO> extends
		CrudActionBase<DTO> {
	private final String myNumberField;
	private final String myForwardEdit;

	private final IUserSessionBean myUserSession;
	private final IPaymentSessionBean myPaymentSession;

	public AbstractPaymentMethodMaintainAction(String formName,
			String logFriendlyActionType, String numberField, String forwardEdit) {
		super(formName, logFriendlyActionType);
		myNumberField = numberField;
		myForwardEdit = forwardEdit;

		try {
			myUserSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
			myPaymentSession = (IPaymentSessionBean) Context.getBean(
                    Context.Name.PAYMENT_SESSION);

		} catch (Exception e) {
			throw new SessionInternalError("Initializing "
					+ logFriendlyActionType + " CRUD action: " + e.getMessage());
		}
	}

	@Override
	protected void preEdit() {
		super.preEdit();
		// this default forward used, e.g, in case of validation problems
		String forwardOnValidationProblem = myForwardEdit;
		setForward(forwardOnValidationProblem);
	}

	/**
	 * Called by subclass from setup() indicating that DTO were not found.
	 */
	protected void setupNotFound() {
		// we will use empty field number as a flag for postSetup();
		// @see postSetup
		myForm.set(myNumberField, "");
	}

	@Override
	protected void postSetup() {
		super.postSetup();
		// we are using an empty <number-field> as a flag indicating
		// that DTO were not found
		// in any case, empty <number-field> means that something is wrong
		// so it makes sence to remove the cached form anyway
		// @see setup()

		if ("".equals(myForm.get(myNumberField))) {
			removeFormFromSession();
		}
	}

	@Override
	protected ForwardAndMessage doCreate(DTO dto) {
		throw new UnsupportedOperationException(
				"Can't create payment method. Create mode is not directly supported");
	}

	@Override
	protected void resetCachedList() {
		// single payment method per customer, no lists
	}

	protected IUserSessionBean getUserSession() {
		return myUserSession;
	}

	protected IPaymentSessionBean getPaymentSession() {
		return myPaymentSession;
	}

}
