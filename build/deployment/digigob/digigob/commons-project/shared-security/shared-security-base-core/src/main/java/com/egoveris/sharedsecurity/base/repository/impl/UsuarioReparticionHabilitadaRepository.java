package com.egoveris.sharedsecurity.base.repository.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitada;

/**
 * The Interface UsuarioReparticionHabilitadaRepository.
 */
public interface UsuarioReparticionHabilitadaRepository
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
	@Query("SELECT usRep FROM UsuarioReparticionHabilitada usRep WHERE usRep.nombreUsuario = :username "
			+ "AND usRep.reparticion.vigenciaDesde <= :vigencia AND usRep.reparticion.vigenciaHasta >= :vigencia AND usRep.reparticion.estadoRegistro = 1")
  List<UsuarioReparticionHabilitada> findByNombreUsuario(@Param("username") String username, @Param("vigencia") Date vigencia);

  /**
   * Find by reparticion codigo reparticion and sector codigo sector and
   * reparticion vigencia hasta after and reparticion vigencia desde before.
   *
   * @param codigoReparticion
   *          the codigo reparticion
   * @param codigoSector
   *          the codigo sector
   * @param vigenciaHasta
   *          the vigencia hasta
   * @param vigenciaDesde
   *          the vigencia desde
   * @return the list
   */
	@Query("SELECT usRep FROM UsuarioReparticionHabilitada usRep "
			+ "WHERE usRep.reparticion.codigo = :codigoReparticion AND usRep.sector.codigo = :codigoSector "
			+ "AND usRep.reparticion.vigenciaDesde <= :vigencia AND usRep.reparticion.vigenciaHasta >= :vigencia "
			+ "AND usRep.sector.vigenciaDesde <= :vigencia AND usRep.sector.vigenciaHasta >= :vigencia "
			+ "AND usRep.reparticion.estadoRegistro = 1 AND usRep.sector.estadoRegistro = 1")
	List<UsuarioReparticionHabilitada> findByReparticionCodigoAndSectorCodigo(
			@Param("codigoReparticion") String codigoReparticion, @Param("codigoSector") String codigoSector,
			@Param("vigencia") Date vigencia);
  
  /**
   * Find by reparticion codigo reparticion and reparticion vigencia hasta after
   * and reparticion vigencia desde before.
   *
   * @param codigoReparticion
   *          the codigo reparticion
   * @param vigenciaHasta
   *          the vigencia hasta
   * @param vigenciaDesde
   *          the vigencia desde
   * @return the list
   */
	@Query("SELECT usRep FROM UsuarioReparticionHabilitada usRep WHERE usRep.reparticion.codigo = :codigoReparticion AND usRep.reparticion.vigenciaDesde <= :vigencia "
			+ "AND usRep.reparticion.vigenciaHasta >= :vigencia AND usRep.reparticion.estadoRegistro = 1")
	List<UsuarioReparticionHabilitada> findByReparticionCodigo(@Param("codigoReparticion") String codigoReparticion,
			@Param("vigencia") Date vigencia);

  /**
   * Find by reparticion codigo reparticion.
   *
   * @param codigoReparticion
   *          the codigo reparticion
   * @return the list
   */
  List<UsuarioReparticionHabilitada> findByReparticion_Codigo(String codigoReparticion);

}