package com.egoveris.te.base.service.expediente;

import java.util.List;

import com.egoveris.te.base.model.ExpedienteElectronicoConSuspensionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

/**
 * The Interface ExpedienteElectronicoConSuspensionService.
 */
public interface ExpedienteElectronicoConSuspensionService {

	/**
	 * Inserta el ee en la tabla EE_CON_SUSPENSION.
	 *
	 * @param expedienteElectronico
	 *            the expediente electronico
	 * @throws ParametroIncorrectoException
	 *             the parametro incorrecto exception
	 */
	public void bloquearExpedienteElectronicoTV(ExpedienteElectronicoDTO expedienteElectronico)
			throws ParametroIncorrectoException;

	/**
	 * Actualizar expediente electronico TV.
	 *
	 * @param eeConSuspension
	 *            the ee con suspension
	 * @throws ParametroIncorrectoException
	 *             the parametro incorrecto exception
	 */
	public void actualizarExpedienteElectronicoTV(ExpedienteElectronicoConSuspensionDTO eeConSuspension)
			throws ParametroIncorrectoException;

	/**
	 * Eliminar EE con suspension.
	 *
	 * @param eeConSuspension
	 *            the ee con suspension
	 * @throws ParametroIncorrectoException
	 *             the parametro incorrecto exception
	 */
	public void eliminarEEConSuspension(ExpedienteElectronicoConSuspensionDTO eeConSuspension)
			throws ParametroIncorrectoException;

	/**
	 * Obtener EE con suspension por codigo.
	 *
	 * @param idEEConSuspension
	 *            the id EE con suspension
	 * @return the expediente electronico con suspension
	 * @throws ParametroIncorrectoException
	 *             the parametro incorrecto exception
	 */
	public ExpedienteElectronicoConSuspensionDTO obtenerEEConSuspensionPorCodigo(Long idEEConSuspension)
			throws ParametroIncorrectoException;

	/**
	 * Obtener all EE con suspension.
	 *
	 * @return the list
	 * @throws ParametroIncorrectoException
	 *             the parametro incorrecto exception
	 */
	public List<ExpedienteElectronicoConSuspensionDTO> obtenerAllEEConSuspension() throws ParametroIncorrectoException;
}
