package com.egoveris.edt.base.service.impl.actuacion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.actuacion.ActuacionDTO;
import com.egoveris.edt.base.service.actuacion.IActuacionHelper;
import com.egoveris.edt.base.service.actuacion.IActuacionService;

@Service("actuacionHelper")
@Transactional
public class ActuacionHelper implements IActuacionHelper {

  @Autowired
  private IActuacionService actuacionService;

  private List<ActuacionDTO> listaEstructuras;

  @Override
  @Scheduled(fixedRate = 300000)
  public void cachearListaActuaciones() {
    listaEstructuras = actuacionService.listActuacion();
  }

  @Override
  public List<ActuacionDTO> obtenerTodasActuaciones() {
    if (listaEstructuras == null) {
      cachearListaActuaciones();
      return listaEstructuras;
    }

    return listaEstructuras;
  }

  public IActuacionService getActuacionService() {
    return actuacionService;
  }

  public void setEstructuraService(IActuacionService actuacionService) {
    this.actuacionService = actuacionService;
  }
}