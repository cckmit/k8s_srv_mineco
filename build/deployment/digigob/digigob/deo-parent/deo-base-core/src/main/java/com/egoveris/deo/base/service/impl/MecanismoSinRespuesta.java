package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.ErrorInvocacionException;
import com.egoveris.deo.base.exception.InconsistenciaDeDatosException;
import com.egoveris.deo.base.service.MecanismoRespuesta;
import com.egoveris.deo.model.model.GedoADestinatariosMessageRequest;
import com.egoveris.deo.model.model.GedoADestinatariosMessageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class MecanismoSinRespuesta implements MecanismoRespuesta {

	private static final Logger LOGGER = LoggerFactory.getLogger(MecanismoSinRespuesta.class);
	
	public GedoADestinatariosMessageResponse execute(GedoADestinatariosMessageRequest gedoADestinatariosMessage)
			throws ApplicationException, ErrorInvocacionException,
			InconsistenciaDeDatosException {

		LOGGER.info("No existe un mecanismo de respuesta asociada a la peticion.");

		GedoADestinatariosMessageResponse gedoADestinatariosMessageResponse = new GedoADestinatariosMessageResponse();
		
		gedoADestinatariosMessageResponse.setRespuesta("ERROR");
		gedoADestinatariosMessageResponse.setMotivo("No existe una DIRECCION_RESPUESTA asociada al Sistema Origen: " + 
				gedoADestinatariosMessage.getNombreSistemaOrigen());
		gedoADestinatariosMessageResponse.setIdError("1");
		
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("execute(GedoADestinatariosMessageRequest) - end"); //$NON-NLS-1$
    }
  		return gedoADestinatariosMessageResponse;
	}
}
