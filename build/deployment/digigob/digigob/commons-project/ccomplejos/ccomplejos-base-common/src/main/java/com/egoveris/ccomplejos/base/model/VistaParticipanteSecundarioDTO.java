package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaParticipanteSecundarioDTO extends AbstractCComplejoDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6427179872503210478L;
	
	
	protected String nombreContacto;
	protected String emailContacto;
	
	public String getNombreContacto() {
		return nombreContacto;
	}
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	public String getEmailContacto() {
		return emailContacto;
	}
	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

	
	
	
}
