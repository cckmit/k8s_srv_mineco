package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.exception.ArchivoDuplicadoException;
import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.CantidadDatosException;
import com.egoveris.deo.base.exception.ExtensionesFaltantesException;
import com.egoveris.deo.base.exception.FormatoInvalidoException;
import com.egoveris.deo.base.exception.PDFConversionException;
import com.egoveris.deo.base.exception.ParametroInvalidoException;
import com.egoveris.deo.base.exception.TamanoInvalidoException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.base.service.ConversorPdf;
import com.egoveris.deo.base.service.DocumentoAdjuntoService;
import com.egoveris.deo.base.service.DocumentoTemplateService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.service.SistemaOrigenService;
import com.egoveris.deo.base.service.SuscripcionService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.GeneradorDocumentoFactory;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.DocumentoTemplateDTO;
import com.egoveris.deo.model.model.DocumentoTemplatePKDTO;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.model.model.MetadataDTO;
import com.egoveris.deo.model.model.RequestExternalGenerarTarea;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarTarea;
import com.egoveris.deo.model.model.SistemaOrigenDTO;
import com.egoveris.deo.model.model.SuscripcionDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionTareaException;
import com.egoveris.deo.ws.exception.ParametroInvalidoTareaException;
import com.egoveris.deo.ws.exception.ParametroNoExisteException;
import com.egoveris.deo.ws.exception.UsuarioSinPermisoException;
import com.egoveris.deo.ws.service.IExternalGenerarTareaNombramientoService;
import com.egoveris.deo.ws.util.ValidacionesGenerarTareaNombramiento;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Component;

