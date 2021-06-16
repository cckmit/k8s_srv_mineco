package com.egoveris.sharedorganismo.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.sharedorganismo.base.model.Reparticion;

public interface ReparticionRepository extends JpaRepository<Reparticion, Integer> {
  
	  @Query(value = "SELECT si.codigo_reparticion, r.nombre_reparticion, r.id_reparticion, r.estado_registro, r.vigencia_desde, r.cod_dgtal, r.vigencia_hasta, r.es_dgtal, r.id_estructura, rep_padre FROM edt_sade_sector_usuario su, edt_sade_reparticion r, edt_sade_sector_interno si WHERE su.nombre_usuario=?  AND si.id_sector_interno=su.id_sector_interno AND si.codigo_reparticion=r.id_reparticion AND su.estado_registro = 1 ", nativeQuery=true)
  	  Reparticion findByUserName(String username);
	   
	  @Query(value = "SELECT r.nombre_reparticion FROM edt_sade_reparticion r WHERE r.codigo_reparticion=?", nativeQuery=true)
  	  String buscarNombreReparticionPorCodigo(String username);
	  
}
