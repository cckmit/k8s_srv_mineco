package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.DocumentoSolicitudDTO;

public interface DocumentoSolicitudService {

  public DocumentoSolicitudDTO findByWorkflowId(String workflowId);

  public DocumentoSolicitudDTO findByNumeroSade(String numeroSade);

}
