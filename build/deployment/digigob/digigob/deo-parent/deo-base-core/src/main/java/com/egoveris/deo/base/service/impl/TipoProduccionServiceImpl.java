package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.repository.TipoProduccionRepository;
import com.egoveris.deo.base.service.TipoProduccionService;
import com.egoveris.deo.model.model.TipoProduccionDTO;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TipoProduccionServiceImpl implements TipoProduccionService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TipoProduccionServiceImpl.class);

  @Autowired
  private TipoProduccionRepository tipoProduccionRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public TipoProduccionDTO findById(Integer id) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findById(Integer) - start"); //$NON-NLS-1$
    }

    TipoProduccionDTO returnTipoProduccionDTO = this.mapper.map(this.tipoProduccionRepo.findById(id), TipoProduccionDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findById(Integer) - end"); //$NON-NLS-1$
    }
    return returnTipoProduccionDTO;
  }

}
