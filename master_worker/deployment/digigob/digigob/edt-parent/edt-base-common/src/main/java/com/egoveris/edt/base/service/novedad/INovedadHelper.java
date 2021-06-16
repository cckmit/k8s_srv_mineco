package com.egoveris.edt.base.service.novedad;

import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;

import java.util.List;

/**
 * The Interface INovedadHelper.
 */
public interface INovedadHelper {

  /**
   * Obtener todas las novedades.
   *
   * @return the list
   */
  List<NovedadDTO> obtenerTodas();

  /**
   * Cachear lista novedades.
   */
  void cachearListaNovedades();

}