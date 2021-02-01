package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class HojaInsumosDATPAComposer extends ComplexComponentComposer {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7245171908491748656L;

	Cell aduanaDiv;
	Cell codigoDespachadorDiv;
	Cell consignatarioDiv;
	Cell rutImportadorDiv;

	InputElement aduana;
	InputElement codigoDespachador;
	InputElement consignatario;
	InputElement rutImportador;
	

	@Override
	protected String getName() {
		return "hojaInsumoDATPA";
	}
	  
	
	@Override
	protected void setDefaultValues(String prefixName) {

	}

}
