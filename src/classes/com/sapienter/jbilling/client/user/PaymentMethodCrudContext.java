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

import com.sapienter.jbilling.server.user.db.AchDTO;
import com.sapienter.jbilling.server.user.db.CreditCardDTO;


public class PaymentMethodCrudContext<DTO> {
	private final DTO myDto;
	private Boolean myIsAutomaticPayment;
	
	public PaymentMethodCrudContext(DTO dto){
		myDto = dto;
	}

	public void setIsAutomaticPayment(boolean value){
		myIsAutomaticPayment = Boolean.valueOf(value);
	}
	
	public Boolean isAutomaticPayment() {
		return myIsAutomaticPayment;
	}
	
	public DTO getDto(){
		return myDto;
	}

	//just aliases 
	public static class CCContext extends PaymentMethodCrudContext<CreditCardDTO> {
		public CCContext(CreditCardDTO dto) {
			super(dto);
		}
	}
	
	public static class AchContext extends PaymentMethodCrudContext<AchDTO> {
		public AchContext(AchDTO dto){
			super(dto);
		}
	}
	
}
