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


<logic:notPresent parameter="noTitle">
	<p class="title"> <bean:message key="invoice.title"/> </p>
	<p class="instr"> 
		<bean:message key="invoice.instr"/><br/>
		<bean:message key="all.prompt.help" />
	    <jbilling:help page="invoices" anchor="details">
			 <bean:message key="all.prompt.here" />
	    </jbilling:help>
	</p>
	
	<html:messages message="true" id="myMessage">
		<p><bean:write name="myMessage"/></p>
	</html:messages>
</logic:notPresent>

<logic:present name='<%=Constants.SESSION_INVOICE_DTO%>' scope="session">
<logic:equal name='<%=Constants.SESSION_USER_DTO%>'
					 property="mainRoleId"
					 scope="session"
					 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
	<logic:equal name='<%=Constants.SESSION_INVOICE_DTO%>' 
		         property="invoiceStatus.id"
		         value="2"
		         scope="session">
	<p>
		<html:link page="/paymentMaintain.do?action=current_invoice">
		   <bean:message key="invoice.pay.link"/>
		</html:link>
	</p>
	</logic:equal>
</logic:equal>
</logic:present>
	

<logic:present parameter="confirm">
	<logic:equal parameter="confirm" value="do">
		  <p>
		      <bean:message key="invoice.delete.confirm"/> <br/>
              <html:link page="/invoiceMaintain.do?action=delete">
              	   <bean:message key="all.prompt.yes"/>
              </html:link><br/>
              <html:link page="/invoice/view.jsp?confirm=no">
              	   <bean:message key="all.prompt.no"/>
              </html:link><br/>
   	    </p>
	</logic:equal>
	
	<logic:equal parameter="confirm" value="no">
          <p><bean:message key="invoice.delete.notDone"/></p>
	</logic:equal>	
</logic:present>

<table class="info">
	<tr>
		<th class="info" colspan="2"><bean:message key="invoice.info.title"/></th>
	</tr>
<logic:present name='<%=Constants.SESSION_INVOICE_DTO%>' scope="session">
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="invoice.number.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="number"
                        scope="session"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="invoice.id.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="id"
                        scope="session"/>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="invoice.createDateTime.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="createDatetime"
                        scope="session"
                        formatKey="format.date"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="invoice.dueDate.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="dueDate"
                        scope="session"
                        formatKey="format.date"/>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="invoice.total.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="total"
                        scope="session"
                        formatKey="format.money"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="invoice.is_payable.prompt"/></td>
		<td class="infodata">	
			<logic:equal name='<%=Constants.SESSION_INVOICE_DTO%>' 
                         property="invoiceStatus.id"
				         scope="session"
				         value="1">
					<bean:message key="invoice.status.paid"/>
			</logic:equal>
			<logic:equal name='<%=Constants.SESSION_INVOICE_DTO%>' 
                         property="invoiceStatus.id"
				         scope="session"
				         value="2">
					<bean:message key="invoice.status.notPaid"/>
			</logic:equal>
			<logic:equal name='<%=Constants.SESSION_INVOICE_DTO%>'
                         property="invoiceStatus.id"
				         scope="session"
				         value="3">
					<bean:message key="invoice.status.carried"/>
			</logic:equal>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="invoice.balance.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="balance"
                        scope="session"
                        formatKey="format.money"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="invoice.carriedBalance.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="carriedBalance"
                        scope="session"
                        formatKey="format.money"/>
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="invoice.attempts.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="paymentAttempts"
                        scope="session"/>
        </td>
	</tr>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="invoice.userId.prompt"/></td>
		<td class="infodata">	
			<%-- customers don't see a link to themselves --%>
			<logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
								 property="mainRoleId"
								 scope="session"
								 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
			<html:link page="/userMaintain.do?action=setup" 
				       paramId="id" 
					   paramName='<%=Constants.SESSION_INVOICE_DTO%>' 
					   paramProperty="userId">
					<bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="userId"
                        scope="session"/>
            </html:link>
		    </logic:notEqual>
		    
		    <logic:equal name='<%=Constants.SESSION_USER_DTO%>'
								 property="mainRoleId"
								 scope="session"
								 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="userId"
                        scope="session"/>
		    </logic:equal>
			
        </td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="currency.external.prompt.name"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="currencyName"
                        scope="session"/>
        </td>
	</tr>

    <%-- now go through each related payment and display a link to it --%>
	<tr class="infoB">
		<td class="infoprompt"><bean:message key="invoice.payments.prompt"/></td>
		<td></td>
	</tr>
	<logic:iterate name='<%=Constants.SESSION_INVOICE_DTO%>' 
					   scope="session"
					   id="payment"
					   property="paymentMap">

		<tr class="infoB">
			<td class="infoprompt"></td>
			<td>
				<table>
				<tr class="infoB">
				<td class="infodata">
				<html:link page="/paymentMaintain.do?action=view" paramId="id" 
					       paramName="payment"
					       paramProperty="payment.id">
					<bean:write name="payment" property="payment.id"/>
				</html:link>
				</td>
				<td class="infodata">
				<bean:write name="payment" 
                        property="payment.createDatetime"
                        scope="page"
                        formatKey="format.date"/>
                </td>
                <td class="infodata">
                	<bean:define id="index" name="payment"
					  property="payment.currency.id"/>
					<bean:write name='<%= Constants.APP_CURRENCY_SYMBOLS %>'
				   		property='<%= "symbols[" + index + "].symbol" %>'
					    scope="application"
				   		filter="false"/>
            		<bean:write name="payment" 
                        property="payment.amount"
                        scope="page"
                        formatKey="format.money"/>
                	
               	</td>
				</tr>
				</table>
			</td>
        </tr>			
	</logic:iterate>
	
    <%-- now go through each included order and display a link to it --%>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="invoice.payments.orders"/></td>
		<td></td>
	</tr>
	<tr class="infoA">
		<td class="infoprompt"></td>
		<td>
		<table>
		<logic:iterate name='<%=Constants.SESSION_INVOICE_DTO%>' 
						   scope="session"
						   id="order"
						   property="orderProcesses">
		  <tr class="infoA">
			<td class="infodata">
				<html:link page="/orderMaintain.do?action=view" paramId="id" 
						   paramName="order"
						   paramProperty="purchaseOrder.id">
					<bean:write name="order" property="purchaseOrder.id"/>
				</html:link>
			</td>
			<td class="infodata">
				<bean:write name="order" property="purchaseOrder.createDate" formatKey="format.date"/>
			</td>
		  </tr>	
		</logic:iterate>
		</table>		
		</td>
	</tr>

	<tr class="infoB">
		<td class="infoprompt"><bean:message key="invoice.delegated.prompt"/></td>
		<td class="infodata">	
			<html:link action="invoiceMaintain" paramId="id" 
					   paramName='<%=Constants.SESSION_INVOICE_DTO%>'
					   paramProperty="delegatedInvoiceId"
					   paramScope="session">
				<bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
							property="delegatedInvoiceId"
							scope="session" />
			</html:link>
			
        </td>
	</tr>
	
    <%-- now go through each included invoice and display a link to it --%>
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="invoice.included.prompt"/></td>
		<td></td>
	</tr>
	<logic:present name='<%=Constants.SESSION_INVOICE_DTO%>' property="invoices">
	<tr class="infoA">
		<td class="infoprompt"></td>
		<td>
		<table>
		<logic:iterate name='<%=Constants.SESSION_INVOICE_DTO%>' 
						   scope="session"
						   id="incInvoice"
						   property="invoices">
          <bean:define id="incInvoiceId" name="incInvoice" property="id"/>
		  <tr class="infoA">
			<td class="infodata">
				<html:link page="/invoiceMaintain.do?action=view" paramId="id" 
						   paramName="incInvoiceId">
					<bean:write name="incInvoiceId"/>
				</html:link>
			</td>
		  </tr>	
		</logic:iterate>
		</table>		
		</td>
	</tr>
	</logic:present>

	<tr class="infoB">
		<td class="infoprompt"><bean:message key="invoice.notes.prompt"/></td>
		<td class="infodata">	
            <bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
                        property="customerNotes"
                        scope="session"
                        filter="false"/>
        </td>
	</tr>
	<%-- customers don't see a link to self-send an email --%>
	<logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
						 property="mainRoleId"
						 scope="session"
						 value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
	
	<tr>
		<td class="infocommands" colspan="2">
			<html:link page="/invoiceMaintain.do?action=notify"
				       paramId="id"
				       paramName='<%=Constants.SESSION_INVOICE_DTO%>' 
				       paramProperty="id"
				       paramScope="session">
				<bean:message key="invoice.notify.link"/>
			</html:link>
	    </td>
	</tr>
	</logic:notEqual>
	
