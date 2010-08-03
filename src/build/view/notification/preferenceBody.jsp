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

<p class="instr">
  <br/>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<html:form action="/notificationPreference?action=edit&mode=notificationPreference" >
  <table class="form">
	 	<tr class="form">
			<td>
				 <jbilling:help page="notifications" anchor="self_delivery">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="notification.preference.selfDeliver.prompt"/></td>
	 		<td><html:checkbox property="chbx_self_delivery"/></td>
	 	</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="notifications" anchor="notes">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="notification.preference.showNotes.prompt"/></td>
	 		<td><html:checkbox property="chbx_show_notes"/></td>
	 	</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="notifications" anchor="orders">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="notification.preference.orderDays1.prompt"/></td>
	 		<td><html:text property="order_days1" size="2"/></td>
	 	</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="notifications" anchor="orders">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="notification.preference.orderDays2.prompt"/></td>
	 		<td><html:text property="order_days2" size="2"/></td>
	 	</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="notifications" anchor="orders">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="notification.preference.orderDays3.prompt"/></td>
	 		<td><html:text property="order_days3" size="2"/></td>
	 	</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="notifications" anchor="invoiceReminders">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="notification.preference.invoiceRemiders.prompt"/></td>
	 		<td><html:checkbox property="chbx_invoice_reminders"/></td>
	 	</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="notifications" anchor="invoiceReminders">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="notification.preference.reminders.first"/></td>
	 		<td><html:text property="first_reminder" size="2"/></td>
	 	</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="notifications" anchor="invoiceReminders">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="notification.preference.reminders.next"/></td>
	 		<td><html:text property="next_reminder" size="2"/></td>
	 	</tr>
	  
	<tr class="form">
		<td></td>
		<td colspan="2" class="form_button">
			<html:submit styleClass="form_button">
                <bean:message key="all.prompt.submit"/>                
            </html:submit>
        </td>
	</tr>

  </table>
</html:form>
