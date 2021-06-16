package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
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
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Paginal;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.rendered.SupervisadosInboxItemRenderer;
import com.egoveris.te.base.service.SupervisadosService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ExternalPagingSorting;
import com.egoveris.te.base.util.Task2TareaTransformer;
import com.egoveris.te.base.util.Utils;
import com.egoveris.te.model.exception.ParametroIncorrectoException;



/**
 * Composer encargado de la administración de la vista de Inbox de tareas de los
 * supervisados.
 * 
 * @author bfontana
 * 
 */
@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SupervisadosInboxComposer extends ValidarApoderamientoComposer {
	private final static Logger logger = LoggerFactory.getLogger(SupervisadosInboxComposer.class);
	private static final String TRAMITACION_EN_PARALELO = "Paralelo";
	private static final String ANULAR_MODIFICAR = "Anular/Modificar Solicitud";
	private List<Tarea> tasks = null;
	@Autowired
	private Listbox tasksList;
	@Autowired
	private AnnotateDataBinder binder;
	private Usuario supervisado;
  private Usuario usuarioReducido;
	private Tarea selectedTask = null;
	private Button btnrefresh;
	@Autowired
	private Window supervisadosWindow;
	private List<Usuario> usuarios;
	private Combobox usuarioDestino;
	protected Task workingTask = null;
	@Autowired
	private Toolbarbutton reasignacionButton;
	@Autowired
	private Toolbarbutton enviarAGuardaTemporalButton;
	@Autowired
	private Popup subordinadosListPopUp;
	@Autowired
	private Groupbox idgroupbox;
	private List<Tarea> tareasSeleccionadas = new ArrayList<Tarea>();
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	private ProcessEngine processEngine;
	@WireVariable(ConstantesServicios.TAREA_PARALELO_SERVICE)
	private TareaParaleloService tareaParaleloService;
	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;
	@WireVariable(ConstantesServicios.SUPERVISADOS_SERVICE)
	private SupervisadosService supervisadosService;
	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;
	private Window motivoGuardaTemporalWindow;
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	private ExpedienteElectronicoDTO selectedExpedienteElectronico;
	private Window parametrosConsultaWindow;
	private ExternalPagingSorting extTaskPagSort = null;
	@WireVariable(ConstantesServicios.TASK_VIEW_SERVICE)
	private ITaskViewService taskViewService;
	@Autowired
	private Paginal taskPaginator; 
	@WireVariable("ExecutionServiceImpl")
	private ExecutionService executionService;
 

	// ********************************************
	// *** METHOD'S
	// ********************************************
	@Override
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    component.addEventListener(Events.ON_USER, new SupervisadosInboxComposerListener(this));
		tasksList.setItemRenderer(new SupervisadosInboxItemRenderer(this));
		Usuario user = usuariosSADEService.obtenerUsuarioActual();
		this.self.setAttribute("dontAskBeforeClose", true);
		this.supervisado = (Usuario) this.self.getDesktop().getAttribute("supervisado");
		this.supervisadosWindow.setTitle("Buzón de tareas de " + this.supervisado.getUsername());
		this.usuarioDestino.setModel(ListModels.toListSubModel(
				new ListModelList(this.usuariosSADEService.obtenerUsuariosSupervisados(user.getUsername())),
				new UsuariosComparator(), 30));
		this.initTaskPagSort(); 
	}

	/**
	 * <p>
	 * Se configura <code>ExternalPagingSorting</code> con el criterio
	 * <code>TaskQuery.PROPERTY_ASSIGNEE</code>, <code>java.lang.String</code>
	 * descending inicializa el evento <code>Listbox.Events.ON_NOTIFY</code> de
	 * páginado.
	 * </p>
	 */
	public void initTaskPagSort() {
		extTaskPagSort = new ExternalPagingSorting(tasksList, taskPaginator);
		extTaskPagSort.setObjetoModelo(this.taskViewService);
		extTaskPagSort.setNombreMetodo("buscarTareasPorUsuario");

		Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
		parametrosConsulta.put("usuario", this.supervisado.getUsername());
		if (Executions.getCurrent().getDesktop().getAttribute("cacheQueries") != null) {
			Executions.getCurrent().getDesktop().setAttribute("cacheQueries", "true");
			parametrosConsulta.put("cacheQueries", Executions.getCurrent().getDesktop().getAttribute("cacheQueries"));
		}

		extTaskPagSort.setParametrosConsulta(parametrosConsulta);

		tasksList.addEventListener(Events.ON_NOTIFY, new EventListener() {
			@SuppressWarnings("unchecked")
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals(Events.ON_NOTIFY)) {
					tasks = (List<Tarea>) extTaskPagSort.getDatos();
					binder.loadComponent(tasksList);
				}
			}
		});

		//
		btnrefresh.addEventListener(Events.ON_CLICK, new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName() != null && binder != null) {
					if (event.getName().equals(Events.ON_CLICK)) {
						refreshInbox();
					}
				}
			}
		});

		return;
	}

	public boolean existeEnLista(Tarea tarea) {
		for (Tarea t : tareasSeleccionadas) {
			if (t.equals(tarea)) {
				return true;
			}
		}
		return false;
	}

	public void removerDeLista(Tarea tarea) {
		Iterator i = tareasSeleccionadas.iterator();

		while (i.hasNext()) {
			Tarea t = (Tarea) i.next();
			if (t.equals(tarea)) {
				i.remove();
			}
		}
	}

	public void agregarALista(Tarea t) {
		tareasSeleccionadas.add(t);
	}

	public void refreshInbox() throws InterruptedException {
		tareasSeleccionadas.clear();
		this.populateTasks();
		this.binder.loadComponent(this.tasksList);
		this.binder.loadAll();
	}

	public void onVerExpediente() {
		this.selectedExpedienteElectronico = this.buscarExpediente(this.selectedTask);

		if (selectedExpedienteElectronico != null) {
			Long idExpedienteElectronico = 0l;
			idExpedienteElectronico = this.selectedExpedienteElectronico.getId();
			Executions.getCurrent().getDesktop().setAttribute("idExpedienteElectronico", idExpedienteElectronico);
			Executions.getCurrent().getDesktop().setAttribute("codigoExpedienteElectronico",
					this.selectedExpedienteElectronico.getCodigoCaratula());
			this.openModalWindow("/expediente/detalleExpedienteElectronico.zul");
		} else {
			Messagebox.show(Labels.getLabel("te.supervisados.errorDatos"),
					Labels.getLabel("te.supervisados.errorDeDatos"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public ExpedienteElectronicoDTO buscarExpediente(Tarea selectedTask2) {
		ExpedienteElectronicoDTO expedietne = null;

		try {
			expedietne = this.expedienteElectronicoService
					.obtenerExpedienteElectronicoPorCodigo(selectedTask2.getCodigoExpediente());
		} catch (ParametroIncorrectoException e) {
			logger.error("[ThreadId :" + Thread.currentThread().getId() + "] Hubo un error en el método buscar.", e);
		}

		return expedietne;
	}

	public void openModalWindow(String zulFile) {
		Utilitarios.closePopUps("detalleExpedienteElectronicoWindow");
		// Le pasamos el mismo composer a la ventana modal para poder
		// comunicarla con la ventana principal
		Map<String, Object> values = new HashMap<>();
		values.put("parentComposer", this);
		this.parametrosConsultaWindow = (Window) Executions.createComponents(Utils.formatZulPath(zulFile), this.self,
				values);
		this.parametrosConsultaWindow.setParent(this.supervisadosWindow);
		this.parametrosConsultaWindow.setPosition("center");
		this.parametrosConsultaWindow.setVisible(true);
		this.parametrosConsultaWindow.doModal();
	}

	public List getTasks() {
		long startTime = System.currentTimeMillis();

		if (this.tasks == null) {
			tasks = this.getSupervisadoTasks();
		}

		logger.debug("[ThreadId=" + Thread.currentThread().getId() + "] Run getTasks() Time : "
				+ (System.currentTimeMillis() - startTime));

		return tasks;
	}

	public void setTasks(List<Tarea> tasks) {
		this.tasks = tasks;
	}

	public Toolbarbutton getEnviarAGuardaTemporalButton() {
		return enviarAGuardaTemporalButton;
	}

	public void setEnviarAGuardaTemporalButton(Toolbarbutton enviarAGuardaTemporalButton) {
		this.enviarAGuardaTemporalButton = enviarAGuardaTemporalButton;
	}

	public void onClick$advocacionButton() throws InterruptedException {
		List<Tarea> selectedItems = this.tareasSeleccionadas;

		if ((selectedItems != null) && (selectedItems.size() > 0)) {
			Messagebox.show(Labels.getLabel("ee.general.advocarseTareas"), Labels.getLabel("ee.general.question"),
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								avocarseTareas();

								break;

							case Messagebox.NO:
								break;
							}
						}
					});
		} else {
			Messagebox.show(Labels.getLabel("ee.general.ningunaTareaSeleccionadaParaAvocar"),
					Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void onClick$cerrar() {
		((Window) this.self).onClose();
	}

	public void onClick$asignarTareas() throws InterruptedException {
		if (this.usuarioDestino.getSelectedItem() == null) {
			throw new WrongValueException(usuarioDestino, Labels.getLabel("ee.error.usuarioInexistente"));
		}

    if (!tareasSeleccionadas.isEmpty()) {
      usuarioReducido = (Usuario) this.usuarioDestino.getSelectedItem().getValue();
      Usuario us = this.getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());
      Map<String, Object> datos = new HashMap<>();
      datos.put("funcion", "validarApoderamiento");
      datos.put("datos", us);
      enviarBloqueoPantalla(datos);
    } else {
			Messagebox.show(Labels.getLabel("ee.general.ningunaTareaSeleccionada"),
					Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
	}

	public void onClick$reasignacionButton() throws InterruptedException {
		if (!tareasSeleccionadas.isEmpty()) {
			this.reasignacionButton.setPopup("subordinadosListPopUp");
			this.subordinadosListPopUp.open(idgroupbox);
			this.binder.loadComponent(this.subordinadosListPopUp);
		} else {
			Messagebox.show(Labels.getLabel("ee.general.ningunaTareaSeleccionada"), Labels.getLabel("ee.general.information"),
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClick$enviarAGuardaTemporalButton() throws InterruptedException {
		List<Tarea> selectedItems = tareasSeleccionadas;
		if ((selectedItems != null) && (selectedItems.size() == 1)) {
			Tarea li = selectedItems.iterator().next();
			Task currentSelectedTask = (Task) li.getTask();

			if (!currentSelectedTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
				if (!currentSelectedTask.getActivityName().equals(ANULAR_MODIFICAR)) {
					// Actividades pendientes
					if (tieneActividadesPendientes(currentSelectedTask.getExecutionId())) {
						Messagebox.show(Labels.getLabel("ee.act.msg.body.actPendientes"),
								Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
					} else {
						HashMap hma = new HashMap();
						hma.put("currentSelectedTask", currentSelectedTask);
						hma.put("supervisados", new String("supervisados"));
						this.motivoGuardaTemporalWindow = (Window) Executions
								.createComponents("/pase/motivoguardatemporal.zul", this.self, hma);
						this.motivoGuardaTemporalWindow.setPosition("center");
						this.motivoGuardaTemporalWindow.setClosable(true);
						this.motivoGuardaTemporalWindow.doModal();
					}
				} else {
					this.usuarioDestino.setValue("");
					Messagebox.show(Labels.getLabel("ee.general.TareaAnularModificarSolicitud"),
							Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
				}
			} else {
				this.usuarioDestino.setValue("");
				Messagebox.show(Labels.getLabel("ee.general.TareaParalelo"), Labels.getLabel("ee.general.information"),
						Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} else {
			this.usuarioDestino.setValue("");
			Messagebox.show(Labels.getLabel("ee.general.unaTareaSeleccionada"),
					Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
		}

		this.binder.loadComponent(this.tasksList);
	}

	private boolean tieneActividadesPendientes(String workflowid) {
		IActividadExpedienteService actividadExpedienteService = (IActividadExpedienteService) SpringUtil
				.getBean(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE);
		List<String> workIds = new ArrayList<String>();
		workIds.add(workflowid);

		return !actividadExpedienteService.buscarActividadesVigentes(workIds).isEmpty();
	}

	@SuppressWarnings("rawtypes")
	void avocarseTareas() {
		String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		List<Tarea> selectedItems = tareasSeleccionadas;

		try {
			if (selectedItems != null) {
				Iterator it = selectedItems.iterator();

				while (it.hasNext()) {
					Tarea t = (Tarea) it.next();
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());

					Task currentSelectedTask = (Task) t.getTask();
					currentSelectedTask.getCreateTime().setTime(cal.getTimeInMillis());
					this.processEngine.getTaskService().saveTask(currentSelectedTask);
					supervisadosService.reasignarTarea(currentSelectedTask, loggedUsername,
							this.supervisado.getUsername(), ConstantesWeb.MOTIVO_PASE_AVOCACION, loggedUsername);
				}

				refreshInbox();
				this.binder.loadComponent(this.tasksList);
			}
		} catch (Exception e) {
			String messageException = "";
			
			if (e.getMessage() != null) {
				messageException = e.getMessage();
			}
			
			Messagebox.show(
					messageException.contains("gave up after")
							? "El expediente consultado ya fue trámitado, por favor actualice la consulta"
							: "Se ha producido un error inesperado",
							Labels.getLabel("ee.consultaExpedienteComposer.msgbox.errorComunicacion"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings("rawtypes")
	void reasignarTareas() {
		String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		List<Tarea> selectedItems = tareasSeleccionadas;

		try {
			if (selectedItems != null) {
				Iterator it = selectedItems.iterator();

				while (it.hasNext()) {
					Tarea li = (Tarea) it.next();
					Task currentSelectedTask = (Task) li.getTask();
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					currentSelectedTask.getCreateTime().setTime(cal.getTimeInMillis());
					this.processEngine.getTaskService().saveTask(currentSelectedTask);
					supervisadosService.reasignarTarea(currentSelectedTask, usuarioReducido.getUsername(),
							this.supervisado.getUsername(), ConstantesWeb.MOTIVO_PASE_REASIGNACION, loggedUsername);
				}
				refreshInbox();
				this.binder.loadComponent(this.tasksList);
	      Clients.clearBusy();
			}
		} catch (Exception e) {
      logger.error("Mensaje de error", e);
		}
	}

	@SuppressWarnings("rawtypes")
	void cancelarProceso() {
		Set selectedItems = this.tasksList.getSelectedItems();

		if (selectedItems != null) {
			Iterator it = selectedItems.iterator();

			while (it.hasNext()) {
				Listitem li = (Listitem) it.next();
				Task currentSelectedTask = (Task) this.tasks.get(li.getIndex()).getTask();
				supervisadosService.cancelarTarea(currentSelectedTask);
			}

			this.binder.loadComponent(this.tasksList);
		}
	}

	List<Tarea> getSupervisadoTasks() {
		if (this.supervisado == null) {
			return new ArrayList<Tarea>();
		}

		if (this.processEngine == null) {
				Messagebox.show(Labels.getLabel("te.supervisados.processEngine"),
						Labels.getLabel("ee.consultaExpedienteComposer.msgbox.errorComunicacion"), Messagebox.OK, Messagebox.ERROR);
				return new ArrayList<Tarea>();
		}

		if (Executions.getCurrent().getDesktop() != null) {
			try {
				tasks = this.populateTasks();
			} catch (InterruptedException ie) {
				return new ArrayList<Tarea>();
			}

			return tasks;
		}

		return new ArrayList<Tarea>();
	}

	private List<Tarea> getPersonalTasks() {
		List<Tarea> initilizationList = new ArrayList<Tarea>(); 

		if (this.processEngine == null) {
				Messagebox.show(Labels.getLabel("te.supervisados.processEngine"),
						Labels.getLabel("ee.consultaExpedienteComposer.msgbox.errorComunicacion"), Messagebox.OK, Messagebox.ERROR);
				return initilizationList;
		}

		if (Executions.getCurrent().getDesktop() != null) {
			String usuarioSupervisado = this.supervisado.getUsername();

			if (usuarioSupervisado != null) {
				return cargarListaTareas(processEngine,
						this.processEngine.getTaskService().findPersonalTasks(this.supervisado.getUsername()));
			} else {
				this.showTrouble();
				logger.error("null username attribute in desktop" + Executions.getCurrent().getDesktop());
			}
		} else {
			logger.error("Couldn't find desktop", new Exception("Couldn't find desktop"));
			this.showTrouble();
		}

		return initilizationList;
	}

	private void showTrouble() {
		supervisadosWindow.setVisible(false);
	}

	// ********************************************
	// *** ACCESSOR'S
	// ********************************************

	/**
	 * Seteo <code>ExternalPagingSorting</code> con el criterio
	 * <code>TaskQuery.PROPERTY_??</code>, <code>java.lang.String</code>
	 * descending
	 * 
	 * @return <code>List<Tarea></code> listado de objetos <code>Tarea</code>
	 *         populado.
	 * @throws <code>InterruptedException</code>
	 */
	@SuppressWarnings("unchecked")
	public List<Tarea> populateTasks() throws InterruptedException {
		extTaskPagSort.setCriterio(TaskQuery.PROPERTY_CREATEDATE);
		extTaskPagSort.setOrden("descending");
		tasks = (List<Tarea>) extTaskPagSort.cargarDatos();
		binder.loadComponent(tasksList);

		return tasks;
	}

	/**
	 * Se popula el <code>Tarea</code>
	 * 
	 * @return <code>List<Tarea></code> listado de objetos <code>Tarea</code>
	 *         populado.
	 * @throws <code>InterruptedException</code>
	 * @deprecated
	 */
	@SuppressWarnings("unchecked")
	public List<Tarea> cargarListaTareas(ProcessEngine processEngine, List<Task> listaTareaUsuario) {
		Task2TareaTransformer task2TareaTransformer = new Task2TareaTransformer(trataService, tareaParaleloService,
				processEngine, null, null);

		// Assert.assertNotNull("No se seteo los registros para páginar",
		// listaTareaUsuario);
		return ((List<Tarea>) CollectionUtils.collect(listaTareaUsuario, task2TareaTransformer));
	}

	public void onCreate$supervisadosWindow(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
	}

	public Button getBtnrefresh() {
		return btnrefresh;
	}

	public void setBtnrefresh(Button btnrefresh) {
		this.btnrefresh = btnrefresh;
	}

	public Tarea getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Tarea selectedTask) {
		this.selectedTask = selectedTask;
	}

	/**
	 * Ordena la lista de tareas de acuerdo al criterio de la más reciente
	 * primero.
	 * 
	 * @param tasks
	 *            Lista de tareas a ordenar
	 * @return La lista de tareas ordenada de acuerdo al criterio enunciado más
	 *         arriba.
	 */
	private List<Tarea> ordenarPersonalTaks(final List<Tarea> tasks) {
		Collections.sort(tasks, new SupervisadosTasksComparator());

		return tasks;
	}

	public List<Usuario> getUsuarios() {

		try {
			this.usuarios = this.usuariosSADEService.getTodosLosUsuarios();
		} catch (SecurityNegocioException e) {
			logger.error(e.getMessage());
			throw new WrongValueException(Labels.getLabel("ee.error.usuario.label.invalidos"));
		}
		return this.usuarios;
	}

	public Task getWorkingTask() {
		return this.workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	public void setTasksList(Listbox tasksList) {
		this.tasksList = tasksList;
	}

	public Listbox getTasksList() {
		return this.tasksList;
	}

	public void setTaskPaginator(Paginal taskPaginator) {
		this.taskPaginator = taskPaginator;
	}

	public Paginal getTaskPaginator() {
		this.taskPaginator
				.setTotalSize(this.taskViewService.numeroTotalTareasPorUsuario(this.supervisado.getUsername()));
		return this.taskPaginator;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void asignarTarea() throws Exception {
		Clients.clearBusy();
		Messagebox.show(Labels.getLabel("ee.general.reasignar", new String[] { usuarioReducido.getUsername() }),
				Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new EventListener() {
					public void onEvent(Event evt) {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							Map<String, Object> datos = new HashMap<String, Object>();
							datos.put("funcion", "reasignarTareas");
							enviarBloqueoPantalla(datos);
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
	
  /**
   * Validaciones de adoderamiento y usuarios que pertenecen a la misma
   * repartición.
   * 
   * @throws InterruptedException
   */
  public void validarUsuarios(Usuario usuarioAValidar) throws InterruptedException {
    super.validarApoderamiento(usuarioAValidar);
  }

	/**
	 * @return the usuarioReducido
	 */
	public Usuario getUsuarioReducido() {
		return usuarioReducido;
	}

	/**
	 * @param usuarioReducido the usuarioReducido to set
	 */
	public void setUsuarioReducido(Usuario usuarioReducido) {
		this.usuarioReducido = usuarioReducido;
	}
}

/**
 * Implementación de Comparator que permite comparar dos Task JBPM por la fecha
 * de creación y devuelve la más reciente primero.
 * 
 * @author bfontana
 * 
 */
final class SupervisadosTasksComparator implements Comparator<Tarea> {
	/**
	 * Compara dos instancias de Task y devuelve la comparación usando el
	 * criterio de más reciente primera en el orden.
	 * 
	 * @param t1
	 *            Primer tarea a comparar
	 * @param t2
	 *            Segunda tarea a comparar con la primera
	 */
	public int compare(Tarea t1, Tarea t2) {
		if (t1.getFechaCreacion() == null) {
			return -1;
		} else if (t2.getFechaCreacion() == null) {
			return 1;
		} else {
			if (t1.getFechaModificacion() != null) {
				if (t2.getFechaModificacion() != null) {
					return t1.getFechaModificacion().compareTo(t2.getFechaModificacion());
				} else {
					return t1.getFechaModificacion().compareTo(t2.getFechaCreacion());
				}
			} else {
				if (t2.getFechaModificacion() != null) {
					return t1.getFechaCreacion().compareTo(t2.getFechaCreacion());
				} else {
					return t1.getFechaCreacion().compareTo(t2.getFechaCreacion());
				}
			}
		}
	}
}

@SuppressWarnings("rawtypes")
class UsuariosComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		String userText = null;

		if (o1 instanceof String) {
			userText = ((String) o1).trim();
		}

		if (userText != null && (o2 instanceof Usuario) && (StringUtils.isNotEmpty(userText))
				&& (userText.length() > 2)) {
			Usuario dub = (Usuario) o2;
			String NyAp = dub.getNombreApellido();

			if (NyAp != null) {
				if (NyAp.toLowerCase().contains(userText.toLowerCase())) {
					return 0;
				}
			}
		}

		return 1;
	}
}

final class SupervisadosInboxComposerListener implements EventListener {
  private SupervisadosInboxComposer composer;

  public SupervisadosInboxComposerListener(SupervisadosInboxComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {
      Map<String, Object> datos = (Map<String, Object>) event.getData();
      if ("validarApoderamiento".equals(datos.get("funcion"))) {
        Usuario usuario = (Usuario) datos.get("datos");
        this.composer.validarUsuarios(usuario);
      }
      if ("validarReparticion".equals(datos.get("funcion"))) {
        Usuario usuario = (Usuario) datos.get("datos");
      	this.composer.setUsuarioReducido(usuario);
        this.composer.validacionesReparticion(usuario);
      }
      if ("asignarTarea".equals(datos.get("funcion")))
        this.composer.asignarTarea();
      if ("reasignarTareas".equals(datos.get("funcion")))
        this.composer.reasignarTareas();
    }
  }
}
