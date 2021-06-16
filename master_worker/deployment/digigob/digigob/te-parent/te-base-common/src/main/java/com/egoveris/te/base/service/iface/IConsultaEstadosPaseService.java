package com.egoveris.te.base.service.iface;


import java.util.Set;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;

/**
 * La presente interface ofrece los servicios de consulta de estados de workflow
 * en los que se puede encontrar un expediente electronico.
 * 
 * @author cearagon
 * 
 */
public interface IConsultaEstadosPaseService {

	/**
	 * Retorna una lista, conteniendo los nombres de los posibles estados a los
	 * que puede emigrar el expediente electronico de codigo SADE dado como
	 * parametro de entrada.
	 * 
	 * @param codigoEE
	 *            : codigo de expediente en formato SADE.
	 *            Ej."EX-2012-00001234-   -MGEYA-MGEYA"
	 * @return listado conteniendo los nombres de los estados posibles a los
	 *         cuales puede emigrar el expediente electronico de codigo SADE
	 *         dado.
	 * @throws ParametroIncorrectoException
	 * @throws ProcesoFallidoException
	 */
	public Set<String> consultaEstadosPosiblesPaseExpediente(String codigoEE)
			throws ParametroIncorrectoException, ProcesoFallidoException;

	/**
	 * Retorna el estado actual en el que se encuentra el expediente electronico
	 * de codigo dado como parametro de entrada.
	 * 
	 * @param codigoEE
	 *            : codigo de expediente en formato SADE.
	 *            Ej."EX-2012-00001234-   -MGEYA-MGEYA"
	 * @return el nombre del estado en el que se encuentra al momento, el
	 *         expediente electronico de codigo dado.
	 * @throws ParametroIncorrectoException
	 * @throws ProcesoFallidoException
	 */
	public String consultaEstadoActualExpediente(String codigoEE)
			throws ParametroIncorrectoException, ProcesoFallidoException;

	/**
	 * Verifica que el expediente de codigoEE, pueda emigrar al estadoDestino
	 * mencionado.
	 * 
	 * @param codigoEE
	 * @return True: si el estadoDestino es un estado de pase valido para la
	 *         condicion actual del expediente electronico cuyo codigoEE es el
	 *         dado como parametro de entrada. False: caso contrario.
	 * @throws ParametroIncorrectoException
	 * @throws ProcesoFallidoException
	 */
	public boolean esEstadoDePaseValido(String codigoEE, String estadoDestino)
			throws ParametroIncorrectoException, ProcesoFallidoException;

}
