package com.egoveris.commons.firmadigital.itext.excepciones;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SinCamposFirmaException extends ApplicationException {
	private static final long serialVersionUID = -566611374730873812L;

	public SinCamposFirmaException(final String str) {
		super("El documento no contiene campos de firma en blanco disponibles para usar " + str);
	}

	public SinCamposFirmaException(final String string, final Exception ex) {
		super(string, ex);
	}

}
