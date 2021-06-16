package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class DetalleCuotaComposer extends ComplexComponentComposer{
	
	private static final long serialVersionUID = -2159746408724809603L;
	Cell codigoUnoDiv;
	Cell codigoDosDiv;
	Cell fechaDiv;
	Cell montoDiv;
	
	InputElement codigoUno;
	InputElement codigoDos;
	InputElement fecha;
	InputElement monto;
	
	
	@Override
	protected void setDefaultValues(String prefixName) {
	}

}
