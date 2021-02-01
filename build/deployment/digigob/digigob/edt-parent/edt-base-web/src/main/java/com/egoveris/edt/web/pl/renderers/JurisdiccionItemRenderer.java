package com.egoveris.edt.web.pl.renderers;

import com.egoveris.edt.base.model.eu.JurisdiccionDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class JurisdiccionItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    JurisdiccionDTO jurisdiccion = (JurisdiccionDTO) data;

    Listcell jurisdiccionCell = new Listcell(
        jurisdiccion.getId().toString().concat(" - ").concat(jurisdiccion.getDescripcion()));
    jurisdiccionCell.setParent(item);

  }

}