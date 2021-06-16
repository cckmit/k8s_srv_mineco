
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class HsCodeMatrizComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

 Cell idHSCodeMatrizDiv;
 Cell idMatrizVBDiv;
 Cell idHSCodeDiv;


	InputElement idHSCodeMatriz;
	InputElement idMatrizVB;
	InputElement idHSCode;




	@Override
	protected String getName() {
		return "hscodematriz";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.idHSCodeMatriz.setText("1");
		this.idMatrizVB.setText("2");
		this.idHSCode.setText("3");
	}

}
