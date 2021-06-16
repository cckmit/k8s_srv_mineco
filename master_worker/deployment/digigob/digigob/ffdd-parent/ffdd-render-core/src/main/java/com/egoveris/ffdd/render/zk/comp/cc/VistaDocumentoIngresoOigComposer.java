
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaDocumentoIngresoOigComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 1L;
  
  
  Cell oigDocumentoDiv;
  Cell nombreDocumentoDiv;
  Cell bodegaDestinoDiv;
  Cell numeroDocumentoDiv;
  Cell fechaDocumentoDiv;

  
  InputElement oigDocumento;
  InputElement nombreDocumento;
  InputElement bodegaDestino;
  InputElement numeroDocumento;
  InputElement fechaDocumento;

  
  

 @Override
	protected String getName() {
		return "vistadocumentoingresooig";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  
	}

}
