package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ReservaDTO extends AbstractCComplejoDTO implements Serializable{
	
	private static final long serialVersionUID = -5212704575857275962L;
	protected Long idReserva;
	protected String numeroReservaEmbarque;
	protected String numeroRefEnvio;
	protected String nombrePrincipal;
	protected String numeroRotacion;
	protected String ucr;
	protected String buqueRegMomentoLlegada;
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
