package com.sapienter.jbilling.client.jspc.payment;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.sapienter.jbilling.client.util.Constants;

public final class enterCustomerSelectionTop_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fjbilling_005fgenericList_0026_005ftype_005fsetup_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fjbilling_005fgenericList_0026_005ftype_005fsetup_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname.release();
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.release();
    _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname.release();
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.release();
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.release();
    _005fjspx_005ftagPool_005fjbilling_005fgenericList_0026_005ftype_005fsetup_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');

  session.removeAttribute("payment");  
  session.removeAttribute(Constants.SESSION_PAYMENT_DTO);
  session.removeAttribute(Constants.SESSION_LIST_KEY + Constants.LIST_TYPE_PAYMENT_USER);  
  session.removeAttribute(Constants.SESSION_INVOICE_DTO);
  session.removeAttribute(Constants.SESSION_LIST_KEY + Constants.LIST_TYPE_INVOICE);  

      out.write("\r\n\r\n\r\n<p class=\"title\">\r\n\t");
      if (_jspx_meth_sess_005fexistsAttribute_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_sess_005fexistsAttribute_005f1(_jspx_page_context))
        return;
      out.write("\r\n</p>\r\n\r\n<p class=\"instr\">\r\n\t");
      if (_jspx_meth_sess_005fexistsAttribute_005f2(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_sess_005fexistsAttribute_005f3(_jspx_page_context))
        return;
      out.write("\r\n</p>\r\n\r\n");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f0 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fdefine_005f0.setParent(null);
      // /payment/enterCustomerSelectionTop.jsp(67,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f0.setId("forward_from");
      // /payment/enterCustomerSelectionTop.jsp(67,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f0.setValue(Constants.FORWARD_PAYMENT_CREATE);
      // /payment/enterCustomerSelectionTop.jsp(67,0) name = toScope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f0.setToScope("session");
      int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
      if (_jspx_th_bean_005fdefine_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
        return;
      }
      _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
      java.lang.String forward_from = null;
      forward_from = (java.lang.String) _jspx_page_context.findAttribute("forward_from");
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f1 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fdefine_005f1.setParent(null);
      // /payment/enterCustomerSelectionTop.jsp(71,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f1.setId("forward_to");
      // /payment/enterCustomerSelectionTop.jsp(71,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f1.setValue(Constants.FORWARD_PAYMENT_CREATE);
      // /payment/enterCustomerSelectionTop.jsp(71,0) name = toScope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fdefine_005f1.setToScope("session");
      int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
      if (_jspx_th_bean_005fdefine_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
        return;
      }
      _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
      java.lang.String forward_to = null;
      forward_to = (java.lang.String) _jspx_page_context.findAttribute("forward_to");
      out.write("\r\n \r\n");
      //  jbilling:genericList
      com.sapienter.jbilling.client.list.GenericListTag _jspx_th_jbilling_005fgenericList_005f0 = (com.sapienter.jbilling.client.list.GenericListTag) _005fjspx_005ftagPool_005fjbilling_005fgenericList_0026_005ftype_005fsetup_005fnobody.get(com.sapienter.jbilling.client.list.GenericListTag.class);
      _jspx_th_jbilling_005fgenericList_005f0.setPageContext(_jspx_page_context);
      _jspx_th_jbilling_005fgenericList_005f0.setParent(null);
      // /payment/enterCustomerSelectionTop.jsp(75,0) name = setup type = java.lang.Boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_jbilling_005fgenericList_005f0.setSetup(new Boolean(true));
      // /payment/enterCustomerSelectionTop.jsp(75,0) name = type type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_jbilling_005fgenericList_005f0.setType(Constants.LIST_TYPE_CUSTOMER_SIMPLE);
      int _jspx_eval_jbilling_005fgenericList_005f0 = _jspx_th_jbilling_005fgenericList_005f0.doStartTag();
      if (_jspx_th_jbilling_005fgenericList_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fjbilling_005fgenericList_0026_005ftype_005fsetup_005fnobody.reuse(_jspx_th_jbilling_005fgenericList_005f0);
        return;
      }
      _005fjspx_005ftagPool_005fjbilling_005fgenericList_0026_005ftype_005fsetup_005fnobody.reuse(_jspx_th_jbilling_005fgenericList_005f0);
      out.write("\r\n\t            \r\n");
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

  private boolean _jspx_meth_sess_005fexistsAttribute_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:existsAttribute
    org.apache.taglibs.session.ExistsAttributeTag _jspx_th_sess_005fexistsAttribute_005f0 = (org.apache.taglibs.session.ExistsAttributeTag) _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname.get(org.apache.taglibs.session.ExistsAttributeTag.class);
    _jspx_th_sess_005fexistsAttribute_005f0.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fexistsAttribute_005f0.setParent(null);
    // /payment/enterCustomerSelectionTop.jsp(44,1) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fexistsAttribute_005f0.setName("jsp_is_refund");
    // /payment/enterCustomerSelectionTop.jsp(44,1) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fexistsAttribute_005f0.setValue(false);
    int _jspx_eval_sess_005fexistsAttribute_005f0 = _jspx_th_sess_005fexistsAttribute_005f0.doStartTag();
    if (_jspx_eval_sess_005fexistsAttribute_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n        ");
        if (_jspx_meth_bean_005fmessage_005f0(_jspx_th_sess_005fexistsAttribute_005f0, _jspx_page_context))
          return true;
        out.write("\r\n    ");
        int evalDoAfterBody = _jspx_th_sess_005fexistsAttribute_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_sess_005fexistsAttribute_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname.reuse(_jspx_th_sess_005fexistsAttribute_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname.reuse(_jspx_th_sess_005fexistsAttribute_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_sess_005fexistsAttribute_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sess_005fexistsAttribute_005f0);
    // /payment/enterCustomerSelectionTop.jsp(45,8) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f0.setKey("payment.customer.title");
    int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
    if (_jspx_th_bean_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
    return false;
  }

  private boolean _jspx_meth_sess_005fexistsAttribute_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:existsAttribute
    org.apache.taglibs.session.ExistsAttributeTag _jspx_th_sess_005fexistsAttribute_005f1 = (org.apache.taglibs.session.ExistsAttributeTag) _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname.get(org.apache.taglibs.session.ExistsAttributeTag.class);
    _jspx_th_sess_005fexistsAttribute_005f1.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fexistsAttribute_005f1.setParent(null);
    // /payment/enterCustomerSelectionTop.jsp(47,1) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fexistsAttribute_005f1.setName("jsp_is_refund");
    int _jspx_eval_sess_005fexistsAttribute_005f1 = _jspx_th_sess_005fexistsAttribute_005f1.doStartTag();
    if (_jspx_eval_sess_005fexistsAttribute_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n        ");
        if (_jspx_meth_bean_005fmessage_005f1(_jspx_th_sess_005fexistsAttribute_005f1, _jspx_page_context))
          return true;
        out.write("\r\n    ");
        int evalDoAfterBody = _jspx_th_sess_005fexistsAttribute_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_sess_005fexistsAttribute_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname.reuse(_jspx_th_sess_005fexistsAttribute_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname.reuse(_jspx_th_sess_005fexistsAttribute_005f1);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_sess_005fexistsAttribute_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sess_005fexistsAttribute_005f1);
    // /payment/enterCustomerSelectionTop.jsp(48,8) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f1.setKey("refund.customer.title");
    int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
    if (_jspx_th_bean_005fmessage_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
    return false;
  }

  private boolean _jspx_meth_sess_005fexistsAttribute_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:existsAttribute
    org.apache.taglibs.session.ExistsAttributeTag _jspx_th_sess_005fexistsAttribute_005f2 = (org.apache.taglibs.session.ExistsAttributeTag) _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname.get(org.apache.taglibs.session.ExistsAttributeTag.class);
    _jspx_th_sess_005fexistsAttribute_005f2.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fexistsAttribute_005f2.setParent(null);
    // /payment/enterCustomerSelectionTop.jsp(53,1) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fexistsAttribute_005f2.setName("jsp_is_refund");
    // /payment/enterCustomerSelectionTop.jsp(53,1) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fexistsAttribute_005f2.setValue(false);
    int _jspx_eval_sess_005fexistsAttribute_005f2 = _jspx_th_sess_005fexistsAttribute_005f2.doStartTag();
    if (_jspx_eval_sess_005fexistsAttribute_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n        ");
        if (_jspx_meth_bean_005fmessage_005f2(_jspx_th_sess_005fexistsAttribute_005f2, _jspx_page_context))
          return true;
        out.write("<br/>\r\n        ");
        if (_jspx_meth_bean_005fmessage_005f3(_jspx_th_sess_005fexistsAttribute_005f2, _jspx_page_context))
          return true;
        out.write("\r\n\t    ");
        if (_jspx_meth_jbilling_005fhelp_005f0(_jspx_th_sess_005fexistsAttribute_005f2, _jspx_page_context))
          return true;
        out.write("\r\n    ");
        int evalDoAfterBody = _jspx_th_sess_005fexistsAttribute_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_sess_005fexistsAttribute_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname.reuse(_jspx_th_sess_005fexistsAttribute_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fvalue_005fname.reuse(_jspx_th_sess_005fexistsAttribute_005f2);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_sess_005fexistsAttribute_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f2 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f2.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sess_005fexistsAttribute_005f2);
    // /payment/enterCustomerSelectionTop.jsp(54,8) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f2.setKey("payment.customer.select");
    int _jspx_eval_bean_005fmessage_005f2 = _jspx_th_bean_005fmessage_005f2.doStartTag();
    if (_jspx_th_bean_005fmessage_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_sess_005fexistsAttribute_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f3 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f3.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sess_005fexistsAttribute_005f2);
    // /payment/enterCustomerSelectionTop.jsp(55,8) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f3.setKey("all.prompt.help");
    int _jspx_eval_bean_005fmessage_005f3 = _jspx_th_bean_005fmessage_005f3.doStartTag();
    if (_jspx_th_bean_005fmessage_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fhelp_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_sess_005fexistsAttribute_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:help
    com.sapienter.jbilling.client.util.HelpTag _jspx_th_jbilling_005fhelp_005f0 = (com.sapienter.jbilling.client.util.HelpTag) _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.get(com.sapienter.jbilling.client.util.HelpTag.class);
    _jspx_th_jbilling_005fhelp_005f0.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fhelp_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sess_005fexistsAttribute_005f2);
    // /payment/enterCustomerSelectionTop.jsp(56,5) name = page type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f0.setPage("payments");
    // /payment/enterCustomerSelectionTop.jsp(56,5) name = anchor type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f0.setAnchor("new");
    int _jspx_eval_jbilling_005fhelp_005f0 = _jspx_th_jbilling_005fhelp_005f0.doStartTag();
    if (_jspx_eval_jbilling_005fhelp_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t");
        if (_jspx_meth_bean_005fmessage_005f4(_jspx_th_jbilling_005fhelp_005f0, _jspx_page_context))
          return true;
        out.write("\r\n\t    ");
        int evalDoAfterBody = _jspx_th_jbilling_005fhelp_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_jbilling_005fhelp_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_jbilling_005fhelp_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f4 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f4.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_jbilling_005fhelp_005f0);
    // /payment/enterCustomerSelectionTop.jsp(57,3) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f4.setKey("all.prompt.here");
    int _jspx_eval_bean_005fmessage_005f4 = _jspx_th_bean_005fmessage_005f4.doStartTag();
    if (_jspx_th_bean_005fmessage_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f4);
    return false;
  }

  private boolean _jspx_meth_sess_005fexistsAttribute_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sess:existsAttribute
    org.apache.taglibs.session.ExistsAttributeTag _jspx_th_sess_005fexistsAttribute_005f3 = (org.apache.taglibs.session.ExistsAttributeTag) _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname.get(org.apache.taglibs.session.ExistsAttributeTag.class);
    _jspx_th_sess_005fexistsAttribute_005f3.setPageContext(_jspx_page_context);
    _jspx_th_sess_005fexistsAttribute_005f3.setParent(null);
    // /payment/enterCustomerSelectionTop.jsp(60,1) name = name type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_sess_005fexistsAttribute_005f3.setName("jsp_is_refund");
    int _jspx_eval_sess_005fexistsAttribute_005f3 = _jspx_th_sess_005fexistsAttribute_005f3.doStartTag();
    if (_jspx_eval_sess_005fexistsAttribute_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n        ");
        if (_jspx_meth_bean_005fmessage_005f5(_jspx_th_sess_005fexistsAttribute_005f3, _jspx_page_context))
          return true;
        out.write("\r\n    ");
        int evalDoAfterBody = _jspx_th_sess_005fexistsAttribute_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_sess_005fexistsAttribute_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname.reuse(_jspx_th_sess_005fexistsAttribute_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fsess_005fexistsAttribute_0026_005fname.reuse(_jspx_th_sess_005fexistsAttribute_005f3);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_sess_005fexistsAttribute_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f5 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f5.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_sess_005fexistsAttribute_005f3);
    // /payment/enterCustomerSelectionTop.jsp(61,8) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f5.setKey("refund.customer.select");
    int _jspx_eval_bean_005fmessage_005f5 = _jspx_th_bean_005fmessage_005f5.doStartTag();
    if (_jspx_th_bean_005fmessage_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f5);
    return false;
  }
}
