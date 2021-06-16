package com.egoveris.edt.base.service.usuario;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.cargo.CargoHistoricoDTO;

/**
 * The Interface ICargoHistService.
 */
public interface ICargoHistService {

  /**
   * Gets the historiales.
   *
   * @return the historiales
   */
  List<CargoHistoricoDTO> getHistoriales();

  /**
   * Gets the historial.
   *
   * @param id
   *          the id
   * @return the historial
   */
  List<CargoHistoricoDTO> getHistorial(Integer id);

}
