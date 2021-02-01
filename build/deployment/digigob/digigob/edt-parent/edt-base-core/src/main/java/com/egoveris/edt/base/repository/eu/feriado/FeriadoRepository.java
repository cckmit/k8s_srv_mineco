package com.egoveris.edt.base.repository.eu.feriado;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.edt.base.model.eu.feriado.Feriado;

/**
 * The Interface FeriadoRepository.
 *
 * @author lfishkel
 */
public interface FeriadoRepository extends JpaRepository<Feriado, Integer> {

  /**
   * Devuelve los feriados ordenados por fecha.
   *
   * @return the list
   */
  List<Feriado> findAllByOrderByFecha();

}