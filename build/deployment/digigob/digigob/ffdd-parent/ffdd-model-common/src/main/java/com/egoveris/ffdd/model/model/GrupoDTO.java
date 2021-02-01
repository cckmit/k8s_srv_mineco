package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class GrupoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2871313754117973385L;
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaModificacion;	
	private String usuarioModificador;
	private String usuarioCreador;
	private Set<GrupoComponenteDTO> grupoComponentes;
	
	
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getUsuarioModificador() {
		return usuarioModificador;
	}
	public void setUsuarioModificador(String usuarioModificador) {
		this.usuarioModificador = usuarioModificador;
	}
	public String getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	public Set<GrupoComponenteDTO> getGrupoComponentes() {
		return grupoComponentes;
	}
	public void setGrupoComponentes(Set<GrupoComponenteDTO> grupoComponentes) {
		this.grupoComponentes = grupoComponentes;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GrupoDTO [id=").append(id).append(", nombre=").append(nombre).append(", descripcion=")
				.append(descripcion).append(", fechaCreacion=").append(fechaCreacion).append(", fechaModificacion=")
				.append(fechaModificacion).append(", usuarioModificador=").append(usuarioModificador)
				.append(", usuarioCreador=").append(usuarioCreador).append(", grupoComponentes=")
				.append(grupoComponentes).append("]");
		return builder.toString();
	}
	
}
