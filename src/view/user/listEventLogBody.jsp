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

<%@ page language="java" import="com.sapienter.jbilling.client.util.Constants;"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>

<p class="title"> <bean:message key="user.eventLog.list.title"/> </p>
<p class="instr"> <bean:message key="user.eventLog.list.instr"/>
    <bean:write name='<%=Constants.SESSION_USER_ID%>' scope="session"/> </p>

<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<table class="list">
    <tr class="listH">
        <td><bean:message key="user.eventLog.column.id"/></td>
        <td><bean:message key="user.eventLog.column.executorUserName"/></td>
        <td><bean:message key="user.eventLog.column.tableName"/></td>
        <td><bean:message key="user.eventLog.column.foreignId"/></td>
        <td><bean:message key="user.eventLog.column.createDate"/></td>
        <td><bean:message key="user.eventLog.column.messageLevel"/></td>
        <td><bean:message key="user.eventLog.column.module"/></td>
        <td><bean:message key="user.eventLog.column.message"/></td>
        <td><bean:message key="user.eventLog.column.oldNumber"/></td>
        <td><bean:message key="user.eventLog.column.oldString"/></td>
        <td><bean:message key="user.eventLog.column.oldDate"/></td>
    </tr>

    <c:set var="flag" value="1"/>
    <logic:iterate id="event" name='<%=Constants.REQUEST_EVENT_LOG%>' scope="request">
        <c:choose>
            <c:when test="${flag == 1}">
                <tr class="listA">
                <c:remove var="flag"/>
            </c:when>
            <c:otherwise>
                <tr class="listB">
                <c:set var="flag" value="1"/>
            </c:otherwise>
        </c:choose>

        <td class="list"><bean:write name="event" property="id"/></td>

        <td class="list">
            <logic:notEmpty name="event" property="baseUser">
                <bean:write name="event" property="baseUser.userName"/>
            </logic:notEmpty>
        </td>

        <td class="list">
            <logic:notEmpty name="event" property="jbillingTable">
                <bean:write name="event" property="jbillingTable.name"/>
            </logic:notEmpty>
        </td>

        <td class="list"><bean:write name="event" property="foreignId"/></td>

        <td class="list"><bean:write name="event" property="createDatetime"/></td>

        <td class="list"><bean:message key="user.eventLog.level.${event.levelField}"/></td>
        

        <td class="list"><bean:message key="user.eventLog.module.${event.eventLogModule.id}"/></td>

        <td class="list"><bean:message key="user.eventLog.message.${event.eventLogMessage.id}"/></td>

        <td class="list">
            <logic:notEmpty name="event" property="oldNum">
                <bean:write name="event" property="oldNum"/>
            </logic:notEmpty>
        </td>

        <td class="list">
            <logic:notEmpty name="event" property="oldStr">
                <bean:write name="event" property="oldStr"/>
            </logic:notEmpty>
        </td>

        <td class="list">
            <logic:notEmpty name="event" property="oldDate">
                <bean:write name="event" property="oldDate"/>
            </logic:notEmpty>
        </td>
    </tr>
    </logic:iterate>
</table>
