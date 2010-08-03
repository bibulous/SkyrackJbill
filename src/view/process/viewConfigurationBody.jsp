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
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c_rt" %>


<jbilling:getOptions generalPeriod="true"/>

<p class="title"><bean:message key="process.configuration.title"/></p>
<p class="instr"><bean:message key="process.configuration.instr"/></p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<html:form action="/processConfigurationMaintain?action=edit&mode=configuration" >

<table class="form">
	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="next_run_date">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.nextRunDate"/></td>
 		<jbilling:dateFormat format="mm-dd">
			<td><html:text property="run_month" size="2" maxlength="2"/></td>
			<td><html:text property="run_day" size="2" maxlength="2"/></td>
		</jbilling:dateFormat>
 		<jbilling:dateFormat format="dd-mm">
			<td><html:text property="run_day" size="2" maxlength="2"/></td>
			<td><html:text property="run_month" size="2" maxlength="2"/></td>
		</jbilling:dateFormat>
		<td><html:text property="run_year" size="4" maxlength="4"/></td>
		<td><bean:message key="all.prompt.dateFormat"/></td>
	</tr>
	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="report">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.generateReport"/></td>
		<td colspan="4">	
			 <html:checkbox property="chbx_generateReport" />
         </td>
	</tr>
	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="report_days">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.daysForReport"/></td>
		<td colspan="4">	
			<html:text property="report_days" size="2" maxlength="2"/>        
        </td>
	</tr>
	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="retry_number">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.retries"/></td>
		<td colspan="4">	
			<html:text property="retries" size="2" maxlength="2"/>        
		</td>
	</tr>
	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="retry_days">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.daysForRetry"/></td>
		<td colspan="4">	
			<html:text property="retries_days" size="2" maxlength="2"/>        
        </td>
	</tr>
	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="period">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.period"/></td>
		<td colspan="4">
		   <html:text property="period_unit_value" size="2" maxlength="2"/>&nbsp
		   <html:select property="period_unit_id">
			   <html:options collection='<%=Constants.PAGE_GENERAL_PERIODS%>' 
				 property="code"
				labelProperty="description"	/>
		  </html:select>
 	    </td>
	</tr>
	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="due_date">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.daysDueDate"/></td>
		<td colspan="4">
		   <html:text property="due_date_value" size="2" maxlength="2"/>&nbsp
		   <html:select property="due_date_unit_id">
			   <html:options collection='<%=Constants.PAGE_GENERAL_PERIODS%>' 
				 property="code"
				labelProperty="description"	/>
		  </html:select>
 	    </td>
	</tr>
	
	<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_USE_DF_FM%>'
			beanName="preference"/> 
	<logic:equal name="preference" value="1">
	<tr class="form">
		<td></td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.df_fm"/></td>
		<td colspan="4">
		   <html:checkbox property="chbx_df_fm"/>
 	    </td>
	</tr>
	</logic:equal>
	
	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="require_recurring">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.onlyRecurring"/></td>
		<td colspan="4">
		   <html:checkbox property="chbx_only_recurring"/>
 	    </td>
	</tr>

	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="invoice_date">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.invoiceDate"/></td>
		<td colspan="4">
		   <html:checkbox property="chbx_invoice_date"/>
 	    </td>
	</tr>

	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="auto_payment">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.autoPayment"/></td>
		<td colspan="4">
		   <html:checkbox property="chbx_auto_payment"/>
 	    </td>
	</tr>

	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="maximum_periods">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.maxPeriods"/></td>
		<td colspan="4">
		   <html:text property="maximum_periods" size="2" maxlength="2"/>
 	    </td>
	</tr>

	<tr class="form">
		<td>
			 <jbilling:help page="process" anchor="payment_apply">
				 <img border="0" src="/billing/graphics/help.gif"/>
			 </jbilling:help>
		</td>
		<td class="form_prompt"><bean:message key="process.configuration.prompt.paymentApply"/></td>
		<td colspan="4">
		   <html:checkbox property="chbx_payment_apply"/>
 	    </td>
	</tr>

	
	<tr class="form">
		<td colspan="6" class="form_button">
			<html:submit styleClass="form_button">
                <bean:message key="all.prompt.submit"/>                
            </html:submit>
        </td>
	</tr>
</table>
</html:form>
