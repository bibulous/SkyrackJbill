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

package com.sapienter.jbilling.client.report;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Provides a class for the jsp to interact with, when collecting the
 * user's preferences.
 */
public class Form extends ActionForm {
    private boolean select[] = null;
    private String where[] = null;
    private String orderBy[] = null;
    private String operator[] = null;
    private String function[] = null;
    // for date management
    private String year[] = null;
    private String month[] = null;
    private String day[] = null;
    private int size;
    // for the save report criteria
    private String saveName = null;
    private String saveFlag = null;
    
    public Form(int size) {
        select = new boolean[size];
        where = new String[size];
        orderBy = new String[size];
        operator = new String[size];
        function = new String[size];
        year = new String[size];
        month = new String[size];
        day = new String[size];
        this.size = size;        
    }

    public boolean getSelect(int i) {
        return select[i];
    }
    
    public void setSelect(int i, boolean s) {
        select[i] = s;
    }
    /**
     * @return
     */
    public String getOrderBy(int i) {
        return orderBy[i];
    }

    /**
     * @return
     */
    public String getWhere(int i) {
        return where[i];
    }

    /**
     * @param is
     */
    public void setOrderBy(int i, String is) {
        orderBy[i] = is;
    }

    /**
     * @param strings
     */
    public void setWhere(int i, String strings) {
        where[i] = strings;
    }
    
    public int getSize() {
        return size;
    }

    /**
     * @return
     */
    public String getDay(int i) {
        return day[i];
    }

    /**
     * @return
     */
    public String getMonth(int i) {
        return month[i];
    }

    /**
     * @return
     */
    public String getYear(int i) {
        return year[i];
    }

    /**
     * @param is
     */
    public void setDay(int i, String is) {
        day[i] = is;
    }

    /**
     * @param is
     */
    public void setMonth(int i, String is) {
        month[i] = is;
    }

    /**
     * @param is
     */
    public void setYear(int i, String is) {
        year[i] = is;
    }

    /**
     * @return
     */
    public String getOperator(int i) {
        return operator[i];
    }

    /**
     * @param strings
     */
    public void setOperator(int i,String operator) {
        this.operator[i] = operator;
    }

    /**
     * @return
     */
    public String getFunction(int i) {
        return function[i];
    }

    /**
     * @param strings
     */
    public void setFunction(int i, String fun) {
        function[i] = fun;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // this is a requirement form the checkbox tag
        for (int f = 0; f < select.length; f++) {
            select[f] = false;
        } 
        saveFlag = null;
        saveName = null;
    }
    /**
     * @return
     */
    public String getSaveName() {
        return saveName;
    }

    /**
     * @param string
     */
    public void setSaveName(String string) {
        saveName = string;
    }

    /**
     * @return
     */
    public String getSaveFlag() {
        return saveFlag;
    }

    /**
     * @param string
     */
    public void setSaveFlag(String string) {
        saveFlag = string;
    }

    public String toString() {
        StringBuffer retValue = new StringBuffer();
        int f;
        
        retValue.append("select = ");
        
        for (f = 0; f < select.length; f++) {
            retValue.append(select[f] + ",");
        }

        retValue.append("\nwhere = ");
        
        for (f = 0; f < where.length; f++) {
            retValue.append(where[f] + ",");
        }

        retValue.append("\norder by = ");
        
        for (f = 0; f < orderBy.length; f++) {
            retValue.append(orderBy[f] + ",");
        }

        retValue.append("\noperator = ");
        
        for (f = 0; f < operator.length; f++) {
            retValue.append(operator[f] + ",");
        }

        retValue.append("\nfunction = ");
        
        for (f = 0; f < year.length; f++) {
            retValue.append(year[f] + ",");
        }

        retValue.append("\nyear = ");
        
        for (f = 0; f < year.length; f++) {
            retValue.append(year[f] + ",");
        }

        retValue.append("\nmonth = ");
        
        for (f = 0; f < month.length; f++) {
            retValue.append(month[f] + ",");
        }

        retValue.append("\nday = ");
        
        for (f = 0; f < day.length; f++) {
            retValue.append(day[f] + ",");
        }
        
        retValue.append("\nsize = " + size + " saveName = " +
                saveName + " saveFalg = " + saveFlag);
        return retValue.toString();
    }
}
