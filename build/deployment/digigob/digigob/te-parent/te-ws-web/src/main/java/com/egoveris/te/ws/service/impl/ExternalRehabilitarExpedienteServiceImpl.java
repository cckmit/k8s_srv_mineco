package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.RehabilitacionExpedienteRequest;
import com.egoveris.te.ws.service.IRehabilitarExpedienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalRehabilitarExpedienteServiceImpl implements IRehabilitarExpedienteService {

  @Autowired
  private com.egoveris.te.base.service.iface.IRehabilitarExpedienteService baseService;

  @Override
  public void rehabilitarExpediente(RehabilitacionExpedienteRequest datosExpediente)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.rehabilitarExpediente(datosExpediente);
  }

}