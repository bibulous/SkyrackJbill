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

package com.sapienter.jbilling.server.user;


import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.user.db.AchDAS;
import com.sapienter.jbilling.server.user.db.AchDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.audit.EventLogger;

public class AchBL {
    private AchDAS achDas = null;
    private AchDTO ach = null;
    private Logger log = null;
    private EventLogger eLogger = null;
    
    public AchBL(Integer achId) {
        init();
        set(achId);
    }
    
    public AchBL() {
        init();
    }
    
    public AchBL(AchDTO row) {
        init();
        ach = row;
    }
    
    private void init() {
        log = Logger.getLogger(AchBL.class);     
        eLogger = EventLogger.getInstance();        
        achDas = new AchDAS();
    }

    public AchDTO getEntity() {
        return ach;
    }
    
    public void set(Integer id) {
        ach = achDas.find(id);
    }
    
    public void set(AchDTO pEntity) {
        ach = pEntity;
    }
    
    public Integer create(AchDTO dto) {
        ach = achDas.create(dto.getAbaRouting(), dto.getBankAccount(),
        		dto.getAccountType(), dto.getBankName(),
				dto.getAccountName()); 
                
        return ach.getId();       
    }
    
    public void update(Integer executorId, AchDTO dto) {
        if (executorId != null) {
            eLogger.audit(executorId, dto.getBaseUser().getId(), 
                    Constants.TABLE_ACH, ach.getId(),
                    EventLogger.MODULE_CREDIT_CARD_MAINTENANCE, 
                    EventLogger.ROW_UPDATED, null,  
                    ach.getBankAccount(), null);
        }
        ach.setAbaRouting(dto.getAbaRouting());
        ach.setAccountName(dto.getAccountName());
        ach.setAccountType(dto.getAccountType());
        ach.setBankAccount(dto.getBankAccount());
        ach.setBankName(dto.getBankName());
    }
    
    public void delete(Integer executorId) {
        // now delete this ach record
        eLogger.audit(executorId, ach.getBaseUser().getId(), 
                Constants.TABLE_ACH, ach.getId(),
                EventLogger.MODULE_CREDIT_CARD_MAINTENANCE, 
                EventLogger.ROW_DELETED, null, null,null);
        
        achDas.delete(ach);
    }
    
    public AchDTO getDTO() {
        AchDTO dto = new AchDTO();
        
        dto.setId(ach.getId());
        dto.setAbaRouting(ach.getAbaRouting());
        dto.setAccountName(ach.getAccountName());
        dto.setAccountType(ach.getAccountType());
        dto.setBankAccount(ach.getBankAccount());
        dto.setBankName(ach.getBankName());
        
        return dto;
    }
}
