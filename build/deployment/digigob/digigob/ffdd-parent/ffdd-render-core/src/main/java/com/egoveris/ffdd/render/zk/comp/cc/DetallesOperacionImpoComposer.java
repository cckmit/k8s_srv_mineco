package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.model.InputComponent;

import org.zkoss.zul.Cell;

public class DetallesOperacionImpoComposer extends ComplexComponentComposer{
	
	private static final long serialVersionUID = -3756736076675600612L;
	
	Cell estadoOpDiv;
	Cell numeroOpDiv;
	Cell fechaCreacionDiv;
	Cell tipoOperacionDiv;
	Cell tipoIngresoDiv;
	
	
	InputComponent estadoOp;
	InputComponent numeroOp;
	InputComponent fechaCreacion;
	InputComponent tipoOperacion;
	InputComponent tipoIngreso;

	@Override
	protected void setDefaultValues(String prefixName) {
		// TODO Auto-generated method stub
		
	}

}
