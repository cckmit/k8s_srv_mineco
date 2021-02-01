package com.egoveris.te.base.service.iface;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.PaseExpedienteRequest;

/**
 * La presente interfaz de servicio, expone los métodos necesarios para realizar
 * pases de expedientes electrónicos.
 * 
 * @author cearagon
 * 
 */
public interface IGenerarPaseExpedienteService {

	
	/**
	 * Genera un pase de expediente, sin bloqueo, ni desbloqueo.
	 * @param datosPase
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public String generarPaseEE(PaseExpedienteRequest datosPase)throws ProcesoFallidoException,
			ParametroIncorrectoException,ExpedienteNoDisponibleException;

	/**
	 * Genera un pase de expediente, dejandolo en el mismo estado que se encontraba
	 * @param datosPase
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public String generarPaseEEAdministrador(PaseExpedienteRequest datosPase)throws ProcesoFallidoException,
			 ParametroIncorrectoException,ExpedienteNoDisponibleException;
	
	/**
	 * Genera un pase de expediente, bloqueando al mismo.
	 * @param datosPase
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public String generarPaseEEConBloqueo(PaseExpedienteRequest datosPase)throws ProcesoFallidoException,
	 ParametroIncorrectoException,ExpedienteNoDisponibleException;
	
	/**
	 * Genera un pase de expediente, bloqueando al mismo.
	 * @param datosPase
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public String generarPaseEEConBloqueoYAclaracion(PaseExpedienteRequest datosPase, String motivo)throws ProcesoFallidoException,
	 ParametroIncorrectoException,ExpedienteNoDisponibleException;

	/**
	 * Genera un pase de expediente, desbloqueando al mismo. Lo retorna a EE.
	 * @param datosPase
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public String generarPaseEEConDesbloqueo(PaseExpedienteRequest datosPase)throws ProcesoFallidoException, 
	ParametroIncorrectoException,ExpedienteNoDisponibleException;

	

	/**
	 * Genera un pase de expediente, desbloqueando al mismo. Lo retorna a EE.
	 * @param datosPase
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public String generarPaseEESinDocumento(PaseExpedienteRequest datosPase, String numeroSadePase)throws ProcesoFallidoException, 
	ParametroIncorrectoException,ExpedienteNoDisponibleException;
		
	public void generarPaseEEAArchivo(PaseExpedienteRequest datosPase)throws ProcesoFallidoException,
	 ParametroIncorrectoException,ExpedienteNoDisponibleException;
		
		
	public void generarPaseEEASolicitudArchivo(PaseExpedienteRequest datosPase) throws ProcesoFallidoException,
	 ParametroIncorrectoException,ExpedienteNoDisponibleException;
		
 
	
}