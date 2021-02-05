package org.apache.jsp.WEB_002dINF.jsp.content.inicio;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<div class=\"section\"></div>\r\n");
      out.write("<main>\r\n");
      out.write("  <center>\r\n");
      out.write("    <div class=\"section\"></div>\r\n");
      out.write("\r\n");
      out.write("    <h5 class=\"indigo-text\">Inicie sesi&oacute;n</h5>\r\n");
      out.write("    <div class=\"section\"></div>\r\n");
      out.write("\r\n");
      out.write("    <div class=\"container\">\r\n");
      out.write("      <div class=\"z-depth-1 grey lighten-4 row\" style=\"display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;\">\r\n");
      out.write("\r\n");
      out.write("        <form class=\"col s12\" name=\"frmLogin\" id=\"frmLogin\" action=\"j_security_check\" method=\"post\">\r\n");
      out.write("          <div class='row'>\r\n");
      out.write("            <div class='col s12'>\r\n");
      out.write("            </div>\r\n");
      out.write("          </div>\r\n");
      out.write("\r\n");
      out.write("          <div class='row'>\r\n");
      out.write("            <div class='input-field col s12'>\r\n");
      out.write("              <input class='validate' type='email' name='j_username' id='idUserFake' onkeypress=\"return submitenter(this,event)\" onkeyup=\"this.value = this.value.toLowerCase()\" required=\"true\"/>\r\n");
      out.write("              <label for='idUserFake'>Usuario</label>\r\n");
      out.write("              <span class=\"helper-text red-text\" id=\"usuario-error\" style=\"display: none;\">El campo usuario es requerido</span>\r\n");
      out.write("            </div>\r\n");
      out.write("          </div>\r\n");
      out.write("\r\n");
      out.write("          <div class='row'>\r\n");
      out.write("            <div class='input-field col s12'>\r\n");
      out.write("              <input class='validate' type='password' name='j_password' id='j_password' onkeypress=\"return submitenter(this,event)\" onblur=\"focoBoton()\" required=\"true\" />\r\n");
      out.write("              <label for='j_password'>Contrase&ntilde;a</label>\r\n");
      out.write("              <span class=\"helper-text red-text\" id=\"password-error\" style=\"display: none;\">El campo Contraseï¿½a es requerido</span>\r\n");
      out.write("            </div>\r\n");
      out.write("            <label style='float: right;'>\r\n");
      out.write("              <a class='aqua-text' href='");
      out.print( request.getContextPath() );
      out.write("/usuario/recover.do'><b>&iquest;Ha olvidado su contrase&ntilde;a?</b></a>\r\n");
      out.write("            </label>\r\n");
      out.write("          </div>\r\n");
      out.write("\r\n");
      out.write("          <br />\r\n");
      out.write("          <center>\r\n");
      out.write("            <div class='row'>\r\n");
      out.write("              <button type='button' name='Ingresar' id=\"Ingresar\" class='col s12 btn btn-large waves-effect indigo' onClick=\"MuestraMjs();\">Ingresar</button>\r\n");
      out.write("            </div>\r\n");
      out.write("          </center>\r\n");
      out.write(" \t\t  ");
if(request.getParameter("error")!=null){
      out.write(" \r\n");
      out.write(" \t\t    <label  style=\"font-size: 15px; color: red;\" > Usuario o Contrase&ntilde;a incorrecto(s) </label>\r\n");
      out.write(" \t\t  ");
}
      out.write("\r\n");
      out.write("        </form>\r\n");
      out.write("      </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <a href=\"");
      out.print( request.getContextPath() );
      out.write("/usuario/add.do\">Registrarse</a>\r\n");
      out.write("  </center>\r\n");
      out.write("\r\n");
      out.write("  <div class=\"section\"></div>\r\n");
      out.write("  <div class=\"section\"></div>\r\n");
      out.write("</main>\r\n");
      out.write("\r\n");
      out.write("<script language=\"JavaScript\">\r\n");
      out.write("\r\n");
      out.write("document.frmLogin.idUserFake.focus();\r\n");
      out.write("\r\n");
      out.write("function focoBoton(){\r\n");
      out.write("\tdocument.frmLogin.Ingresar.focus();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function MuestraMjs(){\r\n");
      out.write("\t$('#usuario-error').hide();\r\n");
      out.write("\t$('#password-error').hide();\r\n");
      out.write("\tif ($('#idUserFake').val() && $('#j_password').val()) {\r\n");
      out.write("\t\tvalidaConexionService.existeConexion(existeConexionBD);\t \t\r\n");
      out.write("\t} else {\r\n");
      out.write("\t\tif (!$('#idUserFake').val()) {\r\n");
      out.write("\t\t\t$('#usuario-error').show();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif (!$('#j_password').val()) {\r\n");
      out.write("\t\t\t$('#password-error').show();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("} \t\r\n");
      out.write("\r\n");
      out.write("function submitenter(myfield,e){\r\n");
      out.write("\tvar keycode;\r\n");
      out.write("\tif (window.event) keycode = window.event.keyCode;\r\n");
      out.write("\telse if (e) keycode = e.which;\r\n");
      out.write("\telse return true;\t\r\n");
      out.write("\tif (keycode == 13)\r\n");
      out.write("\t{\t\t\r\n");
      out.write("\t\tMuestraMjs();\r\n");
      out.write("\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\telse\r\n");
      out.write("\treturn true;\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script> \r\n");
      out.write("\r\n");
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
