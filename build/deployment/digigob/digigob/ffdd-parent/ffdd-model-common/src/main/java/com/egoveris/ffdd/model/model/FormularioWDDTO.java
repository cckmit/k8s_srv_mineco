package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class FormularioWDDTO implements Serializable {

	private static final long serialVersionUID = -8361003554478509940L;

	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Date fechaCreacion;
	private String usuarioCreador;
	private Date fechaModificacion;	
	private String usuarioModificador;
	
	private Set<FormularioComponenteWDDTO> formularioComponentes;
	private List<VisibilidadComponenteDTO> listaComponentesOcultos;

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

	public Set<FormularioComponenteWDDTO> getFormularioComponentes() {
		return formularioComponentes;
	}

	public void setFormularioComponentes(Set<FormularioComponenteWDDTO> formularioComponentes) {
		this.formularioComponentes = formularioComponentes;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
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

	public List<VisibilidadComponenteDTO> getListaComponentesOcultos() {
		return listaComponentesOcultos;
	}

	public void setListaComponentesOcultos(List<VisibilidadComponenteDTO> listaComponentesOcultos) {
		this.listaComponentesOcultos = listaComponentesOcultos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormularioDTO [id=").append(id).append(", nombre=").append(nombre).append(", descripcion=")
				.append(descripcion).append(", fechaCreacion=").append(fechaCreacion).append(", usuarioCreador=")
				.append(usuarioCreador).append(", fechaModificacion=").append(fechaModificacion)
				.append(", usuarioModificador=").append(usuarioModificador).append(", formularioComponentes=")
				.append(formularioComponentes).append(", listaComponentesOcultos=").append(listaComponentesOcultos)
				.append("]");
		return builder.toString();
	}
}