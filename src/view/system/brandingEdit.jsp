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

<p class="title"><bean:message key="system.branding.title"/></p>
<p class="instr">
   <bean:message key="all.prompt.help" />
   <jbilling:help page="system" anchor="branding">
		 <bean:message key="all.prompt.here" />
   </jbilling:help>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<html:form action="/brandingMaintain?action=edit&mode=branding" >
  <table class="form">
	  <tr class="form">
		  <td>
			 <jbilling:help page="system" anchor="branding.logo">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		  </td>
		  <td class="form_prompt"><bean:message key="system.branding.prompt.logo"/></td>
		  <td><html:text property="logo" /></td>
	  </tr>
	  <tr class="form">
		  <td>
			 <jbilling:help page="system" anchor="branding.css">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		  </td>
		  <td class="form_prompt"><bean:message key="system.branding.prompt.css"/></td>
		  <td><html:text property="css" /></td>
	  </tr>
	<tr class="form">
		<td></td>
		<td colspan="2" class="form_button">
			<html:submit styleClass="form_button">
                <bean:message key="all.prompt.submit"/>                
            </html:submit>
        </td>
	</tr>

  </table>
</html:form>
