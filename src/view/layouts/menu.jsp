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

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.Iterator" %>


<%-- Menu Layout
  This layout render a menu with links.
  It takes as parameter the title, and a list of items. Each item is a bean with following properties :
  value, href, icon, tooltip.
  @param title Menu title
  @param items list of items. Items are beans whith following properties : 
 --%>



<%-- Push tiles attributes in page context --%>
<tiles:importAttribute />

<table>
<logic:present name="title">
<tr>
  <th colspan=2>
    <div align="left"><strong><tiles:getAsString name="title"/></strong></div>
  </th>
</tr>
</logic:present>

<%-- iterate on items list --%>
<logic:iterate id="item" name="items" type="org.apache.struts.tiles.beans.MenuItem" >

<%  // Add site url if link start with "/"
  String link = item.getLink();
	if(link.startsWith("/") ) link = request.getContextPath() + link;
%>
<tr>
  <td width="10" valign="top" ></td>
  <td valign="top"  >
	  <font size="-1"><a href="<%=link%>">
<logic:notPresent name="item" property="icon"><%=item.getValue()%></logic:notPresent>
<logic:present name="item" property="icon">
	<%  // Add site url if link start with "/"
	  String icon = item.getIcon();
		if(icon.startsWith("/") ) icon = request.getContextPath() + icon;
	%>
<img src='<%=request.getContextPath()%><bean:write name="item" property="icon" scope="page"/>'
       alt='<bean:write name="item" property="tooltip" scope="page" ignore="true"/>' /></logic:present></a>
	  </font>
  </td>
</tr>
</logic:iterate>
</table>
