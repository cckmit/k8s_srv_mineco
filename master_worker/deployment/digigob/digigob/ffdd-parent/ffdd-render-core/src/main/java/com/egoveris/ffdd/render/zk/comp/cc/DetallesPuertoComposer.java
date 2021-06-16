
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class DetallesPuertoComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell idDetallesPuertoDiv;
	Cell paisDiv;
	Cell codPuertoDiv;
	Cell tipoPuertoDiv;
	Cell nombrePuertoDiv;
	Cell locacionPuertoDiv;
	Cell fechaDiv;
	Cell regionDiv;
	Cell fechaEstimadaDiv;
	
	
	
	InputElement idDetallesPuerto;
	InputElement pais;
	InputElement codPuerto;
	InputElement tipoPuerto;
	InputElement nombrePuerto;
	InputElement locacionPuerto;
	InputElement fecha;
	InputElement region;
	InputElement fechaEstimada;
 
	@Override
	protected String getName() {
		return "detallesPuerto";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  
	}

}
