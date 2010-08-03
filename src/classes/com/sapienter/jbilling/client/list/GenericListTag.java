/*
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
*/

package com.sapienter.jbilling.client.list;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.list.IListSessionBean;
import com.sapienter.jbilling.server.list.ListDTO;
import com.sapienter.jbilling.server.list.PagedListDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.process.BillingProcessDTOEx;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.util.Context;
import java.util.ArrayList;

/**
 * Prepares the result set so the InsertDataRowTag can then render
 * the data in a table.
 * Requires the list type in the request. It then creates a map with
 * the parameters for this type and makes a generic call to a session
 * bean that will return the CachedRowSet.
 * 
 * @author emilc
 *
 * @jsp:tag name="genericList"
 * 			body-content="JSP"
 */
public class GenericListTag extends ListTagBase {
    
    // these are parameters used for the setup
    private Boolean setup = new Boolean(false);
    private String type = null;
    private String method = null;
    
    public int doStartTag() throws JspException {
        int retValue = SKIP_BODY;

        log = Logger.getLogger(GenericListTag.class);
        // The session is always useful
        HttpSession session = pageContext.getSession();
        
        if (method == null) {
            method = (String) pageContext.getAttribute(
                    Constants.REQUEST_LIST_METHOD, 
                    PageContext.REQUEST_SCOPE);
        }
        // save the query method
        if (method == null || method.length() == 0 ||
                method.equalsIgnoreCase("jdbc")) {
            //default is jdbc
            super.queryMethod = METHOD_JDBC;
            method = "jdbc";
        } else if (method.equalsIgnoreCase("ejb")) {
            super.queryMethod = METHOD_EJB;
        } else {
            throw new JspException("method attribute has to be jdbc or ejb");
        }
  
        // Now I'll call the session bean to get the CachedRowSet with
        // the results of the query
        IListSessionBean listSession;
        try {
            listSession = (IListSessionBean) Context.getBean(
                    Context.Name.LIST_SESSION);
        } catch (Exception e) {
            throw new JspException(e);
        }
        
        Integer entityID = (Integer) session.getAttribute(
                Constants.SESSION_ENTITY_ID_KEY);
        Integer loggedUserId = (Integer) session.getAttribute(
                Constants.SESSION_LOGGED_USER_ID);

        
        // setup is to be called from the top jsp.
        // it defines the columns and makes the type available in the session
        if (setup.booleanValue()) {
            boolean isPaged = false;
            if (type.equals(Constants.LIST_TYPE_CUSTOMER_SIMPLE) ||
                    type.equals(Constants.LIST_TYPE_INVOICE_GRAL) ||
                    type.equals(Constants.LIST_TYPE_ORDER) ||
                    type.equals(Constants.LIST_TYPE_PAYMENT)) {
                isPaged = true;
            }
            List<String> listColumns = new ArrayList<String>();
            // *** EDIT HERE *** to add another list
            if (type.equals(Constants.LIST_TYPE_ITEM_TYPE)) {
                listColumns.add("item.type.prompt.id");
                listColumns.add("item.type.prompt.description");
            } else if (type.equals(Constants.LIST_TYPE_ITEM_ORDER)) {
                // this is an special list, because it has its own
                // jsp, therefore the columns are in /order/itemListBody.jsp                
            } else if (type.equals(Constants.LIST_TYPE_ITEM)) {
                listColumns.add("item.prompt.id");
                listColumns.add("item.prompt.internalNumber");
                listColumns.add("item.prompt.description");
            } else if (type.equals(Constants.LIST_TYPE_ITEM_USER_PRICE)) {
                listColumns.add("item.prompt.id");
                listColumns.add("item.prompt.internalNumber");
                listColumns.add("item.prompt.description");
                listColumns.add("item.prompt.price");
            } else if (type.equals(Constants.LIST_TYPE_PROMOTION)) { 
                listColumns.add("promotion.prompt.code");
                listColumns.add("promotion.prompt.since");
                listColumns.add("promotion.prompt.until");
                listColumns.add("promotion.prompt.once");                               
                listColumns.add("item.prompt.description");
            } else if (type.equals(Constants.LIST_TYPE_PAYMENT) ||
                    type.equals(Constants.LIST_TYPE_PAYMENT_USER) ||
                    type.equals(Constants.LIST_TYPE_REFUND)) {
                listColumns.add("payment.id");
                listColumns.add("user.prompt.username");
                listColumns.add("contact.list.organization");
                listColumns.add("blank");
                listColumns.add("payment.amount");
                listColumns.add("payment.date");
                listColumns.add("payment.method");
                listColumns.add("payment.result");
            } else if (type.equals(Constants.LIST_TYPE_CUSTOMER) ||
                    type.equals(Constants.LIST_TYPE_CUSTOMER_SIMPLE) ||
                    type.equals(Constants.LIST_TYPE_PARTNERS_CUSTOMER) ||
                    type.equals(Constants.LIST_TYPE_SUB_ACCOUNTS)) {
                listColumns.add("user.prompt.id");
                listColumns.add("contact.list.organization");
                listColumns.add("customer.last_name");
                listColumns.add("customer.first_name");
                listColumns.add("user.prompt.username");
            } else if (type.equals(Constants.LIST_TYPE_ORDER)) {
                listColumns.add("order.prompt.id");
                listColumns.add("user.prompt.username");
                listColumns.add("contact.list.organization");
                listColumns.add("order.prompt.createDate");
            } else if (type.equals(Constants.LIST_TYPE_INVOICE)) {
                listColumns.add("invoice.number");
                listColumns.add("invoice.id.prompt");
                listColumns.add("invoice.create_date");
                listColumns.add("invoice.due_date");
                listColumns.add("blank");
                listColumns.add("invoice.total");
                listColumns.add("invoice.balance");
            } else if (type.equals(Constants.LIST_TYPE_INVOICE_GRAL) ||
                    type.equals(Constants.LIST_TYPE_INVOICE_ORDER)) {
                listColumns.add("invoice.number");
                listColumns.add("user.prompt.username");
                listColumns.add("contact.list.organization");
                listColumns.add("invoice.id.prompt");
                listColumns.add("invoice.create_date");
                listColumns.add("blank");
                listColumns.add("invoice.total");
                listColumns.add("invoice.balance");
            } else if (type.equals(Constants.LIST_TYPE_PROCESS)) {
                listColumns.add("process.prompt.id");
                listColumns.add("process.prompt.date");
            } else if (type.equals(Constants.LIST_TYPE_PROCESS_INVOICES)) {
                listColumns.add("invoice.number");
                listColumns.add("invoice.id.prompt");
                listColumns.add("user.prompt.username");
                listColumns.add("contact.list.organization");
                listColumns.add("invoice.due_date");
                listColumns.add("blank");
                listColumns.add("invoice.total");
                listColumns.add("invoice.is_payable");
            } else if (type.equals(Constants.LIST_TYPE_PROCESS_ORDERS)) {
                listColumns.add("order.prompt.id");
                listColumns.add("user.prompt.username");
                listColumns.add("order.prompt.createDate");
            } else if (type.equals(Constants.LIST_TYPE_NOTIFICATION_TYPE)) {
                listColumns.add("notification.prompt.type");
            } else if (type.equals(Constants.LIST_TYPE_PARTNER)) {
                listColumns.add("partner.prompt.id");
                listColumns.add("user.prompt.username");
                listColumns.add("partner.prompt.nextPayout");
                listColumns.add("partner.prompt.duePayout");
            } else if (type.equals(Constants.LIST_TYPE_PAYOUT)) {
                listColumns.add("payout.prompt.id");
                listColumns.add("payout.prompt.startDate");
                listColumns.add("payout.prompt.endDate");
                listColumns.add("payout.prompt.paid");
            } else {
                log.error("list type " + type + " is not supported");
                throw new JspException("list type " + type +
                        " is not supported");
            }
            // leave info in the request
            pageContext.setAttribute(Constants.REQUEST_LIST_COLUMNS, 
                    listColumns, PageContext.SESSION_SCOPE);
            pageContext.setAttribute(Constants.REQUEST_LIST_TYPE, 
                    type, PageContext.REQUEST_SCOPE);
            pageContext.setAttribute(Constants.REQUEST_LIST_METHOD, 
                    method, PageContext.REQUEST_SCOPE);
            pageContext.setAttribute(Constants.REQUEST_LIST_IS_PAGED, 
                    new Boolean(isPaged), PageContext.REQUEST_SCOPE);
            
            // paged lists need some server information
            if (isPaged) {
                try {
                    PagedListDTO dto = listSession.getPagedListDTO(null, 
                            type, entityID, loggedUserId);
                    PagedList list = new PagedList(dto);
                    // the first page start from nothing
                    list.getPageFrom().add(null);
                    list.setLegacyName(type);
                    list.setDoSearch(null);
                    session.setAttribute(Constants.SESSION_PAGED_LIST, 
                            list);
                } catch (Exception e) {
                    throw new JspException(e);
                }
            } else {
                session.removeAttribute(Constants.SESSION_PAGED_LIST);
            }
            
            return retValue;
        }
        
        ActionErrors errors = new ActionErrors();
        Hashtable<String, Integer> parameters = new Hashtable<String, Integer>();
        
        try {
            // I need the type no matter what
            type = (String) pageContext.getAttribute(
                    Constants.REQUEST_LIST_TYPE, PageContext.REQUEST_SCOPE);

            if (type == null || type.length() == 0) {
                throw new JspException("The list type is required in the" +
                        " session.");
            }
            
            
            log.debug("List requested:" + type);    
            // may be this list has run recently ...
            if (queryMethod == METHOD_JDBC) {
                queryResults = (CachedRowSet) session.getAttribute(
                        Constants.SESSION_LIST_KEY + type);
            } else {
                queryDtoResults = (ListDTO) session.getAttribute(
                        Constants.SESSION_LIST_KEY + type);
            }

            PagedList pagedDto = (PagedList) session.getAttribute(
                    Constants.SESSION_PAGED_LIST);
            // invalidate any cashing for paged list
            if (pagedDto != null) {
                queryResults = null;
            }

            if (queryResults == null && queryDtoResults == null) {
                log.debug("Running list " + type);
                // some parameters are in common
                Integer userId = (Integer) session.getAttribute(
                        Constants.SESSION_USER_ID);
                Integer languageId = (Integer) session.getAttribute(
                        Constants.SESSION_LANGUAGE);        
                Integer userType = ((UserDTOEx) session.getAttribute(
                        Constants.SESSION_USER_DTO)).getMainRoleId();
                
                
                // *** EDIT HERE *** to add another list        
                if (type.equals(Constants.LIST_TYPE_CUSTOMER) ||
                        type.equals(Constants.LIST_TYPE_CUSTOMER_SIMPLE)) {
                    parameters.put("userType", userType);
                    parameters.put("entityId", entityID);
                    parameters.put("userId", loggedUserId);
                } else if (type.equals(Constants.LIST_TYPE_SUB_ACCOUNTS)) {
                    parameters.put("userId", userId);
                } else if (type.equals(Constants.LIST_TYPE_PARTNERS_CUSTOMER)) {
                    Partner partner = (Partner) 
                            session.getAttribute(
                                Constants.SESSION_PARTNER_DTO);
                    parameters.put("partnerId", partner.getId());
                } else if (type.equals(Constants.LIST_TYPE_ITEM_TYPE)) {
                    parameters.put("entityId", entityID);
                    parameters.put("languageId", languageId);
                } else if (type.equals(Constants.LIST_TYPE_ITEM)) {
                    parameters.put("entityId", entityID);
                    parameters.put("languageId", languageId);
                } else if (type.equals(Constants.LIST_TYPE_ITEM_ORDER)) {
                    parameters.put("entityId", entityID);
                    parameters.put("languageId", languageId);
                    parameters.put("userId", userId);
                } else if (type.equals(Constants.LIST_TYPE_ITEM_USER_PRICE)) {
                    parameters.put("entityId", entityID);
                    // I need the id of the user selected, not the one logged
                    parameters.put("userId", userId);
                    // there's the description of the item being shown
                    parameters.put("languageId", languageId); 
                } else if (type.equals(Constants.LIST_TYPE_PROMOTION)) {
                    parameters.put("entityId", entityID);
                    // for the description of the related item
                    parameters.put("languageId", languageId);
                } else if (type.equals(Constants.LIST_TYPE_PAYMENT) ||
                        type.equals(Constants.LIST_TYPE_REFUND)) {
                    parameters.put("entityId", entityID);
                    parameters.put("userType", userType);
                    parameters.put("languageId", languageId);
                    parameters.put("userId", loggedUserId); // just in case it's a partner
                } else if (type.equals(Constants.LIST_TYPE_ORDER)) {
                    parameters.put("entityId", entityID);
                    parameters.put("userType", userType);                           
                    parameters.put("userId", loggedUserId); // for customers/partners             
                } else if (type.equals(Constants.LIST_TYPE_INVOICE)) {
                    // I need the id of the user selected, not the one logged
                    parameters.put("userId", userId);
                } else if (type.equals(Constants.LIST_TYPE_INVOICE_ORDER)) {
                    // I need the id of the user of the order selected
                    OrderDTO order = (OrderDTO) session.getAttribute(
                            Constants.SESSION_ORDER_DTO);
                    if (order == null) {
                        throw new SessionInternalError("an order dto has to be " +
                                "present to list invoices");
                    }
                    parameters.put("orderId", order.getId());
                } else if (type.equals(Constants.LIST_TYPE_PAYMENT_USER)) {
                    // I need the id of the user selected, not the one logged
                    parameters.put("userId", userId);
                    parameters.put("languageId", languageId);
                } else if (type.equals(Constants.LIST_TYPE_INVOICE_GRAL)) {
                    parameters.put("entityId", entityID);
                    parameters.put("userType", userType);                           
                    parameters.put("userId", loggedUserId); // for customers/partners             
                } else if (type.equals(Constants.LIST_TYPE_PROCESS)) {
                    parameters.put("entityId", entityID);
                } else if (type.equals(Constants.LIST_TYPE_PROCESS_INVOICES) ||
                        type.equals(Constants.LIST_TYPE_PROCESS_ORDERS)) {
                    BillingProcessDTOEx dto = (BillingProcessDTOEx) session.
                            getAttribute(Constants.SESSION_PROCESS_DTO);
                    parameters.put("processId", dto.getId());
                } else if (type.equals(Constants.LIST_TYPE_NOTIFICATION_TYPE)) {
                    parameters.put("languageId", languageId);
                } else if (type.equals(Constants.LIST_TYPE_PARTNER)) {
                    parameters.put("entityId", entityID);
                } else if (type.equals(Constants.LIST_TYPE_PAYOUT)) {
                    parameters.put("partnerId", ((Partner) 
                            session.getAttribute(
                                Constants.SESSION_PARTNER_DTO)).getId());
                } else {
                    log.error("list type " + type + " is not supported");
                    throw new JspException("list type " + type +
                            " is not supported");
                }
                
                // make the server call
                // for paged lists, this is the initial page
                if (pagedDto != null && pagedDto.getDoSearch() == null) {
                    int currentPage = pagedDto.getCurrentPage().intValue();
                    Integer start = null, end = null;
                    if (pagedDto.getPageFrom().size() > currentPage) {
                        // we have a start
                        start = (Integer) pagedDto.getPageFrom().get(currentPage);
                        if (pagedDto.getPageFrom().size() > currentPage + 1) {
                            // we have an end
                            end = (Integer) pagedDto.getPageFrom().get(
                                    currentPage + 1);
                        }
                    }
                    queryResults = listSession.getPage(start, end, 
                            pagedDto.getPageSize(), pagedDto.getListId(), 
                            entityID, pagedDto.getDirection(), 
                            pagedDto.getKeyFieldId(), parameters);
                    
                    // let know the page is there is a previous one
                    if (start == null) {
                        session.removeAttribute(Constants.SESSION_PAGED_IS_PREV);
                    } else {
                        session.setAttribute(Constants.SESSION_PAGED_IS_PREV, 
                                new Boolean(true));
                    }
                } else if (pagedDto != null) {
                    // this is a serach
                    queryResults = listSession.search(pagedDto.getSearchStart(),
                            pagedDto.getSearchEnd(), pagedDto.getSearchFieldId(),
                            pagedDto.getListId(), entityID, parameters);

                } else {
                    // cache this in the session
                    if (queryMethod == METHOD_JDBC) {
                        queryResults = listSession.getList(type, parameters);
                        session.setAttribute(Constants.SESSION_LIST_KEY + type, 
                                queryResults);                
                    } else {
                        queryDtoResults = listSession.getDtoList(type, parameters);
                        session.setAttribute(Constants.SESSION_LIST_KEY + type, 
                                queryDtoResults);                
                    }
                }

            } else {
                log.debug("Using cached list " + type);
                // let's rewind, this will change when the web-paging is done
                if (queryMethod == METHOD_JDBC) {
                    queryResults.beforeFirst();
                } else {
                    dtoIndex = 0;
                }
            }
            
            //  we leave the cursor ready to render data
            if (queryMethod == METHOD_JDBC) {       
                if (queryResults.last()) {
                    // means that there are rows returned
                    int totalRows = queryResults.getRow();
                    session.setAttribute(Constants.SESSION_LIST_ROWS, 
                            new Integer(totalRows));
                    queryResults.first();
                    retValue = EVAL_BODY_INCLUDE;
                    
                    // paged list need to now if there's a next one
                    if (pagedDto != null) {
                        log.debug("rows = " + totalRows + " page " + pagedDto.getPageSize());
                        if (pagedDto.getPageSize().intValue() > totalRows) {
                            session.removeAttribute(Constants.SESSION_PAGED_IS_NEXT);
                        } else {
                            session.setAttribute(Constants.SESSION_PAGED_IS_NEXT,
                                    new Boolean(true));
                        }
                    }
                }
            } else {
                dtoIndex = 0;
                if (dtoIndex < queryDtoResults.getLines().size()) {
                    retValue = EVAL_BODY_INCLUDE;
                }
            }

        } catch (SessionInternalError e) {
            errors.add(ActionErrors.GLOBAL_ERROR, 
                    new ActionError("all.internal"));
            pageContext.getRequest().setAttribute(Globals.ERROR_KEY, 
                    errors);
            log.error("Session exception at Generic List tag.", e);
        } catch (Exception e) {
            log.error("Exception at Generic List tag", e);
            throw new JspException("Web error" + e.getMessage());
        }

        return retValue;

    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getSetup() {
        return setup;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.String"
     */
    public String getType() {
        return type;
    }

    /**
     * @param boolean1
     */
    public void setSetup(Boolean boolean1) {
        setup = boolean1;
    }

    /**
     * @param string
     */
    public void setType(String string) {
        type = string;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.String"
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param string
     */
    public void setMethod(String string) {
        method = string;
    }

}




