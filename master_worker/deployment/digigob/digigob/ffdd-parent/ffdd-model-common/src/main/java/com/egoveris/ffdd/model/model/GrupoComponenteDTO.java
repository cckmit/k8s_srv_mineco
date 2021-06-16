package com.egoveris.ffdd.model.model;

import java.io.Serializable;

public class GrupoComponenteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1185636416891538627L;
	
	private Integer id;
	private String nombre;
	private String etiqueta;
	private Integer orden;
	private ComponenteDTO componente;
	private GrupoDTO grupo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public ComponenteDTO getComponente() {
		return componente;
	}
	public void setComponente(ComponenteDTO componente) {
		this.componente = componente;
	}
	public GrupoDTO getGrupo() {
		return grupo;
	}
	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public Integer getOrden() {
		return orden;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GrupoComponenteDTO [id=").append(id).append(", nombre=").append(nombre).append(", etiqueta=")
				.append(etiqueta).append(", orden=").append(orden).append(", componente=").append(componente)
				.append(", grupo=").append(grupo).append("]");
		return builder.toString();
	}
}
