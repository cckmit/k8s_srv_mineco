package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ConsultaExpedienteEnArchImpl implements ConsultaExpedienteEnArchService {
  private static final Logger logger = LoggerFactory.getLogger(ConsultaExpedienteEnArchImpl.class);


  @Override
  public String consultaNumeroDigitalizado(String numeroSadeTrack) {
    if (logger.isDebugEnabled()) {
      logger.debug("consultaNumeroDigitalizado(numeroSadeTrack={}) - start", numeroSadeTrack);
    }

    String returnString = "";
    if (logger.isDebugEnabled()) {
      logger.debug("consultaNumeroDigitalizado(String) - end - return value={}", returnString);
    }
    return returnString;
  }

  @Override
  public boolean enviarASolicitudArchivo(String codExpediente, String usuarioFirmante,
      String usuarioSolicitante) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "enviarASolicitudArchivo(codExpediente={}, usuarioFirmante={}, usuarioSolicitante={}) - start",
          codExpediente, usuarioFirmante, usuarioSolicitante);
    }

    boolean returnboolean = true;
    if (logger.isDebugEnabled()) {
      logger.debug("enviarASolicitudArchivo(String, String, String) - end - return value={}",
          returnboolean);
    }
    return returnboolean;
  }


}
