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

package com.sapienter.jbilling.server.pluggableTask;

import java.util.Date;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;

/**
 * Sends an email when a payment processor is down.
 * @author Lucas Pickstone
 */
public class ProcessorEmailAlarmTask extends PluggableTask
            implements ProcessorAlarm {

    // pluggable task parameters names
    public static final String PARAMETER_FAILED_LIMIT = "failed_limit";
    public static final String PARAMETER_FAILED_TIME = "failed_time";
    public static final String PARAMETER_TIME_BETWEEN_ALARMS 
            = "time_between_alarms";

    // optional parameter
    public static final String PARAMETER_EMAIL_ADDRESS = "email_address";

    private String processorName;
    private Integer entityId;
    private ProcessorEmailAlarm alarm;
    
    private int failedLimit;
    private int failedTime;
    private int timeBetweenAlarms;

    private Logger log = Logger.getLogger(ProcessorEmailAlarmTask.class);
    
    @Override
    public void initializeParamters(PluggableTaskDTO task) throws PluggableTaskException {
    	super.initializeParamters(task);
    	failedLimit = parseInt(parameters.get(PARAMETER_FAILED_LIMIT));
    	failedTime = parseInt(parameters.get(PARAMETER_FAILED_TIME));
    	failedTime = parseInt(parameters.get(PARAMETER_TIME_BETWEEN_ALARMS));
    }

    // Initialisation
    public void init(String processorName, Integer entityId) {
        this.processorName = processorName;
        this.entityId = entityId;
        alarm = ProcessorEmailAlarm.getAlarm(processorName, entityId);
    }

    // Payment processed, but failed/declined.
    public void fail() {
        if (alarm.fail(failedLimit, failedTime, timeBetweenAlarms)) {
            String params[] = new String[4];
            params[0] = processorName;
            params[1] = entityId.toString();
            params[2] = "" + alarm.getFailedCounter();
            params[3] = (new Date()).toString();
            sendEmail("processorAlarm.fail", params);
        }
    }

    // Processor was unavailable.
    public void unavailable() {
        if (alarm.unavailable(timeBetweenAlarms)) {
            String params[] = new String[3];
            params[0] = processorName;
            params[1] = entityId.toString();
            params[2] = (new Date()).toString();
            sendEmail("processorAlarm.unavailable", params);
        }
    }

    // Payment processed and successful.
    public void successful() {
        alarm.successful();
    }

    // Sends email with given messageKey and params.
    private void sendEmail(String messageKey, String[] params) {
        log.debug("Sending alarm email.");

        String address = (String) parameters.get(PARAMETER_EMAIL_ADDRESS);

        try {
            // if email address supplied as parameter, use it,
            if (address != null) {
                NotificationBL.sendSapienterEmail(address, entityId, 
                        messageKey, null, params);
            } 
            // otherwise use the entityId's default address.
            else {
                NotificationBL.sendSapienterEmail(entityId, messageKey, 
                        null, params);
            }
        } catch (Exception e) {
            log.error("Couldn't send email.", e);
        }
    }
    
    private int parseInt(Object object) throws PluggableTaskException {
    	if (object instanceof Number){
    		return ((Number)object).intValue();
    	}
    	if (object instanceof String){
    		try {
    			return Integer.parseInt((String)object);
    		} catch (NumberFormatException e){
    			//fall through
    		}
    	}
    	throw new PluggableTaskException("Number expected: " + object);
    }
}
