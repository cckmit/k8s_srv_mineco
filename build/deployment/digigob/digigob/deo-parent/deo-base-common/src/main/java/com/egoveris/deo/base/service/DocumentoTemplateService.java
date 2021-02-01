package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.DocumentoTemplateDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;

public interface DocumentoTemplateService {
  
  /**
   * 
   * @param documentoTemplate
   */
  public void save(DocumentoTemplateDTO documentoTemplate);
  
  /**
   * 
   * @param workflowId
   * @return
   */
  public DocumentoTemplateDTO findByWorkflowId(String workflowId, TipoDocumentoDTO tipoDocumento);

}
