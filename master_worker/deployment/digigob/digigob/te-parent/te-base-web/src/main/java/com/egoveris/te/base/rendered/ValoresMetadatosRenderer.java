package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ExpedienteMetadataDTO;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class ValoresMetadatosRenderer implements RowRenderer {
  public void render(Row row, Object data, int arg1) throws Exception {

    ExpedienteMetadataDTO metadatos = (ExpedienteMetadataDTO) data;
    row.getParent().setId("rows");

    // NOMBRE
    Label nombre = new Label();
    nombre.setValue(metadatos.getNombre());
    nombre.setParent(row);

    // VALOR
    Label valor = new Label();
    valor.setId(metadatos.getNombre() + "_");
    valor.setValue(metadatos.getValor());
    valor.setParent(row);

  }
}
