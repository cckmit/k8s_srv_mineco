package org.apache.jsp.WEB_002dINF.jsp.content.operaciones;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class scripts_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/interface/OperacionesDwrAction.js\"></script>\n");
      out.write("<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/dwr/operacionesJS.js\"></script>\n");
      out.write("<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/interface/BusquedaOpDwrAction.js\"></script>\n");
      out.write("<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/dwr/busquedaMisOpPag.js\"></script>\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/tooltips/tooltip.js\"></script>\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/thickbox.js\"></script>\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/material-dialog.min.js\"></script>\n");
      out.write("<script> \n");
      out.write("iniciaPaginacionPendientes('");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("','1');\n");
      out.write("\n");
      out.write("\tfunction desactiva(){\n");
      out.write("    \tdocument.finiciaM.submit();\n");
      out.write("    \tif (document.getElementById(\"suini\")!=null){\n");
      out.write("\t\t\tdocument.getElementById(\"suini\").value = \"Enviando\";\n");
      out.write("\t    \tdocument.getElementById(\"suini\").disabled = true;\n");
      out.write("    \t}\n");
      out.write("     \t\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tfunction runScript(event) {\n");
      out.write("\t    if (event.which == 13 || event.keyCode == 13) {\n");
      out.write("\t        //code to execute here\n");
      out.write("\t        return false;\n");
      out.write("\t    }\n");
      out.write("\t    return true;\n");
      out.write("\t};\n");
      out.write("\t\n");
      out.write("\tfunction muestraInfo(idTramite){\n");
      out.write("\t\tOperacionesDwrAction.detalleGarantia(idTramite, showGarantia);\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tfunction showGarantia(message){\n");
      out.write("\t\t if (message.codeError==0){\t\t\t \n");
      out.write("\t\t\t MaterialDialog.alert(\n");
      out.write("\t\t\t\t\t\tmessage.message,\n");
      out.write("\t\t\t\t\t\t{\n");
      out.write("\t\t\t\t\t\t\ttitle:'Detalle Garantia', // Modal title\n");
      out.write("\t\t\t\t\t\t\tbuttons:{ // Receive buttons (Alert only use close buttons)\n");
      out.write("\t\t\t\t\t\t\t\tclose:{\n");
      out.write("\t\t\t\t\t\t\t\t\ttext:'close', //Text of close button\n");
      out.write("\t\t\t\t\t\t\t\t\tclassName:'red', // Class of the close button\n");
      out.write("\t\t\t\t\t\t\t\t\tcallback:function(){ // Function for modal click\n");
      out.write("\t\t\t\t\t\t\t\t\t\t//alert(\"hello\")\n");
      out.write("\t\t\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t);\n");
      out.write("\t\t\t}\n");
      out.write("\t\t displayLoader(false);\n");
      out.write("\t\t \n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tfunction buscarporfiltro(){\n");
      out.write("\t\tvar filtro = getObject('terfiltro').value;\n");
      out.write("\t\t\t\t\n");
      out.write("\t\tif(filtro!=null && filtro!=''){\n");
      out.write("\t\t\tiniciaPaginacionFiltro('");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write("','3',filtro);\n");
      out.write("\t\t} else {\t\t\t\n");
      out.write("\t\t\tiniciaPaginacionPendientes('");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write("','3');\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tfunction activa(){\n");
      out.write("\t\tif (document.getElementById(\"suini\")!=null){\n");
      out.write("\t\t\tdocument.getElementById(\"suini\").value = \"Operaciones de Usuarios Delegados\";\n");
      out.write("\t\t\tdocument.getElementById(\"suini\").disabled = false;\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("\tactiva();\n");
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
    _jspx_th_s_property_0.setValue("idPersona");
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
    _jspx_th_s_property_2.setValue("idPersona");
    int _jspx_eval_s_property_2 = _jspx_th_s_property_2.doStartTag();
    if (_jspx_th_s_property_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
    return false;
  }
}
