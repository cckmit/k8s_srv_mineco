package com.egoveris.vucfront.model.service;

import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.NotificacionDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;

import java.util.List;

public interface NotificacionService {

  /**
   * Creates a Notificacion from a Document.
   * 
   * @param codSadeExpediente
   * @param notificacionDTO
   * @param motivo
   */
  void altaNotificacionTAD(String codSadeExpediente, DocumentoDTO documento, String motivo);

  /**
   * Get's all the notifications from a person.
   * 
   * @param persona
   * @return
   */
  List<NotificacionDTO> getNotificacionesByPersona(PersonaDTO persona);

  /**
   * Checks if a Document is notified in the selected Expediente.
   * 
   * @param codSadeExpediente
   * @param documento
   * @return
   */
  Boolean isDocumentNotified(String codSadeExpediente, String codSadeDocumento);

  /**
   * Get's a Notificacion by it's ID.
   * 
   * @param id
   * @return
   */
  NotificacionDTO getNotificacionById(Long id);

  /**
   * Set a Notificacion as viewed.
   * 
   * @param notificacion
   */
  void setNotificacionAsViewed(NotificacionDTO notificacion);

  /**
   * Count the unseen Notificaciones from a Person.
   * 
   * @param persona
   * @return
   */
  Integer getUnseenNotifications(PersonaDTO persona);
}
