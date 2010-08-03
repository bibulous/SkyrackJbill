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
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<p align="center">
	<html:link page='<%= "/report/form.jsp?back=yes&"  + session.getAttribute(Constants.SESSION_REPORT_LINK) %>'>
		<bean:message key="report.link"/>
	</html:link>
</p>
<logic:present parameter="user_report_id">
	<p align="center">
		<bean:message key="report.user.delete.confirm" 
				arg0='<%= request.getParameter(Constants.REQUEST_USER_REPORT_ID) %>'/> <br/>
		<html:link page='<%= "/report/delete.jsp?confirm=yes&rid=" + request.getParameter(Constants.REQUEST_USER_REPORT_ID) %>' >
			<bean:message key="all.prompt.yes"/>
		</html:link> <br/>
		<html:link page="/report/delete.jsp?confirm=no">
			<bean:message key="all.prompt.no"/>
		</html:link> <br/>
	</p> 
</logic:present>

<logic:present parameter="confirm">
	<logic:equal parameter="confirm"
		         value="yes">
		<jbilling:reportDelete reportId='<%= Integer.valueOf(request.getParameter("rid")) %>'/>
    	<html:errors/>
    	<logic:messagesNotPresent>
    		<p><bean:message key="report.user.delete.done"/></p>
    	</logic:messagesNotPresent>
   </logic:equal>
   <logic:equal parameter="confirm"
		         value="no">
	   <p><bean:message key="report.user.delete.no"/></p>
   </logic:equal>
</logic:present>
