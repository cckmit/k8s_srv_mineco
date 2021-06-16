package com.egoveris.te.ws.service;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.ws.exception.ExpedienteInexistenteException;

/**
 * La presente interfaz de servicio, expone los métodos necesarios para la
 * administración de expedientes asociados a un expediente electrónico.
 * 
 * @author cearagon
 * 
 */
public interface IAdministracionDeExpedientesAsociadosService {

	/**
	 * Asocia al expediente electrónico de código (codigoEE) provisto por
	 * parámetro, otro expediente de código (codigoEEAsociado) también otorgado
	 * por parámetro.
	 * 
	 * @param sistemaUsuario
	 *            : Nombre del Sistema al que pertenece el usuario; desde el
	 *            cual solicita la asociación.
	 * @param usuario
	 *            : Nombre del usuario que solicita la asociación.
	 * @param codigoEE
	 *            : código SADE del expediente electrónico al cual se le
	 *            asociará otro expediente electrónico. Ej.
	 *            "EX-2012-00001234-   -MGEYA-MGEYA"
	 * @param codigoEEAsociado
	 *            : código SADE del expediente electrónico que será asociado al
	 *            expediente de código codigoEE. Ej.
	 *            "EX-2012-00008524-   -MGEYA-MGEYA"
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public void asociarExpediente(String sistemaUsuario,String usuario,
			String codigoEE,String codigoEEAsociado)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;

	/**
	 * Desasocia al expediente electrónico de código (codigoEE) provisto por parámetro, 
	 * otro expediente de código (codigoEEAsociado) también otorgado por parámetro.
	 * 
	 * @param sistemaUsuario
	 *            : Nombre del Sistema al que pertenece el usuario; desde el
	 *            cual solicita la desasociación.
	 * @param usuario
	 *            : Nombre del usuario que solicita la desasociación.
	 * @param codigoEE
	 *            : código SADE del expediente electrónico al cual se le
	 *            desasociará otro expediente electrónico. Ej. "EX-2012-00014789-   -MGEYA-MGEYA"
	 * @param codigoEEDesasociado
	 *            : código SADE del expediente electrónico que será desasociado
	 *            del expediente de código codigoEE. Ej. "EX-2012-00005874-   -MGEYA-MGEYA"
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public void desasociarExpediente(String sistemaUsuario,String usuario,
			String codigoEE, String codigoEEDesasociado)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;

}
