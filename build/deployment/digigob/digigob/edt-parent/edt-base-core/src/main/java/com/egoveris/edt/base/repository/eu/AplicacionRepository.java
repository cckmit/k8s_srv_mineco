/**
 * 
 */
package com.egoveris.edt.base.repository.eu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.edt.base.model.eu.Aplicacion;

/**
 * The Interface AplicacionRepository.
 *
 * @author pfolgar
 */
public interface AplicacionRepository extends JpaRepository<Aplicacion, Integer> {

  /**
   * Find all by order by nombre aplicacion asc.
   *
   * @return the list
   */
  List<Aplicacion> findAllByOrderByNombreAplicacionAsc();

  /**
   * Metodo busca registros según nombreAplicación.
   *
   * @param nombreAplicacion
   *          the nombre aplicacion
   * @return the aplicacion
   */
  Aplicacion findByNombreAplicacion(String nombreAplicacion);

  /**
   * Metodo busca registros según idAplicacion.
   *
   * @param idAplicacion
   *          the id aplicacion
   * @return the aplicacion
   */
  Aplicacion findByIdAplicacion(Integer idAplicacion);

  /**
   * Metodo queretorna las aplicaciones que tengan el el valor Visible en true.
   *
   * @return the list
   */
  List<Aplicacion> findByVisibleTrue();

}