package com.egoveris.te.base.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.TransaccionValidaDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.GenerarCaratulaEnGedoException;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.ValidarTrataException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoDeIdentidadDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IGenerarExpedienteService;
import com.egoveris.te.base.service.iface.ISolrService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ExpedienteElectronicoUtils;
import com.egoveris.te.base.util.ValidacionesUtils;
import com.egoveris.te.base.util.ValidadorDeCuit;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.CaratulacionExpedienteRequest;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

@Service
@Transactional
public class GenerarExpedienteServiceImpl implements IGenerarExpedienteService {
  private static final Logger logger = LoggerFactory.getLogger(GenerarExpedienteServiceImpl.class);

  private static final String INICIAR_EXPEDIENTE = "Iniciar Expediente";
  private static final String INICIACION = "Iniciacion";

  @Autowired
  ValidaUsuarioExpedientesService validacionUsuario;
  @Autowired
  SolicitudExpedienteService solicitudExpedienteService;
  @Autowired
  TipoDocumentoService tipoDocumentoService;
  @Autowired
  ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  TrataService trataService;
  @Autowired
  IExternalGenerarDocumentoService generarDocumentoService;
  @Autowired
  WorkFlowService workflowService;
  @Autowired
  PaseExpedienteService paseExpedienteService;

  @Autowired
  ExternalTransaccionService externalTansaccionServie;

  @Autowired
  UsuariosSADEService usuariosSADEService;

  @Autowired
  TrataReparticionService trataReparticionService;

  @Autowired
  ExternalFormularioService formularioService;

  @Autowired
  ExternalTransaccionService transaccionService;

  @Autowired
  ISolrService solrService;

  @Autowired
  private String reparticionGT;

