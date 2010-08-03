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

package com.sapienter.jbilling.client.process;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.sapienter.jbilling.client.util.UpdateOnlyCrudActionBase;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.process.IBillingProcessSessionBean;
import com.sapienter.jbilling.server.process.db.BillingProcessConfigurationDTO;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.util.Context;

public class ConfigurationMaintainAction extends
		UpdateOnlyCrudActionBase<BillingProcessConfigurationDTO> {

	private static final String FORM_CONFIGURATION = "configuration";

	private static final String FIELD_GROUP_RUN = "run";
	private static final String FIELD_GENERATE_REPORT = "chbx_generateReport";
	private static final String FIELD_RETRIES = "retries";
	private static final String FIELD_RETRIES_DAYS = "retries_days";
	private static final String FIELD_REPORT_DAYS = "report_days";
	private static final String FIELD_PERIOD_UNIT = "period_unit_id";
	private static final String FIELD_PERIOD_VALUE = "period_unit_value";
	private static final String FIELD_DUE_DATE_UNIT = "due_date_unit_id";
	private static final String FIELD_DUE_DATE_VALUE = "due_date_value";
	private static final String FIELD_DF_FM = "chbx_df_fm";
	private static final String FIELD_RECURRING_ONLY = "chbx_only_recurring";
	private static final String FIELD_INVOICE_DATE = "chbx_invoice_date";
	private static final String FIELD_AUTO_PAYMENT = "chbx_auto_payment";
	private static final String FIELD_MAX_PEROIDS = "maximum_periods";
	private static final String FIELD_APPLY_PAYMENT = "chbx_payment_apply";

	private static final String FORWARD_EDIT = "configuration_edit";

	private static final String MESSAGE_UPDATE_SUCCESS = "process.configuration.updated";

	private final IBillingProcessSessionBean myBillingSession;

	public ConfigurationMaintainAction() {
		super(FORM_CONFIGURATION, "billing process configuration", FORWARD_EDIT);
		try {
			myBillingSession = (IBillingProcessSessionBean) Context.getBean(
                    Context.Name.BILLING_PROCESS_SESSION);
		} catch (Exception e) {
			throw new SessionInternalError(
					"Initializing billing process configuration"
							+ " CRUD action: " + e.getMessage());

		}
	}

	@Override
	protected BillingProcessConfigurationDTO doEditFormToDTO() {
		BillingProcessConfigurationDTO dto = new BillingProcessConfigurationDTO();
		//we need to find the entity by id(entityId)
		dto.setEntity(new CompanyDTO(entityId));

		dto.setRetries(getIntegerFieldValue(FIELD_RETRIES));
		dto.setNextRunDate(parseDate(FIELD_GROUP_RUN,
				"process.configuration.prompt.nextRunDate"));
		dto.setDaysForRetry(getIntegerFieldValue(FIELD_RETRIES_DAYS));
		dto.setDaysForReport(getIntegerFieldValue(FIELD_REPORT_DAYS));
		dto.setGenerateReport(getCheckBoxValue(FIELD_GENERATE_REPORT));
		dto.setDfFm(getCheckBoxValue(FIELD_DF_FM));
		dto.setOnlyRecurring(getCheckBoxValue(FIELD_RECURRING_ONLY));
		dto.setInvoiceDateProcess(getCheckBoxValue(FIELD_INVOICE_DATE));
		dto.setAutoPayment(getCheckBoxValue(FIELD_AUTO_PAYMENT));
		dto.setAutoPaymentApplication(getCheckBoxValue(FIELD_APPLY_PAYMENT));
		
		//we need to find the periodUnit by id
		dto.setPeriodUnit(new PeriodUnitDTO(getIntegerFieldValue(FIELD_PERIOD_UNIT)));
		dto.setPeriodValue(getIntegerFieldValue(FIELD_PERIOD_VALUE));
		dto.setDueDateUnitId(getIntegerFieldValue(FIELD_DUE_DATE_UNIT));
		dto.setDueDateValue(getIntegerFieldValue(FIELD_DUE_DATE_VALUE));
		dto.setMaximumPeriods(getIntegerFieldValue(FIELD_MAX_PEROIDS));

		if (dto.getAutoPayment().intValue() == 0
				&& dto.getRetries().intValue() > 0) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"process.configuration.error.auto"));
		}

		return dto;
	}

	@Override
	protected ForwardAndMessage doUpdate(BillingProcessConfigurationDTO dto) {
		myBillingSession.createUpdateConfiguration(executorId, dto);
		return new ForwardAndMessage(FORWARD_EDIT, MESSAGE_UPDATE_SUCCESS);
	}

	@Override
	protected ForwardAndMessage doSetup() {
		BillingProcessConfigurationDTO dto;
		dto = myBillingSession.getConfigurationDto(entityId);

		setFormDate(FIELD_GROUP_RUN, dto.getNextRunDate());

		myForm.set(FIELD_GENERATE_REPORT, isOne(dto.getGenerateReport()));
		myForm.set(FIELD_DF_FM, isOneMayBeNull(dto.getDfFm()));
		myForm
				.set(FIELD_RECURRING_ONLY, isOneMayBeNull(dto
						.getOnlyRecurring()));
		myForm.set(FIELD_INVOICE_DATE, isOneMayBeNull(dto
				.getInvoiceDateProcess()));
		myForm.set(FIELD_AUTO_PAYMENT, isOneMayBeNull(dto.getAutoPayment()));
		myForm.set(FIELD_APPLY_PAYMENT, isOneMayBeNull(dto
				.getAutoPaymentApplication()));

		myForm.set(FIELD_RETRIES, stringMayBeNull(dto.getRetries()));
		myForm.set(FIELD_RETRIES_DAYS, stringMayBeNull(dto.getDaysForRetry()));
		myForm.set(FIELD_REPORT_DAYS, stringMayBeNull(dto.getDaysForReport()));
		myForm.set(FIELD_MAX_PEROIDS, stringMayBeNull(dto.getMaximumPeriods()));

		myForm.set(FIELD_PERIOD_UNIT, dto.getPeriodUnit().getId() + "");
		myForm.set(FIELD_PERIOD_VALUE, dto.getPeriodValue() + "");
		myForm.set(FIELD_DUE_DATE_UNIT, dto.getDueDateUnitId() + "");
		myForm.set(FIELD_DUE_DATE_VALUE, dto.getDueDateValue() + "");

		return getForwardEdit();
	}

	private Integer getCheckBoxValue(String fieldName) {
		Boolean value = (Boolean) myForm.get(fieldName);
		return value.booleanValue() ? 1 : 0;
	}

	private Boolean isOne(Integer integer) {
		return Integer.valueOf(1).equals(integer);
	}

	private Boolean isOneMayBeNull(Integer integer) {
		return integer == null ? null : isOne(integer);
	}

	private String stringMayBeNull(Integer integer) {
		return integer == null ? null : integer.toString();
	}

}
