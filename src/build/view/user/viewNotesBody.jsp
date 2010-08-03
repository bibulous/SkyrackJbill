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

<logic:notPresent name='<%=Constants.SESSION_CUSTOMER_DTO%>' 
		property="customer.parent">
<table class="info" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th class="info"><bean:message key="user.notes.info.title"/></th>
	</tr>
	<tr class="infoA">
		<td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_DTO%>'  
			property="customer.notes" scope="session" filter="false"/></td>
	</tr>
	<jbilling:permission permission='<%=Constants.P_USER_EDIT_LINKS%>'>
	<tr>
		<td class="infocommands">
			<html:link page="/userNotesEdit.do?action=setup">
				<bean:message key="user.edit.notes.link"/>
			</html:link>
		</td>
	</tr>
	</jbilling:permission>
</table>
</logic:notPresent>