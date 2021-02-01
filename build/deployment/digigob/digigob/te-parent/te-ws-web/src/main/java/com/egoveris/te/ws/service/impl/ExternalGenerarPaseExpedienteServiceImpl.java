package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.PaseExpedienteRequest;
import com.egoveris.te.ws.service.IGenerarPaseExpedienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExternalGenerarPaseExpedienteServiceImpl implements IGenerarPaseExpedienteService {

  @Autowired
  @Qualifier("generarPaseExpedienteServiceImpl")
  private com.egoveris.te.base.service.iface.IGenerarPaseExpedienteService baseService;

  @Override
  public String generarPaseEE(PaseExpedienteRequest datosPase) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException { 
    return baseService.generarPaseEE(datosPase);
  }

  @Override
  public String generarPaseEEAdministrador(PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    return baseService.generarPaseEEAdministrador(datosPase);
  }

  @Override
  public String generarPaseEEConBloqueo(PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    return baseService.generarPaseEEConBloqueo(datosPase);
  }

  @Override
  public String generarPaseEEConBloqueoYAclaracion(PaseExpedienteRequest datosPase, String motivo)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    return baseService.generarPaseEEConBloqueoYAclaracion(datosPase, motivo);
  }

  @Override
  public String generarPaseEEConDesbloqueo(PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    return baseService.generarPaseEEConDesbloqueo(datosPase);
  }

  @Override
  public String generarPaseEESinDocumento(PaseExpedienteRequest datosPase, String numeroSadePase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    return baseService.generarPaseEESinDocumento(datosPase, numeroSadePase);
  }

  @Override
  public void generarPaseEEAArchivo(PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.generarPaseEEAArchivo(datosPase);
  }

  @Override
  public void generarPaseEEASolicitudArchivo(PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    baseService.generarPaseEEASolicitudArchivo(datosPase);
  }

}