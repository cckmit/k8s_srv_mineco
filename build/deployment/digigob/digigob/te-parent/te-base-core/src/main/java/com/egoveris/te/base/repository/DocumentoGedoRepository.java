package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.DocumentoGedo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoGedoRepository extends JpaRepository<DocumentoGedo, Integer> {

  DocumentoGedo findByNumero(String numero);
  

}