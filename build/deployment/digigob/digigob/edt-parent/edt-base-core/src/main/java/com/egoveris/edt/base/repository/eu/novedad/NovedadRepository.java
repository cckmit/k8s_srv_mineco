package com.egoveris.edt.base.repository.eu.novedad;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.edt.base.model.eu.novedad.Novedad;

/**
 * The Interface NovedadRepository.
 */
public interface NovedadRepository extends JpaRepository<Novedad, Integer> {

  /**
   * Find by estado and fecha fin after order by fecha modificacion desc fecha
   * inicio desc fecha fin desc categoria asc estado asc.
   *
   * @param estado
   *          the estado
   * @return the list
   */
  List<Novedad> findByEstadoAndFechaFinAfterOrderByFechaModificacionDescFechaInicioDescFechaFinDescCategoriaAscEstadoAsc(
      String estado, Date fechaFin);

  /**
   * Find by estado in and fecha fin after order by fecha modificacion desc
   * fecha inicio desc fecha fin desc categoria asc estado asc.
   *
   * @param estados
   *          the estados
   * @return the list
   */
  List<Novedad> findByEstadoInAndFechaFinAfterOrderByFechaModificacionDescFechaInicioDescFechaFinDescCategoriaAscEstadoAsc(
      List<String> estados, Date fecha);

  /**
   * Find by estado.
   *
   * @param estado
   *          the estado
   * @return the list
   */
  List<Novedad> findByEstado(String estado);

  /**
   * Find all by order by fecha modificacion desc fecha inicio desc fecha
   * findesc categoria asc estado asc.
   *
   * @return the list
   */
  List<Novedad> findAllByOrderByFechaModificacionDescFechaInicioDescFechaFinDescCategoriaAscEstadoAsc();

  /**
   * Find by categoria categoria nombre.
   *
   * @param categoriaNombre
   *          the categoria nombre
   * @return the list
   */
  List<Novedad> findByCategoria_CategoriaNombre(String categoriaNombre);

  /**
   * Find by fecha inicio before and estado and fecha fin after.
   *
   * @param fechaInicio
   *          the fecha inicio
   * @param estado
   *          the estado
   * @param fechaFin
   *          the fecha fin
   * @return the list
   */
  List<Novedad> findByFechaInicioBeforeAndEstadoAndFechaFinAfter(Date fechaInicio, String estado,
      Date fechaFin);

  /**
   * Find by fecha fin before and estado.
   *
   * @param fechaFin
   *          the fecha fin
   * @param estado
   *          the estado
   * @return the list
   */
  List<Novedad> findByFechaFinBeforeAndEstado(Date fechaFin, String estado);

}