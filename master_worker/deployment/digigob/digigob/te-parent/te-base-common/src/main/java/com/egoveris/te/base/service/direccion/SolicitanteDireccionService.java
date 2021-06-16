package com.egoveris.te.base.service.direccion;

import java.util.List;

import com.egoveris.te.base.model.direccion.DataProvinciaDTO;
import com.egoveris.te.base.model.direccion.SolicitanteDireccionDTO;

public interface SolicitanteDireccionService {
	
	public List<DataProvinciaDTO> getProvinciasCascade();
	public void save(SolicitanteDireccionDTO solicitanteDireccionDTO);
	public SolicitanteDireccionDTO load(Integer idSolicitante);
	
}
