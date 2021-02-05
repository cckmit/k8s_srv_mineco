package org.apache.jsp.WEB_002dINF.jsp.Layout;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import mx.gob.se.rug.constants.Constants;

public final class header_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
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
      out.write("<ul id=\"account-menu\" class=\"dropdown-content\">\r\n");
      out.write("\t<li><a href=\"");
      out.print( request.getContextPath() );
      out.write("/administracion/perfil/edit.do\">Mi cuenta</a></li>\r\n");
      out.write("</ul>\r\n");
      out.write("<nav class=\"blue darken-4\" role=\"navigation\">\r\n");
      out.write("\t<div class=\"nav-wrapper\">\r\n");
      out.write("\t\t<a href=\"");
      out.print( request.getContextPath() );
      out.write("\" class=\"brand-logo\">\r\n");
      out.write("\t\t\t<img class=\"responsive-img\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/imgs/logo2v1.jpg\">\r\n");
      out.write("\t\t</a>\r\n");
      out.write("\t\t<ul class=\"left menu\">\r\n");
      out.write("\t\t\t");

		if (session.getAttribute(Constants.USUARIO) != null) {
		
      out.write("\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<!-- \t\t\t<a href=\"#\" data-activates=\"slide-out\" class=\"sidenav-trigger btn-floating btn-flat waves-effect waves-light transparent\"><i class=\"material-icons\">menu</i></a> -->\r\n");
      out.write("\t\t\t\t<a href=\"#\" data-activates=\"slide-out\"\r\n");
      out.write("\t\t\t\t\tclass=\"btn-menu btn-floating btn-flat waves-effect waves-light transparent\"><i\r\n");
      out.write("\t\t\t\t\t\tclass=\"material-icons\">menu</i></a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t");

		}
		
      out.write("\r\n");
      out.write("\t\t\t<li class=\"hide-on-med-and-down\" style=\"padding-left: 20px; font-size: 1.6em;\">\r\n");
      out.write("\t\t\t\tRegistro de Garant&iacute;as Mobiliarias \r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t\t<ul class=\"right\">\r\n");
      out.write("\t\t\t");

		if (session.getAttribute(Constants.USUARIO) == null) {
		
      out.write("\r\n");
      out.write("\t\t\t<li><a href=\"");
      out.print( request.getContextPath() );
      out.write("/usuario/add.do\"\r\n");
      out.write("\t\t\t\t\tclass=\"hide-on-med-and-down waves-effect red darken-4 waves-light btn\">Registrarse</a></li>\r\n");
      out.write("\t\t\t<li><a href=\"");
      out.print( request.getContextPath() );
      out.write("/\">Iniciar sesi&oacute;n</a></li>\r\n");
      out.write("\t\t\t");

		} else {
		
      out.write("\r\n");
      out.write("\t\t\t<li class=\"hide-on-med-and-down\">\r\n");
      out.write("\t\t\t\t<a href=\"#!\" class=\"dropdown-trigger\" data-activates=\"account-menu\" data-belowOrigin=\"true\">\r\n");
      out.write("\t\t\t\t\t<span class=\"white-text name\">\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t</span><i class=\"material-icons right\">arrow_drop_down</i>\r\n");
      out.write("\t\t\t\t</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li class=\"hide-on-med-and-down\"><a href=\"");
      out.print(request.getContextPath());
      out.write("/home/logout.do\"\r\n");
      out.write("\t\t\t\t\tclass=\"waves-effect red darken-4 waves-light btn\">Salir</a></li>\r\n");
      out.write("\t\t\t<li class=\"hide-on-large-only\">\r\n");
      out.write("\t\t\t\t<a href=\"");
      out.print(request.getContextPath());
      out.write("/home/logout.do\"\r\n");
      out.write("\t\t\t\t\tclass=\"btn-floating btn-flat waves-effect waves-light transparent\">\r\n");
      out.write("\t\t\t\t\t<i class=\"material-icons\">exit_to_app</i>\r\n");
      out.write("\t\t\t\t</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t");

		}
		
      out.write("\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t</div>\r\n");
      out.write("</nav>");
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
}
