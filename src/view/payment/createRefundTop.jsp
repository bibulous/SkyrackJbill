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
<%@ taglib uri="http://jakarta.apache.org/taglibs/session-1.0" prefix="sess" %>


<%-- now let know the invoice list the forward values 
     can't use the Constants :( . The payment info form does the paging, so 
     here both from/to are the same --%>
<bean:define id="forward_from" 
	         value='<%=Constants.FORWARD_PAYMENT_CREATE%>' 
	         toScope="session"/>

<bean:define id="forward_to" 
	         value='<%=Constants.FORWARD_PAYMENT_CREATE%>' 
             toScope="session"/>

<p class="title">
<sess:equalsAttribute name="jsp_payment_method" match="cc">                	
     <bean:message key="refund.cc.title"/>
</sess:equalsAttribute>
<sess:equalsAttribute name="jsp_payment_method" match="cheque">                
     <bean:message key="refund.cheque.title"/>
</sess:equalsAttribute>
<sess:equalsAttribute name="jsp_payment_method" match="ach">                
     <bean:message key="refund.ach.title"/>
</sess:equalsAttribute>
</p>

<p class="instr">
     <bean:message key="refund.enter.instr"/>
</p>

<jbilling:genericList setup="true" type='<%=Constants.LIST_TYPE_PAYMENT_USER%>'/>


<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>


<p class="instr">
     <bean:message key="refund.enter.payment.list"/>
</p>
