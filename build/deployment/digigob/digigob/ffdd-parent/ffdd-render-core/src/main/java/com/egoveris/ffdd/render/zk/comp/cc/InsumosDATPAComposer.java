package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class InsumosDATPAComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3800990850871800045L;

	Cell numeroInsumoDiv;
	Cell descripcionInsumoDiv;
	Cell cantidadInsumoDiv;
	Cell unidadMedidaCantidadDiv;
	Cell numeroHojaDiv;
	Cell numeroItemDiv;
	Cell valorCIFUnitarioDiv;
	Cell unidadMedidaCIFDiv;

	InputElement numeroInsumo;
	InputElement descripcionInsumo;
	InputElement cantidadInsumo;
	InputElement unidadMedidaCantidad;
	InputElement numeroHoja;
	InputElement numeroItem;
	InputElement valorCIFUnitario;
	InputElement unidadMedidaCIF;
	
	
	@Override
	protected void setDefaultValues(String prefixName) {
		
	}

}
