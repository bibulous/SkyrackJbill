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

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

public abstract class PreferencesCrudActionBase<DTO> extends UpdateOnlyCrudActionBase<DTO> {
	private final IUserSessionBean myUserSession;
	
	public PreferencesCrudActionBase(String formName, String logFriendlyActionType, String forwardEdit) {
		super(formName, logFriendlyActionType, forwardEdit);
		try {
			myUserSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
		} catch (Exception e) {
			throw new SessionInternalError(
					"Initializing " + logFriendlyActionType + " CRUD action: "
							+ e.getMessage());
		}
	}

	protected final IUserSessionBean getUserSession(){
		return myUserSession;
	}
	
	@SuppressWarnings("unchecked")
	protected final PreferencesMap getEntityPreferences(Integer[] ids) throws RemoteException {
		return new PreferencesMap(myUserSession.getEntityParameters(entityId, ids));
	}
	
	protected final boolean getCheckBoxBooleanValue(String fieldName) {
		return (Boolean) myForm.get(fieldName);
	}

}
