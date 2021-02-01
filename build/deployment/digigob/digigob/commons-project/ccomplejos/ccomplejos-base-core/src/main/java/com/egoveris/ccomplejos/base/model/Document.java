package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_DOCUMENTO")
public class Document extends AbstractCComplejoJPA {

	@Column(name = "ID_DOCUMENTO")
	Long idDocumento;

	@Column(name = "COD_DOCUMENTO")
	String codigoDocumento;

	@Column(name = "NOMBRE_DOCUMENTO")
	String nombreDocumento;

	@Column(name = "TIPO_DOCUMENTO")
	String tipoDocumento;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PARTICIPANTE")
	ParticipanteSecundario participante;

	@Column(name = "PAIS_DOCUMENTO")
	String paisDocumento;

	@Column(name = "FECHA_DOCUMENTO")
	Date fechaDocumento;

	@ManyToOne
	@JoinColumn(name = "ID_ITEM", referencedColumnName = "id")
	ItemJPA item;

	/**
	 * @return the idDocumento
	 */
	public Long getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento
	 *            the idDocumento to set
	 */
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * @return the codigoDocumento
	 */
	public String getCodigoDocumento() {
		return codigoDocumento;
	}

	/**
	 * @param codigoDocumento
	 *            the codigoDocumento to set
	 */
	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	/**
	 * @return the nombreDocumento
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}

	/**
	 * @param nombreDocumento
	 *            the nombreDocumento to set
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the participante
	 */
	public ParticipanteSecundario getParticipante() {
		return participante;
	}

	/**
	 * @param participante
	 *            the participante to set
	 */
	public void setParticipante(ParticipanteSecundario participante) {
		this.participante = participante;
	}

	/**
	 * @return the paisDocumento
	 */
	public String getPaisDocumento() {
		return paisDocumento;
	}

	/**
	 * @param paisDocumento
	 *            the paisDocumento to set
	 */
	public void setPaisDocumento(String paisDocumento) {
		this.paisDocumento = paisDocumento;
	}

	/**
	 * @return the fechaDocumento
	 */
	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	/**
	 * @param fechaDocumento
	 *            the fechaDocumento to set
	 */
	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	/**
	 * @return the item
	 */
	public ItemJPA getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(ItemJPA item) {
		this.item = item;
	}

}
