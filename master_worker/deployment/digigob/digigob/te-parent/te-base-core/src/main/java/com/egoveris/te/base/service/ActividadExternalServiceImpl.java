/**
 *
 */
package com.egoveris.te.base.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.exception.external.ActividadException;
import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ParametroActividad;
import com.egoveris.te.base.model.ParametroActividadDTO;
import com.egoveris.te.base.model.TipoActividadDTO;
import com.egoveris.te.base.repository.tipo.IActividadParamRepository;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExternalService;
import com.egoveris.te.base.util.ConstEstadoActividad;
import com.egoveris.te.base.util.ConstParamActividad;
import com.egoveris.te.base.util.ConstTipoActividad;
import com.egoveris.te.base.util.WorkFlow;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.model.ActividadRequest;
import com.egoveris.te.model.model.GuardaTempRequest;
import com.egoveris.te.model.model.ResolucionSubsDocRequest;
import com.egoveris.te.model.model.ResolucionSubsRequest;

@Service
@Transactional
public class ActividadExternalServiceImpl
    implements IActividadExternalService, ApplicationContextAware {

  private static final String ESTADO_GUARDA_TEMP = "Guarda Temporal";
  private static final String ESTADO_TRAMITACION_EN_PARALELO = "Paralelo";
  private static final String USUARIO_GENERICO_SUBSANACION_TAD = "SUBSANACION_TAD";
  private static final String USUARIO_GENERICO_GENERACION_AUDITORIA_ARCH = "CIERRE_ACTIVIDADES_ARCH";

  private static final String USUARIO_GENERICO_GUARDA_TEMP_TAD = "GUARDA_TEMP_TAD";

  private static final String USUARIO_GENERICO_PAGO_SIR = "SOLICITUD_PAGO_SIR";

  private transient static Logger logger = LoggerFactory
      .getLogger(ActividadExternalServiceImpl.class);
  @Autowired
  private static ApplicationContext applicationContext = null;

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public static Object getService(final String serviceName) {
    if (logger.isDebugEnabled()) {
      logger.debug("getService(serviceName={}) - start", serviceName);
    }

    Object returnObject = applicationContext.getBean(serviceName);
    if (logger.isDebugEnabled()) {
      logger.debug("getService(String) - end - return value={}", returnObject);
    }
    return returnObject;
  }

  @Autowired
  private IActividadService actividadService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private AsignacionService asignacionService;
  @Autowired
  private DocumentoGedoService documentoGedoService;
  @Autowired
  private IActividadParamRepository actividadParamService;
  @Autowired
  private ICambiarEstadoExpediente cambiarEstadoExpedie;
  
  private DozerBeanMapper mapper = new DozerBeanMapper();
  @Override
  public int cerrarActividadAuditoriaExpedienteIfceArch(final ActividadRequest request)
      throws ActividadException {
    if (logger.isDebugEnabled()) {
      logger.debug("cerrarActividadAuditoriaExpedienteIfceArch(request={}) - start", request);
    }

    Map<String, ParametroActividadDTO> params;

    final ExpedienteElectronicoDTO expediente = obtenerExpediente(request.getNumeroExpediente());
    final String idObj = expediente.getIdWorkflow();
    final List<ActividadDTO> listActividades = actividadService.buscarActividadesVigentes(idObj);
    boolean encontroAct = false;
    for (final ActividadDTO actividad : listActividades) {
      if (actividad.getEstado().equals(ConstEstadoActividad.ESTADO_ABIERTA)

          && actividad.getTipoActividad().getNombre()
              .equals(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_PAQUETE_ARCHIVO)) {
        actividadService.cerrarActividad(actividad, USUARIO_GENERICO_GENERACION_AUDITORIA_ARCH);
        final ParametroActividadDTO p = new ParametroActividadDTO();
        p.setValor(request.getMotivo());
        params = new HashMap<>();
        params.put("MOTIVO_EE", p);
        actividad.setParametros(params);
        encontroAct = true;
        break;
      }
    }

    if (!encontroAct) {
      throw new ActividadException("No encontro actividad previa en el expediente", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "cerrarActividadAuditoriaExpedienteIfceArch(ActividadRequest) - end - return value={}",
          1);
    }
    return 1;
  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public Long generarActividadGuardaTemporal(final GuardaTempRequest request)
      throws ActividadException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarActividadGuardaTemporal(request={}) - start", request);
    }

    final ExpedienteElectronicoDTO expediente = obtenerExpediente(request.getNumeroExpediente());

    // Id objetivo
    final String idObj = expediente.getIdWorkflow();

    // Fecha actual
    final Calendar cal = Calendar.getInstance();
    final Date date = cal.getTime();

    TipoActividadDTO activGuardaDoc;
    if (request.getTipo() == null) {
      activGuardaDoc = actividadService
          .buscarTipoActividad(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_GUARDA_TEMP);
    } else {
      activGuardaDoc = actividadService.buscarTipoActividad(request.getTipo());
    }

    final ActividadDTO act = new ActividadDTO();

    // Usuario alta generico
    final String usuarioTAD = USUARIO_GENERICO_GUARDA_TEMP_TAD;

    // Estado
    final String abierta = ConstEstadoActividad.ESTADO_ABIERTA;
    ParametroActividadDTO test = new ParametroActividadDTO();
    act.setIdObjetivo(idObj);
    act.setFechaAlta(date);
    act.setTipoActividad(activGuardaDoc);
    act.setParametros(new HashMap<String, ParametroActividadDTO>());
    if (expediente.getTrata() != null) {
    	
    	test.setCampo(ConstParamActividad.PARAM_TRATA);
    	test.setValor(expediente.getTrata().getCodigoTrata());
    	
      act.getParametros().put(ConstParamActividad.PARAM_TRATA, test);
    }
    
    test.setCampo(ConstParamActividad.PARAM_NRO_EXP);
	test.setValor(request.getNumeroExpediente());
    act.getParametros().put(ConstParamActividad.PARAM_NRO_EXP, test);
    
    
    act.getParametros().put(ConstParamActividad.PARAM_MOTIVO_TAD,
        new ParametroActividadDTO(request.getMotivo()));
    act.setUsuarioAlta(usuarioTAD);
    act.setEstado(abierta);

    if (request.getValor() != null) {
      act.getParametros().put(ConstParamActividad.PARAM_PEDIDO_GEDO,
          new ParametroActividadDTO(request.getValor()));
    }
    if (request.getUserMailDestino() != null) {
      act.getParametros().put(ConstParamActividad.PARAM_USER_MAIL,
          new ParametroActividadDTO(request.getUserMailDestino()));
    }

    // alta dao
    Long returnint = actividadService.generarActividad(act);
    if (logger.isDebugEnabled()) {
      logger.debug("generarActividadGuardaTemporal(GuardaTempRequest) - end - return value={}",
          returnint);
    }
    return returnint;
  }

  @Override
  public int generarActividadSIR(final ActividadRequest request) throws ActividadException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarActividadSIR(request={}) - start", request);
    }

    ExpedienteElectronicoDTO expedienteElectronico;
    try {
      expedienteElectronico = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(request.getNumeroExpediente());

      if (request.getDocumentosOficiales().size() == 0) {
        throw new ActividadException(
            "Error al crear actividad, no posee documentos para vincular.", null);
      }
      for (final String numeroDocumentoGedo : request.getDocumentosOficiales()) {

        final String expr = numeroDocumentoGedo;
        final String delims = "-"; // so the delimiters are: + - * / ^
        // space
        final String[] tokens = expr.split(delims);

        try {

          if (tokens[3] != null && tokens[3].length() < 3) {
            tokens[3] = "   ";
          }

          final StringBuilder numeroDocumento = new StringBuilder();
          numeroDocumento.append(tokens[0]);
          numeroDocumento.append("-");
          numeroDocumento.append(tokens[1]);
          numeroDocumento.append("-");
          numeroDocumento.append(tokens[2]);
          numeroDocumento.append("-");
          numeroDocumento.append(tokens[3]);
          numeroDocumento.append("-");
          numeroDocumento.append(tokens[4]);

          final DocumentoDTO documento = documentoGedoService
              .obtenerDocumentoPorNumeroEstandar(numeroDocumento.toString());

          if (documento == null) {
            throw new ActividadException(
                "No se pudo obtener el documento " + numeroDocumento.toString(), null);
          }

          for (final DocumentoDTO doc : expedienteElectronico.getDocumentos()) {
            if (doc.getNumeroSade().equals(documento.getNumeroSade())) {
              throw new ActividadException("El documento " + numeroDocumento.toString()
                  + " fue vinculado con anterioridad, por favor verifique.", null);
            }
          }

        } catch (final ActividadException e) {
          throw e;
        } catch (final Exception e) {
          logger.error("generarActividadSIR(ActividadRequest)", e);

          throw new ActividadException(
              "Error al crear actividad SIR, no se puedo vincular el documento: "
                  + numeroDocumentoGedo,
              e);
        }
      }

      final WorkFlow workFlow = getWorkFlowService().createWorkFlow(expedienteElectronico.getId());

      asignacionService.asignarExpedienteBandeja(expedienteElectronico, workFlow);

    } catch (final ActividadException e) {
      throw e;
    } catch (final ParametroIncorrectoException e) {
      logger.error("generarActividadSIR(ActividadRequest)", e);

      throw new ActividadException(
          "Error al obtener el expediente " + request.getNumeroExpediente(), e);
    }

    // Valido que exista el numero de expediente
    final ExpedienteElectronicoDTO expediente = obtenerExpediente(request.getNumeroExpediente());

    final String idObj = expediente.getIdWorkflow();
    // creo la actividad

    final TipoActividadDTO activSub = actividadService
        .buscarTipoActividad(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_PAGO_SIR);

    final ActividadDTO act = new ActividadDTO();

    // Usuario alta generico
    final String usuarioSIR = USUARIO_GENERICO_PAGO_SIR;

    // Estado
    final String abierta = ConstEstadoActividad.ESTADO_ABIERTA;

    // Fecha actual
    final Calendar cal = Calendar.getInstance();
    final Date date = cal.getTime();

    act.setIdObjetivo(idObj);
    act.setFechaAlta(date);
    act.setTipoActividad(activSub);
    act.setParametros(new HashMap<String, ParametroActividadDTO>());
    act.getParametros().put(ConstParamActividad.PARAM_NRO_EXP,
        new ParametroActividadDTO(request.getNumeroExpediente()));

    final String concatenadoDocumento = StringUtils.join(request.getDocumentosOficiales(), ",");

    act.getParametros().put(ConstParamActividad.PARAM_LISTA_NRO_DOC,
        new ParametroActividadDTO(concatenadoDocumento));

    act.getParametros().put(ConstParamActividad.PARAM_MOTIVO_TAD,
        new ParametroActividadDTO(request.getMotivo()));

    if (expediente != null) {
      act.getParametros().put(ConstParamActividad.PARAM_TRATA,
          new ParametroActividadDTO(expediente.getTrata().getCodigoTrata()));
    }

    act.setUsuarioAlta(usuarioSIR);
    act.setEstado(abierta);

    // alta dao
    final Long parentId = actividadService.generarActividad(act);

    int returnint = parentId.intValue();
    if (logger.isDebugEnabled()) {
      logger.debug("generarActividadSIR(ActividadRequest) - end - return value={}", returnint);
    }
    return returnint;
  }

  @Override
  @Transactional
  public void generarActividadSubsanacion(final ResolucionSubsRequest request)
      throws ActividadException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarActividadSubsanacion(request={}) - start", request);
    }

    if (request.getListDocAct() == null || request.getListDocAct().isEmpty()) {
      throw new ActividadException("La lista de documentos no puede estar vacia", null);
    }

    // Id objetivo
    final ExpedienteElectronicoDTO expediente = obtenerExpediente(request.getNumeroExpediente());
    final String idObj = expediente.getIdWorkflow();

    // Fecha actual
    final Calendar cal = Calendar.getInstance();
    final Date date = cal.getTime();

    final List<ActividadDTO> listActividades = actividadService.buscarActividadesVigentes(idObj);
    boolean encontroAct = false;
    for (final ActividadDTO actividad : listActividades) {
      if (actividad.getEstado().equals(ConstEstadoActividad.ESTADO_PENDIENTE)
          && actividad.getTipoActividad().getNombre()
              .equals(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_SUBSANACION)) {
        actividadService.cerrarActividad(actividad, USUARIO_GENERICO_SUBSANACION_TAD);
        encontroAct = true;
        break;
      }
    }

    if (!encontroAct) {
      throw new ActividadException("No encontro actividad previa en el expediente", null);
    }

    final TipoActividadDTO activSub = actividadService
        .buscarTipoActividad(ConstTipoActividad.ACTIVIDAD_TIPO_SUBSANACION);
    final TipoActividadDTO activAprobDoc = actividadService
        .buscarTipoActividad(ConstTipoActividad.ACTIVIDAD_TIPO_APROB_DOC);

    final ActividadDTO act = new ActividadDTO();

    // Usuario alta generico
    final String usuarioTAD = USUARIO_GENERICO_SUBSANACION_TAD;

    // Estado
    final String abierta = ConstEstadoActividad.ESTADO_ABIERTA;

    act.setIdObjetivo(idObj);
    act.setFechaAlta(date);
    act.setTipoActividad(activSub);
    act.setParametros(new HashMap<String, ParametroActividadDTO>());
    act.getParametros().put(ConstParamActividad.PARAM_NRO_EXP,
        new ParametroActividadDTO(request.getNumeroExpediente()));
    act.setUsuarioAlta(usuarioTAD);
    act.setEstado(abierta);
    if (expediente.getTrata() != null) {
      act.getParametros().put(ConstParamActividad.PARAM_TRATA,
          new ParametroActividadDTO(expediente.getTrata().getCodigoTrata()));
    }

    // alta dao
    final Long parentId = actividadService.generarActividad(act);

    // alta de documentos
    ActividadDTO actDoc;
    for (final ResolucionSubsDocRequest docAct : request.getListDocAct()) {
      actDoc = new ActividadDTO();
      actDoc.setIdObjetivo(idObj);
      actDoc.setTipoActividad(activAprobDoc);
      actDoc.setParametros(new HashMap<String, ParametroActividadDTO>());
      actDoc.getParametros().put(ConstParamActividad.PARAM_NRO_DOC,
          new ParametroActividadDTO(docAct.getNumeroDocumento()));
      actDoc.getParametros().put(ConstParamActividad.PARAM_TIPO_DOC_TAD,
          new ParametroActividadDTO(docAct.getTipoDocTad()));
      actDoc.setFechaAlta(date);
      actDoc.setEstado(abierta);
      actDoc.setUsuarioAlta(usuarioTAD);
      actDoc.setParentId(parentId);
      // alta dao actDoc
      actividadService.generarActividad(actDoc);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("generarActividadSubsanacion(ResolucionSubsRequest) - end");
    }
  }

  
  
  @Override
  public void notificarDocumentoGenerado(String indicador, String numero, String executionId, String usuario)
		  throws ActividadException {
	  // Valida si existe proceso en actividad_param
	  if (null != executionId) {
		  ParametroActividad  paramActividad =  actividadParamService.buscarProceso(executionId);
		  if (null != paramActividad) {
			  String idWorkflow = paramActividad.getIdActividad().getIdObjetivo();
			  // search expediente
			  ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
			          .buscarExpedienteElectronicoByIdTask(idWorkflow);
			  // se valida que exista el expedinte
			  if (null != expedienteElectronico) {
				  // se vinculara el documento con el expediente
				  expedienteElectronicoService.vincularDocumentoGEDO_Definitivo(expedienteElectronico, numero, usuario);
				  // se cambia el estado de la actividad 
				  ActividadDTO actividad = new ActividadDTO();
				  TipoActividadDTO tipoAt = new TipoActividadDTO();
				  tipoAt.setId(paramActividad.getIdActividad().getTipoActividad().getId());
				  actividad.setId(paramActividad.getIdActividad().getId());
				  actividad.setTipoActividad(tipoAt);
				  actividad.setFechaAlta(paramActividad.getIdActividad().getFechaAlta());
				  actividad.setUsuarioAlta(paramActividad.getIdActividad().getUsuarioAlta());
				  actividad.setIdObjetivo(paramActividad.getIdActividad().getIdObjetivo());
				  cambiarEstadoExpedie.cambiarEstadoExpediente(actividad);
			  }
		  }
	  }   
  }

  
  
  
  
  
  public IActividadService getActividadService() {
    return actividadService;
  }

  public AsignacionService getAsignacionService() {
    return asignacionService;
  }

  public DocumentoGedoService getDocumentoGedoService() {
    return documentoGedoService;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public WorkFlowService getWorkFlowService() {
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - start");
    }

    WorkFlowService returnWorkFlowService = (WorkFlowService) ActividadExternalServiceImpl
        .getService("workFlowService");
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - end - return value={}", returnWorkFlowService);
    }
    return returnWorkFlowService;
  }

  private ExpedienteElectronicoDTO obtenerExpediente(final String nroExp)
      throws ActividadException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpediente(nroExp={}) - start", nroExp);
    }

    ExpedienteElectronicoDTO expedienteElectronico;
    try {
      expedienteElectronico = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(nroExp);
    } catch (final ParametroIncorrectoException e) {
      logger.error("obtenerExpediente(String)", e);

      throw new ActividadException("Error al obtener el expediente " + nroExp, e);
    }

    if (expedienteElectronico == null) {
      throw new ActividadException("No se encuentra el expediente " + nroExp, null);
    } else if (expedienteElectronico.getEstado().equals(ESTADO_GUARDA_TEMP)) {
      throw new ActividadException(
          "El expediente " + nroExp + " se encuentra en estado guarda temporal", null);
    } else if (expedienteElectronico.getEstado().equals(ESTADO_TRAMITACION_EN_PARALELO)) {
      throw new ActividadException(
          "El expediente " + nroExp + " se encuentra en estado tramitacion en paralelo", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpediente(String) - end - return value={}", expedienteElectronico);
    }
    return expedienteElectronico;
  }

  public void setActividadService(final IActividadService actividadService) {
    this.actividadService = actividadService;
  }

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext)
      throws BeansException {
    ActividadExternalServiceImpl.applicationContext = applicationContext;

  }

  public void setAsignacionService(final AsignacionService asignacionService) {
    this.asignacionService = asignacionService;
  }

  public void setDocumentoGedoService(final DocumentoGedoService documentoGedoService) {
    this.documentoGedoService = documentoGedoService;
  }

  public void setExpedienteElectronicoService(
      final ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

 
}
