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

package com.sapienter.jbilling.client.payment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.FieldChecks;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.CrudActionBase;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.payment.IPaymentSessionBean;
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.db.PaymentInfoChequeDTO;
import com.sapienter.jbilling.server.payment.db.PaymentMethodDTO;
import com.sapienter.jbilling.server.payment.db.PaymentResultDTO;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.db.AchDTO;
import com.sapienter.jbilling.server.user.db.CreditCardDTO;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;

public class PaymentCrudAction extends CrudActionBase<PaymentDTOEx> {
	private static final String CREDIT_CARD_MASK = "************";

	private static final String FORM = "payment";

	private static final String FIELD_CREATE = "create";
	
	private static final String FIELD_PAY_METHOD = "method";
	private static final String FIELD_GROUP_DATE = "date";
	private static final String FIELD_AMOUNT = "amount";
	private static final String FIELD_ID = "id";
	private static final String FIELD_CURRENCY = "currencyId";
	private static final String FIELD_PROCESS_NOW = "chbx_processNow";
	
	private static final String FIELD_ACH_ACCOUNT_NAME = "account_name";
	private static final String FIELD_ACH_BANK_NAME = "bank_name";
	private static final String FIELD_ACH_ACCOUNT_TYPE = "account_type";
	private static final String FIELD_ACH_ACCOUNT_NUMBER = "account_number";
	private static final String FIELD_ACH_ABA_CODE = "aba_code";
	
	private static final String FIELD_GROUP_CC_EXPIRY = "ccExpiry";
	private static final String FIELD_CC_NAME = "ccName";
	private static final String FIELD_CC_NUMBER = "ccNumber";

	private static final String FIELD_GROUP_CHEQUE_DATE = "chequeDate";
	private static final String FIELD_CHEQUE_NUMBER = "chequeNumber";
	private static final String FIELD_CHEQUE_BANK = "bank";
	
	private static final String FORWARD_FROM_ORDER = "payment_fromOrder";
	private static final String FORWARD_EDIT = "payment_edit";
	private static final String FORWARD_LIST = "payment_list";
	private static final String FORWARD_UPDATE = "payment_update";
	private static final String FORWARD_REVIEW = "payment_review";
	private static final String FORWARD_REVIEW_PAYOUT = "payment_reviewPayout";
	private static final String FORWARD_PAYOUT = "payment_payout";
	private static final String FORWARD_DELETED = "payment_deleted";
	
	private static final String MESSAGE_REVIEW = "payment.review";
	private static final String MESSAGE_INVOICE_GENERATED = "process.invoiceGenerated";
	
	private final IPaymentSessionBean myPaymentSession;
	
	public PaymentCrudAction(IPaymentSessionBean paymentSession){
		super(FORM, "payment");
		myPaymentSession = paymentSession;
		LOG = Logger.getLogger(PaymentCrudAction.class);
	}
	
	@Override
	protected void preEdit() {
		super.preEdit();
		setForward(FORWARD_EDIT);
		if ("yes".equals(myForm.get("direct"))) {
			setForward(FORWARD_FROM_ORDER);
		}
	}
	
	@Override
	protected ForwardAndMessage doCreate(PaymentDTOEx dto) {
        // this is not an update, it's the previous step of the review
        // payments have no updates (unmodifiable transactions).
        if (dto.getIsRefund() == 1) {
            session.setAttribute(Constants.SESSION_PAYMENT_DTO_REFUND, dto);
        } else {
            session.setAttribute(Constants.SESSION_PAYMENT_DTO, dto);
        }
        
        if ("payout".equals(myForm.get(FIELD_CREATE))){
        	return new ForwardAndMessage(FORWARD_REVIEW_PAYOUT, MESSAGE_REVIEW);
        } else {
        	return new ForwardAndMessage(FORWARD_REVIEW, MESSAGE_REVIEW);
        }
	}
	
