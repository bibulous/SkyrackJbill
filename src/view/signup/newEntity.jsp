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
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>


<HTML>
  <HEAD>
    <title><bean:message key="newEntity.window_title"/></title>
    <link rel="stylesheet" type="text/css" 
  		href="/billing/css/jbilling.css" /> 
  </HEAD>
<body>

<p align="center">
<table class="body" style="width: 670px;">
	<tr align="center">
	  <td valign="top" align="center">
		
		<p align=center>
			<html:errors/>
		</p>
		<table class="info" align="center">
			<tr>
				 <th class="info" colspan="3"><bean:message key="newEntity.form.title"/></th>
			</tr>
			<tr><td>&nbsp</td><td></td></tr>
			
			<html:form action="/signupEntity.do?action=edit">
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_subtitle" colspan="2"><bean:message key="newEntity.prompt.company"/></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.organizationName"/></td>
					 <td><html:text property="company_name" size="40" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.address1"/></td>
					 <td><html:text property="address1" size="40" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.address2"/></td>
					 <td><html:text property="address2" size="40" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.city"/></td>
					 <td><html:text property="city" size="20" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.stateProvince"/></td>
					 <td><html:text property="state" size="20" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.countryCode"/></td>
					 <td>
						<jbilling:getOptions countries="true"/>
						<html:select property="country">
						  <html:options collection='<%=Constants.PAGE_COUNTRIES%>' 
											property="code"
											labelProperty="description"
											filter="false"/>
					   </html:select>
					</td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.postalCode"/></td>
					 <td><html:text property="postal_code" size="6" /></td>
				</tr>
		
				<tr><td>&nbsp</td><td></td></tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_subtitle" colspan="2"><bean:message key="newEntity.prompt.contact"/></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_instr" colspan="2"><bean:message key="newEntity.instr.user"/></td>
				</tr>
				
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.firstName"/></td>
					 <td><html:text property="first_name" size="20" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.lastName"/></td>
					 <td><html:text property="last_name" size="20" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.phoneAreaCode"/></td>
					 <td><html:text property="phone_area" size="5" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.phoneNumber"/></td>
					 <td><html:text property="phone_number" size="20" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="contact.prompt.email"/></td>
					 <td><html:text property="email" size="40" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="prompt.username"/></td>
					 <td><html:text property="user_name" size="20" /></td>
				</tr>
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td class="signup_prompt"><bean:message key="prompt.password"/></td>
					 <td><html:text property="password" size="20" /></td>
				</tr>
				
				<tr class="form">
					<td class="signup_pad">&nbsp</td>
					<td colspan="2" class="form_button">
						  <html:submit styleClass="form_button">
							  <bean:message key="newEntity.prompt.continue"/>
						  </html:submit>
					</td>		
				</tr>
			</html:form>
			<tr><td>&nbsp</td><td></td></tr>
		</table>
	  </td>
	</tr>
	<tr>
	  <td>
		<p class="footer"><bean:message key="all.copyright"/></p>
	  </td>
	</tr>
</table>
</p>

</body>
</html> 