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

<logic:present name='<%=Constants.SESSION_INVOICE_DTO%>' scope="session">
	<table>
	<tr>
		<td class="leftMenuOption">
			<html:link styleClass="leftMenu" page="/invoice/download.jsp">
				<bean:message key="invoice.downloadPDF.link"/>
			</html:link>
		</td>
	</tr>
	<%-- the delete link. Only for those with the permission --%>
	<jbilling:permission permission='<%=Constants.P_INVOICE_DELETE%>'>

		<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_INVOICE_DELETE%>'
			beanName="preference"/> 
		<logic:equal name="preference" value="1">
	  	<tr>
		  <td class="leftMenuOption">
			  <html:link styleClass="leftMenu" page="/invoice/view.jsp?confirm=do&noTitle=yes">
				  <bean:message key="invoice.delete.link"/>
			  </html:link>
 		  </td>
	    </tr>
	    </logic:equal>
	    
	</jbilling:permission>
	
	</table>
</logic:present>


