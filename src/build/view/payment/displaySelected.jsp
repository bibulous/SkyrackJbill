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

<%@ page language="java"  import="com.sapienter.jbilling.client.util.Constants"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>

<table class="info">
<tr><th class="info" colspan="2"><bean:message key="payment.selected"/></th></tr>

<logic:present name='<%=Constants.SESSION_PAYMENT_DTO%>'
	           scope="session">
	 <tr class="infoA">
	 		<td class="infoprompt"><bean:message key="payment.id"/></td>
	 	    <td class="infodata">
	 	    	<bean:write name='<%=Constants.SESSION_PAYMENT_DTO%>'
	  	                              property="id"/>
	  	    </td>
	 </tr>
	 <tr class="infoB">
	 		<td class="infoprompt"><bean:message key="payment.amount"/></td>
	 	    <td class="infodata align="right">
			    <bean:define id="index" name='<%=Constants.SESSION_PAYMENT_DTO%>'
				      property="currency.id"/>
			    <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				      property='<%= "symbols[" + index + "].symbol" %>'
				      scope="application"
				      filter="false"/>
	 	    	
	 	    	<bean:write name='<%=Constants.SESSION_PAYMENT_DTO%>'
	  	                    property="amount" formatKey="format.money"/>
	  	    </td>
	 </tr>
	 <tr class="infoA">
	 		<td class="infoprompt"><bean:message key="payment.date"/></td>
	 	    <td class="infodata align="right">
	 	    	<bean:write name='<%=Constants.SESSION_PAYMENT_DTO%>'
	  	                    property="createDatetime" formatKey="format.timestamp"/>
	  	     </td>
	 </tr>
</logic:present>

<%-- let the user know if a payment has been selected or not--%>
<logic:notPresent name='<%=Constants.SESSION_PAYMENT_DTO%>'
	                         scope="session">
	 <tr class="infoA">
        <td class="infoprompt"><bean:message key="payment.notSelected"/></td>
	 </tr>
</logic:notPresent>

</table>
