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

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>


<%-- Centered Layout Tiles 
  This layout render a heade tile, body and footer.
  Created initially for the login screen

  @param header Header tile (jsp url or definition name)
  @param body Body or center tile
  @param footer Footer tile
--%>

<HTML>
  <HEAD>
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" type="text/css" 
  		href="<jbilling:getPreference preferenceId='<%=Constants.PREFERENCE_CSS_LOCATION%>'/>" /> 
  </HEAD>

<body>

<p align="center">
<table class="body">
<tr>
  <td valign="top">
    <tiles:insert attribute='body' />
  </td>
</tr>
<tr>
  <td colspan="3">
    <tiles:insert attribute="footer" />
  </td>
</tr>
</table>
</p>

</body>
</html>
