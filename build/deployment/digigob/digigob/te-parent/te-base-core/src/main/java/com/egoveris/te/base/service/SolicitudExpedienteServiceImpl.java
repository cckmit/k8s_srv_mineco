package com.egoveris.te.base.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.exception.GenerarExpedienteException;
import com.egoveris.te.base.model.DocumentoDeIdentidadDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpediente;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.repository.SolicitudExpedienteRepository;

@Service
@Transactional
public class SolicitudExpedienteServiceImpl implements SolicitudExpedienteService {
  private static final Logger logger = LoggerFactory
      .getLogger(SolicitudExpedienteServiceImpl.class);

  @Autowired
  private SolicitudExpedienteRepository solicitudExpedienteDAO;
  @Autowired
  private HistorialOperacionService historialOperacionService;
  @Autowired
  private ExpedienteElectronicoFactory expedienteElectronicoFactory;
  private DozerBeanMapper mapper = new DozerBeanMapper();

  /**
   * Construye un <code>IngresoSolicitudExpediente</code> con algunos parámetros
   * más. Generacion de Expediente para verificar que se pueda crear el
   * Expediente Electronico, antes de terminar la generacion de la solicitud. Se
   * inicializa el <code>WorkFlow</code> para alguna <code>Tarea</code> -
   * <code>SolicitudExpediente</code>
   * 
   * @param <code>ProcessEngine</code>
   *          processEngine,
   * @param La
   *          estructura que devuelve es
   *          <code>Map<String, Object></code>inputMap
   *          <ul>
   *          <li>ATTR_USER_NAME</li>
   *          <li>ATTR_APELLIDO_SOLICITANTE</li>
   *          <li>ATTR_SEGUNDO_APELLIDO_SOLICITANTE</li>
   *          <li>ATTR_TERCER_APELLIDO_SOLICITANTE</li>
   *          <li>ATTR_NOMBRE_SOLICITANTE</li>
   *          <li>ATTR_SEGUNDO_NOMBRE_SOLICITANTE</li>
   *          <li>ATTR_TERCER_NOMBRE_SOLICITANTE</li>
   *          <li>ATTR_RAZON_SOCIAL</li>
   *          <li>ATTR_EMAIL</li>
   *          <li>ATTR_TELEFONO</li>
   *          <li>ATTR_TIPO_DOC</li>
   *          <li>ATTR_CUIT_CUIL</li>
   *          <li>ATTR_NUM_DOC</li>
   *          <li>ATTR_MOTIVO_INTERNO</li>
   *          <li>ATTR_MOTIVO_EXTERNO</li>
   *          </ul>
   * @param <code>java.lang.Boolean</code>
   *          isSolicitudExterna,
   * @return <code>java.lang.Object</code> solicitudExpediente,
   * @throws GenerarExpedienteException 
   * @exception <code>Exception</code>
   *              exception
   */
  @Transactional
  public Object generarSolicitudExpediente(final Map<String, Object> inputMap,
      final org.jbpm.api.ProcessEngine processEngine, final TrataDTO norma,
      final String descripcion, final List<ExpedienteMetadataDTO> expedienteMetadata,
      final String username, final String motivoExpediente, final Boolean isSolicitudExterna)
      throws GenerarExpedienteException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarSolicitudExpediente(inputMap={}, processEngine={}, norma={}, descripcion={}, expedienteMetadata={}, username={}, motivoExpediente={}, isSolicitudExterna={}) - start",
          inputMap, processEngine, norma, descripcion, expedienteMetadata, username,
          motivoExpediente, isSolicitudExterna);
    }

    IngresoSolicitudExpedienteDTO ingresoSolicitudExpediente = new IngresoSolicitudExpedienteDTO();

    SolicitudExpedienteDTO solicitudExpediente = this.crearSolicitudExpediente(inputMap,
        new Date(), isSolicitudExterna);

    // guardar la solicitud
    // solicitudExpedienteDAO.guardar(solicitudExpediente);
    HistorialOperacionDTO historialOperacion = new HistorialOperacionDTO();
    Map<String, String> detalles = new HashMap<>();
    detalles.put("usuarioAnterior", username);
    detalles.put("motivo", solicitudExpediente.getMotivoExterno());

    // Grabo la solicitud con false
    this.grabarSolicitud(solicitudExpediente, historialOperacion, false);
    detalles.put("tipoOperacion", "SOLICITUD");
    detalles.put("idSolicitud", solicitudExpediente.getId().toString());

    historialOperacion.setDetalleOperacion(detalles);
    // grabo el historial con true
    this.grabarSolicitud(solicitudExpediente, historialOperacion, true);

    /**
     * la generacion de Expediente para verificar que se pueda crear el
     * Expediente Electronico, antes de terminar la generacion de la solicitud.
     */
    try {
      validarGenerarNuevoExpedienteElectronico(solicitudExpediente.getId());
    } catch (Exception e) {
        logger.error("error al validar nuevo Expediente", e);
    }

    ExpedienteElectronicoDTO expedienteElectronico;
    try {
      expedienteElectronico = getExpedienteElectronicoFactory()
          .crearExpedienteElectronico(processEngine, solicitudExpediente, norma, descripcion,
              expedienteMetadata, username, motivoExpediente);

              ingresoSolicitudExpediente.setSolicitudExpediente(solicitudExpediente);
              ingresoSolicitudExpediente.setExpedienteElectronico(expedienteElectronico);

    } catch (Exception e) {
        logger.error("error al obtener expediente Electronico", e);
     
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarSolicitudExpediente(Map<String,Object>, org.jbpm.api.ProcessEngine, Trata, String, List<ExpedienteMetadata>, String, String, Boolean) - end - return value={}",
          ingresoSolicitudExpediente);
    }
    return ingresoSolicitudExpediente;
  }

  /**
   * Se crea un SolicitudExpediente sin estado
   * 
   * @param La
   *          estructura que devuelve es
   *          <code>Map<String, Object></code>inputMap
   *          <ul>
   *          <li>ATTR_USER_NAME</li>
   *          <li>ATTR_APELLIDO_SOLICITANTE</li>
   *          <li>ATTR_NOMBRE_SOLICITANTE</li>
   *          <li>ATTR_RAZON_SOCIAL</li>
   *          <li>ATTR_EMAIL</li>
   *          <li>ATTR_TELEFONO</li>
   *          <li>ATTR_TIPO_DOC</li>
   *          <li>ATTR_NUM_DOC</li>
   *          <li>ATTR_MOTIVO_INTERNO</li>
   *          <li>ATTR_MOTIVO_EXTERNO</li>
   *          </ul>
   * @param <code>java.util.Date</code>now
   * @param <code>java.lang.Boolean</code>isSolicitudExterna
   * @return <code>SolicitudExpediente</code>object
   */
  private SolicitudExpedienteDTO crearSolicitudExpediente(final Map<String, Object> inputMap,
      final Date now, final Boolean isSolicitudExterna) {
    if (logger.isDebugEnabled()) {
      logger.debug("crearSolicitudExpediente(inputMap={}, now={}, isSolicitudExterna={}) - start",
          inputMap, now, isSolicitudExterna);
    }

    SolicitudExpedienteDTO solicitud;
	if(isSolicitudExterna) {
		solicitud = this.crearSolicitudExterna(inputMap, now, isSolicitudExterna);
	} else {
		solicitud = this.crearSolicitudInterna(inputMap, now, isSolicitudExterna);
	}
	
	if (logger.isDebugEnabled()) {
		logger.debug("crearSolicitudExpediente(Map<String,Object>, Date, Boolean) - end - return value={}", solicitud);
	}

    if (isSolicitudExterna) {
      solicitud = this.crearSolicitudExterna(inputMap, now, isSolicitudExterna);
    } else {
      solicitud = this.crearSolicitudInterna(inputMap, now, isSolicitudExterna);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearSolicitudExpediente(Map<String,Object>, Date, Boolean) - end - return value={}",
          solicitud);
    }
    return solicitud;
  }

  private SolicitudExpedienteDTO crearSolicitudExterna(final Map<String, Object> inputMap,
      final Date now, final Boolean isSolicitudExterna) {
    if (logger.isDebugEnabled()) {
      logger.debug("crearSolicitudExterna(inputMap={}, now={}, isSolicitudExterna={}) - start",
          inputMap, now, isSolicitudExterna);
    }

    SolicitudExpedienteDTO solicitudExterno = new SolicitudExpedienteDTO();

    SolicitanteDTO solicitante = new SolicitanteDTO();
    solicitudExterno.setFechaCreacion(now);
    solicitudExterno.setUsuarioCreacion((String) inputMap.get("ATTR_USER_NAME"));
    solicitudExterno.setEsSolicitudInterna(!isSolicitudExterna);

    solicitante.setApellidoSolicitante((String) inputMap.get("ATTR_APELLIDO_SOLICITANTE"));
    solicitante
        .setSegundoApellidoSolicitante((String) inputMap.get("ATTR_SEGUNDO_APELLIDO_SOLICITANTE"));
    solicitante
        .setTercerApellidoSolicitante((String) inputMap.get("ATTR_TERCER_APELLIDO_SOLICITANTE"));

    solicitante.setNombreSolicitante((String) inputMap.get("ATTR_NOMBRE_SOLICITANTE"));
    solicitante
        .setSegundoNombreSolicitante((String) inputMap.get("ATTR_SEGUNDO_NOMBRE_SOLICITANTE"));
    solicitante
        .setTercerNombreSolicitante((String) inputMap.get("ATTR_TERCER_NOMBRE_SOLICITANTE"));

    solicitante.setRazonSocialSolicitante((String) inputMap.get("ATTR_RAZON_SOCIAL"));
    solicitante.setEmail((String) inputMap.get("ATTR_EMAIL"));
    solicitante.setTelefono((String) inputMap.get("ATTR_TELEFONO"));

    DocumentoDeIdentidadDTO documento = new DocumentoDeIdentidadDTO();
    documento.setTipoDocumento((String) inputMap.get("ATTR_TIPO_DOC"));
    // documento.setNumeroDocumento(String.valueOf((Long)inputMap.get("ATTR_NUM_DOC")));
    documento.setNumeroDocumento((String) (inputMap.get("ATTR_NUM_DOC")));
    solicitante.setDocumento(documento);

    solicitante.setCuitCuil((String) inputMap.get("ATTR_CUIT_CUIL"));

    solicitante.setDomicilio((String) inputMap.get("ATTR_DOMICILIO"));
    solicitante.setPiso((String) inputMap.get("ATTR_PISO"));
    solicitante.setDepartamento((String) inputMap.get("ATTR_DEPARTAMENTO"));
    solicitante.setCodigoPostal((String) inputMap.get("ATTR_CODIGO_POSTAL"));
    // solicitante.setBarrio((String)inputMap.get("ATTR_BARRIO"));
    // solicitante.setComuna((String)inputMap.get("ATTR_COMUNA"));

    solicitudExterno.setMotivo((String) inputMap.get("ATTR_MOTIVO_INTERNO"));
    solicitudExterno.setMotivoExterno((String) inputMap.get("ATTR_MOTIVO_EXTERNO"));

    solicitudExterno.setSolicitante(solicitante);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearSolicitudExterna(Map<String,Object>, Date, Boolean) - end - return value={}",
          solicitudExterno);
    }
    return solicitudExterno;

  }

  private SolicitudExpedienteDTO crearSolicitudInterna(final Map<String, Object> inputMap,
      final Date now, final Boolean isSolicitudExterna) {
    if (logger.isDebugEnabled()) {
      logger.debug("crearSolicitudInterna(inputMap={}, now={}, isSolicitudExterna={}) - start",
          inputMap, now, isSolicitudExterna);
    }

    SolicitudExpedienteDTO solicitudInterna = new SolicitudExpedienteDTO();
    SolicitanteDTO solicitante = new SolicitanteDTO();
    solicitudInterna.setFechaCreacion(now);
    solicitudInterna.setUsuarioCreacion((String) inputMap.get("ATTR_USER_NAME"));
    solicitudInterna.setEsSolicitudInterna(!isSolicitudExterna);
    solicitudInterna.setMotivo((String) inputMap.get("ATTR_MOTIVO_INTERNO"));
    solicitudInterna.setMotivoExterno((String) inputMap.get("ATTR_MOTIVO_EXTERNO"));
    solicitante.setEmail((String) inputMap.get("ATTR_EMAIL"));
    solicitante.setTelefono((String) inputMap.get("ATTR_TELEFONO"));
    solicitudInterna.setSolicitante(solicitante);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearSolicitudInterna(Map<String,Object>, Date, Boolean) - end - return value={}",
          solicitudInterna);
    }
    return solicitudInterna;

  }

  private void validarGenerarNuevoExpedienteElectronico(Long idSolicitudExpediente)
      throws GenerarExpedienteException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarGenerarNuevoExpedienteElectronico(idSolicitudExpediente={}) - start",
          idSolicitudExpediente);
    }

    try {
      getExpedienteElectronicoFactory()
          .validarGenerarNuevoExpedienteElectronico(idSolicitudExpediente);
    } catch (Exception e) {
      logger.error("error al generar nuevo expediente", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarGenerarNuevoExpedienteElectronico(Integer) - end");
    }
  }

  @Override
  public void grabarSolicitud(SolicitudExpedienteDTO solicitudExpediente,
      HistorialOperacionDTO historialOperacion, Boolean grabaHistorial) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "grabarSolicitud(solicitudExpediente={}, historialOperacion={}, grabaHistorial={}) - start",
          solicitudExpediente, historialOperacion, grabaHistorial);
    }

    if (!grabaHistorial) {
      SolicitudExpediente solicuEnt = mapper.map(solicitudExpediente, SolicitudExpediente.class);
      solicitudExpedienteDAO.save(solicuEnt);
      solicitudExpediente.setId(solicuEnt.getId());
    }
    if (grabaHistorial) {
      historialOperacionService.guardarOperacion(historialOperacion);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("grabarSolicitud(SolicitudExpediente, HistorialOperacion, Boolean) - end");
    }
  }

  public void modificarSolicitud(SolicitudExpedienteDTO solicitudExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("modificarSolicitud(solicitudExpediente={}) - start", solicitudExpediente);
    }

    SolicitudExpediente solicuEnt = mapper.map(solicitudExpediente, SolicitudExpediente.class);

    solicitudExpedienteDAO.save(solicuEnt);

    if (logger.isDebugEnabled()) {
      logger.debug("modificarSolicitud(SolicitudExpediente) - end");
    }
  }

  public SolicitudExpedienteDTO obtenerSolitudByIdSolicitud(Long idSolicitud) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerSolitudByIdSolicitud(idSolicitud={}) - start", idSolicitud);
    }

    SolicitudExpediente expedienteEnt = solicitudExpedienteDAO.findById(idSolicitud);

    SolicitudExpedienteDTO returnSolicitudExpediente = mapper.map(expedienteEnt,
        SolicitudExpedienteDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerSolitudByIdSolicitud(Integer) - end - return value={}",
          returnSolicitudExpediente);
    }
    return returnSolicitudExpediente;
  }

  public void grabarSolicitudPorCaratulacionInternaoExterna(
      SolicitudExpedienteDTO solicitudExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("grabarSolicitudPorCaratulacionInternaoExterna(solicitudExpediente={}) - start",
          solicitudExpediente);
    }

    SolicitudExpediente solicEnty = mapper.map(solicitudExpediente, SolicitudExpediente.class);

    solicitudExpedienteDAO.save(solicEnty);

    if (logger.isDebugEnabled()) {
      logger.debug("grabarSolicitudPorCaratulacionInternaoExterna(SolicitudExpediente) - end");
    }
  }

  public ExpedienteElectronicoFactory getExpedienteElectronicoFactory() {
    if (logger.isDebugEnabled()) {
      logger.debug("getExpedienteElectronicoFactory() - start");
    }

    if (expedienteElectronicoFactory == null) {
      this.expedienteElectronicoFactory = ExpedienteElectronicoFactory.getInstance();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getExpedienteElectronicoFactory() - end - return value={}",
          this.expedienteElectronicoFactory);
    }
    return this.expedienteElectronicoFactory;
  }

  public void setExpedienteElectronicoFactory(
      ExpedienteElectronicoFactory expedienteElectronicoFactory) {
    this.expedienteElectronicoFactory = expedienteElectronicoFactory;
  }

}
