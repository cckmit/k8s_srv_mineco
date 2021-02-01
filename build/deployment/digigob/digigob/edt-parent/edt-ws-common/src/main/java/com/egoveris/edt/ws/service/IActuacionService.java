package com.egoveris.edt.ws.service;

import com.egoveris.edt.model.model.ActuacionSadeDTO;

import java.util.Date;
import java.util.List;

public interface IActuacionService {

  List<ActuacionSadeDTO> obtenerListaTodasLasActuacionesActivasVigentesYEsDocumento(Date vigenciaDesde, Date vigenciaHasta);
  
  /**
   * devolver todas las actuaciones
   * @return
   */
	public List<ActuacionSadeDTO> todasLasActuaciones();
}
