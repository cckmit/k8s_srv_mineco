package com.egoveris.te.model.model;

import java.io.Serializable;

/**
 * Objeto request para hacer definitivos todos los documentos de un Expediente Electronico
 * @author joflores
 *
 */
public class VinculacionDefinitivaDeDocsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5847515932055269685L;

	
	
	private String sistemaUsuario;
	private String usuario;
	private String codigoEE;
	public String getSistemaUsuario() {
		return sistemaUsuario;
	}
	public void setSistemaUsuario(String sistemaUsuario) {
		this.sistemaUsuario = sistemaUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCodigoEE() {
		return codigoEE;
	}
	public void setCodigoEE(String codigoEE) {
		this.codigoEE = codigoEE;
	}
	
	
	
	
	
	
	
}
