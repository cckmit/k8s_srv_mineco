package com.egoveris.edt.base.service;

import java.util.List;

import com.egoveris.edt.base.model.eu.usuario.RolDTO;

/**
 * The Interface IRolService.
 */
public interface IRolService {

  /**
   * Gets the roles activos.
   *
   * @return the roles activos
   */
  List<RolDTO> getRolesActivos();

  /**
   * Gets the roles ocultos.
   *
   * @return the roles ocultos
   */
  List<RolDTO> getRolesOcultos();

  /**
   * Gets the roles ocultos vigentes.
   *
   * @return the roles ocultos vigentes
   */
  List<RolDTO> getRolesOcultosVigentes();

  /**
   * Gets the roles vigentes.
   *
   * @return the roles vigentes
   */
  List<RolDTO> getRolesVigentes();

  /**
   * Eliminar.
   *
   * @param rol
   *          the rol
   * @return true, if successful
   */
  boolean eliminar(RolDTO rol);

  /**
   * Save.
   *
   * @param rol
   *          the rol
   */
  void save(RolDTO rol);

  /**
   * Refresh cache.
   */
  void refreshCache();

	public RolDTO getRolByRolNombre(String nombre);

	public RolDTO getRolById(Integer id);

}