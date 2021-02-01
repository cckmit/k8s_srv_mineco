package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.FormatoTamanoArchivoDTO;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;


public class ComboBoxExtensionesArchivosRenderer implements ComboitemRenderer {

	public void render(Comboitem item, Object data, int arg2) throws Exception {
		
		FormatoTamanoArchivoDTO formatoTamanoArchivo = (FormatoTamanoArchivoDTO) data;
		
		item.setLabel(formatoTamanoArchivo.getFormato());
		
	}
	
}
