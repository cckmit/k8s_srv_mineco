
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaDeclaracionDinComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;

	Cell numeroIdentificacionDiv;
	Cell codigoAduanaDiv;
	Cell campoFormDiv;
	Cell fechaDeVencimientoDiv;
	Cell codigoTipoOperacionDiv;
	Cell tipoDeIngresoDiv;
	Cell urlDinDiv;
	Cell tipoDestinacionDiv;
	Cell regionIngresoDiv;

	InputElement numeroIdentificacion;
	InputElement codigoAduana;
	InputElement campoForm;
	InputElement fechaDeVencimiento;
	InputElement codigoTipoOperacion;
	InputElement tipoDeIngreso;
	InputElement urlDin;
	InputElement tipoDestinacion;
	InputElement regionIngreso;


 

	@Override
	protected String getName() {
		return "vistadeclaraciondin";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  
	}
	

}
