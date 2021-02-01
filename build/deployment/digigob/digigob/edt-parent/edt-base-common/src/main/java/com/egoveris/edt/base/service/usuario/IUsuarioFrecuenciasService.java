package com.egoveris.edt.base.service.usuario;

import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;

/**
 * The Interface IUsuarioFrecuenciasService.
 */
public interface IUsuarioFrecuenciasService {

  /**
   * Insertar usuario frecuencias.
   *
   * @param usuarioFrecuencia
   *          the usuario frecuencia
   */
  public void insertarUsuarioFrecuencias(UsuarioFrecuenciaDTO usuarioFrecuencia);

  /**
   * Buscar frecuencias por usuario.
   *
   * @param userName
   *          the user name
   * @return the usuario frecuencia DTO
   */
  public UsuarioFrecuenciaDTO buscarFrecuenciasPorUsuario(String userName);

}
