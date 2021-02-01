package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class DatosUsuarioRolId implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7103950429957477826L;

	@ManyToOne(fetch = FetchType.LAZY)
	private DatosUsuario datosUsuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Rol rol;

	
	
	public DatosUsuarioRolId(DatosUsuario datosUsuario, Rol rol) {
		super();
		this.datosUsuario = datosUsuario;
		this.rol = rol;
	}
	
	public DatosUsuarioRolId() {
		super();
	}

	public DatosUsuario getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(DatosUsuario datosUsuario) {
		this.datosUsuario = datosUsuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	
}
