package com.egoveris.edt.base.service;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.PermisoDTO;

public interface IPermisoService {

  /**
   * Devuelve todos los permisos.
   *
   * @return the list
   */
  List<PermisoDTO> obtenerPermisos();

  /**
   * Filtra la lista de permisos disponibles para el usuario del sistema que
   * posee en la tabla de aplicacion del sistema . Si el nombre de la aplicacion
   * es un "TODOS" , devolvera toda la lista de permisos .
   *
   * @param nombreGrupoUsuario
   *          the nombre grupo usuario
   * @param sistema
   *          the sistema
   * @return the list
   */
  List<PermisoDTO> filtrarListaPermisosPorSistemaYGrupoUsuario(String nombreGrupoUsuario,
      String sistema);

  /**
   * Devuelve los distintos sistemas que poseen permisos .
   *
   * @return the list
   */
  List<String> filtrarListaAplicacionesGrupos();

  /**
   * Filtrar lista permisos por grupo usuario.
   *
   * @param nombreGrupoUsuario
   *          the nombre grupo usuario
   * @return the list
   */
  List<PermisoDTO> filtrarListaPermisosPorGrupoUsuario(String nombreGrupoUsuario);

  /**
   * Filtrar lista permisos por sistema.
   *
   * @param sistema
   *          the sistema
   * @return the list
   */
  List<PermisoDTO> filtrarListaPermisosPorSistema(String sistema);

  /**
   * Obtener permiso asignador.
   *
   * @return the permiso DTO
   */
  PermisoDTO obtenerPermisoAsignador();

}