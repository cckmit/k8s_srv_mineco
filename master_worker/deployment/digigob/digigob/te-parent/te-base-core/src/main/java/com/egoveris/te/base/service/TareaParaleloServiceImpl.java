package com.egoveris.te.base.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessEngine;
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
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TareaParalelo;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TareaParaleloInbox;
import com.egoveris.te.base.repository.TareaParaleloRepository;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;

@Service
@Transactional
public class TareaParaleloServiceImpl implements TareaParaleloService {
  private static final Logger logger = LoggerFactory.getLogger(TareaParaleloServiceImpl.class);

  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private TareaParaleloRepository tareaParaleloDAO;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private UsuariosSADEService usuarioSadeService;
  private DozerBeanMapper mapper = new DozerBeanMapper();

  public void guardar(TareaParaleloDTO tareaParaleloDto) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardar(tareaParalelo={}) - start", tareaParaleloDto);
    }

    TareaParalelo tareaParalelo = mapper.map(tareaParaleloDto, TareaParalelo.class);
    this.tareaParaleloDAO.save(tareaParalelo);

    if (logger.isDebugEnabled()) {
      logger.debug("guardar(TareaParalelo) - end");
    }
  }

  public void modificar(TareaParaleloDTO tareaParaleloDto) {
    if (logger.isDebugEnabled()) {
      logger.debug("modificar(tareaParalelo={}) - start", tareaParaleloDto);
    }

    TareaParalelo tareaParalelo = mapper.map(tareaParaleloDto, TareaParalelo.class);
    this.tareaParaleloDAO.save(tareaParalelo);

    if (logger.isDebugEnabled()) {
      logger.debug("modificar(TareaParalelo) - end");
    }
  }

  public List<TareaParaleloInbox> buscarTareasEnParaleloByUsername(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasEnParaleloByUsername(username={}) - start", username);
    }

    List<TareaParalelo> listaTask = this.tareaParaleloDAO
        .buscarTareasEnParaleloByUsername(username);

    List<TareaParaleloInbox> listaInbox = ListMapper.mapList(listaTask, mapper,
        TareaParaleloInbox.class);
    for (TareaParaleloInbox tareaParaleloInbox : listaInbox) {

      if (tareaParaleloInbox.getIdTask() != null) {
        TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
            .executionId(tareaParaleloInbox.getIdTask());
        Task task = taskQuery.uniqueResult();
        if (task != null) {
          if (task.getAssignee() != null && !task.getAssignee().equals("")) {
            tareaParaleloInbox.setUsuarioGrupoDestinoAsignado(task.getAssignee());
          } else {
            // Si existe una tarea pero no tiene assignee significa
            // que la tiene la reparticion y nadie se la adquirió.
            tareaParaleloInbox
                .setUsuarioGrupoDestinoAsignado(tareaParaleloInbox.getUsuarioGrupoDestino());
          }
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasEnParaleloByUsername(String) - end - return value={}", listaTask);
    }
    List<TareaParaleloInbox> listaSalida = ListMapper.mapList(listaInbox, mapper,
        TareaParaleloInbox.class);
    return listaSalida;
  }

  public List<TareaParaleloDTO> buscarTareasPendientesByExpediente(Long idExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPendientesByExpediente(idExpediente={}) - start", idExpediente);
    }

    List<TareaParaleloDTO> listaTareasPendientes;

    List<TareaParalelo> tareaEnty = this.tareaParaleloDAO
        .buscarTareasPendientesByExpediente(idExpediente);

    listaTareasPendientes = ListMapper.mapList(tareaEnty, mapper, TareaParaleloDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPendientesByExpediente(int) - end - return value={}",
          listaTareasPendientes);
    }
    return listaTareasPendientes;
  }

  public TareaParaleloDTO buscarTareaEnParaleloByIdTask(String taskId) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareaEnParaleloByIdTask(taskId={}) - start", taskId);
    }
    TareaParaleloDTO tareaParalelo = null;
    TareaParalelo tareaEnt = this.tareaParaleloDAO.findByIdTask(taskId);
    if (tareaEnt != null) {
      tareaParalelo = mapper.map(tareaEnt, TareaParaleloDTO.class);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareaEnParaleloByIdTask(String) - end - return value={}", tareaParalelo);
    }
    return tareaParalelo;
  }

  public void eliminarTareasParaleloByExpediente(Long idExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarTareasParaleloByExpediente(idExpediente={}) - start", idExpediente);
    }

    TareaParalelo tareaParalelo = new TareaParalelo();

    tareaParalelo.setId(idExpediente);

    this.tareaParaleloDAO.delete(tareaParalelo);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarTareasParaleloByExpediente(int) - end");
    }
  }

  public void modificarTareaParalelo(String idTask, String estado) {
    if (logger.isDebugEnabled()) {
      logger.debug("modificarTareaParalelo(idTask={}, estado={}) - start", idTask, estado);
    }

    this.tareaParaleloDAO.modificarTareaParalelo(idTask, estado);

    if (logger.isDebugEnabled()) {
      logger.debug("modificarTareaParalelo(String, String) - end");
    }
  }

  public void modificarMotivoTareaParalelo(String idTask, String motivo) {
    if (logger.isDebugEnabled()) {
      logger.debug("modificarMotivoTareaParalelo(idTask={}, motivo={}) - start", idTask, motivo);
    }

    this.tareaParaleloDAO.modificarMotivoTareaParalelo(idTask, motivo);

    if (logger.isDebugEnabled()) {
      logger.debug("modificarMotivoTareaParalelo(String, String) - end");
    }
  }

  public void modificarMotivoRespuestaTareaParalelo(String idTask, String motivoRespuesta) {
    if (logger.isDebugEnabled()) {
      logger.debug("modificarMotivoRespuestaTareaParalelo(idTask={}, motivoRespuesta={}) - start",
          idTask, motivoRespuesta);
    }

    this.tareaParaleloDAO.modificarMotivoRespuestaTareaParalelo(idTask, motivoRespuesta);

    if (logger.isDebugEnabled()) {
      logger.debug("modificarMotivoRespuestaTareaParalelo(String, String) - end");
    }
  }

  public TareaParaleloDTO buscarTareaEnParaleloByExpedienteYDestino(Long idExpediente,
      String destino) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "buscarTareaEnParaleloByExpedienteYDestino(idExpediente={}, destino={}) - start",
          idExpediente, destino);
    }

    TareaParaleloDTO tareaParalelo;

    TareaParalelo tareaEnty = this.tareaParaleloDAO.findByIdExpAndUsuarioGrupoDestino(idExpediente,
        destino);

    tareaParalelo = mapper.map(tareaEnty, TareaParaleloDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "buscarTareaEnParaleloByExpedienteYDestino(int, String) - end - return value={}",
          tareaParalelo);
    }
    return tareaParalelo;
  }

  public boolean poseeDocumentosOficialesPendientes(TareaParaleloDTO tareaParalelo) {
    if (logger.isDebugEnabled()) {
      logger.debug("poseeDocumentosOficialesPendientes(tareaParalelo={}) - start", tareaParalelo);
    }

    boolean existeUnDocumentoPendiente = false;
    int cont = 0;

    ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
        .buscarExpedienteElectronico(tareaParalelo.getIdExp());
    List<DocumentoDTO> listaDocumentos = expedienteElectronico.getDocumentos();

    while (cont < listaDocumentos.size() && !existeUnDocumentoPendiente) {
      if (listaDocumentos.get(cont).getIdTask() != null
          && listaDocumentos.get(cont).getIdTask().equals(tareaParalelo.getIdTask())) {
        if (!listaDocumentos.get(cont).getDefinitivo()) {
          existeUnDocumentoPendiente = true;
        }
      }
      cont++;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("poseeDocumentosOficialesPendientes(TareaParalelo) - end - return value={}",
          existeUnDocumentoPendiente);
    }
    return existeUnDocumentoPendiente;
  }

  public boolean poseeDocumentosDeTrabajoPendientes(TareaParaleloDTO tareaParalelo) {
    if (logger.isDebugEnabled()) {
      logger.debug("poseeDocumentosDeTrabajoPendientes(tareaParalelo={}) - start", tareaParalelo);
    }

    boolean existeUnArchivoPendiente = false;
    int cont = 0;

    ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
        .buscarExpedienteElectronico(tareaParalelo.getIdExp());

    List<ArchivoDeTrabajoDTO> listaArchivosTrabajo = expedienteElectronico.getArchivosDeTrabajo();

    while (cont < listaArchivosTrabajo.size() && !existeUnArchivoPendiente) {
      if (listaArchivosTrabajo.get(cont).getIdTask() != null
          && listaArchivosTrabajo.get(cont).getIdTask().equals(tareaParalelo.getIdTask())) {
        if (!listaArchivosTrabajo.get(cont).isDefinitivo()) {
          existeUnArchivoPendiente = true;
        }
      }
      cont++;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("poseeDocumentosDeTrabajoPendientes(TareaParalelo) - end - return value={}",
          existeUnArchivoPendiente);
    }
    return existeUnArchivoPendiente;
  }

  public boolean poseeExpedientesAsociadosPendientes(TareaParaleloDTO tareaParalelo) {
    if (logger.isDebugEnabled()) {
      logger.debug("poseeExpedientesAsociadosPendientes(tareaParalelo={}) - start", tareaParalelo);
    }

    boolean existeUnExpedientePendiente = false;
    int cont = 0;

    ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
        .buscarExpedienteElectronico(tareaParalelo.getIdExp());

    List<ExpedienteAsociadoEntDTO> listaExpedienteAsociado = expedienteElectronico
        .getListaExpedientesAsociados();

    while (cont < listaExpedienteAsociado.size() && !existeUnExpedientePendiente) {
      if (listaExpedienteAsociado.get(cont).getIdTask() != null
          && listaExpedienteAsociado.get(cont).getIdTask().equals(tareaParalelo.getIdTask())) {
        if (!listaExpedienteAsociado.get(cont).getDefinitivo()) {
          existeUnExpedientePendiente = true;
        }
      }
      cont++;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("poseeExpedientesAsociadosPendientes(TareaParalelo) - end - return value={}",
          existeUnExpedientePendiente);
    }
    return existeUnExpedientePendiente;
  }

  // @Override
  // public List<TramitacionLibre> findTramitacionLibre(String usuario) {
  // if (logger.isDebugEnabled()) {
  // logger.debug("findTramitacionLibre(usuario={}) - start", usuario);
  // }
  //
  // List<TramitacionLibre> returnList =
  // tareaParaleloDAO.findTramitacionLibre(usuario);
  // if (logger.isDebugEnabled()) {
  // logger.debug("findTramitacionLibre(String) - end - return value={}",
  // returnList);
  // }
  // return returnList;
  // }

  public void integrarTarea(Usuario usuarioProductorInfo, Map<String, String> detalles,
      String usuario, TareaParaleloDTO tareaParalelo, String motivoStr,
      ExpedienteElectronicoDTO expedienteElectronico, Task workingTask, boolean paseComun)
      throws InterruptedException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "integrarTarea(usuarioProductorInfo={}, detalles={}, usuario={}, tareaParalelo={}, motivoStr={}, expedienteElectronico={}, workingTask={}, paseComun={}) - start",
          usuarioProductorInfo, detalles, usuario, tareaParalelo, motivoStr, expedienteElectronico,
          workingTask, paseComun);
    }

    try {
      String proximoEstadoExpediente;

      /**
       * Busco si hay tareas en estado pendiente o adquiridas para este
       * expediente. Si no las hay es por que estan todas devueltas entonces las
       * elimino de mi tabla temporal.
       */
      List<TareaParaleloDTO> listaTareasPendientes = this
          .buscarTareasPendientesByExpediente(expedienteElectronico.getId());

      /**
       * En el caso de que esten todas las tareas salvo una (la actual) en
       * paralelo terminadas (todas en el join) actualizo el estado del
       * expediente para que no quede en estado paralelo sino el estado anterior
       * al paralelo.
       */
      boolean ultimaTarea = listaTareasPendientes.size() == 1
          && listaTareasPendientes.get(0).getId().equals(tareaParalelo.getId());

      if (ultimaTarea) {
        proximoEstadoExpediente = tareaParalelo.getEstadoAnterior();
      } else {
        proximoEstadoExpediente = "Paralelo";
      }

      if (paseComun) {
        this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask,
            expedienteElectronico, usuario, proximoEstadoExpediente, motivoStr, detalles);
      } else {
        Usuario user = usuarioSadeService.getDatosUsuario(usuario);
        this.expedienteElectronicoService.generarPaseExpedienteElectronicoSinDocumentoMigracion(
            workingTask, expedienteElectronico, user, proximoEstadoExpediente, motivoStr,
            detalles);
      }

      setVariableWorkFlow("usuarioSeleccionado", usuarioProductorInfo.getUsername(), workingTask);

      final String taskId = workingTask.getId();
      final String estadoAnterior = tareaParalelo.getEstadoAnterior();

      String signalBy = processEngine.execute(new Command<String>() {
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

      processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
          signalBy);

      /**
       * En el caso de que esten todas las tareas en paralelo terminadas (todas
       * en el join) elimino las tareas en paralelo
       */
      if (ultimaTarea) {
        this.eliminarTareasParaleloByExpediente(expedienteElectronico.getId());
      } else {
        tareaParalelo.setUsuarioGrupoAnterior(usuario);
        tareaParalelo
            .setMotivoRespuesta(this.expedienteElectronicoService.formatoMotivo(motivoStr));
        tareaParalelo.setEstado("Terminado");
        this.modificar(tareaParalelo);
      }

    } catch (Exception e) {
      logger.warn(
          "integrarTarea(Usuario, Map<String,String>, String, TareaParalelo, String, ExpedienteElectronico, Task, boolean) - exception ignored",
          e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "integrarTarea(Usuario, Map<String,String>, String, TareaParalelo, String, ExpedienteElectronico, Task, boolean) - end");
    }
  }

  public void setVariableWorkFlow(String name, Object value, Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug("setVariableWorkFlow(name={}, value={}, workingTask={}) - start", name, value,
          workingTask);
    }

    this.processEngine.getExecutionService().setVariable(workingTask.getExecutionId(), name,
        value);

    if (logger.isDebugEnabled()) {
      logger.debug("setVariableWorkFlow(String, Object, Task) - end");
    }
  }

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

        if (outgoingTransitions != null && !outgoingTransitions.isEmpty()) {
          for (Transition transition : outgoingTransitions) {
            Transition outgoing = transition.getDestination().getDefaultOutgoingTransition();
            if (outgoing != null) {
              String destinationName = outgoing.getDestination().getName();

              if (destinationName != null && destinationName.equalsIgnoreCase(activityName))
                return transition.getName();
            }
          }
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getJoinedTask(Environment, String, String) - end - return value={null}");
    }
    return null;
  }

  public UsuariosSADEService getUsuarioSadeService() {
    return usuarioSadeService;
  }

  public void setUsuarioSadeService(UsuariosSADEService usuarioSadeService) {
    this.usuarioSadeService = usuarioSadeService;
  }



}
