package com.egoveris.edt.base.service.grupos;

import java.util.List;

/**
 * The Interface IGruposAplicacionService.
 */
public interface IGruposAplicacionService {

  /**
   * Buscar grupos usuario aplicacion.
   *
   * @param user
   *          the user
   * @return the list
   */
  public List<String> buscarGruposUsuarioAplicacion(String user);
}
