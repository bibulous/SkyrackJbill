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

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<p class="title"><bean:message key="notification.compose.title"/></p>
<p class="instr"><bean:message key="notification.compose.instr"/></p>

<jbilling:getOptions language="true"/>
<html:form action="/notificationMaintain?action=edit&mode=notification">
	<table border="0">
		<tr>
			<td colspan="2">
				<table><tr class="form">
			<td  class="form_prompt"><bean:message key="notification.prompt.use"/></td>
			<td><html:checkbox property="chbx_use_flag"/></td>
			</tr></table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table><tr>
					<td align="right">
						<html:select property="language">
						 	<html:options collection='<%=Constants.PAGE_LANGUAGES%>' 
				                    property="code"
				                    labelProperty="description"/>
					    </html:select>
					</td>
					<td align="left">
						<html:submit styleClass="form_button" property="reload">
							<bean:message key="all.prompt.reload"/>
						</html:submit>						
					</td>
				</tr></table>
			</td>
		</tr>
		<logic:iterate id="line" name="message" property="sections"
			           scope="session" indexId="index">
	    	<tr>
	    		<td><bean:write name="message" 
	    					  property='<%= "sectionNumbers[" + index + "]" %>'/></td>
	    		<td><html:textarea rows="15" cols="50"
	    				property='<%= "sections[" + index + "]" %>'/></td>
	    		
	    	</tr>
	    </logic:iterate>
		<tr><td colspan="2" align="center">
			<html:submit styleClass="form_button">
				<bean:message key="all.prompt.submit"/>
			</html:submit>
		</td></tr>
	</table>
</html:form>