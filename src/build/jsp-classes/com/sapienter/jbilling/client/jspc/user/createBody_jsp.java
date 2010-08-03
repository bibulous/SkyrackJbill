package com.sapienter.jbilling.client.jspc.user;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.server.user.UserDTOEx;

public final class createBody_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fjbilling_005fpermission_0026_005fpermission;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fuserType_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fcurrencies_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fstyleClass;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fjbilling_005fpermission_0026_005fpermission = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fuserType_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fcurrencies_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fstyleClass = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.release();
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.release();
    _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.release();
    _005fjspx_005ftagPool_005fjbilling_005fpermission_0026_005fpermission.release();
    _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fuserType_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.release();
    _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fcurrencies_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fstyleClass.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n<p class=\"title\">\r\n");
      if (_jspx_meth_bean_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("\r\n</p>\r\n\r\n<p class=\"instr\">\r\n");
      if (_jspx_meth_bean_005fmessage_005f1(_jspx_page_context))
        return;
      out.write("\r\n</p>\r\n\r\n");
      //  html:messages
      org.apache.struts.taglib.html.MessagesTag _jspx_th_html_005fmessages_005f0 = (org.apache.struts.taglib.html.MessagesTag) _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(org.apache.struts.taglib.html.MessagesTag.class);
      _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fmessages_005f0.setParent(null);
      // /user/createBody.jsp(38,0) name = message type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fmessages_005f0.setMessage("true");
      // /user/createBody.jsp(38,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fmessages_005f0.setId("myMessage");
      int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
      if (_jspx_eval_html_005fmessages_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.String myMessage = null;
        if (_jspx_eval_html_005fmessages_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_html_005fmessages_005f0.doInitBody();
        }
        myMessage = (java.lang.String) _jspx_page_context.findAttribute("myMessage");
        do {
          out.write("\r\n\t<p>");
          if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
            return;
          out.write("</p>\r\n");
          int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
          myMessage = (java.lang.String) _jspx_page_context.findAttribute("myMessage");
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_html_005fmessages_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.popBody();
        }
      }
      if (_jspx_th_html_005fmessages_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
        return;
      }
      _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_html_005ferrors_005f0(_jspx_page_context))
        return;
      out.write("\r\n\r\n\r\n");
      //  logic:present
      org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
      _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fpresent_005f0.setParent(null);
      // /user/createBody.jsp(44,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fpresent_005f0.setParameter("customer");
      int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
      if (_jspx_eval_logic_005fpresent_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f0 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
          _jspx_th_bean_005fdefine_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f0);
          // /user/createBody.jsp(45,1) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f0.setId("jsp_is_customer");
          // /user/createBody.jsp(45,1) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f0.setValue("yes");
          // /user/createBody.jsp(45,1) name = toScope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f0.setToScope("session");
          int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
          if (_jspx_th_bean_005fdefine_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
            return;
          }
          _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
          java.lang.String jsp_is_customer = null;
          jsp_is_customer = (java.lang.String) _jspx_page_context.findAttribute("jsp_is_customer");
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fpresent_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f0);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f0);
      out.write('\r');
      out.write('\n');
      //  logic:present
      org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f1 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
      _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fpresent_005f1.setParent(null);
      // /user/createBody.jsp(47,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fpresent_005f1.setParameter("partner");
      int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
      if (_jspx_eval_logic_005fpresent_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f1 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
          _jspx_th_bean_005fdefine_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f1);
          // /user/createBody.jsp(48,1) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f1.setId("jsp_is_partner");
          // /user/createBody.jsp(48,1) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f1.setValue("yes");
          // /user/createBody.jsp(48,1) name = toScope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f1.setToScope("session");
          int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
          if (_jspx_th_bean_005fdefine_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
            return;
          }
          _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
          java.lang.String jsp_is_partner = null;
          jsp_is_partner = (java.lang.String) _jspx_page_context.findAttribute("jsp_is_partner");
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fpresent_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f1);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f1);
      out.write('\r');
      out.write('\n');
      //  logic:present
      org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f2 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
      _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fpresent_005f2.setParent(null);
      // /user/createBody.jsp(50,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fpresent_005f2.setParameter("partner_id");
      int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
      if (_jspx_eval_logic_005fpresent_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f2 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
          _jspx_th_bean_005fdefine_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f2);
          // /user/createBody.jsp(51,1) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f2.setId("jsp_partnerId");
          // /user/createBody.jsp(51,1) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f2.setValue(request.getParameter("partnerId") );
          // /user/createBody.jsp(51,1) name = toScope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_bean_005fdefine_005f2.setToScope("session");
          int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
          if (_jspx_th_bean_005fdefine_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
            return;
          }
          _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
          java.lang.String jsp_partnerId = null;
          jsp_partnerId = (java.lang.String) _jspx_page_context.findAttribute("jsp_partnerId");
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fpresent_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f2);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f2);
      out.write('\r');
      out.write('\n');
      //  logic:present
      org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f3 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.get(org.apache.struts.taglib.logic.PresentTag.class);
      _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fpresent_005f3.setParent(null);
      // /user/createBody.jsp(53,0) name = parameter type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fpresent_005f3.setParameter("frompartner");
      int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
      if (_jspx_eval_logic_005fpresent_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n    ");

       session.setAttribute("jsp_partnerId", String.valueOf(((UserDTOEx) session.getAttribute(
       			Constants.SESSION_USER_DTO)).getPartner().getId()));
    
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fpresent_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f3);
        return;
      }
      _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fparameter.reuse(_jspx_th_logic_005fpresent_005f3);
      out.write("\r\n\r\n\r\n");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fform_005f0.setParent(null);
      // /user/createBody.jsp(61,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fform_005f0.setAction("/userCreate");
      int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
      if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t <table class=\"form\">\r\n\t \t<tr class=\"form\">\r\n\t \t\t<td></td>\r\n\t \t\t<td class=\"form_prompt\">");
          if (_jspx_meth_bean_005fmessage_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t\t<td >");
          if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t</tr>\r\n\t \t<tr class=\"form\">\r\n\t \t\t<td></td>\r\n\t \t\t<td class=\"form_prompt\">");
          if (_jspx_meth_bean_005fmessage_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t\t<td >");
          if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t</tr>\r\n\t \t<tr class=\"form\">\r\n\t \t\t<td></td>\r\n\t \t\t<td class=\"form_prompt\">");
          if (_jspx_meth_bean_005fmessage_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t\t<td >");
          if (_jspx_meth_html_005fpassword_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t</tr>\r\n\t \t<tr class=\"form\">\r\n\t\t\t<td>\r\n\t\t\t\t ");
          if (_jspx_meth_jbilling_005fhelp_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t</td>\r\n\t \t\t<td class=\"form_prompt\">");
          if (_jspx_meth_bean_005fmessage_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t\t<td >");
          if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t</tr>\r\n\r\n    \t");
          //  logic:notPresent
          org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag) _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.get(org.apache.struts.taglib.logic.NotPresentTag.class);
          _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
          _jspx_th_logic_005fnotPresent_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
          // /user/createBody.jsp(88,5) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fnotPresent_005f0.setName("jsp_is_customer");
          // /user/createBody.jsp(88,5) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fnotPresent_005f0.setScope("session");
          int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
          if (_jspx_eval_logic_005fnotPresent_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n    \t");
              //  logic:notPresent
              org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (org.apache.struts.taglib.logic.NotPresentTag) _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.get(org.apache.struts.taglib.logic.NotPresentTag.class);
              _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fnotPresent_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f0);
              // /user/createBody.jsp(89,5) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fnotPresent_005f1.setName("jsp_is_partner");
              // /user/createBody.jsp(89,5) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fnotPresent_005f1.setScope("session");
              int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
              if (_jspx_eval_logic_005fnotPresent_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("    \t\t\r\n\t    ");
                  //  jbilling:permission
                  com.sapienter.jbilling.client.user.PermissionTag _jspx_th_jbilling_005fpermission_005f0 = (com.sapienter.jbilling.client.user.PermissionTag) _005fjspx_005ftagPool_005fjbilling_005fpermission_0026_005fpermission.get(com.sapienter.jbilling.client.user.PermissionTag.class);
                  _jspx_th_jbilling_005fpermission_005f0.setPageContext(_jspx_page_context);
                  _jspx_th_jbilling_005fpermission_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f1);
                  // /user/createBody.jsp(90,5) name = permission type = java.lang.Integer reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                  _jspx_th_jbilling_005fpermission_005f0.setPermission(Constants.P_USER_CREATE_TYPE);
                  int _jspx_eval_jbilling_005fpermission_005f0 = _jspx_th_jbilling_005fpermission_005f0.doStartTag();
                  if (_jspx_eval_jbilling_005fpermission_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t \t\t");
                      if (_jspx_meth_jbilling_005fgetOptions_005f0(_jspx_th_jbilling_005fpermission_005f0, _jspx_page_context))
                        return;
                      out.write("\r\n\t\t\t <tr class=\"form\">\r\n\t\t\t \t <td></td>\r\n\t\t\t\t <td class=\"form_prompt\">");
                      if (_jspx_meth_bean_005fmessage_005f6(_jspx_th_jbilling_005fpermission_005f0, _jspx_page_context))
                        return;
                      out.write("</td>\r\n\t\t\t\t <td >\r\n\t\t\t\t \t");
                      out.write("\r\n\t\t\t\t \t");
                      //  html:select
                      org.apache.struts.taglib.html.SelectTag _jspx_th_html_005fselect_005f0 = (org.apache.struts.taglib.html.SelectTag) _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.get(org.apache.struts.taglib.html.SelectTag.class);
                      _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
                      _jspx_th_html_005fselect_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_jbilling_005fpermission_005f0);
                      // /user/createBody.jsp(97,6) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                      _jspx_th_html_005fselect_005f0.setProperty("type");
                      int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
                      if (_jspx_eval_html_005fselect_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        if (_jspx_eval_html_005fselect_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                          out = _jspx_page_context.pushBody();
                          _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                          _jspx_th_html_005fselect_005f0.doInitBody();
                        }
                        do {
                          out.write("\r\n\t\t\t\t\t\t");
                          //  html:options
                          org.apache.struts.taglib.html.OptionsTag _jspx_th_html_005foptions_005f0 = (org.apache.struts.taglib.html.OptionsTag) _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.get(org.apache.struts.taglib.html.OptionsTag.class);
                          _jspx_th_html_005foptions_005f0.setPageContext(_jspx_page_context);
                          _jspx_th_html_005foptions_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fselect_005f0);
                          // /user/createBody.jsp(98,6) name = collection type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                          _jspx_th_html_005foptions_005f0.setCollection(Constants.PAGE_USER_TYPES);
                          // /user/createBody.jsp(98,6) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                          _jspx_th_html_005foptions_005f0.setProperty("code");
                          // /user/createBody.jsp(98,6) name = labelProperty type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                          _jspx_th_html_005foptions_005f0.setLabelProperty("description");
                          int _jspx_eval_html_005foptions_005f0 = _jspx_th_html_005foptions_005f0.doStartTag();
                          if (_jspx_th_html_005foptions_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                            _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.reuse(_jspx_th_html_005foptions_005f0);
                            return;
                          }
                          _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.reuse(_jspx_th_html_005foptions_005f0);
                          out.write("\r\n\t\t\t\t\t");
                          int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
                          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                            break;
                        } while (true);
                        if (_jspx_eval_html_005fselect_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                          out = _jspx_page_context.popBody();
                        }
                      }
                      if (_jspx_th_html_005fselect_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
                        return;
                      }
                      _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
                      out.write("\r\n\t\t\t\t </td>\r\n\t\t\t </tr>\r\n \t\t");
                      int evalDoAfterBody = _jspx_th_jbilling_005fpermission_005f0.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_jbilling_005fpermission_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _005fjspx_005ftagPool_005fjbilling_005fpermission_0026_005fpermission.reuse(_jspx_th_jbilling_005fpermission_005f0);
                    return;
                  }
                  _005fjspx_005ftagPool_005fjbilling_005fpermission_0026_005fpermission.reuse(_jspx_th_jbilling_005fpermission_005f0);
                  out.write("\r\n\t\t");
                  int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
                return;
              }
              _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
              out.write("\r\n\t\t");
              int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fnotPresent_005f0);
            return;
          }
          _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fnotPresent_005f0);
          out.write("\r\n\t\t");
          //  logic:present
          org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f4 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.get(org.apache.struts.taglib.logic.PresentTag.class);
          _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
          _jspx_th_logic_005fpresent_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
          // /user/createBody.jsp(107,2) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fpresent_005f4.setName("jsp_is_customer");
          // /user/createBody.jsp(107,2) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fpresent_005f4.setScope("session");
          int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
          if (_jspx_eval_logic_005fpresent_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t");
              //  html:hidden
              org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
              _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f4);
              // /user/createBody.jsp(108,3) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fhidden_005f0.setProperty("type");
              // /user/createBody.jsp(108,3) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fhidden_005f0.setValue(Constants.TYPE_CUSTOMER.toString());
              int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
              if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
                return;
              }
              _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
              out.write("\r\n\t\t");
              int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_005fpresent_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fpresent_005f4);
            return;
          }
          _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fpresent_005f4);
          out.write("\r\n\t\t");
          //  logic:present
          org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f5 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.get(org.apache.struts.taglib.logic.PresentTag.class);
          _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
          _jspx_th_logic_005fpresent_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
          // /user/createBody.jsp(110,2) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fpresent_005f5.setName("jsp_is_partner");
          // /user/createBody.jsp(110,2) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fpresent_005f5.setScope("session");
          int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
          if (_jspx_eval_logic_005fpresent_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t");
              //  html:hidden
              org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
              _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
              _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f5);
              // /user/createBody.jsp(111,3) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fhidden_005f1.setProperty("type");
              // /user/createBody.jsp(111,3) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fhidden_005f1.setValue(Constants.TYPE_PARTNER.toString());
              int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
              if (_jspx_th_html_005fhidden_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
                return;
              }
              _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
              out.write("\r\n\t\t");
              int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_005fpresent_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fpresent_005f5);
            return;
          }
          _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fpresent_005f5);
          out.write("\r\n\r\n\t \t<tr class=\"form\">\r\n\t\t\t<td>\r\n\t\t\t\t ");
          if (_jspx_meth_jbilling_005fhelp_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t</td>\r\n\t \t\t<td class=\"form_prompt\">");
          if (_jspx_meth_bean_005fmessage_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("</td>\r\n\t \t\t<td >\r\n\t \t\t\t");
          if (_jspx_meth_jbilling_005fgetOptions_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t");
          //  html:select
          org.apache.struts.taglib.html.SelectTag _jspx_th_html_005fselect_005f1 = (org.apache.struts.taglib.html.SelectTag) _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.get(org.apache.struts.taglib.html.SelectTag.class);
          _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
          _jspx_th_html_005fselect_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
          // /user/createBody.jsp(123,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_html_005fselect_005f1.setProperty("currencyId");
          int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
          if (_jspx_eval_html_005fselect_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            if (_jspx_eval_html_005fselect_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_html_005fselect_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_html_005fselect_005f1.doInitBody();
            }
            do {
              out.write("\r\n\t\t\t\t\t");
              //  html:options
              org.apache.struts.taglib.html.OptionsTag _jspx_th_html_005foptions_005f1 = (org.apache.struts.taglib.html.OptionsTag) _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.get(org.apache.struts.taglib.html.OptionsTag.class);
              _jspx_th_html_005foptions_005f1.setPageContext(_jspx_page_context);
              _jspx_th_html_005foptions_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fselect_005f1);
              // /user/createBody.jsp(124,5) name = collection type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005foptions_005f1.setCollection(Constants.PAGE_CURRENCIES);
              // /user/createBody.jsp(124,5) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005foptions_005f1.setProperty("code");
              // /user/createBody.jsp(124,5) name = labelProperty type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005foptions_005f1.setLabelProperty("description");
              int _jspx_eval_html_005foptions_005f1 = _jspx_th_html_005foptions_005f1.doStartTag();
              if (_jspx_th_html_005foptions_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.reuse(_jspx_th_html_005foptions_005f1);
                return;
              }
              _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.reuse(_jspx_th_html_005foptions_005f1);
              out.write("\r\n\t\t\t\t");
              int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_html_005fselect_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_html_005fselect_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
            return;
          }
          _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
          out.write("\r\n\t \t\t</td>\r\n\t \t</tr>\r\n\t \t\r\n\t \t");
          out.write("\r\n\t \t");
          //  logic:present
          org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f6 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.get(org.apache.struts.taglib.logic.PresentTag.class);
          _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
          _jspx_th_logic_005fpresent_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
          // /user/createBody.jsp(133,3) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fpresent_005f6.setName("jsp_is_customer");
          // /user/createBody.jsp(133,3) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
          _jspx_th_logic_005fpresent_005f6.setScope("session");
          int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
          if (_jspx_eval_logic_005fpresent_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n    \t");
              if (_jspx_meth_logic_005fnotPresent_005f2(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("\r\n    \t");
              //  logic:present
              org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f7 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.get(org.apache.struts.taglib.logic.PresentTag.class);
              _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fpresent_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
              // /user/createBody.jsp(145,5) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fpresent_005f7.setName("jsp_partnerId");
              // /user/createBody.jsp(145,5) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fpresent_005f7.setScope("session");
              int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
              if (_jspx_eval_logic_005fpresent_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n    \t\t");
                  //  html:hidden
                  org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f2 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
                  _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
                  _jspx_th_html_005fhidden_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f7);
                  // /user/createBody.jsp(146,6) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                  _jspx_th_html_005fhidden_005f2.setProperty("partnerId");
                  // /user/createBody.jsp(146,6) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                  _jspx_th_html_005fhidden_005f2.setValue((String)session.getAttribute("jsp_partnerId"));
                  int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
                  if (_jspx_th_html_005fhidden_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
                    return;
                  }
                  _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
                  out.write("\r\n\t \t");
                  int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_005fpresent_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fpresent_005f7);
                return;
              }
              _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fpresent_005f7);
              out.write("\r\n\t \t\r\n\t \t<tr class=\"form\">\r\n\t\t\t<td>\r\n\t\t\t\t ");
              if (_jspx_meth_jbilling_005fhelp_005f3(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t</td>\r\n\t \t\t<td class=\"form_prompt\">");
              if (_jspx_meth_bean_005fmessage_005f9(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("</td>\r\n\t \t\t<td >");
              if (_jspx_meth_html_005ftext_005f3(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("</td>\r\n\t \t</tr>\r\n\t \t<tr class=\"form\">\r\n\t\t\t<td>\r\n\t\t\t\t ");
              if (_jspx_meth_jbilling_005fhelp_005f4(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t</td>\r\n\t \t\t<td class=\"form_prompt\">");
              if (_jspx_meth_bean_005fmessage_005f10(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("</td>\r\n\t \t\t<td >");
              if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("</td>\r\n\t \t</tr>\r\n\t \t<tr class=\"form\">\r\n\t\t\t<td>\r\n\t\t\t\t ");
              if (_jspx_meth_jbilling_005fhelp_005f5(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t</td>\r\n\t \t\t<td class=\"form_prompt\">");
              if (_jspx_meth_bean_005fmessage_005f11(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("</td>\r\n\t \t\t<td >");
              if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
                return;
              out.write("</td>\r\n\t \t</tr>\r\n\t \t\r\n\t \t");
              int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_005fpresent_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fpresent_005f6);
            return;
          }
          _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fpresent_005f6);
          out.write("\r\n\r\n\t \t<tr class=\"form\">\r\n\t \t\t<td></td>\r\n\t \t\t<td class=\"form_button\" colspan=\"2\">\r\n              \t");
          if (_jspx_meth_html_005fsubmit_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
            return;
          out.write("\r\n            </td>\r\n\t\t</tr>\t \r\n\t</table>\r\n");
          int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
        return;
      }
      _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
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

  private boolean _jspx_meth_bean_005fmessage_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f0.setParent(null);
    // /user/createBody.jsp(31,0) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f0.setKey("user.create.title");
    int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
    if (_jspx_th_bean_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f1.setParent(null);
    // /user/createBody.jsp(35,0) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f1.setKey("user.create.instr");
    int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
    if (_jspx_th_bean_005fmessage_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fmessages_005f0);
    // /user/createBody.jsp(39,4) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f0.setName("myMessage");
    int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
    if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005ferrors_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:errors
    org.apache.struts.taglib.html.ErrorsTag _jspx_th_html_005ferrors_005f0 = (org.apache.struts.taglib.html.ErrorsTag) _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(org.apache.struts.taglib.html.ErrorsTag.class);
    _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005ferrors_005f0.setParent(null);
    int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
    if (_jspx_th_html_005ferrors_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f2 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f2.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(65,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f2.setKey("user.prompt.username");
    int _jspx_eval_bean_005fmessage_005f2 = _jspx_th_bean_005fmessage_005f2.doStartTag();
    if (_jspx_th_bean_005fmessage_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(66,9) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f0.setProperty("username");
    // /user/createBody.jsp(66,9) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f0.setSize("15");
    int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
    if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f3 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f3.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(70,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f3.setKey("user.prompt.password");
    int _jspx_eval_bean_005fmessage_005f3 = _jspx_th_bean_005fmessage_005f3.doStartTag();
    if (_jspx_th_bean_005fmessage_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
    return false;
  }

  private boolean _jspx_meth_html_005fpassword_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:password
    org.apache.struts.taglib.html.PasswordTag _jspx_th_html_005fpassword_005f0 = (org.apache.struts.taglib.html.PasswordTag) _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody.get(org.apache.struts.taglib.html.PasswordTag.class);
    _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fpassword_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(71,9) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fpassword_005f0.setProperty("password");
    // /user/createBody.jsp(71,9) name = redisplay type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fpassword_005f0.setRedisplay(false);
    // /user/createBody.jsp(71,9) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fpassword_005f0.setSize("15");
    int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
    if (_jspx_th_html_005fpassword_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f4 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f4.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(75,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f4.setKey("user.prompt.verifyPassword");
    int _jspx_eval_bean_005fmessage_005f4 = _jspx_th_bean_005fmessage_005f4.doStartTag();
    if (_jspx_th_bean_005fmessage_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f4);
    return false;
  }

  private boolean _jspx_meth_html_005fpassword_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:password
    org.apache.struts.taglib.html.PasswordTag _jspx_th_html_005fpassword_005f1 = (org.apache.struts.taglib.html.PasswordTag) _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody.get(org.apache.struts.taglib.html.PasswordTag.class);
    _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005fpassword_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(76,9) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fpassword_005f1.setProperty("verifyPassword");
    // /user/createBody.jsp(76,9) name = redisplay type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fpassword_005f1.setRedisplay(false);
    // /user/createBody.jsp(76,9) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fpassword_005f1.setSize("15");
    int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
    if (_jspx_th_html_005fpassword_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fsize_005fredisplay_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fhelp_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:help
    com.sapienter.jbilling.client.util.HelpTag _jspx_th_jbilling_005fhelp_005f0 = (com.sapienter.jbilling.client.util.HelpTag) _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.get(com.sapienter.jbilling.client.util.HelpTag.class);
    _jspx_th_jbilling_005fhelp_005f0.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fhelp_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(80,5) name = page type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f0.setPage("users");
    // /user/createBody.jsp(80,5) name = anchor type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f0.setAnchor("email");
    int _jspx_eval_jbilling_005fhelp_005f0 = _jspx_th_jbilling_005fhelp_005f0.doStartTag();
    if (_jspx_eval_jbilling_005fhelp_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t <img border=\"0\" src=\"/billing/graphics/help.gif\"/>\r\n\t\t\t\t ");
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

  private boolean _jspx_meth_bean_005fmessage_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f5 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f5.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(84,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f5.setKey("user.prompt.email");
    int _jspx_eval_bean_005fmessage_005f5 = _jspx_th_bean_005fmessage_005f5.doStartTag();
    if (_jspx_th_bean_005fmessage_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f5);
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f1 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(85,9) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f1.setProperty("email");
    // /user/createBody.jsp(85,9) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f1.setSize("50");
    int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
    if (_jspx_th_html_005ftext_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fgetOptions_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_jbilling_005fpermission_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:getOptions
    com.sapienter.jbilling.client.util.GetOptionsTag _jspx_th_jbilling_005fgetOptions_005f0 = (com.sapienter.jbilling.client.util.GetOptionsTag) _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fuserType_005fnobody.get(com.sapienter.jbilling.client.util.GetOptionsTag.class);
    _jspx_th_jbilling_005fgetOptions_005f0.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fgetOptions_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_jbilling_005fpermission_005f0);
    // /user/createBody.jsp(91,4) name = userType type = java.lang.Boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fgetOptions_005f0.setUserType(new Boolean(true));
    int _jspx_eval_jbilling_005fgetOptions_005f0 = _jspx_th_jbilling_005fgetOptions_005f0.doStartTag();
    if (_jspx_th_jbilling_005fgetOptions_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fuserType_005fnobody.reuse(_jspx_th_jbilling_005fgetOptions_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fuserType_005fnobody.reuse(_jspx_th_jbilling_005fgetOptions_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_jbilling_005fpermission_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f6 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f6.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_jbilling_005fpermission_005f0);
    // /user/createBody.jsp(94,29) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f6.setKey("user.prompt.type");
    int _jspx_eval_bean_005fmessage_005f6 = _jspx_th_bean_005fmessage_005f6.doStartTag();
    if (_jspx_th_bean_005fmessage_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f6);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fhelp_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:help
    com.sapienter.jbilling.client.util.HelpTag _jspx_th_jbilling_005fhelp_005f1 = (com.sapienter.jbilling.client.util.HelpTag) _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.get(com.sapienter.jbilling.client.util.HelpTag.class);
    _jspx_th_jbilling_005fhelp_005f1.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fhelp_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(116,5) name = page type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f1.setPage("users");
    // /user/createBody.jsp(116,5) name = anchor type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f1.setAnchor("currency");
    int _jspx_eval_jbilling_005fhelp_005f1 = _jspx_th_jbilling_005fhelp_005f1.doStartTag();
    if (_jspx_eval_jbilling_005fhelp_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t <img border=\"0\" src=\"/billing/graphics/help.gif\"/>\r\n\t\t\t\t ");
        int evalDoAfterBody = _jspx_th_jbilling_005fhelp_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_jbilling_005fhelp_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f1);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f7 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f7.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(120,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f7.setKey("currency.external.prompt.name");
    int _jspx_eval_bean_005fmessage_005f7 = _jspx_th_bean_005fmessage_005f7.doStartTag();
    if (_jspx_th_bean_005fmessage_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f7);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fgetOptions_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:getOptions
    com.sapienter.jbilling.client.util.GetOptionsTag _jspx_th_jbilling_005fgetOptions_005f1 = (com.sapienter.jbilling.client.util.GetOptionsTag) _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fcurrencies_005fnobody.get(com.sapienter.jbilling.client.util.GetOptionsTag.class);
    _jspx_th_jbilling_005fgetOptions_005f1.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fgetOptions_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(122,5) name = currencies type = java.lang.Boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fgetOptions_005f1.setCurrencies(new Boolean(true));
    int _jspx_eval_jbilling_005fgetOptions_005f1 = _jspx_th_jbilling_005fgetOptions_005f1.doStartTag();
    if (_jspx_th_jbilling_005fgetOptions_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fcurrencies_005fnobody.reuse(_jspx_th_jbilling_005fgetOptions_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005fgetOptions_0026_005fcurrencies_005fnobody.reuse(_jspx_th_jbilling_005fgetOptions_005f1);
    return false;
  }

  private boolean _jspx_meth_logic_005fnotPresent_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notPresent
    org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (org.apache.struts.taglib.logic.NotPresentTag) _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.get(org.apache.struts.taglib.logic.NotPresentTag.class);
    _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
    _jspx_th_logic_005fnotPresent_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(134,5) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fnotPresent_005f2.setName("jsp_partnerId");
    // /user/createBody.jsp(134,5) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_logic_005fnotPresent_005f2.setScope("session");
    int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
    if (_jspx_eval_logic_005fnotPresent_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t \t<tr class=\"form\">\r\n\t\t\t<td>\r\n\t\t\t\t ");
        if (_jspx_meth_jbilling_005fhelp_005f2(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
          return true;
        out.write("\r\n\t\t\t</td>\r\n\t \t\t<td class=\"form_prompt\">");
        if (_jspx_meth_bean_005fmessage_005f8(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
          return true;
        out.write("</td>\r\n\t \t\t<td >");
        if (_jspx_meth_html_005ftext_005f2(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
          return true;
        out.write("</td>\r\n\t \t</tr>\r\n\t \t");
        int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fnotPresent_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fscope_005fname.reuse(_jspx_th_logic_005fnotPresent_005f2);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fhelp_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:help
    com.sapienter.jbilling.client.util.HelpTag _jspx_th_jbilling_005fhelp_005f2 = (com.sapienter.jbilling.client.util.HelpTag) _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.get(com.sapienter.jbilling.client.util.HelpTag.class);
    _jspx_th_jbilling_005fhelp_005f2.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fhelp_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f2);
    // /user/createBody.jsp(137,5) name = page type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f2.setPage("users");
    // /user/createBody.jsp(137,5) name = anchor type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f2.setAnchor("partner");
    int _jspx_eval_jbilling_005fhelp_005f2 = _jspx_th_jbilling_005fhelp_005f2.doStartTag();
    if (_jspx_eval_jbilling_005fhelp_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t <img border=\"0\" src=\"/billing/graphics/help.gif\"/>\r\n\t\t\t\t ");
        int evalDoAfterBody = _jspx_th_jbilling_005fhelp_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_jbilling_005fhelp_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f2);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f8 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f8.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f2);
    // /user/createBody.jsp(141,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f8.setKey("user.prompt.partner");
    int _jspx_eval_bean_005fmessage_005f8 = _jspx_th_bean_005fmessage_005f8.doStartTag();
    if (_jspx_th_bean_005fmessage_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f8);
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f2 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
    _jspx_th_html_005ftext_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fnotPresent_005f2);
    // /user/createBody.jsp(142,9) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f2.setProperty("partnerId");
    // /user/createBody.jsp(142,9) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f2.setSize("5");
    int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
    if (_jspx_th_html_005ftext_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fhelp_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:help
    com.sapienter.jbilling.client.util.HelpTag _jspx_th_jbilling_005fhelp_005f3 = (com.sapienter.jbilling.client.util.HelpTag) _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.get(com.sapienter.jbilling.client.util.HelpTag.class);
    _jspx_th_jbilling_005fhelp_005f3.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fhelp_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(151,5) name = page type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f3.setPage("users");
    // /user/createBody.jsp(151,5) name = anchor type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f3.setAnchor("parent");
    int _jspx_eval_jbilling_005fhelp_005f3 = _jspx_th_jbilling_005fhelp_005f3.doStartTag();
    if (_jspx_eval_jbilling_005fhelp_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t <img border=\"0\" src=\"/billing/graphics/help.gif\"/>\r\n\t\t\t\t ");
        int evalDoAfterBody = _jspx_th_jbilling_005fhelp_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_jbilling_005fhelp_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f3);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f9 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f9.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(155,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f9.setKey("user.prompt.parent");
    int _jspx_eval_bean_005fmessage_005f9 = _jspx_th_bean_005fmessage_005f9.doStartTag();
    if (_jspx_th_bean_005fmessage_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f9);
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f3 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
    _jspx_th_html_005ftext_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(156,9) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f3.setProperty("parentId");
    // /user/createBody.jsp(156,9) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f3.setSize("5");
    int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
    if (_jspx_th_html_005ftext_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fhelp_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:help
    com.sapienter.jbilling.client.util.HelpTag _jspx_th_jbilling_005fhelp_005f4 = (com.sapienter.jbilling.client.util.HelpTag) _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.get(com.sapienter.jbilling.client.util.HelpTag.class);
    _jspx_th_jbilling_005fhelp_005f4.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fhelp_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(160,5) name = page type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f4.setPage("users");
    // /user/createBody.jsp(160,5) name = anchor type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f4.setAnchor("parent");
    int _jspx_eval_jbilling_005fhelp_005f4 = _jspx_th_jbilling_005fhelp_005f4.doStartTag();
    if (_jspx_eval_jbilling_005fhelp_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t <img border=\"0\" src=\"/billing/graphics/help.gif\"/>\r\n\t\t\t\t ");
        int evalDoAfterBody = _jspx_th_jbilling_005fhelp_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_jbilling_005fhelp_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f4);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f10 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f10.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(164,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f10.setKey("user.prompt.invoiceChild");
    int _jspx_eval_bean_005fmessage_005f10 = _jspx_th_bean_005fmessage_005f10.doStartTag();
    if (_jspx_th_bean_005fmessage_005f10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f10);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f10);
    return false;
  }

  private boolean _jspx_meth_html_005fcheckbox_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:checkbox
    org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (org.apache.struts.taglib.html.CheckboxTag) _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
    _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fcheckbox_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(165,9) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fcheckbox_005f0.setProperty("chbx_invoiceChild");
    int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
    if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
    return false;
  }

  private boolean _jspx_meth_jbilling_005fhelp_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:help
    com.sapienter.jbilling.client.util.HelpTag _jspx_th_jbilling_005fhelp_005f5 = (com.sapienter.jbilling.client.util.HelpTag) _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.get(com.sapienter.jbilling.client.util.HelpTag.class);
    _jspx_th_jbilling_005fhelp_005f5.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005fhelp_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(169,5) name = page type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f5.setPage("users");
    // /user/createBody.jsp(169,5) name = anchor type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_jbilling_005fhelp_005f5.setAnchor("allow");
    int _jspx_eval_jbilling_005fhelp_005f5 = _jspx_th_jbilling_005fhelp_005f5.doStartTag();
    if (_jspx_eval_jbilling_005fhelp_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t <img border=\"0\" src=\"/billing/graphics/help.gif\"/>\r\n\t\t\t\t ");
        int evalDoAfterBody = _jspx_th_jbilling_005fhelp_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_jbilling_005fhelp_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005fhelp_0026_005fpage_005fanchor.reuse(_jspx_th_jbilling_005fhelp_005f5);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f11 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f11.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(173,28) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f11.setKey("user.prompt.isParent");
    int _jspx_eval_bean_005fmessage_005f11 = _jspx_th_bean_005fmessage_005f11.doStartTag();
    if (_jspx_th_bean_005fmessage_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f11);
    return false;
  }

  private boolean _jspx_meth_html_005fcheckbox_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:checkbox
    org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (org.apache.struts.taglib.html.CheckboxTag) _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
    _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005fcheckbox_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f6);
    // /user/createBody.jsp(174,9) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fcheckbox_005f1.setProperty("chbx_is_parent");
    int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
    if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005fsubmit_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:submit
    org.apache.struts.taglib.html.SubmitTag _jspx_th_html_005fsubmit_005f0 = (org.apache.struts.taglib.html.SubmitTag) _005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fstyleClass.get(org.apache.struts.taglib.html.SubmitTag.class);
    _jspx_th_html_005fsubmit_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fsubmit_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /user/createBody.jsp(182,15) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fsubmit_005f0.setStyleClass("form_button");
    int _jspx_eval_html_005fsubmit_005f0 = _jspx_th_html_005fsubmit_005f0.doStartTag();
    if (_jspx_eval_html_005fsubmit_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_html_005fsubmit_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_html_005fsubmit_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_html_005fsubmit_005f0.doInitBody();
      }
      do {
        out.write("\r\n              \t\t");
        if (_jspx_meth_bean_005fmessage_005f12(_jspx_th_html_005fsubmit_005f0, _jspx_page_context))
          return true;
        out.write("\r\n              \t");
        int evalDoAfterBody = _jspx_th_html_005fsubmit_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_html_005fsubmit_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_html_005fsubmit_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fstyleClass.reuse(_jspx_th_html_005fsubmit_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fstyleClass.reuse(_jspx_th_html_005fsubmit_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fsubmit_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f12 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    _jspx_th_bean_005fmessage_005f12.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fmessage_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fsubmit_005f0);
    // /user/createBody.jsp(183,16) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fmessage_005f12.setKey("user.create.submit");
    int _jspx_eval_bean_005fmessage_005f12 = _jspx_th_bean_005fmessage_005f12.doStartTag();
    if (_jspx_th_bean_005fmessage_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f12);
    return false;
  }
}
