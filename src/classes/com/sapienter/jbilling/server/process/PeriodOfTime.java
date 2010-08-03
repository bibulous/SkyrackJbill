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
package com.sapienter.jbilling.server.process;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.joda.time.DateMidnight;
import org.joda.time.Days;

public class PeriodOfTime {
    
	private final DateMidnight start;
	private final DateMidnight end;
	private final int position;
	private final int daysInCycle;

	public PeriodOfTime(Date start, Date end, int dayInCycle, int position) {
		this.start = new DateMidnight(start);
		this.end = new DateMidnight(end);
		this.position = position;
		this.daysInCycle = dayInCycle;
	}

	public Date getEnd() {
		return end.toDate();
	}

	public int getPosition() {
		return position;
	}

	public Date getStart() {
		return start.toDate();
	}

	public int getDaysInCycle() {
		return daysInCycle;
	}

    /**
     * Find the number of days between the period start date to the period end date. This means
     * that the start date is counted as a days within the period, but not the end date. For example, January 01 to
     * January 10th includes 9 days total.
     *
     * This method takes into account daylight savings time to ensure that days are counted
     * correctly across DST boundaries.
     *
     * @return number of days between start and end dates
     */
	public int getDaysInPeriod() {
        if (end.isBefore(start)) { // not sure why this is necessary. 
            return 0;
        }
        return Days.daysBetween(start, end).getDays();
	}

    @Override
	public String toString() {
		return "period starts: " + start + " ends " + end + " position "
				+ position + " days in cycle " + getDaysInCycle();
	}
}
