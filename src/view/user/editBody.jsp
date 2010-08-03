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
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>


<jbilling:getUser userId='<%=(Integer) session.getAttribute(Constants.SESSION_USER_ID) %>'
	           createForm="true"/>

<logic:present name='<%=Constants.SESSION_PARTNER_ID%>' scope="session">
<logic:notEqual name='<%=Constants.SESSION_USER_DTO%>'
				 property="mainRoleId"
				 scope="session"
				 value='<%=Constants.TYPE_PARTNER.toString()%>'>
	<html:link page="/partnerMaintain.do?action=setup&mode=partner">
		<bean:message key="user.edit.partner"/>
	</html:link>
</logic:notEqual>
</logic:present>
	                          
<html:form action="/userMaintain?action=update">
	 <table class="form">
	 	<%--  The ID is visiable for everyone  --%>
	 	<tr class="form">
	 		<td></td>
	 		<td class="form_prompt"><bean:message key="user.prompt.id"/></td>
	 		<td ><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="userId" scope="page"/></td>
	 	</tr>

	 	<%--  Entity is editable or nothing  --%>	 	
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_ENTITY%>'>
			 <tr class="form">
		 		<td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.entity"/></td>
				 <td ><html:text property="entity" size="10" /></td>
				 <bean:define id="submitable" value="1"/>
			 </tr>
	 	</jbilling:permission>

	 	<%--  The type is editable for some, viewable to others, and some get nothing  --%>	 	
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_TYPE%>'>
	 		<%-- Change: the type can not be changed. If a customer becames a partner,
	 		     a new user has to be created, with all the partner fields --%>
			 <tr class="form">
		 		 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.type"/></td>
				 <td><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="mainRoleStr" scope="page"/></td>
			 </tr>
			 <bean:define id="submitable" value="1"/>
	 	</jbilling:permission>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_TYPE%>'>
			 <tr class="form">
	  			 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.type"/></td>
				 <td><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="mainRoleStr" scope="page"/></td>
			 </tr>
	 	</jbilling:permission>

		<%-- The username is editable for some and for the rest is visable --%>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_USERNAME%>'>		
			 <tr class="form">
	  			 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.username"/></td>
				 <td ><html:text property="username" size="10" /></td>
			 </tr>
			 <bean:define id="submitable" value="1"/>
	 	</jbilling:permission>			 
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_USERNAME%>' negative="true">			 	
			 <tr class="form">
	  			 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.username"/></td>
				 <td ><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="userName" scope="page"/></td>
			 </tr>
	 	</jbilling:permission>			 	 		

		<%-- Password is editable for some, and for the rest only if it is their own --%>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_PASSWORD%>'>	
	 		<%-- Now check if I am changing my own password --%>
	 		<logic:equal name='<%=Constants.PAGE_USER_DTO%>' 
	 			                     property="userId"
	 			                     scope="page"
	 			                     value='<%=((Integer) session.getAttribute(Constants.SESSION_LOGGED_USER_ID)).toString() %>' >
				 <tr class="form">
	  			 	 <td></td>
					 <td class="form_prompt"><bean:message key="user.prompt.oldpassword"/></td>
					 <td ><html:password property="oldPassword" redisplay="false" size="10" /></td>
				 </tr>
			</logic:equal>			
			 <tr class="form">
	  			 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.password"/></td>
				 <td ><html:password property="password" redisplay="false" size="10" /></td>
			 </tr>
			 <tr class="form">
	  			 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.verifyPassword"/></td>
				 <td ><html:password property="verifyPassword" redisplay="false" size="10" /></td>
			 </tr>
			 <bean:define id="submitable" value="1"/>			 
	 	</jbilling:permission>			 	 		
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_PASSWORD%>' negative="true">		
	 		<%-- Now check if I am changing my own password --%>
	 		<logic:equal name='<%=Constants.PAGE_USER_DTO%>' 
	 			                     property="userId"
	 			                     scope="page"
	 			                     value='<%=((Integer) session.getAttribute(Constants.SESSION_LOGGED_USER_ID)).toString() %>' >
				 <tr class="form">
	  			 	 <td></td>
					 <td class="form_prompt"><bean:message key="user.prompt.oldpassword"/></td>
					 <td ><html:password property="oldPassword" redisplay="false" size="10" /></td>
				 </tr>
				 <tr class="form">
	  			 	 <td></td>
					 <td class="form_prompt"><bean:message key="user.prompt.password"/></td>
					 <td ><html:password property="password" redisplay="false" size="10" /></td>
				 </tr>
				 <tr class="form">
	  			 	 <td></td>
					 <td class="form_prompt"><bean:message key="user.prompt.verifyPassword"/></td>
					 <td ><html:password property="verifyPassword" redisplay="false" size="10" /></td>
				 </tr>
 				 <bean:define id="submitable" value="1"/>
	 	    </logic:equal>
	 	</jbilling:permission>			 	 			 		

		<%-- Status is editable for some, viewable for others, and nothing for the rest --%>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_STATUS%>'>		
	 		<jbilling:getOptions userStatus="true"/>
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.status"/></td>
				 <td >
				 	<html:select property="status">
						 	<html:options collection='<%=Constants.PAGE_USER_STATUS%>' 
				                    property="code"
				                    labelProperty="description"/>
					</html:select>
	 				<bean:define id="submitable" value="1"/>
				 </td>
			 </tr>
	 	</jbilling:permission>			 	 			 			 	
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_STATUS%>'>		
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.status"/></td>
				 <td ><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="statusStr" scope="page"/></td>
			 </tr>
	 	</jbilling:permission>			 	 			 			 	

		<%-- Subscriber Status is editable for some, viewable for others, and nothing for the rest --%>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_STATUS%>'>		
	 		<jbilling:getOptions subscriberStatus="true"/>
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.subscriber_status"/></td>
				 <td >
				 	<html:select property="subscriberStatus">
						 	<html:options collection='<%=Constants.PAGE_SUBSCRIBER_STATUS%>' 
				                    property="code"
				                    labelProperty="description"/>
					</html:select>
	 				<bean:define id="submitable" value="1"/>
				 </td>
			 </tr>
	 	</jbilling:permission>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_STATUS%>'>		
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.subscriber_status"/></td>
				 <td ><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="subscriptionStatusStr" scope="page"/></td>
			 </tr>
	 	</jbilling:permission>

		<%-- Language is editable for some, viewable for others, and nothing for the rest --%>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_LANGUAGE%>'>		
	 	 	 <jbilling:getOptions language="true"/>
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.language"/></td>
				 <td >
				 	<html:select property="language">
						 	<html:options collection='<%=Constants.PAGE_LANGUAGES%>' 
				                    property="code"
				                    labelProperty="description"/>
					 </html:select>
	 				 <bean:define id="submitable" value="1"/>
				 </td>
			 </tr>
	 	</jbilling:permission>			 	 			 			 	
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_LANGUAGE%>'>		
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_prompt"><bean:message key="user.prompt.language"/></td>
				 <td ><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="languageStr" scope="page"/></td>
			 </tr>
	 	</jbilling:permission>			
	 	 	 			 			 	
		<%-- Currency is editable for some, viewable for others, and nothing for the rest --%>
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_CHANGE_CURRENCY%>'>		
	 	 	 <jbilling:getOptions currencies="true"/>
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_prompt"><bean:message key="currency.external.prompt.name"/></td>
				 <td >
				 	<html:select property="currencyId">
						 	<html:options collection='<%=Constants.PAGE_CURRENCIES%>' 
				                    property="code"
				                    labelProperty="description"/>
					 </html:select>
	 				 <bean:define id="submitable" value="1"/>
				 </td>
			 </tr>
	 	</jbilling:permission>			 	 			 			 	
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_VIEW_CURRENCY%>'>		
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_prompt"><bean:message key="currency.external.prompt.name"/></td>
				 <td ><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="currencyName" scope="page"/></td>
			 </tr>
	 	</jbilling:permission>		
	 	
	 	<logic:present name='<%=Constants.PAGE_USER_DTO%>' property="customer">
	 	<logic:present name='<%=Constants.PAGE_USER_DTO%>' property="customer.parent">
	 	<tr class="form">
	  	 	 <td></td>
			 <td class="form_prompt"><bean:message key="user.prompt.parent"/></td>
			 <td><bean:write name='<%=Constants.PAGE_USER_DTO%>'  property="customer.parent.baseUser.id" scope="page"/></td>
		 </tr>
		 </logic:present>
	 	<tr class="form">
		 	 <td></td>
			 <td class="form_prompt"><bean:message key="user.prompt.isParent"/></td>
			 <td>
			 	<logic:equal name='<%=Constants.PAGE_USER_DTO%>'  
			 		property="customer.isParent" 
			 		scope="page" value="1">
			 		<bean:message key="all.prompt.yes"/>
			 	</logic:equal>
			 	<logic:equal name='<%=Constants.PAGE_USER_DTO%>'  
			 		property="customer.isParent" 
			 		scope="page" value="0">
			 		<bean:message key="all.prompt.no"/>
			 	</logic:equal>
			 </td>
		</tr>
	 	<tr class="form">
		 	 <td></td>
			 <td class="form_prompt"><bean:message key="user.prompt.invoiceChild"/></td>
			 <td>
		 	<logic:equal name='<%=Constants.PAGE_USER_DTO%>'  
			 		property="customer.invoiceChild" 
			 		scope="page" value="1">
			 		<bean:message key="all.prompt.yes"/>
		 	</logic:equal>
		 	<logic:equal name='<%=Constants.PAGE_USER_DTO%>'  
			 		property="customer.invoiceChild" 
			 		scope="page" value="0">
			 		<bean:message key="all.prompt.no"/>
		 	</logic:equal>
			 </td>
		</tr>
		</logic:present>
	 	
	 	<jbilling:permission permission='<%=Constants.P_USER_EDIT_LINKS%>'>
	 	<logic:equal name="userEdit" property="type" 
	 		value='<%=Constants.TYPE_CUSTOMER.toString()%>'>
	 		<jbilling:getOptions deliveryMethod="true"/>
	 		<tr class="form">
  			 	 <td>
  			 	 	<jbilling:help page="users" anchor="deliveryMethod">
					 <img border="0" src="/billing/graphics/help.gif"/>
 				     </jbilling:help>
  			 	 </td>
				 <td class="form_prompt"><bean:message key="user.prompt.deliveryMethod"/></td>
				 <td>
				 	<html:select property="deliveryMethodId">
						 	<html:options collection='<%=Constants.PAGE_DELIVERY_METHOD%>' 
				                    property="code"
				                    labelProperty="description"/>
					</html:select>
				 </td>
			</tr>

	 		<jbilling:getOptions generalPeriod="true"/>
	 		<tr class="form">
				<td>
					 <jbilling:help page="users" anchor="duedate">
						 <img border="0" src="/billing/graphics/help.gif"/>
					 </jbilling:help>
				</td>
		    	<td class="form_prompt"><bean:message key="user.prompt.dueDate"/></td>
		    	<td>
		        	<html:text property="due_date_value" size="2" maxlength="2"/>&nbsp
		        	<html:select property="due_date_unit_id">
			        	<html:options collection='<%=Constants.PAGE_GENERAL_PERIODS%>' 
				                  property="code"
				                  labelProperty="description"	/>
		        	</html:select>
 	        	</td>
	        </tr>
			<tr class="form">
				<td></td>
		    	<td class="form_prompt"><bean:message key="user.prompt.partner"/></td>
		    	<td>
		        	<html:text property="partnerId" size="5" />
 	        	</td>
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

            <jbilling:getOptions balanceType="true"/>
            <tr class="form">
				<td></td>
		    	<td class="form_prompt"><bean:message key="user.prompt.balanceType"/></td>
		    	<td>
                    <html:select property="balance_type">
						 	<html:options collection='<%=Constants.PAGE_BALANCE_TYPE%>'
				                    property="code"
				                    labelProperty="description"/>
					</html:select>
 	        	</td>
	        </tr>
            <tr class="form">
				<td></td>
		    	<td class="form_prompt"><bean:message key="user.prompt.creditLimit"/></td>
		    	<td>
		        	<html:text property="credit_limit" size="10" />
 	        	</td>
	        </tr>
             <tr class="form">
                 <td></td>
                 <td class="form_prompt"><bean:message key="user.prompt.autoRecharge"/></td>
                 <td>
                     <html:text property="auto_recharge" size="10"/>
                 </td>
             </tr>

		</logic:equal>
			<tr class="form">
  			 	<td></td>
				<td class="form_prompt"><bean:message key="user.prompt.excludeAging"/></td>
				<td>
				   <html:checkbox property="chbx_excludeAging"/>
				</td>
			</tr>
		</jbilling:permission>	
		
		<logic:present name="submitable" scope="page">
			 <tr class="form">
  			 	 <td></td>
				 <td class="form_button" colspan="2">
					  <html:submit styleClass="form_button">
					  	<bean:message key="all.prompt.submit"/>
					   </html:submit>
				</td>
			</tr>	 
		</logic:present>
	 	
	</table>	
</html:form>

