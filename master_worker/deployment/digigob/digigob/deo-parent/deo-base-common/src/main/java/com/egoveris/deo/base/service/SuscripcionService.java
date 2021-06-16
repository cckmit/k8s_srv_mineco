package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.SuscripcionDTO;

public interface SuscripcionService {

  /**
   * Para acceder desde servicios externos en deo-ws
   * @param suscripcion
   */
  public void save(SuscripcionDTO suscripcion);
  
}
