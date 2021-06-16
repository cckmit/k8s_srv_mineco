package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.Historial;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistorialRepository extends JpaRepository<Historial, Integer> {

  List<Historial> findByWorkflowOrigenOrderByFechaFinAsc(String workflowOrigen);

  Historial findByWorkflowOrigenAndActividadAndFechaFinIsNull(String workflowOrigen,
      String actividad);

  List<Historial> findByWorkflowOrigenAndFechaFinIsNotNullOrderByFechaFinDesc(
      String workflowOrigen);

  @Modifying
  @Query("update Historial set fechaFin= :fechaFin, mensaje= :mensaje  where "
      + "workfloworigen= :workfloworigen and actividad= :actividad and fechaFin is null")
  void actualizarHistorialUsuarioBaja(@Param("fechaFin") Date fechaActual,
      @Param("mensaje") String mensaje, @Param("workfloworigen") String executionID,
      @Param("actividad") String activityName);

  @Modifying
  @Query("update Historial set actividad= :actividad where workfloworigen= :workfloworigen and fechaFin is null")
  void actualizarActividadHistorialUsuarioBajaFirmante(@Param("actividad") String activityName,
      @Param("workfloworigen") String executionID);

  @Modifying
  @Query("update Historial set mensaje= :mensaje "
      + " where workfloworigen= :executionID and actividad= :activityName and fechaFin is null")
  void actualizarHistorialUsuarioBajaFirmante(@Param("mensaje") String mensaje,
      @Param("executionID") String executionID, @Param("activityName") String activityName);

  @Modifying
  @Query("update Historial set mensaje= :mensaje where "
      + "workfloworigen= :workfloworigen and usuario= :usuario and fechaFin is null")
  void actualizarHistorialUsuarioRevisorBaja(@Param("mensaje") String mensaje,
      @Param("workfloworigen") String executionID, @Param("usuario") String usuarioBaja);

}