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

<%@ page language="java" import="com.sapienter.jbilling.client.util.Constants, com.sapienter.jbilling.server.report.ReportDTOEx"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>


<html:errors/>

<p class="title"><bean:message key="report.list.title"/></p>
<p class="instr"><bean:message key="report.list.instr"/></p>

<table class="list">
	<jbilling:reportList mode="entity"/>	
	<logic:iterate  name='<%=Constants.SESSION_REPORT_LIST%>'
					scope="session"
					id="report">
		<jbilling:permission typeId="5" foreignId='<%= ((ReportDTOEx)pageContext.getAttribute("report")).getId() %>'>
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
			<td>
				<bean:message name="report" property="titleKey"/>
			</td>
			<td>
				<html:link page="/report/form.jsp" paramId="report_id" 
						paramName="report" paramProperty="id">
					<bean:message key="report.list.go"/>
				</html:link>
			</td>
		</tr>
		</jbilling:permission>
	</logic:iterate>
</table>
