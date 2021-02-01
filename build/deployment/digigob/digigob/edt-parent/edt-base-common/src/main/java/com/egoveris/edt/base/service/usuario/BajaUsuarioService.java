package com.egoveris.edt.base.service.usuario;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;

/**
 * The Interface BajaUsuarioService.
 */
public interface BajaUsuarioService {

  /**
   * Baja de usuario .
   *
   * @param usuario
   *          the usuario
   * @throws SecurityNegocioException
   *           the security negocio exception
   */
  public void bajaUsuario(String usuario) throws SecurityNegocioException;

}
