package com.egoveris.sharedsecurity.base.repository.admin;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.model.admin.AdminReparticion;

/**
 * The Interface AdminReparticionRepository.
 */
public interface AdminReparticionRepository extends JpaRepository<AdminReparticion, Integer> {

  /**
   * Método que retorna una lista de AdminReparticion realizando la busqueda por
   * nombreUsuario, acota el rango de fecha de Reparticion entre las fechas
   * indicadas en parametros vigenciaDesde y vigenciaHasta.
   * 
   * @param nombreUsuario
   *          the nombre usuario
   * @param vigenciaDesde
   *          the vigencia desde
   * @param vigenciaHasta
   *          the vigencia hasta
   * @return the list
   */
  List<AdminReparticion> findByNombreUsuarioAndReparticion_VigenciaDesdeBeforeAndReparticion_VigenciaHastaAfter(
      String nombreUsuario, Date vigenciaDesde, Date vigenciaHasta);

  /**
   * Método que retorna una lista de AdminReparticion realizando la busqueda por
   * repartición, acota el rango de fecha de Reparticion entre las fechas
   * indicadas en parametros vigenciaDesde y vigenciaHasta.
   *
   * @param reparticion
   *          the reparticion
   * @param vigenciaDesde
   *          the vigencia desde
   * @param vigenciaHasta
   *          the vigencia hasta
   * @return the list
   */
  List<AdminReparticion> findByReparticionAndReparticion_VigenciaDesdeBeforeAndReparticion_VigenciaHastaAfter(
      Reparticion reparticion, Date vigenciaDesde, Date vigenciaHasta);

}