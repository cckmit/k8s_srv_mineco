package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.NumerosUsados;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NumerosUsadoRepository extends JpaRepository<NumerosUsados, Integer> {

  NumerosUsados findByNumeroSADE(String numeroSADE);
  
  List<NumerosUsados> findByAnioAndCodigoReparticionAndNumeroEspecialIsNotNull(String anio, String codigoReparticion);
  
}
