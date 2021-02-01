package com.egoveris.edt.web.admin.pl.renderers;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;

public class HistorialAdmRepaHabilitadaItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    UsuarioReparticionHabilitadaDTO repaHabilitada = (UsuarioReparticionHabilitadaDTO) data;

    // reparticion
    item.setValue(repaHabilitada);

    Listcell lblReparticion = new Listcell(repaHabilitada.getReparticion().getCodigo());
    lblReparticion.setTooltiptext(repaHabilitada.getReparticion().getCodigo());
    lblReparticion.setParent(item);

    // sector
    Listcell lblSector = new Listcell(repaHabilitada.getSector().getCodigo());
    lblSector.setParent(item);

    // fecha revision
    Listcell lblFechaRevision = new Listcell(sdf.format(repaHabilitada.getFechaRevision()));
    lblFechaRevision.setParent(item);

    // tipo revision
    Listcell lblTipoRevision = new Listcell(repaHabilitada.getTipoRevision());
    lblTipoRevision.setParent(item);
  }
}
