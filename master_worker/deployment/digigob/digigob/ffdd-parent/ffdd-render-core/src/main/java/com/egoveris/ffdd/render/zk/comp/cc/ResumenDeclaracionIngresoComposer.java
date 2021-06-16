
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ResumenDeclaracionIngresoComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell numeroOpDiv;
	Cell destinacionAduaneraDiv;
	Cell tipoOperacionDiv;
	Cell fechaCreacionDiv;
	Cell estadoOpDiv;
	Cell totalGiroUSDDiv;

	InputElement numeroOp;
	InputElement destinacionAduanera;
	InputElement tipoOperacion;
	InputElement fechaCreacion;
	InputElement estadoOp;
	InputElement totalGiroUSD;

	@Override
	protected String getName() {
		return "resumendeclaracioningreso";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  
	}
	  

}
