package com.egoveris.vucfront.ws.service;

import java.util.Date;
import java.util.List;

import com.egoveris.vucfront.ws.model.ConsolidacionDTO;

public interface ExternalConsolidacionService {
	
	
	List<ConsolidacionDTO> consultarConsolidacionEntreFecha(Date fechaDesde, Date fechaHasta, String organismo);

}
