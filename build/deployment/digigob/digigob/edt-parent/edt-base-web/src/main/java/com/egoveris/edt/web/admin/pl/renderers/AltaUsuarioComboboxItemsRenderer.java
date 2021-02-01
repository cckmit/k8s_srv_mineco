package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

public class AltaUsuarioComboboxItemsRenderer implements ComboitemRenderer {

  private final static String ESTILO_TODAS = "font-weight: bold; color: black";

  @Override
  public void render(Comboitem item, Object data, int arg2) throws Exception {

    String sistema = (String) data;
    item.setLabel(sistema);
    item.setValue(sistema);
    if (sistema.equals(ConstantesSesion.TODOS_SISTEMAS))
      item.setStyle(ESTILO_TODAS);
  }

}