package com.egoveris.te.model.model;

import java.io.Serializable;

public class DTODatosCaratula implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombreSolicitante;
	private String apellidoSolicitante;
	private String razonSocialSolicitante;
	private String tipoDocumento;
	private String numeroDocumento;
	private Boolean esPersonaJuridica; 
	private String numeroSadeDocumentoCaratula;
	
	private String segundoNombreSolicitante;
	private String segundoApellidoSolicitante;
	
	private String tercerNombreSolicitante;
	private String tercerApellidoSolicitante;
	
	private String cuitCuil;
	
	private String domicilio;
	private String piso;
	private String departamento;
	private String codigoPostal;
	
	private String telefono;
	private String mail;
	
	public DTODatosCaratula() {
		
	}

	public DTODatosCaratula(String nombreSolicitante, 
			String apellidoSolicitante,
			String razonSocialSolicitante,
			String tipoDocumento,
			String numeroDocumento,
			Boolean esPersonaJuridica,
			String numeroSadeDocumentoCaratula) {
		
		this.nombreSolicitante = nombreSolicitante;
		this.apellidoSolicitante = apellidoSolicitante;
		this.razonSocialSolicitante = razonSocialSolicitante;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.esPersonaJuridica = esPersonaJuridica;
		this.numeroSadeDocumentoCaratula = numeroSadeDocumentoCaratula;
	}

	public DTODatosCaratula(
			String nombreSolicitante,
			String segundoNombreSolicitante, 
			String tercerNombreSolicitante, 
			String apellidoSolicitante, 
			String segundoApellidoSolicitante,
			String tercerApellidoSolicitante,
			String razonSocialSolicitante,
			String tipoDocumento, 
			String numeroDocumento,
			String cuitCuil, 
			String domicilio, 
			String piso,
			String departamento, 
			String codigoPostal, 
			String telefono,
			String mail, 
			Boolean esPersonaJuridica, 
			String numeroSadeDocumentoCaratula) {
		this.nombreSolicitante = nombreSolicitante;
		this.apellidoSolicitante = apellidoSolicitante;
		this.razonSocialSolicitante = razonSocialSolicitante;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.esPersonaJuridica = esPersonaJuridica;
		this.numeroSadeDocumentoCaratula = numeroSadeDocumentoCaratula;
		this.segundoNombreSolicitante = segundoNombreSolicitante;
		this.segundoApellidoSolicitante = segundoApellidoSolicitante;
		this.tercerNombreSolicitante = tercerNombreSolicitante;
		this.tercerApellidoSolicitante = tercerApellidoSolicitante;
		this.cuitCuil = cuitCuil;
		this.domicilio = domicilio;
		this.piso = piso;
		this.departamento = departamento;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.mail = mail;
	}

	public String getNombreSolicitante() {
		return this.nombreSolicitante;
	}

	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	public String getApellidoSolicitante() {
		return apellidoSolicitante;
	}

	public void setApellidoSolicitante(String apellidoSolicitante) {
		this.apellidoSolicitante = apellidoSolicitante;
	}

	public String getRazonSocialSolicitante() {
		return this.razonSocialSolicitante;
	}

	public void setRazonSocialSolicitante(String razonSocialSolicitante) {
		this.razonSocialSolicitante = razonSocialSolicitante;
	}

	public String getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroSadeDocumentoCaratula() {
		return this.numeroSadeDocumentoCaratula;
	}

	public void setNumeroSadeDocumentoCaratula(
			String numeroSadeDocumentoCaratula) {
		this.numeroSadeDocumentoCaratula = numeroSadeDocumentoCaratula;
	}

	public Boolean getEsPersonaJuridica() {
		return this.esPersonaJuridica;
	}

	public void setEsPersonaJuridica(Boolean esPersonaJuridica) {
		this.esPersonaJuridica = esPersonaJuridica;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");

		sb
		.append("DTODatosCaratula [")
		.append((nombreSolicitante != null ? "nombreSolicitante=" + nombreSolicitante + ", " : ""))
		.append((apellidoSolicitante != null ? "apellidoSolicitante=" + apellidoSolicitante + ", " : ""))
		
		.append((apellidoSolicitante != null ? "segundoApellidoSolicitante=" + segundoApellidoSolicitante + ", " : ""))
		.append((apellidoSolicitante != null ? "tercerApellidoSolicitante=" + tercerApellidoSolicitante + ", " : ""))
		
		.append((nombreSolicitante != null ? "segundoNombreSolicitante=" + segundoNombreSolicitante + ", " : ""))
		.append((nombreSolicitante != null ? "tercerNombreSolicitante=" + tercerNombreSolicitante + ", " : ""))
		
		.append((apellidoSolicitante != null ? "cuitCuil=" + cuitCuil + ", " : ""))
		
		.append((razonSocialSolicitante != null ? "razonSocialSolicitante=" + razonSocialSolicitante + ", " : ""))
		.append((tipoDocumento != null ? "tipoDocumento=" + tipoDocumento + ", " : ""))
		.append((numeroDocumento != null ? "numeroDocumento=" + numeroDocumento + ", " : ""))		
		.append( "esPersonaJuridica=" ) 
		.append( esPersonaJuridica )
		.append( ", " )
		
		.append((numeroSadeDocumentoCaratula != null ? "numeroSadeDocumentoCaratula=" + numeroSadeDocumentoCaratula + ", " : ""))
		
		.append((domicilio != null ? "domicilio=" + domicilio + ", " : ""))
		.append((piso != null ? "piso=" + piso + ", " : ""))
		.append((departamento != null ? "departamento=" + departamento + ", " : ""))
		.append((codigoPostal != null ? "codigoPostal=" + codigoPostal + ", " : ""))
		
		.append((telefono != null ? "telefono=" + telefono + ", " : ""))
		.append((mail != null ? "mail=" + mail + ", " : ""))
		
		.append("]");

		return sb.toString();
	}

	public void setSegundoNombreSolicitante(String segundoNombreSolicitante) {
		this.segundoNombreSolicitante = segundoNombreSolicitante;
	}

	public String getSegundoNombreSolicitante() {
		return segundoNombreSolicitante;
	}

	public void setSegundoApellidoSolicitante(String segundoApellidoSolicitante) {
		this.segundoApellidoSolicitante = segundoApellidoSolicitante;
	}

	public String getSegundoApellidoSolicitante() {
		return segundoApellidoSolicitante;
	}

	public void setTercerNombreSolicitante(String tercerNombreSolicitante) {
		this.tercerNombreSolicitante = tercerNombreSolicitante;
	}

	public String getTercerNombreSolicitante() {
		return tercerNombreSolicitante;
	}

	public void setTercerApellidoSolicitante(String tercerApellidoSolicitante) {
		this.tercerApellidoSolicitante = tercerApellidoSolicitante;
	}

	public String getTercerApellidoSolicitante() {
		return tercerApellidoSolicitante;
	}

	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}

	public String getCuitCuil() {
		return cuitCuil;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPiso() {
		return piso;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}

}
