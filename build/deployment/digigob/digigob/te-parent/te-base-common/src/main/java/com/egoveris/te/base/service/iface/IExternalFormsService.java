package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;

public interface IExternalFormsService {

	public List<FormularioDTO> buscarFormulariosFFDD() throws DynFormException;

	public FormularioDTO buscarFormularioNombreFFDD(ExpedienteFormularioDTO expedienteFormulario) throws DynFormException;

	public void eliminarFormularioFFDD(ExpedienteFormularioDTO expedienteFormulario) throws DynFormException;

	public TransaccionDTO buscarTransaccionPorIdFFDD(Integer idTransaction) throws DynFormException;

}
