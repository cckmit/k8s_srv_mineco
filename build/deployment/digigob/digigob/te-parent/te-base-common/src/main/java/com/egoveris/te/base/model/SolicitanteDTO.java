package com.egoveris.te.base.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Solicitante del inicio de un expediente.  
 * 
 * @author rgalloci
 * 
 */

public class SolicitanteDTO implements Serializable{
	private static final Logger logger = LoggerFactory.getLogger(SolicitanteDTO.class);

	private static final long serialVersionUID = 2977983441580077390L;
	private Integer id;
	private String email;
	private String telefono;
	private String nombreSolicitante;
	private String segundoNombreSolicitante;
	private String tercerNombreSolicitante;
	private String apellidoSolicitante;
	private String segundoApellidoSolicitante;
	private String tercerApellidoSolicitante;
	private String razonSocialSolicitante;
	private String cuitCuil;
	
	private String domicilio;
	private String piso;
	private String departamento;
	private String codigoPostal;
//	private String barrio;
//	private String comuna;
	
	private DocumentoDeIdentidadDTO documento;
	
	public SolicitanteDTO(){
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRazonSocialSolicitante() {
		return razonSocialSolicitante;
	}

	public void setRazonSocialSolicitante(String razonSocialSolicitante) {
		this.razonSocialSolicitante = razonSocialSolicitante;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public DocumentoDeIdentidadDTO getDocumento() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumento() - start");
		}

		if(documento==null)
		{
			documento = new DocumentoDeIdentidadDTO();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDocumento() - end - return value={}", documento);
		}
		return documento;
	}

	public void setDocumento(DocumentoDeIdentidadDTO documento) {
		this.documento = documento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((apellidoSolicitante == null) ? 0 : apellidoSolicitante
						.hashCode());
		result = prime * result
				+ ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime
				* result
				+ ((nombreSolicitante == null) ? 0 : nombreSolicitante
						.hashCode());
		result = prime
				* result
				+ ((razonSocialSolicitante == null) ? 0
						: razonSocialSolicitante.hashCode());
		result = prime * result
				+ ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitanteDTO other = (SolicitanteDTO) obj;
		if (apellidoSolicitante == null) {
			if (other.apellidoSolicitante != null)
				return false;
		} else if (!apellidoSolicitante.equals(other.apellidoSolicitante))
			return false;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nombreSolicitante == null) {
			if (other.nombreSolicitante != null)
				return false;
		} else if (!nombreSolicitante.equals(other.nombreSolicitante))
			return false;
		if (razonSocialSolicitante == null) {
			if (other.razonSocialSolicitante != null)
				return false;
		} else if (!razonSocialSolicitante.equals(other.razonSocialSolicitante))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}
	
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	
	public void setSegundoNombreSolicitante(String segundoNombreSolicitante) {
		this.segundoNombreSolicitante = segundoNombreSolicitante;
	}

	public String getSegundoNombreSolicitante() {
		return segundoNombreSolicitante;
	}

	public void setTercerNombreSolicitante(String tercerNombreSolicitante) {
		this.tercerNombreSolicitante = tercerNombreSolicitante;
	}

	public String getTercerNombreSolicitante() {
		return tercerNombreSolicitante;
	}
	
	public void setApellidoSolicitante(String apellidoSolicitante) {
		this.apellidoSolicitante = apellidoSolicitante;
	}

	public String getApellidoSolicitante() {
		return apellidoSolicitante;
	}

	public void setSegundoApellidoSolicitante(String segundoApellidoSolicitante) {
		this.segundoApellidoSolicitante = segundoApellidoSolicitante;
	}

	public String getSegundoApellidoSolicitante() {
		return segundoApellidoSolicitante;
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

//	public String getBarrio() {
//		return barrio;
//	}

//	public void setBarrio(String barrio) {
//		this.barrio = barrio;
//	}

//	public String getComuna() {
//		return comuna;
//	}

//	public void setComuna(String comuna) {
//		this.comuna = comuna;
//	}

	
}
