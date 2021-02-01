package com.egoveris.deo.base.asignacion;

import com.egoveris.deo.base.exception.NotificacionMailException;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.NotificacionMailService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAsignarTarea implements AssignmentHandler {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(AbstractAsignarTarea.class);

  protected String varAsignacionUsuario;
  protected String mensaje;
  protected String referenciaDocumento;
  protected String referenciaDocumentoSub;
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private NotificacionMailService notificacionMailService;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private HistorialService historialService;
  private Mapper mapper;
  private String appName;
  
  public void assign(Assignable assignable, OpenExecution execution) throws Exception {

    String usuario = (String) execution.getVariable(this.varAsignacionUsuario);

    String usuarioApoderadoBean = getUsuarioApoderado(usuario);

    execution.setVariable(Constantes.VAR_SOLICITUD_ENVIO_MAIL_FAIL, false);
    if (usuarioApoderadoBean != null) {
      execution.setVariable(Constantes.VAR_USUARIO_APODERADOR, usuario);
      execution.setVariable(this.varAsignacionUsuario, usuarioApoderadoBean);
      usuario = usuarioApoderadoBean;
    } else {
      execution.setVariable(Constantes.VAR_USUARIO_APODERADOR, null);
    }

    if (usuario != null) {
      this.historialService.nuevoHistorial(usuario, execution.getProcessInstance());
      assignable.setAssignee(usuario);
      Boolean o = (Boolean) execution.getVariable(Constantes.VAR_SOLICITUD_ENVIO_MAIL);
      if (o != null && o) {
        //// sacamos la fecha actual del sistema
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String cadenaFecha = formato.format(fechaActual);

        /// traemos el tipo de documento que selecciono el usuario para rellenar
        /// el cuerpo del mail.
        TipoDocumentoDTO nuevodoc = tipoDocumentoService.buscarTipoDocumentoPorId(
            Integer.valueOf((String) execution.getVariable(Constantes.VAR_TIPO_DOCUMENTO)));

        // sacamos en que modulo esta el usuario
        Usuario usuariob = usuarioService.obtenerUsuario(usuario);
        Map<String, String> variablesTemplate = new HashMap<>();
        variablesTemplate.put("referenciadocumento", referenciaDocumento);
        variablesTemplate.put("fechamodi", cadenaFecha);
        variablesTemplate.put("codigo", nuevodoc.getAcronimo());
        variablesTemplate.put("descripcion", nuevodoc.getNombre());
        byte[] nombreDestinatarioBytes = usuariob.getNombreApellido().getBytes();
        String nombreDestinatario = new String(nombreDestinatarioBytes, "UTF-8");
        variablesTemplate.put("nombreCompletoDestinatario", nombreDestinatario);
        if (execution.getVariable(this.mensaje) != null) {

          byte[] mensajeBytes = ((String) execution.getVariable(this.mensaje)).getBytes();
          String mensajeIso = new String(mensajeBytes, "UTF-8");
          if (StringUtils.isNotEmpty(mensajeIso)) {
            variablesTemplate.put("mensaje", mensajeIso);
          } else {
            variablesTemplate.put("mensaje", "No se ingresó ningún mensaje");
          }
        } else {
          variablesTemplate.put("mensaje", "No se ingresó ningún mensaje");
        }
        String subject;
        if (execution.getVariable(Constantes.VAR_MOTIVO) != null) {
          byte[] motivoBytes = ((String) execution.getVariable(Constantes.VAR_MOTIVO)).getBytes();
          String motivoIso = new String(motivoBytes, "UTF-8");
          subject = referenciaDocumentoSub + " - " + motivoIso;
          variablesTemplate.put("motivo", motivoIso);
        } else {
          subject = referenciaDocumentoSub + " - " + "Sin referencia";
          variablesTemplate.put("motivo", "N/A");
        }

        try {
          notificacionMailService.componerCorreo(subject, usuariob.getEmail(),
              "templateMailNuevo", variablesTemplate);
        } catch (NotificacionMailException nme) {
          logger.error("Ha ocurrido un error al enviar el mail para el usuario: " + usuariob
              + nme.getLocalizedMessage(), nme);
          execution.setVariable(Constantes.VAR_SOLICITUD_ENVIO_MAIL_FAIL, true);
        }
        execution.setVariable(Constantes.VAR_SOLICITUD_ENVIO_MAIL, false);

      }
    }

  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public NotificacionMailService getNotificacionMailService() {
    return notificacionMailService;
  }

  public void setNotificacionMailService(NotificacionMailService notificacionMailService) {
    this.notificacionMailService = notificacionMailService;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  public String getAppName() {
	  return "egoveris";
	  //return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public IUsuarioService getUsuarioService() {
    return usuarioService;
  }

  public void setUsuarioService(IUsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  /**
   * Permite obtener el usuario apoderado de manera recursiva.Comtempla los
   * casos que sea lic de lic
   * 
   * @param usuarioProductor
   * @return
   * @throws SecurityNegocioException
   */
  protected String getUsuarioApoderado(String usuarioProductor) throws SecurityNegocioException {
    String apoderado = getUsuarioApoderadoRecur(usuarioProductor);
    if (apoderado.equals(usuarioProductor)) {
      return null;
    } else {
      return apoderado;
    }
  }

  private String getUsuarioApoderadoRecur(String usuarioProductor)
      throws SecurityNegocioException {
    if (usuarioService.licenciaActiva(usuarioProductor, new Date())) {
      String apoderado = usuarioService.obtenerUsuario(usuarioProductor).getApoderado();
      if (apoderado != null && !apoderado.trim().isEmpty()) {
        return getUsuarioApoderadoRecur(apoderado);
      } else {
        // inconsistencia
        return usuarioProductor;
      }
    } else {
      return usuarioProductor;
    }
  }
}
