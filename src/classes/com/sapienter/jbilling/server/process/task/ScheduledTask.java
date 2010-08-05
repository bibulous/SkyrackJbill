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
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;

import java.util.HashMap;

/**
 * @author Brian Cowdery
 * @since 04-02-2010
 */
public abstract class ScheduledTask extends PluggableTask implements IScheduledTask {

    /**
     * Constructs the JobDetail for this scheduled task, and copies the plug-in parameter
     * map into the detail JobDataMap for use when the task is executed by quartz.
     *
     * @return job detail
     * @throws com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException
     */
    public JobDetail getJobDetail() throws PluggableTaskException {
        JobDetail detail = new JobDetail(getTaskName() + " job", Scheduler.DEFAULT_GROUP, this.getClass());
        detail.getJobDataMap().put("entityId", getEntityId());
        detail.getJobDataMap().put("taskId", getTaskId());
        detail.getJobDataMap().putAll(parameters);
        return detail;
    }

    /**
     * Copies plug-in parameters from the JobDetail map into the plug-in's working
     * parameter map. This is a compatibility step so that we don't have to write
     * separate parameter handling code specifically for scheduled tasks.
     * 
     * @param context executing job context
     * @throws JobExecutionException thrown if an exception occurs while initializing parameters
     */
    protected void _init(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        setEntityId(map.getInt("entityId"));

        parameters = new HashMap<String, Object>();
        for (Object key : map.keySet())
            parameters.put((String) key, map.get(key));
    }


	/**
     * Return this plug-ins schedule as a readable string. Can be used as part of
     * {@link IScheduledTask#getTaskName()} to make the task name unique to the schedule
     * allowing multiple plug-ins of the same type to be added with different schedules.
     *
     * @return schedule string
     */
    public abstract String getScheduleString();
}
