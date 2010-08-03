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
package com.sapienter.jbilling.server.user.validator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.Resources;

public class AlphaNumValidator {

	private static String NUMBERS = ".*[0-9].*";
	private static String LETTERS = ".*[A-Za-z].*";

	
	public static boolean basicValidation(String password) {
		boolean result = true;
		if (password == null || password.equals("")) {
			return true;
		}
		if (!password.matches(LETTERS) || !password.matches(NUMBERS)) {
			result = false;
		}
		return result;
	}


	public static boolean validateAlphaNum(
			Object bean,
			ValidatorAction va, 
			Field field,
			ActionErrors errors,
			HttpServletRequest request, 
			ServletContext application) {

		String value = ValidatorUtils.getValueAsString(
				bean, field.getProperty());

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!basicValidation(value)) {
					errors.add(field.getKey(),
							Resources.getActionError(
									request,
									va,
									field));

					return false;
				}
			} catch (Exception e) {
				errors.add(field.getKey(),
						Resources.getActionError(
								request,
								va,
								field));
				return false;
			}
		}
		return true;
	}
	
}
