package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class FamiliaTipoDocumentoIDDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8191339399831613857L;
	private Integer id ;
	private String nombre ;
	private String descripcion ;
	private Set<TipoDocumentoIDDTO> listaTipoDocumento = new HashSet<TipoDocumentoIDDTO>();
	
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
	public Set<TipoDocumentoIDDTO> getListaTipoDocumento() {
		return listaTipoDocumento;
	}
	public void setListaTipoDocumento(Set<TipoDocumentoIDDTO> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}
	
	
	
}
