package com.egoveris.te.base.service;

import com.egoveris.te.model.model.TipoReservaDTO;

/**
 * The Interface TipoReservaService.
 */
public interface TipoReservaService {

  /**
   * Buscar tipo reserva.
   *
   * @param tipoReserva
   *          the tipo reserva
   * @return the tipo reserva
   */
  public TipoReservaDTO buscarTipoReserva(String tipoReserva);
  
}
