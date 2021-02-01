package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class UsuarioDTO extends AbstractCComplejoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	String usuario;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
