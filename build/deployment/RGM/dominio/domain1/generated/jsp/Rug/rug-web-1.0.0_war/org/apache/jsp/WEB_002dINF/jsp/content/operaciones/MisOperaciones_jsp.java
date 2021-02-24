package org.apache.jsp.WEB_002dINF.jsp.content.operaciones;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;

public final class MisOperaciones_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_hidden_value_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_hidden_value_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_hidden_value_name_id_nobody.release();
    _jspx_tagPool_s_property_value_nobody.release();
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
      response.setHeader("X-Powered-By", "JSP/2.3");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<main>\r\n");
      out.write("\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t<div class=\"col s10\">\r\n");
      out.write("\t\t\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t\t\t<div class=\"card-content\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col s12\">\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"card-title\">Mis Operaciones</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"section row\">\r\n");
      out.write("\t\t\t\t\t \t<div class=\"col s12\">\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<ul class=\"tabs\">\r\n");
      out.write("\t\t\t\t\t\t\t    <li class=\"tab col s3\"><a class=\"blue-text text-darken-2\" id =\"tabs1\" href =\"#\" onclick = \"iniciaPaginacionPendientes('");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("','1')\">Pendientes de Captura de Datos</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t    <li class=\"tab col s3\"><a class=\"blue-text text-darken-2\" id = \"tabs2\" href =\"#\" onclick = \"iniciaPaginacionPendientes('");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write("','2')\">Pendientes de Confirmaci&oacute;n</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t    <li class=\"tab col s3\"><a class=\"blue-text text-darken-2\" id = \"tabs3\" href =\"#\" onclick = \"iniciaPaginacionPenAsMultiples('");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write("')\">Pendientes de Confirmaci&oacute;n Registros Multiples</a></li>\t\t\t\t\t\t\t    \t\t\t\t\t\t\t    \r\n");
      out.write("\t\t\t\t\t\t\t    <li class=\"tab col s3\"><a class=\"blue-text text-darken-2\" id = \"tabs4\" href =\"#\" onclick = \"iniciaPaginacionPendientes('");
      if (_jspx_meth_s_property_3(_jspx_page_context))
        return;
      out.write("','3')\">Terminados</a>  </li>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"indicator blue\" style=\"z-index:1\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"section\" id=\"tabs-1\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row note\">\t\t\t\t\t    \t\r\n");
      out.write("\t\t\t\t\t        \t<span>Pendientes de Captura de Datos</span>\t\t\t\t\t   \t\t\r\n");
      out.write("\t\t\t\t\t \t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t    <div id=\"OpPendientes\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"tabs-2\" class=\"section\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row note\">\t\t\t\t\t    \t\r\n");
      out.write("\t\t\t\t\t        \t<span>Pendientes de Confirmacion</span>\t\t\t\t\t   \t\t\r\n");
      out.write("\t\t\t\t\t \t</div>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t    <div id=\"OpPenFirma\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"tabs-3\" class=\"section\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row note\">\t\t\t\t\t    \t\r\n");
      out.write("\t\t\t\t\t        \t<span>Pendientes de Confirmacion Registros Multiples</span>\t\t\t\t\t   \t\t\r\n");
      out.write("\t\t\t\t\t \t</div>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t    <div id=\"OpPenFirmaMasiva\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"tabs-4\" class=\"section\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row note\">\t\t\t\t\t    \t\r\n");
      out.write("\t\t\t\t\t        \t<span>Terminados</span>\t\t\t\t\t   \t\t\r\n");
      out.write("\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t \t\t<form>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s4\">\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"terfiltro\" name=\"terfiltro\" placeholder=\"Buscar\" onkeypress=\"return runScript(event)\">\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col s4\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn waves-effect indigo\" onclick = \"buscarporfiltro()\" >\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"fooicon fooicon-search\"></span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<p class=\"waves-effect waves-light btn\" onclick=\"ExportExcelTerminadas()\">Exportar</p>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t \t\t<div id=\"OpTerminadas\">\r\n");
      out.write("\t\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t\t \t</div>\t\t\t\t\t\t\t\t\t\t\t   \r\n");
      out.write("\t\t\t\t\t</div> \r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_s_hidden_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\t\r\n");
      out.write("</main>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\tfunction ExportExcelTerminadas(){\r\n");
      out.write("\t\tvar idPersona=document.getElementById('idPersona').value;\r\n");
      out.write("\t\tvar filtros = getObject('terfiltro').value;\r\n");
      out.write("\t\tvar URL=\"");
      out.print(request.getContextPath());
      out.write("/home/exportOperaciones.do?persona=\" + idPersona + \"&filtroExcel=\" + filtros;\r\n");
      out.write("\t\twindow.open(URL, \"_blank\");\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_s_property_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_0 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_0.setPageContext(_jspx_page_context);
    _jspx_th_s_property_0.setParent(null);
    _jspx_th_s_property_0.setValue("idPersona");
    int _jspx_eval_s_property_0 = _jspx_th_s_property_0.doStartTag();
    if (_jspx_th_s_property_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
    return false;
  }

  private boolean _jspx_meth_s_property_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_1 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_1.setPageContext(_jspx_page_context);
    _jspx_th_s_property_1.setParent(null);
    _jspx_th_s_property_1.setValue("idPersona");
    int _jspx_eval_s_property_1 = _jspx_th_s_property_1.doStartTag();
    if (_jspx_th_s_property_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
    return false;
  }

  private boolean _jspx_meth_s_property_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_2 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_2.setPageContext(_jspx_page_context);
    _jspx_th_s_property_2.setParent(null);
    _jspx_th_s_property_2.setValue("idPersona");
    int _jspx_eval_s_property_2 = _jspx_th_s_property_2.doStartTag();
    if (_jspx_th_s_property_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
    return false;
  }

  private boolean _jspx_meth_s_property_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_3 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_3.setPageContext(_jspx_page_context);
    _jspx_th_s_property_3.setParent(null);
    _jspx_th_s_property_3.setValue("idPersona");
    int _jspx_eval_s_property_3 = _jspx_th_s_property_3.doStartTag();
    if (_jspx_th_s_property_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
    return false;
  }

  private boolean _jspx_meth_s_hidden_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:hidden
    org.apache.struts2.views.jsp.ui.HiddenTag _jspx_th_s_hidden_0 = (org.apache.struts2.views.jsp.ui.HiddenTag) _jspx_tagPool_s_hidden_value_name_id_nobody.get(org.apache.struts2.views.jsp.ui.HiddenTag.class);
    _jspx_th_s_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_s_hidden_0.setParent(null);
    _jspx_th_s_hidden_0.setValue("%{idPersona}");
    _jspx_th_s_hidden_0.setId("idPersona");
    _jspx_th_s_hidden_0.setName("idPersona");
    int _jspx_eval_s_hidden_0 = _jspx_th_s_hidden_0.doStartTag();
    if (_jspx_th_s_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_hidden_value_name_id_nobody.reuse(_jspx_th_s_hidden_0);
      return true;
    }
    _jspx_tagPool_s_hidden_value_name_id_nobody.reuse(_jspx_th_s_hidden_0);
    return false;
  }
}
