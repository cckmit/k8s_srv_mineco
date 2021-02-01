package com.egoveris.ffdd.model.model;

import java.io.Serializable;

public class CampoBusqueda implements Serializable {

	private static final long serialVersionUID = -7578485839245653334L;
	private String nombre;
	private String etiqueta;
	private Integer relevanciaBusqueda;
	private Integer orden;
	private String mascara;
	private String mensaje;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Integer getRelevanciaBusqueda() {
		return relevanciaBusqueda;
	}

	public void setRelevanciaBusqueda(Integer relevanciaBusqueda) {
		this.relevanciaBusqueda = relevanciaBusqueda;
	}	
	
	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	@Override
	public String toString() {
		return this.etiqueta;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
