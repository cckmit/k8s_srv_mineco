package com.egoveris.te.base.service.expediente;


import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;

/**
 * The Interface ExpedienteAsociadoService.
 */
public interface ExpedienteAsociadoService {

	/**
	 * Delete expediente asociado.
	 *
	 * @param expedienteAsociado the expediente asociado
	 */
	public void deleteExpedienteAsociado(ExpedienteAsociadoEntDTO expedienteAsociado);
	
	/**
	 * Obtener expediente asociado por TC.
	 *
	 * @param numero the numero
	 * @param anio the anio
	 * @param asociadoTC the asociado TC
	 * @return the expediente asociado
	 */
	public ExpedienteAsociadoEntDTO obtenerExpedienteAsociadoPorTC(Integer numero, Integer anio, boolean asociadoTC);
	
	/**
	 * Obtener expediente asociado por fusion.
	 *
	 * @param stNumeroSADE the st numero SADE
	 * @param stAnioSADE the st anio SADE
	 * @param asociadoFusion the asociado fusion
	 * @return the expediente asociado
	 */
	public ExpedienteAsociadoEntDTO obtenerExpedienteAsociadoPorFusion(Integer stNumeroSADE, Integer stAnioSADE, boolean asociadoFusion);
	
	/**
	 * Actualizar expediente asociado.
	 *
	 * @param expedienteAsociado the expediente asociado
	 */
	public void actualizarExpedienteAsociado(ExpedienteAsociadoEntDTO expedienteAsociado);

}
