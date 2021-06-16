package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.DocumentoMetadataDTO;

import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;



public class DatosDocumentoRenderer implements RowRenderer {
	
	Boolean habilitarEdicion;
	
	public DatosDocumentoRenderer(Boolean habilitarEdicion) {
		this.habilitarEdicion = habilitarEdicion;
	}
	
	public void render(Row row, Object data, int arg2) throws Exception {

		DocumentoMetadataDTO metadatosDeTrata = (DocumentoMetadataDTO) data;
	
		row.getParent().setId("rows");
		
		//NOMBRE
		Label label1 = new Label();
		label1.setValue(metadatosDeTrata.getNombre());
		label1.setParent(row);

		Hlayout hbox = new Hlayout();

		// VALOR
		if (habilitarEdicion.equals(Boolean.TRUE)) {
			Textbox textbox = new Textbox();
			textbox.setValue(metadatosDeTrata.getValor());
			textbox.setParent(hbox);
		} else {
			Label label = new Label();
			label.setValue(metadatosDeTrata.getValor());
			label.setParent(hbox);
		}

		// CAMPO REQUERIDO
		Label label2 = new Label();
		if (metadatosDeTrata.isObligatoriedad()){
			label2.setValue(" *");
			label2.setStyle("color:red;");
			label2.setParent(hbox);
		}
		hbox.setParent(row);
			
	}
}
