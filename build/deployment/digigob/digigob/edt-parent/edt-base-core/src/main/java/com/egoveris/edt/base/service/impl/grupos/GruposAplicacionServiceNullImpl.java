package com.egoveris.edt.base.service.impl.grupos;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.service.grupos.IGruposAplicacionService;

/**
 * Implementación nula para casos donde no se cuenta con buzón grupal o donde el
 * concepto de grupo no aplica
 * 
 * @author alelarre
 *
 */
@Service
public class GruposAplicacionServiceNullImpl implements IGruposAplicacionService {

  private static final Logger logger = LoggerFactory
      .getLogger(GruposAplicacionServiceNullImpl.class);

  @Override
  public List<String> buscarGruposUsuarioAplicacion(String user) {
    logger.debug("buscarGruposUsuarioAplicacion() is an empty method. Please check it.");
    return new ArrayList<>();
  }

}
