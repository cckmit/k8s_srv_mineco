
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class DocumentoComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell idDocumentoDiv;

	Cell codigoDocumentoDiv;

	Cell nombreDocumentoDiv;

	Cell tipoDocumentoDiv;

	Cell paisDocumentoDiv;

	Cell fechaDocumentoDiv;

	Cell participanteDiv;

	InputElement idDocumento;

	InputElement codigoDocumento;

	InputElement nombreDocumento;

	InputElement tipoDocumento;

	InputElement paisDocumento;

	InputElement fechaDocumento;

	SeparatorComplex participante;



	@Override
	protected String getName() {
		return "documento";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.codigoDocumento.setText("cod A");
		this.nombreDocumento.setText("nom B");
		this.tipoDocumento.setText("tipo C");
		this.paisDocumento.setText("pais D");
	}

}
