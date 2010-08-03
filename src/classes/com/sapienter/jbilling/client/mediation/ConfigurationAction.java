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

package com.sapienter.jbilling.client.mediation;

import java.util.Calendar;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.util.CrudAction;
import com.sapienter.jbilling.common.InvalidArgumentException;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.mediation.IMediationSessionBean;
import com.sapienter.jbilling.server.mediation.db.MediationConfiguration;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.sapienter.jbilling.server.util.Context;

public class ConfigurationAction extends CrudAction {

    //private static final Logger LOG = Logger.getLogger(TaskAction.class);
    private IMediationSessionBean configurationSession = null;
    
    public ConfigurationAction() {
        setFormName("configuration");
        try {
            configurationSession = (IMediationSessionBean) Context.getBean(
                    Context.Name.MEDIATION_SESSION);
        } catch (Exception e) {
            throw new SessionInternalError("Initializing configuration action" + 
                    e.getMessage());
        }
    }
    
    public void setup() {
        List<MediationConfiguration> configs = configurationSession.getAllConfigurations(entityId);
        myForm.set("configurations", configs);
    }

    public Object editFormToDTO() {
        return myForm.get("configurations");
    }

    public void create(Object dtoHolder) {
        // nothing to de here
    }

    public String update(Object dtoHolder) {
        try {
            myForm.set("configurations",configurationSession.updateAllConfiguration(
                    executorId, (List) dtoHolder));
        } catch (Exception e) {
            if (e.getCause().getClass().equals(OptimisticLockException.class)) {
                setup();
                throw new OptimisticLockException();
            } else if (e.getCause().getClass().equals(InvalidArgumentException.class)) {
                errors.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("mediation.configuration.error"));
                return null;
            } else {
                throw new SessionInternalError("update configuration action", ConfigurationAction.class, e);
            }
        }
        return "mediation.configuration.updated";
    }

    public String delete() {
        Integer id = Integer.valueOf(request.getParameter("id"));
        try {
            if (id != null) {
                configurationSession.delete(executorId, id);
                return "mediation.configuration.deleted";
            }
            return null;
        } catch (Exception e) {
            throw new SessionInternalError("delete configuration action" + 
                    e.getMessage());
        } 
    }

    public void reset() {
        // nothing here
    }

    public boolean otherAction(String action) {
        if (action.equals("add")) {
            // create a dummy configuration with default data
            MediationConfiguration config = new MediationConfiguration();
            config.setEntityId(entityId);
            config.setName("Configuration Name");
            config.setOrderValue(1);
            config.setCreateDatetime(Calendar.getInstance().getTime());
            PluggableTaskDTO task = new PluggableTaskDTO();
            task.setId(1);
            config.setPluggableTask(task);
            
            ((List<MediationConfiguration>) myForm.get("configurations")).add(config);
            
        } else {
            return false;
        }
        // forward this to a custom mapping, for refresh
        forward = "edit";
        return true;
    }

}
