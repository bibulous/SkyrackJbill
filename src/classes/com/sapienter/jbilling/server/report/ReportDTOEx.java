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

package com.sapienter.jbilling.server.report;

import java.util.Locale;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.report.db.ReportDTO;
import java.util.ArrayList;


public class ReportDTOEx extends ReportDTO {
	
	private static final Logger LOG = Logger.getLogger(ReportDTOEx.class);
	
    /*
     * Constants that define a report, it has to stay in synch with 
     * the database ( saved_report_type ).
     * There is one type per get methos in ReportBL
     */
    public static final Integer GENERAL_ORDERS = new Integer(1);
    
    // error codes
    public static final int OK = 0;
    public static final int ERROR_DATATYPE = 1;
    public static final int ERROR_WHERE = 2;
    public static final int ERROR_WHERE_NOINTEGER = 3;
    public static final int ERROR_OPERATOR = 4;
    public static final int ERROR_FUNCTION = 5;
    public static final int ERROR_ISNULL = 6;
    public static final int ERROR_TITLE = 7;
    public static final int ERROR_NO_OPERATOR = 8;
    public static final int ERROR_MISSING = 9;
    public static final int ERROR_AGREGATE = 10;
    public static final int ERROR_ADD_AGREGATE = 11;
    public static final int ERROR_WHERE_NOFLOAT= 12;
    public static final int ERROR_WHERE_NODATE= 13;
    public static final int ERROR_ORDER_RANGE = 14;
    public static final int ERROR_ORDER = 15;
    public static final int ERROR_ORDER_AGGREGATE= 16;    
    public static final int ERROR_IN_OP_EQUAL = 17;
    public static final int ERROR_NULL_OPERATOR = 18;
    
    //reports ids, in synch with the DB
    public static final Integer REPORT_ORDER = new Integer(1); 
    public static final Integer REPORT_INVOICE = new Integer(2); 
    public static final Integer REPORT_PAYMENT = new Integer(3); 
    public static final Integer REPORT_ORDER_LINE = new Integer(4); 
    public static final Integer REPORT_REFUND = new Integer(5);
    public static final Integer REPORT_INVOICED_DATE = new Integer(6);
    public static final Integer REPORT_PAYMENT_DATE = new Integer(7);
    public static final Integer REPORT_REFUND_DATE = new Integer(8);
    public static final Integer REPORT_ORDER_DATE = new Integer(9);
    public static final Integer REPORT_OVERDUE = new Integer(10);
    public static final Integer REPORT_INVOICE_BALANCE = new Integer(11);
    public static final Integer REPORT_PARTNER_ORDERS = new Integer(12);
    public static final Integer REPORT_PARTNER_PAYMENTS = new Integer(13);
    public static final Integer REPORT_PARTNER_REFUNDS = new Integer(14);
    public static final Integer REPORT_PARTNER = new Integer(15);
    public static final Integer REPORT_PAYOUT = new Integer(16);
    public static final Integer REPORT_INVOICE_LINE = new Integer(17);
    public static final Integer REPORT_USERS = new Integer(18);
    public static final Integer REPORT_ITEMS = new Integer(19);
    public static final Integer REPORT_TRANSACTIONS = new Integer(20);
    public static final Integer REPORT_SUBSCRIPTIONS = new Integer(21);
    public static final Integer REPORT_STATUS_TRANSITIONS = new Integer(22);
    public static final Integer REPORT_SUBSC_TRANSITIONS = new Integer(23);
    
    /*
     * Private fields
     */
    private List<Field> fields = null;
    private Integer userReportId = null; // for when this report has been loaded from a user's saved
    private Locale locale = null;

    // some flags to simplfy the presentation logic of the form
    private Boolean isAgregated = null;
    private Boolean selectable = null;
    private Boolean ordenable = null;
    private Boolean wherable = null;
    private Integer ordenableFields = null;
    
    // can't have the Logger here, it is not serializable !
    
    private List errorFields = null;
    private List errorCodes = null;
    
