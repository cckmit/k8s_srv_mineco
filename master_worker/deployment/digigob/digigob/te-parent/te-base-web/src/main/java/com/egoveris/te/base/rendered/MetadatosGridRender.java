package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.MetaDocumento;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

/**
 * @author MAGARCES
 *
 */
public class MetadatosGridRender implements RowRenderer {

  public void render(Row row, Object data, int arg1) throws Exception {

    MetaDocumento metadatos = (MetaDocumento) data;
    row.getParent().setId("rows");

    // NOMBRE
    Label nombre = new Label();
    nombre.setValue(metadatos.getNombre());
    nombre.setParent(row);

    // VALOR
    Label valor = new Label();
    valor.setId(metadatos.getNombre());
    valor.setValue(String.valueOf(metadatos.getValor()));
    valor.setParent(row);

  }
}
