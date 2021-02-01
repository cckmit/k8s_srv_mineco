package com.egoveris.te.base.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Solicitante del inicio de un expediente.  
 * 
 * @author rgalloci
 * 
 */
@Entity
@Table(name="SOLICITANTE")
public class Solicitante implements Serializable{
	private static final long serialVersionUID = 2977983441580077390L;
	
	@Id
	@Column(name="ID_SOLICITANTE")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ID_DOCUMENTO")
	private DocumentoDeIdentidad documento;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="TELEFONO")
	private String telefono;
	
	@Column(name="NOMBRE_SOLICITANTE")
	private String nombreSolicitante;
	
	@Column(name="SEGUNDO_NOMBRE_SOLICITANTE")
	private String segundoNombreSolicitante;
	
	@Column(name="TERCER_NOMBRE_SOLICITANTE")
	private String tercerNombreSolicitante;
	
	@Column(name="APELLIDO_SOLICITANTE")
	private String apellidoSolicitante;
	
	@Column(name="SEGUNDO_APELLIDO_SOLICITANTE")
	private String segundoApellidoSolicitante;
	
	@Column(name="TERCER_APELLIDO_SOLICITANTE")
	private String tercerApellidoSolicitante;
	
	@Column(name="RAZON_SOCIAL_SOLICITANTE")
	private String razonSocialSolicitante;
	
	@Column(name="CUIT_CUIL")
	private String cuitCuil;  
	
	@Column(name="DOMICILIO")
	private String domicilio;
	
	@Column(name="PISO")
	private String piso;
	
	@Column(name="DEPARTAMENTO")
	private String departamento;
	
	@Column(name="CODIGO_POSTAL")
	private String codigoPostal;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the documento
	 */
	public DocumentoDeIdentidad getDocumento() {
		return documento;
	}

	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(DocumentoDeIdentidad documento) {
		this.documento = documento;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the nombreSolicitante
	 */
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}

	/**
	 * @param nombreSolicitante the nombreSolicitante to set
	 */
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	/**
	 * @return the segundoNombreSolicitante
	 */
	public String getSegundoNombreSolicitante() {
		return segundoNombreSolicitante;
	}

	/**
	 * @param segundoNombreSolicitante the segundoNombreSolicitante to set
	 */
	public void setSegundoNombreSolicitante(String segundoNombreSolicitante) {
		this.segundoNombreSolicitante = segundoNombreSolicitante;
	}

	/**
	 * @return the tercerNombreSolicitante
	 */
	public String getTercerNombreSolicitante() {
		return tercerNombreSolicitante;
	}

	/**
	 * @param tercerNombreSolicitante the tercerNombreSolicitante to set
	 */
	public void setTercerNombreSolicitante(String tercerNombreSolicitante) {
		this.tercerNombreSolicitante = tercerNombreSolicitante;
	}

	/**
	 * @return the apellidoSolicitante
	 */
	public String getApellidoSolicitante() {
		return apellidoSolicitante;
	}

	/**
	 * @param apellidoSolicitante the apellidoSolicitante to set
	 */
	public void setApellidoSolicitante(String apellidoSolicitante) {
		this.apellidoSolicitante = apellidoSolicitante;
	}

	/**
	 * @return the segundoApellidoSolicitante
	 */
	public String getSegundoApellidoSolicitante() {
		return segundoApellidoSolicitante;
	}

	/**
	 * @param segundoApellidoSolicitante the segundoApellidoSolicitante to set
	 */
	public void setSegundoApellidoSolicitante(String segundoApellidoSolicitante) {
		this.segundoApellidoSolicitante = segundoApellidoSolicitante;
	}

	/**
	 * @return the tercerApellidoSolicitante
	 */
	public String getTercerApellidoSolicitante() {
		return tercerApellidoSolicitante;
	}

	/**
	 * @param tercerApellidoSolicitante the tercerApellidoSolicitante to set
	 */
	public void setTercerApellidoSolicitante(String tercerApellidoSolicitante) {
		this.tercerApellidoSolicitante = tercerApellidoSolicitante;
	}

	/**
	 * @return the razonSocialSolicitante
	 */
	public String getRazonSocialSolicitante() {
		return razonSocialSolicitante;
	}

	/**
	 * @param razonSocialSolicitante the razonSocialSolicitante to set
	 */
	public void setRazonSocialSolicitante(String razonSocialSolicitante) {
		this.razonSocialSolicitante = razonSocialSolicitante;
	}

	/**
	 * @return the cuitCuil
	 */
	public String getCuitCuil() {
		return cuitCuil;
	}

	/**
	 * @param cuitCuil the cuitCuil to set
	 */
	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}

	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * @param domicilio the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * @return the piso
	 */
	public String getPiso() {
		return piso;
	}

	/**
	 * @param piso the piso to set
	 */
	public void setPiso(String piso) {
		this.piso = piso;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

}
