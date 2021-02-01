package com.egoveris.edt.web.pl.renderers;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.EstructuraDTO;

public class EstructuraItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    EstructuraDTO estructura = (EstructuraDTO) data;

    Listcell codigo = new Listcell(estructura.getCodigoEstructura().toString());
    codigo.setParent(item);

    Listcell nombre = new Listcell(estructura.getNombreEstructura());
    nombre.setParent(item);

  }

}