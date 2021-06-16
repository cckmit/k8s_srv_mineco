package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoNombramientoService;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoConSuspensionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TomaVistaDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoConSuspensionService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.INotificacionEEService;
import com.egoveris.te.base.service.iface.ITomaVistaService;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
//TODO Se comenta en la clase lo faltante de vuc
//import com.egoveris.vuc.external.service.client.service.external.INotificacionService;
//import com.egoveris.vuc.external.service.client.service.external.bean.DocumentoRequest;
//import com.egoveris.vuc.external.service.client.service.external.exception.ExpedienteInexistenteException;
import com.egoveris.te.model.exception.TomaVistaException;

@Service
@Transactional
public class TomaVistaServiceImpl implements ITomaVistaService, ApplicationContextAware {

  private final static Logger logger = LoggerFactory.getLogger(TomaVistaServiceImpl.class);

  private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
  private static final String REFERENCIA_TOMAVISTA_RECHAZO = "Rechazo de la toma vista del Expediente";
  private static final String REFERENCIA_TOMAVISTA_ACEPTACION = "Aceptación de la toma vista del Expediente";
  private static final String ACEPTACION_TOMAVISTA_MOTIVO = "Concédase la vista solicitada, quedando suspendidos los plazos por el término de";
  private static final String ACEPTACION_TOMAVISTA_MOTIVO_2 = " días";
  private static final String RECHAZO_TOMAVISTA_MOTIVO = "Recházase la solicitud de toma de vista en razón de: ";

  @Autowired
  private AppProperty dBProperty;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private IExternalGenerarDocumentoNombramientoService documentoGedoService;
  @Autowired
  private ExpedienteElectronicoConSuspensionService eeConSuspensionService;
  @Autowired
  private INotificacionEEService notificacionEEService;
  @Autowired
  private PaseExpedienteService paseExpedienteService;
  @Autowired
  ProcessEngine processEngine;
  private Map<String, String> detalles;
  private String estadoAnterior;
  @Autowired
  private String reparticionGT;

