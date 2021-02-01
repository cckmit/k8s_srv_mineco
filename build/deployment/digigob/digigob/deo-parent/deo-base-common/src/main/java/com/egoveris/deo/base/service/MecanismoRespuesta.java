package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.ErrorInvocacionException;
import com.egoveris.deo.base.exception.InconsistenciaDeDatosException;
import com.egoveris.deo.model.model.GedoADestinatariosMessageRequest;
import com.egoveris.deo.model.model.GedoADestinatariosMessageResponse;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface MecanismoRespuesta {

	public static final String MECANISMO_RESPUESTA_OK= "OK";
	public static final String MECANISMO_RESPUESTA_ERROR = "ERROR";
	
	public GedoADestinatariosMessageResponse execute(GedoADestinatariosMessageRequest gedoADestinatariosMessage)
			throws ApplicationException, ErrorInvocacionException, InconsistenciaDeDatosException;
	
}
