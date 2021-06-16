package com.egoveris.te.ws.service;

import com.egoveris.te.model.exception.ActividadException;
import com.egoveris.te.model.model.ActividadControlRequest;
/**
 * Interfaz creada para que sea expuesta solo por springRemoting 
 * @author administradorit
 *
 */
public interface IExternalActividadService {
	
	public void cerrarActividad(ActividadControlRequest request)throws ActividadException; 	
}
