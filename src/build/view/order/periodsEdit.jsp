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

<p class="title"><bean:message key="order.periods.title"/></p>
<p class="instr">
   <bean:message key="order.periods.instr"/>
   <bean:message key="all.prompt.help" />
   <jbilling:help page="orders" anchor="periods">
		 <bean:message key="all.prompt.here" />
   </jbilling:help>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<jbilling:getOptions generalPeriod="true"/>
<html:form action="/orderPeriod?action=edit" >
  <table class="form">
  	<tr class="form">
  		<td class="form_prompt"><bean:message key="order.period.prompt.id"/></td>
  		<td class="form_prompt"><bean:message key="order.period.prompt.value"/></td>
  		<td class="form_prompt"><bean:message key="order.period.prompt.unit"/></td>
  		<td class="form_prompt"><bean:message key="order.period.prompt.description"/></td>
  		<td></td>
  	</tr>
  	
  	<logic:iterate id="anId" name="orderPeriod" property="id"
			           scope="session" indexId="index">
	   <tr class="form">
	    	<td>
	    		<bean:write name="orderPeriod" property='<%= "id[" + index + "]" %>'/>
	    	</td>
	    	<td>
	    		<html:text property='<%= "value[" + index + "]" %>' size="2"/>
	    	</td>
	    	<td>
	    		<html:select property='<%= "unit[" + index + "]" %>'>
			   		<html:options collection='<%=Constants.PAGE_GENERAL_PERIODS%>' 
				 		property="code"
						labelProperty="description"	/>
		  		</html:select>
	    	</td>
	    	<td>
	    		<html:text property='<%= "description[" + index + "]" %>' size="10"/>
	    	</td>
	    	<td>
	    		<html:link page="/orderPeriod.do?action=delete" paramName="orderPeriod"
	    			paramProperty='<%= "id[" + index + "]" %>' paramId="id">
					<bean:message key="order.period.delete"/>
				</html:link>
	    	</td>
    	</tr>
    </logic:iterate>

	<tr><td colspan="5" align="center">
		<html:link page="/orderPeriod.do?action=add">
			<bean:message key="order.period.add"/>
		</html:link>
	</td></tr>
    
	<tr><td colspan="5"  class="form_button">
		<html:submit styleClass="form_button">
			<bean:message key="all.prompt.submit"/>
		</html:submit>
	</td></tr>
  </table>
</html:form>