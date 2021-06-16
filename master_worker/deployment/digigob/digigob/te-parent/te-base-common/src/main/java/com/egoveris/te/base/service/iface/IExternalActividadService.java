package com.egoveris.te.base.service.iface;

import com.egoveris.te.base.exception.external.ActividadException;
import com.egoveris.te.base.model.ActividadControlRequest;
/**
 * Interfaz creada para que sea expuesta solo por springRemoting 
 * @author administradorit
 *
 */
public interface IExternalActividadService {
	
	public void cerrarActividad(ActividadControlRequest request)throws ActividadException; 	
}
