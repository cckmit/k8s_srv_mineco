package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class ExtensionTipoDocRowRenderer implements RowRenderer{
	private Grid grillaExtensiones;

	public void render(Row row, Object data, int arg2) throws Exception {
		
		TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos = (TipoDocumentoEmbebidosDTO) data;
		row.getParent().setId("rows");
	
		Label extension = new Label();
		extension.setValue(tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato());
		extension.setParent(row);
		
		Label descripcion = new Label();
		descripcion.setValue(tipoDocumentoEmbebidos.getDescripcion());
		descripcion.setParent(row);
		
		Intbox tamanio = new Intbox();
		tamanio.setValue(tipoDocumentoEmbebidos.getSizeArchivoEmb()== null ? tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getTamano():tipoDocumentoEmbebidos.getSizeArchivoEmb());
		ComponentsCtrl.applyForward(tamanio, "onBlur=onIntbox()");
		tamanio.setAttribute("formato", tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato());
		tamanio.setWidth("50px");
		tamanio.setParent(row);
		
		Checkbox obligatoriedad = new Checkbox();
		obligatoriedad.setChecked(tipoDocumentoEmbebidos.getObligatorio());
		ComponentsCtrl.applyForward(obligatoriedad, "onClick=onCheck()");
		obligatoriedad.setAttribute("formato", tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato());
		obligatoriedad.setParent(row);
		
		Hbox hbox = new Hbox();
		Button buttonEliminar = new Button();
		buttonEliminar.setImage("/imagenes/Eliminar.png");
		ComponentsCtrl.applyForward(buttonEliminar, "onClick=onEliminar()");
		buttonEliminar.setAttribute("formato", tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato());
		buttonEliminar.setParent(hbox);
		Label labelEliminar = new Label();
		labelEliminar.setValue(Labels.getLabel("ccoo.alertaCO.eliminar"));
		labelEliminar.setParent(hbox);
		hbox.setParent(row);
	}
	
	public Grid getGrillaExtensiones() {
		return grillaExtensiones;
	}

	public void setGrillaExtensiones(Grid grillaExtensiones) {
		this.grillaExtensiones = grillaExtensiones;
	}

}
