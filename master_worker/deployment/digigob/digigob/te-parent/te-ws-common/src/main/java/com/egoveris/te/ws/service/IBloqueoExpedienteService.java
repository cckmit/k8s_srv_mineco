package com.egoveris.te.ws.service;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.ws.exception.ExpedienteInexistenteException;

/**
 * La presente interfaz de servicio, expone los métodos necesarios para el
 * bloqueo y/o desbloqueo de expedientes electrónicos.
 * 
 * @author cearagon
 * 
 */
public interface IBloqueoExpedienteService {

	/**
	 * Bloquea un expediente electrónico, de tal manera que ningún otro usuario
	 * pueda operarlo. Sólo se podrá bloquear un expediente que no se encuentre
	 * bloqueado por un sistema externo distinto.
	 * 
	 * @param codigoEE
	 *            : código SADE del expediente electrónico que se desea
	 *            bloquear. Ej. "EX-2012-00001234-   -MGEYA-MGEYA"
	 * @param sistemaBloqueador
	 *            : nombre del sistema que desea bloquear el expediente para
	 *            operar con él.
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public void bloquearExpediente(String sistemaBloqueador, String codigoEE)
	throws ProcesoFallidoException, 
	ParametroIncorrectoException, ExpedienteNoDisponibleException ;

	/**
	 * Desbloquea un expediente electrónico, de tal manera que EE disponga de
	 * él. Sólo podrá desbloquear un expediente, el sistema que lo posea
	 * (sistemaApoderado).
	 * @param sistemaSolicitante: Nombre del sistema que solicita el bloqueo del expediente.
	 * @param codigoEE: código SADE del expediente electrónico que se desea
	 *            desbloquear. Ej. "EX-2012-00001234-   -MGEYA-MGEYA"
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public void desbloquearExpediente(String sistemaSolicitante, String codigoEE)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;

	/**
	 * Verifica si un expediente electrónico se encuentra bloqueado por algún
	 * sistema.
	 * 
	 * @param codigoEE
	 *            : código SADE del expediente electrónico que se desea
	 *            verificar su bloqueo. Ej. "EX-2012-00001234-   -MGEYA-MGEYA"
	 * 
	 */
	public boolean isBloqueado(String codigoEE) throws ProcesoFallidoException,
			 ParametroIncorrectoException;
}
