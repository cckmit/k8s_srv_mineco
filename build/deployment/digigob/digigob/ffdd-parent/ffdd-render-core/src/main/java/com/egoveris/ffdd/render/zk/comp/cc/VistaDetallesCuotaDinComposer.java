
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaDetallesCuotaDinComposer extends ComplexComponentComposer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cell codigoMontoCuotaDiv;
	Cell fechaVencimientoCuotaDiv;
	Cell montoCuotaDiv;

	InputElement codigoMontoCuota;
	InputElement fechaVencimientoCuota;
	InputElement montoCuota;

	@Override
	protected String getName() {
		return "Detalle Cuota";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

}
