package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.util.UtilsDate;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class DocumentosTadInboxItemRenderer implements ListitemRenderer {

  public void render(Listitem item, Object data, int arg1) throws Exception {
    DocumentoDTO documento = (DocumentoDTO) data;

    new Listcell("").setParent(item);

    Listcell numDoc;

    if (documento.isSubsanadoLimitado()) {

      String numeroSade = documento.getNumeroSade().substring(0, 12) + "XXXX"
          + documento.getNumeroSade().substring(16, 22) + "XXXX";

      // Numero Documento
      numDoc = new Listcell(numeroSade);
    } else {
      numDoc = new Listcell(documento.getNumeroSade());
    }


    numDoc.setParent(item);
    // Referencia
    String motivo = documento.getMotivo();
    Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
    listCellMotivo.setTooltiptext(motivo);

    listCellMotivo.setParent(item);

    Listcell fechaCreacion = new Listcell(
        UtilsDate.formatearFechaHora(documento.getFechaCreacion()));

    fechaCreacion.setParent(item);
  }

  private String motivoParseado(String motivo) {
    int cantidadCaracteres = 10;
    String substringMotivo = "";

    if (motivo != null && !"null".equalsIgnoreCase(motivo) && !"".equalsIgnoreCase(motivo)) {
      if (motivo.length() > cantidadCaracteres) {
        substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
      } else {
        substringMotivo = motivo.substring(0, motivo.length());
      }
    }
    return substringMotivo;
  }
}