  @Override
  public void aceptarTomadeVista(TomaVistaDTO tomaVistaDTO) throws TomaVistaException {
    if (logger.isDebugEnabled()) {
      logger.debug("aceptarTomadeVista(tomaVistaDTO={}) - start", tomaVistaDTO);
    }

    ExpedienteElectronicoDTO ee = null;
    ExpedienteAsociadoEntDTO expedienteAsociado = null;
    ExpedienteElectronicoConSuspensionDTO eeConSuspension = null;

    try {

      this.validarDatos(tomaVistaDTO, Boolean.FALSE);

      ExpedienteElectronicoDTO expedienteTV = this.expedienteElectronicoService
          .buscarExpedienteElectronico(tomaVistaDTO.getId());

      if (logger.isInfoEnabled()) {
        logger.info(
            "Proceso aceptar toma vista del expediente: " + expedienteTV.getCodigoCaratula());
      } else {
        logger.debug(
            "Proceso aceptar toma vista del expediente: " + expedienteTV.getCodigoCaratula());
      }

      if (null != expedienteTV.getListaExpedientesAsociados()
          && !CollectionUtils.isEmpty(expedienteTV.getListaExpedientesAsociados())) {
        expedienteAsociado = expedienteTV.getListaExpedientesAsociados().get(0);

        if (logger.isInfoEnabled()) {
          logger.info("Expediente asociado: " + expedienteAsociado.getAsNumeroSade());
        } else {
          logger.debug("Expediente asociado: " + expedienteAsociado.getAsNumeroSade());
        }

      } else {
        throw new NegocioException("No se encontro un expediente con los datos brindados", null);
      }

      ee = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(expedienteAsociado.getAsNumeroSade());

      eeConSuspension = new ExpedienteElectronicoConSuspensionDTO();
      eeConSuspension.setEe(ee);
      eeConSuspension.setUsuarioSuspension(tomaVistaDTO.getUsuario());
      eeConSuspension.setFechaSuspension(tomaVistaDTO.getFechaFinSusp());
      eeConSuspension.setCodigoCaratula(ee.getCodigoCaratula());

      try {
        logger.info("Notificando la aceptacion de la toma vista con suspension del expediente:"
            + ee.getCodigoCaratula());

        List<DocumentoDTO> docs = new ArrayList<>();
        notificacionEEService.altaNotificacionVUC(tomaVistaDTO.getUsuario(), ee, docs,
            REFERENCIA_TOMAVISTA_ACEPTACION);

      } catch (Exception e) {
        logger.error("aceptarTomadeVista(TomaVistaDTO)", e);

        throw new TomaVistaException(
            "Ha ocurrido un error de comunicación con servicios de TAD, por favor contacte a su Administrador",
            e);
      }

      paseAGuardaTemporal(tomaVistaDTO, expedienteTV);

      this.eeConSuspensionService.actualizarExpedienteElectronicoTV(eeConSuspension);

    } catch (ParametroIncorrectoException e) {
      logger.error(e.getMessage(), e);
      throw new TomaVistaException(e.getMessage(), e);
    } catch (TomaVistaException tvExc) {
      logger.error(tvExc.getMessage(), tvExc);
      throw new TomaVistaException(tvExc.getMessage(), tvExc);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new TomaVistaException(e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("aceptarTomadeVista(TomaVistaDTO) - end");
    }
  }

  @Override
  public void rechazarTomadeVista(TomaVistaDTO tomaVistaDTO) throws TomaVistaException {
    if (logger.isDebugEnabled()) {
      logger.debug("rechazarTomadeVista(tomaVistaDTO={}) - start", tomaVistaDTO);
    }

    ExpedienteElectronicoDTO ee = null;
    ExpedienteAsociadoEntDTO expedienteAsociado = null;
    ExpedienteElectronicoConSuspensionDTO eeConSuspension = null;

    try {

      this.validarDatos(tomaVistaDTO, Boolean.TRUE);

      ExpedienteElectronicoDTO expedienteTV = this.expedienteElectronicoService
          .buscarExpedienteElectronico(tomaVistaDTO.getId());

      if (logger.isInfoEnabled()) {
        logger.info(
            "Proceso aceptar toma vista del expediente: " + expedienteTV.getCodigoCaratula());
      } else {
        logger.debug(
            "Proceso aceptar toma vista del expediente: " + expedienteTV.getCodigoCaratula());
      }

      if (null != expedienteTV.getListaExpedientesAsociados()
          && !CollectionUtils.isEmpty(expedienteTV.getListaExpedientesAsociados())) {
        expedienteAsociado = expedienteTV.getListaExpedientesAsociados().get(0);
      } else {
        throw new NegocioException("No se encontro un expediente con los datos brindados", null);
      }
      ee = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(expedienteAsociado.getAsNumeroSade());

      eeConSuspension = new ExpedienteElectronicoConSuspensionDTO();
      eeConSuspension.setEe(ee);

      try {
        logger.info(
            "Notificando la Toma Vista con suspencion del expediente: " + ee.getCodigoCaratula());

        List<DocumentoDTO> docs = new ArrayList<>();
        notificacionEEService.altaNotificacionVUC(tomaVistaDTO.getUsuario(), ee, docs,
            REFERENCIA_TOMAVISTA_RECHAZO);
      } catch (Exception e) {
        logger.error("rechazarTomadeVista(TomaVistaDTO)", e);

        throw new TomaVistaException(
            "Ha ocurrido un error de comunicación con servicios de TAD, por favor contacte a su Administrador",
            e);
      }

      paseAGuardaTemporal(tomaVistaDTO, expedienteTV);

      this.eeConSuspensionService.eliminarEEConSuspension(eeConSuspension);

    } catch (ParametroIncorrectoException e) {
      logger.error(e.getMessage(), e);
      throw new TomaVistaException(e.getMessage(), e);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new TomaVistaException(e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("rechazarTomadeVista(TomaVistaDTO) - end");
    }
  }

  private void paseAGuardaTemporal(TomaVistaDTO tomaVistaDTO,
      ExpedienteElectronicoDTO expedienteTV) throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("paseAGuardaTemporal(tomaVistaDTO={}, expedienteTV={}) - start", tomaVistaDTO,
          expedienteTV);
    }

    detalles = new HashMap<>();
    detalles.put("estadoAnterior", expedienteTV.getEstado());
    detalles.put("estadoAnteriorParalelo", null);
    detalles.put("destinatario", "MGEYA");

    Task workingTaskEeTV;
    workingTaskEeTV = this.obtenerWorkingTask(expedienteTV);

    this.paseExpedienteService.paseGuardaTemporal(expedienteTV, workingTaskEeTV,
        tomaVistaDTO.getUsuario(), this.detalles, estadoAnterior, tomaVistaDTO.getMotivo());

    // Avanza la tarea en el workflow
    signalExecution(ESTADO_GUARDA_TEMPORAL, workingTaskEeTV);

    // Vuelve a avanzar la tarea en el workflow para cerrar la misma.
    
    // 11-05-2020: No ejecutar el cierre, o desaparece la tarea
    //processEngine.getExecutionService().signalExecutionById(workingTaskEeTV.getExecutionId(),
    //    "Cierre");

    logger.info(
        "Se ha enviado a guarda temporal el expediente: " + expedienteTV.getCodigoCaratula());
  }

