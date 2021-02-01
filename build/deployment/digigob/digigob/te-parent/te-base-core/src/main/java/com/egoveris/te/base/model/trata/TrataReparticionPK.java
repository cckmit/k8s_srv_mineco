package com.egoveris.te.base.model.trata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable

public class TrataReparticionPK implements Serializable {

	private static final long serialVersionUID = -6002997425131983993L;

	@Column(name = "ID_TRATA")
	private Long idTrata;
	
	@Column(name = "orden")
	private int orden;

	public TrataReparticionPK() {
		// Default constructor
	}
	
	public TrataReparticionPK(Long idTrata, int orden) {
		this.idTrata = idTrata;
		this.orden = orden;
	}
	
	public Long getIdTrata() {
		return idTrata;
	}

	public void setIdTrata(Long idTrata) {
		this.idTrata = idTrata;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
}
