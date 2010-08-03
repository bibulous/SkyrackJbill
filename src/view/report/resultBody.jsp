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

<%@ page language="java" import="com.sapienter.jbilling.client.util.Constants, org.apache.struts.Globals"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>


<p align="center">
	<%-- The download page won't be served by the struts contoller,
	     therefore, the request will lack the messages. These are 
	     necessary for the report to show the proper column names. --%>
	<bean:define name='<%=Globals.MESSAGES_KEY%>'
	             scope="request"
	             toScope="session"
	             id="sessionMessages"/>
	<html:link page="/report/download.jsp">
		<bean:message key="report.download.prompt"/>
	</html:link> <br/>
	<logic:present name='<%=Constants.SESSION_REPORT_LINK%>' scope="session">
	<html:link page='<%= "/report/form.jsp?back=yes&"  + session.getAttribute(Constants.SESSION_REPORT_LINK) %>'>
		<bean:message key="report.link"/>
	</html:link>
	</logic:present>
</p>

<bean:define id="firstField" value="0" scope="page"/>
<logic:equal name='<%=Constants.SESSION_REPORT_DTO%>'
			 property="idColumn"
			 value="1"
			 scope="session">
	<bean:define id="firstField" value="1" scope="page"/>
	<logic:equal name='<%=Constants.SESSION_REPORT_DTO%>'
				 property="agregated"
				 value="false"
				 scope="session">
		<bean:define id="showId" value="yes" scope="page"/>
	</logic:equal>
</logic:equal>

<table class="list">
	<tr class="listH">
		<logic:iterate  name='<%=Constants.SESSION_REPORT_DTO%>'
							property="fields"
							scope="session"
							id="field"
							offset='<%= (String)pageContext.getAttribute("firstField") %>'>
			<logic:equal name="field"
				             property="isShown"
				             value="1">
   	        	<td><bean:message name="field" property="titleKey"/> </td>
			</logic:equal>
		</logic:iterate>	
		<logic:present name="showId" scope="page">
			<td></td>
		</logic:present>
	</tr>		
	<jbilling:reportExecute>
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
			<logic:present name="showId" scope="page">
				<td class="list">
					<bean:define id="link" name='<%=Constants.SESSION_REPORT_DTO%>' property="link"/>
					<html:link page='<%= (String) pageContext.getAttribute("link") %>'
						       paramId="id"
						       paramName="rowID">
						 <bean:message key="reports.link.prompt"/>
					</html:link>
				</td>
			</logic:present>
		</tr>
	</jbilling:reportExecute>
</table>
