package com.egoveris.deo.base.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

import com.egoveris.deo.base.exception.NegocioSadeRuntimeException;
import com.egoveris.deo.base.model.Documento;
import com.egoveris.deo.base.model.DocumentoSolicitud;
import com.egoveris.deo.base.repository.DocumentoRepository;
import com.egoveris.deo.base.repository.DocumentoSolicitudRepository;
import com.egoveris.deo.base.repository.TipoReservaRepository;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.AvisoService;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.CerrarDocumentoService;
import com.egoveris.deo.base.service.ComunicacionService;
import com.egoveris.deo.base.service.DocumentoPublicableService;
import com.egoveris.deo.base.service.DocumentoTemplateService;
import com.egoveris.deo.base.service.IEcosistemaService;
import com.egoveris.deo.base.service.ObtenerNumeracionEspecialService;
import com.egoveris.deo.base.util.UtilsDate;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDetalle;
import com.egoveris.deo.model.model.ComunicacionDTO;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.DocumentoTemplateDTO;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.model.model.ReparticionAcumuladaDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoReservaDTO;
import com.egoveris.deo.model.model.UsuarioReservadoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@Service
@Transactional
public class CerrarDocumentoServiceImpl implements CerrarDocumentoService {

  private static final Logger logger = LoggerFactory.getLogger(CerrarDocumentoServiceImpl.class);

  @Autowired
  private DocumentoRepository documentoRepo;
  @Autowired
  private AvisoService avisoService;
  @Autowired
  private ComunicacionService comunicacionService;
  @Autowired
  private ObtenerNumeracionEspecialService obtenerNumeracionEspecialService;
  @Autowired
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  @Autowired
  private TipoReservaRepository tipoReservaRepo;
  @Autowired
  private DocumentoSolicitudRepository documentoSolicitudRepo;
  @Autowired
  private DocumentoTemplateService documentoTemplateService;
  @Autowired
  public IUsuarioService usuarioService;
  @Autowired
  private BuscarDocumentosGedoService buscarDocGedoSer;
  @Autowired
  private ProcessEngine processEngine;
  @Value("${app.name}")
  private String moduloOrigen;
  @Value("${app.mail.ccoo.link}")
  private String linkCcoo;
  @Autowired
  private IEcosistemaService ecosistemaService;
  @Autowired
  private DocumentoPublicableService documentoPublicableService;
  
