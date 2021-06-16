package com.egoveris.edt.base.service;

import com.egoveris.edt.base.model.eu.CalleDTO;

import java.util.List;

/**
 * The Interface ICalleService.
 */
public interface ICalleService {

  /**
   * Lista calles.
   *
   * @return the list
   */
  List<CalleDTO> listCalles();

  /**
   * Buscar calle por parametro.
   *
   * @param parametro
   *          the parametro
   * @return the list
   */
  List<CalleDTO> buscarCallePorParametro(String parametro);

  /**
   * Buscar calle por ID.
   *
   * @param id
   *          the id
   * @return the calle DTO
   */
  CalleDTO buscarCallePorID(Integer id);

}
