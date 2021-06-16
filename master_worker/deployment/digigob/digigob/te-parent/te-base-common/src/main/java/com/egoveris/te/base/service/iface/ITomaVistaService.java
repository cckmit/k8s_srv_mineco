package com.egoveris.te.base.service.iface;

import com.egoveris.te.base.model.TomaVistaDTO;
import com.egoveris.te.model.exception.TomaVistaException;

/**
 * Interfaz que expone los servicios utilizados internamente en la toma de vista.
 *
 * @author MAGARCES
 */
public interface ITomaVistaService {
	
	/**
	 * Aceptar toma de vista.
	 *
	 * @param tomaVistaDTO the toma vista DTO
	 * @throws TomaVistaException the toma vista exception
	 */
	public void aceptarTomadeVista(TomaVistaDTO tomaVistaDTO) throws TomaVistaException;
	
	/**
	 * Rechazar toma de vista.
	 *
	 * @param tomaVistaDTO the toma vista DTO
	 * @throws TomaVistaException the toma vista exception
	 */
	public void rechazarTomadeVista(TomaVistaDTO tomaVistaDTO) throws TomaVistaException;

}
