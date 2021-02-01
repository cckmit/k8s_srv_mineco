
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaLoteDocIngresoEnvioComposer extends ComplexComponentComposer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cell valorLoteDiv;

	InputElement valorLote;

	@Override
	protected String getName() {
		return "lote";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.valorLote.setText("$1.234");
	}

}
