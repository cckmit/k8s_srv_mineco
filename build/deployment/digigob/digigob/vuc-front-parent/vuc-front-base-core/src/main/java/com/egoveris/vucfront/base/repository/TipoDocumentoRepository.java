package com.egoveris.vucfront.base.repository;

import com.egoveris.vucfront.base.model.TipoDocumento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {

  TipoDocumento getByAcronimoGedo(String acronimoGedo);

  TipoDocumento getByAcronimoTad(String acronimoTad);
  
}
