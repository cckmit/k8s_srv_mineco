
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaOperacionRespuestaDinComposer extends ComplexComponentComposer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cell codigoOperacionDiv;
	Cell creadoPorDiv;
	Cell fechaCreacionDiv;
	Cell fechaEstadoDiv;

	InputElement codigoOperacion;
	InputElement creadoPor;
	InputElement fechaCreacion;
	InputElement fechaEstado;


	@Override
	protected String getName() {
		return "Vista Operacion";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

}
