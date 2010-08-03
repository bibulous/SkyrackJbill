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
   <bean:message key="all.prompt.help" />
   <jbilling:help page="partners" anchor="defaults">
		 <bean:message key="all.prompt.here" />
   </jbilling:help>
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<jbilling:getOptions generalPeriod="true"/>
<jbilling:getOptions currencies="true"/>

<html:form action="/partnerDefaults?action=edit&mode=partnerDefault" >
  <table class="form">
	  <tr class="form">
		  <td>
			<jbilling:help page="partners" anchor="parameter.rate">
		      <img border="0" src="/billing/graphics/help.gif"/>
		    </jbilling:help>
		  </td>
		  <td class="form_prompt"><bean:message key="partner.prompt.rate"/></td>
		  <td colspan="2"><html:text property="rate" /></td>
	  </tr>
	  <tr class="form">
		  <td>
			<jbilling:help page="partners" anchor="parameter.fee">
		      <img border="0" src="/billing/graphics/help.gif"/>
		    </jbilling:help>
		  </td>
		  <td class="form_prompt"><bean:message key="partner.prompt.fee"/></td>
		  <td colspan="2"><html:text property="fee" /></td>
	  </tr>
	  <tr class="form">
		  <td>
			<jbilling:help page="partners" anchor="parameter.currency">
		      <img border="0" src="/billing/graphics/help.gif"/>
		    </jbilling:help>
		  </td>
	      <td class="form_prompt"><bean:message key="partner.prompt.feeCurrency"/></td>
	 	  <td colspan="2">
	 		<html:select property="fee_currency">
				<html:options collection='<%=Constants.PAGE_CURRENCIES%>' 
							  property="code"
							  labelProperty="description"/>
			</html:select>
          </td>
	  </tr>
	 	<tr class="form">
		  <td>
			<jbilling:help page="partners" anchor="parameter.oneTime">
		      <img border="0" src="/billing/graphics/help.gif"/>
		    </jbilling:help>
		  </td>
	 		<td class="form_prompt"><bean:message key="partner.prompt.onetime"/></td>
	 		<td colspan="2"><html:checkbox property="chbx_one_time"/></td>
	 	</tr>
	 	<tr class="form">
		  <td>
			<jbilling:help page="partners" anchor="parameter.period">
		      <img border="0" src="/billing/graphics/help.gif"/>
		    </jbilling:help>
		  </td>
	 		<td class="form_prompt"><bean:message key="partner.prompt.period"/></td>
	 		<td><html:text property="period_value" size="2" /></td>
	 		<td>
	 			<html:select property="period_unit_id">
					<html:options collection='<%=Constants.PAGE_GENERAL_PERIODS%>' 
								  property="code"
								  labelProperty="description"/>
				</html:select>
            </td>
	 	</tr>
	 	<tr class="form">
		  <td>
			<jbilling:help page="partners" anchor="parameter.batch">
		      <img border="0" src="/billing/graphics/help.gif"/>
		    </jbilling:help>
		  </td>
	 		<td class="form_prompt"><bean:message key="partner.prompt.process"/></td>
	 		<td colspan="2"><html:checkbox property="chbx_process"/></td>
	 	</tr>
	 	<tr class="form">
		  <td>
			<jbilling:help page="partners" anchor="parameter.clerk">
		      <img border="0" src="/billing/graphics/help.gif"/>
		    </jbilling:help>
		  </td>
	 		<td class="form_prompt"><bean:message key="partner.prompt.clerk"/></td>
	 		<td colspan="2"><html:text property="clerk" size="5" /></td>
	 	</tr>
	  
	<tr class="form">
		<td></td>
		<td colspan="3" class="form_button">
			<html:submit styleClass="form_button">
                <bean:message key="all.prompt.submit"/>                
            </html:submit>
        </td>
	</tr>

  </table>
</html:form>
