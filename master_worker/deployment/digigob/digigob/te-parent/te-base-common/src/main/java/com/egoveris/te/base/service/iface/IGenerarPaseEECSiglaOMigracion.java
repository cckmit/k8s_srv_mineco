package com.egoveris.te.base.service.iface;



import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.PaseExpedienteRequest;

public interface IGenerarPaseEECSiglaOMigracion {
	
	
	
	public void generarPaseEESinDocumentoCambioSigla(PaseExpedienteRequest datosPase, String aclaracion,String tipoBloqueo, Boolean generarDoc)throws ProcesoFallidoException, 
	ParametroIncorrectoException,ExpedienteNoDisponibleException;

}
