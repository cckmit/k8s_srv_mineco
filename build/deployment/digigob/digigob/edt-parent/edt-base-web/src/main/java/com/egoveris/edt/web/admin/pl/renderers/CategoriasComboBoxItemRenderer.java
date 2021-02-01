package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.model.eu.CategoriaDTO;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class CategoriasComboBoxItemRenderer implements ComboitemRenderer {

  @Override
  public void render(Comboitem item, Object data, int arg2) throws Exception {
    CategoriaDTO categoria = (CategoriaDTO) data;
    item.setLabel(categoria.getCategoriaNombre());
    item.setValue(categoria.getId());
  }
}