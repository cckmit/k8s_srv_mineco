package com.egoveris.deo.web.satra.supervisados;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.ISupervisadosService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.ValidarApoderamientoComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;

/**
 * Composer encargado de la administración de la vista de Inbox de tareas de los
 * supervisados.
 * 
 * @author bfontana
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SupervisadosInboxComposer extends ValidarApoderamientoComposer {

  private static final long serialVersionUID = 2914994937323557872L;

  private static Logger logger = LoggerFactory.getLogger(SupervisadosInboxComposer.class);
  
  @WireVariable("processEngine")
  private ProcessEngine processEngine;
  private List<Task> tasks = null;
  private Listbox tasksList;
  private AnnotateDataBinder binder;
  private Usuario supervisado;
  private Usuario usuarioReducido;
  
  @WireVariable("supervisadosServiceImpl")
  private ISupervisadosService supervisadosService;
  @WireVariable("firmaConjuntaServiceImpl")
  private FirmaConjuntaService firmaConjuntaService;
  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  @WireVariable("historialServiceImpl")
  private HistorialService historialService;

  private Popup subordinadosListPopUp;
  private Window supervisadosWindow;
  private Combobox usuarioDestino;
  private Toolbarbutton reasignacionButton;

  private List<Usuario> usuarios;

  @Override
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    component.addEventListener(Events.ON_USER, new SupervisadosInboxComposerListener(this));
    this.self.setAttribute("dontAskBeforeClose", true);
    this.supervisado = (Usuario) this.self.getDesktop().getAttribute("supervisado");
    if (this.supervisado != null) {
      this.supervisadosWindow.setTitle(Labels.getLabel("gedo.supervisados.buzonDe") + " "
          + this.supervisado.getNombreApellido());
      this.usuarioDestino.setModel(ListModels.toListSubModel(
          new ListModelList(getUsuarioService().obtenerUsuarios()), new UsuariosComparator(), 30));

    } else {
      Messagebox.show(Labels.getLabel("gedo.error.errorObteniendoDatosSupervisado"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR,
          new EventListener() {
            public void onEvent(Event evt) {
              return;
            }
          });
      this.closeWindow();
    }
  }

  public void onCreate$supervisadosWindow(Event event) {
    this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
  }

  public List<Task> getTasks() {
    this.tasks = this.getSupervisadoTasks();
    return this.tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  /**
   * Carga los datos del tipo de documento asociado a la tarea seleccionada.
   * 
   * @param currentSelectedTask
   * @return
   */
  private TipoDocumentoDTO cargarDatosTipoDocumento(Task currentSelectedTask) {
    String idTipoDocumento = (String) this.processEngine.getExecutionService()
        .getVariable(currentSelectedTask.getExecutionId(), Constantes.VAR_TIPO_DOCUMENTO);

    return this.tipoDocumentoService.buscarTipoDocumentoPorId(Integer.valueOf(idTipoDocumento));

  }

  public void onClick$terminarButton() throws InterruptedException {
    Set selectedItems = this.tasksList.getSelectedItems();
    if (selectedItems != null) {
      if (!selectedItems.isEmpty()) {
        Messagebox.show(Labels.getLabel("gedo.information.cancelarProceso"),
            Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
            Messagebox.QUESTION, new EventListener() {
              public void onEvent(Event evt) {
                switch (((Integer) evt.getData()).intValue()) {
                case Messagebox.YES:
                  cancelarProceso();
                  break;
                case Messagebox.NO:
                  break;
                }
              }
            });
      } else {
        Messagebox.show(Labels.getLabel("gedo.general.ningunaTareaSeleccionada"),
            Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.general.ningunaTareaSeleccionada"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  public void onClick$advocacionButton() throws InterruptedException {
    Set<Listitem> selectedItems = this.tasksList.getSelectedItems();
    String mensaje = Labels.getLabel("gedo.general.avocarseTareas");
    if (selectedItems != null) {
      if (!selectedItems.isEmpty()) {
        if (!this.validarTareasFirmaConjunta(selectedItems)) {
          Messagebox.show(Labels.getLabel("gedo.supervisados.error.incluyeFirmaConjunta"),
              Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
          this.limpiarSeleccion();
          return;
        }
        if (selectedItems.size() == 1) {
          boolean mensajeFirmaConjunta = this.esTareaFirmaConjunta(selectedItems);
          if (mensajeFirmaConjunta)
            mensaje = Labels.getLabel("gedo.supervisados.avocarseTareasFirmaConjunta");
        }
        Messagebox.show(mensaje, Labels.getLabel("gedo.general.question"),
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
        Messagebox.show(Labels.getLabel("gedo.general.ningunaTareaSeleccionada"),
            Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.general.ningunaTareaSeleccionada"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  public void onClick$cerrar() {
    this.closeWindow();
  }

  private void closeWindow() {
    ((Window) this.self).onClose();
  }

  /**
   * Evalua condiciones para continuar o no con la reasignación.
   * 
   * @throws InterruptedException
   */
	public void onClick$reasignacionButton() throws InterruptedException {
		Set<Listitem> selectedItems = this.tasksList.getSelectedItems();
		if (!selectedItems.isEmpty()) {
			if (this.validarTareasFirmaConjunta(selectedItems)) {
				if (!this.reasignacionButton.getChildren().contains(this.subordinadosListPopUp)) {
					this.reasignacionButton.setPopup(this.subordinadosListPopUp);
				}
				this.subordinadosListPopUp.open(this.reasignacionButton);
				this.binder.loadComponent(reasignacionButton);
			} else {
				Messagebox.show(Labels.getLabel("gedo.supervisados.error.incluyeFirmaConjunta"),
						Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
				this.limpiarSeleccion();
			}
		} else {
			Messagebox.show(Labels.getLabel("gedo.general.ningunaTareaSeleccionada"),
					Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

  public void onClick$asignarTareas() throws InterruptedException, SecurityNegocioException {
		if (this.usuarioDestino.getSelectedItem() == null) {
			throw new WrongValueException(usuarioDestino, Labels.getLabel("gedo.error.usuarioInexistente"));
		}
    Set<Listitem> selectedItems = this.tasksList.getSelectedItems();
    if (!selectedItems.isEmpty()) {
    	usuarioReducido = (Usuario) this.usuarioDestino.getSelectedItem().getValue();
      Usuario us = this.getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());
      Map<String, Object> datos = new HashMap<>();
      datos.put("funcion", "validarApoderamiento");
      datos.put("datos", us);
      enviarBloqueoPantalla(datos);
    } else {
      Messagebox.show(Labels.getLabel("gedo.general.ningunaTareaSeleccionada"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  /**
   * Validaciones de adoderamiento y usuarios que pertenecen a la misma
   * repartición.
   * 
   * @throws InterruptedException
   */
  public void validarUsuarios(Usuario usuarioAValidar) throws InterruptedException {
    super.validarApoderamiento(usuarioAValidar, null);
  }

  void avocarseTareas() {
    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(Constantes.SESSION_USERNAME);
    Set selectedItems = this.tasksList.getSelectedItems();
    if (selectedItems != null) {
      Iterator it = selectedItems.iterator();
      while (it.hasNext()) {
        Listitem li = (Listitem) it.next();
        Task currentSelectedTask = this.tasks.get(li.getIndex());
        TipoDocumentoDTO tipoDocumento = cargarDatosTipoDocumento(currentSelectedTask);
        supervisadosService.reasignarTarea(currentSelectedTask, loggedUsername);
        this.cambiarHistorial(currentSelectedTask, this.getCurrentUser(), loggedUsername);
        if (tipoDocumento.getEsFirmaConjunta() && currentSelectedTask.getActivityName()
            .compareTo(Constantes.TASK_FIRMAR_DOCUMENTO) == 0) {
          this.reemplazarFirmante(currentSelectedTask, loggedUsername);
        }
        this.processEngine.getExecutionService().setVariable(currentSelectedTask.getExecutionId(),
            Constantes.VAR_USUARIO_APODERADOR, StringUtils.EMPTY);
      }
      this.binder.loadComponent(this.tasksList);
    }
  }

  void reasignarTareas(Usuario usuarioReducido) throws SecurityNegocioException {
    Usuario us = this.getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());
    Usuario usuarioApoderado = super.getUsuarioApoderado(us);
    Set selectedItems = this.tasksList.getSelectedItems();
    if (selectedItems != null) {
      Iterator it = selectedItems.iterator();
      while (it.hasNext()) {
        Listitem li = (Listitem) it.next();
        Task currentSelectedTask = this.tasks.get(li.getIndex());
        supervisadosService.reasignarTarea(currentSelectedTask, us.getUsername());
        this.cambiarHistorial(currentSelectedTask, this.getCurrentUser(), us.getUsername());
        TipoDocumentoDTO tipoDocumento = cargarDatosTipoDocumento(currentSelectedTask);
        if (tipoDocumento.getEsFirmaConjunta() && currentSelectedTask.getActivityName()
            .compareTo(Constantes.TASK_FIRMAR_DOCUMENTO) == 0) {
          this.reemplazarFirmante(currentSelectedTask, us.getUsername());
        }
        if (usuarioApoderado != null) {
          this.processEngine.getExecutionService().setVariable(
              currentSelectedTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR,
              us.getUsername());
        } else {
          this.processEngine.getExecutionService().setVariable(
              currentSelectedTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR,
              StringUtils.EMPTY);
        }
      }
      this.binder.loadComponent(this.tasksList);
      Clients.clearBusy();
    }
  }

	/**
	 * Cambiar historial.
	 *
	 * @param task the task
	 * @param de   the de
	 * @param para the para
	 */
	public void cambiarHistorial(Task task, String de, String para) {
		this.historialService.actualizarHistorial(task.getExecutionId());
		ProcessInstance pInstance = this.processEngine.getExecutionService()
				.findProcessInstanceById(task.getExecutionId());
		// El inicio del documento, y del historial
		String mensaje = Labels.getLabel("deo.misSupervisados.reasignacion.historial.mensaje", new String[] { de });

		HistorialDTO inicio = new HistorialDTO(para, task.getActivityName(), pInstance.getId());
		inicio.setMensaje(mensaje);
		inicio.setFechaInicio(new Date());
		this.historialService.guardarHistorial(inicio);
	}

  void cancelarProceso() {
    Set selectedItems = this.tasksList.getSelectedItems();
    if (selectedItems != null) {
      Iterator it = selectedItems.iterator();
      while (it.hasNext()) {
        Listitem li = (Listitem) it.next();
        try {
          Task currentSelectedTask = this.tasks.get(li.getIndex());
          supervisadosService.cancelarTarea(currentSelectedTask, this.getCurrentUser());
        } catch (Exception e) {
          logger.error("Error al cancelar proceso. " + e.getMessage(), e);
        }
      }
      this.binder.loadComponent(this.tasksList);
    }
  }

  List<Task> getSupervisadoTasks() {
    if (this.supervisado == null) {
      return new ArrayList<>();
    }
    
    if (Executions.getCurrent().getDesktop() != null) {
      logger.debug("Obteniendo tareas personales del usuario supervisados "
          + this.supervisado.getUsername());

      if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
        return this.supervisadosService
            .obtenerTareasSupervisadosCCOO(this.supervisado.getUsername());
      } else {
        if (this.processEngine != null) {
          return this.processEngine.getTaskService().createTaskQuery()
              .assignee(this.supervisado.getUsername()).orderDesc(TaskQuery.PROPERTY_CREATEDATE)
              .list();
        }else{
          Messagebox.show(Labels.getLabel("gedo.bandejaCo.msgbox.noComunicacion"),
              Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
              Messagebox.ERROR);
        }
      }
    }
    return new ArrayList<>();
  }

  public List<Usuario> getUsuarios() {
    try {
      this.usuarios = getUsuarioService().obtenerUsuarios("*");
    } catch (SecurityNegocioException e) {
      logger.error("Mensaje de error", e);
    }
    return this.usuarios;
  }

  /**
   * Reemplaza el usuario firmante para el documento que exija firma conjunta.
   * 
   * @param currentSelectedTask
   * @param nuevoUsuario
   * @throws InterruptedException
   */
  private void reemplazarFirmante(Task currentSelectedTask, String nuevoUsuario) {
    String workflowId = currentSelectedTask.getExecutionId();
    String usuarioAsignado = currentSelectedTask.getAssignee();
    try {
      String usuarioApoderador = (String) this.processEngine.getExecutionService()
          .getVariable(currentSelectedTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR);
      if (StringUtils.isEmpty(usuarioApoderador)) {
        this.firmaConjuntaService.reemplazarFirmante(usuarioAsignado, nuevoUsuario, workflowId);
      } else {
        this.firmaConjuntaService.reemplazarFirmante(usuarioApoderador, nuevoUsuario, workflowId);
      }
    } catch (Exception e) {
      logger.error("Error al reemplazar firmante. " + e.getMessage(), e);
      Messagebox.show(Labels.getLabel("gedo.general.error"),
          Labels.getLabel("gedo.error.reemplazarFirmantes"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Realiza validaciones para impedir la asignación o avocación de tareas de
   * firma conjunta, de manera colectiva.
   * 
   * @param selectedItems
   * @return
   * @throws InterruptedException
   */
  private boolean validarTareasFirmaConjunta(Set<Listitem> selectedItems) {
    boolean continuar = true;
    if (selectedItems.size() > 1) {
      for (Listitem listitem : selectedItems) {
        Task currentSelectedTask = this.tasks.get(listitem.getIndex());
        if (currentSelectedTask.getActivityName()
            .compareTo(Constantes.TASK_FIRMAR_DOCUMENTO) == 0) {
          TipoDocumentoDTO tipoDocumento = cargarDatosTipoDocumento(currentSelectedTask);
          // FIXME: REVISAR ESTA VALIDACION DADO QUE NO SE SI TIENE
          // SENTIDO EL TIPO DE PRODUCCION.
          if (tipoDocumento.getEsFirmaConjunta()
              && tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_IMPORTADO)
            continuar = false;
        }
      }
    }
    return continuar;
  }

  /**
   * Limpiar checkbox seleccionados.
   */
  private void limpiarSeleccion() {
    this.tasksList.setCheckmark(true);
    this.binder.loadComponent(this.tasksList);
  }

  /**
   * Evalúa condiciones de tarea de firma conjunta, con el fin de presentarle a
   * los usuarios, mensajes referentes a las mismas.
   * 
   * @param selectedItems
   * @return
   */
  private boolean esTareaFirmaConjunta(Set<Listitem> selectedItems) {
    boolean tareaFirmaConjunta = false;
    Iterator<Listitem> it = selectedItems.iterator();
    Listitem listItem = it.next();
    Task currentSelectedTask = this.tasks.get(listItem.getIndex());
    if (currentSelectedTask.getActivityName().compareTo(Constantes.TASK_FIRMAR_DOCUMENTO) == 0) {
      TipoDocumentoDTO tipoDocumento = cargarDatosTipoDocumento(currentSelectedTask);
      if (tipoDocumento.getEsFirmaConjunta()
          && tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_IMPORTADO) {
        String usuarioApoderador = (String) processEngine.getExecutionService()
            .getVariable(currentSelectedTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR);
        String usuarioFirmanteBuscar;
        if (usuarioApoderador == null) {
          usuarioFirmanteBuscar = currentSelectedTask.getAssignee();
        } else {
          usuarioFirmanteBuscar = usuarioApoderador;
        }

        FirmanteDTO firmante = this.firmaConjuntaService.buscarFirmante(usuarioFirmanteBuscar,
            currentSelectedTask.getExecutionId(), false);
        if (firmante.getOrden().intValue() != 1)
          tareaFirmaConjunta = true;
      }
    }
    return tareaFirmaConjunta;
  }

  /**
   * Identifica si la tarea es de firma conjunta y evalua la respuesta del
   * usuario, ante la pregunta de confirmación de ejecución del proceso.
   * 
   * @throws SecurityNegocioException
   */
  private void validarFirmaConjuntaReasignacion()
      throws InterruptedException, SecurityNegocioException {
		String mensaje = Labels.getLabel("gedo.general.reasignar", new String[] { usuarioReducido.getUsername() });
    Set<Listitem> selectedItems = this.tasksList.getSelectedItems();
    if (selectedItems.size() == 1) {
      boolean mensajeFirmaConjunta = this.esTareaFirmaConjunta(selectedItems);
      if (mensajeFirmaConjunta)
        mensaje = Labels.getLabel("gedo.supervisados.reasignarTareasFirmaConjunta");
    }
    Clients.clearBusy();
    Messagebox.show(mensaje, Labels.getLabel("gedo.general.question"),
        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              Map<String, Object> datos = new HashMap<String, Object>();
              datos.put("funcion", "reasignarTareas");
							datos.put("datos", usuarioReducido);
              enviarBloqueoPantalla(datos);
              break;
            case Messagebox.NO:
              return;
            }
          }
        });
    return;
  }

  @Override
  protected void asignarTarea() throws InterruptedException, SecurityNegocioException {
    validarFirmaConjuntaReasignacion();

  }

  @Override
  protected void enviarBloqueoPantalla(Object data) {
    Clients.showBusy(Labels.getLabel("gedo.general.procesando.procesandoSolicitud"));
    Events.echoEvent(Events.ON_USER, this.self, data);
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
      if ("asignarTarea".equals(datos.get("funcion"))) {
        this.composer.asignarTarea();
      }
      if ("reasignarTareas".equals(datos.get("funcion"))) {
      	 Usuario usuario = (Usuario) datos.get("datos");
         this.composer.reasignarTareas(usuario);
      }
    }
  }
}
