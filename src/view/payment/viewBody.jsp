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


<%-- If this is a refund, the dto is in the session with a different key.
     This is necessary because SESSION_PAYMENT_DTO will hold the payment linked
     to this refund --%>
<sess:existsAttribute name="jsp_is_refund" value="false">
	<bean:define id="dto" name='<%=Constants.SESSION_PAYMENT_DTO%>'
		         scope="session"/>
</sess:existsAttribute>
<sess:existsAttribute name="jsp_is_refund" >
	<bean:define id="dto" name='<%=Constants.SESSION_PAYMENT_DTO_REFUND%>'
		         scope="session"/>
</sess:existsAttribute>

<%
    // eventually, this was the cleanest way to do this ... :(
	Integer methodId = ((com.sapienter.jbilling.server.payment.PaymentDTOEx) pageContext.getAttribute("dto")).getMethodId();
	
	if (methodId.equals(Constants.PAYMENT_METHOD_CHEQUE)) {
		session.setAttribute("jsp_payment_method", "cheque");
	} else if (methodId.equals(Constants.PAYMENT_METHOD_ACH)) {
		session.setAttribute("jsp_payment_method", "ach");
	} else if (methodId.equals(Constants.PAYMENT_METHOD_PAYPAL)) {
		session.setAttribute("jsp_payment_method", "paypal");
	} else { // all the rest are credit cards (visa, mc, amex, ... )
		session.setAttribute("jsp_payment_method", "cc");
	}
		
%>


