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
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>

<logic:notPresent name='<%=Constants.SESSION_CUSTOMER_DTO%>' 
		property="customer.parent">
<table class="info">
	<tr>
		<th class="info" colspan="2"><bean:message key="creditcard.info.title"/></th>
	</tr>

<logic:present name='<%=Constants.SESSION_CUSTOMER_DTO%>' property="creditCard" scope="session">
   <tr class="infoA">
	   <td class="infoprompt"><bean:message key="payment.cc.number"/></td>
	   <td class="infodata">
	   
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_CC%>'>
	 	     <jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_HIDE_CC_NUMBERS%>'
			                     beanName="hide_cc"/>
		     <logic:equal name="hide_cc" value="0">
            		<bean:write name='<%=Constants.SESSION_CUSTOMER_DTO%>' property="creditCard.number" />
             </logic:equal>
		     <logic:equal name="hide_cc" value="1">
            		     <c:set var="ccNumber" value="${customer_dto.creditCard.number}"/>
                                  ************<%=((String)(pageContext.getAttribute("ccNumber"))).substring(
                                        ((String)(pageContext.getAttribute("ccNumber"))).length() - 4) %>
             </logic:equal>
        </jbilling:permission>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_CC%>'
                            negative="true">
                       ****************
        </jbilling:permission>
       </td>
   </tr>
   <tr class="infoB">
	   <td class="infoprompt"><bean:message key="payment.cc.name"/></td>
	   <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_DTO%>' property="creditCard.name" /></td>
   </tr>
   <tr class="infoA">
 	   <td class="infoprompt"><bean:message key="payment.cc.date"/></td>
	   <td class="infodata"><bean:write name='<%=Constants.SESSION_CUSTOMER_DTO%>' 
	   		           property="creditCard.ccExpiry"
	   		           formatKey="format.date"/>
	   </td>
   </tr>
   <jbilling:permission permission='<%=Constants.P_USER_EDIT_LINKS%>'>
   <tr>
	  <td class="infocommands" colspan="2">
		  <html:link action="/creditCardMaintain.do?action=setup&mode=creditCard"
		  	         paramId="userId"
				     paramName='<%=Constants.SESSION_CUSTOMER_DTO%>'
				     paramProperty="userId">
			  <bean:message key="creditcard.edit.link"/>
	      </html:link>
	  </td>
   </tr>
   </jbilling:permission>
   
</logic:present>

<logic:notPresent name='<%=Constants.SESSION_CUSTOMER_DTO%>' property="creditCard" scope="session">
	<tr class="infoA">
		<td class="infoprompt" colspan="2"><bean:message key="creditcard.notPresent"/></td>
	</tr>
</logic:notPresent>

</table>
</logic:notPresent>
