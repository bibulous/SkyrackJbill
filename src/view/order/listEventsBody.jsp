<%--
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
--%>

<%@ page language="java" import="com.sapienter.jbilling.client.util.Constants"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/session-1.0" prefix="sess" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>

<p class="title"><bean:message key="mediation.event.title"/></p>

<table class="list">
<%-- print the headers of the columns --%>
<tr class="listH">
    <td><bean:message key="mediation.event.record"/></td>
    <td><bean:message key="mediation.event.line"/></td>
    <td><bean:message key="mediation.event.date"/></td>
    <td><bean:message key="mediation.event.amount"/></td>
    <td><bean:message key="mediation.event.quantity"/></td>
    <td><bean:message key="mediation.event.Description"/></td>
</tr>
<%-- now each of the lines --%>
<logic:iterate name='<%=Constants.SESSION_ORDER_CDR%>' 
               scope="session"
               id="line"
               indexId="index">
    
    <c:choose>
        <c:when test="${flag == 1}">
            <tr class="listB">
            <c:remove var="flag"/>
        </c:when>
        <c:otherwise>
            <tr class="listA">
            <c:set var="flag" value="1"/>
        </c:otherwise>
    </c:choose>
    <td align="right" class="list">
        <bean:write name="line" property="record.key"/>
    </td>
    <td align="right" class="list">
        <bean:write name="line" property="orderLine.id"/>
    </td>
    <td class="list">
        <bean:write name="line" property="eventDate" formatKey="format.timestamp"/>
    </td>
    <td class="list">
        <bean:write name="line" property="amount" formatKey="format.money"/>
    </td>
    <td class="list">
        <bean:write name="line" property="quantity" formatKey="format.double"/>
    </td>
    <td class="list">
        <bean:write name="line" property="description"/>
    </td>
    
    </tr>
    
</logic:iterate>	
</table>		
