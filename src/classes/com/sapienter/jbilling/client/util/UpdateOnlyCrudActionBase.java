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

package com.sapienter.jbilling.client.util;

import java.rmi.RemoteException;

public abstract class UpdateOnlyCrudActionBase<DTO> extends CrudActionBase<DTO> {
	private ForwardAndMessage myForwardEdit;
	
	public UpdateOnlyCrudActionBase(String formName, String logFriendlyActionType, String forwardEdit) {
		super(formName, logFriendlyActionType);
		myForwardEdit = new ForwardAndMessage(forwardEdit);
	}
	
	protected final ForwardAndMessage getForwardEdit(){
		return myForwardEdit;
	}
	
	protected final ForwardAndMessage getForwardEdit(String messageKey){
		return new ForwardAndMessage(myForwardEdit.getForward(), messageKey);
	}
	
	@Override
	protected final void resetCaches() {
		// nothing to reset, the form is singleton and should not be reset
	}

	@Override
	protected final void resetCachedList() {
		// not in list
	}

	@Override
	protected final ForwardAndMessage doCreate(DTO dto) throws RemoteException {
		throw wrapError("create", new RemoteException(
				"Can not create -- it is always here"));
	}

	@Override
	protected final ForwardAndMessage doDelete() throws RemoteException {
		throw wrapError("delete", new RemoteException(
				"Can not delete -- it is always here"));
	}
	
	@Override
	protected void preEdit() {
		super.preEdit();
		setForward(getForwardEdit().getForward());
	}
	
	
}
