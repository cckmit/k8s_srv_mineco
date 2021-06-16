package com.egoveris.vucfront.base.mbeans.steps;

import javax.faces.bean.ManagedProperty;

import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.service.ExpedienteBaseService;
import com.egoveris.vucfront.model.service.ExpedienteService;

@SuppressWarnings("serial")
/**
 * Shared fields of Step*Mb
 * 
 * @author rgodoylo
 *
 */
public abstract class StepParentMb extends AbstractMb {

	@ManagedProperty("#{expedienteBaseServiceImpl}")
	private ExpedienteBaseService expedienteBaseService;

	@ManagedProperty("#{expedienteServiceImpl}")
	private ExpedienteService expedienteService;

	public Long idExpediente;
	private ExpedienteFamiliaSolicitudDTO expediente;
	private PersonaDTO persona;

	public ExpedienteBaseService getExpedienteBaseService() {
		return expedienteBaseService;
	}

	public void setExpedienteBaseService(ExpedienteBaseService expedienteBaseService) {
		this.expedienteBaseService = expedienteBaseService;
	}

	public ExpedienteService getExpedienteService() {
		return expedienteService;
	}

	public void setExpedienteService(ExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public abstract void init();

	public Long getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}

	public ExpedienteFamiliaSolicitudDTO getExpediente() {
		return expediente;
	}

	public void setExpediente(ExpedienteFamiliaSolicitudDTO expediente) {
		this.expediente = expediente;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public void cmdCancel() {
		redirect(ConstantsUrl.INDEX);
	}

	/**
	 * Checks if the expediente is in borrador state.
	 * 
	 * @return true if it's borrador, false otherwise.
	 */
	public boolean isBorrador() {
		boolean isBorrador = false;
		if (expediente.getEstadoTramite() != null && expediente.getEstadoTramite().getId().equals(1l)) {
			isBorrador = true;
		}
		return isBorrador;
	}

}