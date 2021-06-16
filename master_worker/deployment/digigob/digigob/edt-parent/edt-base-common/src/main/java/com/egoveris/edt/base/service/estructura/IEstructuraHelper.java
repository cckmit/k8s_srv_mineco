package com.egoveris.edt.base.service.estructura;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.EstructuraDTO;

/**
 * The Interface IEstructuraHelper.
 */
public interface IEstructuraHelper {

  /**
   * Cachear lista estructuras.
   */
  void cachearListaEstructuras();

  /**
   * Obtener todas estructuras.
   *
   * @return the list
   */
  List<EstructuraDTO> obtenerTodasEstructuras();

}
