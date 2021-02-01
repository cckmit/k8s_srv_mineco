package com.egoveris.te.base.repository.tipo;

import com.egoveris.te.base.model.tipo.TipoDocumento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {

  TipoDocumento findByUsoEnEE(String uso);
  
}