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

<%@ page language="java" import="com.sapienter.jbilling.server.report.ReportDTOEx,com.sapienter.jbilling.client.util.Constants"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
	
<table>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page='<%="/reportTrigger.do?mode=customer&id=" + ReportDTOEx.REPORT_ORDER%>'>
				<bean:message key="user.report.orders"/>
			</html:link>
		</td>
	</tr>
	
	<logic:notPresent name='<%=Constants.SESSION_CUSTOMER_DTO%>' 
		property=".parentId">
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page='<%="/reportTrigger.do?mode=customer&id=" + ReportDTOEx.REPORT_INVOICE%>'>
				<bean:message key="user.report.invoices"/>
			</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page='<%="/reportTrigger.do?mode=customer&id=" + ReportDTOEx.REPORT_PAYMENT%>'>
				<bean:message key="user.report.payments"/>
			</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page='<%="/reportTrigger.do?mode=customer&id=" + ReportDTOEx.REPORT_REFUND%>'>
				<bean:message key="user.report.refunds"/>
			</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page="/user/eventLog.do?action=displayLog">
				<bean:message key="user.eventLog.initial.link"/>
			</html:link>
		</td>
	</tr>
	</logic:notPresent>
	
	<%-- not a report, this is the list of children accounts --%>
	<logic:present name='<%=Constants.SESSION_CUSTOMER_DTO%>' property="customer.isParent">
		<logic:equal name='<%=Constants.SESSION_CUSTOMER_DTO%>' 
			property="customer.isParent" value="1">
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page="/user/listSubAccounts.jsp">
				<bean:message key="user.report.sub_accounts"/>
			</html:link>
		</td>
		</logic:equal>
	</logic:present>
</table>
