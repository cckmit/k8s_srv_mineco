package com.egoveris.edt.base.repository.eu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.edt.base.model.eu.Permiso;

/**
 * The Interface PermisoRepository.
 */
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

  /**
   * Devuelve todos los permisos filtrado por el nombre de grupo de usuarios.
   *
   * @param nombreGrupoUsuario
   *          the nombre grupo usuario
   * @return the list
   */

  List<Permiso> findByGrupoPermiso_GrupoUsuario(String nombreGrupoUsuario);

  /**
   * Obtiene el listado los permisos permitidos por el grupo usuario, filtrado
   * por el sistema.
   *
   * @param grupoUsuario
   *          the grupo usuario
   * @param sistema
   *          the sistema
   * @return List de permisos
   */
  List<Permiso> findByGrupoPermiso_GrupoUsuarioAndSistema(String grupoUsuario, String sistema);

  /**
   * Obtiene el listado de los sistemas con permisos asociados.
   *
   * @return List de nombres de aplicaciones
   */

  @Query(value = "SELECT DISTINCT (ap.sistema) FROM ADMINSADE_PERMISOS ap WHERE ap.sistema is not null", nativeQuery = true)
  List<String> filtrarListaAplicacionesGrupos();

  /**
   * Obtiene los permisos de asignador.
   *
   * @param permiso
   *          the permiso
   * @return Los permisos basicos para ser asignador
   */
  Permiso findByPermiso(String permiso);

  /**
   * Retrieves all permisos by sistema name.
   *
   * @param nombreSistema
   *          the nombre sistema
   * @return the list
   */
  List<Permiso> findBySistema(String nombreSistema);

}