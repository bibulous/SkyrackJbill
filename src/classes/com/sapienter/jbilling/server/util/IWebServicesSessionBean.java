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

import java.math.BigDecimal;
import java.util.Date;

import javax.jws.WebService;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.InvoiceWS;
import com.sapienter.jbilling.server.item.ItemDTOEx;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.ItemTypeWS;
import com.sapienter.jbilling.server.order.OrderLineWS;
import com.sapienter.jbilling.server.order.OrderWS;
import com.sapienter.jbilling.server.payment.PaymentAuthorizationDTOEx;
import com.sapienter.jbilling.server.payment.PaymentWS;
import com.sapienter.jbilling.server.user.ContactWS;
import com.sapienter.jbilling.server.user.CreateResponseWS;
import com.sapienter.jbilling.server.user.UserTransitionResponseWS;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.ValidatePurchaseWS;
import com.sapienter.jbilling.server.util.api.JbillingAPIException;

/**
 * Interface for WebServicesSessionBean
 */
@WebService
public interface IWebServicesSessionBean {

    /*
     * INVOICES
     */
    public InvoiceWS getInvoiceWS(Integer invoiceId)
            throws SessionInternalError;

    public InvoiceWS getLatestInvoice(Integer userId)
            throws SessionInternalError;

    public Integer[] getLastInvoices(Integer userId, Integer number)
            throws SessionInternalError;

    public Integer[] getInvoicesByDate(String since, String until)
            throws SessionInternalError;

    public Integer[] getUserInvoicesByDate(Integer userId, String since, 
            String until) throws SessionInternalError;

    /**
     * Deletes an invoice 
     * @param invoiceId
     * The id of the invoice to delete
     */
    public void deleteInvoice(Integer invoiceId);

    /**
     * Generates invoices for orders not yet invoiced for this user.
     * Optionally only allow recurring orders to generate invoices. 
     * Returns the ids of the invoices generated. 
     */
    public Integer[] createInvoice(Integer userId, boolean onlyRecurring)
            throws SessionInternalError;


    /*
     * USERS
     */
    /**
     * Creates a new user. The user to be created has to be of the roles customer
     * or partner.
     * The username has to be unique, otherwise the creating won't go through. If 
     * that is the case, the return value will be null.
     * @param newUser 
     * The user object with all the information of the new user. If contact or 
     * credit card information are present, they will be included in the creation
     * although they are not mandatory.
     * @return The id of the new user, or null if non was created
     */
    public Integer createUser(UserWS newUser) throws SessionInternalError;

    public void deleteUser(Integer userId) throws SessionInternalError;

    public void updateUserContact(Integer userId, Integer typeId,
            ContactWS contact) throws SessionInternalError;

    /**
     * @param user 
     */
    public void updateUser(UserWS user) throws SessionInternalError;

    /**
     * Retrieves a user with its contact and credit card information. 
     * @param userId
     * The id of the user to be returned
     */
    public UserWS getUserWS(Integer userId) throws SessionInternalError;

    /**
     * Retrieves aall the contacts of a user 
     * @param userId
     * The id of the user to be returned
     */
    public ContactWS[] getUserContactsWS(Integer userId)
            throws SessionInternalError;

    /**
     * Retrieves the user id for the given username 
     */
    public Integer getUserId(String username) throws SessionInternalError;

    /**
     * Retrieves an array of users in the required status 
     */
    public Integer[] getUsersInStatus(Integer statusId)
            throws SessionInternalError;

    /**
     * Retrieves an array of users in the required status 
     */
    public Integer[] getUsersNotInStatus(Integer statusId)
            throws SessionInternalError;

    /**
     * Retrieves an array of users in the required status 
     */
    public Integer[] getUsersByCustomField(Integer typeId, String value)
            throws SessionInternalError;
    
    /**
     * Retrieves an array of users in the required status 
     */
    public Integer[] getUsersByCreditCard(String number)
            throws SessionInternalError;

    /**
     * Retrieves an array of users in the required status 
     */
    public Integer[] getUsersByStatus(Integer statusId, Integer entityId,
        boolean in) throws SessionInternalError;

    /**
     * Creates a user, then an order for it, an invoice out the order
     * and tries the invoice to be paid by an online payment
     * This is ... the mega call !!! 
     */
    public CreateResponseWS create(UserWS user, OrderWS order)
            throws SessionInternalError;

    /**
     * Validates the credentials and returns if the user can login or not
     * @param username
     * @param password
     * @return
     * 0 if the user can login (success), or grater than 0 if the user can not login.
     * See the constants in WebServicesConstants (AUTH*) for details.
     * @throws SessionInternalError
     */
    public Integer authenticate(String username, String password)
            throws SessionInternalError;


    /**
     * Updates a user's credit card.
     * @param userId
     * The id of the user updating credit card data.
     * @param creditCard
     * The credit card data to be updated. 
     */
    public void updateCreditCard(Integer userId, 
            com.sapienter.jbilling.server.entity.CreditCardDTO creditCard)
            throws SessionInternalError;

    /*
     * ORDERS
     */
    /**
     * @return the information of the payment aurhotization, or NULL if the 
     * user does not have a credit card
     */
    public PaymentAuthorizationDTOEx createOrderPreAuthorize(OrderWS order)
            throws SessionInternalError;