	@Override
	protected ForwardAndMessage doSetup() {
        CreditCardDTO ccDto = null;
        AchDTO achDto = null;
        PaymentInfoChequeDTO chequeDto = null;
        
        boolean isEdit = "edit".equals(request.getParameter("submode"));
        
        // if an invoice was selected, pre-populate the amount field
        InvoiceDTO invoiceDto = (InvoiceDTO) session.getAttribute(
                Constants.SESSION_INVOICE_DTO);
        PaymentDTOEx paymentDto = (PaymentDTOEx) session.getAttribute(
                Constants.SESSION_PAYMENT_DTO);

        if (invoiceDto != null) {
            LOG.debug("setting payment with invoice:" + invoiceDto.getId());
            
            myForm.set(FIELD_AMOUNT, invoiceDto.getBalance().toString());
            //paypal can't take i18n amounts
            session.setAttribute("jsp_paypay_amount", invoiceDto.getBalance());
            myForm.set(FIELD_CURRENCY, invoiceDto.getCurrency().getId());
        } else if (paymentDto != null) {
            // this works for both refunds and payouts
            LOG.debug("setting form with payment:" + paymentDto.getId());
            myForm.set(FIELD_ID, paymentDto.getId());
            myForm.set(FIELD_AMOUNT, paymentDto.getAmount().toString());
            setFormDate(FIELD_GROUP_DATE, paymentDto.getPaymentDate());
            myForm.set(FIELD_CURRENCY, paymentDto.getCurrency().getId());
            ccDto = paymentDto.getCreditCard();
            achDto = paymentDto.getAch();
            chequeDto = paymentDto.getCheque();
        } else { // this is not an invoice selected, it's the first call
            LOG.debug("setting payment without invoice");
            // the date might come handy
            setFormDate(FIELD_GROUP_DATE, Calendar.getInstance().getTime());
            // make the default real-time
            myForm.set(FIELD_PROCESS_NOW, new Boolean(true));
            // find out if this is a payment or a refund
        }
        
        
        boolean isRefund = session.getAttribute("jsp_is_refund") != null; 

        // populate the credit card fields with the cc in file
        // if this is a payment creation only
        if (!isRefund && !isEdit && 
                ((String) myForm.get(FIELD_CC_NUMBER)).length() == 0) {
            // normal payment, get the selected user cc
            // if the user has a credit card, put it (this is a waste for
            // cheques, but it really doesn't hurt)
            LOG.debug("getting this user's cc");
            UserDTOEx user = getSessionUser();
            ccDto = user.getCreditCard();
            achDto = user.getAch();
        } 
        
        if (ccDto != null) {
            String ccNumber = ccDto.getNumber();
            // mask cc number
            ccNumber = maskCreditCard(ccNumber);
            myForm.set(FIELD_CC_NUMBER, ccNumber);
            myForm.set(FIELD_CC_NAME, ccDto.getName());
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(ccDto.getCcExpiry());
            myForm.set(FIELD_GROUP_CC_EXPIRY + "_month", String.valueOf(cal.get(
                    GregorianCalendar.MONTH) + 1));
            myForm.set(FIELD_GROUP_CC_EXPIRY + "_year", String.valueOf(cal.get(
                    GregorianCalendar.YEAR)));
        }    
        
        if (achDto != null) {
        	myForm.set(FIELD_ACH_ABA_CODE, achDto.getAbaRouting());
            myForm.set(FIELD_ACH_ACCOUNT_NUMBER, achDto.getBankAccount());
            myForm.set(FIELD_ACH_BANK_NAME, achDto.getBankName());
            myForm.set(FIELD_ACH_ACCOUNT_NAME, achDto.getAccountName());
            myForm.set(FIELD_ACH_ACCOUNT_TYPE, achDto.getAccountType());
        }

        if (chequeDto != null) {
            myForm.set(FIELD_CHEQUE_BANK, chequeDto.getBank());
            myForm.set(FIELD_CHEQUE_NUMBER, chequeDto.getNumber());
            setFormDate(FIELD_GROUP_CHEQUE_DATE, chequeDto.getDate());
        }
        
        ForwardAndMessage result = new ForwardAndMessage(FORWARD_EDIT);
        // if this payment is direct from an order, continue with the
        // page without invoice list
        if (request.getParameter("direct") != null) {
            // the date won't be shown, and it has to be initialized
            setFormDate(FIELD_GROUP_DATE, Calendar.getInstance().getTime());
            myForm.set(FIELD_PAY_METHOD, "cc");
            result = new ForwardAndMessage(FORWARD_FROM_ORDER, MESSAGE_INVOICE_GENERATED);
        }
        
        // if this is a payout, it has its own page
        if (request.getParameter("payout") != null) {
            result = new ForwardAndMessage(FORWARD_PAYOUT);
        } 
        
        // payment edition has a different layout
        if (isEdit) {
        	result = new ForwardAndMessage(FORWARD_UPDATE);
        }
        
        return result;
	}

