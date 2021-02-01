
package com.egoveris.edt.base.repository.eu;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.edt.base.model.eu.AlertaAviso;
import com.egoveris.edt.base.model.eu.Aplicacion;
import com.egoveris.edt.base.model.eu.NotificacionesPorAplicacion;

/**
 * The Interface AlertaAvisoRepository.
 */
public interface AlertaAvisoRepository extends JpaRepository<AlertaAviso, Integer> {

  /**
   * Metodo qu eobtiene la cantidad de alertas avisos no leidas por aplicacion,
   * se filtran los registros por username y estado.
   *
   * @param userName
   *          the user name
   * @param estado
   *          the estado
   * @return the list
   */
  @Query("SELECT new com.egoveris.edt.base.model.eu.NotificacionesPorAplicacion(a.aplicacion, COUNT(a)) FROM AlertaAviso a  WHERE a.userName = ?1 AND a.estado = ?2 GROUP BY a.aplicacion")
  List<NotificacionesPorAplicacion> obtenerCantidadDeAlertasAvisosNoLeidasPorAplicacion(
      String userName, String estado);

  /**
   * Metodo que retorna una lista de AlertaAvisos filtrando los registros por
   * los parametros aplicación y username.
   *
   * @param aplicacion
   *          the aplicacion
   * @param userName
   *          the user name
   * @return the list
   */
  List<AlertaAviso> findByAplicacionAndUserName(Aplicacion aplicacion, String userName);

  /**
   * Metodo que retorna una lista de AlertaAviso filtrando por los parámetros
   * username y fkIdModulo, se buscarán los registros cuyo valor de los campos
   * motivo o referencia contengan el parámetro filtro.
   *
   * @param userName
   *          the user name
   * @param fkIdModulo
   *          the fk id modulo
   * @param filtro
   *          the filtro
   * @return the list
   */
  @Query(value = "SELECT aa FROM AlertaAviso aa WHERE nombre_usuario = ?1 AND fk_id_modulo = ?2 AND (motivo LIKE %?3% OR referencia LIKE %?3%)")
  List<AlertaAviso> filtrarAlertasAvisosPorMotivoYReferencia(String userName, Integer fkIdModulo,
      String filtro);

  /**
   * Metodo que retorna una lista de AlertaAviso filtrando por los parámetros
   * username, fkIdModulo además los valores con fecha de creación < que el
   * parametro fechaHasta, se buscarán los registros cuyo valor de los campos
   * motivo o referencia contengan el parámetro filtro.
   *
   * @param userName
   *          the user name
   * @param fkIdModulo
   *          the fk id modulo
   * @param filtro
   *          the filtro
   * @param fechaDesde
   *          the fecha desde
   * @return the list
   */
  @Query(value = "SELECT aa FROM AlertaAviso aa WHERE nombre_usuario = ?1 AND fk_id_modulo = ?2 AND (motivo LIKE %?3% OR referencia LIKE %?3%) AND fecha_creacion >= ?4")
  List<AlertaAviso> filtrarAlertasAvisosPorMotivoYReferenciaYFechaDesde(String userName,
      Integer fkIdModulo, String filtro, Date fechaDesde);

  /**
   * Metodo que retorna una lista de AlertaAviso filtrando por los parámetros
   * username, fkIdModulo además los valores con fecha de creación < que el
   * parametro fechaHasta, se buscarán los registros cuyo valor de los campos
   * motivo o referencia contengan el parámetro filtro.
   *
   * @param userName          the user name
   * @param app the app
   * @param filtro          the filtro
   * @param fechaHasta          the fecha hasta
   * @return the list
   */
  @Query(value = "SELECT aa FROM AlertaAviso aa WHERE aa.userName = ?1 AND aa.aplicacion = ?2 AND (aa.motivo LIKE %?3% OR aa.referencia LIKE %?3%) AND aa.fechaCreacion <= ?4")
  List<AlertaAviso> filtrarAlertasAvisosPorMotivoYReferenciaYFechaHasta(String userName,
      Aplicacion app, String filtro, Date fechaHasta);

}