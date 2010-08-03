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

import java.util.Collection;

import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.common.SessionInternalError;

/**
 *
 * This is the session facade for the reports in general. It is a statless
 * bean that provides services not directly linked to a particular operation
 *
 * @author emilc
 **/
public interface IReportSessionBean {

    /**
     * This retrives the report for the given type (report id)
     */
    public ReportDTOEx getReportDTO(Integer type, Integer entityId) 
            throws SessionInternalError;
    
    /**
     * This retrives a user saved report
     */
    public ReportDTOEx getReportDTO(Integer userReportId) 
            throws SessionInternalError;
    
    public CachedRowSet execute(ReportDTOEx report) throws SessionInternalError;

    public Collection getList(Integer entityId) throws SessionInternalError;

    /**
     * Returns a vector of ReportDTOEx that belong
     * to the given report type
     */
    public Collection getListByType(Integer typeId) throws SessionInternalError;

    public void save(ReportDTOEx report, Integer user, String title) 
            throws SessionInternalError;

    public Collection getUserList(Integer report, Integer userId) 
            throws SessionInternalError;

    public void delete(Integer userReportId) throws SessionInternalError;
}
