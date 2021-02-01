package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ListaDocumentoIngresoComposer extends ComplexComponentComposer{
	
	private static final long serialVersionUID = 3166787755954947205L;
	
	Cell idSolicitudDiv;
	Cell nombreSSPPDiv;
	Cell nombreDocumentoDiv;
	Cell nombreInstalacionDestinoDiv;

	
	InputElement idSolicitud;
	InputElement nombreSSPP;
	InputElement nombreDocumento;
	InputElement nombreInstalacionDestino;


	
	
	
	
 @Override
 protected String getName() {
  return "listadocumentoingreso";
 }
 
 
	@Override
	protected void setDefaultValues(String prefixName) {
		
	}
	
}
