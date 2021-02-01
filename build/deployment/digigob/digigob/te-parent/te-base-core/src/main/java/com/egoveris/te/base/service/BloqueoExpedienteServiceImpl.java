package com.egoveris.te.base.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IBloqueoExpedienteService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;

@Service
@Transactional
public class BloqueoExpedienteServiceImpl implements IBloqueoExpedienteService {
  private static final Logger logger = LoggerFactory.getLogger(BloqueoExpedienteServiceImpl.class);

  @Autowired
  ExpedienteElectronicoService expedienteElectronicoService;

  public synchronized void bloquearExpediente(String sistemaBloqueador, String codigoEE)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("bloquearExpediente(sistemaBloqueador={}, codigoEE={}) - start",
          sistemaBloqueador, codigoEE);
    }

    if (codigoEE == null) {
      throw new ParametroIncorrectoException("El código de expediente dado es nulo.", null);
    } else {
      List<String> listDesgloseCodigoEE = BusinessFormatHelper.obtenerDesgloseCodigoEE(codigoEE);

      expedienteElectronicoService.bloquearExpedienteElectronico(sistemaBloqueador,
          listDesgloseCodigoEE.get(0), Integer.valueOf(listDesgloseCodigoEE.get(1)),
          Integer.valueOf(listDesgloseCodigoEE.get(2)), listDesgloseCodigoEE.get(5));
    }

    if (logger.isDebugEnabled()) {
      logger.debug("bloquearExpediente(String, String) - end");
    }
  }

  public synchronized void desbloquearExpediente(String sistemaSolicitante, String codigoEE)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("desbloquearExpediente(sistemaSolicitante={}, codigoEE={}) - start",
          sistemaSolicitante, codigoEE);
    }

    if (codigoEE == null) {
      throw new ParametroIncorrectoException("El código de expediente dado es nulo.", null);
    } else {
      List<String> listDesgloseCodigoEE = BusinessFormatHelper.obtenerDesgloseCodigoEE(codigoEE);

      expedienteElectronicoService.desbloquearExpedienteElectronico(sistemaSolicitante,
          listDesgloseCodigoEE.get(0), Integer.valueOf(listDesgloseCodigoEE.get(1)),
          Integer.valueOf(listDesgloseCodigoEE.get(2)), listDesgloseCodigoEE.get(5));
    }

    if (logger.isDebugEnabled()) {
      logger.debug("desbloquearExpediente(String, String) - end");
    }
  }

  @Transactional
  public boolean isBloqueado(String codigoEE)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("isBloqueado(codigoEE={}) - start", codigoEE);
    }

    if (codigoEE == null) {
      throw new ParametroIncorrectoException("El código de expediente dado es nulo.", null);
    } else {
      List<String> listDesgloseCodigoEE = BusinessFormatHelper.obtenerDesgloseCodigoEE(codigoEE);
      boolean returnboolean = expedienteElectronicoService.isExpedienteElectronicoBloqueado(
          listDesgloseCodigoEE.get(0), Integer.valueOf(listDesgloseCodigoEE.get(1)),
          Integer.valueOf(listDesgloseCodigoEE.get(2)), listDesgloseCodigoEE.get(5));
      if (logger.isDebugEnabled()) {
        logger.debug("isBloqueado(String) - end - return value={}", returnboolean);
      }
      return returnboolean;
    }
  }

}