    // this are dynamic parameters usually coming from the gui. They will
    // replace sql parameters '?'
    private List dynamicParameters = null;
    
    /*
     * Constructs
     */
    public ReportDTOEx(String titleKey, String instructionsKey,
            String tables, String where, Locale locale) { 
        this.locale = locale;
        setTitleKey(titleKey);
        setInstructionsKey(instructionsKey);
        setTablesList(tables);
        setWhereStr(where);
        isAgregated = new Boolean(false);
        selectable = new Boolean(false);
        ordenable = new Boolean(false);
        wherable = new Boolean(false);
        setIdColumn(new Integer(0));
        fields = new ArrayList();
        ordenableFields = new Integer(0);
        
        errorFields = new ArrayList();
        errorCodes = new ArrayList();
        dynamicParameters = new ArrayList();
        
    }
    
    
    
    /*
     * Methods
     */
    private void addError(int code, int field) {
        errorFields.add(new Integer(field));
        errorCodes.add(new Integer(code));
    }
    
    public List getErrorFields() {
        return errorFields;
    }

    public List getErrorCodes() {
        return errorCodes;
    }

    /** 
     * reports with id column that are run agregated have to loose their
     * id column
     */
    public int getFirstFieldIndex() {
        int retValue = 0;
        if (getAgregated().booleanValue() && 
                getIdColumn() == 1) {
            retValue = 1;
        }        
        
        LOG.debug("first field = " + retValue);
        return retValue;
    }
    
    public boolean validate() {

    	boolean retValue = true;
        int fieldsOrdered = 0;
        errorFields = new ArrayList();
        errorCodes = new ArrayList();
        
        LOG.debug("validating report. aggregated=" + isAgregated);
        
        int firstField = getFirstFieldIndex();
        
        if (getTitleKey() == null || getInstructionsKey() == null ||
                fields == null || getTablesList() == null || getWhereStr() == null ) {
            retValue = false;
            addError(ERROR_MISSING, -1);
            LOG.info("Missing fields.");
        } else {
            // go over each field and validate it
            for (int f = firstField; f < fields.size(); f++) {
                Field field = (Field) fields.get(f);
                int code = field.validate(locale); 
                if (code != OK) {
                    retValue = false;
                    addError(code, f);
                    LOG.info("Invalid field:" + field.getColumnName());
                    break;
                }
                // verify that the select/group by is consistent
                if (isAgregated.booleanValue()) {
                    if (!field.isAgregated() && 
                            field.getIsShown().intValue() == 1) {
                        LOG.debug("Field " + field.getColumnName() + " is not " +
                                "agregated for an agregated report");
                        addError(ERROR_AGREGATE, f);
                        retValue = false;
                        break;
                    }
                }
                
                
                if (field.getOrderPosition() != null) {
                    // verify the value of the order by is within range
                    int val = field.getOrderPosition().intValue();
                    fieldsOrdered++;
                    if (val > ordenableFields.intValue() || val < 1) {
                        LOG.debug("Field " + field.getColumnName() + " order " +
                                "position is out of bounds:" + val);   
                        addError(ERROR_ORDER_RANGE, f);
                        retValue = false;
                        break;
                    }
                    // for aggregated reports, you can't order by a non
                    // shown column
                    if (isAgregated.booleanValue() && 
                            field.getIsShown().intValue() == 0) {
                        addError(ERROR_ORDER_AGGREGATE, f);
                        retValue = false;
                        break;
                    }
                }
            }
        }
        
        //  verify that the order values are consecutive
        if (fieldsOrdered > 0) {
            
            for (int done = 1; done <= fieldsOrdered; done++) {
                int f;
                for (f = firstField; f < fields.size(); f++) {
                    Field field = (Field) fields.get(f);
                    if (field.getOrderPosition() != null && 
                            field.getOrderPosition().intValue() == done) {
                        break;
                    }
                }
                if (f >= fields.size()) {
                    LOG.debug("Order values are not consecutive");
                    addError(ERROR_ORDER, -1);
                    retValue = false;
                    break;
                }            
            }       
        }

        
        return retValue;
    }
    
