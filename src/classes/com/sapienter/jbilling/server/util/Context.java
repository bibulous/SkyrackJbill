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
package com.sapienter.jbilling.server.util;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.context.ApplicationContext;

public class Context {

    // get ApplicationContext configured by jbilling-beansRefFactory.xml
    private static final BeanFactoryReference factoryRef = 
            ContextSingletonBeanFactoryLocator.getInstance(
            "jbilling-beansRefFactory.xml").useBeanFactory(
            "com.sapienter.jbilling");
    private static final ApplicationContext spring = (ApplicationContext)
            factoryRef.getFactory();
    
    public enum Name {
        ITEM_SESSION, 
        NOTIFICATION_SESSION,
        CUSTOMER_SESSION,
        LIST_SESSION,
        USER_SESSION,
        INVOICE_SESSION,
        ORDER_SESSION,
        PLUGGABLE_TASK_SESSION,
        PAYMENT_SESSION,
        MEDIATION_SESSION,
        REPORT_SESSION,
        BILLING_PROCESS_SESSION,
        PROVISIONING_PROCESS_SESSION,
        WEB_SERVICES_SESSION,
        PROVISIONING,
        VELOCITY,
        DATA_SOURCE,
        TRANSACTION_MANAGER,
        HIBERNATE_SESSION,
        JDBC_TEMPLATE,
        DESCRIPTION_DAS,
        JBILLING_TABLE_DAS,
        PLUGGABLE_TASK_DAS,
        CACHE,
        CACHE_MODEL_ITEM_PRICE,
        CACHE_FLUSH_MODEL_ITEM_PRICE,
        CACHE_MODEL_RW,
        CACHE_FLUSH_MODEL_RW,
        CAI,
        MMSC,
        WEB_SERVICES_CALLER_DEFAULTS,
        JMS_TEMPLATE,
        PROCESSORS_DESTINATION,
        PROVISIONING_COMMANDS_DESTINATION,
        PROVISIONING_COMMANDS_REPLY_DESTINATION,
        INTERNAL_EVENTS_RULES_TASK_CONFIG
    }
    
    private static final Map<Name, String> springBeans = new EnumMap<Name, String>(Name.class);
    
    // all the managed beans
    static {
        // those that act as session facade, mostly for transaction demarcation
        springBeans.put(Name.ITEM_SESSION, "itemSession");
        springBeans.put(Name.NOTIFICATION_SESSION, "notificationSession");
        springBeans.put(Name.CUSTOMER_SESSION, "customerSession");
        springBeans.put(Name.LIST_SESSION, "listSession");
        springBeans.put(Name.USER_SESSION, "userSession");
        springBeans.put(Name.INVOICE_SESSION, "invoiceSession");
        springBeans.put(Name.ORDER_SESSION, "orderSession");
        springBeans.put(Name.PLUGGABLE_TASK_SESSION, "pluggableTaskSession");
        springBeans.put(Name.PAYMENT_SESSION, "paymentSession");
        springBeans.put(Name.MEDIATION_SESSION, "mediationSession");
        springBeans.put(Name.REPORT_SESSION, "reportSession");
        springBeans.put(Name.BILLING_PROCESS_SESSION, "billingProcessSession");
        springBeans.put(Name.PROVISIONING_PROCESS_SESSION, 
                "provisioningProcessSession");
        springBeans.put(Name.WEB_SERVICES_SESSION, "webServicesSession");

        // data access service
        springBeans.put(Name.DESCRIPTION_DAS, "internationalDescriptionDAS");
        springBeans.put(Name.JBILLING_TABLE_DAS, "jbillingTableDAS");
        springBeans.put(Name.PLUGGABLE_TASK_DAS, "pluggableTaskDAS");
        
        // other simple beans
        springBeans.put(Name.PROVISIONING, "provisioning");
        springBeans.put(Name.VELOCITY, "velocityEngine");
        springBeans.put(Name.DATA_SOURCE, "dataSource");
        springBeans.put(Name.TRANSACTION_MANAGER, "transactionManager");
        springBeans.put(Name.HIBERNATE_SESSION, "sessionFactory");
        springBeans.put(Name.JDBC_TEMPLATE, "jdbcTemplate");
        springBeans.put(Name.CACHE, "cacheProviderFacade");
        springBeans.put(Name.CACHE_MODEL_ITEM_PRICE, "cacheModelItemPrice");
        springBeans.put(Name.CACHE_FLUSH_MODEL_ITEM_PRICE, 
                "flushModelItemPrice");
        springBeans.put(Name.CACHE_MODEL_RW, "cacheModelPTDTO");
        springBeans.put(Name.CACHE_FLUSH_MODEL_RW,
                "flushModelPTDTO");
        springBeans.put(Name.CAI, "cai");
        springBeans.put(Name.MMSC, "mmsc");
        springBeans.put(Name.WEB_SERVICES_CALLER_DEFAULTS, 
                "webServicesCallerDefaults");
        springBeans.put(Name.INTERNAL_EVENTS_RULES_TASK_CONFIG, 
                "internalEventsRulesTaskConfig");

        // JMS related beans
        springBeans.put(Name.JMS_TEMPLATE, "jmsTemplate");
        springBeans.put(Name.PROCESSORS_DESTINATION, "processorsDestination");
        springBeans.put(Name.PROVISIONING_COMMANDS_DESTINATION, 
                "provisioningCommandsDestination");
        springBeans.put(Name.PROVISIONING_COMMANDS_REPLY_DESTINATION,
                "provisioningCommandsReplyDestination");
    };
    
    // should not be instantiated
    private Context() {
    }
    
    public static Object getBean(Name bean) {
        return spring.getBean(springBeans.get(bean));
    }

    // called at shutdown
    public static void shutdown() {
        factoryRef.release();
    }
}
