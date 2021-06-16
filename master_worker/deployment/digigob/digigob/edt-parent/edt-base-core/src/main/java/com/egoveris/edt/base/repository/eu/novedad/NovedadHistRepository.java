package com.egoveris.edt.base.repository.eu.novedad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.edt.base.model.eu.novedad.NovedadHist;

/**
 * The Interface NovedadHistRepository.
 */
public interface NovedadHistRepository extends JpaRepository<NovedadHist, Integer> {

  /**
   * Find by revision order by fecha modificacion desc fecha inicio desc fecha
   * fin desc categoria asc estado asc.
   *
   * @param id
   *          the id
   * @return the list
   */
  List<NovedadHist> findByRevisionOrderByFechaModificacionDescFechaInicioDescFechaFinDescCategoriaAscEstadoAsc(
      Integer id);

}