@Service
public class ExternalGenerarTareaNombramientoImpl extends ValidacionesGenerarTareaNombramiento
    implements IExternalGenerarTareaNombramientoService {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(ExternalGenerarTareaNombramientoImpl.class);

  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private FirmaConjuntaService firmaConjuntaService;
  @Autowired
  private HistorialService historialService;
  @Autowired
  private GeneradorDocumentoFactory generadorDocumentoFactory;
  @Autowired
  private DocumentoAdjuntoService documentoAdjuntoService;
  @Autowired
  private SuscripcionService suscripcionService;
  @Autowired
  private SistemaOrigenService sistemaOrigenService;
  @Autowired
  private ConversorPdf conversorPdf;
  @Autowired
  private ProcesamientoTemplate procesamientoTemplate;
  @Autowired
  private IFormManagerFactory<IFormManager<Component>> formManagerFact;
  @Autowired
  private DocumentoTemplateService documentoTemplateService;
  @Autowired
  private ArchivosEmbebidosService archivosEmbebidosService;
  @Autowired
  private ArchivosEmbebidosService archivoEmbebidoService;
  @Autowired
  private IUsuarioService usuarioService;
  @Value("${app.sistema.gestor.documental}")
  private String gestorDocumental;
  @Autowired
  private Mapper mapper;

  protected GenerarDocumentoService generarDocumentoService;

  public ResponseExternalGenerarTarea generarTareaGEDO(RequestExternalGenerarTarea request)
      throws ParametroInvalidoException, ParametroInvalidoTareaException,
      ParametroNoExisteException, UsuarioSinPermisoException, ErrorGeneracionTareaException,
      CantidadDatosNoSoportadaException, ClavesFaltantesException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarTareaGEDO(RequestExternalGenerarTarea) - start"); //$NON-NLS-1$
    }

    TipoDocumentoDTO tipoDocumento = null;
    Task tarea = null;
    List<Usuario> usuariosFirmaConjunta = null;
    List<String> usuarioFirmante = null;
    List<DocumentoMetadataDTO> listaDatosPropios = null;
    try {
      tipoDocumento = obtenerTipoDocumento(request.getAcronimoTipoDocumento());

      this.generarDocumentoService = generadorDocumentoFactory
          .obtenerGeneradorDocumento(tipoDocumento);

      this.validacionGenerarTarea(request, tipoDocumento);

      if (request.getUsuarioFirmante() != null && !request.getUsuarioFirmante().isEmpty()) {
        usuarioFirmante = new ArrayList<String>(request.getUsuarioFirmante().values());
      }

      if (tipoDocumento.getEsFirmaConjunta() && request.getUsuarioFirmante() != null) {
        usuariosFirmaConjunta = this.obtenerFirmantes(usuarioFirmante);
      }

      listaDatosPropios = cargarDatosPropios(request.getMetaDatos(), tipoDocumento);

      Map<String, Object> variables = this.cargarVariables(request, listaDatosPropios,
          tipoDocumento, usuarioFirmante);

      ProcessInstance pInstance = this.processEngine.getExecutionService()
          .startProcessInstanceByKey("procesoGEDO", variables);

      if (request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR)) {
        this.inicioHistorial(pInstance.getId(), request.getUsuarioEmisor(), request.getMensaje(),
            Constantes.INICIAR_DOCUMENTO);
      } else {
        this.inicioHistorial(pInstance.getId(), request.getUsuarioEmisor(), null,
            Constantes.INICIAR_DOCUMENTO);

        // Se utilza este metodo, para que haya 1 segundo de diferencia entre la
        // tarea de Inicio y la de Confeccion
        // y asi a la hora de mostrar el Historial, este aparezca ordenado
        // correctamente.
        Thread.sleep(1000);

        tarea = this.processEngine.getTaskService().createTaskQuery()
            .executionId(pInstance.getId()).uniqueResult();

        if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
            || tipoDocumento
                .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
          altaDocumentoAdjunto(variables.get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA).toString(),
              tarea.getExecutionId(), request, tipoDocumento);
        }

        // Revisar
        if (request.getTarea().equalsIgnoreCase(Constantes.ACT_REVISAR)) {
          this.processEngine.getExecutionService().setVariable(tarea.getExecutionId(),
              Constantes.VAR_SOLICITUD_ENVIO_MAIL, request.isEnviarCorreoReceptor());
          this.historialService.actualizarHistorial(tarea.getExecutionId());
          tarea.setProgress(100);
          this.processEngine.getExecutionService().signalExecutionById(tarea.getExecutionId(),
              Constantes.TRANSICION_REVISAR);

          if (tipoDocumento.getPermiteEmbebidos() && request.getListaArchivosEmbebidos() != null
              && request.getListaArchivosEmbebidos().size() > 0) {
            this.settearListaArchivosEmbebidos(request, tarea.getExecutionId(), tipoDocumento,
                false);
          }
          if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
              || tipoDocumento
                  .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
            guardarDocumentoTemplate(request, tipoDocumento, tarea);
          }
        }
        // Firmar
        if (request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)) {
          this.processEngine.getExecutionService().setVariable(tarea.getExecutionId(),
              Constantes.VAR_SOLICITUD_ENVIO_MAIL, request.isEnviarCorreoReceptor());
          crearDocumentoPDF(tipoDocumento, request,
              variables.get(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA).toString(),
              tarea.getExecutionId());
          this.historialService.actualizarHistorial(tarea.getExecutionId());
          tarea.setProgress(100);
          this.processEngine.getExecutionService().signalExecutionById(tarea.getExecutionId(),
              Constantes.TRANSICION_USO_PORTAFIRMA);
          if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
              || tipoDocumento
                  .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE)
            guardarDocumentoTemplate(request, tipoDocumento, tarea);
        }

      }
      if (usuariosFirmaConjunta != null) {
        try {
          this.firmaConjuntaService.guardarFirmantes(usuariosFirmaConjunta, pInstance.getId());
        } catch (Exception e) {
          logger.error("Error guardando lista de firmantes " + e);
        }
      }

      if (request.getSuscribirseAlDocumento() != null
          && request.getSuscribirseAlDocumento() == true) {
        SuscripcionDTO suscripcion = armarSuscripcion(request.getSistemaIniciador(),
            request.getUsuarioEmisor(), pInstance.getId());
        if (suscripcion != null) {
          try {
            suscripcionService.save(suscripcion);
          } catch (Exception e) {
            logger.error("No puede llevarse adelante la suscripcion del sistema: "
                + request.getSistemaIniciador() + " al workflowId: " + pInstance.getId() + " - "
                + e.getMessage(), e);
          }
        }
      }

      ResponseExternalGenerarTarea returnResponseExternalGenerarTarea = armarResponse(
          pInstance.getId(), request, usuarioFirmante);
      if (logger.isDebugEnabled()) {
        logger.debug("generarTareaGEDO(RequestExternalGenerarTarea) - end"); //$NON-NLS-1$
      }
      return returnResponseExternalGenerarTarea;
    } catch (ParametroInvalidoTareaException pie) {
      logger.error("Parámetros inválidos para la tarea que se intenta realizar. ", pie);
      throw pie;
    } catch (ParametroInvalidoException pie) {
      logger.error("Parámetros inválidos para la tarea que se intenta realizar. ", pie);
      throw pie;
    } catch (ParametroNoExisteException pie) {
      logger.error("Parámetros inexistentes para la tarea que se intenta realizar. ", pie);
      throw pie;
    } catch (UsuarioSinPermisoException upe) {
      logger.error("No se tiene permiso para la tarea que se intenta realizar. ", upe);
      throw upe;
    } catch (ArchivoFirmadoException afe) {
      logger.error("Error en validación de data para generacion de Tarea Externa para la tarea: "
          + request.getTarea() + " . Sistema Iniciador " + request.getSistemaIniciador(), afe);
      throw new ParametroInvalidoException(
          "Error en validación de data para generacion de Tarea Externa para la tarea: "
              + request.getTarea() + ", " + afe.getMessage());
    } catch (ClavesFaltantesException cfe) {
      logger.error("Error en validaciones para Tipo De Documento Template. ", cfe);
      throw cfe;
    } catch (ValidacionContenidoException vce) {
      logger.error("Error en validación del contenido de la Tarea: " + request.getTarea()
          + " . Sistema Iniciador " + request.getSistemaIniciador(), vce);
      throw new ParametroInvalidoException("Error en validación del contenido de la Tarea: "
          + request.getTarea() + ", " + vce.getMessage());
    } catch (CantidadDatosException cde) {
      logger.error("Error en validación de la Tarea: " + request.getTarea()
          + " . Sistema Iniciador " + request.getSistemaIniciador(), cde);
      throw new CantidadDatosNoSoportadaException(
          "Error en validación de la Tarea: " + request.getTarea() + ", " + cde.getMessage());
    } catch (PDFConversionException pdfe) {
      logger.error("Error en conversión de archivo pdf de la Tarea: " + request.getTarea()
          + " . Sistema Iniciador " + request.getSistemaIniciador(), pdfe);
      throw new ErrorGeneracionTareaException("Error en conversión de archivo pdf de la Tarea: "
          + request.getTarea() + ", " + pdfe.getMessage());
    } catch (TamanoInvalidoException tie) {
      logger.error("Un archivo supera el tamaño permitido por el tipo de documento seleccionado",
          tie);
      throw new CantidadDatosNoSoportadaException(
          "Error en validación de archivos embebidos con referencia: " + request.getReferencia()
              + ", Un archivo supera el tamaño permitido por el tipo de documento seleccionado");
    } catch (FormatoInvalidoException fie) {
      logger.error("Un archivo es de un formato no permitido por el tipo de documento", fie);
      throw new ParametroInvalidoException(
          "Error en validacion de archivos embebidos con referencia: " + request.getReferencia()
              + ", Un archivo tiene un formato no permitido para el tipo de documento seleccionado.");
    } catch (ArchivoDuplicadoException ade) {
      logger.error("Un archivo se encuentra duplicado en la lista de archivos embebidos", ade);
      throw new ParametroInvalidoException(
          "Error en validacion de archivos embebidos con referencia: " + request.getReferencia()
              + ", " + ade.getMessage());
    } catch (ExtensionesFaltantesException efe) {
      logger.error("No se subieron archivos de con el formato de las extensiones obligatorias."
          + efe.getMessage(), efe);
      throw new ParametroInvalidoException(
          "Error en validacion de archivos embebidos con referencia: " + request.getReferencia()
              + ", no se subieron archivos con el formato de las extensiones obligatorias.");
    } catch (Exception e) {
      cancelarTarea(tarea);
      logger.error("Error en generación de la tarea que intenta realizar. ", e);
      throw new ErrorGeneracionTareaException(
          "Error en generación de la Tarea: " + request.getTarea() + ", " + e.getMessage());
    }
  }

  private void guardarDocumentoTemplate(RequestExternalGenerarTarea request,
      TipoDocumentoDTO tipoDocumento, Task tarea) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "guardarDocumentoTemplate(RequestExternalGenerarTarea, TipoDocumentoDTO, Task) - start"); //$NON-NLS-1$
    }

    Integer idTransaccion;
    TipoDocumentoTemplateDTO tipoDocumentoTemplate = procesamientoTemplate
        .obtenerUltimoTemplatePorTipoDocumento(tipoDocumento);

    DocumentoTemplateDTO documentoTemplate = new DocumentoTemplateDTO();

    DocumentoTemplatePKDTO documentoTemplatePK = new DocumentoTemplatePKDTO();

    documentoTemplatePK.setIdTipoDocumento(
        tipoDocumentoTemplate.getTipoDocumentoTemplatePK().getIdTipoDocumento());
    documentoTemplatePK
        .setVersion(tipoDocumentoTemplate.getTipoDocumentoTemplatePK().getVersion());
    documentoTemplatePK.setWorkflowId(tarea.getExecutionId());

    documentoTemplate.setDocumentoTemplatePK(documentoTemplatePK);

    documentoTemplate.setTipoDocumentoTemplate(tipoDocumentoTemplate);

    try {

      if (request.getIdTransaccion() != null) {
        idTransaccion = request.getIdTransaccion();
      } else {
        idTransaccion = guardarTransaccionFormularioControlado(request, tipoDocumentoTemplate);
      }
      documentoTemplate.setIdTransaccion(idTransaccion);
      this.documentoTemplateService.save(documentoTemplate);
    } catch (Exception e) {
      logger.error("No se ha podido persistir el Documento Template: " + tarea.getExecutionId()
          + " - " + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "guardarDocumentoTemplate(RequestExternalGenerarTarea, TipoDocumentoDTO, Task) - end"); //$NON-NLS-1$
    }
  }

  private Integer guardarTransaccionFormularioControlado(RequestExternalGenerarTarea request,
      TipoDocumentoTemplateDTO tipoDocumentoTemplate) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "guardarTransaccionFormularioControlado(RequestExternalGenerarTarea, TipoDocumentoTemplateDTO) - start"); //$NON-NLS-1$
    }

    try {
      // FIXME esto no anda para mi ..
      // porque no hay contexto de zk.
      // No deberia estar esto aca
      // Guarda solo STRINGS!!
      IFormManager<Component> formManager = formManagerFact
          .create(tipoDocumentoTemplate.getIdFormulario());
      formManager
          .fillCompValues(this.transformMapStringStringToStringObject(request.getCampoTemplate()));

      Integer returnInteger = formManager.saveValues();
      if (logger.isDebugEnabled()) {
        logger.debug(
            "guardarTransaccionFormularioControlado(RequestExternalGenerarTarea, TipoDocumentoTemplateDTO) - end"); //$NON-NLS-1$
      }
      return returnInteger;
    } catch (DynFormException e) {
      logger.error(
          "Ha ocurrido un error al renderizar la pantalla para este Tipo de Documento Template - "
              + e.getMessage(),
          e);
      throw new Exception(e.getMessage());
    }
  }

  private void validacionGenerarTarea(RequestExternalGenerarTarea request,
      TipoDocumentoDTO tipoDocumento) throws ClavesFaltantesException, Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("validacionGenerarTarea(RequestExternalGenerarTarea, TipoDocumento) - start"); //$NON-NLS-1$
    }

    List<String> usuarioFirmante = null;
    Map<String, Object> camposTemplate;

    if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
        || tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {

      if (!request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR)) {

        // Si envian ambos campos completos
        if (request.getIdTransaccion() != null && !request.getIdTransaccion().equals(0)
            && request.getCampoTemplate() != null && !request.getCampoTemplate().isEmpty()) {
          throw new ValidacionContenidoException("Debe ingresar o IdTransaccion o CamposTemplate, "
              + "no puede procesar ambos a la vez. Para mas informacion, contacte a su administrador.");
        }

        // Si envian ambos campos nulos o vacios
        if ((request.getIdTransaccion() != null && request.getIdTransaccion().equals(0)
            && request.getCampoTemplate() != null && request.getCampoTemplate().isEmpty())
            || (request.getIdTransaccion() == null && request.getCampoTemplate() == null)) {
          throw new ValidacionContenidoException("Debe ingresar o IdTransaccion o CamposTemplate, "
              + "no puede no ingresar ningun valor. Para mas informacion, contacte a su administrador.");
        }

        if (request.getIdTransaccion() == null
            && request.getTarea().equalsIgnoreCase(Constantes.ACT_REVISAR)) {
          throw new ValidacionContenidoException("Actualmente no pueden generarse tareas "
              + "de Revision para Tipos de Documentos Template si no es por IdTransaccion. "
              + "Para mas informacion, contacte a su administrador.");
        }
        camposTemplate = obtenerCamposTemplate(request);
        validarParametrosTemplate(camposTemplate, tipoDocumento);
      }
    }

    if (!request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR)
        && tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_TEMPLATE) {
      validarContenido(request);
    }

    if (request.getUsuarioFirmante() != null && !request.getUsuarioFirmante().isEmpty()) {
      usuarioFirmante = new ArrayList<String>(request.getUsuarioFirmante().values());
    }

    this.validarParametros(request, tipoDocumento, usuarioFirmante);

    this.validarPermisos(request, tipoDocumento, usuarioFirmante);

    if (logger.isDebugEnabled()) {
      logger.debug("validacionGenerarTarea(RequestExternalGenerarTarea, TipoDocumento) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Se arma el objeto Sucripcion que finalmente se persistera en la tabla
   * GEDO_SUSCRIPCION.
   * 
   * @param sistema
   * @param usuario
   * @param workflowId
   * @return Suscripcion
   */
  private SuscripcionDTO armarSuscripcion(String sistema, String usuario, String workflowId) {
    if (logger.isDebugEnabled()) {
      logger.debug("armarSuscripcion(String, String, String) - start"); //$NON-NLS-1$
    }

    SuscripcionDTO suscripcion = null;
    try {
      SistemaOrigenDTO sistemaOrigen = sistemaOrigenService.findByNombreLike(sistema);

      if (sistemaOrigen != null) {
        suscripcion = new SuscripcionDTO();

        suscripcion.setEstado(Constantes.SUSCRIPCION_ESTADO_PENDIENTE);
        suscripcion.setFechaCreacion(new Date());
        suscripcion.setReintento(0);
        suscripcion.setSistemaOrigen(sistemaOrigen);
        suscripcion.setUsuarioAlta(usuario);
        suscripcion.setWorkflowId(workflowId);
      } else {
        logger.error("No existe el sistema de origen: " + sistema
            + " por lo tanto no puede llevarse adelante la suscripcion.");
      }
    } catch (Exception e) {
      logger.error("No puede llevarse adelante la suscripcion del sistema: " + sistema
          + " al workflowId: " + workflowId + " - " + e.getMessage(), e);
      suscripcion = null;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("armarSuscripcion(String, String, String) - end"); //$NON-NLS-1$
    }
    return suscripcion;
  }

  /**
   * Cancela la tarea en caso de que se presente un error al generar la tarea
   * pedida
   * 
   * @param tarea
   */

  public void cancelarTarea(Task tarea) {
    if (logger.isDebugEnabled()) {
      logger.debug("cancelarTarea(Task) - start"); //$NON-NLS-1$
    }

    if (tarea != null) {
      processEngine.getExecutionService().endProcessInstance(tarea.getExecutionId(),
          ProcessInstance.STATE_ENDED);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cancelarTarea(Task) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Valida el tipo de archivo y el tamaño, del contenido de la tarea.
   * 
   * @param request
   * @throws ValidacionContenidoException
   * @throws CantidadDatosException
   * @throws ValidacionCampoFirmaException
   */

  public void validarContenido(RequestExternalGenerarTarea request)
      throws ValidacionContenidoException, CantidadDatosException, ArchivoFirmadoException,
      ValidacionCampoFirmaException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarContenido(RequestExternalGenerarTarea) - start"); //$NON-NLS-1$
    }

    // Valido que el tamaño sea valido
    this.validarTamanioContenido(request.getData());

    // Obtengo el tipo de archivo con TIKA en caso de que no me lo envien
    if (StringUtils.isEmpty(request.getTipoArchivo())) {
      request.setTipoArchivo(this.obtenerTipoArchivo(request.getData()));
    } else if (request.getTipoArchivo().contains(".")) {
      request.setTipoArchivo(StringUtils.removeStart(request.getTipoArchivo(), "."));
    }

    // Valido tipo de Archivo
    this.generarDocumentoService.validarContenidoDocumento(request.getTipoArchivo());

    // Valido si el documentro esta firmado o no y si eso es correcto segun el
    // tipo de documento o no.
    if (request.getTipoArchivo().equalsIgnoreCase("pdf"))
      this.generarDocumentoService.validarArchivoASubirPorSusFirmas(request.getData());

    if (logger.isDebugEnabled()) {
      logger.debug("validarContenido(RequestExternalGenerarTarea) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Arma el ResponseExternalGenerarTarea, que se enviara como respuesta al
   * sistema externo que este utilizando el WS.
   * 
   * @param processId
   *          : Id del proceso en ejecucion.
   * @param request
   *          : Request con los parametros ingresados en el WS.
   * @return responseExternal: La respuesta otorgada por el WS, de no tener
   *         Usuario Apoderado, no se muestra.
   * @throws SecurityNegocioException
   */

  public ResponseExternalGenerarTarea armarResponse(String processId,
      RequestExternalGenerarTarea request, List<String> usuarioFirmante)
      throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("armarResponse(String, RequestExternalGenerarTarea, List<String>) - start"); //$NON-NLS-1$
    }

    ResponseExternalGenerarTarea responseExternal = new ResponseExternalGenerarTarea();
    responseExternal.setProcessId(processId);
    Map<String, String> mapLicenciadoApoderado = new HashMap<String, String>();
    String licencia = "";
    if (request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR) && !this.processEngine
        .getExecutionService().getVariable(processId, Constantes.VAR_USUARIO_PRODUCTOR)
        .equals(request.getUsuarioReceptor())) {

      licencia = licencia.concat("El usuario " + request.getUsuarioReceptor()
          + " se encuentra de licencia. Se redigirá a "
          + (String) this.processEngine.getExecutionService().getVariable(processId,
              Constantes.VAR_USUARIO_PRODUCTOR + "\n"));
      if (!licencia.isEmpty()) {
        responseExternal.setLicencia(licencia);
      }

      responseExternal.setUsuarioApoderado((String) this.processEngine.getExecutionService()
          .getVariable(processId, Constantes.VAR_USUARIO_PRODUCTOR));
    } else if (request.getTarea().equalsIgnoreCase(Constantes.ACT_REVISAR) && !this.processEngine
        .getExecutionService().getVariable(processId, Constantes.VAR_USUARIO_REVISOR)
        .equals(request.getUsuarioReceptor())) {
      licencia = licencia.concat("El usuario " + request.getUsuarioReceptor()
          + " se encuentra de licencia. Se redigirá a " + (String) this.processEngine
              .getExecutionService().getVariable(processId, Constantes.VAR_USUARIO_REVISOR)
          + "\n");
      if (!licencia.isEmpty()) {
        responseExternal.setLicencia(licencia);
      }

      responseExternal.setUsuarioApoderado((String) this.processEngine.getExecutionService()
          .getVariable(processId, Constantes.VAR_USUARIO_REVISOR));
    } else if (request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR) && !this.processEngine
        .getExecutionService().getVariable(processId, Constantes.VAR_USUARIO_FIRMANTE)
        .equals(usuarioFirmante.get(0))) {
      for (String usuario : usuarioFirmante) {
        obtenerApoderado(usuario, new Date(), mapLicenciadoApoderado);
      }

      for (Map.Entry<String, String> entry : mapLicenciadoApoderado.entrySet()) {
        licencia = licencia.concat("El usuario " + entry.getKey()
            + " se encuentra de licencia. Se redigirá a " + entry.getValue() + "\n");

      }
      if (!licencia.isEmpty()) {
        responseExternal.setLicencia(licencia);
      }

      responseExternal.setUsuarioApoderado((String) this.processEngine.getExecutionService()
          .getVariable(processId, Constantes.VAR_USUARIO_FIRMANTE));
    }

    if (logger.isDebugEnabled()) {
      logger.debug("armarResponse(String, RequestExternalGenerarTarea, List<String>) - end"); //$NON-NLS-1$
    }
    return responseExternal;
  }

  private String obtenerApoderado(String usuario, Date fecha,
      Map<String, String> mapLicenciadoApoderado) throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerApoderado(String, Date, Map<String,String>) - start"); //$NON-NLS-1$
    }

    String apoderado = (this.usuarioService.licenciaActiva(usuario, fecha)
        ? this.usuarioService.obtenerUsuario(usuario).getApoderado() : null);
    if (apoderado != null) {
      while (this.usuarioService.licenciaActiva(apoderado, fecha)) {
        apoderado = this.usuarioService.obtenerUsuario(apoderado).getApoderado();
      }
      mapLicenciadoApoderado.put(usuario, apoderado);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerApoderado(String, Date, Map<String,String>) - end"); //$NON-NLS-1$
    }
    return apoderado;
  }

  /**
   * Carga los datos necesarios para iniciar la tarea seleccionada.
   * 
   * @param request
   *          : La request que llega como parametro al WS
   * @param listaDatosPropios
   *          : Lista de datos propios, puede existir o no, depende del Tipo de
   *          Documento.
   */

  public Map<String, Object> cargarVariables(RequestExternalGenerarTarea request,
      List<DocumentoMetadataDTO> listaDatosPropios, TipoDocumentoDTO tipoDocumento,
      List<String> usuarioFirmante) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "cargarVariables(RequestExternalGenerarTarea, List<DocumentoMetadata>, TipoDocumento, List<String>) - start"); //$NON-NLS-1$
    }

    Map<String, Object> variables = new HashMap<String, Object>();
    if (!request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR)) {
      if (request.getTarea().equalsIgnoreCase(Constantes.ACT_REVISAR)) {
        variables.put(Constantes.VAR_USUARIO_REVISOR, request.getUsuarioReceptor());
        variables.put(Constantes.VAR_MENSAJE_A_REVISOR, request.getMensaje());
        variables.put(Constantes.VAR_MENSAJE_A_REVISOR, request.getMensaje());
      } else {
        variables.put(Constantes.VAR_USUARIO_FIRMANTE, usuarioFirmante.get(0));
        variables.put(Constantes.VAR_SOLICITUD_ENVIO_MAIL, request.isEnviarCorreoReceptor());
      }
      variables.put(Constantes.VAR_USUARIO_DERIVADOR, request.getUsuarioEmisor());
      variables.put(Constantes.VAR_USUARIO_PRODUCTOR, request.getUsuarioEmisor());
      variables.put(Constantes.VAR_MOTIVO, request.getReferencia());

      if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {
        variables.put(Constantes.VAR_CONTENIDO, request.getData());
      }
    } else {
      variables.put(Constantes.VAR_USUARIO_PRODUCTOR, request.getUsuarioReceptor());
      variables.put(Constantes.VAR_MENSAJE_PRODUCTOR, request.getMensaje());
      variables.put(Constantes.VAR_SOLICITUD_ENVIO_MAIL, request.isEnviarCorreoReceptor());
    }
    if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
      // WEBDAV
      String nombreArchivoTemporal = this.gestionArchivosWebDavService
          .crearNombreArchivoTemporal();
      variables.put(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA, nombreArchivoTemporal);
    }

    variables.put(Constantes.VAR_USUARIO_CREADOR, request.getUsuarioEmisor());
    variables.put(Constantes.VAR_DOCUMENTO_DATA,
        listaDatosPropios == null ? new ArrayList<DocumentoMetadataDTO>() : listaDatosPropios);
    TipoDocumentoDTO tp = tipoDocumentoService
        .buscarTipoDocumentoByAcronimo(request.getAcronimoTipoDocumento());
    variables.put(Constantes.VAR_TIPO_DOCUMENTO, tp.getId().toString());
    variables.put(Constantes.VAR_SISTEMA_INICIADOR, request.getSistemaIniciador());

    List<String> receptores = new ArrayList<String>();

    if (tipoDocumento.getTieneAviso()) {
      if (request.isRecibirAvisoFirma())
        receptores.add(request.getUsuarioEmisor());
    }

    variables.put(Constantes.VAR_RECEPTORES_AVISO_FIRMA, receptores);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "cargarVariables(RequestExternalGenerarTarea, List<DocumentoMetadata>, TipoDocumento, List<String>) - end"); //$NON-NLS-1$
    }
    return variables;
  }

  /**
   * Crea el PDF del documento a ser firmado.
   * 
   * @param tipoDocumento:
   *          Tipo del documento a crear
   * @param request:
   *          Request del WS de tipo RequestExternalGenerarTarea
   * @param nombreArchivoTemporal:
   *          Archivo temporal, en el caso de importar documento, llega null
   * @param executionId:
   *          WorkflowId generado hasta el momento para la solicitud de Workflow
   *          JBPM
   * @return nombreArchivoTemporal: Devuelve el nombre del archivo temporal, en
   *         el caso de Importar Documento.
   * @throws Exception
   */

  public String crearDocumentoPDF(TipoDocumentoDTO tipoDocumento,
      RequestExternalGenerarTarea request, String nombreArchivoTemporal, String executionId)
      throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearDocumentoPDF(TipoDocumento, RequestExternalGenerarTarea, String, String) - start"); //$NON-NLS-1$
    }

    Integer numeroFirmas = 0;
    if (tipoDocumento.getEsFirmaConjunta()) {
      numeroFirmas = request.getUsuarioFirmante().size();
    } else {
      numeroFirmas = Integer.valueOf(1);
    }
    RequestGenerarDocumento requestDoc = new RequestGenerarDocumento();
    requestDoc.setTipoDocumentoGedo(tipoDocumento);
    requestDoc.setMotivo(request.getReferencia());
    requestDoc.setData(request.getData());
    requestDoc.setNombreArchivo(nombreArchivoTemporal);
    requestDoc.setTipoArchivo(request.getTipoArchivo());
    requestDoc.setWorkflowId(executionId);

    if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
        || tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      requestDoc.setCamposTemplate((HashMap<String, Object>) this.obtenerCamposTemplate(request));
    }
    if (request.getListaArchivosEmbebidos() != null
        && request.getListaArchivosEmbebidos().size() != 0) {
      Boolean bloqueante = true;
      requestDoc.setListaArchivosEmbebidos(
          this.settearListaArchivosEmbebidos(request, executionId, tipoDocumento, bloqueante));
    }
    this.generarDocumentoService.generarDocumentoPDF(requestDoc, numeroFirmas, true);

    if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
        || tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      this.processEngine.getExecutionService().setVariable(executionId, Constantes.VAR_CONTENIDO,
          requestDoc.getDataOriginal());
    }

    String returnString = requestDoc.getNombreArchivo();
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearDocumentoPDF(TipoDocumento, RequestExternalGenerarTarea, String, String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  /**
   * Inicia el historial del Documento GEDO.
   * 
   * @param workflowId
   * @param emisor
   *          : El usuario que realizar el mensaje
   * @param tarea
   *          : Tarea en la que se inicia (Iniciar Documento o Importar
   *          Documento)
   * @param mensaje
   *          : El mensaje que se le quiere dar al usuario receptor, puede
   *          existir o no.
   */

  public void inicioHistorial(String workflowId, String emisor, String mensaje, String tarea) {
    if (logger.isDebugEnabled()) {
      logger.debug("inicioHistorial(String, String, String, String) - start"); //$NON-NLS-1$
    }

    HistorialDTO inicio = new HistorialDTO(emisor, tarea, workflowId);
    inicio.setFechaFin(new Date());
    inicio.setMensaje(mensaje);
    inicio.setFechaInicio(new Date());
    this.historialService.guardarHistorial(inicio);

    if (logger.isDebugEnabled()) {
      logger.debug("inicioHistorial(String, String, String, String) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene el objeto Tipo de documento, identificado por el acrónimo dado
   * (Siempre y cuando este haya sino ingresado), siempre que supere las
   * validaciones necesarias. Validaciones: - Existencia del tipo de documento
   * identificado por el acrónimo. - Que el tipo ade documento teng el atributo
   * "esManual" en true. - Que el tipo de documento este en estado "ALTA".
   * 
   * @param acronimoTipoDocumento
   *          : Cadena que almacena el acrónimo del tipo de documento.
   * @return TipoDocumento.
   * @throws ParametroInvalidoException
   */

  private TipoDocumentoDTO obtenerTipoDocumento(String acronimoTipoDocumento)
      throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipoDocumento(String) - start"); //$NON-NLS-1$
    }

    TipoDocumentoDTO tipoDocumento = null;
    if (acronimoTipoDocumento == null) {
      throw new ParametroInvalidoException("Parámetro Tipo Documento es obligatorio");
    }
    tipoDocumento = tipoDocumentoService.buscarTipoDocumentoByAcronimo(acronimoTipoDocumento);
    if (tipoDocumento == null)
      throw new ParametroInvalidoException(
          "Tipo de documento: " + acronimoTipoDocumento + " inexistente");
    if (!tipoDocumento.getEsManual())
      throw new ParametroInvalidoException(
          "El tipo de documento: " + acronimoTipoDocumento + " debe ser de generación manual");
    if (tipoDocumento.getEstado().compareTo(TipoDocumentoDTO.ESTADO_ACTIVO) != 0)
      throw new ParametroInvalidoException(
          "El tipo de documento: " + acronimoTipoDocumento + " debe estar habilitado");
    if (tipoDocumento.getEsFirmaExterna())
      throw new ParametroInvalidoException(
          "El tipo de documento: " + acronimoTipoDocumento + " no debe ser Firma Externa");

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipoDocumento(String) - end"); //$NON-NLS-1$
    }
    return tipoDocumento;
  }

  /**
   * Valida que los campos pasados para el tipo de documentos Template, no sean
   * menos que los que realmente necesita para su confeccion.
   * 
   * @param request
   * @param tipoDocumento
   * @throws Exception
   * @throws ClavesFaltantesException
   */
  private void validarParametrosTemplate(Map<String, Object> campoTemplate,
      TipoDocumentoDTO tipoDocumento) throws Exception, ClavesFaltantesException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarParametrosTemplate(Map<String,Object>, TipoDocumento) - start"); //$NON-NLS-1$
    }

    try {
      procesamientoTemplate.validarCamposTemplatePorTipoDocumentoYMap(tipoDocumento,
          campoTemplate);
    } catch (ClavesFaltantesException vie) {
      logger.error("Error al validar campos template. " + vie.getMessage(), vie);
      throw new ClavesFaltantesException(
          "Los campos ingresados para el Tipo de Documento Template: "
              + tipoDocumento.getAcronimo() + " son insuficientes");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarParametrosTemplate(Map<String,Object>, TipoDocumento) - end"); //$NON-NLS-1$
    }
  }

  private Map<String, Object> obtenerCamposTemplate(RequestExternalGenerarTarea request)
      throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCamposTemplate(RequestExternalGenerarTarea) - start"); //$NON-NLS-1$
    }

    if (request.getIdTransaccion() != null) {
      Map<String, Object> returnMap = procesamientoTemplate
          .obtenerCamposTemplate(request.getIdTransaccion());
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerCamposTemplate(RequestExternalGenerarTarea) - end"); //$NON-NLS-1$
      }
      return returnMap;
    } else {
      Map<String, Object> returnMap = this
          .transformMapStringStringToStringObject(request.getCampoTemplate());
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerCamposTemplate(RequestExternalGenerarTarea) - end"); //$NON-NLS-1$
      }
      return returnMap;
    }
  }

  private Map<String, Object> transformMapStringStringToStringObject(Map<String, String> mapss) {
    if (logger.isDebugEnabled()) {
      logger.debug("transformMapStringStringToStringObject(Map<String,String>) - start"); //$NON-NLS-1$
    }

    Map<String, Object> mapso = new HashMap<String, Object>();

    for (Entry<String, String> entry : mapss.entrySet()) {
      mapso.put(entry.getKey(), entry.getValue());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("transformMapStringStringToStringObject(Map<String,String>) - end"); //$NON-NLS-1$
    }
    return mapso;
  }

  /**
   * Convierte el Map recibido en una lista de metadatos, basándose en los datos
   * propios configurados para el tipo de documento. Valida la obligatoriedad de
   * los datos propios del documento.
   * 
   * @param datosPropios
   */

  private List<DocumentoMetadataDTO> cargarDatosPropios(Map<String, String> datosPropiosDocumento,
      TipoDocumentoDTO tipoDocumento) throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("cargarDatosPropios(Map<String,String>, TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    List<MetadataDTO> listaDatosPropiosTipoDocumento = tipoDocumento.getListaDatosVariables();
    List<DocumentoMetadataDTO> listaDatosPropiosDocumento = new ArrayList<>();
    boolean datoPropioObligatorio = false;
    for (MetadataDTO datoPropio : listaDatosPropiosTipoDocumento) {
      String valorDatoPropio = null;
      if (datosPropiosDocumento == null && datoPropio.isObligatoriedad())
        datoPropioObligatorio = true;
      if (datosPropiosDocumento != null) {
        valorDatoPropio = (String) datosPropiosDocumento.get(datoPropio.getNombre());
        if (valorDatoPropio == null && datoPropio.isObligatoriedad())
          datoPropioObligatorio = true;
        if (valorDatoPropio != null) {
          DocumentoMetadataDTO documentoMetadata = new DocumentoMetadataDTO();
          documentoMetadata.setNombre(datoPropio.getNombre());
          documentoMetadata.setValor(valorDatoPropio);
          documentoMetadata.setTipo(datoPropio.getTipo());
          documentoMetadata.setObligatoriedad(datoPropio.isObligatoriedad());
          documentoMetadata.setOrden(datoPropio.getOrden());
          listaDatosPropiosDocumento.add(documentoMetadata);
        }
      }
      if (datoPropioObligatorio) {
        String mensajeError = "Dato propio: " + datoPropio.getNombre()
            + " es obligatorio para el tipo de documento: " + tipoDocumento.getNombre();
        throw new ParametroInvalidoException(mensajeError);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cargarDatosPropios(Map<String,String>, TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return listaDatosPropiosDocumento;
  }

  /**
   * Realiza las operaciones de subida a la tabla gedo_documento_adjunto y al
   * repositorio
   * 
   * @param nombreArchivoTemporal
   * @param executionId
   * @param documento
   * @param usuario
   * @throws Exception
   */
  private void altaDocumentoAdjunto(String nombreArchivoTemporal, String executionId,
      RequestExternalGenerarTarea request, TipoDocumentoDTO tipoDocumento) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "altaDocumentoAdjunto(String, String, RequestExternalGenerarTarea, TipoDocumento) - start"); //$NON-NLS-1$
    }

    try {
      DocumentoAdjuntoDTO documentoAdjunto = llenarDocumentoAdjunto(nombreArchivoTemporal,
          executionId, request.getData(), request.getUsuarioEmisor());

      // Impacto el documento en la base
      this.documentoAdjuntoService.grabarDocumentoAdjuntoBD(documentoAdjunto);

      // Impacto el documento en el repositorio
      if (request.getTipoArchivo().equalsIgnoreCase("html")) {
        documentoAdjunto
            .setDataArchivo(conversorPdf.convertirHTMLaPDF(documentoAdjunto.getDataArchivo()));
      }

      // WEBDAV
      this.documentoAdjuntoService.subirDocumentoAdjuntoWebDav(documentoAdjunto);

    } catch (Exception e) {
      logger.error("Error en generación del Documento Adjunto. ", e);
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "altaDocumentoAdjunto(String, String, RequestExternalGenerarTarea, TipoDocumento) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Genera el objeto DocumentoAdjunto que se subira a la tabla
   * gedo_documento_adjunto y al repositorio
   * 
   * @param nombreArchivoUpload
   */
  private DocumentoAdjuntoDTO llenarDocumentoAdjunto(String nombreArchivoTemporal,
      String executionId, byte[] documento, String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("llenarDocumentoAdjunto(String, String, byte[], String) - start"); //$NON-NLS-1$
    }

    String pathRelativo;
    DocumentoAdjuntoDTO documentoAdjunto = new DocumentoAdjuntoDTO();

    documentoAdjunto.setIdTask(executionId);
    documentoAdjunto.setNombreArchivo(nombreArchivoTemporal);
    documentoAdjunto.setDataArchivo(documento);
    documentoAdjunto.setUsuarioAsociador(usuario);
    documentoAdjunto.setFechaAsociacion(new Date());
    documentoAdjunto.setDefinitivo(false);

    // Elimina la extension .pdf
    pathRelativo = eliminarExtensionPath(nombreArchivoTemporal);
    documentoAdjunto.setPathRelativo(pathRelativo);

    if (logger.isDebugEnabled()) {
      logger.debug("llenarDocumentoAdjunto(String, String, byte[], String) - end"); //$NON-NLS-1$
    }
    return documentoAdjunto;
  }

  private String eliminarExtensionPath(String pathRelativo) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarExtensionPath(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = pathRelativo.split("-");
    String idAleatorioConExtension = espacios[3];
    String idAleatorioSplit[] = idAleatorioConExtension.split("\\.");

    String idAleatorio = idAleatorioSplit[0];
    StringBuilder url = new StringBuilder("");
    for (int i = 0; i < espacios.length - 1; i++) {
      url.append(espacios[i] + "-");
    }
    url.append(idAleatorio);
    pathRelativo = url.toString();

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarExtensionPath(String) - end"); //$NON-NLS-1$
    }
    return pathRelativo;
  }

  private List<ArchivoEmbebidoDTO> settearListaArchivosEmbebidos(
      RequestExternalGenerarTarea request, String executionId, TipoDocumentoDTO tipoDocumento,
      Boolean bloqueante) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "settearListaArchivosEmbebidos(RequestExternalGenerarTarea, String, TipoDocumento, Boolean) - start"); //$NON-NLS-1$
    }

    List<ArchivoEmbebidoDTO> listaArchivosEmbebidos = new ArrayList<>();
    String pathRelativo = (String) this.processEngine.getExecutionService()
        .getVariable(executionId, Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
    pathRelativo = pathRelativo.substring(0, pathRelativo.lastIndexOf("."));
    for (ArchivoEmbebidoDTO archivoEmbebidoDTO : request.getListaArchivosEmbebidos()) {
      this.archivosEmbebidosService.verificarArchivo(archivoEmbebidoDTO.getDataArchivo(),
          tipoDocumento);
      ArchivoEmbebidoDTO archivoEmbebido = new ArchivoEmbebidoDTO();
      archivoEmbebido.setIdTask(executionId);
      archivoEmbebido.setNombreArchivo(archivoEmbebidoDTO.getNombreArchivo());
      this.archivosEmbebidosService.validarNombre(listaArchivosEmbebidos, archivoEmbebido);
      archivoEmbebido.setDataArchivo(archivoEmbebidoDTO.getDataArchivo());
      archivoEmbebido
          .setMimetype(archivosEmbebidosService.getMimetype(archivoEmbebido.getDataArchivo()));
      archivoEmbebido.setUsuarioAsociador(request.getUsuarioEmisor());
      archivoEmbebido.setFechaAsociacion(new Date());
      archivoEmbebido.setPathRelativo(pathRelativo);
      archivoEmbebido.setPeso(archivoEmbebido.getDataArchivo().length);
      archivoEmbebido.setId(archivoEmbebidoService.guardarArchivoEmbebido(archivoEmbebido));

      // SADE
      archivosEmbebidosService.subirArchivoEmbebidoTemporal(archivoEmbebido);

      archivoEmbebidoService.guardarArchivoEmbebido(archivoEmbebido);
      listaArchivosEmbebidos.add(archivoEmbebido);
    }
    if (bloqueante) {
      this.archivosEmbebidosService.verificarObligatoriedadExtensiones(listaArchivosEmbebidos,
          tipoDocumento);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "settearListaArchivosEmbebidos(RequestExternalGenerarTarea, String, TipoDocumento, Boolean) - end"); //$NON-NLS-1$
    }
    return listaArchivosEmbebidos;
  }

}
