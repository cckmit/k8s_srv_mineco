package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.egoveris.commons.databaseconfiguration.exceptions.ParametroInvalidoException;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoNombramientoService;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.GenerarDocumentoException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoDeIdentidadDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoConSuspensionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoConSuspensionService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.INotificacionEEService;
import com.egoveris.te.base.service.iface.ITomaVistaExternalService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.DocumentoGedoTemplate;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.exception.TomaVistaException;
import com.egoveris.te.model.model.DocumentoTVDTO;
import com.egoveris.te.model.model.TomaVistaResponse;

@Service
@Transactional
public class TomaVistaExternalServiceImpl extends ExternalServiceAbstract
    implements ITomaVistaExternalService, ApplicationContextAware {

  private static final Logger logger = LoggerFactory.getLogger(TomaVistaExternalServiceImpl.class);

  public static final String ACRONIMO_PASE = "PV";
  public static final String comunicacionesOficialesServicePort = "/COServices/comunicacionesOficialesService";

  private static String motivoCaratulacionTomarVista = "Solicitud de toma de vista del expediente: ";

  private static ApplicationContext applicationContext = null;

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public static Object getService(final String serviceName) {
    if (logger.isDebugEnabled()) {
      logger.debug("getService(serviceName={}) - start", serviceName);
    }

    final Object returnObject = applicationContext.getBean(serviceName);
    if (logger.isDebugEnabled()) {
      logger.debug("getService(String) - end - return value={}", returnObject);
    }
    return returnObject;
  }

  @Autowired
  private String muleUrl;

  @Autowired
  private IExternalGenerarDocumentoService generarDocService;

  private SectorInternoBean sectorMesa;

  @Autowired
  private String acronimoTipoDocTomaVistaSinSuspension;
  @Autowired
  private String trataTomaVista;

  @Autowired
  private IExternalGenerarDocumentoNombramientoService generarDocumentoService;
  @Autowired
  private DocumentoService documentoService;
  @Autowired
  private DocumentoManagerService documentoManagerService;
  @Autowired
  private String tipoComunicacionOficialNota;
  @Autowired
  private PaseExpedienteService paseExpedienteService;
  private WorkFlowService workFlowService;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ExpedienteElectronicoConSuspensionService eeConSuspensionService;
  @Autowired
  private TrataService trataService;
  @Autowired
  private SolicitudExpedienteService solicitudExpedienteService;
  @Autowired
  private ReparticionServ reparticionServ;
  @Autowired
  private SectorInternoServ sectorServ;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  @Qualifier("usuariosSADEServiceImpl")
  private UsuariosSADEService usuariosSADEServiceImpl;
  private ExpedienteElectronicoFactory expedienteElectronicoFactory;
  @Autowired
  private INotificacionEEService notificacionService;

  private List<Usuario> listaUsuarios = null;

  @Autowired
  private String reparticionGT;
  
  private DozerBeanMapper mapper = new DozerBeanMapper();


  private RequestExternalGenerarDocumento armarRequestParaGenerarDocumentoEnGedo(
      final ExpedienteElectronicoDTO expedienteElectronico, final String usuarioTAD) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoEnGedo(expedienteElectronico={}, usuarioTAD={}) - start",
          expedienteElectronico, usuarioTAD);
    }

    final RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    final DocumentoDTO documento = new DocumentoDTO();
    documento.setDefinitivo(true);
    documento.setMotivo("Se ha generado la toma vista sin suspensión del expediente: "
        + expedienteElectronico.getCodigoCaratula());

    final DocumentoGedoTemplate template = new DocumentoGedoTemplate();
    request.setData(template.generarDocumentoComoByteArray(documento));
    request.setReferencia(
        "Toma de vista sin suspensión de: " + expedienteElectronico.getCodigoCaratula());
    request.setAcronimoTipoDocumento(acronimoTipoDocTomaVistaSinSuspension);
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);
    request.setUsuario(usuarioTAD);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarDocumentoEnGedo(ExpedienteElectronico, String) - end - return value={}",
          request);
    }
    return request;
  }

  private void asociarExpediente(final ExpedienteElectronicoDTO expedienteElectronico,
      final ExpedienteElectronicoDTO expedienteElectronicoTV) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "asociarExpediente(expedienteElectronico={}, expedienteElectronicoTV={}) - start",
          expedienteElectronico, expedienteElectronicoTV);
    }

    final ExpedienteAsociadoEntDTO expedienteAsociado = new ExpedienteAsociadoEntDTO();

    expedienteAsociado.setTipoDocumento(expedienteElectronicoTV.getTipoDocumento());
    expedienteAsociado.setAnio(expedienteElectronicoTV.getAnio());
    expedienteAsociado.setNumero(expedienteElectronicoTV.getNumero());
    expedienteAsociado.setSecuencia(expedienteElectronicoTV.getSecuencia());
    expedienteAsociado.setDefinitivo(Boolean.TRUE);
    expedienteAsociado
        .setCodigoReparticionActuacion(expedienteElectronicoTV.getCodigoReparticionActuacion());
    expedienteAsociado
        .setCodigoReparticionUsuario(expedienteElectronicoTV.getCodigoReparticionUsuario());
    expedienteAsociado.setEsElectronico(expedienteElectronicoTV.getEsElectronico());
    expedienteAsociado.setTrata(expedienteElectronicoTV.getTrata().getCodigoTrata());
    expedienteAsociado.setIdCodigoCaratula(expedienteElectronicoTV.getId());
    expedienteAsociado.setFechaAsociacion(new Date());
    expedienteAsociado.setUsuarioAsociador(expedienteElectronicoTV.getUsuarioCreador());
    expedienteAsociado.setEsExpedienteAsociadoFusion(expedienteElectronicoTV.getEsReservado());
    expedienteAsociado.setEsExpedienteAsociadoTC(expedienteElectronicoTV.getEsCabeceraTC());

    expedienteElectronico.getListaExpedientesAsociados().add(expedienteAsociado);

    this.expedienteElectronicoService.grabarExpedienteElectronico(expedienteElectronico);

    if (logger.isDebugEnabled()) {
      logger.debug("asociarExpediente(ExpedienteElectronico, ExpedienteElectronico) - end");
    }
  }

  private void bloquearExpediente(final ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("bloquearExpediente(expedienteElectronico={}) - start", expedienteElectronico);
    }

    try { 
      this.getEeConSuspensionService().bloquearExpedienteElectronicoTV(expedienteElectronico);
    } catch (final ParametroIncorrectoException e) {
      logger.error(e.getMessage(), e);
      throw new TeRuntimeException(e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("bloquearExpediente(ExpedienteElectronico) - end");
    }
  }

  /**
   *
   * @param expedienteElectronicoTV
   * @param usuariosDestinatarios
   * @return ComunicacionOficialExternalBean - El tipo de comunicación debe se
   *         Nota o Memo
   */
  /*
   * private ComunicacionOficialExternalBean
   * getComunicacionOficialExternalBean(ExpedienteElectronico
   * expedienteElectronicoTV, String[] usuariosDestinatarios,String
   * userQueTieneElEE) {
   *
   * logger.info("ENVIANDO COMUNICACION OFICIAL A " +
   * destinatarios(usuariosDestinatarios,userQueTieneElEE));
   *
   * ComunicacionOficialExternalBean externalBean = new
   * ComunicacionOficialExternalBean( null, null,
   * "Se ha solicitado una toma de vista con suspensión de plazos sobre el expediente: "
   * + expedienteElectronicoTV.getCodigoCaratula()+
   * ". La misma se encuentra pendiente de resolución", usuariosDestinatarios,
   * new ArchivosBean[0],
   * "Se ha solicitado una toma de vista con suspensión de plazos sobre el expediente:"
   * + expedienteElectronicoTV.getCodigoCaratula() +
   * " La misma se encuentra pendiente de resolución", "Comunicacion", "Nota",
   * expedienteElectronicoTV.getUsuarioCreador());
   *
   * return externalBean; }
   *
   * /* public Documento buscarDocumento(ComunicacionOficialExternalResult
   * result ) {
   *
   * String actuacionSADETipoDoc =
   * this.extraerAcronimo(result.getCodigoComunicacionOficial()); Integer
   * anioDocumento = this.extraerAnio(result.getCodigoComunicacionOficial());
   * Integer numeroDocumento =
   * this.extraerNumeroDocumento(result.getCodigoComunicacionOficial()); String
   * reparticionDocumento =
   * this.extraerReparticionDocumento(result.getCodigoComunicacionOficial());
   *
   * Documento documentoEncontrado =
   * documentoManagerService.buscarDocumentoEstandar(actuacionSADETipoDoc,
   * anioDocumento, numeroDocumento, reparticionDocumento);
   *
   * return documentoEncontrado; }
   */

  public DocumentoDTO buscarDocumento(final ResponseExternalGenerarDocumento response) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDocumento(response={}) - start", response);
    }

    final String actuacionSADETipoDoc = this.extraerAcronimo(response.getNumero());
    final Integer anioDocumento = this.extraerAnio(response.getNumero());
    final Integer numeroDocumento = this.extraerNumeroDocumento(response.getNumero());
    final String reparticionDocumento = this.extraerReparticionDocumento(response.getNumero());

    final DocumentoDTO documentoEncontrado = documentoManagerService.buscarDocumentoEstandar(
        actuacionSADETipoDoc, anioDocumento, numeroDocumento, reparticionDocumento);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarDocumento(ResponseExternalGenerarDocumento) - end - return value={}",
          documentoEncontrado);
    }
    return documentoEncontrado;
  }

  private ExpedienteElectronicoDTO buscarExpedientePor(final String numeroEE)
      throws ParametroIncorrectoException, ProcesoFallidoException, TomaVistaException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarExpedientePor(numeroEE={}) - start", numeroEE);
    }

    ExpedienteElectronicoDTO expediente;

    if (StringUtils.isBlank(numeroEE)) {
      throw new TomaVistaException("Debe ingresar un expediente.", null);
    } else {
      expediente = obtenerExpedienteElectronico(numeroEE);

      if (null == expediente) {
        throw new ProcesoFallidoException(
            "El número de expediente electrónico ingresado: " + numeroEE + " es inexistente.",
            null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarExpedientePor(String) - end - return value={}", expediente);
    }
    return expediente;
  }

  private SolicitanteDTO copiarDatosDelSolicitante(final SolicitanteDTO solicitante) {
    if (logger.isDebugEnabled()) {
      logger.debug("copiarDatosDelSolicitante(solicitante={}) - start", solicitante);
    }

    final SolicitanteDTO sol = new SolicitanteDTO();
    sol.setApellidoSolicitante(solicitante.getApellidoSolicitante());
    sol.setCodigoPostal(solicitante.getCodigoPostal());
    sol.setCuitCuil(solicitante.getCuitCuil());
    sol.setDepartamento(solicitante.getDepartamento());
    sol.setDocumento(copiarDocumento(solicitante.getDocumento()));
    sol.setDomicilio(solicitante.getDomicilio());
    sol.setEmail(solicitante.getEmail());
    sol.setNombreSolicitante(solicitante.getNombreSolicitante());
    sol.setPiso(solicitante.getPiso());
    sol.setRazonSocialSolicitante(solicitante.getRazonSocialSolicitante());
    sol.setSegundoApellidoSolicitante(solicitante.getSegundoApellidoSolicitante());
    sol.setTercerApellidoSolicitante(solicitante.getTercerApellidoSolicitante());
    sol.setSegundoNombreSolicitante(solicitante.getSegundoNombreSolicitante());
    sol.setTercerNombreSolicitante(solicitante.getTercerNombreSolicitante());
    sol.setTelefono(solicitante.getTelefono());

    if (logger.isDebugEnabled()) {
      logger.debug("copiarDatosDelSolicitante(Solicitante) - end - return value={}", sol);
    }
    return sol;
  }

  private DocumentoDeIdentidadDTO copiarDocumento(final DocumentoDeIdentidadDTO docu) {
    if (logger.isDebugEnabled()) {
      logger.debug("copiarDocumento(docu={}) - start", docu);
    }

    final DocumentoDeIdentidadDTO doc = new DocumentoDeIdentidadDTO();
    doc.setNumeroDocumento(docu.getNumeroDocumento());
    doc.setTipoDocumento(docu.getTipoDocumento());
    doc.setTipoDocumentoPosible(docu.getTipoDocumentoPosible());

    if (logger.isDebugEnabled()) {
      logger.debug("copiarDocumento(DocumentoDeIdentidad) - end - return value={}", doc);
    }
    return doc;
  }

  private String destinatarios(final List<String> destinatarios, final String userQueTieneElEE) {
    if (logger.isDebugEnabled()) {
      logger.debug("destinatarios(destinatarios={}, userQueTieneElEE={}) - start", destinatarios,
          userQueTieneElEE);
    }

    String info = "destinatarios: ";

    for (String destino : destinatarios) {
      if (destino == null) {
        destino = userQueTieneElEE;
        info += "Y AL USUARIO QUE POSEE EL EE: " + destino;
        break;
      } else {
        info += destino + ", ";
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("destinatarios(List<String>, String) - end - return value={}", info);
    }
    return info;
  }

  public void eliminarRepetidos(final List<String> list) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarRepetidos(list={}) - start", list);
    }

    final Set<String> hs = new HashSet<String>();
    hs.addAll(list);
    list.clear();
    list.addAll(hs);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarRepetidos(List<String>) - end");
    }
  }

  private DocumentoDTO enviarComunicacionOficial(final ExpedienteElectronicoDTO ee,
      final List<String> usuariosAsignadores, final String usuarioQueTieneEE)
      throws ParametroInvalidoException, CantidadDatosNoSoportadaException,
      ErrorGeneracionDocumentoException, ClavesFaltantesException,
      ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "enviarComunicacionOficial(ee={}, usuariosAsignadores={}, usuarioQueTieneEE={}) - start",
          ee, usuariosAsignadores, usuarioQueTieneEE);
    }

    final RequestExternalGenerarDocumento request = this.generarCCOO(ee, usuariosAsignadores,
        usuarioQueTieneEE);
    final ResponseExternalGenerarDocumento response = generarDocService
        .generarDocumentoGEDO(request);

    final DocumentoDTO returnDocumento = this.buscarDocumento(response);
    if (logger.isDebugEnabled()) {
      logger.debug(
          "enviarComunicacionOficial(ExpedienteElectronico, List<String>, String) - end - return value={}",
          returnDocumento);
    }
    return returnDocumento;
  }

  private String extraerAcronimo(String value) {
    if (logger.isDebugEnabled()) {
      logger.debug("extraerAcronimo(value={}) - start", value);
    }

    value = value.substring(0, value.indexOf("-"));

    if (logger.isDebugEnabled()) {
      logger.debug("extraerAcronimo(String) - end - return value={}", value);
    }
    return value;
  }

  private Integer extraerAnio(String value) {
    if (logger.isDebugEnabled()) {
      logger.debug("extraerAnio(value={}) - start", value);
    }

    value = value.substring(value.indexOf("-") + 1, value.indexOf("-") + 5);
    final Integer returnInteger = new Integer(value);
    if (logger.isDebugEnabled()) {
      logger.debug("extraerAnio(String) - end - return value={}", returnInteger);
    }
    return returnInteger;
  }

  private Integer extraerNumeroDocumento(String value) {
    if (logger.isDebugEnabled()) {
      logger.debug("extraerNumeroDocumento(value={}) - start", value);
    }

    value = value.substring(8, 16);
    final Integer returnInteger = new Integer(value);
    if (logger.isDebugEnabled()) {
      logger.debug("extraerNumeroDocumento(String) - end - return value={}", returnInteger);
    }
    return returnInteger;
  }

  /*
   * private Documento enviarComunicacionOficial(ExpedienteElectronico
   * expedienteElectronico, String[] usuariosAsignadores,String
   * usuarioQuePoseeElExp) throws RemoteException, org.apache.axis.AxisFault {
   *
   * String ccoo = ""; java.net.URL urlComunicacionOficialWS = null;
   *
   * ComunicacionOficialExternalBean externalBean = null;
   * IComunicacionesOficialesWSServiceSoapBindingStub comunicacionesOficiales =
   * null; ComunicacionOficialExternalResult result = null;
   *
   * externalBean =
   * this.getComunicacionOficialExternalBean(expedienteElectronico,
   * usuariosAsignadores,usuarioQuePoseeElExp);
   *
   * try {
   *
   * urlComunicacionOficialWS = new URL(muleUrl +
   * comunicacionesOficialesServicePort); comunicacionesOficiales =
   * (IComunicacionesOficialesWSServiceSoapBindingStub) (new
   * ar.gob.gcaba.co.ws.comunicacionesOficiales.
   * IComunicacionesOficialesWSServiceLocator(urlCCOO)).
   * getIComunicacionesOficialesWSPort(urlComunicacionOficialWS);
   *
   *
   * logger.info(comunicacionesOficiales.getClass().getName()); result =
   * comunicacionesOficiales.insertarComunicacionOficial(externalBean);
   *
   * } catch (MalformedURLException mfURLExc) { logger.error(
   * "Ha ocurrido un error obteniendo la URL de ComunicacionesOficialesWSService"
   * + mfURLExc.getMessage()); throw new TeException(mfURLExc.getMessage()); }
   * catch (javax.xml.rpc.ServiceException e) { logger.error(
   * "Ha ocurrido un error obteniendo ComunicacionesOficialesWSService" +
   * e.getMessage()); throw new TeException(e.getMessage()); } catch
   * (RemoteException e){ logger.error(
   * "Ha ocurrido un error insertando Comunicacion Oficial: " + e.getMessage(),
   * e); throw new TeException(
   * "Ha ocurrido un error insertando Comunicacion Oficial: " + e.getMessage());
   * }
   *
   * if (null != result) { ccoo = result.getCodigoComunicacionOficial();
   * Documento documento = this.buscarDocumento(result);
   * documento.setDefinitivo(true);
   * documento.setNombreArchivo(documento.getNumeroSade()+".pdf");
   * if(logger.isInfoEnabled()) { logger.info(
   * "CCOO realizada con exito - Codigo de Comunicacion Oficial: " + ccoo); }
   * else { logger.debug(
   * "CCOO realizada con exito - Codigo de Comunicacion Oficial: " + ccoo); }
   *
   * if (null != documento) { logger.info(
   * "GENERANDO DOCUMENTO POR LA COMUNICACION OFICIAL... DOCUMENTO: " +
   * documento.getNumeroSade()); return documento; } } return null; }
   */

  private String extraerReparticionDocumento(String value) {
    if (logger.isDebugEnabled()) {
      logger.debug("extraerReparticionDocumento(value={}) - start", value);
    }

    value = value.substring(21, value.length());

    if (logger.isDebugEnabled()) {
      logger.debug("extraerReparticionDocumento(String) - end - return value={}", value);
    }
    return value;
  }

  private RequestExternalGenerarDocumento generarCCOO(final ExpedienteElectronicoDTO e,
      final List<String> destinatarios, final String usuarioQueTieneEE) {

    logger.info(
        "ENVIANDO COMUNICACION OFICIAL A " + destinatarios(destinatarios, usuarioQueTieneEE));

    final RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    request.setAcronimoTipoDocumento(tipoComunicacionOficialNota);
    request.setSistemaOrigen("EE");
    request.setData(
        ("Se ha solicitado una toma de vista con suspensión de plazos sobre el expediente: "
            + e.getCodigoCaratula() + ". La misma se encuentra pendiente de resolución")
                .getBytes());
    request.setReferencia(
        "Se ha solicitado la Toma de vista del Expediente: " + e.getCodigoCaratula());
    request.setUsuario(e.getUsuarioCreador());
    final List<String> users = new ArrayList<String>();
    // Se agrega el user que posee el doc
    if (!StringUtils.isEmpty(usuarioQueTieneEE)) {
      users.add(usuarioQueTieneEE);
    }

    for (final String destino : destinatarios) {
      users.add(destino);
    }
    eliminarRepetidos(users);
    request.setListaUsuariosDestinatarios(users);
    request.setMensajeDestinatario(
        "Se ha solicitado una toma de vista con suspensión de plazos sobre el expediente:"
            + e.getCodigoCaratula() + " La misma se encuentra pendiente de resolución");

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarCCOO(ExpedienteElectronico, List<String>, String) - end - return value={}",
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
      final RequestExternalGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoEnGedo(request={}) - start", request);
    }

    ResponseExternalGenerarDocumento response = null;
    try {
      response = this.generarDocumentoService.generarDocumentoGEDO(request);

    } catch (final GenerarDocumentoException e) {
      throw e;
    }

    if (response == null) {
      logger.error("Respuesta nula de Gedo para generar el documento a: " + request.getUsuario()
          + " con motivo: " + request.getReferencia());
      throw new TeRuntimeException("Se produjo un error al generar el documento", null);
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarDocumentoEnGedo(com.egoveris.deo.generardocumento.service.client.external.nombramiento.RequestExternalGenerarDocumento) - end - return value={}",
            response);
      }
      return response;
    }
  }

  private DocumentoDTO generarDocumentoGEDO(final ExpedienteElectronicoDTO expedienteElectronico,
      final String usuarioTAD) throws RuntimeException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoGEDO(expedienteElectronico={}, usuarioTAD={}) - start",
          expedienteElectronico, usuarioTAD);
    }

    try {
      final RequestExternalGenerarDocumento request = this.armarRequestParaGenerarDocumentoEnGedo(expedienteElectronico, usuarioTAD);
      final ResponseExternalGenerarDocumento response = this.generarDocumentoEnGedo(request);
      final DocumentoDTO documento = new DocumentoDTO();
      documento.setFechaCreacion(new Date());
      documento.setFechaAsociacion(new Date());
      documento.setDefinitivo(true);
      documento.setTipoDocAcronimo(acronimoTipoDocTomaVistaSinSuspension);
      documento.setNombreArchivo(response.getNumero() + ".pdf");
      documento.setNumeroSade(response.getNumero());
      documento.setMotivo("Toma de vista sin suspensión del expediente: "
          + expedienteElectronico.getCodigoCaratula());
      documento.setTipoDocGenerado(ConstantesWeb.TIPO_DOCUMENTO_PASE);

      if (logger.isDebugEnabled()) {
        logger.debug("generarDocumentoGEDO(ExpedienteElectronico, String) - end - return value={}",
            documento);
      }
      return documento;

    } catch (final RuntimeException rte) {
      throw rte;
    }
  }

  private ExpedienteElectronicoDTO generarExpedienteTomaVista(final ExpedienteElectronicoDTO ee,
      final String motivo) throws ProcesoFallidoException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarExpedienteTomaVista(ee={}, motivo={}) - start", ee, motivo);
    }

    ExpedienteElectronicoDTO expedienteElectronicoTV = null;

    try {

      final SolicitudExpedienteDTO solicitud = generarSolicitud(ee, motivo);

      solicitudExpedienteService.grabarSolicitudPorCaratulacionInternaoExterna(solicitud);
      final TrataDTO trata = trataService.buscarTrataByCodigo(trataTomaVista);
      expedienteElectronicoTV = getExpedienteElectronicoFactory().crearExpedienteElectronico(
          processEngine, solicitud, trata, "", null, ee.getUsuarioCreador(),
          motivoCaratulacionTomarVista + ee.getCodigoCaratula());

    } catch (final Exception e) {
      logger.error(e.getMessage());
      throw new TeRuntimeException(
          "Se produjo un error al generar el expediente toma vista" + e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarExpedienteTomaVista(ExpedienteElectronico, String) - end - return value={}",
          expedienteElectronicoTV);
    }
    return expedienteElectronicoTV;
  }

  private SolicitudExpedienteDTO generarSolicitud(final ExpedienteElectronicoDTO ee,
      final String motivo) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarSolicitud(ee={}, motivo={}) - start", ee, motivo);
    }

    final SolicitudExpedienteDTO solicitudTV = new SolicitudExpedienteDTO();
    solicitudTV.setFechaCreacion(new Date());
    solicitudTV.setUsuarioCreacion(ee.getUsuarioCreador());
    solicitudTV.setEsSolicitudInterna(Boolean.FALSE);
    final StringBuilder str = new StringBuilder();
    str.append(motivoCaratulacionTomarVista);
    str.append(ee.getCodigoCaratula());
    str.append(" - ");
    str.append(motivo);
    solicitudTV.setMotivo(str.toString());
    final SolicitanteDTO solicitante = copiarDatosDelSolicitante(
        ee.getSolicitudIniciadora().getSolicitante());

    solicitudTV.setSolicitante(solicitante);

    if (logger.isDebugEnabled()) {
      logger.debug("generarSolicitud(ExpedienteElectronico, String) - end - return value={}",
          solicitudTV);
    }
    return solicitudTV;

  }

  @Override
  @WebMethod(operationName = "generarTomaVistaConSuspension")
  public String generarTomaVistaConSuspension(
      @WebParam(name = "numeroEE") final String codigoCaratula,
      @WebParam(name = "motivo") String motivo)
      throws ProcesoFallidoException, ParametroIncorrectoException, TomaVistaException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarTomaVistaConSuspension(codigoCaratula={}, motivo={}) - start",
          codigoCaratula, motivo);
    }

    if (StringUtils.isEmpty(motivo)) {
      motivo = "Tomar vista con suspensión. ";
    }

    List<String> usuariosAsignadores = new ArrayList<String>();
    ExpedienteElectronicoDTO ee = null;
    ExpedienteElectronicoDTO expedienteElectronicoTV = null;
    Task workingTaskEeTV = null;
    int idEeBloqueado = 0;

    try {
      ee = this.buscarExpedientePor(codigoCaratula);

      if (this.getEeConSuspensionService().obtenerEEConSuspensionPorCodigo(ee.getId()) != null) {
        throw new TomaVistaException(
            "El expediente ya se encuentra en proceso de toma vista con suspension.", null);       
      }
      
      final ReparticionBean dgtal = reparticionServ
          .buscarDGTALByReparticion(ee.getCodigoReparticionUsuario());

      if (dgtal != null) {
        // filtar usuarios, quedarse solamente con los que tienen perfil
        // asignador
        usuariosAsignadores = this.loadUsersByDGTALCode(dgtal);
      } else {
        throw new ParametroIncorrectoException("La Repartición del expediente no poseÃ© DGTAL",
            null);
      }

      expedienteElectronicoTV = this.generarExpedienteTomaVista(ee, motivo);
      workingTaskEeTV = this.obtenerWorkingTask(expedienteElectronicoTV);
      final Task workingTaskEE = this.obtenerWorkingTask(ee);

      this.bloquearExpediente(ee);
      if (logger.isInfoEnabled()) {
        logger.info("Se ha bloqueado el expediente con id: " + ee.getId());
      } else {
        logger.debug("Se ha bloqueado el expediente con id: " + ee.getId());
      }

      // Voy a buscar los usuarios para la reparticion, los obtengo de la
      // participation
      final List<Participation> aux = this.processEngine.getTaskService()
          .getTaskParticipations(workingTaskEE.getId());

      String reparticion = new String();

      if (aux != null && aux.size() > 0) {
        final String grupo = aux.get(0).getGroupId();
        reparticion = StringUtils.substringBefore(grupo, "-");
        reparticion = StringUtils.substringBefore(reparticion, ".");
      }

      if (!StringUtils.isEmpty(reparticion)) {
        final List<Usuario> listaUSuariosPorReparticion = usuariosSADEServiceImpl
            .getUsuarios(reparticion);

        if (listaUSuariosPorReparticion.size() > 0) {
          for (final Usuario nombreUsuario : listaUSuariosPorReparticion) {
            if (!usuariosAsignadores.contains(nombreUsuario.getNombreApellido())) {
              if (usuariosSADEService.usuarioTieneRol(nombreUsuario.getUsername(),
                  ConstantesWeb.ROL_ASIGNADOR)) {
                usuariosAsignadores.add(nombreUsuario.getUsername());
              }
            }
          }
        }
      }

      DocumentoDTO d = null;
      try {
        d = this.enviarComunicacionOficial(ee, usuariosAsignadores, workingTaskEE.getAssignee());
      } catch (final Exception e) {
        logger.info("Error al invocar servicio de CCOO... Restaurando expediente: "
            + ee.getCodigoCaratula());
        final ExpedienteElectronicoConSuspensionDTO ees = eeConSuspensionService
            .obtenerEEConSuspensionPorCodigo(ee.getId());
        eeConSuspensionService.eliminarEEConSuspension(ees);
        paseAGuardaTemporal(expedienteElectronicoTV);
        logger.error(e.getMessage());
        throw new TomaVistaException("Error al comunicarse con los Servicios de CCOO.", e);
      }
      d.setFechaAsociacion(new Date());
      d.setDefinitivo(true);
      logger.info("Agregando documento generado por la comunicacion oficial al expediente: "
          + ee.getCodigoCaratula());
      ee.getDocumentos().add(d);

      logger.info(
          "Agregando documento generado por la comunicacion oficial al expediente toma vista: "
              + expedienteElectronicoTV.getCodigoCaratula());

      expedienteElectronicoTV.getDocumentos().add(d);

      logger.info("ASOCIANDO EXPEDIENTES...");
      this.asociarExpediente(ee, expedienteElectronicoTV);
      this.asociarExpediente(expedienteElectronicoTV, ee);

      this.realizarPase(expedienteElectronicoTV, dgtal.getCodigo(), workingTaskEeTV);

      if (logger.isInfoEnabled()) {
        logger.info("Se ha generado el expediente toma vista: "
            + expedienteElectronicoTV.getCodigoCaratula());
      } else {
        logger.debug("Se ha generado el expediente toma vista: "
            + expedienteElectronicoTV.getCodigoCaratula());
      }

      return expedienteElectronicoTV.getCodigoCaratula();

    } catch (final TomaVistaException e) {
      logger.error(e.getMessage(), e);
      throw new TomaVistaException(e.getMessage(), e);
    } catch (final Exception e) {
      logger.error(e.getMessage(), e);
      throw new TeRuntimeException(e.getMessage(), e);
    }

  }

  @Override
  @WebMethod(operationName = "generarTomaVistaSinSuspension")
  public TomaVistaResponse generarTomaVistaSinSuspension(
      @WebParam(name = "numeroEE") final String numeroEE,
      @WebParam(name = "usuarioTAD") final String usuarioTAD)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarTomaVistaSinSuspension(numeroEE={}, usuarioTAD={}) - start", numeroEE,
          usuarioTAD);
    }

    TomaVistaResponse tomaVista = null;

    try {

      tomaVista = new TomaVistaResponse();

      final ExpedienteElectronicoDTO expediente = this.buscarExpedientePor(numeroEE);

      validaUsuarioExpedientes(usuarioTAD);

      tomaVista.setListDocumentosOficiales(this.listDocumentosOficiales(expediente));

      DocumentoDTO documentoMotivo = this.generarDocumentoGEDO(expediente, usuarioTAD);
      expediente.agregarDocumento(documentoMotivo);
      expedienteElectronicoService.modificarExpedienteElectronico(expediente);
      this.documentoService.guardar(documentoMotivo);

    } catch (final ParametroIncorrectoException | ProcesoFallidoException| TomaVistaException e) {
      logger.error(e.getMessage(), e);
      throw new TeRuntimeException(e.getMessage(), e);
    } 

    if (logger.isDebugEnabled()) {
      logger.debug("generarTomaVistaSinSuspension(String, String) - end - return value={}",
          tomaVista);
    }
    return tomaVista;
  }

  public ExpedienteElectronicoConSuspensionService getEeConSuspensionService() {
    return this.eeConSuspensionService;
  }

  public ExpedienteElectronicoFactory getExpedienteElectronicoFactory() {
    if (logger.isDebugEnabled()) {
      logger.debug("getExpedienteElectronicoFactory() - start");
    }

    if (this.expedienteElectronicoFactory == null) {
      this.expedienteElectronicoFactory = ExpedienteElectronicoFactory.getInstance();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getExpedienteElectronicoFactory() - end - return value={}",
          this.expedienteElectronicoFactory);
    }
    return this.expedienteElectronicoFactory;
  }

  public IExternalGenerarDocumentoService getGenerarDocService() {
    return generarDocService;
  }

  public String getMule_url() {
    return this.muleUrl;
  }

  public PaseExpedienteService getPaseExpedienteService() {
    return paseExpedienteService;
  }

  public String getReparticionGT() {
    return reparticionGT;
  }

  public ReparticionServ getReparticionServ() {
    return this.reparticionServ;
  }

  public String getTipoComunicacionOficialNota() {
    return tipoComunicacionOficialNota;
  }

  private List<Usuario> getTodosLosUsuariosPorReparticion(final String reparticion,
      final String sector) {
    if (logger.isDebugEnabled()) {
      logger.debug("getTodosLosUsuariosPorReparticion(reparticion={}, sector={}) - start",
          reparticion, sector);
    }

    listaUsuarios = this.getUsuariosSADEService()
        .getTodosLosUsuariosXReparticionYSectorEE(reparticion, sector);

    if (logger.isDebugEnabled()) {
      logger.debug("getTodosLosUsuariosPorReparticion(String, String) - end - return value={}",
          listaUsuarios);
    }
    return listaUsuarios;
  }

  public String getTrataTomaVista() {
    return trataTomaVista;
  }

  private List<String> getUsuariosPorRolAsignador(final List<Usuario> usuarios) {
    if (logger.isDebugEnabled()) {
      logger.debug("getUsuariosPorRolAsignador(usuarios={}) - start", usuarios);
    }

    String info = "USUARIOS ASIGNADORES A LOS QUE SE VA A ENVIAR LA CCOO: ";
    final List<String> asignadores = new ArrayList<String>();
    for (int i = 0; i < usuarios.size(); i++) {
      if (usuarios.get(i) != null
          && usuariosSADEService.usuarioTieneRol(usuarios.get(i).getUsername(), "EE.ASIGNADOR")) {
        asignadores.add(usuarios.get(i).getUsername());
        info += ", " + usuarios.get(i).getUsername();
      }
    }
    logger.info(info);
    return asignadores;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

//  public UsuariosSADEServiceImpl getUsuariosSADEServiceImpl() {
//    return this.usuariosSADEServiceImpl;
//  }

  public WorkFlowService getWorkFlowService() {
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - start");
    }

    workFlowService = (WorkFlowService) TomaVistaExternalServiceImpl.getService("workFlowService");

    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - end - return value={}", workFlowService);
    }
    return workFlowService;
  }

  private List<DocumentoTVDTO> listDocumentosOficiales(final ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("listDocumentosOficiales(expediente={}) - start", expediente);
    }

    final List<DocumentoTVDTO> listDocumentosOficiales = new ArrayList<>();
    final TomaVistaResponse tomaVista = new TomaVistaResponse();
    
		for (final DocumentoDTO lista : expediente.getDocumentos()) {
			final DocumentoTVDTO doc = new DocumentoTVDTO();
			doc.setNumero(lista.getNumeroSade());
			doc.setReferencia(lista.getMotivo());
			doc.setNombreOriginal(lista.getNombreArchivo());
			doc.setFechaAsociacion(lista.getFechaAsociacion());
			doc.setAcronimo(lista.getTipoDocAcronimo());
			listDocumentosOficiales.add(doc);
		}

    tomaVista.setListDocumentosOficiales(listDocumentosOficiales);

    if (logger.isDebugEnabled()) {
      logger.debug("listDocumentosOficiales(ExpedienteElectronico) - end - return value={}",
          listDocumentosOficiales);
    }
    return listDocumentosOficiales;
  }

  /**
   *
   * Se asume que el codigo de la DGTAL enviado por TAD coincide con los valores
   * de REPARTICION.CODIGO_REPARTICION
   *
   * Se asume el usuario creador es el que posee el EE y al mismo se le envia la
   * CCOO sino se deberia enviar la CCOO al usuario que figura en el historial
   * de operaciones del EE.
   *
   * @author hgiudatt
   * @param codigoDGTAL
   * @return
   * @throws TomaVistaException
   */
  private List<String> loadUsersByDGTALCode(final ReparticionBean dgtal)
      throws ParametroIncorrectoException, TomaVistaException {
    if (logger.isDebugEnabled()) {
      logger.debug("loadUsersByDGTALCode(dgtal={}) - start", dgtal);
    }

    final List<Usuario> usuarios = new ArrayList<Usuario>();
    List<String> usuariosAsignadores;
    String info = " SECTORES QUE SON MESA:  ";

    final List<SectorInternoBean> sectores = sectorServ
        .buscarSectoresPorReparticionOrderByMesa(dgtal.getId());
    sectorMesa = sectores.get(0);
    // Buscar a los usuarios asignadores de la mesa de entrada de la DGTAL
    // Buscar al usuario que posee el EE
    for (final SectorInternoBean s : sectores) {
      info += ", " + s.getCodigo();
      usuarios.addAll(this.getTodosLosUsuariosPorReparticion(dgtal.getCodigo(), s.getCodigo()));
    }

    logger.info(info);
    if (usuarios.isEmpty()) {
      throw new TomaVistaException("No ha sido posible cargar los Usuarios asignadores", null);
    }

    if (!CollectionUtils.isEmpty(usuarios)) {
      usuariosAsignadores = getUsuariosPorRolAsignador(usuarios);
    } else {
      throw new TomaVistaException(
          "La reparticion: " + dgtal.getNombre() + " no posee usuarios asignadores.", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("loadUsersByDGTALCode(ReparticionBean) - end - return value={}",
          usuariosAsignadores);
    }
    return usuariosAsignadores;
  }

  /**
   * Obtiene la tarea del expediente electrónico otorgado por parámetro.
   *
   * @param expedienteElectronico
   * @return la tarea correspondiente al expediente electrónico otorgado por
   *         parámetro.
   * @throws ParametroIncorrectoException
   */
  @Override
  public Task obtenerWorkingTask(final ExpedienteElectronicoDTO expedienteElectronico)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerWorkingTask(expedienteElectronico={}) - start", expedienteElectronico);
    }

    if (expedienteElectronico != null) {
      final TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery()
          .executionId(expedienteElectronico.getIdWorkflow());

      final Task returnTask = taskQuery.uniqueResult();
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerWorkingTask(ExpedienteElectronico) - end - return value={}",
            returnTask);
      }
      return returnTask;

    } else {
      throw new ParametroIncorrectoException("El expediente electrónico es nulo.", null);
    }
  }

  private void paseAGuardaTemporal(final ExpedienteElectronicoDTO expedienteTV)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("paseAGuardaTemporal(expedienteTV={}) - start", expedienteTV);
    }

    final HashMap<String, String> detalles = new HashMap<String, String>();
    detalles.put("estadoAnterior", expedienteTV.getEstado());
    detalles.put("estadoAnteriorParalelo", null);
    detalles.put("destinatario", reparticionGT);

    Task workingTaskEeTV;
    workingTaskEeTV = this.obtenerWorkingTask(expedienteTV);

    this.paseExpedienteService.paseGuardaTemporal(expedienteTV, workingTaskEeTV,
        expedienteTV.getUsuarioCreador(), detalles, expedienteTV.getEstado(),
        "Envío a Guarda Temporal por fallo en la solicitud de toma de vista.");

    // Avanza la tarea en el workflow
    signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskEeTV, detalles);

    // Vuelve a avanzar la tarea en el workflow para cerrar la misma.
    
    // 11-05-2020: No ejecutar el cierre, o desaparece la tarea
    //processEngine.getExecutionService().signalExecutionById(workingTaskEeTV.getExecutionId(),
    //    "Cierre");

    logger.info(
        "Se ha enviado a guarda temporal el expediente: " + expedienteTV.getCodigoCaratula());
  }

  /**
   *
   * Pendiente de definicion - Cuando se conozcan la DGTAL de la reparticion del
   * usuario del EE original y el sector que va a ser constante para todas las
   * DGTAL ESTOS DEBERAN PASARSE COMO DETALLES
   *
   * @param expedienteElectronicoTV
   * @param usuarioEEOriginal
   * @param workingTask
   */
  private void realizarPase(final ExpedienteElectronicoDTO expedienteElectronicoTV,
      final String reparticion, final Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "realizarPase(expedienteElectronicoTV={}, reparticion={}, workingTask={}) - start",
          expedienteElectronicoTV, reparticion, workingTask);
    }

    final Map<String, String> detalles = new HashMap<String, String>();

    detalles.put("destinatario", reparticion);
    detalles.put("tareaGrupal", "esTareaGrupal");
    detalles.put("grupoSeleccionado", reparticion);
    detalles.put("idExpedienteElectronico", String.valueOf(expedienteElectronicoTV.getId()));
    this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask,
        expedienteElectronicoTV, expedienteElectronicoTV.getUsuarioCreador(),
        workingTask.getActivityName(),
        "Pase del Expediente para la aceptacion o rechazo de la toma vista", detalles, true);

    final List<Participation> aux = this.processEngine.getTaskService()
        .getTaskParticipations(workingTask.getId());
    if (aux != null && aux.size() > 0) {
      this.processEngine.getTaskService().removeTaskParticipatingGroup(workingTask.getId(),
          aux.get(0).getGroupId(), "canditate");
    } else {
      this.processEngine.getTaskService().addTaskParticipatingGroup(workingTask.getId(),
          reparticion, "candidate");
      this.processEngine.getTaskService().assignTask(workingTask.getId(), null);
      setearVariablesAlWorkflow(workingTask, detalles);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("realizarPase(ExpedienteElectronico, String, Task) - end");
    }
  }

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext)
      throws BeansException {
    TomaVistaExternalServiceImpl.applicationContext = applicationContext;

  }

  private void setearVariablesAlWorkflow(final Task workingTask,
      final Map<String, String> detalles) throws NumberFormatException {
    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(workingTask={}, detalles={}) - start", workingTask,
          detalles);
    }

    final Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("estadoAnterior", detalles.get("estadoAnterior"));
    variables.put("estadoAnteriorParalelo", detalles.get("estadoAnteriorParalelo"));
    variables.put("grupoSeleccionado", detalles.get("grupoSeleccionado"));
    variables.put("tareaGrupal", detalles.get("tareaGrupal"));
    variables.put("usuarioSeleccionado", detalles.get("usuarioSeleccionado"));
    variables.put("idExpedienteElectronico",
        Integer.parseInt(detalles.get("idExpedienteElectronico")));
    setVariablesWorkFlow(variables, workingTask);

    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(Task, Map<String,String>) - end");
    }
  }

  public void setEeConSuspensionService(
      final ExpedienteElectronicoConSuspensionService eeConSuspensionService) {
    this.eeConSuspensionService = eeConSuspensionService;
  }

  public void setGenerarDocService(final IExternalGenerarDocumentoService generarDocService) {
    this.generarDocService = generarDocService;
  }

  // public String gettipoDocumentoCCOOTomaVista() {
  // return tipoDocumentoCCOOTomaVista;
  // }
  //
  // public void settipoDocumentoCCOOTomaVista(String tipoDocumentoCCOO) {
  // this.tipoDocumentoCCOOTomaVista = tipoDocumentoCCOO;
  // }

  public void setMule_url(final String mule_url) {
    this.muleUrl = mule_url;
  }

  public void setPaseExpedienteService(final PaseExpedienteService paseExpedienteService) {
    this.paseExpedienteService = paseExpedienteService;
  }

  public void setReparticionGT(final String reparticionGT) {
    this.reparticionGT = reparticionGT;
  }

  public void setReparticionServ(final ReparticionServ reparticionServ) {
    this.reparticionServ = reparticionServ;
  }

  public void setTipoComunicacionOficialNota(final String tipoComunicacionOficialNota) {
    this.tipoComunicacionOficialNota = tipoComunicacionOficialNota;
  }

  public void setTrataTomaVista(final String trataTomaVista) {
    this.trataTomaVista = trataTomaVista;
  }

  public void setUsuariosSADEService(final UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public void setUsuariosSADEServiceImpl(final UsuariosSADEServiceImpl usuariosSADEServiceImpl) {
    this.usuariosSADEServiceImpl = usuariosSADEServiceImpl;
  }

  public void setVariablesWorkFlow(final Map<String, Object> variables, final Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug("setVariablesWorkFlow(variables={}, workingTask={}) - start", variables,
          workingTask);
    }

    getWorkFlowService().setVariables(processEngine, workingTask.getExecutionId(), variables);

    if (logger.isDebugEnabled()) {
      logger.debug("setVariablesWorkFlow(Map<String,Object>, Task) - end");
    }
  }

  public void signalExecution(final String nameTransition, final Task workingTask,
      final Map<String, String> detalles) {
    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(nameTransition={}, workingTask={}, detalles={}) - start",
          nameTransition, workingTask, detalles);
    }

    // PASO TODAS LAS VARIABLES ANTES DE REALIZAR EL SIGNAL
    setearVariablesAlWorkflow(workingTask, detalles);

    // Paso a la siguiente Tarea/Estado definida en el Workflow
    processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
        nameTransition);

    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(String, Task, Map<String,String>) - end");
    }
  }

  private void validaUsuarioExpedientes(final String username) throws TomaVistaException {
    if (logger.isDebugEnabled()) {
      logger.debug("validaUsuarioExpedientes(username={}) - start", username);
    }

    if (StringUtils.isBlank(username)) {
      throw new TomaVistaException("Debe ingresar un usuario.", null);
    } else {
      Usuario usuario = getUsuariosSADEService().getDatosUsuario(username);

      if (null == usuario.getCodigoReparticion()) {
        throw new TomaVistaException("El usuario ingresado: " + username + " es inexistente.",
            null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validaUsuarioExpedientes(String) - end");
    }
  }

}
