package com.egoveris.edt.model.model;

import java.io.Serializable;

public class ReparticionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String codigo;
	private String sectorInterno;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getSectorInterno() {
		return sectorInterno;
	}

	public void setSectorInterno(String sectorInterno) {
		this.sectorInterno = sectorInterno;
	}

}
