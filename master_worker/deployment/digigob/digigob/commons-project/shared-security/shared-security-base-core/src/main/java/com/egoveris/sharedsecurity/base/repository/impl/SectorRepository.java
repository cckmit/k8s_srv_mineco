package com.egoveris.sharedsecurity.base.repository.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.sharedsecurity.base.model.Sector;

/**
 * The Interface SectorRepository.
 */
public interface SectorRepository extends JpaRepository<Sector, Integer> {

  /**
   * Find by id and vigencia desde after and vigencia desde before and estado
   * true.
   *
   * @param id          the id
   * @param vigencia the vigencia
   * @return the list
   */ 
	@Query("SELECT sect FROM Sector sect WHERE sect.reparticion.id = :idReparticion "
			+ "AND (sect.vigenciaDesde <= :vigencia AND sect.vigenciaHasta >= :vigencia) AND sect.estadoRegistro = 1")
	List<Sector> findByReparticionEstadoRegistroTrue(@Param("idReparticion") Integer id, @Param("vigencia") Date vigencia);

  /**
   * Find by id and vigencia desde after and vigencia desde before and estado
   * true and sector mesa.
   *
   * @param id          the id
   * @param vigencia the vigencia
   * @param sectorMesa          the sector mesa
   * @return the list
   */
	@Query("SELECT sect FROM Sector sect WHERE sect.reparticion.id = :idReparticion "
			+ "AND (sect.vigenciaDesde <= :vigencia AND sect.vigenciaHasta >= :vigencia) AND sect.estadoRegistro = 1 AND sect.sectorMesa = :sectorMesa")
	List<Sector> findByReparticionEstadoRegistroTrueAndSectorMesa(@Param("idReparticion") Integer id,
			@Param("vigencia") Date vigencia, @Param("sectorMesa") String sectorMesa);

  /**
   * Find by id and vigencia desde after and vigencia desde before and estado
   * true and mesa virtual true.
   *
   * @param id          the id
   * @param vigencia the vigencia
   * @return the list
   */
	@Query("SELECT sect FROM Sector sect WHERE sect.reparticion.id = :idReparticion "
			+ "AND (sect.vigenciaDesde <= :vigencia AND sect.vigenciaHasta >= :vigencia) AND sect.estadoRegistro = 1 AND sect.mesaVirtual = 1")
	List<Sector> findByReparticionEstadoRegistroTrueAndMesaVirtualTrue(@Param("idReparticion") Integer id,
			@Param("vigencia") Date vigencia);

  /**
   * Find by nombre sector containing.
   *
   * @param nombreSector
   *          the nombre sector
   * @return the list
   */
  List<Sector> findByNombreContaining(String nombreSector);

  /**
   * Find by id and codigo sector and vigencia desde after and vigencia desde
   * before and estado true.
   *
   * @param id
   *          the id
   * @param codigoSector
   *          the codigo sector
   * @param vigenciaHasta
   *          the vigencia hasta
   * @param vigenciaDesde
   *          the vigencia desde
   * @return the sector
   */
	@Query("SELECT sect FROM Sector sect WHERE sect.reparticion.id = :idReparticion AND sect.codigo = :codigoSector"
			+ " AND (sect.vigenciaDesde <= :vigencia AND sect.vigenciaHasta >= :vigencia) AND sect.estadoRegistro = 1")
	Sector findByReparticionAndCodigoAndEstadoRegistroTrue(@Param("idReparticion") Integer id,
			@Param("codigoSector") String codigoSector, @Param("vigencia") Date vigencia);

  /**
   * Find by reparticion id in.
   *
   * @param idRepartiones
   *          the id repartiones
   * @return the list
   */
  List<Sector> findByReparticion_IdIn(List<Integer> idRepartiones);

  /**
   * Find by codigo sector containing.
   *
   * @param codigoSector
   *          the codigo sector
   * @return the list
   */
  List<Sector> findByCodigoContaining(String codigoSector);
  
  
  @Query("SELECT Concat(sect.nombre, ' (', sect.codigo, ')') FROM Sector sect WHERE sect.reparticion.id = :idReparticion")
  List<String> obtenerSectorByIdReparticion(@Param("idReparticion") Integer id);

}