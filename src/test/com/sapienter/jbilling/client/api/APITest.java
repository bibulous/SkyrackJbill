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

package com.sapienter.jbilling.client.api;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.common.GatewayBL;

/**
 * EJBFactory
 * 
 * @author Emil
 */

public class APITest extends TestCase {


	public void testSimpleCall () {
	    
        try {
            String[] result;
            NameValuePair[] data1 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "authenticate"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_password", "asdfasdf"),
                    };  
            result = makeCall(data1);
            assertEquals("Simple call ", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);              
     		

            NameValuePair[] data2 = {
                new NameValuePair("s_login", "customer"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "authenticate"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_password", "asdfasdf"),
                    };  
            result = makeCall(data2);
            assertEquals("User not root ", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);              

            NameValuePair[] data3 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "not good"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "authenticate"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_password", "asdfasdf"),
                    };  
            result = makeCall(data3);
            assertEquals("bad company id ", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       

            NameValuePair[] data4 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "authenticate"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_password", "asdfasdf"),
                new NameValuePair("s_separator", ","),
            };  
            result = makeCall(data4, ",");
            assertTrue("Separator " + result[0], result[0].equals("1"));              

            NameValuePair[] data5 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "pepinillo"),
                new NameValuePair("s_action", "authenticate"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_password", "asdfasdf"),
            };  
            result = makeCall(data5);
            assertEquals("bad area 1", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("bad area 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_AREA),  result[1]);       

            NameValuePair[] data6 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "authenticate"),
                new NameValuePair("s_username", "customer"),
            };  
            result = makeCall(data6);
            assertEquals("missing password 1", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("missing password 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION),  result[1]);       

            NameValuePair[] data7 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "authenticate"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_password", "bad bad"),
            };  
            result = makeCall(data7);
            assertEquals("Bad authentication", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Bad authentication 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_NOAUTH),  result[1]);       


