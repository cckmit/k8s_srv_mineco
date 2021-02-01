package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;





public class FamiliaTipoDocumentoDTO implements Serializable {

	
	/**
   * 
   */
  private static final long serialVersionUID = -4586260907450443873L;
  /**
	 * 
	 */
	private Integer id ;
	private String nombre ;
	private String descripcion ;
	private Set<TipoDocumentoDTO> listaTipoDocumento = new HashSet<>();
	
	
	
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
	public Set<TipoDocumentoDTO> getListaTipoDocumento() {
		return listaTipoDocumento;
	}
	public void setListaTipoDocumento(Set<TipoDocumentoDTO> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}
	
}
