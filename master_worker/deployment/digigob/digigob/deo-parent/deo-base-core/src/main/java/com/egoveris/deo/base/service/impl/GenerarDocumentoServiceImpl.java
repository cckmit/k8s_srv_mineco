package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.ArchivoDeTrabajoException;
import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.CantidadDatosException;
import com.egoveris.deo.base.exception.SinNumeracionSadeExcepcion;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.CerrarDocumentoService;
import com.egoveris.deo.base.service.DocumentoAdjuntoService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.FirmaDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.IndexarDocumentoService;
import com.egoveris.deo.base.service.ObtenerNumeracionEspecialService;
import com.egoveris.deo.base.service.ObtenerNumeracionSadeService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.UtilitariosServicios;
import com.egoveris.deo.base.util.UtilsDate;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.numerador.model.model.NumeroDTO;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.terasoluna.plus.common.exception.ApplicationException;

public abstract class GenerarDocumentoServiceImpl {

  private static final Logger logger = LoggerFactory.getLogger(GenerarDocumentoServiceImpl.class);

  @Autowired
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  @Autowired
  protected ObtenerNumeracionSadeService numeracionSadeService;
  @Autowired
  protected ObtenerNumeracionEspecialService obtenerNumeracionEspecialService;
  @Autowired
  protected PdfService pdfService;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  protected GestionArchivosWebDavService gestionArchivosWebDavService;
  @Autowired
  protected BuscarDocumentosGedoService buscarDocumentosGedoService;
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  protected CerrarDocumentoService cerrarDocumentoService;
  @Value("${gedo.maximoArchivos}")
  protected String maximoArchivos;
  @Autowired
  protected DocumentoAdjuntoService documentoAdjuntoService;
  @Autowired
  protected TipoDocumentoService tipoDocumentoService;
  @Autowired
  protected ProcessEngine processEngine;
  @Autowired
  private FirmaConjuntaService firmaConjuntaService;
  @Autowired
  private HistorialService historialService;
  // HACER UN SERVICE PARA DOCUMENTO
  @Value("${app.sistema.gestor.documental}")
  private String gestorDocumental;
  @Value("${app.ecosistema}")
  private String ecosistema;
  @Autowired
  private IndexarDocumentoService indexarService;
  
  @Autowired
  private FirmaDocumentoService firmaDocumentoService;

  protected abstract void validarContenidoDocumento(String tipoContenido)
      throws ValidacionContenidoException;

  protected abstract void validarArchivoASubirPorSusFirmas(byte[] data)
      throws ValidacionCampoFirmaException, ArchivoFirmadoException;

  protected abstract void generarDocumentoPDF(RequestGenerarDocumento request,
      Integer numeroFirmas, Boolean almacenarRepositorioTemporal)
      throws IOException, ApplicationException;