            NameValuePair[] data8 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_password", "aaaaaaaa"),
                new NameValuePair("s_type_id", Constants.TYPE_CUSTOMER.toString()),
                new NameValuePair("s_email", "emilconde@telus.net"),
            };  
            result = makeCall(data8);
            assertEquals("Create user - no username", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Create user - no username 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_REQ),  result[1]);       

            NameValuePair[] data9 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "apicustomerX"),
                new NameValuePair("s_password", "aaaaaaaa"),
                new NameValuePair("s_email", "emilconde@telus.net"),
            };  
            result = makeCall(data9);
            assertEquals("Create user - no type", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Create user - no type 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_REQ),  result[1]);       

            NameValuePair[] data10 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "apicustomerX"),
                new NameValuePair("s_password", "aaaaaaaa"),
                new NameValuePair("s_type_id", Constants.TYPE_CUSTOMER.toString()),
            };  
            result = makeCall(data10);
            assertEquals("Create user - no email", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Create user - no email 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION),  result[1]);       

            NameValuePair[] data11 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "apicustomer6"),
                new NameValuePair("s_password", "aaaaaaaa"),
                new NameValuePair("s_type_id", Constants.TYPE_CUSTOMER.toString()),
                new NameValuePair("s_email", "emilconde@telus.net"),
            };  
            result = makeCall(data11);
            assertEquals("User creation", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       

            NameValuePair[] data11c = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "apicustomer6"),
                new NameValuePair("s_password", "aaaaaaaa"),
                new NameValuePair("s_type_id", Constants.TYPE_CUSTOMER.toString()),
                new NameValuePair("s_email", "emilconde@telus.net"),
            };  
            result = makeCall(data11c);
            assertEquals("Create user - duplicated username", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Create user - duplicated username 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_USER),  result[1]);       

            NameValuePair[] data11b = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "delete"),
                new NameValuePair("s_username", "apicustomer6"),
            };  
            result = makeCall(data11b);
            assertEquals("User deletion", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       

            NameValuePair[] data12 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "apipartner"),
                new NameValuePair("s_password", "bbbbbbbb"),
                new NameValuePair("s_type_id", Constants.TYPE_PARTNER.toString()),
                new NameValuePair("s_email", "emilconde@telus.net"),
            };  
            result = makeCall(data12);
            assertEquals("Create partner - no extra fields", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Create partner - no extra fields 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION),  result[1]);       

            NameValuePair[] data13 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "apipartner"),
                new NameValuePair("s_password", "bbbbbbbb"),
                new NameValuePair("s_type_id", Constants.TYPE_PARTNER.toString()),
                new NameValuePair("s_email", "emilconde@telus.net"),
                new NameValuePair("s_batch", "1"),
                new NameValuePair("s_one_time", "0"),
                new NameValuePair("s_period_unit", "1"),
                new NameValuePair("s_period_value", "2"),
                new NameValuePair("s_next_payout", "bad"),
                new NameValuePair("s_clerk", "5"),
            };  
            result = makeCall(data13);
            assertEquals("Create partner - bad date", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Create partner - bad date 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_TYPE),  result[1]);       


            NameValuePair[] data14a = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "apipartner2"),
                new NameValuePair("s_password", "bbbbbbbb"),
                new NameValuePair("s_type_id", Constants.TYPE_PARTNER.toString()),
                new NameValuePair("s_email", "emilc bad sapienter.com"),
                new NameValuePair("s_batch", "4"),
                new NameValuePair("s_one_time", "5"),
                new NameValuePair("s_period_value", "2"),
                new NameValuePair("s_next_payout", "2004-01-15"),
                new NameValuePair("s_clerk", "5"),
            };  
            result = makeCall(data14a);
            assertEquals("Create partner - bad data", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Create partner - bad data 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION), 
                    result[1]);       

            NameValuePair[] data14 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "apipartner2"),
                new NameValuePair("s_password", "bbbbbbbb"),
                new NameValuePair("s_type_id", Constants.TYPE_PARTNER.toString()),
                new NameValuePair("s_email", "emilc@sapienter.com"),
                new NameValuePair("s_batch", "1"),
                new NameValuePair("s_one_time", "0"),
                new NameValuePair("s_period_unit", "1"),
                new NameValuePair("s_period_value", "2"),
                new NameValuePair("s_next_payout", "2004-01-15"),
                new NameValuePair("s_clerk", "5"),
            };  
            result = makeCall(data14);
            assertEquals("Create partner", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       

            NameValuePair[] data15 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "delete"),
                new NameValuePair("s_username", "apipartner2"),
            };  
            result = makeCall(data15);
            assertEquals("User deletion 2", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       

            NameValuePair[] data16 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "creditcard"),
                new NameValuePair("s_username", "customer"),
            };  
            result = makeCall(data16);
            assertEquals("Credit card - no fields", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       

            NameValuePair[] data17 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "creditcard"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_name", "Emil P. Chister"),
                new NameValuePair("s_expiry_month", "23"),
                new NameValuePair("s_expiry_year", "05"),
                new NameValuePair("s_number", "4111111111111111"),
            };  
            result = makeCall(data17);
            assertEquals("Credit card - bad month", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Credit card - bad month 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_RANGE),  result[1]);       

            NameValuePair[] data18 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "creditcard"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_name", "Emil P. Chister"),
                new NameValuePair("s_expiry_month", "03"),
                new NameValuePair("s_expiry_year", "05"),
                new NameValuePair("s_number", "4111111911111111"),
            };  
            result = makeCall(data18);
            assertEquals("Credit card - cc number", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Credit card - cc number 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION),  result[1]);       

            NameValuePair[] data19 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "user"),
                new NameValuePair("s_action", "creditcard"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_name", "Emil P. Chister"),
                new NameValuePair("s_expiry_month", "03"),
                new NameValuePair("s_expiry_year", "05"),
                new NameValuePair("s_number", "4111111111111111"),
            };  
            result = makeCall(data19);
            assertEquals("Credit card - ok", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       

            NameValuePair[] data20 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "payment"),
                new NameValuePair("s_action", "process"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_name", "Emil P. Chister"),
                new NameValuePair("s_expiry_month", "03"),
                new NameValuePair("s_expiry_year", "05"),
                new NameValuePair("s_number", "4111111111111111"),
                new NameValuePair("s_amount", "20.5"),
                new NameValuePair("s_currency_id", "1"),
            };  
            result = makeCall(data20);
            assertEquals("Payment - ok", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       

            NameValuePair[] data21 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "payment"),
                new NameValuePair("s_action", "process"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_name", "Emil P. Chister"),
                new NameValuePair("s_expiry_month", "03"),
                new NameValuePair("s_expiry_year", "05"),
                new NameValuePair("s_number", "4111111111111111"),
                new NameValuePair("s_currency_id", "1"),
            };  
            result = makeCall(data21);
            assertEquals("Payment - no amount", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Payment - no amount 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION),  
                    result[1]);       

            NameValuePair[] data22 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "payment"),
                new NameValuePair("s_action", "process"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_name", "Emil P. Chister"),
                new NameValuePair("s_expiry_month", "03"),
                new NameValuePair("s_expiry_year", "05"),
                new NameValuePair("s_number", "4111111111111111"),
                new NameValuePair("s_amount", "20.5"),
                new NameValuePair("s_currency_id", "not good"),
            };  
            result = makeCall(data22);
            assertEquals("Payment - bad currency", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Payment - bad currency 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_TYPE),  
                    result[1]);       

            NameValuePair[] data23 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "payment"),
                new NameValuePair("s_action", "process"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_name", "Emil P. Chister"),
                new NameValuePair("s_expiry_month", "03"),
                new NameValuePair("s_expiry_year", "05"),
                new NameValuePair("s_number", "4111111NO111111111"),
                new NameValuePair("s_amount", "20.5"),
                new NameValuePair("s_currency_id", "1"),
            };  
            result = makeCall(data23);
            assertEquals("Payment - bad cc", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Payment - bad cc 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION),  
                    result[1]);       

            NameValuePair[] data24 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "order"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_currency_id", "1"),
                new NameValuePair("s_order_type_id", "1"),
                new NameValuePair("s_period_id", "3"),
                new NameValuePair("s_process", "0"),
                new NameValuePair("s_line_1_amount", "10.22"),
                new NameValuePair("s_line_1_description", "api test line"),
                new NameValuePair("s_line_1_item_id", "1"),
                new NameValuePair("s_line_1_price", "10.22"),
                new NameValuePair("s_line_1_quantity", "1"),
                new NameValuePair("s_line_1_type_id", "1"),
            };  
            result = makeCall(data24);
            assertEquals("Order - ok", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       

            NameValuePair[] data25 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "order"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_currency_id", "1"),
                new NameValuePair("s_amount", "20.5"),
                new NameValuePair("s_order_type_id", "1"),
                new NameValuePair("s_period_id", "3"),
                new NameValuePair("s_process", "0"),
           };  
            result = makeCall(data25);
            assertEquals("Order - no lines", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Order - no lines 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_NOLINES),  
                    result[1]);       

            NameValuePair[] data26 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "order"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_currency_id", "1"),
                new NameValuePair("s_period_id", "3"),
                new NameValuePair("s_process", "0"),
                new NameValuePair("s_line_1_amount", "10.22"),
                new NameValuePair("s_line_1_description", "api test line"),
                new NameValuePair("s_line_1_item_id", "1"),
                new NameValuePair("s_line_1_price", "10.22"),
                new NameValuePair("s_line_1_quantity", "1"),
                new NameValuePair("s_line_1_type_id", "1"),
            };  
            result = makeCall(data26);
            assertEquals("Order - no type", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Order - no type 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION),  
                    result[1]);       

            NameValuePair[] data27 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "order"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_currency_id", "1"),
                new NameValuePair("s_order_type_id", "1"),
                new NameValuePair("s_period_id", "3"),
                new NameValuePair("s_process", "0"),
                new NameValuePair("s_line_1_amount", "10.22"),
                new NameValuePair("s_line_1_description", "api test line"),
                new NameValuePair("s_line_1_item_id", "1"),
                new NameValuePair("s_line_1_price", "dfsd"),
                new NameValuePair("s_line_1_quantity", "1"),
                new NameValuePair("s_line_1_type_id", "1"),
            };  
            result = makeCall(data27);
            assertEquals("Order - bad price", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Order - bad price 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_TYPE),  
                    result[1]);       

            NameValuePair[] data28 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "order"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_currency_id", "1"),
                new NameValuePair("s_order_type_id", "1"),
                new NameValuePair("s_period_id", "3"),
                new NameValuePair("s_process", "1"),
                new NameValuePair("s_line_1_item_id", "1"),
                new NameValuePair("s_line_1_quantity", "3"),
                new NameValuePair("s_line_1_type_id", "1"),
                new NameValuePair("s_line_2_item_id", "3"),
                new NameValuePair("s_line_2_quantity", "1"),
                new NameValuePair("s_line_2_description", "api tax test line"),
                new NameValuePair("s_line_2_price", "100"),
                new NameValuePair("s_line_2_amount", "100"),
                new NameValuePair("s_line_2_type_id", "2"),
            };  
            result = makeCall(data28);
            assertEquals("Order - ok with tax", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       


            NameValuePair[] data30 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "order"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_currency_id", "1"),
                new NameValuePair("s_order_type_id", "1"),
                new NameValuePair("s_period_id", "3"),
                new NameValuePair("s_process", "1"),
                new NameValuePair("s_line_1_description", "api test line"),
                new NameValuePair("s_line_1_item_id", "1"),
                new NameValuePair("s_line_1_type_id", "1"),
            };  
            result = makeCall(data30);
            assertEquals("Order - no quantity", 
                    String.valueOf(GatewayBL.RES_CODE_ERROR),  result[0]);       
            assertEquals("Order - no quantity 2", 
                    String.valueOf(GatewayBL.RES_SUB_CODE_ERR_VALIDATION),  
                    result[1]);       

            NameValuePair[] data31 = {
                new NameValuePair("s_login", "root"),
                new NameValuePair("s_company_id", "coolString1"),
                new NameValuePair("s_area", "order"),
                new NameValuePair("s_action", "create"),
                new NameValuePair("s_username", "customer"),
                new NameValuePair("s_currency_id", "1"),
                new NameValuePair("s_order_type_id", "1"),
                new NameValuePair("s_period_id", "3"),
                new NameValuePair("s_process", "1"),
                new NameValuePair("s_generate_invoice", "1"),
                new NameValuePair("s_line_1_item_id", "1"),
                new NameValuePair("s_line_1_quantity", "3"),
                new NameValuePair("s_line_1_type_id", "1"),
                new NameValuePair("s_line_2_item_id", "3"),
                new NameValuePair("s_line_2_quantity", "1"),
                new NameValuePair("s_line_2_description", "api tax test line"),
                new NameValuePair("s_line_2_price", "100"),
                new NameValuePair("s_line_2_amount", "100"),
                new NameValuePair("s_line_2_type_id", "2"),
            };  
            result = makeCall(data31);
            assertEquals("Order - ok full with invoice", 
                    String.valueOf(GatewayBL.RES_CODE_OK),  result[0]);       

        } catch (Exception e) {
            e.printStackTrace();
            fail("Got an exception " + e );
        }
	}
    
    private String[] makeCall(NameValuePair[] data) 
            throws HttpException, IOException {
        return makeCall(data, "\\|");
    }
    
    private String[] makeCall(NameValuePair[] data, String separator) 
            throws HttpException, IOException {
                
        Credentials creds = null;
//            creds = new UsernamePasswordCredentials(args[1], args[2]);

        //create a singular HttpClient object
        HttpClient client = new HttpClient();
        client.setConnectionTimeout(5000);
        

        //set the default credentials
        if (creds != null) {
            client.getState().setCredentials(null, null, creds);
        }

        PostMethod post = new PostMethod("http://localhost/betty/gateway");
        
        post.setRequestBody(data);

        //execute the method
        String responseBody = null;
        client.executeMethod(post);
        responseBody = post.getResponseBodyAsString();

        System.out.println("Got response:" + responseBody);
        //write out the response body
        //clean up the connection resources
        post.releaseConnection();
        post.recycle();

        return responseBody.split(separator, -1);
    }

}
