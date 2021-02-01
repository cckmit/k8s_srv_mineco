package com.egoveris.deo.model.model;

import java.io.Serializable;

public class UsuarioReservadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3158319433005539514L;
	private Integer idDocumento;
	private String userName;
	
	
	public Integer getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
