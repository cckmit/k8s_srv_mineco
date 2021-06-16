package com.egoveris.te.base.model.tipo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TIPO_DOCUMENTO_DEPURACION")
public class TipoDocumentoDepuracion implements Serializable{

	private static final long serialVersionUID = -213335252860599444L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "TIPO_DOC_ACRONIMO")
	private String tipoDocAcronimo;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;

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
	 * @return the tipoDocAcronimo
	 */
	public String getTipoDocAcronimo() {
		return tipoDocAcronimo;
	}

	/**
	 * @param tipoDocAcronimo the tipoDocAcronimo to set
	 */
	public void setTipoDocAcronimo(String tipoDocAcronimo) {
		this.tipoDocAcronimo = tipoDocAcronimo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
}
