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
 * Created on Apr 5, 2004
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.server.user;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import com.sapienter.jbilling.server.payment.db.PaymentDTO;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.user.partner.db.PartnerPayout;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.RemoteContext;

/**
 * @author Emil
 */
public class PartnerTest extends TestCase {

    public void testPartnerGeneral() {
        try {
            IUserSessionBean session = (IUserSessionBean) RemoteContext.getBean(
                    RemoteContext.Name.USER_REMOTE_SESSION);
            Partner partner = null;

            Calendar cal = Calendar.getInstance();
            cal.clear();

            /* 
             *  first run
             */
            cal.set(2009, Calendar.MARCH, 15);
            session.processPayouts(cal.getTime());

            // partner 1
            partner = session.getPartnerDTO(new Integer(10));

            // no payouts
            assertEquals("No new payouts for 1", 0, partner.getPartnerPayouts().size());

            cal.set(2009, Calendar.APRIL, 1);
            assertEquals("1:next payout still apr 1", partner.getNextPayoutDate().getTime(), cal.getTime().getTime());

            // partner 2
            partner = session.getPartnerDTO(new Integer(11));

            // no payouts, this guy doens't get paid in the batch
            assertEquals("No new payouts for 2", 0, partner.getPartnerPayouts().size());

            // still she should get paid
            // note: value should come from the ranged commission
            assertEquals("2: due payout ", new BigDecimal("2.3"), partner.getDuePayout());
            cal.set(2009, Calendar.MARCH, 1);
            assertEquals("2:next payout mar 1", partner.getNextPayoutDate().getTime(), cal.getTime().getTime());

            // partner 3
            partner = session.getPartnerDTO(new Integer(12));

            // a new payout
            Set<PartnerPayout> payouts = partner.getPartnerPayouts();
            assertEquals("3: one payout", 1, payouts.size());

            Iterator<PartnerPayout> payoutsIter = payouts.iterator();
            PartnerPayout payout = payoutsIter.next();
            assertNotNull("Payout", payout);

            PaymentDTO payment = payout.getPayment();
            assertNotNull("Payout payment", payment);
            assertEquals("3: payout total", new BigDecimal("2.5"), payment.getAmount());
            assertEquals("3: sucessful payment in new payout", Constants.RESULT_OK, payment.getResultId());
            assertEquals("3 due payout zero", BigDecimal.ZERO, partner.getDuePayout());

            cal.set(2009, Calendar.MARCH, 25);
            assertEquals("3:next payout 10 days later ", partner.getNextPayoutDate().getTime(), cal.getTime().getTime());

            /*
             * second run
             */
            cal.set(2009, Calendar.APRIL, 1);
            session.processPayouts(cal.getTime());
            
            // partner 1
            partner = session.getPartnerDTO(new Integer(10));

            // new payout
            payouts = partner.getPartnerPayouts();
            assertEquals("1:New payout", 1, payouts.size());
            payoutsIter = payouts.iterator();
            payout = payoutsIter.next();

            assertNotNull("Payout", payout);
            payment = payout.getPayment();
            assertNotNull("Payout payment", payment);
            assertEquals("1: payout total", new BigDecimal("5"), payment.getAmount());
            assertEquals("1: payout payments total", new BigDecimal("10"), payout.getPaymentsAmount());
            assertEquals("1: payout refunds total", new BigDecimal("5"), payout.getRefundsAmount());
            assertEquals("1: sucessful payment in new payout", Constants.RESULT_OK, payment.getResultId());
            assertEquals("1 due payout zero", BigDecimal.ZERO, partner.getDuePayout());

            // partner 2
            partner = session.getPartnerDTO(new Integer(11));

            // no payouts, this guy doens't get paid in the batch
            assertEquals("No new payouts for 2", 0, partner.getPartnerPayouts().size());

            // still she should get paid
            assertEquals("2: due payout ", new BigDecimal("2.3"), partner.getDuePayout());
            cal.set(2009, Calendar.MARCH, 1);
            assertEquals("2:next payout mar 1", partner.getNextPayoutDate().getTime(), cal.getTime().getTime());

            // partner 3
            partner = session.getPartnerDTO(new Integer(12));

            // a new payout
            payouts = partner.getPartnerPayouts();
            assertEquals("3: two payout", 2, payouts.size());
            payoutsIter = payouts.iterator();
            payout = payoutsIter.next();

            // make sure we have the lastest payout
            PartnerPayout payout2 = payoutsIter.next();
            if (payout2.getId() > payout.getId()) {
                payout = payout2;
            }
            payment = payout.getPayment();
            assertNotNull("Payout payment", payment);
            assertEquals("3: payout total", BigDecimal.ZERO, payment.getAmount());
            assertEquals("3 due payout zero", BigDecimal.ZERO, partner.getDuePayout());

            cal.set(2009, Calendar.MARCH, 25);
            cal.add(Calendar.DATE, 10);
            assertEquals("3 (2):next payout ", partner.getNextPayoutDate().getTime(), cal.getTime().getTime());
          
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception caught:" + e);
        }
    }

    public static void assertEquals(BigDecimal expected, BigDecimal actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, BigDecimal expected, BigDecimal actual) {
        assertEquals(message,
                     (Object) (expected == null ? null : expected.setScale(2, RoundingMode.HALF_UP)),
                     (Object) (actual == null ? null : actual.setScale(2, RoundingMode.HALF_UP)));
    }
}
