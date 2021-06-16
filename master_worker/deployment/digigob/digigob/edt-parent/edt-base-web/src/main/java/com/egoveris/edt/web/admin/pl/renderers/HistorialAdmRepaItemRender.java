package com.egoveris.edt.web.admin.pl.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.AdminReparticionDTO;

public class HistorialAdmRepaItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    AdminReparticionDTO repaAdmin = (AdminReparticionDTO) data;

    // reparticion
    item.setValue(repaAdmin);

    Listcell lblReparticion = new Listcell(repaAdmin.getReparticion().getCodigo());
    lblReparticion.setTooltiptext(repaAdmin.getReparticion().getCodigo());
    lblReparticion.setParent(item);

    // fecha revision
    Listcell lblFechaRevision = new Listcell(sdf.format(repaAdmin.getFechaRevision()));
    lblFechaRevision.setParent(item);

    // usuario modificacion
    Listcell lblTipoRevision = new Listcell(repaAdmin.getTipoRevision());
    lblTipoRevision.setParent(item);
  }
}
