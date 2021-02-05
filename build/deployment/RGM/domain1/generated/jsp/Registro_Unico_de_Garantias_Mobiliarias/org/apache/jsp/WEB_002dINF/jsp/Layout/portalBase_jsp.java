package org.apache.jsp.WEB_002dINF.jsp.Layout;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import mx.gob.se.rug.constants.Constants;

public final class portalBase_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=UTF-8");
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
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("    <title>Registro de Garant&iacute;as Mobiliarias </title>\r\n");
      out.write("    <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/materialize.min.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/custom.css?v=1.4\" />\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/footable.standalone.min.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/font-awesome.min.css\">\r\n");
      out.write("<!--     <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\"> -->\r\n");
      out.write("\t  <link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/material-icons.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/rgm.css?v=1.4\" />\r\n");
      out.write("    ");
      if (_jspx_meth_tiles_insertAttribute_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/engine.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/util.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/Utils/GeneralUtil.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/custom.js\"></script>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/moment.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t");
      if (_jspx_meth_tiles_insertAttribute_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("  </head>\r\n");

Constants.setContextPath(request.getContextPath());

      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"se-pre-con\"></div>\r\n");
      out.write("\t");
      if (_jspx_meth_tiles_insertAttribute_2(_jspx_page_context))
        return;
      out.write("\t\r\n");
      out.write("\t");
      if (_jspx_meth_tiles_insertAttribute_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t");
      if (_jspx_meth_tiles_insertAttribute_4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write(" \t");
      if (_jspx_meth_tiles_insertAttribute_5(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t");
      if (_jspx_meth_tiles_insertAttribute_6(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-3.3.1.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/modernizr.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/materialize.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/footable.min.js\"></script>\r\n");
      out.write("\t\r\n");
      out.write("\t<script src=\"https://cdn.jsdelivr.net/npm/tableexport.jquery.plugin@1.10.21/libs/js-xlsx/xlsx.core.min.js\"></script>\r\n");
      out.write("\t<script src=\"https://cdn.jsdelivr.net/npm/tableexport.jquery.plugin@1.10.21/tableExport.min.js\"></script>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t$(document).ready(function() {\r\n");
      out.write("\t\t$(\".btn-menu\").sideNav();\r\n");
      out.write("\t\t$('select').material_select();\r\n");
      out.write("\t\t$('.modal').modal({\r\n");
      out.write("\t\t    ready: function(modal, trigger) {\r\n");
      out.write("\t\t        modal.scrollTop(0);\t\t        \r\n");
      out.write("\t\t        $('select').material_select('destroy');\r\n");
      out.write("\t\t        $('select').material_select();\r\n");
      out.write("\t\t      }\t\t\t\r\n");
      out.write("\t\t});\t\t\r\n");
      out.write("\t\t$(\".button-collapse\").sideNav();\r\n");
      out.write("\t\t$('.table').footable({\r\n");
      out.write("\t\t\t\"filtering\": {\r\n");
      out.write("\t\t\t\t\"placeholder\": \"Buscar\",\r\n");
      out.write("\t\t\t\t\"position\": \"left\",\t\t\t\t\r\n");
      out.write("\t\t\t\t\"empty\": \"No se encontraron resultados\"\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$('.datepicker').on('mousedown', function (event) {\r\n");
      out.write("\t        event.preventDefault();\r\n");
      out.write("\t    });\r\n");
      out.write("\t\t$('.datepicker').pickadate({\r\n");
      out.write("\t        selectMonths: true,\r\n");
      out.write("\t        selectYears: 15, \r\n");
      out.write("\t        today: 'Hoy',\r\n");
      out.write("\t        clear: 'Inicializar',\r\n");
      out.write("\t        monthsFull: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],\r\n");
      out.write("\t        weekdaysShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],\r\n");
      out.write("\t        close: 'Ok',\r\n");
      out.write("\t        closeOnSelect: false,\r\n");
      out.write("\t        format: 'dd/mm/yyyy'\r\n");
      out.write("\t    });\r\n");
      out.write("\t\t$('.carousel.carousel-slider').carousel({\r\n");
      out.write("\t\t\tfullWidth: true,\r\n");
      out.write("\t\t\tindicators: true\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$('.moveNextCarousel').click(function(e) {\r\n");
      out.write("\t\t\te.preventDefault();\r\n");
      out.write("\t\t\te.stopPropagation();\r\n");
      out.write("\t\t\t$('.carousel').carousel('next');\r\n");
      out.write("\t   });\r\n");
      out.write("\r\n");
      out.write("\t   // move prev carousel\r\n");
      out.write("\t   $('.movePrevCarousel').click(function(e) {\r\n");
      out.write("\t\t\te.preventDefault();\r\n");
      out.write("\t\t\te.stopPropagation();\r\n");
      out.write("\t\t\t$('.carousel').carousel('prev');\r\n");
      out.write("\t   });\r\n");
      out.write("\t   $(\".dropdown-trigger\").dropdown();\r\n");
      out.write("\t});\r\n");
      out.write("\t$('.content-modal').on('click', function(event) {\r\n");
      out.write("\t\t$target = $(event.target);\r\n");
      out.write("\t\tevent.preventDefault();\r\n");
      out.write("\t\t$('#contentModal .modal-content').empty();\r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write("\t\t\turl: event.target.href,\r\n");
      out.write("\t\t\tsuccess: function(result) {\r\n");
      out.write("\t\t\t\t//$('#contentModal .modal-content').append('<h4>' + $target.attr('data-title') + '</h4>');\r\n");
      out.write("\t\t\t\t$('#contentModal .modal-content').append(result);\r\n");
      out.write("\t\t\t\t$('#contentModal').modal('open');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t});\r\n");
      out.write("\t/*$('.sidenav-trigger').on('click', function(event) {\r\n");
      out.write("\t\t$('#slide-out').open();\r\n");
      out.write("\t});*/\r\n");
      out.write("\t\r\n");
      out.write("\t$(window).on('load', function() {\r\n");
      out.write("\t\t$(\".se-pre-con\").fadeOut(\"slow\");\r\n");
      out.write("\t});\r\n");
      out.write("\t$(window).on('beforeunload', function () {\r\n");
      out.write("\t\t$(\".se-pre-con\").fadeIn(\"slow\");\r\n");
      out.write("\t});\r\n");
      out.write("\t$(document).ajaxStart(function() {\r\n");
      out.write("\t\t$(\".se-pre-con\").fadeIn(\"slow\");\r\n");
      out.write("\t});\r\n");
      out.write("\t$(document).ajaxComplete(function() {\r\n");
      out.write("\t\t$(\".se-pre-con\").fadeOut(\"slow\");\r\n");
      out.write("\t});\r\n");
      out.write("\tvar oldXHR = window.XMLHttpRequest;\r\n");
      out.write("\r\n");
      out.write("\tfunction newXHR() {\r\n");
      out.write("\t    var realXHR = new oldXHR();\r\n");
      out.write("\t    realXHR.addEventListener(\"readystatechange\", function() {\r\n");
      out.write("\t        if(realXHR.readyState==1){\r\n");
      out.write("\t            $(\".se-pre-con\").fadeIn(\"slow\");\r\n");
      out.write("\t        }\r\n");
      out.write("\t        if(realXHR.readyState==4){\r\n");
      out.write("\t        \t$(\".se-pre-con\").fadeOut(\"slow\");\r\n");
      out.write("\t        }\r\n");
      out.write("\t    }, false);\r\n");
      out.write("\t    return realXHR;\r\n");
      out.write("\t}\r\n");
      out.write("\twindow.XMLHttpRequest = newXHR;\r\n");
      out.write("\t</script>\r\n");
      out.write("\t");
      if (_jspx_meth_tiles_insertAttribute_7(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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

  private boolean _jspx_meth_tiles_insertAttribute_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_insertAttribute_0 = (_jspx_resourceInjector != null)? _jspx_resourceInjector.createTagHandlerInstance(org.apache.tiles.jsp.taglib.InsertAttributeTag.class) : new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    _jspx_th_tiles_insertAttribute_0.setJspContext(_jspx_page_context);
    _jspx_th_tiles_insertAttribute_0.setName(".include-styles");
    _jspx_th_tiles_insertAttribute_0.doTag();
    if (_jspx_resourceInjector != null) _jspx_resourceInjector.preDestroy(_jspx_th_tiles_insertAttribute_0);
    return false;
  }

  private boolean _jspx_meth_tiles_insertAttribute_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_insertAttribute_1 = (_jspx_resourceInjector != null)? _jspx_resourceInjector.createTagHandlerInstance(org.apache.tiles.jsp.taglib.InsertAttributeTag.class) : new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    _jspx_th_tiles_insertAttribute_1.setJspContext(_jspx_page_context);
    _jspx_th_tiles_insertAttribute_1.setName(".header-scripts");
    _jspx_th_tiles_insertAttribute_1.doTag();
    if (_jspx_resourceInjector != null) _jspx_resourceInjector.preDestroy(_jspx_th_tiles_insertAttribute_1);
    return false;
  }

  private boolean _jspx_meth_tiles_insertAttribute_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_insertAttribute_2 = (_jspx_resourceInjector != null)? _jspx_resourceInjector.createTagHandlerInstance(org.apache.tiles.jsp.taglib.InsertAttributeTag.class) : new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    _jspx_th_tiles_insertAttribute_2.setJspContext(_jspx_page_context);
    _jspx_th_tiles_insertAttribute_2.setName(".header");
    _jspx_th_tiles_insertAttribute_2.doTag();
    if (_jspx_resourceInjector != null) _jspx_resourceInjector.preDestroy(_jspx_th_tiles_insertAttribute_2);
    return false;
  }

  private boolean _jspx_meth_tiles_insertAttribute_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_insertAttribute_3 = (_jspx_resourceInjector != null)? _jspx_resourceInjector.createTagHandlerInstance(org.apache.tiles.jsp.taglib.InsertAttributeTag.class) : new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    _jspx_th_tiles_insertAttribute_3.setJspContext(_jspx_page_context);
    _jspx_th_tiles_insertAttribute_3.setName(".sideMenu");
    _jspx_th_tiles_insertAttribute_3.doTag();
    if (_jspx_resourceInjector != null) _jspx_resourceInjector.preDestroy(_jspx_th_tiles_insertAttribute_3);
    return false;
  }

  private boolean _jspx_meth_tiles_insertAttribute_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_insertAttribute_4 = (_jspx_resourceInjector != null)? _jspx_resourceInjector.createTagHandlerInstance(org.apache.tiles.jsp.taglib.InsertAttributeTag.class) : new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    _jspx_th_tiles_insertAttribute_4.setJspContext(_jspx_page_context);
    _jspx_th_tiles_insertAttribute_4.setName(".tabs");
    _jspx_th_tiles_insertAttribute_4.doTag();
    if (_jspx_resourceInjector != null) _jspx_resourceInjector.preDestroy(_jspx_th_tiles_insertAttribute_4);
    return false;
  }

  private boolean _jspx_meth_tiles_insertAttribute_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_insertAttribute_5 = (_jspx_resourceInjector != null)? _jspx_resourceInjector.createTagHandlerInstance(org.apache.tiles.jsp.taglib.InsertAttributeTag.class) : new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    _jspx_th_tiles_insertAttribute_5.setJspContext(_jspx_page_context);
    _jspx_th_tiles_insertAttribute_5.setName("working.region");
    _jspx_th_tiles_insertAttribute_5.doTag();
    if (_jspx_resourceInjector != null) _jspx_resourceInjector.preDestroy(_jspx_th_tiles_insertAttribute_5);
    return false;
  }

  private boolean _jspx_meth_tiles_insertAttribute_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_insertAttribute_6 = (_jspx_resourceInjector != null)? _jspx_resourceInjector.createTagHandlerInstance(org.apache.tiles.jsp.taglib.InsertAttributeTag.class) : new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    _jspx_th_tiles_insertAttribute_6.setJspContext(_jspx_page_context);
    _jspx_th_tiles_insertAttribute_6.setName(".footer");
    _jspx_th_tiles_insertAttribute_6.doTag();
    if (_jspx_resourceInjector != null) _jspx_resourceInjector.preDestroy(_jspx_th_tiles_insertAttribute_6);
    return false;
  }

  private boolean _jspx_meth_tiles_insertAttribute_7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_insertAttribute_7 = (_jspx_resourceInjector != null)? _jspx_resourceInjector.createTagHandlerInstance(org.apache.tiles.jsp.taglib.InsertAttributeTag.class) : new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    _jspx_th_tiles_insertAttribute_7.setJspContext(_jspx_page_context);
    _jspx_th_tiles_insertAttribute_7.setName(".include-scripts");
    _jspx_th_tiles_insertAttribute_7.doTag();
    if (_jspx_resourceInjector != null) _jspx_resourceInjector.preDestroy(_jspx_th_tiles_insertAttribute_7);
    return false;
  }
}
