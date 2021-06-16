package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_DOCUMENTO_APOYO_DA")
public class VistaDocumentoApoyoDA extends AbstractCComplejoJPA {

	@Column(name = "DESCRIPCION")
	String descripcion;
	@Column(name = "NOMBRE_DOCUMENTO")
	String nombreDocumento;
	@Column(name = "NOMBRE_EMISOR")
	String nombreEmisor;
	@Column(name = "TIPO_DOCUMENTO_ADJUNTO")
	String tipoDocumentoAdjunto;
	@Column(name = "SECUENCIA_DOCUMENTO")
	Integer secuenciaDocumento;
	@Column(name = "NUMERO_DOCUMENTO")
	Integer numeroDocumento;
	@Column(name = "FECHA_DOCUMENTO")
	Date fechaDocumento;
	@Column(name = "ADJUNTO")
	byte adjunto;
	@ManyToOne
	@JoinColumn(name = "NUMERO_DOCUMENTO", referencedColumnName = "id", insertable = false, updatable = false)
	VistaItemDA vistaDocumento;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getNombreEmisor() {
		return nombreEmisor;
	}

	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}

	public String getTipoDocumentoAdjunto() {
		return tipoDocumentoAdjunto;
	}

	public void setTipoDocumentoAdjunto(String tipoDocumentoAdjunto) {
		this.tipoDocumentoAdjunto = tipoDocumentoAdjunto;
	}

	public Integer getSecuenciaDocumento() {
		return secuenciaDocumento;
	}

	public void setSecuenciaDocumento(Integer secuenciaDocumento) {
		this.secuenciaDocumento = secuenciaDocumento;
	}

	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public byte getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(byte adjunto) {
		this.adjunto = adjunto;
	}

	/**
	 * @return the vistaDocumento
	 */
	public VistaItemDA getVistaDocumento() {
		return vistaDocumento;
	}

	/**
	 * @param vistaDocumento
	 *            the vistaDocumento to set
	 */
	public void setVistaDocumento(VistaItemDA vistaDocumento) {
		this.vistaDocumento = vistaDocumento;
	}

}