	@Override
	protected PaymentDTOEx doEditFormToDTO() {
		PaymentDTOEx dto = new PaymentDTOEx();
        // the id, only for payment edits
        dto.setId((Integer) myForm.get(FIELD_ID) == null ? 0 : (Integer) myForm.get(FIELD_ID));
        // set the amount
        dto.setAmount(string2decimal((String) myForm.get(FIELD_AMOUNT)));
        // set the date
        dto.setPaymentDate(parseDate(FIELD_GROUP_DATE, "payment.date"));
        final String payMethod = (String) myForm.get(FIELD_PAY_METHOD);  
        if ("cheque".equals(payMethod)) {
            // create the cheque dto
            PaymentInfoChequeDTO chequeDto = new PaymentInfoChequeDTO();
            chequeDto.setBank((String) myForm.get(FIELD_CHEQUE_BANK));
            chequeDto.setNumber((String) myForm.get(FIELD_CHEQUE_NUMBER));
            chequeDto.setDate(parseDate(FIELD_GROUP_CHEQUE_DATE, "payment.cheque.date"));
            // set the cheque
            dto.setCheque(chequeDto);
            dto.setPaymentMethod(new PaymentMethodDTO(Constants.PAYMENT_METHOD_CHEQUE));
            // validate required fields        
            required(chequeDto.getNumber(),"payment.cheque.number");
            required(chequeDto.getDate(), "payment.cheque.date");
            // cheques now are never process realtime (may be later will support
            // electronic cheques
            dto.setPaymentResult(new PaymentResultDTO(Constants.RESULT_ENTERED));
            session.setAttribute("tmp_process_now", new Boolean(false));                
            
        } else if ("cc".equals(payMethod)) {
            String ccNumber = (String) myForm.get(FIELD_CC_NUMBER);
            boolean masked = false;

            // check if cc number is masked
            if (isMaskedCreditCard(ccNumber)) {
                LOG.debug("cc no. masked; " 
                        + "getting user's existing cc details");
                // try to get existing cc details
                UserDTOEx user = getSessionUser();
                CreditCardDTO existingCcDTO = user.getCreditCard();
                if(existingCcDTO != null) {
                    String existingNumber = existingCcDTO.getNumber();
                    // check that four last digits match
                    if(existingNumber.substring(
                            existingNumber.length() - 4).equals(
                            ccNumber.substring(ccNumber.length() - 4))) {
                        LOG.debug("got a matching masked cc number");
                        masked = true;
                        ccNumber = existingNumber;
                    }
                }
            }

            // do cc validation for non-masked numbers
            if (!masked) {
                validateCreditCard();

                // return if credit card validation failed
                if (!errors.isEmpty()) {
                    return null;
                }
            }

            CreditCardDTO ccDto = new CreditCardDTO();
            ccDto.setNumber(ccNumber);
            ccDto.setName((String) myForm.get(FIELD_CC_NAME));
            myForm.set(FIELD_GROUP_CC_EXPIRY + "_day", "01"); // to complete the date
            ccDto.setCcExpiry(parseDate(FIELD_GROUP_CC_EXPIRY, "payment.cc.date"));
            if (ccDto.getCcExpiry() != null) {
                // the expiry can't be past today
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(ccDto.getCcExpiry());
                cal.add(GregorianCalendar.MONTH, 1); // add 1 month
                if (Calendar.getInstance().getTime().after(cal.getTime())) {
                    errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("creditcard.error.expired", 
                                "payment.cc.date"));
                }
            }
            dto.setCreditCard(ccDto);
            
            // this will be checked when the payment is sent
            session.setAttribute("tmp_process_now", (Boolean) myForm.get(FIELD_PROCESS_NOW));
            // validate required fields        
            required(ccDto.getNumber(), "payment.cc.number");
            required(ccDto.getCcExpiry(), "payment.cc.date");
            required(ccDto.getName(), "payment.cc.name");

            // make sure that the cc is valid before trying to get
            // the payment method from it
            if (errors.isEmpty()) {
                dto.setPaymentMethod(new PaymentMethodDTO(Util.getPaymentMethod(ccDto.getNumber())));
            }

        } else if ("ach".equals(payMethod)) {
        	AchDTO ach = new AchDTO();
        	ach.setAbaRouting((String) myForm.get(FIELD_ACH_ABA_CODE));
        	ach.setBankAccount((String) myForm.get(FIELD_ACH_ACCOUNT_NUMBER));
        	ach.setAccountType((Integer) myForm.get(FIELD_ACH_ACCOUNT_TYPE));
        	ach.setBankName((String) myForm.get(FIELD_ACH_BANK_NAME));
        	ach.setAccountName((String) myForm.get(FIELD_ACH_ACCOUNT_NAME));
        	dto.setAch(ach);
            //this will be checked when the payment is sent
            session.setAttribute("tmp_process_now",  new Boolean(true));

            // since it is one big form for all methods, we need to 
            // validate the required manually
            required(ach.getAbaRouting(), "ach.aba.prompt");
            required(ach.getBankAccount(), "ach.account_number.prompt");
            required(ach.getBankName(), "ach.bank_name.prompt");
            required(ach.getAccountName(), "ach.account_name.prompt");
            
            if (errors.isEmpty()) {
                dto.setPaymentMethod(new PaymentMethodDTO(Constants.PAYMENT_METHOD_ACH));
            }
        }

