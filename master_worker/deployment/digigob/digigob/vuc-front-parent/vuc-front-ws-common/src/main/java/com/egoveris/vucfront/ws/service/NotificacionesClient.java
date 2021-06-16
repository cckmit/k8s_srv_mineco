package com.egoveris.vucfront.ws.service;

import com.egoveris.vucfront.model.model.DocumentoDTO;

public interface NotificacionesClient {

	  void altaNotificacionTAD(String codSadeExpediente, DocumentoDTO notificacionDTO, String motivo);

	  Boolean isDocumentNotified(String codSadeExpediente, String codSadeDocumento);	
	
}
