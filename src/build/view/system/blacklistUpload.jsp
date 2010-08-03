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

<p class="title"><bean:message key="system.blacklist.title" /></p>
<p class="instr">
    <bean:message key="system.blacklist.instr" />
</p>

<html:errors/>
<html:messages message="true" id="myMessage">
    <p><bean:write name="myMessage" /></p>
</html:messages>

<p>
<html:form action="/system/blacklistUpload" enctype="multipart/form-data">
    <table class="form"><tbody>
        <tr class="form">
            <td class="form_prompt">
                <bean:message key="system.blacklist.add_records" />
            </td>
            <td>
                <html:radio property="addOrReplace" value="add" />
            </td>
        </tr>
        <tr class="form">
            <td class="form_prompt">
                <bean:message key="system.blacklist.replace_records" />
            </td>
            <td>
                <html:radio property="addOrReplace" value="replace" />
            </td>
        </tr>
        <tr class="form">
            <td class="form_prompt">
                <bean:message key="system.blacklist.select_file" />
            </td>
            <td>
                <html:file property="csvFile" />
            </td>
        </tr>
        <tr class="form">
            <td colspan="2" class="form_button">
                <html:submit styleClass="form_button">
                    <bean:message key="all.prompt.submit" />
                </html:submit>
            </td>
        </tr>
     </tbody></table>
</html:form>
</p>
