package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.TipoDocumentoDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class InboxTopListItemRenderer  implements ListitemRenderer{

	public void render(Listitem item, Object data, int arg2) throws Exception {
		TipoDocumentoDTO tipoDocumento = (TipoDocumentoDTO)data;

		Listcell acr = new Listcell(tipoDocumento.getNombre());
		acr.setParent(item);
		
		Listcell nombre = new Listcell(tipoDocumento.getAcronimo());
		nombre.setParent(item);
		
		Listcell descripcion = new Listcell(tipoDocumento.getDescripcion());
		descripcion.setParent(item);
		
		
		
	}

}
