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

<logic:present parameter="create">
    <%-- removal of the form bean from the session is necessary to
         avoid old data to show up later in creation/edition --%>
    <% session.removeAttribute("promotion"); %>
</logic:present>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p> 
</html:messages>

<html:form action="/promotionMaintain?action=edit&mode=promotion">
      <logic:present parameter="create">                
               <html:hidden property="create" value="yes"/>
      </logic:present>
	
	  <table class="form">
	    <tr class="form">
	      <td class="form_prompt"><bean:message key="promotion.prompt.code"/></td>
	      <td colspan="4">
	         <jbilling:permission permission='<%=Constants.P_ITEM_EDIT%>'>
				 <html:text property="code" />
	         </jbilling:permission>
		     <jbilling:permission permission='<%=Constants.P_ITEM_EDIT%>' negative="true">
		         <bean:write name="promotion" property="code" />
	         </jbilling:permission>	      
	      </td>	      
    	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="promotion.prompt.since"/></td>
	 		<jbilling:dateFormat format="mm-dd">
				 <td><html:text property="since_month" size="2" maxlength="2"/></td>
				 <td><html:text property="since_day" size="2" maxlength="2"/></td>
			</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
				 <td><html:text property="since_day" size="2" maxlength="2"/></td>
				 <td><html:text property="since_month" size="2" maxlength="2"/></td>
			</jbilling:dateFormat>
	 		<td><html:text property="since_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 	</tr>
 	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="promotion.prompt.until"/></td>
	 		<jbilling:dateFormat format="mm-dd">
				 <td><html:text property="until_month" size="2" maxlength="2"/></td>
				 <td><html:text property="until_day" size="2" maxlength="2"/></td>
			</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
				 <td><html:text property="until_day" size="2" maxlength="2"/></td>
				 <td><html:text property="until_month" size="2" maxlength="2"/></td>
			</jbilling:dateFormat>
				 
	 		<td><html:text property="until_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 	</tr>
    	
	    <tr class="form">
	      <td class="form_prompt"><bean:message key="promotion.prompt.once"/></td>
	      <td colspan="4">
	         <jbilling:permission permission='<%=Constants.P_ITEM_EDIT%>'>
				 <html:checkbox property="chbx_once" />
	         </jbilling:permission>
		     <jbilling:permission permission='<%=Constants.P_ITEM_EDIT%>' negative="true">
		         <bean:write name="promotion" property="chbx_once" />
	         </jbilling:permission>	      
	      </td>	      
    	</tr>
	    <tr class="form">
	      <td class="form_prompt"><bean:message key="promotion.prompt.notes"/></td>
	       <td colspan="4">
	           <jbilling:permission permission='<%=Constants.P_ITEM_EDIT%>'>	      
                    <html:text property="notes" />
	           </jbilling:permission>	      
	           <jbilling:permission permission='<%=Constants.P_ITEM_EDIT%>' negative="true">	      
	                 <bean:write name="promotion" property="notes" />
	           </jbilling:permission>	      
	       </td>
    	</tr>
    	
      <jbilling:permission permission='<%=Constants.P_ITEM_EDIT%>'>
	    <tr class="form">
    	  <td colspan="6" class="form_button">
            <logic:present parameter="create">
                <html:submit styleClass="form_button" property="create">
                    <bean:message key="promotion.prompt.create"/>
                </html:submit>
            </logic:present>
           <logic:notPresent parameter="create">
                <html:submit styleClass="form_button">
                    <bean:message key="all.prompt.submit"/>                
                </html:submit>
            </logic:notPresent>
         </td>
    	</tr>
    	 <logic:notPresent parameter="create">
    	 <tr class="form">
    	 	<td colspan="6" align="center">
    	 		<html:link forward='<%=Constants.FORWARD_ITEM_EDIT%>'>
    	 			<bean:message key="promotion.edit.item"/>
    	 		</html:link>
    	 	</td>
    	 </tr>
    	 </logic:notPresent>
    	
      </jbilling:permission>    	
	  </table>
</html:form>
