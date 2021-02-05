package org.apache.jsp.WEB_002dINF.jsp.content.tramites;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Iterator;
import java.util.Map;
import mx.gob.se.rug.seguridad.to.PrivilegioTO;
import mx.gob.se.rug.seguridad.dao.PrivilegiosDAO;
import mx.gob.se.rug.seguridad.to.PrivilegiosTO;
import mx.gob.se.rug.to.UsuarioTO;
import mx.gob.se.rug.seguridad.to.MenuTO;
import mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl;
import mx.gob.se.rug.constants.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import mx.gob.se.rug.constants.Constants;

public final class FormularioFactoraje_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/WEB-INF/jsp/Layout/menu/applicationCtx.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_name_maxlength_id_data$1length_cssClass_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_if_test;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_data$1length_cssClass_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.release();
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_data$1length_cssClass_cols_nobody.release();
    _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody.release();
    _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.release();
    _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody.release();
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	ApplicationContext ctx = null;
	if (ctx == null) {
		ctx = new ClassPathXmlApplicationContext(Constants.SEGURIDAD_APP_CONTEXT);
	}

      out.write("\r\n");
      out.write("\r\n");
      out.write("<main>\r\n");

//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();


      out.write("\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/factoraje.css\">\r\n");
      out.write("\t<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-3.3.1.min.js\"></script>\r\n");
      out.write("<input type=\"hidden\" name=\"tipoBienAll\" value=\"false\" id=\"idTipoBienAll\" />\r\n");
      out.write("<div class=\"container-fluid\">\t\r\n");
      out.write("\t<div id=\"modifica\" class=\"row\">\r\n");
      out.write("\t\t<div class=\"col s12\"><div class=\"card\">\r\n");
      out.write("\t\t\t<div class=\"col s2\"></div>\r\n");
      out.write("\t\t\t<div class=\"col s8\">\r\n");
      out.write("\t\t\t\t<!-- row note teal -->\r\n");
      out.write("\t\t\t\t<form id=\"fafactoraje\" name=\"fafactoraje\" action=\"saveFactoraje.do\" method=\"post\">\r\n");
      out.write("\t\t\t\t\t<span class=\"card-title\">");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"refInscripcion\" id=\"refInscripcion\" value=\"");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write("\" />\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"reftipogarantia\" id=\"reftipogarantia\" value=\"");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write("\" />\r\n");
      out.write("\t\t\t\t\t<div class=\"row note_tabs light-blue darken-4\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t    <span class=\"white-text\">");
      if (_jspx_meth_s_property_3(_jspx_page_context))
        return;
      out.write("</span>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div id =\"divParteDWRxx2\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row note_tabs light-blue darken-4\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">");
      if (_jspx_meth_s_property_4(_jspx_page_context))
        return;
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div id=\"divParteDWRxx3\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_s_if_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t<div class=\"row note_tabs light-blue darken-4\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">");
      if (_jspx_meth_s_property_6(_jspx_page_context))
        return;
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textarea_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<label for=\"tiposbienes\">Descripci&oacute;n General:</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"blue-text text-darken-2\">");
      if (_jspx_meth_s_property_7(_jspx_page_context))
        return;
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col s12 right-align\">\r\n");
      out.write("\t\t\t\t\t\t\t<a class=\"btn-floating btn-large waves-effect indigo modal-trigger\" onclick=\"limpiaCampos()\"\r\n");
      out.write("\t\t\t\t\t\t\t\thref=\"#frmBien\" id=\"btnAgregar\"><i\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"material-icons left\">add</i></a>\r\n");
      out.write("\t\t\t\t\t\t\t<a class=\"btn-floating btn-large waves-effect indigo modal-trigger\" onclick=\"limpiaCamposFile()\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\thref=\"#frmFile\" id=\"btnFile\"><i\r\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"material-icons left\">attach_file</i></a>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"divParteDWRBienes\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t \t<div class=\"row note_tabs light-blue darken-4\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">");
      if (_jspx_meth_s_property_8(_jspx_page_context))
        return;
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t        \t");
      if (_jspx_meth_s_textarea_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t        \t<label for=\"instrumento\">");
      if (_jspx_meth_s_property_9(_jspx_page_context))
        return;
      out.write("</label>\r\n");
      out.write("\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t    \t\t");
      if (_jspx_meth_s_textarea_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t        \t<label for=\"modotrosgarantia\">");
      if (_jspx_meth_s_property_10(_jspx_page_context))
        return;
      out.write("</label>\r\n");
      out.write("\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t    <hr />\r\n");
      out.write("\t\t\t\t \t<center>\r\n");
      out.write("\t\t\t            <div class='row'>\r\n");
      out.write("\t\t\t            \t<input type=\"button\" id=\"bFirmar\" name=\"button\" class=\"btn btn-large waves-effect indigo\" value=\"Aceptar\" onclick=\"inscripcionFactoraje();\"/>\r\n");
      out.write("\t\t\t            </div>\r\n");
      out.write("\t\t          \t</center>\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div></div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<script type=\"text/javascript\"> \t\r\n");
      out.write("\t\t\t\t\tvar idPersona = ");
      if (_jspx_meth_s_property_11(_jspx_page_context))
        return;
      out.write(";\r\n");
      out.write("\t\t\t\t\tvar idTramite= ");
      if (_jspx_meth_s_property_12(_jspx_page_context))
        return;
      out.write(";\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t\t\tcargaParteDeudor('divParteDWRxx2',idTramite, idPersona,'0','1');\r\n");
      out.write("\t\t\t\t\tcargaParteAcreedor('divParteDWRxx3',idTramite, idPersona,'0','1');\r\n");
      out.write("\t\t\t\t\tcargaParteOtorgante('divParteDWRxx4',idTramite, idPersona,'0','1');\r\n");
      out.write("\t\t\t\t\tcargaParteBienes('divParteDWRBienes',idTramite);\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t//escondePartes();\t\r\n");
      out.write("\t\t\t\t</script> \t\t\t\t\r\n");
      out.write("</main>\r\n");
      out.write("<div id=\"frmBien\" class=\"modal  modal-fixed-footer\">\r\n");
      out.write("\t<div class=\"modal-content\">\r\n");
      out.write("\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t<div class=\"card-content\">\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t<span class=\"card-title\">Bien Especial</span>\r\n");
      out.write("\t\t\t\t<div class=\"row\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<div class=\"col s12\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_0(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t    \r\n");
      out.write("\t\t\t\t\t\t    \t<label>Tipo Bien Especial</label>\r\n");
      out.write("\t\t\t\t\t\t  \t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"showContainerData\">\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId4\" class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label id=\"lblMdFactura1\" for=\"mdFactura1\">No. Contribuyente Emite: (NIT)</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"mdFactura2\" class=\"datepicker\" id=\"mdFactura2\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label id=\"lblMdFactura2\" for=\"mdFactura2\">Fecha: </label>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\" >\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textarea_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label id=\"lblMdDescripcion\" for=\"mdDescripcion\">Descripci&oacute;n del bien</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId3\" class=\"row\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador3\" for=\"mdIdentificador3\">VIN</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador2\" for=\"mdIdentificador2\">VIN</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId1\"class=\"row\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador\">Placa</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-field col s6\">2\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId2\" class=\"row\" style=\"display: none;\"><span class=\"col s12 center-align\">Y</span></div>\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"modal-footer\">\r\n");
      out.write("\t\t<div id=\"secId5\" >\r\n");
      out.write("\t\t\t<a href=\"#!\"\r\n");
      out.write("\t\t\t\tclass=\"modal-action modal-close btn teal lighten-1\"\r\n");
      out.write("\t\t\t\tonclick=\"add_bien();\">Aceptar</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id=\"secId6\" >\r\n");
      out.write("\t\t\t<a href=\"#!\" id=\"formBienButton\"\r\n");
      out.write("\t\t\t\tclass=\"modal-action modal-close btn teal lighten-1\"\r\n");
      out.write("\t\t\t\tonclick=\"add_bien();\">Aceptar</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<div id=\"frmFile\" class=\"modal\">\r\n");
      out.write("\t<div class=\"modal-content\">\r\n");
      out.write("\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t<div class=\"card-content\">\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t<span class=\"card-title\">Bien Especial</span>\r\n");
      out.write("\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t\t<div class=\"col s10\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_2(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t    \r\n");
      out.write("\t\t\t\t\t\t    \t<label>Tipo Bien Especial</label>\r\n");
      out.write("\t\t\t\t\t\t  \t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secTxt3\" class=\"row note\">\r\n");
      out.write("\t\t\t\t\t\t\t<span id=\"txtspan\" name=\"txtspan\"></span>\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</div>\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"file-field input-field\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"btn\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span>Archivo</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t <input type=\"file\" name=\"excelfile\" id=\"excelfile\" />\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"file-path-wrapper\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"file-path validate\" type=\"text\" name=\"namefile\" id=\"namefile\"\tplaceholder=\"Seleccione...\" >\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s4\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"btn-large indigo\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tonclick=\"ExportToTable();\">Cargar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"exceltable\">  \r\n");
      out.write("\t\t\t\t\t\t\t</table> \r\n");
      out.write("\t\t\t\t\t\t</div>\t\r\n");
      out.write("\t\t\t\t\t\t<br />\r\n");
      out.write("\t\t\t\t\t\t<hr />\r\n");
      out.write("\t\t\t\t\t\t<center>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"modal-close btn-large indigo\">Aceptar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</center>\r\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-3.3.1.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/xlsx.core.min.js\"></script> \r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\tfunction characterNotAllowed(evt){\r\n");
      out.write("\t\tvar charText = evt.which;\r\n");
      out.write("\t\tif(charText >= 32 && charText <= 47){\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}else if(charText >= 58 && charText <=64){\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("function add_bien() {\r\n");
      out.write("\t  \r\n");
      out.write("\t  var idTramite = document.getElementById(\"refInscripcion\").value;\r\n");
      out.write("\t  var mdDescripcion = document.getElementById(\"mdDescripcion\").value;\r\n");
      out.write("\t  var idTipo = document.getElementById(\"mdBienEspecial\").value;\r\n");
      out.write("\t  var mdIdentificador = document.getElementById(\"mdIdentificador\").value;\r\n");
      out.write("\t  var mdIdentificador1 = document.getElementById(\"mdIdentificador1\").value;\r\n");
      out.write("\t  var mdIdentificador2 = document.getElementById(\"mdIdentificador2\").value;\r\n");
      out.write("\t  var mdIdentificador3 = document.getElementById(\"mdIdentificador3\").value;\r\n");
      out.write("\r\n");
      out.write("\t  if(!noVacio(mdDescripcion)){\r\n");
      out.write("\t\t  alertMaterialize('Debe ingresar la descripcion del bien especial');\r\n");
      out.write("\t\t  return false;\r\n");
      out.write("\t  }\r\n");
      out.write("\t  \r\n");
      out.write("\t  if(idTipo == '2'){\r\n");
      out.write("\t\t  mdDescripcion = 'Emitido por: ' + document.getElementById(\"mdFactura1\").value + \" Fecha: \" + document.getElementById(\"mdFactura2\").value + \" \" + mdDescripcion;\r\n");
      out.write("\t  }\r\n");
      out.write("\t  \r\n");
      out.write("\t  if(idTipo == '1'){\r\n");
      out.write("\t\t  if(!noVacio(mdIdentificador2)) {\r\n");
      out.write("\t\t\t  alertMaterialize('Debe ingresar el VIN del vehiculo');\r\n");
      out.write("\t\t\t  return false;\r\n");
      out.write("\t\t  }\r\n");
      out.write("\t  } \r\n");
      out.write("\t  \r\n");
      out.write("\t  ParteDwrAction.registrarBien('divParteDWRBienes',idTramite, mdDescripcion, idTipo, mdIdentificador,\r\n");
      out.write("\t\t\t  mdIdentificador1, mdIdentificador2, mdIdentificador3,showParteBienes);\r\n");
      out.write("          \r\n");
      out.write("\r\n");
      out.write("\t  \r\n");
      out.write("\t  $(document).ready(function() {\t  \r\n");
      out.write("\t\t\t$('#frmBien').modal('close');\r\n");
      out.write("\t\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function cambiaBienesEspeciales() {\r\n");
      out.write("\t  var x = document.getElementById(\"mdBienEspecial\").value;\r\n");
      out.write("\tconsole.log(x);\r\n");
      out.write("\r\n");
      out.write("\t  if(x=='1'){\r\n");
      out.write("\t\t  document.getElementById(\"mdDescripcion\").disabled = false;\t  \r\n");
      out.write("\t\t  document.getElementById(\"secId1\").style.display = 'block'; \r\n");
      out.write("\t\t  document.getElementById(\"secId2\").style.display = 'block';\r\n");
      out.write("\t\t  document.getElementById(\"secId3\").style.display = 'block';\r\n");
      out.write("\t\t  document.getElementById(\"secId4\").style.display = 'none';\r\n");
      out.write("\t\t  \r\n");
      out.write("\t\t  document.getElementById(\"lblMdDescripcion\").innerHTML = 'Descripci&oacute;n del veh&iacute;culo';\r\n");
      out.write("\t\t  document.getElementById(\"lblMdIdentificador2\").innerHTML = 'VIN';\r\n");
      out.write("\t  } else if (x=='2'){\r\n");
      out.write("\r\n");
      out.write("\t  \tdocument.getElementById('showContainerData').style.display = 'block';\r\n");
      out.write("\t\t  document.getElementById(\"mdDescripcion\").disabled = false;\t  \r\n");
      out.write("\t\t  document.getElementById(\"secId1\").style.display = 'none'; \r\n");
      out.write("\t\t  document.getElementById(\"secId2\").style.display = 'none';\r\n");
      out.write("\t\t  document.getElementById(\"secId3\").style.display = 'block';\t\t\r\n");
      out.write("\t\t  document.getElementById(\"secId4\").style.display = 'block';\r\n");
      out.write("\t\t  \r\n");
      out.write("\t\t  document.getElementById(\"lblMdIdentificador2\").innerHTML = 'No. Factura';\r\n");
      out.write("\t\t  document.getElementById(\"lblMdIdentificador3\").innerHTML = 'Serie'\r\n");
      out.write("\t\t  document.getElementById(\"lblMdDescripcion\").innerHTML = 'Observaciones Generales';\r\n");
      out.write("\t  } else if (x=='3'){\r\n");
      out.write("\t\t  document.getElementById(\"mdDescripcion\").disabled = false;\t  \r\n");
      out.write("\t\t  document.getElementById(\"secId1\").style.display = 'none'; \r\n");
      out.write("\t\t  document.getElementById(\"secId2\").style.display = 'none';\r\n");
      out.write("\t\t  document.getElementById(\"secId3\").style.display = 'block';\t\t\r\n");
      out.write("\t\t  document.getElementById(\"secId4\").style.display = 'none';\r\n");
      out.write("\t\t  \r\n");
      out.write("\t\t  document.getElementById(\"lblMdIdentificador2\").innerHTML = 'No. Serie';\r\n");
      out.write("\t\t  document.getElementById(\"lblMdDescripcion\").innerHTML = 'Descripci&oacute;n del bien';\r\n");
      out.write("\t  }\r\n");
      out.write("\t  \r\n");
      out.write("\t  \r\n");
      out.write("}\r\n");
      out.write("  \r\n");
      out.write("function limpiaCampos() {\r\n");
      out.write("\r\n");
      out.write("\t// show Data info\r\n");
      out.write("\tdocument.getElementById('showContainerData').style.display = 'none';\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t  document.getElementById(\"secId1\").style.display = 'none'; \r\n");
      out.write("\t  document.getElementById(\"secId2\").style.display = 'none';\r\n");
      out.write("\t  document.getElementById(\"secId3\").style.display = 'none';\r\n");
      out.write("\t  document.getElementById(\"secId4\").style.display = 'none';\r\n");
      out.write("\t  document.getElementById(\"secId5\").style.display = 'block';\r\n");
      out.write("\t  document.getElementById(\"secId6\").style.display = 'none';\r\n");
      out.write("\t  \r\n");
      out.write("\t  document.getElementById(\"mdIdentificador\").value = '0';\r\n");
      out.write("\t  document.getElementById(\"mdIdentificador1\").value = '';\r\n");
      out.write("\t  document.getElementById(\"mdIdentificador2\").value = '';\r\n");
      out.write("\t  document.getElementById(\"mdIdentificador3\").value = '';\r\n");
      out.write("\r\n");
      out.write("\t  document.getElementById(\"mdFactura1\").value = '';\r\n");
      out.write("\t  document.getElementById(\"mdFactura2\").value = '';\r\n");
      out.write("\t  \t  \r\n");
      out.write("\t  document.getElementById(\"mdDescripcion\").value = '';\t  \r\n");
      out.write("\t  document.getElementById(\"mdDescripcion\").disabled = true;\t  \r\n");
      out.write("\t  \r\n");
      out.write("\t  document.getElementById(\"mdBienEspecial\").value  = '0';\r\n");
      out.write("\t  \r\n");
      out.write("\t  \r\n");
      out.write("\t  Materialize.updateTextFields();\r\n");
      out.write("}\r\n");
      out.write("  \r\n");
      out.write("function otroRegistro() {\r\n");
      out.write("\t  var checkBox = document.getElementById(\"aRegistro\");\r\n");
      out.write("\t  if (checkBox.checked == true) {\r\n");
      out.write("\t\t  document.getElementById(\"txtregistro\").disabled = false;\r\n");
      out.write("\t\t  Materialize.updateTextFields();\r\n");
      out.write("\t  } else {\r\n");
      out.write("\t\t  document.getElementById(\"txtregistro\").value  = '';\r\n");
      out.write("\t\t  document.getElementById(\"txtregistro\").disabled = true;\r\n");
      out.write("\t\t  Materialize.updateTextFields();\r\n");
      out.write("\t  }\r\n");
      out.write("  }\r\n");
      out.write(" \r\n");
      out.write("function BindTable(jsondata, tableid) {/*Function used to convert the JSON array to Html Table*/  \r\n");
      out.write("     var columns = BindTableHeader(jsondata, tableid); /*Gets all the column headings of Excel*/  \r\n");
      out.write("\t var idTramite = document.getElementById(\"refInscripcion\").value;\r\n");
      out.write("\t var mdDescripcion = '';\r\n");
      out.write("\t var idTipo = document.getElementById(\"mdBienEspecial2\").value;\r\n");
      out.write("\t var mdIdentificador = '';\r\n");
      out.write("\t var mdIdentificador1 = '';\r\n");
      out.write("\t var mdIdentificador2 = '';\r\n");
      out.write("\t var mdFactura1 = '';\r\n");
      out.write("\t var mdFactura2 = '';\r\n");
      out.write("\t var tipoId = '';\r\n");
      out.write("\t var correcto = 0;\r\n");
      out.write("\t var limite = 50;\r\n");
      out.write("\t \r\n");
      out.write("\t if(jsondata.length > limite.valueOf()){\r\n");
      out.write("\t\t alertMaterialize('Error!. Solo se pueden cargar ' + limite + ' registros');\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t \r\n");
      out.write("\t if(idTipo == '0'){\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t \r\n");
      out.write("     for (var i = 0; i < jsondata.length; i++) {  \r\n");
      out.write("         var row$ = $('<tr/>');  \r\n");
      out.write("\t\t mdDescripcion = '';\r\n");
      out.write("\t\t mdIdentificador = '';\r\n");
      out.write("\t\t mdIdentificador1 = '';\r\n");
      out.write("\t     mdIdentificador2 = '';\r\n");
      out.write("\t\t mdFactura1 = '';\r\n");
      out.write("\t\t mdFactura2 = '';\r\n");
      out.write("\t\t correcto = 0;\r\n");
      out.write("\t\t tipoId = '';\r\n");
      out.write("\t\t \r\n");
      out.write("         for (var colIndex = 0; colIndex < columns.length; colIndex++) {  \r\n");
      out.write("             var cellValue = jsondata[i][columns[colIndex]];  \r\n");
      out.write("             if (cellValue == null)  \r\n");
      out.write("                 cellValue = \"\";  \r\n");
      out.write("\t\t\t if(idTipo == '1') {\r\n");
      out.write("\t\t\t\t if(colIndex == 0) {\r\n");
      out.write("\t\t\t\t\t tipoId = cellValue;\r\n");
      out.write("\t\t\t\t\t if(cellValue == '1'){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Placa';\r\n");
      out.write("\t\t\t\t\t } else if(cellValue == '2'){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'VIN';\r\n");
      out.write("\t\t\t\t\t } else {\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t }\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex == 1) {\t\r\n");
      out.write("\t\t\t\t\tif(cellValue.length > 25){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tif(tipoId == '1'){\r\n");
      out.write("\t\t\t\t\t\t\tif(cellValue.includes(\"-\")){\r\n");
      out.write("\t\t\t\t\t\t\t\tvar str = cellValue.split(\"-\");\r\n");
      out.write("\t\t\t\t\t\t\t\tmdIdentificador = str[0];\r\n");
      out.write("\t\t\t\t\t\t\t\tmdIdentificador1 = str[1];\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\tmdIdentificador2 = cellValue;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex == 2) {\r\n");
      out.write("\t\t\t\t\t if(cellValue.length > 100){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tmdDescripcion = cellValue;\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex > 2) {\r\n");
      out.write("\t\t\t\t\t correcto = 1;\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t } else if(idTipo == '2') { //Facturas\t\t\t\t \r\n");
      out.write("\t\t\t\t if(colIndex == 0) {\t\r\n");
      out.write("\t\t\t\t\tif(cellValue.length > 25){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t} else {\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tmdFactura1 = cellValue;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex == 1) {\r\n");
      out.write("\t\t\t\t\tvar patt = /^(0?[1-9]|[12][0-9]|3[01])[\\/](0?[1-9]|1[012])[\\/]\\d{4}$/;\r\n");
      out.write("\t\t\t\t\tconsole.log(cellValue);\r\n");
      out.write("\t\t\t\t\tif(!patt.test(cellValue)){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tmdFactura2 = cellValue;\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex == 2) {\r\n");
      out.write("\t\t\t\t\t if(cellValue.length > 25){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tmdIdentificador2 = cellValue;\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex == 3) {\r\n");
      out.write("\t\t\t\t\t if(cellValue.length > 100){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t} else {\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tmdDescripcion = 'Emitido por: ' + mdFactura1 + \" Fecha: \" + mdFactura2 + \" \" + cellValue;\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex > 3) {\r\n");
      out.write("\t\t\t\t\t correcto = 1;\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t } else if(idTipo == '3') { \r\n");
      out.write("\t\t\t\t if(colIndex == 0) {\t\r\n");
      out.write("\t\t\t\t\tif(cellValue.length > 25){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tmdIdentificador2 = cellValue;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex == 1) {\r\n");
      out.write("\t\t\t\t\tif(cellValue.length > 100){\r\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\r\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tmdDescripcion = cellValue;\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t\t if(colIndex > 1) {\r\n");
      out.write("\t\t\t\t\t correcto = 1;\r\n");
      out.write("\t\t\t\t }\r\n");
      out.write("\t\t\t }\t \r\n");
      out.write("             row$.append($('<td/>').html(cellValue));  \r\n");
      out.write("         }  \r\n");
      out.write("\t\t if(correcto == 0) {\r\n");
      out.write("\t\t\tParteDwrAction.registrarBien('divParteDWRBienes',idTramite, mdDescripcion, idTipo, mdIdentificador, \r\n");
      out.write("\t\t\t\tmdIdentificador1, mdIdentificador2, showParteBienes); \r\n");
      out.write("\t\t\trow$.append('<td><font color=\"green\">Cargado</font></td>');\r\n");
      out.write("\t\t } else {\r\n");
      out.write("\t\t\t row$.append('<td><font color=\"red\">Error verifique datos</font></td>');\r\n");
      out.write("\t\t }\r\n");
      out.write("         $(tableid).append(row$);  \r\n");
      out.write("     }  \r\n");
      out.write(" }\r\n");
      out.write(" \r\n");
      out.write("function BindTableHeader(jsondata, tableid) {/*Function used to get all column names from JSON and bind the html table header*/  \r\n");
      out.write("     var columnSet = [];  \r\n");
      out.write("     var headerTr$ = $('<tr/>');  \r\n");
      out.write("     for (var i = 0; i < jsondata.length; i++) {  \r\n");
      out.write("         var rowHash = jsondata[i];  \r\n");
      out.write("         for (var key in rowHash) {  \r\n");
      out.write("             if (rowHash.hasOwnProperty(key)) {  \r\n");
      out.write("                 if ($.inArray(key, columnSet) == -1) {/*Adding each unique column names to a variable array*/  \r\n");
      out.write("                     columnSet.push(key);  \r\n");
      out.write("                     headerTr$.append($('<th/>').html(key));  \r\n");
      out.write("                 }  \r\n");
      out.write("             }  \r\n");
      out.write("         }  \r\n");
      out.write("     }  \r\n");
      out.write("\t headerTr$.append('<th>Resultado</th>');\r\n");
      out.write("     $(tableid).append(headerTr$);  \r\n");
      out.write("     return columnSet;  \r\n");
      out.write(" }  \r\n");
      out.write(" \r\n");
      out.write("function ExportToTable() { \r\n");
      out.write("\r\n");
      out.write("document.getElementById(\"exceltable\").innerHTML = '<table id=\"exceltable\"></table> ';\r\n");
      out.write("\r\n");
      out.write("var regex = /^([a-zA-Z0-9\\s_\\\\.\\-:])+(.xlsx|.xls)$/;  \r\n");
      out.write("     /*Checks whether the file is a valid excel file*/  \r\n");
      out.write("     if (regex.test($(\"#excelfile\").val().toLowerCase())) {  \r\n");
      out.write("         var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/  \r\n");
      out.write("         if ($(\"#excelfile\").val().toLowerCase().indexOf(\".xlsx\") > 0) {  \r\n");
      out.write("             xlsxflag = true;  \r\n");
      out.write("         }  \r\n");
      out.write("         /*Checks whether the browser supports HTML5*/  \r\n");
      out.write("         if (typeof (FileReader) != \"undefined\") {  \r\n");
      out.write("             var reader = new FileReader();  \r\n");
      out.write("             reader.onload = function (e) {  \r\n");
      out.write("                 var data = e.target.result;  \r\n");
      out.write("                 /*Converts the excel data in to object*/  \r\n");
      out.write("                 if (xlsxflag) {  \r\n");
      out.write("                     var workbook = XLSX.read(data, { type: 'binary' });  \r\n");
      out.write("                 }  \r\n");
      out.write("                 else {  \r\n");
      out.write("                     var workbook = XLS.read(data, { type: 'binary' });  \r\n");
      out.write("                 }  \r\n");
      out.write("                 /*Gets all the sheetnames of excel in to a variable*/  \r\n");
      out.write("                 var sheet_name_list = workbook.SheetNames;  \r\n");
      out.write("  \r\n");
      out.write("                 var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/  \r\n");
      out.write("                 sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/  \r\n");
      out.write("                     /*Convert the cell value to Json*/  \r\n");
      out.write("                     if (xlsxflag) {  \r\n");
      out.write("                         var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);  \r\n");
      out.write("                     }  \r\n");
      out.write("                     else {  \r\n");
      out.write("                         var exceljson = XLS.utils.sheet_to_row_object_array(workbook.Sheets[y]);  \r\n");
      out.write("                     }  \r\n");
      out.write("                     if (exceljson.length > 0 && cnt == 0) {  \r\n");
      out.write("                         BindTable(exceljson, '#exceltable');  \r\n");
      out.write("                         cnt++;  \r\n");
      out.write("                     }  \r\n");
      out.write("                 });  \r\n");
      out.write("                 $('#exceltable').show();  \r\n");
      out.write("             }  \r\n");
      out.write("             if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/  \r\n");
      out.write("                 reader.readAsArrayBuffer($(\"#excelfile\")[0].files[0]);  \r\n");
      out.write("             }  \r\n");
      out.write("             else {  \r\n");
      out.write("                 reader.readAsBinaryString($(\"#excelfile\")[0].files[0]);  \r\n");
      out.write("             }  \r\n");
      out.write("         }  \r\n");
      out.write("         else {  \r\n");
      out.write("             alertMaterialize(\"Error! Su explorador no soporta HTML5!\");  \r\n");
      out.write("         }  \r\n");
      out.write("     }  \r\n");
      out.write("     else {  \r\n");
      out.write("         alertMaterialize(\"Por favor seleccione un archivo de Excel valido!\");  \r\n");
      out.write("     }  \r\n");
      out.write("} \r\n");
      out.write("\r\n");
      out.write("function cambiaBienesEspecialesFile() {\r\n");
      out.write("\t  var x = document.getElementById(\"mdBienEspecial2\").value;\r\n");
      out.write("\t  \r\n");
      out.write("\t  if(x=='1'){\r\n");
      out.write("\t\t  document.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: '\r\n");
      out.write("\t\t    + '<p><b>Tipo Identificador</b>, 1 si es Placa y 2 si es VIN<p>'\r\n");
      out.write("\t\t    + '<p><b>Identificador</b>, maximo 25 caracteres</p>'\r\n");
      out.write("\t\t\t+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';\r\n");
      out.write("\t\t  \r\n");
      out.write("\t  } else if (x=='2'){\r\n");
      out.write("\t\t  document.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: '\r\n");
      out.write("\t\t    + '<p><b>Numero Identificacion Contribuyente</b>, maximo 25 caracteres</p>'\r\n");
      out.write("\t\t\t+ '<p><b>Fecha</b>, formato texto DD/MM/YYYY</p>'\r\n");
      out.write("\t\t\t+ '<p><b>Numero Factura</b>, maximo 25 caracteres</p>'\r\n");
      out.write("\t\t\t+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';\r\n");
      out.write("\t  } else if (x=='3'){\r\n");
      out.write("\t\t  document.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: '\r\n");
      out.write("\t\t    + '<p><b>Identificador</b>, maximo 25 caracteres</p>'\r\n");
      out.write("\t\t\t+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';\r\n");
      out.write("\t  }\r\n");
      out.write("\t  \r\n");
      out.write("\t  \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function limpiaCamposFile() {\t  \r\n");
      out.write("\t  document.getElementById(\"mdBienEspecial2\").value  = '0';\r\n");
      out.write("\t  document.getElementById(\"txtspan\").innerHTML = '';\r\n");
      out.write("\t  \r\n");
      out.write("\t  document.getElementById(\"exceltable\").innerHTML = '<table id=\"exceltable\"></table> ';\t  \r\n");
      out.write("\t  var input = $(\"#excelfile\");\r\n");
      out.write("\t  var name = $(\"#namefile\");\r\n");
      out.write("\t  input.replaceWith(input.val('').clone(true));\r\n");
      out.write("\t  name.replaceWith(name.val('').clone(true));\r\n");
      out.write("\t  \r\n");
      out.write("\t  Materialize.updateTextFields();\r\n");
      out.write("} \r\n");
      out.write(" \r\n");
      out.write("</script>\r\n");
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
    _jspx_th_s_property_0.setValue("%{textosFormulario.get(0)}");
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
    _jspx_th_s_property_1.setValue("idTramite");
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
    _jspx_th_s_property_2.setValue("idTipoGarantia");
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
    _jspx_th_s_property_3.setValue("%{textosFormulario.get(1)}");
    int _jspx_eval_s_property_3 = _jspx_th_s_property_3.doStartTag();
    if (_jspx_th_s_property_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
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
    _jspx_th_s_property_4.setValue("%{textosFormulario.get(2)}");
    int _jspx_eval_s_property_4 = _jspx_th_s_property_4.doStartTag();
    if (_jspx_th_s_property_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
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
    _jspx_th_s_if_0.setTest("hayOtorgantes");
    int _jspx_eval_s_if_0 = _jspx_th_s_if_0.doStartTag();
    if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t<div class=\"row note_tabs light-blue darken-4\">\r\n");
        out.write("\t\t\t\t\t\t\t<span class=\"white-text\">");
        if (_jspx_meth_s_property_5((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_if_0, _jspx_page_context))
          return true;
        out.write("</span>\r\n");
        out.write("\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
        out.write("\t\t\t\t\t\t\t<div id =\"divParteDWRxx4\"></div>\r\n");
        out.write("\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t");
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

  private boolean _jspx_meth_s_property_5(javax.servlet.jsp.tagext.JspTag _jspx_th_s_if_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_5 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_5.setPageContext(_jspx_page_context);
    _jspx_th_s_property_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_if_0);
    _jspx_th_s_property_5.setValue("%{textosFormulario.get(3)}");
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
    _jspx_th_s_property_6.setValue("%{textosFormulario.get(4)}");
    int _jspx_eval_s_property_6 = _jspx_th_s_property_6.doStartTag();
    if (_jspx_th_s_property_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
    return false;
  }

  private boolean _jspx_meth_s_textarea_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_0 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_0.setParent(null);
    _jspx_th_s_textarea_0.setName("moddescripcion");
    _jspx_th_s_textarea_0.setCols("70");
    _jspx_th_s_textarea_0.setRows("10");
    _jspx_th_s_textarea_0.setId("tiposbienes");
    _jspx_th_s_textarea_0.setValue("%{moddescripcion}");
    int _jspx_eval_s_textarea_0 = _jspx_th_s_textarea_0.doStartTag();
    if (_jspx_th_s_textarea_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_0);
      return true;
    }
    _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_0);
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
    _jspx_th_s_property_7.setValue("%{textosFormulario.get(5)}");
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
    _jspx_th_s_property_8.setValue("%{textosFormulario.get(6)}");
    int _jspx_eval_s_property_8 = _jspx_th_s_property_8.doStartTag();
    if (_jspx_th_s_property_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_8);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_8);
    return false;
  }

  private boolean _jspx_meth_s_textarea_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_1 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_1.setParent(null);
    _jspx_th_s_textarea_1.setRows("10");
    _jspx_th_s_textarea_1.setCols("80");
    _jspx_th_s_textarea_1.setId("instrumento");
    _jspx_th_s_textarea_1.setName("instrumento");
    _jspx_th_s_textarea_1.setValue("%{instrumento}");
    _jspx_th_s_textarea_1.setDynamicAttribute(null, "maxlength", new String("3500"));
    int _jspx_eval_s_textarea_1 = _jspx_th_s_textarea_1.doStartTag();
    if (_jspx_th_s_textarea_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_1);
      return true;
    }
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_1);
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
    _jspx_th_s_property_9.setValue("%{textosFormulario.get(7)}");
    int _jspx_eval_s_property_9 = _jspx_th_s_property_9.doStartTag();
    if (_jspx_th_s_property_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_9);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_9);
    return false;
  }

  private boolean _jspx_meth_s_textarea_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_2 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_2.setParent(null);
    _jspx_th_s_textarea_2.setId("modotrosgarantia");
    _jspx_th_s_textarea_2.setName("modotrosgarantia");
    _jspx_th_s_textarea_2.setCols("80");
    _jspx_th_s_textarea_2.setRows("10");
    _jspx_th_s_textarea_2.setDynamicAttribute(null, "maxlength", new String("3500"));
    _jspx_th_s_textarea_2.setValue("%{modotrosgarantia}");
    int _jspx_eval_s_textarea_2 = _jspx_th_s_textarea_2.doStartTag();
    if (_jspx_th_s_textarea_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_2);
      return true;
    }
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_2);
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
    _jspx_th_s_property_10.setValue("%{textosFormulario.get(8)}");
    int _jspx_eval_s_property_10 = _jspx_th_s_property_10.doStartTag();
    if (_jspx_th_s_property_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_10);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_10);
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
    _jspx_th_s_if_1.setTest("aBoolean");
    int _jspx_eval_s_if_1 = _jspx_th_s_if_1.doStartTag();
    if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_1.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aBoolean').checked = 1;\r\n");
        out.write("\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t");
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
    _jspx_th_s_if_2.setTest("aMonto");
    int _jspx_eval_s_if_2 = _jspx_th_s_if_2.doStartTag();
    if (_jspx_eval_s_if_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_2.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aMonto').checked = 1;\r\n");
        out.write("\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t");
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
    _jspx_th_s_if_3.setTest("aPrioridad");
    int _jspx_eval_s_if_3 = _jspx_th_s_if_3.doStartTag();
    if (_jspx_eval_s_if_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_3.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aPrioridad').checked = 1;\r\n");
        out.write("\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t");
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

  private boolean _jspx_meth_s_if_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_4 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_if_4.setPageContext(_jspx_page_context);
    _jspx_th_s_if_4.setParent(null);
    _jspx_th_s_if_4.setTest("aRegistro");
    int _jspx_eval_s_if_4 = _jspx_th_s_if_4.doStartTag();
    if (_jspx_eval_s_if_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_4.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_4.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aRegistro').checked = 1;\r\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById(\"txtregistro\").disabled = false;\r\n");
        out.write("\t\t\t\t\t\t\tMaterialize.updateTextFields();\r\n");
        out.write("\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_if_4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_if_4 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_if_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_4);
      return true;
    }
    _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_4);
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

  private boolean _jspx_meth_s_property_12(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_12 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_12.setPageContext(_jspx_page_context);
    _jspx_th_s_property_12.setParent(null);
    _jspx_th_s_property_12.setValue("idTramite");
    int _jspx_eval_s_property_12 = _jspx_th_s_property_12.doStartTag();
    if (_jspx_th_s_property_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_12);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_12);
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
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_0 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_0.setParent(null);
    _jspx_th_s_textfield_0.setName("mdFactura1");
    _jspx_th_s_textfield_0.setId("mdFactura1");
    _jspx_th_s_textfield_0.setCssClass("validate");
    _jspx_th_s_textfield_0.setMaxlength("150");
    _jspx_th_s_textfield_0.setOnkeypress("return characterNotAllowed(event);");
    int _jspx_eval_s_textfield_0 = _jspx_th_s_textfield_0.doStartTag();
    if (_jspx_th_s_textfield_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_0);
      return true;
    }
    _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_0);
    return false;
  }

  private boolean _jspx_meth_s_textarea_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_3 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_name_maxlength_id_data$1length_cssClass_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_3.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_3.setParent(null);
    _jspx_th_s_textarea_3.setRows("10");
    _jspx_th_s_textarea_3.setId("mdDescripcion");
    _jspx_th_s_textarea_3.setCols("80");
    _jspx_th_s_textarea_3.setName("mdDescripcion2");
    _jspx_th_s_textarea_3.setDynamicAttribute(null, "data-length", new String("700"));
    _jspx_th_s_textarea_3.setCssClass("materialize-textarea");
    _jspx_th_s_textarea_3.setDynamicAttribute(null, "maxlength", new String("700"));
    int _jspx_eval_s_textarea_3 = _jspx_th_s_textarea_3.doStartTag();
    if (_jspx_th_s_textarea_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_name_maxlength_id_data$1length_cssClass_cols_nobody.reuse(_jspx_th_s_textarea_3);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_data$1length_cssClass_cols_nobody.reuse(_jspx_th_s_textarea_3);
    return false;
  }

  private boolean _jspx_meth_s_textfield_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_1 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_1.setParent(null);
    _jspx_th_s_textfield_1.setName("mdIdentificador3");
    _jspx_th_s_textfield_1.setId("mdIdentificador3");
    _jspx_th_s_textfield_1.setCssClass("validate");
    _jspx_th_s_textfield_1.setMaxlength("150");
    _jspx_th_s_textfield_1.setOnkeypress("return characterNotAllowed(event);");
    int _jspx_eval_s_textfield_1 = _jspx_th_s_textfield_1.doStartTag();
    if (_jspx_th_s_textfield_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
      return true;
    }
    _jspx_tagPool_s_textfield_onkeypress_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
    return false;
  }

  private boolean _jspx_meth_s_textfield_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_2 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_2.setParent(null);
    _jspx_th_s_textfield_2.setName("mdIdentificador2");
    _jspx_th_s_textfield_2.setId("mdIdentificador2");
    _jspx_th_s_textfield_2.setCssClass("validate");
    _jspx_th_s_textfield_2.setMaxlength("150");
    int _jspx_eval_s_textfield_2 = _jspx_th_s_textfield_2.doStartTag();
    if (_jspx_th_s_textfield_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_2);
      return true;
    }
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_2);
    return false;
  }

  private boolean _jspx_meth_s_select_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:select
    org.apache.struts2.views.jsp.ui.SelectTag _jspx_th_s_select_1 = (org.apache.struts2.views.jsp.ui.SelectTag) _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody.get(org.apache.struts2.views.jsp.ui.SelectTag.class);
    _jspx_th_s_select_1.setPageContext(_jspx_page_context);
    _jspx_th_s_select_1.setParent(null);
    _jspx_th_s_select_1.setName("mdIdentificador");
    _jspx_th_s_select_1.setId("mdIdentificador");
    _jspx_th_s_select_1.setList("listaUsos");
    _jspx_th_s_select_1.setListKey("idTipo");
    _jspx_th_s_select_1.setListValue("desTipo");
    int _jspx_eval_s_select_1 = _jspx_th_s_select_1.doStartTag();
    if (_jspx_th_s_select_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_1);
      return true;
    }
    _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_1);
    return false;
  }

  private boolean _jspx_meth_s_textfield_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_3 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_3.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_3.setParent(null);
    _jspx_th_s_textfield_3.setName("mdIdentificador1");
    _jspx_th_s_textfield_3.setId("mdIdentificador1");
    _jspx_th_s_textfield_3.setCssClass("validate");
    _jspx_th_s_textfield_3.setMaxlength("150");
    int _jspx_eval_s_textfield_3 = _jspx_th_s_textfield_3.doStartTag();
    if (_jspx_th_s_textfield_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_3);
      return true;
    }
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_3);
    return false;
  }

  private boolean _jspx_meth_s_select_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:select
    org.apache.struts2.views.jsp.ui.SelectTag _jspx_th_s_select_2 = (org.apache.struts2.views.jsp.ui.SelectTag) _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.get(org.apache.struts2.views.jsp.ui.SelectTag.class);
    _jspx_th_s_select_2.setPageContext(_jspx_page_context);
    _jspx_th_s_select_2.setParent(null);
    _jspx_th_s_select_2.setName("mdBienEspecial2");
    _jspx_th_s_select_2.setId("mdBienEspecial2");
    _jspx_th_s_select_2.setList("listaBienEspecial");
    _jspx_th_s_select_2.setListKey("idTipo");
    _jspx_th_s_select_2.setListValue("desTipo");
    _jspx_th_s_select_2.setOnchange("cambiaBienesEspecialesFile()");
    int _jspx_eval_s_select_2 = _jspx_th_s_select_2.doStartTag();
    if (_jspx_th_s_select_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_2);
      return true;
    }
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_2);
    return false;
  }
}
