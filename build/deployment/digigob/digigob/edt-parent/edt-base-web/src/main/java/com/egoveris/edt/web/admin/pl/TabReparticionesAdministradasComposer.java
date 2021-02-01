package com.egoveris.edt.web.admin.pl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.AdminReparticionDTO;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;

public class TabReparticionesAdministradasComposer extends BaseComposer {

  private static Logger logger = LoggerFactory
      .getLogger(TabReparticionesAdministradasComposer.class);

  private static final long serialVersionUID = -2955225383367609065L;

  private IReparticionEDTService reparticionService;
  private IAdminReparticionService adminReparticionService;
  private UsuarioBaseDTO usuarioSeleccionado;
  private Set<AdminReparticionDTO> repsAdministradasABorrar;
  private Label lbl_reparticionAsignada;
  private ReparticionDTO reparticionDelUsuarioSeleccionado;

  @Autowired
  protected AnnotateDataBinder binder;
  @Autowired
  private ReparticionDTO reparticionSeleccionada;
  @Autowired
  private Set<AdminReparticionDTO> reparticionesAdministradas;
  private Set<AdminReparticionDTO> reparticionesAgregadas;
  @Autowired
  private AdminReparticionDTO reparticionAdministradaSeleccionada;
  @Autowired
  private Listbox reparticionesAdministradasListbox;
  @Autowired
  private Include inc_reparticionSectorSelector;
  @Autowired
  private Window ventanaContenedora;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    usuarioSeleccionado = (UsuarioBaseDTO) Executions.getCurrent()
        .getAttribute(ConstantesSesion.KEY_USUARIO);
    adminReparticionService = (IAdminReparticionService) SpringUtil
        .getBean("adminReparticionService");
    reparticionService = (IReparticionEDTService) SpringUtil.getBean("reparticionEDTService");
    reparticionesAdministradas = new HashSet<AdminReparticionDTO>();
    reparticionesAgregadas = new HashSet<AdminReparticionDTO>();
    this.reparticionesAdministradas = new HashSet<AdminReparticionDTO>(adminReparticionService
        .obtenerReparticionesAdministradasByUsername(getUsuarioSeleccionado().getUid()));

    ventanaContenedora = (Window) Executions.getCurrent()
        .getAttribute(ConstantesSesion.KEY_WINDOW_REPARTICIONES_ADMINISTRADAS_USUARIO);

    comp.addEventListener(Events.ON_CHANGE, new TabReparticionesAdministradasListener(this));

    repsAdministradasABorrar = new HashSet<AdminReparticionDTO>();
    reparticionDelUsuarioSeleccionado = reparticionService
        .getReparticionByUserName(usuarioSeleccionado.getUid());
    if (reparticionDelUsuarioSeleccionado != null) {
      lbl_reparticionAsignada.setValue(Labels
          .getLabel("eu.adminSade.reparticiones.administradas.label.reparticionAsignadaDelUsuario")
          + " " + reparticionDelUsuarioSeleccionado.getCodigo());
    } else {
      lbl_reparticionAsignada.setVisible(false);
    }

