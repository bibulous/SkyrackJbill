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

package com.sapienter.jbilling.server.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListDTO implements Serializable {
    private List<Object[]> lines = null;
    private Integer types[] = null;
    
    public ListDTO() {
        lines = new ArrayList<Object[]>();
    }
    /**
     * @return
     */
    public List<Object[]> getLines() {
        return lines;
    }

    /**
     * @return
     */
    public Integer[] getTypes() {
        return types;
    }

    /**
     * @param vector
     */
    public void setLines(List<Object[]> vector) {
        lines = vector;
    }

    /**
     * @param integers
     */
    public void setTypes(Integer[] integers) {
        types = integers;
    }

}
