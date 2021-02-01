package com.egoveris.vucfront.ws.model;

import java.io.Serializable;
import java.util.Date;

public class ConsolidacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3761507829435132891L;

	private String codigoTrata;
	
	private String descripcionTrata;
	
	private String numeroExpediente;
	
	private Long numeroTransaccionTad;
	
	private Date fechaPago;
	
	private String numeroAutorizacion;
	
	private String transaccionPago;
	
	private String apiKey;
	
	private String monto;
	
	private String titularTarjeta;
	
	private String numeroDUI;
	
	private String organismoIniciador;

	public String getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public Long getNumeroTransaccionTad() {
		return numeroTransaccionTad;
	}

	public void setNumeroTransaccionTad(Long numeroTransaccionTad) {
		this.numeroTransaccionTad = numeroTransaccionTad;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public String getTransaccionPago() {
		return transaccionPago;
	}

	public void setTransaccionPago(String transaccionPago) {
		this.transaccionPago = transaccionPago;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getTitularTarjeta() {
		return titularTarjeta;
	}

	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}

	public String getNumeroDUI() {
		return numeroDUI;
	}

	public void setNumeroDUI(String numeroDUI) {
		this.numeroDUI = numeroDUI;
	}

	public String getOrganismoIniciador() {
		return organismoIniciador;
	}

	public void setOrganismoIniciador(String organismoIniciador) {
		this.organismoIniciador = organismoIniciador;
	}
	
	
}
