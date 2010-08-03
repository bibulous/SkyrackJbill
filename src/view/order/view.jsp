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

<table class="info">
	<tr>
		<th class="info" colspan="2"><bean:message key="order.info.title"/></th>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="order.prompt.period"/></td>
		<td class="infodata">
			<bean:write name='<%=Constants.SESSION_ORDER_SUMMARY%>' 
				                    property="periodStr" 
				                    scope="session"/>
		</td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="order.prompt.billingType"/></td>
		<td class="infodata">
			<bean:write name='<%=Constants.SESSION_ORDER_SUMMARY%>' 
				                    property="billingTypeStr" 
				                    scope="session"/>
		</td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="order.prompt.activeSince"/></td>
		<td class="infodata">
			<bean:write name='<%=Constants.SESSION_ORDER_SUMMARY%>' 
				                    property="activeSince" 
				                    scope="session"
				                    formatKey="format.date"/>
		</td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="order.prompt.activeUntil"/></td>
		<td class="infodata">
			<bean:write name='<%=Constants.SESSION_ORDER_SUMMARY%>' 
				                    property="activeUntil" 
				                    scope="session"
				                    formatKey="format.date"/>
		</td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="order.prompt.cycleStart"/></td>
		<td class="infodata">
			<bean:write name='<%=Constants.SESSION_ORDER_SUMMARY%>' 
				                    property="cycleStarts" 
				                    scope="session"
				                    formatKey="format.date"/>
		</td>
	</tr>
	
</table>