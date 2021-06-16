package com.egoveris.te.base.service.iface;

import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.model.ActividadSolicGuardaTemp;

/**
 * The Interface IActivGuardaTempService.
 */
public interface IActivGuardaTempService {
	
	/**
	 * Buscar actividad solicitud guarda temp.
	 *
	 * @param actividadInbox the actividad inbox
	 * @return the actividad solic guarda temp
	 */
	public ActividadSolicGuardaTemp buscarActividadSolicitudGuardaTemp(ActividadInbox actividadInbox);
	
	/**
	 * Aprobar pase guarda temporal.
	 *
	 * @param solicitudGuardaTemp the solicitud guarda temp
	 * @param username the username
	 * @throws Exception the exception
	 */
	public void aprobarPaseGuardaTemporal(ActividadSolicGuardaTemp solicitudGuardaTemp, String username) throws AsignacionException;
	
	/**
	 * Rechazar pase guarda temporal.
	 *
	 * @param solicitud the solicitud
	 * @param loggedUsername the logged username
	 */
	public void rechazarPaseGuardaTemporal(ActividadSolicGuardaTemp solicitud, String loggedUsername);
}