        // set the customer id selected in the list (not the logged)
        dto.setUserId((Integer) session.getAttribute(Constants.SESSION_USER_ID));
        // specify if this is a normal payment or a refund
        dto.setIsRefund(session.getAttribute("jsp_is_refund") == null ? 0 : 1);
        LOG.debug("refund = " + dto.getIsRefund());
        // set the selected payment for refunds
        if (dto.getIsRefund() == 1) {
            PaymentDTOEx refundPayment = (PaymentDTOEx) session.getAttribute(
                    Constants.SESSION_PAYMENT_DTO); 
            /*
             * Right now, to process a real-time credit card refund it has to be to
             * refund a previously done credit card payment. This could be
             * changed, to say, refund using the customer's credit card no matter
             * how the guy paid initially. But this might be subjet to the
             * processor features.
             * 
             */
            
            ActionError realTimeNoPayment = null;
            boolean processNow = (Boolean) myForm.get(FIELD_PROCESS_NOW);
            if ("cc".equals(payMethod) && processNow){
                if (refundPayment == null || 
                	refundPayment.getCreditCard() == null ||
                    refundPayment.getAuthorization() == null ||
                    !Constants.RESULT_OK.equals(refundPayment.getPaymentResult().getId())) {
                	
                	realTimeNoPayment = new ActionError(//
                			"refund.error.realtimeNoPayment", 
                    		"payment.cc.processNow");
                }
            }
            
            if (realTimeNoPayment != null){
            	errors.add(ActionErrors.GLOBAL_ERROR, realTimeNoPayment);	
            } else {
                dto.setPayment(refundPayment);
            }

            // refunds, I need to manually delete the list, because
            // in the end only the LIST_PAYMENT will be removed
            session.removeAttribute(Constants.SESSION_LIST_KEY + 
                    Constants.LIST_TYPE_REFUND);
        }
        
