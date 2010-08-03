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
package com.sapienter.jbilling.server.mediation;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.sapienter.jbilling.server.mediation.db.MediationRecordStatusDAS;
import com.sapienter.jbilling.server.mediation.db.MediationRecordStatusDTO;
import com.sapienter.jbilling.server.mediation.task.IMediationErrorHandler;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sapienter.jbilling.common.InvalidArgumentException;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.mediation.db.MediationConfiguration;
import com.sapienter.jbilling.server.mediation.db.MediationConfigurationDAS;
import com.sapienter.jbilling.server.mediation.db.MediationMapDAS;
import com.sapienter.jbilling.server.mediation.db.MediationOrderMap;
import com.sapienter.jbilling.server.mediation.db.MediationProcess;
import com.sapienter.jbilling.server.mediation.db.MediationProcessDAS;
import com.sapienter.jbilling.server.mediation.db.MediationRecordDAS;
import com.sapienter.jbilling.server.mediation.db.MediationRecordDTO;
import com.sapienter.jbilling.server.mediation.db.MediationRecordLineDAS;
import com.sapienter.jbilling.server.mediation.db.MediationRecordLineDTO;
import com.sapienter.jbilling.server.mediation.task.IMediationProcess;
import com.sapienter.jbilling.server.mediation.task.IMediationReader;
import com.sapienter.jbilling.server.mediation.task.MediationResult;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskBL;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDAS;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.user.EntityBL;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.util.StopWatch;

/**
 *
 * @author emilc
 **/
@Transactional( propagation = Propagation.REQUIRED )
public class MediationSessionBean implements IMediationSessionBean {

    private static final Logger LOG = Logger.getLogger(MediationSessionBean.class);

    public void trigger() {
        StopWatch watch = new StopWatch("trigger watch");
        watch.start();

        MediationConfigurationDAS cfgDAS = new MediationConfigurationDAS();
        MediationProcessDAS processDAS = new MediationProcessDAS();
        List<String> errorMessages = new ArrayList<String>();
        IMediationSessionBean local = (IMediationSessionBean) Context.getBean(
                Context.Name.MEDIATION_SESSION);

        LOG.debug("Running mediation trigger.");

        try {
            EntityBL entityBL = new EntityBL();

            // loop over all the entities
            for (Integer entityId : entityBL.getAllIDs()) {
                LOG.debug("Processing entity " + entityId);
                // get only once the task that will be needed to normalize records
                PluggableTaskManager<IMediationProcess> tm =
                        new PluggableTaskManager<IMediationProcess>(entityId,
                        Constants.PLUGGABLE_TASK_MEDIATION_PROCESS);
                IMediationProcess processTask = tm.getNextClass();
                if (processTask == null) {
                    LOG.debug("Entity " + entityId + " does not have a mediation process plug-in");
                    continue;
                }
                // find the root user of this entity. It will be the executor for the order updates
                Integer executorId = entityBL.getRootUser(entityId);
                // now process this entity
                // go over each mediation configuration. An entity can opt out from 
                // mediation by simply not having any configuration present
                for (MediationConfiguration cfg : cfgDAS.findAllByEntity(entityId)) {
                    LOG.debug("Now using configuration " + cfg);
                    PluggableTaskBL<IMediationReader> taskManager =
                            new PluggableTaskBL<IMediationReader>();
                    taskManager.set(cfg.getPluggableTask());
                    IMediationReader reader = taskManager.instantiateTask();

                    if (reader.validate(errorMessages)) {
                        // there is going to be records processed from this configuration
                        // create a new process row. This happends in its own transactions
                        // so it needs to be brought to the persistant context here again
                        MediationProcess process = local.createProcessRecord(cfg);

                        for (List<Record> thisGroup : reader) {
                            LOG.debug("Now processing " + thisGroup.size() + " records.");
                            local.normalizeRecordGroup(processTask, executorId, process, thisGroup, entityId, cfg);
                        }

                        // save the information about this just ran mediation process in
                        // the mediation_process table
                        processDAS.reattach(process);
                        process.setEndDatetime(Calendar.getInstance().getTime());
                    } else {
                        LOG.error("skipping invalid reader " + cfg.getPluggableTask() +
                                " error " + errorMessages);
                    }
                }
            }
        } catch (Exception e) {
            throw new SessionInternalError("Exception in mediation trigger",
                    MediationSessionBean.class, e);
        }

        if (!errorMessages.isEmpty()) {
            StringBuffer buf = new StringBuffer("Wrong configuration of reader plugin\n");
            for (String message : errorMessages) {
                buf.append("ERROR: " + message + "\n");
            }
            throw new SessionInternalError(buf.toString());
        }

        watch.stop();
        LOG.debug("Mediation process done. Duration (mls):" + watch.getTotalTimeMillis());
    }

