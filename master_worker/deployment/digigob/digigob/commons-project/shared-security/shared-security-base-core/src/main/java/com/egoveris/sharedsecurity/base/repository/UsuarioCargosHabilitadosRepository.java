package com.egoveris.sharedsecurity.base.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitada;

/**
 * The Interface UsuarioReparticionHabilitadaRepository.
 */
public interface UsuarioCargosHabilitadosRepository
    extends JpaRepository<UsuarioReparticionHabilitada, Integer> {

  /**
   * Find by cargo id.
   *
   * @param id
   *          the id
   * @return the list
   */
  List<UsuarioReparticionHabilitada> findByCargoId(Integer id);

  /**
   * Find by nombre usuario and reparticion vigencia hasta after and reparticion
   * vigencia desde before.
   *
   * @param username
   *          the username
   * @param vigenciaHasta
   *          the vigencia hasta
   * @param vigenciaDesde
   *          the vigencia desde
   * @return the list
   */
  List<UsuarioReparticionHabilitada> findByNombreUsuarioAndReparticion_VigenciaHastaAfterAndReparticion_VigenciaDesdeBefore(
      String username, Date vigenciaHasta, Date vigenciaDesde);

  
}