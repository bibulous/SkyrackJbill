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

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.Resources;

import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.JBCrypto;
import com.sapienter.jbilling.common.JNDILookup;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.UserSQL;
import com.sapienter.jbilling.server.util.Context;
import java.util.ArrayList;

public class RepeatedPasswordValidator {

    private static final Logger LOG = Logger.getLogger(RepeatedPasswordValidator.class);

	/**
     * Queries the event_log table to check whether the user has already used 
     * in the past the password he's trying to set now.
     * @param userId Id of the user whose password is being changed.
     * @return An array of <code>java.lang.String</code> containing the passwords
     * recently used by this user.
     */
    private static String[] getPasswords(Integer userId)
    		throws SQLException, NamingException {

    	String[] passw = null;
    	CachedRowSet cachedResults = new CachedRowSet();
    	JNDILookup jndi = JNDILookup.getFactory();
    	Connection conn = jndi.lookUpDataSource().getConnection();
    	cachedResults.setCommand(UserSQL.findUsedPasswords);
    	GregorianCalendar date = new GregorianCalendar();
    	date.add(GregorianCalendar.YEAR, -2);
    	cachedResults.setDate(1, new Date(date.getTimeInMillis()));
    	cachedResults.setInt(2, userId);
    	cachedResults.execute(conn);

    	List<String> result = new ArrayList<String>();

    	while (cachedResults.next()) {
    		result.add(cachedResults.getString(1));
    	}
    	
    	if (!result.isEmpty()) {
    		passw = new String[result.size()];
    		int index = 0;
    		for (Iterator i = result.iterator(); i.hasNext(); ) {
    			passw[index] = (String)i.next();
    			index++;
    		}
    	}
    	
		conn.close();
    	return passw;
    }


    /**
     * Perform the basic validation of this validator: checks whether the
     * password being changed has been previously used.
     * @param userId Id of the user changing passwords.
     * @param role Role of the user changing passwords.
     * @param value New password being set.
     * @return <code>true</code> if the validation passes and the password
     * has not been used in the last two years. Otherwise, it returns 
     * <code>false</code>.
     */
    public static boolean basicValidation(
    		Integer userId,
    		Integer role,
    		String value) {

    	boolean result = true;

    	// Encrypt the password to check it against previous passwords,
    	// which should be encrypted as well.
    	JBCrypto passwordCryptoService = JBCrypto.getPasswordCrypto(role);
    	String newPassword = passwordCryptoService.encrypt(value);
		try {
			String[] oldPasswords = getPasswords(userId);
			if (oldPasswords != null) {
				for (int i = 0; i < oldPasswords.length; i++) {
					if (oldPasswords[i].equals(newPassword)) {
						result = false;
					}
				}
			}
		} catch (Exception e) {
            LOG.error("Exception validating for repeated password ", e);
			result = false;
		}
		return result;
    }


    /**
     * Struts validator that checks whether the password that is being set
     * by the user has been already used in the last two years.
     * @param bean
     * @param va
     * @param field
     * @param errors
     * @param request
     * @param application
     * @return <code>true</code> if the validation passes and the password
     * has not been used, otherwise <code>false</code>.
     */
	public static boolean validateRepeatedPassword(
			Object bean,
			ValidatorAction va, 
			Field field,
			ActionErrors errors,
			HttpServletRequest request, 
			ServletContext application) {

		boolean result = true;
		String value = ValidatorUtils.getValueAsString(
				bean, field.getProperty());

		// Determine the id and role of the user changing his password
		Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		Integer userRole = null;
		try {
            IUserSessionBean user = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
			userRole = user.getUserDTOEx(userId).getMainRoleId();
		} catch (Exception e) {
			result = false;
		}
		// Perform the check in the event_log table to see if the user has
		// previously used the password he's trying to set now.
		if (result && !GenericValidator.isBlankOrNull(value)) {
			result = basicValidation(userId, userRole, value);
		}
		
		if (result == false) {
			errors.add(field.getKey(),
				Resources.getActionError(
						request,
						va,
						field));
		}

		return result;
	}
}
