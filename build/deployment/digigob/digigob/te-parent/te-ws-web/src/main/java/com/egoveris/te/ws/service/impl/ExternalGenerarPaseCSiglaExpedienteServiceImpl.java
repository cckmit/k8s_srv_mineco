package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.PaseExpedienteRequest;
import com.egoveris.te.ws.service.IGenerarPaseEECSiglaOMigracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalGenerarPaseCSiglaExpedienteServiceImpl
    implements IGenerarPaseEECSiglaOMigracion {

  @Autowired
  private com.egoveris.te.base.service.iface.IGenerarPaseEECSiglaOMigracion baseService;

  @Override
  public void generarPaseEESinDocumentoCambioSigla(PaseExpedienteRequest datosPase,
      String aclaracion, String tipoBloqueo, Boolean generarDoc) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    baseService.generarPaseEESinDocumentoCambioSigla(datosPase, aclaracion, tipoBloqueo,
        generarDoc);
  }

}