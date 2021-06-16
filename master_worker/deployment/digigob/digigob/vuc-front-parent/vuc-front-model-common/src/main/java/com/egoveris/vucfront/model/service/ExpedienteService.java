package com.egoveris.vucfront.model.service;

import java.util.List;
import java.util.Map;

import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;

public interface ExpedienteService {

	/**
	 * Get's the ExpedienteFamiliaSolicitud by id.
	 *
	 * @param The ExpedienteFamiliaSolicitud's ID
	 * @return the ExpedienteFamiliaSolicitud by id
	 */
	ExpedienteFamiliaSolicitudDTO getExpedienteFamiliaSolicitudById(Long idExpediente);

	/**
	 * Sends the Expediente to TE from tramitación.
	 * 
	 * @param expediente
	 */
	void sendApplication(ExpedienteFamiliaSolicitudDTO expediente, Map<String, String> infoPago);

	/**
	 * Get's an ExpedienteBase by it's Código.
	 * 
	 * @param codSadeExpediente
	 * @return
	 */

	ExpedienteBaseDTO getExpedienteBaseByCodigo(String codSadeExpediente);

	/**
	 * Get's all the Expedientes from a Persona.
	 * 
	 * @param persona
	 * @return
	 */
	List<ExpedienteBaseDTO> getExpedientesBaseByPersona(PersonaDTO persona);

}
