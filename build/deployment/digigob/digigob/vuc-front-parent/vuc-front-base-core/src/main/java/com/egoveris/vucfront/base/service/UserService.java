package com.egoveris.vucfront.base.service;

import com.egoveris.vucfront.model.model.PersonaDTO;

public interface UserService {

  /**
   * Gets Persona by cuit.
   *
   * @param Persona cuit
   * @return the Persona by cuit
   */
  PersonaDTO getPersonaByCuit(String cuit);
  
  PersonaDTO save(PersonaDTO persona);
  
  public Boolean isIdentidadExterna();
}
