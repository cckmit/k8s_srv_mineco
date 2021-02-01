package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class FamiliaTipoDocumentoDTO implements Serializable {

	private static final long serialVersionUID = 1756236905480105216L;
	
	public FamiliaTipoDocumentoDTO(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public FamiliaTipoDocumentoDTO(TipoDocumentoDTO tipoDoc) {
		super();
		listaTipoDocumento.add(tipoDoc);
	}

	private String nombre ;

	private Set<TipoDocumentoDTO> listaTipoDocumento = new TreeSet<TipoDocumentoDTO>();
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<TipoDocumentoDTO> getListaTipoDocumento() {
		return listaTipoDocumento;
	}
	public void setListaTipoDocumento(Set<TipoDocumentoDTO> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}
	
}
