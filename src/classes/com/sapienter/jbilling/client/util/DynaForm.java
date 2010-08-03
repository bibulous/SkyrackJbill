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
 * Created on Aug 15, 2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.client.util;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.FormPropertyConfig;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * @author Emil
 */
public class DynaForm extends DynaValidatorForm {

    /**
     * 
     */
    public DynaForm() {
        super();
    }

    public void reset(ActionMapping mapping,
            javax.servlet.http.HttpServletRequest request) {
        
        String name = mapping.getName();
        if (name == null) {
            return;
        }
        FormBeanConfig config = mapping.getModuleConfig().
                findFormBeanConfig(name);
        if (config == null) {
            return;
        }
        
        FormPropertyConfig props[] = config.findFormPropertyConfigs();
        for (int i = 0; i < props.length; i++) {
            if (props[i].getName().startsWith("chbx_")) {
                set(props[i].getName(), new Boolean(false));
            }
        }
    }
}
