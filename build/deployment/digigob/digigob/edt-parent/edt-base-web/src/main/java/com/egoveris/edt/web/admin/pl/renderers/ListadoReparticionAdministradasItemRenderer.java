package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.AdminReparticionDTO;

public class ListadoReparticionAdministradasItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    AdminReparticionDTO adminReparticion = (AdminReparticionDTO) data;

    Listcell codigo = new Listcell(adminReparticion.getReparticion().getCodigo());
    codigo.setParent(item);

    Listcell nombre = new Listcell(adminReparticion.getReparticion().getNombre());
    nombre.setParent(item);

    Listcell eliminar = new Listcell();
    eliminar.setImage("/imagenes/Eliminar.png");
    eliminar.setTooltiptext("Eliminar Organismo Administrado");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminar,
        "onClick=composer.onEliminarReparticionesAdministradas");
    eliminar.setParent(item);

  }

}
