package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;

public class SectorMesaComboBoxItemRenderer implements ComboitemRenderer {

  @Override
  public void render(Comboitem item, Object data, int arg2) throws Exception {

    SectorDTO sector = (SectorDTO) data;
    item.setLabel(sector.getCodigo() + " - " + sector.getNombre());
    item.setValue(sector.getId());
  }
}