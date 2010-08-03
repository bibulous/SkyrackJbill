package com.sapienter.jbilling.client.jspc.payment;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.sapienter.jbilling.client.util.Constants;

public final class customerSelect_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fparameter;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fscope_005fproperty_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fflush_005fdefinition_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fproperty_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fredirect_0026_005fforward_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fparameter = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fscope_005fproperty_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fflush_005fdefinition_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fproperty_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fredirect_0026_005fforward_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.release();
    _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.release();
    _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fparameter.release();
    _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fscope_005fproperty_005fname.release();
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fflush_005fdefinition_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fproperty_005fname.release();
    _005fjspx_005ftagPool_005flogic_005fredirect_0026_005fforward_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');

  session.removeAttribute("payment");  
  session.removeAttribute(Constants.SESSION_PAYMENT_DTO);

      out.write("\r\n\r\n");
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_logic_005fpresent_005f3(_jspx_page_context))
        return;
      out.write("\r\n\r\n");
      out.write('\r');
      out.write('\n');
      //  logic:present
      org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f4 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
      _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fpresent_005f4.setParent(null);
      // /payment/customerSelect.jsp(59,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fpresent_005f4.setParameter("refund");
      int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
      if (_jspx_eval_logic_005fpresent_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("                \r\n\t");
          if (_jspx_meth_sess_005fsetAttribute_005f4(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
            return;
          out.write('\r');
          out.write('\n');
          out.write('	');

	  session.removeAttribute(Constants.SESSION_LIST_KEY + Constants.LIST_TYPE_PAYMENT_USER);  
	
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fpresent_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f4);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f4);
      out.write('\r');
      out.write('\n');
      //  logic:notPresent
      org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag) _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fparameter.get(org.apache.struts.taglib.logic.NotPresentTag.class);
      _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fnotPresent_005f0.setParent(null);
      // /payment/customerSelect.jsp(65,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fnotPresent_005f0.setParameter("refund");
      int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
      if (_jspx_eval_logic_005fnotPresent_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("                \r\n\t");

	  session.removeAttribute(Constants.SESSION_PAYMENT_DTO);
	  session.removeAttribute(Constants.SESSION_INVOICE_DTO);
	  session.removeAttribute(Constants.SESSION_LIST_KEY + Constants.LIST_TYPE_INVOICE);  
      session.removeAttribute("jsp_linked_invoices");
	
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fparameter.reuse(_jspx_th_logic_005fnotPresent_005f0);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fparameter.reuse(_jspx_th_logic_005fnotPresent_005f0);
      out.write("\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (org.apache.struts.taglib.logic.NotEqualTag) _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fscope_005fproperty_005fname.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fnotEqual_005f0.setParent(null);
      // /payment/customerSelect.jsp(76,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fnotEqual_005f0.setName(Constants.SESSION_USER_DTO);
      // /payment/customerSelect.jsp(76,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fnotEqual_005f0.setProperty("mainRoleId");
      // /payment/customerSelect.jsp(76,0) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fnotEqual_005f0.setScope("session");
      // /payment/customerSelect.jsp(76,0) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fnotEqual_005f0.setValue(Constants.TYPE_CUSTOMER.toString());
      int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
      if (_jspx_eval_logic_005fnotEqual_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n     ");
          if (_jspx_meth_tiles_005finsert_005f0(_jspx_th_logic_005fnotEqual_005f0, _jspx_page_context))
            return;
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fscope_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fscope_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
      out.write("\r\n\r\n");
      out.write('\r');
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_005fequal_005f0 = (org.apache.struts.taglib.logic.EqualTag) _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fproperty_005fname.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fequal_005f0.setParent(null);
      // /payment/customerSelect.jsp(85,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fequal_005f0.setName(Constants.SESSION_USER_DTO);
      // /payment/customerSelect.jsp(85,0) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fequal_005f0.setProperty("mainRoleId");
      // /payment/customerSelect.jsp(85,0) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fequal_005f0.setScope("session");
      // /payment/customerSelect.jsp(85,0) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fequal_005f0.setValue(Constants.TYPE_CUSTOMER.toString());
      int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
      if (_jspx_eval_logic_005fequal_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t    ");

	         session.setAttribute(Constants.SESSION_USER_ID, session.getAttribute(
	                    Constants.SESSION_LOGGED_USER_ID));
	    
          out.write("     \r\n\t\t");
          //  logic:redirect
          org.apache.struts.taglib.logic.RedirectTag _jspx_th_logic_005fredirect_005f0 = (org.apache.struts.taglib.logic.RedirectTag) _005fjspx_005ftagPool_005flogic_005fredirect_0026_005fforward_005fnobody.get(org.apache.struts.taglib.logic.RedirectTag.class);
          _jspx_th_logic_005fredirect_005f0.setPageContext(_jspx_page_context);
          _jspx_th_logic_005fredirect_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fequal_005f0);
          // /payment/customerSelect.jsp(93,2) name = forward type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fredirect_005f0.setForward(Constants.FORWARD_PAYMENT_CREATE);
          int _jspx_eval_logic_005fredirect_005f0 = _jspx_th_logic_005fredirect_005f0.doStartTag();
          if (_jspx_th_logic_005fredirect_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005flogic_005fredirect_0026_005fforward_005fnobody.reuse(_jspx_th_logic_005fredirect_005f0);
            return;
          }
          _005fjspx_005ftagPool_005flogic_005fredirect_0026_005fforward_005fnobody.reuse(_jspx_th_logic_005fredirect_005f0);
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fequal_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:present
    org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
    _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
    _jspx_th_logic_005fpresent_005f0.setParent(null);
    // /payment/customerSelect.jsp(45,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fpresent_005f0.setParameter("cc");
    int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
    if (_jspx_eval_logic_005fpresent_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("                \r\n   ");
        if (_jspx_meth_sess_005fsetAttribute_005f0(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_005fpresent_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f0);
    return false;
  }

  private boolean _jspx_meth_sess_005fsetAttribute_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:setAttribute
    org.apache.taglibs.session.SetAttributeTag _jspx_th_sess_005fsetAttribute_005f0 = (org.apache.taglibs.session.SetAttributeTag) _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.get(org.apache.taglibs.session.SetAttributeTag.class);
    _jspx_th_sess_005fsetAttribute_005f0.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fsetAttribute_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f0);
    // /payment/customerSelect.jsp(46,3) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fsetAttribute_005f0.setName("jsp_payment_method");
    int _jspx_eval_sess_005fsetAttribute_005f0 = _jspx_th_sess_005fsetAttribute_005f0.doStartTag();
    if (_jspx_eval_sess_005fsetAttribute_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_sess_005fsetAttribute_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_sess_005fsetAttribute_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_sess_005fsetAttribute_005f0.doInitBody();
      }
      do {
        out.write('c');
        out.write('c');
        int evalDoAfterBody = _jspx_th_sess_005fsetAttribute_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_sess_005fsetAttribute_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_sess_005fsetAttribute_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f0);
    return false;
  }

  private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:present
    org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f1 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
    _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
    _jspx_th_logic_005fpresent_005f1.setParent(null);
    // /payment/customerSelect.jsp(48,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fpresent_005f1.setParameter("cheque");
    int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
    if (_jspx_eval_logic_005fpresent_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("            \r\n   ");
        if (_jspx_meth_sess_005fsetAttribute_005f1(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_005fpresent_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f1);
    return false;
  }

  private boolean _jspx_meth_sess_005fsetAttribute_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:setAttribute
    org.apache.taglibs.session.SetAttributeTag _jspx_th_sess_005fsetAttribute_005f1 = (org.apache.taglibs.session.SetAttributeTag) _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.get(org.apache.taglibs.session.SetAttributeTag.class);
    _jspx_th_sess_005fsetAttribute_005f1.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fsetAttribute_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f1);
    // /payment/customerSelect.jsp(49,3) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fsetAttribute_005f1.setName("jsp_payment_method");
    int _jspx_eval_sess_005fsetAttribute_005f1 = _jspx_th_sess_005fsetAttribute_005f1.doStartTag();
    if (_jspx_eval_sess_005fsetAttribute_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_sess_005fsetAttribute_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_sess_005fsetAttribute_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_sess_005fsetAttribute_005f1.doInitBody();
      }
      do {
        out.write("cheque");
        int evalDoAfterBody = _jspx_th_sess_005fsetAttribute_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_sess_005fsetAttribute_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_sess_005fsetAttribute_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f1);
    return false;
  }

  private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:present
    org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f2 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
    _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
    _jspx_th_logic_005fpresent_005f2.setParent(null);
    // /payment/customerSelect.jsp(51,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fpresent_005f2.setParameter("ach");
    int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
    if (_jspx_eval_logic_005fpresent_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("            \r\n   ");
        if (_jspx_meth_sess_005fsetAttribute_005f2(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_005fpresent_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f2);
    return false;
  }

  private boolean _jspx_meth_sess_005fsetAttribute_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:setAttribute
    org.apache.taglibs.session.SetAttributeTag _jspx_th_sess_005fsetAttribute_005f2 = (org.apache.taglibs.session.SetAttributeTag) _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.get(org.apache.taglibs.session.SetAttributeTag.class);
    _jspx_th_sess_005fsetAttribute_005f2.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fsetAttribute_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f2);
    // /payment/customerSelect.jsp(52,3) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fsetAttribute_005f2.setName("jsp_payment_method");
    int _jspx_eval_sess_005fsetAttribute_005f2 = _jspx_th_sess_005fsetAttribute_005f2.doStartTag();
    if (_jspx_eval_sess_005fsetAttribute_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_sess_005fsetAttribute_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_sess_005fsetAttribute_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_sess_005fsetAttribute_005f2.doInitBody();
      }
      do {
        out.write('a');
        out.write('c');
        out.write('h');
        int evalDoAfterBody = _jspx_th_sess_005fsetAttribute_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_sess_005fsetAttribute_005f2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_sess_005fsetAttribute_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f2);
    return false;
  }

  private boolean _jspx_meth_logic_005fpresent_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:present
    org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f3 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
    _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
    _jspx_th_logic_005fpresent_005f3.setParent(null);
    // /payment/customerSelect.jsp(54,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fpresent_005f3.setParameter("paypal");
    int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
    if (_jspx_eval_logic_005fpresent_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n   ");
        if (_jspx_meth_sess_005fsetAttribute_005f3(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
          return true;
        out.write('\r');
        out.write('\n');
        int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_005fpresent_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f3);
    return false;
  }

  private boolean _jspx_meth_sess_005fsetAttribute_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:setAttribute
    org.apache.taglibs.session.SetAttributeTag _jspx_th_sess_005fsetAttribute_005f3 = (org.apache.taglibs.session.SetAttributeTag) _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.get(org.apache.taglibs.session.SetAttributeTag.class);
    _jspx_th_sess_005fsetAttribute_005f3.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fsetAttribute_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f3);
    // /payment/customerSelect.jsp(55,3) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fsetAttribute_005f3.setName("jsp_payment_method");
    int _jspx_eval_sess_005fsetAttribute_005f3 = _jspx_th_sess_005fsetAttribute_005f3.doStartTag();
    if (_jspx_eval_sess_005fsetAttribute_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_sess_005fsetAttribute_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_sess_005fsetAttribute_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_sess_005fsetAttribute_005f3.doInitBody();
      }
      do {
        out.write("paypal");
        int evalDoAfterBody = _jspx_th_sess_005fsetAttribute_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_sess_005fsetAttribute_005f3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_sess_005fsetAttribute_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f3);
    return false;
  }

  private boolean _jspx_meth_sess_005fsetAttribute_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:setAttribute
    org.apache.taglibs.session.SetAttributeTag _jspx_th_sess_005fsetAttribute_005f4 = (org.apache.taglibs.session.SetAttributeTag) _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.get(org.apache.taglibs.session.SetAttributeTag.class);
    _jspx_th_sess_005fsetAttribute_005f4.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fsetAttribute_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f4);
    // /payment/customerSelect.jsp(60,1) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fsetAttribute_005f4.setName("jsp_is_refund");
    int _jspx_eval_sess_005fsetAttribute_005f4 = _jspx_th_sess_005fsetAttribute_005f4.doStartTag();
    if (_jspx_eval_sess_005fsetAttribute_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_sess_005fsetAttribute_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_sess_005fsetAttribute_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_sess_005fsetAttribute_005f4.doInitBody();
      }
      do {
        out.write('y');
        out.write('e');
        out.write('s');
        int evalDoAfterBody = _jspx_th_sess_005fsetAttribute_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_sess_005fsetAttribute_005f4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_sess_005fsetAttribute_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fsetAttribute_0026_005fname.reuse(_jspx_th_sess_005fsetAttribute_005f4);
    return false;
  }

  private boolean _jspx_meth_tiles_005finsert_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotEqual_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insert
    org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag) _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fflush_005fdefinition_005fnobody.get(org.apache.struts.taglib.tiles.InsertTag.class);
    _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
    _jspx_th_tiles_005finsert_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotEqual_005f0);
    // /payment/customerSelect.jsp(80,5) name = definition type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsert_005f0.setDefinition("payment.enter.customerSelection");
    // /payment/customerSelect.jsp(80,5) name = flush type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsert_005f0.setFlush(true);
    int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
    if (_jspx_th_tiles_005finsert_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fflush_005fdefinition_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fflush_005fdefinition_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
    return false;
  }
}
