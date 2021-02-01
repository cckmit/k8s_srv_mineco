package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.TipoDocumentoEmbebidos;
import com.egoveris.deo.base.model.TipoDocumentoEmbebidosPK;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDocumentoEmbebidosRepository
    extends JpaRepository<TipoDocumentoEmbebidos, TipoDocumentoEmbebidosPK> {

  List<TipoDocumentoEmbebidos> findByTipoDocumentoEmbebidosPK(
      TipoDocumentoEmbebidosPK tipoDocumentoPK);
}