    configurarCombosSectorReparticion();
    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();

  }

  private void configurarCombosSectorReparticion() {
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_SECTOR,
        false);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_COMBO_REPARTICION,
        true);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_CARGAR_COMBO_REPARTICION,
        false);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_CARGO,
        false);
    inc_reparticionSectorSelector.setSrc(ConstantesSesion.PANEL_REPARTICION_SECTOR_SELECTOR);
  }

  public Include getInc_reparticionSectorSelector() {
    return inc_reparticionSectorSelector;
  }

  public void setInc_reparticionSectorSelector(Include inc_reparticionSectorSelector) {
    this.inc_reparticionSectorSelector = inc_reparticionSectorSelector;
  }

  private void validarComboReparticion() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent(Events.ON_CHECK, comp, null);
  }

  private void cleanOrganismoBandbox() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent("onCleanOrganismoBandbox", comp, null);
  }

  public void onClick$btn_agregarReparticion() {
    validarComboReparticion();
    if (getSession().getAttribute(ConstantesSesion.REPARTICION_SELECCIONADA) != null) {
      setReparticionSeleccionada(
          (ReparticionDTO) getSession().getAttribute(ConstantesSesion.REPARTICION_SELECCIONADA));
      if ((reparticionDelUsuarioSeleccionado != null
          && !reparticionDelUsuarioSeleccionado.equals(reparticionSeleccionada))
          || reparticionDelUsuarioSeleccionado == null) {
        AdminReparticionDTO adminReparticionNueva = new AdminReparticionDTO();
        adminReparticionNueva.setNombreUsuario(usuarioSeleccionado.getUid());
        adminReparticionNueva.setReparticion(reparticionSeleccionada);
        reparticionesAgregadas.add(adminReparticionNueva);
        reparticionesAdministradas.add(adminReparticionNueva);
        this.binder.loadComponent(reparticionesAdministradasListbox);
        // Limpia variable de sesión y bandbox
        getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, null);
        cleanOrganismoBandbox();
      } else {
        try {
          Messagebox.show(Labels.getLabel("eu.tabReparAdmComposer.msgbox.organismoNoValido"),
      				Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK, Messagebox.INFORMATION);
        } catch (Exception ex) {
          logger.error("error en el mensaje: organismo no válido", ex);
        }
      }
    }
  }

  public void onClick$btn_guardar() throws InterruptedException, Exception {
    validarComboReparticion();
    try {
      for (AdminReparticionDTO adminReparticion : reparticionesAgregadas)
        adminReparticionService.agregarAdminReparticion(adminReparticion);
      for (AdminReparticionDTO adminReparticion : repsAdministradasABorrar)
        adminReparticionService.eliminarAdminReparticion(adminReparticion);
      Messagebox.show(Labels.getLabel("eu.tabReparAdmComposer.msgbox.guardadoExitoso"));
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
    Events.sendEvent(ventanaContenedora, new Event(Events.ON_CLOSE));
  }

  public void onEliminarReparticionesAdministradas() throws InterruptedException {
    try {
      repsAdministradasABorrar.add(reparticionAdministradaSeleccionada);
      reparticionesAdministradas.remove(reparticionAdministradaSeleccionada);

      Messagebox.show(Labels.getLabel("eu.adminSade.reparticiones.administradas.eliminacion"),
          Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
      binder.loadAll();
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }

    this.binder.loadComponent(reparticionesAdministradasListbox);
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public ReparticionDTO getReparticionSeleccionada() {
    return reparticionSeleccionada;
  }

  public void setReparticionSeleccionada(ReparticionDTO reparticionSeleccionada) {
    this.reparticionSeleccionada = reparticionSeleccionada;
  }

  public UsuarioBaseDTO getUsuarioSeleccionado() {
    return usuarioSeleccionado;
  }

  public void setUsuarioSeleccionado(UsuarioBaseDTO usuarioSeleccionado) {
    this.usuarioSeleccionado = usuarioSeleccionado;
  }

  public AdminReparticionDTO getReparticionAdministradaSeleccionada() {
    return reparticionAdministradaSeleccionada;
  }

  public void setReparticionAdministradaSeleccionada(
      AdminReparticionDTO reparticionAdministradaSeleccionada) {
    this.reparticionAdministradaSeleccionada = reparticionAdministradaSeleccionada;
  }

  public Set<AdminReparticionDTO> getReparticionesAdministradas() {
    return reparticionesAdministradas;
  }

  public void setReparticionesAdministradas(Set<AdminReparticionDTO> reparticionesAdministradas) {
    this.reparticionesAdministradas = reparticionesAdministradas;
  }

  public Set<AdminReparticionDTO> getRepsAdministradasABorrar() {
    return repsAdministradasABorrar;
  }

  public void setRepsAdministradasABorrar(Set<AdminReparticionDTO> repsAdministradasABorrar) {
    this.repsAdministradasABorrar = repsAdministradasABorrar;
  }

  public Label getLbl_reparticionAsignada() {
    return lbl_reparticionAsignada;
  }

  public void setLbl_reparticionAsignada(Label lbl_reparticionAsignada) {
    this.lbl_reparticionAsignada = lbl_reparticionAsignada;
  }

  public Listbox getReparticionesAdministradasListbox() {
    return reparticionesAdministradasListbox;
  }

  public void setReparticionesAdministradasListbox(Listbox reparticionesAdministradasListbox) {
    this.reparticionesAdministradasListbox = reparticionesAdministradasListbox;
  }

  public ReparticionDTO getReparticionDelUsuarioSeleccionado() {
    return reparticionDelUsuarioSeleccionado;
  }

  public void setReparticionDelUsuarioSeleccionado(
      ReparticionDTO reparticionDelUsuarioSeleccionado) {
    this.reparticionDelUsuarioSeleccionado = reparticionDelUsuarioSeleccionado;
  }

  private void resetearCombosReparticionSector() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent(Events.ON_CHANGE, comp, null);
  }

  final class TabReparticionesAdministradasListener implements EventListener {
    @SuppressWarnings("unused")
    private TabReparticionesAdministradasComposer composer;

    public TabReparticionesAdministradasListener(TabReparticionesAdministradasComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_CHANGE)) {
        resetearCombosReparticionSector();
      }
    }
  }

  public Set<AdminReparticionDTO> getReparticionesAgregadas() {
    return reparticionesAgregadas;
  }

  public void setReparticionesAgregadas(Set<AdminReparticionDTO> reparticionesAgregadas) {
    this.reparticionesAgregadas = reparticionesAgregadas;
  }
}