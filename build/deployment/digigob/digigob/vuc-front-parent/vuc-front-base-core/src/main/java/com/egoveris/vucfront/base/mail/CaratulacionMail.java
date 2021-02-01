package com.egoveris.vucfront.base.mail;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaratulacionMail implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1529905853406766607L;

	private String numeroTramite;
	
	private String codigoTramite;
	
	private String nombreTramite;
	
	private String nombreCompleto;
	
	private String numeroCaratula;
	
	private String nombreDocumento;
	
	private Date fecha;
	
	private byte[] docPDF;
	
	public String getNumeroTramite() {
		return numeroTramite;
	}

	public void setNumeroTramite(String numeroTramite) {
		this.numeroTramite = numeroTramite;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public byte[] getDocPDF() {
		return docPDF;
	}

	public void setDocPDF(byte[] docPDF) {
		this.docPDF = docPDF;
	}

	public String getNumeroCaratula() {
		return numeroCaratula;
	}

	public void setNumeroCaratula(String numeroCaratula) {
		this.numeroCaratula = numeroCaratula;
	}

	public String getCodigoTramite() {
		return codigoTramite;
	}

	public void setCodigoTramite(String codigoTramite) {
		this.codigoTramite = codigoTramite;
	}

	public String getNombreTramite() {
		return nombreTramite;
	}

	public void setNombreTramite(String nombreTramite) {
		this.nombreTramite = nombreTramite;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getFechaString() {
		if(this.fecha==null)return null;
		
		SimpleDateFormat fechaPagoFormat = new SimpleDateFormat();
		fechaPagoFormat.applyPattern("dd/MM/yyyy HH:mm:ss");
		
		return fechaPagoFormat.format(this.fecha);
	}
	
}
