package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaDetallesPuertoDAComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1753599815517301462L;
	
	Cell regionDiv;
	Cell codigoPaisDiv;
	Cell fechaDiv;
	Cell secuencialDiv;

	InputElement region;
	InputElement codigoPais;
	InputElement fecha;
	InputElement secuencial;

	

	@Override
	protected String getName() {
		return "VistaDetallesPuertoDA";
	}

	@Override
	protected void setDefaultValues(final String name) {

	}

}
