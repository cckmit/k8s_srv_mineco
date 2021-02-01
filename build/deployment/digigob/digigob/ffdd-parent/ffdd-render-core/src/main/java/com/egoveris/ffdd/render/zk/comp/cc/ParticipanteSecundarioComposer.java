
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ParticipanteSecundarioComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell participanteIDDiv;
	Cell personaTypeEnumDiv;
	Cell docPersonaTypeDiv;
	Cell docPersonaNumDiv;
	Cell participanteNombreDiv;
	Cell participanteAddressDiv;
	Cell participanteEmailDiv;
	Cell participanteNumeroTelefonoFijoDiv;
	Cell participanteNumeroTelefonoMovilDiv;

	InputElement participanteID;
	InputElement personaTypeEnum;
	InputElement docPersonaType;
	InputElement docPersonaNum;
	InputElement participanteNombre;
	SeparatorComplex participanteAddress;
	InputElement participanteEmail;
	InputElement participanteNumeroTelefonoFijo;
	InputElement participanteNumeroTelefonoMovil;

	@Override
	protected String getName() {
		return "participantesecundario";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.participanteNombre.setText("Nombre default");
		this.docPersonaNum.setText("12.345.678-9");
		this.participanteEmail.setText("participante@email.com");
		this.participanteNumeroTelefonoMovil.setText("+56 9 87654321");
	}

}
