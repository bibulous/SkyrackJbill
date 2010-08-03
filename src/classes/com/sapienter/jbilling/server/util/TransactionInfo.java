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

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.TransactionManager;


/**
 * @author Collin VanDyck
 *
 */
public class TransactionInfo {

    public static final boolean inActiveTransaction()
    {
        try
        {
            TransactionManager tm = (TransactionManager) new InitialContext().lookup("java:/TransactionManager");
            int status = tm.getStatus();
            if (status == Status.STATUS_ACTIVE)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    public static final int getTransactionStatus()
    {
        try
        {
            TransactionManager tm = (TransactionManager) new InitialContext().lookup("java:/TransactionManager");
            int status = tm.getStatus();
            return status;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
    
    public static final String getTransactionInformation()
    {
        String result = "";

        try
        {
            TransactionManager tm = (TransactionManager) new InitialContext().lookup("java:/TransactionManager");
            int status = tm.getStatus();
            
            switch (status)
            {
                case Status.STATUS_ACTIVE:
                    result = "ACTIVE";
                    break;
                case Status.STATUS_COMMITTED:
                    result = "COMMITTED";
                    break;
                case Status.STATUS_COMMITTING:
                    result = "COMMITTING";
                    break;
                case Status.STATUS_MARKED_ROLLBACK:
                    result = "MARKED_ROLLBACK";
                    break;
                case Status.STATUS_NO_TRANSACTION:
                    result = "NO_TRANSACTION";
                    break;
                case Status.STATUS_PREPARED:
                    result = "PREPARED";
                    break;
                case Status.STATUS_PREPARING:
                    result = "PREPARING";
                    break;
                case Status.STATUS_ROLLEDBACK:
                    result = "ROLLEDBACK";
                    break;
                case Status.STATUS_ROLLING_BACK:
                    result = "ROLLING_BACK";
                    break;
                case Status.STATUS_UNKNOWN:
                    result = "UNKNOWN";
                    break;
                default:
                    result = "UNDEFINED";
            }
        }
        catch (Exception e)
        {
            result = "ERROR: could not get tx status: " + e.getMessage();
        }
        
        return result;
    }

}