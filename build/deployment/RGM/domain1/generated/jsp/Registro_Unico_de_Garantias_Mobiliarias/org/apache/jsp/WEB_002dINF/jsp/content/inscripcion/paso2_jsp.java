package org.apache.jsp.WEB_002dINF.jsp.content.inscripcion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map;
import mx.gob.se.rug.seguridad.to.PrivilegioTO;
import mx.gob.se.rug.seguridad.dao.PrivilegiosDAO;
import mx.gob.se.rug.seguridad.to.PrivilegiosTO;
import mx.gob.se.rug.to.UsuarioTO;
import mx.gob.se.rug.constants.Constants;

public final class paso2_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_name_id_data$1length_cssClass_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_onchange_name_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_name_id_data$1length_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_onblur_name_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_name_id_disabled_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_form_theme_namespace_name_method_id_action_acceptcharset;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_if_test;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_textarea_rows_name_id_data$1length_cssClass_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_rows_onchange_name_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_name_id_data$1length_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_rows_onblur_name_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_name_id_disabled_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_form_theme_namespace_name_method_id_action_acceptcharset = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_textarea_rows_name_id_data$1length_cssClass_cols_nobody.release();
    _jspx_tagPool_s_textarea_rows_onchange_name_id_cols_nobody.release();
    _jspx_tagPool_s_textfield_name_id_data$1length_cssClass_nobody.release();
    _jspx_tagPool_s_textarea_rows_onblur_name_id_cols_nobody.release();
    _jspx_tagPool_s_textarea_name_id_disabled_nobody.release();
    _jspx_tagPool_s_form_theme_namespace_name_method_id_action_acceptcharset.release();
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody.release();
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.release();
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.release();
    _jspx_tagPool_s_property_value_nobody.release();
    _jspx_tagPool_s_if_test.release();
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
      response.setContentType("text/html;charset=UTF-8");
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
      out.write("<!DOCTYPE html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<main>\r\n");
      out.write("\t");

	//Privilegios
	PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
	PrivilegiosTO privilegiosTO = new PrivilegiosTO();
	privilegiosTO.setIdRecurso(new Integer(6));
	privilegiosTO = privilegiosDAO.getPrivilegios(privilegiosTO,
			(UsuarioTO) session.getAttribute(Constants.USUARIO));
	Map<Integer, PrivilegioTO> priv = privilegiosTO.getMapPrivilegio();

      out.write("\r\n");
      out.write("\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t<!-- Empieza div cuerpo -->\r\n");
      out.write("\t\t<div id=\"tt\">\r\n");
      out.write("\t\t\t<div id=\"tttop\"></div>\r\n");
      out.write("\t\t\t<div id=\"ttcont\"></div>\r\n");
      out.write("\t\t\t<div id=\"ttbot\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div class=\"col s12\">\r\n");
      out.write("\t\t\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t\t<div class=\"col s10\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"card-title\">Inscripci&oacute;n Garant&iacute;a Mobiliaria</span>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"refInscripcion\" id=\"refInscripcion\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("\" />\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_form_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_if_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_if_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_if_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_if_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\t//setActiveTab('cuatroMenu');\r\n");
      out.write("\t\t//$(\"#cuatroMenu\").attr(\"class\",\"linkSelected\");\r\n");
      out.write("\t\tactivaBtn1();\r\n");
      out.write("\t\tactivaBtn2();\r\n");
      out.write("\t\tescondePartes();\r\n");
      out.write("\t</script>\r\n");
      out.write("</main>\r\n");
      out.write("\r\n");
      out.write("<div id=\"frmBien\" class=\"modal\">\r\n");
      out.write("\t<div class=\"modal-content\">\r\n");
      out.write("\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t<div id=\"frmBienContent\" class=\"card-content\">\r\n");
      out.write("\t\t\t\t<span class=\"card-title\">Bien Especial</span>\r\n");
      out.write("\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t\t<div class=\"col s10\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<label>Tipo Bien Especial</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secId4\" class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdFactura1\" for=\"mdFactura1\">No. Contribuyente Emite:</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"mdFactura2\" class=\"datepicker\" id=\"mdFactura2\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdFactura2\" for=\"mdFactura2\">Fecha: </label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textarea_4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdDescripcion\" for=\"mdDescripcion\">Descripci&oacute;n del bien </label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secId1\" class=\"row\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador\">Placa</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<select name=\"mdIdentificador\" id=\"mdIdentificador\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"0\">Seleccione</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"P0\">P0</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"A0\">A0</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"C0\">C0</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"CC\">CC</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"CD\">CD</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"DIS\">DIS</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"M0\">M0</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"MI\">MI</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"O0\">O0</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"TC\">TC</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"TE\">TE</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"TRC\">TRC</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"U0\">U0</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<option value=\"00\">00</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secId2\" class=\"row\" style=\"display: none;\"><span class=\"col s12 center-align\">Y</span></div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secId3\" class=\"row\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador2\" for=\"mdIdentificador2\">VIN</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<br />\r\n");
      out.write("\t\t\t\t\t\t<center>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId5\" class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\" class=\"btn-large indigo\" onclick=\"add_bien();\">Aceptar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId6\" class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\" id=\"formBienButton\" class=\"btn-large indigo\" onclick=\"add_bien();\">Aceptar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</center>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<div id=\"frmFile\" class=\"modal\">\r\n");
      out.write("\t<div class=\"modal-content\">\r\n");
      out.write("\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t<div class=\"card-content\">\r\n");
      out.write("\t\t\t\t<span class=\"card-title\">Bien Especial</span>\r\n");
      out.write("\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t\t<div class=\"col s10\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<label>Tipo Bien Especial</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secTxt3\" class=\"row note\">\r\n");
      out.write("\t\t\t\t\t\t\t<span id=\"txtspan\" name=\"txtspan\"></span>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"file-field input-field\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"btn\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span>Archivo</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input type=\"file\" name=\"excelfile\" id=\"excelfile\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"file-path-wrapper\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"file-path validate\" type=\"text\" name=\"namefile\" id=\"namefile\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tplaceholder=\"Seleccione...\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s4\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\" class=\"btn-large indigo\" onclick=\"ExportToTable();\">Cargar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"exceltable\">\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<br />\r\n");
      out.write("\t\t\t\t\t\t<hr />\r\n");
      out.write("\t\t\t\t\t\t<center>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\" class=\"modal-close btn-large indigo\">Aceptar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</center>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/material-dialog.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-3.3.1.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/xlsx.core.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar idTramite = ");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write(" ;\r\n");
      out.write("\r\n");
      out.write("\tcargaParteBienes('divParteDWRBienes', idTramite);\r\n");
      out.write("</script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tfunction add_bien() {\r\n");
      out.write("\r\n");
      out.write("\t\tvar idTramite = document.getElementById(\"refInscripcion\").value;\r\n");
      out.write("\t\tvar mdDescripcion = document.getElementById(\"mdDescripcion\").value;\r\n");
      out.write("\t\tvar idTipo = document.getElementById(\"mdBienEspecial\").value;\r\n");
      out.write("\t\tvar mdIdentificador = document.getElementById(\"mdIdentificador\").value;\r\n");
      out.write("\t\tvar mdIdentificador1 = document.getElementById(\"mdIdentificador1\").value;\r\n");
      out.write("\t\tvar mdIdentificador2 = document.getElementById(\"mdIdentificador2\").value;\r\n");
      out.write("\r\n");
      out.write("\t\tif (!noVacio(mdDescripcion)) {\r\n");
      out.write("\t\t\talertMaterialize('Debe ingresar la descripcion del bien especial');\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif (idTipo == '2') {\r\n");
      out.write("\t\t\tmdDescripcion = 'Emitido por: ' + document.getElementById(\"mdFactura1\").value + \" Fecha: \" + document\r\n");
      out.write("\t\t\t\t.getElementById(\"mdFactura2\").value + \" \" + mdDescripcion;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif (idTipo == '1') {\r\n");
      out.write("\t\t\tif (!noVacio(mdIdentificador2)) {\r\n");
      out.write("\t\t\t\talertMaterialize('Debe ingresar el VIN del vehiculo');\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tParteDwrAction.registrarBien('divParteDWRBienes', idTramite, mdDescripcion, idTipo, mdIdentificador,\r\n");
      out.write("\t\t\tmdIdentificador1, mdIdentificador2, showParteBienes);\r\n");
      out.write("\r\n");
      out.write("\t\t$(document).ready(function () {\r\n");
      out.write("\t\t\t$('#frmBien').modal('close');\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction cambiaBienesEspeciales() {\r\n");
      out.write("\t\tvar x = document.getElementById(\"mdBienEspecial\").value;\r\n");
      out.write("\r\n");
      out.write("\t\tif (x == '1') {\r\n");
      out.write("\t\t\tdocument.getElementById(\"mdDescripcion\").disabled = false;\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId1\").style.display = 'block';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId2\").style.display = 'block';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId3\").style.display = 'block';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId4\").style.display = 'none';\r\n");
      out.write("\r\n");
      out.write("\t\t\tdocument.getElementById(\"lblMdDescripcion\").innerHTML = 'Descripci&oacute;n del veh&iacute;culo';\r\n");
      out.write("\t\t\tdocument.getElementById(\"lblMdIdentificador2\").innerHTML = 'VIN';\r\n");
      out.write("\t\t} else if (x == '2') {\r\n");
      out.write("\t\t\tdocument.getElementById(\"mdDescripcion\").disabled = false;\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId1\").style.display = 'none';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId2\").style.display = 'none';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId3\").style.display = 'block';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId4\").style.display = 'block';\r\n");
      out.write("\r\n");
      out.write("\t\t\tdocument.getElementById(\"lblMdIdentificador2\").innerHTML = 'No. Factura';\r\n");
      out.write("\t\t\tdocument.getElementById(\"lblMdDescripcion\").innerHTML = 'Observaciones Generales';\r\n");
      out.write("\t\t} else if (x == '3') {\r\n");
      out.write("\t\t\tdocument.getElementById(\"mdDescripcion\").disabled = false;\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId1\").style.display = 'none';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId2\").style.display = 'none';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId3\").style.display = 'block';\r\n");
      out.write("\t\t\tdocument.getElementById(\"secId4\").style.display = 'none';\r\n");
      out.write("\r\n");
      out.write("\t\t\tdocument.getElementById(\"lblMdIdentificador2\").innerHTML = 'No. Serie';\r\n");
      out.write("\t\t\tdocument.getElementById(\"lblMdDescripcion\").innerHTML = 'Descripci&oacute;n del bien';\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction esPrioritaria() {\r\n");
      out.write("\t\tvar checkBox = document.getElementById(\"actoContratoTO.garantiaPrioritaria\");\r\n");
      out.write("\t\tif (checkBox.checked == true) {\r\n");
      out.write("\t\t\tMaterialDialog.alert(\r\n");
      out.write("\t\t\t\t'<p style=\"text-align: justify; text-justify: inter-word;\">Recuerde: <b>Artï¿½culo 17. Garantia Mobiliria Prioritaria.</b> ' +\r\n");
      out.write("\t\t\t\t'La publicidad de la garantï¿½a mobiliaria se constituye por medio de la inscripciï¿½n del formulario registral, ' +\r\n");
      out.write("\t\t\t\t'que haga referencia al carï¿½cter prioritario especial de esta garantï¿½a y que describa los bienes gravadoas por ' +\r\n");
      out.write("\t\t\t\t'categorï¿½a, sin necesidad de descripciï¿½n pormenorizada. <br> <br>' +\r\n");
      out.write("\t\t\t\t'Para el caso se consituya respecto de bienes que ' +\r\n");
      out.write("\t\t\t\t'pasarï¿½n a formar parte del inventario el deudor garante, el acreedor garantizado que financie adquisicion ' +\r\n");
      out.write("\t\t\t\t'de la garantï¿½a mobiliaria prioritaria deberï¿½ notificar por escrito, en papel o por medio de un documento ' +\r\n");
      out.write("\t\t\t\t'electrï¿½nico, con anterioridad o al momento de la inscripciï¿½n e esta garantï¿½a, a aquello acreedores garantizados ' +\r\n");
      out.write("\t\t\t\t'que hayan inscrito previamente garantï¿½as mobiliarias sobre el inventario, a fin de que obtenga un grado de ' +\r\n");
      out.write("\t\t\t\t'prelacion superior al de estos acreedores. <br><br>' +\r\n");
      out.write("\t\t\t\t'<b>Artï¿½culo 56. Prelaciï¿½n de la garantï¿½a mobiliaria prioritaria para la adquisiciï¿½n de bienes. </b> Una garantï¿½a ' +\r\n");
      out.write("\t\t\t\t'mobiliaria prioritaria para la adquisiciï¿½n de bienes especï¿½ficos tendrï¿½ prelaciï¿½n sobre cualquier ' +\r\n");
      out.write("\t\t\t\t'garantï¿½a anterior que afecte bienes muebles futuros del mismo tipo en ' +\r\n");
      out.write("\t\t\t\t'posesiï¿½n del deudor garante, siempre y cuando la garantï¿½a mobiliaria ' +\r\n");
      out.write("\t\t\t\t'prioritaria se constituya y publicite conforme lo establecido por esta ' +\r\n");
      out.write("\t\t\t\t'ley, aï¿½n y cuando a esta garantï¿½a mobiliaria prioritaria se le haya dado ' +\r\n");
      out.write("\t\t\t\t'publicidad con posterioridad a la publicidad de la garantï¿½a anterior. La ' +\r\n");
      out.write("\t\t\t\t'garantï¿½a mobiliaria prioritaria para la adquisiciï¿½n de bienes especï¿½ficos ' +\r\n");
      out.write("\t\t\t\t'se extenderï¿½ exclusivamente sobre los bienes muebles especï¿½ficos ' +\r\n");
      out.write("\t\t\t\t'adquiridos y al numerario especï¿½ficamente atribuible a la venta de estos ' +\r\n");
      out.write("\t\t\t\t'ï¿½ltimos, siempre y cuando el acreedor garantizado cumpla con los ' +\r\n");
      out.write("\t\t\t\t'requisitos de inscripciï¿½n de la garantï¿½a mobiliaria prioritaria, ' +\r\n");
      out.write("\t\t\t\t'establecidos en esta ley. </p>', {\r\n");
      out.write("\t\t\t\t\ttitle: 'Garantia Prioritaria', // Modal title\r\n");
      out.write("\t\t\t\t\tbuttons: { // Receive buttons (Alert only use close buttons)\r\n");
      out.write("\t\t\t\t\t\tclose: {\r\n");
      out.write("\t\t\t\t\t\t\ttext: 'close', //Text of close button\r\n");
      out.write("\t\t\t\t\t\t\tclassName: 'red', // Class of the close button\r\n");
      out.write("\t\t\t\t\t\t\tcallback: function () { // Function for modal click\r\n");
      out.write("\t\t\t\t\t\t\t\t//alert(\"hello\")\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction limpiaCampos() {\r\n");
      out.write("\t\tdocument.getElementById(\"secId1\").style.display = 'none';\r\n");
      out.write("\t\tdocument.getElementById(\"secId2\").style.display = 'none';\r\n");
      out.write("\t\tdocument.getElementById(\"secId3\").style.display = 'none';\r\n");
      out.write("\t\tdocument.getElementById(\"secId4\").style.display = 'none';\r\n");
      out.write("\t\tdocument.getElementById(\"secId5\").style.display = 'block';\r\n");
      out.write("\t\tdocument.getElementById(\"secId6\").style.display = 'none';\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.getElementById(\"mdIdentificador\").value = '0';\r\n");
      out.write("\t\tdocument.getElementById(\"mdIdentificador1\").value = '';\r\n");
      out.write("\t\tdocument.getElementById(\"mdIdentificador2\").value = '';\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.getElementById(\"mdFactura1\").value = '';\r\n");
      out.write("\t\tdocument.getElementById(\"mdFactura2\").value = '';\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.getElementById(\"mdDescripcion\").value = '';\r\n");
      out.write("\t\tdocument.getElementById(\"mdDescripcion\").disabled = true;\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.getElementById(\"mdBienEspecial\").value = '0';\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\tMaterialize.updateTextFields();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction otroRegistro() {\r\n");
      out.write("\t\tvar checkBox = document.getElementById(\"actoContratoTO.cpRegistro\");\r\n");
      out.write("\t\tif (checkBox.checked == true) {\r\n");
      out.write("\t\t\tdocument.getElementById(\"actoContratoTO.txtRegistro\").disabled = false;\r\n");
      out.write("\t\t\tMaterialize.updateTextFields();\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tdocument.getElementById(\"actoContratoTO.txtRegistro\").value = '';\r\n");
      out.write("\t\t\tdocument.getElementById(\"actoContratoTO.txtRegistro\").disabled = true;\r\n");
      out.write("\t\t\tMaterialize.updateTextFields();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction BindTable(jsondata, tableid) {\r\n");
      out.write("\t\t/*Function used to convert the JSON array to Html Table*/\r\n");
      out.write("\t\tvar columns = BindTableHeader(jsondata, tableid); /*Gets all the column headings of Excel*/\r\n");
      out.write("\t\tvar idTramite = document.getElementById(\"refInscripcion\").value;\r\n");
      out.write("\t\tvar mdDescripcion = '';\r\n");
      out.write("\t\tvar idTipo = document.getElementById(\"mdBienEspecial2\").value;\r\n");
      out.write("\t\tvar mdIdentificador = '';\r\n");
      out.write("\t\tvar mdIdentificador1 = '';\r\n");
      out.write("\t\tvar mdIdentificador2 = '';\r\n");
      out.write("\t\tvar mdFactura1 = '';\r\n");
      out.write("\t\tvar mdFactura2 = '';\r\n");
      out.write("\t\tvar tipoId = '';\r\n");
      out.write("\t\tvar correcto = 0;\r\n");
      out.write("\t\tvar limite = 50;\r\n");
      out.write("\r\n");
      out.write("\t\tif (jsondata.length > limite.valueOf()) {\r\n");
      out.write("\t\t\talertMaterialize('Error!. Solo se pueden cargar ' + limite + ' registros');\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif (idTipo == '0') {\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tfor (var i = 0; i < jsondata.length; i++) {\r\n");
      out.write("\t\t\tvar row$ = $('<tr/>');\r\n");
      out.write("\t\t\tmdDescripcion = '';\r\n");
      out.write("\t\t\tmdIdentificador = '';\r\n");
      out.write("\t\t\tmdIdentificador1 = '';\r\n");
      out.write("\t\t\tmdIdentificador2 = '';\r\n");
      out.write("\t\t\tmdFactura1 = '';\r\n");
      out.write("\t\t\tmdFactura2 = '';\r\n");
      out.write("\t\t\tcorrecto = 0;\r\n");
      out.write("\t\t\ttipoId = '';\r\n");
      out.write("\r\n");
      out.write("\t\t\tfor (var colIndex = 0; colIndex < columns.length; colIndex++) {\r\n");
      out.write("\t\t\t\tvar cellValue = jsondata[i][columns[colIndex]];\r\n");
      out.write("\t\t\t\tif (cellValue == null)\r\n");
      out.write("\t\t\t\t\tcellValue = \"\";\r\n");
      out.write("\t\t\t\tif (idTipo == '1') {\r\n");
      out.write("\t\t\t\t\tif (colIndex == 0) {\r\n");
      out.write("\t\t\t\t\t\ttipoId = cellValue;\r\n");
      out.write("\t\t\t\t\t\tif (cellValue == '1') {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Placa';\r\n");
      out.write("\t\t\t\t\t\t} else if (cellValue == '2') {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'VIN';\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex == 1) {\r\n");
      out.write("\t\t\t\t\t\tif (cellValue.length > 25) {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tif (tipoId == '1') {\r\n");
      out.write("\t\t\t\t\t\t\t\tif (cellValue.includes(\"-\")) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvar str = cellValue.split(\"-\");\r\n");
      out.write("\t\t\t\t\t\t\t\t\tmdIdentificador = str[0];\r\n");
      out.write("\t\t\t\t\t\t\t\t\tmdIdentificador1 = str[1];\r\n");
      out.write("\t\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\tmdIdentificador2 = cellValue;\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex == 2) {\r\n");
      out.write("\t\t\t\t\t\tif (cellValue.length > 100) {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tmdDescripcion = cellValue;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex > 2) {\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t} else if (idTipo == '2') { //Facturas\t\t\t\t \r\n");
      out.write("\t\t\t\t\tif (colIndex == 0) {\r\n");
      out.write("\t\t\t\t\t\tif (cellValue.length > 25) {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tmdFactura1 = cellValue;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex == 1) {\r\n");
      out.write("\t\t\t\t\t\tvar patt = /^(0?[1-9]|[12][0-9]|3[01])[\\/](0?[1-9]|1[012])[\\/]\\d{4}$/;\r\n");
      out.write("\t\t\t\t\t\tconsole.log(cellValue);\r\n");
      out.write("\t\t\t\t\t\tif (!patt.test(cellValue)) {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tmdFactura2 = cellValue;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex == 2) {\r\n");
      out.write("\t\t\t\t\t\tif (cellValue.length > 25) {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tmdIdentificador2 = cellValue;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex == 3) {\r\n");
      out.write("\t\t\t\t\t\tif (cellValue.length > 100) {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tmdDescripcion = 'Emitido por: ' + mdFactura1 + \" Fecha: \" + mdFactura2 + \" \" + cellValue;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex > 3) {\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t} else if (idTipo == '3') {\r\n");
      out.write("\t\t\t\t\tif (colIndex == 0) {\r\n");
      out.write("\t\t\t\t\t\tif (cellValue.length > 25) {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tmdIdentificador2 = cellValue;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex == 1) {\r\n");
      out.write("\t\t\t\t\t\tif (cellValue.length > 100) {\r\n");
      out.write("\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tmdDescripcion = cellValue;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif (colIndex > 1) {\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\trow$.append($('<td/>').html(cellValue));\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tif (correcto == 0) {\r\n");
      out.write("\t\t\t\tParteDwrAction.registrarBien('divParteDWRBienes', idTramite, mdDescripcion, idTipo, mdIdentificador,\r\n");
      out.write("\t\t\t\t\tmdIdentificador1, mdIdentificador2, showParteBienes);\r\n");
      out.write("\t\t\t\trow$.append('<td><font color=\"green\">Cargado</font></td>');\r\n");
      out.write("\t\t\t} else {\r\n");
      out.write("\t\t\t\trow$.append('<td><font color=\"red\">Error verifique datos</font></td>');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$(tableid).append(row$);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction BindTableHeader(jsondata, tableid) {\r\n");
      out.write("\t\t/*Function used to get all column names from JSON and bind the html table header*/\r\n");
      out.write("\t\tvar columnSet = [];\r\n");
      out.write("\t\tvar headerTr$ = $('<tr/>');\r\n");
      out.write("\t\tfor (var i = 0; i < jsondata.length; i++) {\r\n");
      out.write("\t\t\tvar rowHash = jsondata[i];\r\n");
      out.write("\t\t\tfor (var key in rowHash) {\r\n");
      out.write("\t\t\t\tif (rowHash.hasOwnProperty(key)) {\r\n");
      out.write("\t\t\t\t\tif ($.inArray(key, columnSet) == -1) {\r\n");
      out.write("\t\t\t\t\t\t/*Adding each unique column names to a variable array*/\r\n");
      out.write("\t\t\t\t\t\tcolumnSet.push(key);\r\n");
      out.write("\t\t\t\t\t\theaderTr$.append($('<th/>').html(key));\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\theaderTr$.append('<th>Resultado</th>');\r\n");
      out.write("\t\t$(tableid).append(headerTr$);\r\n");
      out.write("\t\treturn columnSet;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction ExportToTable() {\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.getElementById(\"exceltable\").innerHTML = '<table id=\"exceltable\"></table> ';\r\n");
      out.write("\r\n");
      out.write("\t\tvar regex = /^([a-zA-Z0-9\\s_\\\\.\\-:])+(.xlsx|.xls)$/;\r\n");
      out.write("\t\t/*Checks whether the file is a valid excel file*/\r\n");
      out.write("\t\tif (regex.test($(\"#excelfile\").val().toLowerCase())) {\r\n");
      out.write("\t\t\tvar xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/\r\n");
      out.write("\t\t\tif ($(\"#excelfile\").val().toLowerCase().indexOf(\".xlsx\") > 0) {\r\n");
      out.write("\t\t\t\txlsxflag = true;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t/*Checks whether the browser supports HTML5*/\r\n");
      out.write("\t\t\tif (typeof (FileReader) != \"undefined\") {\r\n");
      out.write("\t\t\t\tvar reader = new FileReader();\r\n");
      out.write("\t\t\t\treader.onload = function (e) {\r\n");
      out.write("\t\t\t\t\tvar data = e.target.result;\r\n");
      out.write("\t\t\t\t\t/*Converts the excel data in to object*/\r\n");
      out.write("\t\t\t\t\tif (xlsxflag) {\r\n");
      out.write("\t\t\t\t\t\tvar workbook = XLSX.read(data, {\r\n");
      out.write("\t\t\t\t\t\t\ttype: 'binary'\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tvar workbook = XLS.read(data, {\r\n");
      out.write("\t\t\t\t\t\t\ttype: 'binary'\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t/*Gets all the sheetnames of excel in to a variable*/\r\n");
      out.write("\t\t\t\t\tvar sheet_name_list = workbook.SheetNames;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/\r\n");
      out.write("\t\t\t\t\tsheet_name_list.forEach(function (y) {\r\n");
      out.write("\t\t\t\t\t\t/*Iterate through all sheets*/\r\n");
      out.write("\t\t\t\t\t\t/*Convert the cell value to Json*/\r\n");
      out.write("\t\t\t\t\t\tif (xlsxflag) {\r\n");
      out.write("\t\t\t\t\t\t\tvar exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tvar exceljson = XLS.utils.sheet_to_row_object_array(workbook.Sheets[y]);\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\tif (exceljson.length > 0 && cnt == 0) {\r\n");
      out.write("\t\t\t\t\t\t\tBindTable(exceljson, '#exceltable');\r\n");
      out.write("\t\t\t\t\t\t\tcnt++;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t$('#exceltable').show();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif (xlsxflag) {\r\n");
      out.write("\t\t\t\t\t/*If excel file is .xlsx extension than creates a Array Buffer from excel*/\r\n");
      out.write("\t\t\t\t\treader.readAsArrayBuffer($(\"#excelfile\")[0].files[0]);\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\treader.readAsBinaryString($(\"#excelfile\")[0].files[0]);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t} else {\r\n");
      out.write("\t\t\t\talertMaterialize(\"Error! Su explorador no soporta HTML5!\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\talertMaterialize(\"Por favor seleccione un archivo de Excel valido!\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction cambiaBienesEspecialesFile() {\r\n");
      out.write("\t\tvar x = document.getElementById(\"mdBienEspecial2\").value;\r\n");
      out.write("\r\n");
      out.write("\t\tif (x == '1') {\r\n");
      out.write("\t\t\tdocument.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: ' +\r\n");
      out.write("\t\t\t\t'<p><b>Tipo Identificador</b>, 1 si es Placa y 2 si es VIN<p>' +\r\n");
      out.write("\t\t\t\t'<p><b>Identificador</b>, maximo 25 caracteres</p>' +\r\n");
      out.write("\t\t\t\t'<p><b>Descripcion</b>, maximo 100 caracteres</p>';\r\n");
      out.write("\r\n");
      out.write("\t\t} else if (x == '2') {\r\n");
      out.write("\t\t\tdocument.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: ' +\r\n");
      out.write("\t\t\t\t'<p><b>Numero Identificacion Contribuyente</b>, maximo 25 caracteres</p>' +\r\n");
      out.write("\t\t\t\t'<p><b>Fecha</b>, formato texto DD/MM/YYYY</p>' +\r\n");
      out.write("\t\t\t\t'<p><b>Numero Factura</b>, maximo 25 caracteres</p>' +\r\n");
      out.write("\t\t\t\t'<p><b>Descripcion</b>, maximo 100 caracteres</p>';\r\n");
      out.write("\t\t} else if (x == '3') {\r\n");
      out.write("\t\t\tdocument.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: ' +\r\n");
      out.write("\t\t\t\t'<p><b>Identificador</b>, maximo 25 caracteres</p>' +\r\n");
      out.write("\t\t\t\t'<p><b>Descripcion</b>, maximo 100 caracteres</p>';\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction limpiaCamposFile() {\r\n");
      out.write("\t\tdocument.getElementById(\"mdBienEspecial2\").value = '0';\r\n");
      out.write("\t\tdocument.getElementById(\"txtspan\").innerHTML = '';\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.getElementById(\"exceltable\").innerHTML = '<table id=\"exceltable\"></table> ';\r\n");
      out.write("\t\tvar input = $(\"#excelfile\");\r\n");
      out.write("\t\tvar name = $(\"#namefile\");\r\n");
      out.write("\t\tinput.replaceWith(input.val('').clone(true));\r\n");
      out.write("\t\tname.replaceWith(name.val('').clone(true));\r\n");
      out.write("\r\n");
      out.write("\t\tMaterialize.updateTextFields();\r\n");
      out.write("\t}\r\n");
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
    _jspx_th_s_property_0.setValue("idInscripcion");
    int _jspx_eval_s_property_0 = _jspx_th_s_property_0.doStartTag();
    if (_jspx_th_s_property_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
    return false;
  }

  private boolean _jspx_meth_s_form_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:form
    org.apache.struts2.views.jsp.ui.FormTag _jspx_th_s_form_0 = (org.apache.struts2.views.jsp.ui.FormTag) _jspx_tagPool_s_form_theme_namespace_name_method_id_action_acceptcharset.get(org.apache.struts2.views.jsp.ui.FormTag.class);
    _jspx_th_s_form_0.setPageContext(_jspx_page_context);
    _jspx_th_s_form_0.setParent(null);
    _jspx_th_s_form_0.setAction("agregarGarantia.do");
    _jspx_th_s_form_0.setNamespace("inscripcion");
    _jspx_th_s_form_0.setTheme("simple");
    _jspx_th_s_form_0.setId("formS2ag");
    _jspx_th_s_form_0.setName("formS2ag");
    _jspx_th_s_form_0.setAcceptcharset("UTF-8");
    _jspx_th_s_form_0.setMethod("post");
    int _jspx_eval_s_form_0 = _jspx_th_s_form_0.doStartTag();
    if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_form_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_form_0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<div class=\"card\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"card-content\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<span>Bienes en garant&iacute;a si estos no tienen n&uacute;mero de serie:</span>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textarea_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<label for=\"descripcionId\">Descripci&oacute;n general</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<span>Bienes en garant&iacute;a si estos tienen n&uacute;mero de serie:</span>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col s12 right-align\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"btn-floating btn-large waves-effect indigo modal-trigger\" onclick=\"limpiaCampos()\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"#frmBien\" id=\"btnAgregar\"><i class=\"material-icons left\">add</i></a>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"btn-floating btn-large waves-effect indigo modal-trigger\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tonclick=\"limpiaCamposFile()\" href=\"#frmFile\" id=\"btnFile\"><i\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tclass=\"material-icons left\">attach_file</i></a>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"divParteDWRBienes\"></div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<div class=\"card\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"card-content\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"actoContratoTO.noGarantiaPreviaOt\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tid=\"actoContratoTO.noGarantiaPreviaOt\" value=\"true\" />\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<label for=\"actoContratoTO.noGarantiaPreviaOt\">Declaro que de conformidad con el contrato de\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tgarant&iacute;a, el deudor declar&oacute; que sobre los bienes en garant&iacute;a no existen\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\totro gravamen, anotaci&oacute;n o limitaci&oacute;n previa.</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"actoContratoTO.cambiosBienesMonto\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tid=\"actoContratoTO.cambiosBienesMonto\" value=\"true\" />\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<label for=\"actoContratoTO.cambiosBienesMonto\">Los atribuibles y derivados no esta afectos a\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tla Garant&iacute;a Mobiliaria</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"actoContratoTO.garantiaPrioritaria\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tid=\"actoContratoTO.garantiaPrioritaria\" value=\"true\" onclick=\"esPrioritaria()\" />\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<label for=\"actoContratoTO.garantiaPrioritaria\">Es prioritaria la garant&iacute;a\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tmobiliaria</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<p>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"actoContratoTO.cpRegistro\" id=\"actoContratoTO.cpRegistro\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tvalue=\"true\" onclick=\"otroRegistro()\" />\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<label for=\"actoContratoTO.cpRegistro\">El bien se encuentra en otro registro</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textarea_1((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<label for=\"actoContratoTO.txtRegistro\">Especifique cual</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<div class=\"card\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"card-content\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<span class=\"card-title\">Datos Generales del Contrato de la Garant&iacute;a</span>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textarea_2((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<label for=\"actoContratoTO.instrumentoPub\">Informaci&oacute;n general del contrato de la\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tGarant&iacute;a(Lugar y Fecha, tipo de documento, monto inicial garantizado, plazo,\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\tetc.)</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<div class=\"card\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"card-content\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<span class=\"card-title\">Datos Adicionales</span>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t");
        if (_jspx_meth_s_textarea_3((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<label for=\"actoContratoTO.otrosTerminos\">Observaciones Adicionales</label>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"divider\"></div>\r\n");
        out.write("\t\t\t\t\t\t\t\t<center>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<div class='row'>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<input type=\"button\" class=\"btn btn-large waves-effect indigo\" value=\"Regresar\" id=\"buttonID\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\tonclick=\"paso1()\" />\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<input type=\"button\" class=\"btn btn-large waves-effect indigo\" onclick=\"msg_guardar()\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\tvalue=\"Guardar\" id=\"guardarID\" />\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<input onclick=\"seguroContinuar()\" class=\"btn btn-large waves-effect indigo\" value=\"Continuar\"\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\ttype=\"button\" id=\"baceptar\" />\r\n");
        out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t\t\t</center>\r\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_form_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_form_theme_namespace_name_method_id_action_acceptcharset.reuse(_jspx_th_s_form_0);
      return true;
    }
    _jspx_tagPool_s_form_theme_namespace_name_method_id_action_acceptcharset.reuse(_jspx_th_s_form_0);
    return false;
  }

  private boolean _jspx_meth_s_textarea_0(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_0 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_onchange_name_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textarea_0.setRows("10");
    _jspx_th_s_textarea_0.setId("descripcionId");
    _jspx_th_s_textarea_0.setCols("80");
    _jspx_th_s_textarea_0.setOnchange("replaceValue('descripcionId')");
    _jspx_th_s_textarea_0.setName("actoContratoTO.descripcion");
    int _jspx_eval_s_textarea_0 = _jspx_th_s_textarea_0.doStartTag();
    if (_jspx_th_s_textarea_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_onchange_name_id_cols_nobody.reuse(_jspx_th_s_textarea_0);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_onchange_name_id_cols_nobody.reuse(_jspx_th_s_textarea_0);
    return false;
  }

  private boolean _jspx_meth_s_textarea_1(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_1 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_name_id_disabled_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textarea_1.setName("actoContratoTO.txtRegistro");
    _jspx_th_s_textarea_1.setId("actoContratoTO.txtRegistro");
    _jspx_th_s_textarea_1.setDisabled("true");
    int _jspx_eval_s_textarea_1 = _jspx_th_s_textarea_1.doStartTag();
    if (_jspx_th_s_textarea_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_name_id_disabled_nobody.reuse(_jspx_th_s_textarea_1);
      return true;
    }
    _jspx_tagPool_s_textarea_name_id_disabled_nobody.reuse(_jspx_th_s_textarea_1);
    return false;
  }

  private boolean _jspx_meth_s_textarea_2(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_2 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textarea_2.setRows("10");
    _jspx_th_s_textarea_2.setCols("80");
    _jspx_th_s_textarea_2.setName("actoContratoTO.instrumentoPub");
    _jspx_th_s_textarea_2.setDynamicAttribute(null, "maxlength", new String("3500"));
    _jspx_th_s_textarea_2.setId("actoContratoTO.instrumentoPub");
    int _jspx_eval_s_textarea_2 = _jspx_th_s_textarea_2.doStartTag();
    if (_jspx_th_s_textarea_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_2);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_2);
    return false;
  }

  private boolean _jspx_meth_s_textarea_3(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_3 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_onblur_name_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_3.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_textarea_3.setRows("10");
    _jspx_th_s_textarea_3.setCols("80");
    _jspx_th_s_textarea_3.setId("actoContratoTO.otrosTerminos");
    _jspx_th_s_textarea_3.setName("actoContratoTO.otrosTerminos");
    _jspx_th_s_textarea_3.setOnblur("actualizaCopia()");
    int _jspx_eval_s_textarea_3 = _jspx_th_s_textarea_3.doStartTag();
    if (_jspx_th_s_textarea_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_onblur_name_id_cols_nobody.reuse(_jspx_th_s_textarea_3);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_onblur_name_id_cols_nobody.reuse(_jspx_th_s_textarea_3);
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
    _jspx_th_s_if_0.setTest("actoContratoTO.noGarantiaPreviaOt");
    int _jspx_eval_s_if_0 = _jspx_th_s_if_0.doStartTag();
    if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById('actoContratoTO.noGarantiaPreviaOt').checked = 1;\r\n");
        out.write("\t\t\t\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t\t\t\t");
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

  private boolean _jspx_meth_s_if_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_1 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_if_1.setPageContext(_jspx_page_context);
    _jspx_th_s_if_1.setParent(null);
    _jspx_th_s_if_1.setTest("actoContratoTO.cambiosBienesMonto");
    int _jspx_eval_s_if_1 = _jspx_th_s_if_1.doStartTag();
    if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_1.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById('actoContratoTO.cambiosBienesMonto').checked = 1;\r\n");
        out.write("\t\t\t\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t\t\t\t");
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

  private boolean _jspx_meth_s_if_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_2 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_if_2.setPageContext(_jspx_page_context);
    _jspx_th_s_if_2.setParent(null);
    _jspx_th_s_if_2.setTest("actoContratoTO.garantiaPrioritaria");
    int _jspx_eval_s_if_2 = _jspx_th_s_if_2.doStartTag();
    if (_jspx_eval_s_if_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_2.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById('actoContratoTO.garantiaPrioritaria').checked = 1;\r\n");
        out.write("\t\t\t\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_if_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_if_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_if_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_2);
      return true;
    }
    _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_2);
    return false;
  }

  private boolean _jspx_meth_s_if_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_3 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_if_3.setPageContext(_jspx_page_context);
    _jspx_th_s_if_3.setParent(null);
    _jspx_th_s_if_3.setTest("actoContratoTO.cpRegistro");
    int _jspx_eval_s_if_3 = _jspx_th_s_if_3.doStartTag();
    if (_jspx_eval_s_if_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_3.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById('actoContratoTO.cpRegistro').checked = 1;\r\n");
        out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById(\"actoContratoTO.txtRegistro\").disabled = false;\r\n");
        out.write("\t\t\t\t\t\t\t\t\tMaterialize.updateTextFields();\r\n");
        out.write("\t\t\t\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_if_3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_if_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_if_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_3);
      return true;
    }
    _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_3);
    return false;
  }

  private boolean _jspx_meth_s_select_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:select
    org.apache.struts2.views.jsp.ui.SelectTag _jspx_th_s_select_0 = (org.apache.struts2.views.jsp.ui.SelectTag) _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.get(org.apache.struts2.views.jsp.ui.SelectTag.class);
    _jspx_th_s_select_0.setPageContext(_jspx_page_context);
    _jspx_th_s_select_0.setParent(null);
    _jspx_th_s_select_0.setName("mdBienEspecial");
    _jspx_th_s_select_0.setId("mdBienEspecial");
    _jspx_th_s_select_0.setList("listaBienEspecial");
    _jspx_th_s_select_0.setListKey("idTipo");
    _jspx_th_s_select_0.setListValue("desTipo");
    _jspx_th_s_select_0.setOnchange("cambiaBienesEspeciales()");
    int _jspx_eval_s_select_0 = _jspx_th_s_select_0.doStartTag();
    if (_jspx_th_s_select_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_0);
      return true;
    }
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_0 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_0.setParent(null);
    _jspx_th_s_textfield_0.setName("mdFactura1");
    _jspx_th_s_textfield_0.setId("mdFactura1");
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

  private boolean _jspx_meth_s_textarea_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_4 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_name_id_data$1length_cssClass_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_4.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_4.setParent(null);
    _jspx_th_s_textarea_4.setRows("10");
    _jspx_th_s_textarea_4.setId("mdDescripcion");
    _jspx_th_s_textarea_4.setCols("80");
    _jspx_th_s_textarea_4.setName("mdDescripcion");
    _jspx_th_s_textarea_4.setCssClass("materialize-textarea");
    _jspx_th_s_textarea_4.setDynamicAttribute(null, "data-length", new String("500"));
    int _jspx_eval_s_textarea_4 = _jspx_th_s_textarea_4.doStartTag();
    if (_jspx_th_s_textarea_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_name_id_data$1length_cssClass_cols_nobody.reuse(_jspx_th_s_textarea_4);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_name_id_data$1length_cssClass_cols_nobody.reuse(_jspx_th_s_textarea_4);
    return false;
  }

  private boolean _jspx_meth_s_textfield_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_1 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_1.setParent(null);
    _jspx_th_s_textfield_1.setName("mdIdentificador1");
    _jspx_th_s_textfield_1.setId("mdIdentificador1");
    _jspx_th_s_textfield_1.setCssClass("validate");
    _jspx_th_s_textfield_1.setMaxlength("150");
    int _jspx_eval_s_textfield_1 = _jspx_th_s_textfield_1.doStartTag();
    if (_jspx_th_s_textfield_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
      return true;
    }
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
    return false;
  }

  private boolean _jspx_meth_s_textfield_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_2 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_id_data$1length_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_2.setParent(null);
    _jspx_th_s_textfield_2.setName("mdIdentificador2");
    _jspx_th_s_textfield_2.setId("mdIdentificador2");
    _jspx_th_s_textfield_2.setCssClass("validate");
    _jspx_th_s_textfield_2.setDynamicAttribute(null, "data-length", new String("150"));
    int _jspx_eval_s_textfield_2 = _jspx_th_s_textfield_2.doStartTag();
    if (_jspx_th_s_textfield_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_id_data$1length_cssClass_nobody.reuse(_jspx_th_s_textfield_2);
      return true;
    }
    _jspx_tagPool_s_textfield_name_id_data$1length_cssClass_nobody.reuse(_jspx_th_s_textfield_2);
    return false;
  }

  private boolean _jspx_meth_s_select_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:select
    org.apache.struts2.views.jsp.ui.SelectTag _jspx_th_s_select_1 = (org.apache.struts2.views.jsp.ui.SelectTag) _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.get(org.apache.struts2.views.jsp.ui.SelectTag.class);
    _jspx_th_s_select_1.setPageContext(_jspx_page_context);
    _jspx_th_s_select_1.setParent(null);
    _jspx_th_s_select_1.setName("mdBienEspecial2");
    _jspx_th_s_select_1.setId("mdBienEspecial2");
    _jspx_th_s_select_1.setList("listaBienEspecial");
    _jspx_th_s_select_1.setListKey("idTipo");
    _jspx_th_s_select_1.setListValue("desTipo");
    _jspx_th_s_select_1.setOnchange("cambiaBienesEspecialesFile()");
    int _jspx_eval_s_select_1 = _jspx_th_s_select_1.doStartTag();
    if (_jspx_th_s_select_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_1);
      return true;
    }
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_1);
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
    _jspx_th_s_property_1.setValue("idInscripcion");
    int _jspx_eval_s_property_1 = _jspx_th_s_property_1.doStartTag();
    if (_jspx_th_s_property_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
    return false;
  }
}
