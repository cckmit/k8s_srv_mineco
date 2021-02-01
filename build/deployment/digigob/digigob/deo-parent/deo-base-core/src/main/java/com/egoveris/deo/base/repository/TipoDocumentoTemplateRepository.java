package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.TipoDocumentoTemplate;
import com.egoveris.deo.base.model.TipoDocumentoTemplatePK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDocumentoTemplateRepository
    extends JpaRepository<TipoDocumentoTemplate, TipoDocumentoTemplatePK> {

}
