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

<p class="title"><bean:message key="task.parameter.title"/></p>
<p class="instr">
	<bean:message key="task.parameter.instr"/>
	<logic:equal parameter="type" value="notification">
	   <br/>
	   <bean:message key="all.prompt.help" />
	   <jbilling:help page="notifications" anchor="emailParameters">
		   <bean:message key="all.prompt.here" />
	   </jbilling:help>
	</logic:equal>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>


<html:form action="/parameterMaintain?action=edit&mode=parameter">
	<table class="form">
		<logic:iterate id="aValue" name="parameter" property="value"
			           scope="session" indexId="index">
	    	<tr class="form">
	    		<td class="form_prompt"><bean:write name="parameter" 
	    					  property='<%= "name[" + index + "]" %>'/></td>
	    		<td><html:text 
	    				property='<%= "value[" + index + "]" %>'/></td>
	    		
	    	</tr>
	    </logic:iterate>
		<tr><td colspan="2"  class="form_button">
			<html:submit styleClass="form_button">
				<bean:message key="all.prompt.submit"/>
			</html:submit>
		</td></tr>
	</table>
</html:form>