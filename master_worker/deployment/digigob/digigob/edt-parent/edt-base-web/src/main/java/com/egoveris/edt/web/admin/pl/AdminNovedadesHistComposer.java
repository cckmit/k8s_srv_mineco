package com.egoveris.edt.web.admin.pl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.service.novedad.INovedadService;
import com.egoveris.edt.web.common.BaseComposer;

/**
 * 
 * @author marcmino
 *
 */
public class AdminNovedadesHistComposer extends BaseComposer {

  private static final long serialVersionUID = -8500984077240792615L;

  private static Logger logger = LoggerFactory.getLogger(AdminNovedadesHistComposer.class);
  private Listbox listBoxNovedades;
  private List<NovedadDTO> novedades;
  private NovedadDTO novedad;
  private AnnotateDataBinder binder;
  private Window hiddenWindow;
  private INovedadService novedadService;

  @Autowired
  private Window win_adminnovedadesHistorial;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.self.addEventListener(Events.ON_DROP, new NovedadHistEventListener(this));
    binder = new AnnotateDataBinder(comp);
    this.novedadService = (INovedadService) SpringUtil.getBean("novedadService");
    this.cargarListbox();

    comp.addEventListener(Events.ON_USER, new EventListener() {

      @Override
      public void onEvent(Event event) throws Exception {
        onEvento(event);
      }
    });
  }

  public void onEvento(Event event) {
    if (event.getName().equals(Events.ON_USER) || event.getName().equals(Events.ON_DROP)) {
      if ("refresh".equals(event.getData())) {
        cargarListbox();
      }
    }
  }

  /**
   * Crea el popUp para visualizar el historial de una NovedadDTO
   */
  public void onVerHistorialNovedad() {
    HashMap<String, Object> hm = new HashMap<>();
    hm.put("novedad", novedad);
    hiddenWindow = (Window) Executions
        .createComponents("/administrator/tabsNovedades/historialNovedad.zul", this.self, hm);
    hiddenWindow.setClosable(true);
    hiddenWindow.setPosition("center");
    hiddenWindow.doModal();
  }

  /**
   * Carga el listBox de novedades con todas las que tengan que aparecer en el
   * historial
   */
  public void cargarListbox() {
    this.novedades = this.novedadService.gethistorial();
    this.listBoxNovedades.setModel(new ListModelList(novedades));
    this.binder.loadComponent(listBoxNovedades);
  }

  final class NovedadHistEventListener implements EventListener {
    private AdminNovedadesHistComposer composer;

    public NovedadHistEventListener(AdminNovedadesHistComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_USER) || event.getName().equals(Events.ON_DROP)) {
        this.composer.cargarListbox();
      }
    }
  }

  public Listbox getListBoxNovedades() {
    return listBoxNovedades;
  }

  public void setListBoxNovedades(Listbox listBoxNovedades) {
    this.listBoxNovedades = listBoxNovedades;
  }

  public List<NovedadDTO> getNovedades() {
    return novedades;
  }

  public void setNovedades(List<NovedadDTO> novedades) {
    this.novedades = novedades;
  }

  public NovedadDTO getNovedad() {
    return novedad;
  }

  public void setNovedad(NovedadDTO novedad) {
    this.novedad = novedad;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Window getHiddenWindow() {
    return hiddenWindow;
  }

  public void setHiddenWindow(Window hiddenWindow) {
    this.hiddenWindow = hiddenWindow;
  }

  void refreshWindows() {
    this.cargarListbox();
  }
}