  private DozerBeanMapper mapper = new DozerBeanMapper();
 
   
  @Override
  public void guardarCierreDocumento(RequestGenerarDocumento request, List<String> receptoresAviso,
      NumerosUsadosDTO numerosUsados) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarCierreDocumento(RequestGenerarDocumento, List<String>, NumerosUsadosDTO) - start"); //$NON-NLS-1$
    }

    // Guardar documento en base de datos GEDO.
    DocumentoDTO documento = this.guardarInformacionDocumento(request);
    // Actualizar numeracionEspecial y numerosUsados
    if (Boolean.TRUE.equals(request.getTipoDocumentoGedo().getEsEspecial())) {
      this.obtenerNumeracionEspecialService
          .actualizarNumerosEspeciales(request.getNumeroEspecial(), numerosUsados);
    }

    // Guardo comunicacion si es documento comunicable
    if (request.getTipoDocumentoGedo().getEsComunicable()) {
      try {
        ComunicacionDTO comunicacion = guardarComunicacion(request, documento);
        if (request.getIdComunicacionRespondida() != null) {
          ComunicacionDTO co = actualizarComunicacionRespuesta(request, comunicacion);
          comunicacionService.actualizarComunicacion(co);
        }
        envioMail(comunicacion);
      } catch (SecurityNegocioException e) {
        logger.error("guardarCierreDocumento(RequestGenerarDocumento, List<String>, NumerosUsadosDTO)", e); //$NON-NLS-1$

        throw new NegocioSadeRuntimeException("Error al guardar el cierre de documentos.");

      }
    }

    // Enviar aviso de firma a receptores
    if (receptoresAviso != null && !receptoresAviso.isEmpty())
      this.registrarAvisosFirma(documento, receptoresAviso, request.getUsuario());
    if (request.getTipoDocumentoGedo() != null
        && request.getTipoDocumentoGedo()
            .getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
        || request.getTipoDocumentoGedo()
            .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      guardarDocumentoSolicitud(request);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardarCierreDocumento(RequestGenerarDocumento, List<String>, NumerosUsadosDTO) - end"); //$NON-NLS-1$
    }
  }

  private ComunicacionDTO actualizarComunicacionRespuesta(RequestGenerarDocumento request,
      ComunicacionDTO comunicacion) {
    if (logger.isDebugEnabled()) {
      logger.debug("actualizarComunicacionRespuesta(RequestGenerarDocumento, ComunicacionDTO) - start"); //$NON-NLS-1$
    }

    ComunicacionDTO co = comunicacionService
        .buscarComunicacionPorId(request.getIdComunicacionRespondida());

    if (logger.isDebugEnabled()) {
      logger.debug("actualizarComunicacionRespuesta(RequestGenerarDocumento, ComunicacionDTO) - end"); //$NON-NLS-1$
    }
    return co;
  }

  private ComunicacionDTO guardarComunicacion(RequestGenerarDocumento request,
      DocumentoDTO documento) throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarComunicacion(RequestGenerarDocumento, DocumentoDTO) - start"); //$NON-NLS-1$
    }

    ComunicacionDTO comunicacion = new ComunicacionDTO();
    comunicacion.setId(null);
    comunicacion.setDocumento(documento);
    comunicacion.setUsuarioCreador(request.getUsuarioFirmante());
    comunicacion.setNombreApellidoUsuario(
        this.usuarioService.obtenerUsuario(comunicacion.getUsuarioCreador()) != null
            ? this.usuarioService.obtenerUsuario(comunicacion.getUsuarioCreador())
                .getNombreApellido()
            : "N/D");

    comunicacion.setFechaCreacion(documento.getFechaCreacion());
    comunicacion.setFechaEliminacion(request.getListaUsuariosDestinatarios().isEmpty()
        && request.getListaUsuariosDestinatariosCopia().isEmpty()
        && request.getListaUsuariosDestinatariosCopiaOculta().isEmpty()
            ? comunicacion.getFechaCreacion() : null);
    comunicacion.setMensaje(request.getMensajeDestinatario());

    if (request.getIdComunicacionRespondida() != null) {
      ComunicacionDTO co = comunicacionService
          .buscarComunicacionPorId(request.getIdComunicacionRespondida());
      comunicacion.setNroComunicacionRespondida(co.getDocumento().getNumero());
    }
    List<ArchivoDeTrabajoDetalle> listaAdjuntos = this.buscarDocGedoSer
        .buscarDocumentoDetalle(documento.getNumero(), comunicacion.getUsuarioCreador())
        .getListaArchivosDeTrabajoDetalle();

    comunicacion
        .setTieneAdjuntos(listaAdjuntos != null && !listaAdjuntos.isEmpty() ? true : false);
    comunicacionService.guardarComunicacion(comunicacion);

    if (logger.isDebugEnabled()) {
      logger.debug("guardarComunicacion(RequestGenerarDocumento, DocumentoDTO) - end"); //$NON-NLS-1$
    }
    return comunicacion;
  }

  private void envioMail(ComunicacionDTO comunicacion) throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("envioMail(ComunicacionDTO) - start"); //$NON-NLS-1$
    }

    Usuario usuario;
    String tipoDocumento;
    StringBuilder cadenaLogo = new StringBuilder();
    cadenaLogo.append(this.moduloOrigen);
    cadenaLogo.append("/avisoNuevaComunicacionOficial.ftl");

    Map<String, String> variablesTemplate = new HashMap<>();
    if ("ME".equals(comunicacion.getDocumento().getTipo().getCodigoTipoDocumentoSade())) {
      tipoDocumento = "MEMO";
    } else {
      tipoDocumento = "NOTA";
    }

    variablesTemplate.put("linkAComunicacionOficial", this.linkCcoo);

    variablesTemplate.put("tipoComunicacionOficial", tipoDocumento);

    variablesTemplate.put("nroCaratula", comunicacion.getDocumento().getNumero());

    variablesTemplate.put("contieneAdjuntos", "Si");

    variablesTemplate.put("mensajeDelOriginante",
        comunicacion.getMensaje() != "" ? comunicacion.getMensaje() : "");

    Usuario usuarioCreador = this.usuarioService.obtenerUsuario(comunicacion.getUsuarioCreador());

    variablesTemplate.put("originante", usuarioCreador.getNombreApellido());

    variablesTemplate.put("cargo", usuarioCreador.getCargo());

    if (logger.isDebugEnabled()) {
      logger.debug("envioMail(ComunicacionDTO) - end"); //$NON-NLS-1$
    }
  }

  private void guardarDocumentoSolicitud(RequestGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarDocumentoSolicitud(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    try {

      DocumentoSolicitud documentoSolicitud = new DocumentoSolicitud();

      documentoSolicitud.setNumeroSade(request.getNumero());
      documentoSolicitud.setWorkflowId(request.getWorkflowId());

		if (request.getIdTransaccion() != null) {
			documentoSolicitud.setIdTransaccion((double) request.getIdTransaccion().intValue());
		} else if (request.getWorkflowId() != null) {
			DocumentoTemplateDTO docTemplate = documentoTemplateService.findByWorkflowId(request.getWorkflowId(),
					request.getTipoDocumentoGedo());
			if (docTemplate != null) {
				documentoSolicitud.setIdTransaccion(Double.valueOf(docTemplate.getIdTransaccion().toString()));
			}
		}
      documentoSolicitudRepo.save(documentoSolicitud);
    } catch (Exception e) {
      logger.error("No se ha podido persistir el DocumentoSolicitud - " + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardarDocumentoSolicitud(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Guarda lista de avisos.
   * 
   * @param documento
   * @param receptores
   * @param usuarioActual
   */
  private void registrarAvisosFirma(DocumentoDTO documento, List<String> receptores,
      String usuarioActual) {
    if (logger.isDebugEnabled()) {
      logger.debug("registrarAvisosFirma(DocumentoDTO, List<String>, String) - start"); //$NON-NLS-1$
    }

    this.avisoService.guardarAvisos(receptores, documento, usuarioActual);

    if (logger.isDebugEnabled()) {
      logger.debug("registrarAvisosFirma(DocumentoDTO, List<String>, String) - end"); //$NON-NLS-1$
    }
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

    if (!this.ecosistemaService.obtenerEcosistema().trim().isEmpty()) {
      String returnString = (tipoDoc.getAcronimo() + "-" + numeroEspecial.getAnio() + "-" + numeroEspecial.getNumero() + "-" + this.ecosistemaService.obtenerEcosistema() + "-" + numeroEspecial.getCodigoReparticion()).trim();
      if (logger.isDebugEnabled()) {
        logger.debug("generarNumeroEspecial(TipoDocumentoDTO, NumeracionEspecialDTO) - end"); //$NON-NLS-1$
      }
      return returnString;
    } else {
      String returnString = (tipoDoc.getAcronimo() + "-" + numeroEspecial.getAnio() + "-" + numeroEspecial.getNumero() + "-" + numeroEspecial.getCodigoReparticion()).trim();
      if (logger.isDebugEnabled()) {
        logger.debug("generarNumeroEspecial(TipoDocumentoDTO, NumeracionEspecialDTO) - end"); //$NON-NLS-1$
      }
      return returnString;
    }
  }

  @Override
  public DocumentoDTO guardarInformacionDocumento(RequestGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarInformacionDocumento(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    } 
    
    DocumentoDTO documento = new DocumentoDTO();
    documento.setMotivo(request.getMotivo());
    documento.setFechaModificacion(documento.getFechaCreacion());
    documento.setTipo(request.getTipoDocumentoGedo());
    documento.setUsuarioGenerador(request.getUsuario());
    documento.setNumero(request.getNumero());
    documento.setAnio(UtilsDate.obtenerAnioActual());
    documento.setReparticion(request.getCodigoReparticion());
    documento.setReparticionActual(documento.getReparticion());
    documento.setListaMetadatos(request.getDocumentoMetadata());
    documento.setWorkflowOrigen(request.getWorkflowId());
    documento.setNumeroSadePapel(request.getNumeroSadePapel());
    documento.setUsuarioIniciador(request.getUsuarioIniciador());
    documento.setPeso(request.getData().length);
    documento.setReparticionesAcumuladas(
        crearReparticionesAcumuladas(request.getCodigoReparticion(), request.getUsuario()));
    documento.setTipoReserva(crearTipoReserva());
    String apoderador = null;
    if (request.getWorkflowId() != null) {
      apoderador = (String) processEngine.getExecutionService()
          .getVariable(request.getWorkflowId(), Constantes.VAR_USUARIO_APODERADOR);
//			String idPublicable = (String) processEngine.getExecutionService().getVariable(request.getWorkflowId(),
//					Constantes.VAR_DOCUMENTO_PUBLICABLE_ID);
//			documento.setIdPublicable(idPublicable);
	}
	documento.setIdPublicable(this.documentoPublicableService.getIdPublicableByNombreArchivoTemporal(request.getNombreArchivo()));

    if (apoderador != null || request.getUsuarioApoderador() != null) {
      documento.setApoderado(request.getUsuarioFirmante());
    } else {
      documento.setApoderado(null);
    }

    if (request.getNumeroEspecial() != null) {
      // VOLVER ATRAS DEJANDO SOLO A DOCUMENTO
      String numeroEspecial = generarNumeroEspecial(request.getTipoDocumentoGedo(),
          request.getNumeroEspecial());
      documento.setNumeroEspecial(numeroEspecial);
      request.setNumeroEspecialCompleto(numeroEspecial);
    } else {
      documento.setNumeroEspecial(null);
    }
    if (request.getNombreAplicacion() != null)
      documento.setSistemaOrigen(request.getNombreAplicacion());
    if (request.getNombreAplicacionIniciadora() != null)
      documento.setSistemaIniciador(request.getNombreAplicacionIniciadora());

    if (request.getTipoDocumentoGedo() != null
        && request.getTipoDocumentoGedo().getEsConfidencial()
        && request.getListaUsuariosReservados() != null
        &&!request.getListaUsuariosReservados().isEmpty()) {
      documento.setUsuariosReservados(crearUsuariosReservados(request));
    }
    request.setDocumento(documento);
    Documento documenEntity = this.mapper.map(documento, Documento.class);
   
    Documento documenoSav = documentoRepo.save(documenEntity);
    
    DocumentoDTO documentoOut = this.mapper.map(documenoSav, DocumentoDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("guardarInformacionDocumento(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return documentoOut;
  }

  private Set<UsuarioReservadoDTO> crearUsuariosReservados(RequestGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("crearUsuariosReservados(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    Set<UsuarioReservadoDTO> usuariosReservados = new HashSet<>();
    for (String datoUsuario : request.getListaUsuariosReservados()) {

      UsuarioReservadoDTO usuarioReservado = new UsuarioReservadoDTO();
      usuarioReservado.setUserName(datoUsuario);
      usuariosReservados.add(usuarioReservado);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("crearUsuariosReservados(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return usuariosReservados;
  }

  /**
   * Busca la reserva 1: "NO RESERVADO / RESERVADO POR TIPO" para dar de alta el
   * documento.
   */
  private TipoReservaDTO crearTipoReserva() {
    if (logger.isDebugEnabled()) {
      logger.debug("crearTipoReserva() - start"); //$NON-NLS-1$
    }

    TipoReservaDTO tipoReserva = null;
    try {
      tipoReserva = this.mapper.map(tipoReservaRepo.findById(1), TipoReservaDTO.class);
    } catch (Exception e) {
      logger.error("Mensaje de error", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("crearTipoReserva() - end"); //$NON-NLS-1$
    }
    return tipoReserva;
  }

  /**
   * Creacion de la ReparticionAcumulada que se agregara a un documento
   * determinado.
   * 
   * @param reparticion
   * @param documento
   * @param usuario
   * @return
   */
  private Set<ReparticionAcumuladaDTO> crearReparticionesAcumuladas(String reparticion,
      String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("crearReparticionesAcumuladas(String, String) - start"); //$NON-NLS-1$
    }

    Set<ReparticionAcumuladaDTO> reparticionesAcumuladas = new HashSet<>();
    ReparticionAcumuladaDTO reparticionAcumulada = new ReparticionAcumuladaDTO();
    reparticionAcumulada.setFechaModificacion(new Date());
    reparticionAcumulada.setReparticion(reparticion);
    reparticionAcumulada.setTipoOperacion("ALTA");
    reparticionAcumulada.setUserName(usuario);

    reparticionesAcumuladas.add(reparticionAcumulada);

    if (logger.isDebugEnabled()) {
      logger.debug("crearReparticionesAcumuladas(String, String) - end"); //$NON-NLS-1$
    }
    return reparticionesAcumuladas;
  }

  @Override
  public void copiarArchivosDeTrabajoTemporales(RequestGenerarDocumento request) throws ApplicationException {
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

  @Override
  public void borrarArchivosDeTrabajoTemporales(String workFlowId) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("borrarArchivosDeTrabajoTemporales(String) - start"); //$NON-NLS-1$
    }

    List<ArchivoDeTrabajoDTO> archivosDeTrabajo;
    archivosDeTrabajo = this.archivoDeTrabajoService.buscarArchivosDeTrabajoPorProceso(workFlowId);
    if (!org.springframework.util.CollectionUtils.isEmpty(archivosDeTrabajo)) {
      for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivosDeTrabajo) {
        archivoDeTrabajoService.borrarArchivoDeTrabajoTemporal(archivoDeTrabajo.getPathRelativo(),
            archivoDeTrabajo.getNombreArchivo());
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("borrarArchivosDeTrabajoTemporales(String) - end"); //$NON-NLS-1$
    }
  }
}
