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

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>


<%-- Layout component 
  parameters : mainMenuBar, secondMenuBar, leftMenuBar, body, featureTitle,  footer 
--%>


<html:html>
  <HEAD>
    <title><bean:message key="all.title"/></title>
  	<link rel="stylesheet" type="text/css" 
  		href="<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_CSS_LOCATION%>'/>" /> 
  </HEAD>
  <body>

   	<table width="760" cellpadding="0" cellspacing="0" border="0">

  	<!-- This is the top bar -->
  	<tr class="topBar">

  	<!-- This is the coporate graphic -->
  	<td class="logo"><center><img src="<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_LOGO_LOCATION%>'/>" /></center></td>

	<!-- This is the menu and feature title bar -->  	
	<td class="threeBars">
		<table  class="threeBars" cellpadding="0" cellspacing="0" border="0">
			<!-- The main menu, to be rendered by a custom tag -->
			<tr class="bar"><td>
				<table class="menu" align="center" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<tiles:insert attribute="mainMenuBar" />
					</tr>
				</table>
			</td></tr>
			<!-- The secondary menu to be rendered by a custom tag -->
			<tr class="bar"><td>
				<table  class="submenu" align="center" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<tiles:insert attribute="secondMenuBar" />
					</tr>
				</table>
			</td></tr>
			<!-- The Feature Title & Logout -->
			<tr class="bar"> <td>
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
				     <td class="title">
				     	<tiles:useAttribute id="barTitle" name="featureTitle"/>
				     	<bean:message name="barTitle"/>
				     </td>
				     
				     <!-- No help for end customers -->
				     <logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
					 	property="mainRoleId"
					 	scope="session"
					 	value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
				     <td class="logout">
						<a target="jbsite" href="http://www.jbilling.com/?q=node/304">
						   <bean:message key="all.training"/>
					    </a>				     
					 </td>
				     <td class="logout">
					    <jbilling:help page="index">
						   <bean:message key="all.help"/>
					    </jbilling:help>
				     </td>
				     </logic:notEqual>
				     
				     <td class="logout">
					    <html:link page="/logout.do">
						   <bean:message key="all.logout"/>
					    </html:link>
				     </td>
					</tr>
				</table>
			</td></tr>
		</table>
	</td>

  	</tr>

	<!-- The Main Body-->
	<tr>
		<td class="leftMenu"><tiles:insert attribute="leftMenuBar"/></td>
		<td class="body"><tiles:insert attribute="body"/></td>
	</tr>
	
	<!-- The Footer -->
	<tr>
		<td colspan="2"><tiles:insert attribute="footer" /></td>
	</tr>


  	</table>
  </body>
</html:html>