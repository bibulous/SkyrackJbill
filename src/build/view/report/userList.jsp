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
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>


<table class="list">
	<tr class="listH">
		<td colspan="4"><bean:message key="report.user.list.title"/></td>
	</tr>
	
	<jbilling:reportList mode="user"/>	
	<logic:iterate  name='<%=Constants.SESSION_REPORT_LIST_USER%>'
					scope="session"
					id="userReport">
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
				<bean:write name="userReport" property="id"/>-
			</td>

			<td class="list">
				<bean:write name="userReport" property="title"/>
			</td>
			<td class="list">
				<html:link page="/report/form.jsp" paramId="user_report_id" 
						paramName="userReport" paramProperty="id">
					<bean:message key="report.user.load.prompt"/>
				</html:link>
			</td>
			<td class="list">
				<html:link page="/report/delete.jsp" paramId="user_report_id" 
						paramName="userReport" paramProperty="id">
					<bean:message key="report.user.delete.prompt"/>
				</html:link>
			</td>
		</tr>
	</logic:iterate>

</table>