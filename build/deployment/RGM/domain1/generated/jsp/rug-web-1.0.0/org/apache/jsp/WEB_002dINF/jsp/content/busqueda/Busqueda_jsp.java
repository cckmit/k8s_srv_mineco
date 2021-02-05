package org.apache.jsp.WEB_002dINF.jsp.content.busqueda;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import mx.gob.se.rug.garantia.dao.GarantiasDAO;
import java.util.List;
import java.util.Iterator;

public final class Busqueda_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_type_name_id_class_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_name_id_class_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_form_theme_namespace_cssClass_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_if_test;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_else;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_type_name_id_class_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_name_id_class_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_form_theme_namespace_cssClass_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_else = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.release();
    _jspx_tagPool_s_textfield_type_name_id_class_nobody.release();
    _jspx_tagPool_s_textfield_name_id_class_nobody.release();
    _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.release();
    _jspx_tagPool_s_form_theme_namespace_cssClass_action.release();
    _jspx_tagPool_s_property_value_nobody.release();
    _jspx_tagPool_s_if_test.release();
    _jspx_tagPool_s_else.release();
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"section\"></div>\r\n");
      out.write("<main>\r\n");
      out.write("\t<div class=\"container\">\r\n");
      out.write("\t\t<div class=\"card\">\r\n");
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\t\t\t<div class=\"card-tabs\">\r\n");
      out.write("\t\t\t\t<ul class=\"tabs tabs-fixed-width\">\r\n");
      out.write("\t\t\t\t\t<li class=\"tab\"><a class=\"active\" href=\"#criterio\">B&uacute;squeda  por Criterios</a></li>\r\n");
      out.write("\t\t\t\t\t<li class=\"tab\"><a href=\"#secundaria\">B&uacute;squeda  Secundaria</a></li>\r\n");
      out.write("\t\t\t\t\t<li class=\"tab\"><a href=\"#factura\">B&uacute;squeda por Factura</a></li>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"card-content grey lighten-4\">\r\n");
      out.write("\t\t\t\t<div id=\"criterio\">\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_s_form_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div id=\"secundaria\">\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_s_form_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div id=\"factura\">\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_s_form_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div class=\"col s12\">\r\n");
      out.write("\t\t\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t\t\t<div class=\"card-content\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"card-title\">Resultado de la Consulta</span>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"resultadoDIV\"></div>  \r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</main>\r\n");
      out.write("<div class=\"section\"></div>\r\n");
      out.write("\r\n");
      out.write("<div id=\"cuerpo\"><!-- Empieza div cuerpo -->\r\n");
      out.write("\t<div align=\"center\" >\r\n");
      out.write("    <table width=\"960px\"> \r\n");
      out.write("      <tr>\r\n");
      out.write("\t\t\t<td valign=\"top\">\r\n");
      out.write("        \r\n");
      out.write("\t\t\t\t<table width=\"100%\" height=\"100%\" >\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<tr><td> \r\n");
      out.write("            \r\n");
      out.write("            \r\n");
      out.write("              <table width=\"100%\">\r\n");
      out.write("                <tr>\r\n");
      out.write("                  <td>\r\n");
      out.write("                    <table>  \t\t\t\t\t\r\n");
      out.write("                      ");
      if (_jspx_meth_s_if_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                      ");
      if (_jspx_meth_s_else_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                    </table>\r\n");
      out.write("                  </td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                \r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- \t<tr> <td height=\"18px\" style=\"padding-bottom: 1px; padding-top: 18px\"> &nbsp;</td> </tr> -->\r\n");
      out.write("                \r\n");
      out.write("              </table>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      \r\n");
      out.write("      <tr>\r\n");
      out.write("        <td colspan=\"2\">\r\n");
      out.write("          <div id=\"resultadoOtorgantesPrevios\"></div>\r\n");
      out.write("        </td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td colspan=\"2\">\r\n");
      out.write("          <div id=\"resultadoDIV\"></div>\r\n");
      out.write("        </td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>");
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

  private boolean _jspx_meth_s_form_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:form
    org.apache.struts2.views.jsp.ui.FormTag _jspx_th_s_form_0 = (org.apache.struts2.views.jsp.ui.FormTag) _jspx_tagPool_s_form_theme_namespace_cssClass_action.get(org.apache.struts2.views.jsp.ui.FormTag.class);
    _jspx_th_s_form_0.setPageContext(_jspx_page_context);
    _jspx_th_s_form_0.setParent(null);
    _jspx_th_s_form_0.setNamespace("usuario");
    _jspx_th_s_form_0.setAction("resBusqueda.do");
    _jspx_th_s_form_0.setTheme("simple");
    _jspx_th_s_form_0.setCssClass("col s12");
    int _jspx_eval_s_form_0 = _jspx_th_s_form_0.doStartTag();
    if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_form_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_form_0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"curpOtorgante\">N&uacute;mero de Identificaci&oacute;n del Deudor Garante (DPI o Pasaporte)</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_1((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"rfcOtorgante\">NIT del Deudor Garante</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_2((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"serial\">N&uacute;mero de serie</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t<center>\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class='row'>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<a class=\"btn btn-large waves-effect indigo\" onclick=\"busquedaJSP(");
        if (_jspx_meth_s_property_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write(",1);\">Consultar</a>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t</center>\r\n");
        out.write("\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_form_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_form_theme_namespace_cssClass_action.reuse(_jspx_th_s_form_0);
      return true;
    }
    _jspx_tagPool_s_form_theme_namespace_cssClass_action.reuse(_jspx_th_s_form_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_0(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_0 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textfield_0.setName("curpOtorgante");
    _jspx_th_s_textfield_0.setId("curpOtorgante");
    _jspx_th_s_textfield_0.setSize("15");
    _jspx_th_s_textfield_0.setMaxlength("15");
    _jspx_th_s_textfield_0.setOnkeypress("return aceptaalfa(event);");
    _jspx_th_s_textfield_0.setCssClass("validate tooltipped");
    _jspx_th_s_textfield_0.setDynamicAttribute(null, "data-position", new String("right"));
    _jspx_th_s_textfield_0.setDynamicAttribute(null, "data-delay", new String("50"));
    _jspx_th_s_textfield_0.setDynamicAttribute(null, "data-tooltip", new String("Ingrese su nï¿½mero sin espacios en blanco"));
    int _jspx_eval_s_textfield_0 = _jspx_th_s_textfield_0.doStartTag();
    if (_jspx_th_s_textfield_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.reuse(_jspx_th_s_textfield_0);
      return true;
    }
    _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.reuse(_jspx_th_s_textfield_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_1(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_1 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textfield_1.setName("rfcOtorgante");
    _jspx_th_s_textfield_1.setId("rfcOtorgante");
    _jspx_th_s_textfield_1.setSize("15");
    _jspx_th_s_textfield_1.setMaxlength("15");
    _jspx_th_s_textfield_1.setOnkeypress("return aceptaalfa(event);");
    _jspx_th_s_textfield_1.setCssClass("validate tooltipped");
    _jspx_th_s_textfield_1.setDynamicAttribute(null, "data-position", new String("right"));
    _jspx_th_s_textfield_1.setDynamicAttribute(null, "data-delay", new String("50"));
    _jspx_th_s_textfield_1.setDynamicAttribute(null, "data-tooltip", new String("Ingrese su nï¿½mero sin espacios en blanco ni guiï¿½n"));
    int _jspx_eval_s_textfield_1 = _jspx_th_s_textfield_1.doStartTag();
    if (_jspx_th_s_textfield_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
      return true;
    }
    _jspx_tagPool_s_textfield_size_onkeypress_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
    return false;
  }

  private boolean _jspx_meth_s_textfield_2(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_2 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textfield_2.setName("serial");
    _jspx_th_s_textfield_2.setId("serial");
    _jspx_th_s_textfield_2.setSize("25");
    _jspx_th_s_textfield_2.setMaxlength("25");
    int _jspx_eval_s_textfield_2 = _jspx_th_s_textfield_2.doStartTag();
    if (_jspx_th_s_textfield_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.reuse(_jspx_th_s_textfield_2);
      return true;
    }
    _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.reuse(_jspx_th_s_textfield_2);
    return false;
  }

  private boolean _jspx_meth_s_property_0(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_0 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_0.setPageContext(_jspx_page_context);
    _jspx_th_s_property_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_property_0.setValue("idPersona");
    int _jspx_eval_s_property_0 = _jspx_th_s_property_0.doStartTag();
    if (_jspx_th_s_property_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
    return false;
  }

  private boolean _jspx_meth_s_form_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:form
    org.apache.struts2.views.jsp.ui.FormTag _jspx_th_s_form_1 = (org.apache.struts2.views.jsp.ui.FormTag) _jspx_tagPool_s_form_theme_namespace_cssClass_action.get(org.apache.struts2.views.jsp.ui.FormTag.class);
    _jspx_th_s_form_1.setPageContext(_jspx_page_context);
    _jspx_th_s_form_1.setParent(null);
    _jspx_th_s_form_1.setNamespace("usuario");
    _jspx_th_s_form_1.setAction("resBusqueda.do");
    _jspx_th_s_form_1.setTheme("simple");
    _jspx_th_s_form_1.setCssClass("col s12");
    int _jspx_eval_s_form_1 = _jspx_th_s_form_1.doStartTag();
    if (_jspx_eval_s_form_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_form_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_form_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_form_1.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_3((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_1, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"nombreOtorgante\">Nombre o Raz&oacute;n Social del Deudor Garante</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_4((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_1, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"idGarantia\">N&uacute;mero de inscripci&oacute;n de la Garant&iacute;a</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t<center>\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class='row'>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<a class=\"btn btn-large waves-effect indigo\" onclick=\"busquedaJSP(");
        if (_jspx_meth_s_property_1((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_1, _jspx_page_context))
          return true;
        out.write(",2);\">Consultar</a>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t</center>\r\n");
        out.write("\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_form_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_form_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_form_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_form_theme_namespace_cssClass_action.reuse(_jspx_th_s_form_1);
      return true;
    }
    _jspx_tagPool_s_form_theme_namespace_cssClass_action.reuse(_jspx_th_s_form_1);
    return false;
  }

  private boolean _jspx_meth_s_textfield_3(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_3 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_3.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_1);
    _jspx_th_s_textfield_3.setName("nombreOtorgante");
    _jspx_th_s_textfield_3.setId("nombreOtorgante");
    _jspx_th_s_textfield_3.setSize("32");
    _jspx_th_s_textfield_3.setMaxlength("100");
    int _jspx_eval_s_textfield_3 = _jspx_th_s_textfield_3.doStartTag();
    if (_jspx_th_s_textfield_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.reuse(_jspx_th_s_textfield_3);
      return true;
    }
    _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.reuse(_jspx_th_s_textfield_3);
    return false;
  }

  private boolean _jspx_meth_s_textfield_4(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_4 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_4.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_1);
    _jspx_th_s_textfield_4.setName("idGarantia");
    _jspx_th_s_textfield_4.setId("idGarantia");
    _jspx_th_s_textfield_4.setSize("17");
    _jspx_th_s_textfield_4.setMaxlength("20");
    int _jspx_eval_s_textfield_4 = _jspx_th_s_textfield_4.doStartTag();
    if (_jspx_th_s_textfield_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.reuse(_jspx_th_s_textfield_4);
      return true;
    }
    _jspx_tagPool_s_textfield_size_name_maxlength_id_nobody.reuse(_jspx_th_s_textfield_4);
    return false;
  }

  private boolean _jspx_meth_s_property_1(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_1 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_1.setPageContext(_jspx_page_context);
    _jspx_th_s_property_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_1);
    _jspx_th_s_property_1.setValue("idPersona");
    int _jspx_eval_s_property_1 = _jspx_th_s_property_1.doStartTag();
    if (_jspx_th_s_property_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
    return false;
  }

  private boolean _jspx_meth_s_form_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:form
    org.apache.struts2.views.jsp.ui.FormTag _jspx_th_s_form_2 = (org.apache.struts2.views.jsp.ui.FormTag) _jspx_tagPool_s_form_theme_namespace_cssClass_action.get(org.apache.struts2.views.jsp.ui.FormTag.class);
    _jspx_th_s_form_2.setPageContext(_jspx_page_context);
    _jspx_th_s_form_2.setParent(null);
    _jspx_th_s_form_2.setNamespace("usuario");
    _jspx_th_s_form_2.setAction("busquedaFac.do");
    _jspx_th_s_form_2.setTheme("simple");
    _jspx_th_s_form_2.setCssClass("col s12");
    int _jspx_eval_s_form_2 = _jspx_th_s_form_2.doStartTag();
    if (_jspx_eval_s_form_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_form_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_form_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_form_2.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_5((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_2, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"invoice\">No. Factura:</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_6((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_2, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"set\">Serie:</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"row center-align\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<a class=\"btn btn-large waves-effect indigo\" onclick=\"checkText(");
        if (_jspx_meth_s_property_2((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_2, _jspx_page_context))
          return true;
        out.write(",3);\">Consultar</a>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_form_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_form_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_form_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_form_theme_namespace_cssClass_action.reuse(_jspx_th_s_form_2);
      return true;
    }
    _jspx_tagPool_s_form_theme_namespace_cssClass_action.reuse(_jspx_th_s_form_2);
    return false;
  }

  private boolean _jspx_meth_s_textfield_5(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_5 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_id_class_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_5.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_2);
    _jspx_th_s_textfield_5.setId("invoice");
    _jspx_th_s_textfield_5.setName("invoice");
    _jspx_th_s_textfield_5.setCssClass("validate");
    int _jspx_eval_s_textfield_5 = _jspx_th_s_textfield_5.doStartTag();
    if (_jspx_th_s_textfield_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_id_class_nobody.reuse(_jspx_th_s_textfield_5);
      return true;
    }
    _jspx_tagPool_s_textfield_name_id_class_nobody.reuse(_jspx_th_s_textfield_5);
    return false;
  }

  private boolean _jspx_meth_s_textfield_6(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_6 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_type_name_id_class_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_6.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_2);
    _jspx_th_s_textfield_6.setType("text");
    _jspx_th_s_textfield_6.setId("set");
    _jspx_th_s_textfield_6.setName("set");
    _jspx_th_s_textfield_6.setCssClass("validate");
    int _jspx_eval_s_textfield_6 = _jspx_th_s_textfield_6.doStartTag();
    if (_jspx_th_s_textfield_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_type_name_id_class_nobody.reuse(_jspx_th_s_textfield_6);
      return true;
    }
    _jspx_tagPool_s_textfield_type_name_id_class_nobody.reuse(_jspx_th_s_textfield_6);
    return false;
  }

  private boolean _jspx_meth_s_property_2(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_2 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_2.setPageContext(_jspx_page_context);
    _jspx_th_s_property_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_2);
    _jspx_th_s_property_2.setValue("idPersona");
    int _jspx_eval_s_property_2 = _jspx_th_s_property_2.doStartTag();
    if (_jspx_th_s_property_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
    return false;
  }

  private boolean _jspx_meth_s_if_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_0 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_if_0.setPageContext(_jspx_page_context);
    _jspx_th_s_if_0.setParent(null);
    _jspx_th_s_if_0.setTest("hayDatos");
    int _jspx_eval_s_if_0 = _jspx_th_s_if_0.doStartTag();
    if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("                        <tr id=\"datosCadena\">\r\n");
        out.write("                          <td>\r\n");
        out.write("                            <table >\r\n");
        out.write("                              <tr>\r\n");
        out.write("                                <td align=\"left\"><span class=\"textoGeneralRojo\"> La Cadena &Uacute;nica de Datos se encuentra relacionado al Tr&aacute;mite:</span> <span class=\"textoGeneral\">");
        if (_jspx_meth_s_property_3((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_if_0, _jspx_page_context))
          return true;
        out.write(" </span></td>  \r\n");
        out.write("                              </tr>\r\n");
        out.write("                              <tr>\r\n");
        out.write("                                <td align=\"left\"> <span class=\"textoGeneralRojo\"> El n&uacute;mero de garant&iacute;a es:</span> <span class=\"textoGeneral\">");
        if (_jspx_meth_s_property_4((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_if_0, _jspx_page_context))
          return true;
        out.write(" </span></td>\r\n");
        out.write("                              </tr>\r\n");
        out.write("                              <tr>\r\n");
        out.write("                                <td align=\"left\"> <span class=\"textoGeneralRojo\"> Fue realizada el d&iacute;a: </span> <span class=\"textoGeneral\"> ");
        if (_jspx_meth_s_property_5((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_if_0, _jspx_page_context))
          return true;
        out.write(" </span></td> \r\n");
        out.write("                              </tr>\r\n");
        out.write("                              <tr>\t\r\n");
        out.write("                                <td align=\"left\"> <span class=\"textoGeneralRojo\"> Por el Otorgante de la Garant&iacute;a Mobiliaria: </span> <span class=\"textoGeneral\"> ");
        if (_jspx_meth_s_property_6((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_if_0, _jspx_page_context))
          return true;
        out.write(" </span></td>\r\n");
        out.write("                              </tr>\r\n");
        out.write("                              <tr>\t\r\n");
        out.write("                                <td align=\"left\"><span class=\"textoGeneralRojo\"> Con el folio electr&oacute;nico: </span> <span class=\"textoGeneral\"> ");
        if (_jspx_meth_s_property_7((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_if_0, _jspx_page_context))
          return true;
        out.write(" </span></td>\r\n");
        out.write("                              </tr>\r\n");
        out.write("                              <tr>\r\n");
        out.write("                                <td align=\"center\"> \r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h4 style=\"cursor: pointer\">\r\n");
        out.write("                                    <a onclick=\"showBoleta();\"> <u>Clic aqu&iacute; para ver la boleta</u></a>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</h4>\r\n");
        out.write("                                </td>\r\n");
        out.write("                              </tr>\r\n");
        out.write("                            </table>\r\n");
        out.write("                          </td>\r\n");
        out.write("                        </tr>\r\n");
        out.write("                      ");
        int evalDoAfterBody = _jspx_th_s_if_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_if_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_0);
      return true;
    }
    _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_0);
    return false;
  }

  private boolean _jspx_meth_s_property_3(javax.servlet.jsp.tagext.JspTag _jspx_th_s_if_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_3 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_3.setPageContext(_jspx_page_context);
    _jspx_th_s_property_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_if_0);
    _jspx_th_s_property_3.setValue("tipoOperacion");
    int _jspx_eval_s_property_3 = _jspx_th_s_property_3.doStartTag();
    if (_jspx_th_s_property_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
    return false;
  }

  private boolean _jspx_meth_s_property_4(javax.servlet.jsp.tagext.JspTag _jspx_th_s_if_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_4 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_4.setPageContext(_jspx_page_context);
    _jspx_th_s_property_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_if_0);
    _jspx_th_s_property_4.setValue("numGarantia");
    int _jspx_eval_s_property_4 = _jspx_th_s_property_4.doStartTag();
    if (_jspx_th_s_property_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
    return false;
  }

  private boolean _jspx_meth_s_property_5(javax.servlet.jsp.tagext.JspTag _jspx_th_s_if_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_5 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_5.setPageContext(_jspx_page_context);
    _jspx_th_s_property_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_if_0);
    _jspx_th_s_property_5.setValue("fechaInsc");
    int _jspx_eval_s_property_5 = _jspx_th_s_property_5.doStartTag();
    if (_jspx_th_s_property_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
    return false;
  }

  private boolean _jspx_meth_s_property_6(javax.servlet.jsp.tagext.JspTag _jspx_th_s_if_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_6 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_6.setPageContext(_jspx_page_context);
    _jspx_th_s_property_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_if_0);
    _jspx_th_s_property_6.setValue("nomOtorgante");
    int _jspx_eval_s_property_6 = _jspx_th_s_property_6.doStartTag();
    if (_jspx_th_s_property_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
    return false;
  }

  private boolean _jspx_meth_s_property_7(javax.servlet.jsp.tagext.JspTag _jspx_th_s_if_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_7 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_7.setPageContext(_jspx_page_context);
    _jspx_th_s_property_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_if_0);
    _jspx_th_s_property_7.setValue("folioOtorgante");
    int _jspx_eval_s_property_7 = _jspx_th_s_property_7.doStartTag();
    if (_jspx_th_s_property_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_7);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_7);
    return false;
  }

  private boolean _jspx_meth_s_else_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:else
    org.apache.struts2.views.jsp.ElseTag _jspx_th_s_else_0 = (org.apache.struts2.views.jsp.ElseTag) _jspx_tagPool_s_else.get(org.apache.struts2.views.jsp.ElseTag.class);
    _jspx_th_s_else_0.setPageContext(_jspx_page_context);
    _jspx_th_s_else_0.setParent(null);
    int _jspx_eval_s_else_0 = _jspx_th_s_else_0.doStartTag();
    if (_jspx_eval_s_else_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_else_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_else_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_else_0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_if_1((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_else_0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("                      ");
        int evalDoAfterBody = _jspx_th_s_else_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_else_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_else_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_else.reuse(_jspx_th_s_else_0);
      return true;
    }
    _jspx_tagPool_s_else.reuse(_jspx_th_s_else_0);
    return false;
  }

  private boolean _jspx_meth_s_if_1(javax.servlet.jsp.tagext.JspTag _jspx_th_s_else_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_1 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_if_1.setPageContext(_jspx_page_context);
    _jspx_th_s_if_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_else_0);
    _jspx_th_s_if_1.setTest("pasoInicio");
    int _jspx_eval_s_if_1 = _jspx_th_s_if_1.doStartTag();
    if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_1.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<tr id=\"noDatosCadena\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"textoGeneralRojo\" align=\"center\"> No existe un Tr&aacute;mite relacionado a esta Cadena &Uacute;nica de Datos</td>  \r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_if_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_if_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_1);
      return true;
    }
    _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_1);
    return false;
  }
}
