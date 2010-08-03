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

package com.sapienter.jbilling.server.process;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.CurrencyBL;
import com.sapienter.jbilling.server.payment.db.PaymentMethodDAS;
import com.sapienter.jbilling.server.process.db.BillingProcessDAS;
import com.sapienter.jbilling.server.process.db.BillingProcessDTO;
import com.sapienter.jbilling.server.process.db.ProcessRunDAS;
import com.sapienter.jbilling.server.process.db.ProcessRunDTO;
import com.sapienter.jbilling.server.process.db.ProcessRunTotalDAS;
import com.sapienter.jbilling.server.process.db.ProcessRunTotalDTO;
import com.sapienter.jbilling.server.process.db.ProcessRunTotalPmDAS;
import com.sapienter.jbilling.server.process.db.ProcessRunTotalPmDTO;

public class BillingProcessRunBL {
    private ProcessRunDAS processRunDas = null;
    private ProcessRunTotalDAS processRunTotalDas = null;
    private ProcessRunTotalPmDAS billingProcessRunTotalPmDas = null;
    private ProcessRunDTO billingProcessRun = null;
    private static final Logger LOG = Logger.getLogger(BillingProcessRunBL.class);

    public class DateComparator implements Comparator {

        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Object o1, Object o2) {
            ProcessRunDTO a = (ProcessRunDTO) o1;
            ProcessRunDTO b = (ProcessRunDTO) o2;
        
            if (a.getStarted().after(b.getStarted())) {
                return 1;
            } else if (a.getStarted().before(b.getStarted())) {
                return -1;
            }
            return 0;
        }

    }
    
    public BillingProcessRunBL(Integer billingProcessRunId) {
        init();
        set(billingProcessRunId);
    }
    
    public BillingProcessRunBL() {
        init();
    }
    
    public BillingProcessRunBL(ProcessRunDTO run)  {
        init();
        billingProcessRun = run;
    }
    
    private void init() {
        processRunDas = new ProcessRunDAS();

        processRunTotalDas = new ProcessRunTotalDAS();

        billingProcessRunTotalPmDas = new ProcessRunTotalPmDAS();

    }

    public ProcessRunDTO getEntity() {
        return billingProcessRun;
    }
    
    public void set(Integer id) {
        billingProcessRun = processRunDas.find(id);
    }

    /**
     * Finds the run based on the process id. Assumes that there is
     * only one run associated with the process.
     * This method is called asynch by the MDBs.
     * @param id
     */
    public void setProcess(Integer id) {
        BillingProcessBL bl = new BillingProcessBL(id);
        if (bl.getEntity().getProcessRuns().size() != 1) {
            throw new SessionInternalError("Process " + id +
                        " should have 1 run. It has " + bl.getEntity().getProcessRuns().size());
        } else {
            billingProcessRun = bl.getEntity().getProcessRuns().iterator().next();
        }
    }
    
    public Integer create(BillingProcessDTO process, Date runDate) {
        if (runDate == null) {
            throw new SessionInternalError("run date can't be null");
        }

        billingProcessRun = processRunDas.create(process, runDate, 0);
        return billingProcessRun.getId();
    }
    
    /**
     * Adds the payment total to the run totals
     * @param currencyId
     * @param methodId
     * @param total
     * @param ok
     */
    public void updateNewPayment(Integer currencyId, Integer methodId,
            BigDecimal total, boolean ok) {
        // update the payments total
        ProcessRunTotalDTO totalRow = findOrCreateTotal(currencyId);

        BigDecimal tmpValue = null;
        if (ok) {
            totalRow.setTotalPaid(totalRow.getTotalPaid().add(total));

            // the payment is good, update the method total as well
            ProcessRunTotalPmDTO pm = findOrCreateTotalPM(methodId, totalRow);
            pm.setTotal(pm.getTotal().add(total));

            // link it to the payment method table
            PaymentMethodDAS paymentMethodHome = new PaymentMethodDAS();
            pm.setPaymentMethod(paymentMethodHome.find(methodId));
        } else {
            totalRow.setTotalNotPaid(totalRow.getTotalNotPaid().add(total));
        }
    }

    
    /**
     * Adds an invoice to the run totals
     */
    public void updateTotals(Integer billingProcessId) {

        for (Iterator it = new BillingProcessDAS().getCountAndSum(billingProcessId); it.hasNext();) {
            Object[] row = (Object[]) it.next();
            // add the total to the total invoiced
            ProcessRunTotalDTO totalRow = findOrCreateTotal((Integer) row[2]);

            billingProcessRun.setInvoicesGenerated(billingProcessRun.getInvoicesGenerated() + ((Long) row[0]).intValue());
            totalRow.setTotalInvoiced(((BigDecimal) row[1]));
            LOG.debug("updating invoice run total version " + totalRow.getVersionNum());
        }
    }

    private ProcessRunTotalDTO findOrCreateTotal(Integer currencyId) {
        ProcessRunTotalDTO ret = processRunTotalDas.getByCurrency(billingProcessRun, currencyId);
        
        if (ret == null) { // not present for this currency
            ret = processRunTotalDas.create(billingProcessRun, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, currencyId);
        }
        return ret;
    }

    private ProcessRunTotalPmDTO findOrCreateTotalPM(Integer methodId, ProcessRunTotalDTO total) {
        ProcessRunTotalPmDTO ret = billingProcessRunTotalPmDas.getByMethod(methodId, total);

        if (ret == null) { // not present for this currency
            ret = billingProcessRunTotalPmDas.create(BigDecimal.ZERO);
            // link it to the total row
            total.getTotalsPaymentMethod().add(ret);
            ret.setProcessRunTotal(total);
        }
        return ret;
    }

    // called when the run is over, to update the dates only
    public void updateFinished() {
        // get the very latest version
        billingProcessRun = processRunDas.findForUpdate(billingProcessRun.getId());
        billingProcessRun.setFinished(Calendar.getInstance().getTime());
        LOG.debug("updating run " + billingProcessRun.getId() +" version " + billingProcessRun.getVersionNum());
        billingProcessRun = processRunDas.save(billingProcessRun);
    }
    
    public void updatePaymentsFinished() {
        // get the very latest version
        billingProcessRun = processRunDas.findForUpdate(billingProcessRun.getId());
        billingProcessRun.setPaymentFinished(Calendar.getInstance().getTime());
        LOG.debug("updating payments run " + billingProcessRun.getId() +" version " + billingProcessRun.getVersionNum());
        billingProcessRun = processRunDas.save(billingProcessRun);
    }
    
    
    public BillingProcessRunDTOEx getDTO(Integer language) {

        BillingProcessRunDTOEx dto = new BillingProcessRunDTOEx();
        
        dto.setId(billingProcessRun.getId());
        dto.setFinished(billingProcessRun.getFinished());
        dto.setInvoicesGenerated(billingProcessRun.getInvoicesGenerated());
        dto.setStarted(billingProcessRun.getStarted());
        dto.setRunDate(billingProcessRun.getRunDate());
        dto.setPaymentFinished(billingProcessRun.getPaymentFinished());
        // now the totals
        if (!billingProcessRun.getProcessRunTotals().isEmpty()) {
            for (Iterator tIt = billingProcessRun.getProcessRunTotals().iterator(); 
                    tIt.hasNext();) {
                ProcessRunTotalDTO totalRow = 
                        (ProcessRunTotalDTO) tIt.next();
                BillingProcessRunTotalDTOEx totalDto = getTotalDTO(totalRow,
                        language);
                dto.getTotals().add(totalDto);
            }
        }
 
        return dto;
    }
    
    public BillingProcessRunTotalDTOEx getTotalDTO(
            ProcessRunTotalDTO row, Integer languageId) {
        BillingProcessRunTotalDTOEx retValue = 
                new BillingProcessRunTotalDTOEx();
        retValue.setCurrency(row.getCurrency());
        retValue.setId(row.getId());
        retValue.setTotalInvoiced(row.getTotalInvoiced());
        retValue.setTotalNotPaid(row.getTotalNotPaid());
        retValue.setTotalPaid(row.getTotalPaid());
        
        // now go over the totals by payment method
        Hashtable totals = new Hashtable();
        for (Iterator it = row.getTotalsPaymentMethod().iterator(); 
                it.hasNext();) {
            ProcessRunTotalPmDTO pmTotal =
                    (ProcessRunTotalPmDTO) it.next();
            totals.put(pmTotal.getPaymentMethod().getDescription(languageId),
                    pmTotal.getTotal());
                    
        }
        retValue.setPmTotals(totals);
        
        // add the currency name, it's handy on the client side
        CurrencyBL currency = new CurrencyBL(retValue.getCurrency().getId());
        retValue.setCurrencyName(currency.getEntity().getDescription(
                languageId));
        
        return retValue;
    }

    public void updatePaymentsStatistic(Integer runId) {
        BillingProcessRunBL run = new BillingProcessRunBL(runId);
        for (Iterator it = new BillingProcessDAS().getSuccessfulProcessCurrencyMethodAndSum(run.getEntity().getBillingProcess().getId()); it.hasNext();) {
            Object[] row = (Object[]) it.next();
            run.updateNewPayment((Integer) row[0], (Integer) row[1], (BigDecimal) row[2], true);
        }

        for (Iterator it = new BillingProcessDAS().getFailedProcessCurrencyAndSum(run.getEntity().getBillingProcess().getId()); it.hasNext();) {
            Object[] row = (Object[]) it.next();
            run.updateNewPayment((Integer) row[0], null, (BigDecimal) row[1], false);
        }
    }
}
