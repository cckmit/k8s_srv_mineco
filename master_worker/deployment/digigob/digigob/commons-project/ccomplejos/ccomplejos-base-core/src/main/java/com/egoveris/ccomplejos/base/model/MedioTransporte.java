package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_MEDIO_TRANSPORTE")
public class MedioTransporte extends AbstractCComplejoJPA{
	
	@Column(name = "FECHA_ESTIMADA_LLEGADA")
	protected Date fechaEstimadaLlegada;
	@Column(name = "RUTA")
	protected String ruta;
	@Column(name = "PATENTE")
	protected String patente;
	@ManyToOne
	@JoinColumn(name = "ID_TRANSPORTISTA", referencedColumnName = "id")
	protected Participantes transportista;
	@Column(name = "TIPO_MEDIO_TRANSPORTE")
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
	public Participantes getTransportista() {
		return transportista;
	}
	public void setTransportista(Participantes transportista) {
		this.transportista = transportista;
	}
	public String getTipoMedioTranspote() {
		return tipoMedioTranspote;
	}
	public void setTipoMedioTranspote(String tipoMedioTranspote) {
		this.tipoMedioTranspote = tipoMedioTranspote;
	}

}
