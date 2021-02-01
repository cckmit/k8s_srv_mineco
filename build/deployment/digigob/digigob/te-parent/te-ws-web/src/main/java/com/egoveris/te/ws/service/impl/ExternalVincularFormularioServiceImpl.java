package com.egoveris.te.ws.service.impl;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.VincularFormularioRequest;
import com.egoveris.te.ws.service.IVincularFormularioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalVincularFormularioServiceImpl implements IVincularFormularioService {

  @Autowired
  private com.egoveris.te.base.service.iface.IVincularFormularioService baseService;

  @Override
  public void vincularExpediente(VincularFormularioRequest vincularFormularioRequest)
      throws ProcesoFallidoException, ParametroIncorrectoException {
    baseService.vincularExpediente(vincularFormularioRequest);
  }

}