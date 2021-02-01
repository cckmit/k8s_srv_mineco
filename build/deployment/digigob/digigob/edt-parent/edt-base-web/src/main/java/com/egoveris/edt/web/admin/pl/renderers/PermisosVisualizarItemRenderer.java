package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.PermisoDTO;

public class PermisosVisualizarItemRenderer implements ListitemRenderer {

  public PermisosVisualizarItemRenderer() {

  }

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    Listcell currentCell;

    PermisoDTO per = (PermisoDTO) data;
    currentCell = new Listcell(per.getPermiso());
    currentCell.setStyle("text-align: left");

    currentCell.setParent(item);

    currentCell = new Listcell(per.getSistema());
    currentCell.setStyle("text-align: center");
    currentCell.setParent(item);

  }
}