package com.egoveris.edt.web.admin.pl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Tab;

import com.egoveris.edt.web.common.BaseComposer;

public class NovedadesAdminComposer extends BaseComposer {

  private Tab tabNovedadesHist;
  private Tab tabNovedades;

  private AnnotateDataBinder binder;

  private static final long serialVersionUID = -3774404200605962656L;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    binder.loadComponent(tabNovedades);
  }

  public void onClick$tabNovedades() {
    binder.loadComponent(tabNovedades);
  }

  /*
   * Se desconoce la intención de este evento y genera un error en consola:
   * org.zkoss.zk.ui.ComponentNotFoundException: Fellow component not found:
   * win_adminnovedadesHistorial
   */
  // public void onClick$tabNovedadesHist() {
  // Object obj = ZkUtil.findById(this.self, "win_adminnovedadesHistorial");
  // if (obj != null) {
  // Events.sendEvent(Events.ON_DROP, (Component) obj, null);
  // }
  // binder.loadComponent(tabNovedadesHist);
  // }

  public Tab getTabNovedadesHist() {
    return tabNovedadesHist;
  }

  public void setTabNovedadesHist(Tab tabNovedadesHist) {
    this.tabNovedadesHist = tabNovedadesHist;
  }

  public Tab getTabNovedades() {
    return tabNovedades;
  }

  public void setTabNovedades(Tab tabNovedades) {
    this.tabNovedades = tabNovedades;
  }
}
