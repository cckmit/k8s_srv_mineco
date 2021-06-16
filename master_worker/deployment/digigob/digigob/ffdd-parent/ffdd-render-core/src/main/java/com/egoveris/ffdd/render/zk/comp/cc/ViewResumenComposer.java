package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ViewResumenComposer extends ComplexComponentComposer {
	
	private static final long serialVersionUID = 7644466683698799014L;
	
	Cell codOperacionDiv;
	Cell tipoTramiteDiv;
	Cell creadoPorDiv;
	Cell actualizadoPorDiv;
	Cell bultoTotalDiv;
	Cell pesoNetoEmbarqueDiv;
	Cell processingStatusDiv;
	Cell fechaCreacionDiv;
	Cell fechaActualizacionDiv;
	Cell itemTotalDiv;
	Cell pesoBrutoItemDiv;
	Cell volumenTotalDiv;
	
	InputElement codOperacion;
	InputElement tipoTramite;
	InputElement creadoPor;
	InputElement actualizadoPor;
	InputElement bultoTotal;
	InputElement pesoNetoEmbarque;
	InputElement processingStatus;
	InputElement fechaCreacion;
	InputElement fechaActualizacion;
	InputElement itemTotal;
	InputElement pesoBrutoItem;
	InputElement volumenTotal;
	
	
	@Override
	protected void setDefaultValues(String prefixName) {
		
	}

}
