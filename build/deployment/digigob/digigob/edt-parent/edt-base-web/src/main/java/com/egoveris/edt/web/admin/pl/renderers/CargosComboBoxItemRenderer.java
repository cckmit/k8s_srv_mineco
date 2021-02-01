package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;

public class CargosComboBoxItemRenderer implements ComboitemRenderer {

  @Override
  public void render(Comboitem item, Object data, int arg2) throws Exception {

    CargoDTO cargo = (CargoDTO) data;
    item.setLabel(cargo.getCargoNombre());
    item.setValue(cargo.getId());
  }
}
