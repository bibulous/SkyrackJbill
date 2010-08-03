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
 * Created on Dec 29, 2003
 *
 * Copyright Sapienter Enterprise Software
 */
package com.sapienter.jbilling.server.order;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Emil
 */
public class OrderWS implements Serializable {

    private Integer id;
    private Integer createdBy;
    private Integer statusId;
    private Integer billingTypeId;
    private Date activeSince;
    private Date activeUntil;
    private Date createDate;
    private Date nextBillableDay;
    private int deleted;
    private Integer notify;
    private Date lastNotified;
    private Integer notificationStep;
    private Integer dueDateUnitId;
    private Integer dueDateValue;
    private Integer dfFm;
    private Integer anticipatePeriods;
    private Integer ownInvoice;
    private String notes;
    private Integer notesInInvoice;
    private Integer isCurrent;
    private Integer versionNum;
    private Date cycleStarts;
    
    //
    private String statusStr = null;
    private String timeUnitStr = null;

	// 
    private OrderLineWS orderLines[] = null;
    private Integer period = null;
    private Integer userId = null; // who is buying ?
    private Integer currencyId = null;
    // show the description
    // instead of the ids
    private String periodStr = null;
    private String billingTypeStr = null;
    private String pricingFields = null;

    /**
     * 
     */
    public OrderWS() {
    }

    /**
     * @param id
     * @param billingTypeId
     * @param activeSince
     * @param activeUntil
     * @param createDate
     * @param nextBillableDay
     * @param createdBy
     * @param toProcess
     * @param deleted
     */
    public OrderWS(Integer id, Integer billingTypeId, Integer notify,
    		Date activeSince, Date activeUntil, Date createDate, 
    		Date nextBillableDay, Integer createdBy, Integer statusId, 
    		Integer deleted, Integer currencyId, Date lastNotified, 
    		Integer notifStep, Integer dueDateUnitId, Integer dueDateValue, 
    		Integer anticipatePeriods, Integer dfFm, Integer isCurrent, 
    		String notes, Integer notesInInvoice, Integer ownInvoice, 
    		Integer period, Integer userId, Integer version, Date cycleStarts) {
    	setId(id);
    	setBillingTypeId(billingTypeId);
    	setNotify(notify);
    	setActiveSince(activeSince);
    	setActiveUntil(activeUntil);
    	setAnticipatePeriods(anticipatePeriods);
    	setCreateDate(createDate);
    	setNextBillableDay(nextBillableDay);
    	setCreatedBy(createdBy);
    	setStatusId(statusId);
    	setDeleted(deleted.shortValue());
    	setCurrencyId(currencyId);
    	setLastNotified(lastNotified);
    	setNotificationStep(notifStep);
    	setDueDateUnitId(dueDateUnitId);
    	setDueDateValue(dueDateValue);
    	setDfFm(dfFm);
    	setIsCurrent(isCurrent);
    	setNotes(notes);
    	setNotesInInvoice(notesInInvoice);
    	setOwnInvoice(ownInvoice);
    	setPeriod(period);
    	setUserId(userId);
    	setVersionNum(version);
    	setCycleStarts(cycleStarts);
    }

    /**
     * @return
     */
    public String getBillingTypeStr() {
        return billingTypeStr;
    }

    /**
     * @param billingTypeStr
     */
    public void setBillingTypeStr(String billingTypeStr) {
        this.billingTypeStr = billingTypeStr;
    }

    /**
     * @return
     */
    public OrderLineWS[] getOrderLines() {
        return orderLines;
    }

    /**
     * @param orderLines
     */
    public void setOrderLines(OrderLineWS orderLines[]) {
        this.orderLines = orderLines;
    }

    /**
     * @return
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * @param period
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * @return
     */
    public String getPeriodStr() {
        return periodStr;
    }

    /**
     * @param periodStr
     */
    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }


    /**
     * @return
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer(super.toString() + "periodStr= " + periodStr +
				" currencyId= " + currencyId);
		str.append("lines=");
		if (getOrderLines() != null) {
			for (OrderLineWS line: getOrderLines()) {
				str.append(line.toString() + "-");
			}
		} else {
			str.append(" none ");
		}
		str.append("]");
		return str.toString();

	}

	public Date getActiveSince() {
		return activeSince;
	}

	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
	}

	public Date getActiveUntil() {
		return activeUntil;
	}

	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}

	public Integer getAnticipatePeriods() {
		return anticipatePeriods;
	}

	public void setAnticipatePeriods(Integer anticipatePeriods) {
		this.anticipatePeriods = anticipatePeriods;
	}

	public Integer getBillingTypeId() {
		return billingTypeId;
	}

	public void setBillingTypeId(Integer billingTypeId) {
		this.billingTypeId = billingTypeId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Integer getDfFm() {
		return dfFm;
	}

	public void setDfFm(Integer dfFm) {
		this.dfFm = dfFm;
	}

	public Integer getDueDateUnitId() {
		return dueDateUnitId;
	}

	public void setDueDateUnitId(Integer dueDateUnitId) {
		this.dueDateUnitId = dueDateUnitId;
	}

	public Integer getDueDateValue() {
		return dueDateValue;
	}

	public void setDueDateValue(Integer dueDateValue) {
		this.dueDateValue = dueDateValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(Integer isCurrent) {
		this.isCurrent = isCurrent;
	}

	public Date getLastNotified() {
		return lastNotified;
	}

	public void setLastNotified(Date lastNotified) {
		this.lastNotified = lastNotified;
	}

	public Date getNextBillableDay() {
		return nextBillableDay;
	}

	public void setNextBillableDay(Date nextBillableDay) {
		this.nextBillableDay = nextBillableDay;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getNotesInInvoice() {
		return notesInInvoice;
	}

	public void setNotesInInvoice(Integer notesInInvoice) {
		this.notesInInvoice = notesInInvoice;
	}

	public Integer getNotificationStep() {
		return notificationStep;
	}

	public void setNotificationStep(Integer notificationStep) {
		this.notificationStep = notificationStep;
	}

	public Integer getNotify() {
		return notify;
	}

	public void setNotify(Integer notify) {
		this.notify = notify;
	}

	public Integer getOwnInvoice() {
		return ownInvoice;
	}

	public void setOwnInvoice(Integer ownInvoice) {
		this.ownInvoice = ownInvoice;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getTimeUnitStr() {
		return timeUnitStr;
	}

	public void setTimeUnitStr(String timeUnitStr) {
		this.timeUnitStr = timeUnitStr;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Date getCycleStarts() {
		return cycleStarts;
	}

	public void setCycleStarts(Date cycleStarts) {
		this.cycleStarts = cycleStarts;
	}
	
	public String getPricingFields() {
		return pricingFields;
	}
	
	public void setPricingFields(String pricingFields) {
		this.pricingFields = pricingFields;
	}
}
