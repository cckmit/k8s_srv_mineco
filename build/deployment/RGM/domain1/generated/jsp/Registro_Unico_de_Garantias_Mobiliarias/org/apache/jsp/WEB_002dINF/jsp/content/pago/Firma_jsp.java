package org.apache.jsp.WEB_002dINF.jsp.content.pago;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import mx.gob.se.rug.constants.Constants;
import mx.gob.se.rug.seguridad.dao.PrivilegiosDAO;
import mx.gob.se.rug.seguridad.to.PrivilegiosTO;
import mx.gob.se.rug.to.UsuarioTO;
import java.util.Map;
import mx.gob.se.rug.seguridad.to.PrivilegioTO;

public final class Firma_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_sj_head_jqueryui_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_hidden_value_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_form_theme_id_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_sj_submit_value_timeout_targets_onErrorTopics_onCompleteTopics_onBeforeTopics_indicator_effectOptions_effectDuration_effect_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_sj_head_jqueryui_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_hidden_value_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_form_theme_id_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_sj_submit_value_timeout_targets_onErrorTopics_onCompleteTopics_onBeforeTopics_indicator_effectOptions_effectDuration_effect_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_sj_head_jqueryui_nobody.release();
    _jspx_tagPool_s_hidden_value_name_id_nobody.release();
    _jspx_tagPool_s_form_theme_id_action.release();
    _jspx_tagPool_sj_submit_value_timeout_targets_onErrorTopics_onCompleteTopics_onBeforeTopics_indicator_effectOptions_effectDuration_effect_nobody.release();
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<main> \r\n");
      out.write("\t");
      if (_jspx_meth_sj_head_0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');

 	//Privilegios
 	PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
 	PrivilegiosTO privilegiosTO = new PrivilegiosTO();
 	privilegiosTO.setIdRecurso(new Integer(5));
 	privilegiosTO = privilegiosDAO.getPrivilegios(privilegiosTO,
 			(UsuarioTO) session.getAttribute(Constants.USUARIO));
 	Map<Integer, PrivilegioTO> priv = privilegiosTO.getMapPrivilegio();

 	privilegiosTO.setIdRecurso(new Integer(3));
 	Map<Integer, PrivilegioTO> priv2 = privilegiosDAO
 			.getPrivilegios(privilegiosTO, (UsuarioTO) session.getAttribute(Constants.USUARIO))
 			.getMapPrivilegio();

 	String domainName = new String(request.getRequestURL()).replace(request.getRequestURI(), "");
 	String urlWebserviceFirmaTramites = domainName + "/WebserviceGeneraTramiteXML";
 	String urlSuccessFulGoto = domainName + request.getContextPath() + request.getAttribute("urlBack");
 	UsuarioTO usuarioTO = (UsuarioTO) session.getAttribute(Constants.USUARIO);
 	String urlBaseCode = request.getContextPath() + "/resources/applet/";
 	Integer idTramiteNuevo = (Integer) session.getAttribute(Constants.ID_TRAMITE_NUEVO);
 	Integer idTipoTramiteNuevo = (Integer) request.getAttribute("idTipoTramite");

 	request.removeAttribute("urlBack");
 	request.removeAttribute("idTipoTramite");
 	//session.removeAttribute(Constants.ID_TRAMITE_NUEVO);
 
      out.write(' ');

 	String cadenaOriginal = (String) request.getAttribute("cadenaOriginal");
 	if (cadenaOriginal != null && cadenaOriginal.length() > 5) {
 
      out.write("\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t\t<div class=\"col s12\">\r\n");
      out.write("\t\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t\t<div class=\"col s2\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"col s8\">\t\r\n");
      out.write("\t\t\t\t\t<span class=\"card-title\">Confirmaci&oacute;n Electr&oacute;nica</span>\r\n");
      out.write("\t\t\t\t\t<div class=\"row note\">\r\n");
      out.write("\t\t\t\t\t\t<p>Con su confirmaci&oacute;n electrónica,\r\n");
      out.write("\t\t\t\t\t\tusted, bajo protesta de decir verdad:</b> <br />1. Ratifica todos y\r\n");
      out.write("\t\t\t\t\t\tcada uno de los Términos y Condiciones que aceptó al momento de\r\n");
      out.write("\t\t\t\t\t\tregistrarse como usuario del sistema del Registro de Garant&iacute;as Mobiliarias\r\n");
      out.write("\t\t\t\t\t\t; <br />2. Reconoce la existencia y veracidad de la\r\n");
      out.write("\t\t\t\t\t\tinformación ingresada o trasladada; y <br />3. Manifiesta que conoce la\r\n");
      out.write("\t\t\t\t\t\tresponsabilidad civil, administrativa y penal en que podría\r\n");
      out.write("\t\t\t\t\t\tincurrir en caso de falsedad. </p>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t");
      //  s:form
      org.apache.struts2.views.jsp.ui.FormTag _jspx_th_s_form_0 = (org.apache.struts2.views.jsp.ui.FormTag) _jspx_tagPool_s_form_theme_id_action.get(org.apache.struts2.views.jsp.ui.FormTag.class);
      _jspx_th_s_form_0.setPageContext(_jspx_page_context);
      _jspx_th_s_form_0.setParent(null);
      _jspx_th_s_form_0.setAction("firmaGuarda.do");
      _jspx_th_s_form_0.setTheme("simple");
      _jspx_th_s_form_0.setId("b64Form");
      int _jspx_eval_s_form_0 = _jspx_th_s_form_0.doStartTag();
      if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_s_form_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_s_form_0.doInitBody();
        }
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t\t");
          if (_jspx_meth_s_hidden_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
            return;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t");
          if (_jspx_meth_s_hidden_1((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
            return;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"idTramite\"\r\n");
          out.write("\t\t\t\t\t\t\tvalue=\"");
          out.print(request.getAttribute("idTramite"));
          out.write("\" />\r\n");
          out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"cadenaOriginal\"\r\n");
          out.write("\t\t\t\t\t\t\tvalue=\"");
          out.print(request.getAttribute("cadenaOriginal"));
          out.write("\" />\r\n");
          out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"idTipoTramite\"\r\n");
          out.write("\t\t\t\t\t\t\tvalue=\"");
          out.print(request.getAttribute("idTipoTramite"));
          out.write("\" />\r\n");
          out.write("\t\t\t\t\t\t<center>\r\n");
          out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
          out.write("\t\t\t\t\t\t\t\t<div class=\"btn btn-large waves-effect indigo\" id=\"btnFirma\">\t\t\t\t\t\t\t\t\t\r\n");
          out.write("\t\t\t\t\t\t\t\t\t");
          if (_jspx_meth_sj_submit_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_form_0, _jspx_page_context))
            return;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t\t</div>\r\n");
          out.write("\t\t\t\t\t\t</center>\r\n");
          out.write("\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_s_form_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_s_form_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_s_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_s_form_theme_id_action.reuse(_jspx_th_s_form_0);
        return;
      }
      _jspx_tagPool_s_form_theme_id_action.reuse(_jspx_th_s_form_0);
      out.write("\r\n");
      out.write("\t\t\t\t\t<img id=\"indicator\" src=\"");
      out.print(request.getContextPath());
      out.write("/resources/imgs/loader/loadingAnimation.gif\" alt=\"Loading...\" style=\"display:none\"/>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col s2\"></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div> \r\n");

	} else {

      out.write("\r\n");
      out.write("\tNo se pudo generar la cadena original\r\n");

	}

      out.write("\r\n");
      out.write("</main> \r\n");
      out.write(" \r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t$.subscribe('before', function(event,data) {\r\n");
      out.write("\t\t  var fData = event.originalEvent.formData;\r\n");
      out.write("\t\t alert('About to submit: \\n\\n' + fData[0].value + ' to target '+event.originalEvent.options.target+' with timeout '+event.originalEvent.options.timeout );\r\n");
      out.write("\t\t  var form = event.originalEvent.form[0];\r\n");
      out.write("\t\t  if (form.echo.value.length < 2) {\r\n");
      out.write("\t\t  alert('Please enter a value with min 2 characters');\t  \r\n");
      out.write("\t\t  event.originalEvent.options.submit = false;\r\n");
      out.write("\t\t  }\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$.subscribe('complete', function(event,data) {\r\n");
      out.write("\t\t alert('status: ' + event.originalEvent.status + '\\n\\nresponseText: \\n' + event.originalEvent.request.responseText + \r\n");
      out.write("\t\t '\\n\\nThe output div should have already been updated with the responseText.');\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$.subscribe('errorState', function(event,data) {\r\n");
      out.write("\t\talert('status: ' + event.originalEvent.status + '\\n\\nrequest status: ' +event.originalEvent.request.status);\r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>  \r\n");
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

  private boolean _jspx_meth_sj_head_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sj:head
    com.jgeppert.struts2.jquery.views.jsp.ui.HeadTag _jspx_th_sj_head_0 = (com.jgeppert.struts2.jquery.views.jsp.ui.HeadTag) _jspx_tagPool_sj_head_jqueryui_nobody.get(com.jgeppert.struts2.jquery.views.jsp.ui.HeadTag.class);
    _jspx_th_sj_head_0.setPageContext(_jspx_page_context);
    _jspx_th_sj_head_0.setParent(null);
    _jspx_th_sj_head_0.setJqueryui("true");
    int _jspx_eval_sj_head_0 = _jspx_th_sj_head_0.doStartTag();
    if (_jspx_th_sj_head_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_sj_head_jqueryui_nobody.reuse(_jspx_th_sj_head_0);
      return true;
    }
    _jspx_tagPool_sj_head_jqueryui_nobody.reuse(_jspx_th_sj_head_0);
    return false;
  }

  private boolean _jspx_meth_s_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:hidden
    org.apache.struts2.views.jsp.ui.HiddenTag _jspx_th_s_hidden_0 = (org.apache.struts2.views.jsp.ui.HiddenTag) _jspx_tagPool_s_hidden_value_name_id_nobody.get(org.apache.struts2.views.jsp.ui.HiddenTag.class);
    _jspx_th_s_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_s_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_hidden_0.setId("idBase64UserSign");
    _jspx_th_s_hidden_0.setName("base64UserSign");
    _jspx_th_s_hidden_0.setValue("");
    int _jspx_eval_s_hidden_0 = _jspx_th_s_hidden_0.doStartTag();
    if (_jspx_th_s_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_hidden_value_name_id_nobody.reuse(_jspx_th_s_hidden_0);
      return true;
    }
    _jspx_tagPool_s_hidden_value_name_id_nobody.reuse(_jspx_th_s_hidden_0);
    return false;
  }

  private boolean _jspx_meth_s_hidden_1(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:hidden
    org.apache.struts2.views.jsp.ui.HiddenTag _jspx_th_s_hidden_1 = (org.apache.struts2.views.jsp.ui.HiddenTag) _jspx_tagPool_s_hidden_value_name_id_nobody.get(org.apache.struts2.views.jsp.ui.HiddenTag.class);
    _jspx_th_s_hidden_1.setPageContext(_jspx_page_context);
    _jspx_th_s_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_s_hidden_1.setId("idBase64UserCertificate");
    _jspx_th_s_hidden_1.setName("base64UserCertificate");
    _jspx_th_s_hidden_1.setValue("");
    int _jspx_eval_s_hidden_1 = _jspx_th_s_hidden_1.doStartTag();
    if (_jspx_th_s_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_hidden_value_name_id_nobody.reuse(_jspx_th_s_hidden_1);
      return true;
    }
    _jspx_tagPool_s_hidden_value_name_id_nobody.reuse(_jspx_th_s_hidden_1);
    return false;
  }

  private boolean _jspx_meth_sj_submit_0(javax.servlet.jsp.tagext.JspTag _jspx_th_s_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  sj:submit
    com.jgeppert.struts2.jquery.views.jsp.ui.SubmitTag _jspx_th_sj_submit_0 = (com.jgeppert.struts2.jquery.views.jsp.ui.SubmitTag) _jspx_tagPool_sj_submit_value_timeout_targets_onErrorTopics_onCompleteTopics_onBeforeTopics_indicator_effectOptions_effectDuration_effect_nobody.get(com.jgeppert.struts2.jquery.views.jsp.ui.SubmitTag.class);
    _jspx_th_sj_submit_0.setPageContext(_jspx_page_context);
    _jspx_th_sj_submit_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_form_0);
    _jspx_th_sj_submit_0.setTargets("result");
    _jspx_th_sj_submit_0.setValue("Confirmar");
    _jspx_th_sj_submit_0.setTimeout("2500");
    _jspx_th_sj_submit_0.setIndicator("indicator");
    _jspx_th_sj_submit_0.setOnBeforeTopics("before");
    _jspx_th_sj_submit_0.setOnCompleteTopics("complete");
    _jspx_th_sj_submit_0.setOnErrorTopics("errorState");
    _jspx_th_sj_submit_0.setEffect("highlight");
    _jspx_th_sj_submit_0.setEffectOptions("{ color : '#222222' }");
    _jspx_th_sj_submit_0.setEffectDuration("3000");
    int _jspx_eval_sj_submit_0 = _jspx_th_sj_submit_0.doStartTag();
    if (_jspx_th_sj_submit_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_sj_submit_value_timeout_targets_onErrorTopics_onCompleteTopics_onBeforeTopics_indicator_effectOptions_effectDuration_effect_nobody.reuse(_jspx_th_sj_submit_0);
      return true;
    }
    _jspx_tagPool_sj_submit_value_timeout_targets_onErrorTopics_onCompleteTopics_onBeforeTopics_indicator_effectOptions_effectDuration_effect_nobody.reuse(_jspx_th_sj_submit_0);
    return false;
  }
}
