
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.DatosVariablesComboGruposDTO;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class TiposCombosComboBoxItemRenderer implements ComboitemRenderer {
  @Override
  public void render(Comboitem item, Object data, int arg1) throws Exception {
    DatosVariablesComboGruposDTO tmp = (DatosVariablesComboGruposDTO) data;

    item.setLabel(tmp.getNombreGrupo());
    item.setValue(tmp.getId());
  }
}
