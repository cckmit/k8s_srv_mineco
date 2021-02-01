package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class DocumentoTransporteDTO extends AbstractCComplejoDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3658475616806802171L;

	Long idDocumentoTransporte;

	Long secuencialDocTransporte;

	Long numeroDocTransporte;

	Date fechaDocTransporte;

	String tipoDocTransporte;

	String nombreNave;

	Long numeroViaje;

	String numeroManifiesto;

	String idNave;
	
	/**
	 * @return the idDocumentoTransporte
	 */
	public Long getIdDocumentoTransporte() {
		return idDocumentoTransporte;
	}
	/**
	 * @param idDocumentoTransporte the idDocumentoTransporte to set
	 */
	public void setIdDocumentoTransporte(Long idDocumentoTransporte) {
		this.idDocumentoTransporte = idDocumentoTransporte;
	}
	/**
	 * @return the secuancialDocTransporte
	 */
	public Long getSecuencialDocTransporte() {
		return secuencialDocTransporte;
	}
	/**
	 * @param secuancialDocTransporte the secuancialDocTransporte to set
	 */
	public void setSecuencialDocTransporte(Long secuencialDocTransporte) {
		this.secuencialDocTransporte = secuencialDocTransporte;
	}
	/**
	 * @return the numeroDocTransporte
	 */
	public Long getNumeroDocTransporte() {
		return numeroDocTransporte;
	}
	/**
	 * @param numeroDocTransporte the numeroDocTransporte to set
	 */
	public void setNumeroDocTransporte(Long numeroDocTransporte) {
		this.numeroDocTransporte = numeroDocTransporte;
	}
	/**
	 * @return the fechaDocTransporte
	 */
	public Date getFechaDocTransporte() {
		return fechaDocTransporte;
	}
	/**
	 * @param fechaDocTransporte the fechaDocTransporte to set
	 */
	public void setFechaDocTransporte(Date fechaDocTransporte) {
		this.fechaDocTransporte = fechaDocTransporte;
	}
	/**
	 * @return the tipoDocTransporte
	 */
	public String getTipoDocTransporte() {
		return tipoDocTransporte;
	}
	/**
	 * @param tipoDocTransporte the tipoDocTransporte to set
	 */
	public void setTipoDocTransporte(String tipoDocTransporte) {
		this.tipoDocTransporte = tipoDocTransporte;
	}
	/**
	 * @return the nombreNave
	 */
	public String getNombreNave() {
		return nombreNave;
	}
	/**
	 * @param nombreNave the nombreNave to set
	 */
	public void setNombreNave(String nombreNave) {
		this.nombreNave = nombreNave;
	}
	/**
	 * @return the numeroViaje
	 */
	public Long getNumeroViaje() {
		return numeroViaje;
	}
	/**
	 * @param numeroViaje the numeroViaje to set
	 */
	public void setNumeroViaje(Long numeroViaje) {
		this.numeroViaje = numeroViaje;
	}
	/**
	 * @return the numeroManifiesto
	 */
	public String getNumeroManifiesto() {
		return numeroManifiesto;
	}
	/**
	 * @param numeroManifiesto the numeroManifiesto to set
	 */
	public void setNumeroManifiesto(String numeroManifiesto) {
		this.numeroManifiesto = numeroManifiesto;
	}
	/**
	 * @return the idNave
	 */
	public String getIdNave() {
		return idNave;
	}
	/**
	 * @param idNave the idNave to set
	 */
	public void setIdNave(String idNave) {
		this.idNave = idNave;
	}
	
}