    public void updateAggregatedFlag() {
        isAgregated = new Boolean(false);
        for (int f = 0; f < fields.size(); f++) {
            Field field = (Field) fields.get(f);
            if (field.getFunctionName() != null || 
                    field.getIsGrouped().intValue() == 1) {
                isAgregated = new Boolean(true);
                break;
            }
        }
    }
    
    /*
     * Getters and setters
     */
     
    // this works as the setter of the fields
    public void addField(Field field) 
            throws SessionInternalError {

        if (field.validate(locale) == OK) {
            if (field.getIsShown().intValue() == 1) {
                if (!isAgregated.booleanValue()) {
                    if (field.isAgregated()) {
                        isAgregated = new Boolean(true);
                    }
                } else {
                    if (!field.isAgregated()) {
                        LOG.debug("Can't add a non agregated field " +
                                field.getColumnName() + " to this report");
                        throw new SessionInternalError("Non agregated field" +
                                " added to agregated report");
                    }
                }
            }       
            // add the field     
            fields.add(field);
            // update some flags
            if (field.getOrdenable().intValue() == 1) {
                ordenable = new Boolean(true);
                ordenableFields = new Integer(ordenableFields.intValue() + 1);
            }
            if (field.getSelectable().intValue() == 1 &&
                    !selectable.booleanValue()) {
                selectable = new Boolean(true);
            }
            if (field.getWhereable().intValue() == 1 &&
                    !wherable.booleanValue()) {
                wherable = new Boolean(true);
            }            
        } else {
            throw new SessionInternalError("Adding field " + 
                    field.getTableName() + "." + field.getColumnName() + 
                    " but it is not valid"); 
        }
        
    }
     
    

    public List<Field> getFields() {
        return fields;
    }

    /**
     * @return
     */
    public Boolean getOrdenable() {
        return ordenable;
    }

    /**
     * @return
     */
    public Boolean getSelectable() {
        return selectable;
    }

    /**
     * @param boolean1
     */
    public void setOrdenable(Boolean boolean1) {
        ordenable = boolean1;
    }

    /**
     * @param boolean1
     */
    public void setSelectable(Boolean boolean1) {
        selectable = boolean1;
    }

    /**
     * @return
     */
    public Boolean getWherable() {
        return wherable;
    }

    /**
     * @param boolean1
     */
    public void setWherable(Boolean boolean1) {
        wherable = boolean1;
    }

    /**
     * @return
     */
    public Boolean getAgregated() {
        return isAgregated;
    }

    /**
     * @param boolean1
     */
    public void setAgregated(Boolean boolean1) {
        isAgregated = boolean1;
    }
    
    public Integer getOrdenableFields() {
        return ordenableFields;
    }

    /**
     * @return
     */
    public Integer getUserReportId() {
        return userReportId;
    }

    /**
     * @param integer
     */
    public void setUserReportId(Integer integer) {
        userReportId = integer;
    }

    public String toString() {
        return super.toString() + " userReportId = " + userReportId + " selectable = " + 
                selectable + " ordenable = " + ordenable + " wherable " +
                wherable + " ordenableFields = " + ordenableFields +
                " fields = " + fields + " errorFields = " + errorFields + 
                " dynamic parameters = " + dynamicParameters; 
    }
    
    public void addDynamicParameter(String value) {
        dynamicParameters.add(value);
    }
    
    public String getDynamicParameter(int index) {
        return (String) dynamicParameters.get(index);
    }

    public Field getField(String table, String column) {
        for (int f = 0; f < fields.size(); f++) {
            Field field = (Field) fields.get(f); 
            if (field.getTableName().equals(table) && 
                    field.getColumnName().equals(column)) {
                return (Field) fields.get(f);
            }
        }
        
        return null;
    }
    public Locale getLocale() {
        return locale;
    }
}
