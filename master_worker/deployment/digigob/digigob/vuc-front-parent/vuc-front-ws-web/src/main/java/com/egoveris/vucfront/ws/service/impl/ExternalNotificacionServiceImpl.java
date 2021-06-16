package com.egoveris.vucfront.ws.service.impl;

import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.service.NotificacionService;
import com.egoveris.vucfront.ws.service.ExternalNotificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalNotificacionServiceImpl implements ExternalNotificacionService {

  @Autowired
  private NotificacionService baseService;

  @Override
  public void altaNotificacionTAD(String codSadeExpediente, DocumentoDTO notificacionDTO,
      String motivo) {
    baseService.altaNotificacionTAD(codSadeExpediente, notificacionDTO, motivo);
  }

  @Override
  public Boolean isDocumentNotified(String codSadeExpediente, String codSadeDocumento) {
    return baseService.isDocumentNotified(codSadeExpediente, codSadeDocumento);
  }

}