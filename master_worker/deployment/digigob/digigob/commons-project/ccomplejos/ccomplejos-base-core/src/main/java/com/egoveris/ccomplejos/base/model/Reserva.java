package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_RESERVA")
public class Reserva extends AbstractCComplejoJPA{
	
	@Column(name = "ID_RESERVA")
	protected Long idReserva;
	
	@Column(name = "NUMERO_RESERVA_EMBARQUE")
	protected String numeroReservaEmbarque;
	
	@Column(name = "NUMERO_REF_ENVIO")
	protected String numeroRefEnvio;
	
	@Column(name = "NOBRE_PRINCIPAL")
	protected String nombrePrincipal;
	
	@Column(name = "NUMERO_ROTACION")
	protected String numeroRotacion;
	
	@Column(name = "UCT")
	protected String ucr;
	
	@Column(name = "BUQUE_REG_MOMENTO_LLEGADA")
	protected String buqueRegMomentoLlegada;
	
	@Column(name = "BUQUE_REG_FRONTERA")
	protected String buqueRegFrontera;
	
	
	public Long getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}
	public String getNumeroReservaEmbarque() {
		return numeroReservaEmbarque;
	}
	public void setNumeroReservaEmbarque(String numeroReservaEmbarque) {
		this.numeroReservaEmbarque = numeroReservaEmbarque;
	}
	public String getNumeroRefEnvio() {
		return numeroRefEnvio;
	}
	public void setNumeroRefEnvio(String numeroRefEnvio) {
		this.numeroRefEnvio = numeroRefEnvio;
	}
	public String getNombrePrincipal() {
		return nombrePrincipal;
	}
	public void setNombrePrincipal(String nombrePrincipal) {
		this.nombrePrincipal = nombrePrincipal;
	}
	public String getNumeroRotacion() {
		return numeroRotacion;
	}
	public void setNumeroRotacion(String numeroRotacion) {
		this.numeroRotacion = numeroRotacion;
	}
	public String getUcr() {
		return ucr;
	}
	public void setUcr(String ucr) {
		this.ucr = ucr;
	}
	public String getBuqueRegMomentoLlegada() {
		return buqueRegMomentoLlegada;
	}
	public void setBuqueRegMomentoLlegada(String buqueRegMomentoLlegada) {
		this.buqueRegMomentoLlegada = buqueRegMomentoLlegada;
	}
	public String getBuqueRegFrontera() {
		return buqueRegFrontera;
	}
	public void setBuqueRegFrontera(String buqueRegFrontera) {
		this.buqueRegFrontera = buqueRegFrontera;
	}

}
