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


 <table class="info">
 	 <tr>
 	 	<th class="info" colspan="2">
 	 		<bean:message key="contact.info.title"/>
 	 	</th>
 	 </tr>
	 <tr class="infoA">
		 <td class="infoprompt"><bean:message key="contact.prompt.organizationName"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="organizationName"  /></td>
	 </tr>
	 <tr class="infoB">
		 <td class="infoprompt"><bean:message key="contact.prompt.firstName"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="firstName"  /></td>
	 </tr>
	 <tr class="infoA">
		 <td class="infoprompt"><bean:message key="contact.prompt.lastName"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="lastName"  /></td>
	 </tr>
	 <tr class="infoB">
		 <td class="infoprompt"><bean:message key="contact.prompt.phoneNumber"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="phoneAreaCode" /> - <bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="phoneNumber"  /></td>
	 </tr>
	 <tr class="infoA">
		 <td class="infoprompt"><bean:message key="contact.prompt.email"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="email"  /></td>
	 </tr>
	 <tr class="infoB">
		 <td class="infoprompt"><bean:message key="contact.prompt.address1"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="address1"  /></td>
	 </tr>
	 <tr class="infoA">
		 <td class="infoprompt"><bean:message key="contact.prompt.address2"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="address2"  /></td>
	 </tr>
	 <tr class="infoB">
		 <td class="infoprompt"><bean:message key="contact.prompt.city"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="city"  /></td>
	 </tr>
	 <tr class="infoA">
		 <td class="infoprompt"><bean:message key="contact.prompt.stateProvince"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="stateProvince"  /></td>
	 </tr>
	 <tr class="infoB">
		 <td class="infoprompt"><bean:message key="contact.prompt.postalCode"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="postalCode"  /></td>
	 </tr>
	 <tr class="infoA">
		 <td class="infoprompt"><bean:message key="contact.prompt.countryCode"/></td>
		 <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="countryCode"  /></td>
	 </tr>
	 
	<%-- Now the entity specific custom contact fields (CCF) --%>
 	<logic:iterate id="field" name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property="fieldsTable">
 		<bean:define id="typeId" name="field" property="key"/>
		<c:choose>
			<c:when test="${colorFlag == 1}">
				<tr class="infoA">
				<c:remove var="colorFlag"/>
			</c:when>
			<c:otherwise>
				<tr class="infoB">
				<c:set var="colorFlag" value="1"/>
			</c:otherwise>
	    </c:choose>
	 		<td class="infoprompt"><bean:message name="field" property="value.type.promptKey"/></td>
	 		<td class="infodata"> 
	 			<bean:write name='<%=Constants.SESSION_CUSTOMER_CONTACT_DTO%>' property='<%= "fieldsTable(" + typeId + ").content" %>'/>
	 		</td>
	 	</tr>
 	</logic:iterate>
	 
	 <jbilling:permission permission='<%=Constants.P_USER_EDIT_LINKS%>'>
	 <tr>
		<td class="infocommands" colspan="2">
			<html:link action="/contactEdit.do?action=setup"
				       paramId="userId"
				       paramName='<%=Constants.SESSION_CUSTOMER_DTO%>'
				       paramProperty="userId">
				<bean:message key="contact.edit.link"/>
			</html:link>
		</td>
	</tr>
	</jbilling:permission>
	 
</table>
