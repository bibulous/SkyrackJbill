/*
    jBilling - The Enterprise Open Source Billing System
    Copyright (C) 2003-2009 Enterprise jBilling Software Ltd. and Emiliano Conde

    This file is part of jBilling.

    jBilling is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jBilling is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with jBilling.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.sapienter.jbilling.server.payment.db;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.common.Constants;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

/**
 * 
 * @author abimael, Si Bury
 *
 */
public class PaymentAuthorizationDAS extends AbstractDAS<PaymentAuthorizationDTO> {

	public PaymentAuthorizationDTO create(String processor, String code1) {
		
		PaymentAuthorizationDTO auto = new PaymentAuthorizationDTO();
		auto.setProcessor(processor);
		auto.setCode1(code1);
		auto.setCreateDate(Calendar.getInstance().getTime());
		
		return save(auto);
	}

	/*
	 * Method created for checking if a Micropayments was reversed in the last 6 weeks.
	 * Allowing date and code to be passed in makes it more generic and useful.
	 */
	public Collection<PaymentAuthorizationDTO> 
		findByDateAndCode(String approvalCode, Date createDatetime, String dateTimeComparator, Integer entityId) {
		/*TODO should add some form of restriction maybe for entities and/or users?
		 * 
		 */
		Criteria criteria = getSession().createCriteria(PaymentAuthorizationDTO.class);
		criteria.add(Restrictions.eq("approvalCode", approvalCode));
		if (dateTimeComparator.equalsIgnoreCase(">=")) {
			criteria.add(Restrictions.ge("createDate", createDatetime));
		} else if (dateTimeComparator.equalsIgnoreCase("<=")) {
			criteria.add(Restrictions.le("createDate", createDatetime));
		}
		
		return criteria.list();
	}
	
	/*
	 * Method to save object to dB
	 */
	public PaymentAuthorizationDTO save(PaymentAuthorizationDTO paDto) {
		return save(paDto);
	}
}
