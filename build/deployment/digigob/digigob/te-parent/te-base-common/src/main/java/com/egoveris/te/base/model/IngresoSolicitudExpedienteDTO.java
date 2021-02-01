package com.egoveris.te.base.model;



public class IngresoSolicitudExpedienteDTO {
	
	private SolicitudExpedienteDTO solicitudExpediente;
	private ExpedienteElectronicoDTO expedienteElectronico;

	public IngresoSolicitudExpedienteDTO() {
		
	}
	
	public IngresoSolicitudExpedienteDTO(SolicitudExpedienteDTO solicitudExpediente, ExpedienteElectronicoDTO expedienteElectronico) {
		this.solicitudExpediente = solicitudExpediente;
		this.expedienteElectronico = expedienteElectronico;
	}
	
	public SolicitudExpedienteDTO getSolicitudExpediente() {
		return this.solicitudExpediente;
	}
	
	public void setSolicitudExpediente(SolicitudExpedienteDTO solicitudExpediente) {
		this.solicitudExpediente = solicitudExpediente;
	}
	
	public ExpedienteElectronicoDTO getExpedienteElectronico() {
		return this.expedienteElectronico;
	}
	
	public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
		this.expedienteElectronico = expedienteElectronico;
	}		
}
