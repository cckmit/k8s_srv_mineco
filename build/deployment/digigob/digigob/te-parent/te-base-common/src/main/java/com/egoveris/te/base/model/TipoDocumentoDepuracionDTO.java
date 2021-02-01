package com.egoveris.te.base.model;

import java.io.Serializable;


public class TipoDocumentoDepuracionDTO implements Serializable{

	private static final long serialVersionUID = -213335252860599444L;
	private Long id;
	private String tipoDocAcronimo;
	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocAcronimo() {
		return tipoDocAcronimo;
	}

	public void setTipoDocAcronimo(String tipoDocAcronimo) {
		this.tipoDocAcronimo = tipoDocAcronimo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
