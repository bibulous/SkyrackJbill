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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.report.db.ReportFieldDTO;



public class Field extends ReportFieldDTO {
    
	private static final Logger LOG = Logger.getLogger(Field.class);
	
    /*
     * Supported data types
     */
    public static final String TYPE_STRING="string";
    public static final String TYPE_INTEGER="integer";
    public static final String TYPE_DATE="date";
    public static final String TYPE_FLOAT="float";
    /*
     * Functions
     */
    public static final String FUNCTION_SUM="sum"; 
    public static final String FUNCTION_AVG="avg";
    public static final String FUNCTION_MIN="min";
    public static final String FUNCTION_MAX="max";
    /*
     * Operators
     */
    public static final String OPERATOR_EQUAL="=";
    public static final String OPERATOR_DIFFERENT="!=";
    public static final String OPERATOR_GREATER=">";
    public static final String OPERATOR_SMALLER="<";
    public static final String OPERATOR_GR_EQ=">=";
    public static final String OPERATOR_SM_EQ="<=";
    
    /*
     * These are the minimum parameters. If not further setters are
     * called, the column will be just included in the select.
     */
    public Field(String table, String column, 
            String dataType) {
        
        setTableName(table);
        setColumnName(column);
        setDataType(dataType);
        

        Integer flag = new Integer(0);
        setIsShown(new Integer(1));
        // start the object defaulting to all false for the user fields
        setFunctionable(flag);
        setIsGrouped(flag);
        setSelectable(flag);
        setOrdenable(flag);
        setOperatorable(flag);
        setWherable(flag);
    }
    
    public  String getOperatorKey() {
        Logger log = Logger.getLogger(Field.class);
        if (getOperatorValue().equals(OPERATOR_DIFFERENT)) {
            return "reports.operator.prompt.notequal";
        } else if (getOperatorValue().equals(OPERATOR_EQUAL)) {
            return "reports.operator.prompt.equal";
        } else if (getOperatorValue().equals(OPERATOR_GR_EQ)) {
            return "reports.operator.prompt.eq_gr";
        } else if (getOperatorValue().equals(OPERATOR_GREATER)) {
            return "reports.operator.prompt.greater";
        } else if (getOperatorValue().equals(OPERATOR_SM_EQ)) {
            return "reports.operator.prompt.eq_sm";
        } else if (getOperatorValue().equals(OPERATOR_SMALLER)) {
            return "reports.operator.prompt.smaller";
        } else {
            log.fatal("unable to map " + getOperatorValue());
            return null;
        }
    }
    
    public String getTitleKey() {
        Logger log = Logger.getLogger(Field.class);
        if (super.getTitleKey() == null) {
            log.debug("Creating the titleKey for " + getColumnName());
            return "report.prompt." + getTableName() + "." + getColumnName();
        } else {
            return super.getTitleKey();
        }
    }
    
    public void setFunctionVal(String fun) throws SessionInternalError {
        if (validateFunction(fun)) {
            super.setFunctionName(fun);
        } else {
            throw new SessionInternalError("Function not supported:" +
                    fun);
        }
    }
    
    public void setWherable(Integer w) {
        if (w.intValue() == 1 && getOperatorValue() == null) {
            setOperatorValue(OPERATOR_EQUAL);
        }
        super.setWhereable(w);
    }
    
    public boolean isAgregated() {
        if (getFunctionName() != null || getIsGrouped().intValue() == 1) {
            return true;
        }
        return false;
    }
    
    /*
     * Validation funcitons
     */
    public int validate(Locale locale) {
        Logger log = Logger.getLogger(Field.class);
        int retValue = ReportDTOEx.OK;
        
        if (getTableName() == null) {
            log.debug("Validation:" + "table" + " can't be null");
            retValue = ReportDTOEx.ERROR_ISNULL;
        }
        if (getColumnName() == null) {
            log.debug("Validation:" + "column" + " can't be null");
            retValue = ReportDTOEx.ERROR_ISNULL;
        }
        if (getIsShown() == null) {
            log.debug("Validation:" + "isShown" + " can't be null");
            retValue = ReportDTOEx.ERROR_ISNULL;
        }
        if (getDataType() == null) {
            log.debug("Validation:" + "data type" + " can't be null");
            retValue = ReportDTOEx.ERROR_ISNULL;
        }
        if (getFunctionName() != null && 
                validateFunction(getFunctionName()) == false) {
            retValue = ReportDTOEx.ERROR_FUNCTION;
        }
        if (validateDataType(getDataType()) == false) {
            retValue = ReportDTOEx.ERROR_DATATYPE;
        }

        if (getWhereValue() != null) {
            // then we need an operator
            // it has to be consistent with the data type
            if (getOperatorValue() == null) {
                log.debug("Operator is required when where value is specified.");
                retValue = ReportDTOEx.ERROR_NO_OPERATOR;
            } 
            
            int ret = validateWhere(getWhereValue(), locale);
            if (ret != ReportDTOEx.OK) {
                retValue = ret;
            }
        }
        
        if (getOperatorValue() != null) {
            if (validateOperator(getOperatorValue()) == false) {
                retValue = ReportDTOEx.ERROR_OPERATOR;
            }
        }
        
        if (getFunctionName()!= null && getIsGrouped().intValue() == 1){
            log.debug("A field can't have a function and be grouped by at " +
                    "the same time");
            retValue = ReportDTOEx.ERROR_FUNCTION;
        }
        
        if (getWhereable().intValue() == 1 && super.getTitleKey() == null) {
            log.debug("Can't be whereable and not have a title key");
            retValue = ReportDTOEx.ERROR_WHERE;
        }
        
        return retValue;
    }


