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

public final class Modificacion_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/WEB-INF/jsp/Layout/menu/applicationCtx.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_value_name_id_disabled_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_value_rows_onkeyup_name_maxlength_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_name_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_value_name_id_disabled_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody;
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
    _jspx_tagPool_s_textarea_value_name_id_disabled_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_value_rows_onkeyup_name_maxlength_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_value_name_id_disabled_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_select_name_listValue_listKey_list_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_textarea_value_name_id_disabled_nobody.release();
    _jspx_tagPool_s_textarea_value_rows_onkeyup_name_maxlength_id_cols_nobody.release();
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.release();
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.release();
    _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody.release();
    _jspx_tagPool_s_textfield_value_name_id_disabled_nobody.release();
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody.release();
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
      out.write("<input type=\"hidden\" name=\"tipoBienAll\" value=\"false\" id=\"idTipoBienAll\" />\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div id=\"menuh\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t\t");

				UsuarioTO usuarioTO = (UsuarioTO)session.getAttribute(Constants.USUARIO);
				MenuTO menuTO= new MenuTO(1,request.getContextPath());	
				MenusServiceImpl menuService = (MenusServiceImpl)ctx.getBean("menusServiceImpl");
				
				Boolean noHayCancel= (Boolean) request.getAttribute("noHayCancel");
				Boolean noVigencia = (Boolean) request.getAttribute("vigenciaValida");
				if(noHayCancel==null ||(noHayCancel!=null && noHayCancel.booleanValue()==true)){
					Integer idAcreedorRepresentado=(Integer) session.getAttribute(Constants.ID_ACREEDOR_REPRESENTADO);
					MenuTO menuSecundarioTO = new MenuTO(2, request.getContextPath());
					menuSecundarioTO = menuService.cargaMenuSecundario(idAcreedorRepresentado,usuarioTO,menuSecundarioTO,noVigencia);
					Iterator<String> iterator2 = menuSecundarioTO.getHtml().iterator();
					while (iterator2.hasNext()) {
						String menuItem = iterator2.next();
				
      out.print(menuItem);
      out.write("\r\n");
      out.write("\t\t\t\t");

					}
				}
					
				
      out.write("\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div id=\"modifica\" class=\"row\">\r\n");
      out.write("\t\t<div class=\"col s12\"><div class=\"card\">\r\n");
      out.write("\t\t\t<div class=\"col s2\"></div>\r\n");
      out.write("\t\t\t<div class=\"col s8\">\r\n");
      out.write("\t\t\t\t<form id=\"famodificacion\" name=\"famodificacion\" action=\"savemodificacion.do\" method=\"post\">\r\n");
      out.write("\t\t\t\t\t<span class=\"card-title\">Modificaci&oacute;n de la Garant&iacute;a Mobiliaria</span>\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"refInscripcion\" id=\"refInscripcion\" value=\"");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("\" />\r\n");
      out.write("\t\t\t\t\t<div class=\"row note\">\r\n");
      out.write("\t\t\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t\t\t<span>Mediante esta operaci&oacute;n usted podr&aacute; modificar: </span><br>\r\n");
      out.write("\t\t\t\t\t\t\t<span>1) Sustituci&oacute;n de la Garant&iacute;a Mobiliaria inscrita en el RGM; </span><br>\r\n");
      out.write("\t\t\t\t\t\t\t<span>2) Amplicaci&oacute;n de la Garant&iacute;a Mobiliaria inscrita en el RGM; y o </span><br>\r\n");
      out.write("\t\t\t\t\t\t\t<span>3) Los Deudores Garantes </span>\r\n");
      out.write("\t\t\t\t\t\t\t<span>4) Los Acreedores Garantizados </span>\r\n");
      out.write("\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row note teal\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">Datos de la Inscripci&oacute;n</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"input-field col s12\">\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<label for=\"vigencia\">Vigencia: </label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"blue-text text-darken-2\">");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t<div id =\"divParteDWRxx2\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_s_if_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_s_if_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t    \t\t");
      if (_jspx_meth_s_textarea_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t        \t\t\t\t<label for=\"modotroscontrato\">Datos del Representante(s)</label>\r\n");
      out.write("\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row note teal\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">Informaci&oacute;n de la Garant&iacute;a\r\n");
      out.write("\t\t\t\t\t\t\tMobiliaria</span>\r\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textarea_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<label for=\"tiposbienes\">");
      if (_jspx_meth_s_property_4(_jspx_page_context))
        return;
      out.write("</label>\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\t\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"blue-text text-darken-2\">Bienes en garant&iacute;a si estos tienen n&uacute;mero de serie:</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\t\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col s12 right-align\">\r\n");
      out.write("\t\t\t\t\t\t\t<a class=\"btn-floating btn-large waves-effect indigo modal-trigger\" onclick=\"limpiaCampos()\"\r\n");
      out.write("\t\t\t\t\t\t\t\thref=\"#frmBien\" id=\"btnAgregar\"><i\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"material-icons left\">add</i></a>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<div id=\"divParteDWRBienes\"></div>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row note teal\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"white-text\">Informaci&oacute;n Espec&iacute;fica de la Garant&iacute;a Mobiliaria</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<p>\r\n");
      out.write("\t\t\t\t        \t<input type=\"checkbox\" name=\"aBoolean\" id=\"aBoolean\" value=\"true\"/>\t\t\t\t\t\t\t        \t\r\n");
      out.write("\t\t\t\t        \t<label for=\"aBoolean\">Declaro que de conformidad con el contrato de garant&iacute;a, el deudor declar&oacute; que sobre los bienes en garant&iacute;a no existen otro gravamen, anotaci&oacute;n o limitaci&oacute;n previa.</label>\r\n");
      out.write("\t\t\t\t   \t\t</p>\r\n");
      out.write("\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<p>\r\n");
      out.write("\t\t\t\t        \t<input type=\"checkbox\" name=\"aMonto\" id=\"aMonto\" value=\"true\"/>\r\n");
      out.write("\t\t\t\t        \t<label for=\"aMonto\">Los atribuibles y derivados no esta afectos a la Garant&iacute;a Mobiliaria</label>\r\n");
      out.write("\t\t\t\t   \t\t</p>\r\n");
      out.write("\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<p>\r\n");
      out.write("\t\t\t\t        \t<input type=\"checkbox\" name=\"aPrioridad\" id=\"aPrioridad\" value=\"true\" onclick=\"esPrioritaria()\"/>\r\n");
      out.write("\t\t\t\t        \t<label for=\"aPrioridad\">Es prioritaria la garant&iacute;a mobiliaria</label>\r\n");
      out.write("\t\t\t\t   \t\t</p>\r\n");
      out.write("\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\t\t\t\t\t\t\t    \t\r\n");
      out.write("\t\t\t    \t\t<p>\t\t\t\t\t\t\t    \t\t\r\n");
      out.write("\t\t\t    \t\t\t<input type=\"checkbox\" name=\"aRegistro\" id=\"aRegistro\" value=\"true\" onclick=\"otroRegistro()\"/>\r\n");
      out.write("\t\t\t        \t\t<label for=\"aRegistro\">El bien se encuentra en otro registro</label>\r\n");
      out.write("\t\t\t        \t</p>\r\n");
      out.write("\t\t\t        </div>\r\n");
      out.write("\t\t\t        <div class=\"row\">\t\t\t\t\t\t   \t\t\r\n");
      out.write("\t\t\t\t   \t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t    \t\t");
      if (_jspx_meth_s_textarea_2(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t    \t \t\t\t\t\t\t\t\t        \t\r\n");
      out.write("\t\t\t\t        \t<label for=\"txtregistro\">Especifique cual</label>\r\n");
      out.write("\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t \t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t \r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t        \t");
      if (_jspx_meth_s_textarea_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t        \t<label for=\"instrumento\">");
      if (_jspx_meth_s_property_5(_jspx_page_context))
        return;
      out.write("</label>\r\n");
      out.write("\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t \t</div>\t\t\t\t \t\t\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t    \t\t");
      if (_jspx_meth_s_textarea_4(_jspx_page_context))
        return;
      out.write("\t\t\t\t        \t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t        \t<label for=\"modotrosgarantia\">");
      if (_jspx_meth_s_property_6(_jspx_page_context))
        return;
      out.write("</label>\r\n");
      out.write("\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t \t</div>\t\t\t\t \t\r\n");
      out.write("\t\t\t\t \t<div class=\"row note teal\">\t\t\t\t\t\t\t    \t\r\n");
      out.write("\t\t\t        \t<span class=\"white-text\">\r\n");
      out.write("\t\t\t        \t\tObjeto de la Modificaci&oacute;n\r\n");
      out.write("\t\t\t\t\t\t</span>\t\t\t\t\t\t\t   \t\t\r\n");
      out.write("\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t    \t\t");
      if (_jspx_meth_s_textarea_5(_jspx_page_context))
        return;
      out.write("\t\t\t\t        \t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t        \t<label for=\"modotrosterminos\">Observaciones a la Modificaci&oacute;n:</label>\r\n");
      out.write("\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t \t</div>\t\t\t\t \t\r\n");
      out.write("\t\t\t\t    <hr />\r\n");
      out.write("\t\t\t\t \t<center>\r\n");
      out.write("\t\t\t            <div class='row'>\t\t\t            \t\r\n");
      out.write("\t\t\t            \t<input type=\"button\" id=\"bFirmar\" name=\"button\" class=\"btn btn-large waves-effect indigo\" value=\"Aceptar\" onclick=\"validarSinAutoridad();\"/>\t\t\t            \t\t\t\t            \t\t\t\t\t\t\t            \t\r\n");
      out.write("\t\t\t            </div>\r\n");
      out.write("\t\t          \t</center>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</form>\t\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_2(_jspx_page_context))
        return;
      out.write("\t\t\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_s_if_5(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div></div>\t\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<script type=\"text/javascript\"> \t\r\n");
      out.write("\t\t\t\t\tvar idPersona = ");
      if (_jspx_meth_s_property_7(_jspx_page_context))
        return;
      out.write(";\r\n");
      out.write("\t\t\t\t\tvar idTramite= ");
      if (_jspx_meth_s_property_8(_jspx_page_context))
        return;
      out.write(";\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t\t\tcargaParteDeudor('divParteDWRxx2',idTramite, idPersona,'0','2');\r\n");
      out.write("\t\t\t\t\tcargaParteAcreedor('divParteDWRxx3',idTramite, idPersona,'0','2');\r\n");
      out.write("\t\t\t\t\tcargaParteOtorgante('divParteDWRxx4',idTramite, idPersona,'0','2');\r\n");
      out.write("\t\t\t\t\tcargaParteBienes('divParteDWRBienes',idTramite);\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t//escondePartes();\t\r\n");
      out.write("\t\t\t\t</script> \r\n");
      out.write("</main>\r\n");
      out.write("<div id=\"frmBien\" class=\"modal\">\r\n");
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
      if (_jspx_meth_s_select_0(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t    \r\n");
      out.write("\t\t\t\t\t\t    \t<label>Tipo Bien Especial</label>\r\n");
      out.write("\t\t\t\t\t\t  \t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secId4\" class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdFactura1\" for=\"mdFactura1\">Emitido por:</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"mdFactura2\" class=\"datepicker\" id=\"mdFactura2\" />\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdFactura2\" for=\"mdFactura2\">Fecha: </label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textarea_6(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t    \t \t\t\t\t\t\t\t\t        \t\r\n");
      out.write("\t\t\t\t        \t\t<label id=\"lblMdDescripcion\" for=\"mdDescripcion\">Descripci&oacute;n del bien</label>\r\n");
      out.write("\t\t\t\t        \t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secId1\"class=\"row\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador\">Placa</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_1(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_2(_jspx_page_context))
        return;
      out.write("\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secId2\" class=\"row\" style=\"display: none;\"><span class=\"col s12 center-align\">Y</span></div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"secId3\" class=\"row\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<label id=\"lblMdIdentificador2\" for=\"mdIdentificador2\">VIN</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<br />\r\n");
      out.write("\t\t\t\t\t\t<hr />\r\n");
      out.write("\t\t\t\t\t\t<center>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId5\" class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"btn-large indigo\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tonclick=\"add_bien();\">Aceptar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"secId6\" class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#!\" id=\"formBienButton\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"btn-large indigo\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tonclick=\"add_bien();\">Aceptar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</center>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/material-dialog.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-3.3.1.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"> \r\n");
      out.write("function add_bien() {\r\n");
      out.write("\t  \r\n");
      out.write("\t  var idTramite = document.getElementById(\"refInscripcion\").value;\r\n");
      out.write("\t  var mdDescripcion = document.getElementById(\"mdDescripcion\").value;\r\n");
      out.write("\t  var idTipo = document.getElementById(\"mdBienEspecial\").value;\r\n");
      out.write("\t  var mdIdentificador = document.getElementById(\"mdIdentificador\").value;\r\n");
      out.write("\t  var mdIdentificador1 = document.getElementById(\"mdIdentificador1\").value;\r\n");
      out.write("\t  var mdIdentificador2 = document.getElementById(\"mdIdentificador2\").value;\r\n");
      out.write("\t  \r\n");
      out.write("\t  if(!noVacio(mdDescripcion)){\r\n");
      out.write("\t\t  alertMaterialize('Debe ingresar la descripcion del bien especial');\r\n");
      out.write("\t\t  return false;\r\n");
      out.write("\t  }\r\n");
      out.write("\t  \r\n");
      out.write("\t  if(idTipo == '2'){\r\n");
      out.write("\t\t  mdDescripcion = 'Emitido por: ' + document.getElementById(\"mdFactura1\").value + \" Fecha: \" + document.getElementById(\"mdFactura2\").value + \" \" + mdDescripcion;\r\n");
      out.write("\t  }\r\n");
      out.write("\t  \r\n");
      out.write("\t  ParteDwrAction.registrarBien('divParteDWRBienes',idTramite, mdDescripcion, idTipo, mdIdentificador, \r\n");
      out.write("\t\t\t  mdIdentificador1, mdIdentificador2, showParteBienes);\r\n");
      out.write("\t  \r\n");
      out.write("\t  $(document).ready(function() {\t  \r\n");
      out.write("\t\t\t$('#frmBien').modal('close');\r\n");
      out.write("\t\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function cambiaBienesEspeciales() {\r\n");
      out.write("\t  var x = document.getElementById(\"mdBienEspecial\").value;\r\n");
      out.write("\t  \r\n");
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
      out.write("\t\t  document.getElementById(\"mdDescripcion\").disabled = false;\t  \r\n");
      out.write("\t\t  document.getElementById(\"secId1\").style.display = 'none'; \r\n");
      out.write("\t\t  document.getElementById(\"secId2\").style.display = 'none';\r\n");
      out.write("\t\t  document.getElementById(\"secId3\").style.display = 'block';\t\t\r\n");
      out.write("\t\t  document.getElementById(\"secId4\").style.display = 'block';\r\n");
      out.write("\t\t  \r\n");
      out.write("\t\t  document.getElementById(\"lblMdIdentificador2\").innerHTML = 'No. Factura';\r\n");
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
      out.write("\t  \r\n");
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
      out.write("  function esPrioritaria() {\r\n");
      out.write("\t  var checkBox = document.getElementById(\"actoContratoTO.garantiaPrioritaria\");\r\n");
      out.write("\t  if (checkBox.checked == true) {\r\n");
      out.write("\t\t  MaterialDialog.alert(\r\n");
      out.write("\t\t\t\t\t'<p style=\"text-align: justify; text-justify: inter-word;\">Recuerde: <b>Artï¿½culo 17. Garantia Mobiliria Prioritaria.</b> ' +\r\n");
      out.write("\t\t\t\t\t'La publicidad de la garantï¿½a mobiliaria se constituye por medio de la inscripciï¿½n del formulario registral, '+\r\n");
      out.write("\t\t\t\t\t'que haga referencia al carï¿½cter prioritario especial de esta garantï¿½a y que describa los bienes gravadoas por '+\r\n");
      out.write("\t\t\t\t\t'categorï¿½a, sin necesidad de descripciï¿½n pormenorizada. <br> <br>' +\r\n");
      out.write("\t\t\t\t\t'Para el caso se consituya respecto de bienes que '+\r\n");
      out.write("\t\t    \t    'pasarï¿½n a formar parte del inventario el deudor garante, el acreedor garantizado que financie adquisicion ' +\r\n");
      out.write("\t\t    \t    'de la garantï¿½a mobiliaria prioritaria deberï¿½ notificar por escrito, en papel o por medio de un documento ' +\r\n");
      out.write("\t\t    \t    'electrï¿½nico, con anterioridad o al momento de la inscripciï¿½n e esta garantï¿½a, a aquello acreedores garantizados '+\r\n");
      out.write("\t\t    \t    'que hayan inscrito previamente garantï¿½as mobiliarias sobre el inventario, a fin de que obtenga un grado de ' +\r\n");
      out.write("\t\t    \t    'prelacion superior al de estos acreedores. <br><br>'+\r\n");
      out.write("\t\t    \t    '<b>Artï¿½culo 56. Prelaciï¿½n de la garantï¿½a mobiliaria prioritaria para la adquisiciï¿½n de bienes. </b> Una garantï¿½a ' +\r\n");
      out.write("\t\t    \t    'mobiliaria prioritaria para la adquisiciï¿½n de bienes especï¿½ficos tendrï¿½ prelaciï¿½n sobre cualquier '+\r\n");
      out.write("\t\t\t\t\t'garantï¿½a anterior que afecte bienes muebles futuros del mismo tipo en '+\r\n");
      out.write("\t\t\t\t\t'posesiï¿½n del deudor garante, siempre y cuando la garantï¿½a mobiliaria '+\r\n");
      out.write("\t\t\t\t\t'prioritaria se constituya y publicite conforme lo establecido por esta '+\r\n");
      out.write("\t\t\t\t\t'ley, aï¿½n y cuando a esta garantï¿½a mobiliaria prioritaria se le haya dado '+\r\n");
      out.write("\t\t\t\t\t'publicidad con posterioridad a la publicidad de la garantï¿½a anterior. La '+\r\n");
      out.write("\t\t\t\t\t'garantï¿½a mobiliaria prioritaria para la adquisiciï¿½n de bienes especï¿½ficos '+\r\n");
      out.write("\t\t\t\t\t'se extenderï¿½ exclusivamente sobre los bienes muebles especï¿½ficos '+\r\n");
      out.write("\t\t\t\t\t'adquiridos y al numerario especï¿½ficamente atribuible a la venta de estos '+\r\n");
      out.write("\t\t\t\t\t'ï¿½ltimos, siempre y cuando el acreedor garantizado cumpla con los '+\r\n");
      out.write("\t\t\t\t\t'requisitos de inscripciï¿½n de la garantï¿½a mobiliaria prioritaria, '+\r\n");
      out.write("\t\t\t\t\t'establecidos en esta ley. </p>',\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\ttitle:'Garantia Prioritaria', // Modal title\r\n");
      out.write("\t\t\t\t\t\tbuttons:{ // Receive buttons (Alert only use close buttons)\r\n");
      out.write("\t\t\t\t\t\t\tclose:{\r\n");
      out.write("\t\t\t\t\t\t\t\ttext:'close', //Text of close button\r\n");
      out.write("\t\t\t\t\t\t\t\tclassName:'red', // Class of the close button\r\n");
      out.write("\t\t\t\t\t\t\t\tcallback:function(){ // Function for modal click\r\n");
      out.write("\t\t\t\t\t\t\t\t\t//alert(\"hello\")\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t);\t\t     \r\n");
      out.write("\t\t  } \r\n");
      out.write("  }\r\n");
      out.write("  \r\n");
      out.write("  function otroRegistro() {\r\n");
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
      out.write("  \r\n");
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
    _jspx_th_s_property_0.setValue("idTramite");
    int _jspx_eval_s_property_0 = _jspx_th_s_property_0.doStartTag();
    if (_jspx_th_s_property_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_0 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_value_name_id_disabled_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_0.setParent(null);
    _jspx_th_s_textfield_0.setName("vigencia");
    _jspx_th_s_textfield_0.setId("vigencia");
    _jspx_th_s_textfield_0.setValue("%{vigencia}");
    _jspx_th_s_textfield_0.setDisabled("true");
    int _jspx_eval_s_textfield_0 = _jspx_th_s_textfield_0.doStartTag();
    if (_jspx_th_s_textfield_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_value_name_id_disabled_nobody.reuse(_jspx_th_s_textfield_0);
      return true;
    }
    _jspx_tagPool_s_textfield_value_name_id_disabled_nobody.reuse(_jspx_th_s_textfield_0);
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
    _jspx_th_s_property_1.setValue("%{textosFormulario.get(1)}");
    int _jspx_eval_s_property_1 = _jspx_th_s_property_1.doStartTag();
    if (_jspx_th_s_property_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
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
    _jspx_th_s_if_0.setTest("hayAcreedores");
    int _jspx_eval_s_if_0 = _jspx_th_s_if_0.doStartTag();
    if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t<div class=\"row\">\t\t\t\t\t\t\t\r\n");
        out.write("\t\t\t\t\t\t\t<span class=\"blue-text text-darken-2\">");
        if (_jspx_meth_s_property_2((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_if_0, _jspx_page_context))
          return true;
        out.write("</span>\r\n");
        out.write("\t\t\t\t\t\t\t<div id=\"divParteDWRxx3\"></div>\t\t\t\t\t\t\t\r\n");
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

  private boolean _jspx_meth_s_property_2(javax.servlet.jsp.tagext.JspTag _jspx_th_s_if_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_2 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_2.setPageContext(_jspx_page_context);
    _jspx_th_s_property_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_if_0);
    _jspx_th_s_property_2.setValue("%{textosFormulario.get(2)}");
    int _jspx_eval_s_property_2 = _jspx_th_s_property_2.doStartTag();
    if (_jspx_th_s_property_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
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
    _jspx_th_s_if_1.setTest("hayOtorgantes");
    int _jspx_eval_s_if_1 = _jspx_th_s_if_1.doStartTag();
    if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_1.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t<div class=\"row\">\t\t\t\t\t\t\t\r\n");
        out.write("\t\t\t\t\t\t\t<span class=\"blue-text text-darken-2\">");
        if (_jspx_meth_s_property_3((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_if_1, _jspx_page_context))
          return true;
        out.write("</span>\r\n");
        out.write("\t\t\t\t\t\t\t<div id=\"divParteDWRxx4\"></div>\t\t\t\t\t\t\t\r\n");
        out.write("\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t");
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

  private boolean _jspx_meth_s_property_3(javax.servlet.jsp.tagext.JspTag _jspx_th_s_if_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_3 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_3.setPageContext(_jspx_page_context);
    _jspx_th_s_property_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_if_1);
    _jspx_th_s_property_3.setValue("%{textosFormulario.get(3)}");
    int _jspx_eval_s_property_3 = _jspx_th_s_property_3.doStartTag();
    if (_jspx_th_s_property_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
    return false;
  }

  private boolean _jspx_meth_s_textarea_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_0 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_rows_onkeyup_name_maxlength_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_0.setParent(null);
    _jspx_th_s_textarea_0.setRows("10");
    _jspx_th_s_textarea_0.setCols("80");
    _jspx_th_s_textarea_0.setName("modotroscontrato");
    _jspx_th_s_textarea_0.setId("modotroscontrato");
    _jspx_th_s_textarea_0.setValue("%{modotroscontrato}");
    _jspx_th_s_textarea_0.setDynamicAttribute(null, "maxlength", new String("3000"));
    _jspx_th_s_textarea_0.setOnkeyup("return ismaxlength(this)");
    int _jspx_eval_s_textarea_0 = _jspx_th_s_textarea_0.doStartTag();
    if (_jspx_th_s_textarea_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_rows_onkeyup_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_0);
      return true;
    }
    _jspx_tagPool_s_textarea_value_rows_onkeyup_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_0);
    return false;
  }

  private boolean _jspx_meth_s_textarea_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_1 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_1.setParent(null);
    _jspx_th_s_textarea_1.setName("moddescripcion");
    _jspx_th_s_textarea_1.setCols("70");
    _jspx_th_s_textarea_1.setRows("10");
    _jspx_th_s_textarea_1.setId("tiposbienes");
    _jspx_th_s_textarea_1.setValue("%{moddescripcion}");
    int _jspx_eval_s_textarea_1 = _jspx_th_s_textarea_1.doStartTag();
    if (_jspx_th_s_textarea_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_1);
      return true;
    }
    _jspx_tagPool_s_textarea_value_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_1);
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
    _jspx_th_s_property_4.setValue("%{textosFormulario.get(4)}");
    int _jspx_eval_s_property_4 = _jspx_th_s_property_4.doStartTag();
    if (_jspx_th_s_property_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
    return false;
  }

  private boolean _jspx_meth_s_textarea_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_2 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_name_id_disabled_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_2.setParent(null);
    _jspx_th_s_textarea_2.setName("txtregistro");
    _jspx_th_s_textarea_2.setId("txtregistro");
    _jspx_th_s_textarea_2.setValue("%{txtregistro}");
    _jspx_th_s_textarea_2.setDisabled("true");
    int _jspx_eval_s_textarea_2 = _jspx_th_s_textarea_2.doStartTag();
    if (_jspx_th_s_textarea_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_name_id_disabled_nobody.reuse(_jspx_th_s_textarea_2);
      return true;
    }
    _jspx_tagPool_s_textarea_value_name_id_disabled_nobody.reuse(_jspx_th_s_textarea_2);
    return false;
  }

  private boolean _jspx_meth_s_textarea_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_3 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_3.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_3.setParent(null);
    _jspx_th_s_textarea_3.setRows("10");
    _jspx_th_s_textarea_3.setCols("80");
    _jspx_th_s_textarea_3.setId("instrumento");
    _jspx_th_s_textarea_3.setName("instrumento");
    _jspx_th_s_textarea_3.setValue("%{instrumento}");
    _jspx_th_s_textarea_3.setDynamicAttribute(null, "maxlength", new String("3000"));
    int _jspx_eval_s_textarea_3 = _jspx_th_s_textarea_3.doStartTag();
    if (_jspx_th_s_textarea_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_3);
      return true;
    }
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_3);
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
    _jspx_th_s_property_5.setValue("%{textosFormulario.get(7)}");
    int _jspx_eval_s_property_5 = _jspx_th_s_property_5.doStartTag();
    if (_jspx_th_s_property_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
    return false;
  }

  private boolean _jspx_meth_s_textarea_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_4 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_4.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_4.setParent(null);
    _jspx_th_s_textarea_4.setId("modotrosgarantia");
    _jspx_th_s_textarea_4.setName("modotrosgarantia");
    _jspx_th_s_textarea_4.setCols("80");
    _jspx_th_s_textarea_4.setRows("10");
    _jspx_th_s_textarea_4.setValue("%{modotrosgarantia}");
    _jspx_th_s_textarea_4.setDynamicAttribute(null, "maxlength", new String("3500"));
    int _jspx_eval_s_textarea_4 = _jspx_th_s_textarea_4.doStartTag();
    if (_jspx_th_s_textarea_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_4);
      return true;
    }
    _jspx_tagPool_s_textarea_value_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_4);
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
    _jspx_th_s_property_6.setValue("%{textosFormulario.get(8)}");
    int _jspx_eval_s_property_6 = _jspx_th_s_property_6.doStartTag();
    if (_jspx_th_s_property_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
    return false;
  }

  private boolean _jspx_meth_s_textarea_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_5 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_5.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_5.setParent(null);
    _jspx_th_s_textarea_5.setId("modotrosterminos");
    _jspx_th_s_textarea_5.setName("modotrosterminos");
    _jspx_th_s_textarea_5.setCols("80");
    _jspx_th_s_textarea_5.setRows("10");
    _jspx_th_s_textarea_5.setDynamicAttribute(null, "maxlength", new String("3500"));
    int _jspx_eval_s_textarea_5 = _jspx_th_s_textarea_5.doStartTag();
    if (_jspx_th_s_textarea_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_5);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_name_maxlength_id_cols_nobody.reuse(_jspx_th_s_textarea_5);
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
    _jspx_th_s_if_2.setTest("aBoolean");
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
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aBoolean').checked = 1;\r\n");
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
    _jspx_th_s_if_3.setTest("aMonto");
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
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aMonto').checked = 1;\r\n");
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
    _jspx_th_s_if_4.setTest("aPrioridad");
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
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aPrioridad').checked = 1;\r\n");
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

  private boolean _jspx_meth_s_if_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:if
    org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_5 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
    _jspx_th_s_if_5.setPageContext(_jspx_page_context);
    _jspx_th_s_if_5.setParent(null);
    _jspx_th_s_if_5.setTest("aRegistro");
    int _jspx_eval_s_if_5 = _jspx_th_s_if_5.doStartTag();
    if (_jspx_eval_s_if_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_if_5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_if_5.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_if_5.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t<script type=\"text/javascript\">\r\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById('aRegistro').checked = 1;\r\n");
        out.write("\t\t\t\t\t\t\tdocument.getElementById(\"txtregistro\").disabled = false;\r\n");
        out.write("\t\t\t\t\t\t\tMaterialize.updateTextFields();\r\n");
        out.write("\t\t\t\t\t</script>\r\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_if_5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_if_5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_if_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_5);
      return true;
    }
    _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_5);
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
    _jspx_th_s_property_7.setValue("idpersona");
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
    _jspx_th_s_property_8.setValue("idTramite");
    int _jspx_eval_s_property_8 = _jspx_th_s_property_8.doStartTag();
    if (_jspx_th_s_property_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_8);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_8);
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

  private boolean _jspx_meth_s_textarea_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textarea
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_6 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_6.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_6.setParent(null);
    _jspx_th_s_textarea_6.setRows("10");
    _jspx_th_s_textarea_6.setId("mdDescripcion");
    _jspx_th_s_textarea_6.setCols("80");
    _jspx_th_s_textarea_6.setName("mdDescripcion");
    int _jspx_eval_s_textarea_6 = _jspx_th_s_textarea_6.doStartTag();
    if (_jspx_th_s_textarea_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_6);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_6);
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
}
