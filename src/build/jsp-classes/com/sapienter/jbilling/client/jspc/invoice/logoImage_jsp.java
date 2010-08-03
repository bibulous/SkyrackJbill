package com.sapienter.jbilling.client.jspc.invoice;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class logoImage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fjbilling_005finvoiceLogo_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fjbilling_005finvoiceLogo_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fjbilling_005finvoiceLogo_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      if (_jspx_meth_jbilling_005finvoiceLogo_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
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

  private boolean _jspx_meth_jbilling_005finvoiceLogo_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  jbilling:invoiceLogo
    com.sapienter.jbilling.client.invoice.LogoTag _jspx_th_jbilling_005finvoiceLogo_005f0 = (com.sapienter.jbilling.client.invoice.LogoTag) _005fjspx_005ftagPool_005fjbilling_005finvoiceLogo_005fnobody.get(com.sapienter.jbilling.client.invoice.LogoTag.class);
    _jspx_th_jbilling_005finvoiceLogo_005f0.setPageContext(_jspx_page_context);
    _jspx_th_jbilling_005finvoiceLogo_005f0.setParent(null);
    int _jspx_eval_jbilling_005finvoiceLogo_005f0 = _jspx_th_jbilling_005finvoiceLogo_005f0.doStartTag();
    if (_jspx_th_jbilling_005finvoiceLogo_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fjbilling_005finvoiceLogo_005fnobody.reuse(_jspx_th_jbilling_005finvoiceLogo_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fjbilling_005finvoiceLogo_005fnobody.reuse(_jspx_th_jbilling_005finvoiceLogo_005f0);
    return false;
  }
}
