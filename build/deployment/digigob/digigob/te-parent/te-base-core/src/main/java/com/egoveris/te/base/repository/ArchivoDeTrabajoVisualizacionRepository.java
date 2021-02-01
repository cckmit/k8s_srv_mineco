package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.ArchivoDeTrabajoVisualizacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArchivoDeTrabajoVisualizacionRepository
    extends JpaRepository<ArchivoDeTrabajoVisualizacion, Long> {

  @Query(value = "select count(p.id) from ArchivoDeTrabajoVisualizacion p where  p.idArchivoDeTrabajo =:archivoId and p.codigoReparticionRectora = :reparticion")
  Integer buscarPorRectora(@Param("archivoId") Long archivoId,
      @Param("reparticion") String reparticion);

  @Query(value = " select count(p.id) from ArchivoDeTrabajoVisualizacion p where p.idArchivoDeTrabajo = :archivoId and p.codigoReparticion = :reparticion ")
  Integer buscarPorReparticion(@Param("archivoId") Long archivoId,
      @Param("reparticion") String reparticion);

  @Query(value = "select count(p.id) from ArchivoDeTrabajoVisualizacion p where p.idArchivoDeTrabajo = :archivoId and p.codigoUsuario = :username")
  Integer buscarPorUsuario(@Param("archivoId") Long archivoId,
      @Param("username") String reparticion);

  @Query(value = "select count(p.id) from ArchivoDeTrabajoVisualizacion p where p.idArchivoDeTrabajo = :archivoId and p.codigoSector = :sector and p.codigoReparticion = :reparticion")
  Integer buscarPorSector(@Param("archivoId") Long archivoId, @Param("sector") String sector,
      @Param("reparticion") String reparticion);
  
  
  List<ArchivoDeTrabajoVisualizacion>findByIdArchivoDeTrabajo(Long idArchivoDeTrabajo);
}
