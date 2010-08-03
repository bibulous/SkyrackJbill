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

package com.sapienter.jbilling.client.system;

import java.util.HashMap;
import java.util.Map;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.CrudAction;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class BrandingMaintainAction extends CrudAction {
	private static final String FORM_BRANDING = "branding";
	private static final String FIELD_CSS = "css";
	private static final String FIELD_LOGO = "logo";
	private static final String MESSAGE_SUCCESS = "system.branding.updated";
	private static final String FORWARD_SUCCESS = "branding_edit";

	private final IUserSessionBean myUserSession;

	public BrandingMaintainAction() {
		setFormName(FORM_BRANDING);
		try {
			myUserSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
		} catch (Exception e) {
			throw new SessionInternalError(
					"Initializing branding CRUD action: " + e.getMessage());
		}
	}

	@Override
	public void setup() {
		// set up which preferences do we need
		Integer[] preferenceIds = new Integer[] { //
				Constants.PREFERENCE_CSS_LOCATION, //
				Constants.PREFERENCE_LOGO_LOCATION, //
		};
		// get'em
		Map<?, ?> result;

        result = myUserSession.getEntityParameters(entityId, preferenceIds);

		myForm.set(FIELD_CSS, stringNotNull(result
				.get(Constants.PREFERENCE_CSS_LOCATION)));
		myForm.set(FIELD_LOGO, stringNotNull(result
				.get(Constants.PREFERENCE_LOGO_LOCATION)));
		
		setForward(FORWARD_SUCCESS);
	}

	@Override
	public String delete() {
		throw new UnsupportedOperationException(
				"Can't delete branding. Delete mode is not supported");
	}

	@Override
	public void create(Object dtoHolder) {
		throw new UnsupportedOperationException(
				"Can't create branding. Create mode is not supported");
	}
	
	@Override
	public void reset() {
		//do nothing, reset is not supported
	}
	
	@Override
	public boolean otherAction(String action) {
		return false;
	}

	@Override
	public String update(Object dtoHolder) {
		CssAndLogo cssAndLogo = (CssAndLogo) dtoHolder;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(Constants.PREFERENCE_CSS_LOCATION, cssAndLogo.getCss());
		map.put(Constants.PREFERENCE_LOGO_LOCATION, cssAndLogo.getLogo());
        myUserSession.setEntityParameters(entityId, map);
		setForward(FORWARD_SUCCESS);
		return MESSAGE_SUCCESS;
	}

	@Override
	public Object editFormToDTO() {
		return new CssAndLogo((String) myForm.get(FIELD_CSS), (String) myForm
				.get(FIELD_LOGO));
	}

	protected static String stringNotNull(Object object) {
		return object == null ? "" : String.valueOf(object);
	}

	protected static String safeTrim(String text) {
		return stringNotNull(text).trim();
	}

	private static class CssAndLogo {
		private final String myLogo;
		private final String myCss;

		public CssAndLogo(String css, String logo) {
			myCss = safeTrim(css);
			myLogo = safeTrim(logo);
		}

		public String getCss() {
			return myCss;
		}

		public String getLogo() {
			return myLogo;
		}
	}

}
