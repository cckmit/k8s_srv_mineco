package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.NotImplementedException;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;
import org.zkoss.util.resource.Labels;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.model.model.RequestExternalBuscarDocumentos;
import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.RequestExternalGenerarTarea;
import com.egoveris.deo.model.model.ResponseExternalBuscarDocumentos;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarTarea;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ErrorConsultaNumeroSadeException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalActualizarVisualizacionService;
import com.egoveris.deo.ws.service.IExternalBuscarDocumentoService;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoNombramientoService;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.deo.ws.service.IExternalGenerarTareaService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.Documento;
import com.egoveris.te.base.model.DocumentoArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoGedo;
//import com.egoveris.te.base.model.DocumentoGedo;
import com.egoveris.te.base.model.DocumentoGedoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.SolicitudSubs;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.repository.ArchivoDeTrabajoRepository;
import com.egoveris.te.base.repository.DocumentoGedoRepository;
//import com.egoveris.te.base.repository.DocumentoGedoRepository;
import com.egoveris.te.base.repository.DocumentoRepository;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.DocumentoGedoTemplate;

@Service
@Transactional
public class DocumentoGedoServiceImpl implements DocumentoGedoService {

  @Autowired
  private AppProperty dBProperty;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;

  @Autowired
  private ArchivoDeTrabajoRepository archivoTrabajoRepo;
  @Autowired
  private static String SUBSANACION = "SUBSANACION";

  private final static Logger logger = LoggerFactory.getLogger(DocumentoGedoServiceImpl.class);

  @Autowired
  @Qualifier("consultaDocumento3Service")
  private IExternalConsultaDocumentoService consultaDocumentoService;
  @Autowired
  DocumentoRepository documentoRepository;
  @Autowired
  DocumentoGedoRepository documentoGedoRepository;
  @Autowired
  private IExternalGenerarDocumentoNombramientoService generarDocumentoService;
  @Autowired
  private IExternalGenerarTareaService generarTareaService;
  @Autowired
  private IExternalBuscarDocumentoService buscarDocumentoService;
  @Autowired
  private IExternalActualizarVisualizacionService externalActualizarVisualizacionService; 
  @Autowired
  private ExpedienteElectronicoService expedienteServive;
  private DozerBeanMapper mapper = new DozerBeanMapper();
  @Autowired
  private IExternalGenerarDocumentoService externalGenerarDocumentoService;
  private static String OBTENER_ARCHIVO_DE_TRABAJO = "select nombre_archivo, pathRelativo from gedo_documento INNER JOIN gedo_archivo_de_trabajo ON workfloworigen = idTask where numero = (?) and workflowOrigen is not null";

  @Autowired
  @Qualifier("GEDOurl")
  private String GEDOurl;

