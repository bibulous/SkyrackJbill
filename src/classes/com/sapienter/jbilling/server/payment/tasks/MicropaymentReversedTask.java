package com.sapienter.jbilling.server.payment.tasks;

import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.process.task.AbstractSimpleScheduledTask;
import com.sapienter.jbilling.server.util.Context;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;

import java.text.ParseException;
import java.util.Date;

/**
 * Scheduled micropayments process plug-in, executing the reversed direct debit
 * process on a simple schedule.
 *
 * This plug-in accepts the standard {@link AbstractSimpleScheduledTask} plug-in parameters
 * for scheduling. If these parameters are omitted (all parameters are not defined or blank)
 * the plug-in will be scheduled using the jbilling.properties "process.time" and
 * "process.frequency" values.
 *
 * @see com.sapienter.jbilling.server.process.task.AbstractSimpleScheduledTask
 *
 * @author Si Bury
 * @since 05-08-2010
 */
public class MicropaymentReversedTask extends AbstractSimpleScheduledTask {
    private static final Logger LOG = Logger.getLogger(MicropaymentReversedTask.class);

    private static final String PROPERTY_RUN_REVERSED = "process.run_micropayments_check";
    private static final String PROPERTY_PROCESS_TIME = "process.time";
    private static final String PROPERTY_PROCESS_FREQ = "process.frequency";

    public String getTaskName() {
        return "micropayments reversed process - " + getScheduleString();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        _init(context);
//        IMediationSessionBean mediation = (IMediationSessionBean) Context.getBean(Context.Name.MEDIATION_SESSION);

        if (Util.getSysPropBooleanTrue(PROPERTY_RUN_REVERSED)) {
            LOG.info("Starting micropayments reversed at " + new Date());
//            mediation.trigger(getEntityId());
            LOG.info("Ended micropayments reversed at " + new Date());
        }
    }

    /**
     * Returns the scheduled trigger for the micropayments reversed process.
     *  If the plug-in is missing
     * the {@link com.sapienter.jbilling.server.process.task.AbstractSimpleScheduledTask}
     * parameters use the the default jbilling.properties process schedule instead.
     *
     * @return micropayments reversed trigger for scheduling
     * @throws PluggableTaskException thrown if properties or plug-in parameters could not be parsed
     */
    @Override
    public SimpleTrigger getTrigger() throws PluggableTaskException {
        SimpleTrigger trigger = super.getTrigger();

        // trigger start time and frequency using jbilling.properties unless plug-in
        // parameters have been explicitly set to define the mediation schedule
        if (useProperties()) {
            LOG.debug("Scheduling micropayments reversed process from jbilling.properties ...");
            try {
                // set process.time as trigger start time if set
                String start = Util.getSysProp(PROPERTY_PROCESS_TIME);
                if (start != null && !"".equals(start))
                    trigger.setStartTime(DATE_FORMAT.parse(start));

                // set process.frequency as trigger repeat interval if set
                String repeat = Util.getSysProp(PROPERTY_PROCESS_FREQ);
                if (repeat != null && !"".equals(repeat))
                    trigger.setRepeatInterval(Long.parseLong(repeat) * 60 * 1000);

            } catch (ParseException e) {
                throw new PluggableTaskException("Exception parsing process.time for micropayments reversed schedule", e);
            } catch (NumberFormatException e) {
                throw new PluggableTaskException("Exception parsing process.frequency for micropayments reversed schedule", e);
            }
        } else {
            LOG.debug("Scheduling micropayments reversed process using plug-in parameters ...");
        }

        return trigger;
    }

    @Override
    public String getScheduleString() {
        StringBuilder builder = new StringBuilder();

        try {
            builder.append("start: ");
            builder.append(useProperties()
                           ? Util.getSysProp(PROPERTY_PROCESS_TIME)
                           : getParameter(PARAM_START_TIME, DEFAULT_START_TIME));
            builder.append(", ");

            builder.append("end: ");
            builder.append(getParameter(PARAM_END_TIME, DEFAULT_END_TIME));
            builder.append(", ");

            Integer repeat = getParameter(PARAM_REPEAT, DEFAULT_REPEAT);
            builder.append("repeat: ");
            builder.append((repeat == SimpleTrigger.REPEAT_INDEFINITELY ? "infinite" : repeat));
            builder.append(", ");

            builder.append("interval: ");
            builder.append(useProperties()
                           ? Util.getSysProp(PROPERTY_PROCESS_FREQ) + " mins"
                           : getParameter(PARAM_INTERVAL, DEFAULT_INTERVAL) + " hrs");            

        } catch (PluggableTaskException e) {
            LOG.error("Exception occurred parsing plug-in parameters", e);
        }

        return builder.toString();
    }

    /**
     * Returns true if the micropayments reversed process should be scheduled using values from jbilling.properties
     * or if the schedule should be derived from plug-in parameters.
     *
     * @return true if properties should be used for scheduling, false if schedule from plug-ins
     */
    private boolean useProperties() {
        return parameters.get(PARAM_START_TIME) == null
            && parameters.get(PARAM_END_TIME) == null
            && parameters.get(PARAM_REPEAT) == null
            && parameters.get(PARAM_INTERVAL) == null;
    }
}
