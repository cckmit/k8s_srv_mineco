package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.model.InputComponent;

import org.zkoss.zul.Cell;

public class ViewResumenImpoComposer extends ComplexComponentComposer {

	
	private static final long serialVersionUID = 1L;
	
	Cell processingStatusDiv;
	Cell creadoPorDiv;
	Cell tipoTramiteDiv;
	Cell codOperacionDiv;
	Cell totalItemsDiv;
	Cell totalBultosDiv;
	Cell fechaActualizacionDiv;
	Cell profechaCreacionDiv;
	

	InputComponent processingStatus;
	InputComponent creadoPor;
	InputComponent tipoTramite;
	InputComponent codOperacion;
	InputComponent totalItems;
	InputComponent totalBultos;
	InputComponent fechaActualizacion;
	InputComponent profechaCreacion;
	
	
	@Override
	protected void setDefaultValues(String prefixName) {
		
	}

}
