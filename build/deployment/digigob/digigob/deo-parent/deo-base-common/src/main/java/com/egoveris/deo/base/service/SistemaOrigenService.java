package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.SistemaOrigenDTO;

public interface SistemaOrigenService {

  /**
   * Servicio para acceder a los jpa repositorios desde deo-ws
   * @param sistema
   * @return
   */
  SistemaOrigenDTO findByNombreLike(String sistema);
}
