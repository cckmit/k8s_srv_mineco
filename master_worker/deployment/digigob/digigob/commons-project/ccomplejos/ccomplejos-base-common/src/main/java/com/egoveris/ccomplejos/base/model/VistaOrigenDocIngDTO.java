package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VistaOrigenDocIngDTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2122435726306886817L;
	
	protected String viaDeTransporte;
	protected String codigoPuertoDeEmbarque;
	protected String codigoPaisProcedencia;
	protected String codigoPuertoDesembarque;
	protected Date fechaEmbarque;
	protected Date fechaDesembarque;
	protected ManifiestoDTO manifiesto;
	protected String nombreNave;
	protected String codigoPaisOrigen;
	protected String nombreCompaniaTransporte;
	protected String numeroDocumentoTransporte;
	protected String fechaDocumentoTransporte;
	protected List<VistaDetallesPuertoDADTO> transbordo;
	protected List<VistaParticipantesDaDTO> emisor;

	/**
	 * @return the viaDeTransporte
	 */
	public String getViaDeTransporte() {
		return viaDeTransporte;
	}

	/**
	 * @param viaDeTransporte
	 *            the viaDeTransporte to set
	 */
	public void setViaDeTransporte(String viaDeTransporte) {
		this.viaDeTransporte = viaDeTransporte;
	}

	/**
	 * @return the codigoPuertoDeEmbarque
	 */
	public String getCodigoPuertoDeEmbarque() {
		return codigoPuertoDeEmbarque;
	}

	/**
	 * @param codigoPuertoDeEmbarque
	 *            the codigoPuertoDeEmbarque to set
	 */
	public void setCodigoPuertoDeEmbarque(String codigoPuertoDeEmbarque) {
		this.codigoPuertoDeEmbarque = codigoPuertoDeEmbarque;
	}

	/**
	 * @return the codigoPaisProcedencia
	 */
	public String getCodigoPaisProcedencia() {
		return codigoPaisProcedencia;
	}

	/**
	 * @param codigoPaisProcedencia
	 *            the codigoPaisProcedencia to set
	 */
	public void setCodigoPaisProcedencia(String codigoPaisProcedencia) {
		this.codigoPaisProcedencia = codigoPaisProcedencia;
	}

	/**
	 * @return the codigoPuertoDesembarque
	 */
	public String getCodigoPuertoDesembarque() {
		return codigoPuertoDesembarque;
	}

	/**
	 * @param codigoPuertoDesembarque
	 *            the codigoPuertoDesembarque to set
	 */
	public void setCodigoPuertoDesembarque(String codigoPuertoDesembarque) {
		this.codigoPuertoDesembarque = codigoPuertoDesembarque;
	}

	/**
	 * @return the fechaEmbarque
	 */
	public Date getFechaEmbarque() {
		return fechaEmbarque;
	}

	/**
	 * @param fechaEmbarque
	 *            the fechaEmbarque to set
	 */
	public void setFechaEmbarque(Date fechaEmbarque) {
		this.fechaEmbarque = fechaEmbarque;
	}

	/**
	 * @return the fechaDesembarque
	 */
	public Date getFechaDesembarque() {
		return fechaDesembarque;
	}

	/**
	 * @param fechaDesembarque
	 *            the fechaDesembarque to set
	 */
	public void setFechaDesembarque(Date fechaDesembarque) {
		this.fechaDesembarque = fechaDesembarque;
	}

	/**
	 * @return the manifiesto
	 */
	public ManifiestoDTO getManifiesto() {
		return manifiesto;
	}

	/**
	 * @param manifiesto
	 *            the manifiesto to set
	 */
	public void setManifiesto(ManifiestoDTO manifiesto) {
		this.manifiesto = manifiesto;
	}

	/**
	 * @return the nombreNave
	 */
	public String getNombreNave() {
		return nombreNave;
	}

	/**
	 * @param nombreNave
	 *            the nombreNave to set
	 */
	public void setNombreNave(String nombreNave) {
		this.nombreNave = nombreNave;
	}

	/**
	 * @return the codigoPaisOrigen
	 */
	public String getCodigoPaisOrigen() {
		return codigoPaisOrigen;
	}

	/**
	 * @param codigoPaisOrigen
	 *            the codigoPaisOrigen to set
	 */
	public void setCodigoPaisOrigen(String codigoPaisOrigen) {
		this.codigoPaisOrigen = codigoPaisOrigen;
	}

	/**
	 * @return the nombreCompaniaTransporte
	 */
	public String getNombreCompaniaTransporte() {
		return nombreCompaniaTransporte;
	}

	/**
	 * @param nombreCompaniaTransporte
	 *            the nombreCompaniaTransporte to set
	 */
	public void setNombreCompaniaTransporte(String nombreCompaniaTransporte) {
		this.nombreCompaniaTransporte = nombreCompaniaTransporte;
	}

	/**
	 * @return the numeroDocumentoTransporte
	 */
	public String getNumeroDocumentoTransporte() {
		return numeroDocumentoTransporte;
	}

	/**
	 * @param numeroDocumentoTransporte
	 *            the numeroDocumentoTransporte to set
	 */
	public void setNumeroDocumentoTransporte(String numeroDocumentoTransporte) {
		this.numeroDocumentoTransporte = numeroDocumentoTransporte;
	}

	/**
	 * @return the fechaDocumentoTransporte
	 */
	public String getFechaDocumentoTransporte() {
		return fechaDocumentoTransporte;
	}

	/**
	 * @param fechaDocumentoTransporte
	 *            the fechaDocumentoTransporte to set
	 */
	public void setFechaDocumentoTransporte(String fechaDocumentoTransporte) {
		this.fechaDocumentoTransporte = fechaDocumentoTransporte;
	}

	/**
	 * @return the transbordo
	 */
	public List<VistaDetallesPuertoDADTO> getTransbordo() {
		return transbordo;
	}

	/**
	 * @param transbordo
	 *            the transbordo to set
	 */
	public void setTransbordo(List<VistaDetallesPuertoDADTO> transbordo) {
		this.transbordo = transbordo;
	}

	/**
	 * @return the emisor
	 */
	public List<VistaParticipantesDaDTO> getEmisor() {
		return emisor;
	}

	/**
	 * @param emisor
	 *            the emisor to set
	 */
	public void setEmisor(List<VistaParticipantesDaDTO> emisor) {
		this.emisor = emisor;
	}



}