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

<%@ page language="java" import="com.sapienter.jbilling.client.util.Constants,com.sapienter.jbilling.server.user.partner.db.Partner"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<table class="info" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th class="info" colspan="2"><bean:message key="partner.info.title"/></th>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="partner.prompt.id"/></td>
		<td class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="id" scope="session"/></td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="partner.prompt.balance"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="balance" scope="session"/></td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="partner.prompt.totalPayments"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="totalPayments" scope="session"/></td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="partner.prompt.totalRefunds"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="totalRefunds" scope="session"/></td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="partner.prompt.totalPayouts"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="totalPayouts" scope="session"/></td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="partner.prompt.rate"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="percentageRate" scope="session"/></td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="partner.prompt.fee"/></td>
		<td  class="infodata">
			<logic:present name='<%=Constants.SESSION_PARTNER_DTO%>'
				  property="feeCurrencyId" scope="session">
			 <bean:define id="index" name='<%=Constants.SESSION_PARTNER_DTO%>'
				  property="feeCurrencyId"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>
			</logic:present>
			<bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			       property="referralFee" scope="session"/></td>
	</tr>
	<tr class="infoB">
		<jbilling:getOptions generalPeriod="true" map='<%= String.valueOf(((Partner) session.getAttribute(Constants.SESSION_PARTNER_DTO)).getPeriodUnit().getId())%>' />
		<td class="infoprompt"><bean:message key="partner.prompt.period"/></td>
		<td  class="infodata">
			<bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			            property="periodValue" scope="session"/>
	        <bean:write name="mapped_option"/>
	    </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="partner.prompt.nextPayout"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="nextPayoutDate" scope="session" formatKey="format.date"/></td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="partner.prompt.duePayout"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="duePayout" scope="session" formatKey="format.money"/></td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="partner.prompt.process"/></td>
		<td  class="infodata">
			<logic:equal name='<%=Constants.SESSION_PARTNER_DTO%>' property="automaticProcess"
				         scope="session" value="1">
	        	<bean:message key="all.prompt.yes"/>
	        </logic:equal>
			<logic:equal name='<%=Constants.SESSION_PARTNER_DTO%>' property="automaticProcess"
				         scope="session" value="0">
	        	<bean:message key="all.prompt.no"/>
	        </logic:equal>
	    </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="partner.prompt.clerk"/></td>
		<td  class="infodata"><bean:write name='<%=Constants.SESSION_PARTNER_DTO%>'  
			property="relatedClerkUserId" scope="session"/></td>
	</tr>

	<logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
				 property="mainRoleId"
				 scope="session"
				 value='<%=Constants.TYPE_PARTNER.toString()%>'>
	<jbilling:permission permission='<%=Constants.P_USER_EDIT_LINKS%>'>
	<tr>
		<td class="infocommands" colspan="2">
			<html:link page="/partnerMaintain.do?action=setup&mode=partner">
				<bean:message key="partner.link.edit"/>
			</html:link>
		</td>
	</tr>
	</jbilling:permission>
	</logic:notEqual>
	
</table>