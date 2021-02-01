package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class HistorialEmbebidosItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    TipoDocumentoEmbebidosDTO documentoEmbebido = (TipoDocumentoEmbebidosDTO) data;
    item.setHeight("30px");
    new Listcell(documentoEmbebido.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato()).setParent(item);
    new Listcell(documentoEmbebido.getDescripcion()).setParent(item);
    new Listcell(documentoEmbebido.getFechaCreacion().toString()).setParent(item);
    Integer tamanio = documentoEmbebido.getSizeArchivoEmb();
    new Listcell(tamanio.toString()).setParent(item);
    String celdaObligatoriedad = "";
    if (documentoEmbebido.getObligatorio()) {
      celdaObligatoriedad += "Si";
    } else {
      celdaObligatoriedad += "No";
    }
    new Listcell(celdaObligatoriedad).setParent(item);

  }

}
