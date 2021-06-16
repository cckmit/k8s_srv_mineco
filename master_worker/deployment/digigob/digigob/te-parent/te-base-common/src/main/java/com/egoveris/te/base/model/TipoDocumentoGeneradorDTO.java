package com.egoveris.te.base.model;

import java.io.Serializable;
/**
 * 
 * @author lfishkel
 *
 */
public class TipoDocumentoGeneradorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2206386850577693002L;
	
	private Long id;
	private String tipoDocGenerador;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoDocGenerador() {
		return tipoDocGenerador;
	}
	public void setTipoDocGenerador(String tipoDocGenerador) {
		this.tipoDocGenerador = tipoDocGenerador;
	}
	
	
}
