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
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>

<p class="title"><bean:message key="login.title"/></p>
<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>


<html:form action="/verifyPassword" focus="userName">
<table class="form">

  <tr class="form">
    <td class="form_prompt">
      <bean:message key="prompt.username"/>
    </td>
    <td>
      <html:text property="userName" size="20" maxlength="20"/>
    </td>
  </tr>

  <tr class="form">
    <td class="form_prompt">
      <bean:message key="prompt.password"/>
    </td>
    <td>
      <html:password property="password" size="20" maxlength="20"/>
    </td>
  </tr>

  <%
    // if the entityId is present, put it in a hidden field, 
    // otherwise show a field for the user to enter it.
    String entityId = request.getParameter("entityId");
	Integer asInt = null;
	try { asInt = Integer.valueOf(entityId); }
	catch (Exception e) {}

    if (entityId == null || entityId.length() == 0 || asInt == null) {
  %>
	  <tr class="form">
		<td class="form_prompt">
		  <bean:message key="prompt.entityId"/>
		</td>
		<td>
		  <html:text property="entityId" size="4" maxlength="4"/>
		</td>
	  </tr>
  <% } else { %>
  	<html:hidden property="entityId" value='<%=request.getParameter("entityId") %>' />
  <% } %>
  
  <logic:present parameter="internalKey">
  	<html:hidden property="internalKey" value='<%=request.getParameter("internalKey") %>' />
  </logic:present>  

  <tr>
    <td colspan="2" class="form_button">
      <html:submit styleClass="form_button" property="submit" value="Submit"/>
    </td>
  </tr>

</table>

</html:form>


<a href="<c:url value="/user/forgetPassword.jsp"/>"><bean:message key="prompt.forgetpassword" /></a>


<%-- Experiment for Container base authentication 
<form method="POST" action="j_security_check">
	<input type="text" name="j_username">
	<input type="password" name="j_password">
	<input type="submit"/>
</form>
--%>

