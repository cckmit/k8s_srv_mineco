package com.egoveris.deo.ws.service;

import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;

import java.util.List;

public interface IExternalActualizarVisualizacionService {

	public void actualizarEstadoVisualizacion(String username,
			List<String> lstRectoras, List<String> lstDocumentos)
			throws ParametroInvalidoException, DocumentoNoExisteException,
			ErrorConsultaDocumentoException;
	
}