    /**
     * Needs to be in its own transaction, so it gets created right away
     */
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public MediationProcess createProcessRecord(MediationConfiguration cfg) {
        MediationProcessDAS processDAS = new MediationProcessDAS();
        MediationProcess process = new MediationProcess();
        process.setConfiguration(cfg);
        process.setStartDatetime(Calendar.getInstance().getTime());
        process.setOrdersAffected(0);
        process = processDAS.save(process);
        return process;
    }

    public List<MediationProcess> getAll(Integer entityId) {
        MediationProcessDAS processDAS = new MediationProcessDAS();
        List<MediationProcess> result = processDAS.findAllByEntity(entityId);
        processDAS.touch(result);
        return result;

    }

    public List<MediationConfiguration> getAllConfigurations(Integer entityId) {
        MediationConfigurationDAS cfgDAS = new MediationConfigurationDAS();
        List<MediationConfiguration> result = cfgDAS.findAllByEntity(entityId);

        return result;
    }

    public void createConfiguration(MediationConfiguration cfg) {
        MediationConfigurationDAS cfgDAS = new MediationConfigurationDAS();

        cfg.setCreateDatetime(Calendar.getInstance().getTime());
        cfgDAS.save(cfg);

    }

    public List updateAllConfiguration(Integer executorId, List<MediationConfiguration> configurations)
            throws InvalidArgumentException {
        MediationConfigurationDAS cfgDAS = new MediationConfigurationDAS();
        List<MediationConfiguration> retValue = new ArrayList<MediationConfiguration>();
        try {

            for (MediationConfiguration cfg : configurations) {
                // if the configuration is new, the task needs to be loaded
                if (cfg.getPluggableTask().getEntityId() == null) {
                    PluggableTaskDAS pt = (PluggableTaskDAS) Context.getBean(Context.Name.PLUGGABLE_TASK_DAS);
                    PluggableTaskDTO task = pt.find(cfg.getPluggableTask().getId());
                    if (task != null && task.getEntityId().equals(cfg.getEntityId())) {
                        cfg.setPluggableTask(task);
                    } else {
                        throw new InvalidArgumentException("Task not found or " +
                                "entity of pluggable task is not the same when " +
                                "creating a new mediation configuration", 1);
                    }
                }
                retValue.add(cfgDAS.save(cfg));
            }
            return retValue;
        } catch (EntityNotFoundException e1) {
            throw new InvalidArgumentException("Wrong data saving mediation configuration", 1, e1);
        } catch (InvalidArgumentException e2) {
            throw new InvalidArgumentException(e2);
        } catch (Exception e) {
            throw new SessionInternalError("Exception updating mediation configurations ", MediationSessionBean.class, e);
        }
    }

    public void delete(Integer executorId, Integer cfgId) {
        MediationConfigurationDAS cfgDAS = new MediationConfigurationDAS();

        cfgDAS.delete(cfgDAS.find(cfgId));
        EventLogger.getInstance().audit(executorId, null,
                                        Constants.TABLE_MEDIATION_CFG, cfgId,
                                        EventLogger.MODULE_MEDIATION, EventLogger.ROW_DELETED, null,
                                        null, null);
    }

    /**
     * Calculation number of records for each of the existing mediation record statuses
     *
     * @param entityId EntityId for searching mediationRecords
     * @return map of mediation status as a key and long value as a number of records whit given status
     */
    public Map<MediationRecordStatusDTO, Long> getNumberOfRecordsByStatuses(Integer entityId) {
        MediationRecordDAS recordDas = new MediationRecordDAS();
        MediationRecordStatusDAS recordStatusDas = new MediationRecordStatusDAS();
        Map<MediationRecordStatusDTO, Long> resultMap = new HashMap<MediationRecordStatusDTO, Long>();
        List<MediationRecordStatusDTO> statuses = recordStatusDas.findAll();

        //propagate proxy objects for using out of the transaction
        recordStatusDas.touch(statuses);
        for (MediationRecordStatusDTO status : statuses) {
            Long recordsCount = recordDas.countMediationRecordsByEntityIdAndStatus(entityId, status);
            resultMap.put(status, recordsCount);
        }
        return resultMap;
    }

