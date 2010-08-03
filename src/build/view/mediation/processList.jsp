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

<p class="title"> <bean:message key="mediation.process.list.title"/> </p>
<p class="instr"> <bean:message key="mediation.process.list.instr"/> </p>

<logic:notEmpty name="mediation_process_list" scope="session">
<table class="list">
	<tr class="listH">
		<td><bean:message key="mediation.process.list.id"/></td>
		<td><bean:message key="mediation.process.list.mediation_id"/></td>
		<td><bean:message key="mediation.process.list.start_datetime"/></td>
		<td><bean:message key="mediation.process.list.end_datetime"/></td>
		<td><bean:message key="mediation.process.list.orders"/></td>
	</tr>		
  	<logic:iterate id="oneProcess" name="mediation_process_list" 
		           scope="session" indexId="index">
	
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

		<td class="list">
		    <bean:write name="oneProcess" 
                        property="id"/>
		</td>
		<td class="list">
		    <bean:write name="oneProcess" 
                        property="configuration.id"/>
		</td>
		<td class="list">
		    <bean:write name="oneProcess"
                        property="startDatetime"
                        formatKey="format.timestamp"/>
		</td>
		<td class="list">
		    <bean:write name="oneProcess"
                        property="endDatetime"
                        formatKey="format.timestamp"/>
		</td>
		<td class="list">
		    <bean:write name="oneProcess"
                        property="ordersAffected"/>
		</td>

		</tr>
	</logic:iterate>
</table>
</logic:notEmpty>

<logic:empty name="mediation_process_list" scope="session">
   <bean:message key="mediation.process.list.empty"/>
</logic:empty>

<p class="instr"> <bean:message key="records.by.statuses.map.instr"/> </p>

<logic:notEmpty name="records_by_statuses_map" scope="session">
<table class="list">
	<tr class="listH">
		<td><bean:message key="records.by.statuses.map.description"/></td>
		<td><bean:message key="records.by.statuses.map.records_count"/></td>
	</tr>
  	<logic:iterate id="iter" name="records_by_statuses_map"
		           scope="session" indexId="index">

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

		<td class="list">
		    <bean:write name="iter"
                        property="key.description"/>
		</td>
		<td class="list">
		    <bean:write name="iter"
                        property="value"/>
		</td>
	</logic:iterate>
</table>
</logic:notEmpty>