package com.egoveris.sharedorganismo.base.repository;

import com.egoveris.sharedorganismo.base.model.SectorInterno;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SectorInternoRepository extends JpaRepository<SectorInterno, Integer> {

  @Query(value = "SELECT SI FROM SectorInterno SI "
      + "WHERE SI.codigoReparticion = :idReparticion AND SI.vigenciaDesde < :desde AND SI.vigenciaHasta > :hasta AND SI.estadoRegistro = 1")
  List<SectorInterno> getSectorInternoByReparticion(@Param("idReparticion") Long idReparticion, @Param("desde") Date desde, @Param("hasta") Date hasta);

  List<SectorInterno> findByCodigoReparticionOrderBySectorMesa(Long codigoReparticion);

  @Query(value = "SELECT s.nombre FROM SectorInterno s WHERE s.codigo= :codigoSector "
  		+ "AND s.codigoReparticion = (SELECT r.id FROM Reparticion r WHERE r.codigo = :codigoReparticion)")
  String buscarNombreSectorPorCodigo(@Param("codigoSector") String codigoSector, @Param("codigoReparticion") String codigoReparticion);

}
