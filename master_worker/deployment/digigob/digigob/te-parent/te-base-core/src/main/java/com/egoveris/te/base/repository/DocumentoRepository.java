package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.Documento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long>{
  
  Documento findByNumeroEspecial(String numeroEspecal);
  
  Documento findByNumeroSade(String numeroSade);

}
