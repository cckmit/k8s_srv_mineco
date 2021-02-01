
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaManifiestoDinComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 1L;
  
  
	Cell secuencialManifiestoDiv;
	Cell numeroManifiestoDiv;
	Cell fechaManifiestoDiv;

	InputElement secuencialManifiesto;
	InputElement numeroManifiesto;
	InputElement fechaManifiesto;

  
  
  
	@Override
	protected String getName() {
		return "VistaManifiestoDin";
	}

	@Override
	protected void setDefaultValues(final String name) {

	  
	}

}
