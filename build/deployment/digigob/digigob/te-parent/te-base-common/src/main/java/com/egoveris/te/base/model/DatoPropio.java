package com.egoveris.te.base.model;

import java.io.Serializable;

public class DatoPropio implements Serializable {
	
	private static final long serialVersionUID = -7011531928036832345L;
	
	private int id;
	private int grupoId;
	private String nombre;
	private String estado;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getGrupoId() {
		return grupoId;
	}
	
	public void setGrupoId(int grupoId) {
		this.grupoId = grupoId;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
