package com.egoveris.te.base.repository.trata;

import com.egoveris.te.base.model.trata.TrataTrack;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrataTrackRepository extends JpaRepository<TrataTrack, Long> {

  @Query("SELECT t FROM TrataTrack t WHERE t.vigenciaHasta > :hoy AND t.vigenciaDesde <= :hoy "
      + " AND t.estadoRegistro =:estado AND t.codigoExtracto =:codigo")
  List<TrataTrack> consultarEstadoyVigenciaByCodigo(@Param("hoy") Date hoy, @Param("estado") boolean estado, @Param("codigo") String codigo);

  TrataTrack findBycodigoExtracto(String codigoExtracto);

  List<TrataTrack> findByVigenciaDesdeAndVigenciaHastaAndEstadoRegistro(Date desde, Date hasta,
      boolean estado);

}