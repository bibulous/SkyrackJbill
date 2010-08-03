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

/**
 * 
 */
package com.sapienter.jbilling.client.util;

import java.util.Map;

public class PreferencesMap {
	private final Map<Integer, String> myMap;

	public PreferencesMap(Map<Integer, String> map){
		myMap = map;
	}
	
	public String getString(Integer key){
		String result = myMap.get(key);
		return result == null ? "" : result;
	}
	
	public Integer getInteger(Integer key){
		String result = myMap.get(key);
		return Integer.valueOf(result);
	}
	
	public boolean getBoolean(Integer key){
		String result = myMap.get(key);
		return "1".equals(result);
	}
	
}