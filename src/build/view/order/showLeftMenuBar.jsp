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

<jbilling:permission permission='<%=Constants.P_ORDER_LEFT_OPTIONS%>'>	
<table>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page="/processMaintain.do?action=newInvoice">
				<bean:message key="order.newInvoice"/>
			</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page="/order/applyInvoice.jsp">
				<bean:message key="order.applyToInvoice"/>
			</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page="/reviewOrder.do?action=read" paramId="id" 
					   paramName='<%=Constants.SESSION_ORDER_DTO%>' 
					   paramProperty="id">
				<bean:message key="order.prompt.edit"/>			
			</html:link>
		</td>
	</tr>
	<logic:notEqual name='<%=Constants.SESSION_ORDER_DTO%>' 
		            property="statusId"
		            value='<%=Constants.ORDER_STATUS_ACTIVE.toString()%>' >
		<tr>
			<td class="leftMenuOption">
				<html:link styleClass="leftMenu" page='<%="/order/confirmStatus.jsp?statusId=" + Constants.ORDER_STATUS_ACTIVE%>' >
					<bean:message key="order.prompt.activate"/>
				</html:link>
			</td>
		</tr>
	</logic:notEqual>
	<logic:notEqual name='<%=Constants.SESSION_ORDER_DTO%>' 
		            property="statusId"
		            value='<%=Constants.ORDER_STATUS_SUSPENDED.toString()%>' >
		<tr>
			<td class="leftMenuOption">
				<html:link styleClass="leftMenu" page='<%="/order/confirmStatus.jsp?statusId=" + Constants.ORDER_STATUS_SUSPENDED%>' >
					<bean:message key="order.prompt.suspend"/>
				</html:link>
			</td>
		</tr>
	</logic:notEqual>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page="/order/confirmStatus.jsp?statusId=delete">
				<bean:message key="order.prompt.delete"/>
			</html:link>
		</td>
	</tr>

        <logic:present name="order_has_cdrs">
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page="/orderMaintain.do?action=listEvents">
				<bean:message key="order.prompt.listCDR"/>
			</html:link>
		</td>
	</tr>
        </logic:present>
</table>
</jbilling:permission>