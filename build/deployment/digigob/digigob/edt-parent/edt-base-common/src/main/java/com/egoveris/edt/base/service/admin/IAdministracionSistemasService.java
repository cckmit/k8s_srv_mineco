package com.egoveris.edt.base.service.admin;

import java.util.List;

/**
 * The Interface IAdministracionSistemasService.
 */
public interface IAdministracionSistemasService {

  /**
   * Obtener sistemas por usuario.
   *
   * @param nombreUsuario
   *          the nombre usuario
   * @return the list
   */
  public List<String> obtenerSistemasPorUsuario(String nombreUsuario);
}
