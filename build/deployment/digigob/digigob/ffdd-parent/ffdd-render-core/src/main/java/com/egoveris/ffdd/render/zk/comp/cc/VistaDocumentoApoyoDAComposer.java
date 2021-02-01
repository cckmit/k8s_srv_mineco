package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.model.InputComponent;

import org.zkoss.zul.Cell;

public class VistaDocumentoApoyoDAComposer  extends ComplexComponentComposer{
	
	private static final long serialVersionUID = 8530022565766174865L;
	Cell descripcionDiv;
	Cell nombreDocumentoDiv;
	Cell nombreEmisorDiv;
	Cell tipoDocumentoAdjuntoDiv;
	Cell secuenciaDocumentoDiv;
	Cell numeroDocumentoDiv;
	Cell fechaDocumentoDiv;
	Cell adjuntoDiv;
	
	InputComponent descripcion;
	InputComponent nombreDocumento;
	InputComponent nombreEmisor;
	InputComponent tipoDocumentoAdjunto;
	InputComponent secuenciaDocumento;
	InputComponent numeroDocumento;
	InputComponent fechaDocumento;
	InputComponent adjunto;
	
	@Override
	protected void setDefaultValues(String prefixName) {
		
	}

}
