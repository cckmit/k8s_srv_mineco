package com.egoveris.edt.base.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.egoveris.edt.base.service.ITareasService;
import com.egoveris.edt.base.service.ITareasServiceFactory;

// No usa @Service, esta definido en edt-env.xml
public class TareasServiceFactoryImpl implements ITareasServiceFactory {
  // POR AHORA EL MAPA DE BEANS SE ENCUENTRA AQUI, INYECTADO EN LA DEFINICION
  // DEL BEAN TareasServiceFactory EN EL XML
  // TODO PASAR ESTA LOGICA A UN CONSTRUCTOR QUE RECIBA TODAS LAS APLICACIONES
  // Y QUE EN BASE A ATRIBUTOS DE LA BBDD SEPA QUE BEAN TRAERA LAS TAREAS, QUE
  // DATASOURCE UTILIZAR, ETC
	
	// 05-06-2017 UPDATE: SE UTILIZA AUTOWIRED PARA EL MAP
	
	@Autowired
  private Map<String, ITareasService> mapTareasService;
  
  @Override
  public ITareasService get(String sistemName) {
    return mapTareasService.get(sistemName);
  }
  
  public void setMapTareasService(Map<String, ITareasService> mapTareasService) {
    this.mapTareasService = mapTareasService;
  }
}
