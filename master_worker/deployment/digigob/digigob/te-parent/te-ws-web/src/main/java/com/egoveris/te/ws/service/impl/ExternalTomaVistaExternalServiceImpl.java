package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.exception.TomaVistaException;
import com.egoveris.te.model.model.TomaVistaResponse;
import com.egoveris.te.ws.service.ITomaVistaExternalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalTomaVistaExternalServiceImpl implements ITomaVistaExternalService {

  @Autowired
  private com.egoveris.te.base.service.iface.ITomaVistaExternalService baseService;

  @Override
  public TomaVistaResponse generarTomaVistaSinSuspension(String numeroEE, String usuarioTAD)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    return baseService.generarTomaVistaSinSuspension(numeroEE, usuarioTAD);
  }

  @Override
  public String generarTomaVistaConSuspension(String codigoCaratula, String motivo)
      throws ProcesoFallidoException, ParametroIncorrectoException, TomaVistaException {
    return baseService.generarTomaVistaConSuspension(codigoCaratula, motivo);
  }

}