package com.egoveris.te.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.service.iface.IGenerarPaseExpedienteLOySService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.PaseExpedienteRequest;

@Service
@Transactional
public class GenerarPaseLOySServiceImpl extends GenerarPaseExpedienteServiceImpl
    implements IGenerarPaseExpedienteLOySService {
  private static final Logger logger = LoggerFactory.getLogger(GenerarPaseLOySServiceImpl.class);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public synchronized String generarPaseEELOyS(PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEELOyS(datosPase={}) - start", datosPase);
    }

    boolean esServicioLOyS = true;
    String returnString = this.generarPase(datosPase, ConstantesWeb.PASE_SIN_CAMBIOS, esServicioLOyS);
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEELOyS(PaseExpedienteRequest) - end - return value={}",
          returnString);
    }
    return returnString;
  }

}
