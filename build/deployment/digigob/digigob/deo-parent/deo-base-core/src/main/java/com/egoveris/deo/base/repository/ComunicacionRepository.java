package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.Comunicacion;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComunicacionRepository extends JpaRepository<Comunicacion, Integer> {

  List<Comunicacion> findByUsuarioCreadorAndFechaEliminacionIsNullOrderByFechaCreacion(
      String usuarioCreador);

  Comunicacion findByUsuarioCreadorAndIdComunicacion(String usuarioCreador,
      Integer idComunicacion);

  List<Comunicacion> findByUsuarioCreadorAndFechaEliminacionIsNull(String usuarioCreador);

  List<Comunicacion> findByUsuarioCreadorAndFechaCreacionBetween(String usuarioCreador,
      Date fechaDesde, Date fechaHasta);

  List<Comunicacion> findByUsuarioCreadorAndFechaCreacionBetweenOrderByFechaCreacion(
      String usuarioCreador, Date fechaDesde, Date fechaHasta);

  @Query("select co from Comunicacion co join fetch co.documento doc where lower(doc.motivo) like lower(:referencia)  AND co.usuarioCreador =:usuario order by co.fechaCreacion desc")
  List<Comunicacion> buscarComunicacionesEnviadasPorReferencia(
      @Param("referencia") String referencia, @Param("usuario") String usuario);

  @Query("select co from Comunicacion co join fetch co.documento doc where doc.numero = ?1")
  Comunicacion buscarComunicacionPorCaratula(String numeroDocumento);

  @Query("select co from Comunicacion co join fetch co.documento doc where lower(doc.motivo) like lower(:referencia)  AND co.usuarioCreador =:usuario order by co.fechaCreacion desc")
  List<Comunicacion> buscarComunicacionesPorReferenciaEnviados(
      @Param("referencia") String referencia, @Param("usuario") String usuario);

  Comunicacion findById(Integer id);

  @Modifying
  @Query("update Comunicacion co set co.fechaEliminacion =:fechaEliminacion where co.usuarioCreador =:usuarioCreador and co.fechaEliminacion is null")
  void eliminarComunicacionesEnviadas(@Param("fechaEliminacion") Date fechaEliminacion,
      @Param("usuarioCreador") String usuarioCreador);

}
