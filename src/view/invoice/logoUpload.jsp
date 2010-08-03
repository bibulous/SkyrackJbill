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

<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>

<p class="title"><bean:message key="invoice.logo.title" /></p>
<p class="instr">
   <bean:message key="invoice.logo.instr" />
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
	<p><bean:write name="myMessage" /></p>
</html:messages>

<p>
<jbilling:invoiceLogoExists id="logoExists" />
<c:choose>
  <c:when test="${logoExists}">
    <img src="logoImage.jsp" />
  </c:when>
  <c:otherwise>
    <bean:message key="invoice.logo.noFile" />
  </c:otherwise>
</c:choose>
</p>
<p>
<html:form action="/invoice/logoUpload" enctype="multipart/form-data">
    Select File: <html:file property="logoFile"/> <br/>
    <html:submit value="Upload File"/>
</html:form>
</p>
