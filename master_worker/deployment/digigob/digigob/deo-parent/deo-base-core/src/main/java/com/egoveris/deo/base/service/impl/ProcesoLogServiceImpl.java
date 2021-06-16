package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.ProcesoLog;
import com.egoveris.deo.base.repository.ProcesoLogRepository;
import com.egoveris.deo.base.service.ProcesoLogService;
import com.egoveris.deo.model.model.ProcesoLogDTO;
import com.egoveris.shared.map.ListMapper;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcesoLogServiceImpl implements ProcesoLogService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ProcesoLogServiceImpl.class);

  @Autowired
  private ProcesoLogRepository procesoLogRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public List<ProcesoLogDTO> findByProcesoAndEstado(String proceso, String estado) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findByProcesoAndEstado(String, String) - start"); //$NON-NLS-1$
    }

    List<ProcesoLogDTO> returnList = ListMapper.mapList(
        this.procesoLogRepo.findByProcesoAndEstado(proceso, estado), this.mapper,
        ProcesoLogDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("findByProcesoAndEstado(String, String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public void save(ProcesoLogDTO proceso) {
    this.procesoLogRepo.save(this.mapper.map(proceso, ProcesoLog.class));

  }

}
