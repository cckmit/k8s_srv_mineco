package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.DocumentoTemplate;
import com.egoveris.deo.base.model.DocumentoTemplatePK;
import com.egoveris.deo.base.repository.DocumentoTemplateRepository;
import com.egoveris.deo.base.service.DocumentoTemplateService;
import com.egoveris.deo.model.model.DocumentoTemplateDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class DocumentoTemplateServiceImpl implements DocumentoTemplateService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoTemplateServiceImpl.class);

  @Autowired
  private DocumentoTemplateRepository documentoTemplateRepo;

  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public void save(DocumentoTemplateDTO documentoTemplate) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("save(DocumentoTemplateDTO) - start"); //$NON-NLS-1$
    }

    this.documentoTemplateRepo.save(this.mapper.map(documentoTemplate, DocumentoTemplate.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("save(DocumentoTemplateDTO) - end"); //$NON-NLS-1$
    }
  }

	public DocumentoTemplateDTO findByWorkflowId(String workflowId, TipoDocumentoDTO tipoDocumento) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findByWorkflowId(String) - start"); //$NON-NLS-1$
		}
		DocumentoTemplatePK pk = new DocumentoTemplatePK();
		pk.setWorkflowId(workflowId);
		pk.setIdTipoDocumento(tipoDocumento.getId());
		pk.setVersion(Double.valueOf(tipoDocumento.getVersion()).doubleValue());
		DocumentoTemplate documentiEn = this.documentoTemplateRepo.findByDocumentoTemplatePK(pk);


		DocumentoTemplateDTO returnDocumentoTemplateDTO = null;

		if (null != documentiEn) {
			returnDocumentoTemplateDTO = this.mapper.map(documentiEn, DocumentoTemplateDTO.class);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findByWorkflowId(String) - end"); //$NON-NLS-1$
		}
		return returnDocumentoTemplateDTO;

	}

}
