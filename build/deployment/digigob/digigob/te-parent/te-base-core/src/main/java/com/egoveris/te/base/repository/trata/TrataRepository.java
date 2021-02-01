package com.egoveris.te.base.repository.trata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.trata.Trata;

public interface TrataRepository extends JpaRepository<Trata, Long> {

  List<Trata> findByCodigoTrataAndDescripcion(String codigo, String desc);

  Trata findByCodigoTrata(String codigoTrata);

  List<Trata> findByEstado(String activo);

  List<Trata> findByEsManualAndEstado(Boolean manual, String estado);

  @Query("SELECT trata FROM Trata AS trata WHERE trata.esManual =:esmanual "
      + "AND trata.estado =:estado AND trata.esInterno =:estadoInterno "
      + "AND trata.esExterno =:estadoExterno")
  List<Trata> buscarTratasManualesYTipoCaratulacion(@Param("esmanual") boolean esmanual,
      @Param("estado") String estado, @Param("estadoInterno") boolean estadoInterno,
      @Param("estadoExterno") boolean estadoExterno);

  List<Trata> findByEsInternoAndEsExterno(Boolean esInterno, Boolean esExterno);

  List<Trata> findByEstadoAndEsInternoAndEsExterno(String estado, Boolean esInterno,
      Boolean esExterno);

  Trata findByEstadoAndCodigoTrata(String estado, String codigoTrata);
  
  List<Trata> findByTipoTramiteIn(List<String> tipoTramite);
  
  List<Trata> findByTipoTramiteInAndEsManual(List<String> tipoTramite, boolean manual);
  
}