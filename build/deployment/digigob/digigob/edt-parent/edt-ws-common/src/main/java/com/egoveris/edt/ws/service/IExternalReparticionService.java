package com.egoveris.edt.ws.service;

import java.util.List;
import java.util.Map;

public interface IExternalReparticionService {
 
  
	 /**
	   * Gets the reparticion by user name.
	   *
	   * @param userName
	   *          the user name
	   * @return the reparticion by user name
	   */
	List<Map> getReparticionByUserName(String userName);
	
	List<String> obtenerReparticiones();

	List<String> obtenerSectores(String reparticion);
   
}
