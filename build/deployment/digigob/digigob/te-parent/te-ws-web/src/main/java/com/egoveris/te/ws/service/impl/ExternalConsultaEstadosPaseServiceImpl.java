package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.ws.service.IConsultaEstadosPaseService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalConsultaEstadosPaseServiceImpl implements IConsultaEstadosPaseService {

  @Autowired
  private com.egoveris.te.base.service.iface.IConsultaEstadosPaseService baseService;

  @Override
  public Set<String> consultaEstadosPosiblesPaseExpediente(String codigoEE)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    return baseService.consultaEstadosPosiblesPaseExpediente(codigoEE);
  }

  @Override
  public String consultaEstadoActualExpediente(String codigoEE)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    return baseService.consultaEstadoActualExpediente(codigoEE);
  }

  @Override
  public boolean esEstadoDePaseValido(String codigoEE, String estadoDestino)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    return baseService.esEstadoDePaseValido(codigoEE, estadoDestino);
  }
}