package com.egoveris.deo.base.service;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface IPrevisualizacionDocumentoService {

	public byte[] obtenerPrevisualizacionDocumentoReducida(String numeroSade)throws ApplicationException;
	
	public byte[] obtenerPrevisualizacionDocumentoReducidaBytes(byte[] pdf)throws ApplicationException;
	
	public int obtenerMaximoPrevisualizacion();
	
}
