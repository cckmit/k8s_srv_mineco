package com.egoveris.te.base.service;

import java.util.Map;

import org.jbpm.api.task.Task;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

/**
 * The Interface PaseExpedienteService.
 */
public interface PaseExpedienteService {
	
	/**
	 * Pase guarda temporal.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param workingTask the working task
	 * @param loggedUsername the logged username
	 * @param detalles the detalles
	 * @param estadoAnterior the estado anterior
	 * @param Motivo the motivo
	 */
	public void paseGuardaTemporal(ExpedienteElectronicoDTO expedienteElectronico, Task workingTask, String loggedUsername, Map<String, String> detalles, String estadoAnterior, String motivo);
	
	/**
	 * Pase auto guarda temporal.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param workingTask the working task
	 * @param loggedUsername the logged username
	 * @param detalles the detalles
	 * @param estadoAnterior the estado anterior
	 * @param Motivo the motivo
	 * @param mapEE the map EE
	 * @param mapEEProcesados the map EE procesados
	 */
	public void paseAutoGuardaTemporal(ExpedienteElectronicoDTO expedienteElectronico, Task workingTask, String loggedUsername, Map<String, String> detalles, String estadoAnterior, String motivo, Map<String, ExpedienteElectronicoDTO> mapEE, Map<String, ExpedienteElectronicoDTO> mapEEProcesados);
	
	/**
	 * Pase guarda temporal hijo TC.
	 *
	 * @param ee the ee
	 * @param loggedUsername the logged username
	 * @param detalles the detalles
	 * @param estadoAnterior the estado anterior
	 * @param Motivo the motivo
	 * @param mapEE the map EE
	 * @param mapEEProcesados the map EE procesados
	 */
	public void paseGuardaTemporalHijoTC(ExpedienteElectronicoDTO ee, String loggedUsername, Map<String, String> detalles, String estadoAnterior, String motivo, Map<String, ExpedienteElectronicoDTO> mapEE,Map<String, ExpedienteElectronicoDTO> mapEEProcesados);
	
	/**
	 * Pase guarda temporal hijo fusion.
	 *
	 * @param ee the ee
	 * @param loggedUsername the logged username
	 * @param detalles the detalles
	 * @param estadoAnterior the estado anterior
	 * @param Motivo the motivo
	 * @param mapEE the map EE
	 * @param mapEEProcesados the map EE procesados
	 */
	public void paseGuardaTemporalHijoFusion(ExpedienteElectronicoDTO ee, String loggedUsername, Map<String, String> detalles, String estadoAnterior, String motivo, Map<String, ExpedienteElectronicoDTO> mapEE, Map<String, ExpedienteElectronicoDTO> mapEEProcesados);
	
}
