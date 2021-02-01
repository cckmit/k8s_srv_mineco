package com.egoveris.te.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Documento para la identificacion de una persona fisica
 * o juridica.
 * 
 * @author rgalloci
 *
 */

@Entity
@Table(name="DOCUMENTO_DE_IDENTIDAD")
public class DocumentoDeIdentidad implements Serializable{
	private static final long serialVersionUID = -3216817270662213059L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name="NUMERO_DOCUMENTO")
	private String numeroDocumento;
	
	@Column(name="TIPO_DOCUMENTO_POSIBLE")
	private String tipoDocumentoPosible;
	
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
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * @return the tipoDocumentoPosible
	 */
	public String getTipoDocumentoPosible() {
		return tipoDocumentoPosible;
	}

	/**
	 * @param tipoDocumentoPosible the tipoDocumentoPosible to set
	 */
	public void setTipoDocumentoPosible(String tipoDocumentoPosible) {
		this.tipoDocumentoPosible = tipoDocumentoPosible;
	}

}
