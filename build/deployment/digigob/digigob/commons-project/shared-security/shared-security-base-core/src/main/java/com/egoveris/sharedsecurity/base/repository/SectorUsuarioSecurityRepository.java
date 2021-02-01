package com.egoveris.sharedsecurity.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.sharedsecurity.base.model.SectorUsuario;

/**
 * The Interface SectorUsuarioRepository.
 */
public interface SectorUsuarioSecurityRepository extends JpaRepository<SectorUsuario, Integer> {

  /**
   * Find by nombre usuario and estado true.
   *
   * @param nombreUsuario
   *          the nombre usuario
   * @return the sector usuario
   */
  SectorUsuario findByNombreUsuarioAndEstadoTrue(String nombreUsuario);

  

}