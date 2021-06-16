package com.egoveris.deo.web.satra;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Paginal;

import com.egoveris.deo.base.exception.SinPersistirException;
import com.egoveris.deo.base.service.AvisoService;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.ITaskViewService;
import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.deo.model.model.AvisoDTO;
import com.egoveris.deo.model.model.MacroEventData;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

/**
 * Composer encargado de la administración de la vista de Inbox de tareas.
 * 
 * @author bfontana
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InboxComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -2798957889853349677L;

	private static Logger logger = LoggerFactory.getLogger(InboxComposer.class);
	private static final String USULIST = "usuariosListPopUp";
	private static final String ERRACT = "gedo.inboxComposer.msgbox.errorActual";
	private static final String VISTAERR = "gedo.supervisadosComposer.msg.noPosibleIniciarVista";
	private static final String COMERR = "gedo.supervisadosComposer.msg.errorComunicacion";

	private Window inboxWindow;
	private Window taskView;
	private Window crearAlertaView;
	private Listbox tasksList;
	private Listbox avisosList;
	private Listheader createDateTask;
	private transient Paginal avisosPaginator;
	private transient Paginal taskPaginator;
	private transient Paginal avisosAlertasPaginator;
	private Toolbar toolbarAvisos;

	private Div vistaAvisos;
	private Div alertasCo;

	@WireVariable("processEngine")
	private ProcessEngine processEngine;
	private List<Task> tasks = null;
	private Task selectedTask = null;
	private AvisoDTO selectedAviso = null;
	private AnnotateDataBinder binder;
	private String usuarioActual;
	private Combobox usuarioDestino;
	private String estadoExpediente = null;

	private List<AvisoDTO> avisos = null;

	@WireVariable("avisoServiceImpl")
	private AvisoService avisoService = null;

	@WireVariable("taskViewImpl")
	private ITaskViewService taskViewService;
	private ExternalPagingSorting extPagSort = null;
	private ExternalPagingSorting extTaskPagSort = null;

	@WireVariable("usuarioServiceImpl")
	private IUsuarioService usuarioService;

	@WireVariable("gestionArchivosWebDavServiceImpl")
	private GestionArchivosWebDavService gestionArchivosWebDavService;

	@WireVariable("buscarDocumentosGedoServiceImpl")
	private BuscarDocumentosGedoService buscarDocumentosGedoService;

	@Override
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		this.binder = new AnnotateDataBinder(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		c.addEventListener(Events.ON_NOTIFY, new InboxOnNotifyWindowListener(this));
		c.addEventListener(Events.ON_USER, new InboxOnNotifyWindowListener(this));

		// Implementación de sistema de avisos.
		this.usuarioActual = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(Constantes.SESSION_USERNAME);

		this.usuarioDestino.setModel(ListModels.toListSubModel(new ListModelList(getUsuarioService().obtenerUsuarios()),
				new UsuariosComparator(), 30));
		initPagSort();
		initTaskPagSort();
		createDateTask.sort(true, true);

		if (Executions.getCurrent().getParameter(Constantes.VAR_NUMERO_SA) != null) {
			String numeroSade = Executions.getCurrent().getParameter(Constantes.VAR_NUMERO_SA);
			estadoExpediente = numeroSade;
			populateTasks();
			redireccionarATareaWorflowPorURL(numeroSade);

		} else {
			estadoExpediente = null;
		}
	}

	@SuppressWarnings("unchecked")

	public void onCreate$inboxWindow() throws InterruptedException {
		Map<String, Object> pathMap = (Map<String, Object>) Executions.getCurrent().getSession()
				.getAttribute(Constantes.PATHMAP);
		if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
			this.vistaAvisos.setVisible(false);
			this.alertasCo.setVisible(true);
			this.inboxWindow.setHeight("700px");
		}
		if (!CollectionUtils.isEmpty(pathMap)) {
			String keyWord = (String) pathMap.get(Constantes.KEYWORD);
			if (StringUtils.isNotEmpty(keyWord) && keyWord.equals(Constantes.KEYWORD_TAREAS)) {
				if ((Boolean) pathMap.get(Constantes.REDIRECT) != null && !(Boolean) pathMap.get(Constantes.REDIRECT)) {
					Executions.getCurrent().getSession().removeAttribute(Constantes.PATHMAP);
					String taskId = (String) pathMap.get("ID1");
					if (taskId != null && !taskId.trim().isEmpty()) {
						this.redireccionarATareaPorURL(taskId);
					}
				}
			}
			if (StringUtils.isNotEmpty(keyWord) && keyWord.equals(Constantes.KEYWORD_TAREAS_WORFLOW)) {
				if ((Boolean) pathMap.get(Constantes.REDIRECT) != null && !(Boolean) pathMap.get(Constantes.REDIRECT)) {
					Executions.getCurrent().getSession().removeAttribute(Constantes.PATHMAP);
					String workflowId = (String) pathMap.get("ID1");
					if (workflowId != null && !workflowId.trim().isEmpty()) {
						this.redireccionarATareaWorflowPorURL(Constantes.ACRONIMO_WORKFLOW + "." + workflowId);
					}
				}
			}

		}
	}

	public void onLevantarAlerta() {
		this.crearAlertaView = (Window) Executions.createComponents("/co/alertaCO.zul", this.self, null);
		this.crearAlertaView.setClosable(true);
		try {
			this.crearAlertaView.setPosition("center");
			this.crearAlertaView.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error("Error al generar la ventana alertaCO.zul", e);
		}
	}

	public void onDetalleAlerta() {
		Map<String, Object> map = new HashMap<>();
		this.crearAlertaView = (Window) Executions.createComponents("/co/detalleAlertaCO.zul", this.self, map);
		this.crearAlertaView.setClosable(true);
		try {
			this.crearAlertaView.setPosition("center");
			this.crearAlertaView.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error("Error al generar la ventana detalleAlertaCO.zul", e);
		}
	}

	public void onEliminarAlerta() {
		Messagebox.show(Labels.getLabel("ccoo.alertaCO.eliminar.alerta"), Labels.getLabel("ccoo.alertaCO.eliminar"),
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if ("onYes".equals(evt.getName())) {
							eliminarAlertaSeleccionada();
						}
					}
				});
	}

	protected void eliminarAlertaSeleccionada() {
		refreshModelListAvisosAlerta();
	}

	private void refreshModelListAvisosAlerta() {
		this.binder.loadAll();
	}

	/**
	 * Método que redirecciona a la tarea cuyo workFlowId llega como parametro
	 * por medio de la url ejemplo:
	 * "/gedo-web/u/tareasworflow/procesoGedo.123456"
	 * 
	 * @author lfishkel
	 * @param parameter
	 * @throws InterruptedException
	 */
	public void redireccionarATareaWorflowPorURL(String parameter) throws InterruptedException {
		boolean encontro = false;
		for (Task task : this.tasks) {
			if (task.getExecutionId().equals(parameter)) {
				encontro = true;
				this.selectedTask = task;
				break;
			}
		}
		if (encontro) {
			this.onExecuteTask();
		} else {
			Messagebox.show(Labels.getLabel("gedo.inboxComposer.msgbox.numTareaInexistente"));
		}
	}

	/**
	 * Método que redirecciona a la tarea cuyo id llega como parametro por medio
	 * de la url ejemplo: "/gedo-web/u/tareas/1"
	 * 
	 * @author lfishkel
	 * @param parameter
	 * @throws InterruptedException
	 */
	public void redireccionarATareaPorURL(String parameter) throws InterruptedException {
		boolean encontro = false;
		for (Task task : this.tasks) {
			if (task.getId().equals(parameter)) {
				encontro = true;
				this.selectedTask = task;
				break;
			}
		}
		if (encontro) {
			this.onExecuteTask();
		} else {
			Messagebox.show(Labels.getLabel("gedo.inboxComposer.msgbox.numTareaInexistente"));
		}
	}

	/**
	 * Inicializa el helper de paginacion-ordenamiento. Adiciona al componente
	 * de visualización un listener para escuhar eventos enviados desde el
	 * helper.
	 * 
	 */
	public void initPagSort() {
		extPagSort = new ExternalPagingSorting(avisosList, avisosPaginator);
		extPagSort.setObjetoModelo(this.avisoService);
		extPagSort.setNombreMetodo("buscarAvisosPorUsuario");

		Map<String, Object> parametrosConsulta = new HashMap<>();
		parametrosConsulta.put("usuario", usuarioActual);
		extPagSort.setParametrosConsulta(parametrosConsulta);

		avisosList.addEventListener(Events.ON_NOTIFY, new EventListener() {
			@SuppressWarnings("unchecked")
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals(Events.ON_NOTIFY)) {
					avisos = ((List<AvisoDTO>) extPagSort.getDatos());
					binder.loadComponent(avisosList);
				}
			}
		});
	}

	/**
	 * Inicializa el helper de paginacion-ordenamiento de Tasks. Adiciona al
	 * ListBox un listener para escuhar eventos enviados desde el helper.
	 * 
	 */
	public void initTaskPagSort() {
		extTaskPagSort = new ExternalPagingSorting(tasksList, taskPaginator);
		extTaskPagSort.setObjetoModelo(this.taskViewService);

		if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
			extTaskPagSort.setNombreMetodo("buscarTasksPorUsuarioYComunicable");
		} else {
			extTaskPagSort.setNombreMetodo("buscarTasksPorUsuario");
		}

		Map<String, Object> parametrosConsulta = new HashMap<>();
		parametrosConsulta.put("usuario", usuarioActual);
		extTaskPagSort.setParametrosConsulta(parametrosConsulta);

		tasksList.addEventListener(Events.ON_NOTIFY, new EventListener() {
			@SuppressWarnings("unchecked")
			public void onEvent(Event event) throws Exception {

				if (event.getName().equals(Events.ON_NOTIFY)) {
					tasks = ((List<Task>) extTaskPagSort.getDatos());
					binder.loadComponent(tasksList);
				}
			}
		});
	}

	public Window getTaskView() {
		return taskView;
	}

	public void setTaskView(Window taskView) {
		this.taskView = taskView;
	}

	public Task getSelectedTask() {
		return this.selectedTask;
	}

	public void setSelectedTask(Task aTask) {
		this.selectedTask = aTask;
	}

	/**
	 * @return the selectedAviso
	 */
	public AvisoDTO getSelectedAviso() {
		return this.selectedAviso;
	}

	/**
	 * @param selectedAviso
	 *            the selectedAviso to set
	 */
	public void setSelectedAviso(AvisoDTO selectedAviso) {
		this.selectedAviso = selectedAviso;
	}

	public List<Task> getTasks() {
		if (tasks == null) {
			populateTasks();
		}
		return tasks;
	}

	@SuppressWarnings("unchecked")
	public List<Task> populateTasks() {
		extTaskPagSort.setCriterio(TaskQuery.PROPERTY_CREATEDATE);
		extTaskPagSort.setOrden("descending");
		tasks = (List<Task>) extTaskPagSort.cargarDatos();
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Método package (sin public, private, ni protected al principio) para
	 * permitir accederlo desde InboxOnNotifyWindowListener
	 * 
	 * @return La lista de tareas correspondiente al usuario logueado.
	 */
	List<Task> getPersonalTasks(String exId) {
		return this.processEngine.getTaskService().createTaskQuery().assignee(this.usuarioActual).executionId(exId)
				.list();
	}

	public void onCreateNewDocument() {
		Clients.showBusy("Procesando");
		// Envias el evento
		Events.echoEvent("onUser", this.self, "nuevoDocumento");
		// bloqueamos toda la pantalla tras el popup
	}

	public void crearNuevoDocumento() {

		if (this.taskView != null) {
			this.taskView.invalidate();
			this.taskView = (Window) Executions.createComponents("iniciarDocumento.zul", this.self,
					new HashMap<String, Object>());
			this.taskView.setParent(this.inboxWindow);
			this.taskView.setPosition("center");
			this.taskView.setVisible(true);
			this.taskView.doModal();
		} else {
			Messagebox.show(Labels.getLabel(VISTAERR), Labels.getLabel(COMERR), Messagebox.OK, Messagebox.ERROR);
		}
		Clients.clearBusy();
	}

	public void refreshInbox() {
		this.populateTasks();
		this.avisos = null;
		this.binder.loadAll();
	}

	public void onExecuteTask() throws InterruptedException {

		if (this.selectedTask == null || this.selectedTask.getFormResourceName() == null) {
			Messagebox.show(Labels.getLabel("gedo.inbox.error.cont_tarea"), Labels.getLabel("gedo.inbox.error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (this.taskView != null) {
			this.taskView.detach();
			Map<String, Object> variables = new HashMap<>();
			variables.put("selectedTask", this.selectedTask);

			if (estadoExpediente != null) {
				variables.put(Constantes.VAR_NUMERO_SA, Constantes.VAR_NUMERO_SA);
				this.self.getDesktop().setAttribute(Constantes.VAR_NUMERO_SA, Constantes.VAR_NUMERO_SA);
			} else {
				variables.put(Constantes.VAR_NUMERO_SA, null);
				this.self.getDesktop().setAttribute(Constantes.VAR_NUMERO_SA, null);
			}

			this.self.getDesktop().setAttribute("selectedTask", this.selectedTask);

			this.taskView = (Window) Executions.createComponents(this.selectedTask.getFormResourceName(), this.self,
					variables);
			this.taskView.setParent(inboxWindow);
			this.taskView.setPosition("center");
			this.taskView.setVisible(true);
			try {
				this.taskView.doModal();
			} catch (SuspendNotAllowedException snae) {
				LoggerFactory.getLogger(InboxComposer.class).error("Error al intentar abrir GUI de la tarea", snae);
			}
		} else {
			Messagebox.show(Labels.getLabel(VISTAERR), Labels.getLabel(COMERR), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onBuscarEnMisTareas() {
		Clients.showBusy("Procesando...");
		Events.echoEvent("onUser", this.self, "mistareas");
	}

	/**
	 * Carga la ventana con el historial de las tareas en las que participa el
	 * usuario.
	 */
	public void cargarHistorial() {
		if (this.taskView != null) {
			this.taskView.invalidate();
			this.taskView = (Window) Executions.createComponents("misProcesos.zul", this.self,
					new HashMap<String, Object>());
			this.taskView.setParent(this.inboxWindow);
			this.taskView.setPosition("center");
			this.taskView.setVisible(true);
			this.taskView.doModal();
		} else {
			Messagebox.show(Labels.getLabel(VISTAERR), Labels.getLabel(COMERR), Messagebox.OK, Messagebox.ERROR);
		}
		Clients.clearBusy();
	}

	public void setTasksList(Listbox tasksList) {
		this.tasksList = tasksList;
	}

	public Listbox getTasksList() {
		return tasksList;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public ExternalPagingSorting getExtPagSort() {
		return extPagSort;
	}

	public void setExtPagSort(ExternalPagingSorting extPagSort) {
		this.extPagSort = extPagSort;
	}

	public ExternalPagingSorting getExtTaskPagSort() {
		return extTaskPagSort;
	}

	public void setExtTaskPagSort(ExternalPagingSorting extTaskPagSort) {
		this.extTaskPagSort = extTaskPagSort;
	}

	public Listbox getAvisosList() {
		return avisosList;
	}

	public void setAvisosList(Listbox avisosList) {
		this.avisosList = avisosList;
	}

	public Paginal getAvisosPaginator() {

		/*
		 * Carga el número total de avisos, para que el paginador determine la
		 * cantidad de páginas.
		 */
		this.avisosPaginator.setTotalSize(avisoService.numeroAvisosPorUsuario(usuarioActual));
		habilitarBotonesAvisos();
		return avisosPaginator;
	}

	public void setAvisosPaginator(Paginal avisosPaginator) {
		this.avisosPaginator = avisosPaginator;
	}

	public Paginal getTaskPaginator() {
		if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
			this.taskPaginator.setTotalSize(taskViewService.countTasksComunicables(usuarioActual));
		} else {
			this.taskPaginator.setTotalSize(taskViewService.countTasks(usuarioActual));
		}

		return taskPaginator;
	}

	public void setTaskPaginator(Paginal taskPaginator) {
		this.taskPaginator = taskPaginator;
	}

	public Paginal getAvisosAlertasPaginator() {
		return avisosAlertasPaginator;
	}

	public void setAvisosAlertasPaginator(Paginal avisosAlertasPaginator) {
		this.avisosAlertasPaginator = avisosAlertasPaginator;
	}

	public AvisoService getAvisoService() {
		return avisoService;
	}

	public void setAvisoService(AvisoService avisoService) {
		this.avisoService = avisoService;
	}

	@SuppressWarnings("unchecked")
	public List<AvisoDTO> getAvisos() {
		if (avisos == null) {
			extPagSort.setCriterio("fechaEnvio");
			extPagSort.setOrden("descending");
			avisos = (List<AvisoDTO>) extPagSort.cargarDatos();
		}
		return avisos;
	}

	public void setAvisos(List<AvisoDTO> avisos) {
		this.avisos = avisos;
	}

	public Combobox getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(Combobox usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	/**
	 * Confirma si el usuario esta seguro de eliminar todos los avisos.
	 */
	public void onEliminarTodosAvisos() {
		Messagebox.show(Labels.getLabel("gedo.inbox.avisos.eliminarTodosConfirmacion"),
				Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new EventListener() {
					public void onEvent(Event evt) {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							eliminarTodosAvisos();
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	/**
	 * Elimina todos los avisos del usuario actual
	 * 
	 * @throws InterruptedException
	 */
	public void eliminarTodosAvisos() {
		try {
			this.avisoService.eliminarAvisos(usuarioActual);
			this.avisos = null;
			this.binder.loadAll();
		} catch (DataAccessLayerException dae) {
			logger.error("Error al eliminar todos los avisos ", dae.getMessage());
			Messagebox.show(Labels.getLabel("gedo.inboxComposer.msg.noPosibleEliminarAvisos"), Labels.getLabel(ERRACT),
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * Redirigir los avisos seleccionados, abre la ventana de popup siempre que
	 * se superen las validaciones.
	 */
	public void onRedirigirAvisosSeleccionados() {
		if (this.avisosList.getSelectedItems().isEmpty()) {
			this.usuarioDestino.setSelectedItem(null);
			throw new WrongValueException(this.inboxWindow.getFellow("seleccionAviso"),
					Labels.getLabel("gedo.inbox.avisos.noAvisosSeleccionados"));
		} else {
			abrirCerrarPopup(true);
		}

	}

	/**
	 * Redirigir los avisos seleccionados al usuario designado
	 * 
	 * @throws SecurityNegocioException
	 */
	public void onClick$redirigirAvisos() {
		if (this.usuarioDestino.getSelectedItem() == null) {
			throw new WrongValueException(this.inboxWindow.getFellow(USULIST),
					Labels.getLabel("gedo.error.usuarioInexistente"));
		}
		Usuario usuarioReducido = this.usuarioDestino.getSelectedItem().getValue();
		String usuarioAEnviar = usuarioReducido.getUsername();
		Set<?> itemsSeleccionados = this.avisosList.getSelectedItems();
		List<AvisoDTO> avisosSeleccionados = new ArrayList<>();
		for (Object object : itemsSeleccionados) {
			Listitem li = (Listitem) object;
			AvisoDTO avisoSelected = this.avisos.get(li.getIndex());
			avisosSeleccionados.add(avisoSelected);
		}
		try {
			this.avisoService.redirigirAvisos(avisosSeleccionados, usuarioAEnviar);
			this.avisos = null;
			this.usuarioDestino.setSelectedItem(null);
			this.abrirCerrarPopup(false);
			this.binder.loadAll();
		} catch (SinPersistirException spe) {
			logger.error("Error al redirigir avisos. " + spe.getMessage(), spe);
			Messagebox.show(Labels.getLabel("gedo.inboxComposer.msgbox.noPosibleRedirigirAvisos"),
					Labels.getLabel(ERRACT), Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * Elimina los avisos seleccionados
	 * 
	 * @throws InterruptedException
	 */
	public void onEliminarAvisosSeleccionados() throws InterruptedException {
		if (this.avisosList.getSelectedItems().size() > 1) {
			Messagebox.show(Labels.getLabel("gedo.inbox.avisos.eliminarSeleccionConfirmacion"),
					Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								eliminarAvisosSeleccionados();
								break;
							case Messagebox.NO:
								break;
							}
						}
					});
		} else {
			eliminarAvisosSeleccionados();
		}

	}

	/**
	 * Elimina los avisos seleccionados
	 */
	public void eliminarAvisosSeleccionados() {
		Set<?> itemsSeleccionados = this.avisosList.getSelectedItems();
		if (itemsSeleccionados.isEmpty()) {
			throw new WrongValueException(this.inboxWindow.getFellow("seleccionAviso"),
					Labels.getLabel("gedo.inbox.avisos.noAvisosSeleccionados"));
		}
		List<AvisoDTO> avisosSeleccionados = new ArrayList<>();
		for (Object object : itemsSeleccionados) {
			Listitem li = (Listitem) object;
			AvisoDTO avisoSelected = this.avisos.get(li.getIndex());
			avisosSeleccionados.add(avisoSelected);
		}
		try {
			this.avisoService.eliminarAvisos(avisosSeleccionados);
			this.avisos = null;
			this.binder.loadAll();

		} catch (DataAccessLayerException dae) {
			logger.error("Error al eliminar avisos seleccionados. " + dae.getMessage(), dae);
			Messagebox.show(Labels.getLabel("gedo.inboxComposer.msgbox.noPosibleEliminarAvisos"),
					Labels.getLabel(ERRACT), Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * Descarga el documento firmado al que corresponde el aviso seleccionado
	 */
	public void onVisualizarDocumentoAviso() {
		AvisoDTO aviso = this.avisos.get(avisosList.getSelectedItem().getIndex());
		InputStream file;

		if (buscarDocumentosGedoService.buscarDocumentoPorNumero(aviso.getNumeroSadePapel())
				.getMotivoDepuracion() != null) {
			throw new WrongValueException(
					Labels.getLabel("gedo.inbox.avisos.EliminadoDepurado") + " " + buscarDocumentosGedoService
							.buscarDocumentoPorNumero(aviso.getNumeroSadePapel()).getMotivoDepuracion());
		} else {

			// WEBDAV
			file = gestionArchivosWebDavService.obtenerDocumento(aviso.getNumeroSadePapel()).getFileAsStream();

		}
		Filedownload.save(file, null, aviso.getNumeroSadePapel().concat(".pdf"));
	}

	/**
	 * Habilita o deshabilita los botones del toolbar de avisos, dependiendo de
	 * la existencia de avisos para el usuario.
	 */
	@SuppressWarnings("unchecked")
	private void habilitarBotonesAvisos() {
		boolean deshabilitar = true;
		if (avisosPaginator.getTotalSize() > 0)
			deshabilitar = false;

		List<Component> botones = this.toolbarAvisos.getChildren();
		for (Component toolbarbutton : botones) {
			if (toolbarbutton instanceof Toolbarbutton)
				((Toolbarbutton) toolbarbutton).setDisabled(deshabilitar);
		}
	}

	/**
	 * Dado que se realiza un manejo directo del popup para redirección de
	 * avisos, es necesario indicar de manera explícita el cierre o apertura del
	 * mismo.
	 * 
	 * @param abrir
	 *            , true para abrir popup, false para cerralo.
	 */
	private void abrirCerrarPopup(boolean abrir) {
		Toolbarbutton botonRedirigir = (Toolbarbutton) toolbarAvisos.getFellow("redirigirAvisosSeleccionados");
		Popup popup = (Popup) inboxWindow.getFellowIfAny(USULIST);
		if (popup == null) {
			popup = new Popup();
			popup.setId(USULIST);
		}
		if (abrir) {
			botonRedirigir.setPopup(popup);
			popup.open(botonRedirigir);
		} else {
			popup.close();
		}
	}

	public Toolbar getToolbarAvisos() {
		return toolbarAvisos;
	}

	public void setToolbarAvisos(Toolbar toolbarAvisos) {
		this.toolbarAvisos = toolbarAvisos;
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

}

/**
 * 
 * @author bfontana
 * 
 */
final class InboxOnNotifyWindowListener implements EventListener {
	private InboxComposer composer;

	public InboxOnNotifyWindowListener(InboxComposer comp) {
		this.composer = comp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(Event event) throws Exception {

		if (event.getName().equals(Events.ON_USER)) {
			if ("mistareas".equals(event.getData())) {
				this.composer.cargarHistorial();
			} else if ("nuevoDocumento".equals(event.getData())) {
				this.composer.crearNuevoDocumento();
			}
		} else if (event.getName().equals(Events.ON_NOTIFY)) {
			if (event.getData() == null) {
				this.composer.refreshInbox();
			} else {
				if (event.getData() instanceof MacroEventData) {
					MacroEventData med = (MacroEventData) event.getData();
					List<Task> tasks = this.composer.getPersonalTasks(med.getExecutionId());
					if (tasks != null && !tasks.isEmpty()) {
						this.composer.setSelectedTask(tasks.get(0));
						// Ejecutar la tarea
						this.composer.onExecuteTask();
					}
				} else {
					Map<String, Object> map = (Map<String, Object>) event.getData();
					String accion = (String) map.get("accion");
					if (accion != null && "eliminarAlertaAviso".equals(accion)) {
						this.composer.eliminarAlertaSeleccionada();
					}
				}
			}
		}
	}

}
