package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.ws.service.IAdministracionDeExpedientesAsociadosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalAdministracionDeExpedientesAsociadosServiceImpl
    implements IAdministracionDeExpedientesAsociadosService {

  @Autowired
  private com.egoveris.te.base.service.iface.IAdministracionDeExpedientesAsociadosService baseService;

  @Override
  public void asociarExpediente(String sistemaUsuario, String usuario, String codigoEE,
      String codigoEEAsociado) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.asociarExpediente(sistemaUsuario, usuario, codigoEE, codigoEEAsociado);
  }

  @Override
  public void desasociarExpediente(String sistemaUsuario, String usuario, String codigoEE,
      String codigoEEDesasociado) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.desasociarExpediente(sistemaUsuario, usuario, codigoEE, codigoEEDesasociado);
  }
}