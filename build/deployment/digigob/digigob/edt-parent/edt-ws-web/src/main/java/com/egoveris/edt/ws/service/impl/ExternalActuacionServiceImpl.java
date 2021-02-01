package com.egoveris.edt.ws.service.impl;

import com.egoveris.edt.model.model.ActuacionSadeDTO;
import com.egoveris.edt.ws.service.IActuacionService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalActuacionServiceImpl implements IActuacionService {

  @Autowired
  private com.egoveris.edt.base.service.actuacion.IActuacionService baseService;

  @Override
  public List<ActuacionSadeDTO> obtenerListaTodasLasActuacionesActivasVigentesYEsDocumento(
      Date vigenciaDesde, Date vigenciaHasta) {
    return baseService.obtenerListaTodasLasActuacionesActivasVigentesYEsDocumento(vigenciaDesde,
        vigenciaHasta);
  }
  
  @Override
  public List<ActuacionSadeDTO> todasLasActuaciones(){
  	return baseService.obtenerActuacion();
  }

}
