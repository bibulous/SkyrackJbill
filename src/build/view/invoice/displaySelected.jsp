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

<logic:present name='<%=Constants.SESSION_INVOICE_DTO%>'
	           scope="session">
	 <table class="info">
	 <tr><th class="info" colspan="2"><bean:message key="invoice.selected"/></th></tr>
	 <tr class="infoA">
	 		<td class="infoprompt"><bean:message key="invoice.id.prompt"/></td>
	 	    <td class="infodata">
	 	    	<html:link page="/invoiceMaintain.do?action=view" paramId="id" 
						   paramName='<%=Constants.SESSION_INVOICE_DTO%>'
						   paramProperty="id">
					 <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>'
								  property="id"/>
				</html:link>
	  	    </td>
	 </tr>
	 <tr class="infoB">
	 		<td class="infoprompt"><bean:message key="invoice.number.prompt"/></td>
	 	    <td class="infodata">
	 	    	<bean:write name='<%=Constants.SESSION_INVOICE_DTO%>'
	  	                    property="number"/>
	  	    </td>
	 </tr>
	 <tr class="infoA">
	 		<td class="infoprompt"><bean:message key="invoice.total"/></td>
	 	    <td class="infodata">
	 	    	<bean:define id="index" name='<%=Constants.SESSION_INVOICE_DTO%>'
	 	    		 property="currency.id"/>
	 	    	<bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
	 	    		  property='<%= "symbols[" + index + "].symbol" %>'
	 	    		  scope="application"
	 	    		  filter="false"/>
	 	    	<bean:write name='<%=Constants.SESSION_INVOICE_DTO%>'
	  	                    property="total" formatKey="format.money"/>
	  	    </td>
	 </tr>
	 </table>
</logic:present>
<%-- let the user know if an invoice has been selected or not--%>
<logic:notPresent name='<%=Constants.SESSION_INVOICE_DTO%>'
	                         scope="session">
	 <table class="info">
	 <tr><th class="info"><bean:message key="invoice.selected"/></th></tr>
	 <tr class="infoA">
         <td class="infoprompt"><bean:message key="invoice.notSelected"/></td>
	 </tr>
	 </table>
</logic:notPresent>