        // last, set the currency
        //If a related document is
        // set (invoice/payment) it'll take it from there. Otherwise it
        // wil inherite the one from the user
        Integer currencyId = (Integer) myForm.get(FIELD_CURRENCY);
        dto.setCurrency( currencyId != null ? new CurrencyDTO((Integer) myForm.get(FIELD_CURRENCY)) : null);
        if (dto.getCurrency() == null) {
            dto.setCurrency(getUser(dto.getUserId()).getCurrency());
        }
        
        if (errors.isEmpty()) {
            // verify that this entity actually accepts this kind of 
            //payment method
            if (!myPaymentSession.isMethodAccepted((Integer)
                    session.getAttribute(Constants.SESSION_ENTITY_ID_KEY),
                    dto.getPaymentMethod().getId())) {
            	
                errors.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("payment.error.notAccepted", 
                            "payment.method"));
            }
        }

        //LOG.debug("now payment methodId = " + dto.getPaymentMethod().getId());
        LOG.debug("now paymentDto = " + dto);

        return dto;
	}
	
	@Override
	protected ForwardAndMessage doUpdate(PaymentDTOEx dto) {
		ForwardAndMessage result;
        if ("yes".equals(myForm.get("direct"))) {
        	result = new ForwardAndMessage(FORWARD_FROM_ORDER);
        } else {
        	result = new ForwardAndMessage(FORWARD_LIST);
        }
        return result;
	}
	
	@Override
	protected ForwardAndMessage doDelete() {
        PaymentDTOEx paymentDto = (PaymentDTOEx) //
                session.getAttribute(Constants.SESSION_PAYMENT_DTO);
        Integer id = paymentDto.getId();
        myPaymentSession.deletePayment(id);
        return new ForwardAndMessage(FORWARD_DELETED);
	}
	
	@Override
	protected void resetCachedList() {
		session.removeAttribute(Constants.SESSION_LIST_KEY + FORM);
	}
	
	@Override
	public void reset() {
		super.reset();
        session.removeAttribute(Constants.SESSION_INVOICE_DTO);
        session.removeAttribute(Constants.SESSION_PAYMENT_DTO);
	}

	private void validateCreditCard() {
		// set up for cc validation, 
		// (meant for use within Validator framework)

		// from validator.xml
		Arg arg = new Arg();
		arg.setKey("all.prompt.creditCard");
		arg.setPosition(0);
		Field field = new Field();
		field.addArg(arg);
		field.setProperty(FIELD_CC_NUMBER);
		field.setDepends("creditCard");

		// from validator-rules.xml
		ValidatorAction va = new ValidatorAction();
		va.setName("creditCard");
		va.setClassname("org.apache.struts.validator.FieldChecks");
		va.setMethod("validateCreditCard");
		va.setMethodParams("java.lang.Object, "
		        + "org.apache.commons.validator.ValidatorAction, "
		        + "org.apache.commons.validator.Field, "
		        + "org.apache.struts.action.ActionErrors, "
		        + "javax.servlet.http.HttpServletRequest");
		va.setDepends("");
		va.setMsg("errors.creditcard");

		// do cc number validation
		LOG.debug("doing credit card number validation");
		FieldChecks.validateCreditCard(myForm, va, field, errors, request);
	}
	
	private UserDTOEx getSessionUser() {
        return getUser((Integer) session.getAttribute(Constants.SESSION_USER_ID));
	}
	
	private String maskCreditCard(String ccNumber) {
		return CREDIT_CARD_MASK + ccNumber.substring(
		        ccNumber.length() - 4);
	}

	private boolean isMaskedCreditCard(String ccNumber) {
		return ccNumber != null && ccNumber.length() >= 16
		        && ccNumber.startsWith(CREDIT_CARD_MASK);
	}

}
