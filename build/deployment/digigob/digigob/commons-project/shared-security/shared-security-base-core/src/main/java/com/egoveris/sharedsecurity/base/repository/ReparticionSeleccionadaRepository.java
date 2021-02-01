package com.egoveris.sharedsecurity.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.model.ReparticionSeleccionada;
import com.egoveris.sharedsecurity.base.model.Sector;

/**
 * The Interface ReparticionSeleccionadaRepository.
 */
public interface ReparticionSeleccionadaRepository
    extends JpaRepository<ReparticionSeleccionada, Integer> {

  /**
   * Find by usuario.
   *
   * @param userName
   *          the user name
   * @return the reparticion seleccionada
   */
  ReparticionSeleccionada findByUsuario(String userName);

  /**
   * Find by reparticion.
   *
   * @param reparticion
   *          the reparticion
   * @return the list
   */
  List<ReparticionSeleccionada> findByReparticion(Reparticion reparticion);

  /**
   * Find by reparticion and sector.
   *
   * @param reparticion
   *          the reparticion
   * @param sector
   *          the sector
   * @return the list
   */
  List<ReparticionSeleccionada> findByReparticionAndSector(Reparticion reparticion, Sector sector);
}
