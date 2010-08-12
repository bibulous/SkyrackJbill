package com.sapienter.jbilling.server.payment.tasks;

import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.invoice.db.InvoiceStatusDTO;
import com.sapienter.jbilling.server.payment.PaymentAuthorizationBL;
import com.sapienter.jbilling.server.payment.PaymentBL;
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.db.PaymentAuthorizationDTO;
import com.sapienter.jbilling.server.payment.db.PaymentDTO;
import com.sapienter.jbilling.server.payment.db.PaymentInvoiceMapDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.process.task.AbstractSimpleScheduledTask;
import com.sapienter.jbilling.server.util.Context;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

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

    // mandatory parameters
	public static final String PARAMETER_ACCESSKEY = "access_key";
	public static final String PARAMETER_TESTMODE = "test_mode";
	private int testMode = 1;
	private static final int CONNECTION_TIME_OUT = 10000; // in millisec
	//private static final String PARAMETER_WEB_URL = "https://webservices.micropayment.de/public/debit/v1.4/nvp/";
	private static final String PARAMETER_WEB_URL = "web_url";
	public static final String PARAMETER_PROJECT_CODE = "project_code";
	
    // optional parameters
	private String customerId = "JB_CUSTOMER_ID1";
	public static final String PARAMETER_FREE_PARAMS = "jb";
	private static final int REPLY_TIME_OUT = 30000; // in millisec
	private String sessionId = "JB_CUSTOMER_ID1_TEST";
	public static final String PARAMETER_PROCESSOR_NAME = "processor_name";
	private String processorName = "MicropaymentReversedTask";

	//default fields for testing.
	String payloadData = "";
	String rootUrl = PARAMETER_WEB_URL+"?";
	private String project = "projectCode";
	private String currency = "EUR";
	private String title = "Fax_usage";
	private String payText = "Voxtelo_Jbilling_Test_Buchung";
	private String ip = "tenios.de";
	private int amount = 0;

	private static final String RESPONSE_CODE_APPROVED = "APPROVED";
	private static final String RESPONSE_CODE_REVERSED = "REVERSED";
	private static final String RESPONSE_CODE_CHARGED = "CHARGED";
	private Date createDatetime;
	private long millisBack = (long)1000*60*60*24*7*6;
	private PaymentAuthorizationDTO paDto = null;
	private PaymentAuthorizationBL paBl = null;

    
    public String getTaskName() {
        return "micropayments reversed process - " + getScheduleString();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        _init(context);
//        Could extract to a session bean with interface if necessary 

        if (Util.getSysPropBooleanTrue(PROPERTY_RUN_REVERSED)) {
            LOG.info("Starting micropayments reversed at " + new Date());
            checkReversed(getEntityId());
            confirmCharged(getEntityId());
            LOG.info("Ended micropayments reversed at " + new Date());
        }
    }

    private void confirmCharged(Integer entityId) {
		// Update valid charged payment_authorizations that shouldn't be reversed.
    	this.paBl = new PaymentAuthorizationBL();
    	this.createDatetime = new Date(System.currentTimeMillis()-millisBack);
    	String dateTimeComparator = "<=";
    	Collection paDtos = paBl.findByDateAndCode(RESPONSE_CODE_APPROVED, createDatetime, dateTimeComparator, entityId);
    	  
    	Iterator iter = paDtos.iterator();
    	while (iter.hasNext()) {
    		this.paDto = (PaymentAuthorizationDTO)(iter.next());
            LOG.debug("confirmCharged paDto being checked = " +
    				paDto.getId());
            //Check the status with the API.
            PaymentAuthorizationDTO response = sessionGet(paDto);
            if (response.getApprovalCode().equalsIgnoreCase(RESPONSE_CODE_CHARGED)) {
            	updatePaymentAuthoriaztion(response);
            }
    	}
		
	}

	private void updatePaymentAuthoriaztion(PaymentAuthorizationDTO response) {
		// Update the payment_authorization record
		LOG.debug("Starting updatePaymentAuthoriaztion for paDto = " + response.toString());
		//Save updated PaymentAuthorizationDTO
		paBl.save(response);
		LOG.debug("Ended updatePaymentAuthoriaztion for paDto = " + response.toString());
	}

	private void checkReversed(Integer entityId) {
		// TODO Should really extract a separate Micropayments class and use an instance of it.
    	// Particularly for constants and method calls
		//Get Records to check for this entity.
    	this.paBl = new PaymentAuthorizationBL();
    	this.createDatetime = new Date(System.currentTimeMillis()-millisBack);
    	String dateTimeComparator = ">=";
    	//Collection paDtos = paBl.findByDateAndCode(approvalCode, createDatetime, entityId);
    	List paDtos = paBl.findByDateAndCode(RESPONSE_CODE_APPROVED, createDatetime, dateTimeComparator, entityId);
  
    	//Iterator iter = paDtos.iterator();
    	//while (iter.hasNext()) {
    	for (int f = 0; f < paDtos.size() ; f++) {
            //payment = (PaymentDTO) payments.get(f);
    		//PaymentAuthorizationDTO paDto = (PaymentAuthorizationDTO)(iter.next());
            this.paDto = (PaymentAuthorizationDTO)(paDtos.get(f));
            LOG.debug("MicropaymentReversed.checkReversed paDto being checked = " +
    				paDto.getId());
            //Check the status with the API.
            PaymentAuthorizationDTO response = sessionGet(paDto);
            if (response.getApprovalCode().equalsIgnoreCase(RESPONSE_CODE_REVERSED)) {
            	reversePayment(response);
            }
    	}
		
	}


	private void reversePayment(PaymentAuthorizationDTO response) {
		//Now reverse the payment.
		LOG.debug("Starting reversePayment for paDto = " + response.toString());
		//First save updated PaymentAuthorizationDTO
		//paBl.save(response);
		PaymentAuthorizationBL paymentAuthorizationBL = new PaymentAuthorizationBL(response);
		paymentAuthorizationBL.save(response);
		//Next reverse payment.  Set balance to amount, amount to zero, reset invoice
		
		PaymentDTO paymentDto = response.getPayment();
		BigDecimal reverseAmount = paymentDto.getAmount();
		BigDecimal reverseBalance = paymentDto.getBalance();
		//Not needed really BigDecimal newBalance = reverseBalance + reverseAmount;
		paymentDto.setAmount(new BigDecimal(0));
		paymentDto.setBalance(reverseAmount);
		LOG.debug("reversed payment: reverseAmount = " + reverseAmount);
		LOG.debug("reversed payment: reverseBalance = " + reverseBalance);
		PaymentBL paymentBl = new PaymentBL(); 
		paymentBl.save(paymentDto);
		//Get invoice
		Set<PaymentInvoiceMapDTO> invoicesMap = paymentDto.getInvoicesMap();
		Iterator iter = invoicesMap.iterator();
		while (iter.hasNext()) {
			PaymentInvoiceMapDTO paymentInvoiceMapDto = (PaymentInvoiceMapDTO)(iter.next());
			InvoiceDTO invoice = paymentInvoiceMapDto.getInvoiceEntity();
			BigDecimal invoiceBalance = invoice.getBalance();
			BigDecimal carriedBalance = invoice.getCarriedBalance();
		
			//TODO  get int for status to be set to failed
			int reversedStatus = 1;
			InvoiceStatusDTO invoiceStatus = new InvoiceStatusDTO();
			invoiceStatus.setId(reversedStatus);
			invoice.setInvoiceStatus(invoiceStatus);
			//set process to 1 so invoice is picked up by next billing run.
			invoice.setToProcess(1);
			//Finally save updated invoice.
			InvoiceBL invoiceBl = new InvoiceBL();
			invoiceBl.save(invoice);
		}
		LOG.debug("Ended reversePayment for paDto = " + response.toString());
		//TODO If there's anything else to do do it here.  Ideally let next billing process
		//pick up invoice and deal with as per usual.
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
    
    private PaymentAuthorizationDTO sessionGet(PaymentAuthorizationDTO paDto) {
    	
    	this.sessionId = paDto.getTransactionId();
		// construct payloadData.
		payloadData = "";
		payloadData+="action=sessionGet";
		//  TODO may parameterise methods? +ensureGetParameter(PARAMETER_CUSTOMER_CREATE)+"\n";
		payloadData+="&accessKey="+parameters.get(PARAMETER_ACCESSKEY);
		payloadData+="&testMode="+parameters.get(PARAMETER_TESTMODE);
		payloadData+="&sessionId="+sessionId;
		LOG.debug("sessionGet payloadData = "+ payloadData);
		
		//TODO need to call PG and set resultValues based on response.
		//Also need to handle errors.
		try {
			//Set url
			String urlString = "";
			this.rootUrl = parameters.get(PARAMETER_WEB_URL)+"?";
			urlString = rootUrl+payloadData;
			LOG.debug("urlString = " + urlString);
		
			URL url = new URL(urlString);		
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	
			// read the response
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
						connection.getInputStream()));
						
				String decodedString;
			
				while ((decodedString = in.readLine()) != null) {
					decodedString = URLDecoder.decode(decodedString, "ISO-8859-1");
					LOG.debug("urlDecodedString = " + decodedString);						
				    //TODO iterate around response params and update classes accordingly.
					//Do primary path first error=0.
					/*
					 * v1.4 response params are
					 * error, status, expire, errorMessage.
					 */
					LOG.debug("Response decodedString: "+decodedString);
					// parse and display name/value pair
					int equalPos=decodedString.indexOf('=');
					String name=decodedString.substring(0,equalPos);
					String value=decodedString.substring(equalPos+1);
					LOG.debug(name+"="+value);
					if (name.equals("error")) {
						paDto.setCode1(value); // code if 0 it is ok
						//responseCode = new Integer(value).intValue();
					}
					if(name.equals("status")) {
						paDto.setApprovalCode(value);
						paDto.setCode2(value);
					}
					if(name.equals("sessionId")) {
						paDto.setTransactionId(value);
					}
					if(name.equals("errorMessage")) {
						paDto.setResponseMessage(value);
					}
				}
				in.close();
			} catch(Exception e) {
				LOG.error("Error checking revered status = " + e.getMessage());
			}
			return paDto;
	}

}
