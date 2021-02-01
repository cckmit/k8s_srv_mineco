package com.egoveris.te.ws.service;


import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.RehabilitacionExpedienteRequest;
import com.egoveris.te.ws.exception.ExpedienteInexistenteException;

/**
 * La presente interfaz de servicio, expone los métodos necesarios para realizar
 * la rehabilitacion de un Expediente Electronico desde Guarda Temporal
 * 
 * @author smuzychu
 * 
 */
public interface IRehabilitarExpedienteService {

	
	/**
	 * Realiza La rehabilitacion de un Expediente Electronico desde 
	 * Guarda Temporal para ARCH, 
	 * de esta manera se pueden redigitalizar expedientes en ARCH y asociar nuevos documentos  
	 * gedo para satisfacer la funcionalidad.  
	 * @param datosExpediente
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public void rehabilitarExpediente(RehabilitacionExpedienteRequest datosExpediente)throws ProcesoFallidoException,
			 ParametroIncorrectoException,ExpedienteNoDisponibleException;



	
	
	

	
	
}