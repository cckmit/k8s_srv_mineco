package com.egoveris.sharedsecurity.base.service;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;

/**
 * The Interface ISectorUsuarioService.
 */
public interface ISectorUsuarioService {

  /**
   * Vincular sector usuario.
   *
   * @param sectorUsuario
   *          the sector usuario
   */
  void vincularSectorUsuario(SectorUsuarioDTO sectorUsuario);

  /**
   * Modificar sector usuario.
   *
   * @param sectorUsuario
   *          the sector usuario
   */
  void modificarSectorUsuario(SectorUsuarioDTO sectorUsuario);

  /**
   * Gets SectorUsuarioDTO by username.
   *
   * @param uid
   *          the uid
   * @return SectorUsuarioDTO
   */
  SectorUsuarioDTO getByUsername(String uid);

  /**
   * Verificar Sector mesa actualizado.
   *
   * @param userName
   *          the user name
   * @return the boolean
   */
  Boolean sectorMesaActualizado(String userName);

  /**
   * Desvincular sector usuario.
   *
   * @param userName
   *          the user name
   */
  void desvincularSectorUsuario(String userName);

  /**
   * Obtener username por sector.
   *
   * @param idSector
   *          the id sector
   * @return the list
   */
  List<String> obtenerUsernamePorSector(Integer idSector);

}