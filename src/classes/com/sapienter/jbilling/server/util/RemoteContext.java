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
import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RemoteContext {

    private static final ApplicationContext spring = 
            new ClassPathXmlApplicationContext( new String[] {
            "/jbilling-remote-beans.xml" });
    
    public enum Name {
        ITEM_REMOTE_SESSION,
        LIST_REMOTE_SESSION,
        USER_REMOTE_SESSION,
        INVOICE_REMOTE_SESSION,
        ORDER_REMOTE_SESSION,
        PAYMENT_REMOTE_SESSION,
        MEDIATION_REMOTE_SESSION,
        BILLING_PROCESS_REMOTE_SESSION,
        PROVISIONING_PROCESS_REMOTE_SESSION,
        API_CLIENT,
        API_CLIENT_2
    }
    
    private static final Map<Name, String> springBeans = new EnumMap<Name, String>(Name.class);
    
    // all the managed beans
    static {
        // remote session beans
        springBeans.put(Name.ITEM_REMOTE_SESSION, "itemRemoteSession");
        springBeans.put(Name.LIST_REMOTE_SESSION, "listRemoteSession");
        springBeans.put(Name.USER_REMOTE_SESSION, "userRemoteSession");
        springBeans.put(Name.INVOICE_REMOTE_SESSION, "invoiceRemoteSession");
        springBeans.put(Name.ORDER_REMOTE_SESSION, "orderRemoteSession");
        springBeans.put(Name.PAYMENT_REMOTE_SESSION, "paymentRemoteSession");
        springBeans.put(Name.MEDIATION_REMOTE_SESSION, 
                "mediationRemoteSession");
        springBeans.put(Name.BILLING_PROCESS_REMOTE_SESSION, 
                "billingProcessRemoteSession");
        springBeans.put(Name.PROVISIONING_PROCESS_REMOTE_SESSION,
                "provisioningProcessRemoteSession");
        springBeans.put(Name.API_CLIENT, "apiClient");
        springBeans.put(Name.API_CLIENT_2, "apiClient2");
    };
    
    // should not be instantiated
    private RemoteContext() {
    }
    
    public static Object getBean(Name bean) {
        return spring.getBean(springBeans.get(bean));
    }
}
