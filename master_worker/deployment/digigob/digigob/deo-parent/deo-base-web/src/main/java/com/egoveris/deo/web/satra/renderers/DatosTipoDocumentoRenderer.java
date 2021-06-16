package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.MetadataDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;




/**
 *
 * @author jnorvert
 *
 */

public class DatosTipoDocumentoRenderer implements RowRenderer {
	public void render(Row row, Object data, int arg2) throws Exception {
		/**
		 * Metadatos de la Trata.
		 * Se cargan los campos y sus valores que se encuentren en la data.
		 */
		
		MetadataDTO metadatosDeTrata = (MetadataDTO) data;
		row.getParent().setId("rows");

		// NOMBRE
		Label label1 = new Label();
		label1.setId(metadatosDeTrata.getNombre());
		label1.setValue(metadatosDeTrata.getNombre());
		label1.setParent(row);

		// OBLIGATORIEDAD
		Checkbox checkbox = new Checkbox();
		checkbox.setId(metadatosDeTrata.getNombre() + "_obligatoriedad");
		checkbox.setChecked(metadatosDeTrata.isObligatoriedad());
		checkbox.setParent(row);

		// ACCIÓN
		Hbox hbox = new Hbox();
		Button botonEliminar = new Button();
		botonEliminar.setTooltiptext(Labels.getLabel("gedo.datosPropiosDocumento.tooltip.eliminaDatoDoc"));
		botonEliminar.setImage("/imagenes/decline.png");
		ComponentsCtrl.applyForward(botonEliminar, "onClick=onEliminar()");
		Label label2 = new Label();
		label2.setValue(Labels.getLabel("ccoo.alertaCO.eliminar"));
		botonEliminar.setParent(hbox);
		botonEliminar.setAttribute("metaData", metadatosDeTrata);
		label2.setParent(hbox);
		hbox.setParent(row);

	}
	
}
