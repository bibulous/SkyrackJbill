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

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.Resources;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class NoUserInfoInPasswordValidator {
    
    private static final Logger LOG = Logger.getLogger(NoUserInfoInPasswordValidator.class);

	/**
	 * This method verifies that the password passed as parameter does not
	 * contain any user information as retrieved from the user contact
	 * record.
	 * @param userId User ID of the user whose password is being verified.
	 * @param password the new password that is being validated.
	 * @return <code>true</code> if the password passes the verification,
	 * otherwise returns <code>false</code>.
	 */
	public static boolean basicValidation(Object dto, String password) {
		boolean retVal = true;
		try {
			if (dto == null) {
				retVal = false;
			}
			else {
				// Check all the fields against the password by using reflection.
				Class cl = dto.getClass();
				Method m[] = cl.getMethods();
				for (int i = 0; i < m.length && retVal == true; i++) {
					// We're interested only in the getter methods
					// that return a String value.
					if ( m[i].getReturnType() != String.class ||
						!m[i].getName().startsWith("get")) {
						continue;
					}
					
					// We can now invoke the method via reflection to retrieve
					// the value.
					String temp = (String)m[i].invoke(dto);
					if (temp == null) {
						continue;
					}
					
					// Now check the value against the provided password.
					if (temp.equalsIgnoreCase(password)) {
						retVal = false;
						break;
					}
					/*
					 * Now, break up the returned values into words and check
					 * those. This intercepts a case where, for example, the
					 * contact's name is "John Michael Doe" and the password
					 * is set to be only "michael".
					 */
					String te[] = temp.split(" ");
					for (int j = 0; j < te.length; j++) {
						if (te[j].equalsIgnoreCase(password)) {
							retVal = false;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
            LOG.error("Exception validating for contact in password ", e);
			retVal = false;
		}
		return retVal;
	}


	/**
	 * Struts validator. This method retrieves the parameters necessary for
	 * validating the password passed and calls basicValidation() to verify
	 * the value. As such, it only represents a struts wrapper to
	 * the core validation routine.
	 * @return
	 */
	public static boolean validateNoUserInfo(
			Object bean,
			ValidatorAction va, 
			Field field,
			ActionErrors errors,
			HttpServletRequest request, 
			ServletContext application) {
		
		boolean retVal = true;

		try {

			String value = ValidatorUtils.getValueAsString(
						bean, field.getProperty());

			if (!GenericValidator.isBlankOrNull(value)) {
				IUserSessionBean user = (IUserSessionBean) Context.getBean(
                        Context.Name.USER_SESSION);
				ContactDTOEx dto = user.getPrimaryContactDTO(
						(Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
				if (dto != null) {
					retVal = basicValidation(dto, value);
				}
			}

		} catch (Exception e) {
			retVal = false;
		}
		if (retVal == false) {
			errors.add(field.getKey(),
					Resources.getActionError(
							request,
							va,
							field));
		}
		return retVal;
	}
}
