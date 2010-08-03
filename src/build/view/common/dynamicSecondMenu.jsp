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

<%@ page language="java" import="com.sapienter.jbilling.client.util.Constants, com.sapienter.jbilling.server.user.MenuOption"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>


<logic:iterate name='<%=Constants.SESSION_USER_DTO%>'
	                       property="menu.subOptions"
	                       scope="session"
	                       id="option">
	<bean:define id="flag" value="no"/>
	<logic:equal name="option" property="selected" value="true">
		<td class="menuSelected">
		<bean:define id="flag" value="yes"/>
	</logic:equal>
	<logic:equal name="flag" value="no">
		<td class="menu">
	</logic:equal>
	<logic:notMatch name="option" property="link" value="HELP">
		<html:link page='<%= ((MenuOption) pageContext.getAttribute("option")).getLink() %>'
				paramId="menu_option" paramName="option" paramProperty="id">
			<bean:write name="option" property="display"/>
		</html:link>
	</logic:notMatch>
	<logic:match name="option" property="link" value="HELP">
		<jbilling:help page='<%= ((MenuOption) pageContext.getAttribute("option")).getLink() %>'
				isMenu="true">
			<bean:write name="option" property="display"/>
		</jbilling:help>
	</logic:match>
	</td>
</logic:iterate>
