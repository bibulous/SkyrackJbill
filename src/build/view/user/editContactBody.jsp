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

<p class="title">
<bean:message key="user.contact.edit.title"/>
</p>
<p class="instr">
<bean:message key="user.contact.edit.instr"/>
</p>

<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>
<html:errors/>

<jbilling:getOptions countries="true"/>
<jbilling:getOptions contactType="true"/>
<%--  show the contact form  --%>
<html:form action="/contactEdit?action=submit">
	 <table class="form">
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.type"/></td>
	 		<td >		
	 			<html:select property="type">
			          <html:options collection='<%=Constants.PAGE_CONTACT_TYPES%>' 
				                    property="code"
				                    labelProperty="description"/>
               </html:select>
           </td>
           <td align="left">
				<html:submit styleClass="form_button" property="reload">
					<bean:message key="all.prompt.reload"/>
				</html:submit>						
		   </td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.organizationName"/></td>
	 		<td colspan="2"><html:text property="organizationName" size="40" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.firstName"/></td>
	 		<td colspan="2"><html:text property="firstName" size="20" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.lastName"/></td>
	 		<td colspan="2"><html:text property="lastName" size="20" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.phoneNumber"/></td>
	 		<td colspan="2"><html:text property="phoneAreaCode" size="3" /> - <html:text property="phoneNumber" size="10" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.email"/></td>
	 		<td colspan="2"><html:text property="email" size="40" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.address1"/></td>
	 		<td colspan="2"><html:text property="address1" size="40" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.address2"/></td>
	 		<td colspan="2"><html:text property="address2" size="40" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.city"/></td>
	 		<td colspan="2"><html:text property="city" size="20" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.stateProvince"/></td>
	 		<td colspan="2"><html:text property="stateProvince" size="10" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.postalCode"/></td>
	 		<td colspan="2"><html:text property="postalCode" size="10" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.countryCode"/></td>
	 		<td colspan="2">		
	 			<html:select property="countryCode">
			          <html:options collection='<%=Constants.PAGE_COUNTRIES%>' 
				                    property="code"
				                    labelProperty="description"/>
               </html:select>
           </td>
	 	</tr>
	 	
	 	<%-- Now the entity specific fields --%>
	 	<logic:iterate id="field" name="contact" property="fieldsTable">
 		<bean:define id="typeId" name="field" property="key"/>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message name="field" property="value.type.promptKey"/></td>
	 		<td colspan="2">
			 	<%-- If it is not a customer, it can edit any fields --%>
	 			<logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
					 property="mainRoleId"
					 scope="session"
					 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
	 			<html:text property='<%= "fieldsTable(" + typeId + ").content" %>' size="20" />
	 			</logic:notEqual>

	 			<logic:equal name='<%=Constants.SESSION_USER_DTO%>'
					 property="mainRoleId"
					 scope="session"
					 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
	 			<logic:notEqual name="contact"
					 property='<%= "fieldsTable(" + typeId + ").type.readOnly" %>'
					 scope="session"
					 value="1">
	 				<html:text property='<%= "fieldsTable(" + typeId + ").content" %>' size="20" />
	 			</logic:notEqual>
	 			<logic:equal name="contact"
					 property='<%= "fieldsTable(" + typeId + ").type.readOnly" %>'
					 scope="session"
					 value="1">
	 				<bean:write name="contact" property='<%= "fieldsTable(" + typeId + ").content" %>'/>
	 			</logic:equal>
	 			</logic:equal>

	 		</td>
	 	</tr>
	 	</logic:iterate>

	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="contact.prompt.include"/></td>
	 		<td colspan="2"><html:checkbox property="chbx_include"/></td>
	 	</tr>
	 	
	 	<tr class="form">
	 		<td colspan="3" class="form_button">
              	<html:submit styleClass="form_button">
              		<bean:message key="all.prompt.submit"/>
              	</html:submit>
            </td>
		</tr>	 
	</table>
</html:form>