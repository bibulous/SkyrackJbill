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

/*
 * Created on Aug 11, 2004
 *
 */
package com.sapienter.jbilling.client.invoice;

import java.rmi.RemoteException;
import java.util.HashMap;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.UpdateOnlyCrudActionBase;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class NumberingAction extends UpdateOnlyCrudActionBase<NumberingActionContext> {
    private static final String FORM_NUMBERING = "invoiceNumbering";
    private static final String FORWARD_EDIT = "invoiceNumbering_edit";
    
    private static final String FIELD_PREFIX = "prefix";
    private static final String FIELD_NUMBER = "number";
    
    private static final String MESSAGE_SUCCESSFULLY_UPDATED = "invoice.numbering.updated";
    
    private final IUserSessionBean myUserSession;
    
    public NumberingAction(){
        super(FORM_NUMBERING, "invoice numbering", FORWARD_EDIT);
        try {
            myUserSession = (IUserSessionBean) Context.getBean(
                    Context.Name.USER_SESSION);
        } catch (Exception e) {
            throw new SessionInternalError(
                    "Initializing invoice numbering "
                            + " CRUD action: " + e.getMessage());
        }
    }
    
    @Override
    protected NumberingActionContext doEditFormToDTO() throws RemoteException {
        String prefix = (String) myForm.get(FIELD_PREFIX);
        String number = (String) myForm.get(FIELD_NUMBER);
        return new NumberingActionContext(prefix, number);
    }
    
    @Override
    protected ForwardAndMessage doUpdate(NumberingActionContext dto) throws RemoteException {
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(Constants.PREFERENCE_INVOICE_PREFIX, dto.getPrefix());
        // this has to be an Integer, otherwise the preference will be set as a String
        // then invoice generation fails as the number gets ++
        params.put(Constants.PREFERENCE_INVOICE_NUMBER, Integer.valueOf(dto.getNumber())); 
        myUserSession.setEntityParameters(entityId, params);
        return getForwardEdit(MESSAGE_SUCCESSFULLY_UPDATED);
    }
    
    @Override
    protected ForwardAndMessage doSetup() throws RemoteException {
        Integer[] preferenceIds = new Integer[] {
                Constants.PREFERENCE_INVOICE_PREFIX, 
                Constants.PREFERENCE_INVOICE_NUMBER,
        };
        HashMap<?, ?> result = myUserSession.getEntityParameters(entityId, preferenceIds);
        
        String prefix = (String) result.get(Constants.PREFERENCE_INVOICE_PREFIX); 
        String number = (String) result.get(Constants.PREFERENCE_INVOICE_NUMBER); 
        myForm.set(FIELD_NUMBER, notNull(number));
        myForm.set(FIELD_PREFIX, notNull(prefix));
        
        return getForwardEdit();
    }
    

    @Override
    protected void preEdit() {
        super.preEdit();
        setForward(FORWARD_EDIT);
    }

    private static String notNull(String text){
        return text == null ? "" : text;
    }
    
}
