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

<logic:present parameter="result">
	<p class="title"><bean:message key="payout.manual.result"/></p>
	
	<html:errors/>
	<html:messages message="true" id="myMessage">
		<p><bean:write name="myMessage"/></p>
	</html:messages>
</logic:present>

<table class="info" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th class="info" colspan="2"><bean:message key="payout.info.title"/></th>
	</tr>
	
	<logic:present name='<%=Constants.SESSION_PAYOUT_DTO%>' scope="session">
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="payout.prompt.id"/></td>
		<td class="infodata"><bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			property="id" scope="session"/></td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="payout.prompt.startDate"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			property="startingDate" scope="session" formatKey="format.date"/></td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="payout.prompt.endDate"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			property="endingDate" scope="session" formatKey="format.date"/></td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="payout.prompt.payments"/></td>
		<td  class="infodata">
			 <bean:define id="index" name='<%=Constants.SESSION_PAYOUT_DTO%>'
				  property="payment.currency.id"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>
			<bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			       property="paymentsAmount" scope="session" formatKey="format.money"/>
	    </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="payout.prompt.refunds"/></td>
		<td  class="infodata">
			 <bean:define id="index" name='<%=Constants.SESSION_PAYOUT_DTO%>'
				  property="payment.currency.id"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>
			<bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			       property="refundsAmount" scope="session" formatKey="format.money"/>
	    </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="payout.prompt.total"/></td>
		<td  class="infodata">
			 <bean:define id="index" name='<%=Constants.SESSION_PAYOUT_DTO%>'
				  property="payment.currency.id"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>
			<bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			       property="total" scope="session" formatKey="format.money"/>
	    </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="payout.prompt.balance"/></td>
		<td  class="infodata">
			 <bean:define id="index" name='<%=Constants.SESSION_PAYOUT_DTO%>'
				  property="payment.currency.id"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>
			<bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			       property="balanceLeft" scope="session" formatKey="format.money"/>
	    </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="payout.prompt.paid"/></td>
		<td  class="infodata">
			 <bean:define id="index" name='<%=Constants.SESSION_PAYOUT_DTO%>'
				  property="payment.currency.id"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>
			<bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			       property="payment.amount" scope="session" formatKey="format.money"/>
	    </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="payout.prompt.date"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PAYOUT_DTO%>'  
			property="payment.createDatetime" scope="session" formatKey="format.timestamp"/></td>
	</tr>
	</logic:present>
	<logic:notPresent name='<%=Constants.SESSION_PAYOUT_DTO%>' scope="session">
		<tr class="infoA">
		<td class="infoprompt" colspan="2"><bean:message key="payout.info.void"/></td>
		</tr>
	</logic:notPresent>
</table>