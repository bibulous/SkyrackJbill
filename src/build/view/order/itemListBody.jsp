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

<%@ page language="java"  import="com.sapienter.jbilling.client.util.Constants"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>


<jbilling:genericList setup="true" type='<%=Constants.LIST_TYPE_ITEM_ORDER%>' method="ejb"/>      

<table class="list">
	<tr class="listH">
		<td><bean:message key="item.prompt.description"/></td>
		<td></td>
		<td><bean:message key="item.prompt.price"/></td>
		<td></td>
		<td></td>
	</tr>		
		<jbilling:genericList>
		<c:choose>
			<c:when test="${colorFlag == 1}">
				<tr class="listB">
				<c:remove var="colorFlag"/>
			</c:when>
			<c:otherwise>
				<tr class="listA">
				<c:set var="colorFlag" value="1"/>
			</c:otherwise>
	    </c:choose>
			<jbilling:insertDataRow/>
			<html:form action="/newOrderItem?action=add">
			<input type="hidden" name="itemID" value="<bean:write name="rowID"/>" />
			<td><input class="form_button" type="submit" value="<bean:message key="order.item.add"/>"/></td>
			<td><html:text property="quantity" size="6" value="1" /></td>
			</html:form>
		</tr>
		</jbilling:genericList>
</table>

