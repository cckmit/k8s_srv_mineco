package com.egoveris.te.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.core.util.ApplicationContextUtil;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.SolicitudExpediente;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;

@Service
@Transactional
public class ExpedienteElectronicoFactory implements ApplicationContextAware {

  private static transient Logger logger = LoggerFactory
      .getLogger(ExpedienteElectronicoFactory.class);

  private static final int BPM_MAX_RETRY_COUNTER = 3;

  private static ExpedienteElectronicoFactory instance = null;
  
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  
  private static ApplicationContext applicationContext = null;
  
  public static ExpedienteElectronicoFactory getInstance() {
    if (logger.isDebugEnabled()) {
      logger.debug("getInstance() - start");
    }
    
    if (instance == null) {
      instance = new ExpedienteElectronicoFactory();
      
      // Como esta clase es Singleton, es probable que el @Autowired no funcione, por lo tanto
      // se obtienen las instancias de service con SpringUtil de ZK
      instance.expedienteElectronicoService = (ExpedienteElectronicoService) ApplicationContextUtil.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
      instance.usuariosSADEService = (UsuariosSADEService) ApplicationContextUtil.getBean(ConstantesServicios.USUARIOS_SADE_SERVICE);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getInstance() - end - return value={}", instance);
    }
    
    return instance;
  }
  
  /**
   * Se crea un <code>ExpedienteElectronico</code> con
   * <code>WorkFlowState.INICIACION</code> a partir de una
   * <code>SolicitudElectronico</code>
   * 
   * @param <code>org.jbpm.api.ProcessEngine</code>processEngine
   * @param <code>SolicitudExpediente</code>solicitudExpediente
   * @param <code>Trata</code>norma
   * @param <code>java.lang.String</code>descripcion
   * @param <code>List<ExpedienteMetadata></code>expedienteMetadata
   * @param <code>java.lang.String</code>username
   * @param <code>java.lang.String</code>motivoExpediente
   * @return <code>ExpedienteElectronico</code> con estado
   * @throws <code>Exception</code>exception
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public ExpedienteElectronicoDTO crearExpedienteElectronico(
      org.jbpm.api.ProcessEngine processEngine, final SolicitudExpedienteDTO solicitud,
      final TrataDTO trata, final String descripcion,
      final List<ExpedienteMetadataDTO> expedienteMetadata, final String username,
      final String motivoExpediente) throws Exception {
	
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronico(processEngine={}, solicitudExpediente={}, norma={}, descripcion={}, expedienteMetadata={}, username={}, motivoExpediente={}) - start",
          processEngine, solicitud, trata, descripcion, expedienteMetadata, username,
          motivoExpediente);
    }
    
    if (processEngine == null) {
    	processEngine = getWorkFlowService().getProcessEngine();
    }
    
    ExpedienteElectronicoDTO expedienteElectronico;
    Map<String, Object> variables;
    Map<String, Object> respuesta;
    
    // 1.- GENERAR EE - OBTENER Y PROCESAR RESPUESTA
    respuesta = this.expedienteElectronicoService.generarExpedienteElectronicoCaratulacionDirecta(
        solicitud, trata, descripcion, expedienteMetadata, username, motivoExpediente, true);

    Map<String, String> detalles = new HashMap<>();
    detalles.put(ConstantesWeb.ESTADO, (String) respuesta.get(ConstantesWeb.ESTADO));
    detalles.put(ConstantesWeb.MOTIVO, (String) respuesta.get(ConstantesWeb.MOTIVO));
    detalles.put(ConstantesWeb.DESTINATARIO, (String) respuesta.get(ConstantesWeb.DESTINATARIO));
    
    expedienteElectronico = (ExpedienteElectronicoDTO) respuesta.get("expediente");

    // 2.- CREAR VARIABLES
    variables = crearVariables(expedienteElectronico, solicitud, trata, descripcion, username,
        motivoExpediente);
    
    // 3.- GUARDAR EE [1] - OBTENER ID
    this.expedienteElectronicoService.grabarExpedienteElectronico(expedienteElectronico);
    variables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO, expedienteElectronico.getId());
    
    // 4.- JBPM
    ProcessInstance processInstance = getWorkFlowService().startWorkFlowAndReturnInstance(processEngine, trata.getWorkflow(), variables);
    String idProcessInstance = processInstance.getId();
    
    // ----------------------------------------------------
    // ---- reintentos de obtener una instancia de BPM ----
    // ----------------------------------------------------
    int tryCounter = BPM_MAX_RETRY_COUNTER;
    String nombreWorkflow = trata.getWorkflow();
    
    while (!idProcessInstance.contains(nombreWorkflow) && tryCounter >= 0) {
      logger.info(String.format("ProcessInstance Error (%s - %d): %s --> %s ",
          expedienteElectronico.getCodigoCaratula(), tryCounter, nombreWorkflow,
          idProcessInstance));
      processInstance = getWorkFlowService().startWorkFlowAndReturnInstance(processEngine, trata.getWorkflow(), variables);
      idProcessInstance = processInstance.getId();
      tryCounter--;
    }
    
    if (tryCounter < 0) {
      String errorMsg = String.format("(%s) Error al obtener instancia workflow '%s'",
          expedienteElectronico.getCodigoCaratula(), nombreWorkflow);
      logger.info(errorMsg);
      throw new TeException(errorMsg, null);
    }
    // ----------------------------------------------------
    // ----------------------------------------------------
    // ----------------------------------------------------
    
    // 5.- GUARDAR EE [2] - ID WORKFLOW Y ESTADO
    ExecutionImpl execution = (ExecutionImpl) processInstance;
    String stateName = execution.getActivityName();
    
    expedienteElectronico.setIdWorkflow(idProcessInstance);
    expedienteElectronico.setEstado(stateName);
    this.expedienteElectronicoService.grabarExpedienteElectronico(expedienteElectronico);
    
    // TODO: revisar, aunque parece estar de mas
    
    // --- actualizo la info de las variables por la caratulacion interna ---
    //
    
    /*
    List<Task> tasks = processEngine.getTaskService().createTaskQuery()
    		.executionId(expedienteElectronico.getIdWorkflow()).list();
    
    if (!tasks.isEmpty()) {
	    this.expedienteElectronicoService.actualizoWorkFlowIdEnCaratulacion(tasks.get(0),
	        expedienteElectronico.getId());
	    
	    this.expedienteElectronicoService.grabarExpedienteElectronico(expedienteElectronico);
    }
    */
    
