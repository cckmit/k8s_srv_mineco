package com.egoveris.deo.base.task;

import com.egoveris.deo.base.exception.NumeracionEspecialNoInicializadaException;
import com.egoveris.deo.base.service.GedoADestinatarios;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.GeneradorDocumentoFactory;
import com.egoveris.deo.model.model.ComunicacionDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.UsuarioExternoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CerrarDocumento implements ExternalActivityBehaviour {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(CerrarDocumento.class);

  private static final long serialVersionUID = -8361906809412323498L;

  private GenerarDocumentoService generarDocumentoService;
  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private GeneradorDocumentoFactory generadorDocumentoFactory;
  @Autowired
  private GedoADestinatarios notifiDestinatario;
  
  public void signal(ActivityExecution execution, String signalName, Map<String, ?> parameters)
      throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("signal(ActivityExecution, String, Map<String,?>) - start"); //$NON-NLS-1$
    }

    execution.take(signalName);

    if (logger.isDebugEnabled()) {
      logger.debug("signal(ActivityExecution, String, Map<String,?>) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  public void execute(ActivityExecution execution) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("execute(ActivityExecution) - start"); //$NON-NLS-1$
    }

    String executionId = execution.getId();

    Map<String, Object> listaVariables = this.obtenerVariablesWorkflow(executionId);

    // Mapeo de las variables:
    String idTipoDocumento = (String) listaVariables.get(Constantes.VAR_TIPO_DOCUMENTO);
    String numero = (String) listaVariables.get(Constantes.VAR_NUMERO_SADE);
    String motivo = (String) listaVariables.get(Constantes.VAR_MOTIVO);
    ComunicacionDTO comunicacion = (ComunicacionDTO) listaVariables
        .get(Constantes.VAR_COMUNICACION);
    String usuarioActual = (String) listaVariables.get(Constantes.VAR_USUARIO_ACTUAL);
    List<String> listaDestinatarios = (List<String>) listaVariables
        .get(Constantes.VAR_USUARIOS_DESTINATARIOS);
    List<String> listaDestinatariosCopia = (List<String>) listaVariables
        .get(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA);
    List<String> listaDestinatariosCopiaOculta = (List<String>) listaVariables
        .get(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA_OCULTA);
    List<UsuarioExternoDTO> listaDestinatariosExternos = (List<UsuarioExternoDTO>) listaVariables
        .get(Constantes.VAR_USUARIOS_DESTINATARIOS_EXTERNOS);
    Integer idComunicacionRespondida = (Integer) listaVariables
        .get(Constantes.VAR_ID_COMUNICACION_RESPONDIDA);
    String mensajeDestinatario = (String) listaVariables.get(Constantes.VAR_MENSAJE_DESTINATARIO);
    String usuarioApoderador = (String) listaVariables.get(Constantes.VAR_USUARIO_APODERADOR);
    String usuarioFirmante = (String) listaVariables.get(Constantes.VAR_USUARIO_FIRMANTE);
    // Si bien el usuario firmante solo es requerido si el apoderador es nulo,
    // la mayoría de las veces es así y por lo tanto es conveniente obtenerla en
    // bloque con las restantes variables
    List<DocumentoMetadataDTO> documentoMetadata = (List<DocumentoMetadataDTO>) listaVariables
        .get(Constantes.VAR_DOCUMENTO_DATA);
    String usuarioCreador = (String) listaVariables.get(Constantes.VAR_USUARIO_CREADOR);
    String usuarioSupervisado = (String) listaVariables.get(Constantes.VAR_USUARIO_SUPERVISADO);
    List<String> receptoresAviso = (List<String>) listaVariables
        .get(Constantes.VAR_RECEPTORES_AVISO_FIRMA);
    String nombreArchivo = (String) listaVariables.get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
    String numeroSadePapel = (String) listaVariables.get(Constantes.VAR_NUMERO_SADE_PAPEL);
    String sistemaIniciador = (String) listaVariables.get(Constantes.VAR_SISTEMA_INICIADOR);
    List<String> listaUsuariosReservados = (List<String>) listaVariables
        .get(Constantes.VAR_USUARIOS_RESERVADOS);
    String idGuardaDocumental = (String) listaVariables.get(Constantes.VAR_ID_GUARDA_DOCUMENTAL);
    Integer idDestinatario = (Integer) listaVariables
        .get(Constantes.VAR_ID_DESTINATARIO_COMUNICACION);

    // busca documento
    TipoDocumentoDTO tipoDocumentoGedo = tipoDocumentoService
        .buscarTipoDocumentoPorId(Integer.valueOf(idTipoDocumento));
    generarDocumentoService = generadorDocumentoFactory
        .obtenerGeneradorDocumento(tipoDocumentoGedo);

    // genera documento
    RequestGenerarDocumento request = new RequestGenerarDocumento();
    request.setNombreAplicacion(Constantes.NOMBRE_APLICACION);
    request.setNombreAplicacionIniciadora(sistemaIniciador);

    // Si el número ya está cargado es incorporación
    if (StringUtils.isNotEmpty(numero)) {
      request.setNumero(numero);
    }

    request.setTipoDocumentoGedo(tipoDocumentoGedo);
    request.setTieneToken(tipoDocumentoGedo.getTieneToken());
    request.setMotivo(motivo);
    request.setUsuarioFirmante(usuarioFirmante);
    request.setUsuarioApoderador(usuarioApoderador);
    request.setIdGuardaDocumental(idGuardaDocumental);

    if (comunicacion != null) {
      listaDestinatarios.clear();
      listaDestinatariosCopia.clear();
      listaDestinatariosCopiaOculta.clear();
      listaDestinatariosExternos.clear();
      request.setListaUsuariosDestinatarios(listaDestinatarios);
      request.setListaUsuariosDestinatariosCopia(listaDestinatariosCopia);
      request.setListaUsuariosDestinatariosCopiaOculta(listaDestinatariosCopiaOculta);
      request.setListaUsuariosDestinatariosExternos(listaDestinatariosExternos);
      request.setIdComunicacionRespondida(comunicacion.getIdComunicacion());
      request.setMensajeDestinatario(comunicacion.getMensaje());
      request.setUsuarioActual(usuarioActual);
      request.setUsuarioSupervisado(usuarioSupervisado);
      request.setIdDestinatario(idDestinatario);
    } else {
      request.setComunicacion(comunicacion);
      request.setListaUsuariosDestinatarios(listaDestinatarios);
      request.setListaUsuariosDestinatariosCopia(listaDestinatariosCopia);
      request.setListaUsuariosDestinatariosCopiaOculta(listaDestinatariosCopiaOculta);
      request.setListaUsuariosDestinatariosExternos(listaDestinatariosExternos);
      request.setIdComunicacionRespondida(idComunicacionRespondida);
      request.setMensajeDestinatario(mensajeDestinatario);
      request.setUsuarioActual(usuarioActual);
      request.setUsuarioSupervisado(usuarioSupervisado);
      request.setIdDestinatario(idDestinatario);
    }

    String contenido = "";
    if (tipoDocumentoGedo.getTipoProduccion() != Constantes.TIPO_PRODUCCION_IMPORTADO
        && tipoDocumentoGedo
            .getTipoProduccion() != Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      byte[] contenidoBytes = (byte[]) this.processEngine.getExecutionService()
          .getVariable(executionId, Constantes.VAR_CONTENIDO);
      contenido = new String(contenidoBytes, "UTF-8");
    }
    request.setContenido(contenido);

    if (StringUtils.isEmpty(usuarioApoderador)) {
      request.setUsuario(usuarioFirmante);
    } else {
      request.setUsuario(usuarioApoderador);
    }

    request.setDocumentoMetadata(documentoMetadata);
    request.setNombreArchivo(nombreArchivo);
    Usuario us = this.usuarioService.obtenerUsuario(request.getUsuario());
    request.setCodigoReparticion(us.getCodigoReparticion());
    request.setWorkflowId(executionId);
    request.setUsuarioIniciador(usuarioCreador);
    //
    request.setNumeroSadePapel(
        (numeroSadePapel == null || ("").equals(numeroSadePapel) ? null : numeroSadePapel));
    //
    request.setListaUsuariosReservados(listaUsuariosReservados);

    try {

      this.generarDocumentoService.cerrarDocumento(request, receptoresAviso);
      if (listaVariables.containsKey(Constantes.VAR_SISTEMA_INICIADOR)) {
    	  
    	  notifiDestinatario.notificarADestinatarios(listaVariables.get(Constantes.VAR_SISTEMA_INICIADOR).toString(), request.getDocumento().getNumero(), executionId, usuarioCreador );
    	  //notificarSistemasExternosService.noticiar(listaVariables.get(Constantes.VAR_SISTEMA_INICIADOR),request.getDocumento().getNumero(),executionId);
      }
      logger.info("enviando a notificar a destinatarios");
     // gedoADestinatarios.notificarADestinatarios(executionId, GedoADestinatarios.CERRAR_DOCUMENTO);

    } catch (Exception e) {
      logger.error("Error al cerrar documento, execution: " + executionId, e);
      this.processEngine.getExecutionService().setVariable(executionId,
          Constantes.VAR_MENSAJE_USUARIO, e.getMessage());
      if (e instanceof NumeracionEspecialNoInicializadaException) {
        this.processEngine.getExecutionService().setVariable(executionId,
            Constantes.VAR_REINTENTO_HABILITADO, false);
      }
      this.processEngine.getExecutionService().signalExecutionById(executionId,
          Constantes.TRANSICION_REINTENTAR_CIERRE);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("execute(ActivityExecution) - end"); //$NON-NLS-1$
    }
  }

  private Map<String, Object> obtenerVariablesWorkflow(String executionId) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerVariablesWorkflow(String) - start"); //$NON-NLS-1$
    }

    Set<String> names = new HashSet<>();
    names.add(Constantes.VAR_TIPO_DOCUMENTO);
    names.add(Constantes.VAR_NUMERO_SADE);
    names.add(Constantes.VAR_MOTIVO);
    names.add(Constantes.VAR_USUARIO_APODERADOR);
    names.add(Constantes.VAR_USUARIO_FIRMANTE);
    names.add(Constantes.VAR_DOCUMENTO_DATA);
    names.add(Constantes.VAR_USUARIO_CREADOR);
    names.add(Constantes.VAR_RECEPTORES_AVISO_FIRMA);
    names.add(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
    names.add(Constantes.VAR_NUMERO_SADE_PAPEL);
    names.add(Constantes.VAR_SISTEMA_INICIADOR);
    names.add(Constantes.VAR_USUARIOS_RESERVADOS);
    names.add(Constantes.VAR_COMUNICACION);
    names.add(Constantes.VAR_USUARIO_ACTUAL);
    names.add(Constantes.VAR_USUARIO_SUPERVISADO);
    names.add(Constantes.VAR_USUARIOS_DESTINATARIOS);
    names.add(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA);
    names.add(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA_OCULTA);
    names.add(Constantes.VAR_USUARIOS_DESTINATARIOS_EXTERNOS);
    names.add(Constantes.VAR_ID_COMUNICACION_RESPONDIDA);
    names.add(Constantes.VAR_MENSAJE_DESTINATARIO);
    names.add(Constantes.VAR_ID_GUARDA_DOCUMENTAL);
    names.add(Constantes.VAR_ID_DESTINATARIO_COMUNICACION);
    Map<String, Object> returnMap = processEngine.getExecutionService().getVariables(executionId,
        names);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerVariablesWorkflow(String) - end"); //$NON-NLS-1$
    }
    return returnMap;
  }
}
