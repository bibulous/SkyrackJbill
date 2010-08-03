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

<logic:present name='<%=Constants.SESSION_USER_ID%>' 
	                         scope="session">
<%-- These links make sense only if this is not self-edition --%>
<logic:notEqual name='<%=Constants.SESSION_USER_ID%>' 
                scope="session" 
                value='<%=((Integer) session.getAttribute(Constants.SESSION_LOGGED_USER_ID)).toString() %>' >
<jbilling:permission permission='<%=Constants.P_USER_EDIT_LINKS%>'>	                         
<table>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/userMaintain.do?action=confirmDelete">
		   <bean:message key="user.edit.delete"/>
		</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/contactEdit.do?action=setup">
		   <bean:message key="user.edit.contact"/>
		</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/creditCardMaintain.do?action=setup&mode=creditCard">
		   <bean:message key="user.edit.creditCard"/>
		</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/achMaintain.do?action=setup&mode=ach">
		   <bean:message key="user.edit.ach"/>
		</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/userMaintain.do?action=order">
		   <bean:message key="user.edit.order"/>
		</html:link>
		</td>
	</tr>
	
</table>
</jbilling:permission>
</logic:notEqual>
</logic:present>
