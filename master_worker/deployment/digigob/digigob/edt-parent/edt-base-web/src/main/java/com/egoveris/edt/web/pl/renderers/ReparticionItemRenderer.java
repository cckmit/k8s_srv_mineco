package com.egoveris.edt.web.pl.renderers;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

public class ReparticionItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    ReparticionDTO reparticion = (ReparticionDTO) data;

    Listcell codigo = new Listcell(reparticion.getCodigo());
    codigo.setParent(item);

    Listcell nombre = new Listcell(reparticion.getNombre());
    nombre.setParent(item);

  }

}
