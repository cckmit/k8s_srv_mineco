package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;

public class UsuarioReducido implements Comparable<UsuarioReducido> , Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3141786539491083385L;

	protected String username;
	protected String nombreApellido;

	public UsuarioReducido() {
	}

	public String getNombreApellido() {
		return nombreApellido;
	}

	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
	@Override
	public String toString() {
		StringBuilder rValue = new StringBuilder();		
		rValue.append(nombreApellido);
		rValue.append(" (");
		rValue.append(username);
		rValue.append(")");
		
		return rValue.toString();
	}

	@Override
	public int compareTo(UsuarioReducido o) {		
		return this.username.compareTo(o.username);
	}

}
