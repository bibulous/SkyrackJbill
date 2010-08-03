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

<%@ page language="java" import="com.sapienter.jbilling.client.util.Constants,java.util.Locale"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/session-1.0" prefix="sess" %>


<bean:define id="editable_amount" value="yes"/>
<logic:present parameter="direct">
	<bean:define id="editable_amount" value="no"/>
</logic:present>
<logic:present parameter="payout">
	<bean:define id="editable_amount" value="no"/>
</logic:present>
<logic:equal name='<%=Constants.SESSION_USER_DTO%>'
					   property="mainRoleId"
					   scope="session"
					   value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
    <bean:define id="editable_amount" value="no"/>					   
</logic:equal>



<%--  show the payment-cheque form  --%>
<html:form action="/paymentMaintain?action=edit&mode=payment">
     <logic:present parameter="create">                
        <html:hidden property="create" value="promotion"/>
     </logic:present> 	
     <logic:present parameter="payout">
     	<html:hidden property="create" value="payout"/>
   	 </logic:present>
     
     <%--  The action still needs to know this is a direct payment, to forward to
	       the right palce in case of an error --%>
	<logic:present parameter="direct">
		<html:hidden property="direct" value="yes"/>	 
	</logic:present>

	 <table class="form">
	 	<tr class="form">
	 		<td></td>
	 		<td class="form_prompt"><bean:message key="payment.amount"/></td>
	 		<td colspan="4">
	 			
	 	    <logic:present name="payment" property="currencyId">
			 <bean:define id="index" name="payment"
				  property="currencyId"/>
			 <bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   property='<%= "symbols[" + index + "].symbol" %>'
				   scope="application"
				   filter="false"/>
            </logic:present>
            
	 			<logic:equal name="editable_amount" value="no">
	 				<bean:write name="payment" property="amount" formatKey="format.money"/>
	 			</logic:equal>
	 			<logic:equal name="editable_amount" value="yes">
		 			<html:text property="amount" size="10" />
	 			</logic:equal>
	 		</td>
	 	</tr>
	 	<logic:notPresent parameter="direct">
 		<logic:notPresent parameter="payout">
        <sess:equalsAttribute name="jsp_payment_method" match="paypal" value="false"> 			
	 	<tr class="form">
	 		<td></td>
	 		<td class="form_prompt"><bean:message key="payment.date"/></td>
	 		<jbilling:dateFormat format="mm-dd">
	 			<td><html:text property="date_month" size="2" maxlength="2"/></td>
	 			<td><html:text property="date_day" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
	 			<td><html:text property="date_day" size="2" maxlength="2"/></td>
	 			<td><html:text property="date_month" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<td><html:text property="date_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 	</tr>
	 	</sess:equalsAttribute>
		</logic:notPresent>
		</logic:notPresent>
	 	
       <sess:equalsAttribute name="jsp_payment_method" match="cheque">                
      	   <html:hidden property="method" value="cheque"/>	 	
	 	<tr class="form">
	 		<td></td>
	 		<td class="form_prompt"><bean:message key="payment.cheque.bank"/></td>
	 		<td colspan="4"><html:text property="bank" size="20" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td></td>
	 		<td class="form_prompt"><bean:message key="payment.cheque.number"/></td>
	 		<td colspan="4"><html:text property="chequeNumber" size="20" /></td>
	 	</tr>
	 	<tr class="form">
	 		<td></td>
	 		<td class="form_prompt"><bean:message key="payment.cheque.date"/></td>
	 		<jbilling:dateFormat format="mm-dd">
				 <td><html:text property="chequeDate_month" size="2" maxlength="2"/></td>
				 <td><html:text property="chequeDate_day" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
				 <td><html:text property="chequeDate_day" size="2" maxlength="2"/></td>
				 <td><html:text property="chequeDate_month" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<td><html:text property="chequeDate_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 	</tr>
	 	</sess:equalsAttribute>

       <sess:equalsAttribute name="jsp_payment_method" match="ach">                
      	   <html:hidden property="method" value="ach"/>	 
      	  <tr class="form">
	 		  <td></td>
	 		  <td class="form_prompt"><bean:message key="ach.aba.prompt"/></td>
	 		  <td colspan="4"><html:text property="aba_code" size="9" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td></td>
	 		  <td class="form_prompt"><bean:message key="ach.account_number.prompt"/></td>
	 		  <td colspan="4"><html:text property="account_number" size="20" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td></td>
	 		  <td class="form_prompt"><bean:message key="ach.bank_name.prompt"/></td>
	 		  <td colspan="4"><html:text property="bank_name" size="30" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td></td>
	 		  <td class="form_prompt"><bean:message key="ach.account_name.prompt"/></td>
	 		  <td colspan="4"><html:text property="account_name" size="30" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td></td>
	 		  <td class="form_prompt"><bean:message key="ach.account_type.prompt"/></td>
	 		  <td><bean:message key="ach.account_type.chq.prompt"/></td>
	 		  <td><html:radio property="account_type" value="1"/></td>
	 		  <td><bean:message key="ach.account_type.sav.prompt"/></td>
	 		  <td><html:radio property="account_type" value="2"/></td>
	 	  </tr>
       </sess:equalsAttribute>
       
	   <sess:equalsAttribute name="jsp_payment_method" match="cc">                
      	   <html:hidden property="method" value="cc"/>
      	   <tr class="form">
	 		  <td></td>
	 		  <td class="form_prompt"><bean:message key="payment.cc.number"/></td>
	 		  <td colspan="4"><html:text property="ccNumber" size="20" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td></td>
	 		  <td class="form_prompt"><bean:message key="payment.cc.name"/></td>
	 		  <td colspan="4"><html:text property="ccName" size="20" /></td>
	 	  </tr>
 	 	  <tr class="form">
	 		  <td></td>
	 	  	  <td class="form_prompt"><bean:message key="payment.cc.date"/></td>
	 		  <td><html:text property="ccExpiry_month" size="2" maxlength="2"/></td>
	 		  <td><html:text property="ccExpiry_year" size="4" maxlength="4"/></td>
	 		  <td><bean:message key="all.prompt.ccDateFormat"/></td>
	 	  </tr>
	 	  
		 <logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
								 property="mainRoleId"
								 scope="session"
								 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>	 	  
	 	 <logic:notPresent parameter="direct">
	 	 <logic:notPresent name='<%=Constants.SESSION_PAYMENT_DTO%>' property="id">
			<tr class="form">
				<td>
					 <jbilling:help page="payments" anchor="realTime">
						 <img border="0" src="/billing/graphics/help.gif"/>
					 </jbilling:help>
				</td>
			   <td class="form_prompt"><bean:message key="payment.cc.processNow"/></td>
			   <td colspan="4"><html:checkbox property="chbx_processNow"/></td>
		   </tr>
		 </logic:notPresent>
	     </logic:notPresent>
	     <logic:present parameter="direct">
	     	<html:hidden property="chbx_processNow" value="true"/>
	     </logic:present>
		 </logic:notEqual>	
      	</sess:equalsAttribute>

		 <%-- Customers always pay realtime --%>
		 <logic:equal name='<%=Constants.SESSION_USER_DTO%>'
								 property="mainRoleId"
								 scope="session"
								 value='<%=Constants.TYPE_CUSTOMER.toString()%>'> 	  
			 <html:hidden property="chbx_processNow" value="true"/>
		 </logic:equal>
	 	
       <sess:equalsAttribute name="jsp_payment_method" match="paypal" value="false">
       <tr class="form">
	 		  <td></td>
              <td colspan="5" class="form_button">
              	<html:submit styleClass="form_button" property="submit" value="Submit">
					<bean:message key="all.prompt.submit"/>
				</html:submit>
              </td>
       </tr>
       </sess:equalsAttribute>
       
	 </table>
