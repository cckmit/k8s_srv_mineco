package com.egoveris.edt.web.pl.renderers;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;

public class SectorItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    SectorDTO sector = (SectorDTO) data;

    Listcell codigo = new Listcell(sector.getCodigo());
    codigo.setParent(item);

    Listcell nombre = new Listcell(sector.getNombre());
    nombre.setParent(item);

  }

}
