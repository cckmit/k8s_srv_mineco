package com.egoveris.edt.web.pl.renderers;

import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ConsultaSindicaturaItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    if (data instanceof ReparticionDTO) {
      ReparticionDTO reparticion = (ReparticionDTO) data;

      Listcell codigo = new Listcell(reparticion.getCodigo());
      codigo.setParent(item);

      Listcell nombre = new Listcell(reparticion.getNombre());
      nombre.setParent(item);
    }
    if (data instanceof SectorDTO) {
      SectorDTO sector = (SectorDTO) data;
      Listcell codigo = new Listcell(sector.getCodigo());
      codigo.setParent(item);
      Listcell nombre = new Listcell(sector.getNombre());
      nombre.setParent(item);
    }

  }

}