    public boolean hasBeenProcessed(MediationProcess process, Record record) {
        MediationRecordDAS recordDas = new MediationRecordDAS();
        
        // validate that this group has not been already processed
        if (recordDas.processed(record.getKey())) {
            LOG.debug("Detected duplicated of record: " + record.getKey());
            return true;
        }
        LOG.debug("Detected record as a new event: " + record.getKey());

        // assign to record DONE_AND_BILLABLE status as default before processing
        // after actual processing it will be updated
        MediationRecordStatusDTO status = new MediationRecordStatusDAS().find(Constants.MEDIATION_RECORD_STATUS_DONE_AND_BILLABLE);
        MediationRecordDTO dbRecord = new MediationRecordDTO(record.getKey(),
                                                             Calendar.getInstance().getTime(),
                                                             process,
                                                             status);
        recordDas.save(dbRecord);
        recordDas.flush();

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void normalizeRecordGroup(IMediationProcess processTask, Integer executorId,
                                     MediationProcess process, List<Record> thisGroup, Integer entityId,
                                     MediationConfiguration cfg) throws TaskException {

        StopWatch groupWatch = new StopWatch("group full watch");
        groupWatch.start();
        
        LOG.debug("Normalizing " + thisGroup.size() + " records ...");

        // this process came from a different transaction (persistent context)
        new MediationProcessDAS().reattachUnmodified(process);

        // validate that these records have not been already processed
        for (Iterator<Record> it = thisGroup.iterator(); it.hasNext();) {
            if (hasBeenProcessed(process, it.next())) it.remove();
        }

        if (thisGroup.size() == 0) {
            return; // it could be that they all have been processed already
        }

        ArrayList<MediationResult> results = new ArrayList<MediationResult>(0);

        // call the plug-in to resolve these records
        StopWatch rulesWatch = new StopWatch("rules watch");
        rulesWatch.start();
        processTask.process(thisGroup, results, cfg.getName());
        rulesWatch.stop();
        
        LOG.debug("Processing " + thisGroup.size()
                + " records took: " + rulesWatch.getTotalTimeMillis() + "ms,"
                + " or " + new Double(thisGroup.size()) / rulesWatch.getTotalTimeMillis() * 1000D + " records/sec");
        
        // go over the results
        for (MediationResult result : results) {
            if (!result.isDone()) {
                // this is an error, the rules failed somewhere because the
                // 'done' flag is still false.
                LOG.debug("Record result is not done");

                // errors presented, status of record should be updated
                assignStatusToMediationRecord(result.getRecordKey(),
                                              new MediationRecordStatusDAS().find(Constants.MEDIATION_RECORD_STATUS_ERROR_DETECTED));

                // call error handler for mediation errors
                handleMediationErrors(findRecordByKey(thisGroup, result.getRecordKey()),
                                      resolveMediationResultErrors(result),
                                      entityId);

            } else if (!result.getErrors().isEmpty()) {
                // There are some user-detected errors
                LOG.debug("Record result is done with errors");

                //done, but errors assigned by rules. status of record should be updated
                assignStatusToMediationRecord(result.getRecordKey(),
                                              new MediationRecordStatusDAS().find(Constants.MEDIATION_RECORD_STATUS_ERROR_DECLARED));
                // call error handler for rules errors
                handleMediationErrors(findRecordByKey(thisGroup, result.getRecordKey()),
                                      result.getErrors(),
                                      entityId);
            } else {
                // this record was process without any errors
                LOG.debug("Record result is done");

                if (result.getLines() == null || result.getLines().isEmpty()) {
                    //record was processed, but order lines was not affected
                    //now record has status DONE_AND_BILLABLE, it should be changed
                    assignStatusToMediationRecord(result.getRecordKey(),
                                                  new MediationRecordStatusDAS().find(Constants.MEDIATION_RECORD_STATUS_DONE_AND_NOT_BILLABLE));
                    //not needed to update order affected or lines in this case

                } else {
                    //record has status DONE_AND_BILLABLE, only needed to save processed lines
                    process.setOrdersAffected(process.getOrdersAffected() + result.getLines().size());

                    // relate this order with this process
                    MediationOrderMap map = new MediationOrderMap();
                    map.setMediationProcessId(process.getId());
                    map.setOrderId(result.getCurrentOrder().getId());

                    MediationMapDAS mapDas = new MediationMapDAS();
                    mapDas.save(map);

                    // add the record lines
                    saveEventRecordLines(result.getDiffLines(), new MediationRecordDAS().find(result.getRecordKey()),
                                         result.getEventDate(),
                                         result.getDescription());
                }
            }
        }

        groupWatch.stop();
        LOG.debug("Processing the group took: " + groupWatch.getTotalTimeMillis() + "ms");
    }

    public void saveEventRecordLines(List<OrderLineDTO> newLines, MediationRecordDTO record, Date eventDate,
                                     String description) {
        
        MediationRecordLineDAS mediationRecordLineDas = new MediationRecordLineDAS();

        for (OrderLineDTO line : newLines) {
            MediationRecordLineDTO recordLine = new MediationRecordLineDTO();

            recordLine.setEventDate(eventDate);
            OrderLineDTO dbLine = new OrderLineDAS().find(line.getId());
            recordLine.setOrderLine(dbLine);
            recordLine.setAmount(line.getAmount());
            recordLine.setQuantity(line.getQuantity());
            recordLine.setRecord(record);
            recordLine.setDescription(description);

            recordLine = mediationRecordLineDas.save(recordLine);
            // no need to link to the parent record. The association is completed already
            // record.getLines().add(recordLine);
        }
    }

    public List<MediationRecordLineDTO> getEventsForOrder(Integer orderId) {
        List<MediationRecordLineDTO> events = new MediationRecordLineDAS().getByOrder(orderId);
        for (MediationRecordLineDTO line : events) {
            line.toString(); //as a touch
        }
        return events;
    }
    
    public List<MediationRecordDTO> getMediationRecordsByMediationProcess(Integer mediationProcessId) {
        return new MediationRecordDAS().findByProcess(mediationProcessId);
    }

    private void assignStatusToMediationRecord(String key, MediationRecordStatusDTO status) {
        MediationRecordDAS recordDas = new MediationRecordDAS();
        MediationRecordDTO recordDto = recordDas.findNow(key);
        if (recordDto != null) {
            recordDto.setRecordStatus(status);
            recordDas.save(recordDto);
        } else {
            LOG.debug("Mediation record with key=" + key + " not found");
        }
    }

    private List<String> resolveMediationResultErrors(MediationResult result) {
        List<String> errors = new LinkedList<String>();
        if (result.getLines() == null || result.getLines().isEmpty()) {
            errors.add("JB-NO_LINE");
        }
        if (result.getDiffLines() == null || result.getDiffLines().isEmpty()) {
            errors.add("JB-NO_DIFF");
        }
        if (result.getCurrentOrder() == null) {
            errors.add("JB-NO_ORDER");
        }
        if (result.getUserId() == null) {
            errors.add("JB-NO_USER");
        }
        if (result.getCurrencyId() == null) {
            errors.add("JB-NO_CURRENCY");
        }
        if (result.getEventDate() == null) {
            errors.add("JB-NO_DATE");
        }
        errors.addAll(result.getErrors());
        return errors;
    }

    private Record findRecordByKey(List<Record> records, String key) {
        for (Record r : records) {
            if (r.getKey().equals(key)) {
                return r;
            }
        }
        return null;
    }

    private void handleMediationErrors(Record record,
                                       List<String> errors,
                                       Integer entityId) {
        if (record == null) return;
        StopWatch watch = new StopWatch("saving errors watch");
        watch.start();
        LOG.debug("Saving mediation result errors: " + errors.size());

        try {
            PluggableTaskManager<IMediationErrorHandler> tm = new PluggableTaskManager<IMediationErrorHandler>(entityId,
                    Constants.PLUGGABLE_TASK_MEDIATION_ERROR_HANDLER);
            IMediationErrorHandler errorHandler;
            // iterate through all error handlers for current entityId
            // and process errors
            while ((errorHandler = tm.getNextClass()) != null) {
                try {
                    errorHandler.process(record, errors, new Date());
                } catch (TaskException e) {
                    // exception catched for opportunity of processing errors by other handlers
                    // and continue mediation process for other records
                    // TO-DO: check requirements about error handling in that case
                    LOG.error(e);
                }
            }

        } catch (PluggableTaskException e) {
            LOG.error(e);
            // it's possible plugin configuration exception
            // TO-DO: check requirements about error handling
            // may be rethrow exception
        }

        watch.stop();
        LOG.debug("Saving mediation result errors done. Duration (mls):" + watch.getTotalTimeMillis());
    }
}
