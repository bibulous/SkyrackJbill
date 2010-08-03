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


package com.sapienter.jbilling.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This is a Singleton call that provides the system properties from
 * the jbilling.properties file
 */
public class SystemProperties {
    private static SystemProperties ref;
    private Properties prop = null;
    private static final Logger LOG = Logger.getLogger(SystemProperties.class);


    private SystemProperties() throws IOException {
        prop = new Properties();
        prop.load(SystemProperties.class.getResourceAsStream("/jbilling.properties"));
        LOG.debug("System properties loaded");
    }

    public static SystemProperties getSystemProperties() 
            throws IOException{
        if (ref == null) {
            // it's ok, we can call this constructor
            ref = new SystemProperties();		
        }
        return ref;
    }

    public String get(String key) throws Exception {
        String retValue = prop.getProperty(key);
        // all the system properties have to be there
        if (retValue == null) {
            throw new Exception("Missing system property: " + key);
        }
        //log.debug("Sys prop " + key + " = " + retValue);

        return retValue;
    }
    
    public String get(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
    

    public Object clone()
	    throws CloneNotSupportedException {
        throw new CloneNotSupportedException(); 
        // a singleton should never be cloned
    }
}
