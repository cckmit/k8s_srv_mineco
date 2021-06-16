package com.egoveris.te.base.composer;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessEngine;
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
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IntegrarTareaParalelaComposer extends EnvioExpedienteComposer {

  private static final long serialVersionUID = 6266287038479709703L;
  private static final String JOIN_TRAMITACION = "to joinTramitacion";
  private static final String JOIN_EJECUCION = "to joinEjecucion";
  private static final String ESTADO_PARALELO = "Paralelo";
  private static final String ESTADO_TRAMITACION = "Tramitacion";
  private static final String ESTADO_EJECUCION = "Ejecucion";
  private static Logger logger = LoggerFactory.getLogger(EnvioComposer.class);

  @Autowired
  private Textbox estado;
  @Autowired
  private Textbox usuario;
  private ExpedienteElectronicoDTO expedienteElectronico;
  private Usuario usuarioProductorInfo;
  private Map<String, String> detalles;
  private String loggedUsername;
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;
  protected Task workingTask = null;
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;

  @Autowired
  private DocumentoGedoService documentoGedoService;
  private TareaParaleloDTO tareaParalelo;
  /**
   * Defino los servicios que voy a utilizar
   */
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  @WireVariable(ConstantesServicios.TAREA_PARALELO_SERVICE)
  private TareaParaleloService tareaParaleloService;

  /**
   * Se cargan los datos necesarios y se prepara la pantalla para realizar
   */

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public Textbox getEstado() {
    return estado;
  }

  public void setEstado(Textbox estado) {
    this.estado = estado;
  }

  public void setUsuario(Combobox usuario) {
    this.usuario = usuario;
  }

  public List<Usuario> getUsuarios() {
    return getUsuariosGEDO();
  }

  public ExpedienteElectronicoDTO getExpedienteElectronico() {
    return expedienteElectronico;
  }

  public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
    this.expedienteElectronico = expedienteElectronico;
  }

  /**
   * Según el estado en el que este, cargo la lista de estados con los estados a
   * los que se puede pasar. Esto se logra con el outcomes de jbpm, que devuelve
   * todas las salidas (transiciones) que tiene la tarea acctual
   * 
   * @exception se
   *              utiliza throws Exception por el doAfterCompose de ZK el cual
   *              esta contenido en el GenericForwardComposer
   */
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    component.addEventListener(Events.ON_USER, new IntegrarTareaOnNotifyWindowListener(this));
 
     
    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));
    this.usuario.setReadonly(true);

    this.tareaParalelo = tareaParaleloService
        .buscarTareaEnParaleloByIdTask(this.getWorkingTask().getExecutionId());

    Usuario user = this.usuariosSADEService.getDatosUsuario(this.tareaParalelo.getUsuarioOrigen());

    this.usuario.setValue(user.toString());
    this.usuarioProductorInfo = user;

    this.estado.setValue(this.getWorkingTask().getActivityName());

    this.expedienteElectronico = (ExpedienteElectronicoDTO) Executions.getCurrent().getArg()
        .get("expedienteElectronico");

  }

  public void validarMotivo() {
    if ((this.motivoExpedienteStr == null) || (this.motivoExpedienteStr.trim().equals(""))) {
      throw new WrongValueException(this.motivoExpediente, "Debe ingresar un motivo.");
    } else {
      if (super.expedienteElectronicoService.PasarFormatoStringSinHTML(this.motivoExpedienteStr)
          .length() > 250) {
        throw new WrongValueException(this.motivoExpediente,
            "El motivo ingresado es demasiado largo.");
      }
    }

  }

  /**
   * Al presionar el botón enviar se realiza el PASE ELECTRONICO del Expediente
   * Electrónico.
   */
  public void onEnviar() throws InterruptedException {
    super.definirMotivo();
    this.validarMotivo();
    detalles = new HashMap<String, String>();
    detalles.put("destinatario", usuarioProductorInfo.getUsername());
    setVariableWorkFlow("estadoAnteriorParalelo", ESTADO_PARALELO);
    loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    this.mostrarForegroundBloqueanteToken();
    Events.echoEvent("onUser", this.self, "integrarTarea");

  }

  private void mostrarForegroundBloqueanteToken() {
    Clients.showBusy(Labels.getLabel("ee.tramitacion.enviarExpedienteElectronico.value"));
  }

  /**
   * @param usuarioProductorInfo
   * @param estadoSeleccionado
   * @param detalles
   * @param estadoAnterior
   * @param loggedUsername
   * @param sectorMesaVirtual
   * @throws WrongValueException
   * @throws InterruptedException
   */
  private void integrarTarea(Usuario usuarioProductorInfo, Map<String, String> detalles,
      String loggedUsername, TareaParaleloDTO tareaParalelo)
      throws WrongValueException, InterruptedException {

    try {

      // Mantiene el próximo estado del expediente
      // TODO QUITAR ESTO DE AQUI Y MOVERLO AL SERVICE!!!
      String proximoEstadoExpediente = null;

      /**
       * Busco si hay tareas en estado pendiente o adquiridas para este
       * expediente. Si no las hay es por que estan todas devueltas entonces las
       * elimino de mi tabla temporal.
       **/
      List<TareaParaleloDTO> listaTareasPendientes = this.tareaParaleloService
          .buscarTareasPendientesByExpediente(expedienteElectronico.getId());

      /**
       * En el caso de que esten todas las tareas salvo una (la actual) en
       * paralelo terminadas (todas en el join) actualizo el estado del
       * expediente para que no quede en estado paralelo sino el estado anterior
       * al paralelo.
       **/
      boolean ultimaTarea = listaTareasPendientes.size() == 1
          && listaTareasPendientes.get(0).getId().equals(tareaParalelo.getId());

      if (ultimaTarea) {
        proximoEstadoExpediente = tareaParalelo.getEstadoAnterior();
      } else {
        proximoEstadoExpediente = ESTADO_PARALELO;
      }

      if (proximoEstadoExpediente.equals(ESTADO_PARALELO)) {
        hacerDefinitivosDocumentosVinculadosPorMi();
        this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask,
            this.expedienteElectronico, loggedUsername, proximoEstadoExpediente,
            this.motivoExpedienteStr, detalles, false);
      } else {

        this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask,
            this.expedienteElectronico, loggedUsername, proximoEstadoExpediente,
            this.motivoExpedienteStr, detalles);
      }

      setVariableWorkFlow("usuarioSeleccionado", usuarioProductorInfo.getUsername());

      final String taskId = this.getWorkingTask().getId();
      final String estadoAnterior = tareaParalelo.getEstadoAnterior();

      String signalBy = processEngine.execute(new Command<String>() {
        /*
         * (non-Javadoc)
         * 
         * @see org.jbpm.api.cmd.Command#execute(org.jbpm.api.cmd.Environment)
         */
        @Override
        public String execute(Environment environment) throws Exception {
          return getJoinedTask(environment, taskId, estadoAnterior);
        }
      });

      processEngine.getExecutionService()
          .signalExecutionById(this.getWorkingTask().getExecutionId(), signalBy);


      Messagebox.show(
    		  Labels.getLabel("te.base.composer.enviocomposer.generic.generapaseexpediente") + expedienteElectronico.getCodigoCaratula()
              + Labels.getLabel("te.base.composer.enviocomposer.generic.seenviousuario") + usuarioProductorInfo.getUsername(),
          Labels.getLabel("ee.general.information"), Messagebox.OK,
          Messagebox.INFORMATION);

      /**
       * En el caso de que esten todas las tareas en paralelo terminadas (todas
       * en el join) elimino las tareas en paralelo
       */
      if (ultimaTarea) {
        this.tareaParaleloService
            .eliminarTareasParaleloByExpediente(expedienteElectronico.getId());
      } else {
        tareaParalelo.setUsuarioGrupoAnterior(loggedUsername);
        tareaParalelo.setMotivoRespuesta(
            this.expedienteElectronicoService.formatoMotivo(this.motivoExpedienteStr));
        tareaParalelo.setEstado("Terminado");
        this.tareaParaleloService.modificar(tareaParalelo);
      }

    } catch (Exception e) {
      logger.error("Ocurrió un error al generar el pase.", e);
      Messagebox.show(Labels.getLabel("te.base.composer.enviocomposer.error"),
    		  Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);

    } finally {
      Clients.clearBusy();
      this.closeAssociatedWindow();
    }
  }

  public void hacerDefinitivosDocumentosVinculadosPorMi() {
    this.expedienteElectronico.hacerDefinitivosDocumentosVinculadosPor(this.loggedUsername);
    expedienteElectronico.hacerDefinitivosAsociados();
    this.expedienteElectronico.hacerDefinitivosArchivosDeTranajoVinculadosPor(this.loggedUsername);
    if (expedienteElectronico.getEsReservado()) {
      List<ReparticionParticipanteDTO> list = expedienteElectronicoService
          .obtenerReparticionesRectoras(expedienteElectronico, loggedUsername,
              expedienteElectronico.getTrata().getCodigoTrata());
      Usuario user = usuariosSADEService.getDatosUsuario(loggedUsername);
      expedienteElectronicoService.asignarPermisosVisualizacionArchivo(expedienteElectronico, user,
          list);
    }

  }

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

    return null;
  }

  public void onCancelar() {
    super.onCancelar();
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public List<Usuario> getUsuariosGEDO() {
    try {
      return this.usuariosSADEService.getTodosLosUsuarios();
    } catch (SecurityNegocioException e) {
      logger.error(e.getMessage());
      throw new WrongValueException(Labels.getLabel("ee.error.usuario.label.invalidos"));
    }
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public DocumentoGedoService getDocumentoGedoService() {
    return documentoGedoService;
  }

  public void setDocumentoGedoService(DocumentoGedoService documentoGedoService) {
    this.documentoGedoService = documentoGedoService;
  }

  /**
   * 
   * @param name
   *          : nombre de la variable que quiero setear
   * @param value
   *          : valor de la variable
   */
  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
        value);
  }

  /**
   * 
   * @param nameTransition
   *          : nombre de la transición que voy a usar para la proxima tarea
   * @param usernameDerivador
   *          : usuario que va a tener asignada la tarea
   */
  public void signalExecution(String nameTransition, String usernameDerivador) {
    processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
        nameTransition);
  }

  /**
   * 
   * @param name
   *          : nombre de la variable del WF que quiero encontrar
   * @return objeto guardado en la variable
   */
  public Object getVariableWorkFlow(String name) {
    Object obj = this.processEngine.getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), name);
    if (obj == null) {
      throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
          + this.workingTask.getExecutionId() + ", " + name, null);
    }
    return obj;
  }

  public Window getEnvioWindow() {
    return envioWindow;
  }

  public void setEnvioWindow(Window envioWindow) {
    this.envioWindow = envioWindow;
  }

  final class IntegrarTareaOnNotifyWindowListener implements EventListener {
    private IntegrarTareaParalelaComposer composer;

    public IntegrarTareaOnNotifyWindowListener(IntegrarTareaParalelaComposer comp) {
      this.composer = comp;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("integrarTarea")) {
          this.composer.integrarTarea(usuarioProductorInfo, detalles, loggedUsername,
              tareaParalelo);
        }
      }
    }

  }

}
