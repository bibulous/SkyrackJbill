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

<p class="title"><bean:message key="system.currency.title"/></p>
<p class="instr">
   <bean:message key="all.prompt.help" />
   <jbilling:help page="system" anchor="currencies">
		 <bean:message key="all.prompt.here" />
   </jbilling:help>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<html:form action="/currencyMaintain?action=edit" >
  <table class="form">
  	<tr class="form">
  		<jbilling:getOptions currencies="true"/>
  		<td class="form_prompt"><bean:message key="system.currency.default"/></td>
		<td colspan="4">
		   <html:select property="defaultCurrencyId">
			   <html:options collection='<%=Constants.PAGE_CURRENCIES%>' 
				 property="code"
				 labelProperty="description"	/>
		  </html:select>
 	    </td>
  		
	</tr>
  	<tr class="form">
  		<td class="form_prompt"><bean:message key="system.currency.prompt.name"/></td>
  		<td class="form_prompt"><bean:message key="system.currency.prompt.symbol"/></td>
  		<td class="form_prompt"><bean:message key="system.currency.prompt.inUse"/></td>
  		<td class="form_prompt"><bean:message key="system.currency.prompt.rate"/></td>
  		<td class="form_prompt"><bean:message key="system.currency.prompt.sysRate"/></td>
  	</tr>
  	
  	<logic:iterate id="line" name="currency" property="lines" indexId="index">
	  <tr class="form">
		  <td><bean:write name="currency" property='<%= "lines[" + index+ "].name" %>' /></td>
		  <td align="center">
 			 <bean:define id="cId" name="currency"
			       property='<%= "lines[" + index+ "].id" %>'/>
			<bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				      property='<%= "symbols[" + cId + "].symbol" %>'
				      scope="application"
				      filter="false"/>
		  </td>
		  <td><html:checkbox property='<%= "lines[" + index+ "].inUse" %>' /></td>
		  <td>
		  	<logic:notEqual name="currency" 
		  		            property='<%= "lines[" + index+ "].id" %>'
		  		            value="1">
 		  	    <html:text property='<%= "lines[" + index+ "].rate" %>' />
 		  	</logic:notEqual>
		  </td>
		  <td align="center">
		  	 <bean:write name="currency" property='<%= "lines[" + index+ "].sysRate" %>' formatKey="format.money"/>
		  </td>
	  </tr>
    </logic:iterate>
    
	<tr class="form">
		<td colspan="5" class="form_button">
			<html:submit styleClass="form_button">
                <bean:message key="all.prompt.submit"/>                
            </html:submit>
        </td>
	</tr>

  </table>
</html:form>
