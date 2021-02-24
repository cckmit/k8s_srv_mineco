package org.apache.jsp.WEB_002dINF.jsp.content.certificacion;

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

      out.write("<script type=\"text/javascript\" \n");
      out.write("\tsrc=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/material-dialog.min.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/interface/BusquedaDwrAction.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/dwr/busquedaDWR.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/validaciones/validaciones.js\"></script>\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\n");
      out.write("\tlet datos = false;\n");
      out.write("\n");
      out.write("  function tramitesJSP(persona){\n");
      out.write("\t\tcheckUser(persona);\n");
      out.write("\t\t\n");
      out.write("\t  var ruta = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';\t\n");
      out.write("\t  certificacionDwr(ruta);\n");
      out.write("  }\n");
      out.write("  \n");
      out.write("  function certificacion(garantia, tramite) {\t\n");
      out.write("\t\t\n");
      out.write("\t\tvar ruta = '/Rug/home/certificaTramite.do?idGarantia=' + garantia + '&idTramite='+tramite;   \n");
      out.write("\t\t\n");
      out.write("\t\tif( datos){\n");
      out.write("\t\t\t\n");
      out.write("\t\t\twindow.open(ruta, \"_blank\");\n");
      out.write("\t\t}else{\n");
      out.write("\t\t\tvar ruta = '/Rug/home/certificaTramite.do?idGarantia=' + garantia + '&idTramite='+tramite;    \n");
      out.write("\t\t\t// obtener el costo de una certificacion: tipo_tramite=5\n");
      out.write("\t\t\t$.ajax({\n");
      out.write("\t\t\t\turl: '");
      out.print( request.getContextPath() );
      out.write("/rs/tipos-tramite/5',\n");
      out.write("\t\t\t\tsuccess: function(result) {\n");
      out.write("\t\t\t\t\tMaterialDialog.dialog(\n");
      out.write("\t\t\t\t\t\t\"El costo de una \" + result.descripcion + \" es de Q. \" + (Math.round(result.precio * 100) / 100).toFixed(2) + \", ï¿½estï¿½ seguro que desea continuar?\",\n");
      out.write("\t\t\t\t\t\t{\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\ttitle:'<table><tr><td width=\"10%\"><i class=\"medium icon-green material-icons\">check_circle</i></td><td style=\"vertical-align: middle; text-align:left;\">Confirmar</td></tr></table>', // Modal title\n");
      out.write("\t\t\t\t\t\t\tbuttons:{\n");
      out.write("\t\t\t\t\t\t\t\t// Use by default close and confirm buttons\n");
      out.write("\t\t\t\t\t\t\t\tclose:{\n");
      out.write("\t\t\t\t\t\t\t\t\tclassName:\"red\",\n");
      out.write("\t\t\t\t\t\t\t\t\ttext:\"cancelar\"\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t},\n");
      out.write("\t\t\t\t\t\t\t\tconfirm:{\n");
      out.write("\t\t\t\t\t\t\t\t\tclassName:\"indigo\",\n");
      out.write("\t\t\t\t\t\t\t\t\ttext:\"aceptar\",\n");
      out.write("\t\t\t\t\t\t\t\t\tmodalClose:true,\n");
      out.write("\t\t\t\t\t\t\t\t\tcallback:function(){\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t\t\t\twindow.open(ruta, \"_blank\");\n");
      out.write("\t\t\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t);\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t});\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("\n");
      out.write("\tfunction checkUser(user) {\n");
      out.write("\t\tif (user == 17381) {\n");
      out.write("\t\t\tdatos = true;\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
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
}
