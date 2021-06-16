package com.egoveris.sharedsecurity.base.repository.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.sharedsecurity.base.model.Reparticion;

/**
 * The Interface ReparticionRepository.
 */
public interface ReparticionEDTRepository extends JpaRepository<Reparticion, Integer> {

	/**
	 * Find all reparticion.
	 *
	 * @return the list
	 */
	@Query("SELECT rep FROM Reparticion rep WHERE rep.vigenciaDesde <= :vigencia AND rep.vigenciaHasta >= :vigencia AND rep.estadoRegistro = 1")
	List<Reparticion> findAllReparticion(@Param("vigencia") Date vigencia);
	
	@Query("SELECT Concat(rep.nombre, ' (', rep.codigo, ')') FROM Reparticion rep")
	List<String> obtenerReparticiones();
	
  /**
   * Find by estado registro true and codigo reparticion and vigencia desde
   * before and vigencia hasta after.
   *
   * @param Codigo
   *          the codigo reparticion
   * @param vigenciaDesde
   *          the vigencia desde
   * @param vigenciaHasta
   *          the vigencia hasta
   * @return the reparticion
   */
	@Query("SELECT rep FROM Reparticion rep WHERE rep.codigo = :codigo AND rep.vigenciaDesde <= :vigencia AND rep.vigenciaHasta >= :vigencia "
			+ "AND rep.estadoRegistro = 1")
	Reparticion findByCodigoAndEstadoRegistroTrue(@Param("codigo") String codigo, @Param("vigencia") Date vigencia);

  /**
   * Find by codigo reparticion.
   *
   * @param Codigo
   *          the codigo reparticion
   * @return the reparticion
   */
  Reparticion findByCodigo(String codigo);

  /**
   * Find by nombre containing or codigo reparticion containing.
   *
   * @param nombreReparticion
   *          the nombre reparticion
   * @param Codigo
   *          the codigo reparticion
   * @return the list
   */
  List<Reparticion> findByNombreContainingOrCodigoContaining(String nombreReparticion,
      String codigo);

  /**
   * Find by codigo reparticion containing.
   *
   * @param Codigo
   *          the codigo reparticion
   * @return the list
   */
  List<Reparticion> findByCodigoContaining(String codigo);
  
  /**
   * Obtener reparticion username.
   *
   * @param userName the user name
   * @return the reparticion
   */
  @Query(value = "SELECT rtrim(ltrim(r.codigo_reparticion)) codigo_reparticion, r.nombre_reparticion, r.id_reparticion, r.estado_registro, r.vigencia_desde, r.cod_dgtal, r.vigencia_hasta, r.es_dgtal, r.id_estructura, rep_padre FROM sade_sector_usuario su, sade_reparticion r, sade_sector_interno si WHERE su.nombre_usuario=?1 AND si.id_sector_interno=su.id_sector_interno AND si.codigo_reparticion=r.id_reparticion AND su.estado_registro = 1", nativeQuery = true)
  Reparticion obtenerReparticionUsername(String userName);

  /**
   * Obtener all reparticion username.
   *
   * @param userName the user name
   * @return the list
   */
  @Query(value = "SELECT r.CODIGO_REPARTICION, si.NOMBRE_SECTOR_INTERNO,cargo.CARGO FROM edt_sade_sector_usuario su, edt_sade_reparticion r, edt_sade_sector_interno si, edt_cargos cargo WHERE su.nombre_usuario=?1 AND si.id_sector_interno=su.id_sector_interno AND si.codigo_reparticion=r.id_reparticion AND su.estado_registro = 1 and cargo.id = su.CARGO_ID", nativeQuery = true)
  List<Map> obtenerAllReparticionUsername(String userName);
  
  
  /**
   * Find by estructura id.
   *
   * @param idEstructura the id estructura
   * @return the list
   */
  List<Reparticion> findByEstructura_Id(Integer idEstructura);
  
}