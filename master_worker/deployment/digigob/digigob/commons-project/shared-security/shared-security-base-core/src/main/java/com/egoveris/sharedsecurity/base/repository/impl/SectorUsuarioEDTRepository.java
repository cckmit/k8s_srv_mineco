package com.egoveris.sharedsecurity.base.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.sharedsecurity.base.model.SectorUsuario;

/**
 * The Interface SectorUsuarioRepository.
 */
public interface SectorUsuarioEDTRepository extends JpaRepository<SectorUsuario, Integer> {

  /**
   * Find by nombre usuario and estado true.
   *
   * @param nombreUsuario
   *          the nombre usuario
   * @return the sector usuario
   */
  SectorUsuario findByNombreUsuarioAndEstadoTrue(String nombreUsuario);

  /**
   * Find by cargo id.
   *
   * @param id
   *          the id
   * @return the list
   */ 
  List<SectorUsuario> findByCargoId(Integer id);

  /**
   * Find by sector id and estado true.
   *
   * @param idSector
   *          the id sector
   * @return the list
   */
  List<SectorUsuario> findBySector_IdAndEstadoTrue(Integer idSector);
  
  
  

}