  /**
   * Consulta a la base de GEDO por un documento asociado a un determinado
   * numero especial. Si lo encuentra devuelve sus datos y sino retorna null.
   * 
   */
  public DocumentoDTO obtenerDocumentoPorNumeroEspecial(String numeroEspecial) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentoPorNumeroEspecial(numeroEspecial={}) - start", numeroEspecial);
    }

    Documento documento = documentoRepository.findByNumeroEspecial(numeroEspecial);
    DocumentoDTO docDTO = mapper.map(documento, DocumentoDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentoPorNumeroEspecial(String) - end - return value={}", docDTO);
    }
    return docDTO;
  }

  /**
   * Consulta a la base de GEDO por un documento asociado a un determinado
   * numero estandar. Si lo encuentra devuelve sus datos y sino retorna null.
   * 
   */
  public DocumentoDTO obtenerDocumentoPorNumeroEstandar(String numeroEstandar) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentoPorNumeroEstandar(numeroEstandar={}) - start", numeroEstandar);
    }
    DocumentoDTO retorno = null;

    DocumentoGedo resultado = documentoGedoRepository.findByNumero(numeroEstandar);
    if (resultado != null) {
      retorno = new DocumentoDTO();
      retorno.setNumeroSade(resultado.getNumero());
      retorno.setNumeroEspecial(resultado.getNumeroEspecial());
      retorno.setTipoDocAcronimo(resultado.getTipo().getAcronimo());
      retorno.setMotivo(resultado.getMotivo());
      retorno.setNombreUsuarioGenerador(resultado.getUsuarioGenerador());
      retorno.setFechaCreacion(resultado.getFechaCreacion());
      retorno.setTipoDocGedo(resultado.getTipo().getId());
      retorno.setMotivoDepuracion(resultado.getMotivoDepuracion());
      retorno.setEntidad(resultado.getReparticion());
      retorno.setNombreArchivo(resultado.getNumero() + ".pdf");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentoPorNumeroEstandar(String) - end - return value={}", retorno);
    }

    return retorno;
  }

  public DocumentoDTO generarDocumentoGEDO(ExpedienteElectronicoDTO expedienteElectronico,
      String motivoExpediente, String aclaracion, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoGEDO(expedienteElectronico={}, motivoExpediente={}, aclaracion={}, username={}) - start",
          expedienteElectronico, motivoExpediente, aclaracion, username);
    }

    try {
      RequestExternalGenerarDocumento request = this.armarRequestParaGenerarDocumentoEnGedo(
          motivoExpediente, aclaracion, expedienteElectronico, username);
      ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);
      // setear el resultado al expediente y hacer rollback si no se puede
      DocumentoDTO documento = new DocumentoDTO();
      documento.setFechaCreacion(new Date());
      documento.setFechaAsociacion(new Date());
      documento.setNombreUsuarioGenerador(username);
      documento.setDefinitivo(true);
      documento.setTipoDocAcronimo(this.dBProperty.getString("acronimoPaseEE"));
      documento.setNombreArchivo(response.getNumero() + ".pdf");
      documento.setNumeroSade(response.getNumero());
      documento.setMotivo(ConstantesWeb.MOTIVO_PASE);
      documento.setTipoDocGenerado(ConstantesWeb.TIPO_DOCUMENTO_PASE);

      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarDocumentoGEDO(ExpedienteElectronico, String, String, String) - end - return value={}",
            documento);
      }
      return documento;
    } catch (RuntimeException rte) {
      throw rte;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.te.core.api.ee.services.DocumentoGedoService#
   * generarDocumentoGEDOPase(com.egoveris.te.core.api.expedientes.dominio.
   * ExpedienteElectronico, java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public DocumentoDTO generarDocumentoGEDOPase(ExpedienteElectronicoDTO expedienteElectronico,
      String motivoExpediente, String username, String acronimoPase, String referenciaPase,
      Map<String, String> camposTemplate) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoGEDOPase(expedienteElectronico={}, motivoExpediente={}, username={}, acronimoPase={}, referenciaPase={}, camposTemplate={}) - start",
          expedienteElectronico, motivoExpediente, username, acronimoPase, referenciaPase,
          camposTemplate);
    }

    try {
      RequestExternalGenerarDocumento request = this.armarRequestParaGenerarDocumentoEnGedo(
          motivoExpediente, null, expedienteElectronico, username);
      request.setAcronimoTipoDocumento(acronimoPase);
      request.setReferencia(referenciaPase);
      if (camposTemplate != null) {
        request.setCamposTemplate(camposTemplate);
      }

      ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);
      // setear el resultado al expediente y hacer rollback si no se puede
      DocumentoDTO documento = new DocumentoDTO();
      documento.setFechaCreacion(new Date());
      documento.setFechaAsociacion(new Date());
      documento.setNombreUsuarioGenerador(username);
      documento.setDefinitivo(true);
      documento.setTipoDocAcronimo(acronimoPase);
      documento.setNombreArchivo(response.getNumero() + ".pdf");
      documento.setNumeroSade(response.getNumero());
      documento.setMotivo("Informe de control");
      documento.setTipoDocGenerado(ConstantesWeb.TIPO_DOCUMENTO_SUBSANACION);

      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarDocumentoGEDOPase(ExpedienteElectronico, String, String, String, String, Map<String,String>) - end - return value={}",
            documento);
      }
      return documento;
    } catch (RuntimeException rte) {
      throw rte;
    }
  }

  public DocumentoDTO generarDocumentoSubsanacion(SolicitudSubs solicitudSubs) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoSubsanacion(solicitudSubs={}) - start", solicitudSubs);
    }

    String ref = solicitudSubs.getTipo() + " sobre expediente " + solicitudSubs.getNroExpediente();

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    request.setData(DocumentoGedoTemplate.generarCuerpoDocSubsanacion(solicitudSubs));
    request.setReferencia(ref);
    request.setAcronimoTipoDocumento(
        this.tipoDocumentoService.buscarTipoDocumentoByUso(SUBSANACION).getAcronimo());
    request.setUsuario(solicitudSubs.getUsuarioAlta()); // USUARIO LOGUEADO
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);
    DocumentoDTO documento = new DocumentoDTO();
    Date date = new Date();
    documento.setFechaCreacion(date);
    documento.setFechaAsociacion(date);
    documento.setNombreUsuarioGenerador(solicitudSubs.getUsuarioAlta());
    documento.setUsuarioAsociador(solicitudSubs.getUsuarioAlta());
    documento.setDefinitivo(true);
    documento.setNombreArchivo(response.getNumero() + ".pdf");
    documento.setNumeroSade(response.getNumero());
    documento.setMotivo(ref);
    documento.setTipoDocGenerado(ConstantesWeb.TIPO_DOCUMENTO_SUBSANACION); // "2"
    documento.setTipoDocAcronimo(request.getAcronimoTipoDocumento());

    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoSubsanacion(SolicitudSubs) - end - return value={}",
          documento);
    }
    return documento;
  }

  private RequestExternalGenerarDocumento armarRequestParaGenerarDocumentoEnGedo(
      String motivoExpediente, String aclaracion, ExpedienteElectronicoDTO expedienteElectronico,
      String username) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoEnGedo(motivoExpediente={}, aclaracion={}, expedienteElectronico={}, username={}) - start",
          motivoExpediente, aclaracion, expedienteElectronico, username);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    DocumentoDTO documento = new DocumentoDTO();
    documento.setDefinitivo(true);
    documento.setMotivo(motivoExpediente);

    if (aclaracion != null) {
      StringBuilder aclaracionFormat = new StringBuilder();
      aclaracionFormat.append(aclaracion);
      documento.setAclaracion(aclaracionFormat.toString());
    }

    DocumentoGedoTemplate template = new DocumentoGedoTemplate();
    request.setData(template.generarDocumentoComoByteArray(documento));
    request.setReferencia("Pase electrónico de " + expedienteElectronico.getCodigoCaratula());
    request.setAcronimoTipoDocumento(this.dBProperty.getString("acronimoPaseEE"));
    request.setUsuario(username);
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoEnGedo(String, String, ExpedienteElectronico, String) - end - return value={}",
          request);
    }
    return request;

  }

  private RequestExternalGenerarDocumento armarRequestParaGenerarDocumentoEnGedoConComunicacion(
      String motivoExpediente, ExpedienteElectronicoDTO expedienteElectronico, String username,
      String pieDePaginaDeDestinatarios) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoEnGedoConComunicacion(motivoExpediente={}, expedienteElectronico={}, username={}, pieDePaginaDeDestinatarios={}) - start",
          motivoExpediente, expedienteElectronico, username, pieDePaginaDeDestinatarios);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    DocumentoDTO documento = new DocumentoDTO();
    documento.setDefinitivo(true);

    DocumentoGedoTemplate template = new DocumentoGedoTemplate();
    request.setData(template.generarCuerpoComunicacionOficial(documento, motivoExpediente,
        pieDePaginaDeDestinatarios));
    request.setReferencia("Pase electrónico de " + expedienteElectronico.getCodigoCaratula());
    request.setAcronimoTipoDocumento(this.dBProperty.getString("acronimoPaseEE"));
    request.setUsuario(username);
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoEnGedoConComunicacion(String, ExpedienteElectronico, String, String) - end - return value={}",
          request);
    }
    return request;

  }

  /**
   * Genera un documento en GEDO con la caratula del expediente.
   * 
   * @param request
   */
  private ResponseExternalGenerarDocumento generarDocumentoEnGedo(
      RequestExternalGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoEnGedo(request={}) - start", request);
    }

    ResponseExternalGenerarDocumento response = null;
    try {
      response = this.generarDocumentoService.generarDocumentoGEDO(request);

    } catch (RemoteAccessException e) {
      String error = Labels.getLabel("ee.servicio.gedo.errorComunicacionGEDO");
      logger.error(error, e);
      throw e;
    } catch (ErrorGeneracionDocumentoException e) {
      String error = Labels.getLabel("ee.servicio.gedo.errorComunicacionTSA");
      logger.error(error, e);
      throw new RemoteAccessException(error);
    } catch (Exception e) {
      logger.error(
          "Se produjo un error al generar el documento" + "Referencia: " + request.getReferencia(),
          e);
      throw new TeRuntimeException("Se produjo un error al generar el documento", e);
    }

    if (response == null) {
      logger.error("Respuesta nula de Gedo para generar el documento a: " + request.getUsuario()
          + " con motivo: " + request.getReferencia());
      throw new TeRuntimeException("Se produjo un error al generar el documento", null);
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarDocumentoEnGedo(RequestExternalGenerarDocumento) - end - return value={}",
            response);
      }
      return response;
    }
  }

  /**
   * Genera documento de Vinculación o Desvinculación en Tramitación Conjunta.
   */
  public DocumentoDTO generarDocumentoDeTramitacionConjunta(
      ExpedienteElectronicoDTO expedienteElectronico, String motivo, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeTramitacionConjunta(expedienteElectronico={}, motivo={}, username={}) - start",
          expedienteElectronico, motivo, username);
    }

    try {
      RequestExternalGenerarDocumento request = this
          .armarRequestParaGenerarDocumentoTramitacionConjuntaEnGedo(motivo, expedienteElectronico,
              username);
      ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);
      // setear el resultado al expediente y hacer rollback si no se puede
      DocumentoDTO documento = new DocumentoDTO();
      documento.setFechaCreacion(new Date());
      documento.setFechaAsociacion(new Date());
      documento.setNombreUsuarioGenerador(username);
      documento.setTipoDocAcronimo(request.getAcronimoTipoDocumento());
      documento.setDefinitivo(true);
      documento.setNombreArchivo(response.getNumero() + ".pdf");
      documento.setNumeroSade(response.getNumero());
      documento.setMotivo(motivo);

      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarDocumentoDeTramitacionConjunta(ExpedienteElectronico, String, String) - end - return value={}",
            documento);
      }
      return documento;
    } catch (RuntimeException rte) {
      try {
      } catch (Exception e) {
        logger.error("No se pudo generar el documento: ", e);
      }
      throw rte;
    }
  }

  /**
   * Genera documento de Vinculación en Fusion.
   */
  public DocumentoDTO generarDocumentoDeFusion(ExpedienteElectronicoDTO expedienteElectronico,
      String motivo, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeFusion(expedienteElectronico={}, motivo={}, username={}) - start",
          expedienteElectronico, motivo, username);
    }

    try {
      RequestExternalGenerarDocumento request = this
          .armarRequestParaGenerarDocumentoFusionEnGedo(motivo, expedienteElectronico, username);
      ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);
      // setear el resultado al expediente y hacer rollback si no se puede
      DocumentoDTO documento = new DocumentoDTO();
      documento.setFechaCreacion(new Date());
      documento.setFechaAsociacion(new Date());
      documento.setNombreUsuarioGenerador(username);
      documento.setTipoDocAcronimo(request.getAcronimoTipoDocumento());
      documento.setDefinitivo(true);
      documento.setNombreArchivo(response.getNumero() + ".pdf");
      documento.setNumeroSade(response.getNumero());
      if (expedienteElectronico.getEsCabeceraTC() != null
          && expedienteElectronico.getEsCabeceraTC()) {
        documento.setMotivo(ConstantesWeb.MOTIVO_VINCULACION_FUSION);
      }

      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarDocumentoDeFusion(ExpedienteElectronico, String, String) - end - return value={}",
            documento);
      }
      return documento;
    } catch (RuntimeException rte) {
      try {
      } catch (Exception e) {
        logger.error("No se pudo generar el documento: ", e);
      }
      throw rte;
    }
  }

  private RequestExternalGenerarDocumento armarRequestParaGenerarDocumentoTramitacionConjuntaEnGedo(
      String motivo, ExpedienteElectronicoDTO expedienteElectronico, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoTramitacionConjuntaEnGedo(motivo={}, expedienteElectronico={}, username={}) - start",
          motivo, expedienteElectronico, username);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    DocumentoDTO documento = new DocumentoDTO();
    documento.setDefinitivo(true);
    documento.setMotivo(motivo);

    DocumentoGedoTemplate template = new DocumentoGedoTemplate();
    request.setData(template.generarDocumentoComoByteArray(documento));
    request.setReferencia("Carátula Tram.Conj. de " + expedienteElectronico.getCodigoCaratula());
    request.setAcronimoTipoDocumento(this.dBProperty.getString("acronimoPaseEE"));
    request.setUsuario(username);
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoTramitacionConjuntaEnGedo(String, ExpedienteElectronico, String) - end - return value={}",
          request);
    }
    return request;

  }

  private RequestExternalGenerarDocumento armarRequestParaGenerarDocumentoFusionEnGedo(
      String motivo, ExpedienteElectronicoDTO expedienteElectronico, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoFusionEnGedo(motivo={}, expedienteElectronico={}, username={}) - start",
          motivo, expedienteElectronico, username);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    DocumentoDTO documento = new DocumentoDTO();
    documento.setDefinitivo(true);
    documento.setMotivo(motivo);

    DocumentoGedoTemplate template = new DocumentoGedoTemplate();
    request.setData(template.generarDocumentoComoByteArray(documento));
    request.setReferencia("Carátula Fusión de " + expedienteElectronico.getCodigoCaratula());
    request.setAcronimoTipoDocumento(this.dBProperty.getString("acronimoPaseEE"));
    request.setUsuario(username);
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoFusionEnGedo(String, ExpedienteElectronico, String) - end - return value={}",
          request);
    }
    return request;

  }

  public List<DocumentoArchivoDeTrabajoDTO> obtenerArchivosDeTrabajo(DocumentoDTO documento) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerArchivosDeTrabajo(documento={}) - start", documento);
    }
    throw new NotImplementedException("Pendiente refactorizar");
    // Connection c = null;
    // ResultSet rs = null;
    // PreparedStatement ps = null;
    // int i = 1;
    // try {
    // c = datasource.getConnection();
    // ps = c.prepareStatement(OBTENER_ARCHIVO_DE_TRABAJO);
    // List<DocumentoArchivoDeTrabajoDTO> archTrabajo = new
    // ArrayList<DocumentoArchivoDeTrabajoDTO>();
    // DocumentoArchivoDeTrabajoDTO archivoNuevo = null;
    // ps.setString(1, documento.getNumeroSade());
    // rs = ps.executeQuery();
    // while (rs.next()) {
    // archivoNuevo = new DocumentoArchivoDeTrabajoDTO();
    // archivoNuevo.setOrden(i++);
    // archivoNuevo.setArchivo(rs.getString("nombre_archivo"));
    // archivoNuevo.setPathRelativo(rs.getString("pathRelativo"));
    // archTrabajo.add(archivoNuevo);
    // }
    //
    // if (logger.isDebugEnabled()) {
    // logger.debug("obtenerArchivosDeTrabajo(Documento) - end - return
    // value={}", archTrabajo);
    // }
    // return archTrabajo;
    // } catch (SQLException sqle) {
    // logger.error("Error al obtener datos de GEDO", sqle);
    // throw new TeRuntimeException("Error al obtener datos de GEDO", sqle);
    // } finally {
    // // cerrarResultSet(rs);
    // this.cerrarPreparedStatement(ps);
    // this.cerrarConexionABase(c);
    // }

  }

  public DocumentoDTO generarDocumentoQuitarReserva(ExpedienteElectronicoDTO expedienteElectronico,
      String motivo, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoQuitarReserva(expedienteElectronico={}, motivo={}, username={}) - start",
          expedienteElectronico, motivo, username);
    }

    try {
      RequestExternalGenerarDocumento request = this
          .armarRequestParaGenerarDocumentoQuitarReserva(motivo, expedienteElectronico, username);
      ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);
      DocumentoDTO documento = new DocumentoDTO();
      documento.setTipoDocAcronimo(request.getAcronimoTipoDocumento());
      documento.setFechaCreacion(new Date());
      documento.setFechaAsociacion(new Date());
      documento.setNombreUsuarioGenerador(username);
      documento.setDefinitivo(true);
      documento.setNombreArchivo(response.getNumero() + ".pdf");
      documento.setNumeroSade(response.getNumero());
      documento.setMotivo(motivo);

      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarDocumentoQuitarReserva(ExpedienteElectronico, String, String) - end - return value={}",
            documento);
      }
      return documento;
    } catch (RuntimeException e) {
      logger.error("No se pudo generar el documento: ", e);
      throw e;
    }
  }

  private RequestExternalGenerarDocumento armarRequestParaGenerarDocumentoQuitarReserva(
      String motivo, ExpedienteElectronicoDTO expedienteElectronico, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoQuitarReserva(motivo={}, expedienteElectronico={}, username={}) - start",
          motivo, expedienteElectronico, username);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    DocumentoDTO documento = new DocumentoDTO();
    documento.setDefinitivo(true);
    documento.setMotivo(motivo);

    DocumentoGedoTemplate template = new DocumentoGedoTemplate();
    request.setData(template.generarDocumentoComoByteArray(documento));
    request
        .setReferencia("Documento Quitar Reserva de " + expedienteElectronico.getCodigoCaratula());
    request.setAcronimoTipoDocumento(this.dBProperty.getString("acronimoPaseEE"));
    request.setUsuario(username);
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoQuitarReserva(String, ExpedienteElectronico, String) - end - return value={}",
          request);
    }
    return request;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  /**
   * Genera un tarea en GEDO.
   * 
   * @param request
   */
  private ResponseExternalGenerarTarea generarTareaEnGedo(RequestExternalGenerarTarea request) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarTareaEnGedo(request={}) - start", request);
    }

    ResponseExternalGenerarTarea response = null;
    try {
      response = this.generarTareaService.generarTareaGEDO(request);

    } catch (Exception e) {
      logger.error("Se produjo un error al generar el documento", e);
      throw new TeRuntimeException("Se produjo un error al generar el documento", e);
    }

    if (response == null) {
      logger.error("Respuesta nula de Gedo para generar el documento a: "
          + request.getUsuarioEmisor() + " con motivo: " + request.getReferencia());
      throw new TeRuntimeException("Se produjo un error al generar el documento", null);
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug("generarTareaEnGedo(RequestExternalGenerarTarea) - end - return value={}",
            response);
      }
      return response;
    }
  }

  private RequestExternalGenerarTarea armarRequestParaGenerarDocumentEnGedo(String motivo,
      ExpedienteElectronicoDTO expedienteElectronico, String usuarioReceptor, Usuario username,
      TipoDocumentoDTO tipoDocumento) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentEnGedo(motivo={}, expedienteElectronico={}, usuarioReceptor={}, username={}, tipoDocumento={}) - start",
          motivo, expedienteElectronico, usuarioReceptor, username, tipoDocumento);
    }

    RequestExternalGenerarTarea request = new RequestExternalGenerarTarea();

    request.setMensaje(motivo);
    request.setSuscribirseAlDocumento(true);
    request.setTarea(ConstantesWeb.CONFECCION_DOCUMENTO);
    request.setReferencia("Creacion de documento, peticion desde Expediente Electrónico "
        + expedienteElectronico.getCodigoCaratula());
    request.setAcronimoTipoDocumento(tipoDocumento.getAcronimo());
    request.setUsuarioReceptor(username.getUsername());
    request.setUsuarioEmisor(usuarioReceptor);
    request.setSistemaIniciador(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentEnGedo(String, ExpedienteElectronico, String, Usuario, TipoDocumento) - end - return value={}",
          request);
    }
    return request;

  }

  @Override
  public DocumentoDTO generarPeticionGeneracionDocumentoGEDO(
      ExpedienteElectronicoDTO expedienteElectronico, String userProductor, Usuario userReceptor,
      String motivo, TipoDocumentoDTO tipoDoc) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPeticionGeneracionDocumentoGEDO(expedienteElectronico={}, userProductor={}, userReceptor={}, motivo={}, tipoDoc={}) - start",
					expedienteElectronico, userProductor, userReceptor, motivo, tipoDoc);
		}

		try {
			RequestExternalGenerarTarea request = this.armarRequestParaGenerarDocumentEnGedo(motivo,
					expedienteElectronico, userProductor, userReceptor, tipoDoc);
			ResponseExternalGenerarTarea response = this.generarTareaEnGedo(request);

			// setear el resultado al expediente y hacer rollback si no se puede
			Documento documento = new Documento();

			documento.setFechaCreacion(new Date());
			documento.setFechaAsociacion(new Date());
			documento.setNombreUsuarioGenerador(response.getUsuarioApoderado());
			documento.setTipoDocAcronimo(request.getAcronimoTipoDocumento());
			documento.setDefinitivo(false);
			documento.setNumeroSade(response.getProcessId());
			documento.setNombreArchivo(response.getUsuarioApoderado());

			DocumentoDTO out = new DocumentoDTO();
			// validation
			if (null != documento) {
				// set salida.
				out = mapper.map(documento, DocumentoDTO.class);
			}

			if (expedienteElectronico.getEsCabeceraTC() != null && expedienteElectronico.getEsCabeceraTC()) {
				documento.setMotivo("Creacion de Documento");
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
						"generarPeticionGeneracionDocumentoGEDO(ExpedienteElectronico, String, Usuario, String, TipoDocumento) - end - return value={}",
						documento);
			}
			return out;
    } catch (RuntimeException rte) {
      logger.error("No se pudo generar el documento: ", rte);
      throw rte;
    }
  }

  public ResponseExternalConsultaDocumento consultarDocumentoPorNumero(String nroDocumento,
      String usuarioConsulta) throws ParametroInvalidoConsultaException,
      DocumentoNoExisteException, SinPrivilegiosException, ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoPorNumero(nroDocumento={}, usuarioConsulta={}) - start",
          nroDocumento, usuarioConsulta);
    }

    RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
    request.setNumeroDocumento(nroDocumento);
    request.setUsuarioConsulta(usuarioConsulta);

    ResponseExternalConsultaDocumento returnResponseExternalConsultaDocumento = consultaDocumentoService
        .consultarDocumentoPorNumero(request);
    if (logger.isDebugEnabled()) {
      logger.debug("consultarDocumentoPorNumero(String, String) - end - return value={}",
          returnResponseExternalConsultaDocumento);
    }
    ResponseExternalConsultaDocumento salida = mapper.map(returnResponseExternalConsultaDocumento,
        ResponseExternalConsultaDocumento.class);

    return salida;
  }

  @Override
  public DocumentoDTO generarDocumentoGEDOConComunicacion(
      ExpedienteElectronicoDTO expedienteElectronico, String username, String motivoExpediente,
      String pieDePaginaDeDestinatarios) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoGEDOConComunicacion(expedienteElectronico={}, username={}, motivoExpediente={}, pieDePaginaDeDestinatarios={}) - start",
          expedienteElectronico, username, motivoExpediente, pieDePaginaDeDestinatarios);
    }

    try {
      RequestExternalGenerarDocumento request = this
          .armarRequestParaGenerarDocumentoEnGedoConComunicacion(motivoExpediente,
              expedienteElectronico, username, pieDePaginaDeDestinatarios);
      ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);
      // setear el resultado al expediente y hacer rollback si no se puede
      DocumentoDTO documento = new DocumentoDTO();
      documento.setFechaCreacion(new Date());
      documento.setFechaAsociacion(new Date());
      documento.setNombreUsuarioGenerador(username);
      documento.setDefinitivo(true);
      documento.setTipoDocAcronimo(this.dBProperty.getString("acronimoPaseEE"));
      documento.setNombreArchivo(response.getNumero() + ".pdf");
      documento.setNumeroSade(response.getNumero());
      documento.setMotivo(ConstantesWeb.MOTIVO_PASE);
      documento.setTipoDocGenerado(ConstantesWeb.TIPO_DOCUMENTO_PASE);

      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarDocumentoGEDOConComunicacion(ExpedienteElectronico, String, String, String) - end - return value={}",
            documento);
      }
      return documento;
    } catch (RuntimeException rte) {
      throw rte;
    }

  }

  public void setGEDOurl(String gEDOurl) {
    GEDOurl = gEDOurl;
  }

  public String getGEDOurl() {
    return GEDOurl;
  }

  public ResponseExternalBuscarDocumentos buscarDocumentoGEDOPorDatosPropios(
      RequestExternalBuscarDocumentos busquedaRequest) throws ErrorConsultaNumeroSadeException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDocumentoGEDOPorDatosPropios(busquedaRequest={}) - start",
          busquedaRequest);
    }

    ResponseExternalBuscarDocumentos returnResponseExternalBuscarDocumentos = this.buscarDocumentoService
        .buscarDocumentoEnGedo(busquedaRequest);
    if (logger.isDebugEnabled()) {
      logger.debug(
          "buscarDocumentoGEDOPorDatosPropios(RequestExternalBuscarDocumentos) - end - return value={}",
          returnResponseExternalBuscarDocumentos);
    }

    ResponseExternalBuscarDocumentos salida = mapper.map(returnResponseExternalBuscarDocumentos,
        ResponseExternalBuscarDocumentos.class);

    return salida;

  }

  public DocumentoDTO armarDocDeNotificacion(ExpedienteElectronicoDTO ee, String username,
      String referencia, String motivo) {
    if (logger.isDebugEnabled()) {
      logger.debug("armarDocDeNotificacion(ee={}, username={}, referencia={}, motivo={}) - start",
          ee, username, referencia, motivo);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();

    DocumentoDTO d = new DocumentoDTO();
    d.setMotivo(motivo);
    d.setDefinitivo(true);

    DocumentoGedoTemplate template = new DocumentoGedoTemplate();

    request.setData(template.generarDocumentoComoByteArray(d));
    request.setReferencia(referencia);
    request.setAcronimoTipoDocumento(this.dBProperty.getString("ee.acronimo.notificacion"));
    request.setUsuario(username);
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);

    DocumentoDTO documento = new DocumentoDTO();
    documento.setFechaCreacion(new Date());
    documento.setFechaAsociacion(new Date());
    documento.setNombreUsuarioGenerador(username);
    documento.setTipoDocAcronimo(request.getAcronimoTipoDocumento());
    documento.setDefinitivo(true);
    documento.setNombreArchivo(response.getNumero() + ".pdf");
    documento.setNumeroSade(response.getNumero());
    documento.setMotivo(motivo);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarDocDeNotificacion(ExpedienteElectronico, String, String, String) - end - return value={}",
          documento);
    }
    return documento;
  }

  public void asignarPermisosVisualizacionGEDO(ExpedienteElectronicoDTO expedienteElectronico,
      Usuario usuario, List<ReparticionParticipanteDTO> reparticionesRectora)
      throws RuntimeException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "asignarPermisosVisualizacionGEDO(expedienteElectronico={}, usuario={}, reparticionesRectora={}) - start",
          expedienteElectronico, usuario, reparticionesRectora);
    }

    List<String> listaDeDocumentos = new ArrayList<>();
    List<String> listaDeRectoras = new ArrayList<>();

    List<DocumentoDTO> listaDocumentos = expedienteElectronico.getDocumentos();

    if (listaDocumentos != null) {

      for (DocumentoDTO documento : listaDocumentos) {
        if (documento.getDefinitivo() && (expedienteElectronico.getEsReservado()
            && expedienteElectronico.getFechaReserva() == null
            || expedienteElectronico.getEsReservado() && !expedienteElectronico.getFechaReserva()
                .after(documento.getFechaAsociacion()))) {
          listaDeDocumentos.add(documento.getNumeroSade());
        }
      }
    }

    for (ReparticionParticipanteDTO reparticionRectora : reparticionesRectora) {

      listaDeRectoras.add(reparticionRectora.getReparticion());

    }

    try {
      externalActualizarVisualizacionService.actualizarEstadoVisualizacion(usuario.getUsername(),
          listaDeRectoras, listaDeDocumentos);
    } catch (Exception e) {

      logger.error(e.getMessage());
      throw new TeRuntimeException(e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "asignarPermisosVisualizacionGEDO(ExpedienteElectronico, Usuario, List<ReparticionParticipante>) - end");
    }
  }

  @Override
  public String getPathTareaExterna() {
    if (logger.isDebugEnabled()) {
      logger.debug("getPathTareaExterna() - start");
    }

    String returnString = this.getGEDOurl().concat(ConstantesWeb.PATH_TAREAS_WORKFLOW);
    if (logger.isDebugEnabled()) {
      logger.debug("getPathTareaExterna() - end - return value={}", returnString);
    }
    return returnString;
  }

  public void setdBProperty(AppProperty dBProperty) {
    this.dBProperty = dBProperty;
  }

  public AppProperty getdBProperty() {
    return dBProperty;
  }

  public DocumentoGedoDTO obtenerDocumentoGedoPorNumeroEstandar(String numeroSade) {

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentoGedoPorNumeroEstandar(numeroSade={}) - start", numeroSade);
    }

    // DocumentoGedo documenGedo =
    // documentoGedoRepository.findByNumero(numeroSade);

    DocumentoGedoDTO d = null;
    // mapper.map(documenGedo, DocumentoGedoDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentoGedoPorNumeroEstandar(String) - end - return value={}", d);
    }
    return d;
  }

}
