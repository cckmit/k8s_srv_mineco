package com.egoveris.te.ws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.TrataEE;
import com.egoveris.te.ws.service.IAdministracionTrataService;

@Service
public class ExternalAdministracionTrataServiceImpl implements IAdministracionTrataService {

  @Autowired
  private com.egoveris.te.base.service.iface.IAdministracionTrataService baseService;

  @Override
  public List<TrataEE> obtenerTratasDeEE() throws ProcesoFallidoException {
    return baseService.obtenerTratasDeEE();
  }

  @Override
  public TrataEE obtenerTrataDeEEPorCodigo(String codigoTrata) throws ProcesoFallidoException {
    return baseService.obtenerTrataDeEEPorCodigo(codigoTrata);
  }

  @Override
  public List<TrataEE> buscarTratasEEByTipoSubproceso() throws ProcesoFallidoException {
    return baseService.buscarTratasEEByTipoSubproceso();
  }
  
}