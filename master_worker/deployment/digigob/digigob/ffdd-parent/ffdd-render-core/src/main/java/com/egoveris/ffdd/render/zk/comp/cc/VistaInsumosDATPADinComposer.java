
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaInsumosDATPADinComposer extends ComplexComponentComposer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Cell numeroItemDiv;
	Cell numeroInsumoDiv;
	Cell descripcionInsumoDiv;
	Cell cantidadDeInsumoDiv;
	Cell codigoUnidadDeMedidaDiv;
	Cell valorCifUnitarioDiv;

	InputElement numeroItem;
	InputElement numeroInsumo;
	InputElement descripcionInsumo;
	InputElement cantidadDeInsumo;
	InputElement codigoUnidadDeMedida;
	InputElement valorCifUnitario;

	@Override
	protected String getName() {
		return "Vista Insumos";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

}
