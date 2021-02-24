package org.apache.jsp.WEB_002dINF.jsp.Layout;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class footer_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
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

      out.write("<footer class=\"page-footer blue darken-4\">\r\n");
      out.write("  <div class=\"container\">\r\n");
      out.write("    <div class=\"row\">\r\n");
      out.write("      <div class=\"col l6 s12\">\r\n");
      out.write("        <h5 class=\"white-text\">Ministerio de Econom&iacute;a</h5>\r\n");
      out.write("        <p class=\"grey-text text-lighten-4\">Registro de Garant&iacute;as Mobiliarias</p>\r\n");
      out.write("        <p>11 Avenida 03-14 Zona 01 Guatemala, Guatemala</p>\r\n");
      out.write("        <p>garantiasmobiliarias@mineco.gob.gt</p>\r\n");
      out.write("         <p>Tel&eacute;fonos: 2238-3671 / 2238-3079 / 2232-5805</p>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"col l4 offset-l2 s12\">\r\n");
      out.write("        <h5 class=\"white-text\">Enlaces de inter&eacute;s</h5>\r\n");
      out.write("\t\t<ul>\r\n");
      out.write("\t\t\t<li><a class=\"grey-text text-lighten-3\" href=\"https://www.rgm.gob.gt\" target=\"_blank\">Registro de Garant&iacute;as Mobiliarias</a></li>\r\n");
      out.write("           \t<li><a class=\"grey-text text-lighten-3\" href=\"http://www.mineco.gob.gt/\" target=\"_blank\">Ministerio de Econom&iacute;a</a></li>\r\n");
      out.write("           \t<li><a class=\"grey-text text-lighten-3\" href=\"https://www.rgm.gob.gt/app/marco_legal.html\" target=\"_blank\">Ley de Garant&iacute;as Mobiliarias</a></li>\r\n");
      out.write("           \t<li><a class=\"grey-text text-lighten-3\" href=\"");
      out.print(request.getContextPath());
      out.write("/comun/publico/help.do?llave=terminosyCondicionesdeUso\" target=\"_blank\">T&eacute;rminos y Condiciones</a></li>\r\n");
      out.write("         </ul>\r\n");
      out.write("      </div>\r\n");
      out.write("    </div>\r\n");
      out.write("  </div>\r\n");
      out.write("  <div class=\"footer-copyright\">\r\n");
      out.write("    <div class=\"container\">\r\n");
      out.write("    © 2018\r\n");
      out.write("    </div>\r\n");
      out.write("  </div>\r\n");
      out.write("</footer>\r\n");
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
}