</html:form>

<sess:equalsAttribute name="jsp_payment_method" match="paypal">
	<logic:greaterThan name="payment" property="amount" value="0">
	<form action="https://www.paypal.com/cgi-bin/webscr" method="post">
	   <input type="hidden" name="cmd" value="_xclick"/>
	   
	   <jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_PAYPAL_ACCOUNT%>'
			beanName="jsp_account"/> 
	   <input type="hidden" name="business" value="<bean:write name="jsp_account"/>"/>
	   
	   <input type="hidden" name="item_name" value="<bean:message key="payment.paypal.description"/> <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' property="number"/> "/>
	   <input type="hidden" name="invoice" 
	   	   value="<bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' property="id"/>"/>

	   <input type="hidden" name="amount" value="<c:out value="${jsp_paypay_amount}"/>"/>
	   	
	   <input type="hidden" name="no_note" value="1"/>
	   <input type="hidden" name="no_shipping" value="1"/>
	   
	   <bean:define id="index" name='<%=Constants.SESSION_INVOICE_DTO%>' 
	   		property="currency.id"/>
	   <input type="hidden" name="currency_code" value="<bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
	   		property='<%= "symbols[" + index + "].code" %>'
	   		scope="application"/>"/>
	   	
	   <input type="hidden" name="lc" value="<bean:write name='<%=Constants.SESSION_USER_DTO%>' 
	   		property="locale.country"/>"/>
	   		
		<%--
		PayPal Instant Payment Notification (IPN) calls back a URL where jbilling listens.
		See the URL below for the format that the URL should follow.
		This URL should be setup as part of your PayPal account (for the documentation)
			1. Log in to your Business or Premier PayPal account.
			2. Click the Profile subtab.
			3. Click the Instant Payment Notification Preferences link in the Selling Preferences column.
			4. Click Edit.
			5. Click the checkbox and enter the URL at which you would like to receive your IPN Notifications.
			6. Click Save.
		
		For testing, you could use the following line:
	   <input type="hidden" name="notify_url" 
	   		value="https://yourserver.com/billing/callback?caller=paypal"/>
	   	--%>
	   		
	   <jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_PAYPAL_BUTTON_URL%>'
			beanName="jsp_button_url"/> 		
	   <input type="image" src="<bean:write name="jsp_button_url"/>" 
				 border="0" name="submit"/>
	</form>
	</logic:greaterThan>
</sess:equalsAttribute>
	 	
