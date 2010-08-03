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

<p class="instr"><bean:message key="payout.manual.instr"/></p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<html:form action="/payout?action=edit" >
  <table class="form">
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="payout.prompt.startDate"/></td>
	 		<jbilling:dateFormat format="mm-dd">
				 <td><html:text property="start_month" size="2" maxlength="2"/></td>
				 <td><html:text property="start_day" size="2" maxlength="2"/></td>
			</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
				 <td><html:text property="start_day" size="2" maxlength="2"/></td>
				 <td><html:text property="start_month" size="2" maxlength="2"/></td>
			</jbilling:dateFormat>
	 		<td><html:text property="start_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="payout.prompt.endDate"/></td>
	 		<jbilling:dateFormat format="mm-dd">
				 <td><html:text property="end_month" size="2" maxlength="2"/></td>
				 <td><html:text property="end_day" size="2" maxlength="2"/></td>
			</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
				 <td><html:text property="end_day" size="2" maxlength="2"/></td>
				 <td><html:text property="end_month" size="2" maxlength="2"/></td>
			</jbilling:dateFormat>
	 		<td><html:text property="end_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 	</tr>
	 	<tr class="form">
	 		<td class="form_prompt"><bean:message key="payout.prompt.method"/></td>
	 		<td colspan="4">
	 			<html:radio property="method" value="cheque"> 
	 				<bean:message key="payment.type.cheque"/>
 				</html:radio>
	 			<html:radio property="method" value="cc"> 
	 				<bean:message key="payment.type.cc"/>
 				</html:radio>
	 		</td>
 		</tr>
	 	<tr class="form">
	 		<td class="form_button" colspan="5">
              	<html:submit styleClass="form_button">
              		<bean:message key="all.prompt.submit"/>
              	</html:submit>
            </td>
		</tr>	 
  </table>
</html:form>