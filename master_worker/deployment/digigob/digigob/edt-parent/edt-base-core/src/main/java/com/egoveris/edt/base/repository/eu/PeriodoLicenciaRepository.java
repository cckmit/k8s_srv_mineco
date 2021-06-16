package com.egoveris.edt.base.repository.eu;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.edt.base.model.eu.PeriodoLicencia;

/**
 * The Interface PeriodoLicenciaRepository.
 */
public interface PeriodoLicenciaRepository extends JpaRepository<PeriodoLicencia, Integer> {

  /**
   * Trae solo las licencia del usuario que estan en estado , "pendiente" o
   * "activo" y su fecha de cancelacion sea null.
   *
   * @param userName
   *          the user name
   * @param fecha
   *          the fecha hasta
   * @param condicionPeriodo
   *          the condicion periodo
   * @return periodoLicencia
   */
  @Query("SELECT l FROM PeriodoLicencia l WHERE l.usuario = :usuario AND l.fechaHoraDesde <= :fecha "
  		+ "AND l.fechaHoraHasta >= :fecha AND l.fechaCancelacion IS NULL AND l.condicionPeriodo <> :condicionPeriodo")
  public List<PeriodoLicencia> getPeriodoLicencia(@Param("usuario") String usuario, @Param("fecha") Date fecha, @Param("condicionPeriodo") String condicionPeriodo);
	
  @Query("SELECT l FROM PeriodoLicencia l WHERE l.usuario = :usuario AND "
  		+ "l.fechaCancelacion IS NULL AND l.condicionPeriodo <> :condicionPeriodo")
  public List<PeriodoLicencia> getPeriodoLicencia(@Param("usuario") String usuario, @Param("condicionPeriodo") String condicionPeriodo);
	
  
  @Modifying
  @Query("UPDATE PeriodoLicencia l SET l.condicionPeriodo = :condicionPeriodo, l.fechaCancelacion = :fecha "
  		+ "WHERE :fecha >= l.fechaHoraHasta")
  public void terminarLicenciasPasadas(@Param("condicionPeriodo") String condicionPeriodo, @Param("fecha") Date fecha);
  
}
