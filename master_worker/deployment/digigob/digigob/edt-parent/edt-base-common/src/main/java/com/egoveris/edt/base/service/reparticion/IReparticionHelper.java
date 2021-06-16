package com.egoveris.edt.base.service.reparticion;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

/**
 * The Interface IReparticionHelper.
 */
public interface IReparticionHelper {

  /**
   * Cachear lista reparticiones.
   */
  public void cachearListaReparticiones();

  /**
   * Obtener todas las reparticiones.
   *
   * @return the list
   */
  public List<ReparticionDTO> obtenerTodosReparticiones();

}
