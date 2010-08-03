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

<%-- Warning: the user status id have been hardcoded!
     Active = 1  Deleted = 8. This should be changed to a constant --%>

<p class="title"><bean:message key="system.ageing.title"/></p>
<p class="instr">
   <bean:message key="system.ageing.instr"/>
   <bean:message key="all.prompt.help" />
   <jbilling:help page="system" anchor="ageing">
		 <bean:message key="all.prompt.here" />
   </jbilling:help>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<html:form action="/ageingMaintain?action=edit" >
  <table class="form">
  	<tr class="form">
  		<td class="form_prompt"><bean:message key="system.ageing.gracePeriod"/></td>
		<td>
		   <html:text property="gracePeriod" size="2"/>
 	    </td>
  		
	</tr>
  	<tr class="form">
  		<td class="form_prompt"><bean:message key="system.ageing.urlCallback"/></td>
		<td>
		   <html:textarea property="urlCallback" cols="35" rows="3"/>
 	    </td>
  		
	</tr>
	<tr class="form">
		<td align="right">
            <jbilling:getOptions language="true"/>
			<html:select property="languageId">
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
	</tr>
  </table>

  <table class="form">
  	<tr class="form">
  		<td class="form_prompt"><bean:message key="system.ageing.step"/></td>
  		<td class="form_prompt"><bean:message key="system.ageing.status"/></td>
  		<td class="form_prompt"><bean:message key="system.ageing.canLogin"/></td>
  		<td class="form_prompt"><bean:message key="system.ageing.inUse"/></td>
  		<td class="form_prompt"><bean:message key="system.ageing.days"/></td>
  		<td class="form_prompt"><bean:message key="system.ageing.welcomeMessage"/></td>
  		<td class="form_prompt"><bean:message key="system.ageing.loginMessage"/></td>
  	</tr>
  	
  	<logic:iterate id="line" name="ageing" property="lines" indexId="index">
  	  <bean:define id="showDays" value="yes"/>
		<logic:equal name="ageing" 
		  		        property='<%= "lines[" + index+ "].statusId" %>'
		  		        value="1">  	  
		    <bean:define id="showDays" value="no"/>
		</logic:equal>
		<logic:equal name="ageing" 
		  		        property='<%= "lines[" + index+ "].statusId" %>'
		  		        value="8">  	  
		    <bean:define id="showDays" value="no"/>
		</logic:equal>
	  <tr class="form">
		  <td><bean:write name="ageing" property='<%= "lines[" + index+ "].statusId" %>' /></td>
		  <td><bean:write name="ageing" property='<%= "lines[" + index+ "].statusStr" %>' /></td>
		  <td>
		  	<logic:equal name="ageing" 
		  		            property='<%= "lines[" + index+ "].canLogin" %>'
		  		            value="1">
				<bean:message key="all.prompt.yes"/>
			</logic:equal>
		  	<logic:equal name="ageing" 
		  		            property='<%= "lines[" + index+ "].canLogin" %>'
		  		            value="0">
				<bean:message key="all.prompt.no"/>
			</logic:equal>
		  </td>
		  <td>
		  	<logic:notEqual name="ageing" 
		  		            property='<%= "lines[" + index+ "].statusId" %>'
		  		            value="1">
		  		<html:checkbox property='<%= "lines[" + index+ "].inUse" %>' />
		  	</logic:notEqual>
		  	<logic:equal name="ageing" 
		  		            property='<%= "lines[" + index+ "].statusId" %>'
		  		            value="1">
				<bean:message key="all.prompt.yes"/>
			</logic:equal>
		  </td>
		  <td>
		  	<logic:equal name="showDays" value="yes">
		   	   <html:text property='<%= "lines[" + index+ "].days" %>' size="3"/>
		  	</logic:equal>
		  	<logic:notEqual name="showDays" value="yes">
		   	   <html:hidden property='<%= "lines[" + index+ "].days" %>' value="0"/>
		  	</logic:notEqual>
		  </td>
		  <td>
			<logic:notEqual name="ageing" 
			  property='<%= "lines[" + index+ "].statusId" %>'
			  value="8">
		  		<html:text property='<%= "lines[" + index+ "].welcomeMessage" %>' />
		    </logic:notEqual>
		  </td>
		  <td><html:text property='<%= "lines[" + index+ "].failedLoginMessage" %>' /></td>
	  </tr>
    </logic:iterate>
    
	<tr class="form">
		<td colspan="7" class="form_button">
			<html:submit styleClass="form_button">
                <bean:message key="all.prompt.submit"/>                
            </html:submit>
        </td>
	</tr>

  </table>
</html:form>
