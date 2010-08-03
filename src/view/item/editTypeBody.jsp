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

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<jbilling:getOptions orderLineType="true"/>

<logic:present parameter="create">
    <%-- removal of the form bean from the session is necessary to
         avoid old data to show up later in creation/edition --%>
    <% session.removeAttribute("item"); %>
</logic:present>

<html:form action="/itemTypeMaintain?action=edit&mode=type">
      <logic:present parameter="create">                
         <html:hidden property="create" value="yes"/>
      </logic:present>

	  <table class="form">
	    <tr class="form">
	      <td class="form_prompt"><bean:message key="item.type.prompt.description"/></td>
	      <td><html:text property="name" /></td>
    	</tr>

	    <tr class="form">
	      <td class="form_prompt"><bean:message key="order.line.prompt.type"/></td>
	      <td>
	      	<html:select property="order_line_type">
		          <html:options collection='<%=Constants.PAGE_ORDER_LINE_TYPES%>' 
				            property="code"
				            labelProperty="description"	/>
		    </html:select>
	      </td>
    	</tr>

	    <tr class="form">
    	  <td colspan="2" class="form_button">
              <html:submit styleClass="form_button">
                <logic:present parameter="create">                
                    <bean:message key="item.type.prompt.create"/>
                </logic:present>
                <logic:notPresent parameter="create">
                    <bean:message key="all.prompt.submit"/>                
                </logic:notPresent>
              </html:submit>
          </td>
    	</tr>
	  </table>
</html:form>
