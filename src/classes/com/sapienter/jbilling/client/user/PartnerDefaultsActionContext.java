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

package com.sapienter.jbilling.client.user;

import java.util.HashMap;

import com.sapienter.jbilling.client.util.Constants;

public class PartnerDefaultsActionContext {

	private Float myRate;
	private Float myFee;
	private Integer myFeeCurrency;
	private Integer myPeriodUnitId;
	private Integer myPeriod;
	private boolean myProcess;
	private boolean myOneTime;
	private Integer myClerk;

	public void setRate(Float rate) {
		myRate = rate;
	}

	public void setFee(Float fee) {
		myFee = fee;
	}

	public void setFeeCurrency(Integer feeCurrency) {
		myFeeCurrency = feeCurrency;
	}

	public void setOneTime(boolean oneTime) {
		myOneTime = oneTime;
	}

	public void setPeriodUnitId(Integer periodUnitId) {
		myPeriodUnitId = periodUnitId;
	}

	public void setPeriodValue(Integer period) {
		myPeriod = period;
	}

	public void setProcess(Boolean process) {
		myProcess = process;
	}

	public void setClerk(Integer clerk) {
		myClerk = clerk;
	}
	
	public HashMap<Integer, Number> asPreferencesMap(){
		HashMap<Integer, Number> result = new HashMap<Integer, Number>();
        result.put(Constants.PREFERENCE_PART_DEF_RATE, myRate);
        result.put(Constants.PREFERENCE_PART_DEF_FEE, myFee);
        result.put(Constants.PREFERENCE_PART_DEF_FEE_CURR, myFeeCurrency);
        result.put(Constants.PREFERENCE_PART_DEF_ONE_TIME, asPreferenceValue(myOneTime));
        result.put(Constants.PREFERENCE_PART_DEF_PER_UNIT, myPeriodUnitId);
        result.put(Constants.PREFERENCE_PART_DEF_PER_VALUE, myPeriod);
        result.put(Constants.PREFERENCE_PART_DEF_AUTOMATIC, asPreferenceValue(myProcess));
        result.put(Constants.PREFERENCE_PART_DEF_CLERK, myClerk);
        return result;
	}
	
	private static final int asPreferenceValue(boolean value){
		return value ? 1 : 0;
	}
	
}
