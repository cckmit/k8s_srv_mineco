package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.dao.TipoActuacionDAO;
import com.egoveris.deo.base.service.TipoActuacionService;
import com.egoveris.deo.model.model.ActuacionSADEBean;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TipoActuacionServiceImpl implements TipoActuacionService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TipoActuacionServiceImpl.class);

  @Autowired
  private TipoActuacionDAO tipoActuacionDAO;

  public List<ActuacionSADEBean> obtenerListaTodasLasActuaciones() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerListaTodasLasActuaciones() - start"); //$NON-NLS-1$
    }

    List<ActuacionSADEBean> returnList = this.tipoActuacionDAO.obtenerListaTodasLasActuaciones();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerListaTodasLasActuaciones() - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public ActuacionSADEBean obtenerActuacionPorCodigo(String codigo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerActuacionPorCodigo(String) - start"); //$NON-NLS-1$
    }

    ActuacionSADEBean returnActuacionSADEBean = this.tipoActuacionDAO
        .obtenerActuacionPorCodigo(codigo);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerActuacionPorCodigo(String) - end"); //$NON-NLS-1$
    }
    return returnActuacionSADEBean;
  }

}