    public Integer createOrder(OrderWS order) throws SessionInternalError;

    public OrderWS rateOrder(OrderWS order) throws SessionInternalError;

    public OrderWS[] rateOrders(OrderWS orders[]) throws SessionInternalError;

    public void updateItem(ItemDTOEx item);

    public Integer createOrderAndInvoice(OrderWS order)
            throws SessionInternalError;

    public void updateOrder(OrderWS order) throws SessionInternalError;

    public OrderWS getOrder(Integer orderId) throws SessionInternalError;

    public Integer[] getOrderByPeriod(Integer userId, Integer periodId)
            throws SessionInternalError;

    public OrderLineWS getOrderLine(Integer orderLineId)
            throws SessionInternalError;

    public void updateOrderLine(OrderLineWS line) throws SessionInternalError;

    public OrderWS getLatestOrder(Integer userId) throws SessionInternalError;

    public Integer[] getLastOrders(Integer userId, Integer number)
            throws SessionInternalError;

    public void deleteOrder(Integer id) throws SessionInternalError;

    public OrderWS getCurrentOrder(Integer userId, Date date) 
            throws SessionInternalError;

    public OrderWS updateCurrentOrder(Integer userId, OrderLineWS[] lines, 
            String pricing, Date date, String eventDescription) 
            throws SessionInternalError;

    /*
     * PAYMENT
     */
    /**
     * Pays given invoice, using the first credit card available for invoice'd
     * user.
     *
     * @return <code>null</code> if invoice has not positive balance, or if
     *         user does not have credit card
     * @return resulting authorization record. The payment itself can be found by
     * calling getLatestPayment
     */
    public PaymentAuthorizationDTOEx payInvoice(Integer invoiceId)
            throws SessionInternalError;

    public Integer applyPayment(PaymentWS payment, Integer invoiceId)
            throws SessionInternalError;

    public PaymentWS getPayment(Integer paymentId) throws SessionInternalError;

    public PaymentWS getLatestPayment(Integer userId)
            throws SessionInternalError;

    public Integer[] getLastPayments(Integer userId, Integer number)
            throws SessionInternalError;

	public PaymentAuthorizationDTOEx processPayment(PaymentWS payment);

    /**
     * Validate if the user can buy this. This depends on the balance type:
     *   - none: can always buy
     *   - pre paid: if there is enough dynamic balance
     *   - credit limit: only if credit limie - dynamix balance is enough
     * @param userId
     * @param itemId
     * @param fields
     * @return 0, if she can not. >o the number of quantities that she can buy
     */
    public ValidatePurchaseWS validatePurchase(Integer userId, Integer itemId, 
            String fields);

    public ValidatePurchaseWS validateMultiPurchase(Integer userId, 
            Integer[] itemId, String[] fields);

    /*
     * ITEM
     */
    public Integer createItem(ItemDTOEx item) throws SessionInternalError;

    /**
     * Retrieves an array of items for the caller's entity. 
     * @return an array of items from the caller's entity
     */
    public ItemDTOEx[] getAllItems() throws SessionInternalError;

    /**
     * Implementation of the User Transitions List webservice. This accepts a
     * start and end date as arguments, and produces an array of data containing
     * the user transitions logged in the requested time range.
     * @param from Date indicating the lower limit for the extraction of transition
     * logs. It can be <code>null</code>, in such a case, the extraction will start
     * where the last extraction left off. If no extractions have been done so far and
     * this parameter is null, the function will extract from the oldest transition
     * logged.
     * @param to Date indicatin the upper limit for the extraction of transition logs.
     * It can be <code>null</code>, in which case the extraction will have no upper
     * limit. 
     * @return UserTransitionResponseWS[] an array of objects containing the result
     * of the extraction, or <code>null</code> if there is no data thas satisfies
     * the extraction parameters.
     */
    public UserTransitionResponseWS[] getUserTransitions(Date from, Date to)
            throws SessionInternalError;

    /**
     * @return UserTransitionResponseWS[] an array of objects containing the result
     * of the extraction, or <code>null</code> if there is no data thas satisfies
     * the extraction parameters.
     */
    public UserTransitionResponseWS[] getUserTransitionsAfterId(Integer id)
            throws SessionInternalError;

    public ItemDTOEx getItem(Integer itemId, Integer userId, String pricing);

    public InvoiceWS getLatestInvoiceByItemType(Integer userId, 
        Integer itemTypeId) throws SessionInternalError;

    /**
     * Return 'number' most recent invoices that contain a line item with an
     * item of the given item type.
     */
    public Integer[] getLastInvoicesByItemType(Integer userId, 
            Integer itemTypeId, Integer number) throws SessionInternalError;

    public OrderWS getLatestOrderByItemType(Integer userId, Integer itemTypeId)
            throws SessionInternalError;

    public Integer[] getLastOrdersByItemType(Integer userId, Integer itemTypeId,
            Integer number) throws SessionInternalError;

	public BigDecimal isUserSubscribedTo(Integer userId, Integer itemId);

	public Integer[] getUserItemsByCategory(Integer userId, Integer categoryId);

    public ItemDTOEx[] getItemByCategory(Integer itemTypeId);

    public ItemTypeWS[] getAllItemCategories();
}
