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

<p class="title"><bean:message key="system.task.title"/></p>
<p class="instr">
   <bean:message key="system.task.instr"/>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<jbilling:getOptions taskClasses="true"/>
<html:form action="/task?action=edit" >
  <table class="form">
  	<tr class="form">
  		<td class="form_prompt"><bean:message key="system.task.prompt.id"/></td>
  		<td class="form_prompt"><bean:message key="system.task.prompt.type"/></td>
  		<td class="form_prompt"><bean:message key="system.task.prompt.processing_order"/></td>
  		<td></td>
  	</tr>
  	
  	<logic:iterate id="oneTask" name="task" property="tasks"
			           scope="session" indexId="index">
	   <tr class="form">
	    	<td>
	    		<bean:write name="oneTask" property="id" />
	    	</td>
	    	<td>
	    		<html:select property='<%= "tasks[" + index + "].type.pk" %>'>
			   		<html:options collection='<%=Constants.PAGE_TASK_CLASSES%>' 
				 		property="code"
						labelProperty="description"	/>
		  		</html:select>
	    	</td>
	    	<td>
	    		<html:text property='<%= "tasks[" + index + "].processingOrder" %>' size="2"/>
	    	</td>
	    	<td>
		    	<html:link page="/task.do?action=delete" paramName="oneTask"
	    			paramProperty="id" paramId="id">
					<bean:message key="all.prompt.delete"/>
				</html:link>
	    	</td>
    	</tr>
		<logic:notEmpty name="oneTask" property="parameters">
		<tr><td colspan="4" align="center">
		
        <table class="form">				           
	  	<logic:iterate id="oneParameter" name="oneTask" property="parameters"
	  				indexId="subIndex">
	      <tr class="form">
	    	<td>
	    		<html:text property='<%= "tasks[" + index + "].parameters[" + subIndex + "].name" %>'/>
	    	</td>
	    	<td>
	    		<html:text property='<%= "tasks[" + index + "].parameters[" + subIndex + "].value" %>'/>
	    	</td>
			<td>
		    	<html:link page="/task.do?action=deleteParameter" paramName="oneParameter"
	    			paramProperty="id" paramId="id">
					<bean:message key="all.prompt.delete"/>
				</html:link>
	    	</td>	    	
     	  </tr>
		</logic:iterate>
   	    </table>
		</td></tr>
		</logic:notEmpty>
		
		<tr><td colspan="4" align="center">
		    	<html:link page="/task.do?action=addParameter" paramName="oneTask"
	    			paramProperty="id" paramId="id">
					<bean:message key="system.task.addParameter"/>
				</html:link>
    	</td></tr>
		
		
    </logic:iterate>

	<tr><td colspan="4" align="center">
		<html:link page="/task.do?action=add">
			<bean:message key="system.task.add"/>
		</html:link>
	</td></tr>
	<tr>
   	<td class="form_button" colspan="4" align="center">
		<html:submit styleClass="form_button">
			<bean:message key="all.prompt.submit"/>
		</html:submit>
	</td>
	</tr>
    
  </table>
</html:form>
  