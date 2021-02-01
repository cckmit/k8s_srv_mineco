
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class DocumentoTransporteComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell idDocumentoTransporteDiv;

	Cell secuencialDocTransporteDiv;

	Cell numeroDocTransporteDiv;

	Cell fechaDocTransporteDiv;

	Cell tipoDocTransporteDiv;

	Cell nombreNaveDiv;

	Cell numeroViajeDiv;

	Cell numeroManifiestoDiv;

	Cell idNaveDiv;


	InputElement idDocumentoTransporte;

	InputElement secuencialDocTransporte;

	InputElement numeroDocTransporte;

	InputElement fechaDocTransporte;

	InputElement tipoDocTransporte;

	InputElement nombreNave;

	InputElement numeroViaje;

	InputElement numeroManifiesto;

	InputElement idNave;



	@Override
	protected String getName() {
		return "address";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.secuencialDocTransporte.setText("12");
		this.numeroDocTransporte.setText("34");
		this.tipoDocTransporte.setText("TIPO A");
		this.nombreNave.setText("NAVE B");
		this.numeroViaje.setText("56");
		this.numeroManifiesto.setText("MAN78");
		this.idNave.setText("NAV123");

	}

}
