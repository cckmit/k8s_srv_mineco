package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;

public interface IExpedienteFormularioService {

	public Boolean guardarFormulario(ExpedienteFormularioDTO expedienteFormulario) throws DynFormException;

	public void eliminarFormulario(ExpedienteFormularioDTO expedienteFormulario) throws DynFormException;	

	public List<ExpedienteFormularioDTO> buscarFormulariosPorExpediente(ExpedienteFormularioDTO expedienteFormulario) throws DynFormException;

	/**
	 * Marca como definitivos todos los ExpedienteFormulario del id pasado por parametro.
	 * @param idExpediente Id del expediente
	 */
	public void makeDefinitive(Long idExpediente);

}
