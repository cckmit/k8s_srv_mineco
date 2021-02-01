package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.web.admin.pl.DatosPersonalesComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.PermisoDTO;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PermisosModificarItemRenderer implements ListitemRenderer {
  private DatosPersonalesComposer composer;

  public PermisosModificarItemRenderer(DatosPersonalesComposer datosPersonalesComposer)
      throws InterruptedException, NegocioException {
    this.composer = datosPersonalesComposer;
  }

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    PermisoDTO per = (PermisoDTO) data;
    Checkbox checkPermiso = new Checkbox();
    Listcell currentCell;

    Listcell check = new Listcell();
    check.setParent(item);
    checkPermiso.setId("chckb_" + per.getIdPermiso());
    checkPermiso.addEventListener("onCheck", new PermisoSelectListener2(this.composer, per));

    if (this.composer.getUsuario().getPermisos() != null
        && this.composer.getUsuario().getPermisos().contains(per.getPermiso())) {
      checkPermiso.setChecked(true);
    }
    checkPermiso.setParent(check);

    currentCell = new Listcell((String) per.getPermiso());
    currentCell.setStyle("text-align: left");
    currentCell.setParent(item);

    currentCell = new Listcell(per.getSistema());
    currentCell.setStyle("text-align: center");
    currentCell.setTooltiptext(per.getDescripcion());
    currentCell.setParent(item);

    // Valido que el usuario logueado sea Administrador Central para modificar
    // dicho permiso
    if (!Utilitarios.isAdministradorCentral()
        && StringUtils.containsIgnoreCase(per.getPermiso(), ConstantesSesion.ROL_ADMIN_CENTRAL)) {
      checkPermiso.setDisabled(true);
      item.setDisabled(true);
    }
  }
}

final class PermisoSelectListener2 implements EventListener {
  private DatosPersonalesComposer composer;
  private PermisoDTO permiso;

  public PermisoSelectListener2(DatosPersonalesComposer composer, PermisoDTO permiso) {
    super();
    this.composer = composer;
    this.permiso = permiso;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_CHECK)) {
      Checkbox checkBox = (Checkbox) event.getTarget();
      if (checkBox.isChecked()) {
        if (this.composer.getUsuario().getPermisos() == null) {
          this.composer.getUsuario().setPermisos(new ArrayList<String>());
        }
        this.composer.getUsuario().getPermisos().add(permiso.getPermiso().trim());
      } else {
        this.composer.getUsuario().getPermisos().remove(permiso.getPermiso().trim());
      }
    }
  }
}
