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

public final class Prorroga_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/WEB-INF/jsp/Layout/menu/applicationCtx.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_textfield_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_textfield_name_id_nobody.release();
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
      out.write("<script type=\"text/javascript\" \r\n");
      out.write("\tsrc=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/material-dialog.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/partesJS/parteJS.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var anioModificado;\r\n");
      out.write("var diaModificado ;\r\n");
      out.write("var mesModificado;\r\n");
      out.write("var menos;\r\n");
      out.write("\r\n");
      out.write("function IsNumber(evt) {\r\n");
      out.write("\t var key = (document.all) ? evt.keyCode : evt.which;\r\n");
      out.write("\t if (key == 13)\r\n");
      out.write("\t {  \r\n");
      out.write("\t  validaVigencia();\r\n");
      out.write("\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t return (key == 45 || key <= 13 || (key >= 48 && key <= 57));\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("function loadValues(){\r\n");
      out.write("\tanioModificado= parseInt(getObject('anio').value,10);\r\n");
      out.write("\tdiaModificado = parseInt(getObject('dia').value,10);\r\n");
      out.write("\tmesModificado = parseInt(getObject('mes').value,10);\r\n");
      out.write("//\tdocument.getElementById('meses').focus();\r\n");
      out.write("\tmenos = false;\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function sendForm(){\r\n");
      out.write("\tdocument.getElementById(\"bFirmar\").value = \"Enviando\";\r\n");
      out.write("\tdocument.getElementById(\"bFirmar\").disabled = true;\r\n");
      out.write("\tgetObject('prorrogaGuarda').submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function validaVigencia(){\t\r\n");
      out.write("\r\n");
      out.write("\tvar vigAct = '");
      if (_jspx_meth_s_property_0(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\tvar vigNew = document.getElementById(\"slider1\").value;\r\n");
      out.write("\t\r\n");
      out.write("\tvar meses = parseInt(vigNew) - parseInt(vigAct);\r\n");
      out.write("\t\t\r\n");
      out.write("\tdocument.getElementById(\"meses\").value = meses;\r\n");
      out.write("\t\t\r\n");
      out.write("\t// obtener el costo de una renovacion o reduccion: tipo_tramite=9\r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t\turl: '");
      out.print( request.getContextPath() );
      out.write("/rs/tipos-tramite/9',\r\n");
      out.write("\t\tsuccess: function(result) {\r\n");
      out.write("\t\t\tMaterialDialog.dialog(\r\n");
      out.write("\t\t\t\t\"El costo de una \" + result.descripcion + \" es de Q. \" + (Math.round(result.precio * 100) / 100).toFixed(2) + \", ¿está seguro que desea continuar?\",\r\n");
      out.write("\t\t\t\t{\t\t\t\t\r\n");
      out.write("\t\t\t\t\ttitle:'<table><tr><td width=\"10%\"><i class=\"medium icon-green material-icons\">check_circle</i></td><td style=\"vertical-align: middle; text-align:left;\">Confirmar</td></tr></table>', // Modal title\r\n");
      out.write("\t\t\t\t\tbuttons:{\r\n");
      out.write("\t\t\t\t\t\t// Use by default close and confirm buttons\r\n");
      out.write("\t\t\t\t\t\tclose:{\r\n");
      out.write("\t\t\t\t\t\t\tclassName:\"red\",\r\n");
      out.write("\t\t\t\t\t\t\ttext:\"cancelar\"\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\tconfirm:{\r\n");
      out.write("\t\t\t\t\t\t\tclassName:\"indigo\",\r\n");
      out.write("\t\t\t\t\t\t\ttext:\"aceptar\",\r\n");
      out.write("\t\t\t\t\t\t\tmodalClose:false,\r\n");
      out.write("\t\t\t\t\t\t\tcallback:function(){\r\n");
      out.write("\t\t\t\t\t\t\t\tsendForm();\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function getMeses(){\r\n");
      out.write("\tvar dia = parseInt (getObject('dia').value,10);\r\n");
      out.write("\tvar mes = parseInt (getObject('mes').value,10);\r\n");
      out.write("\tvar anio = parseInt (getObject('anio').value,10);\r\n");
      out.write("\tvar fechaActual = new Date();\r\n");
      out.write("\tvar diaActual= parseInt(fechaActual.getDate(),10);\r\n");
      out.write("\tvar mesActual= parseInt(fechaActual.getMonth(),10) + 1;\r\n");
      out.write("\tvar anioActual= parseInt (fechaActual.getFullYear(),10);\r\n");
      out.write("\tnumMeses = (((anio*12) + mes) - ((anioActual*12) + mesActual));\r\n");
      out.write("    if (dia <= diaActual){\r\n");
      out.write("\t    numMeses = numMeses - 1;\r\n");
      out.write("\t  }\r\n");
      out.write("\t return numMeses;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function aumentar(){\r\n");
      out.write("\tvar mes = parseInt(getObject('meses').value,10);\r\n");
      out.write("\tmes= mes + 1;\r\n");
      out.write("\tgetObject('meses').value = mes;\r\n");
      out.write("\tgetObject('vigenciaM').value = parseInt (getObject('vigencia').value,10) + mes;\r\n");
      out.write("\tif (mes>=0){\r\n");
      out.write("\t\taumentaMes();\r\n");
      out.write("\t\t}\r\n");
      out.write("\telse{\r\n");
      out.write("\t\tdisminuirMes();\r\n");
      out.write("\t\t}\r\n");
      out.write("\tdocument.getElementById('meses').focus();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function disminuir(){\r\n");
      out.write("\tvar mes = parseInt (getObject('meses').value,10);\r\n");
      out.write("\tif ( (mes*(-1)) <= getMeses()){\r\n");
      out.write("\t\tmes= mes - 1;\r\n");
      out.write("\t\tgetObject('meses').value = mes;\r\n");
      out.write("\t\tgetObject('vigenciaM').value = (parseInt (getObject('vigencia').value,10) + mes );\r\n");
      out.write("\t\tif (mes>=0){\r\n");
      out.write("\t\t\taumentaMes();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\telse{\t\r\n");
      out.write("\t\t\tdisminuirMes();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\tdocument.getElementById('meses').focus();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function pasarValor(){\r\n");
      out.write("\tvar dia = parseInt (getObject('dia').value,10);\r\n");
      out.write("\tvar mes = parseInt (getObject('mes').value,10);\r\n");
      out.write("\tvar anio = parseInt (getObject('anio').value,10);\r\n");
      out.write("\tvar mesesSum =parseInt (getObject('meses').value,10);\r\n");
      out.write("if(mesesSum<=9999){\r\n");
      out.write("\tif (mesesSum >=0){\r\n");
      out.write("\t\t\tgetObject('vigenciaM').value = (parseInt (getObject('vigencia').value,10) + mesesSum);\r\n");
      out.write("\t\t\taumentaMes();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\telse{\r\n");
      out.write("\t\t\tif ( (mesesSum*(-1)) <= getMeses()){\r\n");
      out.write("\t\t\t\tgetObject('vigenciaM').value = (parseInt (getObject('vigencia').value,10) + mesesSum);\r\n");
      out.write("\t\t\t\tdisminuirMes();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse{\r\n");
      out.write("\t\t\t\tdisplayAlert(true,'Vigencia','Debe introducir una vigencia que sea mayor a la fecha actual');\r\n");
      out.write("\t\t\t\tgetObject('meses').value =0;\r\n");
      out.write("\t\t\t\tgetObject('vigenciaM').value = getObject('vigencia').value;\r\n");
      out.write("\t\t\t\tgetObject('fechaFinM').value = armaFecha(dia, mes, anio); \r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("}else{\r\n");
      out.write("\tdisplayAlert(true, 'Vigencia', 'Su vigencia solo puede contener 4 digitos');\r\n");
      out.write("}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function cerosIzq(sVal, nPos){\r\n");
      out.write("    var sRes = sVal;\r\n");
      out.write("    for (var i = sVal.length; i < nPos; i++)\r\n");
      out.write("     sRes = \"0\" + sRes;\r\n");
      out.write("    return sRes;\r\n");
      out.write("   }\r\n");
      out.write(" \r\n");
      out.write("   function armaFecha(nDia, nMes, nAno){\r\n");
      out.write("    var sRes = cerosIzq(String(nDia), 2);\r\n");
      out.write("    sRes = sRes + \"/\" + cerosIzq(String(nMes), 2);\r\n");
      out.write("    sRes = sRes + \"/\" + cerosIzq(String(nAno), 4);\r\n");
      out.write("    return sRes;\r\n");
      out.write("   }\r\n");
      out.write("   \r\n");
      out.write("   function esBisiesto(year){\r\n");
      out.write("\t\treturn ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? true : false;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("   \r\n");
      out.write("   function aumentaMes(){\r\n");
      out.write("\t\tvar mesesSum = parseInt (getObject('meses').value,10);\r\n");
      out.write("\t\tvar dia = parseInt (getObject('dia').value,10);\r\n");
      out.write("\t\tvar mes = parseInt (getObject('mes').value,10);\r\n");
      out.write("\t\tvar anio = parseInt (getObject('anio').value,10);\r\n");
      out.write("\t\tvar myDate=new Date();\r\n");
      out.write("\t\tmyDate.setFullYear(anio,mes-1,dia);\r\n");
      out.write("\t//Agregar meses\r\n");
      out.write("\t\tmyDate.setMonth(myDate.getMonth()+mesesSum);\r\n");
      out.write("\t\tgetObject('fechaFinM').value = armaFecha(myDate.getDate(), parseInt(myDate.getMonth(),10)+1, myDate.getFullYear());\r\n");
      out.write("\t\tanioModificado=anio;\t\r\n");
      out.write("\t}\r\n");
      out.write("   \r\n");
      out.write("   function disminuirMes(){\r\n");
      out.write("\t\tvar mesesSum = parseInt (getObject('meses').value,10);\r\n");
      out.write("\t\tvar dia = parseInt (getObject('dia').value,10);\r\n");
      out.write("\t\tvar mes = parseInt (getObject('mes').value,10);\r\n");
      out.write("\t\tvar anio = parseInt (getObject('anio').value,10);\r\n");
      out.write("\t\tvar myDate=new Date();\r\n");
      out.write("\t\tmyDate.setFullYear(anio,mes-1,dia);\r\n");
      out.write("\t//Quitar meses\r\n");
      out.write("\t\r\n");
      out.write("\t\tmyDate.setMonth(myDate.getMonth()-Math.abs(mesesSum));\r\n");
      out.write("\t\r\n");
      out.write("\t\tgetObject('fechaFinM').value = armaFecha(myDate.getDate(), parseInt(myDate.getMonth(),10)+1, myDate.getFullYear());\r\n");
      out.write("\t\tanioModificado=anio;\t\r\n");
      out.write("\t}   \r\n");
      out.write("   \r\n");
      out.write("   function actualizarFecha() {\r\n");
      out.write("\t   var mesesSum = parseInt (getObject('meses').value,10);\r\n");
      out.write("\t   \r\n");
      out.write("\t   if(mesesSum >3) {\r\n");
      out.write("\t\t   alertMaterialize('No puede exceder los 3 años');\r\n");
      out.write("\t\t   return false;\r\n");
      out.write("\t   }\r\n");
      out.write("\t   if(mesesSum <=0) {\r\n");
      out.write("\t\t   alertMaterialize('No puede ser menor o igual a 0 años');\r\n");
      out.write("\t\t   return false;\r\n");
      out.write("\t   }\r\n");
      out.write("\t   var dia = parseInt (getObject('dia').value,10);\r\n");
      out.write("\t   var mes = parseInt (getObject('mes').value,10);\r\n");
      out.write("\t   var anio = parseInt (getObject('anio').value,10);\r\n");
      out.write("\t   \r\n");
      out.write("\t   var myDate = new Date();\r\n");
      out.write("\t   myDate.setFullYear(anio+mesesSum,mes,dia);\r\n");
      out.write("\t   document.getElementById('fechaFinal').innerHTML =  armaFecha(myDate.getDate(), myDate.getMonth(), myDate.getFullYear());\r\n");
      out.write("   }\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	ApplicationContext ctx = null;
	if (ctx == null) {
		ctx = new ClassPathXmlApplicationContext(Constants.SEGURIDAD_APP_CONTEXT);
	}

      out.write("\r\n");
      out.write("<main>\r\n");

//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();

      out.write("\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div id=\"menuh\">\r\n");
      out.write("\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t");

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
      out.write("\r\n");
      out.write("\t\t\t\t\t");

						}
					}
						
					
      out.write("\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div class=\"col s12\"><div class=\"card\">\r\n");
      out.write("\t\t\t\t<div class=\"col s2\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"col s8\">\r\n");
      out.write("\t\t\t\t\t<form id=\"prorrogaGuarda\" name=\"prorrogaGuarda\" action=\"prorrogaGuarda.do\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"card-title\">Vigencia de la Inscripci&oacute;n</span>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row note\">\r\n");
      out.write("\t\t\t\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span>La inscripci&oacute;n en el Registro tendr&aacute; vigencia por un plazo de 5 a&ntilde;os, renovable por per&iacute;odos de tres a&ntilde;os. </span>\r\n");
      out.write("\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"blue-text text-darken-2\">Garant&iacute;a\r\n");
      out.write("\t\t\t\t\t\t\t\t\tMobiliaria No. </span> <span> ");
      if (_jspx_meth_s_property_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span>Su Garant&iacute;a Mobiliaria se encuentra inscrita por <span class=\"blue-text text-darken-4\">");
      if (_jspx_meth_s_property_2(_jspx_page_context))
        return;
      out.write(" </span>&nbsp;\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\ta&ntilde;o (s) , por lo tanto vence en la fecha <span class=\"blue-text text-darken-4\"> ");
      if (_jspx_meth_s_property_3(_jspx_page_context))
        return;
      out.write("</span></span>\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" id=\"slider1\" class=\"slider\">\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t <hr />\r\n");
      out.write("\t\t\t\t \t<center>\r\n");
      out.write("\t\t\t            <div class='row'>\t\t\t            \t\r\n");
      out.write("\t\t\t            \t<input type=\"button\" id=\"bFirmar\" name=\"button\" class=\"btn btn-large waves-effect indigo\" value=\"Aceptar\" onclick=\"validaVigencia();\"/>\t\t\t            \t\t\t\t            \t\t\t\t\t\t\t            \t\r\n");
      out.write("\t\t\t            </div>\r\n");
      out.write("\t\t          \t</center>\t\r\n");
      out.write("\t\t          \t<div style=\"visibility: hidden;\">\r\n");
      out.write("\t\t          \t\t");
      if (_jspx_meth_s_textfield_0(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_s_textfield_1(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_s_textfield_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<input type=\"hidden\" id=\"meses\" name=\"meses\" />\r\n");
      out.write("\t\t          \t</div>\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col s2\"></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</main>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/rSlider.min.css\">\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-3.3.1.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/rSlider.min.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("\tvar vig = '");
      if (_jspx_meth_s_property_4(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\tvar fechaFin = '");
      if (_jspx_meth_s_property_5(_jspx_page_context))
        return;
      out.write("';\r\n");
      out.write("\tvar yearFin = fechaFin.substring(6);\r\n");
      out.write("\t\r\n");
      out.write("\tvar today = new Date();\r\n");
      out.write("\tvar yyyy = today.getFullYear();\r\n");
      out.write("\tvar past = parseInt(yearFin) - parseInt(yyyy);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\tvar max = parseInt(vig) + parseInt(\"3\");\r\n");
      out.write("\tvar min = parseInt(vig) - parseInt(past) + 1;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\tdocument.getElementById(\"slider1\").value = vig;\r\n");
      out.write("\tdocument.getElementById(\"slider1\").max = max;\r\n");
      out.write("\t\r\n");
      out.write("\tvar numbers = [min];\r\n");
      out.write("\tfor(i=min; i<max; i++){\r\n");
      out.write("\t\tnumbers.push(i+1);\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\tvar sets = [parseInt(vig)];\t\t\r\n");
      out.write("\t\r\n");
      out.write("\t//alert(sets);\r\n");
      out.write("\r\n");
      out.write("\tvar mySlider = new rSlider({\r\n");
      out.write("    target: '#slider1',\r\n");
      out.write("\tvalues: numbers,\r\n");
      out.write("\tset: sets,\r\n");
      out.write("    range: false, // range slider,\t\r\n");
      out.write("\tstep: 1,\r\n");
      out.write("\twidth:  600\t\t\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("loadValues();\r\n");
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

  private boolean _jspx_meth_s_property_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_0 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_0.setPageContext(_jspx_page_context);
    _jspx_th_s_property_0.setParent(null);
    _jspx_th_s_property_0.setValue("vigencia");
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
    _jspx_th_s_property_1.setValue("idGarantia");
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
    _jspx_th_s_property_2.setValue("vigencia");
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
    _jspx_th_s_property_3.setValue("fechaFin");
    int _jspx_eval_s_property_3 = _jspx_th_s_property_3.doStartTag();
    if (_jspx_th_s_property_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
    return false;
  }

  private boolean _jspx_meth_s_textfield_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_0 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_id_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_0.setParent(null);
    _jspx_th_s_textfield_0.setName("dia");
    _jspx_th_s_textfield_0.setId("dia");
    int _jspx_eval_s_textfield_0 = _jspx_th_s_textfield_0.doStartTag();
    if (_jspx_th_s_textfield_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_id_nobody.reuse(_jspx_th_s_textfield_0);
      return true;
    }
    _jspx_tagPool_s_textfield_name_id_nobody.reuse(_jspx_th_s_textfield_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_1 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_id_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_1.setParent(null);
    _jspx_th_s_textfield_1.setName("mes");
    _jspx_th_s_textfield_1.setId("mes");
    int _jspx_eval_s_textfield_1 = _jspx_th_s_textfield_1.doStartTag();
    if (_jspx_th_s_textfield_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_id_nobody.reuse(_jspx_th_s_textfield_1);
      return true;
    }
    _jspx_tagPool_s_textfield_name_id_nobody.reuse(_jspx_th_s_textfield_1);
    return false;
  }

  private boolean _jspx_meth_s_textfield_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_2 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_name_id_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_2.setParent(null);
    _jspx_th_s_textfield_2.setName("anio");
    _jspx_th_s_textfield_2.setId("anio");
    int _jspx_eval_s_textfield_2 = _jspx_th_s_textfield_2.doStartTag();
    if (_jspx_th_s_textfield_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_name_id_nobody.reuse(_jspx_th_s_textfield_2);
      return true;
    }
    _jspx_tagPool_s_textfield_name_id_nobody.reuse(_jspx_th_s_textfield_2);
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
    _jspx_th_s_property_4.setValue("vigencia");
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
    _jspx_th_s_property_5.setValue("fechaFin");
    int _jspx_eval_s_property_5 = _jspx_th_s_property_5.doStartTag();
    if (_jspx_th_s_property_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
    return false;
  }
}
