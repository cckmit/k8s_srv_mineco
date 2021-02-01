package com.egoveris.te.base.rendered;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;

@SuppressWarnings("rawtypes")
public class TipoDocVucItemRenderer implements ListitemRenderer {

	@Override
	public void render(Listitem item, Object data, int arg1) throws Exception {

		ExternalDocumentoVucDTO tipo = (ExternalDocumentoVucDTO) data;

		new Listcell(tipo.getTipoDocumento().getAcronimoTad()).setParent(item);
		new Listcell(tipo.getTipoDocumento().getNombre()).setParent(item);
		final Checkbox check = new Checkbox();
		check.setId(tipo.getTipoDocumento().getAcronimoTad());
		check.setChecked(tipo.isSeleccionado());
		check.addForward("onCheck", "this.self", "onSelectTD", tipo);
		Listcell accion = new Listcell();
		check.setParent(accion);
		accion.setParent(item);
	}
}
