package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.TipoDocumentoDTO;

import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class TipoDeDocListBoxItemRenderer implements ListitemRenderer {

  private static final String SIN_TEMPLATE = "Sin Template";

  @Override
  public void render(Listitem item, Object data, int arg1) throws Exception {

    TipoDocumentoDTO tipo = (TipoDocumentoDTO) data;

    if (tipo.getAcronimo().equals(SIN_TEMPLATE)) {
      item.setLabel(tipo.getAcronimo());
      item.setValue(null);
    } else {
      item.setLabel(tipo.getAcronimo() + " - " + tipo.getNombre());
      item.setValue(tipo.getAcronimo());
    }
  }
}
