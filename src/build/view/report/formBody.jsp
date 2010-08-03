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

<%@ page language="java" import="com.sapienter.jbilling.client.util.Constants,com.sapienter.jbilling.server.report.Field,com.sapienter.jbilling.server.report.ReportDTOEx"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="jbilling" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>


<%-- get the report dto with a call to the report session on the server 
     It will pickup which report to load from the request parameters--%>		
<jbilling:reportSetUp/>

<p class="title">
<bean:message name='<%=Constants.SESSION_REPORT_DTO%>'
	                              property="titleKey"
	                              scope="session"/> 
</p>

<p class="instr">
<bean:message name='<%=Constants.SESSION_REPORT_DTO%>'
	                              property="instructionsKey"
	                              scope="session"/> 
</p>

<html:form action="/reportSubmit">
	   
	<p align="center">                 
	<html:submit styleClass="form_button"><bean:message key="reports.submit.prompt"/></html:submit>
	<html:reset styleClass="form_button"><bean:message key="reports.reset.prompt"/></html:reset>
	</p>

	<html:errors/>
	<html:messages message="true" id="myMessage">
	    <p><bean:write name="myMessage"/></p>
    </html:messages>
	

	<p align="center">
		<bean:message key="report.save.description"/>
		<html:text property="saveName"/>
	 	<html:submit styleClass="form_button" property="saveFlag">
	 		<bean:message key="report.save.prompt"/>
	 	</html:submit>
	</p>
	<%-- Can the user pick which columns will be displayed ? --%>
	<logic:equal name='<%=Constants.SESSION_REPORT_DTO%>'
							 property="selectable"
							 scope="session"
							 value="true">
	
		<table class="list">
			<tr class="listH">
				<td colspan="2">
					<jbilling:help page="reports" anchor="select">
						 <img border="0" src="/billing/graphics/help.gif"/>
					</jbilling:help>
					
					<bean:message key="reports.select.prompt"/>
					<br/>	                              
					<bean:message key="reports.select.instr"/>
				</td>
			</tr>
			<logic:iterate  name='<%=Constants.SESSION_REPORT_DTO%>'
							property="fields"
							scope="session"
							id="field"
							indexId="i">
			
				<logic:equal name="field"
				             property="selectable"
				             value="1">
						<c:choose>
							<c:when test="${flag == 1}">
								<tr class="listB">
								<c:remove var="flag"/>
							</c:when>
							<c:otherwise>
								<tr class="listA">
								<c:set var="flag" value="1"/>
							</c:otherwise>
						</c:choose>
						<td>
					         <html:checkbox property='<%= "select[" + i + "]"%>'>
					             <bean:message name="field" property="titleKey"/> 
					         </html:checkbox>
					   </td>
					   <td>
					   	
				        <logic:equal name="field"
				                      property="functionable"
				                      value="1">
				            <html:select property='<%= "function[" + i + "]"%>'>
				                <html:option key="report.function.prompt.none" 
				                    value="none"/>
				                    
				                <logic:notEqual name="field"
				                	               property="dataType"
				                	               value='<%=Field.TYPE_STRING%>'>
				                <logic:notEqual name="field"
				                	               property="dataType"
				                	               value='<%=Field.TYPE_DATE%>'>
									<html:option key="report.function.prompt.sum" 
										value='<%=Field.FUNCTION_SUM%>'/>
									<html:option key="report.function.prompt.avg" 
										value='<%=Field.FUNCTION_AVG%>'/>
				                 </logic:notEqual>
				                 </logic:notEqual>
				                 				                 
				                <logic:notEqual name="field"
				                	               property="dataType"
				                	               value='<%=Field.TYPE_STRING%>'>
									<html:option key="report.function.prompt.min" 
										value='<%=Field.FUNCTION_MIN%>'/>
									<html:option key="report.function.prompt.max" 
										value='<%=Field.FUNCTION_MAX%>'/>
				                </logic:notEqual>
				                
				                <html:option key="report.function.prompt.group" 
				                    value="grouped"/>
				            </html:select>
				        </logic:equal>
					</td>
					</tr>
				</logic:equal>
			</logic:iterate>	
		
		</table>

	</logic:equal>

	<%-- Can the user pick values for some fields? --%>
	<logic:equal name='<%=Constants.SESSION_REPORT_DTO%>'
							 property="wherable"
							 value="true">
	
		<table class="list">
			<tr class="listH">
				<td colspan="3">
					<jbilling:help page="reports" anchor="filter">
						 <img border="0" src="/billing/graphics/help.gif"/>
					</jbilling:help>
					
					<bean:message key="reports.where.prompt"/>
					<br/>	                              
					<bean:message key="reports.where.instr"/>
				</td>
			</tr>
			<logic:iterate  name='<%=Constants.SESSION_REPORT_DTO%>'
							property="fields"
							scope="session"
							id="field"
							indexId="i">
			
				<logic:equal name="field"
				             property="whereable"
				             value="1">
						<c:choose>
							<c:when test="${flag == 1}">
								<tr class="listB">
								<c:remove var="flag"/>
							</c:when>
							<c:otherwise>
								<tr class="listA">
								<c:set var="flag" value="1"/>
							</c:otherwise>
						</c:choose>
						<td><bean:message name="field" property="titleKey"/> </td>
						<td>
				        <logic:equal name="field"
				                      property="operatorable"
				                      value="1">
				            <html:select property='<%= "operator[" + i + "]"%>'>
				                <html:option key="reports.operator.prompt.equal" 
				                    value='<%=Field.OPERATOR_EQUAL%>'/>
				                <html:option key="reports.operator.prompt.notequal" 
				                    value='<%=Field.OPERATOR_DIFFERENT%>'/>
				                <html:option key="reports.operator.prompt.greater" 
				                    value='<%=Field.OPERATOR_GREATER%>'/>
				                <html:option key="reports.operator.prompt.smaller" 
				                    value='<%=Field.OPERATOR_SMALLER%>'/>
				                <html:option key="reports.operator.prompt.eq_gr" 
				                    value='<%=Field.OPERATOR_GR_EQ%>'/>
				                <html:option key="reports.operator.prompt.eq_sm"
				                    value='<%=Field.OPERATOR_SM_EQ%>'/>
				            </html:select>
				        </logic:equal>
				        <logic:notEqual name="field"
				                      property="operatorable"
				                      value="1">
				            <bean:message name="field" property="operatorKey"/>
				        </logic:notEqual>
						</td>
						<logic:equal name="field"
							         property="dataType"
							         value='<%=Field.TYPE_DATE%>'>
						     <td>
						           <jbilling:dateFormat format="mm-dd">
										<html:text property='<%= "month[" + i + "]"%>' size="2" maxlength="2"/>/
										<html:text property='<%= "day[" + i + "]"%>' size="2" maxlength="2"/>/
								   </jbilling:dateFormat>
						           <jbilling:dateFormat format="dd-mm">
										<html:text property='<%= "day[" + i + "]"%>' size="2" maxlength="2"/>/
										<html:text property='<%= "month[" + i + "]"%>' size="2" maxlength="2"/>/
								   </jbilling:dateFormat>
					     	   	   <html:text property='<%= "year[" + i + "]"%>' size="4" maxlength="4"/>
					     	   	   <bean:message key="all.prompt.dateFormat"/>
						     </td>
						</logic:equal>
					    <logic:notEqual name="field"
							            property="dataType"
							            value='<%=Field.TYPE_DATE%>'>
						
					    	<td><html:text property='<%= "where[" + i + "]"%>'/></td>
					    </logic:notEqual>
					</tr>
				</logic:equal>
			</logic:iterate>	
		
		</table>
	
	</logic:equal>
	
	<%-- Can the user choose the order ? --%>
	<logic:equal name='<%=Constants.SESSION_REPORT_DTO%>'
							 property="ordenable"
							 value="true">
	
		<table class="list">
			<tr class="listH">
				<td colspan="2">
					<jbilling:help page="reports" anchor="sort">
						 <img border="0" src="/billing/graphics/help.gif"/>
					</jbilling:help>
					
					<bean:message key="reports.orderby.prompt"/>
					<br/>	                              
					<bean:message key="reports.orderby.instr"/>
				</td>
			</tr>
			<logic:iterate  name='<%=Constants.SESSION_REPORT_DTO%>'
							property="fields"
							scope="session"
							id="field"
							indexId="i">
			
				<logic:equal name="field"
				             property="ordenable"
				             value="1">
						<c:choose>
							<c:when test="${flag == 1}">
								<tr class="listB">
								<c:remove var="flag"/>
							</c:when>
							<c:otherwise>
								<tr class="listA">
								<c:set var="flag" value="1"/>
							</c:otherwise>
						</c:choose>
						<td><bean:message name="field" property="titleKey"/> </td>
						<td>
							 <html:select property='<%= "orderBy[" + i + "]"%>'>
							 	<html:option key="report.orderby.prompt.none" value="0" />
							 	<%-- Scriptlet :( , can't find any other way to do this --%>
							 	<%
							 	for (int f=1; f <= ((ReportDTOEx)session.getAttribute(
							 	         Constants.SESSION_REPORT_DTO)).getOrdenableFields().intValue(); f++) {
							 	%>
							 	<html:option value='<%=String.valueOf(f) %>'> <%= f %> </html:option>
							 	<% } %>
						 	 </html:select>
						</td>
					</tr>
				</logic:equal>
			</logic:iterate>	
		
		</table>
		
	</logic:equal>

	<p align="center">                 
	<html:submit styleClass="form_button"><bean:message key="reports.submit.prompt"/></html:submit>
	<html:reset styleClass="form_button"><bean:message key="reports.reset.prompt"/></html:reset>
	</p>

</html:form>
