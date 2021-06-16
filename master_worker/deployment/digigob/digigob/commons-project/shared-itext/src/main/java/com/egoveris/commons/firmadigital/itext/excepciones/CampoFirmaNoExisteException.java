package com.egoveris.commons.firmadigital.itext.excepciones;

import org.terasoluna.plus.common.exception.ApplicationException;


public class CampoFirmaNoExisteException extends ApplicationException {
	private static final long serialVersionUID = -566611374730873812L;

	public CampoFirmaNoExisteException(String nombreCampo) {
		super("El campo " + nombreCampo + " no existe");
	}
}
