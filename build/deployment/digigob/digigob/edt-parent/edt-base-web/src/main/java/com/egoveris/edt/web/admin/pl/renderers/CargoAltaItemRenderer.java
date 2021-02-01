package com.egoveris.edt.web.admin.pl.renderers;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.edt.base.model.eu.usuario.RolDTO;
import com.egoveris.edt.web.admin.pl.AltaCargoComposer;

public class CargoAltaItemRenderer implements ListitemRenderer {

  private AltaCargoComposer composer;

  public CargoAltaItemRenderer(AltaCargoComposer altaCargoComposer) {
    this.composer = altaCargoComposer;

  }

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    RolDTO rol = (RolDTO) data;
    Checkbox checkPermiso = new Checkbox();
    Listcell currentCell;
    Listcell check = new Listcell();
    check.setParent(item);
    checkPermiso.setId("chckb_" + rol.getId());
    checkPermiso.addEventListener("onCheck", new RolSelectListener(this.composer, rol));

//    if (this.composer.getCargo().getListaRoles() != null
//        && contieneRol(this.composer.getCargo().getListaRoles(), rol)) {
//
//      checkPermiso.setChecked(true);
//
//    }
    checkPermiso.setParent(check);
    currentCell = new Listcell((String) rol.getRolNombre());
    currentCell.setStyle("text-align: left");
    currentCell.setParent(item);
  }

  private boolean contieneRol(List<RolDTO> listRoles, RolDTO rol) {

    boolean bool = false;
    for (int i = 0; i < listRoles.size(); i++) {
      RolDTO rolaux = listRoles.get(i);
      if (rolaux.getId().equals(rol.getId())) {
        bool = true;
        break;
      }
    }
    return bool;

  }
}

final class RolSelectListener implements EventListener {
  private AltaCargoComposer composer;
  private RolDTO rol;

  public RolSelectListener(AltaCargoComposer composer, RolDTO rol) {
    super();
    this.composer = composer;
    this.rol = rol;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_CHECK)) {
      Checkbox checkBox = (Checkbox) event.getTarget();
//      if (checkBox.isChecked()) {
//        this.composer.getCargo().getListaRoles().add(rol);
//      } else {
//        Iterator<RolDTO> iterator = this.composer.getCargo().getListaRoles().iterator();
//        while (iterator.hasNext()) {
//          if (iterator.next().getId().equals(rol.getId())) {
//            iterator.remove();
//            break;
//          }
//        }
//      }
    }
  }
}
