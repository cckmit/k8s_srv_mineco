
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class AcknowledgeComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell idMensajeDiv;

	Cell idTransaccionDiv;
	
	Cell codigoRecepcionDiv;
	
	Cell descripcionRecepcionDiv; 


	InputElement idMensaje;

	InputElement idTransaccion;
	
 InputElement codigoRecepcion;

 InputElement descripcionRecepcion;



	@Override
	protected String getName() {
		return "acknowledge";
	}

	@Override
	protected void setDefaultValues(final String name) {
 	this.idMensaje.setText("ss");;
 	this.idTransaccion.setText("gg");
 	this.codigoRecepcion.setText("acknow");
 	this.descripcionRecepcion.setText("aabbbccc");
	}

}
