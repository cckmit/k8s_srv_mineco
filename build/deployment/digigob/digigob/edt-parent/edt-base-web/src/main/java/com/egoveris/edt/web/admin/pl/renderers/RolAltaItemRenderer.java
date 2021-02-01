package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.web.admin.pl.AltaRolComposer;
import com.egoveris.sharedsecurity.base.model.PermisoDTO;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class RolAltaItemRenderer implements ListitemRenderer {

  private AltaRolComposer composer;

  public RolAltaItemRenderer(AltaRolComposer altaRolComposer) {
    this.composer = altaRolComposer;
  }

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    PermisoDTO permiso = (PermisoDTO) data;

    Checkbox checkPermiso = new Checkbox();
    Listcell currentCell;

    Listcell check = new Listcell();
    check.setParent(item);
    checkPermiso.setId("chckb_" + permiso.getIdPermiso());
    checkPermiso.addEventListener("onCheck", new PermisoRolSelectListener(this.composer, permiso));

    if (this.composer.getRol().getListaPermisos() != null
        && contienePermiso(this.composer.getRol().getListaPermisos(), permiso)) { // REVISAR
      checkPermiso.setChecked(true);
    }

    checkPermiso.setParent(check);

    currentCell = new Listcell((String) permiso.getDescripcion());
    currentCell.setStyle("text-align: left");
    currentCell.setTooltiptext(permiso.getDescripcion());
    currentCell.setParent(item);

    currentCell = new Listcell(permiso.getSistema());
    currentCell.setStyle("text-align: center");
    currentCell.setTooltiptext(permiso.getDescripcion());
    currentCell.setParent(item);

  }

  private boolean contienePermiso(List<PermisoDTO> listPermisos, PermisoDTO permiso) {
    boolean bool = false;
    for (int i = 0; i < listPermisos.size(); i++) {
      PermisoDTO per = listPermisos.get(i);
      if (per.getIdPermiso().equals(permiso.getIdPermiso())) {
        bool = true;
        break;
      }
    }
    return bool;
  }

}

final class PermisoRolSelectListener implements EventListener {
  private AltaRolComposer composer;
  private PermisoDTO permiso;

  public PermisoRolSelectListener(AltaRolComposer composer, PermisoDTO permiso) {
    super();
    this.composer = composer;
    this.permiso = permiso;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_CHECK)) {
      Checkbox checkBox = (Checkbox) event.getTarget();
      if (checkBox.isChecked()) {
        this.composer.getRol().getListaPermisos().add(permiso);
      } else {
        this.composer.getRol().getListaPermisos().remove(permiso);
      }
    }
  }
}