  public void generarDocumentoFirmadoConCertificado(RequestGenerarDocumento request)
      throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoFirmadoConCertificado(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    try {
      firmarDocumento(request);
    } catch (Exception e) {
      logger.error("Error en generación de documento con certificado", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoFirmadoConCertificado(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
  }

  private byte[] firmarDocumento(RequestGenerarDocumento request)
      throws SecurityNegocioException, ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("firmarDocumento(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    Map<String, String> campos = new HashMap<>();
    byte[] contenidoTemporal = request.getData();
    Integer numeroCampo = 0;
    if (request.getTipoDocumentoGedo().getEsFirmaExternaConEncabezado()) {
      numeroCampo = 1;
    }

    if (request.getCargo() != null) {
      campos.put(PdfService.USUARIO_ + numeroCampo, request.getNombreYApellido());
      campos.put(PdfService.CARGO_ + numeroCampo, request.getCargo());
      campos.put(PdfService.REPARTICION_ + numeroCampo, request.getReparticion());
      campos.put(PdfService.SECTOR_ + numeroCampo, request.getSector());
    } else {
      Usuario datosUsuario = this.usuarioService.obtenerUsuario(request.getUsuario());
      String usuarioBuscar = request.getUsuario();
      if (request.getUsuarioApoderador() != null) {
        if (this.usuarioService.licenciaActiva(request.getUsuarioApoderador(), new Date())) {
          usuarioBuscar = request.getUsuarioApoderador();
        }
      }
      if (request.getTipoDocumentoGedo().getEsFirmaConjunta()) {
        numeroCampo = firmaConjuntaService.nroFirmaFirmante(usuarioBuscar, request.getWorkflowId(),
            false);
      }
      String usuarioApoderador = request.getUsuarioApoderador();
      String apellidoYNombre = datosUsuario.getNombreApellido();
      if (StringUtils.isNotEmpty(usuarioApoderador)) {
        apellidoYNombre = "P/P " + apellidoYNombre;
      }
      campos.put(PdfService.USUARIO_ + numeroCampo, apellidoYNombre);
      if (datosUsuario.getCargo() == null || datosUsuario.getCargo().equals("")) {
        campos.put(PdfService.CARGO_ + numeroCampo, datosUsuario.getOcupacion());
      } else {
        campos.put(PdfService.CARGO_ + numeroCampo, datosUsuario.getCargo());
      }
      // MULTIREPARTICION - va la reparticion del track
      campos.put(PdfService.REPARTICION_ + numeroCampo,
          datosUsuario.getNombreReparticionOriginal());
      campos.put(PdfService.SECTOR_ + numeroCampo,
              datosUsuario.getCodigoSectorInternoOriginal());
    }

    try {
      contenidoTemporal = this.pdfService.actualizarCampoPdf(campos, contenidoTemporal);
    } catch (Exception e) {
      logger.error("Error en actualizar documento", e);
    }
    try {
        boolean importado = false;
        if (request.getTipoDocumentoGedo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO || request.getTipoDocumentoGedo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      	  importado = true;
          }
    	
      contenidoTemporal = pdfService.firmarConCertificadoServidor(request, contenidoTemporal,
          PdfService.SIGNATURE_ + numeroCampo, importado);
    } catch (Exception e) {
      logger.error("Error en firmar documento con certificado", e);
      e.printStackTrace();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("firmarDocumento(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return contenidoTemporal;
  }

  /**
   * Permite generar un documento y firmarlo con certificado. Actualiza las
   * tablas GEDO_HISTORIAL/ GEDO_FIRMANTES(si es Firma Conjunta). Realiza
   * Rollback en caso de fallar y no impacta los cambios en la base.
   */
  public void generarDocumentoFirmadoConCertificadoManual(RequestGenerarDocumento request,
      boolean subirTemporal) throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoFirmadoConCertificadoManual(RequestGenerarDocumento, boolean) - start"); //$NON-NLS-1$
    }

    byte[] contenidoTemporal = firmarDocumento(request);
    impactarEnBaseHistorialFirmantes(request.getWorkflowId(),
        request.getTipoDocumentoGedo().getEsFirmaConjunta());
    if (subirTemporal) {
      if (Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
        this.processEngine.getExecutionService().setVariable(request.getWorkflowId(),
            Constantes.VAR_ID_GUARDA_DOCUMENTAL, request.getIdGuardaDocumental());

      } else {
        // WEBDAV
        this.gestionArchivosWebDavService.subirArchivoTemporalWebDav(request.getNombreArchivo(),
            contenidoTemporal);
      }
    } else
      request.setData(contenidoTemporal);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoFirmadoConCertificadoManual(RequestGenerarDocumento, boolean) - end"); //$NON-NLS-1$
    }
  }

  public void impactarEnBaseHistorialFirmantes(String workflowId, boolean esFirmaConjunta) {
    if (logger.isDebugEnabled()) {
      logger.debug("impactarEnBaseHistorialFirmantes(String, boolean) - start"); //$NON-NLS-1$
    }

    if (esFirmaConjunta) {
      actualizarFirmaConjunta(workflowId);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("impactarEnBaseHistorialFirmantes(String, boolean) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Actualiza la tabla GEDO_FIRMANTES, al usuario que firma le cambia el estado
   * de firma.
   */
  private void actualizarFirmaConjunta(String workflowId) {
    if (logger.isDebugEnabled()) {
      logger.debug("actualizarFirmaConjunta(String) - start"); //$NON-NLS-1$
    }

    try {
      String usuarioApoderador = (String) processEngine.getExecutionService()
          .getVariable(workflowId, Constantes.VAR_USUARIO_APODERADOR);
      String usuarioActual = (String) processEngine.getExecutionService().getVariable(workflowId,
          Constantes.USER_ACTUAL);
      String usuarioFirmanteBuscar;
      if (StringUtils.isEmpty(usuarioApoderador)) {
        usuarioFirmanteBuscar = usuarioActual;
        firmaConjuntaService.actualizarFirmante(usuarioFirmanteBuscar, true, workflowId, null);
      } else {
        usuarioFirmanteBuscar = usuarioApoderador;
        firmaConjuntaService.actualizarFirmante(usuarioFirmanteBuscar, true, workflowId,
            usuarioActual);
      }

    } catch (Exception e) {
      logger.error("Error actualizando usuario firmante " + e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("actualizarFirmaConjunta(String) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Invoca servicio del numerador .
   * 
   * @param request
   * @return
   */
  protected NumeroDTO obtieneNumeracionSade(RequestGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtieneNumeracionSade(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    NumeroDTO numeroSade = null;
    try {
      numeroSade = numeracionSadeService.buscarNumeroSecuenciaSade(request.getUsuario(),
          request.getTipoDocumentoGedo().getCodigoTipoDocumentoSade());
    } catch (Exception e) {
      logger.error("Error buscando número SADE", e);
      throw new SinNumeracionSadeExcepcion("No se pudo obtener el número SADE.");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtieneNumeracionSade(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return numeroSade;
  }

  /**
   * Crear el objeto numeros usados
   * 
   * @param request
   * @param anio
   * @return
   */
  protected NumerosUsadosDTO crearNumerosUsados(RequestGenerarDocumento request, String anio) {
    if (logger.isDebugEnabled()) {
      logger.debug("crearNumerosUsados(RequestGenerarDocumento, String) - start"); //$NON-NLS-1$
    }

    NumerosUsadosDTO numerosUsados = new NumerosUsadosDTO();
    numerosUsados.setAnio(anio);
    numerosUsados.setCodigoReparticion(request.getCodigoReparticion());
    numerosUsados.setNumeroSADE(request.getNumero());
    numerosUsados.setTipoDocumento(request.getTipoDocumentoGedo());
    if (request.getNumeroEspecial() != null) {
      numerosUsados.setNumeroEspecial(String.valueOf(request.getNumeroEspecial().getNumero()));
    } else {
      numerosUsados.setNumeroEspecial(null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("crearNumerosUsados(RequestGenerarDocumento, String) - end"); //$NON-NLS-1$
    }
    return numerosUsados;
  }

  protected ResponseGenerarDocumento cerrarDocumento(RequestGenerarDocumento request,
      List<String> receptoresAviso) {
    if (logger.isDebugEnabled()) {
      logger.debug("cerrarDocumento(RequestGenerarDocumento, List<String>) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialDTO numeroEspecial = null;
    ResponseGenerarDocumento responseGenerarDocumento = null;
    // File temporalFirmado = null;
    String numeroDocumento = null;
    NumerosUsadosDTO numerosUsados = null;
    NumeroDTO numeroSecuencia = null;
    try {
      responseGenerarDocumento = new ResponseGenerarDocumento();

      if (request.getData() == null) {
        // WEBDAV
        request.setData(this.gestionArchivosWebDavService
            .obtenerRecursoTemporalWebDav(request.getNombreArchivo()));

      }
      byte[] contenidoTemporal = request.getData();

      // Bloquear número especial.
      if (Boolean.TRUE.equals(request.getTipoDocumentoGedo().getEsEspecial())) {
        numeroEspecial = this.obtenerNumeracionEspecialService.bloquearNumeroEspecial(request);
      }
      if (org.apache.commons.lang.StringUtils.isEmpty(request.getNumero())) {
        numeroSecuencia = this.obtieneNumeracionSade(request);
        String numeroSade = UtilitariosServicios.armarCodigoCaratula(
            request.getTipoDocumentoGedo().getCodigoTipoDocumentoSade(),
            numeroSecuencia.getNumero(), numeroSecuencia.getAnio(), request.getCodigoReparticion(),
            getEcosistema());
        request.setNumero(numeroSade);
        responseGenerarDocumento.setNumero(numeroSade);
      }
      responseGenerarDocumento.setCodigoReparticion(request.getCodigoReparticion());
      if (Boolean.TRUE.equals(request.getTipoDocumentoGedo().getEsEspecial())) {
        responseGenerarDocumento.setNumeroEspecial(numeroEspecial);
        request.setNumeroEspecial(numeroEspecial);
        numeroDocumento = generarNumeroEspecial(request.getTipoDocumentoGedo(), numeroEspecial);
        numerosUsados = crearNumerosUsados(request, UtilsDate.obtenerAnioActual());
        this.obtenerNumeracionEspecialService.guardar(numerosUsados);
        logger.debug("numero SADE guardado " + numerosUsados.getNumeroSADE() + " usuario: "
            + request.getUsuario() + " workflowid " + request.getWorkflowId());
      } else {
        numeroDocumento = request.getNumero();
      }

      Map<String, String> camposActualizar = new HashMap<String, String>();
      // Se actualiza los campos de todos los documentos pq los importados
      // e incorporados ahora (Feature #1725)se generan con template

      camposActualizar.put(PdfService.NUMERO_DOCUMENTO, numeroDocumento);
      camposActualizar.put(PdfService.FECHA, UtilsDate.convertDateToFormalString(new Date()));
//      camposActualizar.put(PdfService.FECHA2,
//          UtilsDate.convertDateToFechaCierreParaArchivoDefinitivo(new Date()));
      contenidoTemporal = this.pdfService.actualizarCampoPdf(camposActualizar, contenidoTemporal);

      if (request.getTipoDocumentoGedo()
          .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
          || request.getTipoDocumentoGedo()
              .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
        contenidoTemporal = this.pdfService.agregarNumeroPaginaNumeroSADE(contenidoTemporal,
            numeroDocumento);
      }
      /**
       * @FIXME: Se implementa la creación del campo de cierre si éste no
       *         existe, el método se encuentra "deprecated", puesto que para
       *         los documentos nuevos, el campo para la firma de cierre, se
       *         crea junto con los campos de firma del funcionario.
       */

      boolean importado = false;
      if (request.getTipoDocumentoGedo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO || request.getTipoDocumentoGedo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
    	  importado = true;
        }
      
      contenidoTemporal = pdfService.firmarConCertificadoServidor(request, contenidoTemporal,
          PdfService.SIGNATURE_CIERRE, importado);

      // Firmar con certificado si tiene documento asociado (Rectificación)
      // si es por token el documento asociado ya se firmo en el applet
      // si es por certificado todavia no se firmo el campo nuevo --> se firma
      // aca
      String nombreArchivoDoc1Temp = null;
      if (!StringUtils.isEmpty(request.getWorkflowId())) {
        nombreArchivoDoc1Temp = (String) processEngine.getExecutionService().getVariable(
            request.getWorkflowId(), Constantes.NOMBRE_DOCUMENTO_1_TEMPORAL_CAMPO_REC);

        if (nombreArchivoDoc1Temp != null) {
          byte[] doc1Temp;

          doc1Temp = gestionArchivosWebDavService
              .obtenerRecursoTemporalWebDav(nombreArchivoDoc1Temp);

          if (tieneCampoFirmaLibre(doc1Temp)) {
            doc1Temp = pdfService.firmarConCertificadoServidor(request, doc1Temp,
                Constantes.SIGNATURE_RECTIFICACION, importado);
          }
          String numeroSADEDoc1Orig = (String) processEngine.getExecutionService()
              .getVariable(request.getWorkflowId(), Constantes.NUMERO_SADE_DOCUMENTO_1_ORIGINAL);

          if (Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {

            // WEBDAV
            gestionArchivosWebDavService.subirArchivoDeTrabajoWebDav(numeroSADEDoc1Orig, doc1Temp,
                numeroSADEDoc1Orig + ".pdf");
          }

        }
      }
      request.setData(contenidoTemporal);
      // WEBDAV
      String urlArchivoGenerado = this.gestionArchivosWebDavService.subirDocumentoWebDav(request);
      responseGenerarDocumento.setUrlArchivoGenerado(urlArchivoGenerado);
      // Para la copia de archivos de trabajo, se valida que el requerimiento
      // tenga un id de workflow asociado.
      if (request.getWorkflowId() != null) {
        Integer idFirmante = (Integer) processEngine.getExecutionService()
            .getVariable(request.getWorkflowId(), Constantes.ID_FIRMANTE);

        if (idFirmante != null && request.getTipoDocumentoGedo().getEsFirmaConjunta()) {
          this.gestionArchivosWebDavService.subirDirectorioRevisionWebDav(request);
        }
      }
      if (request.getWorkflowId() != null)
        this.copiarArchivosDeTrabajoTemporales(request);

      this.cerrarDocumentoService.guardarCierreDocumento(request, receptoresAviso, numerosUsados);

      // Confirma en numero pedido al numerador
      // No propaga la excepción ya que en ese caso se estaría cancelando la
      // generación del documento
      // que ya fue confirmada por el método anterior
      // cerrarDocumentoService.guardarCierreDocumento
      // Existe un proceso batch que regulariza cualquier número que no fue
      // confirmado
      try {
        if (numeroSecuencia != null) {
//          this.numeracionSadeService.confirmarNumeroSADE(numeroSecuencia.getAnio(), // Falta  ID_SADE_NUMERO_CARATULA
//              numeroSecuencia.getNumero());
        }
      } catch (Exception exNumerador) {
        logger.error("Error al confirmar el número " + numeroSecuencia.getAnio() + "-"
            + numeroSecuencia.getNumero() + ": " + exNumerador.getMessage(), exNumerador);
      }

      // Borrar archivos temporales, aplica para los procesos que tengan id de
      // workflow asociado.
      try {
        if (request.getWorkflowId() != null
            && !Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
          this.gestionArchivosWebDavService
              .borrarArchivoTemporalWebDav(request.getNombreArchivo());
          if (nombreArchivoDoc1Temp != null) {
            this.gestionArchivosWebDavService.borrarArchivoTemporalWebDav(nombreArchivoDoc1Temp);
          }
        } else { // PROBAR
          if (request.getWorkflowId() != null
              && !request.getTipoDocumentoGedo().getEsFirmaConjunta()) {

            // WEBDAV
            this.gestionArchivosWebDavService
                .borrarArchivoTemporalWebDav(request.getNombreArchivo());

          }
        }
        List<DocumentoAdjuntoDTO> documentosAdjuntos = this.documentoAdjuntoService
            .buscarArchivosDeTrabajoPorProceso(request.getWorkflowId());
        if (!documentosAdjuntos.isEmpty()) {
          DocumentoAdjuntoDTO documentoAdjunto = documentosAdjuntos.get(0);

          // WEBDAV
          this.documentoAdjuntoService.borrarDocumentoAdjuntoTemporalWebDav(
              documentoAdjunto.getPathRelativo(), documentoAdjunto.getNombreArchivo());

          if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
            this.borrarArchivosDeTrabajoTemporales(request.getWorkflowId(),
                request.getNombreArchivo());
          }
        }
      } catch (Exception e) {
        logger.error(
            "Cierre Documento - No se pudieron borrar archivos temporales en el repositorio", e);
      }
      indexarService.indexarEnSolr(request.getNumero());
    } catch (Exception e) {
      logger.error("cerrarDocumento(RequestGenerarDocumento, List<String>)", e); //$NON-NLS-1$

      // Anula el numero solicitado al numerador en caso que se produzca alguna
      // excepción
      try {
        if (numeroSecuencia != null) {
          this.numeracionSadeService.anularNumeroSADE(numeroSecuencia.getAnio(),
              numeroSecuencia.getNumero());
        }

      } catch (Exception numerador) {
        logger.error("Error al anular el número", numerador);
      }

      // Realiza una regresion del historial generado
      this.historialService.actualizarHistorial(request.getWorkflowId());

      // Valida si ya se subió el archivo al repositorio -> Borrar archivo
      if (responseGenerarDocumento != null
          && responseGenerarDocumento.getUrlArchivoGenerado() != null) {

        // WEBDAV
        this.gestionArchivosWebDavService
            .borrarArchivoWebDav(responseGenerarDocumento.getNumero());

      }
      // Valida si se adquirió el lock del número especial -> Desbloquear número
      // especial.
      if (numeroEspecial != null) {
        if (this.ecosistema.trim().isEmpty()) {
          this.obtenerNumeracionEspecialService.rollbackNumeroEspecial(numeroEspecial);
        } else {
          this.obtenerNumeracionEspecialService.rollbackNumeroEspecialEcosistema(numeroEspecial);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cerrarDocumento(RequestGenerarDocumento, List<String>) - end"); //$NON-NLS-1$
    }
    return responseGenerarDocumento;
  }

  /**
   * Crear y guardar el documento en la base de datos.
   * 
   * @param request
   * @return
   */
  private String generarNumeroEspecial(TipoDocumentoDTO tipoDoc,
      NumeracionEspecialDTO numeroEspecial) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarNumeroEspecial(TipoDocumentoDTO, NumeracionEspecialDTO) - start"); //$NON-NLS-1$
    }

    if (!getEcosistema().trim().isEmpty()) {
      String returnString = (tipoDoc.getAcronimo() + "-" + numeroEspecial.getAnio() + "-"
          + numeroEspecial.getNumero() + "-" + getEcosistema() + "-"
          + numeroEspecial.getCodigoReparticion()).trim();
      if (logger.isDebugEnabled()) {
        logger.debug("generarNumeroEspecial(TipoDocumentoDTO, NumeracionEspecialDTO) - end"); //$NON-NLS-1$
      }
      return returnString;
    } else {
      String returnString = (tipoDoc.getAcronimo() + "-" + numeroEspecial.getAnio() + "-"
          + numeroEspecial.getNumero() + "-" + numeroEspecial.getCodigoReparticion()).trim();
      if (logger.isDebugEnabled()) {
        logger.debug("generarNumeroEspecial(TipoDocumentoDTO, NumeracionEspecialDTO) - end"); //$NON-NLS-1$
      }
      return returnString;
    }
  }

  // Metodo que permite verificar si hay algun campo de firma libre para firmar
  // Se creo para validar si el documento 1 ya tiene el campo de rectificacion
  // firmado
  public boolean tieneCampoFirmaLibre(byte[] data) throws ValidacionCampoFirmaException {
    if (logger.isDebugEnabled()) {
      logger.debug("tieneCampoFirmaLibre(byte[]) - start"); //$NON-NLS-1$
    }

    boolean res = true;
    PdfReader pdfReader = null;
    try {
      pdfReader = new PdfReader(data);
      List<String> camposFirmaEnBlanco = pdfReader.getAcroFields().getBlankSignatureNames();
      if (camposFirmaEnBlanco.size() == 0) {
        res = false;
      }
    } catch (IOException e) {
      logger.error("Error: Validando si hay un Campo Libre de Firma");
      throw new ValidacionCampoFirmaException(
          "Error: Validando firmas del documento" + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("tieneCampoFirmaLibre(byte[]) - end"); //$NON-NLS-1$
    }
    return res;
  }

  /**
   * Copia los archivos de trabajo de la ubicación temporal a la ubicación
   * definitiva.
   * 
   * @param request
   * @throws Exception
   */
  protected void copiarArchivosDeTrabajoTemporales(RequestGenerarDocumento request)
      throws ArchivoDeTrabajoException {
    if (logger.isDebugEnabled()) {
      logger.debug("copiarArchivosDeTrabajoTemporales(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    List<ArchivoDeTrabajoDTO> archivosDeTrabajo;
    archivosDeTrabajo = this.archivoDeTrabajoService
        .buscarArchivosDeTrabajoPorProceso(request.getWorkflowId());
    if (!org.springframework.util.CollectionUtils.isEmpty(archivosDeTrabajo)) {
      for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivosDeTrabajo) {

        byte[] contenidoArchivo = archivoDeTrabajoService.obtenerArchivoDeTrabajoTemporalWebDav(
            archivoDeTrabajo.getPathRelativo(), archivoDeTrabajo.getNombreArchivo());
        String pathDefinitivo = archivoDeTrabajoService.subirArchivoDeTrabajoWebDav(
            request.getNumero(), contenidoArchivo, archivoDeTrabajo.getNombreArchivo());
        archivoDeTrabajo.setDefinitivo(true);
        archivoDeTrabajo.setPathRelativo(pathDefinitivo);
        archivoDeTrabajoService.grabarArchivoDeTrabajo(archivoDeTrabajo);

      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("copiarArchivosDeTrabajoTemporales(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Borra los archivos de trabajo de la ubicación temporal
   * 
   * @param request
   * @param documento
   * @throws Exception
   */
  protected void borrarArchivosDeTrabajoTemporales(String workFlowId, String nombreArchivoTemporal)
      throws ArchivoDeTrabajoException {
    if (logger.isDebugEnabled()) {
      logger.debug("borrarArchivosDeTrabajoTemporales(String, String) - start"); //$NON-NLS-1$
    }

    List<ArchivoDeTrabajoDTO> archivosDeTrabajo;
    String archivoUrl = nombreArchivoTemporal.substring(0, nombreArchivoTemporal.lastIndexOf('.'));
    archivosDeTrabajo = this.archivoDeTrabajoService.buscarArchivosDeTrabajoPorProceso(workFlowId);
    if (!org.springframework.util.CollectionUtils.isEmpty(archivosDeTrabajo)) {
      for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivosDeTrabajo) {
        archivoDeTrabajoService.borrarArchivoDeTrabajoTemporal(archivoUrl,
            archivoDeTrabajo.getNombreArchivo());
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("borrarArchivosDeTrabajoTemporales(String, String) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Generación de documento externo, el cual esta conformado por los siguientes
   * pasos: 1. Validación tamaño del contenido. 2. Validación del contenido del
   * documento. 3. Validación que asegure que el archivo subido no este firmado.
   * 4. Creación de archivo pdf. 5. Firma el documento con certificado de
   * servidor. 6. Cierre del documento. Este proceso es utilizado para la
   * generación externa de documentos manual (con template), e importados (sin
   * template).
   * 
   * @param request
   * @return
   * @throws IOException
   * @throws DocumentException 
    * @throws ApplicationExceptio
    * @throws Exception
   */
  protected ResponseGenerarDocumento generarDocumentoExterno(RequestGenerarDocumento request)
      throws ApplicationException, IOException{
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoExterno(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    ResponseGenerarDocumento response;
    if (request.getTipoDocumentoGedo()
        .getTipoProduccion() != Constantes.TIPO_PRODUCCION_TEMPLATE) {
      validarTamanioContenido(request.getData());
      validarContenidoTipoDocumento(request);
      if (request.getTipoArchivo().equalsIgnoreCase("pdf"))
        validarArchivoASubirPorSusFirmas(request.getData());
    }

    generarDocumentoPDF(request, 1, false);
    
    byte[] contenidoConFirma = firmaDocumentoService.firmaDocumentoConServExternal(request);
//    byte[] contenidoConFirma = pdfService.firmarConCertificadoServidor(request, request.getData(), PdfService.SIGNATURE_+0, false);
    request.setData(contenidoConFirma);
    response = cerrarDocumento(request, null);

    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoExterno(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return response;
  }

  /**
   * Valida que el tamaño del arreglo de bytes que almacena el contenido del
   * documento, no supere el tamaño definido por el sistema.
   * 
   * @param data
   * @throws CantidadDatosException
   */
  protected void validarTamanioContenido(byte[] data) throws CantidadDatosException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarTamanioContenido(byte[]) - start"); //$NON-NLS-1$
    }

    int maxSizeArchivo = Integer.parseInt(this.maximoArchivos);
    int maxSizeArchivoBytesTamanio = maxSizeArchivo * Constantes.FACTOR_CONVERSION;
    if (data.length > maxSizeArchivoBytesTamanio)
      throw new CantidadDatosException(
          "El contenido del documento supera la cantidad soportada de: "
              + maxSizeArchivoBytesTamanio + " Bytes.");

    if (logger.isDebugEnabled()) {
      logger.debug("validarTamanioContenido(byte[]) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene el tipo de contenido del flujo de bytes, si el parámetro de archivo
   * tipoArchivo es null. Valida que el tipo de contenido esté acorde con el
   * tipo de documento que se va a generar.
   * 
   * @param tipoArchivo
   * @param contenido
   * @throws ValidacionContenidoException
   */
  protected void validarContenidoTipoDocumento(RequestGenerarDocumento request)
      throws ValidacionContenidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarContenidoTipoDocumento(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    if (StringUtils.isEmpty(request.getTipoArchivo()))
      request.setTipoArchivo(obtenerTipoArchivo(request.getData()));
    else if (request.getTipoArchivo().contains("."))
      request.setTipoArchivo(StringUtils.removeStart(request.getTipoArchivo(), "."));
    //validarContenidoDocumento(request.getTipoArchivo());

    if (logger.isDebugEnabled()) {
      logger.debug("validarContenidoTipoDocumento(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Detecta el tipo de contenido almacenado en el arreglo de bytes.
   * 
   * @param datos
   * @return La extensión del archivo, identificando el tipo de contenido.
   * @throws ValidacionContenidoException
   */
  private static String obtenerTipoArchivo(byte[] datos) throws ValidacionContenidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipoArchivo(byte[]) - start"); //$NON-NLS-1$
    }

    String tipoContenido = null;
    try {
      tipoContenido = UtilitariosServicios.obtenerTipoContenido(datos);
    } catch (Exception e) {
      logger.error("obtenerTipoArchivo(byte[])", e); //$NON-NLS-1$

      throw new ValidacionContenidoException(
          "No ha sido posible obtener el tipo de contenido de la información enviada", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipoArchivo(byte[]) - end"); //$NON-NLS-1$
    }
    return tipoContenido;
  }

  public void setEcosistema(String ecosistema) {
    this.ecosistema = ecosistema;
  }

  public String getEcosistema() {
    return ecosistema;
  }
}
