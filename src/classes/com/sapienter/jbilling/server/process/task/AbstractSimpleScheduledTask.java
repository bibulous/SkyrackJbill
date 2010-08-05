/*
 jBilling - The Enterprise Open Source Billing System
 Copyright (C) 2003-2010 Enterprise jBilling Software Ltd. and Emiliano Conde

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
package com.sapienter.jbilling.server.process.task;

import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDAS;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.util.Context;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Abstract task that contains all the plumbing necessary to construct a SimpleTrigger for
 * scheduling. This class will default to a daily task that will repeat indefinitely.
 *
 * Plug-in parameters:
 *
 *      start_time      Schedule start time, this task will repeat from this value
 *                      Defaults to "January 01, 2010, 12:00 noon"
 *
 *      end_time        End time, the task will stop running after this date.
 *                      If left blank, this task will execute until it runs out of repetitions.
 *
 *      repeat          The number of times for this task to repeat before ending.
 *                      A repeat value of 0 denotes that this task should repeat indefinitely.
 *                      Defaults to SimpleTrigger.REPEAT_INDEFINITELY
 *
 *      interval        The interval in hours to wait between repetitions.
 *                      Defaults to 24 hours
 *
 *      start_time and end_time plug-in parameters should be dates in the format "yyyyMMdd-HHmm"
 *
 * @author Brian Cowdery
 * @since 02-02-2010
 */
public abstract class AbstractSimpleScheduledTask extends ScheduledTask {
    private static final Logger LOG = Logger.getLogger(AbstractSimpleScheduledTask.class);

    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmm");

    protected static final String PARAM_START_TIME = "start_time";
    protected static final String PARAM_END_TIME = "end_time";
    protected static final String PARAM_REPEAT = "repeat";
    protected static final String PARAM_INTERVAL = "interval";

    protected static final Date DEFAULT_START_TIME = new DateMidnight(2010, 1, 1).toDate();
    protected static final Date DEFAULT_END_TIME = null;
    protected static final Integer DEFAULT_REPEAT = SimpleTrigger.REPEAT_INDEFINITELY;
    protected static final Integer DEFAULT_INTERVAL = 24;

    public SimpleTrigger getTrigger() throws PluggableTaskException {
        SimpleTrigger trigger = new SimpleTrigger(getTaskName(),
                                                  Scheduler.DEFAULT_GROUP,
                                                  getParameter(PARAM_START_TIME, DEFAULT_START_TIME),
                                                  getParameter(PARAM_END_TIME, DEFAULT_END_TIME),
                                                  getParameter(PARAM_REPEAT, DEFAULT_REPEAT),
                                                  getParameter(PARAM_INTERVAL, DEFAULT_INTERVAL) * 3600 * 1000);

        trigger.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT);

        return trigger;
    }

    public String getScheduleString() {
        StringBuilder builder = new StringBuilder();

        try {
            builder.append("start: ");
            builder.append(getParameter(PARAM_START_TIME, DEFAULT_START_TIME));
            builder.append(", ");

            builder.append("end: ");
            builder.append(getParameter(PARAM_END_TIME, DEFAULT_END_TIME));
            builder.append(", ");

            Integer repeat = getParameter(PARAM_REPEAT, DEFAULT_REPEAT);
            builder.append("repeat: ");
            builder.append((repeat == SimpleTrigger.REPEAT_INDEFINITELY ? "infinite" : repeat));
            builder.append(", ");

            builder.append("interval: ");
            builder.append(getParameter(PARAM_INTERVAL, DEFAULT_INTERVAL));
            builder.append(" hrs");

        } catch (PluggableTaskException e) {
            LOG.error("Exception occurred parsing plug-in parameters", e);
        }

        return builder.toString();
    }

    protected Date getParameter(String key, Date defaultValue) throws PluggableTaskException {
        Object value = parameters.get(key);

        try {
            return value != null ? DATE_FORMAT.parse((String) value) : defaultValue;
        } catch (ParseException e) {
            throw new PluggableTaskException(key + " could not be parsed as a date!", e);
        }
    }

    protected Integer getParameter(String key, Integer defaultValue) throws PluggableTaskException {
        Object value = parameters.get(key);

        try {
            return value != null ? Integer.parseInt((String) value) : defaultValue;
        } catch (NumberFormatException e) {
            throw new PluggableTaskException(key + " could not be parsed as an integer!", e);
        }
    }
}
