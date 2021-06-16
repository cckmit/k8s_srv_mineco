package com.egoveris.edt.base.service.actuacion;

import com.egoveris.edt.base.model.eu.actuacion.ActuacionDTO;

import java.util.List;

/**
 * The Interface IActuacionHelper.
 */
public interface IActuacionHelper {

  /**
   * Cachear lista actuaciones.
   */
  public void cachearListaActuaciones();

  /**
   * Obtener todas actuaciones.
   *
   * @return the list
   */
  public List<ActuacionDTO> obtenerTodasActuaciones();

}