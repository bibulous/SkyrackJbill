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

package com.sapienter.jbilling.server.mediation.task;

/**
 * MySQLReader provides a driver string and constructs a url string
 * from plug-in parameters for use by JDBCReader.
 */
public class MySQLReader extends JDBCReader {
    public static final String PARAM_HOST = "host";
    public static final String PARAM_PORT = "port";

    public MySQLReader() {
        super();
    }

    @Override
    public String getDriver() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getUrl() {
        String host = (String) parameters.get(PARAM_HOST);
        String port = (String) parameters.get(PARAM_PORT);

        String url = "jdbc:mysql://";

        if (host != null) {
            url += host;
        }
        if (port != null) {
            url += ":" + port;
        }

        url += "/" + getDatabaseName();

        return url;
    }
}
