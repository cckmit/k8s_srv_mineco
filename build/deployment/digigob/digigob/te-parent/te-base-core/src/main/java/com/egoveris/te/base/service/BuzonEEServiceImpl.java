package com.egoveris.te.base.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.dao.IBuzonDAO;
import com.egoveris.te.base.model.BuzonBean;
import com.egoveris.te.base.service.iface.IBuzonEEService;

@Service
@Transactional
public class BuzonEEServiceImpl implements IBuzonEEService {

  private IBuzonDAO buzonDAO;

  private static final Logger logger = LoggerFactory.getLogger(BuzonEEServiceImpl.class);

  public List<BuzonBean> getTareasSinAsignar() {
    if (logger.isDebugEnabled()) {
      logger.debug("getTareasSinAsignar() - start");
    }

    List<BuzonBean> returnList = buzonDAO.obtenerListaTaskSinAsignacion();
    if (logger.isDebugEnabled()) {
      logger.debug("getTareasSinAsignar() - end - return value={}", returnList);
    }
    return returnList;
  }

  public IBuzonDAO getBuzonDAO() {
    return buzonDAO;
  }

  public void setBuzonDAO(IBuzonDAO buzonDAO) {
    this.buzonDAO = buzonDAO;
  }
}
