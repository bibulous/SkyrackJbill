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
package com.sapienter.jbilling.client.item;

import java.rmi.RemoteException;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.CrudActionBase;
import com.sapienter.jbilling.server.item.IItemSessionBean;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.util.Context;

public class TypeMaintainAction extends CrudActionBase<ItemTypeDTO> {

    private static final String FORM_ITEM_TYPE = "itemType";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_LINE_TYPE = "order_line_type";
    private static final String MESSAGE_CREATE_SUCCESS = "item.type.create.done";
    private static final String MESSAGE_UPDATE_SUCCESS = "item.type.update.done";
    private static final String MESSAGE_DELETE_SUCCESS = "item.type.delete.done";
    private static final String FORWARD_LIST = "type_list";
    private static final String FORWARD_EDIT = "type_edit";
    private static final String FORWARD_DELETED = "type_deleted";
    private final IItemSessionBean myItemSession;

    public TypeMaintainAction() {
        super(FORM_ITEM_TYPE, "item type");
        myItemSession = (IItemSessionBean) Context.getBean(Context.Name.ITEM_SESSION);
    }

    @Override
    protected ForwardAndMessage doSetup() throws RemoteException {
        ItemTypeDTO dto = myItemSession.getType(selectedId);
        myForm.set(FIELD_NAME, dto.getDescription());
        myForm.set(FIELD_LINE_TYPE, dto.getOrderLineTypeId());
        return new ForwardAndMessage(FORWARD_EDIT);
    }

    @Override
    protected ItemTypeDTO doEditFormToDTO() {
        ItemTypeDTO dto = new ItemTypeDTO();
        dto.setDescription((String) myForm.get(FIELD_NAME));
        dto.setOrderLineTypeId((Integer) myForm.get(FIELD_LINE_TYPE));
        dto.setEntity(new CompanyDTO(entityId));
        return dto;
    }

    @Override
    protected ForwardAndMessage doCreate(ItemTypeDTO dto) throws RemoteException {
        myItemSession.createType(dto);
        return new ForwardAndMessage(FORWARD_LIST, MESSAGE_CREATE_SUCCESS);
    }

    @Override
    protected ForwardAndMessage doUpdate(ItemTypeDTO dto) throws RemoteException {
        dto.setId(selectedId);
        myItemSession.updateType(executorId, dto);
        return new ForwardAndMessage(FORWARD_LIST, MESSAGE_UPDATE_SUCCESS);
    }

    @Override
    protected ForwardAndMessage doDelete() throws RemoteException {
        myItemSession.deleteType(executorId, selectedId);
        return new ForwardAndMessage(FORWARD_DELETED, MESSAGE_DELETE_SUCCESS);
    }

    @Override
    protected void resetCachedList() {
        session.removeAttribute(Constants.SESSION_LIST_KEY + "type");
    }
}
