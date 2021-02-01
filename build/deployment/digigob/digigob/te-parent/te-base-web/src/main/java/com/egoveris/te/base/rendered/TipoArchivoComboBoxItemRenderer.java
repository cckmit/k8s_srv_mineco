package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.TipoArchivoTrabajoDTO;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class TipoArchivoComboBoxItemRenderer implements ComboitemRenderer {

  @Override
  public void render(Comboitem item, Object data, int arg1) throws Exception {

    TipoArchivoTrabajoDTO tipoArchivoTrabajo = (TipoArchivoTrabajoDTO) data;
    String nombre = tipoArchivoTrabajo.getNombre();
    item.setLabel(nombre);
    item.setValue(tipoArchivoTrabajo);
  }
}
