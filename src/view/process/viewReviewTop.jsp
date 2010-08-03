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

<p class="title"><bean:message key="process.review.title"/></p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage"/></p>
</html:messages>

<logic:present name='<%=Constants.SESSION_PROCESS_DTO%>' 
	           scope="session">

<p class="instr"><bean:message key="process.review.instr"/></p>
<p class="instr">
	 <bean:message key="all.prompt.help" />
	 <jbilling:help page="process" anchor="review">
			 <bean:message key="all.prompt.here" />
	 </jbilling:help>
</p>

<table class="info">
  <tr>
      <th class="info" colspan="2">
          <bean:message key="process.review.info"/>
	  </th>
  </tr>
  <tr class="infoA">
  	<td class="infoprompt">
	<bean:message key="process.configuration.prompt.status"/>
	</td>
	<td class="infodata">
	<logic:equal name='<%=Constants.SESSION_PROCESS_CONFIGURATION_DTO%>' 
				 property="reviewStatus"
				 scope="session"
				 value='<%=Constants.REVIEW_STATUS_GENERATED.toString()%>' >
		<bean:message key="process.approval.generated"/>
	</logic:equal>
	
	<logic:equal name='<%=Constants.SESSION_PROCESS_CONFIGURATION_DTO%>' 
				 property="reviewStatus"
				 scope="session"
				 value='<%=Constants.REVIEW_STATUS_APPROVED.toString()%>' >
		<bean:message key="process.approval.yes"/>
	</logic:equal>
	
	<logic:equal name='<%=Constants.SESSION_PROCESS_CONFIGURATION_DTO%>' 
				 property="reviewStatus"
				 scope="session"
				 value='<%=Constants.REVIEW_STATUS_DISAPPROVED.toString()%>' >
		<bean:message key="process.approval.no"/>
	</logic:equal>
	</td>
  </tr>	
  <tr class="infoB">
  	<td class="infoprompt">
  	<bean:message key="process.configuration.prompt.nextRunDate"/>
  	</td>
  	<td class="infodata">
	<bean:write name='<%=Constants.SESSION_PROCESS_CONFIGURATION_DTO%>' 
				 property="nextRunDate"
				 scope="session"
				 formatKey="format.date"/>
  	</td>
  </tr>
  
  <logic:equal name='<%=Constants.SESSION_USER_DTO%>'
						 property="mainRoleId"
						 scope="session"
						 value='<%=Constants.TYPE_INTERNAL.toString()%>'>	 	  
	<bean:define id="jsp_hasApproval" scope="page" value="y"/>
  </logic:equal>
  <logic:equal name='<%=Constants.SESSION_USER_DTO%>'
						 property="mainRoleId"
						 scope="session"
						 value='<%=Constants.TYPE_ROOT.toString()%>'>	 	  
	<bean:define id="jsp_hasApproval" scope="page" value="y"/>
  </logic:equal>

  <logic:present name="jsp_hasApproval" scope="page">
  <tr>
  	<td class="infocommands">
  	<html:link page="/invoice/listProcess.jsp?toApprove=yes">
  		<bean:message key="process.approval.approve"/>
	</html:link>
  	</td>
  	<td class="infocommands">
  	<html:link action="processMaintain?action=approval">
  		<bean:message key="process.approval.disapprove"/>
	</html:link>
  	</td>
  </tr>
  </logic:present>
</table>

</logic:present>

  
