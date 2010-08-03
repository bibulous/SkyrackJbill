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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;

import com.sapienter.jbilling.common.JNDILookup;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.user.UserDTOEx;
import java.util.ArrayList;

/*
 * This is a JDBC call for a list of code - description pairs used
 * in forms fields with a select box
 */
public class GetSelectableOptions {
    
    public static Collection getOptions(String type, Integer languageId, 
            Integer entityId, Integer executorType) throws SessionInternalError {
        List retValue = new ArrayList();
        try {
            String sql = null;
            JNDILookup jndi = JNDILookup.getFactory();
            Connection conn = jndi.lookUpDataSource().getConnection();
            boolean argLanguage = true;
            boolean argEntity = false;
            int argExecutorType = -1;
            
            if (type.equals("countries")) {
                sql = 
                "select a.code, b.content " +
                "  from country a, international_description b, jbilling_table c " +
                " where b.table_id = c.id " +
                "   and c.name = 'country' " +
                "   and b.foreign_id = a.id " +
                "   and b.language_id = ? " +
                "   and b.psudo_column = 'description' " +
                "order by 2";
            } else if (type.equals("userType")) {
                sql =  
                "select a.id, b.content " +
                "  from role a, international_description b, jbilling_table c " +
                " where b.table_id = c.id " +
                "   and c.name = 'role' " +
                "   and b.foreign_id = a.id " +
                "   and b.language_id = ? " +
                "   and b.psudo_column = 'title' " +
                "   and a.id > ? " +
                "order by 1";
                
                argExecutorType = 2;
            } else if (type.equals("language")) {
                sql =  
                "select la.id, la.description " +
                "  from language la ";
                
                argLanguage = false;
            } else if (type.equals("userStatus")) {
                sql =  
                "select b.foreign_id, b.content " +
                "  from international_description b, jbilling_table c " +  
                " where b.table_id = c.id  " +
                "   and c.name = 'user_status' " + 
                "   and b.language_id = ?  " +
                "   and b.foreign_id != " + UserDTOEx.STATUS_DELETED + 
                "   and b.psudo_column = 'description' " + 
                "order by 1";
                
            } else if (type.equals("itemType")) {
                // the language of the item types is always the entity's
                // no point of having item types in muliple languages
                sql =  
                "select a.id, a.description " +
                "  from item_type a " +
                " where a.entity_id = ? " +
                "order by 2";
                
                argEntity = true;
                argLanguage = false;
            } else if (type.equals("orderPeriod")) {
            	// The union is for those order periods that are global
            	// The descriptions of those should be in all lanugages
            	// The descriptions for the entity specific should be in the
            	// language of the entity only.
                sql =
                "select op.id, id.content " +
                "  from order_period op, international_description id, jbilling_table bt " +
                " where id.table_id = bt.id " +
                "   and bt.name = 'order_period' " +
                "   and id.foreign_id = op.id " +
                "   and id.language_id = ? " +
                "   and op.entity_id = ? " +
                "   and id.psudo_column = 'description' " +
				"union " +
				"select op.id, id.content " +
                "  from order_period op, international_description id, jbilling_table bt " +
                " where id.table_id = bt.id " +
                "   and bt.name = 'order_period' " +
                "   and id.foreign_id = op.id " +
                "   and id.language_id = ? " +
                "   and op.entity_id is null " +
                "   and id.psudo_column = 'description' " +
                "order by 2";
                
                argEntity = true;
            } else if (type.equals("billingType")) {
                sql =  
                "select obt.id, b.content " +
                "  from order_billing_type obt, international_description b, jbilling_table c " +
                " where b.table_id = c.id " +
                "   and c.name = 'order_billing_type' " +
                "   and b.foreign_id = obt.id " +
                "   and b.language_id = ? " +
                "   and b.psudo_column = 'description' " +
                "order by 2";
            } else if (type.equals("generalPeriod")) {
                sql =
                "select pu.id, id.content " +
                "  from period_unit pu, international_description id, jbilling_table bt " +
                " where id.table_id = bt.id " +
                "   and bt.name = 'period_unit' " +
                "   and id.foreign_id = pu.id " +
                "   and id.language_id = ? " +
                "   and id.psudo_column = 'description' " +
                "order by 2";
            } else if (type.equals("currencies")) {
                sql =
                "select cu.currency_id, id.content " +
                "  from currency_entity_map cu, international_description id, jbilling_table bt " +
                " where id.table_id = bt.id " +
                "   and bt.name = 'currency' " +
                "   and id.foreign_id = cu.currency_id " +
                "   and id.language_id = ? " +
                "   and id.psudo_column = 'description' " +
                "   and cu.entity_id = ? " +
                "order by 2";
                
                argEntity= true;
            } else if (type.equals("contactType")) {
                sql = 
                "select ct.id, id.content " +
                "  from contact_type ct, international_description id, jbilling_table bt " +
                "  where bt.name = 'contact_type' " +
                "    and id.table_id = bt.id " +
                "    and ct.id = id.foreign_id " +
                "    and id.language_id = ? " +
                "    and ct.entity_id = ? " +
                "  order by ct.is_primary desc, id.content";
                
                argEntity= true;
            } else if (type.equals("deliveryMethod")) {
                sql = 
                    "select a.id, b.content " +
                    "  from invoice_delivery_method a, international_description b, jbilling_table c " +
                    " where b.table_id = c.id " +
                    "   and c.name = 'invoice_delivery_method' " +
                    "   and b.foreign_id = a.id " +
                    "   and b.language_id = ? " +
                    "   and b.psudo_column = 'description' " +
                    "order by 2";
            } else if (type.equals("orderLineType")) {
                sql = 
                    "select a.id, b.content " +
                    "  from invoice_delivery_method a, international_description b, jbilling_table c " +
                    " where b.table_id = c.id " +
                    "   and c.name = 'order_line_type' " +
                    "   and b.foreign_id = a.id " +
                    "   and b.language_id = ? " +
                    "   and b.psudo_column = 'description' " +
                    "order by 1";
            } else if (type.equals("taskClasses")) {
                sql = 
                    "select id, class_name " +
                    "  from pluggable_task_type " +
                    "order by 1";
                argLanguage = false;
            } else if (type.equals("subscriberStatus")) {
                sql =
                "select b.foreign_id, b.content " +
                "  from international_description b, jbilling_table c " +  
                " where b.table_id = c.id  " +
                "   and c.name = 'subscriber_status' " + 
                "   and b.language_id = ?  " +
                "   and b.psudo_column = 'description' " + 
                "order by 1";
            } else if (type.equals("provisioningStatus")) {
                sql =
                "select b.foreign_id, b.content " +
                "  from international_description b, jbilling_table c " +  
                " where b.table_id = c.id  " +
                "   and c.name = 'order_line_provisioning_status' " + 
                "   and b.language_id = ?  " +
                "   and b.psudo_column = 'description' " + 
                "order by 1";
            } else if (type.equals("balanceType")) {
                sql =
                "select b.foreign_id, b.content " +
                "  from international_description b, jbilling_table c " +
                " where b.table_id = c.id  " +
                "   and c.name = 'balance_type' " +
                "   and b.language_id = ?  " +
                "   and b.psudo_column = 'description' " +
                "order by 1";
            } else {
                throw new SessionInternalError("type " + type + " is not supported");
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // common parameters
            int parameterIndex = 1;
            if (argLanguage) {
                stmt.setInt(parameterIndex, languageId.intValue());
                parameterIndex++;
            }
            if (argEntity) {
                stmt.setInt(parameterIndex, entityId.intValue());
                parameterIndex++;
            }
            if (argExecutorType > 0) {
                stmt.setInt(argExecutorType, executorType.intValue());
            }
            
            // custom parameters
            if (type.equals("orderPeriod")) {
            	/// the order period needs an extra language because of the union
            	stmt.setInt(parameterIndex, languageId.intValue());
                parameterIndex++;
            }
            
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                OptionDTO dto = new OptionDTO();
                dto.setCode(result.getString(1));
                dto.setDescription(result.getString(2));
                retValue.add(dto);
            }
            result.close();
            stmt.close();
            conn.close();
                
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
        
        return retValue;
    }
}
