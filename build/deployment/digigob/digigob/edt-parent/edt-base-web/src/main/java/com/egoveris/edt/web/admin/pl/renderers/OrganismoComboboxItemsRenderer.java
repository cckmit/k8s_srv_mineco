package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

public class OrganismoComboboxItemsRenderer implements ComboitemRenderer {

  @Override
  public void render(Comboitem item, Object data, int arg2) throws Exception {

    ReparticionDTO reparticion = (ReparticionDTO) data;
    item.setLabel(reparticion.getNombre());
    item.setValue(reparticion.getId());
  }
}
