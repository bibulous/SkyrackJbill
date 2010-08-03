/*
 * Generated by JasperReports - 24/06/10 12:56
 */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.*;

import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.net.*;



/**
 *
 */
class Si2_1277380600677_387387 extends JREvaluator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_REPORT_TIME_ZONE = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_invoiceId = null;
    private JRFillParameter parameter_REPORT_FILE_RESOLVER = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_REPORT_CLASS_LOADER = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_URL_HANDLER_FACTORY = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_REPORT_FORMAT_FACTORY = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;
    private JRFillParameter parameter_REPORT_TEMPLATES = null;
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillField field_amount = null;
    private JRFillField field_id = null;
    private JRFillField field_mediation_record_id = null;
    private JRFillField field_order_line_id = null;
    private JRFillField field_description = null;
    private JRFillField field_OPTLOCK = null;
    private JRFillField field_event_date = null;
    private JRFillField field_quantity = null;
    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;


    /**
     *
     */
    void customizedInit(
        Map pm,
        Map fm,
        Map vm
        )
    {
        initParams(pm);
        initFields(fm);
        initVars(vm);
    }


    /**
     *
     */
    void initParams(Map pm)
    {
        parameter_REPORT_LOCALE = (JRFillParameter)pm.get("REPORT_LOCALE");
        parameter_REPORT_TIME_ZONE = (JRFillParameter)pm.get("REPORT_TIME_ZONE");
        parameter_REPORT_VIRTUALIZER = (JRFillParameter)pm.get("REPORT_VIRTUALIZER");
        parameter_invoiceId = (JRFillParameter)pm.get("invoiceId");
        parameter_REPORT_FILE_RESOLVER = (JRFillParameter)pm.get("REPORT_FILE_RESOLVER");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)pm.get("REPORT_SCRIPTLET");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)pm.get("REPORT_PARAMETERS_MAP");
        parameter_REPORT_CONNECTION = (JRFillParameter)pm.get("REPORT_CONNECTION");
        parameter_REPORT_CLASS_LOADER = (JRFillParameter)pm.get("REPORT_CLASS_LOADER");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)pm.get("REPORT_DATA_SOURCE");
        parameter_REPORT_URL_HANDLER_FACTORY = (JRFillParameter)pm.get("REPORT_URL_HANDLER_FACTORY");
        parameter_IS_IGNORE_PAGINATION = (JRFillParameter)pm.get("IS_IGNORE_PAGINATION");
        parameter_REPORT_FORMAT_FACTORY = (JRFillParameter)pm.get("REPORT_FORMAT_FACTORY");
        parameter_REPORT_MAX_COUNT = (JRFillParameter)pm.get("REPORT_MAX_COUNT");
        parameter_REPORT_TEMPLATES = (JRFillParameter)pm.get("REPORT_TEMPLATES");
        parameter_REPORT_RESOURCE_BUNDLE = (JRFillParameter)pm.get("REPORT_RESOURCE_BUNDLE");
    }


    /**
     *
     */
    void initFields(Map fm)
    {
        field_amount = (JRFillField)fm.get("amount");
        field_id = (JRFillField)fm.get("id");
        field_mediation_record_id = (JRFillField)fm.get("mediation_record_id");
        field_order_line_id = (JRFillField)fm.get("order_line_id");
        field_description = (JRFillField)fm.get("description");
        field_OPTLOCK = (JRFillField)fm.get("OPTLOCK");
        field_event_date = (JRFillField)fm.get("event_date");
        field_quantity = (JRFillField)fm.get("quantity");
    }


    /**
     *
     */
    void initVars(Map vm)
    {
        variable_PAGE_NUMBER = (JRFillVariable)vm.get("PAGE_NUMBER");
        variable_COLUMN_NUMBER = (JRFillVariable)vm.get("COLUMN_NUMBER");
        variable_REPORT_COUNT = (JRFillVariable)vm.get("REPORT_COUNT");
        variable_PAGE_COUNT = (JRFillVariable)vm.get("PAGE_COUNT");
        variable_COLUMN_COUNT = (JRFillVariable)vm.get("COLUMN_COUNT");
    }


    /**
     *
     */
    Object evaluate(int id)
    {
        Object value = null;

        if (id == 0)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 1)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 2)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 3)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 4)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 5)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 6)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 7)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 8)
        {
            value = (java.sql.Timestamp)(((java.sql.Timestamp)field_event_date.getValue()));
        }
        else if (id == 9)
        {
            value = (java.lang.String)(((java.lang.String)field_description.getValue()));
        }
        else if (id == 10)
        {
            value = (java.lang.Double)(((java.lang.Double)field_quantity.getValue()));
        }
        else if (id == 11)
        {
            value = (java.lang.Double)(((java.lang.Double)field_amount.getValue()));
        }
        else if (id == 12)
        {
            value = (java.lang.String)("Page "+((java.lang.Integer)variable_PAGE_NUMBER.getValue())+" of");
        }
        else if (id == 13)
        {
            value = (java.lang.String)(" " + ((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
        }
        else if (id == 14)
        {
            value = (java.util.Date)(new java.util.Date());
        }

        return value;
    }


    /**
     *
     */
    Object evaluateOld(int id)
    {
        Object value = null;

        if (id == 0)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 1)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 2)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 3)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 4)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 5)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 6)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 7)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 8)
        {
            value = (java.sql.Timestamp)(((java.sql.Timestamp)field_event_date.getOldValue()));
        }
        else if (id == 9)
        {
            value = (java.lang.String)(((java.lang.String)field_description.getOldValue()));
        }
        else if (id == 10)
        {
            value = (java.lang.Double)(((java.lang.Double)field_quantity.getOldValue()));
        }
        else if (id == 11)
        {
            value = (java.lang.Double)(((java.lang.Double)field_amount.getOldValue()));
        }
        else if (id == 12)
        {
            value = (java.lang.String)("Page "+((java.lang.Integer)variable_PAGE_NUMBER.getOldValue())+" of");
        }
        else if (id == 13)
        {
            value = (java.lang.String)(" " + ((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
        }
        else if (id == 14)
        {
            value = (java.util.Date)(new java.util.Date());
        }

        return value;
    }


    /**
     *
     */
    Object evaluateEstimated(int id)
    {
        Object value = null;

        if (id == 0)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 1)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 2)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 3)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 4)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 5)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 6)
        {
            value = (java.lang.Integer)(new java.lang.Integer(1));
        }
        else if (id == 7)
        {
            value = (java.lang.Integer)(new java.lang.Integer(0));
        }
        else if (id == 8)
        {
            value = (java.sql.Timestamp)(((java.sql.Timestamp)field_event_date.getValue()));
        }
        else if (id == 9)
        {
            value = (java.lang.String)(((java.lang.String)field_description.getValue()));
        }
        else if (id == 10)
        {
            value = (java.lang.Double)(((java.lang.Double)field_quantity.getValue()));
        }
        else if (id == 11)
        {
            value = (java.lang.Double)(((java.lang.Double)field_amount.getValue()));
        }
        else if (id == 12)
        {
            value = (java.lang.String)("Page "+((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue())+" of");
        }
        else if (id == 13)
        {
            value = (java.lang.String)(" " + ((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
        }
        else if (id == 14)
        {
            value = (java.util.Date)(new java.util.Date());
        }

        return value;
    }


}