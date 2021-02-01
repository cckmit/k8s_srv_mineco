package com.egoveris.sharedorganismo.base.repository;

import com.egoveris.sharedorganismo.base.model.SectorUsuario;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SectorUsuarioRepository extends JpaRepository<SectorUsuario, Integer> {

  @Query(value="SELECT rtrim(ltrim(su.nombre_usuario)) nombre_usuario, "
      + "rtrim(ltrim(si.codigo_sector_interno)) codigo_sector_interno, si.id_sector_interno, "
      + "rtrim(ltrim(si.nombre_sector_interno)) nombre_sector_interno, si.sector_mesa, "
      + "mesa_virtual, " + "rtrim(ltrim(r.codigo_reparticion)) codigo_reparticion "
      + "FROM sade_sector_usuario su, " + "sade_reparticion r, " + " sade_sector_interno si "
      + "WHERE si.id_sector_interno = su.id_sector_interno " + "AND si.estado_registro = 1 "
      + "AND su.estado_registro=1 " + "AND si.vigencia_desde <= ?0 " + "AND si.vigencia_hasta >= ?1 "
      + "AND si.codigo_reparticion=r.id_reparticion ", nativeQuery=true)
  List<SectorUsuario> findSectoresUsuarios( Date desde, Date hasta);
}
