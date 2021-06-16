
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class DetallesDocumentoInstalacionComposer extends ComplexComponentComposer {


  private static final long serialVersionUID = 1L;
  
  
  Cell nombreSSPPDiv;
  Cell codigoDocumentoDiv;
  Cell nombreDocumentoDiv;
  Cell idSolicitudDiv;
  Cell nombreInstalacionDiv;
  Cell regionInstalacionDiv;
  Cell comunaInstalacionDiv;

  InputElement nombreSSPP;
  InputElement codigoDocumento;
  InputElement nombreDocumento;
  InputElement idSolicitud;
  InputElement nombreInstalacion;
  InputElement regionInstalacion;
  InputElement comunaInstalacion;

  

  @Override
	protected String getName() {
		return "detallesinstalacion";
	}

	@Override
	protected void setDefaultValues(final String name) {

	}

}
