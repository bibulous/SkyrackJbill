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

package com.sapienter.jbilling.client.list;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.server.list.ListDTO;

/**
 * This is the base for custom tag that will be used to list data.
 * The class that extends this one has to implement the doStartTag and
 * get the CachedRowSet from the session bean.
 * @author emilc
 *
 */

public abstract class ListTagBase extends TagSupport {

    public static final int METHOD_JDBC = 1;
    public static final int METHOD_EJB = 2;

    protected CachedRowSet queryResults = null;
    protected ListDTO queryDtoResults = null;
    protected int dtoIndex = 0;
    protected int queryMethod = METHOD_JDBC;
    protected Logger log = null;
    
    public int doAfterBody() throws JspException {
        int retValue = SKIP_BODY;
        // now verify that there's another row to render
        try {
            if (queryMethod == METHOD_JDBC) {
                if (queryResults.next()) {
                    retValue = EVAL_BODY_AGAIN;
                }
            } else {
                dtoIndex++;
                if (dtoIndex < queryDtoResults.getLines().size()) {
                    retValue = EVAL_BODY_AGAIN;
                }
            }
        } catch (Exception e) {
            log.error("Exception at RowSet tag", e);
            throw new JspException("Web error" + e.getMessage());
        }

        return retValue;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
    
    public CachedRowSet getQueryResults() {
        return queryResults;
    }

    /**
     * @return
     */
    public int getDtoIndex() {
        return dtoIndex;
    }

    /**
     * @return
     */
    public ListDTO getQueryDtoResults() {
        return queryDtoResults;
    }

    /**
     * @return
     */
    public int getQueryMethod() {
        return queryMethod;
    }

    /**
     * @param i
     */
    public void setDtoIndex(int i) {
        dtoIndex = i;
    }

    /**
     * @param vector
     */
    public void setQueryDtoResults(ListDTO vector) {
        queryDtoResults = vector;
    }

    /**
     * @param i
     */
    public void setQueryMethod(int i) {
        queryMethod = i;
    }

}
