package com.egoveris.edt.base.repository.eu.cargo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.edt.base.model.eu.cargo.CargoHistorico;

/**
 * The Interface CargoHistRepository.
 */
public interface CargoHistRepository extends JpaRepository<CargoHistorico, Integer> {

  /**
   * Find by revision order by fecha modificacion desc.
   *
   * @param id
   *          the id
   * @return the list
   */
  List<CargoHistorico> findByRevisionOrderByFechaModificacionDesc(Integer id);

  /**
   * Find all by order by fecha modificacion desc.
   *
   * @return the list
   */
  List<CargoHistorico> findAllByOrderByFechaModificacionDesc();

}