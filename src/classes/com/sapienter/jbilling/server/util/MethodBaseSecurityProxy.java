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

/*
 * Created on Dec 24, 2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.server.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import org.springframework.aop.MethodBeforeAdvice;

import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.UserDTOEx;

/**
 * @author Emil
 */
public abstract class MethodBaseSecurityProxy implements MethodBeforeAdvice {

    protected Logger log = null; 
    private Method methods[] = null;
    
    protected boolean isMethodPresent(Method m) {
        boolean retValue = false;
        
        for (int f = 0; f < methods.length; f++) {
            if (methods[f].equals(m)) {
                retValue = true;
                break;
            }
        }
        
        return retValue;
    }
    
    /**
     * @param methods
     */
    public void setMethods(Method[] methods) {
        this.methods = methods;
    }
    
    /**
     * This method is pretty heavy ... it should be useing a cached mapped.
     * @param userId
     * @param permission
     */
    protected void validatePermission(Integer userId, Integer permission) {
        UserBL user;
        user = new UserBL(userId);
        UserDTOEx dto = new UserDTOEx();
        dto.setAllPermissions(user.getPermissions());
        if (!dto.isGranted(permission)) {
            throw new SecurityException("Permission " + permission +
                    " not grated for " + userId);
        }
    }
}
