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
 * Created on Jan 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sapienter.jbilling.server.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import com.sapienter.jbilling.server.user.contact.db.ContactFieldDTO;

/**
 * @author Emil
 */
public class ContactWS implements Serializable {

    private int id;
    private String organizationName;
    private String address1;
    private String address2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String countryCode;
    private String lastName;
    private String firstName;
    private String initial;
    private String title;
    private Integer phoneCountryCode;
    private Integer phoneAreaCode;
    private String phoneNumber;
    private Integer faxCountryCode;
    private Integer faxAreaCode;
    private String faxNumber;
    private String email;
    private Date createDate;
    private int deleted;
    private Integer include;

    
    private String[] fieldNames = null;
    private String[] fieldValues = null;
    private Integer type = null; // the contact type
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    public void setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
    }
    public String[] getFieldValues() {
        return fieldValues;
    }
    public void setFieldValues(String[] fieldValues) {
        this.fieldValues = fieldValues;
    }
    /**
     * 
     */
    public ContactWS() {
        super();
    }

    /**
     * @param id
     * @param organizationName
     * @param address1
     * @param address2
     * @param city
     * @param stateProvince
     * @param postalCode
     * @param countryCode
     * @param lastName
     * @param firstName
     * @param initial
     * @param title
     * @param phoneCountryCode
     * @param phoneAreaCode
     * @param phoneNumber
     * @param faxCountryCode
     * @param faxAreaCode
     * @param faxNumber
     * @param email
     * @param createDate
     * @param deleted
     * @param include
     */
    public ContactWS(Integer id, String organizationName, String address1,
            String address2, String city, String stateProvince,
            String postalCode, String countryCode, String lastName,
            String firstName, String initial, String title,
            Integer phoneCountryCode, Integer phoneAreaCode,
            String phoneNumber, Integer faxCountryCode, Integer faxAreaCode,
            String faxNumber, String email, Date createDate, Integer deleted,
            Integer include) {
        this.id = id;
        this.organizationName = organizationName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.stateProvince = stateProvince;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.lastName = lastName;
        this.firstName = firstName;
        this.initial = initial;
        this.title = title;
        this.phoneCountryCode = phoneCountryCode;
        this.phoneAreaCode = phoneAreaCode;
        this.phoneNumber = phoneNumber;
        this.faxCountryCode = faxCountryCode;
        this.faxAreaCode = faxAreaCode;
        this.faxNumber = faxNumber;
        this.email = email;
        this.createDate = createDate;
        this.deleted = deleted;
        this.include = include;
    }
    
    public ContactWS(ContactWS other) {
        setId(other.getId());
        setOrganizationName(other.getOrganizationName());
        setAddress1(other.getAddress1());
        setAddress2(other.getAddress2());
        setCity(other.getCity());
        setStateProvince(other.getStateProvince());
        setPostalCode(other.getPostalCode());
        setCountryCode(other.getCountryCode());
        setLastName(other.getLastName());
        setFirstName(other.getFirstName());
        setInitial(other.getInitial());
        setTitle(other.getTitle());
        setPhoneCountryCode(other.getPhoneCountryCode());
        setPhoneAreaCode(other.getPhoneAreaCode());
        setPhoneNumber(other.getPhoneNumber());
        setFaxCountryCode(other.getFaxCountryCode());
        setFaxAreaCode(other.getFaxAreaCode());
        setFaxNumber(other.getFaxNumber());
        setEmail(other.getEmail());
        setCreateDate(other.getCreateDate());
        setDeleted(other.getDeleted());
        setInclude(other.getInclude());
    }

    /**
     * @param otherValue
     */
    public ContactWS(ContactDTOEx other) {
        setId(other.getId());
        setOrganizationName(other.getOrganizationName());
        setAddress1(other.getAddress1());
        setAddress2(other.getAddress2());
        setCity(other.getCity());
        setStateProvince(other.getStateProvince());
        setPostalCode(other.getPostalCode());
        setCountryCode(other.getCountryCode());
        setLastName(other.getLastName());
        setFirstName(other.getFirstName());
        setInitial(other.getInitial());
        setTitle(other.getTitle());
        setPhoneCountryCode(other.getPhoneCountryCode());
        setPhoneAreaCode(other.getPhoneAreaCode());
        setPhoneNumber(other.getPhoneNumber());
        setFaxCountryCode(other.getFaxCountryCode());
        setFaxAreaCode(other.getFaxAreaCode());
        setFaxNumber(other.getFaxNumber());
        setEmail(other.getEmail());
        setCreateDate(other.getCreateDate());
        setDeleted(other.getDeleted());
        setInclude(other.getInclude());
        setType(other.getType());
        fieldNames = new String[other.getFieldsTable().size()];
        fieldValues = new String[other.getFieldsTable().size()];
        int index = 0;
        for (Iterator it = other.getFieldsTable().keySet().iterator();
                it.hasNext();) {
            fieldNames[index] = (String) it.next();
            ContactFieldDTO fieldDto = (ContactFieldDTO) other.
                getFieldsTable().get(fieldNames[index]);
            fieldValues[index] = fieldDto.getContent();
            index++;
        }
    }

    public String toString() {
        String ret = super.toString();
        ret += " type=" + getType(); 
        if (fieldNames != null) {
            for (int f = 0; f < fieldNames.length; f++) {
                ret = ret + " " + fieldNames[f] + "=" + fieldValues[f];
            }
        }
        return ret;
    }
    public Integer getType() {
        return type;
}
    public void setType(Integer type) {
        this.type = type;
    }
    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public int getDeleted() {
        return deleted;
    }
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getFaxAreaCode() {
        return faxAreaCode;
    }
    public void setFaxAreaCode(Integer faxAreaCode) {
        this.faxAreaCode = faxAreaCode;
    }
    public Integer getFaxCountryCode() {
        return faxCountryCode;
    }
    public void setFaxCountryCode(Integer faxCountryCode) {
        this.faxCountryCode = faxCountryCode;
    }
    public String getFaxNumber() {
        return faxNumber;
    }
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getInclude() {
        return include;
    }
    public void setInclude(Integer include) {
        this.include = include;
    }
    public String getInitial() {
        return initial;
    }
    public void setInitial(String initial) {
        this.initial = initial;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getOrganizationName() {
        return organizationName;
    }
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    public Integer getPhoneAreaCode() {
        return phoneAreaCode;
    }
    public void setPhoneAreaCode(Integer phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }
    public Integer getPhoneCountryCode() {
        return phoneCountryCode;
    }
    public void setPhoneCountryCode(Integer phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getStateProvince() {
        return stateProvince;
    }
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
