package com.egoveris.deo.web.satra.supervisados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.egoveris.deo.base.service.ISupervisadosService;
import com.egoveris.deo.model.model.TareasUsuarioDTO;
import com.egoveris.deo.util.Constantes;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SupervisadosComposer extends GenericForwardComposer {
  /**
  * 
  */
  private static final long serialVersionUID = -4436871756631367971L;
  private AnnotateDataBinder binder;
  @WireVariable("supervisadosServiceImpl")
  private ISupervisadosService supervisadosService;
  private TareasUsuarioDTO selectedSupervisado;
  private Window supervisadosView;
  private Paging supervisadosPaginator;
  private Window misSupervisadosWindow;
  private Listbox tasksBriefingList;
  private Label labelPersonalACargo;

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
     this.binder = new AnnotateDataBinder(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    component.addEventListener(Events.ON_NOTIFY, new SupervisadosOnNotifyWindowListener(this));
    component.addEventListener(Events.ON_USER, new SupervisadosOnNotifyWindowListener(this));

    this.self.setAttribute("dontAskBeforeClose", "true");
    this.binder.loadAll();
  }
  
    
  public void onClick$verTareasClick() {
    Clients.showBusy("procesando");
    // Envias el evento
    Events.echoEvent("onUser", this.self, "vertareas");
    // bloqueamos toda la pantalla tras el popup

  }

  public void verTareas() {

    if (this.supervisadosView != null) {
      this.supervisadosView.invalidate();
      Map<String, Object> variables = new HashMap<>();
      variables.put("supervisado", this.selectedSupervisado.getDatosUsuario());
      this.self.getDesktop().setAttribute("supervisado",
          this.selectedSupervisado.getDatosUsuario());
      this.supervisadosView = (Window) Executions.createComponents("supervisadosInbox.zul",
          this.self, variables);
      this.supervisadosView.setParent(this.misSupervisadosWindow);
      this.supervisadosView.setPosition("center");
      this.supervisadosView.setVisible(true);
      this.supervisadosView.doModal();
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
          Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
    Clients.clearBusy();
  }

  void updateEstadisticas() {
    if (this.self.isVisible()) {
      this.binder.loadComponent(this.tasksBriefingList);
    }
  }

  public List<TareasUsuarioDTO> getTareasSupervisados() throws InterruptedException {
    String loggedUsername = (String) Executions.getCurrent().getSession()
        .getAttribute(Constantes.SESSION_USERNAME);
    List<TareasUsuarioDTO> tareas = new ArrayList<>();
    if (StringUtils.isNotEmpty(loggedUsername)) {
      if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {

        tareas = this.supervisadosService.obtenerResumenTareasSupervisadosCCOO(loggedUsername);

      } else {
        tareas = this.supervisadosService.obtenerResumenTareasSupervisados(loggedUsername);
      }

      if (tareas != null && !tareas.isEmpty()) {
        return tareas;
      }

    }
    supervisadosPaginator.setVisible(false);
    tasksBriefingList.setVisible(false);
    labelPersonalACargo.setVisible(true);
    return tareas;
  }

  public void setSelectedSupervisado(TareasUsuarioDTO selectedSupervisado) {
    this.selectedSupervisado = selectedSupervisado;
  }

  public TareasUsuarioDTO getSelectedSupervisado() {
    return selectedSupervisado;
  }
}

final class SupervisadosOnNotifyWindowListener implements EventListener {
  private SupervisadosComposer composer;

  public SupervisadosOnNotifyWindowListener(SupervisadosComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY) && event.getData() == null) {
      this.composer.updateEstadisticas();
    }
    if (event.getName().equals(Events.ON_USER) && "vertareas".equals(event.getData())) {

      this.composer.verTareas();

    }

  }
}
