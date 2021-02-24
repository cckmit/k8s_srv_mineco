package org.apache.jsp.WEB_002dINF.jsp.content.inscripcion;

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
      response.setContentType("text/html;charset=UTF-8");
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
      out.write("<!DOCTYPE html>\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/engine.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/util.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/interface/DireccionesDwrAction.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/interface/ParteDwrAction.js\"></script>\n");
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
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/dwr/direccionesDWR.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/FuncionesDeFechas.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/RugUtil.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/tooltips/tooltip.js\"></script>\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\t\n");
      out.write("var statSend = false;\n");
      out.write("//$(\"#cuatroMenu\").attr(\"class\",\"linkSelected\");\n");
      out.write("\n");
      out.write("function aceptaalfa(evt)\n");
      out.write("{\n");
      out.write("    var charCode = (evt.which) ? evt.which : event.keyCode\n");
      out.write("    if (charCode > 31 && (charCode < 48 || charCode > 57)\n");
      out.write("                      && (charCode < 65 || charCode > 90)\n");
      out.write("                      && (charCode < 97 || charCode > 122)\n");
      out.write("                      && (charCode <209  || charCode > 249)\n");
      out.write("        )\n");
      out.write("        return false;\n");
      out.write("    return true;\n");
      out.write("}\n");
      out.write("\n");
      out.write("function checkSubmit() {\n");
      out.write("\n");
      out.write("    if (!statSend) {\n");
      out.write("    \tdocument.getElementById(\"baceptar\").value = \"Enviando\";\n");
      out.write("    \tdocument.getElementById(\"baceptar\").disabled = true;\n");
      out.write("        statSend = true;\n");
      out.write("        return true;\n");
      out.write("\n");
      out.write("    } else {\n");
      out.write("\t\t\n");
      out.write("        return false;\n");
      out.write("\n");
      out.write("    }\n");
      out.write("\n");
      out.write("}\n");
      out.write("\n");
      out.write("function activaBtn1_d_paso1(){\n");
      out.write("\tdocument.getElementById(\"buttonID\").value = \"Continuar\";\n");
      out.write("\tdocument.getElementById(\"buttonID\").disabled = false;\n");
      out.write("}\n");
      out.write("\n");
      out.write("function paso2_d_paso1() {\n");
      out.write("\tif(document.getElementById('tableDeudores')==null){\n");
      out.write("\t\talertMaterialize('No se puede continuar sin un Deudor');\n");
      out.write("\t\treturn false;\n");
      out.write("\t}\n");
      out.write("\tif(document.getElementById('tableAcreedores')==null){\n");
      out.write("\t\talertMaterialize('No se puede continuar sin un Acreedor');\n");
      out.write("\t\treturn false;\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\t//if (document.getElementById('sepuedecontinuar')!=null){\n");
      out.write("\t\t//if (!statSend) {\n");
      out.write("\t    \tdocument.getElementById(\"buttonID\").value = \"Enviando\";\n");
      out.write("\t    \tdocument.getElementById(\"buttonID\").disabled = true;\n");
      out.write("\t        statSend = true;\n");
      out.write("\t        window.location.href = \"");
      out.print(request.getContextPath());
      out.write("/inscripcion/paso2.do\";\n");
      out.write("\t\t\t\treturn true;\n");
      out.write("\n");
      out.write("\t\t\t//} else {\n");
      out.write("\n");
      out.write("\t\t\t\t//return false;\n");
      out.write("\n");
      out.write("\t\t\t//}\n");
      out.write("\t\t/*} else {\n");
      out.write("\t\t\talert('No se puede continuar sin un Otorgante');\n");
      out.write("\t\t}*/\n");
      out.write("\n");
      out.write("}\n");
      out.write("\n");
      out.write("function fechasCorrectas(){\n");
      out.write("\t //var strFI = getObject('datepicker4').value;\n");
      out.write("\t //var strFF = getObject('datepicker5').value;\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("function fechaCelebCorrecta(){ \n");
      out.write("\n");
      out.write("}\n");
      out.write("\n");
      out.write("function actualizaCopia(){\n");
      out.write("\t //if(getObject('cpContrato').checked){\n");
      out.write("\t  \n");
      out.write("\t  //getObject('formS2ag_obligacionTO_tipoActoContrato').value=getObject('idTipoGarantia').options[getObject('idTipoGarantia').selectedIndex].text;\n");
      out.write("\t  //getObject('datepicker4').value=getObject('datepicker1').value;\n");
      out.write("\t  //getObject('formS2ag_obligacionTO_otrosTerminos').value=getObject('formS2ag_actoContratoTO_otrosTerminos').value;\n");
      out.write("\t  \n");
      out.write("\t  //getObject('formS2ag_obligacionTO_tipoActoContrato').readOnly = true;\n");
      out.write("\t  //getObject('datepicker4').readOnly = true;\n");
      out.write("\t  //getObject('formS2ag_obligacionTO_otrosTerminos').readOnly = true;\n");
      out.write("\t //}\n");
      out.write("}\n");
      out.write("\n");
      out.write("function textCounter(field, countfield, maxlimit) {\n");
      out.write("\t if (field.value.length > maxlimit) // if too long...trim it!\n");
      out.write("\t field.value = field.value.substring(0, maxlimit);\n");
      out.write("\t // otherwise, update 'characters left' counter\n");
      out.write("\t else\n");
      out.write("\t countfield.value = maxlimit - field.value.length;\n");
      out.write("}\n");
      out.write("\n");
      out.write("var todos = new Array();\n");
      out.write("\n");
      out.write("function marcar(s) {\n");
      out.write("\t cual=s.selectedIndex;\n");
      out.write("\t for(y=0;y<s.options.length;y++){\n");
      out.write("\t\t if(y==cual){\n");
      out.write("\t\t  s.options[y].selected=(todos[y]==true)?false:true;\n");
      out.write("\t\t  todos[y]=(todos[y]==true)?false:true;\n");
      out.write("\t\t }else{\n");
      out.write("\t\t s.options[y].selected=todos[y];\n");
      out.write("\t\t  }\n");
      out.write("\t }\n");
      out.write("}\n");
      out.write("\n");
      out.write("\n");
      out.write("function buscaPunto(texto){\n");
      out.write("\t for(i=0;i<texto.length;i++){\n");
      out.write("\t  if(texto.charAt(i)==\".\") return true;\n");
      out.write("\t }\n");
      out.write("\t return false;\n");
      out.write("}\n");
      out.write("\t\n");
      out.write("function IsNumber(evt) {\n");
      out.write("\tvar key = (document.all) ? evt.keyCode : evt.which;\n");
      out.write("\tvar cadena = document.getElementById('idMontoMaximo').value;\n");
      out.write("\tvar onlyPunto = buscaPunto(cadena);    \n");
      out.write("\tif (onlyPunto){     \n");
      out.write("\t return (key <= 13 || (key >= 48 && key <= 57) || key==118 );\n");
      out.write("\t}else{\n");
      out.write("\t return ( key <= 13 || (key >= 48 && key <= 57) || key==46 || key==118);      \n");
      out.write("\n");
      out.write("\tIsNumberValidate('idMontoMaximo');\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("\n");
      out.write("function noVacio(valor){\n");
      out.write("\t   for ( i = 0; i < valor.length; i++ ) {  \n");
      out.write("\t     if ( valor.charAt(i) != \" \" ) {\n");
      out.write("\t      return true; \n");
      out.write("\t    }  \n");
      out.write("\t   }  \n");
      out.write("\t return false; \n");
      out.write("}\n");
      out.write("\t \n");
      out.write("function activaBtn2(){\n");
      out.write("\t  document.getElementById(\"baceptar\").value = \"Continuar\";\n");
      out.write("      document.getElementById(\"baceptar\").disabled = false;\n");
      out.write("}\n");
      out.write("\n");
      out.write("  function paso1() {\n");
      out.write("\t \n");
      out.write("      document.getElementById(\"buttonID\").value = \"Enviando\";\n");
      out.write("      document.getElementById(\"buttonID\").disabled = true;\n");
      out.write("      var idIns = document.getElementById(\"refInscripcion\").value;\n");
      out.write("      statSend = true;\n");
      out.write("      window.location.href = '");
      out.print( request.getContextPath() );
      out.write("/inscripcion/paso1.do?idInscripcion=' + idIns;\n");
      out.write("      return true;\n");
      out.write("\t   \n");
      out.write("}\n");
      out.write("\t    \n");
      out.write("function validarSelectMultiple(){\n");
      out.write("\t  if (document.getElementById('formS2ag_actoContratoTO_tipoBienes').selectedIndex!=-1){\n");
      out.write("\t   return true;\n");
      out.write("\t  }else{\n");
      out.write("\t   \n");
      out.write("\t   return false;\n");
      out.write("\t  }\n");
      out.write("\t  \n");
      out.write("}\n");
      out.write("\n");
      out.write("function sendForm(){\n");
      out.write("\t  document.getElementById(\"baceptar\").value = \"Enviando\";\n");
      out.write("\t     document.getElementById(\"baceptar\").disabled = true;\n");
      out.write("\t  document.formS2ag.submit();\n");
      out.write(" }\n");
      out.write("\t \n");
      out.write(" function seguroContinuar(){\n");
      out.write("\t  \t  \n");
      out.write("\t  var bienes = getObject('descripcionId').value;\n");
      out.write("\t  var instrumento = getObject('actoContratoTO.instrumentoPub').value;\n");
      out.write("\t  var observaciones = getObject('actoContratoTO.otrosTerminos').value;\n");
      out.write("\t  \t  \n");
      out.write("\t  if (!noVacio(bienes)){\n");
      out.write("\t\talertMaterialize('El campo Descripci&oacute;n de los Bienes Muebles objeto de la Garant&iacute;a Mobiliaria es obligatorio');\n");
      out.write("\t   \treturn false;\n");
      out.write("\t  }\n");
      out.write("\t   \n");
      out.write("\t  if (!noVacio(instrumento)){\n");
      out.write("\t\talertMaterialize('El campo Datos Generales del contrato de la garantia es obligatorio');\n");
      out.write("\t    return false;\n");
      out.write("\t  }\n");
      out.write("\t  \n");
      out.write("\t  if (!noVacio(observaciones)){\n");
      out.write("\t\talertMaterialize('El campo Datos Adicionales de la garantía es obligatorio');\n");
      out.write("\t    return false;\n");
      out.write("\t  }\n");
      out.write("\t   \n");
      out.write("      document.getElementById(\"baceptar\").value = \"Enviando\";\n");
      out.write("      document.getElementById(\"baceptar\").disabled = true;\n");
      out.write("      document.formS2ag.submit();    \n");
      out.write("\n");
      out.write("}\n");
      out.write(" \n");
      out.write(" function activaBtn1(){\n");
      out.write("\t  document.getElementById(\"buttonID\").value = \"Regresar\";\n");
      out.write("\t  document.getElementById(\"buttonID\").disabled = false;\n");
      out.write("}\n");
      out.write("\t \n");
      out.write(" function copiaContrato(){\n");
      out.write("\t  if (!noVacio(getObject('datepicker1').value)){\n");
      out.write("\t   getObject('cpContrato').checked=0;\n");
      out.write("\t   alert('El campo Fecha de celebración del Acto o Contrato es obligatorio');\n");
      out.write("\t   return false;\n");
      out.write("\t  }\n");
      out.write("\t  if(getObject('cpContrato').checked){\n");
      out.write("\t  \n");
      out.write("\t   //getObject('formS2ag_obligacionTO_tipoActoContrato').value=getObject('idTipoGarantia').options[getObject('idTipoGarantia').selectedIndex].text;\n");
      out.write("\t   getObject('datepicker4').value=getObject('datepicker1').value;\n");
      out.write("\t   //getObject('formS2ag_obligacionTO_otrosTerminos').value=getObject('formS2ag_actoContratoTO_otrosTerminos').value;\n");
      out.write("\t   \n");
      out.write("\t   //getObject('formS2ag_obligacionTO_tipoActoContrato').readOnly = true;\n");
      out.write("\t   getObject('datepicker4').readOnly = true;\n");
      out.write("\t   //getObject('formS2ag_obligacionTO_otrosTerminos').readOnly = true;\n");
      out.write("\t  }else{\n");
      out.write("\t   //getObject('formS2ag_obligacionTO_tipoActoContrato').value=\"\";\n");
      out.write("\t   getObject('datepicker4').value=\"\";\n");
      out.write("\t   //getObject('formS2ag_obligacionTO_otrosTerminos').value=\"\";\n");
      out.write("\t   \n");
      out.write("\t   //getObject('formS2ag_obligacionTO_tipoActoContrato').readOnly = false;\n");
      out.write("\t   getObject('datepicker4').readOnly = false;\n");
      out.write("\t   //getObject('formS2ag_obligacionTO_otrosTerminos').readOnly = false;\n");
      out.write("\t  }\n");
      out.write("}\n");
      out.write("\t \n");
      out.write("\t \n");
      out.write("\t \n");
      out.write("\t \n");
      out.write("function escondePartes(){\n");
      out.write("\t\t \n");
      out.write("\t  //var valor = document.getElementById('idTipoGarantia').value;\n");
      out.write("\t  var valor = 1;\n");
      out.write("\t  //var validador=dwr.util.getValue(mensaje.codeError);\n");
      out.write("\t  //var validador=dwr.util.getValue(mensaje);\n");
      out.write("\t\t\n");
      out.write("\t  switch(valor){\n");
      out.write("\t  case \"8\":\n");
      out.write("\t\t  //alert('Validar Folios '+ ");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write(");\n");
      out.write("\t\t  \n");
      out.write("\t\t  //ParteDwrAction.verificarFolios(");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write(",resultadoVerificacion);\n");
      out.write("\t\t    \n");
      out.write("\t\t    break;\n");
      out.write("\t  \n");
      out.write("\t  \n");
      out.write("\t   case \"10\":\n");
      out.write("\t    \n");
      out.write("\t    document.getElementById('obligacionDIV').style.visibility = 'hidden';\n");
      out.write("\t    document.getElementById('obligacionDIV').style.display = 'none';     \n");
      out.write("\t    document.getElementById('fechaCeleb').innerHTML = '<span class=\"textoGeneralRojo\"> * Fecha de surgimiento del Derecho de Retención :</span>';\n");
      out.write("\t    document.getElementById('terIDcond').innerHTML = '<span class=\"textoGeneralRojo\"> * Fundamento Legal del cual surge el Derecho de Retención';\n");
      out.write("\t    break;\n");
      out.write("\t    \n");
      out.write("\t   case \"12\":\n");
      out.write("\t    //document.getElementById('obligacionDIV').style.visibility = 'hidden';\n");
      out.write("\t    //document.getElementById('obligacionDIV').style.display = 'none';     \n");
      out.write("\t    document.getElementById('obligacionDIV').style.visibility = 'visible';\n");
      out.write("\t    document.getElementById('obligacionDIV').style.display = 'block';\n");
      out.write("\t    document.getElementById('fechaCeleb').innerHTML = '<spaon class=\"textoGeneralRojo\"> * Fecha de surgimiento del Privilegio Especial :</span>';\n");
      out.write("\t    document.getElementById('terIDcond').innerHTML = '<span class=\"textoGeneralRojo\"> * Fundamento Legal del cual surge el Privilegio Especial';\n");
      out.write("\t    break;\n");
      out.write("\t   default:\n");
      out.write("\t    //document.getElementById('obligacionDIV').style.visibility = 'visible';\n");
      out.write("\t    //document.getElementById('obligacionDIV').style.display = 'block';\n");
      out.write("\t    //document.getElementById('fechaCeleb').innerHTML = '<span class=\"textoGeneralRojo\"> Fecha de celebración del acto o contrato :</span>';\n");
      out.write("\t    //document.getElementById('terIDcond').innerHTML = '<span class=\"textoGeneralRojo\"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';\n");
      out.write("\t    break;\n");
      out.write("\t  }\n");
      out.write("}\n");
      out.write("\t \n");
      out.write("function continuar(id){\n");
      out.write("\t\t switch(id){\n");
      out.write("\t\t  \tcase 1:\n");
      out.write("\t\t  \t\t//alert(\"redireccion a AR\");\n");
      out.write("\t\t  \t\twindow.location.href = '");
      out.print( request.getContextPath() );
      out.write("/acreedor/inicia.do?idInscripcion=");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write("';\n");
      out.write("\t\t\t  break;\n");
      out.write("\t\t\tcase 2:\n");
      out.write("\t\t  \t\t//alert(\"redireccion a AD\");\n");
      out.write("\t\t  \t\twindow.location.href = '");
      out.print( request.getContextPath() );
      out.write("/inscripcion/paso1.do?idInscripcion=");
      if (_jspx_meth_s_property_3(_jspx_page_context))
        return;
      out.write("';\n");
      out.write("\t\t\t  break;\n");
      out.write("\t\t\tcase 3:\n");
      out.write("\t\t  \t\t//alert(\"redireccion a ARyAD\");\n");
      out.write("\t\t  \t\twindow.location.href = '");
      out.print( request.getContextPath() );
      out.write("/acreedor/inicia.do?idInscripcion=");
      if (_jspx_meth_s_property_4(_jspx_page_context))
        return;
      out.write("';\n");
      out.write("\t\t\t  break;\n");
      out.write("\t\t }\n");
      out.write("}\n");
      out.write("\t  \n");
      out.write(" function cancelar(){\n");
      out.write("\t //alert(\"Setear\");\n");
      out.write("\t displayMessageFolioElectronicoAcreedores(false);\n");
      out.write("\t //me falta setear el combo tipo garantia para que regrese a seleccione\n");
      out.write("\t document.getElementById('idTipoGarantia').selectedIndex=\"0\";\n");
      out.write("\t \n");
      out.write(" }\n");
      out.write("\t \n");
      out.write(" function resultadoVerificacion(mensaje){\n");
      out.write("\t  //alert('resultado '+mensaje.codeError);\n");
      out.write("\t  //alert('Validar Folio'+ ");
      if (_jspx_meth_s_property_5(_jspx_page_context))
        return;
      out.write(");\n");
      out.write("\t  \n");
      out.write("\t switch(mensaje.codeError){\n");
      out.write("\t  \tcase 0:\n");
      out.write("\t  \t\t\n");
      out.write("\t  \t\t//alert(\"Acreedores tienen folio Electrónico \"+");
      if (_jspx_meth_s_property_6(_jspx_page_context))
        return;
      out.write(");\n");
      out.write("\t  \t\tdisplayLoader(false);\n");
      out.write("\t\t  break;\n");
      out.write("\t  \t\n");
      out.write("\t  \tcase 1:\n");
      out.write("\t  \t\tdisplayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','El Acreedor Representado para esta garantía no cuenta con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');\n");
      out.write("\t  \t\t//alert('Validar Folio Acreedor  Ir a alta de acreedores'+ ");
      if (_jspx_meth_s_property_7(_jspx_page_context))
        return;
      out.write(");\n");
      out.write("\t\t  break;\n");
      out.write("\t\t  \n");
      out.write("\t\t  displayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','El Acreedor Adicional para esta garantía no cuenta con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');\n");
      out.write("\t  \t\t//alert('Validar Folios Acreedor Adicional Ir a Paso 1'+ ");
      if (_jspx_meth_s_property_8(_jspx_page_context))
        return;
      out.write(");\n");
      out.write("\t  \tbreak;\n");
      out.write("\t  \tcase 3:\n");
      out.write("\t  \t\tdisplayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','Los Acreedores para esta garantía no cuentan con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');\n");
      out.write("\t  \t\t//alert('Ninguno tiene folio Ir a alta de acreedores'+ ");
      if (_jspx_meth_s_property_9(_jspx_page_context))
        return;
      out.write(");\n");
      out.write("\t\t  break;\n");
      out.write("\n");
      out.write("\t  \t}\n");
      out.write(" }\n");
      out.write("\n");
      out.write("\t \t \n");
      out.write("function IsNumberValidate(id) {\n");
      out.write("\tvar cadena = document.getElementById(id).value;\n");
      out.write("\t\n");
      out.write("\tvar flagCharBad=false;\n");
      out.write("\t   \n");
      out.write("\tfor(i=0; i<cadena.length;i++)  {\n");
      out.write("\t\tvar charValue= cadena.charAt(i)\n");
      out.write("\t\tvar key = ascii_value(charValue);\n");
      out.write("\t\tif (!(key <= 13 || (key >= 48 && key <= 57) || key==46 ) ){\n");
      out.write("\t\t\tflagCharBad=true;\n");
      out.write("\t\t}    \t \n");
      out.write("\t}\t\n");
      out.write("\t\n");
      out.write("\tif(flagCharBad){\n");
      out.write("\t\talert('Por favor ingrese un valor valido para el campo');\n");
      out.write("\t\tdocument.getElementById(id).value='';\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("}\n");
      out.write("\n");
      out.write("function ascii_value (c)\n");
      out.write("{\n");
      out.write("\t// restrict input to a single character\n");
      out.write("\tc = c . charAt (0);\n");
      out.write("\n");
      out.write("\t// loop through all possible ASCII values\n");
      out.write("\tvar i;\n");
      out.write("\tfor (i = 0; i < 256; ++ i)\n");
      out.write("\t{\n");
      out.write("\t\t// convert i into a 2-digit hex string\n");
      out.write("\t\tvar h = i . toString (16);\n");
      out.write("\t\tif (h . length == 1)\n");
      out.write("\t\t\th = \"0\" + h;\n");
      out.write("\n");
      out.write("\t\t// insert a % character into the string\n");
      out.write("\t\th = \"%\" + h;\n");
      out.write("\n");
      out.write("\t\t// determine the character represented by the escape code\n");
      out.write("\t\th = unescape (h);\n");
      out.write("\n");
      out.write("\t\t// if the characters match, we've found the ASCII value\n");
      out.write("\t\tif (h == c)\n");
      out.write("\t\t\tbreak;\n");
      out.write("\t}\n");
      out.write("\treturn i;\n");
      out.write("}\n");
      out.write("\t \n");
      out.write("\t \n");
      out.write("function selecciona(frm) {\n");
      out.write(" \tfor (i = 0; ele = frm.formS2ag_actoContratoTO_tipoBienes.options[i]; i++){\n");
      out.write("  \n");
      out.write("  \t  ele.selected = true;\n");
      out.write(" \t}\n");
      out.write(" \tgetObject('idTipoBienAll').value=\"true\";\n");
      out.write("} \n");
      out.write("\t \t\n");
      out.write("function desSelecciona(frm) {\n");
      out.write("  for (i = 0; ele = frm.formS2ag_actoContratoTO_tipoBienes.options[i]; i++){\n");
      out.write("   \t  ele.selected = false;\n");
      out.write("  }\n");
      out.write("  getObject('idTipoBienAll').value=\"false\";\n");
      out.write("} \n");
      out.write("\n");
      out.write("function loadMaterialize(){\n");
      out.write("\t\n");
      out.write("}\n");
      out.write("\n");
      out.write("function msg_guardar() {\n");
      out.write("\talertMaterialize('El sistema le guardara la garantía temporalmente por 72 horas, esto no constituye una inscripci&oacute;n y por lo tanto no otorga prelacion.');\n");
      out.write("\treturn false;\n");
      out.write("}\n");
      out.write("\n");
      out.write("</script>\n");
      out.write("\n");
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
    _jspx_th_s_property_0.setValue("idInscripcion");
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
    _jspx_th_s_property_1.setValue("idInscripcion");
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
    _jspx_th_s_property_2.setValue("idInscripcion");
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
    _jspx_th_s_property_3.setValue("idInscripcion");
    int _jspx_eval_s_property_3 = _jspx_th_s_property_3.doStartTag();
    if (_jspx_th_s_property_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
    return false;
  }

  private boolean _jspx_meth_s_property_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_4 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_4.setPageContext(_jspx_page_context);
    _jspx_th_s_property_4.setParent(null);
    _jspx_th_s_property_4.setValue("idInscripcion");
    int _jspx_eval_s_property_4 = _jspx_th_s_property_4.doStartTag();
    if (_jspx_th_s_property_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
    return false;
  }

  private boolean _jspx_meth_s_property_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_5 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_5.setPageContext(_jspx_page_context);
    _jspx_th_s_property_5.setParent(null);
    _jspx_th_s_property_5.setValue("idInscripcion");
    int _jspx_eval_s_property_5 = _jspx_th_s_property_5.doStartTag();
    if (_jspx_th_s_property_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
    return false;
  }

  private boolean _jspx_meth_s_property_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_6 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_6.setPageContext(_jspx_page_context);
    _jspx_th_s_property_6.setParent(null);
    _jspx_th_s_property_6.setValue("idInscripcion");
    int _jspx_eval_s_property_6 = _jspx_th_s_property_6.doStartTag();
    if (_jspx_th_s_property_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_6);
    return false;
  }

  private boolean _jspx_meth_s_property_7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_7 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_7.setPageContext(_jspx_page_context);
    _jspx_th_s_property_7.setParent(null);
    _jspx_th_s_property_7.setValue("idInscripcion");
    int _jspx_eval_s_property_7 = _jspx_th_s_property_7.doStartTag();
    if (_jspx_th_s_property_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_7);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_7);
    return false;
  }

  private boolean _jspx_meth_s_property_8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_8 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_8.setPageContext(_jspx_page_context);
    _jspx_th_s_property_8.setParent(null);
    _jspx_th_s_property_8.setValue("idInscripcion");
    int _jspx_eval_s_property_8 = _jspx_th_s_property_8.doStartTag();
    if (_jspx_th_s_property_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_8);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_8);
    return false;
  }

  private boolean _jspx_meth_s_property_9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_9 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_9.setPageContext(_jspx_page_context);
    _jspx_th_s_property_9.setParent(null);
    _jspx_th_s_property_9.setValue("idInscripcion");
    int _jspx_eval_s_property_9 = _jspx_th_s_property_9.doStartTag();
    if (_jspx_th_s_property_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_9);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_9);
    return false;
  }
}
