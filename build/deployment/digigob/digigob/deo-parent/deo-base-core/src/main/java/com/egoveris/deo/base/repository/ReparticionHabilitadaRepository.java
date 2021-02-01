package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.ReparticionHabilitada;
import com.egoveris.deo.base.model.TipoDocumento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReparticionHabilitadaRepository extends JpaRepository<ReparticionHabilitada, Integer> {

  List<ReparticionHabilitada> findByTipoDocumento(TipoDocumento tipoDocumento);
  
  List<ReparticionHabilitada> findByCodigoReparticionAndTipoDocumento(String codigoReparticion, TipoDocumento tipoDocumento);
  
  List<ReparticionHabilitada> findByCodigoReparticion(String codigoReparticion);
  
  List<ReparticionHabilitada> findByTipoDocumentoAndEstado(TipoDocumento tipoDocumento, Boolean estado);
  
  @Modifying
  @Query("update ReparticionHabilitada set codigoreparticion= :reparticionDestino where codigoreparticion=:reparticionOrigen")
  void actualizarReparticionHabilitadaTipoDocumento(String reparticionOrigen, String reparticionDestino);
}
