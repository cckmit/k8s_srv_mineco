package com.egoveris.edt.base.repository;

import java.util.List;

import com.egoveris.edt.base.repository.eu.usuario.UsuarioGenerico;

/**
 * The Interface UsuariosRepository.
 *
 * @author pfolgar
 */
public interface UsuariosRepository {

  /**
   * Este metodo busca las aplicaciones configuradas para un usuario segun la
   * vista que se necesite.
   *
   * @param userName
   *          the user name
   * @return devuelve una lista con los Id's de las aplicaciones configuradas
   *         por un usuario en una vista o una lista vacia en caso de no tener
   *         aplicaciones configuradas
   */
  List<? extends UsuarioGenerico> buscarAplicacionesPorUsuario(String userName);

  /**
   * Save.
   *
   * @param usuarioAplicacion
   *          the usuario aplicacion
   */
  void save(UsuarioGenerico usuarioAplicacion);

  /**
   * Delete.
   *
   * @param usuarioAplicacion
   *          the usuario aplicacion
   */
  void delete(UsuarioGenerico usuarioAplicacion);

}