  public void signalExecution(String nameTransition, Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(nameTransition={}, workingTask={}) - start", nameTransition,
          workingTask);
    }

    // PASO TODAS LAS VARIABLES ANTES DE REALIZAR EL SIGNAL
    setearVariablesAlWorkflow(workingTask);

    // Paso a la siguiente Tarea/Estado definida en el Workflow
    processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
        nameTransition);

    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(String, Task) - end");
    }
  }

  private void setearVariablesAlWorkflow(Task workingTask) throws NumberFormatException {
    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(workingTask={}) - start", workingTask);
    }

    Map<String, Object> variables = new HashMap<>();
    variables.put("estadoAnterior", detalles.get("estadoAnterior"));
    variables.put("estadoAnteriorParalelo", detalles.get("estadoAnteriorParalelo"));
    variables.put("grupoSeleccionado", detalles.get("grupoSeleccionado"));
    variables.put("tareaGrupal", detalles.get("tareaGrupal"));
    variables.put("usuarioSeleccionado", detalles.get("usuarioSeleccionado"));
    variables.put("idExpedienteElectronico",
        Integer.parseInt(detalles.get("idExpedienteElectronico")));
    setVariablesWorkFlow(variables, workingTask);

    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(Task) - end");
    }
  }

  public void setVariablesWorkFlow(Map<String, Object> variables, Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug("setVariablesWorkFlow(variables={}, workingTask={}) - start", variables,
          workingTask);
    }

    getWorkFlowService().setVariables(processEngine, workingTask.getExecutionId(), variables);

    if (logger.isDebugEnabled()) {
      logger.debug("setVariablesWorkFlow(Map<String,Object>, Task) - end");
    }
  }

  public Task obtenerWorkingTask(ExpedienteElectronicoDTO expedienteElectronico)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerWorkingTask(expedienteElectronico={}) - start", expedienteElectronico);
    }

    if (expedienteElectronico != null) {
      TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery()
          .executionId(expedienteElectronico.getIdWorkflow());

      Task returnTask = taskQuery.uniqueResult();
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerWorkingTask(ExpedienteElectronico) - end - return value={}",
            returnTask);
      }
      return returnTask;

    } else {
      throw new ParametroIncorrectoException("El expediente electrónico es nulo.", null);
    }
  }

  private boolean validarDatos(TomaVistaDTO tomaVistaDTO, boolean rechazo)
      throws TomaVistaException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarDatos(tomaVistaDTO={}, rechazo={}) - start", tomaVistaDTO, rechazo);
    }

    if (rechazo) {
      if (StringUtils.isBlank(tomaVistaDTO.getMotivo())) {
        throw new TomaVistaException("Debe completar el campo Motivo.", null);
      }
    } else if (StringUtils.isBlank(tomaVistaDTO.getMotivo())
        || null == tomaVistaDTO.getFechaFinSusp()) {
      throw new TomaVistaException("La fecha o el motivo no son válidos", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarDatos(TomaVistaDTO, boolean) - end - return value={}", true);
    }
    return true;
  }

  private ResponseExternalGenerarDocumento generarDocumentoEnGedo(
      RequestExternalGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoEnGedo(request={}) - start", request);
    }

    ResponseExternalGenerarDocumento response = null;
    try {
      response = this.documentoGedoService.generarDocumentoGEDO(request);

    } catch (Exception e) {
      logger.error("Se produjo un error al generar el documento", e);
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

  public AppProperty getdBProperty() {
    return dBProperty;
  }

  public void setdBProperty(AppProperty dBProperty) {
    this.dBProperty = dBProperty;
  }

  public void setDetalles(Map<String, String> detalles) {
    this.detalles = detalles;
  }

  public Map<String, String> getDetalles() {
    return detalles;
  }

  public void setEstadoAnterior(String estadoAnterior) {
    this.estadoAnterior = estadoAnterior;
  }

  public String getEstadoAnterior() {
    return estadoAnterior;
  }

  public static WorkFlowService getWorkFlowService() {
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - start");
    }

    WorkFlowService returnWorkFlowService = (WorkFlowService) getService("workFlowService");
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - end - return value={}", returnWorkFlowService);
    }
    return returnWorkFlowService;
  }

  private static ApplicationContext applicationContext = null;

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    TomaVistaServiceImpl.applicationContext = applicationContext;

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

  public String getReparticionGT() {
    return reparticionGT;
  }

  public void setReparticionGT(String reparticionGT) {
    this.reparticionGT = reparticionGT;
  }

}
