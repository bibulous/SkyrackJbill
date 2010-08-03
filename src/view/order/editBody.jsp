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

<html:errors/>

<%-- I will need to convert the id to string, so I ask for the map to be in the session --%>
<jbilling:getOptions orderPeriod="true" inSession="true"/>
<jbilling:getOptions billingType="true" inSession="true"/>
<jbilling:getOptions generalPeriod="true"/>

<%-- Define if the period will be editable or not --%>
<bean:define id="jsp_not_invoiced" value="yes"/>
<logic:present name='<%=Constants.SESSION_ORDER_DTO%>' 
			  scope="session"
			  property="invoices">
   <logic:iterate name='<%=Constants.SESSION_ORDER_DTO%>' 
						   scope="session"
						   id="invoice"
						   property="invoices"
						   length="1">
       <bean:define id="jsp_not_invoiced" value="no"/>
   </logic:iterate>
</logic:present>

<html:form action="/orderMaintain?action=edit&mode=order" >
	<table class="form">
		<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="period">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
			<td class="form_prompt"><bean:message key="order.prompt.period"/></td>
			<td>
				<logic:equal name="jsp_not_invoiced" value="yes">
		          <html:select property="period">
		          <html:options collection='<%=Constants.PAGE_ORDER_PERIODS%>' 
				             property="code"
				            labelProperty="description"	/>
		          </html:select>
		        </logic:equal>
				<logic:equal name="jsp_not_invoiced" value="no">
					<bean:write name='<%=Constants.SESSION_ORDER_DTO%>' 
										property="periodStr" 
										scope="session"/>
		        </logic:equal>
             </td>
		</tr>
			<tr class="form">
				<td>
					 <jbilling:help page="orders" anchor="type">
						 <img border="0" src="/billing/graphics/help.gif"/>
					 </jbilling:help>
				</td>
				<td class="form_prompt"><bean:message key="order.prompt.billingType"/></td>
				<td>
				   <logic:equal name="jsp_not_invoiced" value="yes">
		               <html:select property="billingType">
			               <html:options collection='<%=Constants.PAGE_BILLING_TYPE%>' 
				             property="code"
				            labelProperty="description"	/>
		              </html:select>
		        </logic:equal>
				<logic:equal name="jsp_not_invoiced" value="no">
			         <bean:write name='<%=Constants.SESSION_ORDER_DTO%>' 
				                    property="billingTypeStr" 
				                    scope="session"/>
		        </logic:equal>
             </td>
		</tr>

		<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_USE_PRO_RATING%>'
				beanName="preference"/> 
		<logic:equal name="preference" value="1">
	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="active">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="order.prompt.cycleStart"/></td>
	 		
	 		<logic:equal name="jsp_not_invoiced" value="yes">
		 		<td> <table> <tr class="form">
		 		<jbilling:dateFormat format="mm-dd">
			 		<td><html:text property="cycle_month" size="2" maxlength="2"/></td>
			 		<td><html:text property="cycle_day" size="2" maxlength="2"/></td>
		 		</jbilling:dateFormat>
		 		<jbilling:dateFormat format="dd-mm">
			 		<td><html:text property="cycle_day" size="2" maxlength="2"/></td>
			 		<td><html:text property="cycle_month" size="2" maxlength="2"/></td>
		 		</jbilling:dateFormat>
		 		<td><html:text property="cycle_year" size="4" maxlength="4"/></td>
		 		<td><bean:message key="all.prompt.dateFormat"/></td>
		 		</tr></table></td>
	 		</logic:equal>
			<logic:equal name="jsp_not_invoiced" value="no">
				<td>
				<bean:write name='<%=Constants.SESSION_ORDER_DTO%>' 
			                property="cycleStarts" 
				            scope="session"
				            formatKey="format.date"/>
				</td>
	 		</logic:equal>
	 		
	 	</tr>
	 	</logic:equal>

	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="active">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="order.prompt.activeSince"/></td>
	 		<td> <table> <tr class="form">
	 		<jbilling:dateFormat format="mm-dd">
		 		<td><html:text property="since_month" size="2" maxlength="2"/></td>
		 		<td><html:text property="since_day" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
		 		<td><html:text property="since_day" size="2" maxlength="2"/></td>
		 		<td><html:text property="since_month" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<td><html:text property="since_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 		</tr></table></td>
	 	</tr>
 	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="active">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="order.prompt.activeUntil"/></td>
	 		<td> <table> <tr class="form">
	 		<jbilling:dateFormat format="mm-dd">
		 		<td><html:text property="until_month" size="2" maxlength="2"/></td>
		 		<td><html:text property="until_day" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
		 		<td><html:text property="until_day" size="2" maxlength="2"/></td>
		 		<td><html:text property="until_month" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<td><html:text property="until_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 		</tr></table></td>
	 	</tr>
       <%-- Avoid showing the next billiable day for new orders --%>	 	  
	 	<logic:present name='<%=Constants.SESSION_ORDER_DTO%>' 
			  scope="session">
	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="active">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
	 		<td class="form_prompt"><bean:message key="order.prompt.nextBillableDay"/></td>
	 		<td> <table> <tr class="form">
	 		<jbilling:dateFormat format="mm-dd">
		 		<td><html:text property="next_billable_month" size="2" maxlength="2"/></td>
		 		<td><html:text property="next_billable_day" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<jbilling:dateFormat format="dd-mm">
		 		<td><html:text property="next_billable_day" size="2" maxlength="2"/></td>
		 		<td><html:text property="next_billable_month" size="2" maxlength="2"/></td>
	 		</jbilling:dateFormat>
	 		<td><html:text property="next_billable_year" size="4" maxlength="4"/></td>
	 		<td><bean:message key="all.prompt.dateFormat"/></td>
	 		</tr></table></td>
	 	</tr>
	 	</logic:present>
	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="dueDate">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
			<td class="form_prompt"><bean:message key="user.prompt.dueDate"/></td>
			<td> <table> <tr class="form">
			<td >
		   		<html:text property="due_date_value" size="2" maxlength="2"/>
		   	</td>
		   	<td>
		   		<html:select property="due_date_unit_id">
			   		<html:options collection='<%=Constants.PAGE_GENERAL_PERIODS%>' 
				 		property="code"
						labelProperty="description"	/>
		  		</html:select>
 	    	</td>
 	    	<td>
 	    		<bean:message key="user.dueDate.help"/>
 	    	</td>
 	    	</tr></table></td>
		</tr>
		
		<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_USE_DF_FM%>'
				beanName="preference"/> 
		<logic:equal name="preference" value="1">
		<tr class="form">
			<td></td>
			<td class="form_prompt"><bean:message key="process.configuration.prompt.df_fm"/></td>
			<td>
			   <html:checkbox property="chbx_df_fm"/>
			</td>
		</tr>
		</logic:equal>

		<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_USE_ORDER_ANTICIPATION%>'
				beanName="preference"/> 
		<logic:equal name="preference" value="1">
		<tr class="form">
			<td></td>
			<td class="form_prompt"><bean:message key="order.prompt.anticipate"/></td>
			<td>
			   <html:text property="anticipate_periods" size="2"/>
			</td>
		</tr>
		</logic:equal>
		
		<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_ORDER_OWN_INVOICE%>'
				beanName="preference"/> 
		<logic:equal name="preference" value="1">
		<tr class="form">
			<td></td>
			<td class="form_prompt"><bean:message key="process.configuration.prompt.own_invoice"/></td>
			<td>
			   <html:checkbox property="chbx_own_invoice"/>
			</td>
		</tr>
		</logic:equal>
		
	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="notify">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
			<td class="form_prompt"><bean:message key="order.prompt.notify"/></td>
			<td>
				<html:checkbox property="chbx_notify"/>
			</td>
		</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="notes">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
			<td class="form_prompt"><bean:message key="order.prompt.notes"/></td>
			<td>
				<html:textarea rows="3" cols="35" property="notes"/>
			</td>
		</tr>
	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="notes">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
			<td class="form_prompt"><bean:message key="order.prompt.notesInInvoice"/></td>
			<td>
				<html:checkbox property="chbx_notes"/>
			</td>
		</tr>

		<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_USE_CURRENT_ORDER%>'
				beanName="preference"/> 
		<logic:equal name="preference" value="1">
	 	<tr class="form">
			<td>
				 <jbilling:help page="orders" anchor="current">
					 <img border="0" src="/billing/graphics/help.gif"/>
				 </jbilling:help>
			</td>
			<td class="form_prompt"><bean:message key="order.prompt.isCurrent"/></td>
			<td>
				<html:checkbox property="chbx_iscurrent"/>
			</td>
		</tr>
		</logic:equal>
		
	 	
		<tr class="form">
		   <td></td>
		   <td class="form_button" colspan="2">
		    <html:submit styleClass="form_button">
		    	<bean:message key="order.prompt.submit"/>
		    </html:submit>
		   </td>
	    </tr>
	</table>
</html:form>


