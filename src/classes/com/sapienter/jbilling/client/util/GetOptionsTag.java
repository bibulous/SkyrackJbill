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

package com.sapienter.jbilling.client.util;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.IListSessionBean;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.util.OptionDTO;
import com.sapienter.jbilling.server.util.Context;

/**
 * Prepares the a bean to make available to the page the collection
 * of options for order periods
 * 
 * @author emilc
 *
 * @jsp:tag name="getOptions"
 *          body-content="empty"
 */
public class GetOptionsTag extends TagSupport {

    // these are the different types of data to fetch    
    private Boolean countries = new Boolean(false);
    private Boolean userType = new Boolean(false);
    private Boolean language = new Boolean(false);
    private Boolean userStatus = new Boolean(false);
    private Boolean itemType = new Boolean(false);
    private Boolean orderPeriod = new Boolean(false);
    private Boolean billingType = new Boolean(false);
    private Boolean generalPeriod = new Boolean(false);
    private Boolean currencies = new Boolean(false);
    private Boolean contactType = new Boolean(false);
    private Boolean deliveryMethod = new Boolean(false);
    private Boolean orderLineType = new Boolean(false);
    private Boolean taskClasses = new Boolean(false);
    private Boolean subscriberStatus = new Boolean(false);
    private Boolean provisioningStatus = new Boolean(false);
    private Boolean balanceType = new Boolean(false);

    // these are flag to indicate some particluar behavior
    private Boolean inSession = new Boolean(false);
    private String map = null;
    
