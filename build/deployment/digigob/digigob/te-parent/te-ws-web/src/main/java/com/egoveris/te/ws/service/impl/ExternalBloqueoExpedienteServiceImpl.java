package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.ws.service.IBloqueoExpedienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalBloqueoExpedienteServiceImpl implements IBloqueoExpedienteService {

  @Autowired
  private com.egoveris.te.base.service.iface.IBloqueoExpedienteService baseService;

  @Override
  public void bloquearExpediente(String sistemaBloqueador, String codigoEE)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.bloquearExpediente(sistemaBloqueador, codigoEE);
  }

  @Override
  public void desbloquearExpediente(String sistemaSolicitante, String codigoEE)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.desbloquearExpediente(sistemaSolicitante, codigoEE);
  }

  @Override
  public boolean isBloqueado(String codigoEE)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    return baseService.isBloqueado(codigoEE);
  }
}