package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.Documento;
import com.egoveris.deo.base.repository.DocumentoRepository;
import com.egoveris.deo.base.service.DocumentoService;
import com.egoveris.deo.model.model.DocumentoDTO;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class DocumentoServiceImpl implements DocumentoService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoServiceImpl.class);

  @Autowired
  private DocumentoRepository documentoRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public DocumentoDTO findByNumero(String numero) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findByNumero(String) - start"); //$NON-NLS-1$
    }

    DocumentoDTO returnDocumentoDTO = this.mapper.map(this.documentoRepo.findByNumero(numero),
        DocumentoDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findByNumero(String) - end"); //$NON-NLS-1$
    }
    return returnDocumentoDTO;
  }

  @Override
  public void save(DocumentoDTO documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("save(DocumentoDTO) - start"); //$NON-NLS-1$
    }

    this.documentoRepo.save(this.mapper.map(documento, Documento.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("save(DocumentoDTO) - end"); //$NON-NLS-1$
    }
  }

	@Override
	@Transactional
	public DocumentoDTO findByIdPublicable(String idPublicable) {
		DocumentoDTO returnDocumentoDTO = null;
		Documento documento = this.documentoRepo.buscarPorIdPublicable(idPublicable);
		if (documento != null) {
			returnDocumentoDTO = this.mapper
					.map(documento, DocumentoDTO.class);			
		}
		return returnDocumentoDTO;
	}

}
