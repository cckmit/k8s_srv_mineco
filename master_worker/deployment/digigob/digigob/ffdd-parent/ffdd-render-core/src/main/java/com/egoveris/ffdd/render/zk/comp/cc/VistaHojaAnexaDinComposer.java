
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaHojaAnexaDinComposer extends ComplexComponentComposer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Cell numeroDeSecuenciaDiv;
	Cell numeroDeInsumoDiv;
	Cell nombreInsumoDiv;
	Cell numeroDeItemAbonaDiv;
	Cell nombreDeLaMercanciaDiv;
	Cell cantidadDiv;
	Cell codigoUnidadDeMedidaAbonaDiv;
	Cell factorDeConsumoDiv;
	Cell insumosUtilizadosDiv;

	InputElement numeroDeSecuencia;
	InputElement numeroDeInsumo;
	InputElement nombreInsumo;
	InputElement numeroDeItemAbona;
	InputElement nombreDeLaMercancia;
	InputElement cantidad;
	InputElement codigoUnidadDeMedidaAbona;
	InputElement factorDeConsumo;
	InputElement insumosUtilizados;


	@Override
	protected String getName() {
		return "Hoja Anexa";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

}
