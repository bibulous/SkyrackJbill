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

package com.sapienter.jbilling.client.system;

import javax.persistence.OptimisticLockException;

import com.sapienter.jbilling.client.util.CrudAction;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.pluggableTask.IPluggableTaskSessionBean;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskParameterDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskTypeDTO;
import com.sapienter.jbilling.server.util.Context;

public class TaskAction extends CrudAction {

    //private static final Logger LOG = Logger.getLogger(TaskAction.class);
    private IPluggableTaskSessionBean taskSession = null;
    
    public TaskAction() {
        setFormName("task");
        try {
            taskSession = (IPluggableTaskSessionBean) Context.getBean(
                    Context.Name.PLUGGABLE_TASK_SESSION);
        } catch (Exception e) {
            throw new SessionInternalError("Initializing task action" + 
                    e.getMessage());
        }
    }
    
    public void setup() {
        PluggableTaskDTO[] dtos = taskSession.getAllDTOs(entityId);
        for (PluggableTaskDTO dto: dtos) {
            // make sure that each task has its own copy of a type
            // otherwise they share types and updating get messy
            dto.setType(new PluggableTaskTypeDTO(dto.getType()));
        }
        myForm.set("tasks", dtos);
    }

    public Object editFormToDTO() {
        return myForm.get("tasks");
    }

    public void create(Object dtoHolder) {
        // TODO Auto-generated method stub

    }

    public String update(Object dtoHolder) {
        try {
            myForm.set("tasks",taskSession.updateAll(
                    executorId, (PluggableTaskDTO[]) dtoHolder));
        } catch (Exception e) {
            if (e.getCause().getClass().equals(OptimisticLockException.class)) {
                setup();
                throw new OptimisticLockException();
            } else {
                throw new SessionInternalError("update task action", TaskAction.class, e);
            }
        }
        return "system.task.updated";
    }

    public String delete() {
        Integer id = Integer.valueOf(request.getParameter("id"));
        try {
            taskSession.delete(executorId, id);
        } catch (Exception e) {
            throw new SessionInternalError("delete task action" + 
                    e.getMessage());
        } 
        return "system.task.deleted";
    }

    public void reset() {
        // TODO Auto-generated method stub

    }

    public boolean otherAction(String action) {
        if (action.equals("add")) {
            // create a dummy task with default data
            PluggableTaskDTO dto = new PluggableTaskDTO();
            dto.setEntityId(entityId);
            dto.setProcessingOrder(new Integer(1));
            dto.getType().setPk(new Integer(1));
            // call the server tier to get it into the data base
            taskSession.create(executorId, dto);
        } else if (action.equals("addParameter")) {
            PluggableTaskParameterDTO dto = new PluggableTaskParameterDTO();
            dto.setStrValue("default");
            dto.setName("parameter_name");
            
            Integer id = Integer.valueOf(request.getParameter("id"));
            taskSession.createParameter(executorId, id, dto);
        } else if (action.equals("deleteParameter")) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            taskSession.deleteParameter(executorId, id);
        } else {
            return false;
        }
        // remove the old from from the session
        session.removeAttribute(getFormName());
        // forward this to a custom mapping, for refresh
        forward = "added";
        return true;
    }

}
