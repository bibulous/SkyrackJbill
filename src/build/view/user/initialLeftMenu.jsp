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

<logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
					 property="mainRoleId"
					 scope="session"
					 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>

<table>
	<jbilling:permission permission='<%=Constants.P_USER_CREATE%>'>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/user/create.jsp?customer=yes&create=yes">
		   <bean:message key="user.create.customer.link"/>
		</html:link>
		</td>
	</tr>
	</jbilling:permission>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/user/list.jsp">
		   <bean:message key="user.list.customer.link"/>
		</html:link>
		</td>
	</tr>
</table>

</logic:notEqual>

<logic:equal name='<%=Constants.SESSION_USER_DTO%>'
					 property="mainRoleId"
					 scope="session"
					 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
  <table>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/paymentMaintain.do?action=last_invoice">
		   <bean:message key="payment.inital.link"/>
		</html:link>
		</td>
	</tr>
	<tr>
		<td class="leftMenuOption">
		<html:link styleClass="leftMenu" page="/invoiceMaintain.do?latest=yes">
		   <bean:message key="invoice.initial.link"/>
		</html:link>
		</td>
	</tr>
	
  </table>
					
</logic:equal>