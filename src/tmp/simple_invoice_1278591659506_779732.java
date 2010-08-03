/*
 * Generated by JasperReports - 08/07/10 13:20
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
public class simple_invoice_1278591659506_779732 extends JREvaluator
{


    /**
     *
     */
    private JRFillParameter parameter_entityCity = null;
    private JRFillParameter parameter_invoiceDate = null;
    private JRFillParameter parameter_REPORT_TIME_ZONE = null;
    private JRFillParameter parameter_REPORT_FILE_RESOLVER = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_customerCity = null;
    private JRFillParameter parameter_invoiceDueDate = null;
    private JRFillParameter parameter_REPORT_CLASS_LOADER = null;
    private JRFillParameter parameter_REPORT_URL_HANDLER_FACTORY = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_customerPostalCode = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_entityPostalCode = null;
    private JRFillParameter parameter_customerMessage2 = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;
    private JRFillParameter parameter_customerMessage1 = null;
    private JRFillParameter parameter_REPORT_TEMPLATES = null;
    private JRFillParameter parameter_totalWithTax = null;
    private JRFillParameter parameter_entityLogo = null;
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_customerName = null;
    private JRFillParameter parameter_customerProvince = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_invoiceNumber = null;
    private JRFillParameter parameter_entityName = null;
    private JRFillParameter parameter_REPORT_FORMAT_FACTORY = null;
    private JRFillParameter parameter_entityAddress = null;
    private JRFillParameter parameter_entityProvince = null;
    private JRFillParameter parameter_customerAddress = null;
    private JRFillParameter parameter_totalWithoutTax = null;
    private JRFillParameter parameter_notes = null;
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillField field_amount = null;
    private JRFillField field_price = null;
    private JRFillField field_description = null;
    private JRFillField field_quantity = null;
    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_invoice_total_COUNT = null;


    /**
     *
     */
    public void customizedInit(
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
    private void initParams(Map pm)
    {
        parameter_entityCity = (JRFillParameter)pm.get("entityCity");
        parameter_invoiceDate = (JRFillParameter)pm.get("invoiceDate");
        parameter_REPORT_TIME_ZONE = (JRFillParameter)pm.get("REPORT_TIME_ZONE");
        parameter_REPORT_FILE_RESOLVER = (JRFillParameter)pm.get("REPORT_FILE_RESOLVER");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)pm.get("REPORT_PARAMETERS_MAP");
        parameter_customerCity = (JRFillParameter)pm.get("customerCity");
        parameter_invoiceDueDate = (JRFillParameter)pm.get("invoiceDueDate");
        parameter_REPORT_CLASS_LOADER = (JRFillParameter)pm.get("REPORT_CLASS_LOADER");
        parameter_REPORT_URL_HANDLER_FACTORY = (JRFillParameter)pm.get("REPORT_URL_HANDLER_FACTORY");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)pm.get("REPORT_DATA_SOURCE");
        parameter_customerPostalCode = (JRFillParameter)pm.get("customerPostalCode");
        parameter_IS_IGNORE_PAGINATION = (JRFillParameter)pm.get("IS_IGNORE_PAGINATION");
        parameter_entityPostalCode = (JRFillParameter)pm.get("entityPostalCode");
        parameter_customerMessage2 = (JRFillParameter)pm.get("customerMessage2");
        parameter_REPORT_MAX_COUNT = (JRFillParameter)pm.get("REPORT_MAX_COUNT");
        parameter_customerMessage1 = (JRFillParameter)pm.get("customerMessage1");
        parameter_REPORT_TEMPLATES = (JRFillParameter)pm.get("REPORT_TEMPLATES");
        parameter_totalWithTax = (JRFillParameter)pm.get("totalWithTax");
        parameter_entityLogo = (JRFillParameter)pm.get("entityLogo");
        parameter_REPORT_LOCALE = (JRFillParameter)pm.get("REPORT_LOCALE");
        parameter_customerName = (JRFillParameter)pm.get("customerName");
        parameter_customerProvince = (JRFillParameter)pm.get("customerProvince");
        parameter_REPORT_VIRTUALIZER = (JRFillParameter)pm.get("REPORT_VIRTUALIZER");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)pm.get("REPORT_SCRIPTLET");
        parameter_REPORT_CONNECTION = (JRFillParameter)pm.get("REPORT_CONNECTION");
        parameter_invoiceNumber = (JRFillParameter)pm.get("invoiceNumber");
        parameter_entityName = (JRFillParameter)pm.get("entityName");
        parameter_REPORT_FORMAT_FACTORY = (JRFillParameter)pm.get("REPORT_FORMAT_FACTORY");
        parameter_entityAddress = (JRFillParameter)pm.get("entityAddress");
        parameter_entityProvince = (JRFillParameter)pm.get("entityProvince");
        parameter_customerAddress = (JRFillParameter)pm.get("customerAddress");
        parameter_totalWithoutTax = (JRFillParameter)pm.get("totalWithoutTax");
        parameter_notes = (JRFillParameter)pm.get("notes");
        parameter_REPORT_RESOURCE_BUNDLE = (JRFillParameter)pm.get("REPORT_RESOURCE_BUNDLE");
    }


    /**
     *
     */
    private void initFields(Map fm)
    {
        field_amount = (JRFillField)fm.get("amount");
        field_price = (JRFillField)fm.get("price");
        field_description = (JRFillField)fm.get("description");
        field_quantity = (JRFillField)fm.get("quantity");
    }


    /**
     *
     */
    private void initVars(Map vm)
    {
        variable_PAGE_NUMBER = (JRFillVariable)vm.get("PAGE_NUMBER");
        variable_COLUMN_NUMBER = (JRFillVariable)vm.get("COLUMN_NUMBER");
        variable_REPORT_COUNT = (JRFillVariable)vm.get("REPORT_COUNT");
        variable_PAGE_COUNT = (JRFillVariable)vm.get("PAGE_COUNT");
        variable_COLUMN_COUNT = (JRFillVariable)vm.get("COLUMN_COUNT");
        variable_invoice_total_COUNT = (JRFillVariable)vm.get("invoice_total_COUNT");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_totalWithoutTax.getValue())); //$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_notes.getValue())); //$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceNumber.getValue())); //$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.io.File)(((java.io.File)parameter_entityLogo.getValue())); //$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityName.getValue())); //$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityAddress.getValue())); //$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityCity.getValue()) + ", " + ((java.lang.String)parameter_entityProvince.getValue()) + " " + ((java.lang.String)parameter_entityPostalCode.getValue())); //$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerName.getValue())); //$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerAddress.getValue())); //$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceDate.getValue())); //$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceDueDate.getValue())); //$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerMessage1.getValue())); //$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_totalWithTax.getValue())); //$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerCity.getValue()) + ", " + ((java.lang.String)parameter_customerProvince.getValue()) + " " + ((java.lang.String)parameter_customerPostalCode.getValue())); //$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Double)field_quantity.getValue()) != null)); //$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.##")).format(((java.lang.Double)field_quantity.getValue()))); //$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.String)(((java.lang.String)field_description.getValue())); //$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Float)field_price.getValue()) != null)); //$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.00")).format(((java.lang.Float)field_price.getValue()))); //$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Float)field_amount.getValue()) != null)); //$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.00")).format(((java.lang.Float)field_amount.getValue()))); //$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.String)("Page " + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getValue())) + " of "); //$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue())); //$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerMessage2.getValue())); //$JR_EXPR_ID=33$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateOld(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_totalWithoutTax.getValue())); //$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_notes.getValue())); //$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceNumber.getValue())); //$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.io.File)(((java.io.File)parameter_entityLogo.getValue())); //$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityName.getValue())); //$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityAddress.getValue())); //$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityCity.getValue()) + ", " + ((java.lang.String)parameter_entityProvince.getValue()) + " " + ((java.lang.String)parameter_entityPostalCode.getValue())); //$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerName.getValue())); //$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerAddress.getValue())); //$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceDate.getValue())); //$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceDueDate.getValue())); //$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerMessage1.getValue())); //$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_totalWithTax.getValue())); //$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerCity.getValue()) + ", " + ((java.lang.String)parameter_customerProvince.getValue()) + " " + ((java.lang.String)parameter_customerPostalCode.getValue())); //$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Double)field_quantity.getOldValue()) != null)); //$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.##")).format(((java.lang.Double)field_quantity.getOldValue()))); //$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.String)(((java.lang.String)field_description.getOldValue())); //$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Float)field_price.getOldValue()) != null)); //$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.00")).format(((java.lang.Float)field_price.getOldValue()))); //$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Float)field_amount.getOldValue()) != null)); //$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.00")).format(((java.lang.Float)field_amount.getOldValue()))); //$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.String)("Page " + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue())) + " of "); //$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue())); //$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerMessage2.getValue())); //$JR_EXPR_ID=33$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateEstimated(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(1)); //$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.Integer)(new java.lang.Integer(0)); //$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_totalWithoutTax.getValue())); //$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_notes.getValue())); //$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceNumber.getValue())); //$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.io.File)(((java.io.File)parameter_entityLogo.getValue())); //$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityName.getValue())); //$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityAddress.getValue())); //$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_entityCity.getValue()) + ", " + ((java.lang.String)parameter_entityProvince.getValue()) + " " + ((java.lang.String)parameter_entityPostalCode.getValue())); //$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerName.getValue())); //$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerAddress.getValue())); //$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceDate.getValue())); //$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_invoiceDueDate.getValue())); //$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerMessage1.getValue())); //$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_totalWithTax.getValue())); //$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerCity.getValue()) + ", " + ((java.lang.String)parameter_customerProvince.getValue()) + " " + ((java.lang.String)parameter_customerPostalCode.getValue())); //$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Double)field_quantity.getValue()) != null)); //$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.##")).format(((java.lang.Double)field_quantity.getValue()))); //$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.String)(((java.lang.String)field_description.getValue())); //$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Float)field_price.getValue()) != null)); //$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.00")).format(((java.lang.Float)field_price.getValue()))); //$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.Float)field_amount.getValue()) != null)); //$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.String)((new DecimalFormat("#,##0.00")).format(((java.lang.Float)field_amount.getValue()))); //$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.String)("Page " + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue())) + " of "); //$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue())); //$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.String)(((java.lang.String)parameter_customerMessage2.getValue())); //$JR_EXPR_ID=33$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}
