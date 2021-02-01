package com.egoveris.edt.web.pl.renderers;

import com.egoveris.edt.base.model.eu.CalleDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class CalleItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    CalleDTO calle = (CalleDTO) data;

    Listcell codigo = new Listcell(calle.getCodigoCalle().toString());
    codigo.setParent(item);

    Listcell codigoPostal = new Listcell(calle.getCodigoPostal().toString());
    codigoPostal.setParent(item);

    Listcell nombre = new Listcell(calle.getNombreCalle());
    nombre.setParent(item);

  }

}
