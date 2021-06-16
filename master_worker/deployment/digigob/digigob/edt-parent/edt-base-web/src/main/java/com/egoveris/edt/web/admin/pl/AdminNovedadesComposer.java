package com.egoveris.edt.web.admin.pl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.service.novedad.INovedadHelper;
import com.egoveris.edt.base.service.novedad.INovedadHistService;
import com.egoveris.edt.base.service.novedad.INovedadService;
import com.egoveris.edt.base.util.zk.ZkUtil;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

/**
 * 
 * @author marcmino
 *
 */
public class AdminNovedadesComposer extends BaseComposer {

  private static final long serialVersionUID = -9012526102459113901L;

  private static Logger logger = LoggerFactory.getLogger(AdminNovedadesComposer.class);
  private Listbox listBoxNovedades;
  private List<NovedadDTO> novedades;
  private NovedadDTO novedad;
  private AnnotateDataBinder binder;
  private Window hiddenWindow;
  private INovedadService novedadService;
  private INovedadHelper novedadHelper;
  private INovedadHistService novedadHistService;

  @Autowired
  private Window win_adminnovedades;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.self.addEventListener(Events.ON_NOTIFY, new ABMNovedadesOnNotifyWindowListener(this));
    this.self.addEventListener(Events.ON_CHANGING, new ABMNovedadesOnNotifyWindowListener(this));

    binder = new AnnotateDataBinder(comp);
    this.novedadService = (INovedadService) SpringUtil.getBean("novedadService");
    this.novedadHelper = (INovedadHelper) SpringUtil.getBean("novedadHelper");
    this.novedadHistService = (INovedadHistService) SpringUtil.getBean("novedadHistService");

    this.cargarListbox();
  }

  /**
   * Crea el popUp para dar de alta una NovedadDTO
   */
  public void onClick$crearNovedad() {
		Utilitarios.closePopUps("win_novedadNueva");
    HashMap<String, Object> hm = new HashMap<>();
    hm.put("novedades", this.novedades);
    hm.put("alta", true);
    hiddenWindow = (Window) Executions
        .createComponents("/administrator/tabsNovedades/nuevaNovedad.zul", this.self, hm);
    hiddenWindow.setClosable(true);
    hiddenWindow.setPosition("center");
    hiddenWindow.doModal();
  }

  /**
   * Crea el popUp para la modificacion de una NovedadDTO
   */
  public void onModificarNovedad() {
		Utilitarios.closePopUps("win_novedadNueva");
    HashMap<String, Object> hm = new HashMap<>();
    hm.put("novedades", this.novedades);
    hm.put("novedad", novedad);
    hm.put("alta", false);
    hiddenWindow = (Window) Executions
        .createComponents("/administrator/tabsNovedades/nuevaNovedad.zul", this.self, hm);
    hiddenWindow.setClosable(true);
    hiddenWindow.setPosition("center");
    hiddenWindow.doModal();
  }

  /**
   * Elimina la novedad seleccionada (cambia el estado a ELIMIADO)
   * 
   * @throws InterruptedException
   */
  @SuppressWarnings("unchecked")
	public void onEliminarNovedad() throws InterruptedException {
    Messagebox.show(Labels.getLabel("eu.configuracionNovedades.crearNovedad.eliminar"),
        Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
        new org.zkoss.zk.ui.event.EventListener() {

          @Override
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              novedad.setUsuario(getUsername());
              novedadService.delete(novedad);
              novedadHistService.guardarHistorico(novedad, 2);
              cargarListbox();
              novedadHelper.cachearListaNovedades();
              Events.sendEvent(win_adminnovedades, new Event(Events.ON_CHANGING));
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
  }

  /**
   * Carga el listBox de novedades con todas las que no esten en ejecución
   */
  private void cargarListbox() {
    this.novedades = this.novedadService.getPendientes();
    this.listBoxNovedades.setModel(new ListModelList(novedades));
    this.binder.loadComponent(listBoxNovedades);
  }

  final class ABMNovedadesOnNotifyWindowListener implements EventListener {
    private AdminNovedadesComposer composer;

    public ABMNovedadesOnNotifyWindowListener(AdminNovedadesComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        this.composer.cargarListbox();
      }
      if (event.getName().equals(Events.ON_CHANGING)) {
        // List<Window> actWin = ZkUtil.findByType(acti, Window.class);

        Window main = ZkUtil.findParentByType(composer.win_adminnovedades.getParent(),
            Window.class);
        Window historial = ZkUtil.findById(main, "win_adminnovedadesHistorial");
        Events.sendEvent(Events.ON_USER, historial, "refresh");

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
}

// final class AdminNovedadesComposerOnNotifyWindowListener implements
// EventListener {
// private AdminNovedadesComposer composer;
//
// public AdminNovedadesComposerOnNotifyWindowListener(AdminNovedadesComposer
// historicoComposer) {
// this.composer = historicoComposer;
// }
//
//
// @Override
// public void onEvent(Event event) throws Exception {
// // TODO Auto-generated method stub
// if (event.getName().equals(Events.ON_USER)) {
// enviarEventoHistorialNovedades(composer);
// }
// }
//
// private void enviarEventoHistorialNovedades(AdminNovedadesComposer composer){
// Component acti = ZkUtil.findById(composer.win_adminnovedades,
// "novedadesHistorico");
// if(acti!=null){
// List<Window> actWin = ZkUtil.findByType(acti, Window.class);
// HistorialNovedadComposer actComposer = (HistorialNovedadComposer)
// actWin.get(0).getAttribute("win_historialNovedades$composer");
// actComposer.refreshWindows();
// }
// }
//
// }
