package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.model.TareaCambioDeSigla;
import com.egoveris.deo.base.model.TareaCambioDeSiglaError;
import com.egoveris.deo.base.repository.TareaCambioDeSiglaErrorRepository;
import com.egoveris.deo.base.repository.TareaCambioDeSiglaRepository;
import com.egoveris.deo.base.service.TareaCambioDeSiglaService;
import com.egoveris.deo.model.model.TareaCambioDeSiglaDTO;
import com.egoveris.deo.model.model.TareaCambioDeSiglaErrorDTO;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TareaCambioDeSiglaServiceImpl implements TareaCambioDeSiglaService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory
      .getLogger(TareaCambioDeSiglaServiceImpl.class);

  @Autowired
  private TareaCambioDeSiglaRepository tareaCambioDeSiglaRepo;
  @Autowired
  private TareaCambioDeSiglaErrorRepository tareaCambioDeSiglaErrorRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  public void guardarTarea(TareaCambioDeSiglaDTO tareaCambioDeSigla)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarTarea(TareaCambioDeSiglaDTO) - start"); //$NON-NLS-1$
    }

    tareaCambioDeSiglaRepo.save(this.mapper.map(tareaCambioDeSigla, TareaCambioDeSigla.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarTarea(TareaCambioDeSiglaDTO) - end"); //$NON-NLS-1$
    }
  }

  public void guardarErrorTarea(TareaCambioDeSiglaErrorDTO tareaCambioDeSiglaError) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarErrorTarea(TareaCambioDeSiglaErrorDTO) - start"); //$NON-NLS-1$
    }

    tareaCambioDeSiglaErrorRepo
        .save(this.mapper.map(tareaCambioDeSiglaError, TareaCambioDeSiglaError.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarErrorTarea(TareaCambioDeSiglaErrorDTO) - end"); //$NON-NLS-1$
    }
  }

}
