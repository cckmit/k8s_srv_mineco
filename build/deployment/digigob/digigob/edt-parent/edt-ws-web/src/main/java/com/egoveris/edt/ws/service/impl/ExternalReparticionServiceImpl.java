package com.egoveris.edt.ws.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.ws.service.IExternalReparticionService;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.base.service.ISectorEDTService;

@Service
public class ExternalReparticionServiceImpl implements IExternalReparticionService {

  private static final Logger logger = LoggerFactory.getLogger(IExternalReparticionService.class);
	
  @Autowired
  private IReparticionEDTService IReparticionService;
  
  @Autowired
  private ISectorEDTService sectorEDTService;
   
  @Override
  public List<Map> getReparticionByUserName(String userName) { 
	  List<Map> reparti = IReparticionService.getAllReparticionByUserName(userName); 
	  return reparti;
  }
   
	@Override
	public List<String> obtenerReparticiones() {
		return IReparticionService.obtenerReparticiones();
	}

	@Override
	public List<String> obtenerSectores(String reparticion) {
		return sectorEDTService.obtenerSectores(reparticion);
	}

}
