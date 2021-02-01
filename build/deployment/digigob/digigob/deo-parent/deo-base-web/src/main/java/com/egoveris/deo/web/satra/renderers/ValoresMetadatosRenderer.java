package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.MetaDocumentoDTO;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;


public class ValoresMetadatosRenderer implements RowRenderer  {
	public void render(Row row, Object data, int arg2) throws Exception {
		
		MetaDocumentoDTO metadatos = (MetaDocumentoDTO) data;
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
