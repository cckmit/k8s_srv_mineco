package com.egoveris.edt.web.admin.pl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class DatosRepartcionesDeUsuarioComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 5300569296251042155L;
  @Autowired
  protected AnnotateDataBinder binder;

  @Autowired
  private Window win_datosReparticionesUsuario;
  @Autowired
  private Tabpanel panelActivo;
  @Autowired
  private Tabpanel tabpanel_reparticionAdministradas;
  @Autowired
  private Tabpanel tabpanel_reparticionesHabilitadas;
  @Autowired
  private Tabbox gestionesRepTabs;

  @Autowired
  private Include inc_repAdministradas;
  @Autowired
  private Include inc_repHabilitadas;
  private Session session;
  protected UsuarioBaseDTO usuarioSeleccionado;
  protected Map<?, ?> parametros;
  private Tab tab_reparticionesHabilitadas;
  private Tab tab_reparticionesAdministradas;

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    parametros = Executions.getCurrent().getArg();
    usuarioSeleccionado = (UsuarioBaseDTO) parametros.get(ConstantesSesion.KEY_USUARIO);

    // Los AC son los unicos que pueden asignar Reparticiones Habilitadas
    tab_reparticionesHabilitadas.setVisible(Utilitarios.isAdministradorCentral());

    // Los AC no necesitan que les asignen reparticiones para administrar
    tab_reparticionesAdministradas
        .setVisible(Utilitarios.isAdministradorLocalReparticion(usuarioSeleccionado));

    if (tab_reparticionesAdministradas.isVisible()) {
      panelActivo = tabpanel_reparticionAdministradas;
    } else {
      panelActivo = tabpanel_reparticionesHabilitadas;
    }

    inc_repAdministradas.setDynamicProperty(ConstantesSesion.KEY_USUARIO, usuarioSeleccionado);
    inc_repHabilitadas.setDynamicProperty(ConstantesSesion.KEY_USUARIO, usuarioSeleccionado);
    inc_repAdministradas.setDynamicProperty(
        ConstantesSesion.KEY_WINDOW_REPARTICIONES_ADMINISTRADAS_USUARIO, win_datosReparticionesUsuario);
    inc_repHabilitadas.setDynamicProperty(
        ConstantesSesion.KEY_WINDOW_REPARTICIONES_ADMINISTRADAS_USUARIO, win_datosReparticionesUsuario);
    inc_repAdministradas.setSrc(ConstantesSesion.PANEL_REPARTICIONES_ADMINISTRADAS);
    inc_repHabilitadas.setSrc(ConstantesSesion.PANEL_REPARTICIONES_HABILITADAS);

    gestionesRepTabs.setSelectedPanel(panelActivo);

    comp.addEventListener(Events.ON_CLOSE, new DatosRepartcionesDeUsuarioListener(this));

    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
  }

  public void onClick$btn_salir() {
    Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public Tabpanel getTabpanel_reparticionAdministradas() {
    return tabpanel_reparticionAdministradas;
  }

  public void setTabpanel_reparticionAdministradas(Tabpanel tabpanel_reparticionAdministradas) {
    this.tabpanel_reparticionAdministradas = tabpanel_reparticionAdministradas;
  }

  @Override
  public Session getSession() {
    return session;
  }

  @Override
  public void setSession(Session session) {
    this.session = session;
  }

  public void onSelect$tab_reparticionesAdministradas() {
    resetearComboSector();
    resetearComboReparticionDeTabRepAdministradas();
    onSelectGestionRepTabs(tabpanel_reparticionAdministradas);
    this.binder.loadAll();
  }

  public void onSelect$tab_reparticionesHabilitadas() {
    resetearComboReparticionDeRepHabilitadas();
    onSelectGestionRepTabs(tabpanel_reparticionesHabilitadas);
    this.binder.loadAll();
  }

  private void resetearComboReparticionDeTabRepAdministradas() {
    getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, null);
    Component comp = inc_repAdministradas.getFellow("win_gestionReparticionAdministradas");
    Events.sendEvent(Events.ON_CHANGE, comp, null);
  }

  private void resetearComboReparticionDeRepHabilitadas() {
    getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, null);
    Component comp = inc_repHabilitadas.getFellow("win_reparticionesHabilitadas");
    Events.sendEvent(Events.ON_CHANGE, comp, null);
  }

  private void resetearComboSector() {
    getSession().setAttribute(ConstantesSesion.SECTOR_SELECCIONADO, null);
  }

  public Tabpanel getPanelActivo() {
    return panelActivo;
  }

  private void onSelectGestionRepTabs(Tabpanel panelActivo) {
    this.panelActivo = panelActivo;
  }

  public void setPanelActivo(Tabpanel panelActivo) {
    this.panelActivo = panelActivo;
  }

  public Tabpanel getTabpanel_reparticionAdministrada() {
    return tabpanel_reparticionAdministradas;
  }

  public void setTabpanel_reparticionAdministrada(Tabpanel tabpanel_reparticionAdministrada) {
    this.tabpanel_reparticionAdministradas = tabpanel_reparticionAdministrada;
  }

  public Tabpanel getTabpanel_reparticionesHabilitadas() {
    return tabpanel_reparticionesHabilitadas;
  }

  public void setTabpanel_reparticionesHabilitadas(Tabpanel tabpanel_reparticionesHabilitadas) {
    this.tabpanel_reparticionesHabilitadas = tabpanel_reparticionesHabilitadas;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Tabbox getGestionesRepTabs() {
    return gestionesRepTabs;
  }

  public void setGestionesRepTabs(Tabbox gestionesRepTabs) {
    this.gestionesRepTabs = gestionesRepTabs;

  }

  public UsuarioBaseDTO getUsuarioSeleccionado() {
    return usuarioSeleccionado;
  }

  public void setUsuarioSeleccionado(UsuarioBaseDTO usuarioSeleccionado) {
    this.usuarioSeleccionado = usuarioSeleccionado;
  }

  public Window getWin_datosReparticionesUsuario() {
    return win_datosReparticionesUsuario;
  }

  public void setWin_datosReparticionesUsuario(Window win_datosReparticionesUsuario) {
    this.win_datosReparticionesUsuario = win_datosReparticionesUsuario;
  }

  public Tab getTab_reparticionesAdministradas() {
    return tab_reparticionesAdministradas;
  }

  public void setTab_reparticionesAdministradas(Tab tab_reparticionesAdministradas) {
    this.tab_reparticionesAdministradas = tab_reparticionesAdministradas;
  }

  public Tab getTab_reparticionesHabilitadas() {
    return tab_reparticionesHabilitadas;
  }

  public void setTab_reparticionesHabilitadas(Tab tab_reparticionesHabilitadas) {
    this.tab_reparticionesHabilitadas = tab_reparticionesHabilitadas;
  }

  @SuppressWarnings("rawtypes")
  final class DatosRepartcionesDeUsuarioListener implements EventListener {
    @SuppressWarnings("unused")
    private DatosRepartcionesDeUsuarioComposer composer;

    public DatosRepartcionesDeUsuarioListener(DatosRepartcionesDeUsuarioComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_CLOSE)) {
        // Limpiar sesión de repartición seleccionada
        getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, null);
        getSession().setAttribute(ConstantesSesion.SECTOR_SELECCIONADO, null);
      }
    }
  }
}