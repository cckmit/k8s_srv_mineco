package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.HistorialReserva;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HistorialReservaRepository extends JpaRepository<HistorialReserva, Integer> {

  List<HistorialReserva> findByDocumentoAndReparticionRectora(String documento,
      String reparticionRectora);

  List<HistorialReserva> findByUsuarioAndDocumentoAndReparticionRectoraIsNull(String usuario,
      String documento);

  List<HistorialReserva> findByUsuarioAndDocumentoAndReparticionRectora(String usuario,
      String documento, String reparticionRectora);

  List<HistorialReserva> findByUsuarioAndDocumento(String usuario, String documento);

  List<HistorialReserva> findByDocumentoAndReparticion(String documento, String reparticion);

  List<HistorialReserva> findByDocumentoAndSectorAndReparticion(String documento, String sector,
      String reparticion);

  @Modifying
  @Query("update HistorialReserva set reparticion= :reparticionDestino where reparticion=:reparticionOrigen")
  void actualizarReparticionHistorialVisualizacion(String reparticionOrigen,
      String reparticionDestino);

  @Modifying
  @Query("update HistorialReserva set reparticion_rectora= :reparticionRectoraDestino where reparticion_rectora=:reparticionRectoraOrigen")
  void actualizarReparticionRectoraHistorialVisualizacion(String reparticionRectoraOrigen,
      String reparticionRectoraDestino);
  
  @Modifying
  @Query("update HistorialReserva set sector= :sectorDestino, reparticion =:reparticionDestino where sector=:sectorOrigen and reparticion=:reparticionOrigen")
  void actualizarSectorHistorialVisualizacion(String sectorOrigen,
      String sectorDestino, String reparticionOrigen,
      String reparticionDestino);
  
  
}
