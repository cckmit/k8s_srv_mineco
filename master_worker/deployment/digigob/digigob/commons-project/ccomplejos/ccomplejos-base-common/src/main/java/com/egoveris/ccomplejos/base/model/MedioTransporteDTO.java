package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class MedioTransporteDTO extends AbstractCComplejoDTO{
	
	private static final long serialVersionUID = 18800162863976226L;

	
	protected Date fechaEstimadaLlegada;
	protected String ruta;
	protected String patente;
	protected ParticipantesDTO transportista;
	protected String tipoMedioTranspote;
	
	public Date getFechaEstimadaLlegada() {
		return fechaEstimadaLlegada;
	}
	public void setFechaEstimadaLlegada(Date fechaEstimadaLlegada) {
		this.fechaEstimadaLlegada = fechaEstimadaLlegada;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	public ParticipantesDTO getTransportista() {
		return transportista;
	}
	public void setTransportista(ParticipantesDTO transportista) {
		this.transportista = transportista;
	}
	public String getTipoMedioTranspote() {
		return tipoMedioTranspote;
	}
	public void setTipoMedioTranspote(String tipoMedioTranspote) {
		this.tipoMedioTranspote = tipoMedioTranspote;
	}
	
}
