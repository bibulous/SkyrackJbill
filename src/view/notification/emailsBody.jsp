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


<p class="title"><bean:message key="notification.emails.title"/></p>
<p class="instr"><bean:message key="notification.emails.instr"/></p>

<html:form action="/notificationEmails">
	<table class="form">
    	<tr class="form">
    		<td class="form_prompt"> <bean:message key="notification.emails.separator"/> </td>
    		<td align="left"><html:text property="separator" size="3"/></td>
		</tr>
		<tr>
    		<td colspan="2"><html:textarea rows="15" cols="50"
    				property="content" readonly="true"/></td>
    	</tr>
		<tr><td colspan="2"  class="form_button">
			<html:submit styleClass="form_button">
				<bean:message key="notification.emails.all.button"/>
			</html:submit>
		</td></tr>
	</table>
</html:form>