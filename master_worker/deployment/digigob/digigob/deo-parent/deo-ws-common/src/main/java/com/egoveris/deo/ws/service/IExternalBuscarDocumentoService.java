package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalBuscarDocumentos;
import com.egoveris.deo.model.model.ResponseExternalBuscarDocumentos;
import com.egoveris.deo.ws.exception.ErrorConsultaNumeroSadeException;

public interface IExternalBuscarDocumentoService {

	
	public ResponseExternalBuscarDocumentos buscarDocumentoEnGedo(RequestExternalBuscarDocumentos request)
			throws ErrorConsultaNumeroSadeException;
}
