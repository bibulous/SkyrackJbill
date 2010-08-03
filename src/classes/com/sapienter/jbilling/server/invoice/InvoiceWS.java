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

package com.sapienter.jbilling.server.invoice;

import com.sapienter.jbilling.server.entity.InvoiceLineDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Emil
 */
public class InvoiceWS implements Serializable {

    private Integer delegatedInvoiceId = null;
    private Integer payments[] = null;
    private Integer userId = null;
    private InvoiceLineDTO invoiceLines[] = null;
    private Integer orders[] = null;

    // original DTO
    private Integer id;
    private Date createDateTime;
    private Date createTimeStamp;
    private Date lastReminder;
    private Date dueDate;
    private String total;
    private Integer toProcess;
    private Integer statusId;
    private String balance;
    private String carriedBalance;
    private Integer inProcessPayment;
    private Integer deleted;
    private Integer paymentAttempts;
    private Integer isReview;
    private Integer currencyId;
    private String customerNotes;
    private String number;
    private Integer overdueStep;

    public InvoiceWS() {
        super();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getCreateTimeStamp() {
        return this.createTimeStamp;
    }

    public void setCreateTimeStamp(Date createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public Date getLastReminder() {
        return this.lastReminder;
    }

    public void setLastReminder(Date lastReminder) {
        this.lastReminder = lastReminder;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(java.util.Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getTotal() {
        return this.total;
    }

    public BigDecimal getTotalAsDecimal() {
        return (total == null ? null : new BigDecimal(total));
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setTotal(BigDecimal total) {
        if (total != null)
            this.total = total.toString();
    }

    public Integer getToProcess() {
        return this.toProcess;
    }

    public void setToProcess(Integer toProcess) {
        this.toProcess = toProcess;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getBalance() {
        return this.balance;
    }

    public BigDecimal getBalanceAsDecimal() {
        return (balance == null ? null : new BigDecimal(balance));
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBalance(BigDecimal balance) {
        if (balance != null)
            this.balance = balance.toString();
    }

    public String getCarriedBalance() {
        return this.carriedBalance;
    }

    public BigDecimal getCarriedBalanceAsDecimal() {
        return (carriedBalance == null ? null : new BigDecimal(carriedBalance));
    }

    public void setCarriedBalance(String carriedBalance) {
        this.carriedBalance = carriedBalance;
    }

    public void setCarriedBalance(BigDecimal carriedBalance) {
        if (carriedBalance != null)
            this.carriedBalance = carriedBalance.toString();
    }

    public Integer getInProcessPayment() {
        return this.inProcessPayment;
    }

    public void setInProcessPayment(Integer inProcessPayment) {
        this.inProcessPayment = inProcessPayment;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getPaymentAttempts() {
        return this.paymentAttempts;
    }

    public void setPaymentAttempts(Integer paymentAttempts) {
        this.paymentAttempts = paymentAttempts;
    }

    public Integer getIsReview() {
        return this.isReview;
    }

    public void setIsReview(Integer isReview) {
        this.isReview = isReview;
    }

    public Integer getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCustomerNotes() {
        return this.customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getOverdueStep() {
        return this.overdueStep;
    }

    public void setOverdueStep(Integer overdueStep) {
        this.overdueStep = overdueStep;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDelegatedInvoiceId() {
        return delegatedInvoiceId;
    }

    public void setDelegatedInvoiceId(Integer delegatedInvoiceId) {
        this.delegatedInvoiceId = delegatedInvoiceId;
    }

    public InvoiceLineDTO[] getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(InvoiceLineDTO[] invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public Integer[] getOrders() {
        return orders;
    }

    public void setOrders(Integer[] orders) {
        this.orders = orders;
    }

    public Integer[] getPayments() {
        return payments;
    }

    public void setPayments(Integer[] payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();

        str.append("delegated invoice = [")
                .append(delegatedInvoiceId)
                .append("]")
                .append(" payments = ");

        if (payments != null) {
            for (int f = 0; f < payments.length; f++) {
                str.append(payments[f].toString());
            }
        }

        str.append(" userId = [")
                .append(userId)
                .append(" invoiceLines = ");

        if (invoiceLines != null) {
            for (InvoiceLineDTO line : invoiceLines) {
                str.append(line.toString());
            }
        }

        str.append(" orders = ");
        for (int f = 0; f < orders.length; f++) {
            str.append(orders[f].toString());
        }

        str.append("InvoiceDTO = [").append(super.toString());
        return str.toString();
    }
}
