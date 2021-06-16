package com.egoveris.deo.ws.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zkoss.util.resource.Labels;

import com.egoveris.deo.base.exception.InconsistenciaDeDatosException;
import com.egoveris.deo.base.exception.ParametroInvalidoException;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.DocumentoSolicitudService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.SuscripcionUtils;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDetalle;
import com.egoveris.deo.model.model.ArchivoDeTrabajoResponse;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.DocumentoDetalle;
import com.egoveris.deo.model.model.DocumentoMetadataDetalle;
import com.egoveris.deo.model.model.DocumentoMetadataResponse;
import com.egoveris.deo.model.model.HistorialDetalle;
import com.egoveris.deo.model.model.HistorialResponse;
import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.RequestExternalConsultarNumeroSade;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumentoResponse;
import com.egoveris.deo.model.model.ResponseExternalConsultaNumeroSade;
import com.egoveris.deo.model.model.SistemaOrigenDTO;
import com.egoveris.deo.model.model.SuscripcionDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumento2Service;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoServiceExt;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@Service
public class ExternalConsultaDocumentoServiceImpl
    implements IExternalConsultaDocumentoServiceExt, IExternalConsultaDocumento2Service {

  private static final Logger logger = LoggerFactory
      .getLogger(ExternalConsultaDocumentoServiceImpl.class);
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private BuscarDocumentosGedoService buscarDocumentosGedoService;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @Autowired
  private SuscripcionUtils suscripcionUtils;
  @Autowired
  private ProcessEngine processEngine;
  @Value("${app.sistema.gestor.documental}")
  private String gestorDocumental;
  @Autowired
  private DocumentoSolicitudService documentoSolicitudService;

  public ResponseExternalConsultaDocumento consultarDocumentoPorNumero(
      RequestExternalConsultaDocumento request) throws ParametroInvalidoConsultaException,
      DocumentoNoExisteException, SinPrivilegiosException, ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoPorNumero(RequestExternalConsultaDocumento) - start"); //$NON-NLS-1$
    }

    ResponseExternalConsultaDocumento response = new ResponseExternalConsultaDocumento();
    DocumentoDTO documento;

    validarParametrosObligatorios(request);

    documento = obtenerDocumento(request);

    String reparticionUsuario = obtenerReparticionUsuario(request.getUsuarioConsulta());

    if (!buscarDocumentosGedoService.puedeVerDocumentoConfidencial(documento,
        request.getUsuarioConsulta(), reparticionUsuario)) {
      throw new SinPrivilegiosException((!(StringUtils.isEmpty(request.getNumeroEspecial())))
          ? request.getNumeroEspecial() : request.getNumeroDocumento());
    }

    response.setNumeroDocumento(documento.getNumero());
    response.setUsuarioGenerador(documento.getUsuarioGenerador());
    response.setFechaCreacion(documento.getFechaCreacion());
    response.setTipoDocumento(documento.getTipo().getAcronimo());
    response.setSistemaOrigen(documento.getSistemaOrigen());
    response.setUsuarioIniciador(documento.getUsuarioIniciador());
    response.setNumeroEspecial(documento.getNumeroEspecial());
    response.setMotivo(documento.getMotivo());
    if (documento.getMotivoDepuracion() != null) {
      response.setUrlArchivo(Labels.getLabel("gedo.consultaDocumentos.motivoDepuracion"));
    } else {
      setearUrlDescarga(response, documento);
    }

    if (documento.getTipo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE || documento
        .getTipo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      try {
        Integer idTransaccion;
        if (documento.getWorkflowOrigen() != null) {
          idTransaccion = obtenerIdTransaccionByWorkflowId(documento.getWorkflowOrigen());
        } else {
          idTransaccion = obtenerIdTransaccionByNumeroSade(documento.getNumero());
        }
        if (idTransaccion != null) {
          response.setIdTransaccion(idTransaccion);
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw new ErrorConsultaDocumentoException(e.getMessage());
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoPorNumero(RequestExternalConsultaDocumento) - end"); //$NON-NLS-1$
    }
    return response;
  }

  public ResponseExternalConsultaDocumentoResponse consultarDocumentoDetalle(
      RequestExternalConsultaDocumento request) throws ParametroInvalidoConsultaException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoDetalle(RequestExternalConsultaDocumento) - start"); //$NON-NLS-1$
    }

    ResponseExternalConsultaDocumentoResponse response = new ResponseExternalConsultaDocumentoResponse();
    List<DocumentoDetalle> documentosDetalle = null;
    DocumentoDetalle documentoDetalle = null;

    validarParametrosObligatorios(request);

    try {
      if (request != null && !(StringUtils.isEmpty(request.getNumeroEspecial()))) {
        Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("documento.numeroEspecial", request.getNumeroEspecial());
        inputMap.put("usuarioConsulta", request.getUsuarioConsulta());

        documentosDetalle = buscarDocumentosGedoService
            .buscarDocumentosDetallePorCriterio(inputMap);
        if (documentosDetalle == null || documentosDetalle.size() == 0)
          throw new Exception(
              " no se encontro ningun documento que coincida con el numero ingresado.");
        else
          documentoDetalle = documentosDetalle.get(0);
      } else {

        if (null != request) {
          documentoDetalle = buscarDocumentosGedoService
              .buscarDocumentoDetalle(request.getNumeroDocumento(), request.getUsuarioConsulta());
        }

      }
      // Se le agrega la validacion si es que viene null
      if (null != documentoDetalle && !documentoDetalle.getPuedeVerDocumento()) {
        throw new SinPrivilegiosException((!(StringUtils.isEmpty(request.getNumeroEspecial())))
            ? request.getNumeroEspecial() : request.getNumeroDocumento());
      }

      PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
      propertyUtilsBean.copyProperties(response, documentoDetalle);

      if (documentoDetalle != null) {
        response.setDatosPropios(
            this.parsearDocumentoMetadataResponse(documentoDetalle.getDatosPropiosDetalle()));
        response
            .setListaHistorial(this.parsearHistorial(documentoDetalle.getListaHistorialDetalle()));
        response.setListaArchivosDeTrabajo(this
            .parsearArchivoDeTrabajoResponse(documentoDetalle.getListaArchivosDeTrabajoDetalle()));
      }

    } catch (Exception e) {
      String numero = ((request != null) && (((!(StringUtils.isEmpty(request.getNumeroEspecial())))
          ? request.getNumeroEspecial() : request.getNumeroDocumento()) != null))
              ? request.getNumeroDocumento() : "N/A";
      logger.error("Error al consultar detalle para documento " + numero + " - " + e.getMessage(),
          e);

      // TODO REFACTORIZAR USO DE EXCEPCIONES!!!!!!!
      throw new RuntimeException("Error al consultar detalle para documento " + numero
          + " la excepcion es " + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoDetalle(RequestExternalConsultaDocumento) - end"); //$NON-NLS-1$
    }
    return response;
  }

  public ResponseExternalConsultaDocumentoResponse consultarDocumentoDetalle2(String numeroSade,
      String sistema, String usuario) throws ParametroInvalidoConsultaException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoDetalle2(String, String, String) - start"); //$NON-NLS-1$
    }

    // TODO REFACTORIZAR USO DE EXCEPCIONES!!!!!!!
    if (sistema.equals("ARCH")) {
      ResponseExternalConsultaDocumentoResponse response = new ResponseExternalConsultaDocumentoResponse();
      List<DocumentoDetalle> documentosDetalle = null;
      DocumentoDetalle documentoDetalle = null;

      RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
      request.setNumeroDocumento(numeroSade);
      request.setUsuarioConsulta(usuario);

      validarParametrosObligatorios(request);

      try {
        if (!(StringUtils.isEmpty(request.getNumeroEspecial()))) {
          Map<String, String> inputMap = new HashMap<String, String>();
          inputMap.put("documento.numeroEspecial", request.getNumeroEspecial());
          inputMap.put("usuarioConsulta", request.getUsuarioConsulta());

          documentosDetalle = buscarDocumentosGedoService
              .buscarDocumentosDetallePorCriterio(inputMap);
          if (documentosDetalle == null || documentosDetalle.size() == 0)
            throw new Exception(
                " no se encontro ningun documento que coincida con el numero ingresado.");
          else
            documentoDetalle = documentosDetalle.get(0);
        } else {
          documentoDetalle = buscarDocumentosGedoService
              .buscarDocumentoDetalle(request.getNumeroDocumento(), request.getUsuarioConsulta());
        }

        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        propertyUtilsBean.copyProperties(response, documentoDetalle);
        response.setDatosPropios(
            this.parsearDocumentoMetadataResponse(documentoDetalle.getDatosPropiosDetalle()));
        response
            .setListaHistorial(this.parsearHistorial(documentoDetalle.getListaHistorialDetalle()));
        response.setListaArchivosDeTrabajo(this
            .parsearArchivoDeTrabajoResponse(documentoDetalle.getListaArchivosDeTrabajoDetalle()));
      } catch (Exception e) {

        String numero = (((!(StringUtils.isEmpty(request.getNumeroEspecial())))
            ? request.getNumeroEspecial() : request.getNumeroDocumento()) != null)
                ? request.getNumeroDocumento() : "N/A";
        logger.error(
            "Error al consultar detalle para documento " + numero + " - " + e.getMessage(), e);

        // REFACTORIZAR USO DE EXCEPCIONES!!!!!!!
        throw new RuntimeException("Error al consultar detalle para documento " + numero
            + " la excepcion es " + e.getMessage(), e);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("consultarDocumentoDetalle2(String, String, String) - end"); //$NON-NLS-1$
      }
      return response;
    } else
      throw new ParametroInvalidoConsultaException("Sistema origen no permitido " + sistema);
  }

  public byte[] consultarDocumentoPdf(RequestExternalConsultaDocumento request)
      throws ParametroInvalidoConsultaException, DocumentoNoExisteException,
      SinPrivilegiosException, ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoPdf(RequestExternalConsultaDocumento) - start"); //$NON-NLS-1$
    }

    byte[] contenido;
    DocumentoDTO documento = null;

    validarParametrosObligatorios(request);

    String reparticionUsuario = obtenerReparticionUsuario(request.getUsuarioConsulta());
    documento = obtenerDocumento(request);

    if (documento.getTipo().getEsConfidencial()) {
      if (!buscarDocumentosGedoService.puedeVerDocumentoConfidencial(documento,
          request.getUsuarioConsulta(), reparticionUsuario)) {
        throw new SinPrivilegiosException((!(StringUtils.isEmpty(request.getNumeroEspecial())))
            ? request.getNumeroEspecial() : request.getNumeroDocumento());
      }
    }
    if (documento.getMotivoDepuracion() != null) {
      throw new ErrorConsultaDocumentoException(
          Labels.getLabel("gedo.consultaDocumentos.motivoDepuracion"));
    }

    if (documento.getMotivoDepuracion() != null) {
      throw new ErrorConsultaDocumentoException(
          Labels.getLabel("gedo.consultaDocumentos.motivoDepuracion"));
    }

    String idGuardaDocumental = documento.getIdGuardaDocumental();
    try {

      // WEBDAV
      contenido = IOUtils.toByteArray(
          gestionArchivosWebDavService.obtenerDocumento(documento.getNumero()).getFileAsStream());

    } catch (Exception e) {
      logger.error("consultarDocumentoPdf(RequestExternalConsultaDocumento)", e); //$NON-NLS-1$

      throw new ErrorConsultaDocumentoException(
          " al obtener el documento, la excepcion es " + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoPdf(RequestExternalConsultaDocumento) - end"); //$NON-NLS-1$
    }
    return contenido;
  }

  private void validarParametrosObligatorios(RequestExternalConsultaDocumento request)
      throws ParametroInvalidoConsultaException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarParametrosObligatorios(RequestExternalConsultaDocumento) - start"); //$NON-NLS-1$
    }

    String mensaje = "";
    boolean parametroInvalido = false;

    if (request.getUsuarioConsulta() == null || request.getUsuarioConsulta().equals("")) {
      parametroInvalido = true;
      mensaje = "usuario consulta";
    } else {
      request.setUsuarioConsulta(request.getUsuarioConsulta().toUpperCase());
      obtenerReparticionUsuario(request.getUsuarioConsulta());
    }

    if (StringUtils.isEmpty(request.getNumeroDocumento())
        && StringUtils.isEmpty(request.getNumeroEspecial())) {
      parametroInvalido = true;
      mensaje = "Numero de Documento o Numero Especial";
    } else if (!StringUtils.isEmpty(request.getNumeroDocumento())
        && !StringUtils.isEmpty(request.getNumeroEspecial())) {
      throw new ParametroInvalidoConsultaException(
          "La clave de busqueda es solo por Numero del Documento o Numero Especial, ambos no deben estar ingresados para la consulta. ");
    }

    if (parametroInvalido) {
      throw new ParametroInvalidoConsultaException("Parametro " + mensaje + " es obligatorio");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarParametrosObligatorios(RequestExternalConsultaDocumento) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene la repartición del usuario, para lo cuál valida que el usuario se
   * encuentre registrado en la base de datos de CCOO.
   * 
   * @param usuario
   */
  private String obtenerReparticionUsuario(String usuario)
      throws ParametroInvalidoConsultaException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionUsuario(String) - start"); //$NON-NLS-1$
    }

    Usuario datosUsuario = null;
    String reparticionUsuario = null;
    try {
      datosUsuario = this.usuarioService.obtenerUsuario(usuario);
      if ((datosUsuario == null) || (datosUsuario.getCodigoReparticion() == null)) {
        String mensajeError = "El usuario: " + usuario + " no puede consultar documentos en DEO."
            + " Por favor ingrese a Escritorio Único o contacte a su Administrador";
        throw new ParametroInvalidoConsultaException(mensajeError);
      } else {
        reparticionUsuario = datosUsuario.getCodigoReparticion();

      }

    } catch (SecurityNegocioException e) {
      logger.error("Mensaje de error", e);

    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionUsuario(String) - end"); //$NON-NLS-1$
    }
    return reparticionUsuario;
  }

  /**
   * Realiza la consulta del documento por número SADE.
   * 
   * @param numeroDocumento
   *          : Número SADE que identifica el documento generado en GEDO.
   * @return
   * @throws DocumentoNoExisteException
   * @throws ErrorConsultaDocumentoException
   */
  private DocumentoDTO obtenerDocumento(RequestExternalConsultaDocumento request)
      throws DocumentoNoExisteException, ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumento(RequestExternalConsultaDocumento) - start"); //$NON-NLS-1$
    }

    String numeroDocumento = request.getNumeroDocumento();
    DocumentoDTO documento = null;

    try {
      if (!(StringUtils.isEmpty(request.getNumeroEspecial()))) {
        Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("documento.numeroEspecial", request.getNumeroEspecial());
        inputMap.put("usuarioConsulta", request.getUsuarioConsulta());
        documento = buscarDocumentosGedoService.buscarDocumentosPorCriterio(inputMap).get(0);
      } else {
        documento = buscarDocumentosGedoService.buscarDocumentoPorNumero(numeroDocumento);
      }
    } catch (Exception e) {
      logger.error(
          "Error no se ha generado en el sistema DEO un documento, no se encontro ningun documento con numero",
          e);
      throw new ErrorConsultaDocumentoException(
          (!(StringUtils.isEmpty(request.getNumeroEspecial()))) ? request.getNumeroEspecial()
              : (request.getNumeroDocumento() + " " + e.getMessage()));
    }
    if (documento == null) {
      throw new DocumentoNoExisteException((!(StringUtils.isEmpty(request.getNumeroEspecial())))
          ? request.getNumeroEspecial() : request.getNumeroDocumento());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumento(RequestExternalConsultaDocumento) - end"); //$NON-NLS-1$
    }
    return documento;
  }

  /**
   * Parsea los atributos del Historial del documento a HistorialDTO.
   * 
   * @param workflowId
   * @return
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  private List<HistorialResponse> parsearHistorial(List<HistorialDetalle> listaHistorialDetalle)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    if (logger.isDebugEnabled()) {
      logger.debug("parsearHistorial(List<HistorialDetalle>) - start"); //$NON-NLS-1$
    }

    Iterator<HistorialDetalle> iterator;
    HistorialDetalle current;
    List<HistorialResponse> listaHistorialResponse = new ArrayList<HistorialResponse>();
    iterator = listaHistorialDetalle.iterator();

    while (iterator.hasNext()) {
      current = iterator.next();

      HistorialResponse historialResponse = new HistorialResponse();
      org.apache.commons.beanutils.PropertyUtils.copyProperties(historialResponse, current);
      listaHistorialResponse.add(historialResponse);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("parsearHistorial(List<HistorialDetalle>) - end"); //$NON-NLS-1$
    }
    return listaHistorialResponse;
  }

  /**
   * Parsea los atributos del Historial del documento a HistorialDTO.
   * 
   * @param workflowId
   * @return
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  private List<ArchivoDeTrabajoResponse> parsearArchivoDeTrabajoResponse(
      List<ArchivoDeTrabajoDetalle> listaArchivoDeTrabajoDetalle)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    if (logger.isDebugEnabled()) {
      logger.debug("parsearArchivoDeTrabajoResponse(List<ArchivoDeTrabajoDetalle>) - start"); //$NON-NLS-1$
    }

    Iterator<ArchivoDeTrabajoDetalle> iterator;
    ArchivoDeTrabajoDetalle current;
    List<ArchivoDeTrabajoResponse> listaArchivoDeTrabajoResponse = new ArrayList<ArchivoDeTrabajoResponse>();

    iterator = listaArchivoDeTrabajoDetalle.iterator();

    while (iterator.hasNext()) {
      current = iterator.next();

      ArchivoDeTrabajoResponse archivoDeTrabajoResponse = new ArchivoDeTrabajoResponse();
      org.apache.commons.beanutils.PropertyUtils.copyProperties(archivoDeTrabajoResponse, current);
      listaArchivoDeTrabajoResponse.add(archivoDeTrabajoResponse);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("parsearArchivoDeTrabajoResponse(List<ArchivoDeTrabajoDetalle>) - end"); //$NON-NLS-1$
    }
    return listaArchivoDeTrabajoResponse;
  }

  /**
   * Parsea los atributos del Historial del documento a HistorialDTO.
   * 
   * @param workflowId
   * @return
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  private List<DocumentoMetadataResponse> parsearDocumentoMetadataResponse(
      List<DocumentoMetadataDetalle> listaDocumentoMetadataDetalle)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    if (logger.isDebugEnabled()) {
      logger.debug("parsearDocumentoMetadataResponse(List<DocumentoMetadataDetalle>) - start"); //$NON-NLS-1$
    }

    Iterator<DocumentoMetadataDetalle> iterator;
    DocumentoMetadataDetalle current;
    List<DocumentoMetadataResponse> listaArchivoDeTrabajoResponse = new ArrayList<DocumentoMetadataResponse>();

    iterator = listaDocumentoMetadataDetalle.iterator();

    while (iterator.hasNext()) {
      current = iterator.next();

      DocumentoMetadataResponse documentoMetadataResponse = new DocumentoMetadataResponse();
      org.apache.commons.beanutils.PropertyUtils.copyProperties(documentoMetadataResponse,
          current);
      listaArchivoDeTrabajoResponse.add(documentoMetadataResponse);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("parsearDocumentoMetadataResponse(List<DocumentoMetadataDetalle>) - end"); //$NON-NLS-1$
    }
    return listaArchivoDeTrabajoResponse;
  }

  /**
   * Consulta el numero SADE de un documento por workflowId o por messageId
   * dependiendo del sistema origen
   * 
   * @param RequestExternalConsultarNumeroSade
   * @return ResponseExternalConsultaNumeroSade
   * 
   */
  public ResponseExternalConsultaNumeroSade consultarNumeroSade(
      RequestExternalConsultarNumeroSade request) {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarNumeroSade(RequestExternalConsultarNumeroSade) - start"); //$NON-NLS-1$
    }

    String sistemaOrigen = request.getOrigen();
    String workflowId = null;
    ResponseExternalConsultaNumeroSade responseNumeroSade = new ResponseExternalConsultaNumeroSade();
    boolean loguear = true;

    try {
      validarDatosRequest(request);

      // Obtengo SISTEMA_ORIGEN
      SistemaOrigenDTO origen = suscripcionUtils.obtenerSistemaOrigen(sistemaOrigen);

      if (origen == null) {
        throw new ParametroInvalidoException("No existe el Sistema Origen: " + sistemaOrigen);
      }

      // Obtengo WORKFLOWID
      workflowId = request.getId();

      // Obtengo SUSCRIPCION
      SuscripcionDTO suscripcion = suscripcionUtils.obtenerSuscripcion(workflowId, origen);

      if (suscripcion == null) {
        loguear = false;
        throw new InconsistenciaDeDatosException(
            "No existe la suscripcion perteneciente al workflowId: " + workflowId
                + " y al Sistema Origen: " + origen.getNombre());
      }

      // Obtengo Documento
      DocumentoDTO documento = suscripcionUtils.obtenerDocumento(workflowId);

      crearResponse(workflowId, responseNumeroSade, suscripcion, documento);
    } catch (ParametroInvalidoException e) {
      logger.error("Error al consultar numero sade." + e.getMessage(), e);
      responseNumeroSade.setIdError("3");
      responseNumeroSade.setMotivo(e.getMessage());
    } catch (InconsistenciaDeDatosException e) {
      logger.error("Error al consultar numero sade." + e.getMessage(), e);
      responseNumeroSade.setIdError("3");
      responseNumeroSade.setMotivo(e.getMessage());

    } catch (Exception e) {
      responseNumeroSade.setIdError("1");
      responseNumeroSade.setMotivo(e.getMessage());
      suscripcionUtils.persistirLog(workflowId, e.getMessage(), Constantes.NOMBRE_PROCESO,
          Constantes.PROCESO_LOG_ESTADO_ERROR, sistemaOrigen);
      logger.error("Mensaje de error", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultarNumeroSade(RequestExternalConsultarNumeroSade) - end"); //$NON-NLS-1$
    }
    return responseNumeroSade;
  }

  private void validarDatosRequest(RequestExternalConsultarNumeroSade request)
      throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarDatosRequest(RequestExternalConsultarNumeroSade) - start"); //$NON-NLS-1$
    }

    if ((request.getId() == null) || request.getId().isEmpty() || (request.getOrigen() == null)
        || request.getOrigen().isEmpty()) {
      throw new ParametroInvalidoException(
          "Debe completar todos los datos para realizar la consulta.");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarDatosRequest(RequestExternalConsultarNumeroSade) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene el id de transaccion por workflowid. En caso de no existir devuelve
   * null.
   * 
   * @param workflowId
   * @return idTransaccion (Integer)
   * @throws Exception
   */
  private Integer obtenerIdTransaccionByWorkflowId(String workflowId) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerIdTransaccionByWorkflowId(String) - start"); //$NON-NLS-1$
    }

    try {
      Integer idTransaccion = this.documentoSolicitudService.findByWorkflowId(workflowId)
          .getIdTransaccion();

      if (logger.isDebugEnabled()) {
        logger.debug("obtenerIdTransaccionByWorkflowId(String) - end"); //$NON-NLS-1$
      }
      return idTransaccion;

    } catch (Exception e) {
      logger.error("Ha ocurrido un error al obtener el IdTransaccion cuyo workflowId es: "
          + workflowId + " - " + e.getMessage());
      throw e;
    }
  }

  private Integer obtenerIdTransaccionByNumeroSade(String numeroSade) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerIdTransaccionByNumeroSade(String) - start"); //$NON-NLS-1$
    }

    try {
      Integer idTransaccion = this.documentoSolicitudService.findByNumeroSade(numeroSade)
          .getIdTransaccion();

      if (logger.isDebugEnabled()) {
        logger.debug("obtenerIdTransaccionByNumeroSade(String) - end"); //$NON-NLS-1$
      }
      return idTransaccion;
    } catch (Exception e) {
      logger.error("Ha ocurrido un error al obtener el IdTransaccion cuyo workflowId es: "
          + numeroSade + " - " + e.getMessage());
      throw e;
    }
  }

  /**
   * 
   * @param workflowId
   * @param responseNumeroSade
   * @param suscripcion
   * @param documento
   * @throws Exception
   */
  private void crearResponse(String workflowId,
      ResponseExternalConsultaNumeroSade responseNumeroSade, SuscripcionDTO suscripcion,
      DocumentoDTO documento) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearResponse(String, ResponseExternalConsultaNumeroSade, SuscripcionDTO, DocumentoDTO) - start"); //$NON-NLS-1$
    }

    String numeroSade;

    if (documento != null) {
      numeroSade = documento.getNumero();
      responseNumeroSade.setNumeroSade(numeroSade);

      suscripcion.setEstado(Constantes.ESTADO_OK);
      suscripcionUtils.persistirSuscripcion(suscripcion);
      suscripcionUtils.persistirLog(workflowId, Constantes.PROCESO_LOG_MENSAJE_OK,
          Constantes.NOMBRE_PROCESO, Constantes.PROCESO_LOG_ESTADO_OK,
          suscripcion.getSistemaOrigen().getNombre());
    } else {
      org.jbpm.api.Execution execution = processEngine.getExecutionService().findExecutionById(workflowId);

      if (execution == null) {
        suscripcion.setEstado(Constantes.ESTADO_OK);
        suscripcionUtils.persistirSuscripcion(suscripcion);
        suscripcionUtils.persistirLog(workflowId, Constantes.PROCESO_LOG_MENSAJE_OK,
            Constantes.NOMBRE_PROCESO, Constantes.PROCESO_LOG_ESTADO_OK,
            suscripcion.getSistemaOrigen().getNombre());

        responseNumeroSade.setMotivo("El documento se encuentra en estado Cancelado");
      } else {
        suscripcion.setEstado(Constantes.ESTADO_PENDIENTE);
        suscripcionUtils.persistirSuscripcion(suscripcion);
        suscripcionUtils.persistirLog(workflowId, "La solicitud todavia no se encuentra cerrada.",
            Constantes.NOMBRE_PROCESO, Constantes.PROCESO_LOG_ESTADO_OK,
            suscripcion.getSistemaOrigen().getNombre());

        responseNumeroSade
            .setMotivo("El documento se encuentra en estado Pendiente de ser firmado");
        Set<String> activityNames = execution.findActiveActivityNames();
        if (activityNames.size() != 1) {
          throw new InconsistenciaDeDatosException(
              "Error: la execution deberia tener un solo estado");
        } else {
          responseNumeroSade.setEstadoTarea(activityNames.iterator().next());
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearResponse(String, ResponseExternalConsultaNumeroSade, SuscripcionDTO, DocumentoDTO) - end"); //$NON-NLS-1$
    }
  }

  public ResponseExternalConsultaDocumento consultarDocumentoPorNumero2(String numeroSade,
      String sistema, String usuario) throws ParametroInvalidoConsultaException,
      DocumentoNoExisteException, ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoPorNumero2(String, String, String) - start"); //$NON-NLS-1$
    }

    if (sistema.equals("ARCH")) {
      ResponseExternalConsultaDocumento response = new ResponseExternalConsultaDocumento();
      RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
      request.setNumeroDocumento(numeroSade);
      request.setUsuarioConsulta(usuario);
      DocumentoDTO documento = null;
      validarParametrosObligatorios(request);

      documento = obtenerDocumento(request);

      response.setNumeroDocumento(documento.getNumero());
      response.setUsuarioGenerador(documento.getUsuarioGenerador());
      response.setFechaCreacion(documento.getFechaCreacion());
      response.setTipoDocumento(documento.getTipo().getAcronimo());
      response.setSistemaOrigen(documento.getSistemaOrigen());
      response.setUsuarioIniciador(documento.getUsuarioIniciador());
      response.setNumeroEspecial(documento.getNumeroEspecial());
      response.setMotivo(documento.getMotivo());
      if (documento.getMotivoDepuracion() != null) {
        response.setUrlArchivo(Labels.getLabel("gedo.consultaDocumentos.motivoDepuracion"));
      } else {
        setearUrlDescarga(response, documento);
      }

      if (documento.getTipo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
          || documento.getTipo()
              .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
        try {
          Integer idTransaccion;
          if (documento.getWorkflowOrigen() != null) {
            idTransaccion = obtenerIdTransaccionByWorkflowId(documento.getWorkflowOrigen());
          } else {
            idTransaccion = obtenerIdTransaccionByNumeroSade(documento.getNumero());
          }
          if (idTransaccion != null) {
            response.setIdTransaccion(idTransaccion);
          }
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw new ErrorConsultaDocumentoException(e.getMessage());
        }
      }

      if (logger.isDebugEnabled()) {
        logger.debug("consultarDocumentoPorNumero2(String, String, String) - end"); //$NON-NLS-1$
      }
      return response;
    } else
      throw new ParametroInvalidoConsultaException("Sistema origen no permitido " + sistema);
  }

  public ResponseExternalConsultaDocumento consultarPorNumeroReservaTipoDoc(
      RequestExternalConsultaDocumento request) throws ParametroInvalidoConsultaException,
      DocumentoNoExisteException, SinPrivilegiosException, ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarPorNumeroReservaTipoDoc(RequestExternalConsultaDocumento) - start"); //$NON-NLS-1$
    }

    ResponseExternalConsultaDocumento response = new ResponseExternalConsultaDocumento();
    DocumentoDTO documento = null;

    validarParametrosObligatorios(request);

    documento = obtenerDocumento(request);
    String reparticion = obtenerReparticionUsuario(request.getUsuarioConsulta());

    if (!buscarDocumentosGedoService.puedeVerDocumentoGedo(documento, reparticion,
        request.getUsuarioConsulta())) {
      throw new SinPrivilegiosException((!(StringUtils.isEmpty(request.getNumeroEspecial())))
          ? request.getNumeroEspecial() : request.getNumeroDocumento());
    }
    response.setNumeroDocumento(documento.getNumero());
    response.setUsuarioGenerador(documento.getUsuarioGenerador());
    response.setFechaCreacion(documento.getFechaCreacion());
    response.setTipoDocumento(documento.getTipo().getAcronimo());
    response.setSistemaOrigen(documento.getSistemaOrigen());
    response.setUsuarioIniciador(documento.getUsuarioIniciador());
    response.setNumeroEspecial(documento.getNumeroEspecial());
    response.setMotivo(documento.getMotivo());
    if (documento.getMotivoDepuracion() != null) {
      response.setUrlArchivo(Labels.getLabel("gedo.consultaDocumentos.motivoDepuracion"));
    } else {
      setearUrlDescarga(response, documento);
    }

    if (documento.getTipo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE || documento
        .getTipo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      try {
        Integer idTransaccion;
        if (documento.getWorkflowOrigen() != null) {
          idTransaccion = obtenerIdTransaccionByWorkflowId(documento.getWorkflowOrigen());
        } else {
          idTransaccion = obtenerIdTransaccionByNumeroSade(documento.getNumero());
        }
        if (idTransaccion != null) {
          response.setIdTransaccion(idTransaccion);
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw new ErrorConsultaDocumentoException(e.getMessage());
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultarPorNumeroReservaTipoDoc(RequestExternalConsultaDocumento) - end"); //$NON-NLS-1$
    }
    return response;
  }

  private void setearUrlDescarga(ResponseExternalConsultaDocumento response,
      DocumentoDTO documento) {
    if (logger.isDebugEnabled()) {
      logger.debug("setearUrlDescarga(ResponseExternalConsultaDocumento, DocumentoDTO) - start"); //$NON-NLS-1$
    }

    // WEBDAV
    response.setUrlArchivo(
        gestionArchivosWebDavService.obtenerUbicacionDescarga(documento.getNumero()));

    if (logger.isDebugEnabled()) {
      logger.debug("setearUrlDescarga(ResponseExternalConsultaDocumento, DocumentoDTO) - end"); //$NON-NLS-1$
    }
  }

}
