package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;


public class DatosUsuarioRolIdDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2113770180833864898L;

	private DatosUsuarioDTO datosUsuario;
	
	private RolDTO rol;

	
	
	public DatosUsuarioRolIdDTO(DatosUsuarioDTO datosUsuario, RolDTO rol) {
		super();
		this.datosUsuario = datosUsuario;
		this.rol = rol;
	}
	
	public DatosUsuarioRolIdDTO() {
		super();
	}

	public DatosUsuarioDTO getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(DatosUsuarioDTO datosUsuario) {
		this.datosUsuario = datosUsuario;
	}

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datosUsuario == null) ? 0 : datosUsuario.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosUsuarioRolIdDTO other = (DatosUsuarioRolIdDTO) obj;
		if (datosUsuario == null) {
			if (other.datosUsuario != null)
				return false;
		} else if (!datosUsuario.equals(other.datosUsuario))
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		return true;
	}


}
