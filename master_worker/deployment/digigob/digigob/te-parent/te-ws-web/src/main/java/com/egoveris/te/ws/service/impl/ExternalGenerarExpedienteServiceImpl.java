package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.CaratulacionExpedienteRequest;
import com.egoveris.te.ws.service.IGenerarExpedienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalGenerarExpedienteServiceImpl implements IGenerarExpedienteService {

  @Autowired
  private com.egoveris.te.base.service.iface.IGenerarExpedienteService baseService;

  @Override
  public String generarExpedienteElectronicoCaratulacion(CaratulacionExpedienteRequest request)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    return baseService.generarExpedienteElectronicoCaratulacion(request);
  }
}