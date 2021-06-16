package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class AltaCargoComboboxItemsRenderer implements ComboitemRenderer {

  @Override
  public void render(Comboitem item, Object data, int arg2) throws Exception {

    String tipo = (String) data;
    item.setLabel(tipo);
    item.setValue(tipo);
  }
}
