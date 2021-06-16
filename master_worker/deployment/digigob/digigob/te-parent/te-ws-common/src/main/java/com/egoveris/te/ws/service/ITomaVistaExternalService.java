package com.egoveris.te.ws.service;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.exception.TomaVistaException;
import com.egoveris.te.model.model.TomaVistaResponse;
import com.egoveris.te.ws.exception.ExpedienteInexistenteException;

public interface ITomaVistaExternalService {

	/**
	 * 
	 * El usuario TAD solicita la vista de TODOS los documentos GEDO asociados al EE. 
	 * El sistema le devolverá al usuario una lista con los números de documentos asociados al EE.
	 * 
	 * @param numeroEE - Numero del expediente del que se desea tomar vista.
	 * @param usuarioTAD - Usuario necesario al momento de la generacion del documento GEDO al ser solicitada 
	 * 		               la operacion de toma de vista.
	 * @return TomaVistaResponse -DTO con la lista de los documentos oficiales para el EE. 
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public TomaVistaResponse generarTomaVistaSinSuspension(String numeroEE, String usuarioTAD)
		throws ProcesoFallidoException, 	ParametroIncorrectoException;

	/**
	 * 
	 * El usuario TAD solicita la toma de vista con suspensión. 
	 * El sistema bloqueara al EE y por medio de un nuevo EE-TV se realiza el pase del nuevo expediente para que 
	 * sea aprobada por el usuario Autorizado. 
	 * Si el usuario autorizado aprueba la toma de vista, podrá elegir que documentos mostrar y cuales no o bien, 
	 * podrá rechazar el pedido de toma vista. 
	 * 
	 * @param numeroEE - Numero del expediente del que se desea tomar vista.
	 * @param caratulacionExpedienteRequest
	 * @param DGTAL
	 * @return
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public String generarTomaVistaConSuspension(String codigoCaratula, String motivo)
		throws ProcesoFallidoException, ParametroIncorrectoException,TomaVistaException;
	
}
