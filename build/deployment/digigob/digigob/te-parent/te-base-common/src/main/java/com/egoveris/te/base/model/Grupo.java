package com.egoveris.te.base.model;

import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class Grupo {
	
	String reparticion;
	String sector;
	
	Map<Usuario,Integer> asignacionesPorUsuario = new HashMap<Usuario,Integer>();
	
	public String getReparticion() {
		return reparticion;
	}
	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public Map<Usuario, Integer> getAsignacionesPorUsuario() {
		return asignacionesPorUsuario;
	}
	public void setAsignacionesPorUsuario(
			Map<Usuario, Integer> asignacionesPorUsuario) {
		this.asignacionesPorUsuario = asignacionesPorUsuario;
	}
}
