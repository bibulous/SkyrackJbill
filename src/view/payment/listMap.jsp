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

<%-- For convenience only ... --%>
<bean:define id="dto" name='<%=Constants.SESSION_PAYMENT_DTO%>'
		         scope="session"/>

<%-- go through each linked invoice and display a link to it --%>
<logic:notEmpty name="dto" property="paymentMaps">

<p class="title"><bean:message key="payment.prompt.invoices"/></p>

<table class="list">
		<%-- print the headers of the columns --%>
		<tr class="listH">
			<td><bean:message key="invoice.id.prompt"/></td>
			<td><bean:message key="payment.link.date"/></td>
			<td><bean:message key="payment.link.amount"/></td>
			<td></td>
		</tr>
		<%-- now each invoice row --%>
		<logic:iterate name="dto"
					   property="paymentMaps"
					   scope="page"
					   id="map"
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
				<td class="list" align="right">
					<html:link action="invoiceMaintain" paramId="id" 
							   paramName="map"
							   paramProperty="invoiceId">
						<bean:write name="map" property="invoiceId"/>
					</html:link>
				</td>
				<td class="list">
					<bean:write name="map" property="createDatetime"
						        formatKey="format.timestamp"/>
				</td>
				<td class="list" align="right">
					<bean:write name="map" property="amount"
						        formatKey="format.money"/>
				</td>
				<td class="list" align="right">
				    <jbilling:permission permission='<%=Constants.P_PAYMENT_MODIFY%>'>
					<html:link page="/paymentMaintain.do?action=unlink" paramId="mapId" 
							   paramName="map"
							   paramProperty="id">
						<bean:message key="payment.link.remove"/>
					</html:link>
					</jbilling:permission>
				</td>
			</tr>	
		</logic:iterate>
</table>
</logic:notEmpty>

<logic:empty name="dto" property="paymentMaps">
	<p class="title"><bean:message key="payment.prompt.noInvoices"/></p>
</logic:empty>
