package com.egoveris.commons.firmadigital.itext.excepciones;

import org.terasoluna.plus.common.exception.ApplicationException;

public class CampoFirmadoException extends ApplicationException {
	private static final long serialVersionUID = -566611374730873812L;

	public CampoFirmadoException(final String nombreCampo) {
		super("El campo " + nombreCampo + " ya se encuentra firmado");
	}

}
