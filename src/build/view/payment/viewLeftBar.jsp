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

<%-- this menu is only dispalyed when a payment is present, and it is
     not the review screen --%>
<logic:present name='<%=Constants.SESSION_PAYMENT_DTO%>' scope="session">

<table>
<%-- these modify links are only for those with the permission --%>
<jbilling:permission permission='<%=Constants.P_PAYMENT_MODIFY%>'>
   <%-- we can only edit or delete payments NOT linked to any invoice--%>
   <logic:empty name='<%=Constants.SESSION_PAYMENT_DTO%>' property="invoiceIds" 
                scope="session">
	    <tr>
		  <td class="leftMenuOption">
			  <html:link styleClass="leftMenu" page="/payment/view.jsp?confirm=do&noTitle=yes">
				  <bean:message key="payment.delete.link"/>
			  </html:link>
 		  </td>
	    </tr>
   <%-- only ENTERED payments can be edited --%>
   <logic:equal name='<%=Constants.SESSION_PAYMENT_DTO%>' property="resultId" 
                value='<%=Constants.RESULT_ENTERED.toString()%>'
                scope="session">
	  	<tr>
		  <td class="leftMenuOption">
			  <html:link styleClass="leftMenu" page="/paymentMaintain.do?action=setup&mode=payment&submode=edit">
				  <bean:message key="payment.edit.link"/>
			  </html:link>
			  </td>
	    </tr>
    </logic:equal>
    </logic:empty>
   <logic:greaterEqual name='<%=Constants.SESSION_PAYMENT_DTO%>' property="balance" value="0.01"
                scope="session">
	  	<tr>
		  <td class="leftMenuOption">
			  <html:link styleClass="leftMenu" page="/payment/apply.jsp?reset=yes">
				  <bean:message key="payment.apply.link"/>
			  </html:link>
			  </td>
	    </tr>
   </logic:greaterEqual>    
</jbilling:permission>
</table>

</logic:present>

