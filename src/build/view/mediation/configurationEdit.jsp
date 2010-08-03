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

<p class="title"><bean:message key="mediation.configuration.title"/></p>
<p class="instr">
   <bean:message key="mediation.configuration.instr"/>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<html:form action="/mediation/configuration.do?action=edit" >
  <table class="form">
  	<tr class="form">
  		<td class="form_prompt"><bean:message key="mediation.configuration.prompt.id"/></td>
  		<td class="form_prompt"><bean:message key="mediation.configuration.prompt.date"/></td>
  		<td class="form_prompt"><bean:message key="mediation.configuration.prompt.name"/></td>
  		<td class="form_prompt"><bean:message key="mediation.configuration.prompt.order"/></td>
  		<td class="form_prompt"><bean:message key="mediation.configuration.prompt.task"/></td>
  		<td></td>
  	</tr>
  	
  	<logic:iterate id="oneConfig" name="medConfiguration" property="configurations"
			           scope="session" indexId="index">
	   <tr class="form">
	    	<td>
	    		<bean:write name="oneConfig" property="id" />
	    	</td>
	    	<td>
			    <bean:write name="oneConfig"
                        property="createDatetime"
                        formatKey="format.timestamp"/>
	    	</td>
	    	<td>
	    		<html:text property='<%= "configurations[" + index + "].name" %>' size="20"/>
	    	</td>
	    	<td>
	    		<html:text property='<%= "configurations[" + index + "].orderValue" %>' size="2"/>
	    	</td>
	    	<td>
	    		<html:text property='<%= "configurations[" + index + "].pluggableTask.id" %>' size="3"/>
	    	</td>
	    	<td>
		    	<html:link page="/mediation/configuration.do?action=delete" paramName="oneConfig"
	    			paramProperty="id" paramId="id">
					<bean:message key="all.prompt.delete"/>
				</html:link>
	    	</td>
    	</tr>
		
		
    </logic:iterate>

	<tr><td colspan="6" align="center">
		<html:link page="/mediation/configuration.do?action=add">
			<bean:message key="mediation.configuration.add"/>
		</html:link>
	</td></tr>
	<tr>
   	<td class="form_button" colspan="6" align="center">
		<html:submit styleClass="form_button">
			<bean:message key="all.prompt.submit"/>
		</html:submit>
	</td>
	</tr>
    
  </table>
</html:form>
  