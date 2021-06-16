package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaComTransportadoraDUSDTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3750573211400218666L;
	
	
	protected String nombreCiaTransportadora;
	protected String paisCiaTransportadora;
	protected String rutCiaTransporte;
	protected String emisorDocTransporte;
	protected String rutEmisorDocTransporte;
	protected String rutAgenciaCiaTransportadora;
	protected String rutRepLegalEmisorDocTransporte;
	
	
	public String getNombreCiaTransportadora() {
		return nombreCiaTransportadora;
	}
	public void setNombreCiaTransportadora(String nombreCiaTransportadora) {
		this.nombreCiaTransportadora = nombreCiaTransportadora;
	}
	public String getPaisCiaTransportadora() {
		return paisCiaTransportadora;
	}
	public void setPaisCiaTransportadora(String paisCiaTransportadora) {
		this.paisCiaTransportadora = paisCiaTransportadora;
	}
	public String getRutCiaTransporte() {
		return rutCiaTransporte;
	}
	public void setRutCiaTransporte(String rutCiaTransporte) {
		this.rutCiaTransporte = rutCiaTransporte;
	}
	public String getEmisorDocTransporte() {
		return emisorDocTransporte;
	}
	public void setEmisorDocTransporte(String emisorDocTransporte) {
		this.emisorDocTransporte = emisorDocTransporte;
	}
	public String getRutEmisorDocTransporte() {
		return rutEmisorDocTransporte;
	}
	public void setRutEmisorDocTransporte(String rutEmisorDocTransporte) {
		this.rutEmisorDocTransporte = rutEmisorDocTransporte;
	}
	public String getRutAgenciaCiaTransportadora() {
		return rutAgenciaCiaTransportadora;
	}
	public void setRutAgenciaCiaTransportadora(String rutAgenciaCiaTransportadora) {
		this.rutAgenciaCiaTransportadora = rutAgenciaCiaTransportadora;
	}
	public String getRutRepLegalEmisorDocTransporte() {
		return rutRepLegalEmisorDocTransporte;
	}
	public void setRutRepLegalEmisorDocTransporte(String rutRepLegalEmisorDocTransporte) {
		this.rutRepLegalEmisorDocTransporte = rutRepLegalEmisorDocTransporte;
	}


}
