package com.egoveris.deo.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.deo.base.model.DocumentoTemplate;
import com.egoveris.deo.base.model.DocumentoTemplatePK;

public interface DocumentoTemplateRepository
    extends JpaRepository<DocumentoTemplate, DocumentoTemplatePK> {

  DocumentoTemplate findByDocumentoTemplatePK(DocumentoTemplatePK documentoTemplatePK);

//  DocumentoTemplate findByWorkflowId(String workflowId);
}
