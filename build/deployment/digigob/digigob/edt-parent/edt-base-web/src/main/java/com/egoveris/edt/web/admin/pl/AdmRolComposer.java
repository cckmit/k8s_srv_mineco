package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.model.eu.usuario.RolDTO;
import com.egoveris.edt.base.service.IRolService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class AdmRolComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 27495326893407365L;

  private static Logger logger = LoggerFactory.getLogger(AdmRolComposer.class);

  @Autowired
  protected AnnotateDataBinder binder;

  private List<RolDTO> listaResultadoRoles;

  private RolDTO rol;

  // private Textbox txbx_rolBuscado;

  private IRolService rolService;

  @Autowired
  private Listbox lstbx_roles;

  private Integer resultados;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    comp.addEventListener(Events.ON_USER, new BusquedaRolComposerListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new BusquedaRolComposerListener(this));

    rolService = (IRolService) SpringUtil.getBean("rolService");
    // cargoHistService = (ICargoHistService)
    // SpringUtil.getBean("cargoHistService");
    // selectedCargo = new Cargo();

    binder = new AnnotateDataBinder(comp);

    cargarListbox();

  }

  /**
   * Carga el listBox de novedades con todas las que tengan el estado ACTIVA
   */
  public void cargarListbox() {
    listaResultadoRoles = new ArrayList<RolDTO>();
    listaResultadoRoles.addAll(rolService.getRolesActivos());
    // listaResultadoRoles.addAll(rolService.getRolesOcultos());
    Collections.sort(listaResultadoRoles, new CustomComparatorRol());

    resultados = listaResultadoRoles.size();
    this.lstbx_roles.setModel(new ListModelList(listaResultadoRoles));
    this.binder.loadComponent(lstbx_roles);

  }

  public void onClick$tbbtn_altaRol() throws InterruptedException {
    try {
  		Utilitarios.closePopUps("win_altaRol");
      HashMap<String, Object> parametros = new HashMap<String, Object>();
      parametros.put("rol", null);
      parametros.put("alta", true);
      Window window = (Window) Executions.createComponents("/administrator/tabsRoles/altaRol.zul",
          this.self, parametros);
      window.setPosition("center");
      window.setVisible(true);
      window.doModal();
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onModificarRol() throws InterruptedException {
    try {
  		Utilitarios.closePopUps("win_altaRol");
      HashMap<String, Object> hm = new HashMap<String, Object>();
      hm.put("rol", rol);
      hm.put("alta", false);
      Window window = (Window) Executions.createComponents("/administrator/tabsRoles/altaRol.zul",
          this.self, hm);
      window.setPosition("center");
      window.setTitle(Labels.getLabel("eu.panelAdmin.tabAdmRoles.mod.rol"));
      window.setVisible(true);
      window.doModal(); 
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onEliminarRol() throws InterruptedException {
    Messagebox.show(Labels.getLabel("eu.adminSade.rol.mensajes.confirm.eliminacion"),
        Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {

          @Override
          public void onEvent(Event evt) throws InterruptedException {

            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              rol.setEsVigente(false);
              rol.setUsuarioModificacion((String) Executions.getCurrent().getSession()
                  .getAttribute(ConstantesSesion.SESSION_USERNAME));
              boolean bool = rolService.eliminar(rol);
              if (bool) {
                Messagebox.show(Labels.getLabel("eu.adminSade.rol.mensajes.eliminacion.nok"),
                    Labels.getLabel("eu.general.information"), Messagebox.NO,
                    Messagebox.INFORMATION);
                break;
              }
              Messagebox.show(Labels.getLabel("eu.adminSade.rol.mensajes.eliminacion.ok"),
                  Labels.getLabel("eu.general.information"), Messagebox.OK,
                  Messagebox.INFORMATION);
              cargarListbox();
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
  }

  public void refreshRoles() throws InterruptedException {
    rolService.refreshCache();
    listaResultadoRoles.clear();
    listaResultadoRoles.addAll(rolService.getRolesActivos());
    // listaResultadoRoles.addAll(cargoService.getCargosOcultos());
    Collections.sort(listaResultadoRoles, new CustomComparatorRol());

    this.resultados = listaResultadoRoles.size();
    this.lstbx_roles.setModel(new ListModelList(listaResultadoRoles));
    this.binder.loadComponent(lstbx_roles);

  }

  public List<RolDTO> getListaResultadoRoles() {
    return listaResultadoRoles;
  }

  public void setListaResultadoRoles(List<RolDTO> listaResultadoRoles) {
    this.listaResultadoRoles = listaResultadoRoles;
  }

  public RolDTO getRol() {
    return rol;
  }

  public void setRol(RolDTO rol) {
    this.rol = rol;
  }

  public Listbox getLstbx_roles() {
    return lstbx_roles;
  }

  public void setLstbx_roles(Listbox lstbx_roles) {
    this.lstbx_roles = lstbx_roles;
  }

  public Integer getResultados() {
    return resultados;
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }

}

final class BusquedaRolComposerListener implements EventListener {
  private AdmRolComposer composer;

  public BusquedaRolComposerListener(AdmRolComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {
      this.composer.cargarListbox();
    }
    if (event.getName().equals(Events.ON_NOTIFY)) {
      composer.binder.loadAll();
    }
  }
}

final class CustomComparatorRol implements Comparator<RolDTO> {
  @Override
  public int compare(RolDTO o1, RolDTO o2) {
    return o1.getRolNombre().compareTo(o2.getRolNombre());
  }
}
