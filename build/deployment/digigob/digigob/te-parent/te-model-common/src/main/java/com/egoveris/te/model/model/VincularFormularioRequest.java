package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.List;

/**
 * La presente clase da forma a los datos que se requiere ingresar a un servicio
 * externo, para que éste pueda operar.
 * 
 * @author everis
 */
public class VincularFormularioRequest implements Serializable {

	private static final long serialVersionUID = 1358291145144128010L;

	private List<ExpedienteFormularioDTO> expedienteFormularioDTO;

	/**
	 * @return the expedienteFormularioDTO
	 */
	public List<ExpedienteFormularioDTO> getExpedienteFormularioDTO() {
		return expedienteFormularioDTO;
	}

	/**
	 * @param expedienteFormularioDTO the expedienteFormularioDTO to set
	 */
	public void setExpedienteFormularioDTO(List<ExpedienteFormularioDTO> expedienteFormularioDTO) {
		this.expedienteFormularioDTO = expedienteFormularioDTO;
	}



}
