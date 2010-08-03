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

<%@ page language="java"
	import="com.sapienter.jbilling.client.util.Constants"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling"%>

<p class="title"><bean:message key="invoice.download.title" /></p>
<p class="instr"><bean:message key="invoice.download.instr" /></p>


<html:errors />
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage" /></p>
</html:messages>

<html:form action="/download">
	<html:hidden property="operationType" />
	<table class="form">
		<tr class="form">
			<td class="form_prompt"><bean:message
				key="invoice.download.prompt.customer" /></td>
			<td><html:text property="customer" size="6" /></td>
			<td class="form_button"><html:submit styleClass="form_button"
				onclick="setOperationType(1);">
				<bean:message key="invoice.download.label.generate" />
			</html:submit></td>
		</tr>
		<tr class="form">
			<td class="form_prompt"><bean:message
				key="invoice.download.prompt.process" /></td>
			<td><html:text property="process" size="6" /></td>
			<td class="form_button"><html:submit styleClass="form_button"
				onclick="setOperationType(3);">
				<bean:message key="invoice.download.label.generate" />
			</html:submit></td>
		</tr>
		<tr class="form">
			<td class="form_prompt"><bean:message
				key="invoice.download.prompt.range" /></td>
			<td>
			<table class="form">
				<tr class="form">
					<td class="form_prompt"><bean:message
						key="invoice.download.label.start" /></td>
					<td><html:text property="rangeStart" size="6" /></td>
					<td class="form_prompt"><bean:message
						key="invoice.download.label.end" /></td>
					<td><html:text property="rangeEnd" size="6" /></td>
				</tr>
			</table>
			</td>
			<td class="form_button"><html:submit styleClass="form_button"
				onclick="setOperationType(2);">
				<bean:message key="invoice.download.label.generate" />
			</html:submit></td>
		</tr>
		<tr class="form">
			<td class="form_prompt"><bean:message
				key="invoice.download.prompt.date" /></td>
			<td>
			<table>
				<tr class="form">
					<td class="form_prompt"><bean:message
						key="invoice.download.label.start" /></td>
					<jbilling:dateFormat format="mm-dd">
						<td><html:text property="date_from_month" size="2" maxlength="2" /></td>
						<td><html:text property="date_from_day" size="2" maxlength="2" /></td>
					</jbilling:dateFormat>
					<jbilling:dateFormat format="dd-mm">
						<td><html:text property="date_from_day" size="2" maxlength="2" /></td>
						<td><html:text property="date_from_month" size="2" maxlength="2" /></td>
					</jbilling:dateFormat>
					<td><html:text property="date_from_year" size="4" maxlength="4" /></td>
					<td><bean:message key="all.prompt.dateFormat" /></td>
				</tr>
				<tr class="form">
					<td class="form_prompt"><bean:message
						key="invoice.download.label.end" /></td>
					<jbilling:dateFormat format="mm-dd">
						<td><html:text property="date_to_month" size="2" maxlength="2" /></td>
						<td><html:text property="date_to_day" size="2" maxlength="2" /></td>
					</jbilling:dateFormat>
					<jbilling:dateFormat format="dd-mm">
						<td><html:text property="date_to_day" size="2" maxlength="2" /></td>
						<td><html:text property="date_to_month" size="2" maxlength="2" /></td>
					</jbilling:dateFormat>
					<td><html:text property="date_to_year" size="4" maxlength="4" /></td>
					<td><bean:message key="all.prompt.dateFormat" /></td>
				</tr>
			</table>
			</td>
			<td class="form_button"><html:submit styleClass="form_button"
				onclick="setOperationType(4);">
				<bean:message key="invoice.download.label.generate" />
			</html:submit></td>
		</tr>
		<tr class="form">
			<td class="form_prompt"><bean:message
				key="invoice.download.prompt.number" /></td>
			<td>
			<table class="form">
				<tr class="form">
					<td class="form_prompt"><bean:message
						key="invoice.download.label.start" /></td>
					<td><html:text property="number_from" size="15" /></td>
                </tr>
				<tr class="form">
					<td class="form_prompt"><bean:message
						key="invoice.download.label.end" /></td>
					<td><html:text property="number_to" size="15" /></td>
				</tr>
			</table>
			</td>
			<td class="form_button"><html:submit styleClass="form_button"
				onclick="setOperationType(5);">
				<bean:message key="invoice.download.label.generate" />
			</html:submit></td>
		</tr>

	</table>
</html:form>

<script type="text/javascript">
	function setOperationType(val) {		
		document.downloadInvoices.operationType.value = val;
	}
</script>
