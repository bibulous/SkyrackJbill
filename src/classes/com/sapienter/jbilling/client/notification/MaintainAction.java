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

package com.sapienter.jbilling.client.notification;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.CrudActionBase;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.MessageSection;
import com.sapienter.jbilling.server.util.Context;

public class MaintainAction extends CrudActionBase<MessageDTO> {

	private static final String FORM = "notification";

	private static final String FIELD_SECTION_CONSTANTS = "sectionNumbers";
	private static final String FIELD_SECTIONS = "sections";
	private static final String FIELD_USE_ME = "chbx_use_flag";
	private static final String FIELD_LANGUAGE = "language";
	
	private static final String FORWARD_EDIT = "notification_edit";
	private static final String MESSAGE_UPDATE_OK = "notification.message.update.done";

	private final INotificationSessionBean myNotificationSession;

    public MaintainAction() {
        super(FORM, "notification");
        LOG = Logger.getLogger(MaintainAction.class);
        try {
            myNotificationSession = (INotificationSessionBean) Context.getBean(
                    Context.Name.NOTIFICATION_SESSION);
        } catch (Exception e) {
            throw new SessionInternalError(
                    "Initializing notification CRUD action: " + e.getMessage());
        }
    }
	
	@Override
	protected MessageDTO doEditFormToDTO() throws RemoteException {
		MessageDTO dto = new MessageDTO();
        dto.setLanguageId((Integer) myForm.get("language"));
        dto.setTypeId(selectedId);
        dto.setUseFlag((Boolean) myForm.get("chbx_use_flag"));
        // set the sections
        String sections[] = (String[]) myForm.get("sections");
        Integer sectionNumbers[] = (Integer[]) myForm.get("sectionNumbers");
        for (int f = 0; f < sections.length; f++) {
            dto.addSection(new MessageSection(sectionNumbers[f], sections[f]));
            LOG.debug("adding section:" + f + " "  + sections[f]);
        }
        LOG.debug("message is " + dto);
        return dto;
	}
	
	@Override
	protected ForwardAndMessage doUpdate(MessageDTO dto) throws RemoteException {
        myNotificationSession.createUpdate(dto, entityId);
        return new ForwardAndMessage(FORWARD_EDIT, MESSAGE_UPDATE_OK);
	}
	
	@Override
	protected ForwardAndMessage doSetup() throws RemoteException {
        MessageDTO dto = myNotificationSession.getDTO(selectedId, languageId, entityId);
        myForm.set(FIELD_LANGUAGE, languageId);
        myForm.set(FIELD_USE_ME, dto.getUseFlag());
        // now cook the sections for the form's taste
        String sections[] = new String[dto.getContent().length];
        Integer sectionNubmers[] = new Integer[dto.getContent().length];
        for (int f = 0; f < sections.length; f++) {
            sections[f] = dto.getContent()[f].getContent();
            sectionNubmers[f] = dto.getContent()[f].getSection();
        }
        myForm.set(FIELD_SECTIONS, sections);
        myForm.set(FIELD_SECTION_CONSTANTS, sectionNubmers);
        return new ForwardAndMessage(FORWARD_EDIT);
	}
	
	@Override
	protected void resetCachedList() {
		session.removeAttribute(Constants.SESSION_LIST_KEY + FORM);
	}
	
	@Override
	protected ForwardAndMessage doDelete() throws RemoteException {
		throw new UnsupportedOperationException(
				"Set of notification events is fixed. You can not delete it, only switch it off");
	}
	
	@Override
	protected ForwardAndMessage doCreate(MessageDTO dto) throws RemoteException {
		throw new UnsupportedOperationException(
				"Set of notification events is fixed. You can not create it, only switch it on");
	}

	@Override
	protected boolean isCancelled(HttpServletRequest request) {
		return !request.getParameter("mode").equals("setup");
	}
	
	@Override
	protected boolean isResetRequested() {
		return request.getParameter("reload") != null || super.isResetRequested();
	}
	
	@Override
	protected void preReset() {
		//call to super would re-init the form from mapping 
		setForward(FORWARD_EDIT);
	}
	
	@Override
	public void reset() {
		super.reset();
		// this is just a change of language the requires a reload
		// of the bean
		languageId = (Integer) myForm.get(FIELD_LANGUAGE);
		setup();
	}
	
	@Override
	protected void preEdit() {
		super.preEdit();
		setForward(FORWARD_EDIT);
	}
}
