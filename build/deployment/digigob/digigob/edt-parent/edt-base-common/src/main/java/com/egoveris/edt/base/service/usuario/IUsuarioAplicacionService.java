package com.egoveris.edt.base.service.usuario;

import com.egoveris.edt.base.model.eu.usuario.UsuarioGenericoDTO;

import java.util.List;

public interface IUsuarioAplicacionService {

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
  List<Integer> buscarAplicacionesPorUsuario(String userName);

  /**
   * Este metodo invoca al metodo del DAO que inserta un objeto en la base.
   */

  void insertarUsuarioAplicacion(UsuarioGenericoDTO usuarioAplicacion);

  /**
   * Este metodo invoca al metodo del DAO que borra un objeto de la base.
   */

  void borrarUsuarioAplicacion(UsuarioGenericoDTO usuarioAplicacion);

}