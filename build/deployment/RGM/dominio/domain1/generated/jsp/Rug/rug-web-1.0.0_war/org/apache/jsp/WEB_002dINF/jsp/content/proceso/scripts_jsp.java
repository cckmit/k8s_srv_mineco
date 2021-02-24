package org.apache.jsp.WEB_002dINF.jsp.content.proceso;

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

      out.write("\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\"    src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.servletContext.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/dwr/interface/UsuarioDwrAction.js\"></script>\n");
      out.write("<!-- <script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/validator.min.js\"></script> -->\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/validate.js\"></script>\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write(" \n");
      out.write("    \n");
      out.write("function onChangePregunta() {\n");
      out.write("\tvar pregunta = $('#pregunta').val();\n");
      out.write("\tconsole.log(pregunta);\n");
      out.write("\tif (pregunta === 'Agregar otra') {\n");
      out.write("\t\t$('#otra-pregunta').show();\n");
      out.write("\t} else {\n");
      out.write("\t\t$('#otra-pregunta').hide();\n");
      out.write("\t}\n");
      out.write("}\n");
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
      out.write("function inicializaFormUsuario(usuario) {\n");
      out.write("\tif (usuario) {\n");
      out.write("\t\t$('#usuarioModificar').val(usuario.idPersona);\n");
      out.write("\t\t$('#nombre').val(usuario.nombre);\n");
      out.write("\t\t$('#docId').val(usuario.docId);\n");
      out.write("\t\t$('#email').val(usuario.cveUsuario);\n");
      out.write("\t\t$('#password').val('');\n");
      out.write("\t\t$('#confirmacion').val('');\n");
      out.write("\t\t$(\"#pregunta\").prop('selectedIndex', 0);\n");
      out.write("\t\t$('#otraPregunta').val('');\n");
      out.write("\t\t$('#respuesta').val('');\n");
      out.write("\t} else {\n");
      out.write("\t\t$('#usuarioModificar').val('');\n");
      out.write("\t\t$('#nombre').val('');\n");
      out.write("\t\t$('#docId').val('');\n");
      out.write("\t\t$('#email').val('');\n");
      out.write("\t\t$('#password').val('');\n");
      out.write("\t\t$('#confirmacion').val('');\n");
      out.write("\t\t$(\"#pregunta\").prop('selectedIndex', 0);\n");
      out.write("\t\t$('#otraPregunta').val('');\n");
      out.write("\t\t$('#respuesta').val('');\n");
      out.write("\t}\n");
      out.write("\t$('#pregunta').material_select();\n");
      out.write("\tMaterialize.updateTextFields();\n");
      out.write("\t$('#frmUsuario').scrollTop(0);\n");
      out.write("}\n");
      out.write("$('#btnAgregar').on('click', function () {\n");
      out.write("\tinicializaFormUsuario();\n");
      out.write("});\n");
      out.write("function guardaUsuario(idPersona) {\n");
      out.write("\tvar nombre = $('#nombre').val();\n");
      out.write("\tvar docId = $('#docId').val();\n");
      out.write("\tvar email = $('#email').val();\n");
      out.write("\tvar password = $('#password').val();\n");
      out.write("\tvar confirmacion = $('#confirmacion').val();\n");
      out.write("\tvar pregunta = $('#pregunta').val();\n");
      out.write("\tvar otraPregunta = $('#otraPregunta').val();\n");
      out.write("\tvar respuesta = $('#respuesta').val();\n");
      out.write("\tvar usuarioModificar = $('#usuarioModificar').val();\n");
      out.write("\t// validar los datos ingresados\n");
      out.write("\t$('#error').empty();\n");
      out.write("\t$('.error-label').remove();\n");
      out.write("\tif (nombre && docId && email && password && confirmacion /*&& pregunta && respuesta*/) {\n");
      out.write("\t\tif (password != confirmacion) {\n");
      out.write("\t\t\t$('#password').parent().append('<span class=\"error-label\" style=\"position: relative; top: -1rem; left: 0rem; font-size: 0.8rem; color: #FF4081; display: block; text-align: center;\">La contraseña y la confirmación deben ser iguales.</span>');\n");
      out.write("\t\t\treturn;\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t\tif (!validator.matches(password, '(?=.*[0-9])(?=.+[a-z])(?=.*[A-Z]).{8,16}')) {\n");
      out.write("\t\t\t$('#password').parent().append('<span class=\"error-label\" style=\"position: relative; top: -1rem; left: 0rem; font-size: 0.8rem; color: #FF4081; display: block; text-align: center;\">Su contraseña debe tener una longitud de entre 8 y 16 caracteres, debe incluir letras minúsculas, al menos una letra mayúscula y al menos un número.</span>');\n");
      out.write("\t\t\treturn;\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t\tif (!validator.isEmail(email)) {\n");
      out.write("\t\t\t$('#email').parent().append('<span class=\"error-label\" style=\"position: relative; top: -1rem; left: 0rem; font-size: 0.8rem; color: #FF4081; display: block; text-align: center;\">El correo electrónico debe ser válido.</span>');\n");
      out.write("\t\t\treturn;\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t\tif (usuarioModificar) {\n");
      out.write("\t\t\t// actualiza al usuario existente\n");
      out.write("\t\t\tUsuarioDwrAction.actualizaSubusuario(idPersona, usuarioModificar, nombre, docId, email, password, pregunta, otraPregunta, respuesta, resultadoActualizar);\n");
      out.write("\t\t} else {\n");
      out.write("\t\t\t// crea un nuevo usuario\n");
      out.write("\t\t\tUsuarioDwrAction.guardaSubusuario(idPersona, nombre, docId, email, password, pregunta, otraPregunta, respuesta, resultadoGuardar);\n");
      out.write("\t\t}\n");
      out.write("\t} else {\n");
      out.write("\t\t$('#error').append('<p style=\"color: red;\">Todos los datos son obligatorios.</p>');\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("function resultadoGuardar(message) {\n");
      out.write("\tconsole.log(message);\n");
      out.write("\tif (message.codeError == 0) {\n");
      out.write("\t\t// agregar el nuevo usuario al listado\n");
      out.write("\t\tvar data = JSON.parse(message.data);\n");
      out.write("\t\tvar row = $('<tr>', {id: data.idPersona}).append(\n");
      out.write("\t\t\t$('<td>').html(data.nombre)\n");
      out.write("\t\t).append(\n");
      out.write("\t\t\t$('<td>').html(data.cveUsuario)\n");
      out.write("\t\t).append(\n");
      out.write("\t\t\t$('<td>').append(\n");
      out.write("\t\t\t\t$('<a>', {class: 'btn waves-effect indigo', onclick: 'cargaUsuario(' + data.idPersona + ')'}).append(\n");
      out.write("\t\t\t\t\t$('<i>', {class: 'material-icons'}).html('edit')\n");
      out.write("\t\t\t\t)\n");
      out.write("\t\t\t).append(\" \").append(\n");
      out.write("\t\t\t\t$('<a>', {class: 'btn waves-effect red darken-4', onclick: 'eliminarUsuario(' + data.idPersona + ')'}).append(\n");
      out.write("\t\t\t\t\t\t$('<i>', {class: 'material-icons'}).html('delete')\n");
      out.write("\t\t\t\t)\n");
      out.write("\t\t\t)\n");
      out.write("\t\t);\n");
      out.write("\t\t\n");
      out.write("\t\t$('#usuarios tbody').append(row);\n");
      out.write("\t\t\n");
      out.write("\t\t$('#frmUsuario').modal('close');\n");
      out.write("\t\t//inicializaFormAcreedor();\n");
      out.write("\t\t// TODO: mostrar mensaje de exito\n");
      out.write("\t} else {\n");
      out.write("\t\t// TODO: mostrar errores al grabar usuario\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("function resultadoActualizar(message) {\n");
      out.write("\tconsole.log(message);\n");
      out.write("\tif (message.codeError == 0) {\n");
      out.write("\t\t// actualizar datos de usuario\n");
      out.write("\t\tvar data = JSON.parse(message.data);\n");
      out.write("\t\tvar idPersona = data.idPersona;\n");
      out.write("\t\tvar previous = $('#' + idPersona).prev();\n");
      out.write("\t\t$('#' + idPersona).remove();\n");
      out.write("\t\tvar row = $('<tr>', {id: data.idPersona}).append(\n");
      out.write("\t\t\t$('<td>').html(data.nombre)\n");
      out.write("\t\t).append(\n");
      out.write("\t\t\t$('<td>').html(data.cveUsuario)\n");
      out.write("\t\t).append(\n");
      out.write("\t\t\t$('<td>').append(\n");
      out.write("\t\t\t\t$('<a>', {class: 'btn waves-effect indigo', onclick: 'cargaUsuario(' + data.idPersona + ')'}).append(\n");
      out.write("\t\t\t\t\t$('<i>', {class: 'material-icons'}).html('edit')\n");
      out.write("\t\t\t\t)\n");
      out.write("\t\t\t).append(\" \").append(\n");
      out.write("\t\t\t\t$('<a>', {class: 'btn waves-effect red darken-4', onclick: 'eliminarUsuario(' + data.idPersona + ')'}).append(\n");
      out.write("\t\t\t\t\t$('<i>', {class: 'material-icons'}).html('delete')\n");
      out.write("\t\t\t\t)\n");
      out.write("\t\t\t)\n");
      out.write("\t\t);\n");
      out.write("\t\t\n");
      out.write("\t\tconsole.log($(previous));\n");
      out.write("\t\tif(previous) {\n");
      out.write("\t\t\t$(previous).after(row);\n");
      out.write("\t\t} else {\n");
      out.write("\t\t\t$('#usuarios tbody').append(row);\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t\t$('#frmUsuario').modal('close');\n");
      out.write("\t\t//inicializaFormAcreedor();\n");
      out.write("\t\t// TODO: mostrar mensaje de exito\n");
      out.write("\t} else {\n");
      out.write("\t\t// TODO: mostrar errores al grabar usuario\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("function cargaUsuario(usuarioId) {\n");
      out.write("\tUsuarioDwrAction.getUsuario(usuarioId, recibeUsuario);\n");
      out.write("}\n");
      out.write("function recibeUsuario(data) {\n");
      out.write("\tvar usuario = JSON.parse(data);\n");
      out.write("\tinicializaFormUsuario(usuario);\n");
      out.write("\t$('#frmUsuario').modal('open');\t\n");
      out.write("}\n");
      out.write("function eliminarUsuario(usuarioId) {\n");
      out.write("\tif (confirm('¿Está seguro de eliminar al usuario?')) {\n");
      out.write("\t\tUsuarioDwrAction.eliminaSubusuario(usuarioId, resultadoEliminar);\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("function resultadoEliminar(message) {\n");
      out.write("\tif (message.codeError == 0) {\n");
      out.write("\t\t// quitaral usuario de la tabla\n");
      out.write("\t\tvar data = JSON.parse(message.data);\n");
      out.write("\t\tvar idPersona = data.idPersona;\n");
      out.write("\t\t$('#' + idPersona).remove();\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
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
