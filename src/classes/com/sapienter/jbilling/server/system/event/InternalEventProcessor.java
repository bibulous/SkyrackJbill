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
package com.sapienter.jbilling.server.system.event;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.util.Constants;

public class InternalEventProcessor {

    private static final Logger LOG = Logger.getLogger(InternalEventProcessor.class);
    
    public void process(Event event) {

        try {
            PluggableTaskManager<IInternalEventsTask> taskManager = new PluggableTaskManager<IInternalEventsTask>(
                    event.getEntityId(), Constants.PLUGGABLE_TASK_INTERNAL_EVENT);

            for (PluggableTaskDTO task : taskManager.getAllTasks()) {
                IInternalEventsTask myClass = taskManager.getInstance(task.getType()
                        .getClassName(), task.getType().getCategory().getInterfaceName(), task);
                // check for applicable events for this plug-in
                for (Class possibleEvent : myClass.getSubscribedEvents()) {
                    if (possibleEvent.getSimpleName().equals(event.getClass().getSimpleName())) {
                        LOG.debug("Processing " + event + " with " + myClass);
                        myClass.process(event);
                    }
                }
            }
        } catch (PluggableTaskException e) {
            throw new SessionInternalError("Exception processing internal event plug-in",
                    InternalEventProcessor.class, e);
        }
    }
}