</table>
<p><bean:message key="invoice.lines.title"/></p>
<table class="list">
	<tr class="listH">
		<td><bean:message key="invoice.line.description.prompt"/></td>
		<td><bean:message key="invoice.line.quantity.prompt"/></td>
		<td/> <!-- room for the currency symbol -->
		<td><bean:message key="invoice.line.price.prompt"/></td>
		<td><bean:message key="invoice.line.amount.prompt"/></td>
	</tr>
	<logic:iterate id="line" name='<%=Constants.SESSION_INVOICE_DTO%>' 
		           property="invoiceLines"
		           scope="session">
		<logic:present name="line" property="typeId">
		<c:choose>
			<c:when test="${colorFlag == 1}">
				<tr class="listB">
				<c:remove var="colorFlag"/>
			</c:when>
			<c:otherwise>
				<tr class="listA">
				<c:set var="colorFlag" value="1"/>
			</c:otherwise>
		</c:choose>
		</logic:present>
		<logic:notPresent name="line" property="typeId">
			<tr class="listSubaccount">
		</logic:notPresent>
		
			<td class="list"><bean:write name="line" property="description"/></td>
			<td class="list" align="right"><bean:write name="line" property="quantity" formatKey="format.double"/></td>
			
			<logic:equal name="line" property="isPercentage" value="0">
			<td class="list">
				<logic:present name="line" property="amount">
				<bean:write name='<%=Constants.SESSION_INVOICE_DTO%>' 
							property="currencySymbol"
							scope="session"
							filter="false" />
				</logic:present>
			</td>
			<td class="list" align="right"><bean:write name="line" property="price" formatKey="format.money"/></td>
			</logic:equal>
			<%-- Percentage lines do not show up the price --%>
			<logic:equal name="line" property="isPercentage" value="1">
			<td class="list">%</td>
			<td class="list"></td>
			</logic:equal>
			
			<td class="list" align="right"><bean:write name="line" property="amount" formatKey="format.money"/></td>									
		</tr>
	</logic:iterate>
</logic:present>

<logic:notPresent name='<%=Constants.SESSION_INVOICE_DTO%>' scope="session">
	<tr class="infoA">
		<td class="infoprompt"><bean:message key="invoice.notPresent"/></td>
	</tr>
</logic:notPresent>
</table>	
