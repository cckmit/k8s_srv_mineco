package com.egoveris.edt.base.service.impl.estructura;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.service.estructura.IEstructuraHelper;
import com.egoveris.edt.base.service.estructura.IEstructuraService;
import com.egoveris.sharedsecurity.base.model.EstructuraDTO;

@Service("estructuraHelper")
public class EstructuraHelper implements IEstructuraHelper {

  @Autowired
  private IEstructuraService estructuraService;

  private List<EstructuraDTO> listaEstructuras;

  @Override
  @Scheduled(fixedRate = 300000)
  public void cachearListaEstructuras() {
    listaEstructuras = estructuraService.listEstructuras();
  }

  @Override
  public List<EstructuraDTO> obtenerTodasEstructuras() {
    if (listaEstructuras == null) {
      cachearListaEstructuras();
      return listaEstructuras;
    }

    return listaEstructuras;
  }

  public IEstructuraService getReparticionService() {
    return estructuraService;
  }

  public void setEstructuraService(IEstructuraService estructuraService) {
    this.estructuraService = estructuraService;
  }
}