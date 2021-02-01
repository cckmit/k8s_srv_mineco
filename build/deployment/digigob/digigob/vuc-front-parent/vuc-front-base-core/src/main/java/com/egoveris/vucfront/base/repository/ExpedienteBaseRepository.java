package com.egoveris.vucfront.base.repository;

import com.egoveris.vucfront.base.model.ExpedienteBase;
import com.egoveris.vucfront.base.model.Persona;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpedienteBaseRepository extends JpaRepository<ExpedienteBase, Long> {

  @Query("FROM ExpedienteBase WHERE  anio = :anio AND numero = :numero AND tipo = :tipo")
  public ExpedienteBase getExpedienteByCodigo(@Param("anio") Long anio,
      @Param("numero") Long numero, @Param("tipo") String tipo);

  ExpedienteBase findByNumeroExpediente(String numeroExpediente);
  
  List<ExpedienteBase> findByPersonaOrderByFechaCreacionDesc(Persona persona);
  
  @Query("select exp FROM ExpedienteBase exp inner join exp.tipoTramite tt "
  		+ "where   exp.fechaPago >= :fechaDesde and exp.fechaPago <=:fechaHasta  and tt.pago =1 "
  		+ "and (tt.reparticionIniciadora =:organismo or tt.organismoRector =:organismo) and exp.fechaPago is not null "
  		+ "order by exp.fechaPago desc")
  List<ExpedienteBase> findConsolidacionByFechas(@Param("fechaDesde") Date fechaDesde,
  		@Param("fechaHasta") Date fechaHasta, @Param("organismo") String organismo);

 
  
}