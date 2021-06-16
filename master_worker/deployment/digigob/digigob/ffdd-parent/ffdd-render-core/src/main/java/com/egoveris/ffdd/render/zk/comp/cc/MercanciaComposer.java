package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class MercanciaComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5151986056127438142L;

	Cell idMercanciaDiv;
	Cell mercanciaNacionalizadaDiv;
	Cell regionOrigenDiv;
	Cell propiosMediosDiv;
	Cell mercanciaZonaPrimariaDiv;
	Cell adquiridasCIFDiv;
	Cell origenCIFDiv;
	Cell paisOrigenDiv;
	Cell paisAdquisicionDiv;

	InputElement idMercancia;
	InputElement mercanciaNacionalizada;
	InputElement regionOrigen;
	InputElement propiosMedios;
	InputElement mercanciaZonaPrimaria;
	InputElement adquiridasCIF;
	InputElement origenCIF;
	InputElement paisOrigen;
	InputElement paisAdquisicion;


	@Override
	protected void setDefaultValues(String prefixName) {

	}

	@Override
	protected String getName() {
		return "mercancia";
	}
}