    Usuario datosUsuario = usuariosSADEService.getDatosUsuario(username);
    
    detalles.put(ConstantesWeb.ESTADO, stateName);
    detalles.put(ConstantesWeb.REPARTICION_USUARIO, datosUsuario.getCodigoReparticion());
    detalles.put(ConstantesWeb.SECTOR_USUARIO, datosUsuario.getCodigoSectorInterno());
    
    this.expedienteElectronicoService.guardarDatosEnHistorialOperacionP(expedienteElectronico,
        username, detalles);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronico(org.jbpm.api.ProcessEngine, SolicitudExpediente, Trata, String, List<ExpedienteMetadata>, String, String) - end - return value={}",
          expedienteElectronico);
    }
    
    return expedienteElectronico;
  }

  /**
   * Se valida que no haya ningún
   * <code>javal.lang.Integer</code>isSolicitudExpediente, entre los existentes.
   * 
   * @param <code>javal.lang.Integer</code>idSolicitudExpedienteElectronico
   * @throws <code>Exception</code>
   *           excepcion= Ocurrio un error, ya existe un Expediente Electrónico
   *           con la Solicitud número
   */
  @Transactional
  public void validarGenerarNuevoExpedienteElectronico(Long idSolicitudExpedienteElectronico)
      throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarGenerarNuevoExpedienteElectronico(idSolicitudExpedienteElectronico={}) - start",
          idSolicitudExpedienteElectronico);
    }
    
    if (expedienteElectronicoService
            .obtenerSolicitudExpediente(idSolicitudExpedienteElectronico) != null) {
      throw new TeException(
          "Ocurrío un error, ya existe un Expediente Electrónico con la Solicitud número="
              + idSolicitudExpedienteElectronico,
          null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarGenerarNuevoExpedienteElectronico(Integer) - end");
    }
  }

  /**
   * Crea las variables <code>WorkFlow</code> con los parámetros
   * 
   * @param <code>ExpedienteElectronico</code>expedienteExpediente
   * @param <code>SolicitudExpediente</code>solicitudExpediente
   * @param <code>Trata</code>norma
   * @param <code>java.lang.String</code>descripcion
   * @param <code>java.lang.String</code>username
   * @param <code>java.lang.String</code>motivoExpediente
   * @return La estructura que devuelve es
   *         <code>Map<String, Object></code>outputMap
   *         <ul>
   *         <li>ID_SOLUCITUD</li>
   *         <li>ID_EXPEDIENTE_ELECTRONICO</li>
   *         <li>USUARIO_ANTERIOR</li>
   *         <li>MOTIVO</li>
   *         <li>USUARIO_SELECCIONADO</li>
   *         <li>INICIO</li>
   *         <li>USUARIO_SELECCIONADO</li>
   *         <li>CODIGO_EXPEDIENTE</li>
   *         <li>USUARIO_CANDIDATO</li>
   *         <li>CODIGO_TRATA</li>
   *         <li>DESCRIPCION</li>
   *         <li>USUARIO_ANTERIOR</li>
   *         <li>TAREA_GRUPAL</li>
   *         <li>ESTADO</li>
   *         </ul>
   */
  private static Map<String, Object> crearVariables(
      final ExpedienteElectronicoDTO expedienteElectronico, final SolicitudExpedienteDTO solicitud,
      final TrataDTO trata, final String descripcion, final String username,
      final String motivoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearVariables(expedienteElectronico={}, solicitudExpediente={}, norma={}, descripcion={}, username={}, motivoExpediente={}) - start",
          expedienteElectronico, solicitud, trata, descripcion, username, motivoExpediente);
    }

    Map<String, Object> variables = new HashMap<>();
    variables.put(ConstantesWeb.ID_SOLUCITUD, solicitud.getId());
    variables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO, expedienteElectronico.getId());
    variables.put(ConstantesWeb.USUARIO_ANTERIOR, username);
    variables.put(ConstantesWeb.MOTIVO, motivoExpediente);
    variables.put(ConstantesWeb.USUARIO_SELECCIONADO, username);
    variables.put(ConstantesWeb.INICIO, "Caratular");
    variables.put(ConstantesWeb.CODIGO_EXPEDIENTE, expedienteElectronico.getCodigoCaratula());
    variables.put(ConstantesWeb.USUARIO_CANDIDATO, username);
    variables.put(ConstantesWeb.CODIGO_TRATA, trata.getCodigoTrata());
    variables.put(ConstantesWeb.DESCRIPCION, descripcion);
    variables.put(ConstantesWeb.USUARIO_ANTERIOR, username);
    variables.put(ConstantesWeb.TAREA_GRUPAL, "noEsTareaGrupal");
    variables.put(ConstantesWeb.ESTADO, "Iniciacion");

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearVariables(ExpedienteElectronico, SolicitudExpediente, Trata, String, String, String) - end - return value={}",
          variables);
    }
    
    return variables;
  }

  @SuppressWarnings("unused")
  private static Map<String, Object> setearIDExpedienteVariables(
      final ExpedienteElectronicoDTO expedienteElectronico,
      final SolicitudExpediente solicitudExpediente, final TrataDTO norma, final String descripcion,
      final String username, final String motivoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "setearIDExpedienteVariables(expedienteElectronico={}, solicitudExpediente={}, norma={}, descripcion={}, username={}, motivoExpediente={}) - start",
          expedienteElectronico, solicitudExpediente, norma, descripcion, username,
          motivoExpediente);
    }

    Map<String, Object> variables = new HashMap<>();
        variables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO, expedienteElectronico.getId());
        
    if (logger.isDebugEnabled()) {
      logger.debug(
          "setearIDExpedienteVariables(ExpedienteElectronico, SolicitudExpediente, Trata, String, String, String) - end - return value={}",
          variables);
    }
    
    return variables;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public WorkFlowService getWorkFlowService() {
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - start");
    }

    WorkFlowService returnWorkFlowService = (WorkFlowService) getService(ConstantesServicios.WORKFLOW_SERVICE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - end - return value={}", returnWorkFlowService);
    }
    
    return returnWorkFlowService;
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ExpedienteElectronicoFactory.applicationContext = applicationContext;

  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public static Object getService(String serviceName) {
    if (logger.isDebugEnabled()) {
      logger.debug("getService(serviceName={}) - start", serviceName);
    }

    Object returnObject = applicationContext.getBean(serviceName);
    
    if (logger.isDebugEnabled()) {
      logger.debug("getService(String) - end - return value={}", returnObject);
    }
    
    return returnObject;
  }
  
}
