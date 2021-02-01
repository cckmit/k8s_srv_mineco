
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaAlmacenistaDinComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;

	Cell codigoAlmacenistaDiv;
	Cell fechaRecepcionMercanciasDiv;
	Cell fechaRetiroMercanciasDiv;
	Cell numeroRegReconocimientoDiv;
	Cell anioRegReconocimientoDiv;
	Cell codigoDeclaracionReglaDiv;
	
	InputElement codigoAlmacenista;
	InputElement fechaRecepcionMercancias;
	InputElement fechaRetiroMercancias;
	InputElement numeroRegReconocimiento;
	InputElement anioRegReconocimiento;
	InputElement codigoDeclaracionRegla;





	@Override
	protected String getName() {
		return "vistaalmacenistadin";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  
	}
	

}
