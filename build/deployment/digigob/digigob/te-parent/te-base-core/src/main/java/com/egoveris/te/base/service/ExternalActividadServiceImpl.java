/**
 * 
 */
package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.exception.external.ActividadException;
import com.egoveris.te.base.model.ActividadControlRequest;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.IExternalActividadService;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

@Service
@Transactional
public class ExternalActividadServiceImpl implements IExternalActividadService {
  private static final Logger logger = LoggerFactory.getLogger(ExternalActividadServiceImpl.class);
  @Autowired
  private IActividadExpedienteService actividadExpedienteService;

  public void cerrarActividad(ActividadControlRequest request) throws ActividadException {
    if (logger.isDebugEnabled()) {
      logger.debug("cerrarActividad(request={}) - start", request);
    }

    if (StringUtils.isEmpty(request.getEstado())) {
      throw new ActividadException("Error al cerrar actividad, parametro invalido: ESTADO ", null);
    }

    if (StringUtils.isEmpty(request.getIdObjetivo())) {
      throw new ActividadException("Error al cerrar actividad, parametro invalido: IDOBJETIVO ",
          null);
    }

    Long idObjetivo = null;

    try {
      idObjetivo = Long.valueOf(request.getIdObjetivo());
    } catch (Exception e) {
      logger.error("cerrarActividad(ActividadControlRequest)", e);

      throw new ActividadException("Error realizar conversion, parametro invalido: IDOBJETIVO ",
          null);
    }

    if (StringUtils.isEmpty(request.getNombreUsuario())) {
      throw new ActividadException("Error al cerrar actividad, parametro invalido: USUARIO ",
          null);
    }

    if (StringUtils.isEmpty(request.getNumeroExpediente())) {
      throw new ActividadException(
          "Error al cerrar actividad, parametro invalido: NUMEROEXPEDIENTE ", null);
    }

    try {
      actividadExpedienteService.cerrarActividad(request.getNumeroExpediente(),
          request.getTipoActividad(), idObjetivo, request.getNombreUsuario(), request.getEstado());
    } catch (ParametroIncorrectoException e) {
      logger.error("cerrarActividad(ActividadControlRequest)", e);

      throw new ActividadException(e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cerrarActividad(ActividadControlRequest) - end");
    }
  }
}
