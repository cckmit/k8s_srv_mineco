
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ResumenOpDusComposer extends ComplexComponentComposer {


  private static final long serialVersionUID = 1L;
  
 Cell codOperacionDiv;
	Cell destinacionAduaneraDiv;
	Cell fechaCreacionDiv;
	Cell processingStatusDiv;

	InputElement codOperacion;
	InputElement destinacionAduanera;
	InputElement fechaCreacion;
	InputElement processingStatus;




	@Override
	protected String getName() {
		return "resumenopdus";
	}

	@Override
	protected void setDefaultValues(final String name) {
		
	}

}
