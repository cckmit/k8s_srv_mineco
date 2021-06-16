/**
 * 
 */
package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.TipoDocumentoDTO;

@Service
@Transactional
public class TipoActuacionSadeServiceImpl implements TipoActuacionSadeService {
  private static final Logger logger = LoggerFactory.getLogger(TipoActuacionSadeServiceImpl.class);

  @Override
  public List<TipoDocumentoDTO> obtenerTiposDocumentoSade(
      ArrayList<TipoDocumentoDTO> tiposDocumentos) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTiposDocumentoSade(tiposDocumentos={}) - start", tiposDocumentos);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerTiposDocumentoSade(ArrayList<TipoDocumento>) - end - return value={null}");
    }
    return null;
  }

}
