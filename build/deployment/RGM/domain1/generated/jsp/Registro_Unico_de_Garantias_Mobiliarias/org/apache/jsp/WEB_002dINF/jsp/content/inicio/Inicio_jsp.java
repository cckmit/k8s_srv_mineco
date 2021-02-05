package org.apache.jsp.WEB_002dINF.jsp.content.inicio;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import mx.gob.se.rug.to.UsuarioTO;
import mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl;
import java.util.Map;
import mx.gob.se.rug.seguridad.to.PrivilegioTO;
import mx.gob.se.rug.seguridad.to.PrivilegiosTO;
import mx.gob.se.rug.to.UsuarioTO;
import java.util.Iterator;
import mx.gob.se.rug.seguridad.serviceimpl.PrivilegiosServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import mx.gob.se.rug.constants.Constants;

public final class Inicio_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(2);
    _jspx_dependants.add("/WEB-INF/jsp/Layout/menu/privilegios.jsp");
    _jspx_dependants.add("/WEB-INF/jsp/Layout/menu/applicationCtx.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_set_var;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_if_test;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_else;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_set_var = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_else = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_set_var.release();
    _jspx_tagPool_s_if_test.release();
    _jspx_tagPool_s_property_value_nobody.release();
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
      response.setContentType("text/html; charset=ISO-8859-1");
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
      out.write("\r\n");
      out.write("\r\n");

	ApplicationContext ctx = null;
	if (ctx == null) {
		ctx = new ClassPathXmlApplicationContext(Constants.SEGURIDAD_APP_CONTEXT);
	}

      out.write("\r\n");
      out.write("\r\n");

PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
PrivilegiosServiceImpl privilegios =(PrivilegiosServiceImpl)ctx.getBean("privilegiosServiceImpl");
privilegiosTO=privilegios.cargaPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"section\"></div>\r\n");
      out.write("<main>\r\n");
      out.write("\t");

				UsuarioTO usuarioTO = (UsuarioTO)session.getAttribute(Constants.USUARIO);
				MenusServiceImpl menuService = (MenusServiceImpl)ctx.getBean("menusServiceImpl");
				
				Boolean tipo = menuService.cargaMenuJudicial(usuarioTO);
				
	
      out.write("\r\n");
      out.write("\t");
      //  s:set
      org.apache.struts2.views.jsp.SetTag _jspx_th_s_set_0 = (org.apache.struts2.views.jsp.SetTag) _jspx_tagPool_s_set_var.get(org.apache.struts2.views.jsp.SetTag.class);
      _jspx_th_s_set_0.setPageContext(_jspx_page_context);
      _jspx_th_s_set_0.setParent(null);
      _jspx_th_s_set_0.setVar("tipo");
      int _jspx_eval_s_set_0 = _jspx_th_s_set_0.doStartTag();
      if (_jspx_eval_s_set_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_s_set_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_s_set_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_s_set_0.doInitBody();
        }
        do {
          out.print(tipo);
          int evalDoAfterBody = _jspx_th_s_set_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_s_set_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_s_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_s_set_var.reuse(_jspx_th_s_set_0);
        return;
      }
      _jspx_tagPool_s_set_var.reuse(_jspx_th_s_set_0);
      out.write("\r\n");
      out.write("\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div class=\"col s1 m2\"></div>\r\n");
      out.write("\t\t\t<div class=\"col s12 m8\">\r\n");
      out.write("\t\t\t\t");
      //  s:if
      org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_0 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
      _jspx_th_s_if_0.setPageContext(_jspx_page_context);
      _jspx_th_s_if_0.setParent(null);
      _jspx_th_s_if_0.setTest("#tipo == 'false'");
      int _jspx_eval_s_if_0 = _jspx_th_s_if_0.doStartTag();
      if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_s_if_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_s_if_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_s_if_0.doInitBody();
        }
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t<div class=\"carousel carousel-slider center\">\r\n");
          out.write("\t\t\t\t\t\t<div class=\"carousel-item center\">\r\n");
          out.write("\t\t\t\t\t\t\t<div class=\"card teal lighten-2\" style=\"height: 225px !important;\">\r\n");
          out.write("\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t<div class=\"col s12\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"card-content\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"alter-title\" style=\"font-size: 1.5em; background-color: white; text-align: center; margin-bottom: 5px;\">Funcionalidad nueva: Mis usuarios</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<p>Opci&oacute;n para crear subcuentas asociadas a la cuenta principal.</p>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<p class=\"right-align\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"");
          out.print( request.getContextPath() );
          out.write("/usuario/users.do\" class=\"waves-effect waves-teal btn-flat indigo white-text\">Ir a Mis usuarios</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t<div class=\"carousel-item center\">\r\n");
          out.write("\t\t\t\t\t\t\t<div class=\"card teal lighten-2\" style=\"height: 225px !important;\">\r\n");
          out.write("\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t<div class=\"col s12\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"card-content\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"alter-title\" style=\"font-size: 1.5em; background-color: white; text-align: center; margin-bottom: 5px;\">Funcionalidad nueva: Carga masiva</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<p>Opci&oacute;n para realizar tr&aacute;mites de forma masiva al sistema por medio de un archivo XML.</p>\t\t\t\t\t\t\t\t\t\t\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<p class=\"right-align\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"");
          out.print( request.getContextPath() );
          out.write("/masiva/inicia.do\" class=\"waves-effect waves-teal btn-flat indigo white-text\">Ir a Carga masiva</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t<div class=\"carousel-item center\">\r\n");
          out.write("\t\t\t\t\t\t\t<div class=\"card teal lighten-2\" style=\"height: 225px !important;\">\r\n");
          out.write("\t\t\t\t\t\t\t\t<div class=\"row\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t<div class=\"col s12\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"card-content\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"alter-title\" style=\"font-size: 1.5em; background-color: white; text-align: center; margin-bottom: 5px;\">Funcionalidad nueva: Formularios Especiales</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<p>Se han agregado dos formularios especiales nuevos, para brindar un mejor servicio, estos son: </p>\t\t\t\t\t\t\t\t\t\t\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<p class=\"right-align\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t\tFormulario para Cesi&oacute;n en Venta o en Administraci&oacute;n de Derechos de Cr&eacute;dito <a href=\"");
          out.print( request.getContextPath() );
          out.write("/home/factoraje.do?idTipoGarantia=3\" class=\"waves-effect waves-teal btn-flat indigo white-text\">Ir a Formulario</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t<p class=\"right-align\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t\tFormulario para Compra-Venta <a href=\"");
          out.print( request.getContextPath() );
          out.write("/home/factoraje.do?idTipoGarantia=4\" class=\"waves-effect waves-teal btn-flat indigo white-text\">Ir a Formulario</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t</div>\r\n");
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
        return;
      }
      _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_0);
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col s1 m2\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div class=\"col s1 m2\"></div>\r\n");
      out.write("\t\t\t<div class=\"col s12 m8\">\r\n");
      out.write("\t\t\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t\t\t<div class=\"card-content\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"card-title center-align\">Bienvenido ");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        ");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        ");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        ");
      if (_jspx_meth_s_property_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t        </span>\t\t\t\t        \r\n");
      out.write("\t\t\t\t        <div class=\"row\">\r\n");
      out.write("\t\t\t\t\t        <p>En el Registro de Garantias Mobiliarias usted podra realizar</p>\r\n");
      out.write("\t\t\t\t\t        <div class=\"collection\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      //  s:if
      org.apache.struts2.views.jsp.IfTag _jspx_th_s_if_1 = (org.apache.struts2.views.jsp.IfTag) _jspx_tagPool_s_if_test.get(org.apache.struts2.views.jsp.IfTag.class);
      _jspx_th_s_if_1.setPageContext(_jspx_page_context);
      _jspx_th_s_if_1.setParent(null);
      _jspx_th_s_if_1.setTest("#tipo == 'false'");
      int _jspx_eval_s_if_1 = _jspx_th_s_if_1.doStartTag();
      if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_s_if_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_s_if_1.doInitBody();
        }
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\t\t<a href=\"");
          out.print( request.getContextPath() );
          out.write("/inscripcion/paso1.do\" class=\"collection-item\">Primera inscripción</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t<ul class=\"browser-default collection-item\">\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<li>Modificacion</li>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<li>Cancelacion</li>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<li>Ejecucion</li>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<li>Prorroga</li>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t</ul>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t<a href=\"");
          out.print( request.getContextPath() );
          out.write("/home/busqueda.do\" class=\"collection-item\">Consultas</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t<a href=\"");
          out.print( request.getContextPath() );
          out.write("/home/misBoletas.do\" class=\"collection-item\">Cargar Boletas</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_s_if_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_s_if_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_s_if_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_1);
        return;
      }
      _jspx_tagPool_s_if_test.reuse(_jspx_th_s_if_1);
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t");
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
          out.write("\t\t\t\t\t\t\t\t\t<a href=\"");
          out.print( request.getContextPath() );
          out.write("/home/busquedaJud.do\" class=\"collection-item\">Consultas</a>\r\n");
          out.write("\t\t\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_s_else_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_s_else_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_s_else_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_s_else.reuse(_jspx_th_s_else_0);
        return;
      }
      _jspx_tagPool_s_else.reuse(_jspx_th_s_else_0);
      out.write("\r\n");
      out.write("\t\t\t\t\t        </ul>\r\n");
      out.write("\t\t\t\t        </div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col s1 m2\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</main>\r\n");
      out.write("<div class=\"section\"></div>\r\n");
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
    _jspx_th_s_property_0.setValue("#session.User.profile.nombre");
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
    _jspx_th_s_property_1.setValue("#session.User.profile.apellidoPaterno");
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
    _jspx_th_s_property_2.setValue("#session.User.profile.apellidoMaterno");
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
    _jspx_th_s_property_3.setValue("#session.User.profile.apellidoMaterno");
    int _jspx_eval_s_property_3 = _jspx_th_s_property_3.doStartTag();
    if (_jspx_th_s_property_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
    return false;
  }
}
