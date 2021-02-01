
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaOTRDinComposer extends ComplexComponentComposer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cell secuencialCuentaDiv;
	Cell codigoCuentaOTRDiv;
	Cell montoCuentaOTRDiv;

	InputElement secuencialCuenta;
	InputElement codigoCuentaOTR;
	InputElement montoCuentaOTR;

	@Override
	protected String getName() {
		return "OTR";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

}
