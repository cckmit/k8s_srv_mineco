package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ActividadException;
import com.egoveris.te.model.model.ActividadRequest;
import com.egoveris.te.model.model.GuardaTempRequest;
import com.egoveris.te.model.model.ResolucionSubsRequest;
import com.egoveris.te.ws.service.IActividadExternalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalActividadExternalServiceImpl implements IActividadExternalService {

  @Autowired
  private com.egoveris.te.base.service.iface.IActividadExternalService baseService;

  @Override
  public void generarActividadSubsanacion(ResolucionSubsRequest request)
      throws ActividadException {
    baseService.generarActividadSubsanacion(request);
  }

  @Override
  public int generarActividadGuardaTemporal(GuardaTempRequest request) throws ActividadException {
    return baseService.generarActividadGuardaTemporal(request).intValue();
  }

  @Override
  public int generarActividadSIR(ActividadRequest request) throws ActividadException {
    return baseService.generarActividadSIR(request);
  }

  @Override
  public int cerrarActividadAuditoriaExpedienteIfceArch(ActividadRequest request) {
    return baseService.cerrarActividadAuditoriaExpedienteIfceArch(request);
  }

  @Override
  public void notificarDocumentoGenerado(String indicador, String numero, String executionId, String usuario) throws ActividadException {
	  baseService.notificarDocumentoGenerado(indicador, numero, executionId, usuario);
  }
}