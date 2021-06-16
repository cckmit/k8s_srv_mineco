package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.web.admin.pl.AltaUsuarioComposer;
import com.egoveris.sharedsecurity.base.model.PermisoDTO;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PermisosAltaItemRenderer implements ListitemRenderer {

  private AltaUsuarioComposer composer;

  public PermisosAltaItemRenderer(AltaUsuarioComposer altaUsuarioComposer) {
    this.composer = altaUsuarioComposer;
  }

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    PermisoDTO per = (PermisoDTO) data;

    Checkbox checkPermiso = new Checkbox();
    Listcell currentCell;

    Listcell check = new Listcell();
    check.setParent(item);
    checkPermiso.setId("chckb_" + per.getIdPermiso());
    checkPermiso.addEventListener("onCheck", new PermisoSelectListener(this.composer, per));

    if (this.composer.getUsuario().getPermisos() != null
        && this.composer.getUsuario().getPermisos().contains(per.getPermiso())) {
      checkPermiso.setChecked(true);
    }
    checkPermiso.setParent(check);

    currentCell = new Listcell((String) per.getDescripcion());
    currentCell.setStyle("text-align: left");
    currentCell.setTooltiptext(per.getDescripcion());
    currentCell.setParent(item);

    currentCell = new Listcell(per.getSistema());
    currentCell.setStyle("text-align: center");
    currentCell.setTooltiptext(per.getDescripcion());
    currentCell.setParent(item);
  }

}

final class PermisoSelectListener implements EventListener {
  private AltaUsuarioComposer composer;
  private PermisoDTO permiso;

  public PermisoSelectListener(AltaUsuarioComposer composer, PermisoDTO permiso) {
    super();
    this.composer = composer;
    this.permiso = permiso;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_CHECK)) {
      Checkbox checkBox = (Checkbox) event.getTarget();
      if (checkBox.isChecked()) {
        this.composer.getUsuario().getPermisos().add(permiso.getPermiso().trim());
      } else {
        this.composer.getUsuario().getPermisos().remove(permiso.getPermiso().trim());
      }
    }
  }
}
