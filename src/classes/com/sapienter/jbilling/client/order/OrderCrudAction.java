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

package com.sapienter.jbilling.client.order;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.CrudActionBase;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.order.IOrderSessionBean;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.MapPeriodToCalendar;

public class OrderCrudAction extends CrudActionBase<OrderDTO> {

    private static final Logger LOG = Logger.getLogger(OrderCrudAction.class);

    private static final String FORM_ORDER = "order";

    private static final String FORWARD_EDIT = "order_edit";

    private static final String FORWARD_ITEMS = "order_items";

    private static final String FIELD_BILLING_TYPE = "billingType";

    private static final String FIELD_ANTICIPATE_PERIODS = "anticipate_periods";

    private static final String FIELD_NOTES = "notes";

    private static final String FIELD_ADD_NOTES_IN_INVOICE = "chbx_notes";

    private static final String FIELD_OWN_INVOICE = "chbx_own_invoice";

    private static final String FIELD_DF_FM = "chbx_df_fm";

    private static final String FIELD_DUE_DATE_VALUE = "due_date_value";

    private static final String FIELD_DUE_DATE_UNIT_ID = "due_date_unit_id";

    private static final String FIELD_GROUP_NEXT_BILLABLE = "next_billable";

    private static final String FIELD_GROUP_UNTIL = "until";

    private static final String FIELD_GROUP_SINCE = "since";

    private static final String FIELD_GROUP_CYCLE = "cycle";

    private static final String FIELD_NOTIFY = "chbx_notify";

    private static final String FIELD_IS_CURRENT = "chbx_iscurrent";

    private static final String FIELD_PERIOD = "period";

    public OrderCrudAction() {
        super(FORM_ORDER, "order");
    }

    @Override
    protected ForwardAndMessage doSetup() throws RemoteException {
        OrderDTO dto = (OrderDTO) session.getAttribute(Constants.SESSION_ORDER_DTO);

        myForm.set(FIELD_PERIOD, dto.getPeriodId());
        myForm.set(FIELD_NOTIFY, isIntegerTrue(dto.getNotify()));
        setFormDate(FIELD_GROUP_CYCLE, dto.getCycleStarts());
        setFormDate(FIELD_GROUP_SINCE, dto.getActiveSince());
        setFormDate(FIELD_GROUP_UNTIL, dto.getActiveUntil());
        setFormDate(FIELD_GROUP_NEXT_BILLABLE, dto.getNextBillableDay());
        myForm.set(FIELD_DUE_DATE_UNIT_ID, dto.getDueDateUnitId());
        myForm.set(FIELD_DUE_DATE_VALUE, getStringOrNull(dto.getDueDateValue()));
        myForm.set(FIELD_DF_FM, isIntegerTrue(dto.getDfFm()));
        myForm.set(FIELD_OWN_INVOICE, isIntegerTrue(dto.getOwnInvoice()));
        myForm.set(FIELD_ADD_NOTES_IN_INVOICE, isIntegerTrue(dto.getNotesInInvoice()));
        myForm.set(FIELD_NOTES, dto.getNotes());
        myForm.set(FIELD_ANTICIPATE_PERIODS, getStringOrNull(dto.getAnticipatePeriods()));
        myForm.set(FIELD_BILLING_TYPE, dto.getBillingTypeId());
        myForm.set(FIELD_IS_CURRENT, isIntegerTrue(dto.getIsCurrent()));

        return new ForwardAndMessage(FORWARD_EDIT);
    }

    @Override
    protected void preEdit() {
        super.preEdit();
        setForward(FORWARD_EDIT);
    }

