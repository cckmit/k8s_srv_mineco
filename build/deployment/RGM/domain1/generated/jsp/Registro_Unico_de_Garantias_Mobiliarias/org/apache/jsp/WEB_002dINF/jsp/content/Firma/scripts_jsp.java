package org.apache.jsp.WEB_002dINF.jsp.content.Firma;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class scripts_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<script type=\"text/javascript\">\n");
      out.write("\n");
      out.write("\n");
      out.write("   function abrirCentrado(Url,NombreVentana,ancho,alto) {\n");
      out.write("\t\ty=(screen.height-alto)/2;\n");
      out.write("\t\tx=(screen.width-ancho)/2;\n");
      out.write("\t\tventanaHija = window.open(Url,\"Poliza\",\"left=\"+x+\",top=\"+y+\",width=\"+ancho+\",height=\"+alto);\n");
      out.write("\t}\t   \n");
      out.write("    \t\n");
      out.write("\tfunction sendForm(){\n");
      out.write("\t\tdisplayMessageAlert(false);\t\n");
      out.write("\t\tshowBoleta();\t\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tfunction showBoleta() {\n");
      out.write("\t\tvar URL=\"");
      out.print(request.getContextPath());
      out.write("/home/boleta.do\";\n");
      out.write("\t\twindow.open(URL, \"_blank\");\n");
      out.write("\t\t//abrirCentrado(URL,\"Boleta\",\"500\",\"500\"); \n");
      out.write("\t}\n");
      out.write("\t\n");
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
}
