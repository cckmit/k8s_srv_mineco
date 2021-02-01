package com.egoveris.te.base.model.trata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TRATAS_DIFERENCIALES")
public class TrataDiferencial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "CODIGO_TRATA")
	private String codigoTrata;

	@Column(name = "ESTADO")
	private String estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
