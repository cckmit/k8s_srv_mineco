package com.egoveris.edt.web.pl.renderers;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;

public class CargoItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    CargoDTO cargo = (CargoDTO) data;

    Listcell codigo = new Listcell(cargo.getId().toString());
    codigo.setParent(item);

    Listcell nombre = new Listcell(cargo.getCargoNombre());
    nombre.setParent(item);
  }

}
