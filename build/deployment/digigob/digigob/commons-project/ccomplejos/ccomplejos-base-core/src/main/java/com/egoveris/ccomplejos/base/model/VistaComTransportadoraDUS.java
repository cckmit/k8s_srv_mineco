package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_comtransportadoradus")
public class VistaComTransportadoraDUS extends AbstractViewCComplejoJPA {

	@Column(name = "NOMBRE_CIA_TRANSPORTADORA")
	protected String nombreCiaTransportadora;
	
	@Column(name = "PAIS_CIA_TRANSPORTADORA")
	protected String paisCiaTransportadora;
	
	@Column(name = "RUT_CIA_TRANSPORTADORA")
	protected String rutCiaTransporte;
	
	@Column(name = "EMISOR_DOC_TRASNPORTE")
	protected String emisorDocTransporte;
	
	@Column(name = "RUT_EMISOR_DOC_TRASNPORTE")
	protected String rutEmisorDocTransporte;
	
	@Column(name = "RUT_AGEN_CIA_TRASNP")
	protected String rutAgenciaCiaTransportadora;
	
	@Column(name = "RUT_REP_LEGAL_DOC_TRANSP")
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
