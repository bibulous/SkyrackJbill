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

import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;

/*
 * Each task is resposaible of verifying if it should run or not,
 * for example, an custom email task for an entity can extend the
 * basic email task, and then perform a verification if that user
 * has subscribed or not to that particular type of message.
 * Eventually, a method like getPreferredDeliveryType could be
 * provided.   
 */
public interface NotificationTask {
    public void deliver(UserDTO user, MessageDTO sections)
            throws TaskException;
    
    /**
     * The needed sections for a task to do its job. Plain text email will do with 2, but 
     * HTML + text will need 3, for example. 
     * This will tell the GUI how many section to display, and makes notification_message_type.sections obsolete.
     * @return
     */
    public int getSections();
}
