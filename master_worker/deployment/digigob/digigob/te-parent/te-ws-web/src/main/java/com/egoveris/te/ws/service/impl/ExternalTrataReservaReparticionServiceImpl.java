package com.egoveris.te.ws.service.impl;

import com.egoveris.te.ws.service.ITrataReservaReparticionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalTrataReservaReparticionServiceImpl
    implements ITrataReservaReparticionService {

  @Autowired
  private com.egoveris.te.base.service.iface.ITrataReservaReparticionService baseService;

  @Override
  public boolean reparticionDeUsuarioTienePermisosDeReservaEnEE(String usuario) {
    return baseService.reparticionDeUsuarioTienePermisosDeReservaEnEE(usuario);
  }

}