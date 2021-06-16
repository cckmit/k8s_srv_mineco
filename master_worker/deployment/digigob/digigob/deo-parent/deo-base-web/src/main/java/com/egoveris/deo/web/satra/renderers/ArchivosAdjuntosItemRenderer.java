package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.NombreCeldaDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;




public class ArchivosAdjuntosItemRenderer implements ListitemRenderer {

	
	
	public void render(Listitem item, Object data, int arg2) throws Exception {
		NombreCeldaDTO archivoRec = (NombreCeldaDTO)data;
		Listcell celdaNombres = new Listcell();
		Label nombre = new Label();
		nombre.setTooltiptext(archivoRec.getNombre());
		if(archivoRec.getNombre().equals(archivoRec.getNombreDoc())){
			nombre.setStyle("font-weight: bold; font-style: italic");
		}
		nombre.setValue(archivoRec.getNombre().length()>60 ? archivoRec.getNombre().substring(0, 60) + "...":
			archivoRec.getNombre());		
		nombre.setParent(celdaNombres);
		celdaNombres.setParent(item);
		
		Listcell celdaAcciones = new Listcell();
		Hbox hbox = new Hbox();
		Image descargarDocumento = new Image("/imagenes/Descargar.png");
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(descargarDocumento,
				"onClick=detalleCoWindow$DetalleCoComposer.onDescargarAdjunto");
		celdaAcciones.setParent(item);
		hbox.setParent(celdaAcciones);
		descargarDocumento.setTooltiptext(Labels.getLabel("ccoo.detalle.descargarArchivo"));
		descargarDocumento.setParent(hbox);
	}
}
