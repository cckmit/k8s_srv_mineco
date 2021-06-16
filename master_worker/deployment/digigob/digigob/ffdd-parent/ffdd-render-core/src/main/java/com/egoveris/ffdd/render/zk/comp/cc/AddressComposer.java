
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class AddressComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell direccionDiv;
	Cell comunaDiv;
	Cell regionDiv;
	Cell paisDiv;


	InputElement direccion;
	InputElement comuna;
	InputElement region;
	InputElement pais;



	@Override
	protected String getName() {
		return "address";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.direccion.setText("Direccion default");
		this.comuna.setText("sector default");
	}

}
