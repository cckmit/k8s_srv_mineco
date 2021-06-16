package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.List;

public class DatosLdap implements Serializable {

	private static final long serialVersionUID = -4190906323022423025L;

	private List<String> roles;
	private String apeNombre;

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getApeNombre() {
		return apeNombre;
	}

	public void setApeNombre(String apeNombre) {
		this.apeNombre = apeNombre;
	}
}
