package com.egoveris.edt.base.service;

import com.egoveris.edt.base.model.eu.CategoriaDTO;

import java.util.List;

/**
 * The Interface ICategoriaService.
 */
public interface ICategoriaService {

  /**
   * Gets all CategoriaDTO.
   *
   * @return the all
   */
  public List<CategoriaDTO> getAll();

  /**
   * Gets CategoriaDTO by id.
   *
   * @param id
   *          the id
   * @return the by id
   */
  public CategoriaDTO getById(Integer id);
}
