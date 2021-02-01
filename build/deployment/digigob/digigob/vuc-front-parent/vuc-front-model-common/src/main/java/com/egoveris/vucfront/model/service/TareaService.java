package com.egoveris.vucfront.model.service;

import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.model.TareaDTO;

import java.util.List;

public interface TareaService {

  /**
   * Saves a Tarea
   * 
   * @param tarea
   */
  void saveTarea(TareaDTO tarea);

  /**
   * Get all the Tareas Pendiente from a Persona.
   * 
   * @param persona
   * @return
   */
  List<TareaDTO> getTareasPendientesByPersona(PersonaDTO persona);

  /**
   * Set a Tarea as viewed.
   * 
   * @param tarea
   */
  void setTareaAsViewed(TareaDTO tarea);

  /**
   * Count the unseen Tareas from a Person.
   * 
   * @param persona
   * @return
   */
  Integer getUnseenTareas(PersonaDTO persona);

  void confirmarTareaSubsanacion(ExpedienteBaseDTO expediente, List<DocumentoDTO> listDocSubsanados);
  
}
