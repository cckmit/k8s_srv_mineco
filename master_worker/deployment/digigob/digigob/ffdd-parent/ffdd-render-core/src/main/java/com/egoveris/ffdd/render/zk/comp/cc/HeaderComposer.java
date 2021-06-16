
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.impl.InputElement;

public class HeaderComposer extends ComplexComponentComposer {

	/**
	* 
	*/
	private static final long serialVersionUID = 8989623688678301110L;

	Cell idHeaderDiv;
	Cell destinacionAduaneraDiv;
	Cell tipoOperacionDiv;
	Cell tipoTramiteDiv;
	Cell aduanaTramitacionDiv;
	Cell numeroInternoDespachoDiv;
	Cell comentarioDiv;
	Cell tipoIngresoDiv;

	InputElement idHeader;
	InputElement destinacionAduanera;
	InputElement tipoOperacion;
	InputElement tipoTramite;
	InputElement aduanaTramitacion;
	InputElement numeroInternoDespacho;
	InputElement comentario;
	InputElement tipoIngreso;

	@Override
	protected String getName() {
		return "header";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.idHeader.setText("0");
		((Combobox) this.destinacionAduanera).setSelectedIndex(0);
		this.tipoOperacion.setText("TOp98");
		this.tipoTramite.setText("NRE1");
		this.aduanaTramitacion.setText("AT");
		this.numeroInternoDespacho.setText("NID");
	}

}