    @Override
    protected OrderDTO doEditFormToDTO() throws RemoteException {
        // this is kind of a wierd case. The dto in the session is all
        // it is required to edit.

        OrderDTO summary = (OrderDTO) session.getAttribute(Constants.SESSION_ORDER_SUMMARY);

        OrderPeriodDTO period = new OrderPeriodDTO();
        period.setId((Integer) myForm.get(FIELD_PERIOD));
        summary.setOrderPeriod(period);
        summary.setCycleStarts(parseDate(FIELD_GROUP_CYCLE, "order.prompt.cycleStart"));
        summary.setActiveSince(parseDate(FIELD_GROUP_SINCE, "order.prompt.activeSince"));
        summary.setActiveUntil(parseDate(FIELD_GROUP_UNTIL, "order.prompt.activeUntil"));
        summary.setNextBillableDay(parseDate(FIELD_GROUP_NEXT_BILLABLE,
                "order.prompt.nextBillableDay"));

        OrderBillingTypeDTO type = new OrderBillingTypeDTO();
        type.setId((Integer) myForm.get(FIELD_BILLING_TYPE));
        summary.setOrderBillingType(type);

        summary.setNotify(fromCheckBox(FIELD_NOTIFY));
        summary.setDfFm(fromCheckBox(FIELD_DF_FM));
        summary.setOwnInvoice(fromCheckBox(FIELD_OWN_INVOICE));
        summary.setNotesInInvoice(fromCheckBox(FIELD_ADD_NOTES_IN_INVOICE));
        summary.setIsCurrent(fromCheckBox(FIELD_IS_CURRENT));
        summary.setNotes((String) myForm.get(FIELD_NOTES));

        summary.setAnticipatePeriods(getIntegerFieldValue(FIELD_ANTICIPATE_PERIODS));
        summary.setPeriodStr(getOptionDescription(summary.getPeriodId(),
                Constants.PAGE_ORDER_PERIODS));
        summary.setBillingTypeStr(getOptionDescription(summary.getBillingTypeId(),
                Constants.PAGE_BILLING_TYPE));
        summary.setDueDateUnitId((Integer) myForm.get(FIELD_DUE_DATE_UNIT_ID));
        summary.setDueDateValue(getIntegerFieldValue(FIELD_DUE_DATE_VALUE));

        // return any date validation errors to user
        if (!errors.isEmpty()) {
            return null;
        }

        // if she wants notification, we need a date of expiration
        if (isIntegerTrue(summary.getNotify()) && summary.getActiveUntil() == null) {
            addError("order.error.notifyWithoutDate", "order.prompt.notify");
            return null;
        }

        // this is the original order, needed now for validations
        OrderDTO orderDTO = (OrderDTO) session.getAttribute(Constants.SESSION_ORDER_DTO);

        // the start date will be useful for several validations
        Date start = summary.getActiveSince();
        if (start == null) {
            if (orderDTO != null) {
                start = orderDTO.getCreateDate();
            } else {
                start = Calendar.getInstance().getTime();
            }
        }
        start = Util.truncateDate(start);

        // validate the dates if there is a date of expiration
        if (summary.getActiveUntil() != null) {

            // it has to be grater than the starting date
            if (!summary.getActiveUntil().after(start)) {
                addError("order.error.dates", "order.prompt.activeUntil");
                return null;
            }

            // entities with pro-rating do not do the fraction of a period
            // validation
            IUserSessionBean userSession;
            try {
                userSession = (IUserSessionBean) Context.getBean(
                        Context.Name.USER_SESSION);
            } catch (Exception e1) {
                addError("all.internal");
                LOG.error("Getting pro-rating preference", e1);
                return null;
            }
            boolean useProrating = Integer.parseInt(userSession.getEntityPreference(entityId,
                    Constants.PREFERENCE_USE_PRO_RATING)) == 1;

            // only if it is a recurring order, and the entity does not use
            // pro-rating
            if (!useProrating
                    && !summary.getPeriodId().equals(
                            com.sapienter.jbilling.server.util.Constants.ORDER_PERIOD_ONCE)) {
                // the whole period has to be a multiple of the period unit
                // This is true, until there is support for prorating.
                OrderPeriodDTO myPeriod;
                try {
                    IOrderSessionBean orderSession = (IOrderSessionBean) 
                            Context.getBean(Context.Name.ORDER_SESSION);
                    myPeriod = orderSession.getPeriod(languageId, summary.getPeriodId());
                } catch (Exception e) {
                    throw new SessionInternalError("Validating date periods",
                            OrderCrudAction.class, e);
                }

                GregorianCalendar toTest = new GregorianCalendar();
                toTest.setTime(start);
                while (toTest.getTime().before(summary.getActiveUntil())) {
                    toTest.add(MapPeriodToCalendar.map(myPeriod.getUnitId()), myPeriod.getValue()
                            .intValue());
                }
                if (!toTest.getTime().equals(summary.getActiveUntil())) {
                    LOG.debug("Fraction of a period:" + toTest.getTime() + " until: "
                            + summary.getActiveUntil());
                    addError("order.error.period");
                    return null;
                }
            }
        } // end if active until is present

        // the cycle has to be equal or earlier than the start date
        if (summary.getCycleStarts() != null) {
            if (summary.getCycleStarts().after(start)) {
                addError("order.error.cycleTooGreat");
                return null;
            }
        }

        // if a date was submitted, check that it is >= old date or
        // greater than today if old date is null.
        if (summary.getNextBillableDay() != null) {
            if (orderDTO != null && orderDTO.getNextBillableDay() != null) {
                if (summary.getNextBillableDay().before(
                        Util.truncateDate(orderDTO.getNextBillableDay()))) {

                    // new date is less than old date
                    addError("order.error.nextBillableDay.hasOldDate");
                    return null;
                }
            } else if (!summary.getNextBillableDay().after(Calendar.getInstance().getTime())) {

                // old date doesn't exist and new date is not after todays date
                addError("order.error.nextBillableDay.noOldDate");
                return null;
            }
        } else {
            // else no date was submitted, check that old date isn't null
            if (orderDTO != null && orderDTO.getNextBillableDay() != null) {
                addError("order.error.nextBillableDay.null");
                return null;
            }
        }

        
        
        return summary;
    }

    @Override
    protected ForwardAndMessage doUpdate(OrderDTO dto) throws RemoteException {
        return new ForwardAndMessage(FORWARD_ITEMS);
    }

    @Override
    protected ForwardAndMessage doCreate(OrderDTO dto) throws RemoteException {
        return new ForwardAndMessage(FORWARD_ITEMS);
    }

    @Override
    protected ForwardAndMessage doDelete() throws RemoteException {
        throw new UnsupportedOperationException("Direct delete is not available for orders");
    }

    @Override
    protected void resetCachedList() {
        //
    }

    private int fromCheckBox(String fieldName) {
        return asInteger((Boolean) myForm.get(fieldName));
    }

    private int asInteger(boolean b) {
        return b ? 1 : 0;
    }

    private boolean isIntegerTrue(Integer integer) {
        return integer != null && integer.intValue() == 1;
    }

    private String getStringOrNull(Integer integer) {
        return integer == null ? null : integer.toString();
    }

    private String getOptionDescription(Integer id, String optionType) {
        return getFormHelper().getOptionDescription(id, optionType);
    }

    private void addError(String arg0, String arg1) {
        addError(new ActionError(arg0, arg1));
    }

    private void addError(String msg) {
        addError(new ActionError(msg));
    }

    private void addError(ActionError err) {
        errors.add(ActionErrors.GLOBAL_ERROR, err);
    }

}
