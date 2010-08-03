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

package com.sapienter.jbilling.server.util.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 *
 * @author emilc
 */
public class APILogger implements MethodBeforeAdvice, AfterReturningAdvice {

    private static final Logger LOG = Logger.getLogger(APILogger.class);

    public void before(Method method, Object[] args, Object target) throws Throwable {
        LOG.debug("Call to " + method.getName() + " parameters: " + Arrays.toString(args));
    }

    public void afterReturning(Object ret, Method method, Object[] args, Object target) throws Throwable {
        LOG.debug("Done call to " + method.getName() + " returning: " + ret);
    }
}
