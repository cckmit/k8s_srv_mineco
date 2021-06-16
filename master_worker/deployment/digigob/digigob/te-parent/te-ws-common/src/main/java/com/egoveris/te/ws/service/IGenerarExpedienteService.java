package com.egoveris.te.ws.service;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.CaratulacionExpedienteRequest;

/**
 * La presente interfaz de servicio expone los métodos necesarios para la
 * generación de expedientes electrónicos.
 * 
 * @author cearagon
 * 
 */
public interface IGenerarExpedienteService {
	/**
	 * Servicio que genera la caratulación de un Expediente Electrónico, dando
	 * así inicio al mismo.
	 * El expediente electrónico generado, poseerá los siguientes valores significativos:
	 * estado = Iniciacion
	 * bloqueado = true
	 * 
	 * @param request
	 *            : contenedor de datos necesarios para iniciar la creación de
	 *            un expediente electrónico.
	 * @return String: código SADE del expediente electrónico generado.
	 *         Ej."EX-2012-00001478-   -MGEYA-MGEYA"
	 * @throws ProcesoFallidoException
	 * @throws ParametroIncorrectoException
	 */
	public String generarExpedienteElectronicoCaratulacion(
			 CaratulacionExpedienteRequest request)
			throws ProcesoFallidoException, ParametroIncorrectoException;
	
	
	

}
