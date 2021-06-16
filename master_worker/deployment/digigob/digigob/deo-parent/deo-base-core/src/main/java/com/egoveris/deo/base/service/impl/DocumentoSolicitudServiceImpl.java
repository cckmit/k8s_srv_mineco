package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.repository.DocumentoSolicitudRepository;
import com.egoveris.deo.base.service.DocumentoSolicitudService;
import com.egoveris.deo.model.model.DocumentoSolicitudDTO;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentoSolicitudServiceImpl implements DocumentoSolicitudService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoSolicitudServiceImpl.class);

  @Autowired
  private DocumentoSolicitudRepository documentoSolicitudRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;
  
  @Override
  public DocumentoSolicitudDTO findByWorkflowId(String workflowId) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findByWorkflowId(String) - start"); //$NON-NLS-1$
    }

    DocumentoSolicitudDTO returnDocumentoSolicitudDTO = this.mapper.map(this.documentoSolicitudRepo.findByWorkflowId(workflowId), DocumentoSolicitudDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findByWorkflowId(String) - end"); //$NON-NLS-1$
    }
    return returnDocumentoSolicitudDTO;
  }

  @Override
  public DocumentoSolicitudDTO findByNumeroSade(String numeroSade) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findByNumeroSade(String) - start"); //$NON-NLS-1$
    }

    DocumentoSolicitudDTO returnDocumentoSolicitudDTO = this.mapper.map(this.documentoSolicitudRepo.findByNumeroSade(numeroSade), DocumentoSolicitudDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findByNumeroSade(String) - end"); //$NON-NLS-1$
    }
    return returnDocumentoSolicitudDTO;
  }

}