  @Autowired
  protected ProcessEngine processEngine;

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  @Transactional
  public synchronized String generarExpedienteElectronico(CaratulacionExpedienteRequest request)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarExpedienteElectronico(request={}) - start", request);
    }

    ExpedienteElectronicoDTO expedienteElectronicoDTO;
    try {

      // -----------------------
      // -----------------------
      String username = request.getLoggeduser().toUpperCase();

      Usuario datosUsuario = usuariosSADEService.getDatosUsuario(username);

      validarDatosSolicitud(request);

      Map<String, Object> variables = new HashMap<>();

      TrataDTO trata = trataService.buscarTrataByCodigo(request.getSelectTrataCod());

      verificarTipoCaratulacion(request, trata);

      if (!trataReparticionService.validarPermisoReparticion(trata, null, datosUsuario)) {
        StringBuilder strBuffer = new StringBuilder();
        strBuffer.append(trata.getCodigoTrata());
        strBuffer.append(" - ");
        String ar[] = new String[1];
        ar[0] = datosUsuario.getCodigoReparticion();
        strBuffer.append(Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion", ar));
        throw new ProcesoFallidoException(strBuffer.toString(), null);
      }
      variables.put("inicio", "Caratular");
      variables.put("tareaGrupal", "noEsTareaGrupal");
      variables.put("estado", INICIACION);
      variables.put("usuarioAnterior", username);
      variables.put("usuarioSeleccionado", username);
      variables.put("usuarioCandidato", username);
      variables.put("usuarioAnterior", username);

      // ProcessInstance procInstance =
      // getWorkflowWhatApply(trata.getWorkflow(), variables);
      ProcessInstance processInstance = getWorkflowWhatApply(trata.getWorkflow(), variables);

      String estadoExpediente = INICIACION;
      String estadoVariable = INICIAR_EXPEDIENTE;
      String tipoOperacion = (trata.getWorkflow() != null ? trata.getWorkflow().toUpperCase()
          : "solicitud");

      if (!processInstance.getId().contains("solicitud")) {
        estadoExpediente = nextTaskName(processInstance);
        estadoVariable = (String) processInstance.findActiveActivityNames().toArray()[0];
        variables.put("estado", estadoVariable);
      }
      // -----------------------
      // -----------------------

      SolicitudExpedienteDTO solicitud = armarSolicitudCaratulacion(request);
      // AGREGADO POR EL MERGE (HISTORIALOPERACION)
      HistorialOperacionDTO historialOperacion = new HistorialOperacionDTO();
      historialOperacion.setFechaOperacion(solicitud.getFechaCreacion());
      historialOperacion.setTipoOperacion(tipoOperacion); // modificado por
                                                          // hardcoded
                                                          // "SOLICITUD"
      historialOperacion.setUsuario(solicitud.getUsuarioCreacion());

      solicitudExpedienteService.grabarSolicitud(solicitud, historialOperacion, false);

      historialOperacion.setIdSolicitud(solicitud.getId());

      solicitudExpedienteService.grabarSolicitud(solicitud, historialOperacion, true);

      List<ExpedienteMetadataDTO> listaMetaDatos = armarListaMetaDatos(request);

      Map<String, Object> respuesta;

      respuesta = expedienteElectronicoService.generarExpedienteElectronicoCaratulacionDirecta(
          solicitud, trata, request.getDescripcion(), listaMetaDatos, username,
          request.getMotivo(), estadoExpediente, estadoVariable, true);
      Map<String, String> detalles = new HashMap<>();
      detalles.put(ConstantesWeb.ESTADO, (String) respuesta.get(ConstantesWeb.ESTADO));
      detalles.put(ConstantesWeb.MOTIVO, (String) respuesta.get(ConstantesWeb.MOTIVO));
      detalles.put(ConstantesWeb.DESTINATARIO, (String) respuesta.get(ConstantesWeb.DESTINATARIO));

      expedienteElectronicoDTO = (ExpedienteElectronicoDTO) respuesta.get("expediente");

      detalles.put(ConstantesWeb.REPARTICION_USUARIO, datosUsuario.getCodigoReparticion());
      detalles.put(ConstantesWeb.SECTOR_USUARIO, datosUsuario.getCodigoSectorInterno());

      // Se procede al bloqueo del expediente, por ser creado por un
      // sistema externo con la exception que si viene de la APP no se genera el bloqueo 
      if (request.getTaskApp()) {
    	  expedienteElectronicoDTO.setBloqueado(false);
      } else {
    	  expedienteElectronicoDTO.setBloqueado(true);  
      }
     
      expedienteElectronicoDTO.setSistemaCreador(request.getSistema());
      expedienteElectronicoDTO.setSistemaApoderado(request.getSistema());

      // ...//

      variables.put("idSolicitud", solicitud.getId());
      if (!StringUtils.isEmpty(solicitud.getMotivo())) {
        variables.put("motivo", solicitud.getMotivo());
      } else if (!StringUtils.isEmpty(solicitud.getMotivoExterno())) {
        detalles.put(ConstantesWeb.MOTIVO, solicitud.getMotivoExterno());
        variables.put("motivo", solicitud.getMotivoExterno());
      }

      variables.put("idExpedienteElectronico", expedienteElectronicoDTO.getId());
      variables.put("codigoExpediente", expedienteElectronicoDTO.getCodigoCaratula());
      variables.put("codigoTrata", trata.getCodigoTrata());
      variables.put("descripcion", request.getDescripcion());
      variables.put("tipoDocumento", expedienteElectronicoDTO.getTipoDocumento());

      // getWorkflowWhatApply(trata.getWorkflow());

      getProcessEngine().getExecutionService().setVariables(processInstance.getId(), variables);

      expedienteElectronicoDTO.setIdWorkflow(processInstance.getId());
      
      ExpedienteElectronicoUtils.agregarOquitarSufijoBloqueado(expedienteElectronicoDTO, processEngine);

      // Se realiza el update del Expediente Electronico por que se seteo
      // el campo idWorkflow.

      this.expedienteElectronicoService.grabarExpedienteElectronico(expedienteElectronicoDTO);
      Task wokingTask = processEngine.getTaskService().createTaskQuery()
          .executionId(expedienteElectronicoDTO.getIdWorkflow()).list().get(0);
      this.expedienteElectronicoService.actualizoWorkFlowIdEnCaratulacion(wokingTask,
          expedienteElectronicoDTO.getId());
      this.expedienteElectronicoService.grabarExpedienteElectronico(expedienteElectronicoDTO);
      this.expedienteElectronicoService.guardarDatosEnHistorialOperacionP(expedienteElectronicoDTO,
          username, detalles);

      if (expedienteElectronicoDTO.getEsReservado()) {
        expedienteElectronicoService.actualizarReservaEnLaTramitacion(expedienteElectronicoDTO,
            request.getLoggeduser().toUpperCase());
      }

      // expedienteElectronico.setNumero(numero);
      String returnString = expedienteElectronicoDTO.getCodigoCaratula();
      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarExpedienteElectronico(CaratulacionExpedienteRequest) - end - return value={}",
            returnString);
      }
      return returnString;// Ej."EX-2012-00001477- -MGEYA-MGEYA"

    } catch (ParametroIncorrectoException pi) {
      logger.error("generarExpedienteElectronico(CaratulacionExpedienteRequest)", pi);

      throw new ProcesoFallidoException(pi.getMessage(), pi);
    } catch (RemoteAccessException e) {
      logger.error("generarExpedienteElectronico(CaratulacionExpedienteRequest)", e);

      throw new ParametroIncorrectoException(e.getMessage(), e);
    } catch (Exception e) {
      logger.error("generarExpedienteElectronico(CaratulacionExpedienteRequest)", e);

      throw new ProcesoFallidoException(
          "Error al comunicarse con servicios externos. Error: " + e.getMessage(), e);
    }
  }

  private void verificarTipoCaratulacion(CaratulacionExpedienteRequest request, TrataDTO trata)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("verificarTipoCaratulacion(request={}, trata={}) - start", request, trata);
    }

    if (request.isInterno()) {
      if (!trata.getEsInterno()) {
        throw new ParametroIncorrectoException(
            "La trata ingresada no es válida, ingrese una trata de uso Interno.", null);
      }
    }
    if (request.isExterno()) {
      if (!trata.getEsExterno()) {
        throw new ParametroIncorrectoException(
            "La trata ingresada no es válida, ingrese una trata de uso Externo.", null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("verificarTipoCaratulacion(CaratulacionExpedienteRequest, Trata) - end");
    }
  }

  public ProcessInstance getWorkflowWhatApply(String workflowName, Map<String, Object> variables) {
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkflowWhatApply(workflowName={}, variables={}) - start", workflowName,
          variables);
    }

    String name = String.format("workflow-%s", workflowName);
    ProcessDefinitionQuery query = getProcessEngine().getRepositoryService()
        .createProcessDefinitionQuery();
    query.processDefinitionName(name);
    query.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION);
    List<ProcessDefinition> lst = query.list();

    if (lst != null && !lst.isEmpty()) {
      ProcessDefinition procDef = lst.get(0);
      if (procDef != null) {
        ProcessInstance returnProcessInstance = getProcessEngine().getExecutionService()
            .startProcessInstanceById(procDef.getId(), variables);
        if (logger.isDebugEnabled()) {
          logger.debug("getWorkflowWhatApply(String, Map<String,Object>) - end - return value={}",
              returnProcessInstance);
        }
        return returnProcessInstance;
      }
    }

    ProcessInstance returnProcessInstance = getProcessEngine().getExecutionService()
        .startProcessInstanceByKey("solicitud", variables);
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkflowWhatApply(String, Map<String,Object>) - end - return value={}",
          returnProcessInstance);
    }
    return returnProcessInstance;
  }

  public String nextTaskName(ProcessInstance pi) {
    if (logger.isDebugEnabled()) {
      logger.debug("nextTaskName(pi={}) - start", pi);
    }

    TaskQuery taskQuery = getProcessEngine().getTaskService().createTaskQuery()
        .executionId(pi.getProcessInstance().getId());
    Task taskPepe = taskQuery.uniqueResult();

    final String taskId = taskPepe.getId();
    final String estadoAnterior = (String) pi.findActiveActivityNames().toArray()[0];

    String nextTask = processEngine.execute(new Command<String>() {
      /*
       * (non-Javadoc)
       * 
       * @see org.jbpm.api.cmd.Command#execute(org.jbpm.api.cmd.Environment)
       */
      @Override
      public String execute(Environment environment) throws Exception {
        if (logger.isDebugEnabled()) {
          logger.debug("$Command<String>.execute(environment={}) - start", environment);
        }

        String returnString = getJoinedTask(environment, taskId, estadoAnterior);
        if (logger.isDebugEnabled()) {
          logger.debug("$Command<String>.execute(Environment) - end - return value={}",
              returnString);
        }
        return returnString;
      }
    });

    if (logger.isDebugEnabled()) {
      logger.debug("nextTaskName(ProcessInstance) - end - return value={}", nextTask);
    }
    return nextTask;
  };

  /**
   * Method to quering the workflow tree to search the transition who have an
   * outgoing activity by name
   * 
   * @param environment
   *          JBPM4 Environment
   * @param taskId
   *          String ID of the task
   * @param activityName
   *          String name of the activity to search
   * @return Join name how contain the activity name
   */
  public String getJoinedTask(Environment environment, String taskId, String activityName) {
    if (logger.isDebugEnabled()) {
      logger.debug("getJoinedTask(environment={}, taskId={}, activityName={}) - start",
          environment, taskId, activityName);
    }

    DbSession dbSession = (DbSession) environment.get(DbSession.class);
    TaskImpl task = (TaskImpl) dbSession.get(TaskImpl.class, Long.valueOf(Long.parseLong(taskId)));
    if (task == null) {
      throw new JbpmException("task " + taskId + " doesn't exist");
    }

    ExecutionImpl execution = task.getExecution();
    if (execution != null) {
      ActivityImpl activity = execution.getActivity();

      if (activity != null) {
        List<? extends Transition> outgoingTransitions = activity.getOutgoingTransitions();

        if ((outgoingTransitions != null) && (!(outgoingTransitions.isEmpty()))) {
          for (Transition transition : outgoingTransitions) {
            String destinationName = transition.getDestination().getName();
            String cameFrom = transition.getSource().getName();
            if (cameFrom != null && cameFrom.equalsIgnoreCase(activityName))
              return destinationName;
          }
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getJoinedTask(Environment, String, String) - end - return value={null}");
    }
    return null;
  }

  private List<ExpedienteMetadataDTO> armarListaMetaDatos(CaratulacionExpedienteRequest request) {
    if (logger.isDebugEnabled()) {
      logger.debug("armarListaMetaDatos(request={}) - start", request);
    }

    List<ExpedienteMetadataDTO> listaMetaDatos = new ArrayList<>();
    if (request.getMetadata() != null) {
      for (int i = 0; i < request.getMetadata().size(); i++) {
        ExpedienteMetadataDTO metaDato = new ExpedienteMetadataDTO();
        metaDato.setNombre(request.getMetadata().get(i).getNombreMetadata());
        metaDato.setObligatoriedad(request.getMetadata().get(i).isObligatoriedadMetadata());
        metaDato.setOrden(request.getMetadata().get(i).getOrdenMetadata());
        metaDato.setTipo(request.getMetadata().get(i).getTipoMetadata());
        metaDato.setValor(request.getMetadata().get(i).getValorMetadata());

        listaMetaDatos.add(metaDato);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("armarListaMetaDatos(CaratulacionExpedienteRequest) - end - return value={}",
            listaMetaDatos);
      }
      return listaMetaDatos;
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "armarListaMetaDatos(CaratulacionExpedienteRequest) - end - return value={null}");
      }
      return null;
    }
  }

  private SolicitudExpedienteDTO armarSolicitudCaratulacion(
      CaratulacionExpedienteRequest request) {
    if (logger.isDebugEnabled()) {
      logger.debug("armarSolicitudCaratulacion(request={}) - start", request);
    }

    SolicitudExpedienteDTO solicitudInterna = new SolicitudExpedienteDTO();

    solicitudInterna.setFechaCreacion(new Date());
    solicitudInterna.setUsuarioCreacion(request.getLoggeduser());

    solicitudInterna.setEsSolicitudInterna(request.isInterno());
    solicitudInterna.setMotivo(request.getMotivo());
    solicitudInterna.setMotivoExterno(request.getMotivoExterno());
    if (!StringUtils.isEmpty(request.getMotivo())
        && StringUtils.isEmpty(request.getMotivoExterno())) {
      solicitudInterna.setMotivoExterno(request.getMotivo());
    }

    SolicitanteDTO solicitante = new SolicitanteDTO();
    solicitante.setEmail(request.getEmail());
    solicitante.setTelefono(request.getTelefono());

    if (!request.isInterno()) {
      solicitante.setCuitCuil(request.getCuitCuil());
      solicitante.setDomicilio(request.getDomicilio());
      solicitante.setPiso(request.getPiso());
      solicitante.setDepartamento(request.getDepartamento());
      solicitante.setCodigoPostal(request.getCodigoPostal());
      DocumentoDeIdentidadDTO documento = new DocumentoDeIdentidadDTO();
      documento.setTipoDocumento(request.getTipoDoc());
      documento.setNumeroDocumento(request.getNroDoc());
      solicitante.setDocumento(documento);
      if (!request.isPersona()) {
        solicitante.setRazonSocialSolicitante(request.getRazonSocial());
      } else {
        solicitante.setApellidoSolicitante(request.getApellido());
        solicitante.setNombreSolicitante(request.getNombre());
        solicitante.setSegundoNombreSolicitante(request.getSegundoNombre());
        solicitante.setTercerNombreSolicitante(request.getTercerNombre());
        solicitante.setSegundoApellidoSolicitante(request.getSegundoApellido());
        solicitante.setTercerApellidoSolicitante(request.getTercerApellido());
      }
    }

    solicitudInterna.setSolicitante(solicitante);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarSolicitudCaratulacion(CaratulacionExpedienteRequest) - end - return value={}",
          solicitudInterna);
    }
    return solicitudInterna;
  }

  private void validarDatosSolicitud(CaratulacionExpedienteRequest request)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarDatosSolicitud(request={}) - start", request);
    }

    if (request.getLoggeduser() == null) {
      throw new ParametroIncorrectoException("Debe completar el usuario", null);
    } else {// verificar existencia del usuario en EE
      try {
        if (!validacionUsuario.validaUsuarioExpedientes(request.getLoggeduser())) {
          throw new ParametroIncorrectoException(
              "El usuario otorgado no se corresponde con un usuario válido en EE.", null);
        }
      } catch (InterruptedException e) {
        logger.error("validarDatosSolicitud(CaratulacionExpedienteRequest)", e);

        throw new ParametroIncorrectoException("El nombre de usuario otorgado es incorrecto.",
            null);
      }
    }
    if (request.getSistema() == null) {
      throw new ParametroIncorrectoException("Debe completar el nombre del sistema", null);
    }
    if (request.getSistema().equals(ConstantesWeb.SIGLA_MODULO_ORIGEN)) {
      throw new ParametroIncorrectoException("El nombre del sistema debe ser diferente a EE",
          null);
    }
    if (!request.isInterno() && !request.isExterno()) {
      throw new ParametroIncorrectoException("Debe indicar si es una solicitud interna o externa",
          null);
    }
    if (request.getEmail() == null) {
      request.setEmail("");
    }
    if (request.getApellido() == null || request.getApellido().equals("")) {
      if (request.isExterno() && request.isPersona()) {
        throw new ParametroIncorrectoException("Debe completar el apellido del solicitante", null);
      }
    }
    if (request.getNombre() == null || request.getNombre().equals("")) {
      if (request.isExterno() && request.isPersona()) {
        throw new ParametroIncorrectoException("Debe completar el nombre del solicitante", null);
      }
    }
    if (request.getRazonSocial() == null || request.getRazonSocial().equals("")) {
      if (request.isExterno() && request.isEmpresa()) {
        throw new ParametroIncorrectoException("Debe completar la razón social del solicitante",
            null);
      }
    }

    // PERO NO DEBERIA TENER CARACTERES NO NUMERICOS SEGUN RESTRICCION AL
    // CARATULAR EN EE (SOLO NUMEROS)
    if (request.getTelefono() == null || request.getTelefono().trim().equals("")) {
      request.setTelefono("");

    } else {
      boolean valor = ValidacionesUtils.poseeSoloNumeros(request.getTelefono().trim());
      if (!valor) {
        throw new ParametroIncorrectoException("El formato de telefono es incorrecto.", null);
      }
    }
    if (request.getEmail() == null) {
      request.setEmail("");
    }
    if ((request.getTipoDoc() == null || "".equals(request.getTipoDoc())) && request.isExterno()
        && request.isPersona()) {
      throw new ParametroIncorrectoException("Debe completar el tipo de documento del solicitante",
          null);
    }
    if ((request.getNroDoc() == null || "".equals(request.getNroDoc())) && request.isExterno()
        && request.isPersona()) {
      throw new ParametroIncorrectoException(
          "Debe completar el número de documento del solicitante", null);
    }

    if (request.isEmpresa() && request.isExterno()
        && (request.getNroDoc() == null || "".equals(request.getNroDoc()))
        && (request.getTipoDoc() != null && !"".equals(request.getTipoDoc()))) {
      throw new ParametroIncorrectoException("Debe completar el número de documento", null);
    }
    if (request.isEmpresa() && request.isExterno()
        && (request.getNroDoc() != null && !"".equals(request.getNroDoc()))
        && (request.getTipoDoc() == null || "".equals(request.getTipoDoc()))) {
      throw new ParametroIncorrectoException("Debe completar el tipo de documento", null);
    }
    if (request.getSelectTrataCod() == null) {
      throw new ParametroIncorrectoException("Debe completar la trata", null);
    } else {// verificar que exista la trata seleccionada
      TrataDTO trata = trataService.buscarTrataByCodigo(request.getSelectTrataCod());
      if (trata == null) {
        throw new ParametroIncorrectoException("Debe optar por una trata válida.", null);
      } else {
        if (!trata.getEsAutomatica() || !trata.getEstado().equals(TrataDTO.ACTIVO)) {
          throw new ParametroIncorrectoException("Debe optar por una trata válida.", null);
        }
      }
    }
    if (request.getDescripcion() == null || StringUtils.isBlank(request.getDescripcion())) {
      throw new ParametroIncorrectoException("Debe completar la descripción", null);
    }
    if (request.isExterno()) {
      if (request.getCuitCuil() != null) {
        if (request.getCuitCuil().length() == 0 || request.getCuitCuil().equals("")) {
          throw new ParametroIncorrectoException("Debe completar el número de cuit/cuil", null);
        }
//        ValidadorDeCuit validadorDeCuit = new ValidadorDeCuit();
//        try {
//          validadorDeCuit.validarNumeroDeCuit(request.getCuitCuil());
//
//        } catch (NegocioException e) {
//          logger.error("validarDatosSolicitud(CaratulacionExpedienteRequest)", e);
//
//          throw new WrongValueException(e.getMessage());
//        }
      } else {
        request.setCuitCuil(null);
      }

      if (request.getDomicilio() == null || request.getDomicilio().equals("")) {
        throw new ParametroIncorrectoException("Debe completar el campo domicilio", null);
      }

      if (request.getCodigoPostal() == null || request.getCodigoPostal().equals("")) {
        throw new ParametroIncorrectoException("Debe completar el campo código postal", null);
      }

    }

    // Valido si el usuario esta de licencia
    this.ValidarLicenciaUsuarioOrigen(request.getLoggeduser());

    if (request.isInterno()
        && !usuariosSADEService.usuarioTieneRol(request.getLoggeduser().toUpperCase(),
            ConstantesWeb.ROL_CARAT_SADE_INTERNO)) {
      throw new ParametroIncorrectoException("El usuario: " + request.getLoggeduser().toUpperCase()
          + " no posee el rol necesario para caratular el expediente", null);
    }

    if (request.isExterno()
        && !usuariosSADEService.usuarioTieneRol(request.getLoggeduser().toUpperCase(),
            ConstantesWeb.ROL_CARAT_SADE_EXTERNO)) {
      throw new ParametroIncorrectoException("El usuario: " + request.getLoggeduser().toUpperCase()
          + " no posee el rol necesario para caratular el expediente", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarDatosSolicitud(CaratulacionExpedienteRequest) - end");
    }
  }

  private DocumentoDTO generarDocumentoCaratulaVariable(ExpedienteElectronicoDTO ee,
      Integer idTrans, String acronimoTipoDoc, Map<String, Object> map, TrataDTO trata)
      throws ParametroInvalidoException, CantidadDatosNoSoportadaException,
      ErrorGeneracionDocumentoException, ClavesFaltantesException, DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoCaratulaVariable(ee={}, idTrans={}, acronimoTipoDoc={}, map={}, trata={}) - start",
          ee, idTrans, acronimoTipoDoc, map, trata);
    }

    RequestExternalGenerarDocumento request;
    Integer idTransaccion;
    if (idTrans != null) {
      request = armarRequestParaGenerarCaratulaEnGedo(ee, idTrans, acronimoTipoDoc);
      idTransaccion = idTrans;
    } else {
      TransaccionValidaDTO dto = construirDTO(map, ee.getSistemaCreador(), trata);
      idTransaccion = externalTansaccionServie.grabarTransaccionValida(dto);
      request = armarRequestParaGenerarCaratulaEnGedo(ee, idTransaccion, acronimoTipoDoc);
    }
    ResponseExternalGenerarDocumento response = generarDocumentoService
        .generarDocumentoGEDO(request);

    DocumentoDTO documento = new DocumentoDTO();
    documento.setFechaCreacion(ee.getFechaCreacion());
    documento.setFechaAsociacion(new Date());
    documento.setNombreUsuarioGenerador(ee.getUsuarioCreador());
    documento.setDefinitivo(true);
    documento.setNombreArchivo(response.getNumero() + ".pdf");
    documento.setNumeroSade(response.getNumero());
    documento.setUsuarioAsociador(ee.getUsuarioCreador());
    documento.setMotivo(ConstantesWeb.MOTIVO_CARATULA);
    documento.setTipoDocAcronimo(acronimoTipoDoc);
    documento.setIdTransaccionFC(idTransaccion.longValue());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoCaratulaVariable(ExpedienteElectronico, Integer, String, Map<String,Object>, Trata) - end - return value={}",
          documento);
    }
    return documento;
  }

  private Map<String, String> toMapStringString(Map<String, Object> map) {
    if (logger.isDebugEnabled()) {
      logger.debug("toMapStringString(map={}) - start", map);
    }

    Map<String, String> resultado = new HashMap<>();
    try {
      for (String clave : map.keySet()) {
        Object obj = map.get(clave);
        if (obj == null) {
          throw new TeRuntimeException("No se pueden ingresar claves sin valores", null);
        }
        String value;
        if (obj instanceof ElementNSImpl) {
          ElementNSImpl nsi = (ElementNSImpl) obj;
          value = new String(toByte(nsi.getFirstChild().getNodeValue()), "ISO-8859-1");
        } else {
          value = new String(toByte(obj), "ISO-8859-1");
        }
        resultado.put(clave, value);
      }
    } catch (Exception e) {
      logger.error("toMapStringString(Map<String,Object>)", e);

      throw new TeRuntimeException(e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("toMapStringString(Map<String,Object>) - end - return value={}", resultado);
    }
    return resultado;
  }

  private byte[] toByte(Object obj) {
    if (logger.isDebugEnabled()) {
      logger.debug("toByte(obj={}) - start", obj);
    }

    byte[] bytes;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {

      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(obj);
      oos.flush();
      oos.close();
      bos.close();
      bytes = bos.toByteArray();

      if (logger.isDebugEnabled()) {
        logger.debug("toByte(Object) - end - return value={}", bytes);
      }
      return bytes;
    } catch (IOException ex) {
      logger.error("toByte(Object)", ex);

      throw new TeRuntimeException("Ha ocurrido un error al convertir el valor", null);
    }
  }

  private TransaccionValidaDTO construirDTO(Map<String, Object> map, String sistemaCreador,
      TrataDTO trata) {
    if (logger.isDebugEnabled()) {
      logger.debug("construirDTO(map={}, sistemaCreador={}, trata={}) - start", map,
          sistemaCreador, trata);
    }

    TransaccionValidaDTO dto = new TransaccionValidaDTO();
    dto.setSistemaOrigen(sistemaCreador);
    dto.setMapaValores(toMapStringString(map));
    String nombreFormulario = tipoDocumentoService
        .obtenerTipoDocumento(trata.getAcronimoDocumento()).getIdFormulario();
    dto.setNombreFormulario(nombreFormulario);

    if (logger.isDebugEnabled()) {
      logger.debug("construirDTO(Map<String,Object>, String, Trata) - end - return value={}", dto);
    }
    return dto;
  }

  private RequestExternalGenerarDocumento armarRequestParaGenerarCaratulaEnGedo(
      ExpedienteElectronicoDTO ee, Integer idTrans, String acronimo) throws GenerarCaratulaEnGedoException {
    if (logger.isDebugEnabled()) {
      logger.debug("armarRequestParaGenerarCaratulaEnGedo(ee={}, idTrans={}, acronimo={}) - start",
          ee, idTrans, acronimo);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    request.setReferencia("Carátula Variable " + ee.getCodigoCaratula());
    request.setIdTransaccion(idTrans);
    request.setAcronimoTipoDocumento(acronimo);
    request.setUsuario(ee.getUsuarioCreador());
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarCaratulaEnGedo(ExpedienteElectronico, Integer, String) - end - return value={}",
          request);
    }
    return request;
  }

  public void ValidarLicenciaUsuarioOrigen(String nombre) throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("ValidarLicenciaUsuarioOrigen(nombre={}) - start", nombre);
    }

    String usuarioApoderado = null;

    if (usuariosSADEService.licenciaActiva(nombre)) {
      usuarioApoderado = this.validarLicencia(nombre.toUpperCase());
    }

    if (usuarioApoderado != null) {
      throw new ParametroIncorrectoException(
          "El usuario seleccionado no puede caratular ya que está de licencia.", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("ValidarLicenciaUsuarioOrigen(String) - end");
    }
  }

  private String validarLicencia(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarLicencia(username={}) - start", username);
    }

    String returnString = this.usuariosSADEService.getDatosUsuario(username).getApoderado();
    if (logger.isDebugEnabled()) {
      logger.debug("validarLicencia(String) - end - return value={}", returnString);
    }
    return returnString;
  }

  private void validarTrata(TrataDTO trata, Integer idTransaccion, Map<String, Object> datos)
      throws ValidarTrataException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarTrata(trata={}, idTransaccion={}, datos={}) - start", trata,
          idTransaccion, datos);
    }

    if (trata == null) {
      throw new ParametroIncorrectoException("La  trata es invalida", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarTrata(Trata, Integer, Map<String,Object>) - end");
    }
  }

  // SI FALLA, SE ENVIA EL EE A GT Y SE INFORMA UN ERROR

  @Override
  @Transactional(propagation = Propagation.NEVER) 
  public synchronized String generarExpedienteElectronicoCaratulacion(
      @WebParam(name = "request") CaratulacionExpedienteRequest request)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarExpedienteElectronicoCaratulacion(request={}) - start", request);
    }

    String codCaratula = null;
    try {
      codCaratula = this.generarExpedienteElectronico(request);
    } catch (Exception e) {
      logger.error("generarExpedienteElectronicoCaratulacion(CaratulacionExpedienteRequest)", e);

      throw new TeRuntimeException(e.getMessage(), e);
    }
    ExpedienteElectronicoDTO ee = null;
    TrataDTO trata = trataService.buscarTrataByCodigo(request.getSelectTrataCod());
    try {
      validarTrata(trata, request.getIdTransaccion(), request.getCamposFFCC());
      if (request.getCamposFFCC() != null || request.getIdTransaccion() != null) {
        ee = expedienteElectronicoService.obtenerExpedienteElectronicoPorCodigo(codCaratula);
        DocumentoDTO d = generarDocumentoCaratulaVariable(ee, request.getIdTransaccion(),
            trata.getAcronimoDocumento(), request.getCamposFFCC(), trata);
        TransaccionDTO dto = transaccionService.buscarTransaccionPorUUID(d.getIdTransaccionFC().intValue());
        Map<String, Object> camposFC = obtenerCamposIndexables(dto);
        ee.agregarDocumento(d);
        expedienteElectronicoService.modificarExpedienteElectronico(ee, camposFC);
        if (ee.getEsReservado()) {
          expedienteElectronicoService.actualizarReservaEnLaTramitacion(ee,
              request.getLoggeduser().toUpperCase());
        }
      }
    } catch (Exception e) {
      logger.error("generarExpedienteElectronicoCaratulacion(CaratulacionExpedienteRequest)", e);

      if (ee != null) {
        paseAGuardaTemporal(ee);
      }
      throw new ProcesoFallidoException(
          "Ha ocurrido un error al generar el documento de caratula variable. " + e.getMessage(),
          null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarExpedienteElectronicoCaratulacion(CaratulacionExpedienteRequest) - end - return value={}",
          codCaratula);
    }
    return codCaratula;
  }

  private Map<String, Object> obtenerCamposIndexables(TransaccionDTO dto) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCamposIndexables(dto={}) - start", dto);
    }

    Map<String, Object> map = new HashMap<>();

    for (ValorFormCompDTO campo : dto.getValorFormComps()) {
      if (campo.getRelevanciaBusqueda() > 0) {
        String etiqueta = campo.getEtiqueta();
        Object valor = campo.getValor();
        map.put(etiqueta, valor);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCamposIndexables(TransaccionDTO) - end - return value={}", map);
    }
    return map;
  }

  private void paseAGuardaTemporal(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("paseAGuardaTemporal(expediente={}) - start", expediente);
    }

    try {
      HashMap<String, String> detalles = new HashMap<>();
      detalles.put("estadoAnterior", expediente.getEstado());
      detalles.put("estadoAnteriorParalelo", null);
      detalles.put("destinatario", reparticionGT);

      Task workingTaskEE;
      workingTaskEE = expedienteElectronicoService.obtenerWorkingTask(expediente);

      this.paseExpedienteService.paseGuardaTemporal(expediente, workingTaskEE,
          expediente.getUsuarioCreador(), detalles, expediente.getEstado(),
          "Envío a Guarda Temporal por fallo en la vinculacion del documento con caratula variable.");

      // Avanza la tarea en el workflow
      signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskEE, detalles);

      // Vuelve a avanzar la tarea en el workflow para cerrar la misma.
      
      // 11-05-2020: No ejecutar el cierre, o desaparece la tarea
      //processEngine.getExecutionService().signalExecutionById(workingTaskEE.getExecutionId(),
      //    "Cierre");

    } catch (Exception e) {
      logger.error("paseAGuardaTemporal(ExpedienteElectronico)", e);

      throw new TeRuntimeException(e.getMessage(), null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("paseAGuardaTemporal(ExpedienteElectronico) - end");
    }
  }

  public void signalExecution(String nameTransition, Task workingTask,
      Map<String, String> detalles) {
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

  private void setearVariablesAlWorkflow(Task workingTask, Map<String, String> detalles)
      throws NumberFormatException {
    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(workingTask={}, detalles={}) - start", workingTask,
          detalles);
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
      logger.debug("setearVariablesAlWorkflow(Task, Map<String,String>) - end");
    }
  }

  public void setVariablesWorkFlow(Map<String, Object> variables, Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug("setVariablesWorkFlow(variables={}, workingTask={}) - start", variables,
          workingTask);
    }

    workflowService.setVariables(processEngine, workingTask.getExecutionId(), variables);

    if (logger.isDebugEnabled()) {
      logger.debug("setVariablesWorkFlow(Map<String,Object>, Task) - end");
    }
  }

  public String getReparticionGT() {
    return reparticionGT;
  }

  public void setReparticionGT(String reparticionGT) {
    this.reparticionGT = reparticionGT;
  }

}
