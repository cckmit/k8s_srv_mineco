package com.egoveris.vucfront.model.service;

import java.util.Date;
import java.util.List;

import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.ExpedienteDocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;

/**
 * Interfaz asociada a la entidad de Expediente Base y a métodos que requieren
 * una nueva transacción.
 * 
 * @author rgodoylo
 *
 */
public interface ExpedienteBaseService {

	/**
	 * Save ExpedienteFamiliaSolicitud.
	 * 
	 * @param expediente
	 * @return
	 */
	ExpedienteFamiliaSolicitudDTO saveExpedienteFamiliaSolicitud(ExpedienteFamiliaSolicitudDTO expediente);

	List<ExpedienteBaseDTO> buscarConsolidacionPorFecha(Date fechaDesde, Date fechaHasta, String organismoUsuario);

	void saveExpedienteDocumento(ExpedienteDocumentoDTO eeDoc);
}
