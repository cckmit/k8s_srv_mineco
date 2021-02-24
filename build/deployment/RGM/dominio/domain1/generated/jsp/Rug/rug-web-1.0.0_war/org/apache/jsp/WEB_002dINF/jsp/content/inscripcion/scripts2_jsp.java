package org.apache.jsp.WEB_002dINF.jsp.content.inscripcion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class scripts2_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<script language=\"javascript\" type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/tooltips/tooltip.js\"></script>\n");
      out.write("<script type=\"text/javascript\" \n");
      out.write("\tsrc=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/material-dialog.min.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/partesJS/parteJS.js\"></script>\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\n");
      out.write("function noVacio(valor){\n");
      out.write("\t   for ( i = 0; i < valor.length; i++ ) {  \n");
      out.write("\t     if ( valor.charAt(i) != \" \" ) {\n");
      out.write("\t      return true; \n");
      out.write("\t    }  \n");
      out.write("\t   }  \n");
      out.write("\t return false; \n");
      out.write("}\n");
      out.write("\n");
      out.write("function IsNumber(evt) {\n");
      out.write("    var key = (document.all) ? evt.keyCode : evt.which;\n");
      out.write("    \n");
      out.write("    return (key <= 13 || (key >= 48 && key <= 57));\n");
      out.write("}\n");
      out.write("\n");
      out.write("var statSend = false;\n");
      out.write("\n");
      out.write("function paso2() {\n");
      out.write("\tif (!statSend) {\n");
      out.write("    \tdocument.getElementById(\"buttonID\").value = \"Enviando\";\n");
      out.write("    \tdocument.getElementById(\"buttonID\").disabled = true;\n");
      out.write("    \tvar idIns = document.getElementById(\"refInscripcion\").value;\n");
      out.write("       \tstatSend = true;\n");
      out.write("       \twindow.location.href = '");
      out.print( request.getContextPath() );
      out.write("/inscripcion/paso2.do?idInscripcion=' + idIns;       \t\n");
      out.write("       \treturn true;\n");
      out.write("    } else {\t\t\n");
      out.write("        return false;\n");
      out.write("    }\t\t\n");
      out.write(" }\n");
      out.write("\tfunction sendForm(){\n");
      out.write("\t\tdocument.getElementById(\"baceptar\").value = \"Enviando\";\n");
      out.write("    \tdocument.getElementById(\"baceptar\").disabled = true;\n");
      out.write("\t\tdocument.formAcVig.submit();\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("\tfunction validaMesesPaso3(){\n");
      out.write("\t\tvar valor = document.getElementById('formAcVig_inscripcionTO_meses').value;\n");
      out.write("\t\tif (valor == '' || valor == '0'){\n");
      out.write("\t\t\talertMaterialize('La Vigencia debe ser de por lo menos un año');\n");
      out.write("\t\t\treturn false;\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t\tif(valor > 5) {\n");
      out.write("\t\t\talertMaterialize('La Vigencia no puede ser mayor a cinco años');\n");
      out.write("\t\t\treturn false;\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t\tvar saldo = document.getElementById('mdSaldo').value;\t\t\n");
      out.write("\t\tif(saldo==\"0\"){\n");
      out.write("\t\t\talertMaterialize('No tiene saldo suficiente para realizar la inscripción');\n");
      out.write("\t\t\treturn false;\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t\t// obtener el costo de una inscripcion: tipo_tramite=1\n");
      out.write("\t\t$.ajax({\n");
      out.write("\t\t\turl: '");
      out.print( request.getContextPath() );
      out.write("/rs/tipos-tramite/1',\n");
      out.write("\t\t\tsuccess: function(result) {\n");
      out.write("\t\t\t\tMaterialDialog.dialog(\n");
      out.write("\t\t\t\t\t\"El costo de una \" + result.descripcion + \" es de Q. \" + (Math.round(result.precio * 100) / 100).toFixed(2) + \", ¿está seguro que desea continuar?\",\n");
      out.write("\t\t\t\t\t{\t\t\t\t\n");
      out.write("\t\t\t\t\t\ttitle:'<table><tr><td width=\"10%\"><i class=\"medium icon-green material-icons\">check_circle</i></td><td style=\"vertical-align: middle; text-align:left;\">Confirmar</td></tr></table>', // Modal title\n");
      out.write("\t\t\t\t\t\tbuttons:{\n");
      out.write("\t\t\t\t\t\t\t// Use by default close and confirm buttons\n");
      out.write("\t\t\t\t\t\t\tclose:{\n");
      out.write("\t\t\t\t\t\t\t\tclassName:\"red\",\n");
      out.write("\t\t\t\t\t\t\t\ttext:\"cancelar\"\t\t\t\t\t\t\n");
      out.write("\t\t\t\t\t\t\t},\n");
      out.write("\t\t\t\t\t\t\tconfirm:{\n");
      out.write("\t\t\t\t\t\t\t\tclassName:\"indigo\",\n");
      out.write("\t\t\t\t\t\t\t\ttext:\"aceptar\",\n");
      out.write("\t\t\t\t\t\t\t\tmodalClose:false,\n");
      out.write("\t\t\t\t\t\t\t\tcallback:function(){\n");
      out.write("\t\t\t\t\t\t\t\t\tsendForm();\t\n");
      out.write("\t\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t);\n");
      out.write("\t\t\t}\n");
      out.write("\t\t});\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("\tfunction msg_guardar() {\n");
      out.write("\t\talertMaterialize('El sistema le guardara la garantía temporalmente por 72 horas, esto no constituye una inscripción y por lo tanto no otorga prelacion');\n");
      out.write("\t\treturn false;\n");
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
