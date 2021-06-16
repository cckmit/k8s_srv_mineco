package com.egoveris.edt.base.repository.eu.actuacion;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.edt.base.model.eu.actuacion.Actuacion;

// TODO: Auto-generated Javadoc
/**
 * Interface ActuacionRepository.
 */
public interface ActuacionRepository extends JpaRepository<Actuacion, Integer> {

  /**
   * Metodo que busca en la tabla sade_actuación, los registros que se
   * encuentren en estado_registro = 1 y contengan (like) el valor del
   * parámetro.
   *
   * @param nombreActuacion the nombre actuacion
   * @param codigoActuacion the codigo actuacion
   * @return Lista de Actuación.
   */
  List<Actuacion> findByEstadoRegistroTrueAndNombreActuacionContainingOrCodigoActuacionContaining(
      String nombreActuacion, String codigoActuacion);

  /**
   * Método que busca el registro según el código de actuación.
   * 
   * @param codigo
   *          the codigo
   * @return the actuacion
   */
  Actuacion findByCodigoActuacion(String codigo);
  
  /**
   * Método identico al anterior pero que ademas filtra por estado
   * 
   * @param codigo
   * @param estado
   * @return
   */
  Actuacion findByCodigoActuacionAndEstadoRegistro(String codigo, Boolean estado);
  
  /**
   * Find by estado registro true.
   *
   * @return the list
   */
  List<Actuacion> findByEstadoRegistroTrue();
  
  /**
   * Obtener lista todas las actuaciones activas vigentes Y es documento.
   *
   * @param vigenciadesde the vigenciadesde
   * @param vigenciaHasta the vigencia hasta
   * @return the list
   */
  @Query("SELECT act FROM Actuacion act WHERE act.estadoRegistro = 1 AND act.esDocumento = 1 AND act.vigenciaDesde <= :vigenciaDesde AND act.vigenciaHasta > :vigenciaHasta")
  List <Actuacion> obtenerListaTodasLasActuacionesActivasVigentesYEsDocumento(@Param("vigenciaDesde")Date vigenciadesde, @Param("vigenciaHasta")Date vigenciaHasta);
  
  @Query("SELECT act FROM Actuacion act")
  List<Actuacion> obtenerListaActuacion();
}