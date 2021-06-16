package com.egoveris.te.ws.service;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.PaseExpedienteRequest;

public interface IGenerarPaseExpedienteLOySService {
	
	public String generarPaseEELOyS(PaseExpedienteRequest datosPase) throws ProcesoFallidoException,
	 ParametroIncorrectoException,ExpedienteNoDisponibleException;

}
