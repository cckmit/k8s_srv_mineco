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

public final class FormularioUnico_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/WEB-INF/jsp/Layout/menu/applicationCtx.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_value_name_id_disabled_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_name_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_value_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_if_test;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_textarea_value_name_id_disabled_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_value_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_textarea_value_name_id_disabled_nobody.release();
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.release();
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.release();
    _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody.release();
    _jspx_tagPool_s_textfield_value_name_id_nobody.release();
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	ApplicationContext ctx = null;
	if (ctx == null) {
		ctx = new ClassPathXmlApplicationContext(Constants.SEGURIDAD_APP_CONTEXT);
	}

      out.write("\n");
      out.write("\n");
      out.write("<main>\n");

//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();


      out.write("\n");
      out.write("<input type=\"hidden\" name=\"tipoBienAll\" value=\"false\" id=\"idTipoBienAll\" />\n");
      out.write("<div class=\"container-fluid\">\t\n");
      out.write("\t<div id=\"modifica\" class=\"row\">\n");
      out.write("\t\t<div class=\"col s12\"><div class=\"card\">\n");
      out.write("\t\t\t<div class=\"col s2\"></div>\n");
      out.write("\t\t\t<div class=\"col s8\">\n");
      out.write("\t\t\t\t<form id=\"faformulario\" name=\"faformulario\" action=\"saveformulario.do\" method=\"post\">\n");
      out.write("\t\t\t\t\t<span class=\"card-title\">Formulario &uacute;nico por Inscripci&oacute;n especial de traslado</span>\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"refInscripcion\" id=\"refInscripcion\" value=\"");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("\" />\n");
      out.write("\t\t\t\t\t<div class=\"row note\">\n");
      out.write("\t\t\t\t\t\t<p>\n");
      out.write("\t\t\t\t\t\t\t<span>Este formulario se completar&aacute; por el usuario que tenga un derecho de prenda o \n");
      out.write("\t\t\t\t\t\t\t      garant&iacute;a mobiliaria sobre veh&iacute;culo (s) inscrito en el Registro General de la Propiedad \n");
      out.write("\t\t\t\t\t\t\t      o en el Segundo Registro de la Propiedad y que en \n");
      out.write("\t\t\t\t\t\t\t      virtud de lo establecido en el art&iacute;culo 26 del Decreto 4-2018 \n");
      out.write("\t\t\t\t\t\t\t      (Reformas a la Ley de Garant&iacute;as Mobiliarias) debe inscribirse en el Registro \n");
      out.write("\t\t\t\t\t\t\t      de Garant&iacute;as Mobiliarias. </span>\n");
      out.write("\t\t\t\t\t\t</p>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"row note teal\">\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">I. Deudor(es) \\ Deudor(es) Garantes</span>\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t\t<div class=\"row\">\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t<div id =\"divParteDWRxx2\"></div>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"row note teal\">\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">II. Acreedor (es) \\Acreedor (es) Garantizado (s)</span>\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t\t<div class=\"row\">\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t<div id=\"divParteDWRxx3\"></div>\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t\t<div class=\"row note teal\">\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">III. Datos de la prenda \\ Garant&iacute;a\n");
      out.write("\t\t\t\t\t\t\tMobiliaria</span>\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t<span class=\"blue-text text-darken-2\">Bienes en garant&iacute;a si estos tienen n&uacute;mero de serie:</span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t<div class=\"col s12 right-align\">\n");
      out.write("\t\t\t\t\t\t\t<a class=\"btn-floating btn-large waves-effect indigo modal-trigger\" onclick=\"limpiaCampos()\"\n");
      out.write("\t\t\t\t\t\t\t\thref=\"#frmBien\" id=\"btnAgregar\"><i\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"material-icons left\">add</i></a>\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t<a class=\"btn-floating btn-large waves-effect indigo modal-trigger\" onclick=\"limpiaCamposFile()\"\n");
      out.write("\t\t\t\t\t\t\t\thref=\"#frmFile\" id=\"btnFile\"><i\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"material-icons left\">attach_file</i></a>\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t<div id=\"divParteDWRBienes\"></div>\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t \t<div class=\"row\">\t\t\t\t\t\t\t    \t\n");
      out.write("\t\t\t    \t\t<p>\t\t\t\t\t\t\t    \t\t\n");
      out.write("\t\t\t    \t\t\t<input type=\"checkbox\" name=\"aRegistro\" id=\"aRegistro\" value=\"true\" onclick=\"otroRegistro()\"/>\n");
      out.write("\t\t\t        \t\t<label for=\"aRegistro\">El bien se encuentra en otro registro</label>\n");
      out.write("\t\t\t        \t</p>\n");
      out.write("\t\t\t        </div>\n");
      out.write("\t\t\t        <div class=\"row\">\t\t\t\t\t\t   \t\t\n");
      out.write("\t\t\t\t   \t\t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t    \t\t");
      if (_jspx_meth_s_textarea_0(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t    \t \t\t\t\t\t\t\t\t        \t\n");
      out.write("\t\t\t\t        \t<label for=\"txtregistro\">Especifique cual</label>\n");
      out.write("\t\t\t\t   \t\t</div>\n");
      out.write("\t\t\t\t \t</div>\n");
      out.write("\t\t\t\t \t<div class=\"row\">\t\t\t\t\t\t   \t\t\n");
      out.write("\t\t\t\t   \t\t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t    \t\t");
      if (_jspx_meth_s_textfield_0(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t    \t \t\t\t\t\t\t\t\t        \t\n");
      out.write("\t\t\t\t        \t<label for=\"txtfregistro\">Fecha de Inscripci&oacute;n</label>\n");
      out.write("\t\t\t\t   \t\t</div>\n");
      out.write("\t\t\t\t \t</div>\n");
      out.write("\t\t\t\t \t<div class=\"row note\">\t\t\t\t\t\t   \t\t\n");
      out.write("\t\t\t\t   \t\t<span> Ejemplo. Inscrito en Registro General de La Propiedad<br />\n");
      out.write("\t\t\t\t   \t\t\t\t\t\tN&uacute;mero: 01 \tFolio: 01  Libro: 101<br />\n");
      out.write("\t\t\t\t   \t\t\t\t\t\tInscrita el 21 de enero de 2018\n");
      out.write("\t\t\t\t   \t\t</span>\n");
      out.write("\t\t\t\t \t</div>\n");
      out.write("\t\t\t\t \t<div class=\"row note teal\">\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">IV. Contrato de financiamiento con garant&iacute;a prendaria o garant&iacute;a mobiliaria</span>\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t \t\t\t\t\t\t\t\t\t \t\n");
      out.write("\t\t\t\t \t<div class=\"row\">\n");
      out.write("\t\t\t\t    \t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t        \t");
      if (_jspx_meth_s_textarea_1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t        \t<label for=\"instrumento\">Datos generales del instrumento p&uacute;blico que documenta la constituci&oacute;n de la prenda o garant&iacute;a mobiliaria</label>\n");
      out.write("\t\t\t\t   \t\t</div>\n");
      out.write("\t\t\t\t \t</div>\t\t\t\t \t\n");
      out.write("\t\t\t\t \t<div class=\"row\">\n");
      out.write("\t\t\t\t    \t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t    \t\t");
      if (_jspx_meth_s_textarea_2(_jspx_page_context))
        return;
      out.write("\t\t\t\t        \t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t        \t<label for=\"modotrosgarantia\">Observaciones Adicionales de la Garant&iacute;a</label>\n");
      out.write("\t\t\t\t   \t\t</div>\n");
      out.write("\t\t\t\t \t</div>\t\t\t\t \t\n");
      out.write("\t\t\t\t \t<div class=\"row note teal\">\t\t\t\t\t\t\t    \t\n");
      out.write("\t\t\t        \t<span class=\"white-text\">\n");
      out.write("\t\t\t        \t\tV. Plazo de vigencia de la inscripci&oacute;n en el Registro de Garant&iacute;as Mobiliarias\n");
      out.write("\t\t\t\t\t\t</span>\t\t\t\t\t\t\t   \t\t\n");
      out.write("\t\t\t\t \t</div>\n");
      out.write("\t\t\t\t \t<div class=\"row note\">\n");
      out.write("\t\t\t\t    \t<span>\n");
      out.write("\t\t\t\t    \tAdvertencia: El acreedor garantizado tiene derecho de prelaci&oacute;n sobre el bien objeto de prenda \n");
      out.write("\t\t\t\t    \to garant&iacute;a mobiliaria de conformidad con la inscripciï¿½n original que se oper&oacute; en el Registro de \n");
      out.write("\t\t\t\t    \tla Propiedad. La presente inscripci&oacute;n publicita a terceros en virtud del art. 26 del Decreto 4-2018  \n");
      out.write("\t\t\t\t    \ta trav&eacute;s del sistema electr&oacute;nico por el que opera el Registro de Garant&iacute;as Mobiliarias la afectaci&oacute;n \n");
      out.write("\t\t\t\t    \tsobre el veh&acute;culo en garantï¿½a. \n");
      out.write("\t\t\t\t\t\tToda modificaci&oacute;n a esta garant&iacute;a real deber&aacute; operarse en el Registro de Garant&iacute;as Mobiliarias. \t\n");
      out.write("\t\t\t\t    \t</span>\n");
      out.write("\t\t\t\t \t</div>\t\t\t\t \t\n");
      out.write("\t\t\t\t    <hr />\n");
      out.write("\t\t\t\t \t<center>\n");
      out.write("\t\t\t            <div class='row'>\t\t\t            \t\n");
      out.write("\t\t\t            \t<input type=\"button\" id=\"bFirmar\" name=\"button\" class=\"btn btn-large waves-effect indigo\" value=\"Aceptar\" onclick=\"inscripcionFormulario();\"/>\t\t\t            \t\t\t\t            \t\t\t\t\t\t\t            \t\n");
      out.write("\t\t\t            </div>\n");
      out.write("\t\t          \t</center>\t\t\t\t\t\t\n");
      out.write("\t\t\t\t</form>\t\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_0(_jspx_page_context))
        return;
      out.write("\t\t\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_2(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_3(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div></div>\t\n");
      out.write("\t</div>\n");
      out.write("</div>\n");
      out.write("<script type=\"text/javascript\"> \t\n");
      out.write("\t\t\t\t\tvar idPersona = ");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write(";\n");
      out.write("\t\t\t\t\tvar idTramite= ");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write(";\n");
      out.write("\t\t\n");
      out.write("\t\t\t\t\tcargaParteDeudor('divParteDWRxx2',idTramite, idPersona,'0','1');\n");
      out.write("\t\t\t\t\tcargaParteAcreedor('divParteDWRxx3',idTramite, idPersona,'0','1');\n");
      out.write("\t\t\t\t\tcargaParteBienes('divParteDWRBienes',idTramite);\n");
      out.write("\n");
      out.write("\t\t\t\t\t//escondePartes();\t\n");
      out.write("\t\t\t\t</script> \t\t\t\t\n");
      out.write("</main>\n");
      out.write("<div id=\"frmBien\" class=\"modal\">\n");
      out.write("\t<div class=\"modal-content\">\n");
      out.write("\t\t<div class=\"card\">\n");
      out.write("\t\t\t<div class=\"card-content\">\t\t\t\t\t\t\n");
      out.write("\t\t\t\t<span class=\"card-title\">Bien Especial</span>\n");
      out.write("\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\n");
      out.write("\t\t\t\t\t<div class=\"col s10\">\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_0(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t    \n");
      out.write("\t\t\t\t\t\t    \t<label>Tipo Bien Especial</label>\n");
      out.write("\t\t\t\t\t\t  \t</div>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<div id=\"secId4\" class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdFactura1\" for=\"mdFactura1\">Emitido por:</label>\n");
      out.write("\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"mdFactura2\" class=\"datepicker\" id=\"mdFactura2\" />\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdFactura2\" for=\"mdFactura2\">Fecha: </label>\n");
      out.write("\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textarea_3(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t    \t \t\t\t\t\t\t\t\t        \t\n");
      out.write("\t\t\t\t        \t\t<label id=\"lblMdDescripcion\" for=\"mdDescripcion\">Descripci&oacute;n del bien</label>\n");
      out.write("\t\t\t\t        \t</div>\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t<div id=\"secId1\"class=\"row\" style=\"display: none;\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s3\">\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador\">Placa</label>\n");
      out.write("\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s3\">\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_1(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_2(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<div id=\"secId2\" class=\"row\" style=\"display: none;\"><span class=\"col s12 center-align\">Y</span></div>\n");
      out.write("\t\t\t\t\t\t<div id=\"secId3\" class=\"row\" style=\"display: none;\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_3(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador2\" for=\"mdIdentificador2\">VIN</label>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t<br />\n");
      out.write("\t\t\t\t\t\t<hr />\n");
      out.write("\t\t\t\t\t\t<center>\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId5\" class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\"\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"btn-large indigo\"\n");
      out.write("\t\t\t\t\t\t\t\t\tonclick=\"add_bien();\">Aceptar</a>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId6\" class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\" id=\"formBienButton\"\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"btn-large indigo\"\n");
      out.write("\t\t\t\t\t\t\t\t\tonclick=\"add_bien();\">Aceptar</a>\n");
      out.write("\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t</center>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<div id=\"frmFile\" class=\"modal\">\n");
      out.write("\t<div class=\"modal-content\">\n");
      out.write("\t\t<div class=\"card\">\n");
      out.write("\t\t\t<div class=\"card-content\">\t\t\t\t\t\t\n");
      out.write("\t\t\t\t<span class=\"card-title\">Bien Especial</span>\n");
      out.write("\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\n");
      out.write("\t\t\t\t\t<div class=\"col s10\">\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_2(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t    \n");
      out.write("\t\t\t\t\t\t    \t<label>Tipo Bien Especial</label>\n");
      out.write("\t\t\t\t\t\t  \t</div>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t<div id=\"secTxt3\" class=\"row note\">\n");
      out.write("\t\t\t\t\t\t\t<span id=\"txtspan\" name=\"txtspan\"></span>\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t</div>\t\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s8\">\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"file-field input-field\">\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"btn\">\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span>Archivo</span>\n");
      out.write("\t\t\t\t\t\t\t\t\t\t <input type=\"file\" name=\"excelfile\" id=\"excelfile\" />\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"file-path-wrapper\">\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"file-path validate\" type=\"text\" name=\"namefile\" id=\"namefile\"\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tplaceholder=\"Seleccione...\">\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s4\">\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\"\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"btn-large indigo\"\n");
      out.write("\t\t\t\t\t\t\t\t\tonclick=\"ExportToTable();\">Cargar</a>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t<table id=\"exceltable\">  \n");
      out.write("\t\t\t\t\t\t\t</table> \n");
      out.write("\t\t\t\t\t\t</div>\t\n");
      out.write("\t\t\t\t\t\t<br />\n");
      out.write("\t\t\t\t\t\t<hr />\n");
      out.write("\t\t\t\t\t\t<center>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\"\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"modal-close btn-large indigo\">Aceptar</a>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t</center>\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-3.3.1.min.js\"></script>\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/xlsx.core.min.js\"></script>\n");
      out.write("<script type=\"text/javascript\"> \n");
      out.write("function BindTable(jsondata, tableid) {/*Function used to convert the JSON array to Html Table*/  \n");
      out.write("     var columns = BindTableHeader(jsondata, tableid); /*Gets all the column headings of Excel*/  \n");
      out.write("\t var idTramite = document.getElementById(\"refInscripcion\").value;\n");
      out.write("\t var mdDescripcion = '';\n");
      out.write("\t var idTipo = document.getElementById(\"mdBienEspecial2\").value;\n");
      out.write("\t var mdIdentificador = '';\n");
      out.write("\t var mdIdentificador1 = '';\n");
      out.write("\t var mdIdentificador2 = '';\n");
      out.write("\t var tipoId = '';\n");
      out.write("\t var correcto = 0;\n");
      out.write("\t var limite = 50;\n");
      out.write("\t \n");
      out.write("\t if(jsondata.length > limite.valueOf()){\n");
      out.write("\t\t alertMaterialize('Error!. Solo se pueden cargar ' + limite + ' registros');\n");
      out.write("\t\t return false;\n");
      out.write("\t }\n");
      out.write("\t \n");
      out.write("\t if(idTipo == '0'){\n");
      out.write("\t\t return false;\n");
      out.write("\t }\n");
      out.write("\t \n");
      out.write("     for (var i = 0; i < jsondata.length; i++) {  \n");
      out.write("         var row$ = $('<tr/>');  \n");
      out.write("\t\t mdDescripcion = '';\n");
      out.write("\t\t mdIdentificador = '';\n");
      out.write("\t\t mdIdentificador1 = '';\n");
      out.write("\t     mdIdentificador2 = '';\n");
      out.write("\t\t correcto = 0;\n");
      out.write("\t\t tipoId = '';\n");
      out.write("\t\t \n");
      out.write("         for (var colIndex = 0; colIndex < columns.length; colIndex++) {  \n");
      out.write("             var cellValue = jsondata[i][columns[colIndex]];  \n");
      out.write("             if (cellValue == null)  \n");
      out.write("                 cellValue = \"\";  \n");
      out.write("\t\t\t if(idTipo == '1') {\n");
      out.write("\t\t\t\t if(colIndex == 0) {\n");
      out.write("\t\t\t\t\t tipoId = cellValue;\n");
      out.write("\t\t\t\t\t if(cellValue == '1'){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Placa';\n");
      out.write("\t\t\t\t\t } else if(cellValue == '2'){\n");
      out.write("\t\t\t\t\t\tcellValue = 'VIN';\n");
      out.write("\t\t\t\t\t } else {\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t }\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex == 1) {\t\n");
      out.write("\t\t\t\t\tif(cellValue.length > 25){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\tif(tipoId == '1'){\n");
      out.write("\t\t\t\t\t\t\tif(cellValue.includes(\"-\")){\n");
      out.write("\t\t\t\t\t\t\t\tvar str = cellValue.split(\"-\");\n");
      out.write("\t\t\t\t\t\t\t\tmdIdentificador = str[0];\n");
      out.write("\t\t\t\t\t\t\t\tmdIdentificador1 = str[1];\n");
      out.write("\t\t\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\t\tmdIdentificador2 = cellValue;\n");
      out.write("\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex == 2) {\n");
      out.write("\t\t\t\t\t if(cellValue.length > 100){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\tmdDescripcion = cellValue;\n");
      out.write("\t\t\t\t\t}\t\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex > 2) {\n");
      out.write("\t\t\t\t\t correcto = 1;\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t } else if(idTipo == '2') { //Facturas\t\t\t\t \n");
      out.write("\t\t\t\t if(colIndex == 0) {\t\n");
      out.write("\t\t\t\t\tif(cellValue.length > 25){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t} else {\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\tmdIdentificador2 = cellValue;\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex == 1) {\n");
      out.write("\t\t\t\t\tvar patt = /^(0?[1-9]|[12][0-9]|3[01])[\\/](0?[1-9]|1[012])[\\/]\\d{4}$/;\n");
      out.write("\t\t\t\t\tif(!patt.test(cellValue)){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\tmdDescripcion = cellValue;\n");
      out.write("\t\t\t\t\t}\t\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex == 2) {\n");
      out.write("\t\t\t\t\t if(cellValue.length > 25){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\tmdDescripcion = cellValue;\n");
      out.write("\t\t\t\t\t}\t\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex == 3) {\n");
      out.write("\t\t\t\t\t if(cellValue.length > 100){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\tmdDescripcion = cellValue;\n");
      out.write("\t\t\t\t\t}\t\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex > 3) {\n");
      out.write("\t\t\t\t\t correcto = 1;\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t } else if(idTipo == '3') { \n");
      out.write("\t\t\t\t if(colIndex == 0) {\t\n");
      out.write("\t\t\t\t\tif(cellValue.length > 25){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\tmdIdentificador2 = cellValue;\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex == 1) {\n");
      out.write("\t\t\t\t\tif(cellValue.length > 100){\n");
      out.write("\t\t\t\t\t\tcellValue = 'Valor invalido';\n");
      out.write("\t\t\t\t\t\tcorrecto = 1;\n");
      out.write("\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\tmdDescripcion = cellValue;\n");
      out.write("\t\t\t\t\t}\t\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t\t if(colIndex > 1) {\n");
      out.write("\t\t\t\t\t correcto = 1;\n");
      out.write("\t\t\t\t }\n");
      out.write("\t\t\t }\t \n");
      out.write("             row$.append($('<td/>').html(cellValue));  \n");
      out.write("         }  \n");
      out.write("\t\t if(correcto == 0) {\n");
      out.write("\t\t\tParteDwrAction.registrarBien('divParteDWRBienes',idTramite, mdDescripcion, idTipo, mdIdentificador, \n");
      out.write("\t\t\t\tmdIdentificador1, mdIdentificador2, showParteBienes); \n");
      out.write("\t\t\trow$.append('<td><font color=\"green\">Cargado</font></td>');\n");
      out.write("\t\t } else {\n");
      out.write("\t\t\t row$.append('<td><font color=\"red\">Error verifique datos</font></td>');\n");
      out.write("\t\t }\n");
      out.write("         $(tableid).append(row$);  \n");
      out.write("     }  \n");
      out.write(" }  \n");
      out.write("function BindTableHeader(jsondata, tableid) {/*Function used to get all column names from JSON and bind the html table header*/  \n");
      out.write("     var columnSet = [];  \n");
      out.write("     var headerTr$ = $('<tr/>');  \n");
      out.write("     for (var i = 0; i < jsondata.length; i++) {  \n");
      out.write("         var rowHash = jsondata[i];  \n");
      out.write("         for (var key in rowHash) {  \n");
      out.write("             if (rowHash.hasOwnProperty(key)) {  \n");
      out.write("                 if ($.inArray(key, columnSet) == -1) {/*Adding each unique column names to a variable array*/  \n");
      out.write("                     columnSet.push(key);  \n");
      out.write("                     headerTr$.append($('<th/>').html(key));  \n");
      out.write("                 }  \n");
      out.write("             }  \n");
      out.write("         }  \n");
      out.write("     }  \n");
      out.write("\t headerTr$.append('<th>Resultado</th>');\n");
      out.write("     $(tableid).append(headerTr$);  \n");
      out.write("     return columnSet;  \n");
      out.write(" }  \n");
      out.write("function ExportToTable() { \n");
      out.write("\n");
      out.write("document.getElementById(\"exceltable\").innerHTML = '<table id=\"exceltable\"></table> ';\n");
      out.write("\n");
      out.write("var regex = /^([a-zA-Z0-9\\s_\\\\.\\-:])+(.xlsx|.xls)$/;  \n");
      out.write("     /*Checks whether the file is a valid excel file*/  \n");
      out.write("     if (regex.test($(\"#excelfile\").val().toLowerCase())) {  \n");
      out.write("         var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/  \n");
      out.write("         if ($(\"#excelfile\").val().toLowerCase().indexOf(\".xlsx\") > 0) {  \n");
      out.write("             xlsxflag = true;  \n");
      out.write("         }  \n");
      out.write("         /*Checks whether the browser supports HTML5*/  \n");
      out.write("         if (typeof (FileReader) != \"undefined\") {  \n");
      out.write("             var reader = new FileReader();  \n");
      out.write("             reader.onload = function (e) {  \n");
      out.write("                 var data = e.target.result;  \n");
      out.write("                 /*Converts the excel data in to object*/  \n");
      out.write("                 if (xlsxflag) {  \n");
      out.write("                     var workbook = XLSX.read(data, { type: 'binary' });  \n");
      out.write("                 }  \n");
      out.write("                 else {  \n");
      out.write("                     var workbook = XLS.read(data, { type: 'binary' });  \n");
      out.write("                 }  \n");
      out.write("                 /*Gets all the sheetnames of excel in to a variable*/  \n");
      out.write("                 var sheet_name_list = workbook.SheetNames;  \n");
      out.write("  \n");
      out.write("                 var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/  \n");
      out.write("                 sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/  \n");
      out.write("                     /*Convert the cell value to Json*/  \n");
      out.write("                     if (xlsxflag) {  \n");
      out.write("                         var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);  \n");
      out.write("                     }  \n");
      out.write("                     else {  \n");
      out.write("                         var exceljson = XLS.utils.sheet_to_row_object_array(workbook.Sheets[y]);  \n");
      out.write("                     }  \n");
      out.write("                     if (exceljson.length > 0 && cnt == 0) {  \n");
      out.write("                         BindTable(exceljson, '#exceltable');  \n");
      out.write("                         cnt++;  \n");
      out.write("                     }  \n");
      out.write("                 });  \n");
      out.write("                 $('#exceltable').show();  \n");
      out.write("             }  \n");
      out.write("             if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/  \n");
      out.write("                 reader.readAsArrayBuffer($(\"#excelfile\")[0].files[0]);  \n");
      out.write("             }  \n");
      out.write("             else {  \n");
      out.write("                 reader.readAsBinaryString($(\"#excelfile\")[0].files[0]);  \n");
      out.write("             }  \n");
      out.write("         }  \n");
      out.write("         else {  \n");
      out.write("             alertMaterialize(\"Error! Su explorador no soporta HTML5!\");  \n");
      out.write("         }  \n");
      out.write("     }  \n");
      out.write("     else {  \n");
      out.write("         alertMaterialize(\"Por favor seleccione un archivo de Excel valido!\");  \n");
      out.write("     }  \n");
      out.write("}\n");
      out.write("\n");
      out.write("function add_bien() {\n");
      out.write("\t  \n");
      out.write("\t  var idTramite = document.getElementById(\"refInscripcion\").value;\n");
      out.write("\t  var mdDescripcion = document.getElementById(\"mdDescripcion\").value;\n");
      out.write("\t  var idTipo = document.getElementById(\"mdBienEspecial\").value;\n");
      out.write("\t  var mdIdentificador = document.getElementById(\"mdIdentificador\").value;\n");
      out.write("\t  var mdIdentificador1 = document.getElementById(\"mdIdentificador1\").value;\n");
      out.write("\t  var mdIdentificador2 = document.getElementById(\"mdIdentificador2\").value;\n");
      out.write("\t  \n");
      out.write("\t  if(!noVacio(mdDescripcion)){\n");
      out.write("\t\t  alertMaterialize('Debe ingresar la descripcion del bien especial');\n");
      out.write("\t\t  return false;\n");
      out.write("\t  }\n");
      out.write("\t  \n");
      out.write("\t  if(idTipo == '2'){\n");
      out.write("\t\t  mdDescripcion = 'Emitido por: ' + document.getElementById(\"mdFactura1\").value + \" Fecha: \" + document.getElementById(\"mdFactura2\").value + \" \" + mdDescripcion;\n");
      out.write("\t  }\n");
      out.write("\t  \n");
      out.write("\t  if(idTipo == '1'){\n");
      out.write("\t\t  if(!noVacio(mdIdentificador2)) {\n");
      out.write("\t\t\t  alertMaterialize('Debe ingresar el VIN del vehiculo');\n");
      out.write("\t\t\t  return false;\n");
      out.write("\t\t  }\n");
      out.write("\t  } \n");
      out.write("\t  \n");
      out.write("\t  ParteDwrAction.registrarBien('divParteDWRBienes',idTramite, mdDescripcion, idTipo, mdIdentificador, \n");
      out.write("\t\t\t  mdIdentificador1, mdIdentificador2, showParteBienes);\n");
      out.write("\t  \n");
      out.write("\t  $(document).ready(function() {\t  \n");
      out.write("\t\t\t$('#frmBien').modal('close');\n");
      out.write("\t\t});\n");
      out.write("}\n");
      out.write("\n");
      out.write("function cambiaBienesEspecialesFile() {\n");
      out.write("\t  var x = document.getElementById(\"mdBienEspecial2\").value;\n");
      out.write("\t  \n");
      out.write("\t  if(x=='1'){\n");
      out.write("\t\t  document.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: '\n");
      out.write("\t\t    + '<p><b>Tipo Identificador</b>, 1 si es Placa y 2 si es VIN<p>'\n");
      out.write("\t\t    + '<p><b>Identificador</b>, maximo 25 caracteres</p>'\n");
      out.write("\t\t\t+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';\n");
      out.write("\t\t  \n");
      out.write("\t  } else if (x=='2'){\n");
      out.write("\t\t  document.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: '\n");
      out.write("\t\t    + '<p><b>Numero Identificacion Contribuyente<b>, maximo 25 caracteres</p>'\n");
      out.write("\t\t\t+ '<p><b>Fecha</b>, formato texto DD/MM/YYYY</p>'\n");
      out.write("\t\t\t+ '<p><b>Numero Factura</b>, maximo 25 caracteres</p>'\n");
      out.write("\t\t\t+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';\n");
      out.write("\t  } else if (x=='3'){\n");
      out.write("\t\t  document.getElementById(\"txtspan\").innerHTML = 'Los campos del excel son: '\n");
      out.write("\t\t    + '<p><b>Identificador</b>, maximo 25 caracteres</p>'\n");
      out.write("\t\t\t+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';\n");
      out.write("\t  }\n");
      out.write("\t  \n");
      out.write("\t  \n");
      out.write("}\n");
      out.write("\n");
      out.write("function cambiaBienesEspeciales() {\n");
      out.write("\t  var x = document.getElementById(\"mdBienEspecial\").value;\n");
      out.write("\t  \n");
      out.write("\t  if(x=='1'){\n");
      out.write("\t\t  document.getElementById(\"mdDescripcion\").disabled = false;\t  \n");
      out.write("\t\t  document.getElementById(\"secId1\").style.display = 'block'; \n");
      out.write("\t\t  document.getElementById(\"secId2\").style.display = 'block';\n");
      out.write("\t\t  document.getElementById(\"secId3\").style.display = 'block';\n");
      out.write("\t\t  document.getElementById(\"secId4\").style.display = 'none';\n");
      out.write("\t\t  \n");
      out.write("\t\t  document.getElementById(\"lblMdDescripcion\").innerHTML = 'Descripci&oacute;n del veh&iacute;culo';\n");
      out.write("\t\t  document.getElementById(\"lblMdIdentificador2\").innerHTML = 'VIN';\n");
      out.write("\t  } else if (x=='2'){\n");
      out.write("\t\t  document.getElementById(\"mdDescripcion\").disabled = false;\t  \n");
      out.write("\t\t  document.getElementById(\"secId1\").style.display = 'none'; \n");
      out.write("\t\t  document.getElementById(\"secId2\").style.display = 'none';\n");
      out.write("\t\t  document.getElementById(\"secId3\").style.display = 'block';\t\t\n");
      out.write("\t\t  document.getElementById(\"secId4\").style.display = 'block';\n");
      out.write("\t\t  \n");
      out.write("\t\t  document.getElementById(\"lblMdIdentificador2\").innerHTML = 'No. Factura';\n");
      out.write("\t\t  document.getElementById(\"lblMdDescripcion\").innerHTML = 'Observaciones Generales';\n");
      out.write("\t  } else if (x=='3'){\n");
      out.write("\t\t  document.getElementById(\"mdDescripcion\").disabled = false;\t  \n");
      out.write("\t\t  document.getElementById(\"secId1\").style.display = 'none'; \n");
      out.write("\t\t  document.getElementById(\"secId2\").style.display = 'none';\n");
      out.write("\t\t  document.getElementById(\"secId3\").style.display = 'block';\t\t\n");
      out.write("\t\t  document.getElementById(\"secId4\").style.display = 'none';\n");
      out.write("\t\t  \n");
      out.write("\t\t  document.getElementById(\"lblMdIdentificador2\").innerHTML = 'No. Serie';\n");
      out.write("\t\t  document.getElementById(\"lblMdDescripcion\").innerHTML = 'Descripci&oacute;n del bien';\n");
      out.write("\t  }\n");
      out.write("\t  \n");
      out.write("\t  \n");
      out.write("}\n");
      out.write("  \n");
      out.write("function limpiaCampos() {\n");
      out.write("\t  document.getElementById(\"secId1\").style.display = 'none'; \n");
      out.write("\t  document.getElementById(\"secId2\").style.display = 'none';\n");
      out.write("\t  document.getElementById(\"secId3\").style.display = 'none';\n");
      out.write("\t  document.getElementById(\"secId4\").style.display = 'none';\n");
      out.write("\t  document.getElementById(\"secId5\").style.display = 'block';\n");
      out.write("\t  document.getElementById(\"secId6\").style.display = 'none';\n");
      out.write("\t  \n");
      out.write("\t  document.getElementById(\"mdIdentificador\").value = '0';\n");
      out.write("\t  document.getElementById(\"mdIdentificador1\").value = '';\n");
      out.write("\t  document.getElementById(\"mdIdentificador2\").value = '';\n");
      out.write("\t  \n");
      out.write("\t  document.getElementById(\"mdFactura1\").value = '';\n");
      out.write("\t  document.getElementById(\"mdFactura2\").value = '';\n");
      out.write("\t  \t  \n");
      out.write("\t  document.getElementById(\"mdDescripcion\").value = '';\t  \n");
      out.write("\t  document.getElementById(\"mdDescripcion\").disabled = true;\t  \n");
      out.write("\t  \n");
      out.write("\t  document.getElementById(\"mdBienEspecial\").value  = '0';\n");
      out.write("\t  \n");
      out.write("\t  \n");
      out.write("\t  Materialize.updateTextFields();\n");
      out.write("}\n");
      out.write("\n");
      out.write("function limpiaCamposFile() {\t  \n");
      out.write("\t  document.getElementById(\"mdBienEspecial2\").value  = '0';\n");
      out.write("\t  document.getElementById(\"txtspan\").innerHTML = '';\n");
      out.write("\t  \n");
      out.write("\t  document.getElementById(\"exceltable\").innerHTML = '<table id=\"exceltable\"></table> ';\t  \n");
      out.write("\t  var input = $(\"#excelfile\");\n");
      out.write("\t  var name = $(\"#namefile\");\n");
      out.write("\t  input.replaceWith(input.val('').clone(true));\n");
      out.write("\t  name.replaceWith(name.val('').clone(true));\n");
      out.write("\t  \n");
      out.write("\t  Materialize.updateTextFields();\n");
      out.write("}\n");
      out.write("  \n");
      out.write("  function otroRegistro() {\n");
      out.write("\t  var checkBox = document.getElementById(\"aRegistro\");\n");
      out.write("\t  if (checkBox.checked == true) {\n");
      out.write("\t\t  document.getElementById(\"txtregistro\").disabled = false;\n");
      out.write("\t\t  Materialize.updateTextFields();\n");
      out.write("\t  } else {\n");
      out.write("\t\t  document.getElementById(\"txtregistro\").value  = '';\n");
      out.write("\t\t  document.getElementById(\"txtregistro\").disabled = true;\n");
      out.write("\t\t  Materialize.updateTextFields();\n");
      out.write("\t  }\n");
      out.write("  }\n");
      out.write("  \n");
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

  private boolean _jspx_meth_s_property_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_0 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_0.setPageContext(_jspx_page_context);
    _jspx_th_s_property_0.setParent(null);
    _jspx_th_s_property_0.setValue("idTramite");
    int _jspx_eval_s_property_0 = _jspx_th_s_property_0.doStartTag();
    if (_jspx_th_s_property_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
    return false;
  }

  private boolean _jspx_meth_s_textarea_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_0 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_name_id_disabled_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_0.setParent(null);
    _jspx_th_s_textarea_0.setName("txtregistro");
    _jspx_th_s_textarea_0.setId("txtregistro");
    _jspx_th_s_textarea_0.setValue("%{txtregistro}");
    _jspx_th_s_textarea_0.setDisabled("true");
    int _jspx_eval_s_textarea_0 = _jspx_th_s_textarea_0.doStartTag();
    if (_jspx_th_s_textarea_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_name_id_disabled_nobody.reuse(_jspx_th_s_textarea_0);
      return true;
    }
    _jspx_tagPool_s_textarea_value_name_id_disabled_nobody.reuse(_jspx_th_s_textarea_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_0 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_value_name_id_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_0.setParent(null);
    _jspx_th_s_textfield_0.setName("txtfregistro");
    _jspx_th_s_textfield_0.setId("txtfregistro");
    _jspx_th_s_textfield_0.setValue("%{txtfregistro}");
    int _jspx_eval_s_textfield_0 = _jspx_th_s_textfield_0.doStartTag();
    if (_jspx_th_s_textfield_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_value_name_id_nobody.reuse(_jspx_th_s_textfield_0);
      return true;
    }
    _jspx_tagPool_s_textfield_value_name_id_nobody.reuse(_jspx_th_s_textfield_0);
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

  private boolean _jspx_meth_s_if_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_0 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_if_0.setPageContext(_jspx_page_context);
    _jspx_th_s_if_0.setParent(null);
    _jspx_th_s_if_0.setTest("aBoolean");
    int _jspx_eval_s_if_0 = _jspx_th_s_if_0.doStartTag();
    if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_0.doInitBody();
      }
      do {
        out.write("\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aBoolean').checked = 1;\n");
        out.write("\t\t\t\t\t</script>\n");
        out.write("\t\t\t\t");
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
    _jspx_th_s_if_1.setTest("aMonto");
    int _jspx_eval_s_if_1 = _jspx_th_s_if_1.doStartTag();
    if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_1.doInitBody();
      }
      do {
        out.write("\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aMonto').checked = 1;\n");
        out.write("\t\t\t\t\t</script>\n");
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
    _jspx_th_s_if_2.setTest("aPrioridad");
    int _jspx_eval_s_if_2 = _jspx_th_s_if_2.doStartTag();
    if (_jspx_eval_s_if_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_2.doInitBody();
      }
      do {
        out.write("\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aPrioridad').checked = 1;\n");
        out.write("\t\t\t\t\t</script>\n");
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
    _jspx_th_s_if_3.setTest("aRegistro");
    int _jspx_eval_s_if_3 = _jspx_th_s_if_3.doStartTag();
    if (_jspx_eval_s_if_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_3.doInitBody();
      }
      do {
        out.write("\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aRegistro').checked = 1;\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById(\"txtregistro\").disabled = false;\n");
        out.write("\t\t\t\t\t\t\tMaterialize.updateTextFields();\n");
        out.write("\t\t\t\t\t</script>\n");
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
    _jspx_th_s_property_2.setValue("idTramite");
    int _jspx_eval_s_property_2 = _jspx_th_s_property_2.doStartTag();
    if (_jspx_th_s_property_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
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

  private boolean _jspx_meth_s_textfield_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_1 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_1.setParent(null);
    _jspx_th_s_textfield_1.setName("mdFactura1");
    _jspx_th_s_textfield_1.setId("mdFactura1");
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

  private boolean _jspx_meth_s_textarea_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_3 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_3.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_3.setParent(null);
    _jspx_th_s_textarea_3.setRows("10");
    _jspx_th_s_textarea_3.setId("mdDescripcion");
    _jspx_th_s_textarea_3.setCols("80");
    _jspx_th_s_textarea_3.setName("mdDescripcion");
    int _jspx_eval_s_textarea_3 = _jspx_th_s_textarea_3.doStartTag();
    if (_jspx_th_s_textarea_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_3);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_3);
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

  private boolean _jspx_meth_s_textfield_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_2 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_2.setParent(null);
    _jspx_th_s_textfield_2.setName("mdIdentificador1");
    _jspx_th_s_textfield_2.setId("mdIdentificador1");
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

  private boolean _jspx_meth_s_textfield_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_3 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_3.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_3.setParent(null);
    _jspx_th_s_textfield_3.setName("mdIdentificador2");
    _jspx_th_s_textfield_3.setId("mdIdentificador2");
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
