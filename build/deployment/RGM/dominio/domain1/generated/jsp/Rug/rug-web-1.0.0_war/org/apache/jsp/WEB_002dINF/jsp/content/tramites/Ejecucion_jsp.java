package org.apache.jsp.WEB_002dINF.jsp.content.tramites;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map;
import java.util.Iterator;
import mx.gob.se.rug.seguridad.to.PrivilegioTO;
import mx.gob.se.rug.seguridad.dao.PrivilegiosDAO;
import mx.gob.se.rug.seguridad.to.PrivilegiosTO;
import mx.gob.se.rug.to.UsuarioTO;
import mx.gob.se.rug.constants.Constants;
import mx.gob.se.rug.seguridad.to.MenuTO;
import mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import mx.gob.se.rug.constants.Constants;

public final class Ejecucion_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/WEB-INF/jsp/Layout/menu/applicationCtx.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textarea_rows_name_id_cols_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.release();
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

      out.write("\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/validaciones.js\"></script>\n");
      out.write("<script type=\"text/javascript\" \n");
      out.write("\tsrc=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/material-dialog.min.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/partesJS/parteJS.js\"></script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("function sendForm(){\n");
      out.write("\tdocument.getElementById(\"bFirmar\").value = \"Enviando\";\n");
      out.write("\tdocument.getElementById(\"bFirmar\").disabled = true;\n");
      out.write("\tgetObject('ejecucionGuarda').submit();\n");
      out.write("}\n");
      out.write("\n");
      out.write("function validarEnvio() {\n");
      out.write("\tif(getObject('observaciones').value ==\"\") {\n");
      out.write("\t\talertMaterialize('El campo observaciones no puede ser vacio');\n");
      out.write("\t\treturn false;\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\t// obtener el costo de una ejecucion: tipo_tramite=30\n");
      out.write("\t$.ajax({\n");
      out.write("\t\turl: '");
      out.print( request.getContextPath() );
      out.write("/rs/tipos-tramite/30',\n");
      out.write("\t\tsuccess: function(result) {\n");
      out.write("\t\t\tMaterialDialog.dialog(\n");
      out.write("\t\t\t\t\"El costo de una \" + result.descripcion + \" es de Q. \" + (Math.round(result.precio * 100) / 100).toFixed(2) + \", ¿está seguro que desea continuar?\",\n");
      out.write("\t\t\t\t{\t\t\t\t\n");
      out.write("\t\t\t\t\ttitle:'<table><tr><td width=\"10%\"><i class=\"medium icon-green material-icons\">check_circle</i></td><td style=\"vertical-align: middle; text-align:left;\">Confirmar</td></tr></table>', // Modal title\n");
      out.write("\t\t\t\t\tbuttons:{\n");
      out.write("\t\t\t\t\t\t// Use by default close and confirm buttons\n");
      out.write("\t\t\t\t\t\tclose:{\n");
      out.write("\t\t\t\t\t\t\tclassName:\"red\",\n");
      out.write("\t\t\t\t\t\t\ttext:\"cancelar\"\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t},\n");
      out.write("\t\t\t\t\t\tconfirm:{\n");
      out.write("\t\t\t\t\t\t\tclassName:\"indigo\",\n");
      out.write("\t\t\t\t\t\t\ttext:\"aceptar\",\n");
      out.write("\t\t\t\t\t\t\tmodalClose:false,\n");
      out.write("\t\t\t\t\t\t\tcallback:function(){\n");
      out.write("\t\t\t\t\t\t\t\tsendForm();\n");
      out.write("\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t);\n");
      out.write("\t\t}\n");
      out.write("\t});\n");
      out.write("\t\t\n");
      out.write("}\n");
      out.write("</script>\n");
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
      out.write("<main>\n");

//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();

      out.write("\n");
      out.write("<div class=\"container-fluid\">\n");
      out.write("\t<div class=\"row\">\n");
      out.write("\t\t<div id=\"menuh\">\n");
      out.write("\t\t\t<ul>\n");
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
      out.write("\n");
      out.write("\t\t\t\t");

					}
				}
					
				
      out.write("\n");
      out.write("\t\t\t</ul>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("\t<div class=\"row\">\n");
      out.write("\t\t<div class=\"col s12\"><div class=\"card\">\n");
      out.write("\t\t\t<div class=\"col s2\"></div>\n");
      out.write("\t\t\t<div class=\"col s8\">\n");
      out.write("\t\t\t\t<form id=\"ejecucionGuarda\" name=\"ejecucionGuarda\" action=\"ejecucionGuarda.do\">\n");
      out.write("\t\t\t\t\t<span class=\"card-title\">Ejecuci&oacute;n de la Garant&iacute;a Mobiliaria</span>\n");
      out.write("\t\t\t\t\t<div class=\"row note\">\n");
      out.write("\t\t\t\t\t\t<p>\n");
      out.write("\t\t\t\t\t\t\t<span>En el caso de incumplimiento con la obligaci&oacute;n, el acreedor garantizado\n");
      out.write("\t\t\t\t\t\t\tpodr&aacute; iniciar el proceso de ejecuci&oacute;n de la garant&iacute;a.</span>\n");
      out.write("\t\t\t\t\t\t</p>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t\t\t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t\t\t\t<span class=\"blue-text text-darken-2\">Garant&iacute;a\n");
      out.write("\t\t\t\t\t\t\t\tMobiliaria No. </span> <span> ");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t\t\t</span>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t<div class=\"row\">\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t<div class=\"input-field col s12\">\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textarea_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t\t\t<label for=\"observaciones\">Observaciones o motivos de la ejecuci&oacute;n: </label>\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t</div>\t\t\t\t\t\n");
      out.write("\t\t\t\t\t <hr />\n");
      out.write("\t\t\t \t<center>\n");
      out.write("\t\t            <div class='row'>\t\t\t            \t\n");
      out.write("\t\t            \t<input type=\"button\" id=\"bFirmar\" name=\"button\" class=\"btn btn-large waves-effect indigo\" value=\"Aceptar\" onclick=\"validarEnvio();\"/>\t\t\t            \t\t\t\t            \t\t\t\t\t\t\t            \t\n");
      out.write("\t\t            </div>\n");
      out.write("\t          \t</center>\t\t          \t\t\t\t\t\t\n");
      out.write("\t\t\t\t</form>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"col s2\"></div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("\t</div>\n");
      out.write("</div>\n");
      out.write("</main>");
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
    _jspx_th_s_property_0.setValue("idGarantia");
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
    org.apache.struts2.views.jsp.ui.TextareaTag _jspx_th_s_textarea_0 = (org.apache.struts2.views.jsp.ui.TextareaTag) _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.get(org.apache.struts2.views.jsp.ui.TextareaTag.class);
    _jspx_th_s_textarea_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textarea_0.setParent(null);
    _jspx_th_s_textarea_0.setRows("10");
    _jspx_th_s_textarea_0.setCols("80");
    _jspx_th_s_textarea_0.setName("observaciones");
    _jspx_th_s_textarea_0.setId("observaciones");
    int _jspx_eval_s_textarea_0 = _jspx_th_s_textarea_0.doStartTag();
    if (_jspx_th_s_textarea_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_0);
      return true;
    }
    _jspx_tagPool_s_textarea_rows_name_id_cols_nobody.reuse(_jspx_th_s_textarea_0);
    return false;
  }
}
