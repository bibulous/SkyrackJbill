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

<%@ page language="java"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<logic:notPresent parameter="confirm">
   <p class="title">
	   <bean:message key="item.type.edit.title" />
   </p>
   <p class="instr">
	   <bean:message key="item.type.edit.instr" />
   </p>
   
   <logic:notPresent parameter="create">
   <html:link page="/item/maintainType.jsp?confirm=do">
   	  <bean:message key="all.prompt.delete" />
   	</html:link>
   </logic:notPresent>   	

</logic:notPresent>

<logic:present parameter="confirm">
	<logic:equal parameter="confirm" value="do">
		  <p>
		      <bean:message key="item.type.delete.confirm"/> <br/>
              <html:link page="/itemTypeMaintain.do?action=delete&mode=type">
              	   <bean:message key="all.prompt.yes"/>
              </html:link><br/>
              <html:link page="/item/maintainType.jsp?confirm=no">
              	   <bean:message key="all.prompt.no"/>
              </html:link><br/>
   	    </p>
	</logic:equal>
	
	<logic:equal parameter="confirm" value="no">
          <p><bean:message key="item.type.delete.notDone"/></p>
	</logic:equal>	

</logic:present>