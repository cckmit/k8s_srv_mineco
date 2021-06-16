
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ListaProductosDIComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 1L;
  
  Cell codigoProductoDiv;
  Cell descripcionProductoDiv;
  Cell cantidadTotalDiv;
  Cell cantidadUOMDiv;

  
  InputElement codigoProducto;
  InputElement descripcionProducto;
  InputElement cantidadTotal;
  InputElement cantidadUOM;

  
  

  @Override
	protected String getName() {
		return "listaproductosdi";
	}

	@Override
	protected void setDefaultValues(final String name) {
	

	}

}