    public int doStartTag() throws JspException {
        
        Logger log = Logger.getLogger(GetOptionsTag.class);

        // pull some data from the session before making the call
        HttpSession session = pageContext.getSession();
        Integer languageId = (Integer) session.getAttribute(
                Constants.SESSION_LANGUAGE);
        if (languageId == null) {
            languageId = new Integer(1); // def to english
        }
        Integer entityId = (Integer) session.getAttribute(
                Constants.SESSION_ENTITY_ID_KEY);
        UserDTOEx user = (UserDTOEx) session.getAttribute(
                Constants.SESSION_USER_DTO);
        Integer executorType = null;
        if (user!= null) {
            executorType = user.getMainRoleId();
        }
        Collection retValue = null;
        String attributeKey = null;
        String type = null;

		try {
            IListSessionBean remoteList = (IListSessionBean) Context.getBean(
                    Context.Name.LIST_SESSION);
  
            if (countries.booleanValue()) {
                type = "countries";
                attributeKey = Constants.PAGE_COUNTRIES;
            } else if (userType.booleanValue()) {
                type = "userType";
                attributeKey = Constants.PAGE_USER_TYPES;
            } else if (language.booleanValue()) {
                type = "language";
                attributeKey = Constants.PAGE_LANGUAGES;
            } else if (userStatus.booleanValue()) {
                type = "userStatus";
                attributeKey = Constants.PAGE_USER_STATUS;
            } else if (itemType.booleanValue()) {
                type = "itemType";
                attributeKey = Constants.PAGE_ITEM_TYPES;
            } else if (orderPeriod.booleanValue()) {
                type = "orderPeriod";
                attributeKey = Constants.PAGE_ORDER_PERIODS;
            } else if (billingType.booleanValue()) {
                type = "billingType";
                attributeKey = Constants.PAGE_BILLING_TYPE;
            } else if (generalPeriod.booleanValue()) {
                type = "generalPeriod";
                attributeKey = Constants.PAGE_GENERAL_PERIODS;
            } else if (currencies.booleanValue()) {
                type = "currencies";
                attributeKey = Constants.PAGE_CURRENCIES;
            } else if (contactType.booleanValue()) {
                type = "contactType";
                attributeKey = Constants.PAGE_CONTACT_TYPES;
            } else if (deliveryMethod.booleanValue()) {
                type = "deliveryMethod";
                attributeKey = Constants.PAGE_DELIVERY_METHOD;
            } else if (orderLineType.booleanValue()) {
                type = "orderLineType";
                attributeKey = Constants.PAGE_ORDER_LINE_TYPES;
            } else if (taskClasses.booleanValue()) {
                type = "taskClasses";
                attributeKey = Constants.PAGE_TASK_CLASSES;
            } else if (subscriberStatus.booleanValue()) {
                type = "subscriberStatus";
                attributeKey = Constants.PAGE_SUBSCRIBER_STATUS;                
            } else if (provisioningStatus.booleanValue()) {
                type = "provisioningStatus";
                attributeKey = Constants.PAGE_PROVISIONING_STATUS;                
            } else if (balanceType.booleanValue()) {
                type = "balanceType";
                attributeKey = Constants.PAGE_BALANCE_TYPE;
            } else {
                log.error("At least one flag has to be present");
                throw new Exception("at least one attribute required");
            }

            // make the call
            retValue = remoteList.getOptions(type , languageId, entityId,
                    executorType);
            log.debug("Got the options for " + type + " there are " + 
                    retValue.size());
    
            // finally, make the data available to the page
            // see if this call is just to map an id to a string, instead
            // of using it for a select
            if (map != null) {
                for (Iterator it = retValue.iterator(); it.hasNext(); ) {
                    OptionDTO option = (OptionDTO) it.next();
                    if (option.getCode().equals(map)) {
                        pageContext.setAttribute("mapped_option",
                                option.getDescription());
                        break;
                    }
                }
            } else {
                pageContext.setAttribute(attributeKey,
                        retValue, PageContext.PAGE_SCOPE);
            }
		    // in some cases, the result is needed also in the session
            if (inSession.booleanValue()) {
                pageContext.setAttribute("SESSION_" + attributeKey,
                        retValue, PageContext.SESSION_SCOPE);
            }
            
            
        } catch (Exception e) {
		    log.error("Exception on getting the order periods", e);
		    throw new JspException(e);
		}

        return SKIP_BODY;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getCountries() {
        return countries;
    }

    /**
     * @param boolean1
     */
    public void setCountries(Boolean boolean1) {
        countries = boolean1;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getUserType() {
        return userType;
    }

    /**
     * @param boolean1
     */
    public void setUserType(Boolean boolean1) {
        userType = boolean1;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getLanguage() {
        return language;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getUserStatus() {
        return userStatus;
    }

    /**
     * @param boolean1
     */
    public void setLanguage(Boolean boolean1) {
        language = boolean1;
    }

    /**
     * @param boolean1
     */
    public void setUserStatus(Boolean boolean1) {
        userStatus = boolean1;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getItemType() {
        return itemType;
    }

    /**
     * @param boolean1
     */
    public void setItemType(Boolean boolean1) {
        itemType = boolean1;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getOrderPeriod() {
        return orderPeriod;
    }

    /**
     * @param boolean1
     */
    public void setOrderPeriod(Boolean boolean1) {
        orderPeriod = boolean1;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getBillingType() {
        return billingType;
    }

    /**
     * @param boolean1
     */
    public void setBillingType(Boolean boolean1) {
        billingType = boolean1;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getInSession() {
        return inSession;
    }

    /**
     * @param boolean1
     */
    public void setInSession(Boolean boolean1) {
        inSession = boolean1;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getGeneralPeriod() {
        return generalPeriod;
    }

    /**
     * @param generalPeriod
     */
    public void setGeneralPeriod(Boolean generalPeriod) {
        this.generalPeriod = generalPeriod;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getCurrencies() {
        return currencies;
    }

    /**
     * @param currencies
     */
    public void setCurrencies(Boolean currencies) {
        this.currencies = currencies;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Integer"
     */
    public String getMap() {
        return map;
    }

    /**
     * @param map
     */
    public void setMap(String map) {
        this.map = map;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getContactType() {
        return contactType;
    }
    public void setContactType(Boolean contactType) {
        this.contactType = contactType;
    }
    
    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getDeliveryMethod() {
        return deliveryMethod;
    }
    public void setDeliveryMethod(Boolean deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getOrderLineType() {
        return orderLineType;
    }
    public void setOrderLineType(Boolean orderLineType) {
        this.orderLineType = orderLineType;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getTaskClasses() {
        return taskClasses;
    }
    public void setTaskClasses(Boolean taskClasses) {
        this.taskClasses = taskClasses;
    }
    
    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getSubscriberStatus() {
        return subscriberStatus;
    }
    public void setSubscriberStatus(Boolean subscriberStatus) {
        this.subscriberStatus = subscriberStatus;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getProvisioningStatus() {
        return provisioningStatus;
    }
    public void setProvisioningStatus(Boolean provisioningStatus) {
        this.provisioningStatus = provisioningStatus;
    }

    /**
     * @jsp:attribute required="false"
     *                rtexprvalue="true"
     *                type="java.lang.Boolean"
     */
    public Boolean getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Boolean balanceType) {
        this.balanceType = balanceType;
    }


}
