package com.egoveris.edt.base.repository.eu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.edt.base.model.eu.Estructura;

/**
 * The Interface EstructuraRepository.
 */
public interface EstructuraRepository extends JpaRepository<Estructura, Integer> {

  /**
   * Find by estructura poder ejecutivo.
   *
   * @param estructuraPoderEjecutivo
   *          the estructura poder ejecutivo
   * @return the list
   */
  List<Estructura> findByEstructuraPoderEjecutivo(String estructuraPoderEjecutivo);

  /**
   * Find by nombre estructura in.
   *
   * @param nombresEstructura
   *          the nombres estructura
   * @return the list
   */
  List<Estructura> findByNombreEstructuraIn(List<String> nombresEstructura);

  /**
   * Find by nombre estructura or codigo estructura.
   *
   * @param nombre the nombre
   * @param codigo the codigo
   * @return the list
   */
  @Query(value = "SELECT * FROM edt_sade_estructura WHERE estado_registro = 1 AND (nombre_estructura LIKE %?1% OR codigo_estructura LIKE CONCAT('%',?2,'%'))", nativeQuery = true)
  List<Estructura> findByEstadoRegistroTrueAndNombreEstructuraContainingOrCodigoEstructuraContaining(
      String nombre, String codigo);

  /**
   * Find by codigo estructura.
   *
   * @param codigoEstructura
   *          the codigo estructura
   * @return the estructura
   */
  Estructura findByCodigoEstructura(Integer codigoEstructura);

}