
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

public class VistaMercanciaDinComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 1L;

	Cell totalBultosDiv;
	Cell identificacionDeBultosDiv;
	Cell totalPesoBrutoDiv;
	Cell numeroTotalDeItemsDiv;
	Cell itemDiv;

	InputElement totalBultos;
	InputElement identificacionDeBultos;
	InputElement totalPesoBruto;
	InputElement numeroTotalDeItems;
	SeparatorComplex item;


	@Override
	protected String getName() {
		return "VistaMercanciaDinComposer";
	}

	@Override
	protected void setDefaultValues(final String name) {

	}

}
