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

<logic:present name="authorizationDto" scope="request">
<table class="info">
	<tr>
		<th class="info" colspan="2"><bean:message key="authorization.info.title"/></th>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="authorization.processor"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="processor"
                        scope="request"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="authorization.code1"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="code1"
                        scope="request"/>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="authorization.code2"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="code2"
                        scope="request"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="authorization.code3"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="code3"
                        scope="request"/>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="authorization.approvalCode"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="approvalCode"
                        scope="request"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="authorization.transactionId"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="transactionId"
                        scope="request"/>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="authorization.mD5"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="MD5"
                        scope="request"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="authorization.cardCode"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="cardCode"
                        scope="request"/>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="authorization.createDate"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="createDate"
                        scope="request"
                        formatKey="format.timestamp"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="authorization.responseMessage"/></td>
		<td class="infodata">	
            <bean:write name="authorizationDto" 
                        property="responseMessage"
                        scope="request"/>
        </td>
	</tr>
	
</table>	
</logic:present>
