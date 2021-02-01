package com.egoveris.te.ws.service;

import com.egoveris.te.model.exception.ActividadException;
import com.egoveris.te.model.model.ActividadRequest;
import com.egoveris.te.model.model.GuardaTempRequest;
import com.egoveris.te.model.model.ResolucionSubsRequest;
/**
 * The Interface IActividadExternalService.
 */
public interface IActividadExternalService {

	/**
	 * Generar actividad subsanacion.
	 *
	 * @param request the request
	 * @throws ActividadException the actividad exception
	 */
	public void generarActividadSubsanacion(ResolucionSubsRequest request)
			throws ActividadException;
	
	/**
	 * Generar actividad guarda temporal.
	 *
	 * @param request the request
	 * @return the int
	 * @throws ActividadException the actividad exception
	 */
	public int generarActividadGuardaTemporal(GuardaTempRequest request)
			throws ActividadException;
	
	/**
	 * Generar actividad SIR.
	 *
	 * @param request the request
	 * @return the int
	 * @throws ActividadException the actividad exception
	 */
	public int generarActividadSIR(ActividadRequest request)
			throws ActividadException;	
	
	/**
	 * Cerrar actividad auditoria expediente ifce arch.
	 *
	 * @param request the request
	 * @return the int
	 * @throws ActividadException the actividad exception
	 */
	public int cerrarActividadAuditoriaExpedienteIfceArch(ActividadRequest request)
			throws ActividadException;	
	 
	public void notificarDocumentoGenerado(String indicador, String numero, String executionId, String usuario)
			throws ActividadException;	
}
