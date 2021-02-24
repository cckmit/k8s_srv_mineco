package org.apache.jsp.WEB_002dINF.jsp.content.proceso;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class misUsuarios_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_required_name_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_size_required_onkeyup_onkeypress_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_size_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_size_required_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_iterator_value;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_set_var_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_property_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_text_var_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_textfield_type_size_required_onkeyup_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_fielderror_fieldName_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_password_size_required_name_maxlength_id_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_password_size_required_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_text_name_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_textfield_required_name_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_size_required_onkeyup_onkeypress_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_size_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_size_required_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_iterator_value = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_property_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_text_var_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_textfield_type_size_required_onkeyup_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_fielderror_fieldName_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_password_size_required_name_maxlength_id_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_password_size_required_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_text_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_textfield_required_name_id_cssClass_nobody.release();
    _jspx_tagPool_s_textfield_size_required_onkeyup_onkeypress_name_maxlength_id_cssClass_nobody.release();
    _jspx_tagPool_s_textfield_size_name_maxlength_id_cssClass_nobody.release();
    _jspx_tagPool_s_textfield_size_required_name_maxlength_id_cssClass_nobody.release();
    _jspx_tagPool_s_iterator_value.release();
    _jspx_tagPool_s_set_var_value_nobody.release();
    _jspx_tagPool_s_property_value_nobody.release();
    _jspx_tagPool_s_text_var_name_nobody.release();
    _jspx_tagPool_s_textfield_type_size_required_onkeyup_name_maxlength_id_cssClass_nobody.release();
    _jspx_tagPool_s_fielderror_fieldName_nobody.release();
    _jspx_tagPool_s_password_size_required_name_maxlength_id_cssClass_nobody.release();
    _jspx_tagPool_s_password_size_required_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.release();
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.release();
    _jspx_tagPool_s_text_name_nobody.release();
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

      out.write('\r');
      out.write('\n');
      if (_jspx_meth_s_set_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"section\"></div>\r\n");
      out.write("<main>\r\n");
      out.write("\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div class=\"col s2\"></div>\r\n");
      out.write("\t\t\t<div class=\"col s8\">\r\n");
      out.write("\t\t\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t\t\t<div class=\"card-content\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col s8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"card-title\">Mis Usuarios</span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col s4 right-align\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"btn btn-large waves-effect indigo modal-trigger\" href=\"#frmUsuario\" id=\"btnAgregar\"><i class=\"material-icons left\">add</i>Agregar</a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"usuarios\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>Nombre</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>Correo</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>Opciones</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_iterator_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col s2\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</main>\r\n");
      out.write("<div class=\"section\"></div>\r\n");
      out.write("<div id=\"frmUsuario\" class=\"modal\">\r\n");
      out.write("\t<div class=\"modal-content\">\r\n");
      out.write("\t\t<div class=\"card\">\r\n");
      out.write("\t\t\t<div class=\"card-content\">\r\n");
      out.write("\t\t\t\t<span class=\"card-title\">Subusuario</span>\r\n");
      out.write("\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t\t<!--s:form namespace=\"usuario\" action=\"save.do\" theme=\"simple\" cssClass=\"col s10\" method=\"post\"-->\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"usuarioModificar\" id=\"usuarioModificar\" />\r\n");
      out.write("\t\t\t\t\t<div class=\"col s10\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_s_textfield_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<label for=\"nombre\">Nombre completo</label>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t        \t");
      if (_jspx_meth_s_textfield_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        \t<label for=\"docId\">Documento de identificación</label>\r\n");
      out.write("\t\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t        \t");
      if (_jspx_meth_s_textfield_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        \t<label for=\"email\">Correo electrónico</label>\r\n");
      out.write("\t\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t    \t\t");
      if (_jspx_meth_s_text_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        \t");
      if (_jspx_meth_s_password_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        \t<label for=\"password\">");
      if (_jspx_meth_s_text_1(_jspx_page_context))
        return;
      out.write("</label>\r\n");
      out.write("\t\t\t\t        \t</div>\r\n");
      out.write("\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t        \t");
      if (_jspx_meth_s_password_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        \t<label for=\"confirmacion\">");
      if (_jspx_meth_s_text_2(_jspx_page_context))
        return;
      out.write("</label>\r\n");
      out.write("\t\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t\t   \t\t");
      if (_jspx_meth_s_fielderror_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t \t<!--div class=\"row\">\r\n");
      out.write("\t\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("    \t\t\t\t\t\t\t");
      if (_jspx_meth_s_select_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        \t<label for=\"pregunta\">");
      if (_jspx_meth_s_text_3(_jspx_page_context))
        return;
      out.write("</label>\r\n");
      out.write("\t\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row\" id=\"otra-pregunta\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t        \t");
      if (_jspx_meth_s_textfield_3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        \t<label for=\"otraPregunta\">Ingrese la pregunta</label>\r\n");
      out.write("\t\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t    \t<div class=\"input-field col s12\">\r\n");
      out.write("\t\t\t\t\t        \t");
      if (_jspx_meth_s_textfield_4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t        \t<label for=\"respuesta\">");
      if (_jspx_meth_s_text_4(_jspx_page_context))
        return;
      out.write("</label>\r\n");
      out.write("\t\t\t\t\t   \t\t</div>\r\n");
      out.write("\t\t\t\t\t   \t\t");
      if (_jspx_meth_s_fielderror_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t \t</div-->\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row note\">\r\n");
      out.write("\t\t\t\t\t\t\t<p>Para la administraci&oacute;n de las subcuentas, usted:\r\n");
      out.write("\t\t\t\t\t\t\t\t<br />1. Ratifica que los t&eacute;rminos y condiciones aceptados al crear su cuenta maestra aplican para cada subcuenta creada desde dicha cuenta;\r\n");
      out.write("\t\t\t\t\t\t\t\t<br />2. Acepta que las operaciones realizadas por el usuario de la subcuenta estarán bajo su completa responsabilidad;\r\n");
      out.write("\t\t\t\t\t\t\t\t<br />3. La creaci&oacute;n, modificaci&oacute;n e inactivaci&oacute;n de las subcuentas asociadas a su cuenta solo podr&aacute; ser realizada con la cuenta maestra.\r\n");
      out.write("\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t \t<div class=\"row center-align\" id=\"error\">\r\n");
      out.write("\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t \t<center>\r\n");
      out.write("\t\t\t\t            <div class='row'>\r\n");
      out.write("\t\t\t\t            \t<a href=\"#\" class=\"btn btn-large waves-effect indigo\" onclick=\"guardaUsuario(");
      if (_jspx_meth_s_property_5(_jspx_page_context))
        return;
      out.write(")\">Aceptar</a>\r\n");
      out.write("\t\t\t\t            </div>\r\n");
      out.write("\t\t\t          \t</center>\r\n");
      out.write("\t\t          \t</div>\r\n");
      out.write("\t\t\t\t\t<!--/s:form-->\r\n");
      out.write("\t\t\t\t\t<div class=\"col s1\"></div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>");
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

  private boolean _jspx_meth_s_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:set
    org.apache.struts2.views.jsp.SetTag _jspx_th_s_set_0 = (org.apache.struts2.views.jsp.SetTag) _jspx_tagPool_s_set_var_value_nobody.get(org.apache.struts2.views.jsp.SetTag.class);
    _jspx_th_s_set_0.setPageContext(_jspx_page_context);
    _jspx_th_s_set_0.setParent(null);
    _jspx_th_s_set_0.setVar("idPersona");
    _jspx_th_s_set_0.setValue("idPersona");
    int _jspx_eval_s_set_0 = _jspx_th_s_set_0.doStartTag();
    if (_jspx_th_s_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_0);
      return true;
    }
    _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_0);
    return false;
  }

  private boolean _jspx_meth_s_iterator_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:iterator
    org.apache.struts2.views.jsp.IteratorTag _jspx_th_s_iterator_0 = (org.apache.struts2.views.jsp.IteratorTag) _jspx_tagPool_s_iterator_value.get(org.apache.struts2.views.jsp.IteratorTag.class);
    _jspx_th_s_iterator_0.setPageContext(_jspx_page_context);
    _jspx_th_s_iterator_0.setParent(null);
    _jspx_th_s_iterator_0.setValue("usuarios");
    int _jspx_eval_s_iterator_0 = _jspx_th_s_iterator_0.doStartTag();
    if (_jspx_eval_s_iterator_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_iterator_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_iterator_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_iterator_0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t\t\t<tr id='");
        if (_jspx_meth_s_property_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_iterator_0, _jspx_page_context))
          return true;
        out.write("'>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<td>");
        if (_jspx_meth_s_property_1((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_iterator_0, _jspx_page_context))
          return true;
        out.write("</td>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<td>");
        if (_jspx_meth_s_property_2((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_iterator_0, _jspx_page_context))
          return true;
        out.write("</td>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t<td>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" class=\"btn waves-effect indigo\" onclick='cargaUsuario(");
        if (_jspx_meth_s_property_3((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_iterator_0, _jspx_page_context))
          return true;
        out.write(")'><i class=\"material-icons\">edit</i></a>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" class=\"btn waves-effect red darken-4\" onclick='eliminarUsuario(");
        if (_jspx_meth_s_property_4((javax.servlet.jsp.tagext.JspTag) _jspx_th_s_iterator_0, _jspx_page_context))
          return true;
        out.write(")'><i class=\"material-icons\">delete</i></a>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
        out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
        out.write("\t\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_s_iterator_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_iterator_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_s_iterator_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_iterator_value.reuse(_jspx_th_s_iterator_0);
      return true;
    }
    _jspx_tagPool_s_iterator_value.reuse(_jspx_th_s_iterator_0);
    return false;
  }

  private boolean _jspx_meth_s_property_0(javax.servlet.jsp.tagext.JspTag _jspx_th_s_iterator_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_0 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_0.setPageContext(_jspx_page_context);
    _jspx_th_s_property_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_iterator_0);
    _jspx_th_s_property_0.setValue("idPersona");
    int _jspx_eval_s_property_0 = _jspx_th_s_property_0.doStartTag();
    if (_jspx_th_s_property_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_0);
    return false;
  }

  private boolean _jspx_meth_s_property_1(javax.servlet.jsp.tagext.JspTag _jspx_th_s_iterator_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_1 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_1.setPageContext(_jspx_page_context);
    _jspx_th_s_property_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_iterator_0);
    _jspx_th_s_property_1.setValue("nombre");
    int _jspx_eval_s_property_1 = _jspx_th_s_property_1.doStartTag();
    if (_jspx_th_s_property_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_1);
    return false;
  }

  private boolean _jspx_meth_s_property_2(javax.servlet.jsp.tagext.JspTag _jspx_th_s_iterator_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_2 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_2.setPageContext(_jspx_page_context);
    _jspx_th_s_property_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_iterator_0);
    _jspx_th_s_property_2.setValue("cveUsuario");
    int _jspx_eval_s_property_2 = _jspx_th_s_property_2.doStartTag();
    if (_jspx_th_s_property_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_2);
    return false;
  }

  private boolean _jspx_meth_s_property_3(javax.servlet.jsp.tagext.JspTag _jspx_th_s_iterator_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_3 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_3.setPageContext(_jspx_page_context);
    _jspx_th_s_property_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_iterator_0);
    _jspx_th_s_property_3.setValue("idPersona");
    int _jspx_eval_s_property_3 = _jspx_th_s_property_3.doStartTag();
    if (_jspx_th_s_property_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_3);
    return false;
  }

  private boolean _jspx_meth_s_property_4(javax.servlet.jsp.tagext.JspTag _jspx_th_s_iterator_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_property_4 = (org.apache.struts2.views.jsp.PropertyTag) _jspx_tagPool_s_property_value_nobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_property_4.setPageContext(_jspx_page_context);
    _jspx_th_s_property_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_iterator_0);
    _jspx_th_s_property_4.setValue("idPersona");
    int _jspx_eval_s_property_4 = _jspx_th_s_property_4.doStartTag();
    if (_jspx_th_s_property_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_4);
    return false;
  }

  private boolean _jspx_meth_s_textfield_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_0 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_required_name_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_0.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_0.setParent(null);
    _jspx_th_s_textfield_0.setName("usuario.nombre");
    _jspx_th_s_textfield_0.setId("nombre");
    _jspx_th_s_textfield_0.setDynamicAttribute(null, "required", new String("true"));
    _jspx_th_s_textfield_0.setCssClass("validate");
    int _jspx_eval_s_textfield_0 = _jspx_th_s_textfield_0.doStartTag();
    if (_jspx_th_s_textfield_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_required_name_id_cssClass_nobody.reuse(_jspx_th_s_textfield_0);
      return true;
    }
    _jspx_tagPool_s_textfield_required_name_id_cssClass_nobody.reuse(_jspx_th_s_textfield_0);
    return false;
  }

  private boolean _jspx_meth_s_textfield_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_1 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_size_required_onkeyup_onkeypress_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_1.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_1.setParent(null);
    _jspx_th_s_textfield_1.setName("usuario.docId");
    _jspx_th_s_textfield_1.setId("docId");
    _jspx_th_s_textfield_1.setDynamicAttribute(null, "required", new String("true"));
    _jspx_th_s_textfield_1.setCssClass("validate");
    _jspx_th_s_textfield_1.setMaxlength("13");
    _jspx_th_s_textfield_1.setSize("40");
    _jspx_th_s_textfield_1.setOnkeyup("this.value = this.value.toUpperCase()");
    _jspx_th_s_textfield_1.setOnkeypress("return aceptaalfa(event);");
    int _jspx_eval_s_textfield_1 = _jspx_th_s_textfield_1.doStartTag();
    if (_jspx_th_s_textfield_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_size_required_onkeyup_onkeypress_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
      return true;
    }
    _jspx_tagPool_s_textfield_size_required_onkeyup_onkeypress_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_1);
    return false;
  }

  private boolean _jspx_meth_s_textfield_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_2 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_type_size_required_onkeyup_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_2.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_2.setParent(null);
    _jspx_th_s_textfield_2.setName("usuario.cveUsuario");
    _jspx_th_s_textfield_2.setId("email");
    _jspx_th_s_textfield_2.setType("email");
    _jspx_th_s_textfield_2.setDynamicAttribute(null, "required", new String("true"));
    _jspx_th_s_textfield_2.setCssClass("validate");
    _jspx_th_s_textfield_2.setMaxlength("256");
    _jspx_th_s_textfield_2.setSize("40");
    _jspx_th_s_textfield_2.setOnkeyup("this.value = this.value.toLowerCase()");
    int _jspx_eval_s_textfield_2 = _jspx_th_s_textfield_2.doStartTag();
    if (_jspx_th_s_textfield_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_type_size_required_onkeyup_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_2);
      return true;
    }
    _jspx_tagPool_s_textfield_type_size_required_onkeyup_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_2);
    return false;
  }

  private boolean _jspx_meth_s_text_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:text
    org.apache.struts2.views.jsp.TextTag _jspx_th_s_text_0 = (org.apache.struts2.views.jsp.TextTag) _jspx_tagPool_s_text_var_name_nobody.get(org.apache.struts2.views.jsp.TextTag.class);
    _jspx_th_s_text_0.setPageContext(_jspx_page_context);
    _jspx_th_s_text_0.setParent(null);
    _jspx_th_s_text_0.setName("usuario.field.password.ayuda");
    _jspx_th_s_text_0.setVar("password.ayuda");
    int _jspx_eval_s_text_0 = _jspx_th_s_text_0.doStartTag();
    if (_jspx_th_s_text_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_text_var_name_nobody.reuse(_jspx_th_s_text_0);
      return true;
    }
    _jspx_tagPool_s_text_var_name_nobody.reuse(_jspx_th_s_text_0);
    return false;
  }

  private boolean _jspx_meth_s_password_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:password
    org.apache.struts2.views.jsp.ui.PasswordTag _jspx_th_s_password_0 = (org.apache.struts2.views.jsp.ui.PasswordTag) _jspx_tagPool_s_password_size_required_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.PasswordTag.class);
    _jspx_th_s_password_0.setPageContext(_jspx_page_context);
    _jspx_th_s_password_0.setParent(null);
    _jspx_th_s_password_0.setName("usuario.password");
    _jspx_th_s_password_0.setId("password");
    _jspx_th_s_password_0.setDynamicAttribute(null, "required", new String("true"));
    _jspx_th_s_password_0.setCssClass("validate tooltipped");
    _jspx_th_s_password_0.setDynamicAttribute(null, "data-position", new String("right"));
    _jspx_th_s_password_0.setDynamicAttribute(null, "data-delay", new String("50"));
    _jspx_th_s_password_0.setDynamicAttribute(null, "data-tooltip", new String("%{password.ayuda}"));
    _jspx_th_s_password_0.setMaxlength("16");
    _jspx_th_s_password_0.setSize("40");
    int _jspx_eval_s_password_0 = _jspx_th_s_password_0.doStartTag();
    if (_jspx_th_s_password_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_password_size_required_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.reuse(_jspx_th_s_password_0);
      return true;
    }
    _jspx_tagPool_s_password_size_required_name_maxlength_id_data$1tooltip_data$1position_data$1delay_cssClass_nobody.reuse(_jspx_th_s_password_0);
    return false;
  }

  private boolean _jspx_meth_s_text_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:text
    org.apache.struts2.views.jsp.TextTag _jspx_th_s_text_1 = (org.apache.struts2.views.jsp.TextTag) _jspx_tagPool_s_text_name_nobody.get(org.apache.struts2.views.jsp.TextTag.class);
    _jspx_th_s_text_1.setPageContext(_jspx_page_context);
    _jspx_th_s_text_1.setParent(null);
    _jspx_th_s_text_1.setName("usuario.field.password");
    int _jspx_eval_s_text_1 = _jspx_th_s_text_1.doStartTag();
    if (_jspx_th_s_text_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_text_name_nobody.reuse(_jspx_th_s_text_1);
      return true;
    }
    _jspx_tagPool_s_text_name_nobody.reuse(_jspx_th_s_text_1);
    return false;
  }

  private boolean _jspx_meth_s_password_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:password
    org.apache.struts2.views.jsp.ui.PasswordTag _jspx_th_s_password_1 = (org.apache.struts2.views.jsp.ui.PasswordTag) _jspx_tagPool_s_password_size_required_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.PasswordTag.class);
    _jspx_th_s_password_1.setPageContext(_jspx_page_context);
    _jspx_th_s_password_1.setParent(null);
    _jspx_th_s_password_1.setName("usuario.confirmacion");
    _jspx_th_s_password_1.setId("confirmacion");
    _jspx_th_s_password_1.setDynamicAttribute(null, "required", new String("true"));
    _jspx_th_s_password_1.setCssClass("validate");
    _jspx_th_s_password_1.setMaxlength("16");
    _jspx_th_s_password_1.setSize("40");
    int _jspx_eval_s_password_1 = _jspx_th_s_password_1.doStartTag();
    if (_jspx_th_s_password_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_password_size_required_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_password_1);
      return true;
    }
    _jspx_tagPool_s_password_size_required_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_password_1);
    return false;
  }

  private boolean _jspx_meth_s_text_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:text
    org.apache.struts2.views.jsp.TextTag _jspx_th_s_text_2 = (org.apache.struts2.views.jsp.TextTag) _jspx_tagPool_s_text_name_nobody.get(org.apache.struts2.views.jsp.TextTag.class);
    _jspx_th_s_text_2.setPageContext(_jspx_page_context);
    _jspx_th_s_text_2.setParent(null);
    _jspx_th_s_text_2.setName("usuario.field.confirmacion");
    int _jspx_eval_s_text_2 = _jspx_th_s_text_2.doStartTag();
    if (_jspx_th_s_text_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_text_name_nobody.reuse(_jspx_th_s_text_2);
      return true;
    }
    _jspx_tagPool_s_text_name_nobody.reuse(_jspx_th_s_text_2);
    return false;
  }

  private boolean _jspx_meth_s_fielderror_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:fielderror
    org.apache.struts2.views.jsp.ui.FieldErrorTag _jspx_th_s_fielderror_0 = (org.apache.struts2.views.jsp.ui.FieldErrorTag) _jspx_tagPool_s_fielderror_fieldName_nobody.get(org.apache.struts2.views.jsp.ui.FieldErrorTag.class);
    _jspx_th_s_fielderror_0.setPageContext(_jspx_page_context);
    _jspx_th_s_fielderror_0.setParent(null);
    _jspx_th_s_fielderror_0.setFieldName("registroUsuario.confirmacion");
    int _jspx_eval_s_fielderror_0 = _jspx_th_s_fielderror_0.doStartTag();
    if (_jspx_th_s_fielderror_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_fielderror_fieldName_nobody.reuse(_jspx_th_s_fielderror_0);
      return true;
    }
    _jspx_tagPool_s_fielderror_fieldName_nobody.reuse(_jspx_th_s_fielderror_0);
    return false;
  }

  private boolean _jspx_meth_s_select_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:select
    org.apache.struts2.views.jsp.ui.SelectTag _jspx_th_s_select_0 = (org.apache.struts2.views.jsp.ui.SelectTag) _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.get(org.apache.struts2.views.jsp.ui.SelectTag.class);
    _jspx_th_s_select_0.setPageContext(_jspx_page_context);
    _jspx_th_s_select_0.setParent(null);
    _jspx_th_s_select_0.setName("usuario.pregRecupera");
    _jspx_th_s_select_0.setId("pregunta");
    _jspx_th_s_select_0.setList("preguntas");
    _jspx_th_s_select_0.setListKey("pregunta");
    _jspx_th_s_select_0.setListValue("pregunta");
    _jspx_th_s_select_0.setOnchange("onChangePregunta()");
    int _jspx_eval_s_select_0 = _jspx_th_s_select_0.doStartTag();
    if (_jspx_th_s_select_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_0);
      return true;
    }
    _jspx_tagPool_s_select_onchange_name_listValue_listKey_list_id_nobody.reuse(_jspx_th_s_select_0);
    return false;
  }

  private boolean _jspx_meth_s_text_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:text
    org.apache.struts2.views.jsp.TextTag _jspx_th_s_text_3 = (org.apache.struts2.views.jsp.TextTag) _jspx_tagPool_s_text_name_nobody.get(org.apache.struts2.views.jsp.TextTag.class);
    _jspx_th_s_text_3.setPageContext(_jspx_page_context);
    _jspx_th_s_text_3.setParent(null);
    _jspx_th_s_text_3.setName("usuario.field.pregunta");
    int _jspx_eval_s_text_3 = _jspx_th_s_text_3.doStartTag();
    if (_jspx_th_s_text_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_text_name_nobody.reuse(_jspx_th_s_text_3);
      return true;
    }
    _jspx_tagPool_s_text_name_nobody.reuse(_jspx_th_s_text_3);
    return false;
  }

  private boolean _jspx_meth_s_textfield_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_3 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_size_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_3.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_3.setParent(null);
    _jspx_th_s_textfield_3.setName("otraPregunta");
    _jspx_th_s_textfield_3.setId("otraPregunta");
    _jspx_th_s_textfield_3.setCssClass("validate");
    _jspx_th_s_textfield_3.setMaxlength("50");
    _jspx_th_s_textfield_3.setSize("40");
    int _jspx_eval_s_textfield_3 = _jspx_th_s_textfield_3.doStartTag();
    if (_jspx_th_s_textfield_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_size_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_3);
      return true;
    }
    _jspx_tagPool_s_textfield_size_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_3);
    return false;
  }

  private boolean _jspx_meth_s_textfield_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:textfield
    org.apache.struts2.views.jsp.ui.TextFieldTag _jspx_th_s_textfield_4 = (org.apache.struts2.views.jsp.ui.TextFieldTag) _jspx_tagPool_s_textfield_size_required_name_maxlength_id_cssClass_nobody.get(org.apache.struts2.views.jsp.ui.TextFieldTag.class);
    _jspx_th_s_textfield_4.setPageContext(_jspx_page_context);
    _jspx_th_s_textfield_4.setParent(null);
    _jspx_th_s_textfield_4.setName("usuario.respRecupera");
    _jspx_th_s_textfield_4.setId("respuesta");
    _jspx_th_s_textfield_4.setDynamicAttribute(null, "required", new String("true"));
    _jspx_th_s_textfield_4.setCssClass("validate");
    _jspx_th_s_textfield_4.setMaxlength("50");
    _jspx_th_s_textfield_4.setSize("40");
    int _jspx_eval_s_textfield_4 = _jspx_th_s_textfield_4.doStartTag();
    if (_jspx_th_s_textfield_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_textfield_size_required_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_4);
      return true;
    }
    _jspx_tagPool_s_textfield_size_required_name_maxlength_id_cssClass_nobody.reuse(_jspx_th_s_textfield_4);
    return false;
  }

  private boolean _jspx_meth_s_text_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:text
    org.apache.struts2.views.jsp.TextTag _jspx_th_s_text_4 = (org.apache.struts2.views.jsp.TextTag) _jspx_tagPool_s_text_name_nobody.get(org.apache.struts2.views.jsp.TextTag.class);
    _jspx_th_s_text_4.setPageContext(_jspx_page_context);
    _jspx_th_s_text_4.setParent(null);
    _jspx_th_s_text_4.setName("usuario.field.respuesta");
    int _jspx_eval_s_text_4 = _jspx_th_s_text_4.doStartTag();
    if (_jspx_th_s_text_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_text_name_nobody.reuse(_jspx_th_s_text_4);
      return true;
    }
    _jspx_tagPool_s_text_name_nobody.reuse(_jspx_th_s_text_4);
    return false;
  }

  private boolean _jspx_meth_s_fielderror_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:fielderror
    org.apache.struts2.views.jsp.ui.FieldErrorTag _jspx_th_s_fielderror_1 = (org.apache.struts2.views.jsp.ui.FieldErrorTag) _jspx_tagPool_s_fielderror_fieldName_nobody.get(org.apache.struts2.views.jsp.ui.FieldErrorTag.class);
    _jspx_th_s_fielderror_1.setPageContext(_jspx_page_context);
    _jspx_th_s_fielderror_1.setParent(null);
    _jspx_th_s_fielderror_1.setFieldName("registroUsuario.respuesta");
    int _jspx_eval_s_fielderror_1 = _jspx_th_s_fielderror_1.doStartTag();
    if (_jspx_th_s_fielderror_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_fielderror_fieldName_nobody.reuse(_jspx_th_s_fielderror_1);
      return true;
    }
    _jspx_tagPool_s_fielderror_fieldName_nobody.reuse(_jspx_th_s_fielderror_1);
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
    _jspx_th_s_property_5.setValue("idPersona");
    int _jspx_eval_s_property_5 = _jspx_th_s_property_5.doStartTag();
    if (_jspx_th_s_property_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
      return true;
    }
    _jspx_tagPool_s_property_value_nobody.reuse(_jspx_th_s_property_5);
    return false;
  }
}
