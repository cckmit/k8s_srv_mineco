package com.egoveris.te.base.service.iface;


import java.util.Map;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.RehabilitacionExpedienteRequest;

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
	
	/**
	 * Desarchivar expediente. 13-05-2020 - Rehabilitar, en realidad
	 *
	 * @param e the expediente electronico
	 * @param loggedUsername the logged username
	 * @param destino the destino
	 * @param estado the estado
	 * @param motivoExpediente the motivo expediente
	 * @return true, if successful
	 */
	public Boolean desarchivarExpediente(ExpedienteElectronicoDTO e, String loggedUsername, String destino,
			String estado, String motivoExpediente, Map<String, String> detalles);
	
}