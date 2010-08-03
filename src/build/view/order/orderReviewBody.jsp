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
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c" %>


<html:errors/>
<html:form action="/reviewOrder" >
	
	<html:hidden property="action" value="process"></html:hidden>
	<table class="list">
		<tr class="listH">
			<td><bean:message key="item.prompt.description"/></td>
			<td><bean:message key="item.list.quantity"/></td>
			<td><bean:message key="item.prompt.price"/></td>
			<td><bean:message key="order.review.column.total"/></td>
	  	</tr>
	  	
		<logic:iterate id="thisLine" name="orderDTOForm" property="orderLines">
			<bean:define id="itemId" name="thisLine" property="key"/>
			<logic:equal name="thisLine" property="value.editable" value="true">
			<c:choose>
				<c:when test="${colorFlag == 1}">
					<tr class="listB">
					<c:remove var="colorFlag"/>
				</c:when>
				<c:otherwise>
					<tr class="listA">
					<c:set var="colorFlag" value="1"/>
				</c:otherwise>
			</c:choose>
				<td>
				<html:text property='<%= "orderLine(" + itemId + ").description" %>' size="40"/>	
				</td>
				<td>
				<logic:notPresent name="thisLine" 
				              property="value.item.percentage">
				    <html:text property='<%= "orderLine(" + itemId + ").quantity" %>' size="6"/>
                </logic:notPresent>				
				</td>
				<td>
				<logic:equal name="thisLine" 
				              property="value.item.priceManual"
				              value="1">
				    <html:text property='<%= "orderLine(" + itemId + ").priceStr" %>' size="8"/>
				</logic:equal>
				<logic:notEqual name="thisLine" 
				              property="value.item.priceManual"
				              value="1">
				    <logic:notPresent name="thisLine" 
				                 property="value.item.percentage">
				        <bean:write name="thisLine" property="value.price" 
				                    formatKey="format.money"/>
				    </logic:notPresent>
				    <logic:present name="thisLine" 
				                 property="value.item.percentage">
				        <bean:write name="thisLine" property="value.price" 
				                    formatKey="format.percentage"/>
				    </logic:present>

				</logic:notEqual>
				</td>
				<td>
				<bean:write name="thisLine" property="value.amount" formatKey="format.money"/>
				</td>	
			</tr>
			</logic:equal>
		</logic:iterate>
		
		<logic:iterate id="thisLine" name="orderDTOForm" property="orderLines">
			<logic:notEqual name="thisLine" property="value.editable" value="true">
			<c:choose>
				<c:when test="${colorFlag == 1}">
					<tr class="listB">
					<c:remove var="colorFlag"/>
				</c:when>
				<c:otherwise>
					<tr class="listA">
					<c:set var="colorFlag" value="1"/>
				</c:otherwise>
			</c:choose>
				<td>
				<bean:write name="thisLine" property="value.description"/>	
				</td>
				<td>
				<bean:write name="thisLine" property="value.quantity" formatKey="format.double"/>
				</td>
				<td>
				<bean:write name="thisLine" property="value.price" formatKey="format.money"/>
				</td>
				<td>
				<bean:write name="thisLine" property="value.amount" formatKey="format.money"/>
				</td>	
			</tr>
			</logic:notEqual>
		</logic:iterate>
	
		<br/>
	  	
		<tr class="listH">
			<td colspan="2" align="right"><bean:message key="order.summary.total"/></td>
			<td colspan="2" align="right">
				<bean:write name='<%=Constants.SESSION_ORDER_SUMMARY%>' 
				            property="currencySymbol" 
				            scope="session"
				            filter="false"/>
				<bean:write name='<%=Constants.SESSION_ORDER_SUMMARY%>' 
				            property="total" 
				            scope="session"
				            formatKey="format.money"/>
	        </td>
		</tr>
		<tr>
			<td align="center">
				<html:submit styleClass="form_button" property="recalc" ><bean:message key="order.review.recalc"/></html:submit>
			</td>
			<td align="center">
				<html:submit styleClass="form_button" property="commit"><bean:message key="order.review.commit"/></html:submit>
			</td>
			
		</tr>	  	
	</table>	
</html:form>
