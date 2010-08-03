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

/*
 * Created on Dec 2, 2004
 *
 */
package com.sapienter.jbilling.client.list;

import java.util.Hashtable;
import java.util.List;

import com.sapienter.jbilling.server.list.PagedListDTO;
import java.util.ArrayList;

/**
 * @author Emil
 * This is a helper class that goes in the session and holds the info
 * needed to ask for the next/previous page
 * The original object is created by the list tag when called to setup.
 */
public class PagedList extends PagedListDTO {
    private Hashtable parameters = null;
    private List pageFrom = null;
    private Boolean direction = null;
    // works as an index, starting with 0.
    // It is always displayed + 1.
    private Integer currentPage = null;
    private String legacyName = null;
    private Boolean doSearch = null;
    private String searchStart = null;
    private String searchEnd = null;
    private Integer searchFieldId = null;

    public Integer getPageNumber() {
        return new Integer(currentPage.intValue() + 1);
    }
    
    public String getLegacyName() {
        return legacyName;
    }
    public void setLegacyName(String legacyName) {
        this.legacyName = legacyName;
    }
    public PagedList(PagedListDTO dto) {
        super(dto);
        pageFrom = new ArrayList();
        direction = new Boolean(false);
        currentPage = new Integer(0);
    }
    public Long getNumberOfPages() {
        if (getCount() != null) {
            long total = getCount().longValue() / 
                getPageSize().longValue();
            total++;
            return new Long(total);
        } else {
            return null;
        }
    }
    
    public void setNumberOfPages(Long x) {
        
    }
    
    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public Boolean getDirection() {
        return direction;
    }
    public void setDirection(Boolean direction) {
        this.direction = direction;
    }
    public List getPageFrom() {
        return pageFrom;
    }
    public void setPageFrom(List pageFrom) {
        this.pageFrom = pageFrom;
    }
    public Hashtable getParameters() {
        return parameters;
    }
    public void setParameters(Hashtable parameters) {
        this.parameters = parameters;
    }
    public Boolean getDoSearch() {
        return doSearch;
    }
    public void setDoSearch(Boolean doSearch) {
        this.doSearch = doSearch;
    }
    public String getSearchEnd() {
        return searchEnd;
    }
    public void setSearchEnd(String searchEnd) {
        this.searchEnd = searchEnd;
    }
    public String getSearchStart() {
        return searchStart;
    }
    public void setSearchStart(String searchStart) {
        this.searchStart = searchStart;
    }
    public Integer getSearchFieldId() {
        return searchFieldId;
    }
    public void setSearchFieldId(Integer searchFieldId) {
        this.searchFieldId = searchFieldId;
    }
}