<table class="info">
	<tr>
		<logic:present parameter="refund">
		<th class="info" colspan="2"><bean:message key="refund.info.title"/></th>
		</logic:present>
		<logic:notPresent parameter="refund">
		<th class="info" colspan="2"><bean:message key="payment.info.title"/></th>
		</logic:notPresent>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="payment.id"/></td>
		<td class="infodata">	
            <bean:write name="dto" 
                        property="id"
                        scope="page"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="payment.amount"/></td>
		<td class="infodata">	
			 <bean:define id="index" name="dto"
				  property="currency.id"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>

            <bean:write name="dto" 
                        property="amount"
                        scope="page"
                        formatKey="format.money"/>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="payment.date"/></td>
		<td class="infodata">	
            <bean:write name="dto" 
                        property="paymentDate"
                        scope="page"
                        formatKey="format.date"/>
        </td>
	</tr>
	<sess:existsAttribute name="jsp_is_refund" value="false">
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="payment.balance"/></td>
		<td class="infodata">	
			 <bean:define id="index" name="dto"
				  property="currency.id"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>

            <bean:write name="dto" 
                        property="balance"
                        scope="page"
                        formatKey="format.money"/>
        </td>
	</tr>
	</sess:existsAttribute>
	
    <sess:equalsAttribute name="jsp_payment_method" match="cheque">                
		<tr class="infoA">
			<td class="infoprompt"><bean:message key="payment.cheque.bank"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="cheque.bank"
							scope="page"/>
			</td>
		</tr>
		<tr class="infoB">
			<td class="infoprompt"><bean:message key="payment.cheque.number"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="cheque.number"
							scope="page"/>
			</td>
		</tr>
		<tr class="infoA">
			<td class="infoprompt"><bean:message key="payment.cheque.date"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="cheque.date"
							scope="page"
							formatKey="format.date"/>
			</td>
		</tr>
	</sess:equalsAttribute>

    <sess:equalsAttribute name="jsp_payment_method" match="ach">                
		<tr class="infoA">
			<td class="infoprompt"><bean:message key="ach.aba.prompt"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="ach.abaRouting"
							scope="page"/>
			</td>
		</tr>
		<tr class="infoB">
			<td class="infoprompt"><bean:message key="ach.account_number.prompt"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="ach.bankAccount"
							scope="page"/>
			</td>
		</tr>
		<tr class="infoA">
			<td class="infoprompt"><bean:message key="ach.bank_name.prompt"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="ach.bankName"
							scope="page"/>
			</td>
		</tr>
		<tr class="infoB">
			<td class="infoprompt"><bean:message key="ach.account_name.prompt"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="ach.accountName"
							scope="page"/>
			</td>
		</tr>
		<tr class="infoA">
			<td class="infoprompt"><bean:message key="ach.account_type.prompt"/></td>
			<td class="infodata">	
				<logic:equal name="dto" 
							property="ach.accountType"
							scope="page"
							value="1">
					<bean:message key="ach.account_type.chq.prompt"/>
			    </logic:equal>
			    <logic:equal name="dto" 
							property="ach.accountType"
							scope="page"
							value="2">
					<bean:message key="ach.account_type.sav.prompt"/>
			    </logic:equal>
			</td>
		</tr>
		<logic:present name="dto" property="resultStr">
		<tr class="infoB">
			<td class="infoprompt"><bean:message key="payment.result"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="resultStr"
							scope="page"/>
			</td>
		</tr>
		</logic:present>

		<%-- the authorization has to be made available to the page that will show it --%>
		<logic:present name="dto" property="authorization">
			<bean:define id="authorizationDto"  name="dto" property="authorization" toScope="request"/>
		</logic:present>
		
	</sess:equalsAttribute>
	
	<sess:equalsAttribute name="jsp_payment_method" match="paypal">
		<tr class="infoA">
			<td class="infoprompt" colspan="2">
				<bean:message key="payment.paypal.name"/>
			</td>
		</tr>
	</sess:equalsAttribute>

 	<sess:equalsAttribute name="jsp_payment_method" match="cc">                
		<tr class="infoA">
			<td class="infoprompt"><bean:message key="payment.cc.name"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="creditCard.name"
							scope="page"/>
			</td>
		</tr>
		<tr class="infoB">
			<td class="infoprompt"><bean:message key="payment.cc.type"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="creditCard.type"
							scope="page"/>
			</td>
		</tr>
		<tr class="infoA">
			<td class="infoprompt"><bean:message key="payment.cc.number"/></td>
			<td class="infodata">	
			  <jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_CC%>'>
			    <jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_HIDE_CC_NUMBERS%>'
			                            beanName="hide_cc"/>
				<logic:equal name="hide_cc" value="0">
					<bean:write name="dto" 
							property="creditCard.number"
							scope="page"/>
				</logic:equal>    
			    <logic:equal name="hide_cc" value="1">
                                <c:set var="ccNumber" value="${dto.creditCard.number}"/>
               ************<%=((String)(pageContext.getAttribute("ccNumber"))).substring(
                              ((String)(pageContext.getAttribute("ccNumber"))).length() - 4) %>
				</logic:equal>
			  </jbilling:permission>
			  <jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_CC%>'
                            negative="true">
                   <c:set var="ccNumber" value="${dto.creditCard.number}"/>
               ************<%=((String)(pageContext.getAttribute("ccNumber"))).substring(
                              ((String)(pageContext.getAttribute("ccNumber"))).length() - 4) %>
                            
              </jbilling:permission>
			</td>
		</tr>
		<tr class="infoB">
			<td class="infoprompt"><bean:message key="payment.cc.date"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="creditCard.ccExpiry"
							scope="page"
							formatKey="format.date"/>
			</td>
		</tr>
		<logic:present name="dto" property="resultStr">
		<tr class="infoA">
			<td class="infoprompt"><bean:message key="payment.result"/></td>
			<td class="infodata">	
				<bean:write name="dto" 
							property="resultStr"
							scope="page"/>
			</td>
		</tr>
		</logic:present>
		
		<logic:equal name="dto" property="isPreauth" value="1">
		<tr class="infoB">
			<td class="infoprompt"><bean:message key="payment.isPreauth"/></td>
			<td class="infodata"><bean:message key="all.prompt.yes"/></td>
		</tr>
		</logic:equal>
		
		
		<%-- the authorization has to be made available to the page that will show it --%>
		<logic:present name="dto" property="authorization">
			<bean:define id="authorizationDto"  name="dto" property="authorization" toScope="request"/>
		</logic:present>
	</sess:equalsAttribute>
	
	<%-- customers don't see a link to self-send an email --%>
	<logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
					property="mainRoleId"
					scope="session"
					value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
	<logic:present name="dto" property="id">
	<%-- refunds are not emailed --%>
	<sess:existsAttribute name="jsp_is_refund" value="false">
		<tr>
			<td class="infocommands" colspan="2">
				<html:link page="/paymentMaintain.do?action=notify"
						   paramId="id"
						   paramName="dto"
						   paramProperty="id"
						   paramScope="page">
					<bean:message key="payment.notify.link"/>
				</html:link>
			</td>
		</tr>
	</sess:existsAttribute>
	</logic:present>
	</logic:notEqual>
	
</table>
<%-- The send and cancel options are available only if this is a review --%>
<logic:present parameter="review">                
    <p align="center">
       <bean:define id="payout" value="no"/>
       <logic:present parameter="payout">
       	    <bean:define id="payout" value="yes"/>
       </logic:present>
       <html:link page="/paymentMaintain.do?action=send&mode=payment" 
       	          paramId="payout" paramName="payout">
   		    <bean:message key="all.prompt.submit"/>
       </html:link><br/>
       <html:link page="/paymentMaintain.do?action=cancel&mode=payment"
                  paramId="payout" paramName="payout">
		    <bean:message key="all.prompt.cancel"/>
       </html:link>
    </p>    
</logic:present>
