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
 * Created on Jun 28, 2004
 *
 */
package com.sapienter.jbilling.server.notification;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;

import com.sapienter.jbilling.server.entity.InvoiceLineDTO;
import com.sapienter.jbilling.server.invoice.InvoiceDTO;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;

/**
 * @author Emil
 *
 */
public class JReportTest extends TestCase {

	public void testGeneratePaperInvoice() {
		try {
			InvoiceDTO invoice = new InvoiceDTO();
			invoice.setId(new Integer(2910));
			GregorianCalendar cal = new GregorianCalendar();
			invoice.setCreateDateTime(cal.getTime());
			cal.add(Calendar.DATE, 10);
			invoice.setDueDate(cal.getTime());
				
			List lines = new ArrayList();
			lines.add(new InvoiceLineDTO(new Integer(1), "10 classes of yoga", new Float(105.383838834), 
					new Float(10.532342342), new Integer(10), null, null));
			lines.add(new InvoiceLineDTO(new Integer(1), "suntaning ", new Float(100), 
					new Float(10), new Integer(10), null, null));
			lines.add(new InvoiceLineDTO(new Integer(1), "Late payment fee ", new Float(100), 
					null, null, null, null));

			invoice.setInvoiceLines(lines);
			
			ContactDTO to, from;
			to = new ContactDTO();
			to.setAddress1("3423 Surrey Rd.");
			to.setCity("Vancouver");
			to.setStateProvince("BC");
			to.setPostalCode("V4S 3E4");
			to.setFirstName("Isabelle");
			to.setLastName("Oppenheim");
			
			from = new ContactDTO();
			from.setOrganizationName("Natural Green Landscaping");
			from.setAddress1("345 Brooks Av.");
			from.setCity("Burnaby");
			from.setStateProvince("BC");
			from.setPostalCode("V5Q 3E4");
			
			
			NotificationBL.generatePaperInvoiceAsFile("simple_invoice", invoice, from, to, 
					"Take advantage of our new promotion! Two for the price of one until October 31st.",
					"Thank you for your business!", new Integer(1));
			System.out.println("Done");
		} catch(Exception e) {
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}
}
