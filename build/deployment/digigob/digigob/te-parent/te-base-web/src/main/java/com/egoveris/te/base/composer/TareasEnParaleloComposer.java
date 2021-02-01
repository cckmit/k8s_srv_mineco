package com.egoveris.te.base.composer;

import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TareaParaleloInbox;
import com.egoveris.te.base.model.TramitacionLibre;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TareasEnParaleloComposer extends GenericForwardComposer {

  private static final long serialVersionUID = -1421481592013737596L;
  @Autowired
  private Window tareasEnParaleloWindow;
  private List<TareaParaleloInbox> listaTareas = new ArrayList<>();
  private TareaParaleloInbox tareaParaleloSelected;

  private List<TramitacionLibre> listaTareasTL = new ArrayList<>(); // listado
                                                                    // de tareas
                                                                    // en
                                                                    // Tramitacion
                                                                    // Libre
  private TramitacionLibre tareaTLSelected; // Tramitacion Libre

  @Autowired
  private Listbox tasksList;
  @Autowired
  private Listbox tasksListTL; // Tramitacion Libre
  private AnnotateDataBinder binder;
  private Map<String, String> detalles;
  private String username;
  private Task task;
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;
  private Task workingTask;

  // SERVICIOS
  @WireVariable(ConstantesServicios.TAREA_PARALELO_SERVICE)
  private TareaParaleloService tareaParaleloService;
  
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;

  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    c.addEventListener(Events.ON_NOTIFY, new InboxOnNotifyWindowListener(this));
    c.addEventListener(Events.ON_USER, new InboxOnNotifyWindowListener(this));

    username = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
        .toString();
 
    if (this.workingTask == null) {
      workingTask = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
    }

    this.binder = new AnnotateDataBinder(c);
    this.binder.loadAll();

    Events.sendEvent(c, new Event(Events.ON_NOTIFY));
  }

  public void onAdquirirTask() throws InterruptedException {
    Messagebox.show(Labels.getLabel("ee.general.advocarseTareaEnParalelo"),
        Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener() {
          public void onEvent(Event evt) {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              mostrarForegroundBloqueanteToken();
              Events.echoEvent("onUser", self, "adquirirTask");
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
  }

  /**
   * @return the tareaTLSelected
   */
  public TramitacionLibre getTareaTLSelected() {
    return this.tareaTLSelected;
  }

  /**
   * @param tareaTLSelected
   *          the tareaTLSelected to set
   */
  public void setTareaTLSelected(TramitacionLibre tareaTLSelected) {
    this.tareaTLSelected = tareaTLSelected;
  }

  /**
   * @return the processEngine
   */
  public ProcessEngine getProcessEngine() {
     
    return processEngine;
  }

  public void onAdquirirTLTask() throws InterruptedException {
    EventListener btnListener = new EventListener() {
      @Override
      public void onEvent(Event event) throws Exception {
        if (((Integer) event.getData()).intValue() == Messagebox.YES) {
          mostrarForegroundBloqueanteToken();

          try {
            TaskQuery taskQuery = getProcessEngine().getTaskService().createTaskQuery()
                .executionId(getTareaTLSelected().getEe().getIdWorkflow());

            task = taskQuery.uniqueResult();

            if (task != null) {
              // Le remuevo los grupos que puede tener si viene de tarea grupal
              List<Participation> listaGrupo = getProcessEngine().getTaskService()
                  .getTaskParticipations(task.getId());
              for (Participation grupo : listaGrupo) {
                getProcessEngine().getTaskService().removeTaskParticipatingGroup(task.getId(),
                    grupo.getGroupId(), Participation.CANDIDATE);
              }

              getProcessEngine().getTaskService().assignTask(task.getId(), username);
            }
          } catch (JbpmException e) {
            String messageAux = e.getMessage();
            String message = messageAux.substring(21);
            Messagebox.show(Labels.getLabel("ee.buzonGrupal.errorTareaTomada") + message,
                Labels.getLabel("ee.buzonGrupal.tituloTareaTomada"), Messagebox.OK,
                Messagebox.ERROR);

          } finally {
            Clients.clearBusy();
          }
          refreshInbox();
        }
      }
    };

    Messagebox.show(Labels.getLabel("te.tarea.libre"),
        Labels.getLabel("ee.general.question"), (Messagebox.YES | Messagebox.NO),
        Messagebox.QUESTION, btnListener);
  }

  private void mostrarForegroundBloqueanteToken() {
    Clients.showBusy(Labels.getLabel("ee.adquirirTarea.value"));
  }

  public void adquirirTask() throws InterruptedException {
    if (this.tareaParaleloSelected != null) {

      try {
        TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
            .executionId(this.tareaParaleloSelected.getIdTask());
        task = taskQuery.uniqueResult();

        if (task != null) {

          // Obtengo la tarea en paralelo de la tabla temporal
          TareaParaleloDTO tareaParalelo = this.tareaParaleloService
              .buscarTareaEnParaleloByIdTask(this.tareaParaleloSelected.getIdTask());
          ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
              .buscarExpedienteElectronico(this.tareaParaleloSelected.getIdExp());

          // Realizo un pase interno para que quede en el historial.
          // El destinatario es el usuario mismo por que se adquiere
          // la tarea.
          detalles = new HashMap<String, String>();
          detalles.put("destinatario", username);

          // El pase queda: origen: el usuario que tiene asignada la
          // tarea actualmente.
          // destino: el usuario que la adquiere (el que esta
          // logueado).
          // motivo: tarea adquirida.

          String motivo = Labels.getLabel("ee.tarea.adquirida");

          // Si task.getAssignee() tiene valor es por que la tarea
          // esta asignada a un usuario, de lo contrario
          // la tarea esta asignada a una reparticion y el usuario
          // generador del pase interno sera el usuario que se
          // adquiere la
          // tarea
          String userGenerador;
          if (task.getAssignee() != null) {
            userGenerador = task.getAssignee();
          } else {
            userGenerador = username;
          }
          this.expedienteElectronicoService.generarPaseExpedienteElectronico(task,
              expedienteElectronico, userGenerador, "Paralelo", motivo, detalles, false);

          // Realizo las modificaciones en la tabla de tareas en
          // paralelo.
          tareaParalelo.setUsuarioGrupoAnterior(userGenerador);
          tareaParalelo.setFechaPase(new Date());
          tareaParalelo.setEstado("Adquirida");
          tareaParalelo.setTareaGrupal(false);
          tareaParaleloService.modificar(tareaParalelo);

          // Se asigna a si mismo la tarea por que se la adquirió
          this.processEngine.getTaskService().assignTask(task.getId(), username); // TODO
                                                                                  // debería
                                                                                  // haber
                                                                                  // una
                                                                                  // transición
                                                                                  // a
                                                                                  // sí
                                                                                  // mismo
                                                                                  // en
                                                                                  // tarea
                                                                                  // Paralelo
                                                                                  // para
                                                                                  // reiniciar
                                                                                  // la
                                                                                  // fecha
                                                                                  // de
                                                                                  // creación
                                                                                  // de
                                                                                  // las
                                                                                  // tareas
                                                                                  // y
                                                                                  // los
                                                                                  // grupos
                                                                                  // asignados.

          // Le remuevo los grupos que puede tener si viene de tarea grupal
          List<Participation> listaGrupo = processEngine.getTaskService()
              .getTaskParticipations(task.getId());
          for (Participation grupo : listaGrupo) {
            processEngine.getTaskService().removeTaskParticipatingGroup(task.getId(),
                grupo.getGroupId(), Participation.CANDIDATE);
          }

          Messagebox.show(Labels.getLabel("ee.tramitacion.tareaParalelo.adquirir.value"),
              Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK,
              Messagebox.INFORMATION);

        }
      } catch (JbpmException e) {
        String messageAux = e.getMessage();
        String message = messageAux.substring(21);
        Messagebox.show(Labels.getLabel("ee.buzonGrupal.errorTareaTomada") + message,
            Labels.getLabel("ee.buzonGrupal.tituloTareaTomada"), Messagebox.OK, Messagebox.ERROR);

      } finally {
        Clients.clearBusy();
      }

    } else {
      Messagebox.show(Labels.getLabel("ee.inbox.error.grave"),
          Labels.getLabel("ee.errorMessageBox.title"), Messagebox.OK, Messagebox.ERROR);
      return;
    }

    this.refreshInbox();
  }

  final class InboxOnNotifyWindowListener implements EventListener {
    private TareasEnParaleloComposer composer;

    public InboxOnNotifyWindowListener(TareasEnParaleloComposer comp) {
      this.composer = comp;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        this.composer.refreshInbox();
      }
      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("adquirirTask")) {
          this.composer.adquirirTask();
        }
      }
    }

  }

  public void refreshInbox() {
    List<TareaParaleloInbox> tareasEnparalelo = tareaParaleloService
        .buscarTareasEnParaleloByUsername(username);
    this.listaTareas = ordenarTasks(tareasEnparalelo);
    this.tasksList.setModel(new BindingListModelList(this.listaTareas, true));
    this.binder.loadComponent(this.tasksList);
    // ----------- -----------
   // this.listaTareasTL = tareaParaleloService.findTramitacionLibre(username);
    this.tasksListTL.setModel(new BindingListModelList(listaTareasTL, true));
    this.binder.loadComponent(this.tasksListTL);
  }

  private List<TareaParaleloInbox> ordenarTasks(final List<TareaParaleloInbox> tasks) {
    Collections.sort(tasks, new TasksComparator());
    return tasks;
  }

  /**
   * 
   * @param name
   *          : nombre de la variable del WF que quiero encontrar
   * @return objeto guardado en la variable
   */
  public Object getVariableWorkFlow(String name) {
    Object obj = this.processEngine.getExecutionService().getVariable(this.task.getExecutionId(),
        name);
    if (obj == null) {
      throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
          + this.task.getExecutionId() + ", " + name, null);
    }
    return obj;
  }

  final class TasksComparator implements Comparator<TareaParaleloInbox> {

    /**
     * Compara dos instancias de Tarea y devuelve la comparación usando el
     * criterio de más reciente primera en el orden.
     * 
     * @param t1
     *          Primer tarea a comparar
     * @param t2
     *          Segunda tarea a comparar con la primera
     */
    public int compare(TareaParaleloInbox t1, TareaParaleloInbox t2) {
      return (int) (t2.getFechaPase().compareTo(t1.getFechaPase()));
    }

  }

  public Window getTareasEnParaleloWindow() {
    return tareasEnParaleloWindow;
  }

  public void setTareasEnParaleloWindow(Window tareasEnParaleloWindow) {
    this.tareasEnParaleloWindow = tareasEnParaleloWindow;
  }

  public List<TareaParaleloInbox> getListaTareas() {
    return listaTareas;
  }

  public void setListaTareas(List<TareaParaleloInbox> listaTareas) {
    this.listaTareas = listaTareas;
  }

  public TareaParaleloService getTareaParaleloService() {
    return tareaParaleloService;
  }

  public void setTareaParaleloService(TareaParaleloService tareaParaleloService) {
    this.tareaParaleloService = tareaParaleloService;
  }

  public TareaParaleloInbox getTareaParaleloSelected() {
    return tareaParaleloSelected;
  }

  public void setTareaParaleloSelected(TareaParaleloInbox tareaParaleloSelected) {
    this.tareaParaleloSelected = tareaParaleloSelected;
  }

  /**
   * @return the listaTareasTL
   */
  public List<TramitacionLibre> getListaTareasTL() {
    return listaTareasTL;
  }

  /**
   * @param listaTareasTL
   *          the listaTareasTL to set
   */
  public void setListaTareasTL(List<TramitacionLibre> listaTareasTL) {
    this.listaTareasTL = listaTareasTL;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username
   *          the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  public boolean permited(String data) {
    System.out.println("DATA --> " + data);
    return true;
  }
}
