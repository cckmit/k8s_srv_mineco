package com.egoveris.edt.base.service.actuacion;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.actuacion.ActuacionAudiDTO;

/**
 * The Interface IActuacionAudiService.
 */
public interface IActuacionAudiService {

  /**
   * Guardar actuacion audi.
   *
   * @param actuacionAudi
   *          the actuacion audi
   * @throws NegocioException
   *           the negocio exception
   */
  void guardarActuacionAudi(ActuacionAudiDTO actuacionAudi) throws NegocioException;

}