    private boolean validateFunction(String fun) {
        if (fun.equals(FUNCTION_AVG) || fun.equals(FUNCTION_MAX) || 
                fun.equals(FUNCTION_MIN) || fun.equals(FUNCTION_SUM)) {
            if (getDataType().equals(TYPE_STRING)) {
                LOG.debug("type string is not functionable");
                return false;
            } else if (getDataType().equals(TYPE_DATE) &&
                    (fun.equals(FUNCTION_AVG) || fun.equals(FUNCTION_SUM))) {
                LOG.debug("type date can't use avg or sum");
                return false;
            }
            return true;            
        } else {
            LOG.debug("Function " + fun + " not supported");
            return false;
        }
    }
    
    private boolean validateDataType(String type) {
        if (type.equals(TYPE_INTEGER) || type.equals(TYPE_STRING) || 
                type.equals(TYPE_FLOAT) || type.equals(TYPE_DATE)) {
            return true;            
        } else {
            LOG.debug("Datatype " + type + " not supported");
            return false;
        }
    }
    
    private int validateWhere(String where, Locale locale) {
        int retValue = ReportDTOEx.OK;
        
        if (where.length() == 0 || where.equals("?")) {
            // it's empty, a dynamic value or a null value
            return retValue;
        } 
        
        if (where.equalsIgnoreCase("null")) {
            // the operator can be only equal or not equal
            if (!getOperatorValue().equals(Field.OPERATOR_EQUAL) &&
                    !getOperatorValue().equals(Field.OPERATOR_DIFFERENT)) {
                retValue = ReportDTOEx.ERROR_NULL_OPERATOR;
            } 
            return retValue; // further checking would fail
        }
        if (retValue == ReportDTOEx.OK && getDataType().equals(TYPE_INTEGER)) {
            try {
                // see if it is a multiple value entry
                if (where.indexOf(',') >= 0) {
                    StringTokenizer values = new StringTokenizer(where, ",");
                    while(values.hasMoreElements()) {
                        Integer.valueOf(values.nextToken());
                    }
                    if (!getOperatorValue().equals(Field.OPERATOR_EQUAL) &&
                            !getOperatorValue().equals(Field.OPERATOR_DIFFERENT)) {
                        retValue = ReportDTOEx.ERROR_IN_OP_EQUAL;
                    }
                } else {
                    Integer.valueOf(where);
                }
            } catch (Exception e) {
                LOG.debug("Where value " + where + " should be an integer");
                retValue = ReportDTOEx.ERROR_WHERE_NOINTEGER;
            }
        }
        if (retValue == ReportDTOEx.OK && getDataType().equals(TYPE_FLOAT)) {
            try {
                NumberFormat nf = NumberFormat.getInstance(locale);
                nf.parse(where).floatValue();
            } catch (ParseException e) {
                retValue = ReportDTOEx.ERROR_WHERE_NOFLOAT;
            }
        }
        if (retValue == ReportDTOEx.OK && getDataType().equals(TYPE_DATE)) {
            if (Util.parseDate(where) == null) {
                retValue = ReportDTOEx.ERROR_WHERE_NODATE;
            }
        }

        return retValue;
    }

    private boolean validateOperator(String op) {
        if (op.equals(OPERATOR_DIFFERENT) || op.equals(OPERATOR_EQUAL) || 
                op.equals(OPERATOR_GR_EQ) || op.equals(OPERATOR_GREATER) || 
                op.equals(OPERATOR_SM_EQ) ||op.equals(OPERATOR_SMALLER)) {
            return true;
        } 

        LOG.debug("Operator " + op + " it's not supported");
        return false;
    }
}

