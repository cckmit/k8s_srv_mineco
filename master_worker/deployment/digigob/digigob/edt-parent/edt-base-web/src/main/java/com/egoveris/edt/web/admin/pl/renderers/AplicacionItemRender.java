package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class AplicacionItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    ApLayOut aplicacion = (ApLayOut) data;
    item.setLabel(aplicacion.getNombreAplicacion());
    item.setValue(aplicacion.getIdAplicacion());
    if (aplicacion.isEsSeleccionado()) {
      item.setSelected(true);
    }
  }

}
