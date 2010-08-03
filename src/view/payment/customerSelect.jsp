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


<%-- removal of the form bean from the session is necessary to
     avoid old data to show up later in creation/edition --%>
<%-- when a customer is selected, the list of invoices has to be reset
	 otherwise it won't look for it again, even though this is a 
	 different customer. 
	 This is here AND in the customerSelectionTop because the customer
	 doesn't use the customer selection, but it has to be there also because
	 of the 'back' browser button --%>
<%
  session.removeAttribute("payment");  
  session.removeAttribute(Constants.SESSION_PAYMENT_DTO);
%>

<%-- let know the body if this is a cheque or a cc --%>
<logic:present parameter="cc">                
   <sess:setAttribute name="jsp_payment_method">cc</sess:setAttribute>
</logic:present>
<logic:present parameter="cheque">            
   <sess:setAttribute name="jsp_payment_method">cheque</sess:setAttribute>
</logic:present>
<logic:present parameter="ach">            
   <sess:setAttribute name="jsp_payment_method">ach</sess:setAttribute>
</logic:present>
<logic:present parameter="paypal">
   <sess:setAttribute name="jsp_payment_method">paypal</sess:setAttribute>
</logic:present>

<%-- let know the body if this is a refund --%>
<logic:present parameter="refund">                
	<sess:setAttribute name="jsp_is_refund">yes</sess:setAttribute>
	<%
	  session.removeAttribute(Constants.SESSION_LIST_KEY + Constants.LIST_TYPE_PAYMENT_USER);  
	%>
</logic:present>
<logic:notPresent parameter="refund">                
	<%
	  session.removeAttribute(Constants.SESSION_PAYMENT_DTO);
	  session.removeAttribute(Constants.SESSION_INVOICE_DTO);
	  session.removeAttribute(Constants.SESSION_LIST_KEY + Constants.LIST_TYPE_INVOICE);  
      session.removeAttribute("jsp_linked_invoices");
	%>
</logic:notPresent>


<%-- now depending who's logged, show up the list of customers or not --%>
<logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
	                     property="mainRoleId"
	                     scope="session"
	                     value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
     <tiles:insert definition="payment.enter.customerSelection" flush="true" />
</logic:notEqual>

<%-- Customers shouldn't select a user, they are the user
          This basically does just like if a user was selected from the list --%>
<logic:equal name='<%=Constants.SESSION_USER_DTO%>'
	                     property="mainRoleId"
	                     scope="session"
	                     value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
	    <%
	         session.setAttribute(Constants.SESSION_USER_ID, session.getAttribute(
	                    Constants.SESSION_LOGGED_USER_ID));
	    %>     
		<logic:redirect forward='<%=Constants.FORWARD_PAYMENT_CREATE%>' />
</logic:equal>