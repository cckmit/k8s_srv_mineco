package com.egoveris.te.base.composer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Paginal;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.conf.service.SecurityUtil;
import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.AsignacionService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ExternalPagingSorting;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InboxGrupoComposer extends InboxGeneralComposer {
  private static final String ESTADO_INICIAR = "Iniciar Expediente";
  private static final String ESTADO_INICIACION = "Iniciacion";
  private static final String ESTADO_COMUNICACION = "Comunicacion";
  private static final String ESTADO_EJECUCION = "Ejecucion";
  private static final String ESTADO_SUBSANACION = "Subsanacion";
  private static final String ESTADO_TRAMITACION = "Tramitacion";
  private static final String ESTADO_PARALELO = "Paralelo";
  private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
  private static final String ESTADO_ARCHIVO = "Archivo";
  private static final String ESTADO_TODOS = "Todos los estados...";
  private static final long serialVersionUID = -8804746095654509780L;
  private static Logger logger = LoggerFactory.getLogger(InboxComposer.class);
  private static final String TRAMITACION_EN_PARALELO = "Paralelo";

  @Autowired
  private Button botonFiltrar;
  @Autowired
  private Window inboxGrupalWindow;
  @Autowired
  private Window hiddenView;
  @Autowired
  private Listbox solicitudesLb;
  @Autowired
  private Listfooter totalFoot;
  
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;
  
  private List<Tarea> tasks = null;
  private Tarea selectedTask = null;
  @Autowired
  private Window taskView;
  @Autowired
  private Groupbox groupFiltro;
  @Autowired
  private Listbox tasksList;

  // AGREGADOS PARA LA ASIGNACION
  @Autowired
  private Listheader listheader_1;
  @Autowired
  private Combobox usuarioDestino;
  @WireVariable(ConstantesServicios.ASIGNACION_SERVICE)
  private AsignacionService asignacionService;
  private AnnotateDataBinder binder;
  @Autowired
  private Popup usuariosReparticionListPopUp;
  @Autowired
  private Toolbarbutton asignarTarea;
  @Autowired
  private Combobox estados;
  private Listheader fechaModificacion;
  @Autowired
  private Groupbox idgroupbox;
  private Map<String, String> detalles;
  private Usuario usuarioProductorInfo;
  //
  private ExternalPagingSorting extTaskPagSort = null;
  @WireVariable(ConstantesServicios.TASK_VIEW_SERVICE)
  private ITaskViewService taskViewService;
  @Autowired
  private Paginal taskGrupalPaginator;
  private String username;
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;
  @WireVariable(ConstantesServicios.TAREA_PARALELO_SERVICE)
  private TareaParaleloService tareaParaleloService;
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;
  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;
  
  private ExecutionService executionService;

  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(c);
    executionService = processEngine.getExecutionService();
    c.addEventListener(Events.ON_USER, new InboxOnNotifyWindowListener(this));
    c.addEventListener(Events.ON_NOTIFY, new InboxOnNotifyWindowListener(this));
    this.estados.appendItem(ESTADO_TODOS);
    this.estados.appendItem(ESTADO_INICIAR);
    this.estados.appendItem(ESTADO_INICIACION);
    this.estados.appendItem(ESTADO_TRAMITACION);
    this.estados.appendItem(ESTADO_COMUNICACION);
    this.estados.appendItem(ESTADO_SUBSANACION);
    this.estados.appendItem(ESTADO_EJECUCION);
    this.estados.appendItem(ESTADO_GUARDA_TEMPORAL);
    this.estados.appendItem(ESTADO_ARCHIVO);
    this.estados.appendItem(ESTADO_PARALELO);
    this.estados.setValue("");
    //      
    // Valido si el usuario logueado tiene permisos de asignador
    // para mostrar la funcionalidad de asignacion
    this.username = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    validarPermisosUsuario(username);
    //
    //
    // Events.echoEvent("onUser",this.self,"onAdquirirTarea");
    // Nueva Funcionalidad
    initTaskGroupsPagSort();
    fechaModificacion.sort(true, true);
    this.binder.loadComponent(this.groupFiltro);
    this.estados.setValue(ESTADO_TODOS);
  }

  @SuppressWarnings("unchecked")
  public void onCreate$inboxGrupalWindow(Event event) throws InterruptedException {
    this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
  }

  // Por cambio de paginSorting
  public void initTaskGroupsPagSort() {
    extTaskPagSort = new ExternalPagingSorting(tasksList, taskGrupalPaginator);
    extTaskPagSort.setObjetoModelo(this.taskViewService);
    extTaskPagSort.setNombreMetodo("buscarTask2TareaPorGrupo"); //

    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    parametrosConsulta.put("usuario", username);

    if (!estados.getValue().equalsIgnoreCase(ESTADO_TODOS)) {
      parametrosConsulta.put("filtro", estados.getValue());
    }

    // 07-06-2017: Se agrega parametro para filtrar por tareas unnasigned
    // (sin asignacion)
    parametrosConsulta.put("unnasigned", true);
    
    extTaskPagSort.setParametrosConsulta(parametrosConsulta);
    tasksList.addEventListener(Events.ON_NOTIFY, new EventListener() {
      public void onEvent(Event event) throws Exception {
        if (event.getName().equals(Events.ON_NOTIFY)) {
          tasks = (List<Tarea>) extTaskPagSort.getDatos();

          binder.loadComponent(tasksList);
        }
      }
    });
  }

  /**
   * Valido si el usuario tiene permiso de asignador. Si es true entonces
   * muestro la funcionalidad correspondiente. Se pone un nombre genérico para
   * agregar si es necesario mas validaciones por rol
   *
   * @param loggedUsername
   */
  private void validarPermisosUsuario(String loggedUsername) {
    boolean esAsignador = (Boolean) SecurityUtil.isAllGranted(ConstantesWeb.ROL_ASIGNADOR);

    if (esAsignador) {
      this.tasksList.setMultiple(true);
      this.listheader_1.setWidth("40px");
      this.asignarTarea.setVisible(true);

      // Como es asignador cargo la lista de usuarios a los que puede
      // llegar a asigar tareas.
      Usuario datosLoggedUser = this.usuariosSADEService.obtenerUsuarioActual();

      List<Usuario> usuarios = this.usuariosSADEService.getTodosLosUsuariosXReparticionYSectorEE(
          datosLoggedUser.getCodigoReparticion(), datosLoggedUser.getCodigoSectorInterno());

      ListModelList listModelList = new ListModelList(usuarios);

      ListModel listModel = ListModels.toListSubModel((listModelList), new UsuariosComparatorConsultaExpediente(),
          30);

      this.usuarioDestino.setModel(listModel);

    } else {
      this.tasksList.setMultiple(false);
      this.listheader_1.setWidth("20px");
      // this.toolbarGrupal.setVisible(false);
      this.asignarTarea.setVisible(false);
    }
  }

  public void onClick$botonFiltrar() {
    if (this.groupFiltro.isOpen()) {
      botonFiltrar.setLabel("Mostrar Filtros");
      this.groupFiltro.setOpen(false);
    } else {
      botonFiltrar.setLabel("Ocultar Filtros");
      this.groupFiltro.setOpen(true);
    }

    this.binder.loadComponent(this.groupFiltro);
  }

  synchronized public void onAdquirirTarea() throws InterruptedException {
    if (this.selectedTask != null && this.selectedTask.getTask() != null) {
      if (this.selectedTask.getTask().getFormResourceName() == null) {
        Messagebox.show(Labels.getLabel("ee.inbox.error.noview"),
            Labels.getLabel("ee.errorMessageBox.title"), Messagebox.OK, Messagebox.ERROR);

        return;
      }
    } else {
      Messagebox.show(Labels.getLabel("ee.inbox.error.grave"),
          Labels.getLabel("ee.errorMessageBox.title"), Messagebox.OK, Messagebox.ERROR);

      return;
    }

    /**
     * Adquiero la tarea con el usuario loggeado y refresco.
     *
     */

    // signalExecution(estado, "usuarioProductor");
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("selectedTask", this.selectedTask.getTask());
    this.inboxGrupalWindow.getDesktop().setAttribute("selectedTask", this.selectedTask.getTask());

    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    try {
      this.processEngine.getTaskService().takeTask(this.selectedTask.getTask().getId(),
          loggedUsername);

      /**
       * Se comenta la funcionalidad que sigue para poder devolver las tareas en
       * paralelo que se adquieran del buzón grupal En las pruebas no se
       * encontraron fallas o mismo en el código no se ningún cambio que afecte
       * el funcionamiento de tareas en paralelo.
       *
       *
       * if(this.selectedTask.getTask().getActivityName().equals(
       * TRAMITACION_EN_PARALELO)){ TareaParalelo tareaParalelo =
       * this.tareaParaleloService
       * .buscarTareaEnParaleloByIdTask(this.selectedTask
       * .getTask().getExecutionId()); //El usuario/grupo anterior pasa a ser el
       * distinatario original por que esta adquiriendo una tarea en paralelo
       * que la tiene una reparticion tareaParalelo.setTareaGrupal(false);
       * this.tareaParaleloService.modificar(tareaParalelo); } *
       */
    } catch (JbpmException e) {
      String messageAux = e.getMessage();
      String message = messageAux.substring(21);
      Messagebox.show(Labels.getLabel("ee.buzonGrupal.errorTareaTomada") + message,
          Labels.getLabel("ee.buzonGrupal.tituloTareaTomada"), Messagebox.OK, Messagebox.ERROR);
    }
 
    String codigoExpedienteElectronico = (String) executionService
        .getVariable(this.selectedTask.getTask().getExecutionId(), "codigoExpediente");
    Usuario user = usuariosSADEService.getDatosUsuario(username);
    generarTareaHistorico(codigoExpedienteElectronico, "Adquisicion", user.getCodigoReparticion(),
        username, this.selectedTask.getTask());

    this.refreshInbox();

    /**
     * Se comenta la funcionalidad que devolvía al inbox personal luego de
     * adquiriri una tarea a pedido del usuario.
     */

    // Collection<Component> components = this.desktop.getComponents();
    // Event event = null;
    // for (Component componente : components) {
    // if(componente.getId().equalsIgnoreCase("inboxWindow")
    // && event==null){
    // event = new Event(Events.ON_NOTIFY, componente);
    // }
    // if(componente.getId().equalsIgnoreCase("buzondetareas")){
    // Tab tabBuzonTarea = (Tab) componente;
    // tabBuzonTarea.setSelected(true);
    // }
    // }
    // Events.sendEvent(event);
  }

  // Por cambio de PaginSort
  public List<Tarea> populateTasks() {
    extTaskPagSort.setCriterio(TaskQuery.PROPERTY_CREATEDATE);
    extTaskPagSort.setOrden("descending");

    if (!estados.getValue().equalsIgnoreCase(ESTADO_TODOS)) {
      extTaskPagSort.getParametrosConsulta().put("filtro", estados.getValue());
    } else {
      extTaskPagSort.getParametrosConsulta().put("filtro", null);
    }

    tasks = (List<Tarea>) extTaskPagSort.cargarDatos();

   
    return tasks;
  }

  // Por cambio de PaginSort
  private List<Tarea> cargarListaTareas(ExecutionService executionService,
      List<Task> listaTareaUsuario) {
    List<Tarea> listaTarea = new ArrayList<Tarea>();

    for (Task task : listaTareaUsuario) {
      Tarea tarea = new Tarea();
      String nombreTarea = task.getActivityName();
      String codigoExpediente = (String) executionService.getVariable(task.getExecutionId(),
          "codigoExpediente");

      if (codigoExpediente == null) {
        codigoExpediente = "";
      }

      Long idSolicitud = (Long) executionService.getVariable(task.getExecutionId(),
          "idSolicitud");

      String usuarioAnterior;
      String motivo;
      String tareaGrupal;

      if (!nombreTarea.equals(TRAMITACION_EN_PARALELO)) {
        motivo = (String) executionService.getVariable(task.getExecutionId(), "motivo");

        usuarioAnterior = (String) executionService.getVariable(task.getExecutionId(),
            "usuarioAnterior");

        if (usuarioAnterior == null) {
          usuarioAnterior = (String) executionService.getVariable(task.getExecutionId(),
              "usuarioProductor");

          if (usuarioAnterior == null) {
            usuarioAnterior = "";
          }
        }

        tareaGrupal = (String) executionService.getVariable(task.getExecutionId(), "tareaGrupal");
      } else {
        TareaParaleloDTO tareaParalelo = tareaParaleloService
            .buscarTareaEnParaleloByIdTask(task.getExecutionId());

        motivo = tareaParalelo.getMotivo();
        usuarioAnterior = tareaParalelo.getUsuarioGrupoAnterior();

        if (tareaParalelo.getTareaGrupal()) {
          tareaGrupal = "esTareaGrupal";
        } else {
          tareaGrupal = "noEsTareaGrupal";
        }
      }

      String codigoTrata = (String) executionService.getVariable(task.getExecutionId(),
          "codigoTrata");

      if (codigoTrata == null) {
        codigoTrata = "";
      } else {
        TrataDTO trata = this.trataService.buscarTrataByCodigo(codigoTrata);
        tarea.setDescripcionTrata(trata.getCodigoTrata());
      }

      tarea.setNombreTarea(nombreTarea);
      tarea.setCodigoExpediente(codigoExpediente);
      tarea.setIdSolicitud(idSolicitud);
      tarea.setMotivo(motivo);
      tarea.setUsuarioAnterior(usuarioAnterior.toUpperCase());
      tarea.setTask(task);
      tarea.setTareaGrupal(tareaGrupal);
      tarea.setCodigoTrata(codigoTrata);

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      tarea.setFechaCreacion(sdf.format(task.getCreateTime()));
      listaTarea.add(tarea);
    }

    return listaTarea;
  }

  public Window getHiddenView() {
    return hiddenView;
  }

  public void setHiddenView(Window hiddenView) {
    this.hiddenView = hiddenView;
  }

  public Window getInboxGrupalWindow() {
    return inboxGrupalWindow;
  }

  public void setInboxGrupalWindow(Window inboxGrupalWindow) {
    this.inboxGrupalWindow = inboxGrupalWindow;
  }

  public Listbox getSolicitudesLb() {
    return solicitudesLb;
  }

  public void setSolicitudesLb(Listbox solicitudesLb) {
    this.solicitudesLb = solicitudesLb;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public List<Tarea> getTasks() throws InterruptedException {
    try {
      if (this.tasks == null) {
        populateTasks();
      }
    } catch (Exception e) {
      logger.error("Error al recuperar las tareas", e);
      Messagebox.show(Labels.getLabel("ee.buzonGrupal.errorRecuperarTareas"), "Error", Messagebox.OK,
          Messagebox.ERROR);
    }

    return tasks;
  }

  public void setTasks(List<Tarea> tasks) {
    this.tasks = tasks;
  }

  public Tarea getSelectedTask() {
    return selectedTask;
  }

  public void setSelectedTask(Tarea selectedTask) {
    if (selectedTask != null)
      this.selectedTask = selectedTask;
  }

  public Window getTaskView() {
    return taskView;
  }

  public void setTaskView(Window taskView) {
    this.taskView = taskView;
  }

  public Listbox getTasksList() {
    return tasksList;
  }

  public void setTotalFoot(Listfooter totalFoot) {
    this.totalFoot = totalFoot;
  }

  public Listfooter getTotalFoot() {
    return totalFoot;
  }

  public void setTasksList(Listbox tasksList) {
    this.tasksList = tasksList;
  }

  public void refreshInbox() {
    this.populateTasks();
    this.getTaskGrupalPaginator();
    this.estados.setValue("");
    this.groupFiltro.setOpen(false);
    this.binder.loadComponent(this.tasksList);

  }

  public void refreshPaginator(String operacion) {
    this.getTaskGrupalPaginator();
    
    if ("RESTA".equals(operacion)) {
      if (taskGrupalPaginator.getTotalSize() > 0) {
        taskGrupalPaginator.setTotalSize(taskGrupalPaginator.getTotalSize() - 1);
      }
    }

    Integer numero = new Integer(taskGrupalPaginator.getTotalSize());
    totalFoot.setLabel(numero.toString());
    this.binder.loadComponent(this.totalFoot);
  }

  private void showTrouble() {
    inboxGrupalWindow.setVisible(false);
  }

  public void onClick$filtrar() throws InterruptedException {
    this.taskGrupalPaginator.setActivePage(0);
    populateTasks();
    this.binder.loadComponent(this.tasksList);
  }

  /**
   * No debe visualizar aquellas tareas cuyos expedientes se encuentren
   * bloqueados por un sistema externo.
   *
   * @param listaTarea
   * @param tarea
   * @throws RuntimeException
   */
  public void validacionVistaTareaExterna(List<Tarea> listaTarea, Tarea tarea)
      throws RuntimeException {
    listaTarea.add(tarea);
  }

	/**
	 * On click$asignar tareas.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onClick$asignarTareas() throws InterruptedException {
    if (this.usuarioDestino.getSelectedItem() == null) {
      throw new WrongValueException(usuarioDestino,
          Labels.getLabel("ee.error.usuarioAsignadoInexistente"));
    }
    
		Usuario usuarioReducido = (Usuario) this.usuarioDestino.getSelectedItem().getValue();
		usuarioProductorInfo = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());
		Map<String, Object> datos = new HashMap<>();
		datos.put("funcion", "validarApoderamiento");
		datos.put("datos", usuarioProductorInfo);
		enviarBloqueoPantalla(datos);
  }

  public void onClick$asignarTarea() throws InterruptedException {
    Set<?> selectedItems = this.tasksList.getSelectedItems();

    if (selectedItems.size() > 0) {
      this.usuarioDestino.setValue("");
      this.asignarTarea.setPopup("subordinadosListPopUp");
      this.usuariosReparticionListPopUp.open(idgroupbox);
      this.binder.loadComponent(this.usuariosReparticionListPopUp);
    } else {
      this.usuarioDestino.setValue("");
      Messagebox.show(Labels.getLabel("ee.general.ningunaTareaSeleccionada"),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

  /**
   * Asignar tareas.
   *
   * @throws AsignacionException the asignacion exception
   * @throws InterruptedException the interrupted exception
   */
  void asignarTareas() throws AsignacionException, InterruptedException {
    List<Task> listaTareasNoAsigables = new ArrayList<>();
    Set<?> selectedItems = this.tasksList.getSelectedItems();

    if (selectedItems != null) {
      Iterator<?> it = selectedItems.iterator();

      while (it.hasNext()) {
        Listitem li = (Listitem) it.next();
        Tarea currentSelectedTask = (Tarea) this.tasks.get(li.getIndex());

        try {
          TareaParaleloDTO tareaParalelo = this.tareaParaleloService
              .buscarTareaEnParaleloByIdTask(currentSelectedTask.getTask().getExecutionId());

          if (tareaParalelo != null) {
            tareaParalelo.setTareaGrupal(false);
            tareaParaleloService.modificar(tareaParalelo);
          }

          asignacionService.asignarTarea(currentSelectedTask.getTask(), this.username,
              ConstantesWeb.MOTIVO_PASE_ASIGNACION, usuarioProductorInfo.getUsername());
          // todo poner aca historial operacion
          this.processEngine.getExecutionService().setVariable(
              currentSelectedTask.getTask().getExecutionId(), ConstantesWeb.TAREA_GRUPAL,
              "noEsTareaGrupal");
        } catch (AsignacionException asignacionException) {
          asignacionException.getMessage();
          listaTareasNoAsigables.add(currentSelectedTask.getTask());
        }
      }

      if (listaTareasNoAsigables.size() > 0) {
        String tareasAsignadas = "";

        for (Task tarea : listaTareasNoAsigables) {
          Task tareaActualizada = this.processEngine.getTaskService().getTask(tarea.getId());

          if (!tareasAsignadas.contains((CharSequence) tareaActualizada.getAssignee())) {
            tareasAsignadas = tareasAsignadas + " - " + tareaActualizada.getAssignee();
          }
        }

        Messagebox.show(
            Labels.getLabel("ee.error.asignacionRealizada", new String[] { tareasAsignadas }),
            Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION,
            new EventListener() {
              public void onEvent(Event evt) {
                refreshInbox();
              }
            });
      }
      Clients.clearBusy();
      this.refreshInbox();
    }
  }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void asignarTarea() throws Exception {
		Clients.clearBusy();
    Messagebox.show(
        Labels.getLabel("ee.general.asignarBuzonGrupal", new String[] { usuarioProductorInfo.getUsername() }),
        Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener() {
          public void onEvent(Event evt) {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              try {
                asignarTareas();
              } catch (InterruptedException e) {
                logger.error(e.getMessage());
              }
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
	}

	@Override
	protected void enviarBloqueoPantalla(Object data) {
		Clients.showBusy(Labels.getLabel("ee.general.procesando.procesandoSolicitud"));
		Events.echoEvent(Events.ON_USER, this.self, data);
	}
	
  public Button getBotonFiltrar() {
    return botonFiltrar;
  }

  public void setBotonFiltrar(Button botonFiltrar) {
    this.botonFiltrar = botonFiltrar;
  }

  public Groupbox getGroupFiltro() {
    return groupFiltro;
  }

  public void setGroupFiltro(Groupbox groupFiltro) {
    this.groupFiltro = groupFiltro;
  }

  public Combobox getEstados() {
    return estados;
  }

  public void setEstados(Combobox estados) {
    this.estados = estados;
  }

  public Paginal getTaskGrupalPaginator() {
    String estado = null;

    if (!this.estados.getValue().equalsIgnoreCase(ESTADO_TODOS)) {
      estado = this.estados.getValue();
    }

    this.taskGrupalPaginator
        .setTotalSize(taskViewService.numeroTotalTareasGrupoPorUsuario(this.username, estado));

    return taskGrupalPaginator;
  }

  public void setTaskGrupalPaginator(Paginal taskGrupalPaginator) {
    this.taskGrupalPaginator = taskGrupalPaginator;
  }

  public void signalExecution(String nameTransition, String usernameDerivador) {
    // PASO TODAS LAS VARIABLES ANTES DE REALIZAR EL SIGNAL
    setearVariablesAlWorkflow();

    // Paso a la siguiente Tarea/Estado definida en el Workflow
    processEngine.getExecutionService()
        .signalExecutionById(this.selectedTask.getTask().getExecutionId(), nameTransition);
  }

  private void setearVariablesAlWorkflow() throws NumberFormatException {
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put(ConstantesWeb.ESTADO_ANTERIOR,
        detalles.get("estadoAnterior"));
    variables.put(ConstantesWeb.ESTADO_ANTERIOR_PARALELO,
        detalles.get("estadoAnteriorParalelo"));
    variables.put(ConstantesWeb.GRUPO_SELECCIONADO,
        detalles.get("grupoSeleccionado"));
    variables.put(ConstantesWeb.TAREA_GRUPAL,
        detalles.get("tareaGrupal"));
    variables.put(ConstantesWeb.USUARIO_SELECCIONADO,
        detalles.get("usuarioSeleccionado"));
    variables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO,
        Integer.parseInt(detalles.get("idExpedienteElectronico")));
    setVariablesWorkFlow(variables);
  }

  @Deprecated
  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService()
        .setVariable(this.selectedTask.getTask().getExecutionId(), name, value);
  }

  public void setVariablesWorkFlow(Map<String, Object> variables) {
    workFlowService.setVariables(processEngine, this.selectedTask.getTask().getExecutionId(),
        variables);
  }

  public void onClick$quitarFiltro() throws InterruptedException {
    this.estados.setValue(ESTADO_TODOS);
    populateTasks();
    this.binder.loadComponent(this.tasksList);
  }
  
  final class InboxOnNotifyWindowListener implements EventListener {
    private InboxGrupoComposer composer;

    public InboxOnNotifyWindowListener(InboxGrupoComposer comp) {
      this.composer = comp;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() == null) {
          this.composer.refreshInbox();
        }
      }
      if (event.getName().equals(Events.ON_USER)) {
  			Map<String, Object> datos = (Map<String, Object>) event.getData();
  			String funcion = (String) datos.get("funcion");
  			if ("validarApoderamiento".equals(funcion)) {
  				Usuario usuario = (Usuario) datos.get("datos");
  				this.composer.validarUsuarios(usuario);
  			}
  			if ("validarReparticion".equals(funcion)) {
  				Usuario usuario = (Usuario) datos.get("datos");
  				this.composer.validacionesReparticion(usuario);
  			}
        if ("asignarTarea".equals(funcion)) {
        	usuarioProductorInfo = (Usuario) datos.get("datos");
          this.composer.asignarTarea();
        }
  		}
    }
    
  }

  /**
   * Validaciones de adoderamiento y usuarios que pertenecen a la misma
   * repartición.
   * 
   * @throws InterruptedException
   */
  public void validarUsuarios(Usuario usuarioAValidar) throws InterruptedException {
    super.validarApoderamiento(usuarioAValidar);
  }
  
}
