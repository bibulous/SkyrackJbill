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

<logic:notPresent parameter="confirm">
   <p class="title">
	   <bean:message key="ach.edit.title" />
   </p>
   <p class="instr">
	   <bean:message key="ach.edit.instr" />
   </p>
   
   <html:link page="/user/achEdit.jsp?confirm=do">
   	  <bean:message key="all.prompt.delete" />
   </html:link>
</logic:notPresent>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<logic:present parameter="confirm">
	<logic:equal parameter="confirm" value="do">
		  <p>
		      <bean:message key="ach.delete.confirm"/> <br/>
              <html:link page="/achMaintain.do?action=delete&mode=ach">
              	   <bean:message key="all.prompt.yes"/>
              </html:link><br/>
              <html:link page="/user/achEdit.jsp?confirm=no">
              	   <bean:message key="all.prompt.no"/>
              </html:link><br/>
   	    </p>
	</logic:equal>
	
	<logic:equal parameter="confirm" value="no">
          <bean:message key="ach.delete.notDone"/>
	</logic:equal>	
</logic:present>

<html:form action="/achMaintain?action=edit&mode=ach" >
     <table class="form">
      	  <tr class="form">
	 		  <td class="form_prompt"><bean:message key="ach.aba.prompt"/></td>
	 		  <td colspan="4"><html:text property="aba_code" size="9" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td class="form_prompt"><bean:message key="ach.account_number.prompt"/></td>
	 		  <td colspan="4"><html:text property="account_number" size="20" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td class="form_prompt"><bean:message key="ach.bank_name.prompt"/></td>
	 		  <td colspan="4"><html:text property="bank_name" size="30" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td class="form_prompt"><bean:message key="ach.account_name.prompt"/></td>
	 		  <td colspan="4"><html:text property="account_name" size="30" /></td>
	 	  </tr>
      	  <tr class="form">
	 		  <td class="form_prompt"><bean:message key="ach.account_type.prompt"/></td>
	 		  <td><bean:message key="ach.account_type.chq.prompt"/></td>
	 		  <td><html:radio property="account_type" value="1"/></td>
	 		  <td><bean:message key="ach.account_type.sav.prompt"/></td>
	 		  <td><html:radio property="account_type" value="2"/></td>
	 	  </tr>
	 	  
      	  <tr class="form">
	 		  <td class="form_prompt"><bean:message key="creditcard.usethis.prompt"/></td>
	 		  <td colspan="4"><html:checkbox property="chbx_use_this"/></td>
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