package org.apache.jsp.WEB_002dINF.jsp.content.boletas;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class MisBoletas_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_form_theme_namespace_name_method_id_enctype_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_actionerror_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_hidden_value_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_set_var_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_form_theme_namespace_name_method_id_enctype_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_actionerror_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_hidden_value_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_form_theme_namespace_name_method_id_enctype_action.release();
    _jspx_tagPool_s_actionerror_nobody.release();
    _jspx_tagPool_s_hidden_value_name_id_nobody.release();
    _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.release();
    _jspx_tagPool_s_set_var_value_nobody.release();
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.release();
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
      response.setContentType("text/html; charset=UTF-8");
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

      out.write('\n');
      out.write('\n');
      if (_jspx_meth_s_set_0(_jspx_page_context))
        return;
      out.write('\n');
      if (_jspx_meth_s_set_1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("<div class=\"section\"></div>\n");
      out.write("<main>\n");
      out.write("<div class=\"container-fluid\">\n");
      out.write("\t<div class=\"row\">\n");
      out.write("\t\t<div class=\"col s1\"></div>\n");
      out.write("\t\t<div class=\"col s10 note\">En Esta secci&oacute;n podr&aacute;\n");
      out.write("\t\t\tregistrar las boletas de pago dentro del sistema.</div>\n");
      out.write("\t\t<div class=\"col s1\"></div>\n");
      out.write("\t</div>\n");
      out.write("\t<div class=\"row\">\n");
      out.write("\t\t<div class=\"col s1\"></div>\n");
      out.write("\t\t<div class=\"col s10\">\n");
      out.write("\t\t\t<div class=\"card\">\n");
      out.write("\t\t\t\t<div class=\"card-content\">\n");
      out.write("\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t<div class=\"col s12 action-error\">\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_actionerror_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t<span class=\"card-title\">Saldo: ");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("</span>\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t<div class=\"col s8\">\n");
      out.write("\t\t\t\t\t\t\t<span class=\"card-title\">Mis Boletas</span>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<div class=\"col s4 right-align\">\n");
      out.write("\t\t\t\t\t\t\t<a class=\"btn btn-large waves-effect indigo modal-trigger\"\n");
      out.write("\t\t\t\t\t\t\t\thref=\"#frmBoleta\" id=\"btnAgregar\"><i\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"material-icons left\">add</i>Agregar</a>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"section row\">\n");
      out.write("\t\t\t\t\t \t<div class=\"col s12\">\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t<ul class=\"tabs\">\n");
      out.write("\t\t\t\t\t\t\t    <li class=\"tab col s4\"><a class=\"blue-text text-darken-2\" id =\"tabs1\" href =\"#tabs-1\" onclick = \"iniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write("','1', '')\">Boletas Aprobadas</a></li>\n");
      out.write("\t\t\t\t\t\t\t    <li class=\"tab col s4\"><a class=\"blue-text text-darken-2\" id = \"tabs2\" href =\"#tabs-2\" onclick = \"iniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write("','2', '');\">Boletas pendientes por aprobar</a></li>\n");
      out.write("\t\t\t\t\t\t\t    <li class=\"tab col s4\"><a class=\"blue-text text-darken-2\" id = \"tabs3\" href =\"#tabs-3\" onclick = \"iniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_3(_jspx_page_context))
        return;
      out.write("','3', getFiltro());\">Tr&aacute;mites Realizados</a></li>\t\t\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"indicator blue\" style=\"z-index:1\"></div>\n");
      out.write("\t\t\t\t\t\t\t</ul>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"section\" id=\"tabs-1\">\n");
      out.write("\t\t\t\t\t\t<div class=\"row note\">\t\t\t\t\t    \t\n");
      out.write("\t\t\t\t\t        \t<span>Boletas Aprobadas</span>\t\t\t\t\t   \t\t\n");
      out.write("\t\t\t\t\t \t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    \n");
      out.write("\t\t\t\t\t\t<form>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s4\">\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"aprofiltro\" name=\"aprofiltro\" placeholder=\"Buscar\" onkeypress=\"return runScript(event)\">\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col s4\">\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn waves-effect indigo\" onclick = \"aprobadasByFiltro()\" >\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class=\"fooicon fooicon-search\"></span>\n");
      out.write("\t\t\t\t\t\t\t\t</button>\n");
      out.write("\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t</form>\n");
      out.write("\t\t\t\t\t\t<div id=\"BoAprobadas\">\n");
      out.write("\t\t\t\t\t   \t</div>\t\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div id=\"tabs-2\" class=\"section\">\n");
      out.write("\t\t\t\t\t\t<div class=\"row note\">\t\t\t\t\t    \t\n");
      out.write("\t\t\t\t\t        \t<span>Boletas pendientes por aprobar</span>\t\t\t\t\t   \t\t\n");
      out.write("\t\t\t\t\t \t</div>\n");
      out.write("\t\t\t\t\t\t<form>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s4\">\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"penfiltro\" name=\"penfiltro\" placeholder=\"Buscar\" onkeypress=\"return runScript(event)\">\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col s4\">\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn waves-effect indigo\" onclick = \"pendientesByFiltro()\" >\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class=\"fooicon fooicon-search\"></span>\n");
      out.write("\t\t\t\t\t\t\t\t</button>\n");
      out.write("\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t</form>\n");
      out.write("\t\t\t\t\t\t<div id=\"BoPendientes\">\n");
      out.write("\t\t\t\t\t   \t</div>\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div id=\"tabs-3\" class=\"section\">\n");
      out.write("\t\t\t\t\t\t<div class=\"row note\">\n");
      out.write("\t\t\t\t\t\t\t<span>Tr&aacute;mites Realizados</span>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"fechaInicial\" class=\"datepicker\" id=\"fechaInicial\" />\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t<label for=\"fechaFinal\">Fecha Inicial</label>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"fechaFinal\" class=\"datepicker\" id=\"fechaFinal\" />\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t<label for=\"fechaFinal\">Fecha Final</label>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s4\">\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"realfiltro\" name=\"realfiltro\" placeholder=\"Buscar por operación o usuario\" onkeypress=\"return runScript(event)\">\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col s8 right-align\">\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn waves-effect indigo\" onclick = \"realizadosByFiltro()\" >\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class=\"fooicon fooicon-search\"></span>\n");
      out.write("\t\t\t\t\t\t\t\t</button>\n");
      out.write("\t\t\t\t\t\t\t\t<button class=\"waves-effect waves-light btn\" onclick=\"exportData();\">Exportar</button>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<div id=\"TraRealizados\">\n");
      out.write("\t\t\t\t\t   \t</div>\t\t\t\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div id=\"tabs-4\" class=\"section\"></div>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"col s1\"></div>\n");
      out.write("\t</div>\t\n");
      out.write("</div>\n");
      out.write("</main>\n");
      out.write("<div class=\"section\"></div>\n");
      out.write("<div id=\"frmBoleta\" class=\"modal\">\n");
      out.write("\t<div class=\"modal-content\">\n");
      out.write("\t\t<div class=\"card\">\n");
      out.write("\t\t\t<div class=\"card-content\">\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_form_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("</div>\n");
      out.write("<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/interface/OperacionesDwrAction.js\"></script>\n");
      out.write("<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/interface/BoletaDwrAction.js\"></script>\n");
      out.write("<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/dwr/operacionesJS.js\"></script>\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/material-dialog.min.js\"></script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("iniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_4(_jspx_page_context))
        return;
      out.write("','1', '');\n");
      out.write("\n");
      out.write("function runScript(event) {\n");
      out.write("    if (event.which == 13 || event.keyCode == 13) {\n");
      out.write("        //code to execute here\n");
      out.write("        return false;\n");
      out.write("    }\n");
      out.write("    return true;\n");
      out.write("};\n");
      out.write("\n");
      out.write("function realizadosByFiltro(){\n");
      out.write("\t/*var filtro = getObject('realfiltro').value;\n");
      out.write("\tif(filtro!=null && filtro!=''){\t\t\n");
      out.write("\t\tiniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_5(_jspx_page_context))
        return;
      out.write("','3',filtro);\n");
      out.write("\t} else {\t\t\t\n");
      out.write("\t\tiniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_6(_jspx_page_context))
        return;
      out.write("','3', '');\n");
      out.write("\t}*/\n");
      out.write("\tiniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_7(_jspx_page_context))
        return;
      out.write("','3', getFiltro());\n");
      out.write("}\n");
      out.write("\n");
      out.write("function aprobadasByFiltro(){\n");
      out.write("\tvar filtro = getObject('aprofiltro').value;\n");
      out.write("\t\t\t\t\n");
      out.write("\tif(filtro!=null && filtro!=''){\t\t\n");
      out.write("\t\tiniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_8(_jspx_page_context))
        return;
      out.write("','1',filtro);\n");
      out.write("\t} else {\t\t\t\n");
      out.write("\t\tiniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_9(_jspx_page_context))
        return;
      out.write("','1', '');\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("\n");
      out.write("function pendientesByFiltro(){\n");
      out.write("\tvar filtro = getObject('penfiltro').value;\n");
      out.write("\t\t\t\t\n");
      out.write("\tif(filtro!=null && filtro!=''){\t\t\n");
      out.write("\t\tiniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_10(_jspx_page_context))
        return;
      out.write("','2',filtro);\n");
      out.write("\t} else {\t\t\t\n");
      out.write("\t\tiniciaPaginacionBoletas('");
      if (_jspx_meth_s_property_11(_jspx_page_context))
        return;
      out.write("','2', '');\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("\n");
      out.write("function validarBoleta() {\n");
      out.write("\tvar numero = $('#mdNumeroBoleta').val();\n");
      out.write("\tvar monto = $('#mdCantidad').val();\n");
      out.write("\tvar banco = $('#mdBanco').val();\n");
      out.write("\tvar formaPago = $('#mdPago').val();\n");
      out.write("\t\n");
      out.write("\tif (!numero || !monto || !banco || !formaPago) {\n");
      out.write("\t\treturn false;\n");
      out.write("\t}\n");
      out.write("\treturn true;\n");
      out.write("}\n");
      out.write("function ver_pago() {\n");
      out.write("\tif (validarBoleta()) {\n");
      out.write("\t  var e = document.getElementById(\"mdPago\");\n");
      out.write("\t  var valPago = e.options[e.selectedIndex].value;\n");
      out.write("\t  \n");
      out.write("\t  var imgVal = $('#uploadfile').val(); \n");
      out.write("      if(imgVal=='') \n");
      out.write("      { \n");
      out.write("    \t  MaterialDialog.alert(\n");
      out.write("  \t\t\t\t\"Debe cargar la boleta de pago.\",\n");
      out.write("  \t\t\t\t{\n");
      out.write("  \t\t\t\t\ttitle:'<table><tr><td width=\"10%\"><i class=\"medium icon-yellow material-icons\">warning</i></td><td style=\"vertical-align: middle; text-align:left;\">Alerta</td></tr></table>',\n");
      out.write("  \t\t\t\t\tbuttons:{\n");
      out.write("  \t\t\t\t\t\tclose:{\n");
      out.write("  \t\t\t\t\t\t\tclassName:\"red\",\n");
      out.write("  \t\t\t\t\t\t\ttext:\"cerrar\"\t\t\t\t\t\t\n");
      out.write("  \t\t\t\t\t\t}\n");
      out.write("  \t\t\t\t\t}\n");
      out.write("  \t\t\t\t}\n");
      out.write("  \t\t\t);\n");
      out.write("  \t\t\treturn false;          \n");
      out.write("      } \n");
      out.write("\t  \n");
      out.write("\t  if(valPago == '3'){\t\t  \n");
      out.write("\t\t  MaterialDialog.dialog(\n");
      out.write("\t\t\t\"Este tipo de deposito será aprobado hasta realizada la compensaci&oacute;n.\",\n");
      out.write("\t\t\t{\t\t\t\t\n");
      out.write("\t\t\t\ttitle:'<table><tr><td width=\"10%\"><i class=\"medium icon-green material-icons\">check_circle</i></td><td style=\"vertical-align: middle; text-align:left;\">Confirmar</td></tr></table>', // Modal title\n");
      out.write("\t\t\t\tbuttons:{\n");
      out.write("\t\t\t\t\t// Use by default close and confirm buttons\n");
      out.write("\t\t\t\t\tclose:{\n");
      out.write("\t\t\t\t\t\tclassName:\"red\",\n");
      out.write("\t\t\t\t\t\ttext:\"cancelar\"\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t},\n");
      out.write("\t\t\t\t\tconfirm:{\n");
      out.write("\t\t\t\t\t\tclassName:\"indigo\",\n");
      out.write("\t\t\t\t\t\ttext:\"aceptar\",\n");
      out.write("\t\t\t\t\t\tmodalClose:false,\n");
      out.write("\t\t\t\t\t\tcallback:function(){\n");
      out.write("\t\t\t\t\t\t\tdocument.getElementById(\"formBoleta\").submit();\n");
      out.write("\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t}\n");
      out.write("\t\t);\n");
      out.write("\t\t\t\t\t\n");
      out.write("\t  }  else {\n");
      out.write("          document.getElementById(\"formBoleta\").submit();\n");
      out.write("      }\n");
      out.write("\t} else {\n");
      out.write("\t\tMaterialDialog.alert(\n");
      out.write("\t\t\t\t\"Debe ingresar la información de la boleta de pago.\",\n");
      out.write("\t\t\t\t{\n");
      out.write("\t\t\t\t\ttitle:'<table><tr><td width=\"10%\"><i class=\"medium icon-yellow material-icons\">warning</i></td><td style=\"vertical-align: middle; text-align:left;\">Alerta</td></tr></table>',\n");
      out.write("\t\t\t\t\tbuttons:{\n");
      out.write("\t\t\t\t\t\tclose:{\n");
      out.write("\t\t\t\t\t\t\tclassName:\"red\",\n");
      out.write("\t\t\t\t\t\t\ttext:\"cerrar\"\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t);\n");
      out.write("\t\t\treturn false;\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("  \n");
      out.write(" function IsNumber(evt) {\n");
      out.write("\tvar key = (document.all) ? evt.keyCode : evt.which;\t\n");
      out.write("\treturn (key <= 13 || (key >= 48 && key <= 57) || key == 46);\n");
      out.write("}\n");
      out.write("function exportData() {\n");
      out.write("\tvar filtro = JSON.parse(getFiltro());\n");
      out.write("\tvar fechaInicial = moment(filtro.fechaInicial);\n");
      out.write("\tvar fechaFinal = moment(filtro.fechaFinal);\n");
      out.write("\tvar dias = (fechaFinal.diff(fechaInicial, 'days'));\n");
      out.write("\tif (dias > 90) {\n");
      out.write("\t\tMaterialDialog.alert(\n");
      out.write("\t\t\t\"No puede seleccionar más de tres meses para exportar los datos.\",\n");
      out.write("\t\t\t{\n");
      out.write("\t\t\t\ttitle:'<table><tr><td width=\"10%\"><i class=\"medium icon-yellow material-icons\">warning</i></td><td style=\"vertical-align: middle; text-align:left;\">Alerta</td></tr></table>',\n");
      out.write("\t\t\t\tbuttons:{\n");
      out.write("\t\t\t\t\tclose:{\n");
      out.write("\t\t\t\t\t\tclassName:\"red\",\n");
      out.write("\t\t\t\t\t\ttext:\"cerrar\"\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t}\n");
      out.write("\t\t);\n");
      out.write("\t\treturn;\n");
      out.write("\t}\n");
      out.write("\tvar URL=\"");
      out.print(request.getContextPath());
      out.write("/home/exportExcel.do?filtro=\" + filtro.nombre + \"&fechaInicial=\" + filtro.fechaInicial + \"&fechaFinal=\" + filtro.fechaFinal;\n");
      out.write("\tconsole.log(URL);\n");
      out.write("\twindow.open(URL, \"_blank\");\n");
      out.write("}\n");
      out.write("function docReady(fn) {\n");
      out.write("    // see if DOM is already available\n");
      out.write("    if (document.readyState === \"complete\" || document.readyState === \"interactive\") {\n");
      out.write("        // call on next available tick\n");
      out.write("        setTimeout(fn, 1);\n");
      out.write("    } else {\n");
      out.write("        document.addEventListener(\"DOMContentLoaded\", fn);\n");
      out.write("    }\n");
      out.write("}\n");
      out.write("docReady(function() {\n");
      out.write("\tvar today = new Date();\n");
      out.write("\tvar $fechaInicial = $('#fechaInicial').pickadate({\n");
      out.write("        selectMonths: true,\n");
      out.write("        selectYears: 15, \n");
      out.write("        today: 'Hoy',\n");
      out.write("        clear: 'Inicializar',\n");
      out.write("        monthsFull: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],\n");
      out.write("        weekdaysShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],\n");
      out.write("        close: 'Ok',\n");
      out.write("        closeOnSelect: false,\n");
      out.write("        format: 'dd/mm/yyyy'\n");
      out.write("    });\n");
      out.write("\tvar fechaInicialPicker = $fechaInicial.pickadate('picker');\n");
      out.write("\tfechaInicialPicker.set('select', addDays(today, -30));\n");
      out.write("\tvar $fechaFinal = $('#fechaFinal').pickadate({\n");
      out.write("        selectMonths: true,\n");
      out.write("        selectYears: 15, \n");
      out.write("        today: 'Hoy',\n");
      out.write("        clear: 'Inicializar',\n");
      out.write("        monthsFull: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],\n");
      out.write("        weekdaysShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],\n");
      out.write("        close: 'Ok',\n");
      out.write("        closeOnSelect: false,\n");
      out.write("        format: 'dd/mm/yyyy'\n");
      out.write("    });\n");
      out.write("\tvar fechaFinalPicker = $fechaFinal.pickadate('picker');\n");
      out.write("\tfechaFinalPicker.set('select', today);\n");
      out.write("});\n");
      out.write("function addDays(date, days) {\n");
      out.write("\tconst copy = new Date(Number(date))\n");
      out.write("\tcopy.setDate(date.getDate() + days)\n");
      out.write("\treturn copy\n");
      out.write("}\n");
      out.write("function getFiltro() {\n");
      out.write("\tvar fechaInicial = $('#fechaInicial').val();\n");
      out.write("\tfechaInicial = fechaInicial.split('/');\n");
      out.write("\tvar fechaFinal = $('#fechaFinal').val();\n");
      out.write("\tfechaFinal = fechaFinal.split('/');\n");
      out.write("\tvar filtro = {\n");
      out.write("\t\tnombre:\t$('#realfiltro').val(),\n");
      out.write("\t\tfechaInicial: fechaInicial[2] + '-' + fechaInicial[1] + '-' + fechaInicial[0],\n");
      out.write("\t\tfechaFinal: fechaFinal[2] + '-' + fechaFinal[1] + '-' + fechaFinal[0]\n");
      out.write("\t};\n");
      out.write("\tconsole.log(filtro);\n");
      out.write("\treturn JSON.stringify(filtro);\n");
      out.write("}\n");
      out.write("</script>\n");
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

  private boolean _jspx_meth_s_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:set
    org.apache.struts2.views.jsp.SetTag _jspx_th_s_set_0 = (org.apache.struts2.views.jsp.SetTag) _jspx_tagPool_s_set_var_value_nobody.get(org.apache.struts2.views.jsp.SetTag.class);
    _jspx_th_s_set_0.setPageContext(_jspx_page_context);
    _jspx_th_s_set_0.setParent(null);
    _jspx_th_s_set_0.setVar("idPersonaUsuario");
    _jspx_th_s_set_0.setValue("idPersona");
    int _jspx_eval_s_set_0 = _jspx_th_s_set_0.doStartTag();
    if (_jspx_th_s_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_0);
      return true;
    }
    _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_0);
    return false;
  }

  private boolean _jspx_meth_s_set_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:set
    org.apache.struts2.views.jsp.SetTag _jspx_th_s_set_1 = (org.apache.struts2.views.jsp.SetTag) _jspx_tagPool_s_set_var_value_nobody.get(org.apache.struts2.views.jsp.SetTag.class);
    _jspx_th_s_set_1.setPageContext(_jspx_page_context);
    _jspx_th_s_set_1.setParent(null);
    _jspx_th_s_set_1.setVar("maestra");
    _jspx_th_s_set_1.setValue("cuentaMaestra");
    int _jspx_eval_s_set_1 = _jspx_th_s_set_1.doStartTag();
    if (_jspx_th_s_set_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_1);
      return true;
    }
    _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_1);
    return false;
  }

  private boolean _jspx_meth_s_actionerror_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:actionerror
    org.apache.struts2.views.jsp.ui.ActionErrorTag _jspx_th_s_actionerror_0 = (org.apache.struts2.views.jsp.ui.ActionErrorTag) _jspx_tagPool_s_actionerror_nobody.get(org.apache.struts2.views.jsp.ui.ActionErrorTag.class);
    _jspx_th_s_actionerror_0.setPageContext(_jspx_page_context);
    _jspx_th_s_actionerror_0.setParent(null);
    int _jspx_eval_s_actionerror_0 = _jspx_th_s_actionerror_0.doStartTag();
    if (_jspx_th_s_actionerror_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_actionerror_nobody.reuse(_jspx_th_s_actionerror_0);
      return true;
    }
    _jspx_tagPool_s_actionerror_nobody.reuse(_jspx_th_s_actionerror_0);
    return false;
  }

  private boolean _jspx_meth_s_property_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_0 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_0.setPageContext(_jspx_page_context);
    _jspx_th_s_property_0.setParent(null);
    _jspx_th_s_property_0.setValue("getText('Q. {0,number,#,##0.00}',{mdSaldo})");
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

  private boolean _jspx_meth_s_form_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:form
    org.apache.struts2.views.jsp.ui.FormTag _jspx_th_s_form_0 = (org.apache.struts2.views.jsp.ui.FormTag) _jspx_tagPool_s_form_theme_namespace_name_method_id_enctype_action.get(org.apache.struts2.views.jsp.ui.FormTag.class);
    _jspx_th_s_form_0.setPageContext(_jspx_page_context);
    _jspx_th_s_form_0.setParent(null);
    _jspx_th_s_form_0.setAction("registrarBoleta.do");
    _jspx_th_s_form_0.setNamespace("inscripcion");
    _jspx_th_s_form_0.setMethod("post");
    _jspx_th_s_form_0.setEnctype("multipart/form-data");
    _jspx_th_s_form_0.setTheme("simple");
    _jspx_th_s_form_0.setName("formBoleta");
    _jspx_th_s_form_0.setId("formBoleta");
    int _jspx_eval_s_form_0 = _jspx_th_s_form_0.doStartTag();
    if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_form_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_form_0.doInitBody();
      }
      do {
        out.write("\n");
        out.write("\t\t\t\t\t<span class=\"card-title\">Boleta de Pago</span>\n");
        out.write("\t\t\t\t\t<div class=\"row\">\n");
        out.write("\t\t\t\t\t\t<div class=\"col m1\"></div>\n");
        out.write("\t\t\t\t\t\t<div class=\"col s12 m10\">\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"mdNumeroBoleta\">N&uacute;mero de Boleta</label>\n");
        out.write("\t\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textfield_1((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t\t<label for=\"mdCantidad\">Cantidad</label>\n");
        out.write("\t\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
        out.write("\t\t\t\t\t\t\t\t\t<select name=\"mdBanco\" id=\"mdBanco\">\n");
        out.write("\t\t\t\t\t\t\t\t      <option value=\"\" disabled selected>Seleccione una opci&oacute;n</option>\n");
        out.write("\t\t\t\t\t\t\t\t      <option value=\"1\">Banrural</option>\n");
        out.write("\t\t\t\t\t\t\t\t      <option value=\"2\">CHN</option>\t\t\t\t\t\t\t\t      \n");
        out.write("\t\t\t\t\t\t\t\t      <option value=\"BI3\">BI</option>\t\t\t\t\t\t\t\t      \n");
        out.write("\t\t\t\t\t\t\t\t    </select>\n");
        out.write("\t\t\t\t\t\t\t    \t<label>Banco</label>\n");
        out.write("\t\t\t\t\t\t\t    </div>\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
        out.write("\t\t\t\t\t\t\t\t\t<select name=\"mdPago\" id=\"mdPago\">\n");
        out.write("\t\t\t\t\t\t\t\t      <option value=\"\" disabled selected>Seleccione una opci&oacute;n</option>\n");
        out.write("\t\t\t\t\t\t\t\t      <option value=\"1\">Efectivo</option>\n");
        out.write("\t\t\t\t\t\t\t\t      <option value=\"2\">Cheque mismo Banco</option>\t\t\t\t\t\t\t\t      \n");
        out.write("\t\t\t\t\t\t\t\t      <option value=\"3\">Cheque otro Banco</option>\n");
        out.write("\t\t\t\t\t\t\t\t    </select>\n");
        out.write("\t\t\t\t\t\t\t    \t<label>Forma de Pago</label>\n");
        out.write("\t\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t<div class=\"row\">\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"file-field input-field\">\n");
        out.write("\t\t\t\t\t\t\t\t\t<div class=\"btn\">\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<span>Boleta</span>\n");
        out.write("\t\t\t\t\t\t\t\t\t\t <input type=\"file\" name=\"upload\" id=\"uploadfile\" />\t\t\t\t\t\t\t\t\t\t\n");
        out.write("\t\t\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t\t\t<div class=\"file-path-wrapper\">\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"file-path validate\" type=\"text\"\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\tplaceholder=\"Selecciona un archivo...\">\n");
        out.write("\t\t\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t<br />\n");
        out.write("\t\t\t\t\t\t\t<hr />\n");
        out.write("\t\t\t\t\t\t\t<center>\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"row\">\n");
        out.write("\t\t\t\t\t\t\t\t\t<a href=\"#!\"\n");
        out.write("\t\t\t\t\t\t\t\t\t\tclass=\"waves-effect waves-green btn-large indigo\"\n");
        out.write("\t\t\t\t\t\t\t\t\t\tonclick=\"ver_pago();\">Aceptar</a>\n");
        out.write("\t\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t</center>\n");
        out.write("\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t<div class=\"col m1\">\n");
        out.write("\t\t\t\t\t\t\t");
        if (_jspx_meth_s_hidden_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_form_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_form_theme_namespace_name_method_id_enctype_action.reuse(_jspx_th_s_form_0);
      return true;
    }
    _jspx_tagPool_s_form_theme_namespace_name_method_id_enctype_action.reuse(_jspx_th_s_form_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_0(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_0 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textfield_0.setName("mdNumeroBoleta");
    _jspx_th_s_textfield_0.setId("mdNumeroBoleta");
    _jspx_th_s_textfield_0.setCssClass("validate");
    _jspx_th_s_textfield_0.setMaxlength("150");
    int _jspx_eval_s_textfield_0 = _jspx_th_s_textfield_0.doStartTag();
    if (_jspx_th_s_textfield_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_0);
      return true;
    }
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_1(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_1 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textfield_1.setName("mdCantidad");
    _jspx_th_s_textfield_1.setId("mdCantidad");
    _jspx_th_s_textfield_1.setCssClass("validate");
    _jspx_th_s_textfield_1.setMaxlength("150");
    _jspx_th_s_textfield_1.setOnkeypress("return IsNumber(event);");
    int _jspx_eval_s_textfield_1 = _jspx_th_s_textfield_1.doStartTag();
    if (_jspx_th_s_textfield_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
      return true;
    }
    _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
    return false;
  }

  private boolean _jspx_meth_s_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:hidden
    org.apache.struts2.views.jsp.ui.HiddenTag _jspx_th_s_hidden_0 = (org.apache.struts2.views.jsp.ui.HiddenTag) _jspx_tagPool_s_hidden_value_name_id_nobody.get(org.apache.struts2.views.jsp.ui.HiddenTag.class);
    _jspx_th_s_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_s_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
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

  private boolean _jspx_meth_s_property_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_4 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_4.setPageContext(_jspx_page_context);
    _jspx_th_s_property_4.setParent(null);
    _jspx_th_s_property_4.setValue("idPersona");
    int _jspx_eval_s_property_4 = _jspx_th_s_property_4.doStartTag();
    if (_jspx_th_s_property_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
    return false;
  }

  private boolean _jspx_meth_s_property_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_5 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_5.setPageContext(_jspx_page_context);
    _jspx_th_s_property_5.setParent(null);
    _jspx_th_s_property_5.setValue("idPersona");
    int _jspx_eval_s_property_5 = _jspx_th_s_property_5.doStartTag();
    if (_jspx_th_s_property_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
    return false;
  }

  private boolean _jspx_meth_s_property_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_6 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_6.setPageContext(_jspx_page_context);
    _jspx_th_s_property_6.setParent(null);
    _jspx_th_s_property_6.setValue("idPersona");
    int _jspx_eval_s_property_6 = _jspx_th_s_property_6.doStartTag();
    if (_jspx_th_s_property_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
    return false;
  }

  private boolean _jspx_meth_s_property_7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_7 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_7.setPageContext(_jspx_page_context);
    _jspx_th_s_property_7.setParent(null);
    _jspx_th_s_property_7.setValue("idPersona");
    int _jspx_eval_s_property_7 = _jspx_th_s_property_7.doStartTag();
    if (_jspx_th_s_property_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_7);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_7);
    return false;
  }

  private boolean _jspx_meth_s_property_8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_8 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_8.setPageContext(_jspx_page_context);
    _jspx_th_s_property_8.setParent(null);
    _jspx_th_s_property_8.setValue("idPersona");
    int _jspx_eval_s_property_8 = _jspx_th_s_property_8.doStartTag();
    if (_jspx_th_s_property_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_8);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_8);
    return false;
  }

  private boolean _jspx_meth_s_property_9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_9 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_9.setPageContext(_jspx_page_context);
    _jspx_th_s_property_9.setParent(null);
    _jspx_th_s_property_9.setValue("idPersona");
    int _jspx_eval_s_property_9 = _jspx_th_s_property_9.doStartTag();
    if (_jspx_th_s_property_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_9);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_9);
    return false;
  }

  private boolean _jspx_meth_s_property_10(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_10 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_10.setPageContext(_jspx_page_context);
    _jspx_th_s_property_10.setParent(null);
    _jspx_th_s_property_10.setValue("idPersona");
    int _jspx_eval_s_property_10 = _jspx_th_s_property_10.doStartTag();
    if (_jspx_th_s_property_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_10);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_10);
    return false;
  }

  private boolean _jspx_meth_s_property_11(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_11 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_11.setPageContext(_jspx_page_context);
    _jspx_th_s_property_11.setParent(null);
    _jspx_th_s_property_11.setValue("idPersona");
    int _jspx_eval_s_property_11 = _jspx_th_s_property_11.doStartTag();
    if (_jspx_th_s_property_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_11);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_11);
    return false;
  }
}
