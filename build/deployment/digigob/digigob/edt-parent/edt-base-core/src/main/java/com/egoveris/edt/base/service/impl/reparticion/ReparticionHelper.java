package com.egoveris.edt.base.service.impl.reparticion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.service.reparticion.IReparticionHelper;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;

@Service("reparticionHelper")
public class ReparticionHelper implements IReparticionHelper {

  @Autowired
  private IReparticionEDTService reparticionService;

  private List<ReparticionDTO> listaReparticiones;

  @Override
  @Scheduled(fixedRate = 300000)
  public void cachearListaReparticiones() {
    listaReparticiones = reparticionService.listReparticiones();
  }

  @Override
  public List<ReparticionDTO> obtenerTodosReparticiones() {
    if (listaReparticiones == null) {
      cachearListaReparticiones();
      return listaReparticiones;
    }

    return listaReparticiones;
  }

  public IReparticionEDTService getReparticionService() {
    return reparticionService;
  }

  public void setReparticionService(IReparticionEDTService reparticionService) {
    this.reparticionService = reparticionService;
  }
}