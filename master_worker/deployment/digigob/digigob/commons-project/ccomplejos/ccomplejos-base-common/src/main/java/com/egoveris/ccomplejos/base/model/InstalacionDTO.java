package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class InstalacionDTO extends AbstractCComplejoDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3445654878940814593L;

	String oigPropietaria;
	String nombreInstalacion;
	AddressDTO direccionInstalacion;
	String numeroResolucionAut;
	Date fechaEmisionResolucion;
	String entidadEmisora;
	ParticipantesDTO directorTecnico;
	String tipoInstalacionDestino;
	String codigoInstalacionDestino;
	String telefonoInstalacionDestino;
	String contactoInstalacionDestino;
	String razonSocial;

	/**
	 * @return the oigPropietaria
	 */
	public String getOigPropietaria() {
		return oigPropietaria;
	}
	/**
	 * @param oigPropietaria the oigPropietaria to set
	 */
	public void setOigPropietaria(String oigPropietaria) {
		this.oigPropietaria = oigPropietaria;
	}
	/**
	 * @return the nombreInstalacion
	 */
	public String getNombreInstalacion() {
		return nombreInstalacion;
	}
	/**
	 * @param nombreInstalacion the nombreInstalacion to set
	 */
	public void setNombreInstalacion(String nombreInstalacion) {
		this.nombreInstalacion = nombreInstalacion;
	}
	/**
	 * @return the direccionInstalacion
	 */
	public AddressDTO getDireccionInstalacion() {
		return direccionInstalacion;
	}
	/**
	 * @param direccionInstalacion the direccionInstalacion to set
	 */
	public void setDireccionInstalacion(AddressDTO direccionInstalacion) {
		this.direccionInstalacion = direccionInstalacion;
	}
	/**
	 * @return the numeroResolucionAut
	 */
	public String getNumeroResolucionAut() {
		return numeroResolucionAut;
	}
	/**
	 * @param numeroResolucionAut the numeroResolucionAut to set
	 */
	public void setNumeroResolucionAut(String numeroResolucionAut) {
		this.numeroResolucionAut = numeroResolucionAut;
	}
	/**
	 * @return the fechaEmisionResolucion
	 */
	public Date getFechaEmisionResolucion() {
		return fechaEmisionResolucion;
	}
	/**
	 * @param fechaEmisionResolucion the fechaEmisionResolucion to set
	 */
	public void setFechaEmisionResolucion(Date fechaEmisionResolucion) {
		this.fechaEmisionResolucion = fechaEmisionResolucion;
	}
	/**
	 * @return the entidadEmisora
	 */
	public String getEntidadEmisora() {
		return entidadEmisora;
	}
	/**
	 * @param entidadEmisora the entidadEmisora to set
	 */
	public void setEntidadEmisora(String entidadEmisora) {
		this.entidadEmisora = entidadEmisora;
	}
	/**
	 * @return the directorTecnico
	 */
	public ParticipantesDTO getDirectorTecnico() {
		return directorTecnico;
	}
	/**
	 * @param directorTecnico the directorTecnico to set
	 */
	public void setDirectorTecnico(ParticipantesDTO directorTecnico) {
		this.directorTecnico = directorTecnico;
	}
	/**
	 * @return the tipoInstalacionDestino
	 */
	public String getTipoInstalacionDestino() {
		return tipoInstalacionDestino;
	}
	/**
	 * @param tipoInstalacionDestino the tipoInstalacionDestino to set
	 */
	public void setTipoInstalacionDestino(String tipoInstalacionDestino) {
		this.tipoInstalacionDestino = tipoInstalacionDestino;
	}
	/**
	 * @return the codigoInstalacionDestino
	 */
	public String getCodigoInstalacionDestino() {
		return codigoInstalacionDestino;
	}
	/**
	 * @param codigoInstalacionDestino the codigoInstalacionDestino to set
	 */
	public void setCodigoInstalacionDestino(String codigoInstalacionDestino) {
		this.codigoInstalacionDestino = codigoInstalacionDestino;
	}
	/**
	 * @return the telefonoInstalacionDestino
	 */
	public String getTelefonoInstalacionDestino() {
		return telefonoInstalacionDestino;
	}
	/**
	 * @param telefonoInstalacionDestino the telefonoInstalacionDestino to set
	 */
	public void setTelefonoInstalacionDestino(String telefonoInstalacionDestino) {
		this.telefonoInstalacionDestino = telefonoInstalacionDestino;
	}
	/**
	 * @return the contactoInstalacionDestino
	 */
	public String getContactoInstalacionDestino() {
		return contactoInstalacionDestino;
	}
	/**
	 * @param contactoInstalacionDestino the contactoInstalacionDestino to set
	 */
	public void setContactoInstalacionDestino(String contactoInstalacionDestino) {
		this.contactoInstalacionDestino = contactoInstalacionDestino;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
}
